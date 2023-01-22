package dsl.steps.training.classifier.randomForest


import dsl.steps.transformation.Mapper

class RandomForestMapper extends Mapper {




    @Override
    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new RandomForestClassifier())
    }
}
