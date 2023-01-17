preparation {
    train "../input/digit-recognizer/train1.csv"
    test "../input/digit-recognizer/test.csv"

    preprocessing {
        notNull()
        removeOutliers 0.01, 0.8
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
    pipe t2: t1 + [pca55]
}
training {
    declare gaussian1 as gaussian {
        cv 5
        kfold stratified(2, true)
        distributionParams {
            clf_nb__var_smoothing logspace(-9, 0, 5)
        }
        transformation t1
    }

    declare gaussian2 as gaussian {
        cv 5
        kfold stratified(2, true)
        distributionParams {
            clf_nb__var_smoothing logspace(-9, 0, 5)
        }
        transformation t1
    }
}

comparison {
    compare rndForest1, gauss1 with accuracy weight 10 and time weight 3
}
export "wvfwev"