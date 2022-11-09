# 一维插值
插值不同于拟合。插值函数经过样本点，拟合函数一般基于最小二乘法尽量靠近所有样本点穿过。常见插值方法有拉格朗日插值法、分段插值法、样条插值法。
>拉格朗日插值多项式：当节点数n较大时，拉格朗日插值多项式的次数较高，可能出现不一致的收敛情况，而且计算复杂。随着样点增加，高次插值会带来误差的震动现象称为龙格现象。
分段插值：虽然收敛，但光滑性较差。
样条插值:样条插值是使用一种名为样条的特殊分段多项式进行插值的形式。由于样条插值可以使用低阶多项式样条实现较小的插值误差，这样就避免了使用高阶多项式所出现的龙格现象，所以样条插值得到了流行。
```
# -*-coding:utf-8 -*-
import numpy as np
from scipy import interpolate
import pylab as plt

x=np.linspace(0,10,11)
#x=[  0.   1.   2.   3.   4.   5.   6.   7.   8.   9.  10.]
y=np.sin(x)
xnew=np.linspace(0,10,101)
plt.plot(x,y,"ro",label='origin')

for kind in ["nearest","zero","slinear","quadratic","cubic"]:
    #"nearest","zero"为阶梯插值
    #slinear 线性插值
    #"quadratic","cubic" 为2阶、3阶B样条曲线插值
    f=interpolate.interp1d(x,y,kind=kind)
    # ‘slinear’, ‘quadratic’ and ‘cubic’ refer to a spline interpolation of first, second or third order)
    ynew=f(xnew)
    plt.plot(xnew,ynew,label=str(kind))
plt.legend(loc='best')
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-f7ae3c6475e1c128.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 二维插值
方法与一维数据插值类似，为二维样条插值。
```
# -*- coding: utf-8 -*-
import numpy as np
from scipy import interpolate
import matplotlib.pyplot as plt


def func(x, y):
    return (x+y)*np.exp(-5.0*(x**2 + y**2))


# np.mgrid[1:5:0.1, 1:3:0.1]表示1：5切片间隔为0.1，1：3切片间隔为0.1
# np.mgrid[1:5:4j, 1:3:3j]表示1：5切片均匀取数，取4个，1：3切片均匀取数，取3个
y, x = np.mgrid[-1:1:15j, -1:1:15j]  # (15, 15) (15, 15)
fvals = func(x, y)  # 计算每个网格点上的函数值  # (15, 15)

# 三次样条二维插值
newfunc = interpolate.interp2d(x, y, fvals, kind='cubic')

# 计算100*100的网格上的插值
xnew = np.linspace(-1, 1, 100)  # 100
ynew = np.linspace(-1, 1, 100)
fnew = newfunc(xnew, ynew)  # 仅仅是y值   (100, 100)


# 为了更明显地比较插值前后的区别，使用关键字参数interpolation='nearest'关闭imshow()内置的插值运算。
plt.subplot(121)
im1 = plt.imshow(fvals, extent=[-1, 1, -1, 1], cmap=plt.cm.hot, interpolation='nearest', origin="lower")
# extent=[-1,1,-1,1]为x,y范围
plt.colorbar(im1)

plt.subplot(122)
im2 = plt.imshow(fnew, extent=[-1, 1, -1, 1], cmap=plt.cm.hot, interpolation='nearest', origin="lower")
plt.colorbar(im2)
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-a2386a869da38ab4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 二维插值的三维展示方法
```
# -*- coding: utf-8 -*-
import numpy as np
from scipy import interpolate
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


def func(x, y):
    return (x+y)*np.exp(-5.0*(x**2 + y**2))


# X-Y轴分为20*20的网格
x = np.linspace(-1, 1, 2)
y = np.linspace(-1, 1, 2)
print(x, y)  # [-1.  1.]
x, y = np.meshgrid(x, y)  # (20, 20) (20, 20)
print(x, y)
# [[-1.  1.]
# [-1.  1.]]
#
# [[-1. -1.]
# [ 1.  1.]]
fvals = func(x, y)  # (20, 20)

fig = Axes3D(plt.figure(figsize=(9, 6)))
ax = plt.subplot(1, 2, 1, projection='3d')
# rstride:行之间的跨度  cstride:列之间的跨度
# rcount:设置间隔个数，默认50个，ccount:列的间隔个数  不能与上面两个参数同时出现
surf = ax.plot_surface(x, y, fvals, rstride=2, cstride=2, cmap=plt.cm.coolwarm, linewidth=0.5, antialiased=True)
ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('f(x, y)')
plt.colorbar(surf, shrink=0.5, aspect=5)

# 二维插值
newfunc = interpolate.interp2d(x, y, fvals, kind='cubic')

# 计算100*100的网格上的插值
xnew = np.linspace(-1, 1, 100)
ynew = np.linspace(-1, 1, 100)
fnew = newfunc(xnew, ynew)  # 100*100
xnew, ynew = np.meshgrid(xnew, ynew)  # 100*100 100*100
ax2 = plt.subplot(1, 2, 2, projection='3d')
surf2 = ax2.plot_surface(xnew, ynew, fnew, rstride=2, cstride=2, cmap=plt.cm.coolwarm, linewidth=0.5, antialiased=True)
ax2.set_xlabel('xnew')
ax2.set_ylabel('ynew')
ax2.set_zlabel('fnew(x, y)')
plt.colorbar(surf2, shrink=0.5, aspect=5)
plt.show()
```

![](https://upload-images.jianshu.io/upload_images/18339009-79c7fd49e80f3c62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
s
