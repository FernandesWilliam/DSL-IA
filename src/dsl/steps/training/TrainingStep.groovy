package dsl.steps.training

import dsl.ClosureExtractor
import dsl.steps.Step
import dsl.steps.training.classifier.gaussian.GaussianMapper
import dsl.steps.training.classifier.knn.KnnMapper
import dsl.steps.training.classifier.randomForest.RandomForestMapper

class TrainingStep extends Step {


    KnnMapper knnMapper = new KnnMapper();
    GaussianMapper gaussianMapper = new GaussianMapper();
    RandomForestMapper randomForestMapper = new RandomForestMapper();


    void knn(knnClosure) {

        knnMapper.mapNewVariable(currentVariable, knnClosure)
    }

    void gaussian(gaussianClosure) {
        gaussianMapper.mapNewVariable(currentVariable, gaussianClosure)
    }

    void rndForest(rndClosure) {
        randomForestMapper.mapNewVariable(currentVariable, rndClosure)
    }


}
