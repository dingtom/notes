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

MIPS

Million Instructions executed Per Second,每秒执行百万条指令，用来计算同一秒内系统的处理能力，即每秒执行了多少百万条指令。
DMIPS
Dhrystone Million Instructions executed Per Second,主要用于测试整数计算能力。
MFLOPS
Million Floating-point Operations per Second主要用于测浮点计算能力。



- 模型压缩

知识蒸馏(teacher-student网络)

light weight结构设计，例如MobileNet、 SqueezeNet

模型量化(二值化网络，INT8量化)

- 模型精简

CNN剪枝(结构化剪枝、非结构化剪枝)
低秩分解

- 加速计算

Op-level快速算法
Layer-level快速算法FFT Conv2d等

- 优化加速

NAS（神经网络架构搜索）
网络并行结构合并(tensorRT)



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

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-40-42-261.png)



![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-42-33-519.png)

### Distortion Correction

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-44-37-275.png)



![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-45-00-136.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-45-39-183.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-02/2022-12-02_23-46-06-842.png)