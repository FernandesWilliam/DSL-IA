package dsl.steps


interface DSLThrower {

    default reject(String message) {
        def header = "[Syntax Error]" + StringUtilsJupyter.lineFeed()
        throw new Exception(header + message)
    }
}