package kernel.comparaison

import dsl.steps.DSLThrower
import dsl.steps.comparison.ComparisonStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.stringutils.StringUtilsJupyter


class ComparisonGenerator implements Generator, DSLThrower {
    StringBuilder comparisonBuilder = new StringBuilder("## # validation + comparaison").append(StringUtilsJupyter.lineFeed())
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
        def scoringCriteria = new StringBuilder()
        boolean firstCriteria = true;
        for (def criteria : criterias) {
            if (!firstCriteria) {
                scoringCriteria.append(StringUtilsJupyter.lineFeed()).append(StringUtilsJupyter.tab()).append(StringUtilsJupyter.tab()).append(StringUtilsJupyter.tab())
            }
            println(criteria)
            scoringCriteria.append("\'${criteria.toString()}\' : \'$criteria\'")
            firstCriteria = false;
        }
        comparisonBuilder.append("scoring = {${scoringCriteria}}").append(StringUtilsJupyter.lineFeed());
        comparisonBuilder.append("scores = dict()").append(StringUtilsJupyter.lineFeed());
        for (int i = 0; i < criterias.size(); ++i) {
            comparisonBuilder.append("${criterias[i]}_coef = ${weights[i]}").append(StringUtilsJupyter.lineFeed())
        }
    }

    def generateModelsValidation() {
        for (def model : models) {
            comparisonBuilder.append(StringUtilsJupyter.lineFeed()).append("# ${model.toUpperCase()}").append(StringUtilsJupyter.lineFeed())
            initScoresStorageForModel(model);
            comparisonBuilder.append(StringUtilsJupyter.lineFeed());
            generateScores(model);
        }
    }

    def initScoresStorageForModel(model) {
        comparisonBuilder.append("scores['${model}'] = {}").append(StringUtilsJupyter.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("scores['${model}']['${criteria}'] = []").append(StringUtilsJupyter.lineFeed())
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

        comparisonBuilder.append("scores_${model} = cross_validate(rs_${model},X_train_${modelObject.transformation}, y_train, cv=${modelObject.cv}, scoring = scoring)").append(StringUtilsJupyter.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("${criteria}_${model} = np.mean(scores_rf['${criteria}']), np.std(scores_rf['${criteria}'])").append(StringUtilsJupyter.lineFeed())
        }
        comparisonBuilder.append(StringUtilsJupyter.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("scores['${model}']['${criteria}'].append(scores_${model}['${criteria}'])").append(StringUtilsJupyter.lineFeed());
        }
    }

    def generateGlobalsScoresComputation() {
        comparisonBuilder.append(StringUtilsJupyter.lineFeed()).append("# COMPUTE GLOBAL SCORE").append(StringUtilsJupyter.lineFeed());
        comparisonBuilder.append("models_scores = {}").append(StringUtilsJupyter.lineFeed());
        for (def model : models) {
            boolean firstCriteria = true;
            String computation = "";
            for (def criteria : criterias) {
                if (!firstCriteria) {
                    computation += " + "
                }
                computation += "${criteria}_${model} * ${criteria}_coef";
                firstCriteria = false;
            }
            comparisonBuilder.append("models_scores['${model}'] = ${computation}").append(StringUtilsJupyter.lineFeed());
        }
    }

    def generateWinningModel() {
        comparisonBuilder.append(StringUtilsJupyter.lineFeed()).append("# WINNER MODEL").append(StringUtilsJupyter.lineFeed())
        comparisonBuilder.append("winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]").append(StringUtilsJupyter.lineFeed())
        comparisonBuilder.append("print(\'winner model :\',winner_model)")
    }


    @Override
    def generate(Object maps) {
        generateScoreNeededVariables();
        generateModelsValidation();
        generateGlobalsScoresComputation();
        generateWinningModel();
        return comparisonBuilder
    }
}
