package dsl.steps.training.classifier.gaussian

import dsl.ClosureExtractor
import dsl.steps.training.classifier.Classifier

class GaussianClassifier extends Classifier {
    @Override
    void distributionParams(Object hyperParamClosure) {
        distributionParameters = new GaussianDistributionParams();
        ClosureExtractor.extract(hyperParamClosure, distributionParameters)
    }
}
