package dsl.steps.transformation

import dsl.ClosureExtractor

class StandardScalerMapper extends Mapper{
    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        StandardScalerConfiguration scalerCreator = new StandardScalerConfiguration()
        ClosureExtractor.extract(args[0], scalerCreator)
        map[name] = scalerCreator
    }
}
