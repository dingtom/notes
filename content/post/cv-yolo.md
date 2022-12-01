---
    # 文章标题
    title: "cv-yolo"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
    
    # 标签
    #tags:
    # 文章内容摘要
    #description: "{{ .Name }}" 
    # 最后修改日期
    #lastmod: {{ .Date }}
    # 文章内容关键字
    #keywords: "{{replace .Name "-" ","}}"
    # 原文作者
    #author:
    # 原文链接
    #link:
    # 图片链接，用在open graph和twitter卡片上
    #imgs:
    # 在首页展开内容
    #expand: true
    # 外部链接地址，访问时直接跳转
    #extlink:
    # 在当前页面关闭评论功能
    #comment:
    # enable: false
    # 关闭当前页面目录功能
    # 注意：正常情况下文章中有H2-H4标题会自动生成目录，无需额外配置
    #toc: false
    # 绝对访问路径
    #url: "{{ lower .Name }}.html"
    # 开启文章置顶，数字越小越靠前
    #weight: 1
    #开启数学公式渲染，可选值： mathjax, katex
    #math: mathjax
    # 开启各种图渲染，如流程图、时序图、类图等
    #mermaid: true
--- 

## YOLO vs Faster R-CNN



Faster R-CNN**将检测结果分为两部分求解**：物体类别（分类问题）、物体位置即bounding box（回归问题），YOLO**统一为一个回归问题**。



统一网络：YOLO没有显示求取region proposal的过程。Faster R-CNN中尽管RPN与fast rcnn共享卷积层，但是在模型训练过程中，需要反复训练RPN网络和fast rcnn网络。相对于R-CNN系列的"看两眼"(候选框提取与分类)，YOLO只需要Look Once.



>- YOLOv1论文名以及论文地址：**You Only Look Once:Unified, Real-Time Object Detection**、
You Only Look Once:Unified, Real-Time Object Detection: *https://arxiv.org/pdf/1506.02640.pdf*
YOLOv1开源代码：**YOLOv1-Darkent**
YOLOv1-Darkent: *https://github.com/pjreddie/darknet*
>- YOLOv2论文名以及论文地址：**YOLO9000:Better, Faster, Stronger**
YOLO9000:Better, Faster, Stronger: *https://arxiv.org/pdf/1612.08242v1.pdf*
YOLOv2开源代码：**YOLOv2-Darkent**
YOLOv2-Darkent: *https://github.com/pjreddie/darknet*
>- YOLOv3论文名以及论文地址：**YOLOv3: An Incremental Improvement**
YOLOv3: An Incremental Improvement: *https://arxiv.org/pdf/1804.02767.pdf*
YOLOv3开源代码：**YOLOv3-PyTorch**
YOLOv3-PyTorch: *https://github.com/ultralytics/yolov3*
>- YOLOv4论文名以及论文地址：**YOLOv4: Optimal Speed and Accuracy of Object Detection**
YOLOv4: Optimal Speed and Accuracy of Object Detection: *https://arxiv.org/pdf/2004.10934.pdf*
YOLOv4开源代码：**YOLOv4-Darkent**
YOLOv4-Darkent: *https://github.com/AlexeyAB/darknet*
>- YOLOv5论文名以及论文地址：无
YOLOv5开源代码：**YOLOv5-PyTorch**
YOLOv5-PyTorch: *https://github.com/ultralytics/yolov5*
>- YOLOx论文名以及论文地址：**YOLOX: Exceeding YOLO Series in 2021**
YOLOX: Exceeding YOLO Series in 2021: *https://arxiv.org/pdf/2107.08430.pdf*
YOLOx开源代码：**YOLOx-PyTorch**
YOLOx-PyTorch: *https://github.com/Megvii-BaseDetection/YOLOX*
>- YOLOv6论文名以及论文地址：**YOLOv6: A Single-Stage Object Detection Framework for Industrial Applications**
YOLOv6: A Single-Stage Object Detection Framework for Industrial Applications: *https://arxiv.org/pdf/2209.02976.pdf*
YOLOv6开源代码：**YOLOv6-PyTorch**
YOLOv6-PyTorch: *https://github.com/meituan/YOLOv6*
>- YOLOv7论文名以及论文地址：**YOLOv7: Trainable bag-of-freebies sets new state-of-the-art for real-time object detectors**
YOLOv7: Trainable bag-of-freebies sets new state-of-the-art for real-time object detectors: *https://arxiv.org/pdf/2207.02696.pdf*
YOLOv7开源代码：**Official YOLOv7-PyTorch**
Official YOLOv7-PyTorch: *https://github.com/WongKinYiu/yolov7*

# V1

**you only look once:unified, real-time object detection**

## 思想

**yolo的核心思想是将输入的图像经过backbone特征提取后，将的到的特征图划分为S x S的网格，物体的中心落在哪一个网格内，这个网格就负责预测该物体的置信度、类别以及坐标位置。**

- 将一幅图像分成SxS个网格(grid cell),如果某个object的中心落在这个网格中，则这个网格就负责预测这个object。

- 每个网格要预测B个bounding box,每个bounding box除了要预测位置之外，还要附带预测一个confidence值。每个网格还要预测C个类别的分数。

