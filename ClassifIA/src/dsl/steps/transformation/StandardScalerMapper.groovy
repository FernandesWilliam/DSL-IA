package dsl.steps.transformation

class StandardScalerMapper extends Mapper{

    def mapNewVariable(Object name, Object closure) {
        return super.mapNewVariable(name, closure, new StandardScalerConfiguration())
    }
}
