package kernel.transformation

import dsl.steps.transformation.MinMaxMapper
import dsl.steps.transformation.PcaMapper
import dsl.steps.transformation.StandardScalerMapper
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.stringutils.StringUtils

class TransformationGenerator implements Generator {

    StringBuilder stringBuilder;

    TransformationGenerator(TransformationStep transformationStep) {
        stringBuilder = new StringBuilder("###### ---- TRANSFORMATION PHASE ---- ######").append(StringUtils.lineFeed())

        if (transformationStep.normalize != 0) {
            generateNormalize(transformationStep.normalize)
        }
        if (transformationStep.minMaxMapper) {
            generateMinMax(transformationStep.minMaxMapper)
        }
        if (transformationStep.pcaMapper) {
            generatePCA(transformationStep.pcaMapper)
        }
        if (transformationStep.standardScalerMapper) {
            generateStdScaler(transformationStep.standardScalerMapper)
        }
        generatePipeline(transformationStep.pipelines)

    }


    def generateNormalize(float normalizeValue) {
        stringBuilder.append("# Normalization TRANSFORMATION").append(StringUtils.lineFeed())
        stringBuilder.append("X_train = X_train / 255.").append(StringUtils.lineFeed())
        stringBuilder.append("X_test = X_test / 255.").append(StringUtils.lineFeed(2))

    }

    def generateMinMax(MinMaxMapper minMaxMapper) {
        if (minMaxMapper.map.size() == 0) return
        stringBuilder.append("# MinMax TRANSFORMATION").append(StringUtils.lineFeed())
        for (def entry in minMaxMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = MinMaxScaler(feature_range=(${entry.value.feature_range.join(',')}),clip=${entry.value.clip.toString().capitalize()},copy=${entry.value.copy.toString().capitalize()})")
                    .append(StringUtils.lineFeed(2))


        }
    }

    def generatePCA(PcaMapper pcaMapper) {

        if (pcaMapper.map.size() == 0) return
        stringBuilder.append("# PCA TRANSFORMATION").append(StringUtils.lineFeed())
        for (def entry in pcaMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = PCA(n_components=${entry.value.n_components})")
                    .append(StringUtils.lineFeed(2))

        }
    }

    def generateStdScaler(StandardScalerMapper stdScalerMapper){
        if (stdScalerMapper.map.size() == 0) return
        stringBuilder.append("# STANDARDSCALER TRANSFORMATION").append(StringUtils.lineFeed())

        for (def entry in stdScalerMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = StandardScaler(copy=${entry.value.copy.toString().capitalize()}, with_mean=${entry.value.with_mean.toString().capitalize()}, with_std=${entry.value.with_std.toString().capitalize()})")
                    .append(StringUtils.lineFeed(2))

        }
    }


    def generatePipeline(pipelines) {
        if (pipelines.size() == 0) return
        stringBuilder.append(" ## ---- TRANSFORMATION PROCESSING ---- ##")
                .append(StringUtils.lineFeed());
        def start = "X_train"
        def transformationName = start
        for (def i = 0; i < pipelines.size(); i++) {
            stringBuilder.append("### Transformation : ${i + 1}")
                    .append(StringUtils.lineFeed());
            for (def j = 0; j < pipelines[i][1].size(); j++) {
                if (pipelines.find { pipe -> pipe[0] == pipelines[i][1][j] } != null) {
                    transformationName = "${start}_${pipelines[i][1][j]}";
                    continue
                } else if (j == 0) {
                    transformationName = start
                }

                if (pipelines[i][1].size() - 1 == j) {
                    stringBuilder.append("${start}_${pipelines[i][0]} = ${pipelines[i][1][j]}.fit_transform($transformationName)")
                            .append(StringUtils.lineFeed());
                } else {
                    stringBuilder.append("${transformationName}_${pipelines[i][1][j]} = ${pipelines[i][1][j]}.fit_transform($transformationName)")
                            .append(StringUtils.lineFeed());
                }
                transformationName = "${transformationName}_${pipelines[i][1][j]}";

            }

        }

    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
