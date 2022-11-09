- [ 数学原理](#head1)
	- [ 单边梯度抽样算法](#head2)
	- [ 直方图算法](#head3)
		- [ 直方图算法](#head4)
		- [ 直方图加速](#head5)
		- [ 稀疏特征优化](#head6)
	- [ 互斥特征捆绑算法](#head7)
		- [ 哪些特征可以一起绑定？](#head8)
		- [ 特征绑定后，特征值如何确定？](#head9)
	- [带深度限制的 Leaf-wise 算法](#head10)
	- [ 类别特征最优分割](#head11)
- [ 工程实现](#head12)
	- [ 特征并行](#head13)
	- [ 数据并行](#head14)
	- [ 投票并行](#head15)
	- [ 缓存优化](#head16)
- [与 XGBoost 的对比](#head17)
- [ 代码](#head18)
	- [ sklearn参数](#head19)
	- [ LightGBM参数](#head20)
		- [ 针对leaf-wise树的参数优化](#head21)
		- [ 针对更快的训练速度](#head22)
		- [ 获得更好的准确率](#head23)
		- [ 缓解过拟合](#head24)
	- [ LightGBM的调参基本流程](#head25)
	- [ sklearn例子](#head26)
# <span id="head1"> 数学原理</span>
## <span id="head2"> 单边梯度抽样算法</span>
GBDT 算法的梯度大小可以反应样本的权重，梯度越小说明模型拟合的越好，**单边梯度抽样算法（Gradient-based One-Side Sampling, GOSS）利用这一信息对样本进行抽样，减少了大量梯度小的样本，在接下来的计算锅中只需关注梯度高的样本，极大的减少了计算量**。
GOSS 算法保留了梯度大的样本，并对梯度小的样本进行随机抽样，为了不改变样本的数据分布，在计算增益时为梯度小的样本引入一个常数进行平衡。具体算法如下所示：
![](https://upload-images.jianshu.io/upload_images/18339009-7ef1237159d11384.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们可以看到 GOSS 事先基于梯度的绝对值对样本进行排序（无需保存排序后结果），然后拿到前 a% 的梯度大的样本，和剩下样本的 b%，在计算增益时，通过乘上 $\frac{1-a}{b} $来放大梯度小的样本的权重。一方面算法将更多的注意力放在训练不足的样本上，另一方面通过乘上权重来防止采样对原始数据分布造成太大的影响。
## <span id="head3"> 直方图算法</span>
### <span id="head4"> 直方图算法</span>
直方图算法的基本思想是**将连续的特征离散化为 k 个离散特征，同时构造一个宽度为 k 的直方图用于统计信息**（含有 k 个 bin）。利用直方图算法我们**无需遍历数据，只需要遍历 k 个 bin 即可找到最佳分裂点**。
我们知道特征离散化的具有很多优点，如**存储方便、运算更快、鲁棒性强、模型更加稳定**等等。对于直方图算法来说最直接的有以下两个优点（以 k=256 为例）：
- 内存占用更小：XGBoost 需要用 32 位的浮点数去存储特征值，并用 32 位的整形去存储索引，而 LightGBM 只需要用 8 位去存储直方图，相当于减少了 1/8；
- 计算代价更小：计算特征分裂增益时，XGBoost 需要遍历一次数据找到最佳分裂点，而 LightGBM 只需要遍历一次 k 次，直接将时间复杂度从 O(#data  * #feature) 降低到 O(k  * #feature)  ，而我们知道 #data >> k 。

虽然将特征离散化后**无法找到精确的分割点**，可能会对模型的精度产生一定的影响，但**较粗的分割也起到了正则化的效果**，一定程度上降低了模型的方差。
### <span id="head5"> 直方图加速</span>
在构建叶节点的直方图时，我们还可以**通过父节点的直方图与相邻叶节点的直方图相减的方式构建，从而减少了一半的计算量**。在实际操作过程中，我们还可以**先计算直方图小的叶子节点，然后利用直方图作差来获得直方图大的叶子节点**。
![](https://upload-images.jianshu.io/upload_images/18339009-dd7b7ba852e74c46.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### <span id="head6"> 稀疏特征优化</span>
XGBoost 在进行预排序时只考虑非零值进行加速，而 LightGBM 也采用类似策略：**只用非零特征构建直方图**。
## <span id="head7"> 互斥特征捆绑算法</span>
高维特征往往是稀疏的，而且特征间可能是相互排斥的（如两个特征不同时取非零值），如果两个特征并不完全互斥（如只有一部分情况下是不同时取非零值），可以用互斥率表示互斥程度。**互斥特征捆绑算法（Exclusive Feature Bundling, EFB）指出如果将一些特征进行融合绑定，则可以降低特征数量**。
### <span id="head8"> 哪些特征可以一起绑定？</span>
EFB 算法利用特征和特征间的关系构造一个加权无向图，并将其转换为图着色算法。我们知道图着色是个 NP-Hard 问题，故采用贪婪算法得到近似解，具体步骤如下：

1. 构造一个加权无向图，顶点是特征，边是两个特征间互斥程度；
2. 根据节点的度进行降序排序，度越大，与其他特征的冲突越大；
3. 遍历每个特征，将它分配给现有特征包，或者新建一个特征包，是的总体冲突最小。

算法**允许两两特征并不完全互斥来增加特征捆绑的数量，通过设置最大互斥率来平衡算法的精度和效率**。EFB 算法的伪代码如下所示：
![](https://upload-images.jianshu.io/upload_images/18339009-07a2466a12f1658b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们看到时间复杂度为 O(#feature^2) ，在特征不多的情况下可以应付，但如果特征维度达到百万级别，计算量则会非常大，为了改善效率，我们提出了一个更快的解决方案：将 EFB 算法中通过构建图，根据节点度来排序的策略改成了根据非零值的技术排序，因为非零值越多，互斥的概率会越大。


### <span id="head9"> 特征绑定后，特征值如何确定？</span>
论文给出**特征合并算法**，其关键在于原始特征能从合并的特征中分离出来。假设 Bundle 中有两个特征值，A 取值为 [0, 10]、B 取值为 [0, 20]，为了保证特征 A、B 的互斥性，我们可以给特征 B 添加一个偏移量转换为 [10, 30]，Bundle 后的特征其取值为 [0, 30]，这样便实现了特征合并。具体算法如下所示：
![](https://upload-images.jianshu.io/upload_images/18339009-326162cec75146e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head10">带深度限制的 Leaf-wise 算法</span>
在建树的过程中有两种策略：
- Level-wise：基于层进行生长，直到达到停止条件；

  XGBoost 采用 Level-wise 的增长策略，**方便并行计算每一层的分裂节点**，提高了训练速度，但同时也因为节点增益过小增加了很多不必要的分裂，增加了计算量；

- Leaf-wise：每次分裂增益最大的叶子节点，直到达到停止条件。

  LightGBM 采用 **Leaf-wise 的增长策略减少了计算量，配合最大深度的限制防止过拟合，由于每次都需要计算增益最大的节点，所以无法并行分裂**。

  > xgb是level-wise，lgb是leaf-wise，level-wise指在树分裂的过程中，同一层的非叶子节点，只要继续分裂能够产生正的增益就继续分裂下去，而leaf-wise更苛刻一点，同一层的非叶子节点，仅仅选择分裂增益最大的叶子节点进行分裂。

  ![](https://upload-images.jianshu.io/upload_images/18339009-9a46bf032cbe8e13.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head11"> 类别特征最优分割</span>
大部分的机器学习算法都不能直接支持类别特征，一般都会对类别特征进行编码，然后再输入到模型中。常见的处理类别特征的方法为 one-hot 编码，但我们知道对于决策树来说并不推荐使用 one-hot 编码：
- 会**产生样本切分不平衡问题，切分增益会非常小**。如，国籍切分后，会产生是否中国，是否美国等一系列特征，这一系列特征上只有少量样本为 1，大量样本为 0。这种划分的增益非常小：较小的那个拆分样本集，它占总样本的比例太小。无论增益多大，乘以该比例之后几乎可以忽略；较大的那个拆分样本集，它几乎就是原始的样本集，增益几乎为零；
- **影响决策树学习：决策树依赖的是数据的统计信息**，而独热码编码会把数据切分到零散的小空间上。在这些零散的小空间上统计信息不准确的，学习效果变差。本质是因为独热码编码之后的特征的表达能力较差的，特征的预测能力被人为的拆分成多份，每一份与其他特征竞争最优划分点都失败，最终该特征得到的重要性会比实际值低。

LightGBM 原生支持类别特征，采用 many-vs-many 的切分方式将类别特征分为两个子集，实现类别特征的最优切分。假设有某维特征有 k 个类别，则有$ 2^{(k-1)} - 1 $种可能，时间复杂度为 $O(2^k) $，LightGBM 基于 Fisher 大佬的 《On Grouping For Maximum Homogeneity》实现了$ O(klog_2k)$的时间复杂度。
![](https://upload-images.jianshu.io/upload_images/18339009-9c26dfb10efececf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
上图为左边为基于 one-hot 编码进行分裂，后图为 LightGBM 基于 many-vs-many 进行分裂，在给定深度情况下，后者能学出更好的模型。
其基本思想在于每次分组时都会根据训练目标对类别特征进行分类，根据其累积值$ \frac{\sum gradient }{\sum hessian} $对直方图进行排序，然后在排序的直方图上找到最佳分割。此外，LightGBM 还加了约束条件正则化，防止过拟合。
![我们可以看到这种处理类别特征的方式使得 AUC 提高了 1.5 个点，且时间仅仅多了 20%。
](https://upload-images.jianshu.io/upload_images/18339009-8840eb7d916f3db9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# <span id="head12"> 工程实现</span>
## <span id="head13"> 特征并行</span>
**传统的特征并行算法在于对数据进行垂直划分**，然后使用不同机器找到不同特征的最优分裂点，基于通信整合得到最佳划分点，然后基于通信告知其他机器划分结果。
传统的特征并行方法有个很大的缺点：需要告知每台机器最终划分结果，增加了额外的复杂度（因为对数据进行垂直划分，每台机器所含数据不同，划分结果需要通过通信告知）。
**LightGBM 则不进行数据垂直划分，每台机器都有训练集完整数据，在得到最佳划分方案后可在本地执行划分而减少了不必要的通信。**
## <span id="head14"> 数据并行</span>
**传统的数据并行策略主要为水平划分数据**，然后本地构建直方图并整合成全局直方图，最后在全局直方图中找出最佳划分点。
这种数据划分有一个很大的缺点：通讯开销过大。如果使用点对点通信，一台机器的通讯开销大约为 O(#machine * #feature *#bin ) ；如果使用集成的通信，则通讯开销为 O(2 * #feature *#bin ) ，
**LightGBM 采用分散规约（Reduce scatter）的方式将直方图整合的任务分摊到不同机器上，从而降低通信代价，并通过直方图做差进一步降低不同机器间的通信**。
## <span id="head15"> 投票并行</span>
针对数据量特别大特征也特别多的情况下，可以采用投票并行。投票并行主要针对数据并行时数据合并的通信代价比较大的瓶颈进行优化，其通过投票的方式只合并部分特征的直方图从而达到降低通信量的目的。
大致步骤为两步：
- 本地找出 Top K 特征，并基于投票筛选出可能是最优分割点的特征；
- 合并时只合并每个机器选出来的特征。
## <span id="head16"> 缓存优化</span>
上边说到 XGBoost 的预排序后的特征是通过索引给出的样本梯度的统计值，因其索引访问的结果并不连续，XGBoost 提出缓存访问优化算法进行改进。而 LightGBM 所使用直方图算法对 Cache 天生友好：
- **所有的特征都采用相同的方法获得梯度（区别于不同特征通过不同的索引获得梯度），只需要对梯度进行排序并可实现连续访问，大大提高了缓存命中**；
- **因为不需要存储特征到样本的索引，降低了存储消耗，而且也不存在 Cache Miss的问题**。
![](https://upload-images.jianshu.io/upload_images/18339009-2ae26662709d14c0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





# <span id="head17">与 XGBoost 的对比</span>

1. XGBoost 使用预排序后**需要记录特征值及其对应样本的统计值的索引**，而 LightGBM 使用了**直方图算法将特征值转变为 bin 值，且不需要记录特征到样本的索引**，将空间复杂度从 O(2*#data) 降低为 O(#bin) ，极大的减少了内存消耗；

2. LightGBM 采用了直方图算法**将存储特征值转变为存储 bin 值**，更高（效率）更快（速度）更低（内存占用）更泛化（分箱与之后的不精确分割也起到了一定防止过拟合的作用）；

3. LightGBM 在训练过程中采用**互斥特征捆绑算法减少了特征数量**，降低了内存消耗。

4. LightGBM 在训练过程中采用**单边梯度算法过滤掉梯度小的样本**，减少了大量的计算；

5. LightGBM 采用了**基于 Leaf-wise 算法的增长策略构建树，减少了很多不必要的计算量**；

6. LightGBM 采用**优化后的特征并行、数据并行方法加速计算**，当数据量非常大的时候还可以采用投票并行的策略；

7. LightGBM 对**缓存也进行了优化，增加了 Cache hit 的命中率**。



**缺点**：直方图较为粗糙，会损失一定精度，但是在gbm的框架下，基学习器的精度损失可以通过引入更多的tree来弥补。

# <span id="head18"> 代码</span>

## <span id="head19"> sklearn参数</span>

https://blog.csdn.net/hnlylnjyp/article/details/90382417

## <span id="head20"> LightGBM参数</span>

https://lightgbm.readthedocs.io/en/latest/pythonapi/lightgbm.LGBMRegressor.html

https://github.com/Microsoft/LightGBM/blob/master/docs/Parameters.rst

### <span id="head21"> 针对leaf-wise树的参数优化</span>
- num_leaves:控制了叶节点的数目。它是控制树模型复杂度的主要参数。如果是level-wise，则该参数为，其中depth为树的深度。但是当叶子数量相同时，leaf-wise的树要远远深过level-wise树，非常容易导致过拟合。因此应该让num_leaves小于。在leaf-wise树中，并不存在depth的概念。因为不存在一个从leaves到depth的合理映射。
- min_data_in_leaf: 每个叶节点的最少样本数量。它是处理leaf-wise树的过拟合的重要参数。将它设为较大的值，可以避免生成一个过深的树。但是也可能导致欠拟合。
- max_depth：控制了树的最大深度。该参数可以显式的限制树的深度。
### <span id="head22"> 针对更快的训练速度</span>
- 设置 bagging_fraction 和 bagging_freq 参数来使用 bagging 方法
 -设置 feature_fraction 参数来使用特征的子抽样
- 使用较小的 max_bin
- 使用 save_binary 在未来的学习过程对数据加载进行加速
### <span id="head23"> 获得更好的准确率</span>
- 使用较大的 max_bin （学习速度可能变慢）
- 使用较小的 learning_rate 和较大的 num_iterations
- 使用较大的 num_leaves （可能导致过拟合）
- 使用更大的训练数据
- 尝试DART
### <span id="head24"> 缓解过拟合</span>
- 使用较小的 max_bin， 分桶粗一些
- 使用较小的 num_leaves   不要在单棵树分的太细
- 使用 lambda_l1, lambda_l2 和 min_gain_to_split 来使用正则
- 尝试 max_depth 来避免生成过深的树
- 使用 min_data_in_leaf 和 min_sum_hessian_in_leaf， 确保叶子节点有足够多的数据

## <span id="head25"> LightGBM的调参基本流程</span>

过程和RF、GBDT等类似

首先选择较高的学习率，设置lr=0.1，这样是为了加快收敛的速度。然后对决策树基本参数调参；正则化参数调参；最后降低学习率，这里是为了最后提高准确率

原生形式使用lightgbm
```
import lightgbm as lgb
from sklearn.metrics import mean_squared_error
from sklearn.datasets import load_boston
from sklearn.model_selection import train_test_split
 
# 111111111111111第一步：学习率和迭代次数我们先把学习率先定一个较高的值，这里取 learning_rate = 0.1，
#其次确定估计器boosting/boost/boosting_type的类型，不过默认都会选gbdt。
#迭代的次数，也可以说是残差树的数目，参数名为n_estimators/num_iterations/num_round/num_boost_round。我们可以先将该参数设成一个较大的数，然后在cv结果中查看最优的迭代次数，具体如代码。
#在这之前，我们必须给其他重要的参数一个初始值。初始值的意义不大，只是为了方便确定其他参数。下面先给定一下初始值：

# 以下参数根据具体项目要求定：

# 'boosting_type'/'boosting': 'gbdt'
# 'objective': 'binary'
# 'metric': 'auc'
 
# # 以下是选择的初始值
# 'max_depth': 5     # 由于数据集不是很大，所以选择了一个适中的值，其实4-10都无所谓。
# 'num_leaves': 30   # 由于lightGBM是leaves_wise生长，官方说法是要小于2^max_depth
# 'subsample'/'bagging_fraction':0.8           # 数据采样
# 'colsample_bytree'/'feature_fraction': 0.8   # 特征采样


import pandas as pd
import lightgbm as lgb
from sklearn.datasets import load_breast_cancer
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn import metrics
canceData=load_breast_cancer()
X=canceData.data
y=canceData.target
X_train,X_test,y_train,y_test=train_test_split(X,y,random_state=0,test_size=0.2)
params = {    
          'boosting_type': 'gbdt',
          'objective': 'binary',
          'metric': 'auc',
          'nthread':4,
          'learning_rate':0.1,
          'num_leaves':30, 
          'max_depth': 5,   
          'subsample': 0.8, 
          'colsample_bytree': 0.8, 
    }
    
data_train = lgb.Dataset(X_train, y_train)
cv_results = lgb.cv(params, data_train, num_boost_round=1000, nfold=5, stratified=False, shuffle=True, metrics='auc',early_stopping_rounds=50,seed=0)
print('best n_estimators:', len(cv_results['auc-mean']))
print('best cv score:', pd.Series(cv_results['auc-mean']).max())

# 结果：
('best n_estimators:', 188)
('best cv score:', 0.99134716298085424)
# 我们根据以上结果，就可以取n_estimators=188

# 111111111111111第二步：确定max_depth和num_leaves这是提高精确度的最重要的参数。这里我们引入sklearn里的GridSearchCV()函数进行搜索

params_test1={'max_depth': range(3,8,1), 'num_leaves':range(5, 100, 5)}
              
gsearch1 = GridSearchCV(estimator = lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.1, n_estimators=188, max_depth=6, bagging_fraction = 0.8,feature_fraction = 0.8), 
                       param_grid = params_test1, scoring='roc_auc',cv=5,n_jobs=-1)
gsearch1.fit(X_train,y_train)
#gsearch1.grid_scores_, gsearch1.best_params_, gsearch1.best_score_


# # 结果：
# ([mean: 0.99248, std: 0.01033, params: {'num_leaves': 5, 'max_depth': 3},
#   mean: 0.99227, std: 0.01013, params: {'num_leaves': 10, 'max_depth': 3},
#   mean: 0.99227, std: 0.01013, params: {'num_leaves': 15, 'max_depth': 3},
#  ······
#   mean: 0.99331, std: 0.00775, params: {'num_leaves': 85, 'max_depth': 7},
#   mean: 0.99331, std: 0.00775, params: {'num_leaves': 90, 'max_depth': 7},
#   mean: 0.99331, std: 0.00775, params: {'num_leaves': 95, 'max_depth': 7}],
#  {'max_depth': 4, 'num_leaves': 10},
#  0.9943573667711598)
# 根据结果，我们取max_depth=4, num_leaves=10

#  111111111111111第三步：确定min_data_in_leaf和max_bin

params_test2={'max_bin': range(5,256,10), 'min_data_in_leaf':range(1,102,10)}
              
gsearch2 = GridSearchCV(estimator = lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.1, n_estimators=188, max_depth=4, num_leaves=10,bagging_fraction = 0.8,feature_fraction = 0.8), 
                       param_grid = params_test2, scoring='roc_auc',cv=5,n_jobs=-1)
gsearch2.fit(X_train,y_train)
# gsearch2.grid_scores_, gsearch2.best_params_, gsearch2.best_score_
# 这个结果就不显示了，根据结果，我们取min_data_in_leaf=51，max_bin in=15。

# 111111111111111      第四步：确定feature_fraction、bagging_fraction、bagging_freq

params_test3={'feature_fraction': [0.6,0.7,0.8,0.9,1.0],
              'bagging_fraction': [0.6,0.7,0.8,0.9,1.0],
              'bagging_freq': range(0,81,10)}
              
gsearch3 = GridSearchCV(estimator = lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.1, n_estimators=188, max_depth=4, num_leaves=10,max_bin=15,min_data_in_leaf=51), 
                       param_grid = params_test3, scoring='roc_auc',cv=5,n_jobs=-1)
gsearch3.fit(X_train,y_train)
# gsearch3.grid_scores_, gsearch3.best_params_, gsearch3.best_score_
# 111111111111111         第五步：确定lambda_l1和lambda_l2

params_test4={'lambda_l1': [1e-5,1e-3,1e-1,0.0,0.1,0.3,0.5,0.7,0.9,1.0],
              'lambda_l2': [1e-5,1e-3,1e-1,0.0,0.1,0.3,0.5,0.7,0.9,1.0]
}
              
gsearch4 = GridSearchCV(estimator = lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.1, n_estimators=188, max_depth=4, num_leaves=10,max_bin=15,min_data_in_leaf=51,bagging_fraction=0.6,bagging_freq= 0, feature_fraction= 0.8), 
                       param_grid = params_test4, scoring='roc_auc',cv=5,n_jobs=-1)
gsearch4.fit(X_train,y_train)
#gsearch4.grid_scores_, gsearch4.best_params_, gsearch4.best_score_
# 111111111111111     第六步：确定 min_split_gain

params_test5={'min_split_gain':[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0]}
              
gsearch5 = GridSearchCV(estimator = lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.1, n_estimators=188, max_depth=4, num_leaves=10,max_bin=15,min_data_in_leaf=51,bagging_fraction=0.6,bagging_freq= 0, feature_fraction= 0.8,
lambda_l1=1e-05,lambda_l2=1e-05), 
                       param_grid = params_test5, scoring='roc_auc',cv=5,n_jobs=-1)
gsearch5.fit(X_train,y_train)
# print(gsearch5.grid_scores_, gsearch5.best_params_, gsearch5.best_score_)
# 111111111111111         第七步：降低学习率，增加迭代次数，验证模型

model=lgb.LGBMClassifier(boosting_type='gbdt',objective='binary',metrics='auc',learning_rate=0.01, n_estimators=1000, max_depth=4, num_leaves=10,max_bin=15,min_data_in_leaf=51,bagging_fraction=0.6,bagging_freq= 0, feature_fraction= 0.8,
lambda_l1=1e-05,lambda_l2=1e-05,min_split_gain=0)
model.fit(X_train,y_train)
y_pre=model.predict(X_test)
print("acc:",metrics.accuracy_score(y_test,y_pre))
print("auc:",metrics.roc_auc_score(y_test,y_pre))
```

## <span id="head26"> sklearn例子</span>
```
from sklearn.model_selection import GridSearchCV
# 加载数据
boston = load_boston()
data = boston.data
target = boston.target
X_train, X_test, y_train, y_test = train_test_split(data, target, test_size=0.2)
 
# 创建模型，训练模型
gbm = lgb.LGBMRegressor(objective='regression', num_leaves=31, learning_rate=0.05, n_estimators=20)
gbm.fit(X_train, y_train, eval_set=[(X_test, y_test)], eval_metric='l1', early_stopping_rounds=5)
 
# 测试机预测
y_pred = gbm.predict(X_test, num_iteration=gbm.best_iteration_)
 
# 模型评估
print('The rmse of prediction is:', mean_squared_error(y_test, y_pred) ** 0.5)
 
# feature importances
print('Feature importances:', list(gbm.feature_importances_))
 
# 网格搜索，参数优化
estimator = lgb.LGBMRegressor(num_leaves=31)
param_grid = {
    'learning_rate': [0.01, 0.1, 1],
    'n_estimators': [20, 40]
}
gbm = GridSearchCV(estimator, param_grid)
gbm.fit(X_train, y_train)
print('Best parameters found by grid search are:', gbm.best_params_)
```

**在Linux下很方便的就可以开启GPU训练。可以优先选用从pip安装，如果失败再从源码安装。**

安装方法：从源码安装
```python
git clone --recursive https://github.com/microsoft/LightGBM ; 
cd LightGBM
mkdir build ; cd build
cmake ..

# 开启MPI通信机制，训练更快
# cmake -DUSE_MPI=ON ..

# GPU版本，训练更快
# cmake -DUSE_GPU=1 ..
make -j4
安装方法：pip安装
# 默认版本
pip install lightgbm

# MPI版本
pip install lightgbm --install-option=--mpi

# GPU版本
pip install lightgbm --install-option=--gpu
2 调用方法
在Python语言中LightGBM提供了两种调用方式，分为为原生的API和Scikit-learn API，两种方式都可以完成训练和验证。当然原生的API更加灵活，看个人习惯来进行选择。

2.1 定义数据集
df_train = pd.read_csv('https://cdn.coggle.club/LightGBM/examples/binary_classification/binary.train', header=None, sep='\t')
df_test = pd.read_csv('https://cdn.coggle.club/LightGBM/examples/binary_classification/binary.test', header=None, sep='\t')
W_train = pd.read_csv('https://cdn.coggle.club/LightGBM/examples/binary_classification/binary.train.weight', header=None)[0]
W_test = pd.read_csv('https://cdn.coggle.club/LightGBM/examples/binary_classification/binary.test.weight', header=None)[0]

y_train = df_train[0]
y_test = df_test[0]
X_train = df_train.drop(0, axis=1)
X_test = df_test.drop(0, axis=1)
num_train, num_feature = X_train.shape

# create dataset for lightgbm
# if you want to re-use data, remember to set free_raw_data=False

lgb_train = lgb.Dataset(X_train, y_train,
                        weight=W_train, free_raw_data=False)

lgb_eval = lgb.Dataset(X_test, y_test, reference=lgb_train,
                       weight=W_test, free_raw_data=False)

2.2 模型训练
params = {
    'boosting_type': 'gbdt',
    'objective': 'binary',
    'metric': 'binary_logloss',
    'num_leaves': 31,
    'learning_rate': 0.05,
    'feature_fraction': 0.9,
    'bagging_fraction': 0.8,
    'bagging_freq': 5,
    'verbose': 0
}

# generate feature names
feature_name = ['feature_' + str(col) for col in range(num_feature)]
gbm = lgb.train(params,
                lgb_train,
                num_boost_round=10,
                valid_sets=lgb_train,  # eval training data
                feature_name=feature_name,
                categorical_feature=[21])
2.3 模型保存与加载
# save model to file
gbm.save_model('model.txt')

print('Dumping model to JSON...')
model_json = gbm.dump_model()

with open('model.json', 'w+') as f:
    json.dump(model_json, f, indent=4)
2.4 查看特征重要性
# feature names
print('Feature names:', gbm.feature_name())

# feature importances
print('Feature importances:', list(gbm.feature_importance()))
2.5 继续训练
# continue training
# init_model accepts:
# 1. model file name
# 2. Booster()
gbm = lgb.train(params,
                lgb_train,
                num_boost_round=10,
                init_model='model.txt',
                valid_sets=lgb_eval)
print('Finished 10 - 20 rounds with model file...')

2.6 动态调整模型超参数
# decay learning rates
# learning_rates accepts:
# 1. list/tuple with length = num_boost_round
# 2. function(curr_iter)
gbm = lgb.train(params,
                lgb_train,
                num_boost_round=10,
                init_model=gbm,
                learning_rates=lambda iter: 0.05 * (0.99 ** iter),
                valid_sets=lgb_eval)
print('Finished 20 - 30 rounds with decay learning rates...')

# change other parameters during training
gbm = lgb.train(params,
                lgb_train,
                num_boost_round=10,
                init_model=gbm,
                valid_sets=lgb_eval,
                callbacks=[lgb.reset_parameter(bagging_fraction=[0.7] * 5 + [0.6] * 5)])
print('Finished 30 - 40 rounds with changing bagging_fraction...')
2.7 自定义损失函数
# self-defined objective function
# f(preds: array, train_data: Dataset) -> grad: array, hess: array
# log likelihood loss
def loglikelihood(preds, train_data):
    labels = train_data.get_label()
    preds = 1. / (1. + np.exp(-preds))
    grad = preds - labels
    hess = preds * (1. - preds)
    return grad, hess

# self-defined eval metric
# f(preds: array, train_data: Dataset) -> name: string, eval_result: float, is_higher_better: bool
# binary error
# NOTE: when you do customized loss function, the default prediction value is margin
# This may make built-in evalution metric calculate wrong results
# For example, we are doing log likelihood loss, the prediction is score before logistic transformation
# Keep this in mind when you use the customization
def binary_error(preds, train_data):
    labels = train_data.get_label()
    preds = 1. / (1. + np.exp(-preds))
    return 'error', np.mean(labels != (preds > 0.5)), False

gbm = lgb.train(params,
                lgb_train,
                num_boost_round=10,
                init_model=gbm,
                fobj=loglikelihood,
                feval=binary_error,
                valid_sets=lgb_eval)
print('Finished 40 - 50 rounds with self-defined objective function and eval metric...')
```







