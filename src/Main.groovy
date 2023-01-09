import dsl.GroovyBinder
import dsl.PythonModel
import dsl.SyntaxScript
import org.codehaus.groovy.control.CompilerConfiguration


GroovyBinder binder = new GroovyBinder();

binder.pythonModel = new PythonModel()
configuration = new CompilerConfiguration()
configuration.setScriptBaseClass("dsl.SyntaxScript")
GroovyShell shell = new GroovyShell(configuration)


import groovy.io.FileType

def list = []

def dir = new File("../scripts/")
dir.eachFileRecurse(FileType.FILES) { file -> list << file }
list.each {
    SyntaxScript script = shell.parse(new File(it.path)) as SyntaxScript
    binder.setScript(script)
    binder.setScript(script)
    script.setBinding(binder)
    script.run()
}

