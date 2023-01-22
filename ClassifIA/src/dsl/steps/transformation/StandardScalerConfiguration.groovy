package dsl.steps.transformation

import dsl.steps.DSLThrower

class StandardScalerConfiguration implements DSLThrower{
    boolean copy = true;
    boolean with_mean = true;
    boolean with_std = true;

    void copy(boolean value) {
        this.copy = value;
    }

    void with_mean(boolean value) {
        this.with_mean = value;
    }

    void with_std(Boolean value) {
        this.with_std = value;
    }
}
