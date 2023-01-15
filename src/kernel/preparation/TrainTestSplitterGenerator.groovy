package kernel.preparation

import kernel.Generator
import kernel.StringUtils

class TrainTestSplitterGenerator implements Generator {


    StringBuilder trainTestBuilder;

    TrainTestSplitterGenerator(trainingSize, seed) {
        trainTestBuilder = new StringBuilder().append("#Divide Dataset")
                .append(StringUtils.lineFeed())
                .append("X,y = dataset.drop(['label'], axis = 1), dataset['label']")
                .append(StringUtils.lineFeed())
                .append("X_train, X_test, y_train, y_test=" +
                        "train_test_split(X, y,test_size=${trainingSize},random_state=${seed})")
    }


    @Override
    def generate(Object maps) {
        return trainTestBuilder
    }
}
