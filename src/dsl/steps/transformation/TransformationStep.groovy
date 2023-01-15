package dsl.steps.transformation

import dsl.ClosureExtractor
import dsl.steps.DSLThrower

class TransformationStep implements DSLThrower {

    PcaMapper pcaMapper;

    MinMaxMapper minMaxMapper;

    StandardScalerMapper standardScalerMapper;

    float normalize = 0;

    void pca(pcaClosure) {
        if (pcaMapper != null)
            this.reject("You already defined pca property.Can't be append twice")
        pcaMapper = new PcaMapper()
        ClosureExtractor.extract(pcaClosure, pcaMapper)
    }


    void normalizer(float value) {
        if (value == 0)
            this.reject("You can't normalize your dataset with a 0 value")
        if (normalize != 0)
            this.reject("You already defined normalized Function.Can't be append twice")
        this.normalize = value;
    }

    void minmax(minMaxClosure) {
        if (minMaxMapper != null)
            this.reject("You already defined minmax property.Can't be append twice")
        minMaxMapper = new MinMaxMapper();
        ClosureExtractor.extract(minMaxClosure, minMaxMapper)
    }

    void standardScaler(standardScalerClosure){
        if (standardScalerMapper != null)
            this.reject("You already defined standardScaler property.Can't be append twice");
        standardScalerMapper = new StandardScalerMapper();
        ClosureExtractor.extract(standardScalerClosure, standardScalerMapper);
    }


}
