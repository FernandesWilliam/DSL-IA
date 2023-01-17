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

##DATASET SPLIT
X_train, y_train = dataTrainSet.drop(['label'], axis = 1), dataTrainSet['label']
X_test, y_test = dataTestSet.drop(['label'], axis = 1), dataTestSet['label']
###### ---- TRANSFORMATION PHASE ---- ######

# MinMax TRANSFORMATION
minMax01 = MinMaxScaler(feature_range=(0,1),clip=False,copy=True)
# PCA TRANSFORMATION
pca55 = PCA(n_components=0.55)
pca62 = PCA(n_components=0.62)
# SCALER TRANSFORMATION
std = StandardScaler(copy=True, with_mean=True, with_std=True)
###### ---- TRAINING PHASE ---- ######
##KNN CLASSIFIER
##GAUSSIAN CLASSIFIER
kfold_gaussian1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian1= Pipeline([('gaussian1_t',t),('gaussian1_1',1) ,('clf_nb', GaussianClassifier())])
distribution_gaussian1_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian1 =RandomizedSearchCV(estimator= pipe_gaussian1,param_distributions = distribution_gaussian1_param, cv =kfold_gaussian1,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_gaussian1 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={}) 

kfold_gaussian2=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian2= Pipeline([('gaussian2_t',t),('gaussian2_1',1) ,('clf_nb', GaussianClassifier())])
distribution_gaussian2_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian2 =RandomizedSearchCV(estimator= pipe_gaussian2,param_distributions = distribution_gaussian2_param, cv =kfold_gaussian2,  verbose = 2, n_jobs = -1, n_iter = 5)
scores_gaussian2 = cross_validate(rsName, Xtrain ,y_train,cv=5,scoring={}) 

##RANDOMFOREST CLASSIFIER
## # validation + comparaison
socring = {'accuracy' : 'accuracy',
			'time' : 'time'}
scores = dict()
accuracy_coef = 10
time_coef = 3

# RNDFOREST1
scores['rndForest1'] = {}
scores['rndForest1']['accuracy'] = []
scores['rndForest1']['time'] = []

scores_rndForest1 = cross_validate(gs_rndForest1, X_train_t1, y_train, cv=2, scoring = scoring)
accuracy_rndForest1 = np.mean(scores_rf['accuracy']), np.std(scores_rf['accuracy'])
time_rndForest1 = np.mean(scores_rf['time']), np.std(scores_rf['time'])

scores['rndForest1']['accuracy'].append(scores_rndForest1['accuracy'])
scores['rndForest1']['time'].append(scores_rndForest1['time'])

# GAUSS1
scores['gauss1'] = {}
scores['gauss1']['accuracy'] = []
scores['gauss1']['time'] = []

scores_gauss1 = cross_validate(gs_gauss1, X_train_t1, y_train, cv=2, scoring = scoring)
accuracy_gauss1 = np.mean(scores_rf['accuracy']), np.std(scores_rf['accuracy'])
time_gauss1 = np.mean(scores_rf['time']), np.std(scores_rf['time'])

scores['gauss1']['accuracy'].append(scores_gauss1['accuracy'])
scores['gauss1']['time'].append(scores_gauss1['time'])

# COMPUTE GLOBAL SCORE
models_scores = {}
models_scores['rndForest1'] = accuracy_rndForest1 * accuracy_coef + time_rndForest1 * time_coef
models_scores['gauss1'] = accuracy_gauss1 * accuracy_coef + time_gauss1 * time_coef

# WINNER MODEL
winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]
print("winner model :",winner_model)