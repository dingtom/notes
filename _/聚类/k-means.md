- [ 原理](#head1)
- [ 练习](#head2)
- [ 总结](#head3)
	- [ 如何区分k-means与knn：](#head4)
	- [ k-means优点：](#head5)
	- [ k-means缺点：](#head6)
- [ [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.cluster.KMeans.html?highlight=kmeans#sklearn.cluster.KMeans)](#head7)
- [-*- coding: utf-8 -*-](#head8)
- [ 使用K-means对图像进行聚类，并显示聚类压缩后的图像](#head9)
- [ 加载图像，并对数据进行规范化](#head10)
- [ 读文件](#head11)
- [ 得到图像的像素值](#head12)
- [ 得到图像尺寸](#head13)
- [ 得到点(x,y)的三个通道值](#head14)
- [ 加载图像，得到规范化的结果imgData，以及图像尺寸](#head15)
- [ 用K-Means对图像进行16聚类](#head16)
- [ 将图像聚类结果，转化成图像尺寸的矩阵](#head17)
- [ 创建个新图像img，用来保存图像聚类压缩后的结果](#head18)
# <span id="head1"> 原理</span>
1. 随机从数据集中选择k个点作为我们聚类的中心点；
2. 每个点分配到离它最近的类中心点，就形成了k类。然后重新计算每个类的中心点（比如取各类的均值作为新的中心点）
3. 重复第2步，直到类不再发生变化，或者设置最大迭代次数，让算法收敛。

![](https://upload-images.jianshu.io/upload_images/18339009-b475d3e9b8fdeb16.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# <span id="head2"> 练习</span>
>2018 年世界杯中，很多球队没有进入到决赛圈，所以只有进入到决赛圈的球队才有实际的排名。如果是亚洲区预选赛 12 强的球队，排名会设置为 40。如果没有进入亚洲区预选赛 12 强，球队排名会设置为 50。
![](https://upload-images.jianshu.io/upload_images/18339009-78b68c401b775b26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
首先，针对上面的排名，我们需要做的就是数据规范化，你可以把这些值划分到[0,1]或者按照均值为 0，方差为 1 的正态分布进行规范化。我先把数值规范化到了[0,1]空间中，得到了下面的数值表：
![](https://upload-images.jianshu.io/upload_images/18339009-288461ab79e7239f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们随机选取中国、日本、韩国为三个类的中心点，我们就需要看下这些球队到中心点的距离。这里采用欧式距离。
![](https://upload-images.jianshu.io/upload_images/18339009-57547426cd30b15d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后我们再重新计算这三个类的中心点，如何计算呢？最简单的方式就是取平均值，然后根据新的中心点按照距离远近重新分配球队的分类，再根据球队的分类更新中心点的位置。
![](https://upload-images.jianshu.io/upload_images/18339009-64d508912300754a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





# <span id="head3"> 总结</span>

## <span id="head4"> 如何区分k-means与knn：</span>
- 都需要计算样本之间的距离
- k-means是聚类算法，knn是有监督的分类算法；聚类没有标签，分类有标签
- k-means中的k是k类，knn中的k是k个最近的邻居。

## <span id="head5"> k-means优点：</span>
- **原理比较简单**，实现也是很容易，收敛速度快。
- 聚类效果较优。
- 算法的**可解释度比较强**。

## <span id="head6"> k-means缺点：</span>
- **K值的选取不好把握**，一般根据经验或者已经有预判，其次是根据暴力试错k值选择最合适的分类数k。
- **初始值的选取会影响最终聚类效果**
- **采用迭代方法，得到的结果只是局部最优。**，并且目标函数SSE可能会达到局部最优解。这个有相应的改进方法，包括k-means++和二分k-means。
- **非凸的数据集比较难收敛**
- **各隐含类别的数据不平衡效果不佳**
- **对噪声和异常点比较敏感**


# <span id="head7"> [sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.cluster.KMeans.html?highlight=kmeans#sklearn.cluster.KMeans)</span>

```KMeans(n_clusters=8, init='k-means++', n_init=10, max_iter=300, tol=0.0001, precompute_distances='auto', verbose=0, random_state=None, copy_x=True, n_jobs=1, algorithm='auto')```

- n_clusters: 即 K 值，一般需要**多试一些 K 值来保证更好的聚类效果**。
- max_iter：最大迭代次数
- n_init：初始化中心点的运算次数，默认是 10。运行 n_init 次, 取其中最好的作为初始的中心点。如果 K 值比较大的时候，你可以适当增大 n_init 这个值；
- init：即初始值选择的方式，默认k-means++ ，你也可以自己指定中心点
- algorithm：k-means 的实现算法，有“auto” “full”“elkan”三种。如果你选择"full"采用的是传统的 K-Means 算法，“auto”会根据数据的特点自动选择是选择“full”还是“elkan”。

返回值：
- cluster_centers_，聚类中心的坐标。如果算法在完全收敛之前停止(见tol和max iter)，这些将与标签不一致。
- labels_，每个点的标记
- inertia_,样本到其最近的簇中心的距离的平方和
- n_iter迭代运行次数。

```
#############K-means-鸢尾花聚类############
import matplotlib.pyplot as plt
import numpy as np
from sklearn.cluster import KMeans
#from sklearn import datasets
from sklearn.datasets import load_iris
iris = load_iris()
X = iris.data
y = iris.target

#绘制数据分布图
plt.scatter(X[:, 0], X[:, 1], c="red", marker='o', label='see')
plt.xlabel('petal length')
plt.ylabel('petal width')
plt.legend()
plt.show()
 
estimator = KMeans(n_clusters=3)#构造聚类器
estimator.fit(X)#聚类
print(X.shape)  # (150, 4)
label_pred = estimator.labels_  # 获取聚类标签
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
```

![](https://upload-images.jianshu.io/upload_images/18339009-5c8c4ea6bec6d045.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 图像分割
```
# <span id="head8">-*- coding: utf-8 -*-</span>
# <span id="head9"> 使用K-means对图像进行聚类，并显示聚类压缩后的图像</span>
import numpy as np
import PIL.Image as image
from sklearn.cluster import KMeans
from sklearn import preprocessing
import matplotlib.image as mpimg
# <span id="head10"> 加载图像，并对数据进行规范化</span>
def load_data(filePath):
# <span id="head11"> 读文件</span>
    f = open(filePath,'rb')
    data = []
# <span id="head12"> 得到图像的像素值</span>
    img = image.open(f)
# <span id="head13"> 得到图像尺寸</span>
    width, height = img.size
    for x in range(width):
        for y in range(height):
# <span id="head14"> 得到点(x,y)的三个通道值</span>
            c1, c2, c3 = img.getpixel((x, y))
            data.append([(c1+1)/256.0, (c2+1)/256.0, (c3+1)/256.0])
    f.close()
    return np.mat(data), width, height
# <span id="head15"> 加载图像，得到规范化的结果imgData，以及图像尺寸</span>
img, width, height = load_data('./weixin.jpg')
# <span id="head16"> 用K-Means对图像进行16聚类</span>
kmeans =KMeans(n_clusters=16)
label = kmeans.fit_predict(img)
# <span id="head17"> 将图像聚类结果，转化成图像尺寸的矩阵</span>
label = label.reshape([width, height])
# <span id="head18"> 创建个新图像img，用来保存图像聚类压缩后的结果</span>
img=image.new('RGB', (width, height))
for x in range(width):
    for y in range(height):
        c1 = kmeans.cluster_centers_[label[x, y], 0]
        c2 = kmeans.cluster_centers_[label[x, y], 1]
        c3 = kmeans.cluster_centers_[label[x, y], 2]
        img.putpixel((x, y), (int(c1*256)-1, int(c2*256)-1, int(c3*256)-1))
img.save('weixin_new.jpg')
```



