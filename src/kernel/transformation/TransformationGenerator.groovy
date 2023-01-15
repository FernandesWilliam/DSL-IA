package kernel.transformation

import dsl.steps.transformation.MinMaxMapper
import dsl.steps.transformation.PcaMapper
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.StringUtils

class TransformationGenerator implements Generator {

    StringBuilder stringBuilder;

    TransformationGenerator(TransformationStep transformationStep) {
        stringBuilder = new StringBuilder();
        if (transformationStep.normalize != 0) {

        }
        if (transformationStep.minMaxMapper) {
            generateMinMax(transformationStep.minMaxMapper)
        }

        if (transformationStep.pcaMapper) {
            generatePCA(transformationStep.pcaMapper)
        }
    }


    def generateNormalize(float normalizeValue) {
    }

    def generateMinMax(MinMaxMapper minMaxMapper) {
        stringBuilder.append("# MinMax TRANSFORMATION").append(StringUtils.lineFeed())
        for (def entry in minMaxMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = MinMaxScaler((feature_range=(${entry.value.feature_range.join(',')}),clip=${entry.value.clip},copy=${entry.value.copy})")
                    .append(StringUtils.lineFeed())

        }
    }

    def generatePCA(PcaMapper pcaMapper) {

        stringBuilder.append("# PCA TRANSFORMATION").append(StringUtils.lineFeed())
        for (def entry in pcaMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = PCA(n_components=${entry.value.n_components})")
                    .append(StringUtils.lineFeed())

        }
    }

    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
