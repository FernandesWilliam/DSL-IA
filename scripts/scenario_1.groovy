/*
preparation {
    train "../input/digit-recognizer/train1.csv"
    test "../input/digit-recognizer/test.csv"

    preprocessing {
        notNull()
        removeOutliers 0.01, 0.8
<<<<<<< HEAD
        drop "column1" // a faire
=======

>>>>>>> f1c2031 (implement scoring and changed declaration variable structure add pipeline)
    }
}

transformation {
    normalizer 255 // a faire tatana
    pca {
        pca55 :
            n_components 0.5
            a 42


    }
    minmax {
        minMax02 {
            feature_range (0, 4)
            copy false
        }
        minMax01 {
            copy false
        }
    }


    //standardScaler a ajouter
    standardScaler {
        standardScaler01 {
            copy false
        }
        standardScaler02 {
            copy false
            with_mean false
        }
    }

}

training {
    //Knn Classifier
    knn {
        knn1 {
            kfold stratified(2, true)
            scoring accuracy, time
            fzfz aa, zz
            cv 5
            distributionParams {
                clf_knn__n_neighbors randint(1, 11)
                clf_knn__algorithm 'auto'
            }
            transformations minMax02, pca55
        }
        knn2 {
            kfold stratified(2, true)
            scoring accuracy, time
            cv 5
            distributionParams {
                clf_knn__n_neighbors randint(1, 11)
                clf_knn__algorithm 'auto'

            }
            transformations minMax02, pca51
        }
    }
    //Gaussian Classifier
    gaussian {
        gauss1 {
            scoring accuracy, time
            cv 5
            kfold stratified(2, true)
            distributionParams {
                clf_nb__var_smoothing logspace(-9, 0, 5)
            }
            transformations minMax02, pca55
            fzfez zaz, aaa
        }
    }
    // Random Classifier
    rndForest {
        rndForest1 {
            class_weight 'balanced'

            kfold stratified(2, true)
            distributionParams {
                clf__max_depth 5, null
                clf__bootstrap true, false
                clf__criterion "gini", "entropy"
                clf__max_features randint(1, 11)
                clf__min_samples_split randint(2, 11)
                clf__min_samples_leaf randint(1, 11)
                clf_nb__var_smoothing logspace(-9, 0, 5)

            }
            transformations pca55

            scoring accuracy, time
            cv 5
        }
    }
}

comparison {
    compare rndForest1, gauss1 with accuracy weight 10 and time weight 3
}


export "first"


*/
