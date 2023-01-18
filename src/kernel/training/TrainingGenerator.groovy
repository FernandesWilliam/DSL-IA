package kernel.training

import dsl.steps.training.TrainingStep
import dsl.steps.training.classifier.Classifier
import dsl.steps.training.classifier.gaussian.GaussianMapper
import dsl.steps.training.classifier.knn.KnnMapper
import dsl.steps.training.classifier.randomForest.RandomForestMapper
import dsl.steps.transformation.Mapper
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.StringUtils
import kernel.transformation.TransformationGenerator

class TrainingGenerator implements Generator {

    StringBuilder stringBuilder;

    TrainingGenerator(TrainingStep trainingStep) {
        stringBuilder = new StringBuilder("###### ---- TRAINING PHASE ---- ######").append(StringUtils.lineFeed())
        if (trainingStep.knnMapper) {
            generateTrain(trainingStep.knnMapper,"('knn', KNeighborsClassifier())","KNN CLASSIFIER")
        }
        if (trainingStep.gaussianMapper) {
            generateTrain(trainingStep.gaussianMapper,"('gauss', GaussianClassifier())","GAUSSIAN CLASSIFIER")
        }
        if (trainingStep.randomForestMapper) {
            generateTrain(trainingStep.randomForestMapper,"('randForest', RandomForestClassifier())","RANDOMFOREST CLASSIFIER")
        }

    }



    def generateTrain(Mapper mapper, String tupleName, String commentType) {
        stringBuilder.append("##$commentType")
                .append(StringUtils.lineFeed())
        for (def entries in mapper.map) {
            def generation = new ClassifierGenerator(entries.key as String, entries.value as Classifier, tupleName).generate()
            stringBuilder.append(generation)
                    .append(StringUtils.lineFeed(2))
        }


    }



    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}


