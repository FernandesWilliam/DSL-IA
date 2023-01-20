package dsl.steps.training.classifier.gaussian

import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class GaussianDistributionParams extends DistributionParameters {
    Function varSmoothing;

    void smoothing(funcObject) {
        this.varSmoothing = funcObject;
    }

}