![quicker_009f6375-cf72-45a5-8c9a-71d462a9fca2.png](https://s2.loli.net/2022/03/30/fmPys3jO1xXeaS4.png)

![quicker_e565eedf-5844-4a6d-9f72-9f5d292438b9.png](https://s2.loli.net/2022/04/07/Q28uDgZbSz59Kf3.png)

​        该表达式含义：如果有object落在一个grid cell里，则第一项取1，否则取0。 第二项是预测的bounding box和实际的groundtruth之间的IoU值。 



![quicker_8554cab6-43ed-43ba-b576-e57ea2ae429a.png](https://s2.loli.net/2022/03/30/vt8dCxbKTMXD3jo.png)

B=2， 7\*7\*30包含了坐标、置信度、类别结果

x.y是相对每一个gird cell左上角点的坐标，w,h是相对整幅图像的宽高，都是0-1的值。



## 网络结构

![quicker_bbfc17a0-fd55-46e7-a924-608320874e3c.png](https://s2.loli.net/2022/04/07/wGxvH52dr4uq3jQ.png)



最后一层全连接层用线性激活函数，其余层采用 Leaky ReLU。

![quicker_ddc8f262-fece-4ee4-aeb9-276fb623a62d.png](https://s2.loli.net/2022/05/08/sl2un1bEIVZgktB.png)

## 后处理



2个框重合度很高，大概率是一个目标，那就只取一个框。

首先从所有的检测框中找到置信度最大的那个，然后遍历剩余的框，计算其与最大框之间的IOU。如果其值大于一定阈值，则表示重合度过高，那么就将该框就会被剔除；然后对剩余的检测框重复上述过程，直到处理完所有的检测框。

![quicker_d32d2664-49e5-4c50-9f54-c8b28e313f25.png](https://s2.loli.net/2022/03/30/Q8WsNpUHTr7S6xC.png)





## 损失函数

损失函数有三个部分组成，分别是边框损失，置信度损失，以及类别损失。并且三个损失函数都使用均方差损失函数(MSE)。



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-21/2022-10-21_16-43-29-953.png)



只有当某个网格中有object的时候才对classification error进行惩罚。

只有当某个box predictor对某个ground truth box负责的时候，才会对box的coordinate error进行惩罚，而对哪个ground truth box负责就看其预测值和ground truth box的IoU是不是在那个cell的所有box中最大



紫色的框是当gird cell有真实框的中心点的时候取1，否则取0

红色的框为第i个gird cell第j个bounding box是负责预测物体，是为1否则为0

绿色的框呢是第i个gird cell第j个bounding box不负责预测物体为1否则为0



再说两个λ，对于负责预测物体的框呢要严重惩罚所以赋值为５，不负责的呢意思意思为０.５再说每一项，

第一项是中心点定位误差，

第二项是宽高误差，开根号主要是为了惩罚小框，对大框公平一点

第三项是置信度误差，标签是通过计算这个框与ground truth的iou

最后一项是类别预测误差，这个概率就是条件概率乘以置信度，所得到的20维度的类别概率

> 宽和高开根号，是因为偏移相同的距离，对小目标影响更大![quicker_fe20384e-9a44-4c4b-b1d2-8ac93ab34f7b.png](https://s2.loli.net/2022/05/08/MdCFshx5IwDUYty.png)



## 缺陷

![quicker_ea58d9a6-762d-431b-84da-54c0e2cabe7a.png](https://s2.loli.net/2022/05/08/uASTXq365lkvEjF.png)

- YOLO对**相互靠的很近的物体和很小的群体检测效果不好**，这是因为一个网格中只预测了两个框，并且只属于一类；
- 准确度低，recall低
- 同一类物体出现的新的**不常见的长宽比和其他情况时，泛化能力偏弱**；
- **由于损失函数的问题，定位误差是影响检测效果的主要原因**。尤其是大小物体的处理上，还有待加强。

# v2



YOLOv2 也叫 YOLO9000，因为使用了 COCO 数据集以及 Imagenet 数据集来联合训练，最终可以检测9000个类别。

## 思想

使用 Darknet19 作为网络的主干网络。Darknet19 有点类似 VGG，在 Darknet19 中，使用的是 3 x 3 大小的卷积核，并且在每次Pooling 之后都增加一倍通道数，以及将特征图的宽高缩减为原来的一半。网络中有19个卷积层，所以叫 Darknet19，以及有5个 Max Pooling 层，所以这里进行了32倍的下采样。



## 网络结构

使用 Darknet19 作为网络的主干网络。Darknet19 有点类似 VGG，在 Darknet19 中，使用的是 3 x 3 大小的卷积核，并0且在每次Pooling 之后都增加一倍通道数，以及将特征图的宽高缩减为原来的一半。网络中有19个卷积层，所以叫 Darknet19，以及有5个 Max Pooling 层，所以这里进行了32倍的下采样。

![quicker_e071e5e8-99ff-40ab-9c03-b511a0572d92.png](https://s2.loli.net/2022/05/08/cENjmszArXtovaY.png)

![quicker_4ff8f074-ade7-42a8-b122-cb0c80d438d9.png](https://s2.loli.net/2022/05/08/HnX14fM7NuaCwmV.png)

## 改进

### Batch Normalization

在卷积层的后面、激活函数的前面加上了BN层，

使得网络可以加速收敛，

解决了梯度弥散的问题。

不太受初始化的影响，

还可以起到正则化的作用。

![quicker_a21a2608-2876-4a16-80b6-8d6a59448be0.png](https://s2.loli.net/2022/05/08/lV83pBA7FsOHE6G.png)

零均值标准差为1，sigmod、双曲正切函数(tanh)在0附近有比较大的梯度



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-32-57-469.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-36-22-728.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-41-01-767.png)



### 高分辨率分类器

High Resolution Classifier

先来看看yolov1是怎么做的，首先让ImageNet的图像resize到224\*224训练特征提取层，使用更高的448*448的分辨率训练检测头

yolov2避免了这个突变，首先对224\*224的预训练之后，然后在448*448的分辨率上微调10个epoch，这样的过度使得mAP又增加了3.7个百分点

![quicker_cc2c9f30-c06a-4066-8cad-1c85007659c8.png](https://s2.loli.net/2022/05/08/7xGFfyglPTtpber.png)

### Anchor

Convolutional With Anchor Boxes

先受Faster-rcnn的启发，使用**锚框然后预测它的偏移量，而不是直接预测坐标位置**，这样网络学习起来会更容易。所谓锚框，就是之前设定好不同宽高比的先验框

为了更好的理解anchor，我们先来回忆一下yolov1没有anchor的时候是怎么回事？四个坐标呢完全就是由网络计算出来的，(x, y)坐标表示相对于网格单元格边界的方框中心，宽高是相对于整个图像的宽度和高度，这个值生成的宽高比、中心点的位置幔帐图像乱跑的，明显更难预测。

作者输入的是416\*416，而不是448\*448，因为下采样32倍之后416\*416的图像是13\*13，它是一个奇数，为什么我们想要奇数呢？因为图片的中心往往存在大物体，作者希望有一个单独的grid cell来负责预测这个物体，而不是周围的4个

作者将原图划分为13\*13个gird cell，每个grid cell会有5个预测框，13\*13\*5这个数远远大于v1中的7\*7\*2，所以作者去掉了全连接层，参数太多，计算太慢

但是添加了锚框之后mAP下降了0.2%，这依然是一个很好的改进，因为他解决了V1的召回率低的问题，使recall增加到了88%

![quicker_ebf88288-5a3f-4e1d-8ac0-1acd1551802a.png](https://s2.loli.net/2022/05/08/OQAohyxdTgPZ1Ir.png)

### 聚类anchor

Dimension Clusters

先验框先验框，这个宽高比该怎么定呢，该选择几个锚框呢，3个、5个？手动选择吗？显然不是那么合理，所以作何采用了对训练集的bounding_box进行**k_means聚类**的方法。

![quicker_2940e48e-7b55-4a37-89cd-55562f7f9846.png](https://s2.loli.net/2022/05/08/jSmZx9cCOErkywX.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-48-31-857.png)

### Direct location prediction

虽然说现在使用了锚框，但是它的中心点还是可以乱跑的。作者对边框进行了限制，

保证锚框的中心点只能在负责预测的这个grid cell里面（通过sigmoid函数映射到0-1），宽高不设限制。

这里的cx,cy是经过归一化了的，一个cx,cy是1，也就是如果要可视化在原图上还要乘以13

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_16-57-58-123.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_16-57-04-305.png)

![quicker_eddfe836-9897-4e0a-ae21-04ae470c5e8c.png](https://s2.loli.net/2022/05/08/642gsR7lVd9nf1x.png)

### Fine-Grained Features

![1652019960723](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1652019960723.png)

 ![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_17-20-10-865.png)



类似 Pixel-shuffle，**融合高层和低层的信息，这样可以保留一些细节信息，这样可以更好检测小物体。**具体来说，就是进行一拆四的操作，直接传递到池化后的特征图中，进行卷积后再叠加两者，最后一起作为输出特征图进行输出。通过使用 Pass through 层来检测细粒度特征使 mAP 提升了1个点。 

<img src='https://s2.loli.net/2022/05/08/e3TdWYOHxlI2QDA.png' title='quicker_e58852f3-aa65-4471-af7e-881bbeae12b1.png' />

### 多尺度训练

Multi-Scale Training

 由于这个模型只有卷积层、池化层，所以可以动态的调整输入图像的大小，为了让网络适应不同的尺度的图像，**每10个batch，网络随机选择一个新的图像维度大小**，他们都是32的倍数,这一步是在检测数据集上fine tune时候采用的

![quicker_ee5ea3b8-9757-4c4e-ac61-4d1be128c63e.png](https://s2.loli.net/2022/05/08/dPnTxls9EhVo4FM.png)

### 







![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_17-25-21-331.png)









![quicker_be167748-486f-4193-a962-45a55c4fa985.png](https://s2.loli.net/2022/05/08/zmoA9DNHcVFRkeg.png)

## 损失函数

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-22-37-301.png)

# V3

## 网络结构

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_11-16-28-274.png)





上图三个蓝色方框内表示Yolov3的三个基本组件：

1. **CBL：**Yolov3网络结构中的最小组件，由**Conv+Bn+Leaky_relu**激活函数三者组成。
2. **Res unit：**借鉴**Resnet**网络中的残差结构，让网络可以构建的更深。
3. **ResX：**由一个**CBL**和**X**个残差组件构成，是Yolov3中的大组件。每个Res模块前面的CBL都起到下采样的作用，因此经过5次Res模块后，得到的特征图是**608->304->152->76->38->19大小**。

其他基础操作：

1. **Concat：**张量拼接，会扩充两个张量的维度，例如26\*26\*256和26\*26\*512两个张量拼接，结果是26\*26\*768。Concat和cfg文件中的route功能一样。
2. **add：**张量相加，张量直接相加，不会扩充维度，例如104\*104\*128和104\*104\*128相加，结果还是104\*104\*128。add和cfg文件中的shortcut功能一样。

每个ResX中包含1+2*X个卷积层，因此整个主干网络Backbone中一共包含**1+（1+2\*1）+（1+2\*2）+（1+2\*8）+（1+2\*8）+（1+2\*4）=52**，再加上一个FC全连接层，即可以组成一个**Darknet53分类网络**。不过在目标检测Yolov3中，去掉FC层，不过为了方便称呼，仍然把**Yolov3**的主干网络叫做**Darknet53结构**。



卷积的strides默认为（1，1），padding默认为same，当strides为（2，2）时padding为valid。

上图是以输入图像256 x 256进行预训练来进行介绍的，常用的尺寸是416 x 416，都是32的倍数。



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-51-36-317.png)

![quicker_63fb0c5f-b01b-48ea-8421-3db82da2b5a3.png](https://s2.loli.net/2022/05/08/Q1AWzDPpg4sr2jC.png)





**原Darknet53中的尺寸是在图片分类训练集上训练的，所以输入的图像尺寸是256x256，下图是以YOLO v3 416模型进行绘制的，所以输入的尺寸是416x416，预测的三个特征层大小分别是52，26，13。**

![quicker_1e5320d6-33b2-4bdf-8484-555e43228621.png](https://s2.loli.net/2022/05/08/6vFiPaYE5kXMQDZ.png)

在上图中我们能够很清晰的看到三个预测层分别来自的什么地方，以及Concatenate层与哪个层进行深度方向拼接（FPN对应维度上相加）。**注意Convolutional是指Conv2d+BN+LeakyReLU，和Darknet53图中的一样，而生成预测结果的最后三层都只是Conv2d。**

## 目标边界框的预测



![quicker_07c7df41-0dfc-4598-ac25-dfff4392091f.png](https://s2.loli.net/2022/05/08/rPwdmlf4nOvXDjU.png)

## 损失函数

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-22/2022-10-22_15-08-59-922.png)



![1652025879465](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1652025879465.png)

# V3 SPP

而Yolov3和Yolov3_spp的不同点在于，Yolov3的主干网络后面，**添加了spp组件**，这里需要注意。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_16-51-57-117.png)



# V4

[YOLOv4](https://so.csdn.net/so/search?q=YOLOv4&spm=1001.2101.3001.7020): Optimal Speed and Accuracy of Object Detection

## 网络结构



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-23/2022-10-23_16-51-42-777.png)



**先整理下Yolov4的五个基本组件**：

1. **CBM：**Yolov4网络结构中的最小组件，由Conv+Bn+Mish激活函数三者组成。
2. **CBL：**由Conv+Bn+Leaky_relu激活函数三者组成。
3. **Res unit：**借鉴Resnet网络中的残差结构，让网络可以构建的更深。
4. **CSPX：**借鉴CSPNet网络结构，由卷积层和X个Res unint模块Concate组成。
5. **SPP：**采用1×1，5×5，9×9，13×13的最大池化的方式，进行多尺度融合。

**其他基础操作：**

1. **Concat：**张量拼接，维度会扩充，和Yolov3中的解释一样，对应于cfg文件中的route操作。
2. **add：**张量相加，不会扩充维度，对应于cfg文件中的shortcut操作。

**Backbone中卷积层的数量：**

和Yolov3一样，再来数一下Backbone里面的卷积层数量。

每个CSPX中包含5+2\*X个卷积层，因此整个主干网络Backbone中一共包含1+（5+2\*1）+（5+2*2）+（5+2\*8）+（5+2\*8）+（5+2\*4）=72。



Backbone: CSPDarknet53
Neck: SPP，PAN
Head: YOLOv3

相比之前的`YOLOv3`，改进了下Backbone，在`Darknet53`中引入了`CSP`模块（来自`CSPNet`）。在Neck部分，采用了`SPP`模块（`Ultralytics`版的`YOLOv3 SPP`就使用到了）以及`PAN`模块（来自`PANet`）。Head部分没变还是原来的检测头。





## CSPDarknet53网络结构

CSPDarknet53就是将CSP结构融入了Darknet53中。CSP结构是在CSPNet（Cross Stage Partial Network）论文中提出的，CSPNet作者说在目标检测任务中使用CSP结构有如下好处：

Strengthening learning ability of a CNN
Removing computational bottlenecks
Reducing memory costs
**即减少网络的计算量以及对显存的占用，同时保证网络的能力不变或者略微提升。**CSP结构的思想参考原论文中绘制的CSPDenseNet，**进入每个stage（一般在下采样后）先将数据划分成俩部分**，如下图所示的Part1和Part2。但具体怎么划分呢，**在CSPNet中是直接按照通道均分**，但在**YOLOv4网络中是通过两个1x1的卷积层来实现的**。**在Part2后跟一堆Blocks然后在通过1x1的卷积层（图中的Transition），接着将两个分支的信息在通道方向进行Concat拼接，最后再通过1x1的卷积层进一步融合**（图中的Transition）。



![quicker_9c3da51b-5d06-4c84-a90b-c08a371a0edf.png](https://s2.loli.net/2022/05/09/drBFhsqePQcnuYi.png)



CSPDarknet53详细结构（以输入图片大小为416 × 416 × 3 为例）

- 注意，`CSPDarknet53` Backbone中所有的激活函数都是`Mish`激活函数

  ![quicker_d078c76a-ae00-4272-b93a-f4a5a4944b02.png](https://s2.loli.net/2022/05/09/mlPByM4hdgoIS71.png)



## 网络详细结构

![quicker_e0ec51d7-d578-4fb5-8a36-e590863d705f.png](https://s2.loli.net/2022/05/09/S7vgi5XTmFNOM21.png)





## 改进

YoloV4的创新之处进行讲解，让大家一目了然。

1. **输入端：**这里指的创新主要是训练时对输入端的改进，主要包括**Mosaic数据增强、cmBN、SAT自对抗训练**
2. **BackBone主干网络：**将各种新的方式结合起来，包括：**CSPDarknet53、Mish激活函数、Dropblock**
3. **Neck：**目标检测网络在BackBone和最后的输出层之间往往会插入一些层，比如Yolov4中的**SPP模块**、**FPN+PAN结构**
4. **Prediction：**输出层的锚框机制和Yolov3相同，主要改进的是训练时的损失函数**CIOU_Loss**，以及预测框筛选的nms变为**DIOU_nms**

### 输入端

这里指的创新主要是训练时对输入端的改进，主要包括**Mosaic数据增强、cmBN、SAT自对抗训练**

#### Mosaic数据增强

**Yolov4**中使用的**Mosaic**是参考2019年底提出的**CutMix数据增强**的方式，但**CutMix**只使用了两张图片进行拼接，而**Mosaic数据增强**则采用了4张图片，**随机缩放、随机裁剪、随机排布**的方式进行拼接。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-23/2022-10-23_16-54-55-361.png)

主要有几个优点：

1. **丰富数据集：**随机使用**4张图片**，随机缩放，再随机分布进行拼接，大大丰富了检测数据集，特别是随机缩放增加了很多小目标，让网络的鲁棒性更好。
2. **减少GPU：**可能会有人说，随机缩放，普通的数据增强也可以做，但作者考虑到很多人可能只有一个GPU，因此Mosaic增强训练时，可以直接计算4张图片的数据，使得Mini-batch大小并不需要很大，一个GPU就可以达到比较好的效果。

### Backbone

#### CSPDarknet53

**CSPDarknet53**是在Yolov3主干网络**Darknet53**的基础上，借鉴**2019年CSPNet**的经验，产生的**Backbone**结构，其中包含了**5个CSP**模块。



每个CSP模块前面的卷积核的大小都是3\*3，stride=2，因此可以起到下采样的作用。因为Backbone有5个**CSP模块**，输入图像是**608\*608**，所以特征图变化的规律是：**608->304->152->76->38->19**.经过5次CSP模块后得到19*19大小的特征图。

CSPNet论文地址：[https://arxiv.org/pdf/1911.11929.pdf](https://link.zhihu.com/?target=https%3A//arxiv.org/pdf/1911.11929.pdf)

CSPNet全称是Cross Stage Paritial Network，CSPNet的作者认为推理计算过高的问题是由于网络优化中的**梯度信息重复**导致的。因此采用CSP模块先将基础层的特征映射划分为两部分，然后通过**跨阶段层次结构将它们合并**，在**减少了计算量的同时可以保证准确率**。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-24/2022-11-24_19-39-16-343.png)



#### Mish激活函数

而且作者只在Backbone中采用了**Mish激活函数**，网络后面仍然采用**Leaky_relu激活函数。**



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-23/2022-10-23_19-23-58-105.png)

#### Dropblock

Yolov4中使用的**Dropblock**，其实和常见网络中的Dropout功能类似，也是缓解过拟合的一种正则化方式。

Dropblock在2018年提出，论文地址：[https://arxiv.org/pdf/1810.12890.pdf](https://link.zhihu.com/?target=https%3A//arxiv.org/pdf/1810.12890.pdf)

传统的Dropout很简单，一句话就可以说的清：**随机删除减少神经元的数量，使网络变得更简单。**

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-32-03-855.png)

中间Dropout的方式会随机的删减丢弃一些信息，但**Dropblock的研究者**认为，卷积层对于这种随机丢弃并不敏感，因为卷积层通常是三层连用：**卷积+激活+池化层**，池化层本身就是对相邻单元起作用。而且即使随机丢弃，卷积层仍然可以从相邻的激活单元学习到**相同的信息**。

因此，在全连接层上效果很好的Dropout在卷积层上**效果并不好**。

所以**右图Dropblock的研究者**则干脆整个局部区域进行删减丢弃。

这种方式其实是借鉴**2017年的cutout数据增强**的方式，cutout是将输入图像的部分区域清零，而Dropblock则是将Cutout应用到每一个特征图。而且并不是用固定的归零比率，而是在训练时以一个小的比率开始，随着训练过程**线性的增加这个比率**。

**Dropblock**的研究者与**Cutout**进行对比验证时，发现有几个特点：

**优点一：**Dropblock的效果优于Cutout

**优点二：**Cutout只能作用于输入层，而Dropblock则是将Cutout应用到网络中的每一个特征图上

**优点三：**Dropblock可以定制各种组合，在训练的不同阶段可以修改删减的概率，从空间层面和时间层面，和Cutout相比都有更精细的改进。

**Yolov4**中直接采用了更优的**Dropblock**，对网络的正则化过程进行了全面的升级改进。  

### Neck

目标检测网络在BackBone和最后的输出层之间往往会插入一些层，比如Yolov4中的**SPP模块**、**FPN+PAN结构**

#### SPP模块

第一个预测特征层添加了SPP（Spatial Pyramid Pooling）模块，实现了不同尺度的特征融合
注意：这里的SPP和SPPnet中的SPP结构不一样



SPP模块，其实在Yolov3中已经存在了，在**Yolov4**的C++代码文件夹中有一个**Yolov3_spp版本**，但有的同学估计从来没有使用过，在Yolov4中，SPP模块仍然是在Backbone主干网络之后：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-36-24-126.png)

作者在SPP模块中，使用**k={1\*1,5\*5,9\*9,13\*13}**的最大池化的方式，再将不同尺度的特征图进行Concat操作。

**注意：**这里最大池化采用**padding操作**，移动的步长为1，比如13×13的输入特征图，使用5×5大小的池化核池化，**padding=2**，因此池化后的特征图仍然是13×13大小。

#### FPN+PAN

**PAN结构**比较有意思，看了网上Yolov4关于这个部分的讲解，大多都是讲的比较笼统的，而PAN是借鉴[图像分割领域PANet](https://link.zhihu.com/?target=https%3A//arxiv.org/abs/1803.01534)的创新点，有些同学可能不是很清楚。

下面大白将这个部分拆解开来，看下Yolov4中是如何设计的。

**Yolov3结构：**

我们先来看下Yolov3中Neck的FPN结构

  ![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-51-36-317.png)

可以看到经过几次下采样，三个紫色箭头指向的地方，输出分别是**76\*76、38\*38、19\*19。**

以及最后的**Prediction**中用于预测的三个特征图**①19\*19\*255、②38\*38\*255、③76\*76\*255。[注：255表示80类别(1+4+80)×3=255]**

我们将Neck部分用立体图画出来，更直观的看下两部分之间是如何通过**FPN结构**融合的。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-53-31-895.png)

如图所示，FPN是自顶向下的，将高层的特征信息通过**上采样**的方式进行传递融合，得到进行预测的特征图。

**Yolov4结构：**

而Yolov4中Neck这部分除了使用FPN外，还在此基础上使用了PAN结构：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-56-16-588.png)



前面CSPDarknet53中讲到，每个CSP模块前面的卷积核都是**3\*3大小**，**步长为2**，相当于下采样操作。

因此可以看到三个紫色箭头处的特征图是**76\*76、38\*38、19\*19。**

以及最后Prediction中用于预测的三个特征图：**①76\*76\*255，②38\*38\*255，③19\*19\*255。**

我们也看下**Neck**部分的立体图像，看下两部分是如何通过**FPN+PAN结构**进行融合的。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_09-55-30-821.png)

和Yolov3的FPN层不同，Yolov4在FPN层的后面还添加了一个**自底向上的特征金字塔。**

其中包含两个**PAN结构。**

这样结合操作，FPN层自顶向下传达**强语义特征**，而特征金字塔则自底向上传达**强定位特征**，两两联手，从不同的主干层对不同的检测层进行参数聚合,这样的操作确实很皮。

**FPN+PAN**借鉴的是18年CVPR的**PANet**，当时主要应用于**图像分割领域**，但Alexey将其拆分应用到Yolov4中，进一步提高特征提取的能力。

不过这里需要注意几点：

**注意一：**

Yolov3的FPN层输出的三个大小不一的特征图①②③直接进行预测

但Yolov4的FPN层，只使用最后的一个76*76特征图①，而经过两次PAN结构，输出预测的特征图②和③。

这里的不同也体现在cfg文件中，这一点有很多同学之前不太明白，

比如Yolov3.cfg最后的三个Yolo层，

第一个Yolo层是最小的特征图**19\*19**，mask=**6,7,8**，对应**最大的anchor box。**

第二个Yolo层是中等的特征图**38\*38**，mask=**3,4,5**，对应**中等的anchor box。**

第三个Yolo层是最大的特征图**76\*76**，mask=**0,1,2**，对应**最小的anchor box。**

而Yolov4.cfg则**恰恰相反**

第一个Yolo层是最大的特征图**76\*76**，mask=**0,1,2**，对应**最小的anchor box。**

第二个Yolo层是中等的特征图**38\*38**，mask=**3,4,5**，对应**中等的anchor box。**

第三个Yolo层是最小的特征图**19\*19**，mask=**6,7,8**，对应**最大的anchor box。**

**注意点二：**

原本的PANet网络的**PAN结构**中，两个特征图结合是采用**shortcut**操作，而Yolov4中则采用**concat（route）**操作，特征图融合后的尺寸发生了变化。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-13-42-477.png)

### Prediction

输出层的锚框机制和Yolov3相同，主要改进的是训练时的损失函数**CIOU_Loss**，以及预测框筛选的nms变为**DIOU_nms**



目标检测任务的损失函数一般由Classificition Loss（分类损失函数）和Bounding Box Regeression Loss（回归损失函数）两部分构成。

Bounding Box Regeression的Loss近些年的发展过程是：

Smooth L1 Loss

IoU Loss（2016）

GIoU Loss（2019）

DIoU Loss（2020）

CIoU Loss（2020）

#### CIOU_Loss



##### IOU_Loss

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-14-34-147.png)

可以看到IOU的loss其实很简单，主要是**交集/并集**，但其实也存在两个问题。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-16-46-272.png)

问题1：即状态1的情况，当**预测框和目标框不相交时，IOU=0，无法反应两个框距离的远近**，此时损失函数不可导，IOU_Loss无法优化两个框不相交的情况。

问题2：即状态2和状态3的情况，当两个预测框大小相同，两个**IOU也相同，IOU_Loss无法区分两者相交情况的不同。**



GIOU_Loss

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-17-36-178.png)

可以看到右图GIOU_Loss中，增加了**相交尺度的衡量方式**，缓解了单纯IOU_Loss时的尴尬。

但为什么仅仅说缓解呢？因为还存在一种不足：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-18-23-212.png)

问题：状态1、2、3都是预测框在目标框内部且预测框大小一致的情况，这时预测框和目标框的差集都是相同的，因此这三种状态的GIOU值也都是相同的，这时GIOU退化成了IOU，无法区分相对位置关系。

##### DIOU_Loss

好的目标框回归函数应该考虑三个重要几何因素：**重叠面积、中心点距离，长宽比。**

针对IOU和GIOU存在的问题，作者从两个方面进行考虑

**一：如何最小化预测框和目标框之间的归一化距离？**

**二：如何在预测框和目标框重叠时，回归的更准确？**

针对第一个问题，提出了DIOU_Loss（Distance_IOU_Loss）

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-19-59-320.png)

DIOU_Loss考虑了**重叠面积**和**中心点距离**，当目标框包裹预测框的时候，直接度量2个框的距离，因此DIOU_Loss收敛的更快。

但就像前面好的目标框回归函数所说的，没有考虑到长宽比。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-20-43-483.png)

比如上面三种情况，目标框包裹预测框，本来DIOU_Loss可以起作用。

但预测框的中心点的位置都是一样的，因此按照DIOU_Loss的计算公式，三者的值都是相同的。

##### CIOU_Loss

CIOU_Loss和DIOU_Loss前面的公式都是一样的，不过在此基础上还增加了一个影响因子，将预测框和目标框的长宽比都考虑了进去。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-52-58-544.png)

这样CIOU_Loss就将目标框回归函数应该考虑三个重要几何因素：重叠面积、中心点距离，长宽比全都考虑进去了。

再来综合的看下各个Loss函数的不同点：

**IOU_Loss：**主要考虑检测框和目标框重叠面积。

**GIOU_Loss：**在IOU的基础上，解决边界框不重合时的问题。

**DIOU_Loss：**在IOU和GIOU的基础上，考虑边界框中心点距离的信息。

**CIOU_Loss：**在DIOU的基础上，考虑边界框宽高比的尺度信息。

Yolov4中采用了**CIOU_Loss**的回归方式，使得预测框回归的**速度和精度**更高一些。



#### DIOU_nms

Nms主要用于预测框的筛选，常用的目标检测算法中，一般采用普通的nms的方式，Yolov4则借鉴上面D/CIOU loss的论文：[https://arxiv.org/pdf/1911.08287.pdf](https://link.zhihu.com/?target=https%3A//arxiv.org/pdf/1911.08287.pdf)将其中计算IOU的部分替换成DIOU的方式：

再来看下实际的案例

 ![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-53-59-691.png) 

在上图重叠的摩托车检测中，中间的摩托车因为考虑边界框中心点的位置信息，也可以回归出来。因此在重叠目标的检测中，**DIOU_nms**的效果优于**传统的nms**。

**注意：有读者会有疑问，这里为什么不用CIOU_nms，而用DIOU_nms?**

**答：**因为前面讲到的CIOU_loss，是在DIOU_loss的基础上，添加的影响因子，包含groundtruth标注框的信息，在训练时用于回归。但在测试过程中，并没有groundtruth的信息，不用考虑影响因子，因此直接用DIOU_nms即可。



**总体来说，**YOLOv4的论文称的上良心之作，将近几年关于深度学习领域最新研究的改进s移植到Yolov4中做验证测试，将Yolov3的精度提高了不少。虽然没有全新的创新，但很多改进之处都值得借鉴，借用Yolov4作者的总结。

Yolov4 主要带来了 3 点新贡献：

（1）提出了一种高效而强大的目标检测模型，使用 1080Ti 或 2080Ti 就能训练出超快、准确的目标检测器。

（2）在检测器训练过程中，验证了最先进的一些研究成果对目标检测器的影响。

（3）改进了 SOTA 方法，使其更有效、更适合单 GPU 训练。



## 优化策略

有关训练Backbone时采用的优化策略就不讲了有兴趣自己看下论文的`4.2`章节，这里直接讲下训练检测器时作者采用的一些方法。在论文`4.3`章节，作者也罗列了一堆方法，并做了部分消融实验。这里我只介绍确实在代码中有使用到的一些方法。

![quicker_ec7b98a6-e801-41ca-abaa-593108f91593.png](https://s2.loli.net/2022/05/09/NxXor2OA1mbRiWT.png)



# V5

## 网络结构

Yolov5s网络最小，速度最快，AP精度也最低。但如果检测的以大目标为主，追求速度，倒也是个不错的选择。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_10-59-23-477.png)

大家可能对**Yolov3**比较熟悉，因此大白列举它和Yolov3的一些主要的不同点，并和Yolov4进行比较。

**（1）输入端：**Mosaic数据增强、自适应锚框计算、自适应图片缩放
**（2）Backbone：**Focus结构，CSP结构
**（3）Neck：**FPN+PAN结构
**（4）Prediction：**GIOU_Loss





![quicker_d52e935b-94ae-4c77-b7b2-d2bd508eb31f.png](https://s2.loli.net/2022/05/09/kCZgA5jshbKy7lv.png)



## 改进

### 输入端

#### Mosaic数据增强

**Mosaic数据增强**则采用了4张图片，**随机缩放、随机裁剪、随机排布**的方式进行拼接。

####  自适应锚框计算

在Yolo算法中，针对不同的数据集，都**会有初始设定长宽的锚框。在网络训练中，网络在初始锚框的基础上输出预测框，进而和真实框groundtruth进行比对，计算两者差距，再反向更新**，迭代网络参数。

因此初始锚框也是比较重要的一部分，比如Yolov5在Coco数据集上初始设定的锚框：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-03-19-552.png)

在Yolov3、Yolov4中，训练不同的数据集时，计算初始锚框的值是通过单独的程序运行的。

但Yolov5中将此功能嵌入到代码中，每次训练时，自适应的计算不同训练集中的最佳锚框值。

当然，如果觉得计算的锚框效果不是很好，也可以在代码中将自动计算锚框功能关闭。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-05-20-513.png)

#### 自适应图片缩放

在常用的目标检测算法中，不同的图片长宽都不相同，因此常用的方式是将原始图片统一缩放到一个标准尺寸，再送入检测网络中。

比如Yolo算法中常用416\*416，608\*608等尺寸，比如对下面800*600的图像进行缩放。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-06-35-430.png)

作者认为，在项目实际使用时，很多图片的长宽比不同，因此缩放填充后，两端的黑边大小都不同，而如果填充的比较多，则存在信息冗余，影响推理速度。因此在Yolov5的代码中datasets.py的letterbox函数中进行了修改，对原始图像**自适应的添加最少的黑边**。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-06-44-568.png)

