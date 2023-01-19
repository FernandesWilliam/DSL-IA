package kernel

import dsl.steps.comparison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.comparaison.ComparisonGenerator
import kernel.preparation.PreparationGenerator
import kernel.stringutils.StringUtilsPython
import kernel.training.TrainingGenerator
import kernel.transformation.TransformationGenerator

class PythonGenerator implements Generator {
    PreparationGenerator preparation;
    TransformationGenerator transformation;
    TrainingGenerator training;
    ComparisonGenerator comparison;

    PythonGenerator(PreparationStep preparationStep,
                    TransformationStep transformationStep,
                    TrainingStep trainingStep,
                    ComparisonStep comparisonStep) {
        preparation = new PreparationGenerator(preparationStep, new StringUtilsPython())
        transformation = new TransformationGenerator(transformationStep);
        training = new TrainingGenerator(trainingStep)
        comparison = new ComparisonGenerator(comparisonStep,trainingStep,transformationStep);
    }


    @Override
    def generate(Object maps) {
        return preparation.generate([:]) + StringUtils.lineFeed() + transformation.generate([:]) + training.generate([:]) + comparison.generate([:]);
    }
}