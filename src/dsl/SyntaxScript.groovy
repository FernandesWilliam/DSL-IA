package dsl

import dsl.steps.comparaison.ComparisonStep
import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep
import dsl.steps.transformation.TransformationStep

abstract class SyntaxScript extends Script {

    def preparation(preparationClosure) {
        PreparationStep preparationStep = new PreparationStep()
        ClosureExtractor.extract(preparationClosure, preparationStep)
        this.model.preparationStep = preparationStep;
    }

    def transformation(transformationClosure) {
        TransformationStep transformationStep = new TransformationStep()
        ClosureExtractor.extract(transformationClosure, transformationStep)
        this.model.transformationStep = transformationStep;
    }


    def training(trainingClosure) {
        TrainingStep trainingStep = new TrainingStep()
        ClosureExtractor.extract(trainingClosure, trainingStep)
        this.model.trainingStep = trainingStep;
    }

    def comparison(comparisonClosure) {

        ComparisonStep comparisonStep = new ComparisonStep()
        ClosureExtractor.extract(comparisonClosure, comparisonStep)
        this.model.comparisonStep = comparisonStep;
    }


    def export(str) {
        this.model.build(str);

    }

    def propertyMissing(String propertyName) {
        this.metaClass."${propertyName}" = ((GroovyBinder) this.getBinding()).getModel()
        this."${propertyName}"
    }

}
