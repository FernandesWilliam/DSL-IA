package dsl.steps.training.classifier.knn

import dsl.ClosureExtractor
import dsl.steps.transformation.Mapper

class KnnMapper extends Mapper {

    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        KnnClassifier knnCreator = new KnnClassifier()
        ClosureExtractor.extract(args[0], knnCreator)
        map[name] = knnCreator
    }

    @Override
    def mapNewVariable(Object name, Object closure) {
        return null
    }
}
