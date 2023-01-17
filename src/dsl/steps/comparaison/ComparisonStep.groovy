package dsl.steps.comparaison

class ComparisonStep {


    //compare rndForest1, gauss1 with accuracy 10 and time less 4
    def toCompare = []
    def criteria = []
    def criteriaWeight = []


    def compare(... args) {
        toCompare = args;
        { -> }
    }

    def propertyMissing(String propertyName) {
        propertyName
    }

    def weight(value) {
        criteriaWeight.add(value);
        { -> }
    }

    def with(less) {
        criteria.add(less);
        { -> }
    }

    def and(args) {
        criteria.add(args);
        { -> }
    }

    def propertyMissing(r){
        println("missing"+r)
    }
}

