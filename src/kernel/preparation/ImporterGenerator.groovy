package kernel.preparation

import kernel.Generator
import kernel.notebook.CodeBlockGenerator

class ImporterGenerator implements Generator {

    StringBuilder importBuilder = new StringBuilder("## DATASET IMPORT")

    ImporterGenerator(filePath) {
            importBuilder
                    .append(CodeBlockGenerator.NEWLINE)
                    .append("import pandas as pd")
                    .append(CodeBlockGenerator.NEWLINE)
                    .append("try:")
                    .append(CodeBlockGenerator.NEWLINE)
                    .append("\\t")
                    .append("dataset = pd.read_csv(\'${filePath}\')")
                    .append(CodeBlockGenerator.NEWLINE)
                    .append("except FileNotFoundError:")
                    .append(CodeBlockGenerator.NEWLINE)
                    .append("\\t")
                    .append("print('The path of the dataset is invalid')")
    }

    ImporterGenerator(trainPath, testPath) {
        importBuilder
                .append(CodeBlockGenerator.NEWLINE)
                .append("import pandas as pd")
                .append(CodeBlockGenerator.NEWLINE)
                .append("try:")
                .append(CodeBlockGenerator.NEWLINE)
                .append("\\t")
                .append("dataTrainSet = pd.read_csv(\'${trainPath}\')")
                .append(CodeBlockGenerator.NEWLINE)
                .append("\\t")
                .append("dataTestSet = pd.read_csv(\'${testPath}\')")
                .append(CodeBlockGenerator.NEWLINE)
                .append("except FileNotFoundError:")
                .append(CodeBlockGenerator.NEWLINE)
                .append("\\t")
                .append("print(\'The path of the dataset is invalid\')")
    }


    @Override
    def generate(Object maps) {
        return  importBuilder
    }
}
