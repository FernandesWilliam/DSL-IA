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
        String msg = ""
        if(filePath != null) {
            msg += "g.edge('$filePath','data')\n"
            msg += preprocessingGraphGenerator.generate()
            msg += "g.edge('PreProssDATA','split')\n"
            msg += "g.edge('split','PreProssTrain')\n"
            msg += "g.edge('split','PreProssTest')\n"
        }else{
            msg += "g.edge('$trainPath','train')\n"
            msg += "g.edge('$testPath','test')\n"
            msg += preprocessingGraphGenerator.generate()
        }
        return msg;
    }
}
