package dsl.steps.preparation

import dsl.ClosureExtractor

class PreparationStep {

    Preprocessing preprocessing;

    String filePath;
    String testPath;
    String trainPath;

    int training_size = 80;
    int seed = 2394883;


    void fetch(filePath) {
        this.filePath = filePath;
    }


    void generateDataset(dataSetClosure) {
        ClosureExtractor.extract(dataSetClosure, this)

    }

    void seed(value) {
        this.seed = value;
    }

    void training_size(value) {
        this.training_size = value;
    }


    void preprocessing(preprocessingClosure) {
        Preprocessing preprocessing = new Preprocessing()
        ClosureExtractor.extract(preprocessingClosure, preprocessing)
        this.preprocessing = preprocessing
    }


}
