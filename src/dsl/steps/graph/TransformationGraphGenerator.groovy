package dsl.steps.graph


import dsl.steps.transformation.TransformationStep

class TransformationGraphGenerator {
    TransformationStep transformation;
    def pipelines;
    def rootTransformations;
    def transformationGraph;

    TransformationGraphGenerator(TransformationStep transformation){
        this.transformation  = transformation;
        this.pipelines = transformation.pipelines.collectEntries{entry -> [entry[0], entry[1]]};
        this.rootTransformations = GraphUtils.getRootPipelines(this.pipelines)
        this.transformationGraph = GraphUtils.generateRootsSubTree(this.pipelines)
    }

    String generateRootPipeLines() {
        String subGraph = "with g.subgraph(name='cluster_1') as t:\n"
        subGraph += "\tt.attr(style='filled', fillcolor='palegreen3')\n"
        subGraph += "\tt.attr(rank='same')\n"
        this.rootTransformations.forEach{
            pipeName ->
                {
                    subGraph += "\tt.node('${pipeName}', style='filled', fillcolor='thistle')\n"
                    subGraph += "\tt.edge('PreProssTrain','${pipeName}')\n";
                    subGraph += "\tt.edge('PreProssTest','$pipeName')\n";
                    boolean firstTransformation = true;
                    String lastTransformation = ""
                    pipelines.get(pipeName).forEach{transformation -> {
                        if(firstTransformation) {
                            subGraph += "\tt.edge('$pipeName','${pipeName}_$transformation')\n";
                            firstTransformation = false;
                        }
                        else{
                            subGraph += "\tt.edge('${pipeName}_$lastTransformation','${pipeName}_$transformation')\n";
                        }
                        lastTransformation = transformation
                    } }
                    for(def subPipe : transformationGraph[pipeName]){
                        subGraph += "\tt.edge('${pipeName}_$lastTransformation','$subPipe')\n";
                        subGraph += generateSubPipeline(subPipe);
                    }
                }
        }
        subGraph += "\tt.attr(label='Data Transformation')\n"
        return subGraph
    }

    String generateSubPipeline(String pipeName){
        String subGraph = "";
        subGraph += "\tt.node('${pipeName}', style='filled', fillcolor='thistle')\n"
        def pipeline = this.pipelines[pipeName];
        pipeline.removeAt(0)
        boolean firstTransformation = true;
        String lastTransformation = "";
        for(def transformation in pipeline){
            if(firstTransformation){
                subGraph += "\tt.edge('$pipeName','${pipeName}_$transformation')\n";
                firstTransformation = false;
            }
            else{
                subGraph += "\tt.edge('${pipeName}_$lastTransformation','${pipeName}_$transformation')\n";
            }
            lastTransformation = transformation;
        }
        if(this.transformationGraph[pipeName]){
            for(def subPipe : this.transformationGraph[pipeName]){
                subGraph += "\tt.edge('${pipeName}_$lastTransformation','$subPipe')\n";
                subGraph += generateSubPipeline(subPipe)
            }
        }
        return subGraph;
    }

    String generate() {
        String subGraph = generateRootPipeLines();
        return subGraph
    }

}