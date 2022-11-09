分类回归树 (classification and regression tree, CART) 。既可以用于创建分类树 (classification tree)，也可以用于创建回归树 (regression Tree)。CART 的主要特点有：

- 特征选择：根据 CART 要做回归还是分类任务，对于
**回归树：用平方残差 (square of residual) 最小化准则来选择特征，叶子上是实数值**
**分类树：用基尼指数 (Gini index) 最小化准则来选择特征。叶子上是类别值**

- 二叉树：在内节点都是对特征属性进行二分 (binary split)。根据特征属性是连续类型还是离散类型，对于

**连续类型特征 X：X 是实数。**可将 X < c 对应的样例分到左子树，X > c 对应的样例分到右子树，其中 c 是最优分界点，由最小化平方残差而得
**离散类型特征 X ：X 是 n 类变量。**假设 n = 3，特征 X 的取值是好、一般、坏。分别计算按在二分序列做分叉时的基尼指数，然后选取产生最小的基尼指数的二分序列做该特征的分叉二值序列参与回归树。因此，CART 不适用于离散特征有多个取值的场景。
- 停止条件：根据特征属性是连续类型还是离散类型，对于
**连续类型特征：当某个分支里所有样例都分到一边**
**离散类型特征：**
**1.  当某个分支里所有样例都分到一边**
**2.  当某个分支上特征已经用完了**

对“停止条件”这条还想加点说明：根据离散特征分支划分子树时，子树中不应再包含该特征。比如用特征“相貌”来划分成左子树 (相貌=丑) 和右子树 (相貌=美)，那么无论在哪颗子树再往下走，再按特征“相貌”划分完全时多余的，因为上面早已用“相貌”划分好；而根据连续特征分支时，各分支下的子树必须依旧包含该特征 (因为该连续特征再接下来的树分支过程中可能依旧起着决定性作用)。

通常先让 CART 长成一棵完整的树 (fully-grown tree)，之后为了避免过拟合，再后修剪 (post-pruning) 树
![](https://upload-images.jianshu.io/upload_images/18339009-e070a3ca28a10a16.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-5ee48a2b828366ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 损失函数
如果 y 是离散型变量 (对应着提升分类树 y 和 sign(h) 取 -1 和 1)
![](https://upload-images.jianshu.io/upload_images/18339009-504fbeaaa756d379.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如果 y 是连续性变量 (对应着提升回归树 y 和 h 取任意实数)
![](https://upload-images.jianshu.io/upload_images/18339009-0b166b4713471d20.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-674d7d0aa4d62765.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
