import dsl.GroovyBinder
import dsl.PythonModel
import dsl.SyntaxScript
import org.codehaus.groovy.control.CompilerConfiguration
import groovy.io.FileType

GroovyBinder binder = new GroovyBinder();

binder.pythonModel = new PythonModel()
configuration = new CompilerConfiguration()
configuration.setScriptBaseClass("dsl.SyntaxScript")
GroovyShell shell = new GroovyShell(configuration)




def list = []
// read every files contained inside scritps folder, iterates on and produce the corresponding file in python
def dir = new File("../scripts/")
dir.eachFileRecurse(FileType.FILES) { file -> list << file }
list.each {
    SyntaxScript script = shell.parse(new File(it.path)) as SyntaxScript
    binder.setScript(script)
    script.setBinding(binder)
    script.run()
}
