package kernel

import dsl.steps.comparaison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.notebook.BlockGenerator
import kernel.preparation.PreparationGenerator
import kernel.training.TrainingGenerator
import kernel.transformation.TransformationGenerator

class PythonGenerator implements Generator {
    PreparationGenerator preparation;
    TransformationGenerator transformation;
    TrainingGenerator training;
    public static int nb = 0

    PythonGenerator(PreparationStep preparationStep,
                    TransformationStep transformationStep,
                    TrainingStep trainingStep,
                    ComparisonStep comparisonStep) {
        preparation = new PreparationGenerator(preparationStep)
        transformation = new TransformationGenerator(transformationStep);
        training = new TrainingGenerator(trainingStep, transformationStep)

    }

    public static String generateInBlock(Generator generator){
        nb ++;
        return (new BlockGenerator(nb, generator.generate([:]) as String).generate([:]));
    }

    @Override
    def generate(Object maps) {
        return "{\n" +
                " \"cells\": ["+
                preparation.generate([:]) + StringUtils.lineFeed() + generateInBlock(transformation) + generateInBlock(training)+"\n" +
                        " ],";
    }
}
