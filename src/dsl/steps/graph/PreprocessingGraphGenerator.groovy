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
                    msg += "\td.edge('train','${it.key}')\n";
                    msg += "\td.edge('test','${it.key}')\n";
                }
                else{
                    msg += "\td.edge('data','${it.key}')\n";
                }
            }
            else {
                msg += "\td.edge('${lastPreProcess}','${it.key}')\n";
            }
            lastPreProcess = it.key;
        }}

        if(splitData){
            msg += "\td.edge('${lastPreProcess}','PreProssTrain')\n";
            msg += "\td.edge('${lastPreProcess}','PreProssTest')\n";
        }
        else{
            msg += "\td.edge('${lastPreProcess}','PreProssDATA')\n";

        }
        return msg;
    }

}