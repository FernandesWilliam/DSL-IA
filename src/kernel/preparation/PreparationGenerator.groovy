package kernel.preparation


import dsl.steps.preparation.PreparationStep
import kernel.Generator
import kernel.stringutils.StringUtils

class PreparationGenerator implements Generator {


    PreprocessingGenerator preprocessingGenerator;
    ImporterGenerator importerGenerator;
    TrainTestSplitterGenerator trainTestSplitterGenerator

    PreparationGenerator(PreparationStep preparationStep) {

        if (preparationStep.filePath) {
            importerGenerator = new ImporterGenerator(preparationStep.filePath)
            trainTestSplitterGenerator = new TrainTestSplitterGenerator(preparationStep.training_size, preparationStep.seed)
            preprocessingGenerator = new PreprocessingGenerator(preparationStep.preprocessing, true)
        } else {
            importerGenerator = new ImporterGenerator(preparationStep.trainPath, preparationStep.testPath)
            trainTestSplitterGenerator = new TrainTestSplitterGenerator()
            preprocessingGenerator = new PreprocessingGenerator(preparationStep.preprocessing, false)
        }

    }

    @Override
    def generate(maps) {
        return StringUtils.comment("DATASET IMPORT")+
                StringUtils.generateCodeBlock(importerGenerator) +
                StringUtils.comment("PREPROCESSING")+
                StringUtils.generateCodeBlock(preprocessingGenerator) +
                StringUtils.comment("SPLITTING")+
                StringUtils.generateCodeBlock(trainTestSplitterGenerator);
    }
}
