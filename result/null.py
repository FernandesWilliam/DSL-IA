%pylab inline
#%pylab
import numpy as np
from sklearn import datasets, svm, metrics
import pandas as pd
from matplotlib import pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from matplotlib import pyplot as plt
import seaborn as sns
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn import preprocessing
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import MinMaxScaler
from sklearn.decomposition import PCA
from sklearn.metrics import confusion_matrix, recall_score, accuracy_score,precision_score, f1_score, roc_curve, auc, make_scorer,roc_auc_score 
from sklearn.metrics import classification_report
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from scipy import stats
from sklearn.model_selection import RandomizedSearchCV
from scipy.stats import randint as sp_randint
from sklearn.model_selection import cross_validate
from sklearn.model_selection import StratifiedKFold
from sklearn.ensemble import AdaBoostClassifier
from sklearn.gaussian_process import GaussianProcessClassifier
from sklearn.gaussian_process.kernels import RBF
from sklearn.naive_bayes import GaussianNB
import operator

###### ---- PREPARATION PHASE ---- ######

## DATASET IMPORT
import pandas as pd
try:
	dataset = pd.read_csv("../input/digit-recognizer/train.csv")
except FileNotFoundError:
	print("The path of the dataset is invalid")

###### ---- PREPROCESSING PHASE ---- ######

## PREPROCESS : RMNULL 
dataset.dropna()

## PREPROCESS : RMOUTLIERS 
Q1=dataset.quantile(0.01)
Q3=dataset.quantile(0.8)
IQR = Q3 - Q1
dataset[~((dataset < (Q1 - 1.5 * IQR)) | (dataset > (Q3 + 1.5 * IQR))).any(axis = 1)]

##DATASET SPLIT
X,y = dataset.drop(['label'], axis = 1), dataset['label']
X_train, X_test, y_train, y_test=train_test_split(X, y,test_size=80,random_state=2394883)
###### ---- TRANSFORMATION PHASE ---- ######

# MinMax TRANSFORMATION
minMax01 = MinMaxScaler(feature_range=(0,1),clip=False,copy=True)
# PCA TRANSFORMATION
pca55 = PCA(n_components=0.55)
pca62 = PCA(n_components=0.62)
# STANDARDSCALER TRANSFORMATION
std = StandardScaler(copy=True, with_mean=True, with_std=True)
 ## ---- TRANSFORMATION PROCESSING ---- ##
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
kfold_knn1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_knn1= Pipeline([('clf_knn', KNeighborsClassifier())])
distribution_knn1_param={"clf_knn__n_neighbors": sp_randint(1,11),"clf_knn__algorithm": ["auto"] }
rs_knn1 =RandomizedSearchCV(estimator= pipe_knn1,param_distributions = distribution_knn1_param, cv =kfold_knn1,  verbose = 2, n_jobs = -1, n_iter = 5)


##GAUSSIAN CLASSIFIER
kfold_gaussian1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian1= Pipeline([('clf_nb', GaussianNB())])
distribution_gaussian1_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian1 =RandomizedSearchCV(estimator= pipe_gaussian1,param_distributions = distribution_gaussian1_param, cv =kfold_gaussian1,  verbose = 2, n_jobs = -1, n_iter = 5)


kfold_gaussian2=StratifiedKFold(n_splits=2, shuffle = True)
pipe_gaussian2= Pipeline([('clf_nb', GaussianNB())])
distribution_gaussian2_param={"clf_nb__var_smoothing": np.logspace(-9, 0, 5) }
rs_gaussian2 =RandomizedSearchCV(estimator= pipe_gaussian2,param_distributions = distribution_gaussian2_param, cv =kfold_gaussian2,  verbose = 2, n_jobs = -1, n_iter = 5)


