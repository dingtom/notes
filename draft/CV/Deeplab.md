# DeeplabV1

原论文名称：Semantic Image Segmentation with Deep Convolutional Nets and Fully Connected CRFs
论文下载地址：https://arxiv.org/abs/1412.7062
参考源码：https://github.com/TheLegendAli/DeepLab-Context

在论文的引言部分(INTRODUCTION)首先抛出了两个问题（针对语义分割任务）: **信号下采样导致分辨率降低**和**空间“不敏感”** 问题。

> There are two technical hurdles in the application of DCNNs to image labeling tasks: signal downsampling, and spatial ‘insensitivity’ (invariance).

**对于第一个问题信号下采样，作者说主要是采用Maxpooling导致的，为了解决这个问题作者引入了'atrous'(with holes) algorithm（空洞卷积 / 膨胀卷积 / 扩张卷积）。**



对于第二个问题空间“不敏感”，作者说分类器自身的问题（分类器本来就具备一定空间不变性），我个人认为其实还是Maxpooling导致的。**为了解决这个问题作者采用了fully-connected CRF(Conditional Random Field)方法，这个方法只在DeepLabV1-V2中使用到了，从V3之后就不去使用了**，而且这个方法挺耗时的。


## 优势

- 速度更快，论文中说是因为采用了膨胀卷积的原因，但fully-connected CRF很耗时
- 准确率更高，相比之前最好的网络提升了7.2个点
- 模型很简单，主要由DCNN和CRF联级构成



## 搭建细节

### LargeFOV

首先网络的backbone是当时比较火的VGG-16，并且和FCN网络一样将全连接层的权重转成了卷积层的权重，构成全卷积网络。然后关于膨胀卷积的使用，论文中是这么说的：

> We skip subsampling after the last two max-pooling layers in the network of Simonyan & Zisserman (2014) and modify the convolutional filters in the layers that follow them by introducing zeros to increase their length (2×in the last three convolutional layers and 4× in the first fully connected layer).

感觉文中的skip subsampling说的有点模糊（可能是自己英语水平太菜）什么叫做跳过下采样。既然看不懂论文的表述，就去看看代码。根据代码我绘制了如下所示的网络结构（DeepLab-LargeFOV）。

