preparation {
    fetch "../input/digit-recognizer/train.csv"

    // train et test
    //  si il existe kick fetch et generateDataset
    generateDataset {
        seed 45
        training_size 70
    }
    preprocessing {
        notNull() //verif sur les arguments qu'on mette pas
        removeOutliers 0.01, 0.8
        drop "colum1" // a faire
    }
}

transformation {
    normalizer 255 // a faire

    pca {
        pca55 {
            n_components 0.5
        }

        pca51 {
            n_components 0.3
        }
    }
    minmax {
        minMax02 {
            feature_range(0, 4)
            copy false
        }
        minMax01 {
            copy false
        }
    }


    //standardScaler a ajouter

}

training {
    //Knn Classifier
    knn {
        knn1 {
            kfold stratified(2, true)
            scoring accuracy, time
            cv 5
            distributionParams {
                clf_knn__n_neighbors randint(1, 11)
                clf_knn__algorithm 'auto'
            }
            transformations minMax02, pca55


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
        }
    }
    // Random Classifier
    rndForest {
        rndForest1 {
            n_estimators 100
            class_weight 'balanced'

            kfold stratified(2, true)
            distributionParams {
                clf__max_depth 5, null
                clf__bootstrap true, false
                clf__criterion "gini", "entropy"
                clf__max_features randint(1, 11)
                clf__min_samples_split randint(2, 11)
                clf__min_samples_leaf randint(1, 11)

            }
            transformations pca55

            scoring accuracy, time
            cv 5
        }
    }
}

comparison { // a faire
    // faire gaff au error
    compare rndForest1, gauss1 with accuracy weight 10 and time weight 3
}



export "first"