**图像高度上两端的黑边变少了，在推理时，计算量也会减少，即目标检测速度会得到提升。**

这种方式在之前github上Yolov3中也进行了讨论：

[https://github.com/ultralytics/yolov3/issues/232](https://link.zhihu.com/?target=https%3A//wx.qq.com/cgi-bin/mmwebwx-bin/webwxcheckurl%3Frequrl%3Dhttps%3A%2F%2Fgithub.com%2Fultralytics%2Fyolov3%2Fissues%2F232%26skey%3D%40crypt_96d23a7c_7a713cdc64109256773c39e67ce4a665%26deviceid%3De850832231813449%26pass_ticket%3DTgSQoHNgevOIg9%252B8R3aPNK%252F5sw6ZIUuR2A96p1sbiAGBktXTseCh8r9U9jZAQojj%26opcode%3D2%26scene%3D1%26username%3D%408bbd87b4deb686cd79c1471b85752510)在讨论中，通过这种简单的改进，推理速度得到了37%的提升，可以说效果很明显。

但是有的同学可能会有**大大的问号？？**如何进行计算的呢？大白按照Yolov5中的思路详细的讲解一下，在**datasets.py的letterbox函数中**也有详细的代码。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-13-39-077.png)



此外，需要注意的是：

- 这里大白填充的是黑色，即**（0，0，0）**，而Yolov5中填充的是灰色，即**（114,114,114）**，都是一样的效果。

