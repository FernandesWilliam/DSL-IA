#Import DataSet
import pandas as pd
try:
	dataset = pd.read_csv("../input/digit-recognizer/train.csv")
except FileNotFoundError:
	print("The path of the dataset is invalid")
#Preprocessing Phase :
#preprocess notNull :
dataset.dropna()
#preprocess removeOutliers :
Q1=dataset.quantile(0.01)
Q3=dataset.quantile(0.8)
IQR = Q3 - Q1
dataset[~((dataset < (Q1 - 1.5 * IQR)) | (dataset > (Q3 + 1.5 * IQR))).any(axis = 1)]
#Divide Dataset
X,y = dataset.drop(['label'], axis = 1), dataset['label']
X_train, X_test, y_train, y_test=train_test_split(X, y,test_size=70,random_state=45)
# MinMax TRANSFORMATION
minMax02 = MinMaxScaler((feature_range=(0,4),clip=false,copy=true)
minMax01 = MinMaxScaler((feature_range=(0,1),clip=false,copy=true)
# PCA TRANSFORMATION
pca55 = PCA(n_components=0.5)
pca51 = PCA(n_components=0.3)
