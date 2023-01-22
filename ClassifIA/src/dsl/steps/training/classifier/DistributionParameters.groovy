package dsl.steps.training.classifier

import dsl.steps.training.functions.Function

class DistributionParameters {


    @Override
    Object invokeMethod(String name, Object args) {
        new Function(name, args)
    }
}
