package dsl.steps.training.classifier.randomForest

import dsl.ClosureExtractor
import dsl.steps.training.classifier.Classifier

class RandomForestClassifier extends Classifier {


    int n_estimators;
    String class_weight;

    @Override
    void distributionParams(Object hyperParamClosure) {
        distributionParameters = new RandomForestDistributionParams();
        ClosureExtractor.extract(hyperParamClosure, distributionParameters)
    }


    void n_estimators(number) {
        this.n_estimators = number;
    }


    void class_weight(classWeight) {
        this.class_weight = classWeight;
    }
}
