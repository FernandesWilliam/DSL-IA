package kernel.preparation

import kernel.Generator
import kernel.stringutils.StringUtilsJupyter

class ImporterGenerator implements Generator {

    StringBuilder importBuilder = new StringBuilder("## DATASET IMPORT")

    ImporterGenerator(filePath) {
            importBuilder
                    .append(StringUtilsJupyter.lineFeed())
                    .append("import pandas as pd")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("try:")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("\\t")
                    .append("dataset = pd.read_csv(\'${filePath}\')")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("except FileNotFoundError:")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("\\t")
                    .append("print('The path of the dataset is invalid')")
    }

    ImporterGenerator(trainPath, testPath) {
        importBuilder
                .append(StringUtilsJupyter.lineFeed())
                .append("import pandas as pd")
                .append(StringUtilsJupyter.lineFeed())
                .append("try:")
                .append(StringUtilsJupyter.lineFeed())
                .append("\\t")
                .append("dataTrainSet = pd.read_csv(\'${trainPath}\')")
                .append(StringUtilsJupyter.lineFeed())
                .append("\\t")
                .append("dataTestSet = pd.read_csv(\'${testPath}\')")
                .append(StringUtilsJupyter.lineFeed())
                .append("except FileNotFoundError:")
                .append(StringUtilsJupyter.lineFeed())
                .append("\\t")
                .append("print(\'The path of the dataset is invalid\')")
    }


    @Override
    def generate(Object maps) {
        return  importBuilder
    }
}
