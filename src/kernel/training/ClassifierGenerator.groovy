package kernel.training

import dsl.steps.training.classifier.Classifier
import dsl.steps.training.functions.Function
import kernel.Generator
import kernel.StringUtils

class ClassifierGenerator implements Generator {


    StringBuilder stringBuilder;

    ClassifierGenerator(String name, Classifier classifier, classifierTuple) {

        def transform = classifier.transformations.collect {
            trans -> "('${name}_${trans}',${trans})"
        }
        def hyperParams = []
        for (def entry in classifier.distributionParameters.properties.entrySet()) {
            if (entry.key != "class")
                hyperParams << "\"${entry.key}\": ${entry.value instanceof Function ? entry.value.function : entry.value}"
        }

        def pipeName = "pipe_${name}"
        def distributionName = "distribution_${name}_param"
        def kfoldName = "kfold_${name}"
        def rsName = "rs_$name";
        def scoring = classifier.scoring.collect { sc -> "\"$sc\":\"$sc\"" }.join(",")

        stringBuilder = new StringBuilder().append("$kfoldName=${classifier.kfold.function}")
                .append(StringUtils.lineFeed())
                .append("$pipeName= Pipeline([${transform.join(',')} ,$classifierTuple])")
                .append(StringUtils.lineFeed())
                .append("$distributionName={${hyperParams.join(",")} }")
                .append(StringUtils.lineFeed())

                .append("$rsName =RandomizedSearchCV(estimator= $pipeName," +
                        "param_distributions = $distributionName, " +
                        "cv =$kfoldName," +
                        "  verbose = 2, " +
                        "n_jobs = -1, " + "n_iter = 5)")
                .append(StringUtils.lineFeed())
                .append("scores_$name = cross_validate(rsName, Xtrain ,y_train,cv=${classifier.cv},scoring={${scoring}}) ")
    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
