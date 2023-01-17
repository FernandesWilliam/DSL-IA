package kernel.comparaison

import dsl.steps.comparaison.ComparisonStep
import kernel.Generator
import kernel.StringUtils


class ComparisonGenerator implements Generator {
    StringBuilder comparisonBuilder = new StringBuilder("## # validation + comparaison").append(StringUtils.lineFeed())
    def models = []
    def criterias = []
    def weights = []

    ComparisonGenerator(ComparisonStep comparisonStep) {
        this.models = comparisonStep.toCompare;
        this.criterias = comparisonStep.criteria;
        this.weights = comparisonStep.criteriaWeight;
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
        comparisonBuilder.append("socring = {${scoringCriteria}}").append(StringUtils.lineFeed());
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

    def generateScores(model) {
        comparisonBuilder.append("scores_${model} = cross_validate(gs_${model}, X_train_t1, y_train, cv=2, scoring = scoring)").append(StringUtils.lineFeed())
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
