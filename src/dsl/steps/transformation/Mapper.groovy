package dsl.steps.transformation

class Mapper {

    def map = [:]

    @Override
    Object invokeMethod(String name, Object args) {
        if (map.containsKey(name)) {
            throw new Exception("${name} already exist")
        }

    }

}
