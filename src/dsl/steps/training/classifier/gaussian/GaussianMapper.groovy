package dsl.steps.training.classifier.gaussian

import dsl.ClosureExtractor
import dsl.steps.transformation.Mapper

class GaussianMapper extends Mapper {


    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        GaussianClassifier gaussianClassifier = new GaussianClassifier()
        ClosureExtractor.extract(args[0], gaussianClassifier)
        map[name] = gaussianClassifier
    }
}
