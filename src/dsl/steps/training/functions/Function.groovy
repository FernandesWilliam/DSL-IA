package dsl.steps.training.functions

import dsl.steps.DSLThrower
import kernel.stringutils.StringUtils

import java.lang.reflect.Method

class Function implements DSLThrower {

    String function;


    Function(name, args) {

        Method method = Function.methods.find { method -> method.name == name }
        if (!method)
            reject("${name} function doesn't exist or is not support by the kernel")
        invokeMethod(name, args);


    }


    def randint(int min, int max) {
        if (max < min) reject("First argument should be lower than second one")
        this.function = "sp_randint(${min},${max})"

    }


    def stratified(int split, boolean shuffle) {
        if (split <= 1)
            reject("Stratified fold should be greater or equals than 2")
        this.function = "StratifiedKFold(n_splits=$split, shuffle = ${shuffle.toString().capitalize()})"
    }

    def logspace(int a, int b, int c) {
        this.function = "np.logspace($a, $b, $c)"
    }
}
