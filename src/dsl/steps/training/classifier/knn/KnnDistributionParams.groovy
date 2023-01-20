package dsl.steps.training.classifier.knn


import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class KnnDistributionParams extends DistributionParameters {
    Function nNeighbors;
    def algorithm;



    void nNeighbors(functionObject) {
        this.nNeighbors = functionObject
    }


    void algorithm(... value) {
        this.algorithm = value.collect { v -> "\"$v\"" }
    }


}
