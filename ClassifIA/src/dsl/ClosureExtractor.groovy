package dsl

class ClosureExtractor {
    static def extract(closure, object) {
        def code = closure.rehydrate(object, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code();
    }
}
