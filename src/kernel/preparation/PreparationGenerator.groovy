package kernel.preparation


import dsl.steps.preparation.PreparationStep
import kernel.Generator
import kernel.StringUtils

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
        return "###### ---- PREPARATION PHASE ---- ######" + StringUtils.lineFeed(2) +
                importerGenerator.generate([:]) + StringUtils.lineFeed(2) + preprocessingGenerator.generate([:]) + trainTestSplitterGenerator.generate([:])
    }
}
