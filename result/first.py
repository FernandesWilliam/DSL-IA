###### ---- PREPARATION PHASE ---- ######

## DATASET IMPORT
import pandas as pd
try:
	dataTrainSet = pd.read_csv("../input/digit-recognizer/train1.csv")
	dataTestSet = pd.read_csv("../input/digit-recognizer/test.csv")
except FileNotFoundError:
	print("The path of the dataset is invalid")

###### ---- PREPROCESSING PHASE ---- ######

## PREPROCESS : NOTNULL 
dataTrainSet.dropna()

dataTestSet.dropna()

## PREPROCESS : REMOVEOUTLIERS 
Q1=dataTrainSet.quantile(0.01)
Q3=dataTrainSet.quantile(0.8)
IQR = Q3 - Q1
dataTrainSet[~((dataTrainSet < (Q1 - 1.5 * IQR)) | (dataTrainSet > (Q3 + 1.5 * IQR))).any(axis = 1)]

Q1=dataTestSet.quantile(0.01)
Q3=dataTestSet.quantile(0.8)
IQR = Q3 - Q1
dataTestSet[~((dataTestSet < (Q1 - 1.5 * IQR)) | (dataTestSet > (Q3 + 1.5 * IQR))).any(axis = 1)]

<<<<<<< HEAD
## PREPROCESS : DROP 
dataTrainSet.drop(['column1'], axis = 1)

dataTestSet.drop(['column1'], axis = 1)

=======
>>>>>>> f1c2031 (implement scoring and changed declaration variable structure add pipeline)
##DATASET SPLIT
X_train, y_train = dataTrainSet.drop(['label'], axis = 1), dataTrainSet['label']
X_test, y_test = dataTestSet.drop(['label'], axis = 1), dataTestSet['label']
###### ---- TRANSFORMATION PHASE ---- ######

# Normalization TRANSFORMATION
X_train = X_train / 255.
X_test = X_test / 255.
# MinMax TRANSFORMATION
minMax02 = MinMaxScaler(feature_range=(0,4),clip=False,copy=True)
minMax01 = MinMaxScaler(feature_range=(0,1),clip=False,copy=True)
# SCALER TRANSFORMATION
standardScaler01 = StandardScaler(copy=False, with_mean=True, with_std=True)
standardScaler02 = StandardScaler(copy=False, with_mean=False, with_std=True)
###### ---- TRAINING PHASE ---- ######
##KNN CLASSIFIER
kfold_knn1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_knn1= Pipeline([('knn1_minMax02',minMax02),('knn1_pca55',pca55) ,('clf_knn', KNeighborsClassifier())])
distribution_knn1_param={"clf_knn__n_neighbors": sp_randint(1,11),"clf_knn__algorithm": ["auto"] }
rs_knn1 =RandomizedSearchCV(estimator= pipe_knn1,param_distributions = distribution_knn1_param, cv =kfold_knn1,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_knn1 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={"accuracy":"accuracy","time":"time"}) 

kfold_knn2=StratifiedKFold(n_splits=2, shuffle = True)
pipe_knn2= Pipeline([('knn2_minMax02',minMax02),('knn2_pca51',pca51) ,('clf_knn', KNeighborsClassifier())])
distribution_knn2_param={"clf_knn__n_neighbors": sp_randint(1,11),"clf_knn__algorithm": ["auto"] }
rs_knn2 =RandomizedSearchCV(estimator= pipe_knn2,param_distributions = distribution_knn2_param, cv =kfold_knn2,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_knn2 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={"accuracy":"accuracy","time":"time"}) 

##GAUSSIAN CLASSIFIER
kfold_gauss1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gauss1= Pipeline([('gauss1_minMax02',minMax02),('gauss1_pca55',pca55) ,('clf_nb', GaussianClassifier())])
distribution_gauss1_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gauss1 =RandomizedSearchCV(estimator= pipe_gauss1,param_distributions = distribution_gauss1_param, cv =kfold_gauss1,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_gauss1 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={"accuracy":"accuracy","time":"time"}) 

##RANDOMFOREST CLASSIFIER
kfold_rndForest1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_rndForest1= Pipeline([('rndForest1_pca55',pca55) ,('clf_r', RandomForestClassifier())])
distribution_rndForest1_param={"clf__max_depth": [5, null],"clf__min_samples_split": sp_randint(2,11),"clf__max_features": sp_randint(1,11),"clf__min_samples_leaf": sp_randint(1,11),"clf__bootstrap": [true, true],"clf__criterion": [gini, entropy] }
rs_rndForest1 =RandomizedSearchCV(estimator= pipe_rndForest1,param_distributions = distribution_rndForest1_param, cv =kfold_rndForest1,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_rndForest1 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={"accuracy":"accuracy","time":"time"}) 

