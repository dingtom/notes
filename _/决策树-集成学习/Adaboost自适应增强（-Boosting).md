- [ 思想](#head1)
- [ 练习](#head2)
- [ 损失函数](#head3)
- [ 正则化](#head4)
- [ 优缺点](#head5)
- [ sklearn](#head6)
	- [ numpy](#head7)
AdaBoost（Adaptive Boosting，自适应提升），其自适应在于：**前一个基本分类器分错的样本权重会加大，加权后的全体样本再次被用来训练下一个基本分类器**。同时，**在每一轮中加入一个新的弱分类器，直到达到某个预定的足够小的错误率或达到预先指定的最大迭代次数**。最后，**根据弱分类器的表现给它们加上权重，组合成一个强大的分类器**


# <span id="head1"> 思想</span>

Adaboost 迭代算法有三步：

1.  初始化训练样本的权值分布，**每个样本具有相同权重**；

2.  训练弱分类器，如果样本**分类正确，则在构造下一个训练集中，它的权值就会被降低**；反之提高。**用更新过的样本集去训练下一个分类器**；

3.  将所有弱分类组合成强分类器，各个弱分类器的训练过程结束后，**加大误差小的弱分类器的权重**，降低误差大的弱分类器的权重。

![](https://upload-images.jianshu.io/upload_images/18339009-af97f95cacd6ba46?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-f4024b394b3d09bc?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

这个公式可以保证，分类器的分类错误率越高，相应的权重就越大。

用  代表第 k+1 轮训练中，样本的权重集合，其中  代表第 k+1 轮中第一个样本的权重，因此用公式表示为：

![](https://upload-images.jianshu.io/upload_images/18339009-1e2ce5e0d03b3715?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

第 k+1 轮中的样本权重，是根据该样本在第 k 轮的权重以及第 k 个分类器的准确率而定

![](https://upload-images.jianshu.io/upload_images/18339009-1793477f3e8b28d9?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

这个公式保证的就是，如果当前分类器把样本分类错误了，那么样本的w就会变大，如果分类正确了，w就会减小。 这里的Zk是归一化系数

# <span id="head2"> 练习</span>

![](https://upload-images.jianshu.io/upload_images/18339009-33dade455521a362?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

*   首先，我得先给这10个样本划分重要程度，也就是权重，由于是一开始，那就平等，都是1/10。即初始权重D1=(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1)。假设我训练的3个基础分类器如下：

![](https://upload-images.jianshu.io/upload_images/18339009-3ce67f6228c8ed0e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

*   分类器 f1 的错误率为 0.3，也就是 x 取值 6、7、8 时分类错误；分类器 f2 的错误率为 0.4，即 x 取值 0、1、2、9 时分类错误；分类器 f3 的错误率为 0.3，即 x 取值为 3、4、5 时分类错误。根据误差率最小，我训练出一个分类器来如下(选择f1)：

    ![](https://upload-images.jianshu.io/upload_images/18339009-dc4397c818be2338?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

这个分类器的错误率是0.3（x取值6, 7，8的时候分类错误），是误差率最低的了。e1 = 0.3

*   那么根据权重公式得到第一个弱分类器的权重：

    ![](https://upload-images.jianshu.io/upload_images/18339009-f2ebfd93afd0c4c1?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-9cb1f28061849d8f?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

根据这个公式，就可以计算权重矩阵为：D2=(0.0715, 0.0715, 0.0715, 0.0715, 0.0715, 0.0715, 0.1666, 0.1666, 0.1666, 0.0715)。

你会发现，6, 7, 8样本的权重变大了，其他的权重变小（这就意味着，下一个分类器训练的时候，重点关注6, 7, 8这三个样本，）

*   接着我们进行第二轮的训练，继续统计三个分类器的准确率，可以得到：

分类器 f1 的错误率为 0.1666 * 3，也就是 x 取值为 6、7、8 时分类错误。分类器 f2 的错误率为 0.0715 * 4，即 x 取值为 0、1、2、9 时分类错误。分类器 f3 的错误率为 0.0715 * 3，即 x 取值 3、4、5时分类错误。在这 3 个分类器中，f3 分类器的错误率最低，因此我们选择 f3 作为第二轮训练的最优分类器，即：

![](https://upload-images.jianshu.io/upload_images/18339009-646ff8a9077af160?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-91d25fc2a5bdb0bc?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

![](https://upload-images.jianshu.io/upload_images/18339009-aac466fd6a3fce87?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

可以得到 D3=(0.0455,0.0455,0.0455,0.1667, 0.1667,0.01667,0.1060, 0.1060, 0.1060, 0.0455)。你会发现， G2分类错误的3，4， 5这三个样本的权重变大了，说明下一轮的分类器重点在上三个样本上面。

*   假设我们只进行 3 轮的训练，选择 3 个弱分类器，组合成一个强分类器，那么最终的强分类器



# <span id="head3"> 损失函数</span>

Adaboost 模型是加法模型，学习算法为前向分步学习算法，损失函数为指数函数的分类问题。

**加法模型**：最终的**强分类器是由若干个弱分类器加权平均**得到的。

**前向分布学习算法**：算法是通过**一轮轮的弱学习器学习，利用前一个弱学习器的结果来更新后一个弱学习器的训练集权重**。第 k 轮的强学习器为：

![](https://upload-images.jianshu.io/upload_images/18339009-1501f9e190e6f64a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# <span id="head4"> 正则化</span>

为了防止 Adaboost 过拟合，我们通常也会加入正则化项，这个正则化项我们通常称为步长（learning rate）。对于前面的弱学习器的迭代

![](https://upload-images.jianshu.io/upload_images/18339009-2d66557718820a25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

# <span id="head5"> 优缺点</span>

**优点**

1.  **分类精度高**；

2.  可以用各种回归分类模型来构建弱学习器，非常灵活；

3.  **不容易发生过拟合**。

**缺点**

1.  **对异常点敏感**，异常点会获得较高权重。

# <span id="head6"> sklearn</span>

`AdaBoostClassifier(base_estimator=None,n_estimators=50, learning_rate=1.0, algorithm=’SAMME.R’, random_state=None)`

*   base_estimator：代表的是弱分类器。在 AdaBoost 的分类器和回归器中都有这个参数，在 AdaBoost 中默认使用的是决策树，一般我们不需要修改这个参数，当然你也可以指定具体的分类器。

*   n_estimators：算法的最大迭代次数，也是分类器的个数，每一次迭代都会引入一个新的弱分类器来增加原有的分类器的组合能力。默认是 50。

*   learning_rate：代表学习率，取值在 0-1 之间，默认是 1.0。如果学习率较小，就需要比较多的迭代次数才能收敛，也就是说学习率和迭代次数是有相关性的。当你调整 learning_rate 的时候，往往也需要调整 n_estimators 这个参数。

*   algorithm：代表我们要采用哪种 boosting 算法，一共有两种选择：SAMME 和 SAMME.R。默认是 SAMME.R。这两者之间的区别在于对弱分类权重的计算方式不同。

*   random_state：代表随机数种子的设置，默认是 None。随机种子是用来控制随机模式的，当随机种子取了一个值，也就确定了一种随机规则，其他人取这个值可以得到同样的结果。如果不设置随机种子，每次得到的随机数也就不同。

`AdaBoostRegressor(base_estimator=None, n_estimators=50, learning_rate=1.0, loss=‘linear’, random_state=None)`

```python
import numpy as np
 import matplotlib.pyplot as plt
 from sklearn import datasets
 from sklearn.metrics import zero_one_loss
 from sklearn.tree import DecisionTreeClassifier
 from sklearn.ensemble import  AdaBoostClassifier
 # 设置AdaBoost迭代次数
 n_estimators=200
 # 使用
 X,y=datasets.make_hastie_10_2(n_samples=12000,random_state=1)
 # 从12000个数据中取前2000行作为测试集，其余作为训练集
 train_x, train_y = X[2000:],y[2000:]
 test_x, test_y = X[:2000],y[:2000]
 # 弱分类器
 dt_stump = DecisionTreeClassifier(max_depth=1,min_samples_leaf=1)
 dt_stump.fit(train_x, train_y)
 dt_stump_err = 1.0-dt_stump.score(test_x, test_y)
 # 决策树分类器
 dt = DecisionTreeClassifier()
 dt.fit(train_x,  train_y)
 dt_err = 1.0-dt.score(test_x, test_y)
 # AdaBoost分类器
 ada = AdaBoostClassifier(base_estimator=dt_stump,n_estimators=n_estimators)
 ada.fit(train_x,  train_y)
 # 三个分类器的错误率可视化
 fig = plt.figure()
 # 设置plt正确显示中文
 plt.rcParams['font.sans-serif'] = ['SimHei']
 ax = fig.add_subplot(111)
 ax.plot([1,n_estimators],[dt_stump_err]*2, 'k-', label=u'决策树弱分类器 错误率')
 ax.plot([1,n_estimators],[dt_err]*2,'k--', label=u'决策树模型 错误率')
 ada_err = np.zeros((n_estimators,))
 # 遍历每次迭代的结果 i为迭代次数, pred_y为预测结果
 for i,pred_y in enumerate(ada.staged_predict(test_x)):
  # 统计错误率
  ada_err[i]=zero_one_loss(pred_y, test_y)
 # 绘制每次迭代的AdaBoost错误率
 ax.plot(np.arange(n_estimators)+1, ada_err, label='AdaBoost Test 错误率', color='orange')
 ax.set_xlabel('迭代次数')
 ax.set_ylabel('错误率')
 leg=ax.legend(loc='upper right',fancybox=True)
 plt.show()
```

## <span id="head7"> numpy</span>
```
先定义一个决策树桩，本质上就是一个带有阈值划分的决策树结点。
class DecisionStump():
    def __init__(self):
        # 基于划分阈值决定样本分类为1还是-1
        self.polarity = 1
        # 特征索引
        self.feature_index = None
        # 特征划分阈值
        self.threshold = None
        # 指示分类准确率的值
        self.alpha = None
     然后直接定义一个Adaboost算法类，将上述算法流程在类中实现。
class Adaboost():
    # 弱分类器个数
    def __init__(self, n_estimators=5):
        self.n_estimators = n_estimators
    # Adaboost拟合算法
    def fit(self, X, y):
        n_samples, n_features = X.shape

        # (1) 初始化权重分布为均匀分布 1/N
        w = np.full(n_samples, (1/n_samples))
        
        self.estimators = []
        # (2) for m in (1,2,...,M)
        for _ in range(self.n_estimators):
            # (2.a) 训练一个弱分类器：决策树桩
            clf = DecisionStump()
            # 设定一个最小化误差
            min_error = float('inf')
            # 遍历数据集特征，根据最小分类误差率选择最优划分特征
            for feature_i in range(n_features):
                feature_values = np.expand_dims(X[:, feature_i], axis=1)
                unique_values = np.unique(feature_values)
                # 尝试将每一个特征值作为分类阈值
                for threshold in unique_values:
                    p = 1
                    # 初始化所有预测值为1
                    prediction = np.ones(np.shape(y))
                    # 小于分类阈值的预测值为-1
                    prediction[X[:, feature_i] < threshold] = -1
                    # 2.b 计算误差率
                    error = sum(w[y != prediction])
                    
                    # 如果分类误差大于0.5，则进行正负预测翻转
                    # E.g error = 0.8 => (1 - error) = 0.2
                    if error > 0.5:
                        error = 1 - error
                        p = -1

                    # 一旦获得最小误差则保存相关参数配置
                    if error < min_error:
                        clf.polarity = p
                        clf.threshold = threshold
                        clf.feature_index = feature_i
                        min_error = error
                        
            # 2.c 计算基分类器的权重
            clf.alpha = 0.5 * math.log((1.0 - min_error) / (min_error + 1e-10))
            # 初始化所有预测值为1
            predictions = np.ones(np.shape(y))
            # 获取所有小于阈值的负类索引
            negative_idx = (clf.polarity * X[:, clf.feature_index] < clf.polarity * clf.threshold)
            # 将负类设为 '-1'
            predictions[negative_idx] = -1
            # 2.d 更新样本权重
            w *= np.exp(-clf.alpha * y * predictions)
            w /= np.sum(w)

            # 保存该弱分类器
            self.estimators.append(clf)
    
    # 定义预测函数
    def predict(self, X):
        n_samples = np.shape(X)[0]
        y_pred = np.zeros((n_samples, 1))
        # 计算每个弱分类器的预测值
        for clf in self.estimators:
            # 初始化所有预测值为1
            predictions = np.ones(np.shape(y_pred))
            # 获取所有小于阈值的负类索引
            negative_idx = (clf.polarity * X[:, clf.feature_index] < clf.polarity * clf.threshold)
            # 将负类设为 '-1'
            predictions[negative_idx] = -1
            # 2.e 对每个弱分类器的预测结果进行加权
            y_pred += clf.alpha * predictions

        # 返回最终预测结果
        y_pred = np.sign(y_pred).flatten()
        return y_pred
     这样，一个完整Adaboost算法就搞定了。我们使用sklearn默认数据集来看一下算法效果。
from sklearn import datasets
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split

data = datasets.load_digits()
X = data.data
y = data.target

digit1 = 1
digit2 = 8
idx = np.append(np.where(y==digit1)[0], np.where(y==digit2)[0])
y = data.target[idx]
# Change labels to {-1, 1}
y[y == digit1] = -1
y[y == digit2] = 1
X = data.data[idx]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.7)

# 使用5个弱分类器
clf = Adaboost(n_clf=5)
clf.fit(X_train, y_train)
y_pred = clf.predict(X_test)

accuracy = accuracy_score(y_test, y_pred)
print ("Accuracy:", accuracy)
     验证集分类精度接近达到0.94，可见我们编写的Adaboost算法还比较成功。
Accuracy: 0.9397590361445783
```
