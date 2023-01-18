package kernel.training

import dsl.steps.training.TrainingStep
import dsl.steps.training.classifier.Classifier
import dsl.steps.transformation.Mapper
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.PythonGenerator
import kernel.notebook.CodeBlockGenerator

class TrainingGenerator implements Generator {

    StringBuilder stringBuilder;

    TrainingGenerator(TrainingStep trainingStep, TransformationStep transformationStep) {
        stringBuilder = new StringBuilder().append()
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
        stringBuilder.append(PythonGenerator.generateMarkDownBlock("$commentType"));
        for (def entries in mapper.map) {
            def generation =  PythonGenerator.generateCodeBlock(new ClassifierGenerator(entries.key as String, entries.value as Classifier, tupleName))
            stringBuilder.append(generation)
        }


    }



    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}