![quicker_2d7e361c-be91-4596-a8c6-15f68bd2118d.png](https://s2.loli.net/2022/05/06/Nwi8RsdqMKOPvDB.png)



通过分析发现虽然backbone是VGG-16但所使用Maxpool略有不同，**VGG论文中是kernel=2，stride=2，但在DeepLabV1中是kernel=3，stride=2，padding=1。**接着就是最后两个Maxpool层的stride全部设置成1了（这样下采样的倍率就从32变成了8）。最后三个3x3的卷积层采用了膨胀卷积，膨胀系数r=2。然后关于将全连接层卷积化过程中，对于第一个全连接层（FC1）在FCN网络中是直接转换成卷积核大小7x7，卷积核个数为4096的卷积层，但在DeepLabV1中作者说是对参数进行了下采样最终得到的是卷积核大小3x3，卷积核个数为1024的卷积层（这样不仅可以减少参数还可以减少计算量，详情可以看下论文中的Table2），对于第二个全连接层（FC2）卷积核个数也由4096采样成1024。

> After converting the network to a fully convolutional one, the first fully connected layer has 4,096 filters of large 7 × 7 spatial size and becomes the computational bottleneck in our dense score map computation. We have addressed this practical problem by spatially subsampling (by simple decimation) the first FC layer to 4×4 (or 3×3) spatial size.

将FC1卷积化后，还设置了膨胀系数，论文3.1中说的是r=4但在Experimental Evaluation中Large of View章节里设置的是r=12对应LargeFOV。对于FC2卷积化后就是卷积核1x1，卷积核个数为1024的卷积层。接着再通过一个卷积核1x1，卷积核个数为num_classes（包含背景）的卷积层。最后通过8倍上采样还原回原图大小。

下表是关于是否使用LargeFOV（Field of View）的对比。

![quicker_25610459-2310-489c-8688-a78f8ac4b341.png](https://s2.loli.net/2022/05/06/oNzBRYGuO7MmDPk.png)

第一行DeepLab-CRF-7x7就是直接将FC1按照FCN论文中的方法转换成7x7大小的卷积层，并且膨胀因子r=4（receptive field=224）。
第二行DeepLab-CRF是将7x7下采样到4x4大小的卷积层，同样膨胀因子r=4（receptive field=128），可以看到参数减半，训练速度翻倍，但mean IOU下降了约4个点。
第三行DeepLab-CRF-4x4，是在DeepLab-CRF的基础上把膨胀因子r改成了8（receptive field=224），mean IOU又提升了回去了。
第四行DeepLab-CRF-LargeFOV，是将7x7下采样到3x3大小的卷积层，膨胀因子r=12（receptive field=224），相比DeepLab-CRF-7x7，参数减少了6倍，训练速度提升了3倍多，mean IOU不变。

### MSc(Multi-Scale)

其实在论文的4.3中还提到了Multi-Scale Prediction，即**融合多个特征层的输出**。关于MSc(Multi-Scale)的结构论文中是这么说的：

> Specifically, we attach to the input image and the output of each of the first four max pooling layers a
> two-layer MLP (first layer: 128 3x3 convolutional filters, second layer: 128 1x1 convolutional filters) whose feature map is concatenated to the main network’s last layer feature map. The aggregate feature map fed into the softmax layer is thus enhanced by 5 * 128 = 640 channels.

即，**除了使用之前主分支上输出外，还融合了来自原图尺度以及前四个Maxpool层的输出**，更详细的结构参考下图。论文中说使用MSc大概能提升1.5个点，使用fully-connected CRF大概能提升4个点。但在源码中作者建议使用的是不带MSc的版本，以及看github上的一些开源实现都没有使用MSc。我个人猜测是因为这里的MSc不仅费时而且很吃显存。根据参考如下代码绘制了DeepLab-MSc-LargeFOV结构。


![quicker_17af5581-4e0c-4b6c-8225-0672a7d7f443.png](https://s2.loli.net/2022/05/06/cMTNPKo6tgI4jiV.png)

# DeeplabV2

论文名称：Semantic Image Segmentation with Deep Convolutional Nets, Atrous Convolution, and Fully Connected CRFs
论文下载地址：https://arxiv.org/abs/1606.00915



个人感觉相对DeepLab V1，DeepLab V2就是换了个backbone（VGG -> [ResNet](https://so.csdn.net/so/search?q=ResNet&spm=1001.2101.3001.7020)，简单换个backbone就能涨大概3个点）然后引入了一个新的模块ASPP（Atros Spatial Pyramid Pooling），其他的没太大区别。



在文章的引言部分作者提出了DCNNs应用在语义分割任务中遇到的问题。

- 分辨率被降低（主要由于下采样`stride>1`的层导致）
- 目标的多尺度问题
- DCNNs的不变性(invariance)会降低定位精度



针对分辨率被降低的问题，一般就是将最后的几个Maxpooling层的stride给设置成1(如果是通过卷积下采样的，比如resnet，同样将stride设置成1即可)，然后在配合使用膨胀卷积。

> In order to overcome this hurdle and efficiently produce denser feature maps, we remove the downsampling operator from the last few max pooling layers of DCNNs and instead upsample the filters in subsequent convolutional layers, resulting in feature maps computed at a higher sampling rate. Filter upsampling amounts to inserting holes (‘trous’ in French) between nonzero filter taps.

针对目标多尺度的问题，最容易想到的就是将图像缩放到多个尺度分别通过网络进行推理，最后将多个结果进行融合即可。这样做虽然有用但是计算量太大了。为了解决这个问题，DeepLab V2 中提出了**ASPP模块（atrous spatial pyramid pooling）**，具体结构后面会讲。

> A standard way to deal with this is to present to the DCNN rescaled versions of the same image and then aggregate the feature or score maps. We show that this approach indeed increases the performance of our system, but comes at the cost of computing feature responses at all DCNN layers for multiple scaled versions of the input image. Instead, motivated by spatial pyramid pooling, we propose a computationally efficient scheme of resampling a given feature layer at multiple rates prior to convolution. This amounts to probing the original image with multiple filters that have complementary effective fields of view, thus capturing objects as well as useful image context at multiple scales. Rather than actually resampling features, we efficiently implement this mapping using multiple parallel atrous convolutional layers with different sampling rates; we call the proposed technique “atrous spatial pyramid pooling” (ASPP).

针对DCNNs不变性导致定位精度降低的问题，和DeepLab V1差不多还是通过CRFs解决，不过**这里用的是fully connected pairwise CRF，相比V1里的fully connected CRF要更高效点。**在DeepLab V2中CRF涨点就没有DeepLab V1猛了，在DeepLab V1中大概能提升4个点，在DeepLab V2中通过Table4可以看到大概只能提升1个多点了。

> Our work explores an alternative approach which we show to be highly effective. In particular, we boost our model’s ability to capture fine details by employing a fully-connected Conditional Random Field (CRF) [22]. CRFs have been broadly used in semantic segmentation to combine class scores computed by multi-way classifiers with the low-level information captured by the local interactions of pixels and edges [23], [24] or superpixels [25]. Even though works of increased sophistication have been proposed to model the hierarchical dependency [26], [27], [28] and/or high-order dependencies of segments [29], [30], [31], [32], [33], we use the fully connected pairwise CRF proposed by [22] for its efficient computation, and ability to capture fine edge details while also catering for long range dependencies.
> 



## 优势

- 速度更快
- 准确率更高（当时的state-of-art）
- 模型结构简单，还是DCNNs和CRFs联级

> From a practical standpoint, the three main advantages of our DeepLab system are: (1) Speed: by virtue of atrous convolution, our dense DCNN operates at 8 FPS on an NVidia Titan X GPU, while Mean Field Inference for the fully-connected CRF requires 0.5 secs on a CPU. (2) Accuracy: we obtain state-of-art results on several challenging datasets, including the PASCAL VOC 2012 semantic segmentation benchmark [34], PASCAL-Context [35], PASCAL-Person-Part [36], and Cityscapes [37]. (3) Simplicity: our system is composed of a cascade of two very well-established modules, DCNNs and CRFs.

## ASPP(atrous spatial pyramid pooling)

个人觉得在DeepLab V2中值得讲的就这个ASPP模块了，其他的都算不上啥亮点。这个ASPP模块给我感觉就像是DeepLab V1中LargeFOV的升级版（加入了多尺度的特性）。下图是原论文中介绍ASPP的示意图，就是在backbone输出的Feature Map上并联四个分支，**每个分支的第一层都是使用的膨胀卷积，但不同的分支使用的膨胀系数不同（即每个分支的感受野不同，从而具有解决目标多尺度的问题）**。

![quicker_14c7a5db-8895-4bde-a095-5b09d66acb84.png](https://s2.loli.net/2022/05/06/KZoJD3HWrTfNwA2.png)

下图(b)有画出更加详细的ASPP结构（这里是针对VGG网络为例的），将Pool5输出的特征层（这里以VGG为例）并联4个分支，每个分支分别通过一个3x3的膨胀卷积层，1x1的卷积层，1x1的卷积层（卷积核的个数等于num_classes）。最后将四个分支的结果进行Add融合即可。 如果是以ResNet101做为Backbone的话，每个分支只有一个3x3的膨胀卷积层，卷积核的个数等于num_classes（看源码分析得到的）。

![quicker_d64c1758-710c-4482-a3e5-74411931df05.png](https://s2.loli.net/2022/05/06/lhoLjGPHUNxAynV.png)



在论文中有给出两个ASPP的配置，ASPP-S（四个分支膨胀系数分别为2,4,8,12）和ASPP-L（四个分支膨胀系数分别为6,12,18,24），下表是对比LargeFOV、ASPP-S以及ASPP-L的效果。这里只看CRF之前的（before CRF）对比，ASPP-L优于ASPP-S优于LargeFOV。


## 网络结构

这里以ResNet101作为backbone为例，下图是根据官方源码绘制的网络结构（这里不考虑MSC即多尺度）。在ResNet的Layer3中的Bottleneck1中原本是需要下采样的（3x3的卷积层stride=2），但在DeepLab V2中将stride设置为1，即不在进行下采样。而且3x3卷积层全部采用膨胀卷积膨胀系数为2。在Layer4中也是一样，取消了下采样，所有的3x3卷积层全部采用膨胀卷积膨胀系数为4。最后需要注意的是ASPP模块，在以ResNet101做为Backbone时，每个分支只有一个3x3的膨胀卷积层，且卷积核的个数都等于num_classes。
![quicker_39df6c1a-d8dc-4271-94ff-a376c5382425.png](https://s2.loli.net/2022/05/06/quB173hCwcvjfEA.png)



## Learning rate policy

在DeepLab V2中训练时采用的学习率策略叫poly，相比普通的step策略（即每间隔一定步数就降低一次学习率）效果要更好。文中说最高提升了3.63个点，真是炼丹大师。poly学习率变化策略公式如下：
$$ lr \times (1 - \frac{iter}{max\_iter})^{power}$$


其中$lr$为初始学习率，$iter$为当前迭代的step数，$max_{iter}$为训练过程中总的迭代步数。




# DeeplabV3

论文名称：Rethinking Atrous Convolution for Semantic Image Segmentation
论文下载地址：https://arxiv.org/abs/1706.05587
非官方Pytorch实现代码：pytorch_segmentation/deeplab_v3



相比DeepLab V2有三点变化：1）引入了`Multi-grid`，2）改进了`ASPP`结构，3）把`CRFs`后处理给移除掉了。



## 两种模型结构

给出两个模型，cascaded model和ASPP model，在cascaded model中是没有使用ASPP模块的，在ASPP model中是没有使用cascaded blocks模块的，如果没有弄清楚这两个模型的区别，那么这篇文章就算不上看懂。注意，虽然文中提出了两种结构，但作者说ASPP model比cascaded model略好点。包括在Github上开源的一些代码，大部分也是用的ASPP model。

> Both our best cascaded model (in Tab. 4) and ASPP model (in Tab. 6) (in both cases without DenseCRF post-processing or MS-COCO pre-training) already outperform DeepLabv2.

### cascaded model

在这篇论文中，大部分实验基本都是围绕cascaded model来做的。如下图所示，论文中提出的cascaded model指的是图(b)。其中Block1，Block2，Block3，Block4是原始ResNet网络中的层结构，但在Block4中将第一个残差结构里的3x3卷积层以及捷径分支上的1x1卷积层步距stride由2改成了1（即不再进行下采样），并且所有残差结构里3x3的普通卷积层都换成了膨胀卷积层。Block5，Block6和Block7是额外新增的层结构，他们的结构和Block4是一模一样的，即由三个残差结构构成。

![quicker_57cbe1eb-dbba-4dc2-9fd8-5fb77e29b0be.png](https://s2.loli.net/2022/05/06/Jf52EdtQiZDealW.png)

注意，原论文中说在训练cascaded model时output_stride=16（即特征层相对输入图片的下采样率），但验证时使用的output_stride=8（这里论文里虽然没有细讲，但我猜应该是把Block3中的下采样取消了）。因为output_stride=16时最终得到的特征层H和W会更小，这意味着可以设置更大的batch_size并且能够加快训练速度。但特征层H和W变小会导致特征层丢失细节信息（文中说变的更“粗糙”），所以在验证时采用的output_stride=8。其实只要你GPU显存足够大，算力足够强也可以直接把output_stride设置成8。

> Also note that training with output stride = 16 is several times faster than output stride = 8 since the intermediate feature maps are spatially four times smaller, but at a sacrifice of accuracy since output stride = 16 provides coarser feature maps.

另外需要注意的是，图中标注的rate并不是膨胀卷积真正采用的膨胀系数。 真正采用的膨胀系数应该是图中的rate乘上Multi-Grid参数，比如Block4中rate=2，Multi-Grid=(1, 2, 4)那么真正采用的膨胀系数是2 x (1, 2, 4)=(2, 4, 8)。关于Multi-Grid参数后面会提到。

> The final atrous rate for the convolutional layer is equal to the multiplication of the unit rate and the corresponding rate. For example, when output stride = 16 and Multi Grid = (1, 2, 4), the three convolutions will have rates = 2 · (1, 2, 4) = (2, 4, 8) in the block4, respectively.

### ASPP model

虽然论文大篇幅的内容都在讲cascaded model以及对应的实验，但实际使用的最多的还是ASPP model，ASPP model结构如下图所示：

![quicker_107b29d3-ab44-498e-a0c6-5246668c8c7b.png](https://s2.loli.net/2022/05/06/6x5GSlmC3Oo7HKz.png)



注意，和cascaded model一样，原论文中说在训练时output_stride=16（即特征层相对输入图片的下采样率），但验证时使用的output_stride=8。但在Pytorch官方实现的DeepLabV3源码中就直接把output_stride设置成8进行训练的。

接下来分析下DeepLab V3中ASPP结构。首先回顾下上篇博文中讲的DeepLab V2中的ASPP结构，DeepLab V2中的ASPP结构其实就是通过四个并行的膨胀卷积层，每个分支上的膨胀卷积层所采用的膨胀系数不同（注意，这里的膨胀卷积层后没有跟BatchNorm并且使用了偏执Bias）。接着通过add相加的方式融合四个分支上的输出。

![quicker_9e2da515-a7bb-411f-95ab-c4fdae0ca018.png](https://s2.loli.net/2022/05/06/Wta3Byjd4VKpwPY.png)

我们再来看下DeepLab V3中的ASPP结构。这里的ASPP结构有5个并行分支，分别是一个1x1的卷积层，三个3x3的膨胀卷积层，以及一个全局平均池化层（后面还跟有一个1x1的卷积层，然后通过双线性插值的方法还原回输入的W和H）。关于最后一个全局池化分支作者说是为了增加一个全局上下文信息global context information。然后通过Concat的方式将这5个分支的输出进行拼接（沿着channels方向），最后在通过一个1x1的卷积层进一步融合信息。
![quicker_dee1dd7c-5307-499a-a4de-237cc12f6c59.png](https://s2.loli.net/2022/05/06/eK6wWxHUtFI1MBN.png)

关于原论文中ASPP（Atrous Spatial Pyramid Pooling）结构介绍可以看下下面这段话。

> Specifically, we apply global average pooling on the last feature map of the model, feed the resulting image-level features to a 1 × 1 convolution with 256 filters (and batch normalization [38]), and then bilinearly upsample the feature to the desired spatial dimension. In the end, our improved ASPP consists of (a) one 1×1 convolution and three 3 × 3 convolutions with rates = (6, 12, 18) when output stride = 16 (all with 256 filters and batch normalization), and (b) the image-level features, as shown in Fig. 5. Note that the rates are doubled when output stride = 8. The resulting features from all the branches are then concatenated and pass through another 1 × 1 convolution (also with 256 filters and batch normalization) before the final 1 × 1 convolution which generates the final logits.

## Multi-grid

在之前的DeepLab模型中虽然一直在使用膨胀卷积，但设置的膨胀系数都比较随意。在DeepLab V3中作者有去做一些相关实验看如何设置更合理。下表是以cascaded model（ResNet101作为Backbone为例）为实验对象，研究采用不同数量的cascaded blocks模型以及cascaded blocks采用不同的Multi-Grid参数的效果。注意，刚刚在讲cascaded model时有提到，blocks中真正采用的膨胀系数应该是图中的rate乘上这里的Multi-Grid参数。通过实验发现，当采用三个额外的Block时（即额外添加Block5，Block6和Block7）将Multi-Grid设置成(1, 2, 1)效果最好。另外如果不添加任何额外Block（即没有Block5，Block6和Block7）将Multi-Grid设置成(1, 2, 4)效果最好，因为在ASPP model中是没有额外添加Block层的，后面讲ASPP model的消融实验时采用的就是Multi-Grid等于(1, 2, 4)的情况。

