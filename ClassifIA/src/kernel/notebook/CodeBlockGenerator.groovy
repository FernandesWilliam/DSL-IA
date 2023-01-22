package kernel.notebook;

import kernel.Generator
import kernel.stringutils.StringUtils;

class CodeBlockGenerator implements Generator {
    private final int nb;
    private final String source;

    CodeBlockGenerator(int nb, String source) {
        this.nb = nb;
        this.source = source;
    }


    @Override
    Object generate(Object maps) {
        return "{\"cell_type\": \"code\"," +
                "\"execution_count\": "+nb+"," +
                "\"id\": \""+StringUtils.generateId()+"\"," +
                "\"metadata\": {}," +
                "\"outputs\": []," +
                "\"source\": [\""+source+"\"]},";
    }
}
