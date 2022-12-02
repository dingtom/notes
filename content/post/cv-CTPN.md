---
    # 文章标题
    title: "cv-CTPN"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
    
--- 

# CTPN算法

文本通常都是从左往右写的（水平），并且字之间的宽度都大致相同
固定宽度，来检测文本高度即可，但是如何应对变长序列呢？
本质上还是RPN方法(可参考faster--rcnn),可将检测到的框拼在一起！



![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-12-30-819.png)

- CTPN第一步和通用的目标检测网络一样，先用一个backbone，这里用的是`VGG16来提取空间特征`，取VGG的conv5层的输出，输出维度为B × W × H × C（批次batchsize×宽×高×通道数）。这里要注意因为是第五层卷积输出，所以下采样倍数为16，也就是输出的feature map中的每个特征点对应原图16个像素。

- 接下来我们会对feature map做一个编码，`在feature map上使用3×3的滑窗来提取空间特征（也就是卷积）`，经过滑窗后得到的feature map大小依旧是B × W × H × C，但这里的每一个像素点都融合了周围3 × 3的信息（在论文中作者使用caffe中的im2col实现的滑窗操作，将B × W × H × C大小的feature map转换为B × W × H × 9C，本文遵从tf版本）

- 接着将f`eature map reshape成(NH) × W × C输入双向LSTM提取每一行的序列特`征。最后双向LSTM输出(NH) × W × 256，然后重新reshape回N × 256 × H × W

- 将`输出经过一个卷积层（图中的FC），变成N × H × W × 512`

- N × H × W × 512 最后`会经过一个类似RPN的网络，分成三个预测支`路：如上图所示，

  其中一个分支输出N × H × W × 2k，这里的k指的是每个像素对应k个anchor，这里的2K指的是对某一个anchor的预测$v=[v_y,v_h]$(`y坐标和到原点高度，宽度是固定的`)；

  第二个分支输出N × H × W × 2k，这里的2K指的是2K个`前景背景得分`，记做$s=[text,non−text]$。

  最后一个分支输出N × H × W × k，这里是K个`side-refinement，调整边界位置`。

- 经过上面步骤，可以得到密密麻麻的`text proposal，这里使用nms来过滤掉多余的文本框`。

- 假如理想的话（文本水平），会将上述得到的一个文本小框使用文本线构造方法合成一个完整文本行，如果还有些倾斜，会做一个矫正的操作。

以上就是CTPN的架构，看不懂没关系，接下来我会可视化每个过程帮你了解。



torch.size([1,3,900,1600])            3通道  VGG
torch.size([1,512,56,100])           RPN(卷积）
torch.size([1,512,56,100])           transpose 以w为序列方向，h为每个样本
torch.size([1,56,100,512])            reshape
torch.size([56,100,512])               Bi-LSTM
torch.size([1,56,100,256])            channel first
torch.size([1,256,56,100])            fc
torch.size([1,512,56,100])            10个anchor 2 个位置
torch.size([1,20,56,100])              56个特征点，每个产生10个anchor

torch.size([1,5600,2])  

# 特殊的anchor

文本长度的剧烈变化是文本检测的挑战之一，作者认为文本在长度的变化比高度的变化剧烈得多，文本边界开始与结束的地方难以和Faster-rcnn一样去用anchor匹配回归，所以作者提出一种`vertical anchor`的方法，即`我们只去预测文本的竖直方向上的位置，不去预测水平方向的位置，水平位置的确定只需要我们检测一个一个小的固定宽度的文本段，将他们对应的高度预测准确，最后再将他们连接在一起，就得到了我们的文本行`，如下图所示：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-21-56-057.png)

所以，我们对于每个像素点设置的anchor的宽度都是固定的，为16像素（第二部分提到，feature map上的每一点对应原图16个像素），这样就可以覆盖到原图的所有位置，而高度则是从11到273变化，这里我们每个像素点取k=10个anchor。



因为宽度是固定的，所以只需要anchor的中心的y坐标以及anchor的高度就可以确定一个anchor，其中带星号的为ground-truth，没有带星号的则是预测值，带a的则是对应anchor的值，具体那些回归的原理和之前讲解的检测算法一致就不多赘述。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-23-01-986.png)



# 双向LSTM

VGG16提取的是空间特征，而LSTM学习的就是序列特征，而这里使用的是双向LSTM，更好的避免RNN当中的遗忘问题，更完整地提取出序列特征。



