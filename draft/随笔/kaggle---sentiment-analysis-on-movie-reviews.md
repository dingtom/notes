[任务地址](https://www.kaggle.com/c/sentiment-analysis-on-movie-reviews/team)
代码：
```
import pandas as pd
data_train = pd.read_csv('./train.tsv', sep='\t')
data_test = pd.read_csv('./test.tsv', sep='\t')
data_train.head()
data_test.head()
print(data_train.shape)   # (156060, 4)
data_train.info()
print(data_test.shape)

train_sentences = data_train['Phrase']
test_sentences = data_test['Phrase']
sentences = pd.concat([data_train['Phrase'], data_test['Phrase']])
labels = data_train['Sentiment']
stop_words = open('./stop_words.txt', encoding='utf-8').read().splitlines()
print(sentences.shape, labels.shape, len(stop_words))

from sklearn.model_selection import train_test_split
x_train,x_test,y_train,y_test = train_test_split(train_sentences,labels)

# 用sklearn库中的CountVectorizer构建词袋模型
from sklearn.feature_extraction.text import CountVectorizer
co = CountVectorizer(
    analyzer='word',
    ngram_range=(1,4),
    stop_words=stop_words,
    max_features=150000
)
co.fit(sentences)
x_train = co.transform(x_train)
x_test = co.transform(x_test)

import warnings 
warnings.filterwarnings('ignore')

#  逻辑回归分类器
from sklearn.linear_model import LogisticRegression
lg = LogisticRegression()
lg.fit(x_train, y_train)
print(lg.score(x_test, y_test))

# 用朴素贝叶斯进行分类训练和预测
from sklearn.naive_bayes import MultinomialNB
mu = MultinomialNB()
mu.fit(x_train,y_train)
print(mu.score(x_test,y_test))

# 使用TF-IDF模型进行文本特征工程
# TF值衡量了一个词出现的次数。
# IDF值衡量了这个词是不是烂大街。如果是the、an、a等烂大街的词，IDF值就会很低。
from sklearn.model_selection import train_test_split
x_train,x_test,y_train,y_test = train_test_split(train_sentences,labels)

from sklearn.feature_extraction.text import TfidfVectorizer
tf = TfidfVectorizer(
    analyzer='word',
    ngram_range=(1,4),
    # stop_words=stop_words,
    max_features=150000
)
tf.fit(sentences)
x_train = tf.transform(x_train)
x_test = tf.transform(x_test)

#引用朴素贝叶斯进行分类训练和预测
classifier = MultinomialNB()
classifier.fit(x_train,y_train)
print(classifier.score(x_test,y_test))
# sklearn默认的逻辑回归模型
lg1 = LogisticRegression()
lg1.fit(x_train,y_train)
print(lg1.score(x_test,y_test))


# C：正则化系数，C越小，正则化效果越强
# dual：求解原问题的对偶问题
lg2 = LogisticRegression(C=3, dual=True)
lg2.fit(x_train,y_train)
print(lg2.score(x_test,y_test))

# 可以用sklearn提供的强大的网格搜索功能进行超参数的批量试验。
# 搜索空间：C从1到9。对每一个C，都分别尝试dual为True和False的两种参数。
# 最后从所有参数中挑出能够使模型在验证集上预测准确率最高的。
from sklearn.model_selection import GridSearchCV
param_grid = {'C':range(1,10),
             'dual':[True,False]
              }
lgGS = LogisticRegression()
grid = GridSearchCV(lgGS, param_grid=param_grid,cv=3,n_jobs=-1)
grid.fit(x_train,y_train)
print(grid.best_params_)
lg_final = grid.best_estimator_
print(lg_final.score(x_test,y_test))

# 使用TF-IDF对测试集中的文本进行特征工程
test_X = tf.transform(data_test['Phrase'])
# 对测试集中的文本，使用lg_final逻辑回归分类器进行预测
predictions = lg_final.predict(test_X)
print(predictions)
# 将预测结果加在测试集中
data_test.loc[:,'Sentiment'] = predictions
# 按Kaggle比赛官网上的要求整理成这样的格式
final_data = data_test.loc[:,['PhraseId','Sentiment']]
# 保存为.csv文件，即为最终结果
final_data.to_csv('final_data.csv',index=None)
```
