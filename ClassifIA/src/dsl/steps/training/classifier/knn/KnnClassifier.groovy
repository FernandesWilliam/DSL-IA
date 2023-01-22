package dsl.steps.training.classifier.knn

import dsl.ClosureExtractor
import dsl.steps.training.classifier.Classifier

class KnnClassifier extends Classifier {

    @Override
    void distributionParams(hyperParamClosure) {
        distributionParameters = new KnnDistributionParams();
        ClosureExtractor.extract(hyperParamClosure, distributionParameters)
    }

    @Override
    String getPipelineName(){
        return "clf_knn"
    }
}
