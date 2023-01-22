package kernel

import dsl.steps.comparison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep
import kernel.comparaison.ComparisonGenerator
import kernel.imports.ImportsGenerator
import kernel.preparation.PreparationGenerator
import kernel.stringutils.StringUtils
import kernel.training.TrainingGenerator
import kernel.transformation.TransformationGenerator

class PythonGenerator implements Generator {
    PreparationStep preparation;
    TransformationStep transformation;
    TrainingStep training;
    ComparisonStep comparison;

    PythonGenerator(PreparationStep preparation, TransformationStep transformation, TrainingStep training, ComparisonStep comparison) {
        this.preparation = preparation
        this.transformation = transformation
        this.training = training
        this.comparison = comparison
    }

    @Override
    def generate(Object maps) {
        String script =  StringUtils.startScript()+
                StringUtils.comment("IMPORTS") +
                StringUtils.generateCodeBlock(new ImportsGenerator()) +
                StringUtils.comment("PREPROCESSING") +
                (new PreparationGenerator(preparation)).generate([:]) +
                StringUtils.comment("TRANSFORMATION") +
                StringUtils.generateCodeBlock(new TransformationGenerator(transformation)) +
                StringUtils.comment("TRAINING") +
                (new TrainingGenerator(training)).generate([:]) +
                StringUtils.comment("COMPARISON") +
                StringUtils.generateCodeBlock(new ComparisonGenerator(comparison,training,transformation));
        return StringUtils.endScript(script);
    }
}