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
### Transformation : 1
X_train_pca55 = pca55.fit_transform(X_train)
X_train_t1 = pca55.fit_transform(X_train_pca55)
### Transformation : 2
X_train_t1_pca55 = pca55.fit_transform(X_train_t1)
X_train_t1_pca55_pca62 = pca62.fit_transform(X_train_t1_pca55)
X_train_t2 = std.fit_transform(X_train_t1_pca55_pca62)
### Transformation : 3
X_train_t1_pca55 = pca55.fit_transform(X_train_t1)
X_train_t1_pca55_std = std.fit_transform(X_train_t1_pca55)
X_train_t3 = pca62.fit_transform(X_train_t1_pca55_std)
### Transformation : 4
X_train_t2_pca55 = pca55.fit_transform(X_train_t2)
X_train_t2_pca55_std = std.fit_transform(X_train_t2_pca55)
X_train_t4 = std.fit_transform(X_train_t2_pca55_std)
###### ---- TRAINING PHASE ---- ######
##KNN CLASSIFIER
##GAUSSIAN CLASSIFIER
kfold_gaussian1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian1= Pipeline([('clf_nb', GaussianClassifier())])
distribution_gaussian1_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian1 =RandomizedSearchCV(estimator= pipe_gaussian1,param_distributions = distribution_gaussian1_param, cv =kfold_gaussian1,  verbose = 2, n_jobs = -1, n_iter = 5)


kfold_gaussian2=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian2= Pipeline([('clf_nb', GaussianClassifier())])
distribution_gaussian2_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian2 =RandomizedSearchCV(estimator= pipe_gaussian2,param_distributions = distribution_gaussian2_param, cv =kfold_gaussian2,  verbose = 2, n_jobs = -1, n_iter = 5)


##RANDOMFOREST CLASSIFIER
## # validation + comparaison
scoring = {'accuracy' : 'accuracy',
			'time' : 'time'}
scores = dict()
accuracy_coef = 10
time_coef = 3

# GAUSSIAN1
scores['gaussian1'] = {}
scores['gaussian1']['accuracy'] = []
scores['gaussian1']['time'] = []

scores_gaussian1 = cross_validate(rs_gaussian1,X_train_t2, y_train, cv=2, scoring = scoring)
accuracy_gaussian1 = np.mean(scores_rf['accuracy']), np.std(scores_rf['accuracy'])
time_gaussian1 = np.mean(scores_rf['time']), np.std(scores_rf['time'])

scores['gaussian1']['accuracy'].append(scores_gaussian1['accuracy'])
scores['gaussian1']['time'].append(scores_gaussian1['time'])

# GAUSSIAN2
scores['gaussian2'] = {}
scores['gaussian2']['accuracy'] = []
scores['gaussian2']['time'] = []

scores_gaussian2 = cross_validate(rs_gaussian2,X_train_t3, y_train, cv=2, scoring = scoring)
accuracy_gaussian2 = np.mean(scores_rf['accuracy']), np.std(scores_rf['accuracy'])
time_gaussian2 = np.mean(scores_rf['time']), np.std(scores_rf['time'])

scores['gaussian2']['accuracy'].append(scores_gaussian2['accuracy'])
scores['gaussian2']['time'].append(scores_gaussian2['time'])

# COMPUTE GLOBAL SCORE
models_scores = {}
models_scores['gaussian1'] = accuracy_gaussian1 * accuracy_coef + time_gaussian1 * time_coef
models_scores['gaussian2'] = accuracy_gaussian2 * accuracy_coef + time_gaussian2 * time_coef

# WINNER MODEL
winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]
print("winner model :",winner_model)