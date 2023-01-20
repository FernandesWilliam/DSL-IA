package kernel.comparaison

import dsl.steps.DSLThrower
import dsl.steps.comparison.ComparisonStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.stringutils.StringUtils


class ComparisonGenerator implements Generator, DSLThrower {
    StringBuilder comparisonBuilder = new StringBuilder("## # validation + comparaison").append(StringUtils.lineFeed())
    def models = []
    def criterias = []
    def weights = []

    def trainingStep;

    def transformationStep;
    ComparisonGenerator(ComparisonStep comparisonStep, TrainingStep trainingStep,TransformationStep transformationStep) {
        this.models = comparisonStep.toCompare;
        this.criterias = comparisonStep.criteria;
        this.weights = comparisonStep.criteriaWeight;
        // verify that they a

        for (def value : this.models) {
            if (trainingStep.randomForestMapper.map.containsKey(value) || trainingStep.gaussianMapper.map.containsKey(value) ||
                    trainingStep.knnMapper.map.containsKey(value)) continue
            reject("The training algorithm : ${value} isn't declared in training phase")
        }
        this.trainingStep = trainingStep;
        this.transformationStep = transformationStep;
    }


    /**
     * to generate scoring criteria object, scores where to store scores for each model and coefs for each criteria
     */
    def generateScoreNeededVariables() {

        comparisonBuilder.append("scoring = {'acc' : 'accuracy'}").append(StringUtils.lineFeed());
        comparisonBuilder.append("scores = dict()").append(StringUtils.lineFeed());
        for (int i = 0; i < criterias.size(); ++i) {
            comparisonBuilder.append("${criterias[i]}_coef = ${weights[i]}").append(StringUtils.lineFeed())
        }
    }

    def generateModelsValidation() {
        for (def model : models) {
            comparisonBuilder.append(StringUtils.lineFeed()).append("# ${model.toUpperCase()}").append(StringUtils.lineFeed())
            initScoresStorageForModel(model);
            comparisonBuilder.append(StringUtils.lineFeed());
            generateScores(model);
        }
    }

    def initScoresStorageForModel(model) {
        comparisonBuilder.append("scores['${model}'] = {}").append(StringUtils.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("scores['${model}']['${criteria}'] = []").append(StringUtils.lineFeed())
        }
    }

    def findModelObject(model) {
        if (model in trainingStep.randomForestMapper.map) {
            return trainingStep.randomForestMapper.map[model]
        }
        if (model in trainingStep.gaussianMapper.map) {
            return trainingStep.gaussianMapper.map[model]
        }
        if (model in trainingStep.knnMapper.map) {
            return trainingStep.knnMapper.map[model]
        }

    }

    def generateScores(model) {
        def modelObject = findModelObject(model)
        if(transformationStep.pipelines.find{ pipe-> pipe[0]==modelObject.transformation} == null){
            reject("$modelObject.transformation transformation doesn't exist")
        }

        comparisonBuilder.append("scores_${model} = cross_validate(rs_${model},X_train_${modelObject.transformation}, y_train, cv=${modelObject.cv}, scoring = scoring)").append(StringUtils.lineFeed())
        for (def criteria : criterias) {

            comparisonBuilder.append("${criteria}_${model} = np.mean(scores_${model}['${criteria}'])").append(StringUtils.lineFeed())
        }
        comparisonBuilder.append(StringUtils.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("scores['${model}']['${criteria}'] = scores_${model}['${criteria}']").append(StringUtils.lineFeed());
        }
    }

    def generateGlobalsScoresComputation() {
        comparisonBuilder.append(StringUtils.lineFeed()).append("# COMPUTE GLOBAL SCORE").append(StringUtils.lineFeed());
        comparisonBuilder.append("models_scores = {}").append(StringUtils.lineFeed());
        for (def model : models) {
            String computation = "1";
            for (def criteria : criterias) {
                if(criteria in ['fit_time']){
                    computation += " * (${criteria}_coef / (1+${criteria}_${model}))"
                }
                else{
                    computation += " * (${criteria}_${model} * ${criteria}_coef)"
                }
            }
            comparisonBuilder.append("models_scores['${model}'] = ${computation}").append(StringUtils.lineFeed());
        }
    }

