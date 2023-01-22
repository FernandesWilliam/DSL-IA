package dsl.steps.graph

import dsl.steps.comparison.ComparisonStep

class ComparisonGraphGenerator {
    ComparisonStep comparisonStep;
    def comparisonNodeName;
    def ComparisonGraphGenerator(comparisonStep) {
        this.comparisonStep = comparisonStep;
    }
    String validationSubGraph(){
        String subGraph = "with g.subgraph(name='cluster_3') as val:\n"
        subGraph += "\tval.attr(style='filled', fillcolor='antiquewhite3')\n"
        subGraph += "\tval.attr(rank='same')\n"
        subGraph += "\tval.node('cross_validate')\n"
        for(def model : comparisonStep.toCompare) {
            subGraph += "\tval.edge('${model}','cross_validate')\n";
        }
        subGraph += "\tval.attr(label='Validation')\n"
        return subGraph
    }

    String comparisonSubGraph() {
        String subGraph = "with g.subgraph(name='cluster_4') as comp:\n"
        subGraph += "\tcomp.attr(style='filled', fillcolor='antiquewhite2')\n"
        subGraph += "\tcomp.attr(rank='same')\n"
        subGraph += "\tcomp.node('cross_validate')\n"
        def i=0;
        def compareUsing = ""
        def firstCriteria = true
        for(def criteria : comparisonStep.criteria) {
            if(!firstCriteria){
                compareUsing += " | ";
            }
            compareUsing += "${criteria} weight ${comparisonStep.criteriaWeight[i++]}";
            firstCriteria = false;
        }
        println "label='comparison |{ ${compareUsing} }";
        subGraph += "\tcomp.node('compare',shape='record', label='comparison |{ ${compareUsing} }')\n"

        subGraph += "\tcomp.edge('cross_validate','compare')\n";

        subGraph += "\tcomp.attr(label='Comparison')\n"
        return subGraph
    }

    String reportSubGraph() {
        String subGraph = "with g.subgraph(name='cluster_5') as report:\n"
        subGraph += "\treport.attr(style='filled', fillcolor='azure2')\n"
        subGraph += "\treport.attr(rank='same')\n"
        subGraph += "\treport.node('REPORT',shape='record', label='REPORT | { ARRAY RESULT | CLOUD DOT  }')\n"
        subGraph += "\treport.edge('compare','REPORT')\n";
        subGraph += "\treport.attr(label='Display Report')\n"
        return subGraph
    }

    String generate(){
        String subGraph = validationSubGraph();
        subGraph += comparisonSubGraph();
        subGraph += reportSubGraph();
        return subGraph;
    }
}
