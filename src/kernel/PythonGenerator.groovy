package kernel

import dsl.steps.comparaison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.preparation.PreparationGenerator
import kernel.transformation.TransformationGenerator

class PythonGenerator implements Generator {
    PreparationGenerator preparation;
    TransformationGenerator transformation;


    PythonGenerator(PreparationStep preparationStep,
                    TransformationStep transformationStep,
                    TrainingStep trainingStep,
                    ComparisonStep comparisonStep) {
        preparation = new PreparationGenerator(preparationStep)
        transformation = new TransformationGenerator(transformationStep);

    }


    @Override
    def generate(Object maps) {
        return preparation.generate([:]) + StringUtils.lineFeed() + transformation.generate([:])
    }
}
