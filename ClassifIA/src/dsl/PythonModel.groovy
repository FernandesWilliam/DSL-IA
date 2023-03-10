package dsl

import dsl.steps.comparison.ComparisonStep
import dsl.steps.graph.GraphResultGenerator
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.PythonGenerator
import kernel.stringutils.StringUtils
import groovy.json.*

class PythonModel {


    PreparationStep preparationStep;
    TransformationStep transformationStep;
    TrainingStep trainingStep;
    ComparisonStep comparisonStep;
    def build(exportFilePath, mode) {
        StringUtils.notebook = mode.equalsIgnoreCase("notebook")

        def file = new File("../result/" + exportFilePath + (StringUtils.notebook ? ".ipynb":".py"))
        def graphFile = new File("../result/graph_${exportFilePath}.py")
        def generator = new PythonGenerator(preparationStep,
                transformationStep,
                trainingStep,
                comparisonStep)
        file.text =  StringUtils.notebook ? JsonOutput.prettyPrint(generator.generate([:])) : generator.generate([:])

        GraphResultGenerator graphGenerator =  new GraphResultGenerator(preparationStep, trainingStep, transformationStep, comparisonStep)
        graphFile.text = graphGenerator.generate();
        graphFile.text += "\n\ng.view()\n"

    }


}
