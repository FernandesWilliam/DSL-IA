package kernel.training

import dsl.steps.training.classifier.Classifier
import dsl.steps.training.functions.Function
import kernel.Generator
import kernel.stringutils.StringUtils


class ClassifierGenerator implements Generator {

    StringBuilder stringBuilder;

    ClassifierGenerator(String name, Classifier classifier, classifierTuple) {
        def transform = classifier.transformations.collect {
            trans -> "('${name}_${trans}',${trans})"
        }
        def hyperParams = []
        for (def entry in classifier.distributionParameters.properties.entrySet()) {
            if (entry.key == "class") continue
            def key = entry.key.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' )
            hyperParams << "\'${classifier.getPipelineName()}__${key}\': ${entry.value instanceof Function ? entry.value.function : entry.value}"
        }

        def pipeName = "pipe_${name}"
        def distributionName = "distribution_${name}_param"
        def kfoldName = "kfold_${name}"
        def rsName = "rs_$name";

        stringBuilder = new StringBuilder().append("$kfoldName=${classifier.kfold.function}")
                .append(StringUtils.lineFeed())
                .append("$pipeName= Pipeline([$classifierTuple])")
                .append(StringUtils.lineFeed())
                .append(("$distributionName={${hyperParams.join(",")} }").replaceAll(/"/, /'/))
                .append(StringUtils.lineFeed())

                .append("$rsName =RandomizedSearchCV(estimator= $pipeName," +
                        "param_distributions = $distributionName, " +
                        "cv =$kfoldName," +
                        "  verbose = 2, " +
                        "n_jobs = -1, " + "n_iter = 5)")
                .append(StringUtils.lineFeed())
    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}