package dsl.steps.comparison

import dsl.steps.DSLThrower

class ComparisonStep implements DSLThrower{

    def supportedCriteria = ['fit_time', 'test_acc'];
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
        checkAndPushCriteria(less);
        { -> }
    }

    def and(args) {
        checkAndPushCriteria(args);
        { -> }
    }

    private def checkAndPushCriteria(criteria) {
        if(!(criteria in supportedCriteria)){
            reject("criteria ${criteria} not supported.\n > supported criteria ${supportedCriteria}")
        }
        this.criteria.add(criteria)
    }
}

