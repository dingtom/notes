- [ 生成随机数](#head1)
- [ 生成等差数列](#head2)
- [沿着给定的(axis=0 by default)axis加入一系列数组](#head3)
- [ 多维数组降为一维:np.ravel()、np.flatten()](#head4)
- [增加一个一维          np.newaxis](#head5)
- [插入ndarray元素np.append、 np.insert](#head6)
- [ 删除ndarray元素np.delete](#head7)
- [ 矩阵比较](#head8)
- [ mgrid](#head9)
- [ meshgrid：网格](#head10)
- [ 条件语句](#head11)
	- [np.where(condition, [x, y])](#head12)
	- [numpy.piecewise(x, condlist, funclist, *args, **kw)](#head13)
		- [ argsort](#head14)
# <span id="head1"> 生成随机数</span>
**随机数种子**
```np.random.seed(42)```
**设置显示小数点后两位**
```np.set_printoptions(precision=2)```
**产生元素为0到1，大小为4\*3的随机数据矩阵**
```np.random.rand(4,3)```
**元素由标准正态分布产生，大小为4\*3的随机数矩阵**
```np.random.randn(4,3)```
**元素为0到9的随机整数，大小为4\*3的随机数矩阵**
```np.random.randint(0,10,(4,3))```
**元素由期望值为100，标准差为10的正态分布产生，大小为4\*3的随机数矩阵**
```np.random.normal(100,10,(4,3))```
**元素由起始为10，终止为20的均匀分布产生，大小为4\*3的随机数矩阵**
```np.random.uniform(10,20,(4,3))```
**元素由lambda为2的泊松分布产生，大小为4\*3的随机数矩阵**
```np.random.poisson(2.0,(4,3))```
**随机打乱数组**
```np.random.permutation(10)```
array([0, 7, 1, 3, 4, 2, 9, 5, 6, 8])
**shuffle**
permutation产生新数组，shuffle则打乱原始数组
```np.random.shuffle(a)```
**重采样**
```np.random.choice([1, 2, 3],size=(4,3))```
指定概率
```np.random.choice(a,size=(4,3),p=a/np.sum(a))```



#矩阵点乘

元素乘法：np.multiply(a,b)
矩阵乘法：np.dot(a,b) 或 np.matmul(a,b) 或 a.dot(b) 或直接用 a @ b !
唯独注意：*，在 np.array 中重载为元素乘法，在 np.matrix 中重载为矩阵乘法!

在numpy和tensorflow中都是
y = w * x    **
![image.png](https://upload-images.jianshu.io/upload_images/15873283-22aa9297a2b27ab7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#矩阵乘法
tf.matmul(w, x)
np.dot(w,x)
![image.png](https://upload-images.jianshu.io/upload_images/15873283-f149f151d0811052.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#转化为ndarray  :np.asarray、np.array
array和asarray都可以将结构数据转化为ndarray，但是主要区别就是当数据源是ndarray时，**array仍然会copy出一个副本，占用新的内存，但asarray不会。**

# <span id="head2"> 生成等差数列</span>

linspace()通过指定开始值、终值（包含终值）和**元素个数**创建表示等差数列的一维数组，

arange()通过指定开始值、终值(不包含终值)和**步长**创建表示等差数列的一维数组



# <span id="head3">沿着给定的(axis=0 by default)axis加入一系列数组</span>
np.concatenate((a1,a2,...), axis=0, out=None)

```
axis=0\1\2
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6]])
np.concatenate((a, b), axis=0)

> array([[1, 2],
       [3, 4],
       [5, 6]])

np.concatenate((a, b.T), axis=1)

> array([[1, 2, 5], 
         [3, 4, 6]])

np.concatenate((a, b), axis=None)

> array([1, 2, 3, 4, 5, 6])

```
#沿垂直方向堆叠数组:np.vstack()

```
a = np.array([1, 2, 3]) # (1,3)
b = np.array([4, 5, 6])
np.vstack((a, b))

> array([[1, 2, 3],
         [4, 5, 6]])

a = np.array([[1], [2], [3]]) # (3,1)
b = np.array([[2], [3], [4]])
np.vstack((a,b))

> array([[1],
         [2],
         [3],
         [2],
         [3],
         [4]])
```
#沿水平方向堆叠数组:np.hstack()

```
a = np.array((1,2,3))
b = np.array((2,3,4))
np.hstack((a,b))

> array([1, 2, 3, 2, 3, 4])

a = np.array([[1],[2],[3]])
b = np.array([[2],[3],[4]])
np.hstack((a,b))

> array([[1, 2],
         [2, 3],
         [3, 4]])
```
# <span id="head4"> 多维数组降为一维:np.ravel()、np.flatten()</span>
**np.flatten()返回一份拷贝，对拷贝所做修改不会影响原始矩阵，而np.ravel()返回的是视图，修改时会影响原始矩阵**
# <span id="head5">增加一个一维          np.newaxis</span>
```
            x1 = np.array([1, 2, 3, 4, 5])
            the shape of x1 is (5,)
            x1_new = x1[:, np.newaxis]
            now, the shape of x1_new is (5, 1)
```

# <span id="head6">插入ndarray元素np.append、 np.insert</span>

```
x = np.array([1, 2, 3, 4, 5])
Y = np.array([[1,2,3],[4,5,6]])


x = np.append(x, [7,8]) # 秩为1的ndarray，也可通过列表一次添加多个
> x = [1 2 3 4 5 6 7 8]


v = np.append(Y, [[7,8,9]], axis=0) # 秩为2的ndarray，添加一行
q = np.append(Y,[[9],[10]], axis=1) # 秩为2的ndarray，添加一列; 当然也可添加两列 q = np.append(Y,[[9,99],[10,100]], axis=1)
>v =
 [[1 2 3]
  [4 5 6]
  [7 8 9]]
 
> q =
 [[ 1 2 3 9]
  [ 4 5 6 10]]

```
```
x = np.array([1, 2, 5, 6, 7])
Y = np.array([[1,2,3],[7,8,9]])

x = np.insert(x,2,[3,4]) # 向秩为1的ndarray第三个元素前插入3和4
>x = [1 2 3 4 5 6 7]

w = np.insert(Y,1,[4,5,6],axis=0) # 向秩为2的ndarray第二行前，插入4,5,6
v = np.insert(Y,1,5, axis=1) # 向秩为2的ndarray前第二列前，插入5

>w =
[[1 2 3]
 [4 5 6]
 [7 8 9]]

>v =
[[1 5 2 3]
 [7 5 8 9]]
```

# <span id="head7"> 删除ndarray元素np.delete</span>
```
x = np.array([1, 2, 3, 4, 5])
Y = np.array([[1,2,3],[4,5,6],[7,8,9]])

x = np.delete(x, [0,4]) # 此处删除第一个和第五个(最后一个)元素
> x = [2 3 4]

w = np.delete(Y, 0, axis=0) # 此处删除第一行的元素
v = np.delete(Y, [0,2], axis=1) # 此处删除第一列和第三列(最后一列)的元素
>w =
[[4 5 6]
 [7 8 9]]

>v =
[[2]
 [5]
 [8]]
```
# <span id="head8"> 矩阵比较</span>
all（）操作就是对两个矩阵的比对结果再做一次与运算，而any则是做一次或运算
![](https://upload-images.jianshu.io/upload_images/18339009-3cd960158cf2a97e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# <span id="head9"> mgrid</span>
a:b:cj

a=np.mgrid[-4:4:3j]
在[-4,4]区间内取3个值
a=np.mgrid[-4:4:3]
间隔为3取值
```
x, y = np.mgrid[1:3:3j, 4:5:2j]
x=array([[1., 1.],
       [2., 2.],
       [3., 3.]])

y=array([[4., 5.],
       [4., 5.],
       [4., 5.]])
```
# <span id="head10"> meshgrid：网格</span>

1、主要使用的函数为[X,Y]=meshgrid(xgv,ygv);
meshgrid函数生成的X，Y是大小相等的矩阵，xgv，ygv是两个网格矢量，xgv，ygv都是行向量。
X：通过将xgv复制length(ygv)行（严格意义上是length(ygv)-1行）得到
Y：首先对ygv进行转置得到ygv'，将ygv'复制（length(xgv)-1）次得到。
```
[X,Y] = meshgrid(1:3,10:14)
X =     1     2     3
        1     2     3
        1     2     3
        1     2     3
        1     2     3
Y =    10    10    10
       11    11    11
       12    12    12
       13    13    13
       14    14    14
[X,Y]=meshgrid(1:3)
X =      1     2     3
         1     2     3
         1     2     3
 Y =     1     1     1
         2     2     2
         3     3     3
```
```
x = np.linspace(-1, 1, 2)
y = np.linspace(-1, 1, 2)
print(x, y)  # [-1.  1.]
x = np.meshgrid(x, y)  # (20, 20) (20, 20)
print(x)
# [[-1.  1.]
# [-1.  1.]]
#
# [[-1. -1.]
# [ 1.  1.]]
```
# <span id="head11"> 条件语句</span>
## <span id="head12">np.where(condition, [x, y])</span>
这里三个参数,其中必写参数是condition(判断条件),后边的x和y是可选参数.那么这三个参数都有怎样的要求呢?

condition：array_like，bool ,当为True时，产生x，否则产生y
![](https://upload-images.jianshu.io/upload_images/18339009-c4ed22921112a0a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head13">numpy.piecewise(x, condlist, funclist, *args, **kw)</span>

参数一 x:表示要进行操作的对象
参数二：condlist，表示要满足的条件列表，可以是多个条件构成的列表
参数三：funclist，执行的操作列表，参数二与参数三是对应的，当参数二为true的时候，则执行相对应的操作函数。

返回值：返回一个array对象，和原始操作对象x具有完全相同的维度和形状
```
np.piecewise(t,[t<1,t<0.8,t<0.3],
                    [lambda t : np.sin(2*np.pi *f1*t),
                     lambda t : np.sin(2 * np.pi * f2 * t),
                     lambda t : np.sin(2 * np.pi * f3 * t)])
```

### <span id="head14"> argsort</span>
```
x = np.array([3, 1, 2])
np.argsort(x)
```
array([1, 2, 0])
```    
x = np.array([[0, 3], 
            [2, 2]])

np.argsort(x, axis=0)
    array([[0, 1],
           [1, 0]])
np.argsort(x, axis=1)
    array([[0, 1],
           [0, 1]])
```
