---
    # 文章标题
    title: "cv-OpenCV"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00

---

`图像处理中的坐标系，水平向右为x轴正方向，竖直向下为y轴正方向`。



```python
#找源码
grep 'COLOR_BGR2RGBA' * -rn  | grep '\.hpp'
```

安装OpenCV-Python,

```bash
pip install opencv-python==3.4.2.17
```

要利用SIFT和SURF等进行特征提取时，还需要安装：

```bash
pip install opencv-contrib-python==3.4.2.17
```

core、highgui、imgproc是最基础的模块，该课程主要是围绕这几个模块展开的，分别介绍如下：

- `core模块`实现了最核心的数据结构及其基本运算，如绘图函数、数组操作相关函数等。
- `highgui模块`实现了视频与图像的读取、显示、存储等接口。
- `imgproc模块`实现了图像处理的基础方法，包括图像滤波、图像的几何变换、平滑、阈值分割、形态学处理、边缘检测、目标检测、运动分析和对象跟踪等。

对于图像处理其他更高层次的方向及应用，OpenCV也有相关的模块实现

- `features2d模块`用于提取图像特征以及特征匹配，nonfree模块实现了一些专利算法，如sift特征。
- `objdetect模块`实现了一些目标检测的功能，经典的基于Haar、LBP特征的人脸检测，基于HOG的行人、汽车等目标检测，分类器使用Cascade Classification（级联分类）和Latent SVM等。
- `stitching模块`实现了图像拼接功能。
- `FLANN模块`（Fast Library for Approximate Nearest Neighbors），包含快速近似最近邻搜索FLANN 和聚类Clustering算法。
- `ml模块`机器学习模块（SVM，决策树，Boosting等等）。
- `photo模块`包含图像修复和图像去噪两部分。
- `video模块`针对视频处理，如背景分离，前景检测、对象跟踪等。
- `calib3d模块`即Calibration（校准）3D，这个模块主要是相机校准和三维重建相关的内容。包含了基本的多视角几何算法，单个立体摄像头标定，物体姿态估计，立体相似性算法，3D信息的重建等等。
- `G-API模块`包含超高效的图像处理pipeline引擎

# 基本

```python
import cv2
cv2.namedWindow('img', cv2.WINDOW_NORMAL)
cv2.resizeWindow('img', 320, 240)
img = cv2.imread("perspective.jpeg")
# flags 0灰度，1原色
img2 = img.copy()cv2.imshow('img', img2)
key = cv2.waitKey(0)
while True:
    cv2.imshow('img', img2)

    key = cv2.waitKey(0)
    # 等待的时间ms， 0 一直等待，返回按键assic码

    if(key & 0xFF == ord('q')):
        break
    elif(key & 0xFF == ord('s')):
        cv2.imwrite("123.png", img)
    else:
        print(key)
cv2.destroyAllWindows()
```

## 鼠标

```python
setMouseCallback(winname,callback,userdata)
callback(event,x,y,flags,userdata)

#event:鼠标移动、按下左键
#X,y:鼠标坐标
#flags:鼠标键及组合键

#鼠标回调函数
def mouse_callback(event, x, y , flags, userdata):
    print(event, x, y, flags, userdata)
#创建窗口
cv2.namedWindow('mouse', cv2.WINDOW_NORMAL)
cv2.resizeWindow('mouse', 640, 360)
#设置鼠标回调
cv2.setMouseCallback('mouse', mouse_callback, "123")
#显示窗口和背景
img = np.zeros((360, 640, 3), np.uint8)
while True:
    cv2.imshow('mouse', img)
    key = cv2.waitKey(1)
    if key & 0xFF == ord('q'):
        break
cv2.destroyAllWindows()
```

##  色相

 opencv 的接口使用BGR模式，而 matplotlib.pyplot 接口使用的是RGB模式

```PYTHON
b, g, r = cv2.split(srcImage)
srcImage_new = cv2.merge([r, g, b])
plt.imshow(srcImage_new)
# 通道变换之后对灰度图进行输出的图片颜色仍然为绿色,这是因为我们还是直接使用plt显示图像，它默认使用三通道显示图像
grayImage = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)  # 灰度变换
plt.imshow(grayImage, cmap="gray")
```

`HSV`

Hue:色相，即色彩，如红色，蓝色
Saturation:饱和度，颜色的纯度
Value:明度

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_00-10-16-967.png)



`HUV`主要用在视频中

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_00-17-37-568.png)

```python
def callback(userdata):
    pass


cv2.namedWindow('color', cv2.WINDOW_NORMAL)

img = cv2.imread('./RMB.jpeg')

colorspaces = [cv2.COLOR_BGR2RGBA,
               cv2.COLOR_BGR2BGRA,
               cv2.COLOR_BGR2GRAY,
               cv2.COLOR_BGR2HSV,
               cv2.COLOR_BGR2YUV]
cv2.createTrackbar('curcolor', 'color', 0, 4, callback)

while True:
    index = cv2.getTrackbarPos('curcolor', 'color')

    # 颜色空间转换API
    cvt_img = cv2.cvtColor(img, colorspaces[index])

    cv2.imshow('color', cvt_img)
    key = cv2.waitKey(10)
    print(key)
    if key & 0xFF == ord('q'):
        break

cv2.destroyAllWindows()


# 通道分割合并
b, g, r = cv2.split(img)  # 有时需要在B，G，R通道图像上单独工作。
img2 = cv2.merge((b, g, r))

```

## 图像属性

```python
# Mat属性

# dims维度
#channels通道数RGB是3
#rows行数
#size矩阵大小
#cols列数
#type dep+dt+chs CV_8UC3
#depth像素的位深
#data存放数据
# shape属性中包括了三个信息
# 高度，长度 和 通道数
print(img.shape)
# 图像占用多大空间
# 高度 * 长度 * 通道数
print(img.size)


```



## numpy

```python
#通过array定义矩阵
# b = np.array([[1, 2, 3], [4, 5, 6]])
#定义zeros矩阵
img = np.zeros((480, 640, 3), np.uint8)
#定义ones矩阵
#d = np.ones((8,8), np.uint8)
#定义full矩阵
# e = np.full((8,8), 10, np.uint8)
#定义单位矩阵identity
# f = np.identity(8)
# g= np.eye(5, 7, k=1)

```

# 图像处理

## 绘制直线、圆



```python
import cv2
import numpy as np

img = np.zeros((480, 640, 3), np.uint8)

# 画线，坐标点为（x, y）
cv2.line(img, (10, 20), (300, 400), (0, 0, 255), 5, 4)
# line(img,开始点，结束点，颜色、线宽、线形)
# 画矩形
# cv2.rectangle(img, (10,10), (100, 100), (0, 0, 255), -1)

# 画圆
# cv2.circle(img:要绘制圆形的图像
#            Centerpoint, r: 圆心和半径
#            color: 线条的颜色
#            Thickness: 线条宽度，为-1时生成闭合图案并填充颜色)
cv2.circle(img, (320, 240), 5, (0, 0, 255), -1)

# 画椭圆
# 度是按顺时针计算的
# 0度是从左侧开始的
cv2.ellipse(img, (320, 240), (100, 50), 15, 0, 360, (0, 0, 255), -1)
# 中心点，长、宽的一半，角度，从哪个角度开始，到哪个角度结束

# 矩形
#cv.rectangle(img:要绘制矩形的图像
#            Leftupper, rightdown: 矩形的左上角和右下角坐标
#            color: 线条的颜色
#            Thickness: 线条宽度

# 画多边形
pts = np.array([(300, 10), (150, 100), (450, 100)], np.int32)
cv2.polylines(img, [pts], True, (0, 0, 255))
# 点集、是否闭环、颜色

# 填充多边形
# cv2.fillPoly(img, [pts], (255, 255, 0))
# 点集、颜色

cv2.imshow('draw', img)
cv2.waitKey(0)
```

```python
#对mask按位求反
m = cv2.bitwise_not(mask)
#选择dog添加logo的位置
roi = dog[0:200, 0:200]
#与m进行与操作
tmp = cv2.bitwise_and(roi, roi, mask=m)
```

## 二值化

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_21-40-05-948.png)

```python
# 全局阈值
thresh, dst = cv2.threshold(src, thresh, maxVal, type)
# type: cv2.THRESH_BINARY 大于阈值的为maxVal,小于的为0  cv2.THRESH_BINARY_INV 小于阈值的为maxVal,大于的为0 
ret,thresh = cv2.threshold(gray,0,255,cv2.THRESH_BINARY_INV+cv2.THRESH_OTSU) # 自适应

# 由于光照不均匀以及阴影的存在，只有一个间值会使得在阴影处的白色被二值化成黑色
# 自适应阈值
dst = cv2.adaptiveThreshold(src, maxVal, adaptiveMethod, type, blockSize, C)
#type:cv2.THRESH_BINARY
#adapttiveMethod:
#      cv2.ADAPTIVE_THRESH_MEAN_C 计算邻近区域的平均值  
#          ADAPTIVE THRESH GAUSSIAN C:高斯窗口加权平均值
#thresh=blockSize*blockSize矩阵平均值灰度-C，大于thresh的为maxValue
```

