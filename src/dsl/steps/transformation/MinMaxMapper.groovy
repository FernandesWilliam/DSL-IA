package dsl.steps.transformation

import dsl.ClosureExtractor

class MinMaxMapper extends Mapper {


    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        MinMaxConfiguration minMaxCreator = new MinMaxConfiguration()
        ClosureExtractor.extract(args[0], minMaxCreator)
        map[name] = minMaxCreator
    }

}
