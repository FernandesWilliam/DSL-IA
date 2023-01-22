package dsl.steps.transformation


import dsl.steps.DSLThrower
import dsl.steps.Step

class TransformationStep extends Step implements DSLThrower {

    PcaMapper pcaMapper = new PcaMapper();
    MinMaxMapper minMaxMapper = new MinMaxMapper();
    StandardScalerMapper standardScalerMapper = new StandardScalerMapper();

    def pipelines = []

    float normalize = 0;

    def pca(pcaClosure) {
        pcaMapper.mapNewVariable(currentVariable, pcaClosure);
    }


    def minmax(minMaxClosure) {
        minMaxMapper.mapNewVariable(currentVariable, minMaxClosure);
    }

    void standardScaler(standardScalerClosure) {
        standardScalerMapper.mapNewVariable(currentVariable, standardScalerClosure);
    }


    def isAlreadyDeclared(values) {
        for (def value : values) {
            if (pcaMapper.map.containsKey(value) || minMaxMapper.map.containsKey(value) ||
                    standardScalerMapper.map.containsKey(value)) continue
            return value
        }
        return true;

    }

    def pipe(entrySet) {
        def findInPipelines = { name -> pipelines.find { array -> array[0] == name } != null }

        for (def entry in entrySet.entrySet()) {
            if (findInPipelines(entry.key)) {
                reject("${entry.key} is already declared.")
            }


            def clone = entry.value.clone()
            def response = isAlreadyDeclared(clone)

            while (response != true) {
                def isInside = findInPipelines(response)
                if (isInside && clone[0]===  response) {
                    clone.removeIf { v -> response == v };

                }else if(isInside) {
                    reject("${response} transformation needs to be specified in the first.")
                }
                else {
                    reject("${response} isn't declared. You should declare the variable before.")
                }
                response = isAlreadyDeclared(clone)
            }

            // detect same transformation
            for (def pipeEntry : pipelines) {
                def same = 0;

                if (pipeEntry[1].size() != entry.value.size()) continue
                for (def i = 0; i < pipeEntry[1].size(); i++) {
                    if (!pipeEntry[1][i].equals(entry.value[i])) break
                    same++;
                }
                if (same == entry.value.size())
                    reject("${entry.key} transformation already exist : ${pipeEntry[0]}")
            }
            pipelines.add([entry.key, entry.value])
        }

    }


    void normalizer(float value) {
        if (value == 0)
            this.reject("You can't normalize your dataset with a 0 value")
        if (normalize != 0)
            this.reject("You already defined normalized Function.Can't be append twice")
        this.normalize = value;
    }
}




