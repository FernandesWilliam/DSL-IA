package kernel.training

import dsl.steps.training.classifier.gaussian.GaussianClassifier
import dsl.steps.training.functions.Function
import kernel.Generator
import kernel.StringUtils

class GaussianGenerator implements Generator {
    //  gaussian {
    //        gauss1 {
    //            scoring accuracy, time
    //            cv 5
    //            kfold stratified(2, true)
    //            distributionParams {
    //                clf_nb__var_smoothing logspace(-9, 0, 5)
    //            }
    //            transformations minMax02, pca55
    //        }
    //    }

    StringBuilder stringBuilder

    GaussianGenerator(String name ,GaussianClassifier gaussianClassifier){
        stringBuilder =new ClassifierGenerator(name,gaussianClassifier,"('clf_nb', GaussianClassifier())").generate()

    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