- **训练时没有采用缩减黑边的方式，还是采用传统填充的方式**，即缩放到416*416大小。只是在测试，使用**模型推理时，才采用缩减黑边的方式，提高目标检测，推理的速度**。

- 为什么np.mod函数的后面用32？因为Yolov5的网络经过5次下采样，而2的5次方，等于32。所以至少要去掉32的倍数，再进行取余。

### Backbone

#### Focus结构

具体操作为把一张feature map每隔一个像素拿到一个值，类似于邻近下采样，这样我们就拿到了4张feature map



在减少特征信息损失的情况下，**减少特征图尺寸的大小提高计算力**。通道多少对计算量影响不大



切片顺序不同 focus列优先 passthrough行优先

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-15-15-904.png)



Focus结构，在Yolov3&Yolov4中并没有这个结构，其中比较关键是切片操作。比如右图的切片示意图，4\*4\*3的图像切片后变成2\*2\*12的特征图。以Yolov5s的结构为例，原始608\*608\*3的图像输入Focus结构，采用切片操作，先变成304\*304\*12的特征图，再经过一次32个卷积核的卷积操作，最终变成304\*304\*32的特征图。



**需要注意的是**：Yolov5s的Focus结构最后使用了32个卷积核，而其他三种结构，使用的数量有所增加，先注意下，后面会讲解到四种结构的不同点。

