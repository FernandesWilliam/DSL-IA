package dsl.steps.training.classifier.randomForest

import dsl.ClosureExtractor
import dsl.steps.transformation.Mapper

class RandomForestMapper extends Mapper {


    @Override
    Object invokeMethod(String name, Object args) {
        super.invokeMethod(name, args)
        RandomForestClassifier randomForestClassifier = new RandomForestClassifier()
        ClosureExtractor.extract(args[0], randomForestClassifier)
        map[name] = randomForestClassifier
    }
}
