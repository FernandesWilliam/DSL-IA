package dsl.steps.training.classifier


import dsl.steps.training.functions.Function

abstract class Classifier {

    def transformation;
    DistributionParameters distributionParameters;
    Function kfold;

    String[] scoring;
    int cv;

    void kfold(kFoldFunc) {
        this.kfold = kFoldFunc
    }


    void scoring(...scoring) {
        this.scoring = scoring;
    }

    void cv(cv) {
        this.cv = cv;
    }

    def propertyMissing(String propertyName) {
        propertyName
    }

    abstract void distributionParams(hyperParamClosure);

    @Override
    Object invokeMethod(String name, Object args) {
        new Function(name, args);
    }


    void transformation(transformationName) {
        transformation = transformationName
    }

    String getPipelineName(){
        return ""
    }
}
