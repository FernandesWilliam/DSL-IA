package dsl.steps.transformation

import dsl.ClosureExtractor

abstract class Mapper {

    def map = [:]

    def mapNewVariable(name, closure, configuration) {
        if (map.containsKey(name))
            throw new Exception("${name} already exist")
        ClosureExtractor.extract(closure, configuration)
        map[name] = configuration;
    }

    abstract def mapNewVariable(Object name, Object closure);


}
