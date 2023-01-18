package kernel.notebook;

import kernel.Generator;

public class MarkDownBlockGenerator implements Generator {
    private final String source;

    public MarkDownBlockGenerator(String source) {
        this.source = source;
    }

    @Override
    public Object generate(Object maps) {
        return "{\"cell_type\":\"markdown\",\"source\":\""+source+"\",\"metadata\":{}},";
    }
}
