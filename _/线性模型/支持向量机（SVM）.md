- [ SVM 原理](#head1)
	- [ 理解SVM：第一层](#head2)
		- [ 函数间隔与几何间隔](#head3)
		- [ 最大间隔分类器的定义](#head4)
		- [最大间隔损失函数Hinge loss](#head5)
	- [ 深入SVM：第二层](#head6)
		- [ 从线性可分到线性不可分](#head7)
		- [1.3.2 核函数Kernel](#head8)
- [SVM 为什么采用间隔最大化](#head9)
- [ SVM的目标（硬间隔）](#head10)
- [ 求解目标（硬间隔）](#head11)
- [为什么要将求解 SVM 的原始问题转换为其对偶问题](#head12)
- [为什么 SVM 要引入核函数](#head13)
- [ 为什么SVM对缺失数据敏感](#head14)
- [SVM 核函数之间的区别](#head15)
- [ SVM如何处理多分类问题？](#head16)
- [ SVM的特点](#head17)
- [ SVM和LR的区别](#head18)
	- [ 同](#head19)
	- [ 不同](#head20)
- [ 问题](#head21)
- [ sklearn](#head22)
	- [ 导入库](#head23)
	- [ 导入数据](#head24)
	- [ 拆分数据集为训练集合和测试集合](#head25)
	- [ 特征量化](#head26)
	- [ 适配SVM到训练集合](#head27)
	- [ 预测测试集合结果](#head28)
	- [ 创建混淆矩阵](#head29)
	- [ 训练集合结果可视化](#head30)
	- [ 测试集合结果可视化](#head31)


支持向量机既**可用于回归也可用于分类**。
![](https://upload-images.jianshu.io/upload_images/18339009-178802060b021361?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# <span id="head1"> SVM 原理</span>

SVM 是一种**二类分类模型**。它的基本模型是在特征空间中**寻找间隔最大化的分离超平面**的线性分类器。

*   当训练样本**线性可分时，通过硬间隔最大化**，学习一个线性分类器，即**线性可分支持向量机**；
*   当训练数据**近似线性可分时，引入松弛变量，通过软间隔最大化**，学习一个线性分类器，即**线性支持向量机**；
*   当训练数据**线性不可分时，通过使用核技巧及软间隔最大化**，学习**非线性支持向量机**。

以上各种情况下的数学推到应当掌握，硬间隔最大化（几何间隔）、学习的对偶问题、软间隔最大化（引入松弛变量）、非线性支持向量机（核技巧）。



### <span id="head2"> 理解SVM：第一层</span>

支持向量机，因其英文名为support vector machine，故一般简称SVM，通俗来讲，它是一种二类分类模型，其基本模型定义为特征空间上的间隔最大的线性分类器，其学习策略便是间隔最大化，最终可转化为一个凸二次规划问题的求解。

**线性分类器：**给定一些数据点，它们分别属于两个不同的类，现在要找到一个线性分类器把这些数据分成两类。如果用x表示数据点，用y表示类别（y可以取1或者0，分别代表两个不同的类），一个线性分类器的学习目标便是要在n维的数据空间中找到一个超平面（hyper plane），这个超平面的方程可以表示为（ wT中的T代表转置）：

![](https://latex.codecogs.com/gif.latex?w^Tx+b=0)

这里可以查看我之前的逻辑回归章节回顾：[点击打开](https://github.com/NLP-LOVE/ML-NLP/blob/master/Machine%20Learning/2.Logistics%20Regression/2.Logistics%20Regression.md)

这个超平面可以用分类函数  ![](https://latex.codecogs.com/gif.latex?f(x)=w^Tx+b)表示，当f(x) 等于0的时候，x便是位于超平面上的点，而f(x)大于0的点对应 y=1 的数据点，f(x)小于0的点对应y=-1的点，如下图所示：

![](http://wx4.sinaimg.cn/mw690/00630Defly1g4w7oendezj30gz0aojrq.jpg)

#### <span id="head3"> 函数间隔与几何间隔</span>

在超平面$w*x+b=0$确定的情况下，$|w*x+b|$能够表示点x到距离超平面的远近，而通过观察$w*x+b$的符号与类标记y的符号是否一致可判断分类是否正确，所以，可以用$(y*(w*x+b))$的正负性来判定或表示分类的正确性。于此，我们便引出了**函数间隔**（functional margin）的概念。

函数间隔公式： ![](https://latex.codecogs.com/gif.latex?\gamma=y(w^Tx+b)=yf(x))

而超平面(w，b)关于数据集T中所有样本点(xi，yi)的函数间隔最小值（其中，x是特征，y是结果标签，i表示第i个样本），便为超平面(w, b)关于训练数据集T的函数间隔：

![](https://latex.codecogs.com/gif.latex?\gamma=min\gamma_{}i(i=1,...n))

但这样定义的函数间隔有问题，即如果成比例的改变w和b（如将它们改成2w和2b），则函数间隔的值f(x)却变成了原来的2倍（虽然此时超平面没有改变），所以只有函数间隔还远远不够。

**几何间隔**

事实上，我们可以对法向量w加些约束条件，从而引出真正定义点到超平面的距离--几何间隔（geometrical margin）的概念。假定对于一个点 x ，令其垂直投影到超平面上的对应点为 x0 ，w 是垂直于超平面的一个向量，$\gamma$为样本x到超平面的距离，如下图所示：

![](https://julyedu-img.oss-cn-beijing.aliyuncs.com/quesbase64153146493788072777.png)

这里我直接给出几何间隔的公式，详细推到请查看博文：[点击进入](https://blog.csdn.net/v_july_v/article/details/7624837)

几何间隔： ![](https://latex.codecogs.com/gif.latex?\gamma^{'}=\frac{\gamma}{||w||})

从上述函数间隔和几何间隔的定义可以看出：几何间隔就是**函数间隔除以||w||**，而且函数间隔$y*(wx+b) = y*f(x)$实际上就是|f(x)|，只是人为定义的一个间隔度量，而几何间隔|f(x)|/||w||才是直观上的点到超平面的距离。

#### <span id="head4"> 最大间隔分类器的定义</span>

对一个数据点进行分类，**当超平面离数据点的“间隔”越大，分类的确信度（confidence）也越**大。所以，为了使得分类的确信度尽量高，需要让所选择的超平面能够最大化这个“间隔”值。这个间隔就是下图中的Gap的一半。

![](http://wx4.sinaimg.cn/mw690/00630Defly1g4w7q9cnymj30dt09mglu.jpg)

通过由前面的分析可知：函数间隔不适合用来最大化间隔值，因为在超平面固定以后，可以等比例地缩放w的长度和b的值，这样可以使得  ![](https://latex.codecogs.com/gif.latex?f(x)=w^Tx+b)的值任意大，亦即函数间隔可以在超平面保持不变的情况下被取得任意大。但几何间隔因为除上了，使得在缩放w和b的时候几何间隔的值是不会改变的，它只随着超平面的变动而变动，因此，这是更加合适的一个间隔。换言之，这里要找的最大间隔分类超平面中的**“间隔”指的是几何间隔。**

如下图所示，中间的实线便是寻找到的最优超平面（Optimal Hyper Plane），其到两条虚线边界的距离相等，这个距离便是几何间隔，两条虚线间隔边界之间的距离等于2倍几何间隔，而虚线间隔边界上的点则是支持向量。由于这些支持向量刚好在虚线间隔边界上，所以它们满足 ![](https://latex.codecogs.com/gif.latex?y(w_Tx+b)=1)，对于所有不是支持向量的点，则显然有 ![](https://latex.codecogs.com/gif.latex?y(w_Tx+b)>1)。

![](http://wx2.sinaimg.cn/mw690/00630Defly1g4w7r9zvunj30o60hdaar.jpg)

OK，到此为止，算是了解到了SVM的第一层，对于那些只关心怎么用SVM的朋友便已足够，不必再更进一层深究其更深的原理。

#### <span id="head5">最大间隔损失函数Hinge loss</span>

SVM 求解使通过建立二次规划原始问题，引入拉格朗日乘子法，然后转换成对偶的形式去求解，这是一种理论非常充实的解法。这里换一种角度来思考，在机器学习领域，一般的做法是经验风险最小化 （empirical risk minimization,ERM），即构建假设函数（Hypothesis）为输入输出间的映射，然后采用损失函数来衡量模型的优劣。求得使损失最小化的模型即为最优的假设函数，采用不同的损失函数也会得到不同的机器学习算法。SVM采用的就是Hinge Loss，用于“最大间隔(max-margin)”分类。

![](https://latex.codecogs.com/gif.latex?L_i=\sum_{j\neq_{}t_i}max(0,f(x_i,W)_j-(f(x_i,W)_{y_i}-\bigtriangleup)))

- 对于训练集中的第i个数据xi
- 在W下会有一个得分结果向量f(xi,W)
- 第j类的得分为我们记作f(xi,W)j 

要理解这个公式，首先先看下面这张图片：

![](http://wx1.sinaimg.cn/mw690/00630Defly1g4w5ezjr64j30se03pmy6.jpg)

1. 在生活中我们都会认为没有威胁的才是最好的，比如拿成绩来说，自己考了第一名99分，而第二名紧随其后98分，那么就会有不安全的感觉，就会认为那家伙随时都有可能超过我。如果第二名是85分，那就会感觉安全多了，第二名需要花费很大的力气才能赶上自己。拿这个例子套到上面这幅图也是一样的。
2. 上面这幅图delta左边的红点是一个**安全警戒线**，什么意思呢？也就是说**预测错误得分**超过这个安全警戒线就会得到一个惩罚权重，让这个预测错误值退回到安全警戒线以外，这样才能够保证预测正确的结果具有唯一性。
3. 对应到公式中， ![](https://latex.codecogs.com/gif.latex?f(x_i,W)_j)就是错误分类的得分。后面一项就是 **正确得分 - delta = 安全警戒线值**，两项的差代表的就是惩罚权重，越接近正确得分，权重越大。当错误得分在警戒线以外时，两项相减得到负数，那么损失函数的最大值是0，也就是没有损失。
4. 一直往复循环训练数据，直到最小化损失函数为止，也就找到了分类超平面。

### <span id="head6"> 深入SVM：第二层</span>

#### <span id="head7"> 从线性可分到线性不可分</span>

接着考虑之前得到的目标函数(令函数间隔=1)：

![](https://latex.codecogs.com/gif.latex?max\frac{1}{||w||}s.t.,y_i(w^Tx_i+b)\ge1,i=1,...,n)

**转换为对偶问题**，解释一下什么是对偶问题，对偶问题是实质相同但从不同角度提出不同提法的一对问题。

由于求  ![](https://latex.codecogs.com/gif.latex?\frac{1}{||w||})的最大值相当于求  ![](https://latex.codecogs.com/gif.latex?\frac{1}{2}||w||^2)的最小值，所以上述目标函数等价于（w由分母变成分子，从而也有原来的max问题变为min问题，很明显，两者问题等价）：

![](https://latex.codecogs.com/gif.latex?min\frac{1}{2}||w||^2s.t.,y_i(w^Tx_i+b)\ge1,i=1,...,n)

因为现在的目标函数是二次的，约束条件是线性的，所以它是一个凸二次规划问题。这个问题可以用现成的QP (Quadratic Programming) 优化包进行求解。一言以蔽之：在一定的约束条件下，目标最优，损失最小。

此外，由于这个问题的特殊结构，还可以通过拉格朗日对偶性（Lagrange Duality）变换到对偶变量 (dual variable) 的优化问题，即通过求解与原问题等价的对偶问题（dual problem）得到原始问题的最优解，这就是线性可分条件下支持向量机的对偶算法，这样做的优点在于：**一者对偶问题往往更容易求解；二者可以自然的引入核函数，进而推广到非线性分类问题。**

详细过程请参考文章末尾给出的参考链接。

#### <span id="head8">1.3.2 核函数Kernel</span>

事实上，大部分时候数据并不是线性可分的，这个时候满足这样条件的超平面就根本不存在。在上文中，我们已经了解到了SVM处理线性可分的情况，那对于非线性的数据SVM咋处理呢？对于非线性的情况，SVM 的处理方法是选择一个核函数 κ(⋅,⋅) ，通过将数据映射到高维空间，来解决在原始空间中线性不可分的问题。

具体来说，**在线性不可分的情况下，支持向量机首先在低维空间中完成计算，然后通过核函数将输入空间映射到高维特征空间，最终在高维特征空间中构造出最优分离超平面，从而把平面上本身不好分的非线性数据分开。**如图所示，一堆数据在二维空间无法划分，从而映射到三维空间里划分：

![](http://wx3.sinaimg.cn/mw690/00630Defly1g4w7t050xfj30nl0bajrv.jpg)

![](https://julyedu-img.oss-cn-beijing.aliyuncs.com/quesbase6415311358438441728.gif)

通常人们会从一些常用的核函数中选择（根据问题和数据的不同，选择不同的参数，实际上就是得到了不同的核函数），例如：**多项式核、高斯核、线性核。**

读者可能还是没明白核函数到底是个什么东西？我再简要概括下，即以下三点：

1. 实际中，我们会经常遇到线性不可分的样例，此时，我们的常用做法是把样例特征映射到高维空间中去(映射到高维空间后，相关特征便被分开了，也就达到了分类的目的)；
2. 但进一步，如果凡是**遇到线性不可分的样例，一律映射到高维空间，那么这个维度大小是会高到可怕的**。那咋办呢？
3. 此时，核函数就隆重登场了，核函数的价值在于它虽然也是将特征进行从低维到高维的转换，但**核函数绝就绝在它事先在低维上进行计算，而将实质上的分类效果表现在了高维上，避免了直接在高维空间中的复杂计算。**

**如果数据中出现了离群点outliers，那么就可以使用松弛变量来解决。**



# <span id="head9">SVM 为什么采用间隔最大化</span>

当训练数据线性可分时，**存在无穷个分离超平面可以将两类数据正确分开**。感知机利用误分类最小策略，求得分离超平面，不过此时的解有无穷多个。线性可分支持向量机**利用间隔最大化求得最优分离超平面，这时，解是唯一的**。另一方面，此时的分隔超平面所产生的分类结果是**最鲁棒**的，对未知实例的泛化能力最强。可以借此机会阐述一下几何间隔以及函数间隔的关系。

# <span id="head10"> SVM的目标（硬间隔）</span>

有两个目标：第一个是**使间隔最大化**，第二个是使**样本正确分类**，由此推出目标函数：

# <span id="head11"> 求解目标（硬间隔）</span>
从上面的公式看出，这是一个有约束条件的最优化问题，用拉格朗日函数来解决。



# <span id="head12">为什么要将求解 SVM 的原始问题转换为其对偶问题</span>

1. **对偶问题往往更易求解，把目标函数和约束全部融入一个新的函数，即拉格朗日函数，再通过这个函数来寻找最优点**。当我们寻找约束存在时的最优点的时候，约束的存在虽然减小了需要搜寻的范围，但是却使问题变得更加复杂。为了使问题变得易于处理
2. **出现样本内积形式，可以自然引入核函数，进而推广到非线性分类问题**。
3. 把与样本维度相关的求解转换为与样本数量有关（支持向量）

为什么求解对偶问题更加高效？
因为只用求解alpha系数，而alpha系数只有支持向量才非0，其他全部为0.

alpha系数有多少个？
样本点的个数

# <span id="head13">为什么 SVM 要引入核函数</span>

核函数的定义：$K(x,y)=<ϕ(x),ϕ(y)>$，即在特征空间的内积等于它们在原始样本空间中通过核函数 K 计算的结果。除了 SVM 之外，任何将计算表示为数据点的内积的方法，都可以使用核方法进行非线性扩展。
1. **数据变成了高维空间中线性可分的数据**
2. **不需要求解具体的映射函数，只需要给定具体的核函数即可，这样使得求解的难度大大降低**。

# <span id="head14"> 为什么SVM对缺失数据敏感</span>

这里说的缺失数据是指缺失某些特征数据，向量数据不完整。**SVM 没有处理缺失值的策略。而 SVM 希望样本在特征空间中线性可分，所以特征空间的好坏对SVM的性能很重要**。缺失特征数据将影响训练结果的好坏。

# <span id="head15">SVM 核函数之间的区别</span>

一般选择线性核和高斯核，也就是线性核与 RBF 核。
1.**线性核：主要用于线性可分的情形，参数少，速度快，对于一般数据，分类效果已经很理想了。 RBF 核：主要用于线性不可分的情形，参数多，分类结果非常依赖于参数。**有很多人是通过训练数据的交叉验证来寻找合适的参数，不过这个过程比较耗时。 
2.**如果 Feature 的数量很大，跟样本数量差不多，这时候选用线性核的 SVM。 如果 Feature 的数量比较小，样本数量一般，不算大也不算小，选用高斯核的 SVM**。

**大家一定要做到自己可以推导集合间隔、函数间隔以及对偶函数，并且理解对偶函数的引入对计算带来的优势。**


# <span id="head16"> SVM如何处理多分类问题？</span>

1. 直接法，直接在目标函数上修改，**将多个分类面的参数求解合并到一个最优化问题里面**。看似简单但是计算量却非常的大。
2. 间接法：**对训练器进行组合。**其中比较典型的有一对一，和一对多。
(1) 一对多，就是对**每个类都训练出一个分类器**，由svm是二分类，所以将此而分类器的两类设定为目标类为一类，其余类为另外一类。这样针对k个类可以训练出k个分类器，当有一个新的样本来的时候，**用这k个分类器来测试，那个分类器的概率高**，那么这个样本就属于哪一类。这种方法效果不太好，bias比较高。
(2) svm一对一法（one-vs-one），针对**任意两个类训练出一个分类器，如果有k类，一共训练出$C^2_k$个分类器**，这样当有一个新的样本要来的时候，用这$C^2_k$个分类器来测试，**每当被判定属于某一类的时候，该类就加一，最后票数最多的类别被认定为该样本的类。**

# <span id="head17"> SVM的特点</span>
1. 可以解决高维数据的问题（对偶问题把维度相关问题化为了和样本数有关）
2. 全局求解，避免了局部最优
3. 相当于自带L2正则
4. 但是对缺失数据敏感，噪声敏感（关键支持向量）
5. 输入数据需要归一化（涉及计算距离，归一化提高精度；用梯度下降计算的，归一化提高速度）
6. 复杂度为O(n2),大数据不合适


# <span id="head18"> SVM和LR的区别</span>
## <span id="head19"> 同</span>
1. **LR和SVM都可以处理分类问题，且一般都用于处理线性二分类问题**（在改进的情况下可以处理多分类问题）
2. **两个方法都可以增加不同的正则化项**，如L1、L2等等。所以在很多实验中，两种算法的结果是很接近的。
3. **LR和SVM都可以用来做非线性分类，只要加核函数就好。**
4. **LR和SVM都是线性模型**，当然这里我们不要考虑核函数
5. **都属于判别模型**
## <span id="head20"> 不同</span>
1. **LR是参数模型，SVM是非参数模型**。
2. 从损失函数来看，区别在于**逻辑回归采用的是logistical loss（极大似然损失），SVM采用的是合页损失，**这两个损失函数的目的都是增加对分类影响较大的数据点的权重，减少与分类关系较小的数据点的权重。
3. **逻辑回归相对来说模型更简单，好理解**，特别是大规模线性分类时比较方便。而SVM的理解和优化相对来说复杂一些，**SVM转化为对偶问题后,分类只需要计算与少数几个支持向量的距离,这个在进行复杂核函数计算时优势很明显,能够大大简化模型和计算。**
4. **SVM只考虑局部的边界线附近的点**（支持向量），**LR考虑全局**，**SVM不直接依赖数据分布，而LR则依赖整体数据分布**，因为SVM只与支持向量那几个点有关系，而LR和所有点都有关系。
5. **SVM依赖正则系数，实验中需要做CV**
6. **SVM的损失函数自带正则，SVM是结构风险最小化模型**，而LR必须在损失函数外加正则项，是经验风险最小化模型
7. **LR输出具有概率意义，而SVM没有**，直接为1或-1



# <span id="head21"> 问题</span>

1. 是否存在一组参数使SVM训练误差为0？

   答：存在

2. 训练误差为0的SVM分类器一定存在吗？

   答：一定存在

3. 加入松弛变量的SVM的训练误差可以为0吗？

   答：使用SMO算法训练的线性分类器并不一定能得到训练误差为0的模型。这是由 于我们的优化目标改变了，并不再是使训练误差最小。

4. **带核的SVM为什么能分类非线性问题?**

   答：核函数的本质是两个函数的內积，通过核函数将其隐射到高维空间，在高维空间非线性问题转化为线性问题, SVM得到超平面是高维空间的线性分类平面。其分类结果也视为低维空间的非线性分类结果, 因而带核的SVM就能分类非线性问题。

5. **如何选择核函数？**

   - 如果**特征的数量大到和样本数量差不多**，则选用**LR或者线性核的SVM**；
   - 如果**特征的数量小，样本的数量正常**，则选用**SVM+高斯核函数**；
   - 如果**特征的数量小，而样本的数量很大**，则需要**手工添加一些特征从而变成第一种情况**。






# <span id="head22"> sklearn</span>
```sklearn.svm.SVC(C=1.0, kernel='rbf', degree=3, gamma='auto', coef0=0.0, shrinking=True, probability=False,tol=0.001, cache_size=200, class_weight=None, verbose=False, max_iter=-1, decision_function_shape=None,random_state=None)```
参数：

-  C：惩罚参数默认值是1.0
C越大，相当于惩罚松弛变量，希望松弛变量接近0，即对误分类的惩罚增大，趋向于对训练集全分对的情况，这样对训练集测试时准确率很高，但泛化能力弱。C值小，对误分类的惩罚减小，允许容错，将他们当成噪声点，泛化能力较强。
-   kernel ：核函数，默认是rbf，可以是‘linear’, ‘poly’, ‘rbf’, ‘sigmoid’, ‘precomputed’ 
0 – 线性：u'v
1 – 多项式：(gamma*u'*v + coef0)^degree
2 – RBF函数：exp(-gamma|u-v|^2)
3 –sigmoid：tanh(gamma*u'*v + coef0)
-   degree ：多项式poly函数的维度，默认是3，选择其他核函数时会被忽略。
-  gamma ： ‘rbf’,‘poly’ 和‘sigmoid’的核函数参数。默认是’auto’，则会选择1/n_features
-   coef0 ：核函数的常数项。对于‘poly’和 ‘sigmoid’有用。
-   probability ：是否采用概率估计？.默认为False
-  shrinking ：是否采用shrinking heuristic方法，默认为true
-   tol ：停止训练的误差值大小，默认为1e-3
-   cache_size ：核函数cache缓存大小，默认为200
-   class_weight ：类别的权重，字典形式传递。设置第几类的参数C为weight*C(C-SVC中的C)
-  verbose ：允许冗余输出？
-  max_iter ：最大迭代次数。-1为无限制。
-  decision_function_shape ：‘ovo’, ‘ovr’ or None, default=None3
-   random_state ：数据洗牌时的种子值，int值

主要调节的参数有：C、kernel、degree、gamma、coef0。

```python
## <span id="head23"> 导入库</span>
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
## <span id="head24"> 导入数据</span>
dataset = pd.read_csv('Social_Network_Ads.csv')
X = dataset.iloc[:, [2, 3]].values
y = dataset.iloc[:, 4].values


## <span id="head25"> 拆分数据集为训练集合和测试集合</span>

from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25, random_state = 0)


## <span id="head26"> 特征量化</span>

from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.fit_transform(X_test)


## <span id="head27"> 适配SVM到训练集合</span>

from sklearn.svm import SVC
classifier = SVC(kernel = 'linear', random_state = 0)
classifier.fit(X_train, y_train)

## <span id="head28"> 预测测试集合结果</span>

y_pred = classifier.predict(X_test)

## <span id="head29"> 创建混淆矩阵</span>

from sklearn.metrics import confusion_matrix
cm = confusion_matrix(y_test, y_pred)
## <span id="head30"> 训练集合结果可视化</span>

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
plt.title('SVM (Training set)')
plt.xlabel('Age')
plt.ylabel('Estimated Salary')
plt.legend()
plt.show()
## <span id="head31"> 测试集合结果可视化</span>
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
plt.title('SVM (Test set)')
plt.xlabel('Age')
plt.ylabel('Estimated Salary')
plt.legend()
plt.show()
```
