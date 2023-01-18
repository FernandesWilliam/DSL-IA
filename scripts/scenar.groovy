preparation {
    fetch "../input/digit-recognizer/train1.csv"

    preprocessing {
        rmNull
        rmOutliers 0.01, 0.8
    }
}
transformation {

    declare pca55 as pca {
        n_components 0.55
    }
    declare pca62 as pca {
        n_components 0.62
    }
    declare minMax01 as minmax {}

    declare std as standardScaler {}

    pipe t1: [pca55, pca55]
    pipe t2: [t1, pca55, pca62, std]
    pipe t3: [t1, pca55, std, pca62]
    pipe t4: [t2,pca55, std, std]
}
training {
    declare gaussian1 as gaussian {
        cv 5
        kfold stratified(2, true)
        distributionParams {
            smooth logspace(-9, 0, 5)
        }
        transformation t2
    }

    declare gaussian2 as gaussian {
        cv 1
        kfold stratified(2, true)
        distributionParams {
            smooth logspace(-9, 0, 5)
        }
        transformation t3
    }

    declare knn1 as knn {
        cv 5
        kfold stratified(2, true)
        cv 5
        distributionParams {
            neighborsNumber randint(1, 11)
            algo 'auto'
        }
        transformation t2
    }
    declare rndForest1 as randomForest {
        class_weight 'balanced'
        kfold stratified(2, true)
        distributionParams {
            maxDepth 5, null
            bootstrap true, false
            criterion "gini", "entropy"
            maxFeatures randint(1, 11)
            samplesSplit randint(2, 11)
            samplesLeaf randint(1, 11)
        }
        transformation t3
        cv 5
    }


}

comparison {
    compare gaussian1, gaussian2,knn1 with accuracy weight 10 and time weight 3
}
export "wvfwev"