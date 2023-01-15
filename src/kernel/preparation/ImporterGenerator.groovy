package kernel.preparation

import kernel.Generator
import kernel.StringUtils

class ImporterGenerator implements Generator {

    StringBuilder importBuilder;

    ImporterGenerator(filePath) {
        importBuilder = new StringBuilder().append("#Import DataSet")
                .append(StringUtils.lineFeed())
                .append("import pandas as pd")
                .append(StringUtils.lineFeed())
                .append("try:")
                .append(StringUtils.lineFeed())
                .append(StringUtils.tab())
                .append("dataset = pd.read_csv(\"${filePath}\")")
                .append(StringUtils.lineFeed())
                .append("except FileNotFoundError:")
                .append(StringUtils.lineFeed())
                .append(StringUtils.tab())
                .append("print(\"The path of the dataset is invalid\")")
    }

    @Override
    def generate(Object maps) {
        return importBuilder
    }
}
