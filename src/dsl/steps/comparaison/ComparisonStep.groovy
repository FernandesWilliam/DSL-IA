package dsl.steps.comparaison

class ComparisonStep {
    //compare rndForest1, gauss1 with accuracy 10 and time less 4
    String[] toCompare;
    String[] criteria;

    def compare(... args) {
        toCompare = args;
        { -> }
    }

    def propertyMissing(String propertyName) {
        propertyName
    }

    def weight(value) {
        println value;
        { -> }

    }

    def with(...less) {
        criteria = less;
        { -> }
    }

    def and(args) {
        println args;
        { -> }
    }
}
