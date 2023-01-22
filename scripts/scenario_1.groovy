//preparation {
//    fetchAll "../input/digit-recognizer/testTrain.csv"
//    splitDataset {
//        training_size 70
//        seed 2394882
//    }
//    preprocessing {
//        rmNull
//        rmOutliers 0.01, 0.8
//    }
//}
//transformation {
//
//    declare pca55 as pca {
//        n_components 0.55
//    }
//    declare pca62 as pca {
//        n_components 0.62
//    }
//    declare minMax01 as minmax {}
//
//    declare std as standardScaler {}
//
//    pipe t1: [pca55, pca55]
//    pipe t2: [t1, pca55, pca62, std]
//    pipe t3: [t1, pca55, std, pca62]
//    pipe t4: [t2,pca55, std, std]
//}
//training {
//    declare gaussian1 as gaussian {
//        cv 5
//        kfold stratified(2, true)
//        distributionParams {
//            smoothing logspace(-9, 0, 5)
//        }
//        transformation t2
//    }
//
//    declare gaussian2 as gaussian {
//        cv 2
//        kfold stratified(2, true)
//        distributionParams {
//            smoothing logspace(-9, 0, 5)
//        }
//        transformation t3
//    }
//
//    declare knn1 as knn {
//        cv 5
//        kfold stratified(2, true)
//        cv 5
//        distributionParams {
//            nNeighbors randint(1, 11)
//            algorithm 'auto'
//        }
//        transformation t2
//    }
//    declare rndForest1 as randomForest {
//        class_weight 'balanced'
//        kfold stratified(2, true)
//        distributionParams {
//            maxDepth 5, None
//            bootstrap true, false
//            criterion "gini", "entropy"
//            maxFeatures randint(1, 11)
//            minSamplesSplit randint(2, 11)
//            minSamplesLeaf randint(1, 11)
//        }
//        transformation t3
//        cv 5
//    }
//
//
//}
//
//comparison {
//    compare gaussian1, gaussian2,knn1 with test_acc weight 10 and fit_time weight 3
//}
//notebook "wvfwev"