## 寻找轮廓

```python
contours, hierarchy = cv2.findContours(image, mode, method)
# 轮廓检索模式
#cv2.RETR_EXTERNAL=0,表示只检测外轮廓
#cv2.RETR_LIST=1,检测的轮廓不建立等级关系,所有轮廓放在list中
#cv2.RETR_CCOMP=2,每层最多两级
#cv2.RETR_TREE=3,按树形存储轮廓

# 轮廓近似方法
# Cv2.CHAIN_APPROX_NONE保存所有轮廓上的点
# Cv2.CHAIN_APPROX_SIMPLE只保存角点

# 返回：contours：list结构，列表中每个元素代表一个边沿信息。每个元素是(x,1,2)的三维向量，x表示该条边沿里共有多少个像素点，第三维的那个“2”表示每个点的横、纵坐标；

# hierarchy：返回类型是(x,4)的二维ndarray。x和contours里的x是一样的意思。如果输入选择cv2.RETR_TREE，则以树形结构组织输出，hierarchy的四列分别对应下一个轮廓编号、上一个轮廓编号、父轮廓编号、子轮廓编号，该值为负数表示没有对应项。
iamge = cv2.drawContours(image, contours, i, color, thickness)
# i：列表中第几个轮廓，-1所有；color：绘制颜色；thickness：线条粗细，-1填充
x, y, w, h = cv2.boundingRect(contours)  
#用一个最小的矩形，把找到的形状包起来。x，y是矩阵左上点的坐标，w，h是矩阵的宽和高

contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
cv2.drawContours(img, contours, 1, (0, 255, 0), 1)
#计算面积
area = cv2.contourArea(contours[0])
# #计算周长
len = cv2.arcLength(contours[0], False) # 是否是闭合的轮廓

# 多边形逼近（减少存储数据量）与凸包（只需轮廓不需要细节）
approx = cv2.approxPolyDP(contours[0], 20, True)
# curve:轮廓;epsilon精度;closed:是否是闭合的轮廓
hull = cv2.convexHull(contours[1])  
# points:轮廓 clockwise:顺时针绘制
drawShape(img, hull)

def drawShape(src, points):
    i = 0
    while i < len(points):
        if i == len(points) - 1:
            x, y = points[i][0]
            x1, y1 = points[0][0]
            cv2.line(src, (x, y), (x1, y1), (0, 0, 255), 3)
        else:
            x, y = points[i][0]
            x1, y1 = points[i + 1][0]
            cv2.line(src, (x, y), (x1, y1), (0, 0, 255), 3)
        i = i + 1

# -------------最小外接旋转矩形
# minAreaRect(points)
# points:轮廓返回值：RotatedRect  x,y width,height angle   包含角度
# -------------最小外接正矩形
# boundingRect(array)
# array::轮廓 返回值：Rect x,y width,height  无角度
r = cv2.minAreaRect(contours[1])
#--------------求最小外接旋转矩形的四个顶点
# 输入：rect = （（center_x, center_y）， (w, h), angle）
# 输出：box = ((x1, y1), (x2,y2), (x3, y3), (x4, y4))
box = cv2.boxPoints(r)
box = np.int0(box)
cv2.drawContours(img, [box], 0, (0, 0, 255), 2)

x, y, w, h = cv2.boundingRect(contours[1])
cv2.rectangle(img, (x, y), (x + w, y + h), (255, 0, 0), 2)
```

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_22-23-44-716.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_22-24-26-877.png)

![![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_22-26-37-785.png)](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_22-26-13-181.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_22-26-37-785.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_23-23-15-430.png)

## 几何变换

```python
# 读取图像
img = cv.imread('messi5.jpg',0)
#参数：要读取的图像；读取方式的标志
#cv.IMREAD*COLOR：以彩色模式加载图像，任何图像的透明度都将被忽略。这是默认参数。
#cv.IMREAD*GRAYSCALE：以灰度模式加载图像
#cv.IMREAD_UNCHANGED：包括alpha通道的加载图像模式。
#可以使用1、0或者-1来替代上面三个标志


# 显示图像
# opencv中显示
cv.imshow('image',img)
cv.waitKey(0)
# matplotlib中展示
plt.imshow(img[:,:,::-1])

#参数：显示图像的窗口名称，以字符串类型表示，要加载的图像
#注意：在调用显示图像的API后，要调用cv.waitKey()给图像绘留下时间，否则窗口会出现无响应情况，并且图像无法显示出来

# 保存图像
cv.imwrite('messigray.png',img)
#参数：文件名，要保存在哪里；要保存的图像

# 向图像中添加文字
cv.putText(img,text,station, font, fontsize,color,thickness,cv.LINE_AA)
#img: 图像
#text：要写入的文本数据
#station：文本的放置位置
#font：字体
#Fontsize :字体大小

    
#通过行和列的坐标值获取该像素点的像素值。
#对于BGR图像，它返回一个蓝，绿，红值的数组。对于灰度图像，仅返回相应的强度值。使用相同的方法对像素值进行修改。   
img = cv.imread('messi5.jpg')
# 获取某个像素点的值
px = img[100,100]
# 仅获取蓝色通道的强度值
blue = img[100,100,0]
# 修改某个位置的像素值
img[100,100] = [255,255,255]
# 通道拆分
b,g,r = cv.split(img)
# 通道合并
img = cv.merge((b,g,r))
# 色彩空间的改变
cv.cvtColor(image，flag)
cv.COLOR_BGR2GRAY : BGR↔Gray
cv.COLOR_BGR2HSV: BGR→HSV
# 图像的加法
#OpenCV加法和Numpy加法之间存在差异。OpenCV的加法是饱和操作，而Numpy添加是模运算。尽量使用 OpenCV 中的函数。
x = np.uint8([250])
y = np.uint8([10])
print( cv.add(x,y) ) # 250+10 = 260 => 255
print( x+y )          # 250+10 = 260 % 256 = 4

# 图像的混合
#这其实也是加法，但是不同的是两幅图像的权重不同，这就会给人一种混合或者透明的感觉。图像混合的计算公式如下：dst = α⋅img1 + β⋅img2 + γ
img3 = cv.addWeighted(img1,α,img2,β,γ)
```

### 图像缩放、翻转、平移

```python
# 图像缩放
cv2.resize(src,dsize,fx=0,fy=0,interpolation=cv2.INTER_LINEAR)
#src : 输入图像
#dsize: 绝对尺寸，直接指定调整后图像的大小 (2*cols,2*rows)
#fx,fy: 相对尺寸，将dsize设置为None，(img1,None,fx=0.5,fy=0.5)
#interpolation：插值方法，
#cv2.INTER_LINEAR  双线性插值法（周围四个点）
#cv2.INTER_NEAREST 最临近插值（边界直接拷贝，速度快，效果差）
#cv2.INTER_AREA 像素区域重采样{默认}（一片区域）
#cv2.INTER_CUBIC 双三次插值（周围16个点）

# 图像翻转
cv2.flip(img,flipCode)
#flipCode==0上下；flipCode>:0左右；flipCode<O上下+左右
    
# 图像平移
M = np.float32([[1,0,100],[0,1,50]])# 将图像的像素点移动(50,100)的距离：
dst = cv.warpAffine(img1,M,dsize=(cols,rows)，borderValue=(0,0,0))
#warpAffine(src,M,dsize,flags,mode,value)
#img: 输入图像
#M： 2*3移动矩阵
#dsize: 输出图像的大小，它应该是(宽度，高度)的形式。请记住,width=列数，height=行数。
#borderValue为边界填充颜色（注意是BGR顺序，( 0 , 0 , 0 ) (0,0,0)(0,0,0)代表黑色）:
#flag:与resize中的插值算法一致
#mode:边界外推法标志
#vaue:填充边界的值

```

对于(x,y)处的像素点，要把它移动到$(x+t_x , y+t_y)$处时，M矩阵应如下设置：

$M=\begin{bmatrix} 1&0&t_x\\ 0&1&t_y\\ \end{bmatrix}$

注意：将M设置为np.float32类型的Numpy数组。

### 图像旋转

图像旋转是指图像按照某个位置转动一定角度的过程，旋转中图像仍保持这原始尺寸。图像旋转后图像的水平对称轴、垂直对称轴及中心坐标原点都可能会发生变换，因此需要对图像旋转中的坐标进行相应转换。

那图像是怎么进行旋转的呢？如下图所示：

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-04/2023-01-04_16-53-35-325.png)

