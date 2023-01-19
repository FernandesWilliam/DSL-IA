package dsl.steps.preparation

import dsl.ClosureExtractor
import dsl.steps.DSLThrower
import kernel.stringutils.StringUtilsJupyter

class PreparationStep implements DSLThrower{

    Preprocessing preprocessing;

    String filePath = null;
    String testPath = null;
    String trainPath;

    int training_size = 80;
    int seed = 2394883;


    void fetch(filePath) {
        if(filePath.isEmpty()) {
            reject("data filePath is invalid [\""+filePath+"\"]")
        }
        this.filePath = filePath;
    }

    void train(String trainFilePath) {
        String errorMsg = "";
        if(filePath != null) {
            errorMsg += "data file already exist above => "+filePath + StringUtilsJupyter.lineFeed();
        }
        if(trainFilePath.isEmpty()) {
            errorMsg += "data filePath is invalid [\""+trainFilePath+"\"]" + StringUtilsJupyter.lineFeed();
        }
        if(!errorMsg.isEmpty()) {
            reject(errorMsg);
        }
        this.trainPath = trainFilePath
    }

    void test(String testFilePath) {
        String errorMsg = "";
        if(filePath != null) {
            errorMsg += "data file already exist above => "+filePath + StringUtilsJupyter.lineFeed();
        }
        if(trainPath == null) {
            errorMsg += "train data file not defined before" + StringUtilsJupyter.lineFeed();
        }
        if(testFilePath.isEmpty()) {
            errorMsg += "data filePath is invalid [\""+testFilePath+"\"]" + StringUtilsJupyter.lineFeed();
        }
        if(!errorMsg.isEmpty()) {
            reject(errorMsg);
        }
        this.testPath = testFilePath
    }


    void generateDataset(dataSetClosure) {
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
