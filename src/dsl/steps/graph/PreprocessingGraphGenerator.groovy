package dsl.steps.graph

import dsl.steps.preparation.Preprocessing

class PreprocessingGraphGenerator {
    Preprocessing preprocessing;
    boolean splitData;

    PreprocessingGraphGenerator(Preprocessing preprocessing, boolean splitData){
        this.preprocessing  = preprocessing;
        this.splitData = splitData;
    }

    String generate() {
        String msg = "";
        msg += ""
        boolean firstPreProcess = true;
        String lastPreProcess = "";
        preprocessing.methods.entrySet().forEach {it -> {
            if(firstPreProcess){
                firstPreProcess = false;
                if(splitData){
                    msg += "g.edge('train','${it.key}')\n";
                    msg += "g.edge('test','${it.key}')\n";
                }
                else{
                    msg += "g.edge('data','${it.key}')\n";
                }
            }
            else {
                msg += "g.edge('${lastPreProcess}','${it.key}')\n";
            }
            lastPreProcess = it.key;
        }}

        if(splitData){
            msg += "g.edge('${lastPreProcess}','PreProssTrain')\n";
            msg += "g.edge('${lastPreProcess}','PreProssTest')\n";
        }
        else{
            msg += "g.edge('${lastPreProcess}','PreProssDATA')\n";

        }
        return msg;
    }

}