# RPN层

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-25-00-621.png)

CTPN的RPN层和Faster R-CNN很像，第一个分支输出的是我们anchor的位置，也就是我们上面讲解anchor提到的两个参数($v_c,v_h$)，因为每个特征点配置10个anchor，所以这个分支的输出20个channel。

第二个分支则是输出前景背景的得分情况(text/non-text scores)，通过softmax计算得分，所以这里也是输出20个channel。

第三个分支则是输出最后`水平精修side-refinement的比例$o$，这是由于我们每个anchor的宽是一定的，所以有时候会导致水平方向有一点不准，所以这时候就需要校准一下我们的框`（在我自己的实验中这个帮助不大），精修的公式如下：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-27-39-271.png)

和anchor一样，带星号的是ground-truth，$X_{side}$表示文本框的左边界或者右边界，$C^x_a$表示anchor中心的横坐标，$Wa$是anchor固定的宽度16像素，`所以我们可以把这个$o$理解为一个缩放的比例，来对最后的结果做一个准确的拉伸`，下面这张图中红色的就是使用了side-refinement，黄色的则是没有使用的结果。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-28-40-828.png)

这里要注意，作者选择`与真值IoU大于0.7的anchor作为正样本，与真值IoU最大的那个anchor也定义为正样本`，这样做有助于检测出小文本，避免小样本因得分低而被判断为负样本；与真值IOU小于0.5的anchor则定义为负样本，经过RPN之后我们只保留那些正样本。

# nms

经过RPN，就会输出密密麻麻的检测框，这时候使用一个nms来过滤掉多的框。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-30-27-138.png)

# 文本线构造方法

经过上一部分我们已经得到了一系列的小的文本框，接下来我们就是用文本线构造方法将他们连起来。论文中说的不太清楚，

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-31-18-842.png)

有2个text proposal，即蓝色和红色2组Anchor，`CTPN采用如下算法构造文本线：` 

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-22/2022-11-22_11-07-36-767.png)



这只是一个概述，接下来展开叙述。假设每个Anchor index是绿色数字，每个Anchor Softmax score是黑色数字：

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_17-52-42-481.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-22/2022-11-22_11-10-19-137.png)

# 文本框矫正

很多网上的文章忽略了文本框矫正这一点，加入文本并不是理想的，也就是存在倾斜，文本框是需要矫正的，矫正的步骤如下：

（1）上一步我们得到了一些判断为同一个文本序列的anchor，我们首先要求一条直线L使得所有中心到这条直线的距离最小，也就是最小二乘法线性回归。

（2）这时候我们已经得到了每一段文字的基本走向，与此同时我们可以根据每一段里的text proposal得到这段文字的最大区域，我们可视化一下：

3）现在有了最大范围和拟合出的文本的直线，我们要生成最终符合文字倾斜角度和区域的box，CTPN作者使用一种巧妙方法来生成text proposal：首先求每段text proposal的平均高度，并以此和拟合出的文字中的直线做上下平移，来生成候选区域。这个时候我们生成的box的上下边都是我们刚才的拟合出的直线的平行线，左右边则是由上下边生成的垂线生成的平行四边形。

（4）现在我们生成了一个平行四边形，但是我们传入识别部分肯定是一个矩形，所以作者根据框上下边斜度来对左右两条边做出斜度变化的补偿方法，来确定最终的矩形框。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_18-04-39-807.png)



这里要注意，CTPN在实际当中对一些倾斜样本的鲁棒性还是略显不足的。

# 损失函数

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-11/2022-11-11_18-04-07-152.png)

CTPN的损失函数如上图，一眼看上去又是一个Muti-task的loss，分为三个部分：

- LS：每个anchor是否是正样本的classification loss
- Lv:每个anchor的中心y坐标和高度loss
- L0:文本区域两侧精修的x损失

和Faster-RCNN一样，以上的loss都采用smooth L1 loss，$λ$是权重系数，$N$则是归一化系数，这部分很清晰没什么好讲的。







# CRNN

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-12/2022-11-12_14-51-30-550.png)

1:torch.Size([1,1,32,258])  （c,h,w)灰度图单通道
2:torch.size([1,512,1,65])     
3:torch.size([65,1,512])        transpose  65表序列长度，512表序列维度
4:torch.size([65,1,5835])      5835表词库