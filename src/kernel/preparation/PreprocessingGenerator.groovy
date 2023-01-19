package kernel.preparation

import dsl.steps.preparation.Preprocessing
import kernel.Generator
import kernel.stringutils.StringUtilsJupyter

class PreprocessingGenerator implements Generator {

    StringBuilder preprocessingBuilder;
    Boolean mergedData;

    def preprocessingMethods = [

            "rmNull"       : { dataset, v -> "${dataset}.dropna()"+ StringUtilsJupyter.lineFeed()},
            "rmOutliers"    : { dataset, v -> removeOutliers(dataset, v) },
            "drop"          : { dataSet, columnName -> "${dataSet}.drop(['${columnName}'], axis = 1)"+StringUtilsJupyter.lineFeed()}
    ]

    PreprocessingGenerator(Preprocessing preprocessing, Boolean mergedData) {
        this.mergedData = mergedData;
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
                    .append(StringUtilsJupyter.lineFeed())
                    .append("Q3=${dataset}.quantile(${value[1]})")
                    .append(StringUtilsJupyter.lineFeed())
                    .append(iqr)
                    .append(StringUtilsJupyter.lineFeed())
                    .append("$dataset[~(($dataset < (Q1 - 1.5 * IQR)) " +
                            "| ($dataset > (Q3 + 1.5 * IQR))).any(axis = 1)]")
                    .append(StringUtilsJupyter.lineFeed())
        } else {
            stringBuilder.append("cols= [${value[2].join(",")} ]")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("Q1=$dataset[cols].quantile(${value[0]})")
                    .append(StringUtilsJupyter.lineFeed())
                    .append("Q3=$dataset[cols].quantile(${value[1]})")
                    .append(StringUtilsJupyter.lineFeed())
                    .append(iqr)
                    .append(StringUtilsJupyter.lineFeed())
                    .append("$dataset[~(($dataset[cols] < (Q1 - 1.5 * IQR)) " +
                            "| ($dataset[cols] > (Q3 + 1.5 * IQR))).any(axis = 1)]")
                    .append(StringUtilsJupyter.lineFeed())
        }

    }



    def applyProcessingOnDataSet(methods) {
        def entrySet = methods.entrySet()
        def dataSetsName = []
        if(mergedData) {
            dataSetsName = ["dataset"]
        }else {
            dataSetsName = ["dataTrainSet", "dataTestSet"]
        }
        preprocessingBuilder.append("###### ---- PREPROCESSING PHASE ---- ######")
                .append(StringUtilsJupyter.lineFeed())
        for (entry in entrySet) {
            preprocessingBuilder.append("## PREPROCESS : ${entry.key.toUpperCase()} ").append(StringUtilsJupyter.lineFeed())
            for(dataSet in dataSetsName){
                preprocessingBuilder
                        .append(preprocessingMethods[(String) entry.key](dataSet, entry.value))
                        .append(StringUtilsJupyter.lineFeed())
            }
        }
    }

    @Override
    def generate(Object maps) {
        return preprocessingBuilder
    }
}
