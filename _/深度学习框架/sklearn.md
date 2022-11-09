- [多输出的 multioutput](#head1)
- [选择模型的 model_selection](#head2)
- [流水线的 pipeline](#head3)
- [ 1.监督学习](#head4)
	- [1.1. 线性模型](#head5)
	- [1.2. 线性和二次判别分析](#head6)
	- [1.3. 内核岭回归](#head7)
	- [1.4. 支持向量机](#head8)
	- [1.5. 随机梯度下降](#head9)
	- [1.6. 最近邻](#head10)
	- [1.7. 高斯过程](#head11)
	- [1.8. 交叉分解](#head12)
	- [1.9. 朴素贝叶斯](#head13)
	- [1.10. 决策树](#head14)
	- [1.11. 集成方法](#head15)
	- [1.12. 多类和多标签算法](#head16)
	- [1.13. 特征选择](#head17)
	- [1.14. 半监督学习](#head18)
	- [1.15. 等式回归](#head19)
	- [1.16. 概率校准](#head20)
	- [1.17. 神经网络模型（监督的）](#head21)
- [ 2.无监督的学习](#head22)
	- [2.1. 高斯混合模型](#head23)
	- [2.2. 流形学习](#head24)
	- [2.3. 聚类](#head25)
	- [2.4. 双聚类](#head26)
	- [2.5. 分解组件中的信号（矩阵分解问题）](#head27)
	- [2.6. 协方差估计](#head28)
	- [2.7. 异常值检测](#head29)
	- [2.8. 密度估算](#head30)
	- [2.9. 神经网络模型（无监督）](#head31)
- [ 3.模型选择和评估](#head32)
	- [3.1. 交叉验证：评估估算器性能](#head33)
	- [3.2. 调整估算器的超参数](#head34)
- [evaluate baseline model with standardized dataset](#head35)
- [ 标准化的数据传到分类器](#head36)
- [如果不加 toarray() 的话，输出的是稀疏的存储格式，即索引加值的形式，](#head37)
- [也可以通过参数指定 sparse = False 来达到同样的效果](#head38)
- [输出 [[ 1.  0.  0.  1.  0.  0.  0.  0.  1.]]](#head39)
- [等价于 [True, False, True]](#head40)
- [输出 [[ 1.  0.  1. ...,  0.  0.  1.]](#head41)
- [     [ 0.  1.  0. ...,  0.  0.  0.]](#head42)
- [     [ 1.  0.  0. ...,  1.  0.  0.]](#head43)
- [     [ 0.  1.  1. ...,  0.  1.  0.]]](#head44)
- [encode class values as integers](#head45)
	- [6.4. 缺失值的插补](#head46)
	- [6.5. 无监督降维](#head47)
	- [6.6. 随机投影](#head48)
	- [6.7. 内核近似](#head49)
	- [6.8. 成对度量，亲和力和内核](#head50)
	- [6.9. 转换预测目标（y）](#head51)
- [ 7.数据集加载实用程序](#head52)
	- [7.1. 通用数据集API ](#head53)
		- [ 1.分类](#head54)
		- [ 2.回归](#head55)
		- [ 3.多标签回归](#head56)
	- [7.2. 玩具数据集](#head57)
	- [7.3. 现实世界的数据集](#head58)
	- [7.4. 生成数据集](#head59)
		- [ 单标签](#head60)
		- [ 多标签](#head61)
		- [ 二分聚类](#head62)
		- [ 回归生成器](#head63)
	- [7.5. 加载其他数据集](#head64)
- [8.使用scikit-learn ](#head65)
	- [8.1计算. 计算扩展的策略：更大的数据](#head66)
	- [8.2. 计算性能](#head67)
	- [8.3. 并行性，资源管理和配置](#head68)
[中文手册](https://sklearn.apachecn.org/)
[英文手册](https://scikit-learn.org/stable/user_guide.html)
在Sklearn当中有三大模型：Transformer 转换器、Estimator 估计器、Pipeline 管道

**估计器 (estimator)** 可以基于数据集对一些参数进行估计的对象都被称为估计器。
**预测器 (predictor) **在估计器上做了一个延展，延展出预测的功能。
**转换器 (transformer) **也是一种估计器，两者都带拟合功能，但估计器做完拟合来预测，而转换器做完拟合来转换。

估计器都有 fit() 方法，预测器都有 predict() 和 score() （返回的是分类准确率）方法，言外之意不是每个预测器都有 predict_proba() 和 decision_function() （返回的是每个样例在每个类下的分数值）方法

**管道将Transformer、Estimator 组合起来成为一个大模型。
Transformer放在管道前几个模型中，而Estimator 只能放到管道的最后一个模型中。**








###　多分类和多标签的 multiclass








### <span id="head1">多输出的 multioutput</span>

### <span id="head2">选择模型的 model_selection</span>
KFold交叉采样：将训练/测试数据集划分n_splits个互斥子集，每次只用其中一个子集当做测试集，剩下的（n_splits-1）作为训练集，进行n_splits次实验并得到n_splits个结果。
注：对于不能均等分的数据集，前n_samples%n_spllits子集拥有n_samples//n_spllits+1个样本，其余子集都只有n_samples//n_spllits个样本。（例10行数据分3份，只有一份可分4行，其他均为3行）

StratifiedKFold分层采样，用于交叉验证：与KFold最大的差异在于，**StratifiedKFold方法是根据标签中不同类别占比1：1来进行拆分数据的。**
### <span id="head3">流水线的 pipeline</span>
























# <span id="head4"> 1.监督学习</span>
### <span id="head5">1.1. 线性模型</span>
### <span id="head6">1.2. 线性和二次判别分析</span>
### <span id="head7">1.3. 内核岭回归</span>
### <span id="head8">1.4. 支持向量机</span>
### <span id="head9">1.5. 随机梯度下降</span>
### <span id="head10">1.6. 最近邻</span>
### <span id="head11">1.7. 高斯过程</span>
### <span id="head12">1.8. 交叉分解</span>
### <span id="head13">1.9. 朴素贝叶斯</span>
### <span id="head14">1.10. 决策树</span>
### <span id="head15">1.11. 集成方法</span>
*   `AdaBoostClassifier`: 逐步提升分类器
*   `AdaBoostRegressor`: 逐步提升回归器
*   `BaggingClassifier`: 装袋分类器
*   `BaggingRegressor`: 装袋回归器
*   `GradientBoostingClassifier`: 梯度提升分类器
*   `GradientBoostingRegressor`: 梯度提升回归器
*   `RandomForestRegressor`: 随机森林回归
*   `RandomForestClassifier`: 随机森林分类器(同质估计器)

*   `VotingClassifier`: 投票分类器(异质估计器)
*   `VotingRegressor`: 投票回归器

### <span id="head16">1.12. 多类和多标签算法</span>
### <span id="head17">1.13. 特征选择</span>
### <span id="head18">1.14. 半监督学习</span>
### <span id="head19">1.15. 等式回归</span>
### <span id="head20">1.16. 概率校准</span>
### <span id="head21">1.17. 神经网络模型（监督的）</span>
# <span id="head22"> 2.无监督的学习</span>
### <span id="head23">2.1. 高斯混合模型</span>
### <span id="head24">2.2. 流形学习</span>
### <span id="head25">2.3. 聚类</span>
### <span id="head26">2.4. 双聚类</span>
### <span id="head27">2.5. 分解组件中的信号（矩阵分解问题）</span>
### <span id="head28">2.6. 协方差估计</span>
### <span id="head29">2.7. 异常值检测</span>
### <span id="head30">2.8. 密度估算</span>
### <span id="head31">2.9. 神经网络模型（无监督）</span>
# <span id="head32"> 3.模型选择和评估</span>
### <span id="head33">3.1. 交叉验证：评估估算器性能</span>
### <span id="head34">3.2. 调整估算器的超参数</span>
```sklearn.model_selection.GridSearchCV(estimator, param_grid, scoring=None, fit_params=None, n_jobs=1, iid=True, refit=True, cv=None, verbose=0, pre_dispatch=‘2*n_jobs’, error_score=’raise’, return_train_score=’warn’)```

-  estimator：所使用的分类器
- param_grid：值为字典或者列表，即需要最优化的参数的取值
- scoring :准确度评价标准，默认None,如果是None，则使用estimator的误差估计函数。scoring参数选择如下：![](https://upload-images.jianshu.io/upload_images/18339009-8f269477140c5af1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- cv :交叉验证参数，默认None，也可以是yield训练/测试数据的生成器。
- refit :默认为True,即在搜索参数结束后，用最佳参数结果再次fit一遍全部数据集。
- iid:默认True,为True时，默认为各个样本fold概率分布一致，误差估计为所有样本之和，而非各个fold的平均。
- verbose：，0：不输出训练过程，1：偶尔输出，>1：对每个子模型都输出。
- n_jobs: 并行数-1：跟CPU核数一致, 1:默认值。
- pre_dispatch：指定总共分发的并行任务数。当n_jobs大于1时，数据将在每个运行点进行复制，这可能导致OOM，而设置pre_dispatch参数，则可以预先划分总共的job数量，使数据最多被复制pre_dispatch次

方法：
- fit（X, y=None, groups=None, **fit_params）：与所有参数组合运行。
- get_params（[deep]）：获取此分类器的参数。
- inverse_transform（Xt）使用找到的最佳参数在分类器上调用inverse_transform。
- predict（X）调用使用最佳找到的参数对估计量进行预测，X：可索引，长度为n_samples；
- score（X, y=None）返回给定数据上的分数，X： [n_samples，n_features]输入数据，其中n_samples是样本的数量，n_features是要素的数量。y： [n_samples]或[n_samples，n_output]，可选，相对于X进行分类或回归; 无无监督学习。

属性：
- cv_results_ :将键作为列标题和值作为列的字典，可将其导入到pandas DataFrame中。
- best_estimator_ : estimator或dict；由搜索选择的估算器，即在左侧数据上给出最高分数（或者如果指定最小损失）的估算器。 如果refit = False，则不可用。
- best_params_ : 在保持数据上给出最佳结果的参数设置。对于多度量评估，只有在指定了重新指定的情况下才会出现。
- best_score_ : best_estimator的平均交叉验证分数，对于多度量评估，只有在指定了重新指定的情况下才会出现。
- n_splits：交叉验证拆分的数量



### 3.3. 评分：量化预测的质量
准确率
```
from sklearn import metrics
metrics.accuracy_score(y_test, RF.predict(X_test))
```
### 3.4. 模型持久性
### 3.5. 验证曲线：绘制分数以评估模型
# 4.检查
### 4.1. 部分依赖图
### 4.2. 排列特征的重要性
# 5.可视化
### 5.1. 可用的绘图实用程序
# 6.数据集转换
### 6.1. Pipeline（管道）和 复合估计器
###### Pipeline
```
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler

# <span id="head35">evaluate baseline model with standardized dataset</span>
numpy.random.seed(seed)
estimators = []
# <span id="head36"> 标准化的数据传到分类器</span>
estimators.append(('standardize', StandardScaler()))
estimators.append(('mlp', KerasClassifier(build_fn=create_baseline, nb_epoch=100,
    batch_size=5, verbose=0)))
pipeline = Pipeline(estimators)
kfold = StratifiedKFold(y=encoded_Y, n_folds=10, shuffle=True, random_state=seed)
results = cross_val_score(pipeline, X, encoded_Y, cv=kfold)
print("Standardized: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))
```

### 6.2. 特征提取
### 6.3. 预处理数据
###### onehot
```OneHotEncoder(n_values=’auto’,  categorical_features=’all’,  dtype=<class ‘numpy.float64’>,  sparse=True,  handle_unknown=’error’)```

```n_values=’auto’```表示每个特征使用几维的数值来表示。
```categorical_features = 'all'```指定了对哪些特征进行编码，默认对所有类别都进行编码。
```dtype=<class ‘numpy.float64’>``` 表示编码数值格式，默认是浮点型。
```sparse=True``` 表示编码的格式，默认为 True，即为稀疏的格式，指定 False 则就不用 toarray() 了
```handle_unknown=’error’```其值可以指定为 "error" 或者 "ignore"，即如果碰到未知的类别，是返回一个错误还是忽略它。

```
from sklearn.preprocessing import  OneHotEncoder

enc = OneHotEncoder()
enc.fit([[0, 0, 3],
         [1, 1, 0],
         [0, 2, 1],
         [1, 0, 2]])
# <span id="head37">如果不加 toarray() 的话，输出的是稀疏的存储格式，即索引加值的形式，</span>
# <span id="head38">也可以通过参数指定 sparse = False 来达到同样的效果</span>
ans = enc.transform([[0, 1, 3]]).toarray()  
print(ans) 
# <span id="head39">输出 [[ 1.  0.  0.  1.  0.  0.  0.  0.  1.]]</span>
```
把每一行当作一个样本，每一列当作一个特征

第一个特征有两个取值 0 或者 1，那么 one-hot 就会使用两位来表示这个特征，[1,0] 表示 0， [0,1] 表示 1，
第二列 [0,1,2,0]，它有三种值，那么 one-hot 就会使用三位来表示这个特征，[1,0,0] 表示 0， [0,1,0] 表示 1，[0,0,1] 表示 2，
第三列 [3,0,1,2]，它有四种值，那么 one-hot 就会使用四位来表示这个特征，[1,0,0,0] 表示 0， [0,1,0,0] 表示 1，[0,0,1,0] 表示 2，[0,0,0,1] 表示 3

```from sklearn.preprocessing import  OneHotEncoder
enc = OneHotEncoder(n_values = [2, 3, 4])
enc.fit([[0, 0, 3],
         [1, 1, 0]])

ans = enc.transform([[0, 2, 3]]).toarray()
print(ans) # 输出 [[ 1.  0.  0.  0.  1.  0.  0.  0.  1.]]
```
注意到训练样本中第二个特征列没有类别 2，但是结果中依然将类别 2 给编码了出来，这就是自己指定维数的作用了（我们使用 3 位来表示第二个特征，自然包括了类别 2），第三列特征同样如此。这也告诫我们，**如果训练样本中有丢失的分类特征值，我们就必须显示地设置参数 n_values 了，这样防止编码出错。**
```
from sklearn.preprocessing import  OneHotEncoder

enc = OneHotEncoder(categorical_features = [0,2]) 
# <span id="head40">等价于 [True, False, True]</span>
enc.fit([[0, 0, 3],
         [1, 1, 0],
         [0, 2, 1],
         [1, 0, 2]])

ans = enc.transform([[0, 2, 3]]).toarray()
print(ans) # 输出 [[ 1.  0.  0.  0.  0.  1.  2.]]
```
输出结果中前两位 [1,0] 表示 0，中间四位 [0,0,0,1] 表示对第三个特征 3 编码，第二个特征 2 没有进行编码，就放在最后一位。
```
from sklearn.preprocessing import  OneHotEncoder

enc = OneHotEncoder(sparse = False) 
ans = enc.fit_transform([[0, 0, 3],
                         [1, 1, 0],
                         [0, 2, 1],
                         [1, 0, 2]])

print(ans) 
# <span id="head41">输出 [[ 1.  0.  1. ...,  0.  0.  1.]</span>
# <span id="head42">     [ 0.  1.  0. ...,  0.  0.  0.]</span>
# <span id="head43">     [ 1.  0.  0. ...,  1.  0.  0.]</span>
# <span id="head44">     [ 0.  1.  1. ...,  0.  1.  0.]]</span>
```

###### 类别转换为数字
```
# <span id="head45">encode class values as integers</span>
encoder = LabelEncoder()
encoder.fit(Y)
encoded_Y = encoder.transform(Y)
```

###### 练集测试集划分
```X_train,X_test, y_train, y_test =sklearn.model_selection.train_test_split(train_data,train_target,test_size=0.4, random_state=0,stratify=y_train)```
### <span id="head46">6.4. 缺失值的插补</span>
### <span id="head47">6.5. 无监督降维</span>
### <span id="head48">6.6. 随机投影</span>
### <span id="head49">6.7. 内核近似</span>
### <span id="head50">6.8. 成对度量，亲和力和内核</span>
### <span id="head51">6.9. 转换预测目标（y）</span>
# <span id="head52"> 7.数据集加载实用程序</span>
打包好的数据：对于小数据集，用 sklearn.datasets.load_*

分流下载数据：对于大数据集，用 sklearn.datasets.fetch_*

随机创建数据：为了快速展示，用 sklearn.datasets.make_*

### <span id="head53">7.1. 通用数据集API </span>
###### <span id="head54"> 1.分类</span>
手写数字数据集：
```
from sklearn.datasets import load_digits
digits = load_digits(n_class=5)  # 只加载0-4
print(digits.data.shape, digits.target.shape, digits.target_names)  
```
 (901, 64) (901,) [0 1 2 3 4 5 6 7 8 9]

线鸢尾花数据集：
```
from sklearn.datasets import load_iris
iris = load_iris()
print(iris.data.shape, iris.target.shape, iris.feature_names, iris.target_names)  
```
 (150, 4) (150,) ['sepal length (cm)', 'sepal width (cm)', 'petal length (cm)', 'petal width (cm)']  ['setosa' 'versicolor' 'virginica']

乳腺癌数据集：
```
from sklearn.datasets import load_breast_cancer
bc = load_breast_cancer()
print(bc.data.shape, bc.target.shape, bc.feature_names, bc.target_names)  
```
 (569, 30) (569,) ['mean radius' 'mean texture' ...]['malignant' 'benign'] 
###### <span id="head55"> 2.回归</span>
糖尿病数据集：
```
from sklearn.datasets import load_diabetes
diabetes = load_diabetes()
print(diabetes.data.shape, diabetes.target.shape, diabetes.feature_names) 
```
(442, 10) (442,) ['age', 'sex', 'bmi', 'bp', 's1', 's2', 's3', 's4', 's5', 's6'] Average blood pressure

波士顿房价数据集：
```
from sklearn.datasets import load_boston
boston = load_boston()
print(boston.data.shape, boston.target.shape, boston.feature_names)  
```
 (506, 13) (506,) ['CRIM' 'ZN' 'INDUS' 'CHAS' 'NOX' 'RM' 'AGE' 'DIS' 'RAD' 'TAX' 'PTRATIO' 'B' 'LSTAT']
###### <span id="head56"> 3.多标签回归</span>
体能训练数据集：

```
from sklearn.datasets import load_linnerud
linnerud = load_linnerud()
print(linnerud.data.shape, linnerud.target.shape, linnerud.target_names, linnerud.feature_names)  
```
 (20, 3) (20, 3)  ['Weight', 'Waist', 'Pulse']  ['Chins', 'Situps', 'Jumps']

### <span id="head57">7.2. 玩具数据集</span>
### <span id="head58">7.3. 现实世界的数据集</span>
### <span id="head59">7.4. 生成数据集</span>
###### <span id="head60"> 单标签</span>

make_blobs：多类单标签数据集，为每个类分配一个或多个正太分布的点集，对于中心和各簇的标准偏差提供了更好的控制，可用于演示聚类

make_classification：多类单标签数据集，为每个类分配一个或多个正太分布的点集，引入相关的，冗余的和未知的噪音特征；将高斯集群的每类复杂化；在特征空间上进行线性变换

make_gaussian_quantiles：将single Gaussian cluster （单高斯簇）分成近乎相等大小的同心超球面分离。

make_hastie_10_2：产生类似的二进制、10维问题。

make_moons/make_moons：生成二维分类数据集时可以帮助确定算法（如质心聚类或线性分类），包括可以选择性加入高斯噪声。它们有利于可视化。用球面决策边界对高斯数据生成二值分类。

###### <span id="head61"> 多标签</span>

make_multilabel_classification：生成多个标签的随机样本。

###### <span id="head62"> 二分聚类</span>

make_biclusters：Generate an array with constant block diagonal structure for biclustering。

make_checkerboard：Generate an array with block checkerboard structure for biclustering。

###### <span id="head63"> 回归生成器</span>



make_regression：产生的回归目标作为一个可选择的稀疏线性组合的具有噪声的随机的特征。它的信息特征可能是不相关的或低秩（少数特征占大多数的方差）。

make_sparse_uncorrelated: 产生目标为一个有四个固定系数的线性组合。

make_friedman1: 与多项式和正弦相关变换相联系。

make_friedman2: 包括特征相乘与交互。

make_friedman3: 类似与对目标的反正切变换。




### <span id="head64">7.5. 加载其他数据集</span>
# <span id="head65">8.使用scikit-learn </span>
### <span id="head66">8.1计算. 计算扩展的策略：更大的数据</span>
### <span id="head67">8.2. 计算性能</span>
### <span id="head68">8.3. 并行性，资源管理和配置</span>






















