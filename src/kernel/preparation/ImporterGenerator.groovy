package kernel.preparation

import kernel.Generator
import kernel.StringUtils

class ImporterGenerator implements Generator {

    StringBuilder importBuilder = new StringBuilder("## DATASET IMPORT")

    ImporterGenerator(filePath) {
            importBuilder
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

    ImporterGenerator(trainPath, testPath) {
        importBuilder
                .append(StringUtils.lineFeed())
                .append("import pandas as pd")
                .append(StringUtils.lineFeed())
                .append("try:")
                .append(StringUtils.lineFeed())
                .append(StringUtils.tab())
                .append("dataTrainSet = pd.read_csv(\"${trainPath}\")")
                .append(StringUtils.lineFeed())
                .append(StringUtils.tab())
                .append("dataTestSet = pd.read_csv(\"${testPath}\")")
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
