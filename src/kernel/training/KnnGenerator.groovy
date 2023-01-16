package kernel.training

import dsl.steps.training.classifier.knn.KnnClassifier
import dsl.steps.training.functions.Function
import kernel.Generator
import kernel.StringUtils
import kernel.notebook.BlockGenerator

class KnnGenerator implements Generator {
    //  kfold stratified(2, true)
    //            scoring accuracy, time
    //            cv 5
    //            distributionParams {
    //                clf_knn__n_neighbors randint(1, 11)
    //                clf_knn__algorithm 'auto'
    //            }
//    //            transformations minMax02, pca55
//    rand_list_rf = {"clf__max_depth": [5, None],
//        "clf__max_features": sp_randint(1, 11),
//        "clf__min_samples_split": sp_randint(2, 11),
//        "clf__min_samples_leaf": sp_randint(1, 11),
//        "clf__bootstrap": [True, False],
//        "clf__criterion": ["gini", "entropy"]}


    // # gs_RF is training name
//    gs_RF = RandomizedSearchCV(estimator = pipe_rf,
//                               param_distributions = rand_list_rf,
//                               cv = kfold,
//                               verbose = 2,
//                               n_jobs = -1,
//                               n_iter = 5)

    StringBuilder stringBuilder;

    KnnGenerator(String name, KnnClassifier knnClassifier) {
        def transform = knnClassifier.transformations.collect {
            trans -> "('${name}_${trans}',${trans})"
        }
        def hyperParams = []
        for (def entry in knnClassifier.distributionParameters.properties.entrySet()) {
            if (entry.key != "class")
                hyperParams << "\"${entry.key}\": ${entry.value instanceof Function ? entry.value.function : entry.value}" + BlockGenerator.NEWLINE
        }

        def pipeName = "pipe_${name}"
        def distributionName = "distribution_${name}_param"
        def kfoldName = "kfold_${name}"
        def rsName = "rs_$name";
        def scoring = knnClassifier.scoring.collect { sc -> "\"$sc\":\"$sc\"" }.join(",")

        stringBuilder = new StringBuilder()
                .append("$kfoldName=${knnClassifier.kfold.function}")
                .append(BlockGenerator.NEWLINE)
                .append("$pipeName= Pipeline([${transform.join(',')} ,('clf_knn', KNeighborsClassifier())])")
                .append(BlockGenerator.NEWLINE)
                .append("$distributionName={"+BlockGenerator.NEWLINE+"${hyperParams.join(",")} }")
                .append(BlockGenerator.NEWLINE)
                .append("$rsName =RandomizedSearchCV(estimator= $pipeName," +
                        "param_distributions = $distributionName, " + BlockGenerator.NEWLINE +
                        "cv =$kfoldName," + BlockGenerator.NEWLINE +
                        "  verbose = 2, " + BlockGenerator.NEWLINE +
                        "n_jobs = -1, " + "n_iter = 5)")
                .append(BlockGenerator.NEWLINE)
                .append("scores_$name = cross_validate(rsName, Xtrain ,y_train,cv=${knnClassifier.cv},scoring={${scoring}}) ")
    }


    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
