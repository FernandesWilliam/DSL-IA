package kernel.preparation

import kernel.Generator
import kernel.StringUtils
import kernel.notebook.BlockGenerator

class ImporterGenerator implements Generator {

    StringBuilder importBuilder = new StringBuilder("## DATASET IMPORT")

    ImporterGenerator(filePath) {
            importBuilder
                    .append(BlockGenerator.NEWLINE)
                    .append("import pandas as pd")
                    .append(BlockGenerator.NEWLINE)
                    .append("try:")
                    .append(BlockGenerator.NEWLINE)
                    .append("\\t")
                    .append("dataset = pd.read_csv(\'${filePath}\')")
                    .append(BlockGenerator.NEWLINE)
                    .append("except FileNotFoundError:")
                    .append(BlockGenerator.NEWLINE)
                    .append("\\t")
                    .append("print('The path of the dataset is invalid')")
    }

    ImporterGenerator(trainPath, testPath) {
        importBuilder
                .append(BlockGenerator.NEWLINE)
                .append("import pandas as pd")
                .append(BlockGenerator.NEWLINE)
                .append("try:")
                .append(BlockGenerator.NEWLINE)
                .append("\\t")
                .append("dataTrainSet = pd.read_csv(\'${trainPath}\')")
                .append(BlockGenerator.NEWLINE)
                .append("\\t")
                .append("dataTestSet = pd.read_csv(\'${testPath}\')")
                .append(BlockGenerator.NEWLINE)
                .append("except FileNotFoundError:")
                .append(BlockGenerator.NEWLINE)
                .append("\\t")
                .append("print(\'The path of the dataset is invalid\')")
    }


    @Override
    def generate(Object maps) {
        return  importBuilder
    }
}
