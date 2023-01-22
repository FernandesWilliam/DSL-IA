package dsl.steps

class Step {

    protected def currentVariable;

    protected def propertyMissing(name) {
        name
    }

    @Override
    Object invokeMethod(String name, Object args) {
    }

    def declare(variableName) {
        currentVariable = variableName;
        this
    }

}
