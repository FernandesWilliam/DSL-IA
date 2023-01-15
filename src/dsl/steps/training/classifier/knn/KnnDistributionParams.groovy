package dsl.steps.training.classifier.knn


import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class KnnDistributionParams extends DistributionParameters {
    Function clf_knn__n_neighbors;
    def clf_knn__algorithm = []

    void clf_knn__n_neighbors(functionObject) {
        this.clf_knn__n_neighbors = functionObject
    }


    void clf_knn__algorithm(... value) {
        this.clf_knn__algorithm = value
    }


}
