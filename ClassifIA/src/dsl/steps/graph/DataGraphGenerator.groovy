package dsl.steps.graph

import dsl.steps.preparation.PreparationStep

class DataGraphGenerator {
    String filePath = null;
    String trainPath = null;
    String testPath = null;
    int training_size;
    int seed;

    PreprocessingGraphGenerator preprocessingGraphGenerator;

    DataGraphGenerator(PreparationStep preparationStep){
        this.filePath = preparationStep.filePath;
        this.trainPath = preparationStep.trainPath;
        this.testPath = preparationStep.testPath;
        this.training_size = preparationStep.training_size
        this.seed = preparationStep.seed
        preprocessingGraphGenerator = new PreprocessingGraphGenerator(preparationStep.preprocessing, filePath == null)
    }

    String generate(){
        String subGraph = "with g.subgraph(name='cluster_0') as d:\n"
        subGraph += "\td.attr(style='filled', fillcolor='antiquewhite1')\n"
        subGraph += "\td.attr(rank='same')\n"
        if(filePath != null) {
            subGraph += "\td.edge('$filePath','data')\n"
            subGraph += preprocessingGraphGenerator.generate()
            subGraph += "\td.edge('PreProssDATA','split')\n"
            subGraph += "\td.edge('split','PreProssTrain')\n"
            subGraph += "\td.edge('split','PreProssTest')\n"
        }else{
            subGraph += "\td.edge('$trainPath','train')\n"
            subGraph += "\td.edge('$testPath','test')\n"
            subGraph += preprocessingGraphGenerator.generate()
        }
        subGraph += "\td.attr(label='Data pre-processing')\n"
        return subGraph;
    }
}
