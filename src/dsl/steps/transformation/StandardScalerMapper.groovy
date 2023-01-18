package dsl.steps.transformation

import dsl.ClosureExtractor

class StandardScalerMapper extends Mapper{

    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new StandardScalerConfiguration())
    }
}
