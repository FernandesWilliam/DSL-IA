package dsl.steps.graph

class GraphUtils {
    static List getRootPipelines(pipelines){
        println pipelines
        def pipelinesName = pipelines.keySet();
        def roots = pipelines.findAll { key, values ->
            !values.any { value -> pipelines.containsKey(value) }
        }.keySet() as List
        return roots
    }

    static generateRootsSubTree(pipeline){
        def graph = [:]
        pipeline.each {key, value ->
            value.each { v ->
                if(pipeline.containsKey(v)){
                    if(!graph.containsKey(v))
                        graph[v] = []
                    graph[v] << key
                }
            }
        }
        println graph
        return graph
    }


}