```python
#  图像旋转
#旋转中图像仍保持这原始尺寸。图像旋转后图像的水平对称轴、垂直对称轴及中心坐标原点都可能会发生变换，因此需要对图像旋转中的坐标进行相应转换。
# 生成旋转矩阵
h, w, ch = dog.shape
M = cv2.getRotationMatrix2D((w/2, h/2), 90, 1.0)
# cv2.getRotationMatrix2D(center, angle, scale)
# center：旋转中心；angle：逆时针旋转角度；scale：缩放比例
# 进行旋转变换
dst = cv.warpAffine(img,M,(cols,rows))
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-04/2023-01-04_17-06-59-000.png)



### 仿射变换

变换前后满足`平直性`（变换前是直线变换后还是直线）和`平行性`（变换前平行的线变换后依旧平行）

![quicker_f637ed85-6bd2-48b6-9c3c-b1c33325db57.png](https://s2.loli.net/2022/04/28/hTgPDuieE3BClxt.png)

```python
# 仿射变换
#仿射变换主要是对图像的缩放，旋转，翻转和平移等操作的组合。
#通过三个点可以确定变换的位置  两点共线
src = np.float32([[400, 300], [800, 300], [400, 1000]])
dst = np.float32([[200, 400], [600, 500], [150, 1100]])
M = cv2.getAffineTransform(src, dst)
dst = cv.warpAffine(img,M,(cols,rows))

#平移矩阵
#矩阵中的每个像素由(x,y)组成
#因此，其变换矩阵是2x2的矩阵
#平移向量为2x1的向量，所在平移矩阵为2x3矩阵
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-04/2023-01-04_17-07-38-415.png)

### 透射变换

`将一种坐标系转换为另一种坐标系`

![quicker_c19ebd8f-9b86-42d7-b372-dbc223a209a4.png](https://s2.loli.net/2022/04/28/awiPEVFmDq3LJsc.png)

```python
# 透射变换

# 源图像上四个点的坐标构成的矩阵，要求其中任意三点不共线
pts1 = np.float32([[56,65],[368,52],[28,387],[389,390]]) 
#目标图像上四个点的坐标构成的矩阵，要求其中任意三个点不共线，且每个点与src的对应点对应
pts2 = np.float32([[100,145],[300,100],[80,290],[310,300]])
# 根据源图像和目标图像上的四对点坐标来计算从原图像透视变换到目标头像的透视变换矩阵。
T = cv.getPerspectiveTransform(pts1,pts2)
dst = cv.warpPerspective(img,T,(width,hight))


srcPts = np.float32([kp1[m.queryIdx].pt for m in good]).reshape(-1, 1, 2)
dstPts = np.float32([kp2[m.trainIdx].pt for m in good]).reshape(-1, 1, 2)
# 单应性矩阵
H, _ = cv2.findHomography(srcPts, dstPts, cv2.RANSAC, 5.0)
# 错误匹配过滤，随机抽样抑制算法；阀值
h, w = img1.shape[:2]

pts = np.float32([[0, 0], [0, h - 1], [w - 1, h - 1], [w - 1, 0]]).reshape(-1, 1, 2)
# vector 进行透视变换
dst = cv2.perspectiveTransform(pts, H)
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-04/2023-01-04_17-08-09-241.png)

###  图像金字塔

![quicker_01b001f5-46b8-430d-a549-0fa2507eae71.png](https://s2.loli.net/2022/04/29/HYBu8aq1Q4wFAcZ.png)

```python
# 图像金字塔
cv.pyrUp(img)      #对图像进行上采样
cv.pyrDown(img)        #对图像进行下采样
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-04/2023-01-04_17-11-50-907.png)

## 形态学操作

### 连通性

