- [ 1.原理：](#head1)
- [ 2.总结](#head2)
- [ [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.cluster.DBSCAN.html?highlight=dbscan#sklearn.cluster.DBSCAN)](#head3)
- [ 生成随机簇类数据](#head4)
- [ 绘制延伸图](#head5)
- [dbscan聚类算法 按照经验MinPts=2*ndims，因此我们设置MinPts=4。](#head6)
- [ 绘制dbscan聚类结果](#head7)
- [ 性能评价指标ARI](#head8)
- [ARI指数  ARI= 0.99为了较少算法的计算量，我们尝试减小MinPts的值。](#head9)
DBSCAN(Density-Based Spatial Clustering of Applications with Noise，具有噪声的基于密度的聚类方法)是一种很典型的密度聚类算法，和K-Means，BIRCH这些一般只适用于凸样本集的聚类相比，DBSCAN既可以适用于凸样本集，也可以适用于非凸样本集。
# <span id="head1"> 1.原理：</span>

![](https://upload-images.jianshu.io/upload_images/18339009-b80bcf83dd453490.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



$\epsilon$两个样本的最小距离，它的含义为：如果两个样本的距离小于或等于值$\epsilon$那么这两个样本互为邻域。

MinPts：形成簇类所需的最小样本个数，比如MinPts等于5，形成簇类的前提是至少有一个样本的$\epsilon$-邻域大于等于5。


如果$\epsilon$值取的太小，部分样本误认为是噪声点（白色）；$\epsilon$值取的多大，大部分的样本会合并为同一簇类。同样的，若MinPts值过小，则所有样本都可能为核心样本；MinPts值过大时，部分样本误认为是噪声点（白色）

minPts必须大于等于3，因此一般认为minPts=2*dim，若数据集越大，则minPts的值选择的亦越大。
$\epsilon$值常常用k-距离曲线（k-distance graph）得到，计算每个样本与所有样本的距离，选择k个最近邻的距离并从大到小排序，得到k-距离曲线，曲线拐点对应的距离设置为$\epsilon$如下图：
![](https://upload-images.jianshu.io/upload_images/18339009-fc8ccc21272a794a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

一般选择$\epsilon$值等于第（minPts-1）的距离，**计算样本间的距离前需要对数据进行归一化，使所有特征值处于同一尺度范围**
如果(k+1)-距离曲线和k-距离曲线没有明显差异，那么minPts设置为k值。






1. 首先**确定半径$\epsilon$和minPoints**. 从一**个没有被访问过的任意数据点开始**，以这个点为中心，**$\epsilon$为半径的圆内包含的点的数量是否大于或等于minPoints**，如果大于或等于minPoints则改点被标记为central point,反之则会被标记为noise point。
2. 重复1的步骤，**如果一个noise point存在于某个central point为半径的圆内，则这个点被标记为边缘点，反之仍为noise point。**重复步骤1，直到所有的点都被访问过。

# <span id="head2"> 2.总结</span>

优点：

- DBSCAN不需要指定簇类的数量；
- 可以对任意形状的稠密数据集进行聚类，相对的，K-Means之类的聚类算法一般只适用于凸数据
- DBSCAN可以检测数据集的噪声，且对数据集中的异常点不敏感；
- 聚类结果没有偏倚，相对的，K-Means之类的聚类算法初始值对聚类结果有很大影响。

缺点：
- 如果样本集的密度不均匀、聚类间距差相差很大时，聚类质量较差，这时用DBSCAN聚类一般不适合。
- 如果样本集较大时，聚类收敛时间较长，此时可以对搜索最近邻时建立的KD树或者球树进行规模限制来改进。
- 调参相对于传统的K-Means之类的聚类算法稍复杂，主要需要对距离阈值，邻域样本数阈值MinPts联合调参，不同的参数组合对最后的聚类效果有较大影响。

# <span id="head3"> [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.cluster.DBSCAN.html?highlight=dbscan#sklearn.cluster.DBSCAN)</span>

```*class *`sklearn.cluster.``DBSCAN`(*eps=0.5*, *min_samples=5*, *metric='euclidean'*, *metric_params=None*, *algorithm='auto'*, *leaf_size=30*, *p=None*, *n_jobs=None*)```








```
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_blobs
from sklearn.cluster import DBSCAN
from sklearn.preprocessing import StandardScaler

# <span id="head4"> 生成随机簇类数据</span>
X, y = make_blobs(random_state=170,n_samples=600,centers=5)
# <span id="head5"> 绘制延伸图</span>
plt.scatter(X[:,0],X[:,1])
plt.xlabel("Feature 0")
plt.ylabel("Feature 1")
plt.show()
# <span id="head6">dbscan聚类算法 按照经验MinPts=2*ndims，因此我们设置MinPts=4。</span>
dbscan = DBSCAN(eps=1, min_samples=4)
clusters = dbscan.fit_predict(X)
# <span id="head7"> 绘制dbscan聚类结果</span>
plt.scatter(X[:,0],X[:,1],c=clusters,cmap="plasma")
plt.xlabel("Feature 0")
plt.ylabel("Feature 1")
plt.title("eps=0.5,minPts=4")
plt.show()
# <span id="head8"> 性能评价指标ARI</span>
from sklearn.metrics.cluster import adjusted_rand_score
# <span id="head9">ARI指数  ARI= 0.99为了较少算法的计算量，我们尝试减小MinPts的值。</span>
print("ARI=",round(adjusted_rand_score(y,clusters),2))
```
![](https://upload-images.jianshu.io/upload_images/18339009-0bd7fecb02c08f5b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-49f6e19e9526d860.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





