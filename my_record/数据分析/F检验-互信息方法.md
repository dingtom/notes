# F检验用于评估两个随机变量的线性相关性，
# 互信息方法可以计算任何类型的统计依赖关系，但作为非参数方法，需要更多的样本来估计准确。
```
# F检验与互信息比较
from sklearn.feature_selection import f_regression, mutual_info_regression
# 生成原始数据
np.random.seed(0)
X = np.random.rand(1000, 3)
# 由一二列制作
y = X[:, 0] + np.sin(6 * np.pi * X[:, 1]) + 0.1 * np.random.randn(1000)

# 计算f检验值f_test与互信息值mi
f_test, _ = f_regression(X, y)
f_test /= np.max(f_test)
# print(f_test) [1.         0.28024353 0.00252204]
mi = mutual_info_regression(X, y)
mi /= np.max(mi)
# print(mi) [0.36448455 1.         0.        ]

plt.figure(figsize=(15, 5))
for i in range(3):
    plt.subplot(1, 3, i + 1)
    plt.scatter(X[:, i], y, edgecolor='black', s=20)
    plt.xlabel("$x_{}$".format(i + 1), fontsize=14)
    plt.ylabel("$y$", fontsize=14)
    plt.title("F-test={:.2f}, MI={:.2f}".format(f_test[i], mi[i]),
              fontsize=16)
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-d665d9c66accbcae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
