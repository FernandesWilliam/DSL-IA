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
import kernel.notebook.BlockGenerator
import kernel.transformation.TransformationGenerator

class TrainingGenerator implements Generator {

    StringBuilder stringBuilder;

    TrainingGenerator(TrainingStep trainingStep, TransformationStep transformationStep) {
        stringBuilder = new StringBuilder("###### ---- TRAINING PHASE ---- ######").append(BlockGenerator.NEWLINE)
        if (trainingStep.knnMapper) {
            generateTrain(trainingStep.knnMapper,"('clf_knn', KNeighborsClassifier())","KNN CLASSIFIER")
        }
        if (trainingStep.gaussianMapper) {
            generateTrain(trainingStep.gaussianMapper,"('clf_nb', GaussianClassifier())","GAUSSIAN CLASSIFIER")
        }
        if (trainingStep.randomForestMapper) {
            generateTrain(trainingStep.randomForestMapper,"('clf_r', RandomForestClassifier())","RANDOMFOREST CLASSIFIER")
        }

    }



    def generateTrain(Mapper mapper, String tupleName, String commentType) {
        stringBuilder.append("##$commentType")
                .append(BlockGenerator.NEWLINE)
        for (def entries in mapper.map) {
            def generation = new ClassifierGenerator(entries.key as String, entries.value as Classifier, tupleName).generate()
            stringBuilder.append(generation)
                    .append(BlockGenerator.NEWLINE)
        }


    }




    def generateRandomForestTrain() {

    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}


