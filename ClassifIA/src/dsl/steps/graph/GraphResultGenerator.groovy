package dsl.steps.graph


import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep

class GraphResultGenerator{
    PreparationStep preparationStep
    TrainingStep trainingStep;

    DataGraphGenerator dataGenerator;
    TransformationGraphGenerator transformationGraphGenerator;

    TrainingGraphGenerator trainingGraphGenerator;

    ComparisonGraphGenerator comparisonGraphGenerator;

    GraphResultGenerator(preparationStep, trainingStep, transformationStep, comparisonStep){
        this.preparationStep = preparationStep;
        this.trainingStep = trainingStep
        dataGenerator = new DataGraphGenerator(preparationStep)
        transformationGraphGenerator = new TransformationGraphGenerator(transformationStep)
        trainingGraphGenerator = new TrainingGraphGenerator(trainingStep, transformationStep.pipelines);
        comparisonGraphGenerator = new ComparisonGraphGenerator(comparisonStep);
    }

    String generate(){
        String graph ="from graphviz import Digraph\n" +
                "\n" +
                "g = Digraph('G')\n"+
                "g.attr(rankdir='LR')\n"+
                "g.node_attr.update(style='filled', fillcolor='white', shape='box')\n"+
                "colors = ['green','red','blue','orange']\n"
        graph += dataGenerator.generate();
        graph += transformationGraphGenerator.generate()
        graph += trainingGraphGenerator.generate();
        graph += comparisonGraphGenerator.generate();
        graph += "g";
        return graph;
    }
}