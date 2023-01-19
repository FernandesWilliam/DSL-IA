package kernel.imports;

import kernel.Generator;
import kernel.StringUtils;

public class ImportsGenerator implements Generator {
    @Override
    public Object generate(Object maps) {
        return new StringBuilder()
                .append("%pylab inline").append(StringUtils.lineFeed())
                .append("#%pylab").append(StringUtils.lineFeed())
                .append("import numpy as np").append(StringUtils.lineFeed())
                .append("from sklearn import datasets, svm, metrics").append(StringUtils.lineFeed())
                .append("import pandas as pd").append(StringUtils.lineFeed())
                .append("from matplotlib import pyplot as plt").append(StringUtils.lineFeed())
                .append("import seaborn as sns").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import train_test_split").append(StringUtils.lineFeed())
                .append("from matplotlib import pyplot as plt").append(StringUtils.lineFeed())
                .append("import seaborn as sns").append(StringUtils.lineFeed())
                .append("from sklearn.neighbors import KNeighborsClassifier").append(StringUtils.lineFeed())
                .append("from sklearn.naive_bayes import GaussianNB").append(StringUtils.lineFeed())
                .append("from sklearn.svm import SVC").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import KFold").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import cross_val_score").append(StringUtils.lineFeed())
                .append("from sklearn import metrics").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import train_test_split").append(StringUtils.lineFeed())
                .append("from sklearn import preprocessing").append(StringUtils.lineFeed())
                .append("from sklearn.pipeline import Pipeline").append(StringUtils.lineFeed())
                .append("from sklearn.preprocessing import StandardScaler").append(StringUtils.lineFeed())
                .append("from sklearn.preprocessing import MinMaxScaler").append(StringUtils.lineFeed())
                .append("from sklearn.decomposition import PCA").append(StringUtils.lineFeed())
                .append("from sklearn.metrics import confusion_matrix, recall_score, accuracy_score,precision_score, f1_score, roc_curve, auc, make_scorer,roc_auc_score ").append(StringUtils.lineFeed())
                .append("from sklearn.metrics import classification_report").append(StringUtils.lineFeed())
                .append("from sklearn.tree import DecisionTreeClassifier").append(StringUtils.lineFeed())
                .append("from sklearn.ensemble import RandomForestClassifier").append(StringUtils.lineFeed())
                .append("from scipy import stats").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import RandomizedSearchCV").append(StringUtils.lineFeed())
                .append("from scipy.stats import randint as sp_randint").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import cross_validate").append(StringUtils.lineFeed())
                .append("from sklearn.model_selection import StratifiedKFold").append(StringUtils.lineFeed())
                .append("from sklearn.ensemble import AdaBoostClassifier").append(StringUtils.lineFeed())
                .append("from sklearn.gaussian_process import GaussianProcessClassifier").append(StringUtils.lineFeed())
                .append("from sklearn.gaussian_process.kernels import RBF").append(StringUtils.lineFeed())
                .append("from sklearn.naive_bayes import GaussianNB").append(StringUtils.lineFeed())
                .append("import operator").append(StringUtils.lineFeed());

    }
}
