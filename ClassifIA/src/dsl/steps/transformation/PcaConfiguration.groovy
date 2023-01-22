package dsl.steps.transformation

import dsl.steps.DSLThrower

class PcaConfiguration implements DSLThrower {

    float n_components = 0.95;

    void n_components(float value) {
        if (value < 0 || value > 1) {
            this.reject("n_components needs to be between 0 and 1")
        }
        this.n_components = value
    }


}
