package dsl

import dsl.steps.comparaison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.PythonGenerator
import kernel.preparation.PreparationGenerator

class PythonModel {


    PreparationStep preparationStep;
    TransformationStep transformationStep;
    TrainingStep trainingStep;
    ComparisonStep comparisonStep;
    def build(exportFilePath) {
        def file = new File("../result/" + exportFilePath + ".py")

        def generator = new PythonGenerator(preparationStep,
                transformationStep,
                trainingStep,
                comparisonStep)

        file.text = generator.generate([:])


    }


}