#### CSP结构

Yolov4网络结构中，借鉴了CSPNet的设计思路，在主干网络中设计了CSP结构。Yolov5与Yolov4不同点在于，Yolov4中只有主干网络使用了CSP结构。

而Yolov5中设计了两种CSP结构，以**Yolov5s网络**为例，**CSP1_X结构**应用于**Backbone主干网络**，另一种**CSP2_X**结构则应用于**Neck**中。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-24/2022-11-24_19-38-14-562.png)

### Neck

#### FPN+PAN

Yolov5现在的Neck和Yolov4中一样，**都采用FPN+PAN的结构**，但在Yolov5刚出来时，只使用了FPN结构，后面才增加了PAN结构，此外网络中其他部分也进行了调整。



但如上面CSPNet结构中讲到，Yolov5和Yolov4的不同点在于，Yolov4的Neck结构中，采用的都是普通的卷积操作。而Yolov5的Neck结构中，采用借鉴CSPnet设计的CSP2结构，加强网络特征融合的能力。




![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-19-28-972.png)

### Prediction

##### CIOU_Loss—Bounding box损失函数

Yolov5中采用其中的CIOU_Loss做Bounding box的损失函数。

##### DIOU_nms

在目标检测的后处理过程中，针对很多目标框的筛选，通常需要nms操作。

