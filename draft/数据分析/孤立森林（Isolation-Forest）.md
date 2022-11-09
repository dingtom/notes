![](https://upload-images.jianshu.io/upload_images/18339009-2169a2738446e629.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
首先随机选择到的维度是 “年龄”，然后随机选择一个切割点 18，小于 18 岁的只有莫小贝一个人，所以她最先被 “孤立” 出来了；第二个随机选择的特征是 ”体重“，只有大嘴高于 80 公斤，所以也被 ”孤立“ 了；第三个选择 ”文化程度“ 这个特征，由于只有秀才的文化程度为高，于是被 ”孤立“ 出来了 ……

假设我们设定树的高度为 3，那么这棵树的训练就结束了。在这棵树上，莫小贝的路径长度为 1，大嘴为 2，秀才为 3，单看这一棵树，莫小贝的异常程度最高。但很显然，她之所以最先被孤立出来，与特征被随机选择到的顺序有关，所以我们通过对多棵树进行训练，来去除这种随机性，让结果尽量收敛。
# 1.原理
使用孤立森林的前提是，将**异常点定义为那些 “容易被孤立的离群点**——孤立森林算法的理论基础有两点：
- 异常数据占总样本量的比例很小；
- 异常点的特征值与正常点的差异很大。

### 1.1算法思想
想象-我们用一个随机超平面对一个数据空间进行切割，切一次可以生成两个子空间-。接下来，我们再继续随机选取超平面，来切割第一步得到的两个子空间，以此循环下去，直到每子空间里面只包含一个数据点为止。直观上来看，我们可以发现，**那些密度很高的簇要被切很多次才会停止切割，即每个点都单独存在于一个子空间内，但那些分布稀疏的点，大都很早就停到一个子空间内了**

**孤立森林算法总共分两步：**

训练 iForest：从训练集中进行采样，构建孤立树，对森林中的每棵孤立树进行测试，记录路径长度；

计算异常分数：根据异常分数计算公式，计算每个样本点的 anomaly score。

### 1.2单棵树的训练

>- 从训练数据中**随机选择 Ψ 个点**作为子样本，放入一棵孤立树的**根节点；**
>- **随机指定一个维度**，在当前节点数据范围内，**随机产生一个切割点 p**—— **切割点产生于当前节点数据中指定维度的最大值与最小值之间**
>- 此切割点的选取**生成了一个超平面，将当前节点数据空间切分为2个子空间**：把当前**所选维度下小于 p 的点放在当前节点的左分支，**把大于等于 p 的点放在当前节点的右分支；
>- **在节点的左分支和右分支节点递归步骤 2、3**，不断构造新的叶子节点，**直到叶子节点上只有一个数据（无法再继续切割） 或树已经生长到了所设定的高度 。**（至于为什么要对树的高度做限制，后续会解释）

### 1.3整合全部孤立树的结果
由于切割过程是完全随机的，所以需要用 **ensemble 的方法来使结果收敛，即反复从头开始切，然后计算每次切分结果的平均值。**

获得 t 个孤立树后，单棵树的训练就结束了。接下来就可以**用生成的孤立树来评估测试数据了，即计算异常分数 s。**对于每个样本 x，需要对其综合计算每棵树的结果，通过下面的公式计算异常得分：
![](https://upload-images.jianshu.io/upload_images/18339009-269c9e0aeb0a4f5d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
h(x) 为 x 在每棵树的高度，c(Ψ) 为给定样本数 Ψ 时路径长度的平均值，用来对样本 x 的路径长度 h(x) 进行标准化处理。

如果异常得分接近 1，那么一定是异常点；
如果异常得分远小于 0.5，那么一定不是异常点；
如果异常得分所有点的得分都在 0.5 左右，那么样本中很可能不存在异常点。

# 1.4伪代码
1.孤立树的创建。
树的高度限制 l 与子样本数量 ψ 有关。**之所以对树的高度做限制，是因为我们只关心路径长度较短的点，它们更可能是异常点，而并不关心那些路径很长的正常点。**
![](https://upload-images.jianshu.io/upload_images/18339009-e783bdad94f754e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2.每棵孤立树的生长即训练过程。
![](https://upload-images.jianshu.io/upload_images/18339009-6074022a5e7beed7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3.为每个样本点的高度整合计算。
其中 c(size) 是一个 adjustment 项，因为有一些样本点还没有被孤立出来，树就停止生长了，该项对其高度给出修正。
![](https://upload-images.jianshu.io/upload_images/18339009-4a0550bda6a35972.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 2.总结
孤立森林中的 “孤立” (isolation) 指的是 “把异常点从所有样本中孤立出来”，论文中的原文是 “separating an instance from the rest of the instances”.

大多数基于模型的异常检测算法会**先 ”规定“ 正常点的范围或模式，**如果某个点不符合这个模式，或者说不在正常范围内，那么模型会将其判定为异常点。

优点：
- Partial models：在训练过程中，**每棵孤立树都是随机选取部分样本**；
- No distance or density measures：不同于 KMeans、DBSCAN 等算法，孤立森林**不需要计算有关距离、密度的指标**，可大幅度提升速度，减小系统开销；
- Linear time complexity：因为**基于 ensemble，所以有线性时间复杂度。通常树的数量越多，算法越稳定；**
- Handle extremely large data size：**由于每棵树都是独立生成的，因此可部署在大规模分布式系统上来加速运算。**

缺点：
- 若训练样本中异常样本的比例较高，可能会导致最终结果不理想，因为这违背了该算法的理论基础；

- 异常检测跟具体的应用场景紧密相关，因此算法检测出的 “异常” 不一定是实际场景中的真正异常，所以在特征选择时，要尽量过滤不相关的特征。



# 3.[sklearn](https://scikit-learn.org/stable/modules/generated/sklearn.ensemble.IsolationForest.html?highlight=ensemble%20isolationforest)

```IsolationForest(behaviour=' deprecated", bootstrap=False, contamination=0.1, max_features=1.0, max_samples=' auto',n_estimators=5e, n_jobs=None, random_state=None, verbose=e, warm_start=False)```
- n_estimators : 森林中树的颗数
- max_samples : 对每棵树，样本个数或比例
- contamination : 用户设置样本中异常点的比例
- bootstrap,采样是有放回还是无放回
- max_features : 对每棵树，特征个数或比例

方法
- predict(X)返回值：+1 表示正常样本， -1表示异常样本。
- decision_function(X)  返回样本的异常评分。 值越小表示越有可能是异常样本。
```
import numpy as np  
from sklearn.ensemble import IsolationForest  

a=[[1,2,3,4,5,7,8],
[2,3,4,5,6,8,9],
[2,3,4,5,6,7,8],
[1,3,5,6,6,7,8],
[1,10,3,5,6,2,3],
[45,67,88,52,85,84,63]]
df = np.array(a)
rng = np.random.RandomState(42)
n_samples=6  #样本总数
# fit the model
clf = IsolationForest(max_samples=n_samples, random_state=rng, contamination=0.33)  #contamination为异常样本比例
clf.fit(df)
scores_pred = clf.decision_function(df)
print(scores_pred, '----------\n', clf.predict(df))
test=[[2,4,50,3,5,69,8]]
print(clf.decision_function(test))
```
```
[ 0.07008625  0.11427815  0.14274793  0.10726402  0.00840173 -0.26031859] 
---------------
 [ 1  1  1  1 -1 -1]
[0.00754274]
```
