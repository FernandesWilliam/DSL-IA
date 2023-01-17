package dsl

class GroovyBinder extends Binding {


    PythonModel pythonModel
    private SyntaxScript script

    PythonModel getModel() {
        pythonModel
    }

    void setScript(SyntaxScript script) {
        this.script = script
    }

    public Object getVariable(String name) {

        return super.getVariable(name)
    }


}