##RANDOMFOREST CLASSIFIER
kfold_rndForest1=StratifiedKFold(n_splits=2, shuffle = True)
pipe_rndForest1= Pipeline([('clf', RandomForestClassifier())])
distribution_rndForest1_param={"clf__max_depth": [5, None],"clf__min_samples_split": sp_randint(2,11),"clf__max_features": sp_randint(1,11),"clf__min_samples_leaf": sp_randint(1,11),"clf__bootstrap": [True, False],"clf__criterion": ["gini", "entropy"] }
rs_rndForest1 =RandomizedSearchCV(estimator= pipe_rndForest1,param_distributions = distribution_rndForest1_param, cv =kfold_rndForest1,  verbose = 2, n_jobs = -1, n_iter = 5)


## # validation + comparaison
scoring = {'acc' : 'accuracy'}
scores = dict()
test_acc_coef = 10
fit_time_coef = 3

# GAUSSIAN1
scores['gaussian1'] = {}
scores['gaussian1']['test_acc'] = []
scores['gaussian1']['fit_time'] = []

scores_gaussian1 = cross_validate(rs_gaussian1,X_train_t2, y_train, cv=5, scoring = scoring)
test_acc_gaussian1 = np.mean(scores_gaussian1['test_acc']), np.std(scores_gaussian1['test_acc'])
fit_time_gaussian1 = np.mean(scores_gaussian1['fit_time']), np.std(scores_gaussian1['fit_time'])

scores['gaussian1']['test_acc'] = scores_gaussian1['test_acc']
scores['gaussian1']['fit_time'] = scores_gaussian1['fit_time']

# GAUSSIAN2
scores['gaussian2'] = {}
scores['gaussian2']['test_acc'] = []
scores['gaussian2']['fit_time'] = []

scores_gaussian2 = cross_validate(rs_gaussian2,X_train_t3, y_train, cv=2, scoring = scoring)
test_acc_gaussian2 = np.mean(scores_gaussian2['test_acc']), np.std(scores_gaussian2['test_acc'])
fit_time_gaussian2 = np.mean(scores_gaussian2['fit_time']), np.std(scores_gaussian2['fit_time'])

scores['gaussian2']['test_acc'] = scores_gaussian2['test_acc']
scores['gaussian2']['fit_time'] = scores_gaussian2['fit_time']

# KNN1
scores['knn1'] = {}
scores['knn1']['test_acc'] = []
scores['knn1']['fit_time'] = []

scores_knn1 = cross_validate(rs_knn1,X_train_t1, y_train, cv=5, scoring = scoring)
test_acc_knn1 = np.mean(scores_knn1['test_acc']), np.std(scores_knn1['test_acc'])
fit_time_knn1 = np.mean(scores_knn1['fit_time']), np.std(scores_knn1['fit_time'])

scores['knn1']['test_acc'] = scores_knn1['test_acc']
scores['knn1']['fit_time'] = scores_knn1['fit_time']

# COMPUTE GLOBAL SCORE
models_scores = {}
models_scores['gaussian1'] = 1 * (test_acc_gaussian1 * test_acc_coef) * (fit_time_coef / (1+fit_time_gaussian1))
models_scores['gaussian2'] = 1 * (test_acc_gaussian2 * test_acc_coef) * (fit_time_coef / (1+fit_time_gaussian2))
models_scores['knn1'] = 1 * (test_acc_knn1 * test_acc_coef) * (fit_time_coef / (1+fit_time_knn1))

# WINNER MODEL
winner_model = max(models_scores.items(), key=operator.itemgetter(1))[0]
print("winner model :",winner_model)


# COMPARISON CHART
plt.scatter(scores['gaussian1']['test_acc'], scores['gaussian1']['fit_time'], marker='x', color='#cae800', label='gaussian1')
plt.scatter(scores['gaussian2']['test_acc'], scores['gaussian2']['fit_time'], marker='x', color='#bda366', label='gaussian2')
plt.scatter(scores['knn1']['test_acc'], scores['knn1']['fit_time'], marker='x', color='#5a0029', label='knn1')
plt.title('MODELS COMPARISON')
plt.xlabel('accuracy')
plt.ylabel('fit_time(sec)')
plt.legend(loc='lower right', bbox_to_anchor=(1.3,0))
plt.show()
