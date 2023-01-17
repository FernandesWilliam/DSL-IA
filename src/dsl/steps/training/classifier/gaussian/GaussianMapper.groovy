package dsl.steps.training.classifier.gaussian

import dsl.ClosureExtractor
import dsl.steps.transformation.Mapper
import dsl.steps.transformation.PcaConfiguration

class GaussianMapper extends Mapper {


    @Override
    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new GaussianClassifier())

    }
}
