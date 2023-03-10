package dsl.steps.preparation

import dsl.ClosureExtractor
import dsl.steps.DSLThrower

class PreparationStep implements DSLThrower{

    Preprocessing preprocessing;

    String filePath = null;
    String testPath = null;
    String trainPath;

    int training_size = 80;
    int seed = 2394883;


    void fetchAll(filePath) {
        if(filePath.isEmpty()) {
            reject("data filePath is invalid [\""+filePath+"\"]")
        }
        this.filePath = filePath;
    }

    void fetchTrain(String trainFilePath) {
        String errorMsg = "";
        if(filePath != null) {
            errorMsg += "data file already exist above => "+filePath + StringUtils.lineFeed();
        }
        if(trainFilePath.isEmpty()) {
            errorMsg += "data filePath is invalid [\""+trainFilePath+"\"]" + StringUtils.lineFeed();
        }
        if(!errorMsg.isEmpty()) {
            reject(errorMsg);
        }
        this.trainPath = trainFilePath
    }

    void fetchTest(String testFilePath) {
        String errorMsg = "";
        if(filePath != null) {
            errorMsg += "data file already exist above => "+filePath + StringUtils.lineFeed();
        }
        if(trainPath == null) {
            errorMsg += "train data file not defined before" + StringUtils.lineFeed();
        }
        if(testFilePath.isEmpty()) {
            errorMsg += "data filePath is invalid [\""+testFilePath+"\"]" + StringUtils.lineFeed();
        }
        if(!errorMsg.isEmpty()) {
            reject(errorMsg);
        }
        this.testPath = testFilePath
    }


    void splitDataset(dataSetClosure) {
        if(filePath == null && trainPath != null){
            reject("generateDataset not needed, data already split into test and train dataset")
        }
        if(filePath == null && trainPath == null) {
            reject("generateDataset : expect filePath to be not null <data file not defined in this scope>")
        }
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
