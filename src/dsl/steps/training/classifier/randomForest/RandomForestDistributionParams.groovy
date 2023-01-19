package dsl.steps.training.classifier.randomForest

import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class RandomForestDistributionParams extends DistributionParameters {
    def maxDepth = [];
    Function minSamplesSplit;
    Function maxFeatures;
    Function minSamplesLeaf;
    String[] bootstrap;
    String[] criterion;

    void maxDepth(... args) {
        this.maxDepth = args;
    }

    void minSamplesLeaf(funcObject) {
        this.minSamplesLeaf = funcObject;
    }


    void bootstrap(Boolean... args) {
        this.bootstrap = args.collect { b -> b.toString().capitalize() };
    }

    void criterion(... criterion) {
        this.criterion = criterion.collect { v -> "\"$v\"" }
    }

    void maxFeatures(funcObject) {
        this.maxFeatures = funcObject;
    }

    void minSamplesSplit(funcObject) {
        this.minSamplesSplit = funcObject;
    }


}
