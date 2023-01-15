package dsl.steps.training.classifier.gaussian

import dsl.steps.training.classifier.DistributionParameters
import dsl.steps.training.functions.Function

class GaussianDistributionParams extends DistributionParameters {
    Function clf_nb__var_smoothing;

    void clf_nb__var_smoothing(funcObject) {
        this.clf_nb__var_smoothing = funcObject;
    }

}
