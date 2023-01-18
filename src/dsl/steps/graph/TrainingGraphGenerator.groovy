package dsl.steps.graph

import dsl.steps.training.TrainingStep
import dsl.steps.transformation.Mapper

public class TrainingGraphGenerator {

    TrainingStep trainingStep;
    def pipelines;
    int trainingModelsId = 0;
    TrainingGraphGenerator(TrainingStep trainingStep, pipelines) {
        this.trainingStep  = trainingStep;
        this.pipelines = pipelines.collectEntries{entry -> [entry[0], entry[1]]};
    }

    String generate() {
        String graph = "";
        if(trainingStep.randomForestMapper){
            graph += generateTrainSubGraph(trainingStep.randomForestMapper)
        }
        if(trainingStep.gaussianMapper){
            graph += generateTrainSubGraph(trainingStep.gaussianMapper)
        }
        if(trainingStep.knnMapper){
            graph += generateTrainSubGraph(trainingStep.knnMapper)
        }
        return graph;
    }

    String generateTrainSubGraph(Mapper mapper){

        String graph = "";
        var methods = mapper.map.values();
        for(def classifier : mapper.map.entrySet()) {
            graph += "g.node('${classifier.key}', style='filled', fillcolor='cyan')\n"
            def modelPipelineName = classifier.value.transformation
            def lastTransformation = pipelines[modelPipelineName].getAt(pipelines[modelPipelineName].size()-1)
            graph += "g.edge('${modelPipelineName}_${lastTransformation}', '${classifier.key}')\n"
        }
        return graph;
    }
}
