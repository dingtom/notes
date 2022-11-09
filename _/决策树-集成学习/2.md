- [ 思想](#head1)
	- [ 随机森林如何处理缺失值？](#head2)
	- [ 什么是OOB？随机森林中OOB是如何计算的，它有什么优缺点？](#head3)
- [ 优缺点](#head4)
	- [ 优点](#head5)
	- [ 缺点：](#head6)
- [ 随机森林分类效果的影响因素](#head7)
- [ sklearn](#head8)
	- [ 导入数据集](#head9)
	- [ 将数据集拆分成训练集和测试集](#head10)
	- [ 特征缩放](#head11)
		- [ 调试训练集的随机森林](#head12)
	- [ 预测测试集结果](#head13)
	- [ 生成混淆矩阵，也称作误差矩阵](#head14)
Random Forest（随机森林），用**随机的方式建立一个森林**。RF 算法由很多决策树组成，每一棵决策树之间没有关联。建立完森林后，当有新样本进入时，每棵决策树都会**分别进行预测，然后基于投票法**给出分类结果。
>应用场景:
>1. 数据维度相对低（几十维），同时对准确性有较高要求时。
>2. 因为不需要很多参数调整就可以达到不错的效果，基本上不知道用什么方法的时候都可以先试一下随机森林。

# <span id="head1"> 思想</span>

Random Forest（随机森林）是**Bagging 的扩展变体**，它在以决策树为基学习器构建 Bagging 集成的基础上，进一步在决策树的训练过程中引入了**随机特征选择**，因此可以概括 RF 包括四个部分：

1.  **随机选择样本**（放回抽样）；
2.  **随机选择特征**；
3.  构建决策树；
4.  随机森林投票（平均）。

随机选择样本和 Bagging 相同，采用的是 **Bootstrap 自助采样法**；
随机选择特征是指在**每个节点在分裂过程中都是随机选择特征的**（区别与每棵树随机选择一批特征）。

这种随机性导致随机森林的**偏差会有稍微的增加**（相比于单棵不随机树），但是由于随机森林的“平均”特性，会使得它的**方差减小**，而且方差的减小补偿了偏差的增大，因此总体而言是更好的模型。

随机采样由于**引入了两种采样方法保证了随机性，所以每棵树都是最大可能的进行生长就算不剪枝也不会出现过拟合**。



## <span id="head2"> 随机森林如何处理缺失值？</span>

根据随机森林创建和训练的特点，随机森林对缺失值的处理还是比较特殊的。

- 首先，给缺失值预设一些估计值，比如数值型特征，选择其余数据的中位数或众数作为当前的估计值
- 然后，根据估计的数值，建立随机森林，把所有的数据放进随机森林里面跑一遍。记录每一组数据在决策树中一步一步分类的路径.
- 判断哪组数据和缺失数据路径最相似，引入一个相似度矩阵，来记录数据之间的相似度，比如有N组数据，相似度矩阵大小就是N*N
- 如果缺失值是类别变量，通过权重投票得到新估计值，如果是数值型变量，通过加权平均得到新的估计值，如此迭代，直到得到稳定的估计值。

其实，该缺失值填补过程类似于推荐系统中采用协同过滤进行评分预测，先计算缺失特征与其他特征的相似度，再加权得到缺失值的估计，而随机森林中计算相似度的方法（数据在决策树中一步一步分类的路径）乃其独特之处。

## <span id="head3"> 什么是OOB？随机森林中OOB是如何计算的，它有什么优缺点？</span>

**OOB**：

上面我们提到，构建随机森林的关键问题就是如何选择最优的m个特征子集，要解决这个问题主要依据计算袋外错误率oob error（out-of-bag error）。

bagging方法中Bootstrap每次约有1/3的样本不会出现在Bootstrap所采集的样本集合中，当然也就没有参加决策树的建立，把这1/3的数据称为**袋外数据oob（out of bag）**,它可以用于取代测试集误差估计方法。

**袋外数据(oob)误差的计算方法如下：**

- 对于已经生成的随机森林,用袋外数据测试其性能,假设袋外数据总数为O,用这O个袋外数据作为输入,带进之前已经生成的随机森林分类器,分类器会给出O个数据相应的分类
- 因为这O条数据的类型是已知的,则用正确的分类与随机森林分类器的结果进行比较,统计随机森林分类器分类错误的数目,设为X,则袋外数据误差大小=X/O

**优缺点**：

这已经经过证明是无偏估计的,所以在随机森林算法中不需要再进行交叉验证或者单独的测试集来获取测试集误差的无偏估计。 



# <span id="head4"> 优缺点</span>

## <span id="head5"> 优点</span>

1.  在数据集上表现良好，相对于其他算法有较大的优势，由于采用了随机采样，训练出的模型的**方差小，泛化能力强**
2.  易于**并行化**，在**大数据集上有很大的优势**；
3.  能够**处理高维度数据，不用做特征选择**(由于可以随机选择决策树节点划分特征)
4. 在训练后，可以**给出各个特征对于输出的重要性**
5. 相对于Boosting系列的Adaboost和GBDT， **RF实现比较简单**。
6. **对部分特征缺失不敏感。**
7.  在训练过程中，能够检测到feature间的互相影响。
8.  对于不平衡的数据集来说，它可以平衡误差。

## <span id="head6"> 缺点：</span>
在某些噪音比较大的样本集上，**RF模型容易陷入过拟合。**
**取值划分比较多的特征容易对RF的决策产生更大的影响**，从而影响拟合的模型的效果。



# <span id="head7"> 随机森林分类效果的影响因素</span>

- 森林中任意两棵树的相关性：相关性越大，错误率越大；
- 森林中每棵树的分类能力：每棵树的分类能力越强，整个森林的错误率越低。

**减小特征选择个数m，树的相关性和分类能力也会相应的降低；增大m，两者也会随之增大**。所以关键问题是如何选择最优的m（或者是范围），这也是随机森林唯一的一个参数。

# <span id="head8"> sklearn</span>
```sklearn.ensemble.RandomForestClassifier(n_estimators=100, criterion='gini', max_depth=None, min_samples_split=2, min_samples_leaf=1, min_weight_fraction_leaf=0.0, max_features='auto', max_leaf_nodes=None, min_impurity_decrease=0.0, min_impurity_split=None, bootstrap=True, oob_score=False, n_jobs=None, random_state=None, verbose=0, warm_start=False, class_weight=None)```
- n_estimators：森林中决策树的个数，	默认值100
- criterion：对特征的评价标准，分类对应的gini或信息增益；回归对应 msepumae
- max_depth：决策树最大深度，**值越大，模型能学习到的信息越多，越容易过拟合**
- min_samples_split：内部节点再划分所需最小样本数 如果某节点的样本数少于该值，则不会继续再尝试选择最优特征来划分，	默认值2，**值越大，决策树越简单，越不容易过拟合**
- min_samples_leaf：决策树叶结点最小样本数量，默认值1，**值越大，叶子节点越容易被被剪枝，决策树越简单越不易过拟合**
- min_weight_fraction_leaf：决策树叶结点最小加权样本数量，默认0,**样本较多缺失时，可适当增大，样本有缺失值或分布类别偏差很大时引入**
- max_features：划分时考虑的最大特征数，auto(defult): 划分时最多考虑$ \sqrt{N}$个特征，**值越大，模型能学习到的信息越多，越容易过拟合**
- max_leaf_nodes：最大叶子节点数，可以防止过拟合，**值越小，叶子节点个数越少，可以防止过拟合**，默认None,特征较多时，可通过交叉验证设置相应值
- min_impurity_decrease：节点划分最小不纯度，小于阈值时，该节点即为叶子节点，不用设置，默认值1e-7
- bootstrap：是否进行有放回取样
- oob_score：采用袋外样本来评估模型的好坏，默认值False。通过未参加训练的样本估计模型效果
- n_jobs：处理器数量
- random_state：随机种子
- verbose：控制输出
- warm_start：是否使用之前的输出
- class_weight：类别权重


```Python
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

## <span id="head9"> 导入数据集</span>

dataset = pd.read_csv('../datasets/Social_Network_Ads.csv')
X = dataset.iloc[:, [2, 3]].values
y = dataset.iloc[:, 4].values

## <span id="head10"> 将数据集拆分成训练集和测试集</span>

from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25, random_state = 0)

## <span id="head11"> 特征缩放</span>

from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

### <span id="head12"> 调试训练集的随机森林</span>

from sklearn.ensemble import RandomForestClassifier
classifier = RandomForestClassifier(n_estimators = 10, criterion = 'entropy', random_state = 0)
classifier.fit(X_train, y_train)

## <span id="head13"> 预测测试集结果</span>
​```python
y_pred = classifier.predict(X_test)

## <span id="head14"> 生成混淆矩阵，也称作误差矩阵</span>
from sklearn.metrics import confusion_matrix
cm = confusion_matrix(y_test, y_pred)

from matplotlib.colors import ListedColormap
X_set, y_set = X_train, y_train
X1, X2 = np.meshgrid(np.arange(start = X_set[:, 0].min() - 1, stop = X_set[:, 0].max() + 1, step = 0.01),
                     np.arange(start = X_set[:, 1].min() - 1, stop = X_set[:, 1].max() + 1, step = 0.01))
plt.contourf(X1, X2, classifier.predict(np.array([X1.ravel(), X2.ravel()]).T).reshape(X1.shape),
             alpha = 0.75, cmap = ListedColormap(('red', 'green')))
plt.xlim(X1.min(), X1.max())
plt.ylim(X2.min(), X2.max())
for i, j in enumerate(np.unique(y_set)):
    plt.scatter(X_set[y_set == j, 0], X_set[y_set == j, 1],
                c = ListedColormap(('red', 'green'))(i), label = j)
plt.title('Random Forest Classification (Training set)')
plt.xlabel('Age')
plt.ylabel('Estimated Salary')
plt.legend()
plt.show()

from matplotlib.colors import ListedColormap
X_set, y_set = X_test, y_test
X1, X2 = np.meshgrid(np.arange(start = X_set[:, 0].min() - 1, stop = X_set[:, 0].max() + 1, step = 0.01),
                     np.arange(start = X_set[:, 1].min() - 1, stop = X_set[:, 1].max() + 1, step = 0.01))
plt.contourf(X1, X2, classifier.predict(np.array([X1.ravel(), X2.ravel()]).T).reshape(X1.shape),
             alpha = 0.75, cmap = ListedColormap(('red', 'green')))
plt.xlim(X1.min(), X1.max())
plt.ylim(X2.min(), X2.max())
for i, j in enumerate(np.unique(y_set)):
    plt.scatter(X_set[y_set == j, 0], X_set[y_set == j, 1],
                c = ListedColormap(('red', 'green'))(i), label = j)
plt.title('Random Forest Classification (Test set)')
plt.xlabel('Age')
plt.ylabel('Estimated Salary')
plt.legend()
plt.show()
```

