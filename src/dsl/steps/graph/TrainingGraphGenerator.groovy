package dsl.steps.graph

import dsl.steps.training.TrainingStep
import dsl.steps.transformation.Mapper

public class TrainingGraphGenerator {

    TrainingStep trainingStep;
    def pipelines;
    TrainingGraphGenerator(TrainingStep trainingStep, pipelines) {
        this.trainingStep  = trainingStep;
        this.pipelines = pipelines.collectEntries{entry -> [entry[0], entry[1]]};
    }

    String generate() {
        String subGraph = "with g.subgraph(name='cluster_2') as tr:\n"
        subGraph += "\ttr.attr(style='filled', fillcolor='paleturquoise')\n"
        subGraph += "\ttr.attr(rank='same')\n"
        if(trainingStep.randomForestMapper){
            subGraph += generateTrainSubGraph(trainingStep.randomForestMapper)
        }
        if(trainingStep.gaussianMapper){
            subGraph += generateTrainSubGraph(trainingStep.gaussianMapper)
        }
        if(trainingStep.knnMapper){
            subGraph += generateTrainSubGraph(trainingStep.knnMapper)
        }
        subGraph += "\ttr.attr(label='Training')\n"
        return subGraph;
    }

    String generateTrainSubGraph(Mapper mapper){
        String subGraph = "";
        for(def classifier : mapper.map.entrySet()) {
            subGraph += "\ttr.node('${classifier.key}', style='filled', fillcolor='peachpuff')\n"
            def modelPipelineName = classifier.value.transformation
            def lastTransformation = pipelines[modelPipelineName].getAt(pipelines[modelPipelineName].size()-1)
            subGraph += "\ttr.edge('${modelPipelineName}_${lastTransformation}', '${classifier.key}')\n"
        }
        return subGraph;
    }
}
