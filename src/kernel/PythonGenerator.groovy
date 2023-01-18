package kernel

import dsl.steps.comparison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.notebook.CodeBlockGenerator
import kernel.notebook.MarkDownBlockGenerator
import kernel.comparaison.ComparisonGenerator
import kernel.preparation.PreparationGenerator
import kernel.training.TrainingGenerator
import kernel.transformation.TransformationGenerator

class PythonGenerator implements Generator {
    PreparationGenerator preparation;
    TransformationGenerator transformation;
    TrainingGenerator training;
    public static int nb = 0
    ComparisonGenerator comparison;

    PythonGenerator(PreparationStep preparationStep,
                    TransformationStep transformationStep,
                    TrainingStep trainingStep,
                    ComparisonStep comparisonStep) {
        preparation = new PreparationGenerator(preparationStep)
        transformation = new TransformationGenerator(transformationStep);
        training = new TrainingGenerator(trainingStep)
        comparison = new ComparisonGenerator(comparisonStep,trainingStep,transformationStep);
    }

    static String generateCodeBlock(Generator generator){
        nb ++;
        return (new CodeBlockGenerator(nb, generator.generate([:]) as String).generate([:]));
    }

    static String generateMarkDownBlock(String text){
        return (new MarkDownBlockGenerator(text)).generate([:]);
    }

    @Override
    def generate(Object maps) {
        return "{\n" +
                " \"cells\": ["+
                preparation.generate([:]) +
                generateMarkDownBlock( "TRANSFORMATION")+
                generateCodeBlock(transformation) +
                generateMarkDownBlock( "TRAINING")+
                training.generate()+
                generateMarkDownBlock( "COMPARAISON")+
                generateCodeBlock(comparison)
                "\n" +
                        " ]}";

    }
}
