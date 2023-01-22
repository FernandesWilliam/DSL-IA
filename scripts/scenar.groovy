preparation {
    fetchAll "../input/digit-recognizer/testTrain.csv"
    splitDataset {
        training_size 70
        seed 2394882
    }
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

    pipe t1: [minMax01, pca01]
}
training {
    declare gaussian1 as gaussian {
        cv 5
        kfold stratified(5, true)
        distributionParams {
            smoothing logspace(-5, 0, 4)
        }
        transformation t1
    }

    declare gaussian2 as gaussian {
        cv 5
        kfold stratified(5, true)
        distributionParams {
            smoothing logspace(-9, 0, 5)
        }
        transformation t1
    }
}

comparison {
    compare gaussian1, gaussian2 with test_acc weight 10 and fit_time weight 3
}
notebook "compare_gaussian"