因为CIOU_Loss中包含影响因子v，涉及groudtruth的信息，而测试推理时，是没有groundtruth的。所以Yolov4在DIOU_Loss的基础上采用DIOU_nms的方式，而Yolov5中采用加权nms的方式。

可以看出，采用DIOU_nms，下方中间箭头的黄色部分，原本被遮挡的摩托车也可以检出。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-31-35-919.png)



## Yolov5四种网络结构的不同点

Yolov5代码中的四种网络，和之前的Yolov3，Yolov4中的**cfg文件**不同，都是以**yaml**的形式来呈现。

而且四个文件的内容基本上都是一样的，只有最上方的**depth_multiple**和**width_multiple**两个参数不同，很多同学看的**一脸懵逼**，不知道只通过两个参数是如何控制四种结构的？

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-33-11-309.png)

### Yolov5四种网络的深度

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_11-36-19-982.png)



在上图中，大白画了两种CSP结构，CSP1和CSP2，其中CSP1结构主要应用于Backbone中，CSP2结构主要应用于Neck中。

**需要注意的是，四种网络结构中每个CSP结构的深度都是不同的。**

- 以yolov5s为例，第一个CSP1中，使用了1个残差组件，因此是**CSP1_1**。而在Yolov5m中，则增加了网络的深度，在第一个CSP1中，使用了2个残差组件，因此是**CSP1_2**。而Yolov5l中，同样的位置，则使用了**3个残差组件**，Yolov5x中，使用了**4个残差组件**。其余的第二个CSP1和第三个CSP1也是同样的原理。

- 在第二种CSP2结构中也是同样的方式，以第一个CSP2结构为例，Yolov5s组件中使用了2×X=2×1=2个卷积，因为Ｘ=1，所以使用了1组卷积，因此是**CSP2_1**。而Yolov5m中使用了2**组**，Yolov5l中使用了3**组**，Yolov5x中使用了4**组。**其他的四个CSP2结构，也是同理。

