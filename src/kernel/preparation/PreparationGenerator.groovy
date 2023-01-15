package kernel.preparation


import dsl.steps.preparation.PreparationStep
import kernel.Generator
import kernel.StringUtils

class PreparationGenerator implements Generator {


    PreprocessingGenerator preprocessingGenerator;
    ImporterGenerator importerGenerator;
    TrainTestSplitterGenerator trainTestSplitterGenerator

    PreparationGenerator(PreparationStep preparationStep) {
        importerGenerator = new ImporterGenerator(preparationStep.filePath)
        trainTestSplitterGenerator = new TrainTestSplitterGenerator(preparationStep.training_size, preparationStep.seed)
        preprocessingGenerator = new PreprocessingGenerator(preparationStep.preprocessing)
    }

    @Override
    def generate(maps) {
        return importerGenerator.generate([:]) + StringUtils.lineFeed() + preprocessingGenerator.generate([:]) + trainTestSplitterGenerator.generate([:])
    }
}
