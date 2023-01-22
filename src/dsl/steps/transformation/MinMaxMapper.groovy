package dsl.steps.transformation

class MinMaxMapper extends Mapper {


    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new MinMaxConfiguration())
    }

}
