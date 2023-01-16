package kernel.preparation

import kernel.Generator
import kernel.StringUtils
import kernel.notebook.BlockGenerator

class TrainTestSplitterGenerator implements Generator {


    StringBuilder trainTestBuilder = new StringBuilder("##DATASET SPLIT")

    TrainTestSplitterGenerator(trainingSize, seed) {
        trainTestBuilder
                .append(BlockGenerator.NEWLINE)
                .append("X,y = dataset.drop(['label'], axis = 1), dataset['label']")
                .append(BlockGenerator.NEWLINE)
                .append("X_train, X_test, y_train, y_test=" +
                        "train_test_split(X, y,test_size=${trainingSize},random_state=${seed})")
    }

    TrainTestSplitterGenerator() {
        trainTestBuilder
                .append(BlockGenerator.NEWLINE)
                .append("X_train, y_train = dataTrainSet.drop(['label'], axis = 1), dataTrainSet['label']")
                .append(BlockGenerator.NEWLINE)
                .append("X_test, y_test = dataTestSet.drop(['label'], axis = 1), dataTestSet['label']")
    }


    @Override
    def generate(Object maps) {
        return trainTestBuilder
    }
}
