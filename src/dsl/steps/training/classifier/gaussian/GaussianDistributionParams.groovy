package dsl.steps.training.classifier.gaussian

import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class GaussianDistributionParams extends DistributionParameters {
    Function smooth;

    void smooth(funcObject) {
        this.smooth = funcObject;
    }

}
