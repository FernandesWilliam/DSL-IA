package dsl.steps.training.classifier.randomForest

import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class RandomForestDistributionParams extends DistributionParameters {
    def clf__max_depth = [];
    Function clf__min_samples_split;
    Function clf__max_features;
    Function clf__min_samples_leaf;
    Boolean[] clf__bootstrap;
    String[] clf__criterion;

    void clf__max_depth(... args) {
        this.clf__max_depth = args;
    }

    void clf__min_samples_leaf(funcObject) {
        this.clf__min_samples_leaf = funcObject;
    }

    void clf__bootstrap(... args) {
        this.clf__bootstrap = args;

    }

    void clf__criterion(... criterion) {
        this.clf__criterion = criterion;
    }

    void clf__max_features(funcObject) {
        this.clf__max_features = funcObject;
    }

    void clf__min_samples_split(funcObject) {
        this.clf__min_samples_split = funcObject;
    }


}
