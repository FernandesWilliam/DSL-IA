package kernel.training

import dsl.steps.training.TrainingStep
import dsl.steps.training.classifier.Classifier
import dsl.steps.transformation.Mapper
import kernel.Generator
import kernel.stringutils.StringUtils

class TrainingGenerator implements Generator {

    StringBuilder stringBuilder;


    TrainingGenerator(TrainingStep trainingStep) {
        stringBuilder = new StringBuilder()

        if (trainingStep.knnMapper) {
            generateTrain(trainingStep.knnMapper,"('clf_knn', KNeighborsClassifier())","KNN CLASSIFIER")
        }
        if (trainingStep.gaussianMapper) {
            generateTrain(trainingStep.gaussianMapper,"('clf_nb', GaussianNB())","GAUSSIAN CLASSIFIER")
        }
        if (trainingStep.randomForestMapper) {
            generateTrain(trainingStep.randomForestMapper,"('clf', RandomForestClassifier())","RANDOMFOREST CLASSIFIER")
        }

    }



    def generateTrain(Mapper mapper, String tupleName, String commentType) {
        stringBuilder.append(StringUtils.comment("$commentType"));
        for (def entries in mapper.map) {
            def generation = StringUtils.generateCodeBlock(new ClassifierGenerator(entries.key as String, entries.value as Classifier, tupleName))
            stringBuilder.append(generation)
        }


    }



    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}


