package dsl.steps.training.classifier.gaussian


import dsl.steps.transformation.Mapper

class GaussianMapper extends Mapper {


    @Override
    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new GaussianClassifier())

    }
}
