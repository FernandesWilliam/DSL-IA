package kernel.stringutils

import dsl.steps.graph.GraphResultGenerator
import kernel.Generator
import kernel.notebook.CodeBlockGenerator
import kernel.notebook.MarkDownBlockGenerator


class StringUtils{
    public static boolean notebook
    public static int nb = 0

    static def tab(tabNumber = 1) {
        if (notebook) return "\\t".repeat(tabNumber)
        return "\t".repeat(tabNumber)
    }

    static def lineFeed(number = 1) {
        if (notebook) return "\\n\",\"".repeat(number)
        return "\n".repeat(number)
    }

    static def comment(String text){
        if (notebook) return (new MarkDownBlockGenerator(text)).generate([:])
        return "## ${text}"
    }

    static def startScript(GraphResultGenerator graph){
        if (notebook) return "{\n\"cells\": [" +
                                generateCodeBlock(graph.generate())
        return ""
    }

    static def endScript(String script){
        if (notebook) return script.substring(0, script.length()-1)+"\n],\n" +
                " \"metadata\": {\n" +
                "  \"kernelspec\": {\n" +
                "   \"display_name\": \"Python 3 (ipykernel)\",\n" +
                "   \"language\": \"python\",\n" +
                "   \"name\": \"python3\"\n" +
                "  },\n" +
                "  \"language_info\": {\n" +
                "   \"codemirror_mode\": {\n" +
                "    \"name\": \"ipython\",\n" +
                "    \"version\": 3\n" +
                "   },\n" +
                "   \"file_extension\": \".py\",\n" +
                "   \"mimetype\": \"text/x-python\",\n" +
                "   \"name\": \"python\",\n" +
                "   \"nbconvert_exporter\": \"python\",\n" +
                "   \"pygments_lexer\": \"ipython3\",\n" +
                "   \"version\": \"3.11.1\"\n" +
                "  }\n" +
                " },\n" +
                " \"nbformat\": 4,\n" +
                " \"nbformat_minor\": 5\n" +
                "}"
        return script
    }

    static String generateCodeBlock(Generator generator){
        if (notebook){
            nb ++;
            return (new CodeBlockGenerator(nb, generator.generate([:]) as String).generate([:]));
        }
        return "\n"+generator.generate([:])
    }

    static String generateCodeBlock(String string){
        if (notebook){
            nb ++;
            return (new CodeBlockGenerator(nb, string).generate([:]));
        }
    }

    static String startCodeBlock(){
        if (notebook){
            nb ++;
            return "{\"cell_type\": \"code\"," +
                    "\"execution_count\": "+nb+"," +
                    "\"outputs\": []," +
                    "\"id\": \""+generateId()+"\"," +
                    "\"metadata\": {}," +
                    "\"source\": [\""
        }
        return "\n"
    }

    static String endBlock(){
        if (notebook){
            return "]},"
        }
        return "\n"
    }

    static String generateId(){

        return UUID.randomUUID().toString().substring(0,8);
    }


}
