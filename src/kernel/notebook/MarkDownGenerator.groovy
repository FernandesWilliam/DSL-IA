package kernel.notebook;

import kernel.Generator
import kernel.stringutils.StringUtils;

class MarkDownBlockGenerator implements Generator {
    private final String source;

    MarkDownBlockGenerator(String source) {
        this.source = source;
    }

    @Override
    public Object generate(Object maps) {
        return "{\"cell_type\":\"markdown\"," +
                "\"id\": \""+ StringUtils.generateId()+"\"," +
                "\"metadata\": {},\"source\":\""+source+"\"},";
    }
}
