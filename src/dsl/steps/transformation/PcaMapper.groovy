package dsl.steps.transformation

import dsl.ClosureExtractor

class PcaMapper extends Mapper {

    PcaMapper() {

    }

    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new PcaConfiguration())
    }
}