    def generateWinningModel() {

        comparisonBuilder.append(StringUtils.lineFeed()).append("# WINNER MODEL").append(StringUtils.lineFeed())
        comparisonBuilder.append("winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]").append(StringUtils.lineFeed())
        comparisonBuilder.append("print('winner model :',winner_model)").append(StringUtils.lineFeed())
    }

    def generateComparisonChart() {
        comparisonBuilder.append(StringUtils.lineFeed()).append(StringUtils.lineFeed()).append("# COMPARISON CHART").append(StringUtils.lineFeed())
        for(def model : models) {
            def criteriaToPlot = "";
            for (def criteria : criterias) {
                criteriaToPlot += "scores['${model}']['${criteria}'], "
            }
            comparisonBuilder.append("plt.scatter(${criteriaToPlot}marker='x', color='${randomRgbCode()}', label='${model}')").append(StringUtils.lineFeed())
        }
        comparisonBuilder.append("plt.title('MODELS COMPARISON')").append(StringUtils.lineFeed())
        if(criterias.size() == 2){
            comparisonBuilder.append("plt.xlabel('accuracy')").append(StringUtils.lineFeed())
            comparisonBuilder.append("plt.ylabel('fit_time(sec)')").append(StringUtils.lineFeed())
        }
        else{
            comparisonBuilder.append("plt.ylabel('${criterias[0]}')").append(StringUtils.lineFeed())
        }
        comparisonBuilder.append("plt.legend(loc='lower right', bbox_to_anchor=(1.3,0))").append(StringUtils.lineFeed())
        comparisonBuilder.append("plt.show()").append(StringUtils.lineFeed());
    }

    def generateComparisonTable(){
        comparisonBuilder.append(StringUtils.lineFeed()).append(StringUtils.lineFeed()).append("# COMPARISON TABLE").append(StringUtils.lineFeed())

        for(def model: models)
            comparisonBuilder.append("models_scores['${model}'] = np.round(models_scores['${model}'],3)").append(StringUtils.lineFeed())


        for (def criteria : criterias) {
            for(def model: models)
                comparisonBuilder.append("scores['${model}']['${criteria}'] = np.round(np.average(scores['${model}']['${criteria}']),3)").append(StringUtils.lineFeed())
        }

        comparisonBuilder.append(StringUtils.lineFeed())
                .append("fig, ax = plt.subplots()").append(StringUtils.lineFeed())
                .append("fig.patch.set_visible(False)").append(StringUtils.lineFeed())
                .append("ax.axis('off')").append(StringUtils.lineFeed())
                .append("ax.axis('tight')").append(StringUtils.lineFeed())
                .append("df = pd.DataFrame([")

        for (def criteria : criterias) {
            comparisonBuilder.append("['${criteria}', ")
            for(def model: models)
                comparisonBuilder.append("scores['${model}']['${criteria}'],")
            comparisonBuilder.setLength(comparisonBuilder.length() - 1)
            comparisonBuilder.append("],")
        }
        comparisonBuilder.append("['total', ")

        for(def model: models)
            comparisonBuilder.append("models_scores['${model}'], ")
        comparisonBuilder.setLength(comparisonBuilder.length() - 1)
        comparisonBuilder.append("]],columns=['metric',")

        for(def model: models)
            comparisonBuilder.append("'${model}',")
        comparisonBuilder.setLength(comparisonBuilder.length() - 1)
        comparisonBuilder.append("])").append(StringUtils.lineFeed())
                .append("ax.table(cellText=df.values, colLabels=df.columns, loc='center')").append(StringUtils.lineFeed())
                .append("fig.tight_layout()").append(StringUtils.lineFeed())
                .append("plt.show()").append(StringUtils.lineFeed())

    }

    def randomRgbCode() {
        def rgb = new Random().nextInt(1<<24) // A random 24-bit integer
        return '#' + Integer.toString(rgb, 16).padLeft(6, '0')
    }


    @Override
    def generate(Object maps) {
        generateScoreNeededVariables();
        generateModelsValidation();
        generateGlobalsScoresComputation();
        generateWinningModel();
        generateComparisonChart();
        generateComparisonTable()
        return comparisonBuilder
    }
}


