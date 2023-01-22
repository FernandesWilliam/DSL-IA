package dsl.steps.training.classifier.knn


import dsl.steps.transformation.Mapper

class KnnMapper extends Mapper {



    @Override
    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new KnnClassifier())
    }
}
