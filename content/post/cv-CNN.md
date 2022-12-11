---
    # 文章标题
    title: "cv-CNN"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00

---

![](https://upload-images.jianshu.io/upload_images/18339009-4c00b0451280e2f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-3adce96cd54eb3f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 卷积层

`卷积提取底层特征减少神经网络中参数个数`



- `局部连接`。⽐起全连接，局部连接会⼤⼤`减少⽹络的参数`。在⼆维图像中，局部像素的关联性很强，
  设计局部连接保证了卷积⽹络对图像局部特征的强响应能⼒。+
- `下采样`。下采样能逐渐降低图像分辨率，实现了数据的降维，并使浅层的局部特征组合成为深层的特
  征。下采样还能使计算资源耗费变少，加速模型训练，也能有效控制过拟合。
- `权值共享`。参数共享也能`减少整体参数量`，增强了⽹络训练的效率。⼀个卷积核的参数权重被整张图
  ⽚共享，不会因为图像内位置的不同⽽改变卷积核内的参数权重。

![](https://upload-images.jianshu.io/upload_images/18339009-9ff2bf3400efde22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![左侧小矩阵的尺寸为过滤器的尺寸，而右侧单位矩阵的深度为过滤器的深度
](https://upload-images.jianshu.io/upload_images/18339009-83bf8425d394ccdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-bdf823c5a2c28959.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 为了避免尺寸的变化可以在当前层的矩阵的边界加入全０填充（zero-padding）．否则中间的像素会多次进入卷积野而边上的进入次数少
- 还可以通过设置过滤器移动步长调整结果矩阵的大小



![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_14-52-32-636.png)



## 池化层

`下采样。降维、扩大感知野、减小计算量`
`实现非线性`
`实现不变性，（平移不变性、旋转不变性和尺度不变性）`

![池化函数使用某一位置的相邻输出的总体统计特征来代替网络在该位置的输出。](https://upload-images.jianshu.io/upload_images/18339009-8b04d457c6018029.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)






## 卷积层输出矩阵大小

$n_{\text {output}}=\left[\frac{n_{\text {input}}- \text{kernel_size} +2*padding}{stride} + 1\right]$



池化层的输出大小公式也与卷积层一样



## 改进点

![](https://upload-images.jianshu.io/upload_images/18339009-acae173c2caeea35.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3D 卷积

实际上是对一个 3D 体积执行卷积。但通常而言，我们仍在深度学习中称之为 2D 卷积。这是在 3D 体积数据上的 2D 卷积。`过滤器深度与输入层深度一样。这个 3D 过滤器仅沿两个方向移动（图像的高和宽）。`这种操作的输出是一张 2D 图像（仅有一个通道）。

3D 卷积确实存在。这是 2D 卷积的泛化。`其过滤器深度小于输入层深度（核大小<通道大小）。因此，3D 过滤器可以在所有三个方向（图像的高度、宽度、通道）上移动。`在每个位置，逐元素的乘法和加法都会提供一个数值。因为过滤器是滑过一个 3D 空间，所以输出数值也按 3D 空间排布。也就是说输出是一个 3D 数据。

3D 卷积可以描述 3D 空间中目标的空间关系。对某些应用（比如生物医学影像中的 3D 分割/重构）而言，这样的 3D 关系很重要，比如在 CT 和 MRI 中，血管之类的目标会在 3D 空间中蜿蜒曲折。

## 1×1 卷积

⾸发于NIN（Network in Network），后续也在GoogLeNet和ResNet等⽹络中使⽤。

- 跨通道交流信息
- 降维、升维
- 减少参数量
- 1×1 卷积+激活函数 增加⾮线性，提升⽹络表达能⼒。

![](https://upload-images.jianshu.io/upload_images/18339009-cc270f72d13957ae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-95f1db364ebe56bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 两个3\*3代替一个5\*5

两个3\*3卷积核和⼀个5\*5卷积核的感受野相同，但是`减少了参数量和计算量`，加快了模型训练。与此同时由于卷积核的增加，`模型的⾮线性表达能⼒⼤⼤增强`。

参数量：（3\*3+1）*2；5\*5+1

过⼤卷积核也有使⽤的空间，在GAN，图像超分辨率，图像融合等领域依然有较多的应⽤

## 　转置卷积（去卷积、棋盘效应）

`上采样生成高分辨率图像、将低维特征图映射到高维空间`

转置卷积通过训练过程学习到最优的上采样方式，来代替传统的插值上采样方法，以提升图像分割，图像融合，GAN等特定任务的性能。

转置卷积并不是卷积的反向操作，从信息论的角度看，卷积运算是不可逆的。转置卷积可以将输出的特征图尺寸恢复卷积前的特征图尺寸，但不恢复原始数值。

我们一直都可以使用直接的卷积实现转置卷积。对于下图的例子，我们在一个 2×2 的输入（周围加了 2×2 的单位步长的零填充）上应用一个 3×3 核的转置卷积。上采样输出的大小是 4×4。

## 多尺度卷积

![](https://upload-images.jianshu.io/upload_images/18339009-9386a104067200fc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 深度可分卷积

深度可分离卷积将传统的卷积分两步进行，分别是`depthwise和pointwise`。

首先按照`通道进行计算按位相乘的计算`，深度可分离卷积中的卷积核都是单通道的，输出不能改变feature map的通道数，此时通道数不变；

然后将得到将第一步的结果，使用`1*1的卷积核`进行传统的卷积运算，此时`通道数可以进行改变`。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-21-33-925.png)

减少参数量、计算量

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_15-15-14-698.png)

## 分组卷积

特征图局部链接
![传统](https://upload-images.jianshu.io/upload_images/18339009-f180d01ed3f2ddf9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
通道局部链接
![参数量减少](https://upload-images.jianshu.io/upload_images/18339009-d02525a2f6f26516.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![Alexnet](https://upload-images.jianshu.io/upload_images/18339009-ee939e0c39d7e299.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

混分组卷积

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-14-56-805.png)

SE注意力机制

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-15-32-142.png)

## 空洞卷积

1. `扩大感受野`。神经元感受野的值越大表示其能接触到的原始图像范围就越大，也意味着它可能蕴含更为全局，`语义层次更高的特征`

2. 获取多尺度上下文信息。当多个带有不同dilation rate的空洞卷积核叠加时，不同的感受野会带来多尺度信息，这对于分割任务是非常重要的。

3. `可以降低计算量`，不需要引入额外的参数，如上图空洞卷积示意图所示，实际卷积时只有带有红点的元素真正进行计算

   

图像分割领域，图像输入到CN(典型的网络比如FCN)中有两个关键

`一个是 pooling减小图像尺寸增大感受野，另一个是 upsampling扩大图像尺寸。`

`在先减小再增大尺寸的过程中，肯定有一些信息损失掉了`，那么能不能设计一种`新的操作不通过 Pooling也能有较大的感受野`看到更多的信息呢？
![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-15-58-227.png)



与正常的卷积不同的是,空洞卷积引入了一个称为 “`扩张率(dilation rate)`”的超参数(hyper-parameter)，该参数定义了卷积核处理数据时各值的间距。扩张率中文也叫空洞数(Hole Size)。

在此以3*3卷积为例,展示普通卷积和空洞卷积之间的区别

![quicker_1adcb4bc-878f-40dc-a302-c41d066d463c.png](https://s2.loli.net/2022/05/06/atHGAJnj7FqwxOl.png)

从左到右分别为a、b、c子图,三幅图是相互独立进行卷积的(区别于下面图4),大框表示输入图像(感受野默认为1),黑色的圆点表示3*3的卷积核,灰色地带表示卷积后的感受野

- a是普通的卷积过程(dilation rate = 1),卷积后的感受野为3
- b是dilation rate = 2的空洞卷积,卷积后的感受野为5
- c是dilation rate = 3的空洞卷积,卷积后的感受野为8



![quicker_28831eb3-2ba2-46dd-841d-2971bd6e417c.png](https://s2.loli.net/2022/05/06/AyL7B823DcbVWEU.png)





1个 7 x 7 的卷积层的正则等效于 3 个 3 x 3 的卷积层的叠加。而这样的设计可以大幅度的减少参数，有正则化的效果，参数少了就没那么容易发生过拟合。





## 转置卷积

`转置卷积（Transposed Convolution）`它和空洞卷积的思路正好相反，是为上采样而生，也应用于语义分割当中，而且他的计算也和空洞卷积正好相反，`先对输入的feature map间隔补0，卷积核不变，然后使用标准的卷积进行计算，得到更大尺寸的feature map`。`转置卷积不是卷积的逆运算`

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-27-07-564.png)

首先回顾下普通卷积，以stride=1，padding=0，kernel_size=3为例，假设输入特征图大小是4x4的（假设输入输出都是单通道），通过卷积后得到的特征图大小为2x2。一般使用卷积的情况中，要么特征图变小（stride > 1），要么保持不变（stride = 1），当然也可以通过四周padding让特征图变大但没有意义。




转置卷积它只能恢复到原来的大小（shape）数值与原来不同。转置卷积的运算步骤可以归为以下几步：

- 在输入特征图元素间填充s-1行、列0（其中s表示转置卷积的步距）

- 在输入特征图四周填充k-p-1行、列0（其中k表示转置卷积的kernel_size大小，p为转置卷积的padding，注意这里的padding和卷积操作中有些不同）

- 将卷积核参数上下、左右翻转

- 做正常卷积运算（填充0，步距1）

下面假设输入的特征图大小为2x2（假设输入输出都为单通道），通过转置卷积后得到4x4大小的特征图。这里使用的转置卷积核大小为k=3，stride=1，padding=0的情况（忽略偏执bias）。

首先在元素间填充s-1=0行、列0（等于0不用填充）
然后在特征图四周填充k-p-1=2行、列0
接着对卷积核参数进行上下、左右翻转
最后做正常卷积（填充0，步距1）
![quicker_8295a47a-6d1d-4c18-8ba8-b4a724e696d4.png](https://s2.loli.net/2022/05/07/6HUADX2yzZ3vkmt.png)



$$H_{out} = \frac{H_{in}+2p-k}{s}+1$$

$$H_{in} = (H_{out}-1)\times s +k-2p$$



```python
nn.ConvTranspose2d
n_channels, out_channels, kernel_size, stride, padding、
output_padding：在计算得到的输出特征图的高、宽方向各填充几行或列0（注意，这里只是在上下以及左右的一侧one side填充，并不是两侧都填充，有兴趣自己做个实验看下），默认为0不使用。
groups：当使用到组卷积时才会用到的参数，默认为1即普通卷积。
bias：是否使用偏执bias，默认为True使用。
dilation：当使用到空洞卷积（膨胀卷积）时才会使用到的参数，默认为1即普通卷积。
```

$$H_{in} = (H_{out}-1)\times s +dilation \times (k-1)-2p +out\_padding +1$$

Resnet

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-23/2022-11-23_17-16-46-897.png)



## Addition / Concatenate

Addition和Concatenate分支操作统称为shortcut，

Addition是在ResNet中提出，两个相同维度的feature map相同位置点的值直接相加，得到新的相同维度feature map，`这个操作可以融合之前的特征，增加信息的表达，`

Concatenate操作是在Inception中首次使用，被DenseNet发扬光大，和addition不同的是，它只要求两个feature map的HW相同，通道数可以不同，然后两个feature map在通道上直接拼接，得到一个更大的feature map，`它保留了一些原始的特征，增加了特征的数量，使得有效的信息流继续向后传递。`

## VGG

- 更深的网络有助于性能的提升，11层、13层、16层、19层

- 更深的网络不好训练，容易过拟合

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-17-30-944.png)

## GoogLeNet

- 引入Inception结构（不同尺度卷积，再加一块）
- 中间层辅助Loss单元
- 最后的全连接替换为averagepooling

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-24-25-278.png)

v2用3\*3代替5\*5

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-11/2022-12-11_23-58-21-681.png)

## ResNet

明更深的网络在训练过程中的难度更大

不再学习从 x 到 H(x) 的基本映射关系，而是学习这两者之间的差异，也就是「残差（residual）」。

然后，为了计算 H(x)，我们只需要将这个残差加到输入上即可。假设残差为 F(x)=H(x)-x，那么现在我们的网络不会直接学习 H(x) 了，而是学习 F(x)+x。

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-29-44-640.png)
ResNet 的每一个「模块（block）」都由一系列层和一个「捷径（shortcut）」连接组成，`这个「捷径」将该模块的输入和输出连接到`了一起`。然后在元素层面上执行「加法（add）」运算，`

ResNet本质上就干了一件事：`降低数据中信息的冗余度`。具体说来，就是对非冗余信息采用了线性激活（通过skip connection获得无冗余的identity部分），然后对冗余信息采用了非线性激活（通过ReLU对identity之外的其余部分进行信息提取/过滤，提取出的有用信息即是残差）。其中，提取identity这一步，就是ResNet思想的核心。

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_18-02-16-982.png)

## DenseNet

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-34-16-632.png)



## SqueezeNet

- 1\*1卷积替换3\*3卷积

- 3*3卷积采用更少的channel数
- 降采样后置

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-35-03-541.png)

## MobileNet

- 先深度可分离，再1乘1卷积。减少参数量
- ReLU6，移动端部署精度没有那么高

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-08/2022-12-08_17-37-42-315.png)

## shuffleNet