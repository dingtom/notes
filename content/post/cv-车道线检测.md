---
        title: "cv-车道线检测"
        categories:
            - cv
        date: 2022-12-03T01:30:21+08:00

---


## 摄像头主要指标

- FOV视场角

  视场角越大，信息越多，畸变越大，视场角越小，信息越少，畸变越小

- 分辨率
  一般指像素数，分辨率越高，信息越多，计算速度越慢
- 几何畸变
  分桶形畸变和枕形畸变等，由于相机镜头非标准小孔成像模型，故而产生畸变
- 色差
  标准颜色与摄像头获取颜色之间的差
- 曝光
  曝光不准确(过度、不足)，严重时导致信息缺失



## 传统图像处理方法

- 颜色信息

- 高斯噪声过滤

- Canny边缘检测

- 霍夫变换检测直线

- K Means聚类霍夫检测

- 高斯概率模型进行消失点检测

- 透视变换鸟瞰图

- 曲线拟合

- 车道线提取

- 车道线曲率计算

  ## 数据集

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-40-42-261.png)



![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-42-33-519.png)

### Distortion Correction

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-44-37-275.png)



![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-45-00-136.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-45-39-183.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-46-06-842.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_10-24-13-004.png)

图片坐标系共线的点，在霍夫空间映射成相交的线，相交的点表示共的线

dterlane：基于dter的车道线检测，对于dter系列的理解，我是：稀里糊涂的问，一本正经的答
lanATT ：把yolo改来做车道线检测，性能不错，就是复杂了点
LaneNet ：基于分割的算法，做车道线，后处理太繁项，导致缺乏实时性

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_10-10-01-254.png)

Ultra fast ：将车道线检测转化为分类问题，简单、粗果、有效

SCNN:

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_10-12-12-430.png)

