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
        String msg = "";
        this.rootTransformations.forEach{
            pipeName ->
                {
                    msg += "g.node('${pipeName}', style='filled', fillcolor='green')\n"
                    msg += "g.edge('PreProssTrain','${pipeName}')\n";
                    msg += "g.edge('PreProssTest','$pipeName')\n";
                    boolean firstTransformation = true;
                    String lastTransformation = ""
                    pipelines.get(pipeName).forEach{transformation -> {
                        if(firstTransformation) {
                            msg += "g.edge('$pipeName','${pipeName}_$transformation')\n";
                            firstTransformation = false;
                        }
                        else{
                            msg += "g.edge('${pipeName}_$lastTransformation','${pipeName}_$transformation')\n";
                        }
                        lastTransformation = transformation
                    } }
                    for(def subPipe : transformationGraph[pipeName]){
                        msg += "g.edge('${pipeName}_$lastTransformation','$subPipe')\n";
                        msg += generateSubPipeline(subPipe);
                    }
                }
        }
        return msg
    }

    String generateSubPipeline(String pipeName){
        String msg = "";
        msg += "g.node('${pipeName}', style='filled', fillcolor='green')\n"
        def pipeline = this.pipelines[pipeName];
        pipeline.removeAt(0)
        boolean firstTransformation = true;
        String lastTransformation = "";
        for(def transformation in pipeline){
            if(firstTransformation){
                msg += "g.edge('$pipeName','${pipeName}_$transformation')\n";
                firstTransformation = false;
            }
            else{
                msg += "g.edge('${pipeName}_$lastTransformation','${pipeName}_$transformation')\n";
            }
            lastTransformation = transformation;
        }
        if(this.transformationGraph[pipeName]){
            for(def subPipe : this.transformationGraph[pipeName]){
                msg += "g.edge('${pipeName}_$lastTransformation','$subPipe')\n";
                msg += generateSubPipeline(subPipe)
            }
        }
        return msg;
    }

    String generate() {
        String msg = generateRootPipeLines();
        return msg
//        String msg = "";
//        msg += ""
//        boolean firstPreProcess = true;
//        String lastPreProcess = "";
//        preprocessing.methods.entrySet().forEach {it -> {
//            if(firstPreProcess){
//                firstPreProcess = false;
//                msg += "g.edge('train','${it.key}')\n";
//                msg += "g.edge('test','$it.key')\n";
//            }
//            else {
//                msg += "g.edge('${lastPreProcess}','${it.key}')\n";
//                msg += "g.edge('$lastPreProcess','$it.key')\n";
//            }
//            lastPreProcess = it.key;
//        }}
//
//        msg += "g.edge('${lastPreProcess}','TrainDATA')\n";
//        msg += "g.edge('$lastPreProcess','testDATA')\n";
        return msg;
    }

}