![quicker_9780cacd-c3f0-4aec-a8c1-35543bc82d5c.png](https://s2.loli.net/2022/04/28/XwCNFQxH5EhgUAs.png)



![quicker_1f6186c4-8aed-459e-9ae3-ae3af2b09f3c.png](https://s2.loli.net/2022/04/28/UmGAk7bd5sRrO41.png)

### 腐蚀、膨胀



![quicker_d0033f1b-8a50-4897-ae0e-ee437855f6db.png](https://s2.loli.net/2022/04/28/FcCt2RTbWafueKZ.png)



![quicker_6cd09d96-3223-411c-a481-0003cdd07376.png](https://s2.loli.net/2022/04/29/zxWv4Vn1meaZ9ic.png)

开闭运算

![quicker_2a338865-a7ea-4e44-8dfb-9a02908053b4.png](https://s2.loli.net/2022/04/29/c9spWqvybUdoLBE.png)





腐蚀、开 消灭噪音

膨胀、闭 填补空洞



### 礼帽和黑帽

礼帽：噪音提取

黑帽：空洞提取

![quicker_e0e407cf-b766-48e8-9661-db5b0dcd19b8.png](https://s2.loli.net/2022/04/29/3C5dLUcVKSgHjsq.png)

![quicker_758b62e3-23d8-40d4-a7b1-a0c95a19af9d.png](https://s2.loli.net/2022/06/22/iDaXEGzA9Z3wSuR.png)



```python
# 腐蚀、膨胀
cv.erode(img,kernel,iterations)
cv.dilate(img,kernel,iterations)
kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (7, 7))
dst = cv2.erode(img, kernel, iterations=1)
dst1 = cv2.dilate(dst, kernel, iterations=1)

# 开闭运算# 礼帽和黑帽
kernel = np.ones((10, 10), np.uint8)# 2 创建核结构
# kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (7, 7))
#MORPH_RECT、MORPH_ELLIPSE、MORPH_CROSS
cvOpen = cv.morphologyEx(img1,cv.MORPH_OPEN,kernel) # 开运算
cvClose = cv.morphologyEx(img2,cv.MORPH_CLOSE,kernel)# 闭运算
cvOpen = cv.morphologyEx(img1,cv.MORPH_TOPHAT,kernel) # 礼帽运算
cvClose = cv.morphologyEx(img2,cv.MORPH_BLACKHAT,kernel)# 黑帽运算
# 梯度
# dst1 = cv2.morphologyEx(img, cv2.MORPH_GRADIENT, kernel) # 原图-腐蚀，得到边缘
```

## 图像平滑（滤波)



`椒盐噪声`也称为脉冲噪声，是图像中经常见到的一种噪声，它是一种`随机出现的白点或者黑点`，可能是亮的区域有黑色像素或是在暗的区域有白色像素（或是两者皆有）。椒盐噪声的成因可能是影像讯号受到突如其来的强烈干扰而产生、类比数位转换器或位元传输错误等。例如失效的感应器导致像素值为最小值，饱和的感应器导致像素值为最大值。



`高斯噪声`是指噪声密度函数服从高斯分布的一类噪声。由于高斯噪声在空间和频域中数学上的易处理性，这种噪声(也称为正态噪声)模型经常被用于实践中。高斯随机变量z的概率密度函数由下式给出：

![quicker_75363766-8572-412b-9888-e60f83e4a984.png](https://s2.loli.net/2022/05/01/PUKL18ZgFTefzrA.png)



图像平滑从信号处理的角度看就是去除其中的高频信息，保留低频信息。因此我们可以对图像实施低通滤波。低通滤波可以去除图像中的噪声，对图像进行平滑。





`均值滤波`的优点是`算法简单，计算速度较快`，缺点是`在去噪的同时去除了很多细节部分`，将图像变得模糊。

![quicker_71b104d0-8f95-4def-832d-3e3a73bdd548.png](https://s2.loli.net/2022/05/01/42dk7lqmVPBewFh.png)



`高斯平滑`在从图像中`去除高斯噪声方面非常有效`。

正态分布是一种钟形曲线，越接近中心，取值越大，越远离中心，取值越小。计算平滑结果时，只需要`将"中心点"作为原点，其他点按照其在正态曲线上的位置，分配权重`，就可以得到一个加权平均值。





`中值滤波`对`椒盐噪声（salt-and-pepper noise）来说尤其有用`，因为它不依赖于邻域内那些与典型值差别很大的值。是一种典型的非线性滤波技术，基本思想是`用像素点邻域灰度值的中值来代替该像素点的灰度值`。



![quicker_59bc7d70-51bf-45e4-9e48-e760963c175e.png](https://s2.loli.net/2022/05/01/wnAPQrxaGZ82Yty.png)

一幅图像通过滤波器得到另一幅图像；其中滤波器又称为卷积核，滤波的过程称为卷积；



`低通滤波`可以去除噪音或平滑图像
`高通滤波`可以帮助查找图像的边缘

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_17-27-43-802.png)



normalize=true,a=1/W x H
normalize=false,a=1

当normalize=true时方盒滤波=平均滤波

`高斯滤波`，去除高斯噪点，中间权重高

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_17-29-28-961.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_17-30-48-450.png)

`中值滤波`取窗口中间值，作为卷积输出。对胡椒噪音效果明显

`双边滤波`，可以保留边缘同时可以对边缘内的区域进行平滑处理，可用作美颜

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-15/2022-12-15_17-37-52-837.png)

```python
kernel = np.ones((5,5), np.float32) / 25
dst = cv2.filter2D(img, -1, kernel)
# 方盒滤波
dst = cv2.boxFilter(src, ddepth, ksize, anchor, normalize, borderType)
# 均值滤波
cv.blur(src, ksize, anchor, borderType)
#src：输入图像
#ksize：卷积核的大小
#anchor：默认值 (-1,-1) ，表示核中心
#borderType：边界类型
# 高斯滤波
cv2.GaussianBlur(src,ksize,sigmaX,sigmay,borderType)
#src: 输入图像
#ksize:高斯卷积核的大小，注意 ： 卷积核的宽度和高度都应为奇数，且可以不同
#sigmaX: 水平方向的标准差
#sigmaY: 垂直方向的标准差，默认值为0，表示与sigmaX相同
#borderType:填充边界类型
# 中值滤波
cv.medianBlur(src, ksize )
#src：输入图像
#ksize：卷积核的大小
# 双边滤波
dst = cv2.bilateralFilter(img, 7, 20, 50)
```

## 直方图

图像直方图（Image Histogram）是用以表示数字图像中亮度分布的直方图，标绘了图像中`每个亮度值的像素个数`。

这种直方图中，横坐标的左侧为较暗的区域，而右侧为较亮的区域。因此一张较暗图片的直方图中的数据多集中于左侧和中间部分，而整体明亮、只有少量阴影的图像则相反。





### 直方图均衡化

是把原始图像的灰度直方图`从比较集中的某个灰度区间变成在更广泛灰度范围内的分布`。

这种方法`提高图像整体的对比度`，特别是有用数据的像素值分布比较接近时，`在X光图像中使用广泛，可以提高骨架结构的显示`

另外在`曝光过度或不足`的图像中可以更好的突出细节。

```python
# 直方图
cv2.calcHist(images,channels,mask,histSize,ranges[,hist[,accumulate]])
#images: 原图像。当传入函数时应该用中括号 [] 括起来，例如：[img]。
#channels: 如果输入图像是灰度图，它的值就是 [0]；如果是彩色图像的话，传入的参数可以是 [0]，[1]，[2] 它们分别对应着通道 B，G，R。 　　
#mask: 掩模图像。要统计整幅图像的直方图就把它设为 None。但是如果你想统计图像某一部分的直方图的话，你就需要制作一个掩模图像，并使用它。 　　
#histSize:BIN 的数目。也应该用中括号括起来，例如：[256]。 　　
#ranges: 像素值范围，通常为 [0，256]
histr = cv.calcHist([img],[0],None,[256],[0,256])
plt.figure(figsize=(10,6),dpi=100)
plt.plot(histr)
plt.grid()
plt.show()

#  掩膜的应用
# 创建蒙版
mask = np.zeros(img.shape[:2], np.uint8)
mask[400:650, 200:500] = 255
# 查找直方图的区域上创建一个白色的掩膜图像，否则创建黑色
masked_img = cv.bitwise_and(img,img,mask=mask)
# 统计掩膜后图像的灰度图
mask_histr = cv.calcHist([img],[0],mask,[256],[1,256]) 

# 直方图均衡化
dst = cv.equalizeHist(img)
```

![quicker_d57c8302-46c3-428d-8206-ca7e1476b7ab.png](https://s2.loli.net/2022/05/01/fuIqpZUwlSbF7N4.png)

在进行完直方图均衡化之后，图片背景的对比度被改变了，在猫腿这里太暗，我们丢失了很多信息，所以在许多情况下，这样做的效果并不好。需要使用自适应的直方图均衡化

### 自适应的直方图均衡化

整幅图像会被分成很多小块，这些小块被称为“tiles”（在 OpenCV 中 tiles 的 大小默认是 8x8），然后再对`每一个小块分别进行直方图均衡化`。 

所以在每一个的区域中， 直方图会集中在某一个小的区域。`如果有噪声的话，噪声会被放大。为了避免这种情况的出现要使用对比度限制`。对于每个小块来说，`如果直方图中的 bin 超过对比度的上限的话，就把其中的像素点均匀分散到其他 bins 中，然后在进行直方图均衡化。`最后，为了 去除每一个小块之间的边界，再使用双线性差值，对每一小块进行拼接。

```python
# 自适应的直方图均衡化
cv.createCLAHE(clipLimit, tileGridSize)
#clipLimit: 对比度限制，默认是40
#tileGridSize: 分块的大小，默认为8*88∗8
```

## 边缘检测

图像边缘检测大幅度地减少了数据量，并且剔除了可以认为不相关的信息，保留了图像重要的结构属性。有许多方法用于边缘检测，它们的绝大部分可以划分为两类：基于搜索和基于零穿越。



![quicker_8cfce94d-4575-4cec-bc4c-3a4cfd27d0eb.png](https://s2.loli.net/2022/05/01/y9czuDdmoPOR7bv.png)

### Sobel算子

比较简单，实际应用中效率`比canny边缘检测效率要高`，但是边缘`不如Canny检测的准确`，但是很多实际应用的场合，sobel边缘却是首选，Sobel算子是`高斯平滑与微分操作的结合体，所以其抗噪声能力很强，用途较多`。尤其是效率要求较高，而对细纹理不太关心的时候。

`Sobel`（索贝尔）（高斯）卷积核，size设置为-1就是沙尔。

对噪音适应性强，先高斯滤波过滤噪声，再通过一阶导数求图像边缘， `Scharr`（沙尔）比sobel效果好，卷积核size不可改

上面两个计算边缘，只能求横、纵一个方向的，最后相加。

![quicker_b8444d61-1a79-48a9-9df9-ec4a3cfd7d7f.png](https://s2.loli.net/2022/05/01/FR6ZhUeb8flJop1.png)

### Laplacian算子

是利用二阶导数来检测边缘 。（拉普拉斯），对噪音敏感，需手工降噪。可以同时求两个方向的边缘

![quicker_cb380bdc-d81c-4ac5-a4c5-6e83439c8e8d.png](https://s2.loli.net/2022/05/02/xs7ztkwRfuWP1Qy.png)



### Canny 算法

被认为是最优的边缘检测算法。



使用5×5高斯滤波消除噪声
计算图像梯度的方向(0°/45°/90°/135°)
取局部极大值
阈值计算

![quicker_39c15296-346c-42e6-a6ff-4590df34bf5d.png](https://s2.loli.net/2022/05/01/9w4cPjUBeXKZ1np.png)





```python
# Sobel_x = cv2.Sobel(src, ddepth, dx, dy, dst, ksize, scale, delta, borderType)
#src：传入的图像
#ddepth: 图像的深度
#dx和dy: 指求导的阶数，0表示这个方向上没有求导，取值为0、1。
#ksize: 是Sobel算子的大小，即卷积核的大小，必须为奇数1、3、5、7，默认为3。注意：如果ksize=-1，就演变成为3x3的Scharr算子。
#scale：缩放导数的比例常数，默认情况为没有伸缩系数。
#borderType：图像边界的模式，默认值为cv2.BORDER_DEFAULT。

#Sobel函数求完导数后会有负值，还有会大于255的值。而原图像是uint8，即8位无符号数，所以Sobel建立的图像位数不够，会有截断。因此要使用16位有符号的数据类型，即cv2.CV_16S。处理完图像后，再使用cv2.convertScaleAbs()函数将其转回原来的uint8格式，否则图像无法显示。Sobel算子是在两个方向计算的，最后还需要用cv2.addWeighted( )函数将其组合起来
x = cv.Sobel(img, cv.CV_16S, 1, 0)# 2 计算Sobel卷积结果
y = cv.Sobel(img, cv.CV_16S, 0, 1)
Scale_absX = cv.convertScaleAbs(x)  # convert 转换  scale 缩放
Scale_absY = cv.convertScaleAbs(y)
result = cv.addWeighted(Scale_absX, 0.5, Scale_absY, 0.5, 0)# 4 结果合成

# 索贝尔算子y方向边缘
d1 = cv2.Sobel(img, cv2.CV_64F, 1, 0, ksize=5)
# 索贝尔算子x方向边缘
d2 = cv2.Sobel(img, cv2.CV_64F, 0, 1, ksize=5)
# 沙尔
# d1 = cv2.Scharr(img, cv2.CV_64F, 1, 0)
# d2 = cv2.Scharr(img, cv2.CV_64F, 0, 1)

dst = cv2.add(d1, d2)
# cv2.imshow('d1', d1)
# cv2.imshow('d2', d2)
# cv2.imshow('dst', dst)

# 拉普拉斯
cv2.Laplacian(src, ddepth, dst, ksize, scale, delt, borderType)
#Src: 需要处理的图像，
#Ddepth: 图像的深度，-1表示采用的是原图像相同的深度，目标图像的深度必须大于等于原图像的深度；
#ksize：算子的大小，即卷积核的大小，必须为1,3,5,7。
ldst = cv2.Laplacian(img, cv2.CV_64F, ksize=5)
cv2.imshow('img', img)


# canny检测 
0
canny = cv2.Canny(image, threshold1, threshold2)
#image:灰度图，
#threshold1: minval，较小的阈值将间断的边缘连接起来
#threshold2: maxval，较大的阈值检测图像中明显的边缘
canny = cv.Canny(img, 0, 100) 
```





# 图像特征

## 模板匹配



模板匹配，就是在给定的图片中查找和模板最相似的区域，该算法的输入包括模板和图片，整个任务的思路就是按照`滑窗`的思路不断的移动模板图片，计算其与图像中对应区域的匹配度，最终将匹配度最高的区域选择为最终的结果。

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-05/2023-01-05_00-55-07-208.png)

```python
res = cv.matchTemplate(img,template,method)
#img: 要进行模板匹配的图像
#Template ：模板
#method：实现模板匹配的算法，主要有：
#平方差匹配(CV_TM_SQDIFF)：利用模板与图像之间的平方差进行匹配，最好的匹配是0，匹配越差，匹配的值越大。
#相关匹配(CV_TM_CCORR)：利用模板与图像间的乘法进行匹配，数值越大表示匹配程度较高，越小表示匹配效果差。
#利用相关系数匹配(CV_TM_CCOEFF)：利用模板与图像间的相关系数匹配，1表示完美的匹配，-1表示最差的匹配。

#完成匹配后，使用cv.minMaxLoc()方法查找最大值所在的位置即可。如果使用平方差作为比较方法，则最小值位置是最佳匹配位置。

res = cv.matchTemplate(img, template, cv.TM_CCORR)
# 返回图像中最匹配的位置，确定左上角的坐标，并将匹配位置绘制在图像上
min_val, max_val, min_loc, max_loc = cv.minMaxLoc(res)
# top_left = min_loc
# 使用平方差时最小值为最佳匹配位置
top_left = max_loc
h,w,l = template.shape
bottom_right = (top_left[0] + w, top_left[1] + h)
cv.rectangle(img, top_left, bottom_right, (0,255,0), 2)
```

## 霍夫变换

霍夫变换常用来提取图像中的直线和圆等几何形状

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-05/2023-01-05_01-01-51-189.png)

```python
# 霍夫线检测
cv.HoughLines(img, rho, theta, threshold)
#img: 检测的图像，要求是二值化的图像，所以在调用霍夫变换之前首先要进行二值化，或者进行Canny边缘检测
#rho、theta: ρ 和θ的精确度
#threshold: 阈值，只有累加器中的值高于该阈值时才被认为是直线。
    
img = cv.imread('./image/rili.jpg')
gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
edges = cv.Canny(gray, 50, 150)
lines = cv.HoughLines(edges, 0.8, np.pi / 180, 150)
# 将检测的线绘制在图像上（注意是极坐标噢）
for line in lines:
    rho, theta = line[0]
    a = np.cos(theta)
    b = np.sin(theta)
    x0 = a * rho
    y0 = b * rho
    x1 = int(x0 + 1000 * (-b))
    y1 = int(y0 + 1000 * (a))
    x2 = int(x0 - 1000 * (-b))
    y2 = int(y0 - 1000 * (a))
    cv.line(img, (x1, y1), (x2, y2), (0, 255, 0))
plt.figure(figsize=(10,8),dpi=100)
plt.imshow(img[:,:,::-1]),plt.title('霍夫变换线检测')
plt.xticks([]), plt.yticks([])
plt.show()    

#霍夫圆检测
circles = cv.HoughCircles(image, method, dp, minDist, param1=100, param2=100, minRadius=0,maxRadius=0 )
#image：输入图像，应输入灰度图像
#method：使用霍夫变换圆检测的算法，它的参数是CV_HOUGH_GRADIENT
#dp：霍夫空间的分辨率，dp=1时表示霍夫空间与输入图像空间的大小一致，dp=2时霍夫空间是输入图像空间的一半，以此类推
#minDist为圆心之间的最小距离，如果检测到的两个圆心之间距离小于该值，则认为它们是同一个圆心
#param1：边缘检测时使用Canny算子的高阈值，低阈值是高阈值的一半。
#param2：检测圆心和确定半径时所共有的阈值
#minRadius和maxRadius为所检测到的圆半径的最小值和最大值
#返回：circles：输出圆向量，包括三个浮点型的元素——圆心横坐标，圆心纵坐标和圆半径

planets = cv.imread("./image/star.jpeg")
gay_img = cv.cvtColor(planets, cv.COLOR_BGRA2GRAY)
img = cv.medianBlur(gay_img, 7)  
circles = cv.HoughCircles(img, cv.HOUGH_GRADIENT, 1, 200, param1=100, param2=30, minRadius=0, maxRadius=100)
# 4 将检测结果绘制在图像上
for i in circles[0, :]:  # 遍历矩阵每一行的数据
    # 绘制圆形
    cv.circle(planets, (i[0], i[1]), i[2], (0, 255, 0), 2)
    # 绘制圆心
    cv.circle(planets, (i[0], i[1]), 2, (0, 0, 255), 3)
plt.figure(figsize=(10,8),dpi=100)
plt.imshow(planets[:,:,::-1]),plt.title('霍夫变换圆检测')
plt.xticks([]), plt.yticks([])
plt.show()
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-05/2023-01-05_01-06-28-005.png)

## 角点特征

图像特征要有区分性，容易被比较。一般认为角点，斑点等是较好的图像特征



模板匹配不适用于尺度变换，视角变换后的图像，这时我们就要使用关键点匹配算法，比较经典的关键点检测算法包括SIFT和SURF等，主要的思路是首先通过关键点检测算法获取模板和测试图片中的关键点；然后使用关键点匹配算法处理即可，这些关键点可以很好的处理尺度变化、视角变换、旋转变化、光照变化等，具有很好的不变性。



在角点的地方，无论你向哪个方向移动小图，结果都会有很大的不同。所以可以把它们当 成一个好的特征。

### Harris

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-27/2022-12-27_10-29-56-180.png)

光滑地区，无论向哪里移动，衡量系数不变
边缘地址，垂直边缘移动时，衡量系统变化具烈
在交点处，往那个方向移动，衡量系统都变化具烈

优点：

- `旋转不变性`，椭圆转过一定角度但是其形状保持不变（特征值保持不变）
- 对于图像灰度的仿射变化具有部分的不变性，由于仅仅使用了图像的一介导数，对于图像灰度平移变化不变；对于图像灰度尺度变化不变

缺点：

- `对尺度很敏感`，不具备几何尺度不变性。
- 提取的角点是像素级的

```python
#Hariis检测使用的API是：
dst=cv.cornerHarris(src, blockSize, ksize, k)
#img：数据类型为 ﬂoat32 的输入图像。
#blockSize：角点检测中要考虑的邻域大小。
#ksize：sobel求导使用的核大小
#k ：角点检测方程中的自由参数，取值参数为 [0.04，0.06].


# 1 读取图像，并转换成灰度图像
img = cv.imread('./image/chessboard.jpg')
gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
# 2 角点检测
# 2.1 输入图像必须是 float32
gray = np.float32(gray)
# 2.2 最后一个参数在 0.04 到 0.05 之间
dst = cv.cornerHarris(gray,2,3,0.04)
# 3 设置阈值，将角点绘制出来，阈值根据图像进行选择
img[dst>0.001*dst.max()] = [0,0,255]
# 4 图像显示
plt.figure(figsize=(10,8),dpi=100)
plt.imshow(img[:,:,::-1]),plt.title('Harris角点检测')
plt.xticks([]), plt.yticks([])
plt.show()
```



![](https://gitee.com/tomding1995/picture/raw/master/2023-01-05/2023-01-05_10-24-39-609.png)

### Shi-Tomasi

对Harris算法的改进，能够更好地检测角点；Harris角点检测算的稳定性和k有关，而k是个经验值，不好设定最佳值

![quicker_9e35dfbc-4202-42a3-870a-64675311f339.png](https://s2.loli.net/2022/05/04/NOYH29EvTesRbBJ.png)





```python
# Shi-Tomasi
corners = cv2.goodFeaturesToTrack(image, maxcorners, qualityLevel, minDistance )
#Image: 输入灰度图像
#maxCorners : 角点的数目。值为0表示无限制
#qualityLevel：最低可接受的角点质量水平，小于1.0的正数，一般在0.01-0.1之间。
#minDistance：角点之间最小的欧式距离，避免得到相邻特征点。
#mask:感兴趣的区域
#blockSize:检测窗口
#useHarrisDetector:True使用Harris算法
#k:使用Harris算法设置k,默认是0.04

#返回：Corners: 搜索到的角点，在这里所有低于质量水平的角点被排除掉，然后把合格的角点按质量排序，然后将质量较好的角点附近（小于最小欧式距离）的角点删掉，最后找到maxCorners个角点返回。



import numpy as np 
import cv2 as cv
import matplotlib.pyplot as plt
# 1 读取图像
img = cv.imread('./image/tv.jpg') 
gray = cv.cvtColor(img,cv.COLOR_BGR2GRAY)
# 2 角点检测
corners = cv.goodFeaturesToTrack(gray,1000,0.01,10)  
# 3 绘制角点
for i in corners:
    x,y = i.ravel()
    cv.circle(img,(x,y),2,(0,0,255),-1)
# 4 图像展示
plt.figure(figsize=(10,8),dpi=100)
plt.imshow(img[:,:,::-1]),plt.title('shi-tomasi角点检测')
plt.xticks([]), plt.yticks([])
plt.show()    
```



### SIFT/SURF/ORB

Harris和Shi-Tomasi角点检测算法，这两种算法具有旋转不变性，但不具有尺度不变性（缩放后，原来的角点有可能就不是角点了）

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-28/2022-12-28_09-31-50-759.png)

`SIFT（Scale-Invariant Feature Transform）算法的实质是在不同的尺度空间上查找关键点(特征点)，并计算出关键点的方向。SIFT所查找到的关键点是一些十分突出，不会因光照，仿射变换和噪音等因素而变化的点，如角点、边缘点、暗区的亮点及亮区的暗点`，但并不完美，仍然存在实时性不高，有时特征点较少，对边缘光滑的目标无法准确提取特征点等缺陷



SIFT原理：

- 尺度空间极值检测：构建高斯金字塔，高斯差分金字塔，检测极值点。
- 关键点定位：去除对比度较小和边缘对极值点的影响。
- 关键点方向确定：利用梯度直方图确定关键点的方向。
- 关键点描述：对关键点周围图像区域分块，计算块内的梯度直方图，生成具有特征向量，对关键点信息进行描述。



使用 SIFT 算法进行关键点检测和描述的执行速度比较慢， 需要速度更快的算法。 2006 年 Bay提出了 `SURF （Speeded-Up Robust Features）`算法，是SIFT算法的增强版，`它的计算量小，运算速度快，提取的特征与SIFT几乎相同`，将其与SIFT算法对比如下：

![quicker_cdef8f3f-84d9-4b0e-aa28-dd880ba6aab0.png](https://s2.loli.net/2022/05/04/f1HINV4cnmzRDKp.png)



`ORB(Oriented FAST and Rotated BRIEF)`

ORB可以做到实时检测
ORB=Oriented FAST+Rotated BRIEF

原理：是FAST算法和BRIEF算法的结合

Fast算法原理：若一个像素周围有一定数量的像素与该点像素值不同，则认为其为角点

```python
# 1.实例化sift
sift = cv.SIFT_create()

# 2.利用sift.detectAndCompute()检测关键点并计算
kp,des = sift.detectAndCompute(gray,None)
#gray: 进行关键点检测的图像，注意是灰度图像
#mask：指明对img中哪个区域进行计算
#返回：kp: 关键点信息，包括位置，尺度，方向信息
#des: 关键点描述子，每个关键点对应128个梯度信息的特征向量
#关键点：位置，大小和方向
#关键点描述子：记录了关键点周围对其有贡献的像素点的一组向量值，其不受仿射变换、光照变换等影响

# 3.将关键点检测结果绘制在图像上
cv.drawKeypoints(image, keypoints, outputimage, color, flags)
#image: 原始图像
#keypoints：关键点信息，将其绘制在图像上
#outputimage：输出图片，可以是原始图像
#color：颜色设置，通过修改（b,g,r）的值,更改画笔的颜色，b=蓝色，g=绿色，r=红色。
#flags：绘图功能的标识设置
# cv2.DRAW_MATCHES_FLAGS_DEFAULT：创建输出图像矩阵，使用现存的输出图像绘制匹配对和特征点，对每一个关键点只绘制中间点
# cv2.DRAW_MATCHES_FLAGS_DRAW_OVER_OUTIMG：不创建输出图像矩阵，而是在输出图像上绘制匹配对
# cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS：对每一个特征点绘制带大小和方向的关键点图形
# cv2.DRAW_MATCHES_FLAGS_NOT_DRAW_SINGLE_POINTS：单点的特征点不被绘制


# 1 读取图像
img = cv.imread('./image/tv.jpg')
gray= cv.cvtColor(img,cv.COLOR_BGR2GRAY)
# 2 sift关键点检测
# 2.1 实例化sift对象
sift = cv.xfeatures2d.SIFT_create()
# 2.2 关键点检测：kp关键点信息包括方向，尺度，位置信息，des是关键点的描述符
kp,des=sift.detectAndCompute(gray,None)
# 2.3 在图像上绘制关键点的检测结果
cv.drawKeypoints(img,kp,img,flags=cv.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)
# 3 图像显示
plt.figure(figsize=(8,6),dpi=100)
plt.imshow(img[:,:,::-1]),plt.title('sift检测')
plt.xticks([]), plt.yticks([])
plt.show()


# 创建surf对象
# surf = cv2.xfeatures2d.SURF_create()
# 使用surf进行检测
# kp, des = surf.detectAndCompute(gray, None)
# 绘制keypoints
# cv2.drawKeypoints(gray, kp, img)


# 创建ORB对象
# orb = cv2.ORB_create()
# orb进行检测
# kp, des = orb.detectAndCompute(gray, None)
# 绘制keypoints
# cv2.drawKeypoints(gray, kp, img)
```

shift

![shift](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_15-55-29-127.png)



orb

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_16-42-55-172.png)



## 匹配原理

### BF(Brute-Force)暴力特征匹配

它使用第一组中的每个特征的描述子；与第二组中的所有特征描术子进行匹配；计算它们之间的差距，然后将最接近一个匹配返回

速度慢，精度高

### 最快邻近区特征匹配



在进行批量特征匹配时，FLANN速度更快
由于它使用的是邻近近似值，所以精度较差

```python
# ------------------------------------------BF
img1 = cv2.imread('opencv_search.png')
img2 = cv2.imread('opencv_orig.png')
g1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
g2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
sift = cv2.xfeatures2d.SIFT_create()
kp1, des1 = sift.detectAndCompute(g1, None)
kp2, des2 = sift.detectAndCompute(g2, None)
# 1.创建匹配器
bf = cv2.BFMatcher(cv2.NORM_L1)
#normType:NORM_L1,NORM L2,HAMMING1...
#crossCheck:是否进行交叉匹配，默认为false
# 2.特征匹配
match = bf.match(des1, des2)
# 3.绘制匹配点
img3 = cv2.drawMatches(img1, kp1, img2, kp2, match, None)
#搜索img,kp
#匹配图img,kp
#match方法返回的匹配结果


# ------------------------------------------FLANN
import cv2
import numpy as np

img1 = cv2.imread('opencv_search.png')
img2 = cv2.imread('opencv_orig.png')
g1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
g2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
sift = cv2.xfeatures2d.SIFT_create()
kp1, des1 = sift.detectAndCompute(g1, None)
kp2, des2 = sift.detectAndCompute(g2, None)

# 创建匹配器
index_params = dict(algorithm=1, trees=5)
search_params = dict(checks=50)
# index_params字典：匹配算法KDTREE=1、LSH
# search_params字典：指定KDTREE算法中遍历树的次数
flann = cv2.FlannBasedMatcher(index_params, search_params)
# 对描述子进行匹配计算
matchs = flann.knnMatch(des1, des2, k=2)
# 参数为SIFT、SURF、ORB等计算的描述子
# k,表示取欧式距离最近的前k个关键点
# 返回对象：distance,描述子之间的距离，值越低越好
# queryIdx,第一个图像的描述子索引值
# trainIdx,第二个图的描述子索引值
# imgIdx,第二个图的索引值
good = []
for i, (m, n) in enumerate(matchs):
    if m.distance < 0.7 * n.distance:
        good.append(m)

# 绘制匹配点
ret = cv2.drawMatchesKnn(img1, kp1, img2, kp2, [good], None)

cv2.imshow('result', ret)
cv2.waitKey()

```

BF

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_14-56-15-195.png)

FLANN

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_14-55-15-154.png)

## 图像查找

特征匹配+单应性矩(再透视变换，可以找到图中想找的部分)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-28/2022-12-28_10-46-50-646.png)



```python
import cv2
import numpy as np

img1 = cv2.imread('opencv_search.png')
img2 = cv2.imread('opencv_orig.png')
g1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
g2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
sift = cv2.xfeatures2d.SIFT_create()
kp1, des1 = sift.detectAndCompute(g1, None)
kp2, des2 = sift.detectAndCompute(g2, None)

# 创建匹配器
index_params = dict(algorithm=1, trees=5)
search_params = dict(checks=50)
# index_params字典：匹配算法KDTREE=1、LSH
# search_params字典：指定KDTREE算法中遍历树的次数
flann = cv2.FlannBasedMatcher(index_params, search_params)
# 对描述子进行匹配计算
matchs = flann.knnMatch(des1, des2, k=2)
# 参数为SIFT、SURF、ORB等计算的描述子
# k,表示取欧式距离最近的前k个关键点
# 返回对象：distance,描述子之间的距离，值越低越好
# queryIdx,第一个图像的描述子索引值
# trainIdx,第二个图的描述子索引值
# imgIdx,第二个图的索引值
good = []
for i, (m, n) in enumerate(matchs):
    if m.distance < 0.7 * n.distance:
        good.append(m)

if len(good) >= 4:
    srcPts = np.float32([kp1[m.queryIdx].pt for m in good]).reshape(-1, 1, 2)
    dstPts = np.float32([kp2[m.trainIdx].pt for m in good]).reshape(-1, 1, 2)
    # 单应性矩阵
    H, _ = cv2.findHomography(srcPts, dstPts, cv2.RANSAC, 5.0)
    # 错误匹配过滤，随机抽样抑制算法；阀值
    h, w = img1.shape[:2]

    pts = np.float32([[0, 0], [0, h - 1], [w - 1, h - 1], [w - 1, 0]]).reshape(-1, 1, 2)
    # vector 进行透视变换
    dst = cv2.perspectiveTransform(pts, H)

    cv2.polylines(img2, [np.int32(dst)], True, (0, 0, 255))
else:
    print('the number of good is less than 4.')
    exit()
# 绘制匹配点
ret = cv2.drawMatchesKnn(img1, kp1, img2, kp2, [good], None)

cv2.imshow('result', ret)
cv2.waitKey()
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_16-50-14-420.png)



## 图像分割

#### 分水岭法

标记背景、标记前景、标记未知域、进行分割

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-02/2023-01-02_21-48-11-674.png)

```python
import cv2
import numpy as np
from matplotlib import pyplot as plt

# 获取背景
# 1. 通过二值法得到黑白图片
# 2. 通过形态学获取背景

img = cv2.imread('water_coins.jpeg')
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
ret, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)
# 开运算
kernel = np.ones((3, 3), np.int8)
open1 = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel, iterations=2)
# 膨胀
bg = cv2.dilate(open1, kernel, iterations=1)
# 获取前景物体
# 非零值到离他最近的零值的距离
# distanceTransform(img,distanceType,maskSize)
# distanceType:DIST_L1,DIST_L2
# naskSize:L1用3、L2用5
dist = cv2.distanceTransform(open1, cv2.DIST_L2, 5)
ret, fg = cv2.threshold(dist, 0.7 * dist.max(), 255, cv2.THRESH_BINARY)
# plt.imshow(dist, cmap='gray')
# plt.show()
# exit()

# 获取未知区域
fg = np.uint8(fg)
unknow = cv2.subtract(bg, fg)

# 创建连通域
# 求连通域
# connectedComponents(img,connectivity)
# connectivity:4,8（默认）
ret, marker = cv2.connectedComponents(fg)

marker = marker + 1  # 设置背景
marker[unknow == 255] = 0  # 设置未知

# 进行图像分割
result = cv2.watershed(img, marker)
# marker前景、背景设置不同的值用以区分它们
img[result == -1] = [0, 0, 255]

cv2.imshow("img", img)
cv2.imshow("unknow", unknow)
cv2.imshow("fg", fg)
cv2.imshow("bg", bg)
cv2.imshow("thresh", thresh)
cv2.waitKey()
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-03/2023-01-03_00-36-02-020.png)

#### GrabCut法

通过交互的方式获得前景物体

- 用户指定前景的大体区域，剩下的为背景区域
- 用户还可以明确指定某些地方为前景或背景
- GrabCut采用分段迭代的方法分析前景物体形成模型柄
- 最后根据权重决定某个像素是前影还是背景

```python
import cv2
import numpy as np
class App:
    flag_rect = False
    rect = (0, 0, 0, 0)
    startX = 0
    startY = 0

    def onmouse(self, event, x, y, flags, param):

        if event == cv2.EVENT_LBUTTONDOWN:  # 按下鼠标左键
            self.flag_rect = True
            self.startX = x
            self.startY = y
            print("LBUTTIONDOWN")
        elif event == cv2.EVENT_LBUTTONUP:
            self.flag_rect = False
            cv2.rectangle(self.img,
                          (self.startX, self.startY),
                          (x, y),
                          (0, 0, 255),
                          3)
            self.rect = (min(self.startX, x), min(self.startY, y),
                         abs(self.startX - x),
                         abs(self.startY - y))

            print("LBUTTIONUP")
        elif event == cv2.EVENT_MOUSEMOVE:
            if self.flag_rect:  # 鼠标左键按下才画
                self.img = self.img2.copy()
                cv2.rectangle(self.img,
                              (self.startX, self.startY),
                              (x, y),
                              (255, 0, 0),
                              3)
            print("MOUSEMOVE")

        print("onmouse")

    def run(self):
        print("run...")

        cv2.namedWindow('input')
        cv2.setMouseCallback('input', self.onmouse)

        self.img = cv2.imread('./lena.png')
        self.img2 = self.img.copy()
        self.mask = np.zeros(self.img.shape[:2], dtype=np.uint8)
        self.output = np.zeros(self.img.shape, np.uint8)

        while 1:
            cv2.imshow('input', self.img)
            cv2.imshow('output', self.output)
            k = cv2.waitKey(100)
            if k == 27:
                break

            if k == ord('g'):
                bgdmodel = np.zeros((1, 65), np.float64)
                fgdmodel = np.zeros((1, 65), np.float64)
                cv2.grabCut(self.img2, self.mask, self.rect,
                            bgdmodel, fgdmodel,
                            1,
                            cv2.GC_INIT_WITH_RECT)
                # mask BGD:背景，0；FGD:前景，1；PR BGD:可能是背景，2；PR_FGD:可能是前景，3
                # rect 选取的区域，在这个区域进行前后景分离
                # bgdModel,np.float64 type zero arrays of size(1,65)
                # fgdModel,同上
                # iteration
                # mode GC_INIT_WITH_RECT第一次，在区域中找前景； GC_INIT_WITH_MASK第二、三次迭代
            mask2 = np.where((self.mask == 1) | (self.mask == 3), 255, 0).astype('uint8')
            self.output = cv2.bitwise_and(self.img2, self.img2, mask=mask2)


App().run()
```





![](https://gitee.com/tomding1995/picture/raw/master/2023-01-03/2023-01-03_00-36-38-832.png)

#### MeanShift法

严格来说该方法并不是用来对图像分割的，而是在色彩层面的平滑滤波

它会中和色彩分布相近的颜色，平滑色彩细节，侵蚀掉面积较小的区域

它以图像上任一点P为圆心，半径为sp，色彩幅值为sr进行不断的迭代

```python
img = cv2.imread('key.png')
# pyrMeanShiftFiltering (img，dbuble sp，double sr
# sp:半径      Sr： 色彩幅值

mean_img = cv2.pyrMeanShiftFiltering(img, 20, 30)
imgcanny = cv2.Canny(mean_img, 150, 300)
contours, _ = cv2.findContours(imgcanny, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
cv2.drawContours(img, contours, -1, (0, 0, 255), 2)
cv2.imshow('img', img)
cv2.imshow('mean_img', mean_img)
cv2.imshow('canny', imgcanny)
cv2.waitKey()
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-03/2023-01-03_00-34-47-906.png)

## 视频背景抠除

视频是一组连续的帧（一幅幅图组成）
帧与帧之间关系密切(GOP)
在GOP中，背景几乎是不变的



```python
# ======================MOG去背景
# 混合高斯模型为基础的前景/背景分割算法
#createBackgroundSubtractorMOG(history,//默认200ms,建模需要多长时间的帧
#                              nmixtures,//高斯范围值，默认5，把图片分成5*5块
#                              backgroundRatio,//背景比率，默认0.7，
#                              noiseSigma//默认0，自动降噪）

cap = cv2.VideoCapture('./vtest.avi')
mog = cv2.bgsegm.createBackgroundSubtractorMOG()
while True:
    ret, frame = cap.read()
    fgmask = mog.apply(frame)
    cv2.imshow('img', fgmask)
    k = cv2.waitKey(10)
    if k == 27:
        break
cap.release()
cv2.destroyAllWindows()

## ======================MOG2去背景
# 同MOG类似，不过对亮度产生的阴影有更好的识别,但是会产生很多噪点
# cv2.createBackgroundSubtractorMOG2(history,//默认500ms,建模需要多长时间的帧
#                              detectShadows//是否检测阴影，True)）

## ======================GMG去背景
#静态背景图像估计和每个像素的贝叶斯分割,可算出阴影而且抗噪性更强,但是初始帧数大刚开始很长时间不显示
# cv2.bgsegm.createBackgroundSubtractorGMG(initializationFrames,/初始帧数，120)
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-03/2023-01-03_00-41-13-027.png)

## 图像修复

```python
#inpaint(img,mask,
#            inpaintRadius,//每个点的圆形邻域半径
#            flags //INPAINT_NS,INPAINT_TELEA 破损半径内的加权平均

img = cv2.imread('inpaint.png')
mask = cv2.imread('inpaint_mask.png', 0)
dst = cv2.inpaint(img, mask, 5, cv2.INPAINT_TELEA)
cv2.imshow('dst', dst)
cv2.imshow('img', img)
cv2.waitKey()
```

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-03/2023-01-03_00-37-48-726.png)



# 视频操作

```python
#cap = cv2.VideoCapture('./out.mp4')
cap = cv2.VideoCapture(0)
# 获取视频属性
# retval = cap.get(propId)
#0.cv2.CAP_PROP POS MSEC视频文件的当前位置(ms)
#1.cv2.CAP_PROP POS FRAMES从0开始索引帧，帧位置
#2.cv2.CAP_PROP_POS AVI RATIO视频文件的相对位置(0表示开始，1表示结束)
#3.cv2.CAP_PROP FRAME WIDTH视频流的帧宽度
#4.cv2.CAP PROP FRAME HEIGHT视频流的帧高度
#5.cv2.CAP PROP FPS帧率
#6.cv2.CAP PROP FOURCC编解码器四字符代码
#7.cv2.CAP PROP FRAME COUNT视频文件的帧
# 修改视频的属性信息
# cap.set(propId，value)

#创建VideoWriter为写多媒体文件
fourcc = cv2.VideoWriter_fourcc(*'mp4v')
vw = cv2.VideoWriter('./out.mp4', fourcc, 25,
                     (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)),
                      int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))))
#filename：视频保存的位置
#fourcc：指定视频编解码器的4字节代码cv2.VideoWriter_fourcc( c1, c2, c3, c4 ) c1,c2,c3,c4: 是视频编解码器的4字节代码，在fourcc.org中找到可用代码列表，与平台紧密相关
#fps：帧率
#frameSize：帧大小


#判断摄像头是否为打开关态
while cap.isOpened():
    #从摄像头读视频帧
    ret, frame = cap.read()
    if ret == True:
        #将视频帧在窗口中显示
        cv2.imshow('video', frame)
        #重新将窗口设备为指定大小
        cv2.resizeWindow('video', 640, 360)
        #写数据到多媒体文件
        vw.write(frame)
        #等待键盘事件，如果为q，退出
        key = cv2.waitKey(1)
        if(key & 0xFF == ord('q')):
            break
    else:
        break

#释放VideoCapture
cap.release()
#释放VideoWriter
vw.release()
#vw.release()
cv2.destroyAllWindows()
```

## 使用网络摄像头

```python
import cv2
import time

# 这里使用了网络摄像头，可换为ipconf=0使用笔记本摄像头
ipconf = 'http://192.168.68.221:4747/mjpegfeed?1920x1080'
cap = cv2.VideoCapture(ipconf)
assert cap.isOpened(), 'Wrong!'
settings = {'fps': 20, 'size': (1280, 720)}
while cap.isOpened():
    ret, frame = cap.read()
    assert ret, 'Fail to get frames!'
    frame = cv2.resize(frame, settings['size'])
    # frame = cv2.flip(frame, 0)
    h, w, ch = frame.shape
    M = cv2.getRotationMatrix2D((w/2, h/2), 270, 1.0)
    frame = cv2.warpAffine(frame, M, (w, h))

    cv2.imshow('window', frame)
    cv2.waitKey(1)
```



## 视频追踪

![](https://gitee.com/tomding1995/picture/raw/master/2023-01-05/2023-01-05_13-52-44-403.png)

图像是一个矩阵信息，如何在一个视频当中使用meanshift算法来追踪一个运动的物体呢？ 大致流程如下：

1. 首先在图像上选定一个目标区域

2. 计算选定区域的`直方图分布`，一般是HSV色彩空间的直方图。

3. 对下一帧图像b同样计算直方图分布。

4. 计算图像b当中与选定区域直方图分布最为相似的区域，使用meanshift算法将选定区域沿着最为相似的部分进行移动，直到找到最相似的区域，便完成了在图像b中的目标追踪。

5. 重复3到4的过程，就完成整个视频目标追踪。

```python
import numpy as np
import cv2 as cv
# 1.获取图像
cap = cv.VideoCapture('DOG.wmv')

# 2.获取第一帧图像，并指定目标位置
ret,frame = cap.read()
# 2.1 目标位置（行，高，列，宽）
r,h,c,w = 197,141,0,208  
track_window = (c,r,w,h)
# 2.2 指定目标的感兴趣区域
roi = frame[r:r+h, c:c+w]

# 3. 计算直方图
# 3.1 转换色彩空间（HSV）
hsv_roi =  cv.cvtColor(roi, cv.COLOR_BGR2HSV)
# 3.2 去除低亮度的值
# mask = cv.inRange(hsv_roi, np.array((0., 60.,32.)), np.array((180.,255.,255.)))
# 3.3 计算直方图
roi_hist = cv.calcHist([hsv_roi],[0],None,[180],[0,180])
# 3.4 归一化
cv.normalize(roi_hist,roi_hist,0,255,cv.NORM_MINMAX)

# 4. 目标追踪
# 4.1 设置窗口搜索终止条件：最大迭代次数，窗口中心漂移最小值
term_crit = ( cv.TERM_CRITERIA_EPS | cv.TERM_CRITERIA_COUNT, 10, 1 )

whileTrue:
    # 4.2 获取每一帧图像
    ret ,frame = cap.read()
    if ret:
        # 4.3 计算直方图的反向投影
        hsv = cv.cvtColor(frame, cv.COLOR_BGR2HSV)
        dst = cv.calcBackProject([hsv],[0],roi_hist,[0,180],1)
        # cv.meanShift(probImage, window, criteria)
        # probImage: ROI区域，即目标的直方图的反向投影
        # window： 初始搜索窗口，就是定义ROI的rect
        # criteria: 确定窗口搜索停止的准则，主要有迭代次数达到设置的最大值，窗口中心的漂移值大于某个设定的限值等。
        # 4.4 进行meanshift追踪
        ret, track_window = cv.meanShift(dst, track_window, term_crit)

        # 4.5 将追踪的位置绘制在视频上，并进行显示
        x,y,w,h = track_window
        img2 = cv.rectangle(frame, (x,y), (x+w,y+h), 255,2)
        cv.imshow('frame',img2)

        if cv.waitKey(60) & 0xFF == ord('q'):
            break
            # cv2.waitKey(1)在有按键按下的时候返回按键的ASCII值，否则返回-1
		   # & 0xFF的按位与操作只取cv2.waitKey(1)返回值最后八位，因为有些系统cv2.waitKey(1)的返回值不止八位
            # ord(‘q’)表示q的ASCII值
            # 总体效果：按下q键后break
    else:
        break
# 5. 资源释放        
cap.release()
cv.destroyAllWindows()
```

meanshift检测的窗口的大小是固定的，而狗狗由近及远是一个逐渐变小的过程，固定的窗口是不合适的。

所以我们需要根据目标的大小和角度来对窗口的大小和角度进行修正。CamShift可以帮我们解决这个问题。

CamShift算法全称是“Continuously Adaptive Mean-Shift”（连续自适应MeanShift算法），是对MeanShift算法的改进算法，可随着跟踪目标的大小变化实时调整搜索窗口的大小，具有较好的跟踪效果。



Camshift算法首先`应用meanshift，一旦meanshift收敛，它就会更新窗口的大小，还计算最佳拟合椭圆的方向，从而根据目标的位置和大小更新搜索窗口。`

```python
# 4.4进行camshift追踪
ret, track_window = cv.CamShift(dst, track_window, term_crit)

# 4.5绘制追踪结果
pts = cv.boxPoints(ret)
pts = np.int0(pts)
img2 = cv.polylines(frame,[pts],True, 255,2)
```

# 傅里叶变换



傅里叶变换的理解

任何连续周期的信号都可以由一组适当的正弦曲线组合而成



相关概念：

时域：以时间作为参照来分析动态世界的方法

频域：频域它不是真实的，而是一个数学构造

幅度谱：将信号分解为若干不同频率的正弦波，那么每一个正弦波的幅度，就叫做频谱，也叫做幅度谱

相位谱：每一个正弦波的相位，就叫做相位谱



傅里叶变换分类

傅里叶级数：任意的周期连续信号的傅里叶变换

傅里叶变换：非周期连续信号

离散傅里叶变换：非周期离散信号

