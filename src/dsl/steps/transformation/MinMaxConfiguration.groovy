package dsl.steps.transformation

class MinMaxConfiguration {

    boolean copy = true;
    def feature_range = new Tuple(0, 1)
    boolean clip = false;

    void feature_range(int min, int max) {
        this.feature_range = new Tuple(min, max)
    }

    void clip(boolean needClip) {
        this.clip = needClip
    }

    void copy(boolean needCopy) {
        this.copy = copy;
    }


}