Yolov5中，网络的不断加深，也在不断**增加网络特征提取**和**特征融合**的能力。



控制四种网络结构的核心代码是**yolo.py**中下面的代码，存在两个变量，**n和gd**。

我们再将**n和gd**带入计算，看每种网络的变化结果。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_14-41-09-595.png)
  ![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_15-24-36-818.png)

### Yolov5四种网络的宽度

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_14-41-42-893.png)如上图表格中所示，四种yolov5结构在不同阶段的卷积核的数量都是不一样的，因此也直接影响卷积后特征图的第三维度，即**厚度**，大白这里表示为网络的**宽度**。

- 以Yolov5s结构为例，第一个Focus结构中，最后卷积操作时，卷积核的数量是32个，因此经过**Focus结构**，特征图的大小变成**304\*304\*32**。而yolov5m的**Focus结构**中的卷积操作使用了48个卷积核，因此**Focus结构**后的特征图变成**304\*304\*48**。yolov5l，yolov5x也是同样的原理。

- 第二个卷积操作时，yolov5s使用了64个卷积核，因此得到的特征图是**152\*152\*64**。而yolov5m使用96个特征图，因此得到的特征图是**152\*152\*96**。yolov5l，yolov5x也是同理。

-  后面三个卷积下采样操作也是同样的原理，这样大白不过多讲解。

四种不同结构的卷积核的数量不同，这也直接影响网络中，比如**CSP1，CSP2等结构**，以及各个普通卷积，卷积操作时的卷积核数量也同步在调整，影响整体网络的计算量。当然卷积核的数量越多，特征图的厚度，即**宽度越宽**，网络提取特征的**学习能力也越强**。



在yolov5的代码中，控制宽度的核心代码是**yolo.py**文件里面的这一行：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_14-44-36-512.png)



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-24/2022-10-24_15-25-18-348.png)















## 损失函数















# code



















和YOLOv4对比，其实YOLOv5在Backbone部分没太大变化。但是YOLOv5在v6.0版本后相比之前版本有一个很小的改动，把网络的第一层（原来是Focus模块）换成了一个6x6大小的卷积层。两者在理论上其实等价的，但是对于现有的一些GPU设备（以及相应的优化算法）使用6x6大小的卷积层比使用Focus模块更加高效。详情可以参考这个issue #4825。下图是原来的Focus模块(和之前Swin Transformer中的Patch Merging类似)，**将每个2x2的相邻像素划分为一个patch，然后将每个patch中相同位置（同一颜色）像素给拼在一起就得到了4个feature map，然后在接上一个3x3大小的卷积层。这和直接使用一个6x6大小的卷积层等效。**

![quicker_1d6266e5-514d-4ce5-b18b-609bcb1bda92.png](https://s2.loli.net/2022/05/09/8I3kFMYjO2RhL6e.png)



在Neck部分的变化还是相对较大的，首先是将SPP换成成了SPPF（Glenn Jocher自己设计的），这个改动我个人觉得还是很有意思的，**两者的作用是一样的，但后者效率更高**。`SPPF`比`SPP`计算速度快了不止两倍。SPP结构如下图所示，是将输入并行通过多个不同大小的MaxPool，然后做进一步融合，能在一定程度上解决目标多尺度问题。



`SPPF`结构是将输入串行通过多个`5x5`大小的`MaxPool`层，这里需要注意的是**串行两个`5x5`大小的`MaxPool`层是和一个`9x9`大小的`MaxPool`层计算结果是一样的，串行三个`5x5`大小的`MaxPool`层是和一个`13x13`大小的`MaxPool`层计算结果是一样的。**

![quicker_20886403-6055-4761-a782-afdf76d1476b.png](https://s2.loli.net/2022/05/09/ugM3fX7L6myd9ZN.png)

![quicker_99ea837a-ba94-4c1c-86e1-39582eab5c6b.png](https://s2.loli.net/2022/05/09/SiqLO4nXUDtrak9.png)



在**Neck**部分另外一个不同点就是`New CSP-PAN`了，在YOLOv4中，**Neck**的`PAN`结构是没有引入`CSP`结构的，但在YOLOv5中作者在`PAN`结构中加入了`CSP`。每个`C3`模块里都含有`CSP`结构。在**Head**部分，YOLOv3, v4, v5都是一样的



## 数据增强

这里简单罗列部分方法：

- **Mosaic**，将四张图片拼成一张图片，讲过很多次了
- **Random affine(Rotation, Scale, Translation and Shear)**，随机进行仿射变换，但根据配置文件里的超参数发现只使用了`Scale`和`Translation`即缩放和平移。
- **MixUp**，就是将两张图片按照一定的透明度融合在一起，具体有没有用不太清楚，毕竟没有论文，也没有消融实验。代码中只有较大的模型才使用到了`MixUp`，而且每次只有10%的概率会使用到。
- **Albumentations**，主要是做些滤波、直方图均衡化以及改变图片质量等等，我看代码里写的只有安装了`albumentations`包才会启用，但在项目的`requirements.txt`文件中`albumentations`包是被注释掉了的，所以默认不启用。
- **Augment HSV(Hue, Saturation, Value)**，随机调整色度，饱和度以及明度。
- **Random horizontal flip**，随机水平翻转







![quicker_696f7eaa-9fc9-4263-ae6a-59a9b40f18c9.png](https://s2.loli.net/2022/05/09/UhNwK1bSXdei8RA.png)

# X

## 网络结构



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_16-57-39-950.png)



**输入端：**Strong augmentation数据增强

**BackBone主干网络：**主干网络没有什么变化，还是Darknet53。

**Neck：**没有什么变化，Yolov3 baseline的Neck层还是FPN结构。

**Prediction：**Decoupled Head、End-to-End YOLO、Anchor-free、Multi positives。

## 改进



### 输入端

在网络的输入端，Yolox主要采用了**Mosaic、Mixup两种数据增强方法。**

而采用了这两种数据增强，直接将Yolov3 baseline，提升了2.4个百分点。

#### Mosaic数据增强

Mosaic增强的方式，是U版YOLOv3引入的一种非常有效的增强策略。而且在Yolov4、Yolov5算法中，也得到了广泛的应用。通过**随机缩放**、**随机裁剪**、**随机排布**的方式进行**拼接**，对于**小目标**的检测效果提升，还是很不错的。

#### MixUp数据增强

**调整透明度两张图像叠加在一起。**

主要来源于2017年，顶会ICLR的一篇论文《mixup: Beyond Empirical Risk Minimization》。当时主要应用在图像分类任务中，可以在几乎无额外计算开销的情况下，稳定提升1个百分点的分类精度。而在Yolox中，则也应用到目标检测中，代码在yolox/datasets/mosaicdetection.py这个文件中。  

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-01-42-515.png)

其实方式很简单，比如我们在做人脸检测的任务。先读取一张图片，图像两侧填充，缩放到640\*640大小，即Image_1，人脸检测框为红色框。再随机选取一张图片，图像上下填充，也缩放到640*640大小，即Image_2，人脸检测框为蓝色框。然后设置一个融合系数，比如上图中，设置为0.5，将Image_1和Image_2，加权融合，最终得到右面的Image。从右图可以看出，人脸的红色框和蓝色框是叠加存在的。



我们知道，在Mosaic和Mixup的基础上，Yolov3 baseline增加了2.4个百分点。不过有两点需要注意：

（1）在训练的**最后15个epoch，这两个数据增强会被关闭掉**。而在此之前，Mosaic和Mixup数据增强，都是打开的，这个细节需要注意。

（2）由于采取了更强的数据增强方式，作者在研究中发现，ImageNet预训练将毫无意义，因此，**所有的模型，均是从头开始训练的**。



###  Backbone

Yolox-Darknet53的Backbone主干网络，和原本的Yolov3 baseline的主干网络都是一样的。

### Neck

在Neck结构中，Yolox-Darknet53和Yolov3 baseline的Neck结构，也是一样的，都是采用**FPN的结构**进行融合。

而在Yolov4、Yolov5、甚至后面讲到的Yolox-s、l等版本中，都是采用**FPN+PAN的形式**，这里需要注意。

### Prediction

在输出层中，主要从四个方面进行讲解：**Decoupled Head**、**Anchor Free**、**标签分配、Loss计算。**

#### Decoupled	 Head

在很多一阶段网络中都有类似应用，比如**RetinaNet、FCOS等**。

而在Yolox中，作者增加了三个Decoupled Head，俗称“解耦头”。大白这里从两个方面对Decoupled Head进行讲解：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-06-20-655.png)


从上图右面的Prediction中，我们可以看到，有三个Decoupled Head分支。

**① 为什么使用Decoupled Head？**

在了解原理前，我们先了解下改进的原因。为什么将原本的**Yolo head**，修改为**Decoupled Head**呢？

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-08-07-827.png)



