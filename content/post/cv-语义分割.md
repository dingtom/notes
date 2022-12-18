---
    # 文章标题
    title: "cv-语义分割"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
---







# 分割、检测

![quicker_49402ab9-9843-4939-b810-0dec623ac765.png](https://s2.loli.net/2022/03/31/WTwFk2NeqJ6SRxb.png)

图像分类：图像中的气球是一个类别。
语义分割：分割出气球和背景。
目标检测：图像中有7个目标气球，并且检测出每个气球的坐标位置。
实例分割：图像中有7个不同的气球，在像素层面给出属于每个气球的像素。

![quicker_f5c4e0b4-cbb5-44a4-bb19-7515cb96470d.png](https://s2.loli.net/2022/03/30/SpOhMr68QWbF2yg.png)



高分辨率特征(较浅的卷积层)`感知域较`小，有利于feature map和原图进行对齐的，也就是我说的可以提供更多的位置信息。



低分辨率信息(深层的卷积层)由于`感知域较大`，能够学习到更加`抽象`一些的特征，可以提供更多的上下文信息，即强语义信息，这有利于像素的精确分类。





`上采样`（意义在于将小尺寸的高维度feature map恢复回去）一般包括2种方式：

`Resize`，如双线性插值直接对图像进行缩放（这种方法在原文中提到）

`Deconvolution`（反卷积），也叫Transposed Convolution(转置卷积)，可以理解为卷积的逆向操作。

# FCN



FCN首先将一幅RGB图像输入到卷积神经网络后，经过多次卷积以及池化过程得到一系列的特征图，然后`利用反卷积层对最后一个卷积层得到的特征图进行上采样`，使得上采样后特征图与原图像的大小一样，从而实现对特征图上的每个像素值进行预测的同时保留其在原图像中的空间位置信息，最后对上采样特征图进行逐像素分类，逐个像素计算softmax分类损失。

主要特点：

- 不含全连接层（FC）的全卷积（Fully Conv）网络。从而可适应任意尺寸输入。
- 引入增大数据尺寸的反卷积（Deconv）层。能够输出精细的结果。
- 结合不同深度层结果的跳级（skip）结构。同时确保鲁棒性和精确性。

![quicker_fdb13fc8-47b1-42ca-8b6d-ad8d4ce801c2.png](https://s2.loli.net/2022/04/01/aAzVlHI98TebNSZ.png)





经过全卷积后的结果进行反卷积，基本上就能实现语义分割了，但是得到的结果通常是比较粗糙的。

图中，image是原图像，conv1,conv2..,conv5为卷积操作，pool1,pool2,..pool5为pool操作（pool就是使得图片变为原图的1/2），注意con6-7是最后的卷积层，最右边一列是upsample后的end to end结果。`必须说明的是图中nx是指对应的特征图上采样n倍（即变大n倍），并不是指有n个特征图，如32x upsampled 中的32x是图像只变大32倍，不是有32个上采样图像，又如2x conv7是指conv7的特征图变大2倍。`

`（1）FCN-32s过程`

只需要留意第一行，网络里面有5个pool，所以conv7的特征图是原始图像1/32，可以发现最左边image的是32x32（假设以倍数计），同时我们知道在FCN中的卷积是不会改变图像大小（或者只有少量像素的减少，特征图大小基本不会小很多）。看到pool1是16x16，pool2是8x8，pool3是4x4，pool4是2x2，pool5是1x1，所以conv7对应特征图大小为1x1，然后再经过32x upsampled prediction 图片变回32x32。FCN作者在这里增加一个卷积层，卷积后的大小为输入图像的`32`(2^5)倍，我们简单假设这个卷积核大小也为32，这样就是需要通过反馈训练32x32个权重变量即可让图像实现end to end，完成了一个32s的upsample。FCN作者称做后卷积，他也提及可以称为反卷积。事实上在源码中卷积核的大小为64，同时没有偏置bias。还有一点就是FCN论文中最后结果都是21×*，这里的21是指FCN使用的数据集分类，总共有21类。

`（2）FCN-16s过程`

现在我们把1,2两行一起看，忽略32x upsampled prediction，说明FCN-16s的upsample过程。FCN作者在conv7先进行一个2x conv7操作，其实这里也只是增加1个卷积层，这次卷积后特征图的大小为conv7的`2`倍，可以从pool5与2x conv7中看出来。此时2x conv7与pool4的大小是一样的，FCN作者提出对pool4与2x conv7进行一个fuse操作（`事实上就是将pool4与2x conv7相加，另一篇博客说是拼接，个人认为是拼接`）。fuse结果进行16x upsampled prediction，与FCN-32s一样，也是增加一个卷积层，卷积后的大小为输入图像的`16`(2^4)倍。我们知道pool4的大小是2x2，放大16倍，就是32x32，这样最后图像大小也变为原来的大小，至此完成了一个16s的upsample。现在我们可以知道，FCN中的upsample实际是通过增加卷积层，通过bp反馈的训练方法训练卷积层达到end to end，这时`卷积层的作用可以看作是pool的逆过程`。

`（3）FCN-8s过程`

这是我们看第1行与第3行，忽略32x upsampled prediction。conv7经过一次4x upsample，即使用一个卷积层，特征图输出大小为conv7的4倍，所得4x conv7的大小为4x4。然后pool4需要一次2x upsample，变成2x pool4，大小也为4x4。再把4x conv7，2x pool4与pool3进行fuse，得到求和后的特征图。最后增加一个卷积层，使得输出图片大小为pool3的8倍，也就是8x upsampled prediction的过程，得到一个end to end的图像。实验表明`FCN-8s优于FCN-16s，FCN-32s`。 我们可以发现，如果继续仿照FCN作者的步骤，我们可以对pool2，pool1实现同样的方法，可以有FCN-4s，FCN-2s，最后得到end to end的输出。这里作者给出了明确的结论，超过FCN-8s之后，结果并不能继续优化。

![quicker_56103cc9-bdb3-4cab-bb6a-c12d18d7965a.png](https://s2.loli.net/2022/04/01/GMbESzDXdZeiwaq.png)

图中，image是原图像，conv1,conv2..,conv5为卷积操作，pool1,pool2,..pool5为pool操作（pool就是使得图片变为原图的1/2），注意con6-7是最后的卷积层，最右边一列是upsample后的end to end结果。`必须说明的是图中nx是指对应的特征图上采样n倍（即变大n倍），并不是指有n个特征图，如32x upsampled 中的32x是图像只变大32倍，不是有32个上采样图像，又如2x conv7是指conv7的特征图变大2倍。`

`（1）FCN-32s过程`

只需要留意第一行，网络里面有5个pool，所以conv7的特征图是原始图像1/32，可以发现最左边image的是32x32（假设以倍数计），同时我们知道在FCN中的卷积是不会改变图像大小（或者只有少量像素的减少，特征图大小基本不会小很多）。看到pool1是16x16，pool2是8x8，pool3是4x4，pool4是2x2，pool5是1x1，所以conv7对应特征图大小为1x1，然后再经过32x upsampled prediction 图片变回32x32。FCN作者在这里增加一个卷积层，卷积后的大小为输入图像的`32`(2^5)倍，我们简单假设这个卷积核大小也为32，这样就是需要通过反馈训练32x32个权重变量即可让图像实现end to end，完成了一个32s的upsample。FCN作者称做后卷积，他也提及可以称为反卷积。事实上在源码中卷积核的大小为64，同时没有偏置bias。还有一点就是FCN论文中最后结果都是21×*，这里的21是指FCN使用的数据集分类，总共有21类。

`（2）FCN-16s过程`

现在我们把1,2两行一起看，忽略32x upsampled prediction，说明FCN-16s的upsample过程。FCN作者在conv7先进行一个2x conv7操作，其实这里也只是增加1个卷积层，这次卷积后特征图的大小为conv7的`2`倍，可以从pool5与2x conv7中看出来。此时2x conv7与pool4的大小是一样的，FCN作者提出对pool4与2x conv7进行一个fuse操作（`事实上就是将pool4与2x conv7相加，另一篇博客说是拼接，个人认为是拼接`）。fuse结果进行16x upsampled prediction，与FCN-32s一样，也是增加一个卷积层，卷积后的大小为输入图像的`16`(2^4)倍。我们知道pool4的大小是2x2，放大16倍，就是32x32，这样最后图像大小也变为原来的大小，至此完成了一个16s的upsample。现在我们可以知道，FCN中的upsample实际是通过增加卷积层，通过bp反馈的训练方法训练卷积层达到end to end，这时`卷积层的作用可以看作是pool的逆过程`。

`（3）FCN-8s过程`

这是我们看第1行与第3行，忽略32x upsampled prediction。conv7经过一次4x upsample，即使用一个卷积层，特征图输出大小为conv7的4倍，所得4x conv7的大小为4x4。然后pool4需要一次2x upsample，变成2x pool4，大小也为4x4。再把4x conv7，2x pool4与pool3进行fuse，得到求和后的特征图。最后增加一个卷积层，使得输出图片大小为pool3的8倍，也就是8x upsampled prediction的过程，得到一个end to end的图像。实验表明`FCN-8s优于FCN-16s，FCN-32s`。 我们可以发现，如果继续仿照FCN作者的步骤，我们可以对pool2，pool1实现同样的方法，可以有FCN-4s，FCN-2s，最后得到end to end的输出。这里作者给出了明确的结论，超过FCN-8s之后，结果并不能继续优化。

结合上述的FCN的全卷积与upsample，在upsample最后加上softmax，就可以对不同类别的大小概率进行估计，实现end to end。最后输出的图是一个概率估计，对应像素点的值越大，其像素为该类的结果也越大。`FCN的核心贡献在于提出使用卷积层通过学习让图片实现end to end分类。`



> `事实上，FCN有一些短处`，例如使用了较浅层的特征，因为fuse操作会加上较上层的pool特征值，导致高维特征不能很好得以使用，同时也因为使用较上层的pool特征值，导致FCN对图像大小变化有所要求，如果测试集的图像远大于或小于训练集的图像，FCN的效果就会变差。

# SegNet

Segnet是用于进行像素级别图像分割的全卷积网络，分割的核心组件是一个encoder 网络，及其相对应的decoder网络，后接一个象素级别的分类网络。

encoder网络：其结构与VGG16网络的前13层卷积层的结构相似。

decoder网络：作用是将由encoder的到的低分辨率的feature maps 进行映射得到与输入图像feature map相同的分辨率进而进行像素级别的分类。

最终解码器的输出被送入soft-max分类器以独立的为每个像素产生类别概率。

![quicker_15980490-03ee-472c-8b84-902828eeac1f.png](https://s2.loli.net/2022/04/01/VN5MAh1e46WJUBK.png)

Segnet的亮点：`decoder利用与之对应的encoder阶段中进行max-pooling时的pooling index 进行非线性上采样`，这样做的好处是上采样阶段就不需要进行学习。 上采样后得到的feature maps 是非常稀疏的，因此，需要进一步选择合适的卷积核进行卷积得到dense featuremaps 。

![quicker_70e69d53-897c-4edc-b1a3-da05921c3a62.png](https://s2.loli.net/2022/04/01/5u3fUAXbPiFWe1B.png)





#  UNet

![quicker_e2b7c8fb-0081-4b01-b46c-69b87f6858f4.png](https://s2.loli.net/2022/03/31/JFk612wB48r9mvl.png)



1. 首先进行Conv+Pooling下采样；
2. 然后反卷积进行上采样，crop之前的低层feature map，进行融合；
3. 再次上采样。
4. 重复这个过程，直到获得输出388x388x2的feature map，
5. 最后经过softmax获得output segment map。总体来说与FCN思路非常类似。



`UNet的encoder下采样4次，一共下采样16倍，对称地，其decoder也相应上采样4次，将encoder得到的高级语义特征图恢复到原图片的分辨率。`



它采用了与FCN不同的`特征融合`方式：

1. FCN采用的是`逐点相加`，对应tensorflow的tf.add()函数
2. U-Net采用的是`channel维度拼接融合`，对应tensorflow的tf.concat()函数

 Unet结构特点

> UNet相比于FCN和Deeplab等，共进行了4次上采样，并在同一个stage使用了skip connection，而不是直接在高级语义特征上进行监督和loss反传，这样就保证了最后恢复出来的特征图融合了更多的low-level的feature，也使得不同scale的feature得到了的融合，从而可以进行多尺度预测和DeepSupervision。4次上采样也使得分割图恢复边缘等信息更加精细。

为什么适用于医学图像？

> \1. 因为医学图像边界模糊、梯度复杂，需要较多的高分辨率信息。高分辨率用于精准分割。
> \2. 人体内部结构相对固定，分割目标在人体图像中的分布很具有规律，语义简单明确，低分辨率信息能够提供这一信息，用于目标物体的识别。
>
> 
>
> UNet结合了`低分辨率信息（提供物体类别识别依据）和高分辨率信息`（提供精准分割定位依据），完美适用于医学图像分割。

#  DeepLab

基于全卷积对称语义分割模型得到的分割结果比较粗糙，忽略了像素与像素之间的空间一致性关系。于是Google提出了一种新的扩张卷积语义分割模型，考虑了像素与像素之间的空间一致性关系，可以在不增加数量的情况下增加感受野。

![quicker_4964e7e0-fcc8-4e8d-9159-998a8891536c.png](https://s2.loli.net/2022/04/01/V1MXwxTdqUYjEly.png)

- Deeplabv1是由深度卷积网路和概率图模型级联而成的语义分割模型，由于深度卷积网路在重复最大池化和下采样的过程中会丢失很多的细节信息，所以采用`扩张卷积算法增加感受野以获得更多上下文信`息。考虑到深度卷积网路在图像标记任务中的空间不敏感性限制了它的定位精度，采用了`完全连接条件随机场（Conditional Random Field， CRF）来提高模型捕获细节的能力`。
- Deeplabv2予以分割模型增加了ASPP（Atrous spatial pyramid pooling）结构，利用多个不同采样率的扩张卷积提取特征，再将特征融合以捕获不同大小的上下文信息。
- Deeplabv3语义分割模型，在ASPP中加入了全局平均池化，同时在平行扩张卷积后添加批量归一化，有效地捕获了全局语义信息。
- DeepLabV3+语义分割模型在Deeplabv3的基础上增加了编-解码模块和Xception主干网路，增加编解码模块主要是为了恢复原始的像素信息，使得分割的细节信息能够更好的保留，同时编码丰富的上下文信息。增加Xception主干网络是为了采用深度卷积进一步提高算法的精度和速度。在inception结构中，先对输入进行1\*1卷积，之后将通道分组，分别使用不同的3\*3卷积提取特征，最后将各组结果串联在一起作为输出。

> 主要特点：
>
> - 在多尺度上为分割对象进行带洞空间金字塔池化（ASPP）
> - 通过使用DCNNs（空洞卷积）提升了目标边界的定位
> - 降低了由DCNN的不变性导致的定位准确率



# RefineNet

RefineNet采用了通过细化中间激活映射并分层地将其链接到结合多尺度激活，同时防止锐度损失。网络由独立的RefineNet模块组成，每个模块对应于ResNet。

每个RefineNet模块由三个主要模块组成，即剩余卷积单元（RCU），多分辨率融合（MRF）和链剩余池（CRP）。RCU块由一个自适应块组成卷积集，微调预训练的ResNet权重对于分割问题。MRF层融合不同的激活物使用卷积上采样层来创建更高的分辨率地图。最后，在CRP层池中使用多种大小的内核用于从较大的图像区域捕获背景上下文。

![quicker_2cd234a9-55a2-4f01-9df6-f668a22809c5.png](https://s2.loli.net/2022/04/01/n4CqyKYlxwZm6D9.png)

>主要特点：
>
>- 提出一种多路径refinement网络，称为RefineNet。这种网络可以使用各个层级的features，使得语义分割更为精准。
>- RefineNet中所有部分都利用resdiual connections (identity mappings)，使得梯度更容易短向或者长向前传，使端对端的训练变得更加容易和高效。
>- 提出了一种叫做chained residual pooling的模块，它可以从一个大的图像区域捕捉背景上下文信息。
>
>

#  PSPNet

深度卷积神经网络的每一层特征对语义分割都有影响，如何将高层特征的语义信息与底层识别的边界与轮廓信息结合起来是一个具有挑战性的问题。

金字塔场景稀疏网络语义分割模型（Pyramid Scene Parsing Network，PSP）首先结合预训练网络 ResNet和扩张网络来提取图像的特征，得到原图像 1/8 大小的特征图，然后，采用金字塔池化模块将特征图同时通过四个并行的池化层得到四个不同大小的输出，将四个不同大小的输出分别进行上采样，还原到原特征图大小，最后与之前的特征图进行连接后经过卷积层得到最后的预测分割图像。

- PSPNet为像素级场景解析提供了有效的全局上下文先验
- 金字塔池化模块可以收集具有层级的信息，比全局池化更有代表性
- 在计算量方面，我们的PSPNet并没有比原来的空洞卷积FCN网络有很大的增加
- 在端到端学习中，全局金字塔池化模块和局部FCN特征可以被同时训练

![quicker_94da9d25-ad62-4f89-81d5-fff7e145a90e.png](https://s2.loli.net/2022/04/01/VIU1rKW4LQJhZFt.png)



>主要特点：
>
>- 金字塔场景解析网络是建立在FCN之上的基于像素级分类网络。将大小不同的内核集中在一起激活地图的不同区域创建空间池金字塔。
>- 特性映射来自网络被转换成不同分辨率的激活，并经过多尺度处理池层，稍后向上采样并与原始层连接进行分割的feature map。
>- 学习的过程利用辅助分类器进一步优化了像ResNet这样的深度网络。不同类型的池模块侧重于激活的不同区域地图。
>
>





# 基于全卷积的GAN语义分割模型

生成对抗网络模型（Generative Adversarial Nets，GAN）同时训练生成器 G 和判别器 D，判别器用来预测给定样本是来自于真实数据还是来自于生成模型。

利用对抗训练方法训练语义分割模型，将传统的多类交叉熵损失与对抗网络相结合，首先对对抗网络进行预训练，然后使用对抗性损失来微调分割网络，如下图所示。左边的分割网络将 RGB 图像作为输入，并产生每个像素的类别预测。右边的对抗网络将标签图作为输入并生成类标签（1 代表真实标注，0 代表合成标签）。

![quicker_e26e71f8-565c-4c58-9a87-bc6a2e90f368.png](https://s2.loli.net/2022/04/01/u6DhPVCH3jqMIip.png)









# 基于全卷积语义分割模型对比

| 名称           | 优点                                                         | 缺点                                                         |
| :------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| FCN            | 可以接受任意大小的图像输入；避免了采用像素块带来的重复存储和计算的问题 | 得到的结果不太精确，对图像的细节不敏感，没有考虑像素与像素之间的关系，缺乏空间一致性 |
| SegNet         | 使用去池化对特征图进行上采样，在分割中保持细节的完整性；去掉全连接层，拥有较少的参数 | 当对低分辨率的特征图进行去池化时，会忽略邻近像素的信息       |
| Deconvnet      | 对分割的细节处理要强于 FCN，位于低层的filter 能捕获目标的形状信息，位于高层的 filter能够捕获特定类别的细节信息，分割效果更好 | 对细节的处理难度较大                                         |
| U-net          | 简单地将编码器的特征图拼接至每个阶段解码器的上采样特征图，形成了一个梯形结构；采用跳跃连接架构，允许解码器学习在编码器池化中丢失的相关性 | 在卷积过程中没有加pad，导致在每一次卷积后，特征长度就会减少两个像素，导致网络最后的输出与输入大小不一样 |
| DeepLab        | 使用了空洞卷积；全连接条件随机场                             | 得到的预测结果只有原始输入的 1/8 大小                        |
| RefineNet      | 带有解码器模块的编码器-解码器结构；所有组件遵循残差连接的设计方式 | 带有解码器模块的编码器-解码器结构；所有组件遵循残差连接的设计方式 |
| PSPNet         | 提出金字塔模块来聚合背景信息；使用了附加损失                 | 采用四种不同的金字塔池化模块，对细节的处理要求较高           |
| GCN            | 提出了带有大维度卷积核的编码器-解码器结构                    | 计算复杂，具有较多的结构参数                                 |
| DeepLabV3 ASPP | 采用了Multigrid；在原有的网络基础上增加了几个 block；提出了ASPP，加入了   BN | 不能捕捉图像大范围信息，图像层的特征整合只存在于 ASPP中      |
| GAN            | 提出将分割网络作为判别器，GAN   扩展训练数据，提升训练效果；将判别器改造为 FCN，从将判别每一个样本的真假变为每一个像素的真假 | 没有比较与全监督+半监督精调模型的实验结果，只体现了在本文中所提创新点起到了一定的作用，但并没有体现有效的程度 |











# 图像增强

```python
#                                      OpenCV
# 水平翻转
plt.imshow(cv2.flip(img, 0)
# 水平翻转
plt.imshow(cv2.flip(img, 0)
# 随机裁剪
x, y = np.random.randint(0, 256), np.random.randint(0, 256)
plt.imshow(img[x:x+256, y:y+256])

#                                    albumentations           
# 水平翻转
augments = A.HorizontalFlip(p=1)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
# 随机裁剪
augments = A.RandomCrop(p=1, height=256, width=256)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
# 旋转
augments = A.ShiftScaleRotate(p=1)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']

Compose            
transforms：转换类的数组，list类型
bbox_params：用于 bounding boxes 转换的参数，BboxPoarams 类型
keypoint_params：用于 keypoints 转换的参数， KeypointParams 类型
additional_targets：key新target 名字，value 为旧 target 名字的 dict，如 {'image2': 'image'}，dict 类型
p：使用这些变换的概率，默认值为 1.0           
组合与随机选择（Compose & OneOf）           
image4 = Compose([
        RandomRotate90(),
        # 翻转
        Flip(),
        Transpose(),
        OneOf([
            # 高斯噪点
            IAAAdditiveGaussianNoise(),
            GaussNoise(),
        ], p=0.2),
        OneOf([
            # 模糊相关操作
            MotionBlur(p=.2),
            MedianBlur(blur_limit=3, p=0.1),
            Blur(blur_limit=3, p=0.1),
        ], p=0.2),
        ShiftScaleRotate(shift_limit=0.0625, scale_limit=0.2, rotate_limit=45, p=0.2),
        OneOf([
            # 畸变相关操作
            OpticalDistortion(p=0.3),
            GridDistortion(p=.1),
            IAAPiecewiseAffine(p=0.3),
        ], p=0.2),
        OneOf([
            # 锐化、浮雕等操作
            CLAHE(clip_limit=2),
            IAASharpen(),
            IAAEmboss(),
            RandomBrightnessContrast(),            
        ], p=0.3),
        HueSaturationValue(p=0.3),
    ], p=1.0)

augments = image4(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
           
```



