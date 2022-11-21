残差操作这一思想起源于论文《Deep Residual Learning for Image Recognition》。如果存在某个K层的网络f是当前最优的网络，那么可以构造一个更深的网络，其最后几层仅是该网络f第K层输出的恒等映射（IdentityMapping），就可以取得与f一致的结果；也许K还不是所谓“最佳层数”，那么更深的网络就可以取得更好的结果。**总而言之，与浅层网络相比，更深的网络的表现不应该更差。但是如下图所示，56层的神经网络表现明显要比20层的差。**证明更深的网络在训练过程中的难度更大，因此作者提出了残差网络的思想。+
![](https://upload-images.jianshu.io/upload_images/18339009-40d2301f5b154d36.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 定义
ResNet 的作者将这些问题归结成了一个单一的假设：直接映射是难以学习的。而且他们提出了一种修正方法：**不再学习从 x 到 H(x) 的基本映射关系，而是学习这两者之间的差异，也就是「残差（residual）」。然后，为了计算 H(x)，我们只需要将这个残差加到输入上即可。假设残差为 F(x)=H(x)-x，那么现在我们的网络不会直接学习 H(x) 了，而是学习 F(x)+x。**

这就带来了你可能已经见过的著名 ResNet（残差网络）模块：
![](https://upload-images.jianshu.io/upload_images/18339009-03922f431c7b16de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
ResNet 的每一个「模块（block）」都由一系列层和一个「捷径（shortcut）」连接组成，**这个「捷径」将该模块的输入和输出连接到**

**了一起**。然后在元素层面上执行「加法（add）」运算，**当输入和输出维度一致时，可以直接将输入加到输出上。如果输入和输出的大小不同，那就可以使用零填充或映射（通过 1×1 卷积）来得到匹配的大小。**



回到我们的思想实验，这能大大简化我们对恒等层的构建。直觉上就能知道，比起从头开始学习一个恒等变换，学会使 F(x) 为 0 并使输出仍为 x 要容易得多。一般来说，ResNet 会给层一个「参考」点 x，以 x 为基础开始学习。

在此之前，深度神经网络常常会有梯度消失问题的困扰，因为 ResNet 的梯度信号可以直接通过捷径连接回到更早的层，而且它们的表现依然良好。

ResNet本质上就干了一件事：**降低数据中信息的冗余度**。具体说来，就是对非冗余信息采用了线性激活（通过skip connection获得无冗余的identity部分），然后对冗余信息采用了非线性激活（通过ReLU对identity之外的其余部分进行信息提取/过滤，提取出的有用信息即是残差）。其中，提取identity这一步，就是ResNet思想的核心。

# 优点
一方面是残差网络**更好的拟合分类函数以获得更高的分类精度**，另一方面是残差网络如何**解决网络在层数加深时优化训练上的难题**。
### 1.残差网络拟合函数的优越性
首先从万能近似定理（Universal Approximation Theorem）入手。这个定理表明，一个前馈神经网络（feedforward neural network）如果具有线性输出层，同时至少存在一层具有任何一种“挤压”性质的激活函数（例如logistic sigmoid激活函数）的隐藏层，那么只要给予这个网络足够数量的隐藏单元，它就可以以任意的精度来近似任何从一个有限维空间到另一个有限维空间的波莱尔可测函数(Borel Measurable Function)。
万能近似定理意味着我们在构建网络来学习什么函数的时候，我们知道一定存在一个多层感知机（Multilayer Perceptron Model，MLP）能够表示这个函数。然而，我们不能保证训练算法能够学得这个函数。因为即使多层感知机能够表示该函数，学习也可能会失败，可能的原因有两种。

>（1）用于训练的优化算法可能找不到用于期望函数的参数值。
（2）训练算法可能由于过拟合而选择了错误的函数。

第二种过拟合情况不在我们的讨论范围之内，因此我们聚焦在前一种情况，为何残差网络相比简单的多层网络能更好的拟合分类函数，即找到期望函数的参数值。
对于普通的不带短连接的神经网络来说，存在这样一个命题。
![](https://upload-images.jianshu.io/upload_images/18339009-d84f3f0f274ffe51.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
事实上对于高维函数，这一特点依然适用。因此，当函数的输入维度非常高时，这一做法就变的非常有意义。尽管在高维空间这一特点很难被可视化，但是这个理论给了一个很合理的启发，就是原则上，带短连接的网络的拟合高维函数的能力比普通连接的网络更强。这部分我们讨论了残差网络有能力拟合更高维的函数，但是在实际的训练过程中仍然可能存在各种各样的问题使得学习到最优的参数非常困难，因此下一小节讨论残差在训练过程中的优越性。

### 2.残差网络训练过程的优越性

这个部分我们讨论为什么残差能够缓解深层网络的训练问题，以及探讨可能的短连接方式和我们最终选择的残差的理由。正如本章第三部分讨论的一样，整个残差卷积神经网络是由以上的残差卷积子模块堆积而成。如上一小节所定义的，假设第$l$层的残差卷积字子模块的映射为
![](https://upload-images.jianshu.io/upload_images/18339009-44651ee6bd351bdf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# pytorch 实现
```python
import torch.nn as nn
import torch
from torch.nn.init import kaiming_normal, constant

class BasicConvResBlock(nn.Module):

    def __init__(self, input_dim=128, n_filters=256, kernel_size=3, padding=1, stride=1, shortcut=False, downsample=None):
        super(BasicConvResBlock, self).__init__()

        self.downsample = downsample
        self.shortcut = shortcut

        self.conv1 = nn.Conv1d(input_dim, n_filters, kernel_size=kernel_size, padding=padding, stride=stride)
        self.bn1 = nn.BatchNorm1d(n_filters)
        self.relu = nn.ReLU()
        self.conv2 = nn.Conv1d(n_filters, n_filters, kernel_size=kernel_size, padding=padding, stride=stride)
        self.bn2 = nn.BatchNorm1d(n_filters)

    def forward(self, x):
        residual = x

        out = self.conv1(x)
        out = self.bn1(out)
        out = self.relu(out)

        out = self.conv2(out)
        out = self.bn2(out)

        if self.shortcut:
            out += residual

        out = self.relu(out)

        return out

```
