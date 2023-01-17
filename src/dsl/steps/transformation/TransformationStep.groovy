package dsl.steps.transformation

import dsl.ClosureExtractor
import dsl.steps.DSLThrower
import dsl.steps.Step

import java.sql.Array

class TransformationStep extends Step implements DSLThrower {

    PcaMapper pcaMapper = new PcaMapper();
    MinMaxMapper minMaxMapper = new MinMaxMapper();
    StandardScalerMapper standardScalerMapper = new StandardScalerMapper();

    def pipelines = [:]

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
                    standardScalerMapper.map.containsKey(values)) continue
            return value
        }
        return true;

    }

    def pipe(entrySet) {
        for (def entry in entrySet.entrySet()) {
            def response = isAlreadyDeclared(entry.value)
            if (response != true) {
                if (response in pipelines) {
                    entry.value.removeIf{v-> response==v};
                    entry.value = entry.value + pipelines[response]
                } else {
                    reject("${response} isn't declared. You should declare the variable before.")
                }
            }
            if (entry.key in pipelines) {
                reject("${entry.key} is already declared.")
            }
            // to know if there already exist a transformation
            for (def pipeEntry in pipelines.entrySet()) {
                def same = 0;
                if (pipeEntry.value.size() != entry.value.size()) break
                for (def i = 0; i < pipeEntry.value.size(); i++) {
                    if (!pipeEntry.value[i].equals(entry.value[i])) break
                    same++;
                }
                if (same == entry.value.size())
                    reject("${entry.key} transformation already exist : ${pipeEntry.key}")
            }
            pipelines[entry.key] = entry.value
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
