- [K-Nearest Neighbor](#head1)
	- [ 原理](#head2)
	- [ 距离的度量方式](#head3)
	- [k 值的大小如何选择](#head4)
	- [ 分类决策规则](#head5)
- [ KD树](#head6)
- [ 技巧](#head7)
- [ [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.neighbors.KNeighborsClassifier.html?highlight=neighbors%20kneighborsclassifier#sklearn.neighbors.KNeighborsClassifier)](#head8)
- [ 算出和X_train中每个点的距离](#head9)
- [ 对到每个点的距离从小到大排序，返回排序前的索引](#head10)
- [ 选取前三个最靠近的点](#head11)
- [ 根据索引得到值](#head12)
- [ 投票](#head13)
# <span id="head1">K-Nearest Neighbor</span>
![](https://upload-images.jianshu.io/upload_images/18339009-7ea1897bd96957ce?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
作为一种**没有显式训练和学习**过程的**分类和回归**算法，k 近邻在众多有**监督机器学习**算法中算是一种比较独特的方法。说它独特，是因为 k 近邻**不像其他模型有损失函数、有优化算法、有训练过程**。

## <span id="head2"> 原理</span>
给定一个训练数据集，对于新的输入实例，**根据这个实例最近的 k 个实例所属的类别来决定其属于哪一类**。所以相对于其它机器学习模型和算法，k 近邻总体上而言是一种非常简单的方法。

## <span id="head3"> 距离的度量方式</span>
找到与该实例最近邻的实例，这里就涉及到如何找到，即在特征向量空间中，我们要采取**何种方式来对距离进行度量**。

距离的度量用在 k 近邻中我们也可以称之为**相似性度量**，即特征空间中两个实例点相似程度的反映。在机器学习中，常用的距离度量方式包括欧式距离、曼哈顿距离、余弦距离以及切比雪夫距离等。**在 k 近邻算法中常用的距离度量方式是欧式距离，也即 L2 距离，**L2 距离计算公式如下：
![](https://upload-images.jianshu.io/upload_images/18339009-5b5f88f15c0de905.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## <span id="head4">k 值的大小如何选择</span>

一般而言，k 值的大小对分类结果有着重大的影响。**当选择的 k 值较小的情况下，就相当于用较小的邻域中的训练实例进行预测，只有当与输入实例较近的训练实例才会对预测结果起作用。但与此同时预测结果会对实例点非常敏感，分类器抗噪能力较差，因而容易产生过拟合**，所以一般而言，k 值的选择不宜过小。但如果选择较大的 k 值，就相当于在用较大邻域中的训练实例进行预测，但相应的分类误差也会增大，模型整体变得简单，会产生一定程度的欠拟合。所以一般而言，我们需要**采用交叉验证的方式来选择合适的 k 值**。

>交叉验证的思路就是，把样本集中的大部分样本作为训练集，剩余的小部分样本用于预测，来验证分类模型的准确性。所以在 KNN 算法中，我们一般会把 K 值选取在较小的范围内，同时在验证集上准确率最高的那一个最终确定作为 K 值。

## <span id="head5"> 分类决策规则</span>

 k 个实例的多数属于哪个类，明显是多数表决的归类规则。当然还可能使用其他规则，所以第三个关键就是**分类决策规则。**

回归：k个实例该属性值的平均值

# <span id="head6"> KD树</span>
它是一个二叉树的数据结构，方便存储 K 维空间的数据

KNN 的计算过程是大量计算样本点之间的距离。为了减少计算距离次数，提升 KNN 的搜索效率，人们提出了 KD 树（K-Dimensional 的缩写）。KD 树是对数据点在 K 维空间中划分的一种数据结构。在 KD 树的构造中，每个节点都是 k 维数值点的二叉树。既然是二叉树，就可以采用二叉树的增删改查操作，这样就大大提升了搜索效率。





# <span id="head7"> 技巧</span>
- 所有的特征应该被放缩到相同的量级
- 为了免平票的出现，K应该选择奇数
- 投票的结果会被到近邻样本的距离归一化，这样更近的样本的投票价值更大
- 尝试各种不同的距离度量方法
- k值小，低偏差，高方差
K值大，高偏差，低方差



# <span id="head8"> [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.neighbors.KNeighborsClassifier.html?highlight=neighbors%20kneighborsclassifier#sklearn.neighbors.KNeighborsClassifier)</span>

如果是做分类，你需要引用：from sklearn.neihbors import KNeighborsClassifier
如果是回归， 需要引用：from sklearn.neighbors import KNeighborsRegressor

```sklearn.neighbors.KNeighborsClassifier(n_neighbors=5, weights='uniform', algorithm='auto', leaf_size=30, p=2, metric='minkowski', metric_params=None, n_jobs=None, **kwargs)```

- weights： 预测中使用权重函数。
>uniform，代表所有邻居的权重相同；distance，代表权重是距离的倒数，即与距离成反比；
- algorithm：计算最近邻居的算法
>auto，根据数据的情况自动选择适合的算法，默认情况选择 auto；
kd_tree，也叫作 KD 树，是多维空间的数据结构，方便对关键数据进行检索，不过 KD 树适用于维度少的情况，一般维数不超过 20，如果维数大于 20 之后，效率反而会下降；
ball_tree，也叫作球树，它和 KD 树一样都是多维空间的数据结果，不同于 KD 树，球树更适用于维度大的情况；
brute，也叫作暴力搜索，它和 KD 树不同的地方是在于采用的是线性扫描，而不是通过构造树结构进行快速检索。当训练集大的时候，效率很低。
- leaf_size：构造 KD 树或球树时的叶子数，默认是 30，调整 leaf_size 会影响到树的构造和搜索速度。
- p：Minkowski度量的Power参数
- metric：树使用的距离度量
- n_jobsint：为邻居搜索运行的并行作业数。









```
from sklearn.neighbors import KNeighborsClassifier 
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from math import sqrt
from collections import Counter

iris = load_iris()
X = iris.data
y = iris.target
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=42)

kNN_classifier = KNeighborsClassifier(n_neighbors=3)
kNN_classifier.fit(X_train,y_train)
predict_y = kNN_classifier.predict(X_test)

print(predict_y,y_test)
```


# numpy
```
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from math import sqrt
from collections import Counter

iris = load_iris()
X = iris.data
y = iris.target
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=42)

for x_t, y_t in zip(X_test, y_test):
# <span id="head9"> 算出和X_train中每个点的距离</span>
    distances = [sqrt(np.sum((x - x_t)**2)) for x in X_train] 
# <span id="head10"> 对到每个点的距离从小到大排序，返回排序前的索引</span>
    near_index = np.argsort(distances)
# <span id="head11"> 选取前三个最靠近的点</span>
    K = 3 
# <span id="head12"> 根据索引得到值</span>
    topK = [y_train[i] for i in near_index[:K]]
# <span id="head13"> 投票</span>
    votes = Counter(topK)
    predict_y = votes.most_common(1)[0][0]
    print(predict_y,y_t)
```