作者想继续改进，比如输出端改进为End-to-end的方式（即无NMS的形式）。在实验中还发现，不单单是精度上的提高。替换为Decoupled Head后，网络的收敛速度也加快了。



**但是需要注意的是：将检测头解耦，会增加运算的复杂度。**因此作者经过速度和性能上的权衡，最终使用 1个1x1 的卷积先进行降维，并在后面两个分支里，各使用了 2个3x3 卷积，最终调整到仅仅增加一点点的网络参数。而且这里解耦后，还有一个更深层次的重要性：**Yolox的网络架构，可以和很多算法任务，进行一体化结合。**

比如：

（1）YOLOX + Yolact/CondInst/SOLO ，**实现端侧的实例分割。**

（2）YOLOX + 34 层输出，实现端侧人体的 **17 个关键点检测。**



**Decoupled Head的细节？**

我们将Yolox-Darknet53中，Decoupled Head①提取出来，经过前面的Neck层，这里Decouple Head①输入的长宽为20*20。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-14-04-120.png)

从图上可以看出，Concat前总共有三个分支：

（1）cls_output：主要对目标框的类别，预测分数。因为COCO数据集总共有80个类别，且主要是N个二分类判断，因此经过Sigmoid激活函数处理后，变为20*20*80大小。

（2）obj_output：主要判断目标框是前景还是背景，因此经过Sigmoid处理好，变为20*20*1大小。

（3）reg_output：主要对目标框的坐标信息（x，y，w，h）进行预测，因此大小为20*20*4。

最后三个output，经过Concat融合到一起，得到20\*20\*85的特征信息。

当然，这只是Decoupled Head①的信息，再对Decoupled Head②和③进行处理。



![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-16-07-450.png)

Decoupled Head②输出特征信息，并进行Concate，得到40\*40\*85特征信息。

Decoupled Head③输出特征信息，并进行Concate，得到80\*80\*85特征信息。

再对①②③三个信息，进行Reshape操作，并进行总体的Concat，得到8400\*85的预测信息。

并经过一次Transpose，变为85*8400大小的二维向量信息。

这里的8400，指的是预测框的数量，而85是每个预测框的信息（reg，obj，cls）。

有了预测框的信息，下面我们再了解，如何将这些预测框和标注的框，即groundtruth进行关联，从而计算Loss函数，更新网络参数呢？



#### Anchor-free

在Yolov3、Yolov4、Yolov5中，通常都是采用Anchor Based的方式，来提取目标框，进而和标注的groundtruth进行比对，判断两者的差距。

**① Anchor Based方式**

比如输入图像，经过Backbone、Neck层，最终将特征信息，传送到输出的Feature Map中。

这时，就要设置一些Anchor规则，将预测框和标注框进行关联。从而在训练中，计算两者的差距，即损失函数，再更新网络参数。比如在下图的，最后的三个Feature Map上，基于每个单元格，都有三个不同尺寸大小的锚框。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-19-23-910.png)

当输入为416*416时，网络最后的三个特征图大小为13\*13，26\*26，52\*52。我们可以看到，黄色框为小狗的Groundtruth，即标注框。而蓝色的框，为小狗中心点所在的单元格，所对应的锚框，每个单元格都有3个蓝框。当采用COCO数据集，即有80个类别时。基于每个锚框，都有x、y、w、h、obj（前景背景）、class（80个类别），共85个参数。

因此会产生3\*(13\*13+26\*26+52\*52）*85=904995个预测结果。

如果将输入从416\*416，变为640\*640，最后的三个特征图大小为20\*20,40\*40,80\*80。

则会产生3\*（20\*20+40\*40+80\*80）*85=2142000个预测结果。

**② Anchor Free方式**

而Yolox-Darknet53中，则采用Anchor Free的方式。我们从两个方面，来对Anchor Free进行了解。

a.输出的参数量

当输入为640\*640时，最终输出得到的特征向量是85\*8400。通过计算，8400*85=714000个预测结果，**比基于Anchor Based的方式，少了2/3的参数量**。

b.Anchor框信息

在前面Anchor Based中，我们知道，每个Feature map的单元格，都有3个大小不一的锚框。

那么Yolox-Darknet53就没有吗？其实并不然，这里只是巧**妙的，将前面Backbone中，下采样的大小信息引入进来。**

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-41-54-908.png)

比如上图中，最上面的分支，下采样了5次，2的5次方为32。并且Decoupled Head①的输出，为20\*20\*85大小。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-25/2022-10-25_17-46-38-244.png)

最后8400个预测框中，其中有400个框，所对应锚框的大小，为32\*32。同样的原理，中间的分支，最后有1600个预测框，所对应锚框的大小，为16\*16。最下面的分支，最后有6400个预测框，所对应锚框的大小，为8\*8。当有了8400个预测框的信息，每张图片也有标注的目标框的信息。



这时的锚框，就相当于桥梁。这时需要做的，就是将8400个锚框，和图片上所有的目标框进行关联，挑选出正样本锚框。而相应的，正样本锚框所对应的位置，就可以将正样本预测框，挑选出来。这里采用的关联方式，就是标签分配。

#### 标签分配

当有了8400个Anchor锚框后，这里的每一个锚框，都对应85*8400特征向量中的预测框信息。

这些预测框只有**少部分是正样本，绝大多数是负样本。**

**那么到底哪些是正样本呢？**

这里需要利用锚框和实际目标框的关系，挑选出**一部分适合的正样本锚框。**

比如第3、10、15个锚框是正样本锚框，则对应到网络输出的8400个预测框中，第3、10、15个预测框，就是相应的**正样本预测框。**训练过程中，在锚框的基础上，不断的预测，然后不断的迭代，从而更新网络参数，让网络预测的越来越准。

那么在Yolox中，是如何挑选正样本锚框的呢？

**① 初步筛选**

初步筛选的方式主要有两种：**根据中心点来判断**、**根据目标框来判断**；

这部分的代码，在models/yolo_head.py的get_in_boxes_info函数中。

**a. 根据中心点来判断：**

**规则：寻找anchor_box中心点，落在groundtruth_boxes矩形范围的所有anchors。**

比如在get_in_boxes_info的代码中，通过groundtruth的[x_center,y_center，w，h]，计算出每张图片的每个groundtruth的左上角、右下角坐标。





[深入浅出Yolo系列之Yolox核心基础完整讲解 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/397993315)