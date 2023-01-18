package dsl.steps.transformation

import dsl.ClosureExtractor

class MinMaxMapper extends Mapper {


    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new MinMaxConfiguration())
    }

}
