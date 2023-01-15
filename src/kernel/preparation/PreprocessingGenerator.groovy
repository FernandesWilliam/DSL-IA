package kernel.preparation

import dsl.steps.preparation.Preprocessing
import kernel.Generator
import kernel.StringUtils

class PreprocessingGenerator implements Generator {

    StringBuilder preprocessingBuilder;

    def preprocessingMethods = [
            "notNull"       : { dataset, v -> "${dataset}.dropna()" },
            "removeOutliers": { dataset, v -> removeOutliers(dataset, v) }


    ]

    PreprocessingGenerator(Preprocessing preprocessing) {
        preprocessingBuilder = new StringBuilder();
        preprocessing.methods.entrySet();
        applyProcessingOnDataSet(preprocessing.methods)
    }


    /**
     * Define the outlier method that will be apply to remove all unnecessary values.
     * Could be applied on all method
     * @param dataset
     * @param value
     * @return
     */
    def removeOutliers(dataset, value) {
        StringBuilder stringBuilder = new StringBuilder();
        def iqr = "IQR = Q3 - Q1"
        if (value.last() == "*") {
            stringBuilder.append("Q1=${dataset}.quantile(${value[0]})")
                    .append(StringUtils.lineFeed())
                    .append("Q3=${dataset}.quantile(${value[1]})")
                    .append(StringUtils.lineFeed())
                    .append(iqr)
                    .append(StringUtils.lineFeed())
                    .append("$dataset[~(($dataset < (Q1 - 1.5 * IQR)) " +
                            "| ($dataset > (Q3 + 1.5 * IQR))).any(axis = 1)]")
        } else {
            stringBuilder.append("cols= [${value[2].join(",")} ]")
                    .append(StringUtils.lineFeed())
                    .append("Q1=$dataset[cols].quantile(${value[0]})")
                    .append(StringUtils.lineFeed())
                    .append("Q3=$dataset[cols].quantile(${value[1]})")
                    .append(StringUtils.lineFeed())
                    .append(iqr)
                    .append(StringUtils.lineFeed())
                    .append("$dataset[~(($dataset[cols] < (Q1 - 1.5 * IQR)) " +
                            "| ($dataset[cols] > (Q3 + 1.5 * IQR))).any(axis = 1)]")
        }

    }



    def applyProcessingOnDataSet(methods) {
        def entrySet = methods.entrySet()
        def dataSetName = "dataset"
        preprocessingBuilder.append("#Preprocessing Phase :")
                .append(StringUtils.lineFeed())
        for (entry in entrySet) {
            preprocessingBuilder
                    .append("#preprocess ${entry.key} :")
                    .append(StringUtils.lineFeed())
                    .append(preprocessingMethods[(String) entry.key](dataSetName, entry.value))
                    .append(StringUtils.lineFeed())
        }
    }

    @Override
    def generate(Object maps) {
        return preprocessingBuilder
    }
}
