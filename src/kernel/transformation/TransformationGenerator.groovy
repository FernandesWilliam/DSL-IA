package kernel.transformation

import dsl.steps.transformation.MinMaxMapper
import dsl.steps.transformation.PcaMapper
import dsl.steps.transformation.StandardScalerMapper
import dsl.steps.transformation.TransformationStep
import kernel.Generator
import kernel.notebook.CodeBlockGenerator

class TransformationGenerator implements Generator {

    StringBuilder stringBuilder;

    TransformationGenerator(TransformationStep transformationStep) {
        stringBuilder = new StringBuilder("###### ---- TRANSFORMATION PHASE ---- ######").append(CodeBlockGenerator.NEWLINE)

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
    }


    def generateNormalize(float normalizeValue) {
        stringBuilder.append("# Normalization TRANSFORMATION").append(CodeBlockGenerator.NEWLINE)
        stringBuilder.append("X_train = X_train / 255.").append(CodeBlockGenerator.NEWLINE)
        stringBuilder.append("X_test = X_test / 255.")
                        .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

    }

    def generateMinMax(MinMaxMapper minMaxMapper) {
        stringBuilder.append("# MinMax TRANSFORMATION").append(CodeBlockGenerator.NEWLINE)
        for (def entry in minMaxMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = MinMaxScaler(feature_range=(${entry.value.feature_range.join(',')}),clip=${entry.value.clip.toString().capitalize()},copy=${entry.value.copy.toString().capitalize()})")
                    .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)


        }
    }

    def generatePCA(PcaMapper pcaMapper) {
        stringBuilder.append("# PCA TRANSFORMATION").append(CodeBlockGenerator.NEWLINE)
        for (def entry in pcaMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = PCA(n_components=${entry.value.n_components})")
                    .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

        }
    }

    def generateStdScaler(StandardScalerMapper stdScalerMapper){
        stringBuilder.append("# SCALER TRANSFORMATION").append(CodeBlockGenerator.NEWLINE)
        for (def entry in stdScalerMapper.map.entrySet()) {
            stringBuilder.append("${entry.key} = StandardScaler(copy=${entry.value.copy.toString().capitalize()}, with_mean=${entry.value.with_mean.toString().capitalize()}, with_std=${entry.value.with_std.toString().capitalize()})")
                    .append(CodeBlockGenerator.NEWLINE).append(CodeBlockGenerator.NEWLINE)

        }
    }



    @Override
    def generate(Object maps) {
        return stringBuilder
    }
}
