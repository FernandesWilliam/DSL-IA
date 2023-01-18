package dsl.steps.comparison

import dsl.steps.training.TrainingStep

class ComparisonStep {


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


}

