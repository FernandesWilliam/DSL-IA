package dsl

import dsl.steps.comparison.ComparisonStep
import dsl.steps.graph.GraphResultGenerator
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.PythonGenerator

class PythonModel {


    PreparationStep preparationStep;
    TransformationStep transformationStep;
    TrainingStep trainingStep;
    ComparisonStep comparisonStep;
    def build(exportFilePath) {
        def file = new File("../result/" + exportFilePath + ".py")
        def graphFile = new File("../result/graph.py")
        def generator = new PythonGenerator(preparationStep,
                transformationStep,
                trainingStep,
                comparisonStep)

        file.text = generator.generate([:])
        GraphResultGenerator graphGenerator =  new GraphResultGenerator(preparationStep, trainingStep, transformationStep)
        graphFile.text = graphGenerator.generate();

    }


}
