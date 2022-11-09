#  原理



EM 算法是一种求解最大似然估计的方法，通过观测样本，来找出样本的模型参数。

![](https://upload-images.jianshu.io/upload_images/18339009-7efada0447910fcf?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# 练习

假设我们有 A 和 B 两枚硬币，我们做了 5 组实验，每组实验投掷 10 次，每次只能只有A或者B一枚硬币。那么我们统计出现每组实验正面的次数，实验结果如下：

![](https://upload-images.jianshu.io/upload_images/18339009-da6cbf08e057a67b?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-6e9296bbd65b8246.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


虽然B出现正面次数为5的概率比A的小，但是也不是0。这时候我们应该考虑进这种可能的情况，那么这时候，第一轮实验用的A的概率就是: 0.246 / (0.246 + 0.015) = 0.9425；用B的概率就是1-0.9425 = 0.0575。

有0.9425的概率是硬币A，有0.0575的概率是硬币B，不再是非此即彼。这样我们在估计$θA$和$θB$时，就可以用上每一轮实验的数据，而不是某几轮实验的数据，显然这样会更好一些。这一步，我们实际上**估计的是用A或者B的一个概率分布，这步就称作E步**。

以硬币A为例， 第一轮的正面次数为5相当于 5次正面，5次反面

0.9425 * 5 = 4.7125(这是正面），0.9425 * 5 = 4.7125（这是反面）

新的$θA$ = 4.22 / (4.22+7.98)=0.35 这样，改变了硬币A和B的估计方法之后，会发现，**新估计的$θA$会更加接近真实的值，因为我们使用了每一轮的数据，而不是某几轮的数据。** 这步中，我们根据E步求出了硬币A和B在每一轮实验中的一个概率分布，**依据最大似然法则结合所有的数据去估计新的$θA$和$θB$， 被称作M步**

# 总结

EM算法可以先给无监督学习估计一个隐状态（即标签），有了标签，算法模型就可以转换成有监督学习，这时就可以用极大似然估计法求解出模型最优参数。

**第一步**是计算期望（E），利用对隐藏变量的现有估计值，计算其最大似然估计值；
**第二步**是最大化（M），最大化在E步上求得的最大似然值来计算参数的值。M步上找到的参数估计值被用于下一个E步计算中，这个过程不断交替进行。

极大似然估计用一句话概括就是：知道结果，反推条件θ。



EM 算法相当于一个框架，你可以采用不同的模型来进行聚类，比如 GMM（高斯混合模型），或者 HMM（隐马尔科夫模型）来进行聚类。

*   GMM 是通过概率密度来进行聚类，聚成的类符合高斯分布（正态分布）。

* 而 HMM 用到了马尔可夫过程，在这个过程中，我们通过**状态转移矩阵来计算状态转移的概率**。HMM 在自然语言处理和语音识别领域中有广泛的应用。

  

  用EM算法求解的模型一般有GMM或者协同过滤，k-means其实也属于EM。**EM算法一定会收敛，但是可能收敛到局部最优**。由于求和的项数将随着隐变量的数目指数上升，会给梯度计算带来麻烦。

# 优缺点











# Sklearn

``` GaussianMixture(n_components=1, covariance_type='full', max_iter=100)```
- n_components：即高斯混合模型的个数，也就是我们要**聚类的个数**，默认值为 1。如果你不指定 n_components，最终的聚类结果都会为同一个值。
- covariance_type：代表协方差类型。一个高斯混合模型的分布是由均值向量和协方差矩阵决定的，所以协方差的类型也代表了不同的高斯混合模型的特征。协方差类型有 4 种取值：
covariance_type=full，代表完全协方差，也就是元素都不为 0；
covariance_type=tied，代表相同的完全协方差；
covariance_type=diag，代表对角协方差，也就是对角不为 0，其余为 0；
covariance_type=spherical，代表球面协方差，非对角为 0，对角完全相同，呈现球面的特性。
- max_iter：代表最大迭代次数，EM 算法是由 E 步和 M 步迭代求得最终的模型参数，这里可以指定最大迭代次数，默认值为 100。


```
import matplotlib.pyplot as plt
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.mixture import GaussianMixture
#from sklearn import datasets
from sklearn.datasets import load_iris
iris = load_iris()
X = iris.data
y = iris.target

# 采用Z-Score规范化数据，保证每个特征维度的数据均值为0，方差为1
ss = StandardScaler()
X = ss.fit_transform(X)

#绘制数据分布图
plt.scatter(X[:, 0], X[:, 1], c="red", marker='o', label='see')
plt.xlabel('petal length')
plt.ylabel('petal width')
plt.legend()
plt.show()
 
# 构造GMM聚类
gmm = GaussianMixture(n_components=3, covariance_type='full')
gmm.fit(X)
# 训练数据
label_pred = gmm.predict(X)
print('聚类结果', '\n', label_pred)  # (150,) [1 1 1 1 1 2 2 2 2 2 0 2 2 2 2 0 2 2 ...]
print('真实类别', '\n', y)

x0 = X[label_pred == 0]
x1 = X[label_pred == 1]
x2 = X[label_pred == 2]
print(x0.shape, x1.shape, x2.shape)  # (62, 4) (50, 4) (38, 4)
plt.scatter(x0[:, 0], x0[:, 1], c="red", marker='o', label='label0')
plt.scatter(x1[:, 0], x1[:, 1], c="green", marker='*', label='label1')
plt.scatter(x2[:, 0], x2[:, 1], c="blue", marker='+', label='label2')
plt.xlabel('petal length')
plt.ylabel('petal width')
plt.legend()
plt.show()
from sklearn.metrics import calinski_harabasz_score
print(calinski_harabasz_score(X, label_pred))
# 指标分数越高，代表聚类效果越好，也就是相同类中的差异性小，不同类之间的差异性大。当
# 然具体聚类的结果含义，我们需要人工来分析，也就是当这些数据被分成不同的类别之后，具体每个类表代表的含义。
```

![](https://upload-images.jianshu.io/upload_images/18339009-b5aef5b4489504bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

