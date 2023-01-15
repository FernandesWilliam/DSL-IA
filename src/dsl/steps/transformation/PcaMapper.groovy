package dsl.steps.transformation

import dsl.ClosureExtractor

class PcaMapper extends Mapper {


    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        PcaConfiguration pcaCreator = new PcaConfiguration()
        ClosureExtractor.extract(args[0], pcaCreator)
        map[name] = pcaCreator
    }


}
