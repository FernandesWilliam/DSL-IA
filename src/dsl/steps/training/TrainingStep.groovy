package dsl.steps.training

import dsl.ClosureExtractor
import dsl.steps.training.classifier.gaussian.GaussianMapper
import dsl.steps.training.classifier.knn.KnnMapper
import dsl.steps.training.classifier.randomForest.RandomForestMapper

class TrainingStep {


    KnnMapper knnMapper;
    GaussianMapper gaussianMapper;
    RandomForestMapper randomForestMapper;

    void knn(knnClosure) {
        knnMapper = new KnnMapper();
        ClosureExtractor.extract(knnClosure, knnMapper)
    }


    void gaussian(knnClosure) {
        gaussianMapper = new GaussianMapper();
        ClosureExtractor.extract(knnClosure, gaussianMapper)
    }

    void rndForest(rndClosure){
        randomForestMapper = new RandomForestMapper();
        ClosureExtractor.extract(rndClosure, randomForestMapper)
    }
}
