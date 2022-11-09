# 网络整体框架

![quicker_cd67d58a-3733-47e5-91e1-097a53a00766.png](https://s2.loli.net/2022/04/05/L4CS36EP8IJaxio.png)

**层次化构建方法**（Hierarchical feature maps），比如特征图尺寸中有对图像下采样4倍的，8倍的以及16倍的，这样的backbone**有助于在此基础上构建目标检测，实例分割等任务**。



使用了Windows Multi-Head Self-Attention(W-MSA)的概念，比如在下图的4倍下采样和8倍下采样中，**将特征图划分成了多个不相交的区域（Window），并且Multi-Head Self-Attention只在每个窗口（Window）内进行**。相对于Vision Transformer中直接对整个（Global）特征图进行Multi-Head Self-Attention，这样做的目的是**能够减少计算量**的，尤其是在浅层特征图很大的时候。这样做虽然减少了计算量但也会**隔绝不同窗口之间的信息传递**，所以在论文中作者**又提出了 Shifted Windows Multi-Head Self-Attention(SW-MSA)**的概念



![quicker_02c970ce-1262-4ed7-8f02-9058c9d3677a.png](https://s2.loli.net/2022/04/05/lFgL1nkS382qJwQ.png)

- 首先将图片输入到Patch Partition模块中进行分块，即每4x4相邻的像素为一个Patch，然后在channel方向展平（flatten）。

  假设输入的是RGB三通道图片，那么每个patch就有4x4=16个像素，然后每个像素有R、G、B三个值所以展平后是16x3=48，所以通过Patch Partition后图像shape由 [H, W, 3]变成了 [H/4, W/4, 48]。

- 然后在通过Linear Embeding层对每个像素的channel数据做线性变换，由48变成C，即图像shape再由 [H/4, W/4, 48]变成了 [H/4, W/4, C]。

  其实在源码中Patch Partition和Linear Embeding就是**直接通过一个卷积层实现的**，和之前Vision Transformer中讲的 Embedding层结构一模一样。

- 然后就是通过四个Stage构建不同大小的特征图，除了Stage1中先通过一个Linear Embeding层外，剩下三个stage都是先通过一个Patch Merging层进行下采样（后面会细讲）。然后都是重复堆叠Swin Transformer Block

  注意这里的Block其实有两种结构，如图(b)中所示，这两种结构的不同之处仅在于**一个使用了W-MSA结构，一个使用了SW-MSA结构**。而且这两个结构是成对使用的，先使用一个W-MSA结构再使用一个SW-MSA结构。所以你会发现堆叠Swin Transformer Block的次数都是偶数（因为成对使用）。

- 最后对于分类网络，后面还会接上一个**Layer Norm层、全局池化层以及全连接层得到最终输出**。图中没有画，但源码中是这样做的。



# Patch Mergin详解

前面有说，在每个Stage中首先要通过一个Patch Merging层进行下采样（Stage1除外）。

如下图所示，假设输入Patch Merging的是一个4x4大小的单通道特征图（feature map），Patch Merging会将每个2x2的相邻像素划分为一个patch，然后**将每个patch中相同位置（同一颜色）像素给拼在一起就得到了4个feature map**。接着**将这四个feature map在深度方向进行concat拼接，然后在通过一个LayerNorm层**。最后**通过一个全连接层在feature map的深度方向做线性变化，将feature map的深度由C变成C/2**。通过这个简单的例子可以看出，通过Patch Merging层后，**feature map的高和宽会减半，深度会翻倍。**


![quicker_3df9cf3e-87a9-466d-9c3c-031f2c1e4f1b.png](https://s2.loli.net/2022/04/05/ghuNpZe1ycPHiGn.png)

# W-MSA详解

**引入Windows Multi-head Self-Attention（W-MSA）模块是为了减少计算量。**如下图所示，左侧使用的是普通的Multi-head Self-Attention（MSA）模块，对于feature map中的每个像素（或称作token，patch）在Self-Attention计算过程中需要和所有的像素去计算。但在图右侧，在使用Windows Multi-head Self-Attention（W-MSA）模块时，首先将feature map按照MxM（例子中的M=2）大小划分成一个个Windows，然后单独对每个Windows内部进行Self-Attention。

![quicker_66dfac86-8d5b-44a2-994e-8c9e2b87546a.png](https://s2.loli.net/2022/04/05/6OWTpK51flEZkeF.png)



## MSA模块计算量

![quicker_6bd670ae-0754-4b92-9b37-e24e7fa545dd.png](https://s2.loli.net/2022/04/05/Sa5D4HgIb7YeB6c.png)

## W-MSA模块计算量

![quicker_bda58244-7c16-4fee-9129-edd5d05eb0d6.png](https://s2.loli.net/2022/04/05/xhCBjmzGDfAvbLY.png)

# SW-MSA详解

**采用W-MSA模块时，只会在每个窗口内进行自注意力计算，所以窗口与窗口之间是无法进行信息传递的**。为了解决这个问题，作者引入了Shifted Windows Multi-Head Self-Attention（SW-MSA）模块，即进行偏移的W-MSA。如下图所示，左侧使用的是刚刚讲的W-MSA（假设是第L层），那么根据之前介绍的W-MSA和SW-MSA是成对使用的，那么第L+1层使用的就是SW-MSA（右侧图）。

根据左右两幅图对比能够发现窗**口（Windows）发生了偏移（可以理解成窗口从左上角分别向右侧和下方各偏移了$\left \lfloor \frac {M} {2} \right \rfloor $个像素）。**看下偏移后的窗口（右侧图），比如对于第一行第2列的2x4的窗口，它能够使第L层的第一排的两个窗口信息进行交流。再比如，第二行第二列的4x4的窗口，他能够使第L层的四个窗口信息进行交流，其他的同理。那么这就解决了不同窗口之间无法进行信息交流的问题。

![quicker_d4f7db14-e870-4e39-9901-5f1be240a846.png](https://s2.loli.net/2022/04/05/gw2bJv4EsHIQZtC.png)



可以发现通过将窗口进行偏移后，由原来的4个窗口变成9个窗口了。后面又要对每个窗口内部进行MSA，这样做感觉又变麻烦了。为了解决这个麻烦，作者又提出而了Efficient batch computation for shifted configuration，一种更加高效的计算方法。下面是原论文给的示意图。


![quicker_3a8d66d9-e4d8-4d1d-9a84-879bf6f7e16c.png](https://s2.loli.net/2022/04/05/CYn4i8d3sAQxrtq.png)



下图左侧是刚刚通过偏移窗口后得到的新窗口，右侧是为了方便大家理解，对每个窗口加上了一个标识。然后0对应的窗口标记为区域A，3和6对应的窗口标记为区域B，1和2对应的窗口标记为区域C。

![quicker_1682ddbc-cceb-4bde-8b1c-f4ce0a4ce94d.png](https://s2.loli.net/2022/04/05/qCFtg7ZTo9pVlfJ.png)

移动完后，4是一个单独的窗口；将5和3合并成一个窗口；7和1合并成一个窗口；8、6、2和0合并成一个窗口。这样又和原来一样是4个4x4的窗口了，所以能够保证计算量是一样的。这里肯定有人会想，把不同的区域合并在一起（比如5和3）进行MSA，这信息不就乱窜了吗？是的，为了防止这个问题，在**实际计算中使用的是masked MSA即带蒙板mask的MSA，这样就能够通过设置蒙板来隔绝不同区域的信息了**。关于mask如何使用，可以看下下面这幅图，下图是以上面的区域5和区域3为例。

![quicker_d81f0956-d74f-4aad-aa95-023e51d342d0.png](https://s2.loli.net/2022/04/05/EsonFSrhp7IkH9V.png)

# Relative Position Biasi详解

关于相对位置偏执，论文里也没有细讲，就说了参考的哪些论文，然后说使用了相对位置偏执后给够带来明显的提升。根据原论文中的表4可以看出，在Imagenet数据集上如果不使用任何位置偏执，top-1为80.1，但使用了相对位置偏执（rel. pos.）后top-1为83.3，提升还是很明显的。

![quicker_1972d160-e54a-446d-8c66-8e03d5c883c0.png](https://s2.loli.net/2022/04/05/6GUZYuyEaMINHfR.png)

![quicker_1c69f28c-377c-4061-84f9-170fd3f1de53.png](https://s2.loli.net/2022/04/05/Mict5VXB3lHvwLD.png)

![quicker_c2c5e93e-d727-4e56-96f7-828a3b98857f.png](https://s2.loli.net/2022/04/05/dAoRnq7CzWLiOxM.png)


# 模型详细配置参数



下图是原论文中给出的关于不同Swin Transformer的配置，T(Tiny)，S(Small)，B(Base)，L(Large)，其中：

win. sz. 7x7表示使用的窗口（Windows）的大小
dim表示feature map的channel深度（或者说token的向量长度）
head表示多头注意力模块中head的个数

![quicker_de2c4a34-a206-4f41-a256-1d527e54153b.png](https://s2.loli.net/2022/04/05/VbHEF28kQwGNrcx.png)