



![](https://upload-images.jianshu.io/upload_images/18339009-135ea1f9251fdb66.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

自组织映射（Self Organizing Map，SOM）。**蓝色斑点是训练数据的分布**，而**小白色斑点是从该分布中抽取得到的当前训练数据**。首先（左图）**SOM节点被任意地定位在数据空间中**。我们选择**最接近训练数据的节点作为获胜节点**（用黄色突出显示）。**它被移向训练数据，包括（在较小的范围内）其网格上的相邻节点**。经过多次迭代后，网格趋于接近数据分布（右图）。





# 算法流程
我们有一个空间连续的输入空间，其中包含我们的输入向量。我们的目的是将其映射到低维的离散输出空间，其拓扑结构是通过在网格中布置一系列神经元形成的。我们的SOM算法提供了称为特征映射的非线性变换。

- 随机化映射(map)中的节点权重向量
- 随机选取输入向量${{D}(t)}$
- 遍历映射中每一个节点
计算${\displaystyle{D}(t)}$与映射节点之间的相似度(通常使用欧式距离);选取距离最小的节点作为优胜节点(winner node)$，有的时也叫BMU(best matching unit)
- 更新BMU（包括BMU本身）附近节点的权重向量，方法是将它们拉近输入向量,当前权重向量为$\mathbf{W}{v}$的输出节点$v$其权值更新公式为
$${ W_{v}(s+1)=W_{v}(s)+\theta(u, v, ，，s)\cdot\alpha(s)\cdot (D(t)-W_{v}(s))}$$
其中$s$为迭代次数，$D(t)$是当前输入向量$u$为获胜节点
$\theta(u，v，s)$是 $s$ 下给出 $u $和$ v $之间距离的邻近函数，用来确定获胜节点对其近邻节点的影响强弱，$\alpha(s)$是一个单调递减的学习率


- 增加${\displaystyle s}$并在${\displaystyle s<\lambda}$时从步骤2开始重复

###　评估
一种衡量SOM优劣的指标是量化误差（quantization error），输入样本 与 对应的winner神经元的weight 之间的 平方根.可以用下式表示：
$E_{q}=\sum_{i=1}^{n}\left\|x_{i}-w_{j}\right\|^{2}$



### neighborhood function
neighborhood函数用来确定优胜节点对其近邻节点的影响强弱，即优胜邻域中每个节点的更新幅度。最常见的选择是高斯函数，它可以表征优胜邻域内，影响强弱与距离的关系。

假设中心节点是优胜节点
- 高斯近邻函数：
![](https://upload-images.jianshu.io/upload_images/18339009-c0eb8c58c684fbf1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

是连续的，因此sigma的有效取值范围也是连续的
当选sigma设为1时，所有的节点都有一定的更新幅度，中心优胜节点是1，越远离优胜节点，更新幅度越低，
当sigma取值很小时，只有优胜节点更新幅度是1，其余几乎都接近0
当sigma取值较大时，即使是边缘的节点，也有较大的更新幅度

- Bubble近邻函数：只要是在优胜邻域内的神经元，更新系数都是相同的
![](https://upload-images.jianshu.io/upload_images/18339009-1dedc77c3ebea64e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
因此, sigma的有效取值是离散的：
0.5：仅优胜节点
1.5：周围一圈
2.5：周围2圈

### 学习率α、邻域范围σ随时间衰减
SOM网络的另一个特点是，学习率和邻域范围随着迭代次数会逐渐衰减


# 优缺点
能够保持拓扑结构不变，泛化能力好、抗噪音能力强

# [MiniSom](https://github.com/JustGlowing/minisom)

- som.get_weights()： Returns the weights of the neural network
- som.distance_map()：Returns the distance map of the weights
- som.activate(X)： Returns the activation map to x 值越小的神经元，表示与输入样本 越匹配
- som.quantization(X)：Assigns a code book 给定一个 输入样本，找出该样本的优胜节点，然后返回该神经元的权值向量(每个元素对应一个输入单元)
- som.winner(X)： 给定一个 输入样本，找出该样本的优胜节点 格式：输出平面中的位置
- som.win_map(X)：将各个样本，映射到平面中对应的位置 返回一个dict { position: samples_list }
- som.activation_response(X)： 返回输出平面中，各个神经元成为 winner的次数 格式为 1个二维矩阵
- quantization_error(量化误差)： 输入样本 与 对应的winner神经元的weight 之间的 平方根

###### 1.创建一个简单的颜色量化模型
![](https://upload-images.jianshu.io/upload_images/18339009-6c5698a178fd111c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
from minisom import MiniSom
import numpy as np
import matplotlib.pyplot as plt
%matplotlib inline

img = plt.imread(r'C:\Users\tomding\Pictures\640.jpg')
pixels = np.reshape(img, (img.shape[0]*img.shape[1], 3))/255
print(img.shape)
# (170, 296, 3)
```
```som = MiniSom(x=3, y=3, input_len=3, sigma=0.1, learning_rate=0.2)```
>- 第一个参数是 SOM 的维度。在我们的例子中，我们将构建一个 3*3 的 SOM。这意味着我们得到的最终颜色将是 3 * 3，即 9。这里的数字可以随意尝试，看看你会得到哪些不同的结果。为降维任务设置网格大小的经验法则是，它应该包含**5*sqrt（N）**个神经元，其中N是要分析的数据集中的样本数。
>- 第二个参数是 input_len，它是我们数据集中的特征数量。在我们的例子中，我们使用 3，对应了像素数组的形状。
>- 下一个参数是 sigma，它是 SOM 中不同相邻节点的半径，默认值为 1.0。
>- 最后一个参数是 learning_rate，它确定每次迭代期间权重的调整幅度。
>- Neighborhood_function:'gaussian'、'mexican_hat'、'bubble'


```som.random_weights_init(pixels)```
>将SOM的权重初始化为小的标准化随机值。我们使用random_weights_init函数并传入我们的数据（像素）来实现这一点。

```starting_weights = som.get_weights().copy()```
>我们现在需要保存起始权重，它们代表图像的初始颜色。稍后会将它们可视化。我们保存这些权重的方法是使用 get_weights 函数和 Python 的副本来确保在权重更新之前得到它们。

```som.train_random(pixels, 100)```
>然后我们通过运行 train_random 函数来训练像素。它需要两个参数，第一个参数是需要训练的数据集，第二个参数是迭代次数。

```
qnt = som.quantization(pixels)
print(pixels.shape, starting_weights.shape, qnt.shape)
# (165000, 3) (3, 3, 3) (165000, 3)
# 计算分配给地图上的观察 x的坐标，方法为 winner(x)。
# 使用 distance_map() 方法计算地图上权重的平均距离图。
# 计算每个神经元被认为是新数据集观察的赢家的次数，方法是 activation_response(data)。
# 用 quantization_error(data) 方法计算量化误差。
# 矢量量化是由 quantization 方法实现的,颜色量化就是减少图像中所用的特殊颜色,所谓矢量量化算法就是找到一个原型矢量数据集w_i，i=1…，m。用它最大程度的近似表示原始的数据集。最著名的算法k-均值（k-means）算法，它可以很方便的找到原型矢量数据集，并使其量化误差最小。原型矢量的数据密度决定于待训练样本数据密度，总的来说，它满足下式：


```
>量化图像的每个像素。在此过程中，我们将减少图像中的颜色数量。我们使用 MiniSom 中的 quantization 实用程序来完成这一步。

```clustered = np.zeros(img.shape)```
>将图像构建为 3D 图像。

```
for i, q in enumerate(qnt):
  clustered[np.unravel_index(i, dims=(img.shape[0], img.shape[1]))] = q
```
>接下来我们需要做的是将量化后的值放入新图像中。
numpy.unravel_index()函数的作用是获取一个/组int类型的索引值在一个多维数组中的位置。这个函数有三个参数：
indices：一个整数数组，其元素是维度变暗数组的平铺版本的索引。
dims：整数元组用于解开 indices.dex 值的数组的形状
Order：{'C'，'F'}，可选确定是否应将索引视为以行主（C样式）或列主（Fortran样式）顺序编制索引。

```
result = np.zeros(img.shape)
for i, q in enumerate(qnt):
     result[np.unravel_index(i, dims=(img.shape[0], img.shape[1]))] = q
plt.figure(figsize=(12, 6))
plt.subplot(221)
plt.title('Original')
plt.imshow(img)
plt.subplot(222)
plt.title('Result')
plt.imshow(result)
plt.subplot(223)
plt.title('Initial Colors')
plt.imshow(starting_weights)
plt.subplot(224)
plt.title('Learnt Colors')
plt.imshow(som.get_weights())
plt.tight_layout()
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-d3005164327dee54.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.iris 数据集实验
```
import sys
import math
from minisom import MiniSom
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.gridspec import GridSpec
import pandas as pd
from matplotlib.patches import Patch
from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
iris = datasets.load_iris()
feature_names = iris.feature_names
class_names = iris.target_names
X = iris.data
y = iris.target
X_train, X_test, y_train, y_test = train_test_split(X,y,test_size=0.3,random_state=0)
N = X_train.shape[0]  # 样本数量
M = X_train.shape[1]  # 维度/特征数量

size = math.ceil(np.sqrt(5 * np.sqrt(N)))  # 经验公式：决定输出层尺寸
print("训练样本个数:{}  测试样本个数:{}".format(N,X_test.shape[0]))
print("输出网格最佳边长为:",size)
max_iter = 200
som = MiniSom(size, size, M, sigma=3, learning_rate=0.5, 
              neighborhood_function='bubble')

# 初始化权重以跨越前两个主分量。这种初始化不依赖于随机过程，使训练过程收敛得更快。
#　强烈建议在初始化权重之前对数据进行规范化，并对训练数据使用相同的规范化。
som.pca_weights_init(X_train)
som.train_batch(X_train, 50000)  
#  som.win_map(X_train)  返回字典wm，其中wm[（i，j）]是一个列表，其中包含在位置i，j中映射的所有模式。
# (7, 3): [array([5. , 2. , 3.5, 1. ]), array([4.9, 2.4, 3.3, 1. ])

# som.labels_map返回字典wm，其中wm[（i，j）]是一个字典，它包含在位置i，j中映射的给定标签的样本数。{
# {(7, 3): Counter({1: 3}), (2, 2): Counter({2: 1}), (0, 3): Counter({2: 3})
win_map = som.labels_map(X_train,y_train)# 
```
###### 根据权重矩阵W,我们可以计算每个神经元 距离它的邻近神经元们的距离，计算好的矩阵就是U-Matrix
在矩阵中较小的值表示该节点与其邻近节点在输入空间靠得近因此，U-matrix可以看作输入空间中数据点概率密度在二维平面上的映射
```
heatmap = som.distance_map()  #生成U-Matrix
print(heatmap.shape)
plt.imshow(heatmap, cmap='bone_r')      #miniSom案例中用的pcolor函数,需要调整坐标
plt.colorbar()
```

![](https://upload-images.jianshu.io/upload_images/18339009-6bf0577ce7cedb54.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
plt.figure(figsize=(9, 9))
heatmap = som.distance_map()
plt.pcolor(heatmap, cmap='bone_r')  # plotting the distance map as background

# 定义不同标签的图案标记
markers = ['o', 's', 'D']
colors = ['C0', 'C1', 'C2']
category_color = {'setosa': 'C0',
                  'versicolor': 'C1',
                  'virginica': 'C2'}
for cnt, xx in enumerate(X_train):
    position = som.winner(xx)  # getting the winner
    # 在样本Heat的地方画上标记
    plt.plot(position[0]+.5,  position[1]+.5, markers[y_train[cnt]], markerfacecolor='None',
             markeredgecolor=colors[y_train[cnt]], markersize=12, markeredgewidth=2)
plt.axis([0, size, 0, size])
ax = plt.gca()
ax.invert_yaxis() #颠倒y轴方向
legend_elements = [Patch(facecolor=clr, edgecolor='w', label=l) 
                   for l, clr in category_color.items()]
plt.legend(handles=legend_elements, loc='center left', bbox_to_anchor=(1, .95))
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-4cec2a60540aaf9b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###### 不清楚每个格子的样本数量 ,如果有个类别落到同一个格子，看不出比例
```
label_name_map_number = {"setosa":0,"versicolor":1,"virginica":2}
from matplotlib.gridspec import GridSpec
plt.figure(figsize=(9, 9))
# 画非对称的子图
the_grid = GridSpec(size, size)
for position in win_map.keys():
    label_num = [win_map[position][label] 
                   for label in [0, 1, 2]] # 该位置各个类别的数量
    plt.subplot(the_grid[position[1], position[0]], aspect=1)
    patches, _ = plt.pie(label_num) 
    # 如果没有设置autopct,返回(patches, texts)如果设置autopct,返回(patches, texts, autotexts)
    # patches -- list --matplotlib.patches.Wedge对象 texts autotexts -- matplotlib.text.Text对象autotexts：列表。A是数字标签的Text实例列表。
    plt.text(position[0]/100, position[1]/100,  str(len(list(win_map[position].elements()))),
              color='black', fontdict={'weight': 'bold',  'size': 15}, va='center',ha='center')
plt.legend(patches, class_names, loc='center right', bbox_to_anchor=(-1,9), ncol=3)
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-b29b0e07468fc2be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###### Component Plane每个神经元对单个特征什么取值最敏感。W[:,:,i]则取出特征i对应的权值矩阵
```
W = som.get_weights()
plt.figure(figsize=(10, 10))
for i, f in enumerate(feature_names):
    plt.subplot(3, 3, i+1)
    plt.title(f)
    plt.imshow(W[:,:,i], cmap='coolwarm')
    plt.colorbar()
    plt.xticks(np.arange(size))
    plt.yticks(np.arange(size))
#plt.tight_layout()
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-861ad9af2e54ff41.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 3.异常值
```
from minisom import MiniSom
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.gridspec import GridSpec
from sklearn.datasets import make_blobs
from sklearn.preprocessing import scale
outliers_percentage = 0.35
inliers = 300
outliers = int(inliers * outliers_percentage)
data = make_blobs(centers=[[2, 2], [-2, -2]], cluster_std=[.3, .3], n_samples=inliers, random_state=0)[0]
#print(data.shape)  # (300, 2)
data = scale(data)
data = np.concatenate([data, (np.random.rand(outliers, 2)-.5)*4.])
#print(data.shape)  # (405, 2)
som = MiniSom(2, 1, data.shape[1], sigma=1, learning_rate=0.5, neighborhood_function='triangle', random_seed=10)
som.train_batch(data, 100, verbose=True)  # random training
```
设置阈值超过该值的即为异常
```
# 计算二范数
quantization_errors = np.linalg.norm(som.quantization(data) - data, axis=1)
# print(quantization_errors.shape)  (405,)
# 计算一个多维数组的任意百分比分位数
error_treshold = np.percentile(quantization_errors,  100*(1-outliers_percentage)+5)
# print(error_treshold) 0.3417397144318405  超过该值的即为异常
is_outlier = quantization_errors > error_treshold
#　print(is_outlier)  [False False False 
plt.hist(quantization_errors)
plt.axvline(error_treshold, color='k', linestyle='--')
plt.xlabel('error')
plt.ylabel('frequency')
```
![](https://upload-images.jianshu.io/upload_images/18339009-179f311d1efc78cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
plt.figure(figsize=(8, 8))
plt.scatter(data[~is_outlier, 0], data[~is_outlier, 1],
            label='inlier')
plt.scatter(data[is_outlier, 0], data[is_outlier, 1],
            label='outlier')
plt.legend()
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-6b5505f56e816c41.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 4.numpy
```
import numpy as np
import pylab as pl


class SOM(object):
    def __init__(self, X, output, iteration, batch_size):
        """
        :param X: 形状是N*D， 输入样本有N个,每个D维
        :param output: (n,m)一个元组，为输出层的形状是一个n*m的二维矩阵
        :param iteration:迭代次数
        :param batch_size:每次迭代时的样本数量
        初始化一个权值矩阵，形状为D*(n*m)，即有n*m权值向量，每个D维
        """
        self.X = X  
        self.output = output  
        self.iteration = iteration  
        self.batch_size = batch_size  
        self.W = np.random.rand(
            X.shape[1], output[0] * output[1])  
        print("W mat shape is", self.W.shape)

    def GetN(self, t):
        """
        :param t:时间t, 这里用迭代次数来表示时间
        :return: 返回一个整数，表示拓扑距离，时间越大，拓扑邻域越小
        """
        a = min(self.output)  
        return int(a-float(a)*t/self.iteration)  

    def Geteta(self, t, n):
        """
        :param t: 时间t, 这里用迭代次数来表示时间
        :param n: 拓扑距离
        :return: 返回学习率，
        """
        return np.power(np.e, -n)/(t+2)

    def updata_W(self, X, t, winner):
        """ 
        用于更新权值矩阵
        """
        N = self.GetN(t)  
        for x, i in enumerate(winner):  
            to_update = self.getneighbor(i[0], N)  
            for j in range(N+1):
                e = self.Geteta(t, j)
                for w in to_update[j]:
                    self.W[:, w] = np.add(
                        self.W[:, w], e*(X[x, :] - self.W[:, w]))

    def getneighbor(self, index, N):
        """
        :param index:获胜神经元的下标
        :param N: 邻域半径
        :return ans: 返回一个集合列表，分别是不同邻域半径内需要更新的神经元坐标
        """
        a, b = self.output
        length = a*b  

        def distence(index1, index2):
            i1_a, i1_b = index1 // a, index1 % b
            i2_a, i2_b = index2 // a, index2 % b
            return np.abs(i1_a - i2_a), np.abs(i1_b - i2_b)
        
        ans = [set() for i in range(N+1)]
        for i in range(length):
            
            dist_a, dist_b = distence(i, index)
            if dist_a <= N and dist_b <= N:  
                ans[max(dist_a, dist_b)].add(i)  
        return ans

    def train(self):
        """
        train_Y:训练样本与形状为batch_size*(n*m)
        winner:一个一维向量，batch_size个获胜神经元的下标
        :return:返回值是调整后的W
        """
        count = 0  
        while self.iteration > count:
            
            
            train_X = self.X[np.random.choice(
                self.X.shape[0], self.batch_size)]
            
            normal_W(self.W)
            
            normal_X(train_X)
            
            train_Y = train_X.dot(self.W)  
            
            winner = np.argmax(train_Y, axis=1).tolist()  
            
            self.updata_W(train_X, count, winner)
            count += 1
        return self.W

    def train_result(self):
        normal_X(self.X)  
        train_Y = self.X.dot(self.W)  
        
        winner = np.argmax(train_Y, axis=1).tolist()  
        print(winner)
        return winner


def normal_X(X):
    """
    :param X:二维矩阵，N*D，N个D维的数据
    :return: 将X归一化的结果
    """
    N, D = X.shape
    for i in range(N):
        temp = np.sum(np.multiply(X[i], X[i]))
        X[i] /= np.sqrt(temp)
    return X


def normal_W(W):
    """
    :param W:二维矩阵，D*(n*m)，D个n*m维的数据
    :return: 将W归一化的结果
    """
    for i in range(W.shape[1]):
        temp = np.sum(np.multiply(W[:, i], W[:, i]))
        W[:, i] /= np.sqrt(temp)
    return W




def draw(C):
    colValue = ['r', 'y', 'g', 'b', 'c', 'k', 'm']
    for i in range(len(C)):
        coo_X = []  
        coo_Y = []  
        for j in range(len(C[i])):
            coo_X.append(C[i][j][0])
            coo_Y.append(C[i][j][1])
        pl.scatter(coo_X, coo_Y, marker='x',
                   color=colValue[i % len(colValue)], label=i)

    pl.legend(loc='upper right')
    pl.show()



data = """
1,0.697,0.46,2,0.774,0.376,3,0.634,0.264,4,0.608,0.318,5,0.556,0.215,
6,0.403,0.237,7,0.481,0.149,8,0.437,0.211,9,0.666,0.091,10,0.243,0.267,
11,0.245,0.057,12,0.343,0.099,13,0.639,0.161,14,0.657,0.198,15,0.36,0.37,
16,0.593,0.042,17,0.719,0.103,18,0.359,0.188,19,0.339,0.241,20,0.282,0.257,
21,0.748,0.232,22,0.714,0.346,23,0.483,0.312,24,0.478,0.437,25,0.525,0.369,
26,0.751,0.489,27,0.532,0.472,28,0.473,0.376,29,0.725,0.445,30,0.446,0.459"""

a = data.split(',')
dataset = np.mat([[float(a[i]), float(a[i+1])] for i in range(1, len(a)-1, 3)])
dataset_old = dataset.copy()

som = SOM(dataset, (5, 5), 1, 30)
som.train()
res = som.train_result()  
classify = {}  
for i, win in enumerate(res):
    
    if not classify.get(win[0]):
        
        classify.setdefault(win[0], [i])
    else:
        
        classify[win[0]].append(i)
C = []  
D = []  
for i in classify.values():
    C.append(dataset_old[i].tolist())
    D.append(dataset[i].tolist())
draw(C) 
draw(D) 
```
