preparation {
    fetchTrain "../input/digit-recognizer/train.csv"
    fetchTest "../input/digit-recognizer/test.csv"

    preprocessing {
        rmNull
        rmOutliers 0.01, 0.8
    }
}
transformation {
    declare pca01 as pca {
        n_components 0.62
    }
    declare pca02 as pca {
        n_components 0.41
    }
    declare minMax01 as minmax {}

    declare standardScaler1 as standardScaler{

    }

    pipe t1: [minMax01, pca01]
    pipe t2: [minMax01, pca02]
}
training {
    declare gaussian1 as gaussian {
        cv 5
        kfold stratified(5, true)
        distributionParams {
            smoothing logspace(-5, 0, 5)
        }
        transformation t1
    }


    declare knn1 as knn {
        cv 5
        kfold stratified(2, true)
        cv 5
        distributionParams {
            nNeighbors randint(1, 11)
            algorithm 'auto'
        }
        transformation t2
    }


    declare knn2 as knn {
        cv 5
        kfold stratified(2, true)
        cv 5
        distributionParams {
            nNeighbors randint(1, 10)
            algorithm 'auto'
        }
        transformation t2
    }
}

comparison {
    compare gaussian1, knn1 with test_acc weight 10 and fit_time weight 3
}
notebook "compare_gaussian_knn"
