package dsl.steps

import kernel.stringutils.StringUtilsJupyter

interface DSLThrower {

    default reject(String message) {
        def header = "[Syntax Error]" + StringUtilsJupyter.lineFeed()
        throw new Exception(header + message)
    }
}