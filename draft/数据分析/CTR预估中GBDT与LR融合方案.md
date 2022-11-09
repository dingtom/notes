GBDT+LR 使用最广泛的场景是 CTR 点击率预估，即预测当给用户推送的广告会不会被用户点击。

训练样本一般是上亿级别，样本量大，模型常采用速度较快的 LR。但 LR 是线性模型，学习能力有限。GBDT 算法的特点正好可以用来发掘有区分度的特征、特征组合，减少特征工程中人力成本。

GBDT前面的树，特征分裂主要体现对多数样本有区分度的特征；后面的树，主要体现的是经过前N颗树，残差仍然较大的少数样本。优先选用在整体上有区分度的特征，再选用针对少数样本有区分度的特征，思路更加合理.


![](https://upload-images.jianshu.io/upload_images/18339009-47b90957b0f4fb38.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

举个例子，下图是一个 GBDT+LR 模型结构，设 GBDT 有两个弱分类器，分别以蓝色和红色部分表示，其中蓝色弱分类器的叶子结点个数为 3，红色弱分类器的叶子结点个数为 2，并且蓝色弱分类器中对 0-1 的预测结果落到了第二个叶子结点上，红色弱分类器中对 0-1 的预测结果也落到了第二个叶子结点上。那么我们就记蓝色弱分类器的预测结果为[0 1 0]，红色弱分类器的预测结果为[0 1]，**综合起来看，GBDT 的输出为这些弱分类器的组合[0 1 0 0 1] ，或者一个稀疏向量（数组）。**

这里的思想与 One-hot 独热编码类似，事实上，在用 GBDT 构造新的训练数据时，采用的也正是 One-hot 方法。并且**由于每一弱分类器有且只有一个叶子节点输出预测结果，所以在一个具有 n 个弱分类器、共计 m 个叶子结点的 GBDT 中，每一条训练数据都会被转换为 1\*m 维稀疏向量，且有 n 个元素为 1，其余 m-n 个元素全为 0**
新的训练数据构造完成后，下一步就要与原始的训练数据中的 label(输出)数据一并输入到 Logistic Regression 分类器中进行最终分类器的训练。思考一下，**在对原始数据进行 GBDT 提取为新的数据这一操作之后，数据不仅变得稀疏，而且由于弱分类器个数，叶子结点个数的影响，可能会导致新的训练数据特征维度过大的问题，因此，在 Logistic Regression 这一层中，可使用正则化来减少过拟合的风险，在 Facebook 的论文中采用的是 L1 正则化。**
![](https://upload-images.jianshu.io/upload_images/18339009-8e437a44fb78d09b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-c14f5dc098619516.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Logistic Regression 是一个线性分类器，也就是说会忽略掉特征与特征之间的关联信息，那么是否可以采用构建新的交叉特征这一特征组合方式从而提高模型的效果？
其次，GBDT 很有可能构造出的新训练数据是高维的稀疏矩阵，而 Logistic Regression 使用高维稀疏矩阵进行训练，会直接导致计算量过大，特征权值更新缓慢的问题。

使用 FM 算法代替 LR，这样就解决了 Logistic Regression 的模型表达效果及高维稀疏矩阵的训练开销较大的问题。然而，这样就意味着可以高枕无忧了吗？当然不是，因为采用 FM 对本来已经是高维稀疏矩阵做完特征交叉后，新的特征维度会更加多，并且由于元素非 0 即 1，新的特征数据可能也会更加稀疏，那么怎么办？

所以，我们需要再次回到 GBDT 构造新训练数据这里。**当 GBDT 构造完新的训练样本后，我们要做的是对每一个特征做与输出之间的特征重要度评估并筛选出重要程度较高的部分特征，**这样，GBDT 构造的高维的稀疏矩阵就会减少一部分特征，也就是说得到的稀疏矩阵不再那么高维了。**之后，对这些筛选后得到的重要度较高的特征再做 FM 算法构造交叉项，进而引入非线性特征**，继而完成最终分类器的训练数据的构造及模型的训练。(gbdt+FFM后面有代码）

# GBDT+LR

```
# encoding=utf-8
import numpy as np
import pandas as pd
from sklearn.datasets import load_breast_cancer
from sklearn.utils import shuffle

from sklearn.preprocessing import OneHotEncoder, StandardScaler
from sklearn.feature_selection import SelectFromModel

from sklearn.model_selection import train_test_split, StratifiedKFold, GridSearchCV

from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import GradientBoostingClassifier

from sklearn.metrics import make_scorer, roc_curve, accuracy_score, f1_score, confusion_matrix, roc_auc_score

data = load_breast_cancer(return_X_y=True) # data[0]数据 data[1]标签

data_p = []  # positive data
data_n = []  # negative data

for i in range(len(data[1])):
    if data[1][i] == 1:
        data_p.append(data[0][i])
    else:
        data_n.append(data[0][i])

data_p = pd.DataFrame(np.array(data_p))
# print(data_p.values.shape)  # (357, 30)

data_n = np.array(data_n)
data_p = data_p.sample(data_n.shape[0], axis=0)
# print(data_p.values.shape)  # (212, 30)
data_comb = np.vstack((data_p, data_n))

label_p = [1] * data_p.shape[0]
label_n = [0] * data_n.shape[0]
label_p.extend(label_n)
label = np.array(label_p)

x, y = shuffle(data_comb, label, random_state=10)
# print(np.unique(y))  # [0 1]
x = StandardScaler().fit_transform(x)

# print(label.shape)  # (424,)
# print(data_comb.shape)  # (424, 30)
# -------------------------------网格搜索最佳参数
# parameter for LogisticRegression
# parameter = {'C': [1, 10, 100, 1000],'solver':['liblinear','newton-cg','lbfgs','sag','saga']}
# parameter for SVM
# parameter = [{'kernel': ['rbf'], 'gamma': [1e-3, 1e-4],'C': [1, 10, 100, 1000]},{'kernel': ['linear'], 'C': [1, 10, 100, 1000]},{'kernel':['poly'], 'gamma': [1e-3, 1e-4],'C': [1, 10, 100, 1000]},{'kernel':['sigmoid'], 'gamma': [1e-3, 1e-4],'C': [1, 10, 100, 1000]}]
# parameter for DecisonTree
# parameter = {'criterion':['gini','entropy'],'max_features':['sqrt','log2']}
# parameter for RandomForest
# parameter = {'n_estimators':range(5,100,5),'criterion':['gini','entropy'],'max_depth':[1,2,3,4,5,6,7,8,9,10],'max_features':['sqrt','log2']}
# parameter for xgboost
# parameter = {'learning_rate': np.linspace(0.01,0.3,num=30),'n_estimators':range(5,300,5),'max_depth': [1,2,3, 4, 5, 6, 7, 8, 9, 10]}
# parameter for adaboost
# parameter = {'n_estimators': range(5,300,5),"learning_rate":[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]}
# parameter for bagging
# parameter = {'n_estimators': range(5,100,5)}
# parameter for MLP
# parameter = {
#     'hidden_layer_sizes': [(50,50,50), (50,100,50), (100,)],
#     'activation': ['tanh', 'relu'],
#     'solver': ['sgd', 'adam'],
#     'alpha': [0.0001, 0.05],
#     'learning_rate': ['constant','adaptive']}

# clf = LogisticRegression(max_iter=1000,solver='liblinear', C=1)
# clf = SVC(C=1, kernel='rbf', probability=True)
# clf = DecisionTreeClassifier(criterion='entropy',max_features='log2')
# clf = RandomForestClassifier()
# clf = xgb.XGBClassifier()
# clf = AdaBoostClassifier()
# clf = BaggingClassifier(LogisticRegression())
# clf = GradientBoostingClassifier(learning_rate=0.268,  max_depth=3,n_estimators=85)
# clf = MLPClassifier(activation='relu', alpha=0.05, hidden_layer_sizes=(50,50), learning_rate='constant',solver='adam')

# # parameter for GBDT
# parameter = {'learning_rate': np.linspace(0.10, 0.3, num=20),
#              'n_estimators': range(10, 100, 5),
#              'max_depth': [3, 4, 5, 6, 7, 8]}

# # make_scorer生成scoring对象。该函数将metrics转换成在模型评估中可调用的对象。第一个典型的用例是，将一个库中已经存在的metrics函数进行包装，使用定制参数
# scoring = {'roc_auc': 'roc_auc', 'accuracy': make_scorer(accuracy_score),
#            'f1': make_scorer(f1_score)}
# clf = GradientBoostingClassifier()
# model = GridSearchCV(clf, parameter, cv=5, scoring=scoring,
#                      n_jobs=-1, verbose=2, return_train_score=False, refit='roc_auc')

# his = model.fit(x, y)
# print("best estimator : ", his.best_estimator_)
# print("best score : ", his.best_score_)
# print("best parameters : ", his.best_params_)
lr, md, ne =  his.best_params_.values()

# ----------------------------------特征选择
# lsvc = LinearSVC(C=0.01, penalty="l1", dual=False).fit(x, y)
# lsvc = LogisticRegression().fit(x,y)
lsvc = GradientBoostingClassifier(random_state=0, learning_rate=lr, max_depth=md, n_estimators=ne).fit(x, y)
model = SelectFromModel(lsvc, threshold=0.0001, prefit=True)
# print(lsvc.feature_importances_, lsvc.coef_, model.threshold_)
x_new = model.transform(x)
# print(x_new.shape)  # (424, 27)

# ------------------------------GBDT+LR特征融合
# 样本量大，模型常采用速度较快的LR。但LR是线性模型，学习能力有限，此时特征工程尤其重要。
# 现有的特征工程实验，主要集中在寻找到有区分度的特征、特征组合，折腾一圈未必会带来效果提升。
# GBDT算法的特点正好可以用来发掘有区分度的特征、特征组合，减少特征工程中人力成本

skf = StratifiedKFold(n_splits=5)  # n_splits=5 循环5次 训练：测试4：1
AUC = []
Accuracy = []
Sensitivity = []
Specificity = []
F1 = []
for train_index, test_index in skf.split(x_new, y):
    # print(len(train_index), len(test_index))  # train 339       test 85
    X_train, X_test = x_new[train_index], x_new[test_index]
    y_train, y_test = y[train_index], y[test_index]
    X_train, X_train_lr, y_train, y_train_lr = train_test_split(
        X_train, y_train, test_size=0.5, random_state=10)  # 原特征
    # print(X_train.shape, X_train_lr.shape)  # X_train (169, 25)  X_train_lr  (170, 24)
    # X_train数据学习提取特征，X_train_lr数据用于分类
    grd = GradientBoostingClassifier(learning_rate=lr, max_depth=md, n_estimators=ne)
    grd.fit(X_train, y_train)

    grd_enc = OneHotEncoder(categories='auto')
    # print(grd.apply(X_train).shape)  # 梯度提升提取X_train的特征(169, 85, 1)
    grd_enc.fit(grd.apply(X_train)[:, :, 0])  # apply返回训练数据 X_train 在训练好的模型里每棵树中所处的叶子节点的位置（索引）
    # 使用训练好的GBDT模型构建特征，然后将特征经过one-hot编码作为新的特征输入到LR模型训练。
    
    onehot_train_lr = grd_enc.transform(grd.apply(X_train_lr)[:, :, 0]).toarray()
    #　print(onehot_train_lr.shape)  # 梯度提升提取X_train_lr的特征one-hot(170, 245)
    total_train_lr = np.hstack((onehot_train_lr, X_train_lr))
    # print(total_train_lr.shape)  # total_train_lr总特征 (170, 269) 梯度提升提取的特征one-hot+原特征

    onehot_test = grd_enc.transform(grd.apply(X_test)[:, :, 0]).toarray()
    total_test = np.hstack((onehot_test, X_test))

    grd_lm = LogisticRegression(solver='liblinear', max_iter=1000)
    grd_lm.fit(total_train_lr, y_train_lr)

    # predict是训练后返回预测结果，是标签值。
    # predict_proba返回的是一个n行k(类别数）列的数组， 第i行第j列上的数值
    # 模型预测第i个预测样本为j标签的概率，并且每一行的概率和为1。
    y_pred_grd_lm_proba = grd_lm.predict_proba(total_test)
    y_pred_grd_lm = grd_lm.predict(total_test)
    # print(y_pred_grd_lm_proba.shape,y_pred_grd_lm.shape)  # (85, 2) (85,)

    fpr_grd_lm, tpr_grd_lm, _ = roc_curve(y_test, y_pred_grd_lm)
    confusion = confusion_matrix(y_test, y_pred_grd_lm)
    # print(confusion)
    # [[43  0]
    # [ 0 42]]
    tn, fp, fn, tp = confusion.ravel()
    # print(tn, fp, fn, tp)  # 43 0 0 42
    F1.append(f1_score(y_test, y_pred_grd_lm))
    AUC.append(roc_auc_score(y_test, y_pred_grd_lm_proba[:, 1]))
    Accuracy.append(accuracy_score(y_test, y_pred_grd_lm))
    Sensitivity.append(tp / (tp + fn))
    Specificity.append(tn / (tn + fp))

AUC.append(np.mean(AUC))
F1.append(np.mean(F1))
Accuracy.append(np.mean(Accuracy))
Sensitivity.append(np.mean(Sensitivity))
Specificity.append(np.mean(Specificity))

print("Sensitivity : ", Sensitivity)
print("Specificity : ", Specificity)
print("Accuracy : ", Accuracy)
print("F1 Score : ", F1)
print("AUC : ", AUC)
```
# gbdt+FFM
[https://github.com/wangru8080/gbdt-lr](https://github.com/wangru8080/gbdt-lr)

```
# encoding=utf-8
import numpy as np
import pandas as pd
from sklearn.datasets import load_breast_cancer
from sklearn.utils import shuffle

from sklearn.preprocessing import OneHotEncoder, StandardScaler
from sklearn.feature_selection import SelectFromModel

from sklearn.model_selection import train_test_split, StratifiedKFold, GridSearchCV

from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import GradientBoostingClassifier

from sklearn.metrics import make_scorer, roc_curve, accuracy_score, f1_score, confusion_matrix, roc_auc_score

# data = load_breast_cancer(return_X_y=True) # data[0]数据 data[1]标签
# 
# data_p = []  # positive data
# data_n = []  # negative data
# 
# for i in range(len(data[1])):
#     if data[1][i] == 1:
#         data_p.append(data[0][i])
#     else:
#         data_n.append(data[0][i])
# 
# data_p = pd.DataFrame(np.array(data_p))
# print(data_p.values.shape)  # (357, 30)
# 
# data_n = np.array(data_n)
# data_p = data_p.sample(data_n.shape[0], axis=0)
# print(data_p.values.shape)  # (212, 30)
# data_comb = np.vstack((data_p, data_n))
# 
# label_p = [1] * data_p.shape[0]
# label_n = [0] * data_n.shape[0]
# label_p.extend(label_n)
# label = np.array(label_p)
# print(label.shape)  # (424,)
# print(data_comb.shape)  # (424, 30)

import pandas as pd
import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
import lightgbm as lgb
from sklearn.preprocessing import MinMaxScaler, OneHotEncoder, LabelEncoder
from sklearn.metrics import log_loss
import warnings

warnings.filterwarnings('ignore')
import gc
from scipy import sparse


def preProcess():
    path = r'E:\Downloads\Compressed\gbdt-lr-master\data\\'
    print('读取数据...')
    df_train = pd.read_csv(path + 'train.csv')
    df_test = pd.read_csv(path + 'test.csv')
    print('读取结束')
    df_train.drop(['Id'], axis=1, inplace=True)
    df_test.drop(['Id'], axis=1, inplace=True)

    df_test['Label'] = -1

    data = pd.concat([df_train, df_test])
    data = data.fillna(-1)
    data.to_csv('data/data.csv', index=False)
    return data


def lr_predict(data, category_feature, continuous_feature):  # 0.47181
    # 连续特征归一化
    print('开始归一化...')
    scaler = MinMaxScaler()
    for col in continuous_feature:
        data[col] = scaler.fit_transform(data[col].values.reshape(-1, 1))
    print('归一化结束')

    # 离散特征one-hot编码
    print('开始one-hot...')
    for col in category_feature:
        onehot_feats = pd.get_dummies(data[col], prefix=col)
        data.drop([col], axis=1, inplace=True)
        data = pd.concat([data, onehot_feats], axis=1)
    print('one-hot结束')

    train = data[data['Label'] != -1]
    target = train.pop('Label')
    test = data[data['Label'] == -1]
    test.drop(['Label'], axis=1, inplace=True)

    # 划分数据集
    print('划分数据集...')
    x_train, x_val, y_train, y_val = train_test_split(train, target, test_size=0.2, random_state=2018)
    print('开始训练...')
    lr = LogisticRegression()
    lr.fit(x_train, y_train)
    tr_logloss = log_loss(y_train, lr.predict_proba(x_train)[:, 1])
    print('tr-logloss: ', tr_logloss)
    val_logloss = log_loss(y_val, lr.predict_proba(x_val)[:, 1])
    print('val-logloss: ', val_logloss)
    print('开始预测...')
    y_pred = lr.predict_proba(test)[:, 1]
    print('写入结果...')
    res = pd.read_csv('data/test.csv')
    submission = pd.DataFrame({'Id': res['Id'], 'Label': y_pred})
    submission.to_csv('submission/submission_lr_trlogloss_%s_vallogloss_%s.csv' % (tr_logloss, val_logloss),
                      index=False)
    print('结束')


def gbdt_predict(data, category_feature, continuous_feature):  # 0.44548
    # 离散特征one-hot编码
    print('开始one-hot...')
    for col in category_feature:
        onehot_feats = pd.get_dummies(data[col], prefix=col)
        data.drop([col], axis=1, inplace=True)
        data = pd.concat([data, onehot_feats], axis=1)
    print('one-hot结束')

    train = data[data['Label'] != -1]
    target = train.pop('Label')
    test = data[data['Label'] == -1]
    test.drop(['Label'], axis=1, inplace=True)

    # 划分数据集
    print('划分数据集...')
    x_train, x_val, y_train, y_val = train_test_split(train, target, test_size=0.2, random_state=2018)

    print('开始训练..')
    gbm = lgb.LGBMClassifier(objective='binary',
                             subsample=0.8,
                             min_child_weight=0.5,
                             colsample_bytree=0.7,
                             num_leaves=100,
                             max_depth=12,
                             learning_rate=0.01,
                             n_estimators=10000,
                             )

    gbm.fit(x_train, y_train,
            eval_set=[(x_train, y_train), (x_val, y_val)],
            eval_names=['train', 'val'],
            eval_metric='binary_logloss',
            early_stopping_rounds=100,
            )
    tr_logloss = log_loss(y_train, gbm.predict_proba(x_train)[:, 1])
    val_logloss = log_loss(y_val, gbm.predict_proba(x_val)[:, 1])
    y_pred = gbm.predict_proba(test)[:, 1]
    print('写入结果...')
    res = pd.read_csv('data/test.csv')
    submission = pd.DataFrame({'Id': res['Id'], 'Label': y_pred})
    submission.to_csv('submission/submission_gbdt_trlogloss_%s_vallogloss_%s.csv' % (tr_logloss, val_logloss),
                      index=False)
    print('结束')


def gbdt_lr_predict(data, category_feature, continuous_feature):  # 0.43616
    # 离散特征one-hot编码
    print('开始one-hot...')
    for col in category_feature:
        onehot_feats = pd.get_dummies(data[col], prefix=col)
        data.drop([col], axis=1, inplace=True)
        data = pd.concat([data, onehot_feats], axis=1)
    print('one-hot结束')

    train = data[data['Label'] != -1]
    target = train.pop('Label')
    test = data[data['Label'] == -1]
    test.drop(['Label'], axis=1, inplace=True)

    # 划分数据集
    print('划分数据集...')
    x_train, x_val, y_train, y_val = train_test_split(train, target, test_size=0.2, random_state=2018)

    print('开始训练gbdt..')
    gbm = lgb.LGBMRegressor(objective='binary',
                            subsample=0.8,
                            min_child_weight=0.5,
                            colsample_bytree=0.7,
                            num_leaves=100,
                            max_depth=12,
                            learning_rate=0.05,
                            n_estimators=10,
                            )

    gbm.fit(x_train, y_train,
            eval_set=[(x_train, y_train), (x_val, y_val)],
            eval_names=['train', 'val'],
            eval_metric='binary_logloss',
            # early_stopping_rounds = 100,
            )
    model = gbm.booster_
    print('训练得到叶子数')
    gbdt_feats_train = model.predict(train, pred_leaf=True)
    gbdt_feats_test = model.predict(test, pred_leaf=True)
    gbdt_feats_name = ['gbdt_leaf_' + str(i) for i in range(gbdt_feats_train.shape[1])]
    df_train_gbdt_feats = pd.DataFrame(gbdt_feats_train, columns=gbdt_feats_name)
    df_test_gbdt_feats = pd.DataFrame(gbdt_feats_test, columns=gbdt_feats_name)

    print('构造新的数据集...')
    train = pd.concat([train, df_train_gbdt_feats], axis=1)
    test = pd.concat([test, df_test_gbdt_feats], axis=1)
    train_len = train.shape[0]
    data = pd.concat([train, test])
    del train
    del test
    gc.collect()

    # # 连续特征归一化
    # print('开始归一化...')
    # scaler = MinMaxScaler()
    # for col in continuous_feature:
    #     data[col] = scaler.fit_transform(data[col].values.reshape(-1, 1))
    # print('归一化结束')

    # 叶子数one-hot
    print('开始one-hot...')
    for col in gbdt_feats_name:
        print('this is feature:', col)
        onehot_feats = pd.get_dummies(data[col], prefix=col)
        data.drop([col], axis=1, inplace=True)
        data = pd.concat([data, onehot_feats], axis=1)
    print('one-hot结束')

    train = data[: train_len]
    test = data[train_len:]
    del data
    gc.collect()

    x_train, x_val, y_train, y_val = train_test_split(train, target, test_size=0.3, random_state=2018)
    # lr
    print('开始训练lr..')
    lr = LogisticRegression()
    lr.fit(x_train, y_train)
    tr_logloss = log_loss(y_train, lr.predict_proba(x_train)[:, 1])
    print('tr-logloss: ', tr_logloss)
    val_logloss = log_loss(y_val, lr.predict_proba(x_val)[:, 1])
    print('val-logloss: ', val_logloss)
    print('开始预测...')
    y_pred = lr.predict_proba(test)[:, 1]
    print('写入结果...')
    res = pd.read_csv('data/test.csv')
    submission = pd.DataFrame({'Id': res['Id'], 'Label': y_pred})
    submission.to_csv('submission/submission_gbdt+lr_trlogloss_%s_vallogloss_%s.csv' % (tr_logloss, val_logloss),
                      index=False)
    print('结束')


def gbdt_ffm_predict(data, category_feature, continuous_feature):
    # 离散特征one-hot编码
    print('开始one-hot...')
    for col in category_feature:
        onehot_feats = pd.get_dummies(data[col], prefix=col)
        data = pd.concat([data, onehot_feats], axis=1)
    print('one-hot结束')

    feats = [col for col in data if col not in category_feature]  # onehot_feats + continuous_feature
    tmp = data[feats]
    train = tmp[tmp['Label'] != -1]
    target = train.pop('Label')
    test = tmp[tmp['Label'] == -1]
    test.drop(['Label'], axis=1, inplace=True)

    # 划分数据集
    print('划分数据集...')
    x_train, x_val, y_train, y_val = train_test_split(train, target, test_size=0.2, random_state=2018)

    print('开始训练gbdt..')
    gbm = lgb.LGBMRegressor(objective='binary',
                            subsample=0.8,
                            min_child_weight=0.5,
                            colsample_bytree=0.7,
                            num_leaves=100,
                            max_depth=12,
                            learning_rate=0.05,
                            n_estimators=10,
                            )

    gbm.fit(x_train, y_train,
            eval_set=[(x_train, y_train), (x_val, y_val)],
            eval_names=['train', 'val'],
            eval_metric='binary_logloss',
            # early_stopping_rounds = 100,
            )
    model = gbm.booster_
    print('训练得到叶子数')
    gbdt_feats_train = model.predict(train, pred_leaf=True)
    gbdt_feats_test = model.predict(test, pred_leaf=True)
    gbdt_feats_name = ['gbdt_leaf_' + str(i) for i in range(gbdt_feats_train.shape[1])]
    df_train_gbdt_feats = pd.DataFrame(gbdt_feats_train, columns=gbdt_feats_name)
    df_test_gbdt_feats = pd.DataFrame(gbdt_feats_test, columns=gbdt_feats_name)

    print('构造新的数据集...')
    tmp = data[category_feature + continuous_feature + ['Label']]
    train = tmp[tmp['Label'] != -1]
    test = tmp[tmp['Label'] == -1]
    train = pd.concat([train, df_train_gbdt_feats], axis=1)
    test = pd.concat([test, df_test_gbdt_feats], axis=1)
    data = pd.concat([train, test])
    del train
    del test
    gc.collect()

    # 连续特征归一化
    print('开始归一化...')
    scaler = MinMaxScaler()
    for col in continuous_feature:
        data[col] = scaler.fit_transform(data[col].values.reshape(-1, 1))
    print('归一化结束')

    data.to_csv('data/data.csv', index=False)
    return category_feature + gbdt_feats_name


def FFMFormat(df, label, path, train_len, category_feature=[], continuous_feature=[]):
    index = df.shape[0]
    train = open(path + 'train.ffm', 'w')
    test = open(path + 'test.ffm', 'w')
    feature_index = 0
    feat_index = {}
    for i in range(index):
        feats = []
        field_index = 0
        for j, feat in enumerate(category_feature):
            t = feat + '_' + str(df[feat][i])
            if t not in feat_index.keys():
                feat_index[t] = feature_index
                feature_index = feature_index + 1
            feats.append('%s:%s:%s' % (field_index, feat_index[t], 1))
            field_index = field_index + 1

        for j, feat in enumerate(continuous_feature):
            feats.append('%s:%s:%s' % (field_index, feature_index, df[feat][i]))
            feature_index = feature_index + 1
            field_index = field_index + 1

        print('%s %s' % (df[label][i], ' '.join(feats)))

        if i < train_len:
            train.write('%s %s\n' % (df[label][i], ' '.join(feats)))
        else:
            test.write('%s\n' % (' '.join(feats)))
    train.close()
    test.close()


if __name__ == '__main__':
    data = preProcess()
    continuous_feature = ['I'] * 13
    continuous_feature = [col + str(i + 1) for i, col in enumerate(continuous_feature)]
    category_feature = ['C'] * 26
    category_feature = [col + str(i + 1) for i, col in enumerate(category_feature)]
    # lr_predict(data, category_feature, continuous_feature)
    # gbdt_predict(data, category_feature, continuous_feature)
    # gbdt_lr_predict(data, category_feature, continuous_feature)
    category_feature = gbdt_ffm_predict(data, category_feature, continuous_feature)

    data = pd.read_csv('data/data.csv')
    df_train = pd.read_csv('data/train.csv')
    FFMFormat(data, 'Label', 'data/', df_train.shape[0], category_feature, continuous_feature)
```
