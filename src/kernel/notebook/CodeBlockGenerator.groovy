package kernel.notebook;

import kernel.Generator;

public class CodeBlockGenerator implements Generator {
    private final int nb;
    private final String source;

    public CodeBlockGenerator(int nb, String source) {
        this.nb = nb;
        this.source = source;
    }

    @Override
    public Object generate(Object maps) {
        return "{\"cell_type\": \"code\"," +
                "\"execution_count\": "+nb+"," +
                "\"metadata\": {}," +
                "\"output_type\": \"display_data\"," +
                "\"source\": [\""+source+"\"]},";
    }
}
