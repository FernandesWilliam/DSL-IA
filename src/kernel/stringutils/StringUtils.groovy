package kernel.stringutils

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

    static def startScript(){
        if (notebook) return "{\n\"metadata\": {\n" +
                "    \"language_info\": {\n" +
                "      \"name\": \"\"\n" +
                "    },\n" +
                "    \"kernelspec\": {\n" +
                "      \"name\": \"python\",\n" +
                "      \"display_name\": \"Python (Pyodide)\",\n" +
                "      \"language\": \"python\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"nbformat_minor\": 4,\n" +
                "  \"nbformat\": 4,\"cells\": ["
        return ""
    }

    static def endScript(String script){
        if (notebook) return script.substring(0, script.length()-1)+"\n]}"
        return script
    }

    static String generateCodeBlock(Generator generator){
        if (notebook){
            nb ++;
            return (new CodeBlockGenerator(nb, generator.generate([:]) as String).generate([:]));
        }
        return "\n"+generator.generate([:])
    }

    static String startCodeBlock(){
        if (notebook){
            nb ++;
            return "{\"cell_type\": \"code\"," +
                    "\"execution_count\": "+nb+"," +
                    "\"output_type\": \"display_data\"," +
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

}
