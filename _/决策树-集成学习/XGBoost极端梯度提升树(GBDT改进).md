- [ 原理](#head1)
	- [ 目标函数](#head2)
	- [ 基于决策树的目标函数](#head3)
	- [ 最优切分点划分算法](#head4)
		- [ 贪心算法](#head5)
		- [ 近似算法](#head6)
	- [ 加权分位数缩略图](#head7)
	- [ 稀疏感知算法](#head8)
- [ 工程实现](#head9)
	- [ 块结构设计](#head10)
	- [ 缓存访问优化算法](#head11)
	- [ “核外”块计算](#head12)
- [ 优缺点](#head13)
	- [ 优点](#head14)
	- [ 缺点](#head15)
- [ 相关问题](#head16)
	- [ GBDT中的梯度是什么对什么的梯度？](#head17)
	- [ 给一个有m个样本，n维特征的数据集，如果用LR算法，那么梯度是几维?](#head18)
	- [ $m*n$的数据集，如果用GBDT，那么梯度是几维？](#head19)
	- [ 使用XGBoost训练模型时，如果过拟合了怎么调参？](#head20)
	- [ XGBoost算法防止过拟合的方法有哪些？](#head21)
	- [ XGBoost为什么对缺失值不敏感？](#head22)
	- [ **XGBoost中的一棵树的停止生长条件**](#head23)
	- [ XGBoost可以做特征选择，它是如何评价特征重要性的？](#head24)
	- [ **XGBoost中如何对树进行剪枝**](#head25)
	- [ **XGBoost如何选择最佳分裂点？**](#head26)
	- [ XGBoost的延展性很好，怎么理解？](#head27)
- [ 代码](#head28)
	- [ sklearn](#head29)
	- [ numpy](#head30)


eXtreme Gradient Boosting极端梯度提升树

GBDT是一种基于boosting集成思想的加法模型，训练时采用前向分布算法进行贪婪的学习，每次迭代都学习一棵CART树来拟合之前 t-1 棵树的预测结果与训练样本真实值的残差。

XGBoost的基本思想和GBDT相同，但是做了一些优化，如默认的缺失值处理，加入了二阶导数信息、正则项、列抽样，并且可以并行计算等。





**AdaBoost训练弱分类器关注的是那些被分错的样本**，AdaBoost每一次训练都是为了减少错误分类的样本。而**GBDT训练弱分类器关注的是残差，也就是上一个弱分类器的表现与完美答案之间的差距**，GBDT每一次训练分类器，都是为了减少这个差距，进而在残差减少（负梯度）的方向上建立一个新的模型。

x**gboost与gbdt比较大的不同就是目标函数的定义**，但这俩在策略上是类似的，都是聚焦残差（更准确的说， **xgboost其实是gbdt算法在工程上的一种实现方式**）

GBDT旨在通过**不断加入新的树最快速度降低残差**，而XGBoost则可以**人为定义损失函数**（可以是最小平方差、logistic loss function、hinge loss function或者人为定义的loss function），**只需要知道该loss function对参数的一阶、二阶导数便可以进行boosting**，其进一步增大了模型的泛化能力，其**贪婪法寻找添加树的结构以及loss function中的损失函数与正则项**等一系列策略也使得XGBoost预测更准确。

# <span id="head1"> 原理</span>

![](https://upload-images.jianshu.io/upload_images/18339009-7d5df4788d81fde7?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

想预测，这一家子人中每个人想玩游戏的意愿值。我们用xgboost解决这个问题，就是我先训练出来第一棵决策树， 预测了一下小男孩想玩游戏的意愿是2， 然后发现离标准答案差一些，又训练出来了第二棵决策树， 预测了一下小男孩想玩游戏的意愿是0.9， 那么两个相加就是最终的答案2.9。这个其实就接近了标准答案。所以xgboost是训练出来的弱分类结果进行累加就是最终的结论。

## <span id="head2"> 目标函数</span>

我们知道 XGBoost 是由 k 个基模型组成的一个加法运算式：

![](https://upload-images.jianshu.io/upload_images/18339009-fb797ed4011cc1f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

我们知道模型的预测精度由模型的偏差和方差共同决定，损失函数代表了模型的偏差，想要方差小则需要简单的模型，所以目标函数由模型的损失函数 与抑制模型复杂度的正则项组成，所以我们有：

![](https://upload-images.jianshu.io/upload_images/18339009-925dbd58f74a0b28.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-829d2a24b84c8f4a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

**Xgboost系统的每次迭代都会构建一颗新的决策树，决策树通过与真实值之间残差来构建**。

![](https://upload-images.jianshu.io/upload_images/18339009-a3e8314d98b4ebee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 
![一阶导和二阶导的值是常数](https://upload-images.jianshu.io/upload_images/18339009-2b03624dfc959f20.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

## <span id="head3"> 基于决策树的目标函数</span>

我们知道 Xgboost 的基模型不仅支持决策树，还支持线性模型，这里我们**主要介绍基于决策树的目标函数**。

**通过决策树遍历样本，其实就是在遍历叶子节点**

![](https://upload-images.jianshu.io/upload_images/18339009-1575597fe6531737.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-f28eeabfb584928a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-6da42699cdae1238.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

这个就是基于决策树的xgboost模型的目标函数最终版本了，这里的G和H的求法，就需要明确的给出损失函数来， 然后求一阶导和二阶导，然后代入样本值即得出。

我们之前不是说建立一个树就是让残差尽可能的小吗？到底小多少呢？这个obj就是衡量这个的， 可以叫做**结构分数。就类似于基尼系数那样对树结构**打分的一个函数。**这个分数越小，代表这个树的结物越好**

![](https://upload-images.jianshu.io/upload_images/18339009-bd395caa9b931d54.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

假设建了右边的那棵树，那么每个样本都对应到了叶子节点上去，每一个样本都会对应一个g和h， 那么我们遍历叶子节点，就会得到G和H，然后累加就可以得到这棵树的结构分数obj（由于每个样本的g和h没有啥关系，所以可以并行计算，这样就可以加速训练了，而且，g和h是不依赖于损失函数的形式的，只要这个损失函数二次可微就可以了）

## <span id="head4"> 最优切分点划分算法</span>

在决策树的生长过程中，一个非常关键的问题是**如何找到叶子的节点的最优切分点**，Xgboost 支持两种分裂节点的方法——贪心算法和近似算法。

决策树的建树过程，ID3也好，C4.5或者是CART，它们寻找最优切分点的时候都有一个计算收益的东西，分别是信息增益，信息增益比和基尼系数。而xgboost这里的切分， 其实也有一个类似于这三个的东西来计算每个特征点上分裂之后的收益。

### <span id="head5"> 贪心算法</span>

**遍历所有特征以及所有分割点，每次选最好的那个**

1.  从深度为 0 的树开始，对每个叶节点枚举所有的可用特征；

2.  针对每个特征，把属于该节点的训练样本根据该特征值进行升序排列，通过线性扫描的方式来决定该特征的最佳分裂点，并记录该特征的分裂收益；（可以并行计算）

3.  选择收益最大的特征作为分裂特征，用该特征的最佳分裂点作为分裂位置，在该节点上分裂出左右两个新的叶节点，并为每个新节点关联对应的样本集

4.  回到第 1 步，递归执行到满足特定条件为止

![image](https://upload-images.jianshu.io/upload_images/18339009-fdd91a108fc439a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

假设我有这一家子人样本，每个人有性别，年龄，兴趣等几个特征，我想用xgboost建立一棵树预测玩游戏的意愿值。首先，五个人都聚集在根节点上，现在就考虑根节点分叉，我们就遍历每个特征，对于当前的特征，我们要去寻找最优切分点以及带来的最大收益，比如当前特征是年龄，我们需要知道两点：

*   按照年龄分是否有效，也就是是否减少了obj的值

*   如果真的可以分，特征收益比较大

那么我们从哪个年龄点分开呢？我们可以这样做，首先我们先把年龄进行一个排序， 如下图：

![](https://upload-images.jianshu.io/upload_images/18339009-aa5e2f1b526047c0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

按照这个图从左至右扫描，我们就可以找出所有的切分点a， 对于每一个切分点a，计算出分割的梯度和和 。然后用上面的公式计算出每个分割方案的分数。然后哪个最大，就是年龄特征的最优切分点，而最大值就是年龄这个特征的最大信息收益。

普通的决策树在切分的时候并不考虑树的复杂度，所以才有了后续的剪枝操作。而xgboost在切分的时候就已经考虑了树的复杂度（obj里面看到那个了吗）。所以，它不需要进行单独的剪枝操作。

### <span id="head6"> 近似算法</span>

**贪婪算法可以的到最优解，但当数据量太大时则无法读入内存进行计算**，近似算法主要针对贪婪算法这一缺点给出了近似最优解。

**对于每个特征，只考察分位点可以减少计算复杂度。**该算法会首先根据特征分布的分位数提出候选划分点，然后将连续型特征映射到由这些候选点划分的桶中，然后聚合统计信息找到所有区间的最佳分裂点。

在提出候选切分点时有两种策略：

*   Global：学习每棵树前就提出候选切分点，并在每次分裂时都采用这种分割；

*   Local：每次分裂前将重新提出候选切分点。

直观上来看，**Local 策略需要更多的计算步骤，而 Global 策略因为节点没有划分所以需要更多的候选点。**

下图给出不同种分裂策略的 AUC 变换曲线，横坐标为迭代次数，纵坐标为测试集 AUC，eps 为近似算法的精度，其倒数为桶的数量。

![image](https://upload-images.jianshu.io/upload_images/18339009-0dded432095145a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

我们可以看到 Global 策略在候选点数多时（eps 小）可以和 Local 策略在候选点少时（eps 大）具有相似的精度。此外我们还发现，在 eps 取值合理的情况下，分位数策略可以获得与贪婪算法相同的精度。

![image](https://upload-images.jianshu.io/upload_images/18339009-f9e7f15d8e76296e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  下图给出近似算法的具体例子，以三分位为例：

![image](https://upload-images.jianshu.io/upload_images/18339009-6f5ec79ab9b16e58.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

## <span id="head7"> 加权分位数缩略图</span>

![image](https://upload-images.jianshu.io/upload_images/18339009-5b55b8a923acdd2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

## <span id="head8"> 稀疏感知算法</span>

在决策树的第一篇文章中我们介绍 CART 树在应对数据缺失时的分裂策略，XGBoost 也给出了其解决方案。

XGBoost 在构建树的节点过程中只考虑非缺失值的数据遍历，而为每个节点增加了一个缺省方向，当样本相应的特征值缺失时，可以被归类到缺省方向上，最优的缺省方向可以从数据中学到。至于如何学到缺省值的分支，其实很简单，分别枚举特征缺省的样本归为左右分支后的增益，选择增益最大的枚举项即为最优缺省方向。

在构建树的过程中需要枚举特征缺失的样本，乍一看该算法的计算量增加了一倍，但其实该算法在构建树的过程中只考虑了特征未缺失的样本遍历，而特征值缺失的样本无需遍历只需直接分配到左右节点，故算法所需遍历的样本量减少，下图可以看到稀疏感知算法比 basic 算法速度块了超过 50 倍。  ![image](https://upload-images.jianshu.io/upload_images/18339009-18aba3ee4021d550.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# <span id="head9"> 工程实现</span>

### <span id="head10"> 块结构设计</span>

我们知道，决策树的学习最耗时的一个步骤就是在每次寻找最佳分裂点是都需要对特征的值进行排序。而 XGBoost 在训练之前对根据特征对数据进行了排序，然后保存到块结构中，并在每个块结构中都采用了稀疏矩阵存储格式（Compressed Sparse Columns Format，CSC）进行存储，后面的训练过程中会重复地使用块结构，可以大大减小计算量。

> 每一个块结构包括一个或多个已经排序好的特征；  
>
> 缺失特征值将不进行排序；  
>
> 每个特征会存储指向样本梯度统计值的索引，方便计算一阶导和二阶导数值； 

![image](https://upload-images.jianshu.io/upload_images/18339009-86e4585e5c73d2e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

这种块结构存储的特征之间相互独立，方便计算机进行并行计算。在对节点进行分裂时需要选择增益最大的特征作为分裂，这时各个特征的增益计算可以同时进行，这也是 Xgboost 能够实现分布式或者多线程计算的原因。

### <span id="head11"> 缓存访问优化算法</span>

块结构的设计可以减少节点分裂时的计算量，但特征值通过索引访问样本梯度统计值的设计会导致访问操作的内存空间不连续，这样会造成缓存命中率低，从而影响到算法的效率。

为了解决缓存命中率低的问题，XGBoost 提出了缓存访问优化算法：为每个线程分配一个连续的缓存区，将需要的梯度信息存放在缓冲区中，这样就是实现了非连续空间到连续空间的转换，提高了算法效率。

此外适当调整块大小，也可以有助于缓存优化。

### <span id="head12"> “核外”块计算</span>

当数据量过大时无法将数据全部加载到内存中，只能先将无法加载到内存中的数据暂存到硬盘中，直到需要时再进行加载计算，而这种操作必然涉及到因内存与硬盘速度不同而造成的资源浪费和性能瓶颈。为了解决这个问题，XGBoost **独立一个线程专门用于从硬盘读入数据，以实现处理数据和读入数据同时进行。**

此外，XGBoost 还用了两种方法来降低硬盘读写的开销：

> 块压缩：对 Block 进行按列压缩，并在读取时进行解压；  
>
> 块拆分：将每个块存储到不同的磁盘中，从多个磁盘读取可以增加吞吐量。

# <span id="head13"> 优缺点</span>

GBDT是机器学习算法，XGBoost是该算法的工程实现。

## <span id="head14"> 优点</span>

1.  精度更高，GBDT 只用到一阶泰勒展开，而 **XGBoost 对损失函数进行了二阶泰勒展开**（梯度下降的更快更准）。XGBoost 引入二阶导一方面是为了**增加精度**，另一方面也是为了能够**自定义损失函数**，二阶泰勒展开可以近似大量损失函数；
2.  **灵活性**更强，GBDT 以 CART 作为基分类器，**XGBoost 不仅支持 CART 还支持线性分类器**，（使用线性分类器的 XGBoost 相当于带 L1 和 L2 正则化项的逻辑斯蒂回归（分类问题）或者线性回归（回归问题））。此外，XGBoost 工具**支持自定义损失函数，只需函数支持一阶和二阶求导；**
3.  **正则化**，XGBoost **在目标函数中加入了正则项，用于控制模型的复杂度**。正则项里包含了树的叶子节点个数、叶子节点权重的 L2 范式。正则项降低了模型的方差，使学习出来的模型更加简单，有助于防止过拟合；
4.  Shrinkage（缩减）：相当于学习速率。每次迭代，增加新的模型，在前面成上一个小于1的系数，降低优化的速度，每次走一小步逐步逼近最优模型比每次走一大步逼近更加容易避免过拟合现象；
5.  列抽样，传统的GBDT在每轮迭代时使用全部的数据，XGBoost 借鉴了随机森林的做法，**对数据进行列抽样，不仅能降低过拟合，还能减少计算；**
6.  缺失值处理，传统的GBDT没有设计对缺失值进行处理，XGBoost 采用的**稀疏感知算法极大的加快了节点分裂的速度；**
7.  **特征维度上的并行化**。XGBoost预先将每个特征按特征值排好序，存储为块结构，分裂结点时可以采用多线程并行查找每个特征的最佳分割点，极大提升训练速度。

## <span id="head15"> 缺点</span>

1.  虽然利用预排序和近似算法可以降低寻找最佳分裂点的计算量，但在节点**分裂过程中仍需要遍历数据集**；
2.  **预排序过程的空间复杂度过高**，不仅需要**存储特征值，还需要存储特征对应样本的梯度统计值的索引**，相当于消耗了两倍的内存。
3.  Boost是一个串行过程，不好并行化，而且计算复杂度高，同时不太适合高维稀疏特征。



# <span id="head16"> 相关问题</span>

## <span id="head17"> GBDT中的梯度是什么对什么的梯度？</span>

当前损失函数$L(y_i, F(x))$对树​ $F(x_i)$梯度。

## <span id="head18"> 给一个有m个样本，n维特征的数据集，如果用LR算法，那么梯度是几维?</span>

对权重w有n维，对bias有1维，因此是n+1维。

## <span id="head19"> $m*n$的数据集，如果用GBDT，那么梯度是几维？</span>

因为GBDT求导是关于所有生成树的和，而所有生成树的和是一维的数值，又总共有m个sample，所以梯度是m维的。

## <span id="head20"> 使用XGBoost训练模型时，如果过拟合了怎么调参？</span>

- 控制模型的复杂度。包括max_depth,min_child_weight,gamma 等参数。
- 增加随机性，从而使得模型在训练时对于噪音不敏感。包括subsample,colsample_bytree。
- 减小learning rate，但需要同时增加estimator 参数。



## <span id="head21"> XGBoost算法防止过拟合的方法有哪些？</span>

- 在目标函数中添加了正则化。**叶子节点个数+叶子节点权重**的L2正则化。

- 列抽样。训练时只使用一部分的特征。

- 子采样。每轮计算可以不使用全部样本，类似bagging。

- early stopping。如果经过固定的迭代次数后，并没有在验证集上改善性能，停止训练过程。

- shrinkage。调小学习率增加树的数量，为了给后面的训练留出更多的空间。

  

## <span id="head22"> XGBoost为什么对缺失值不敏感？</span>

一些**涉及到对样本距离的度量的模型**，如SVM和KNN，如果缺失值处理不当，最终会导致模型预测效果很差。

而**树模型对缺失值的敏感度低**，大部分时候可以在数据缺失时时使用。原因就是，一棵树中每个结点在分裂时，寻找的是某个特征的最佳分裂点（特征值），完全可以不考虑存在特征值缺失的样本，也就是说，如果某些样本缺失的特征值缺失，对寻找最佳分割点的影响不是很大。

XGBoost的具体处理方法为：

- 在某列特征上寻找分裂节点时，不会对缺失的样本进行遍历，只会对非缺失样本上的特征值进行遍历，这样减少了为稀疏离散特征寻找分裂节点的时间开销。
- 另外，为了保证完备性，对于含有缺失值的样本，会分别把它分配到左叶子节点和右叶子节点，然后再选择分裂后增益最大的那个方向，作为预测时特征值缺失样本的默认分支方向。
- 如果训练集中没有缺失值，但是测试集中有，那么默认将缺失值划分到右叶子节点方向。



## <span id="head23"> **XGBoost中的一棵树的停止生长条件**</span>

- 当树达到最大深度时，停止建树，因为树的深度太深容易出现过拟合，这里需要设置一个超参数max_depth。
- 当新引入的一次分裂所带来的增益Gain<0时，放弃当前的分裂。这是训练损失和模型结构复杂度的博弈过程。
- 当引入一次分裂后，重新计算新生成的左、右两个叶子结点的样本权重和。如果任一个叶子结点的样本权重低于某一个阈值，也会放弃此次分裂。这涉及到一个超参数:最小样本权重和，是指如果一个叶子节点包含的样本数量太少也会放弃分裂，防止树分的太细。

## <span id="head24"> XGBoost可以做特征选择，它是如何评价特征重要性的？</span>

XGBoost中有三个参数可以用于评估特征重要性：

- **weight** ：该特征在所有树中被用作分割样本的总次数。
- **gain** ：该特征在其出现过的所有树中产生的平均增益。
- **cover** ：该特征在其出现过的所有树中的平均覆盖范围。覆盖范围这里指的是一个特征用作分割点后，其影响的样本数量，即有多少样本经过该特征分割到两个子节点。



## <span id="head25"> **XGBoost中如何对树进行剪枝**</span>

- 在目标函数中增加了正则项：使用叶子结点的数目和叶子结点权重的L2模的平方，控制树的复杂度。
- 在结点分裂时，定义了一个阈值，如果分裂后目标函数的增益小于该阈值，则不分裂。
- 当引入一次分裂后，重新计算新生成的左、右两个叶子结点的样本权重和。如果任一个叶子结点的样本权重低于某一个阈值（最小样本权重和），也会放弃此次分裂。
- XGBoost 先从顶到底建立树直到最大深度，再从底到顶反向检查是否有不满足分裂条件的结点，进行剪枝。

## <span id="head26"> **XGBoost如何选择最佳分裂点？**</span>

XGBoost在训练前预先将特征按照特征值进行了排序，并存储为block结构，以后在结点分裂时可以重复使用该结构。

因此，可以采用特征并行的方法利用多个线程分别计算每个特征的最佳分割点，**根据每次分裂后产生的增益，最终选择增益最大的那个特征的特征值作为最佳分裂点**。如果在计算每个特征的最佳分割点时，对每个样本都进行遍历，计算复杂度会很大，这种全局扫描的方法并不适用大数据的场景。XGBoost还提供了一种直方图近似算法，对特征排序后仅选择常数个候选分裂位置作为候选分裂点，极大提升了结点分裂时的计算效率。

## <span id="head27"> XGBoost的延展性很好，怎么理解？</span>

可以有三个方面的延展性：

- **基分类器**：弱分类器可以支持CART决策树，也可以支持LR和Linear。
- **目标函数**：支持自定义loss function，只需要其二阶可导。因为需要用二阶泰勒展开，得到通用的目标函数形式。
- **学习方法**：Block结构支持并行化，支持 Out-of-core计算。

























# <span id="head28"> 代码</span>

## <span id="head29"> sklearn</span>



> Xgboost参数说明页面:[https://xgboost.readthedocs.io/en/latest/parameter.html](https://xgboost.readthedocs.io/en/latest/parameter.html)
> 
> Xgboost调参官方指南:[https://xgboost.readthedocs.io/en/latest/tutorials/param_tuning.html](https://xgboost.readthedocs.io/en/latest/tutorials/param_tuning.html)

*   booster: 决定了XGBoost使用的弱学习器类型，可以是默认的gbtree, 也就是CART决策树，还可以是线性弱学习器gblinear以及DART。一般来说，我们使用gbtree就可以了，不需要调参。

*   silent: silent=0时，不输出中间过程（默认），silent=1输出

*   nthread：nthread=-1时，使用全部CPU进行并行运算（默认），nthread=1用1个

*   scale_pos_weight：正样本的权重，在二分类任务中，当正负样本比例失衡时，设置正样本的权重，模型效果更好。例如，当正负样本比例为1:10时，scale_pos_weight=10。

*   n_estimatores：代表了我们决策树弱学习器的个数，关系到我们XGBoost模型的复杂度。n_estimators太小，容易欠拟合，n_estimators太大，模型会过于复杂，一般需要调参选择一个适中的数值。

*   early_stopping_rounds：在验证集上，当连续n次迭代，分数没有提高后，提前终止训练。

*   max_depth：树的深度，默认值为6，典型值3-10。值越大，越容易过拟合；值越小，越容易欠拟合。数据少或者特征少的时候可以不管这个值。如果模型样本量多，特征也多的情况下，需要限制这个最大深度，具体的取值一般要网格搜索调参。

*   min_child_weight：最小的子节点权重阈值，如果某个树节点的权重小于这个阈值，则不会再分裂子树，即这个树节点就是叶子节点。这里树节点的权重使用的是该节点所有样本的二阶导数的和，即XGBoost原理篇里面的:默认值为1。值越大，越容易欠拟合；值越小，越容易过拟合（值较大时，避免模型学习到局部的特殊样本）。

    这个值需要网格搜索寻找最优值，在sklearn GBDT里面，没有完全对应的参数，不过min_samples_split从另一个角度起到了阈值限制。

*   subsample：训练每棵树时，使用的数据占全部训练集的比例。默认值为1，典型值为0.5-1。子采样参数，这个也是不放回抽样。选择小于1的比例可以减少方差，即防止过拟合，但是会增加样本拟合的偏差，因此取值不能太低。初期可以取值1，如果发现过拟合后可以网格搜索调参找一个相对小一些的值。

*   colsample_bytree：训练每棵树时，使用的特征占全部特征的比例。默认值为1，典型值为0.5-1。防止overfitting。

*   colsample_bytree/colsample_bylevel/colsample_bynode: 这三个参数都是用于特征采样的，默认都是不做采样，即使用所有的特征建立决策树。colsample_bytree控制整棵树的特征采样比例，colsample_bylevel控制某一层的特征采样比例，而colsample_bynode控制某一个树节点的特征采样比例。比如我们一共64个特征，则假设colsample_bytree，colsample_bylevel和colsample_bynode都是0.5，则某一个树节点分裂时会随机采样8个特征来尝试分裂子树。

*   learning_rate：学习率，控制每次迭代更新权重时的步长，默认0.3。，调参：值越小，训练越慢。典型值为0.01-0.2。

*   objective：代表了我们要解决的问题是分类还是回归，或其他问题，以及对应的损失函数。一般我们只关心在分类和回归的时候使用的参数。在回归一般使用reg:squarederror ，即MSE均方误差。二分类问题一般使用binary:logistic, 多分类问题一般使用multi:softmax。

*   eval_metric： 回归任务(默认rmse)rmse，mae；分类任务(默认error)auc--roc，error--错误率（二分类， merror--错误率（多分类）， logloss--负对数似然函数（二分类），mlogloss--负对数似然函数（多分类）

*   gamma：惩罚项系数，指定节点分裂所需的最小损失函数下降值。XGBoost的决策树分裂所带来的损失减小阈值。也就是我们在尝试树结构分裂时，会尝试最大数下式：

    这个最大化后的值需要大于我们的gamma，才能继续分裂子树。这个值也需要网格搜索寻找最优值。

*   alpha：L1正则化系数，默认为1。

*   lambda： L2正则化系数，默认为1。

    上面这些参数都是需要调参的，不过**一般先调max_depth，min_child_weight和gamma**。如果发现有过拟合的情况下，再尝试调后面几个参数。

功能函数

*   载入数据：load_digits()

*   数据拆分：train_test_split()

*   建立模型：XGBClassifier()

*   模型训练：fit()

*   模型预测：predict()

*   性能度量：accuracy_score()

*   特征重要性：plot_importance()

```
 """paramet setting"""
 param = {
  'max_depth': 2,
  'eta': 1, 
  'silent': 1,
  'objective': 'binary:logistic'
 }
 watch_list = [(dtest, 'eval'), (dtrain, 'train')]  # 这个是观测的时候在什么上面的结果  观测集
 num_round = 5
 model = xgb.train(params=param, dtrain=dtrain, num_boost_round=num_round, evals=watch_list)
 然后就是预测：
 
 """预测"""
 pred = model.predict(dtest)    # 这里面表示的是正样本的概率是多少
 
 from sklearn.metrics import accuracy_score
 predict_label = [round(values) for values in pred]
 accuracy_score(labels, predict_label)   # 0.993
 模型的保存了解一下：
 
 """两种方式： 第一种， pickle的序列化和反序列化"""
 pickle.dump(model, open('./model/xgb1.pkl', 'wb'))
 model1 = pickle.load(open('./model/xgb1.pkl', 'rb'))
 model1.predict(dtest)
 
 """第二种模型的存储与导入方式 - sklearn的joblib"""
 from sklearn.externals import joblib
 joblib.dump(model, './model/xgb.pkl')
 model2 = joblib.load('./model/xgb.pkl')
 model2.predict(dtest)
 3.2 交叉验证 xgb.cv
 # 这是模型本身的参数
 param = {'max_depth':2, 'eta':1, 'silent':1, 'objective':'binary:logistic'}
 num_round = 5   # 这个是和训练相关的参数
 
 xgb.cv(param, dtrain, num_round, nfold=5, metrics={'error'}, seed=3)
 3.3 调整样本权重
 这个是针对样本不平衡的情况，可以在训练时设置样本的权重， 训练的时候设置fpreproc这个参数， 相当于在训练之前先对样本预处理。
 
 # 这个函数是说在训练之前，先做一个预处理，计算一下正负样本的个数，然后加一个权重,解决样本不平衡的问题
 def preproc(dtrain, dtest, param): 
  labels = dtrain.get_label()
  ratio = float(np.sum(labels==0)) / np.sum(labels==1)
  param['scale_pos_ratio'] = ratio
  return (dtrain, dtest, param)
 
 # 下面我们在做交叉验证， 指明fpreproc这个参数就可以调整样本权重
 xgb.cv(param, dtrain, num_round, nfold=5, metrics={'auc'}, seed=3, fpreproc=preproc)
 3.4 自定义目标函数（损失函数）
 如果在一个比赛中，人家给了自己的评判标准，那么这时候就需要用人家的这个评判标准，这时候需要修改xgboost的损失函数， 但是这时候请注意一定要提供一阶和二阶导数
 
 # 自定义目标函数（log似然损失），这个是逻辑回归的似然损失。 交叉验证
 # 注意： 需要提供一阶和二阶导数
 
 def logregobj(pred, dtrain):
  labels = dtrain.get_label()
  pred = 1.0 / (1+np.exp(-pred))    # sigmoid函数
  grad = pred - labels
  hess = pred * (1-pred)
  return grad, hess     # 返回一阶导数和二阶导数
 
 def evalerror(pred, dtrain):
  labels = dtrain.get_label()
  return 'error', float(sum(labels!=(pred>0.0)))/len(labels)
 训练的时候，把损失函数指定就可以了：
 
 param = {'max_depth':2, 'eta':1, 'silent':1}
 
 # 自定义目标函数训练
 model = xgb.train(param, dtrain, num_round, watch_list, logregobj, evalerror)
 
 # 交叉验证
 xgb.cv(param, dtrain, num_round, nfold=5, seed=3, obj=logregobj, feval=evalerror)
 3.5 用前n棵树做预测 ntree_limit
 太多的树可能发生过拟合，这时候我们可以指定前n棵树做预测, 预测的时候设置ntree_limit这个参数
 
 # 前1棵
 pred1 = model.predict(dtest, ntree_limit=1)
 evalerror(pred2, dtest)
 3.6 画出特征重要度 plot_importance
 from xgboost import plot_importance
 plot_importance(model, max_num_features=10)
 3.7 同样，也可以用sklearn的GridSearchCV调参
 from sklearn.model_selection import GridSearchCV
 from sklearn.model_selection import StratifiedKFold
 
 model = XGBClassifier()
 learning_rate = [0.0001, 0.001, 0.1, 0.2, 0.3]
 param_grid = dict(learning_rate=learning_rate)
 kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=7)
 grid_search = GridSearchCV(model, param_grid, scoring="neg_log_loss", n_jobs=-1, cv=kfold)
 grid_result = grid_search.fit(x_train, y_train)
 
 print("best: %f using %s" %(grid_result.best_score_, grid_result.best_params_))
 
 means = grid_result.cv_results_['mean_test_score']
 params = grid_result.cv_results_['params']
 
 for mean, param in zip(means, params):
  print("%f  with： %r" % (mean, param))
```

## <span id="head30"> numpy</span>
```
定义XGBoost单棵树模型如下：
class XGBoostTree(Tree):
    # 结点分裂
    def _split(self, y):
        col = int(np.shape(y)[1]/2)
        y, y_pred = y[:, :col], y[:, col:]
        return y, y_pred

    # 信息增益计算公式
    def _gain(self, y, y_pred):
        Gradient = np.power((y * self.loss.gradient(y, y_pred)).sum(), 2)
        Hessian = self.loss.hess(y, y_pred).sum()
        return 0.5 * (Gradient / Hessian)

    # 树分裂增益计算
    def _gain_by_taylor(self, y, y1, y2):
        # 结点分裂
        y, y_pred = self._split(y)
        y1, y1_pred = self._split(y1)
        y2, y2_pred = self._split(y2)

        true_gain = self._gain(y1, y1_pred)
        false_gain = self._gain(y2, y2_pred)
        gain = self._gain(y, y_pred)
        return true_gain + false_gain - gain

    # 叶子结点最优权重
    def _approximate_update(self, y):
        # y split into y, y_pred
        y, y_pred = self._split(y)
        # Newton's Method
        gradient = np.sum(y * self.loss.gradient(y, y_pred), axis=0)
        hessian = np.sum(self.loss.hess(y, y_pred), axis=0)
        update_approximation =  gradient / hessian

        return update_approximation

    # 树拟合方法
    def fit(self, X, y):
        self._impurity_calculation = self._gain_by_taylor
        self._leaf_value_calculation = self._approximate_update
        super(XGBoostTree, self).fit(X, y)
     然后根据前向分步算法定义XGBoost模型：
class XGBoost(object):

    def __init__(self, n_estimators=200, learning_rate=0.001, min_samples_split=2,
                 min_impurity=1e-7, max_depth=2):
        self.n_estimators = n_estimators            # Number of trees
        self.learning_rate = learning_rate          # Step size for weight update
        self.min_samples_split = min_samples_split  # The minimum n of sampels to justify split
        self.min_impurity = min_impurity            # Minimum variance reduction to continue
        self.max_depth = max_depth                  # Maximum depth for tree


        # 交叉熵损失
        self.loss = LogisticLoss()

        # 初始化模型
        self.estimators = []
        # 前向分步训练
        for _ in range(n_estimators):
            tree = XGBoostTree(
                    min_samples_split=self.min_samples_split,
                    min_impurity=min_impurity,
                    max_depth=self.max_depth,
                    loss=self.loss)

            self.estimators.append(tree)

    def fit(self, X, y):
        y = to_categorical(y)

        y_pred = np.zeros(np.shape(y))
        for i in range(self.n_estimators):
            tree = self.trees[i]
            y_and_pred = np.concatenate((y, y_pred), axis=1)
            tree.fit(X, y_and_pred)
            update_pred = tree.predict(X)
            y_pred -= np.multiply(self.learning_rate, update_pred)

    def predict(self, X):
        y_pred = None
        # 预测
        for tree in self.estimators:
            update_pred = tree.predict(X)
            if y_pred is None:
                y_pred = np.zeros_like(update_pred)
            y_pred -= np.multiply(self.learning_rate, update_pred)

        y_pred = np.exp(y_pred) / np.sum(np.exp(y_pred), axis=1, keepdims=True)
        # 将概率预测转换为标签
        y_pred = np.argmax(y_pred, axis=1)
        return y_pred
     使用sklearn数据作为示例：
from sklearn import datasets
data = datasets.load_iris()
X = data.data
y = data.target

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, seed=2)  

clf = XGBoost()
clf.fit(X_train, y_train)
y_pred = clf.predict(X_test)

accuracy = accuracy_score(y_test, y_pred)
print ("Accuracy:", accuracy)
```
