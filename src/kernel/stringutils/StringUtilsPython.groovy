package kernel.stringutils

import kernel.JupyterGenerator

class StringUtilsPython implements IStringUtils{

    @Override
    def tab(tabNumber = 1) {
        return "\t".repeat(tabNumber)
    }

    @Override
    def lineFeed(number = 1) {
        return "\n".repeat(number)
    }

    @Override
    def comment(String text){
        JupyterGenerator.generateMarkDownBlock(text)
    }

}
