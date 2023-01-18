package dsl.steps.graph

import dsl.steps.preparation.PreparationStep
import dsl.steps.training.TrainingStep

class GraphResultGenerator{
    PreparationStep preparationStep
    TrainingStep trainingStep;

    DataGraphGenerator dataGenerator;
    TransformationGraphGenerator transformationGraphGenerator;

    TrainingGraphGenerator trainingGraphGenerator;

    GraphResultGenerator(preparationStep, trainingStep, transformationStep){
        this.preparationStep = preparationStep;
        this.trainingStep = trainingStep
        dataGenerator = new DataGraphGenerator(preparationStep)
        transformationGraphGenerator = new TransformationGraphGenerator(transformationStep)
        trainingGraphGenerator = new TrainingGraphGenerator(trainingStep, transformationStep.pipelines);
    }

    String generate(){
        String graph ="from graphviz import Digraph\n" +
                "\n" +
                "g = Digraph('G', filename='cluster.gv')\n"+
                "colors = ['green','red','blue','orange']\n";
        graph += dataGenerator.generate();
        graph += transformationGraphGenerator.generate()
        graph += trainingGraphGenerator.generate();
        graph += "g.view()";
        return graph;
    }
}