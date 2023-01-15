package dsl.steps

import kernel.StringUtils

interface DSLThrower {

    default reject(String message) {
        def header = "[Syntax Error]" + StringUtils.lineFeed()
        throw new Exception(header + message)
    }
}