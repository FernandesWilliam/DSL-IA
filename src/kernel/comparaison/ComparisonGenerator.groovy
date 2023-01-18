package kernel.comparaison

import dsl.steps.DSLThrower
import dsl.steps.comparison.ComparisonStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.StringUtils


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
        def scoringCriteria = new StringBuilder()
        boolean firstCriteria = true;
        for (def criteria : criterias) {
            if (!firstCriteria) {
                scoringCriteria.append(",\n").append(StringUtils.tab()).append(StringUtils.tab()).append(StringUtils.tab())
            }
            println(criteria)
            scoringCriteria.append("\'${criteria.toString()}\' : \'$criteria\'")
            firstCriteria = false;
        }
        comparisonBuilder.append("scoring = {${scoringCriteria}}").append(StringUtils.lineFeed());
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

    def findModelTransformation(model) {
        if (model in trainingStep.randomForestMapper.map) {
            return trainingStep.randomForestMapper.map[model].transformation
        }
        if (model in trainingStep.gaussianMapper.map) {
            return trainingStep.gaussianMapper.map[model].transformation
        }
        if (model in trainingStep.knnMapper.map) {
            return trainingStep.knnMapper.map[model].transformation
        }

    }

    def generateScores(model) {
        def transformation = findModelTransformation(model)
        if(transformationStep.pipelines.find{ pipe-> pipe[0]==transformation} == null){
            reject("$transformation transformation doesn't exist")
        }

        comparisonBuilder.append("scores_${model} = cross_validate(rs_${model},X_train_${transformation}, y_train, cv=2, scoring = scoring)").append(StringUtils.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("${criteria}_${model} = np.mean(scores_rf['${criteria}']), np.std(scores_rf['${criteria}'])").append(StringUtils.lineFeed())
        }
        comparisonBuilder.append(StringUtils.lineFeed())
        for (def criteria : criterias) {
            comparisonBuilder.append("scores['${model}']['${criteria}'].append(scores_${model}['${criteria}'])").append(StringUtils.lineFeed());
        }
    }

    def generateGlobalsScoresComputation() {
        comparisonBuilder.append(StringUtils.lineFeed()).append("# COMPUTE GLOBAL SCORE").append(StringUtils.lineFeed());
        comparisonBuilder.append("models_scores = {}").append(StringUtils.lineFeed());
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
            comparisonBuilder.append("models_scores['${model}'] = ${computation}").append(StringUtils.lineFeed());
        }
    }

    def generateWinningModel() {
        comparisonBuilder.append(StringUtils.lineFeed()).append("# WINNER MODEL").append(StringUtils.lineFeed())
        comparisonBuilder.append("winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]").append(StringUtils.lineFeed())
        comparisonBuilder.append("print(\"winner model :\",winner_model)")
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
