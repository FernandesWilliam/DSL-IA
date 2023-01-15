package dsl.steps.training.functions

import java.lang.reflect.Method

class Function {

    String function;


    Function(name, args) {

        Method method = Function.methods.find { method -> method.name == name }
        if (!method)
            throw new Exception("Je ne connais pas la fonction ${name} suivante")
        invokeMethod(name, args);


    }


    def randint(min, max) {
        this.function = "randing(${min},${max})"

    }


    def stratified(split, shuffle) {
        this.function = "ewff"
    }

    def logspace(a, b, c) {
        this.function = ",rbek"
    }
}
