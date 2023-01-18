package dsl.steps.training.classifier.knn


import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class KnnDistributionParams extends DistributionParameters {
    Function neighborsNumber;
    def algo;



    void neighborsNumber(functionObject) {
        this.neighborsNumber = functionObject
    }


    void algo(... value) {
        this.algo = value.collect { v -> "\"$v\"" }
    }


}
