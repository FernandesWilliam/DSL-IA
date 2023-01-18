package kernel.training

import dsl.steps.training.classifier.Classifier
import dsl.steps.training.functions.Function
import kernel.Generator
import kernel.notebook.CodeBlockGenerator

class ClassifierGenerator implements Generator {


    StringBuilder stringBuilder;

    ClassifierGenerator(String name, Classifier classifier, classifierTuple) {

        def transform = classifier.transformations.collect {
            trans -> "('${name}_${trans}',${trans})"
        }
        def hyperParams = []
        for (def entry in classifier.distributionParameters.properties.entrySet()) {
            if (entry.key != "class") continue
            hyperParams << "\'${entry.key}\': ${entry.value instanceof Function ? entry.value.function : entry.value}"

        }

        def pipeName = "pipe_${name}"
        def distributionName = "distribution_${name}_param"
        def kfoldName = "kfold_${name}"
        def rsName = "rs_$name";
        def scoring = classifier.scoring.collect { sc -> "\'$sc\':\'$sc\'" }.join(",")
        stringBuilder = new StringBuilder().append("$kfoldName=${classifier.kfold.function}")
                .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)
                .append("$pipeName= Pipeline([$classifierTuple])")
                .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

                .append("$distributionName={${hyperParams.join(",")} }")
                .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

                .append("$rsName =RandomizedSearchCV(estimator= $pipeName," +
                        "param_distributions = $distributionName, " +
                        "cv =$kfoldName," +
                        "  verbose = 2, " +
                        "n_jobs = -1, " + "n_iter = 5)")
                .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
