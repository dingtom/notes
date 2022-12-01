一个二维向量可以对应二维笛卡尔直角坐标系中从原点出发的一个有向线段。
![](https://upload-images.jianshu.io/upload_images/18339009-3c2f6f6dcd84d308.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
两个维数相同的向量的内积被定义为：
![](https://upload-images.jianshu.io/upload_images/18339009-59d8fafc342b9832.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
常见形式：$A·B=|A||B| cos(\alpha)$
A与B的内积等于A到B的投影长度乘以B的模。
如果我们假设B的模为1，即让$|B|=1$，那么就变成了：
$A·B=|A|cos(\alpha)$
其中$|A|=\sqrt{x_1^2+y_1^2}$是向量A的模，也就是A线段的标量长度。
也就是说，设向量B的模为1，**则A与B的内积值等于A向B所在直线投影的矢量长度**


在代数表示方面，我们经常用线段终点的点坐标表示向量，例如上面的B向量可以表示为（3，2），这是我们再熟悉不过的向量表示。
不过我们常常忽略，只有一个（3，2）本身是不能够精确表示一个向量的。我们仔细看一下，这里的3实际表示的是向量在x轴上的投影值是3，在y轴上的投影值是2。也就是说我们其实隐式引入了一个定义：以x轴和y轴上正方向长度为1的向量为标准。那么一个向量（3，2）实际是说在x轴投影为3而y轴的投影为2。**注意投影是一个矢量，所以可以为负。**
更正式的说，向量（x，y）实际上表示线性组合：
$x(1，0)^T+y(0，1)^T$
不难证明所有二维向量都可以表示为这样的线性组合。此处（1，0）和（0，1）叫做二维空间中的一组基。

**所以，要准确描述向量，首先要确定一组基，然后给出在基所在的各个直线上的投影值，就可以了。**

**实际上，对应任何一个向量我们总可以找到其同方向上模为1的向量，只要让两个分量分别除以模就好了。**如，上面的基可以变为。$(-\frac{1}{\sqrt{2}}, \frac{1}{\sqrt{2}})$,$(\frac{1}{\sqrt{2}},\frac{1}{\sqrt{2}})$现在，我们想获得（3，2）在新基上的坐标，即在两个方向上的投影矢量值，那么根据内积的几何意义，我们只要分别计算（3，2）和两个基的内积，不难得到新的坐标为$(\frac{5}{\sqrt{2}},-\frac{1}{\sqrt{2}})$。


我们列举的例子中基是正交的（即内积为0，或直观说相互垂直），但可以成为一组基的唯一要求就是线性无关，非正交的基也是可以的。不过因为正交基有较好的性质，所以一般使用的基都是正交的。

一般的，如果我们有M个N维向量，想将其变换为由R个N维向量表示的新空间中，那么首先将R个基按行组成矩阵A，然后将向量按列组成矩阵B，那么两矩阵的乘积AB就是变换结果，其中AB的第m列为A中第m列变换后的结果。
![](https://upload-images.jianshu.io/upload_images/18339009-14a086910ee97633.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-b65727ceaee09800.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
特别要注意的是，这里R可以小于N，而R决定了变换后数据的维数。也就是说，我们可以将一N维数据变换到更低维度的空间中去，变换后的维度取决于基的数量。因此这种矩阵相乘的表示也可以表示降维变换。

如果我们有一组N维向量，现在要将其降到K维（K小于N），那么我们应该如何选择K个基才能最大程度保留原有的信息？
假设我们的数据由五条记录组成，将它们表示成矩阵形式：
![](https://upload-images.jianshu.io/upload_images/18339009-82f95beae4f89f40.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
为了后续处理方便，我们首先将每个字段内所有值都减去字段均值，其结果是将每个字段都变为均值为0
![](https://upload-images.jianshu.io/upload_images/18339009-24025a212bdac384.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-4d759713bed34c0e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

要在二维平面中选择一个方向，将所有数据都投影到这个方向所在直线上，用投影值表示原始记录。这是一个实际的二维降到一维的问题。那么如何选择这个方向（或者说基）才能尽量保留最多的原始信息呢？一种直观的看法是：希望投影后的投影值尽可能分散。

我们希望投影后投影值尽可能分散，而这种**分散程度，可以用数学上的方差来表述**。一个字段的方差可以看做是每个元素与字段均值的差的平方和的均值。
![](https://upload-images.jianshu.io/upload_images/18339009-35bf140e26bc888e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
由于上面我们已经将每个字段的均值都化为0了，因此方差可以直接用每个元素的平方和除以元素个数表示：
![](https://upload-images.jianshu.io/upload_images/18339009-640d4bfaf54eca69.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
**于是上面的问题被形式化表述为：寻找一个一维基，使得所有数据变换为这个基上的坐标表示后，方差值最大。**

对于上面二维降成一维的问题来说，找到那个使得方差最大的方向就可以了。不过对于更高维，还有一个问题需要解决。考虑三维降到二维问题。与之前相同，首先我们希望找到一个方向使得投影后方差最大，这样就完成了第一个方向的选择，继而我们选择第二个投影方向。如果我们还是单纯只选择方差最大的方向，很明显，这个方向与第一个方向应该是“几乎重合在一起”，显然这样的维度是没有用的，因此，应该有其他约束条件。从直观上说，让两个字段尽可能表示更多的原始信息，我们是不希望它们之间存在（线性）相关性的，因为相关性意味着两个字段不是完全独立，必然存在重复表示的信息。**数学上可以用两个字段的协方差表示其相关性**，由于已经让每个字段均值为0，则：
![](https://upload-images.jianshu.io/upload_images/18339009-d81a6ce3f597d093.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可以看到，在字段均值为0的情况下，两个字段的协方差简洁的表示为其内积除以元素数m。当协方差为0时，表示两个字段完全独立。为了让协方差为0，我们选择第二个基时只能在与第一个基正交的方向上选择。因此最终选择的两个方向一定是正交的。至此，我们得到了降维问题的优化目标：**将一组N维向量降为K维（K大于0，小于N），其目标是选择K个单位（模为1）正交基，使得原始数据变换到这组基上后，各字段两两间协方差为0，而字段的方差则尽可能大（在正交的约束下，取最大的K个方差）。**

我们看到，最终要达到的目的与字段内方差及字段间协方差有密切关系。因此我们希望能将两者统一表示，仔细观察发现，两者均可以表示为内积的形式，而内积又与矩阵相乘密切相关。于是我们来了灵感：

假设我们只有a和b两个字段，那么我们将它们按行组成矩阵X：
![](https://upload-images.jianshu.io/upload_images/18339009-7f469b002a6a0769.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后我们用X乘以X的转置，并乘上系数1/m：
![](https://upload-images.jianshu.io/upload_images/18339009-a56b6d29bf0dbcb2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这个矩阵对角线上的两个元素分别是两个字段的方差，而其它元素是a和b的协方差。两者被统一到了一个矩阵的。

根据上述推导，我们发现要达到优化目地，等价于将协方差矩阵对角化：即除对角线外的其它元素化为0，并且在对角线上将元素按大小从上到下排列，这样我们就达到了优化目的。这样说可能还不是很明晰，我们进一步看下原矩阵与基变换后矩阵协方差矩阵的关系：设原始数据矩阵X对应的协方差矩阵为C，而P是一组基按行组成的矩阵，设Y=PX，则Y为X对P做基变换后的数据。设Y的协方差矩阵为D，我们推导一下D与C的关系：
![](https://upload-images.jianshu.io/upload_images/18339009-0e588186fbfb5ea1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们要找的P不是别的，而是能让原始协方差矩阵对角化的P。换句话说，优化目标变成了寻找一个矩阵P，满足$PCP^T$是一个对角矩阵，并且对角元素按从大到小依次排列，那么P的前K行就是要寻找的基，用P的前K行组成的矩阵乘以X就使得X从N维降到了K维并满足上述优化条件。


由上文知道，协方差矩阵C是一个是对称矩阵，在线性代数上，实对称矩阵有一系列非常好的性质：
1）实对称矩阵不同特征值对应的特征向量必然正交。
2）设特征向量$\lambda$重数为r，则必然存在r个线性无关的特征向量对应于$\lambda$，因此可以将这r个特征向量单位正交化。

由上面两条可知，一个n行n列的实对称矩阵一定可以找到n个单位正交特征向量，设这n个特征向量为$e_1,e_2,...e_n$，我们将其按列组成矩阵：
则对协方差矩阵C有如下结论：
![](https://upload-images.jianshu.io/upload_images/18339009-ab74b18111f33d8d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中$\Lambda$为对角矩阵，其对角元素为各特征向量对应的特征值（可能有重复）。
到这里，我们发现我们已经找到了需要的矩阵P：
![](https://upload-images.jianshu.io/upload_images/18339009-fd0b3cc0d176c275.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
P是协方差矩阵的特征向量单位化后按行排列出的矩阵，其中每一行都是C的一个特征向量。如果设P按照$\Lambda$中特征值的从大到小，将特征向量从上到下排列，则用P的前K行组成的矩阵乘以原始数据矩阵X，就得到了我们需要的降维后的数据矩阵Y。

# **PCA算法**
设有m条n维数据。
1）将原始数据按列组成n行m列矩阵X
2）将X的每一行（代表一个属性字段）进行零均值化，即减去这一行的均值
3）求出协方差矩阵$C= \frac{1}{m} X X^{\top}$
4）求出协方差矩阵的特征值及对应的特征向量
5）将特征向量按对应特征值大小从上到下按行排列成矩阵，取前k行组成矩阵P
6）Y=PX即为降维到k维后的数据

以上文提到的
![](https://upload-images.jianshu.io/upload_images/18339009-a2f3ecae948aecdb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
为例，我们用PCA方法将这组二维数据其降到一维。
因为这个矩阵的每行已经是零均值，这里我们直接求协方差矩阵：
![](https://upload-images.jianshu.io/upload_images/18339009-9fc5461577c9b811.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后求其特征值和特征向量，具体求解方法不再详述，可以参考相关资料。求解后特征值为：
![](https://upload-images.jianshu.io/upload_images/18339009-809b694cff2295cf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其对应的特征向量分别是：

![](https://upload-images.jianshu.io/upload_images/18339009-ab8175034693607a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中对应的特征向量分别是一个通解，$c_1$和$c_2$可取任意实数。那么标准化后的特征向量为：
![](https://upload-images.jianshu.io/upload_images/18339009-b294ffa7737e12c5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
因此我们的矩阵P是：
![](https://upload-images.jianshu.io/upload_images/18339009-d95dba6feb19bbd7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可以验证协方差矩阵C的对角化：
![](https://upload-images.jianshu.io/upload_images/18339009-92ded9ecec0fb09a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
最后我们用P的第一行乘以数据矩阵，就得到了降维后的表示：
![](https://upload-images.jianshu.io/upload_images/18339009-ed9af8999cf9fefc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
降维投影结果如下图：
![](https://upload-images.jianshu.io/upload_images/18339009-9869ed5f3b3a5de0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


PCA本质上是将方差最大的方向作为主要特征，并且在各个正交方向上将数据“离相关”，也就是让它们在不同正交方向上没有相关性。因此，PCA也存在一些限制，例如它可以很好的解除线性相关，但是对于高阶相关性就没有办法了，对于存在高阶相关性的数据，可以考虑Kernel PCA，通过Kernel函数将非线性相关转为线性相关，关于这点就不展开讨论了。另外，PCA假设数据各主特征是分布在正交方向上，如果在非正交方向上存在几个方差较大的方向，PCA的效果就大打折扣了。最后需要说明的是，PCA是一种无参数技术，也就是说面对同样的数据，如果不考虑清洗，谁来做结果都一样，没有主观参数的介入，所以PCA便于通用实现，但是本身无法个性化的优化。

```
import numpy as np
import matplotlib.pyplot as plt
x = np.array([1.5, 1.5, 2.4, 2,3.3, 2.3, 2, 1, 1.5, 1.5])
y = np.array([2.1, 1.7, 2.9, 2.2, 3, 2.7, 1.6, 1.1, 1.6, 0.9])

# 将X的每一行（代表一个属性字段）进行零均值化，即减去这一行的均值
mean_x = np.mean(x)
mean_y = np.mean(y)
scaled_x = x-mean_x
scaled_y = y-mean_y
data = np.matrix([[scaled_x[i], scaled_y[i]] for i in range(len(scaled_x))])
# print(data.shape)  (10, 2)
# 规范化后的数据分布情况
plt.plot(scaled_x, scaled_y, 'o', color='red')
plt.show()

# 求协方差
cov = np.cov(scaled_x, scaled_y)
# print(cov)   (2,2)
# [[0.42666667 0.4       ]
# [0.4        0.53066667]]


# 求出协方差矩阵的特征值及对应的特征向量 eig()和eigh()。一个计算的特征值为复数形式，一个为实数形式。
eig_val, eig_vec = np.linalg.eig(cov)
# print(eig_val, eig_vec.shape) [0.07530083 0.88203251]
# [[-0.75130394 -0.65995635]
#  [ 0.65995635 -0.75130394]]
eig_pairs = [(np.abs(eig_val[i]), eig_vec[:, i]) for i in range(len(eig_val))]

# 将特征向量按对应特征值大小从上到下按行排列成矩阵，取前k行组成矩阵P
eig_pairs.sort(reverse=True)
# print(eig_pairs)
# [(0.882032505577202, array([-0.65995635, -0.75130394])), (0.07530082775613123, array([-0.75130394,  0.65995635]))]
feature = eig_pairs[0][1]
# print(feature)  （1,2）
# [-0.65995635 -0.75130394]

# Y=PX即为降维到k维后的数据
new_data_reduced = np.transpose(np.dot(feature, np.transpose(data)))
# print(new_data_reduced.shape) (10, 1)
# [[ 0.17382607]
# [ 0.47434764]
# [-1.0211778 ]
# [-0.2312825 ]
# [-1.69026891]
# [-0.80492138]
# [ 0.21949986]
# [ 1.25510819]
# [ 0.54947804]
# [ 1.0753908 ]]
new_data = np.transpose(np.dot(eig_vec, np.transpose(data)))
plt.plot(scaled_x, scaled_y, 'o', color='red')
plt.plot([eig_vec[:, 0][0], 0], [eig_vec[:, 0][1], 0], color='red')
# print([eig_vec[:, 0][0], 0], [eig_vec[:, 0][1], 0])
# [-0.7513039432395591, 0] [0.6599563507329023, 0]
plt.plot([eig_vec[:, 1][0], 0], [eig_vec[:, 1][1], 0], color='blue')
plt.plot(new_data[:, 0], new_data[:, 1], '^', color='blue')
plt.plot(new_data_reduced[:, 0], [1.5]*10, '*', color='green')
plt.show()
```
```

from sklearn.datasets import make_blobs
from sklearn.decomposition import PCA
# 生成随机数据，样本量为10000，维度为10
X, y = make_blobs(n_samples=10000, n_features=10)
# print(X.shape, y.shape)
# (10000, 10) (10000,)

pca = PCA(n_components='mle')
# PCA(copy=True, n_components=2, whiten=F
# copy：bool类型，是否将原始数据复制一份，默认为TRUE。
# n_components =k（可以是int型数字或者阈值，这里的‘mle’表示自动选择降维的维数）
# whiten：bool类型，是否进行白化，默认为FALSE。
new_data = pca.fit_transform(X)
# 训练
print(new_data.shape, pca.components_.shape)  # 返回具有最大方差的成分
# (10000, 2) (2, 10)
print(pca.explained_variance_ratio_)   # 返回 所保留的n个成分各自的方差百分比。
print(pca.explained_variance_)  # 返回 所保留的n个成分各自的方差
print(pca.n_components_)    # 查看自动选择降到的维数

```







