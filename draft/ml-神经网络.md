
# 反向传播(Back Propagation)
![image.png](https://upload-images.jianshu.io/upload_images/18339009-1739f2b72a6a9549.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
用样本的特征$x$，计算出神经网络中每个隐藏层节点的输出$a_i$以及输出层每个节点的输出$y_i$然后，我们按照下面的方法计算出每个节点的误差项$\delta_i$
>对于输出层节点i，
$$
\delta_{i}=y_{i}\left(1-y_{i}\right)\left(t_{i}-y_{i}\right)
$$
例如，节点8
$\delta_{8}=y_{1}\left(1-y_{1}\right)\left(t_{1}-y_{1}\right)$

>对于隐藏层节点
$\delta_{i}=a_{i}\left(1-a_{i}\right) \sum_{k \in \text {outputs}} w_{k i} \delta_{k}$
其中，$a_i$是节点的输出值，$w_{ki}$是节点到它的下一层节点k的连接的权重，$\delta_k$是节点i的下一层节点k的误差项。
例如，对于隐藏层节点4来说，计算方法如下：$\delta_{4}=a_{4}\left(1-a_{4}\right)\left(w_{84} \delta_{8}+w_{94} \delta_{9}\right)$

>最后，更新每个连接上的权值：
$w_{j i} \leftarrow w_{j i}+\eta \delta_{j} x_{j i}$
其中，$w_{ji}$是节点i到节点j的权重，$\eta$是一个成为学习速率的常数，$\delta_j$是节点j的误差项，$x_{ji}$是节点i传递给节点j的输入。例如，权重$w_{84}$的更新方法如下：
$w_{84} \leftarrow w_{84}+\eta \delta_{8} a_{4}$



# 激活函数
>如果不用激励函数（其实相当于激励函数是f(x) = x），在这种情况下你每一层节点的输入都是上层输出的线性函数，很容易验证，无论你神经网络有多少层，输出都是输入的线性组合，与没有隐藏层效果相当，这种情况就是最原始的感知机（Perceptron）了，那么网络的逼近能力就相当有限。正因为上面的原因，我们决定引入非线性函数作为激励函数，这样深层神经网络表达能力就更加强大（不再是输入的线性组合，而是几乎可以逼近任意函数）
激活函数是用来加入非线性因素的，解决线性模型所不能解决的问题；
 在神经网络中，我们可以经常看到对于某一个隐藏层的节点，该节点的激活之计算一般分为两步：
（1） 输入该节点的值后先进行一个线性变换，计算出值
（2）再进行一个非线性变换，也就是经过一个非线性激活函数

常用的激活函数包括：sigmoid函数、tanh函数、ReLU函数。
### sigmoid函数：
当目标是解决一个二分类问题，可在输出层使用sigmoid函数进行二分类。

 该函数数将取值为(−∞,+∞) 的数映射到(0,1)之间，其公式以及函数图如下所示：
![image.png](https://upload-images.jianshu.io/upload_images/18339009-5bd89cfb15325e55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)
![](https://upload-images.jianshu.io/upload_images/18339009-ce4352e48c3d9899.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)
缺点
> 1.**当值非常大或者非常小是sigmoid的导数会趋近为0**，则会导致梯度消失
2.**函数的输出不是以0位均值，不便于下一层的计算。**这是不可取的，因为这会导致后一层的神经元将得到上一层输出的非0均值的信号作为输入。 产生的一个结果就是：**???????????如x>0, f=wTx+b 那么对w求局部梯度则都为正，这样在反向传播的过程中w要么都往正方向更新，要么都往负方向更新，导致有一种捆绑的效果，使得收敛缓慢。** 当然了，如果按batch去训练，那么那个batch可能得到不同的信号，所以这个问题还是可以缓解一下的。因此，非0均值这个问题虽然会产生一些不好的影响，不过跟上面提到的梯度消失问题相比还是要好很多的。
3.**解析式中含有幂运算**，计算机求解时相对来讲比较耗时。对于规模比较大的深度网络，这会较大地增加训练时间


### tanh函数
tanh读作Hyperbolic Tangent，它解决了Sigmoid函数的不是zero-centered输出问题，然而，梯度消失（gradient vanishing）的问题和幂运算的问题仍然存在。
该函数数将取值为(−∞,+∞) 的数映射到(-1,1)之间，其公式以及函数图如下所示：
![](https://upload-images.jianshu.io/upload_images/18339009-23dc6503cdc17b26.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)
![](https://upload-images.jianshu.io/upload_images/18339009-037a253b499d575d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)
### ReLU函数
ReLU函数又称为修正线性单元, 是一种分段线性函数，其弥补了sigmoid函数以及tanh函数的梯度消失问题。ReLU函数的公式以及图形如下：
![image.png](https://upload-images.jianshu.io/upload_images/18339009-f27b342366a20935.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 >优点： 1. 当输入大于0时，不存在梯度消失的问题2. 由于ReLU函数只有线性关系，所以计算速度要快很多
缺点：1.当输入小于0时，梯度为0，会产生梯度消失问题。

ReLU函数其实就是一个取最大值函数，注意这并不是全区间可导的，但是我们可以取sub-gradient，如上图所示。ReLU虽然简单，但却是近几年的重要成果，有以下几大优点：
1） 解决了gradient vanishing问题 (在正区间)
2）计算速度非常快，只需要判断输入是否大于0
3）收敛速度远快于sigmoid和tanh

ReLU也有几个需要特别注意的问题：
1）ReLU的输出不是zero-centered
2）Dead ReLU Problem，指的是某些神经元可能永远不会被激活，导致相应的参数永远不能被更新。有两个主要原因可能导致这种情况产生: (1) 非常不幸的参数初始化，这种情况比较少见 (2) learning rate太高导致在训练过程中参数更新太大，不幸使网络进入这种状态。解决方法是可以采用Xavier初始化方法，以及避免将learning rate设置太大或使用adagrad等自动调节learning rate的算法。

尽管存在这两个问题，ReLU目前仍是最常用的activation function，在搭建人工神经网络的时候推荐优先尝试！

#　深度学习中的正则化（参数范数惩罚：L1正则化、L2正则化；数据集增强；噪声添加；early stop；Dropout层）、正则化的介绍。
#　深度模型中的优化：参数初始化策略；自适应学习率算法（梯度下降、AdaGrad、RMSProp、Adam；优化算法的选择）；batch norm层（提出背景、解决什么问题、层在训练和测试阶段的计算公式）；layer norm层。
[https://www.jianshu.com/p/01a5ca060f07](https://www.jianshu.com/p/01a5ca060f07)

#　FastText的原理。
#　利用FastText模型进行文本分类。
[https://blog.csdn.net/yyy430/article/details/88419694#FastText%E4%BB%8B%E7%BB%8D](https://blog.csdn.net/yyy430/article/details/88419694#FastText%E4%BB%8B%E7%BB%8D)
[https://github.com/nicken/nlp_study_on_datawhale/blob/master/task_3/task-03.md](https://github.com/nicken/nlp_study_on_datawhale/blob/master/task_3/task-03.md)
