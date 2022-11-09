- [ 1.安装](#head1)
	- [ 创建一个随机初始化的矩阵:](#head2)
	- [ 创建tensor并使用现有数据初始化:](#head3)
	- [根据现有的张量创建张量。 这些方法将重用输入张量的属性，例如， dtype，除非设置新的值进行覆盖](#head4)
	- [tensor/NumPy 转换](#head5)
	- [CUDA 张量](#head6)
	- [Autograd: 自动求导机制](#head7)
- [torchvision 是PyTorch中专门用来处理图像的库](#head8)
- [torchvision.transforms 模块提供了一般的图像转换操作类，用作数据处理和数据增强](#head9)
- [ 4.反向传播](#head10)
	- [当我们调用 loss.backward()时,整张计算图都会 根据loss进行微分，](#head11)
- [5.损失函数(Loss Function)](#head12)
	- [ nn.L1Loss:](#head13)
	- [ nn.NLLLoss:](#head14)
	- [ nn.MSELoss:](#head15)
	- [ nn.CrossEntropyLoss:](#head16)
	- [ nn.BCELoss:](#head17)
- [ 6.优化算法](#head18)
	- [ torch.optim.SGD](#head19)
	- [ torch.optim.RMSprop](#head20)
	- [ torch.optim.Adam](#head21)
- [ 7.正则化](#head22)
	- [ L1正则化](#head23)
	- [ L2正则化](#head24)
- [ 8.多GPU数据并行](#head25)
- [ 9.CNN](#head26)
	- [ 卷积层输出矩阵大小](#head27)
- [1 input image channel, 6 output channels, 5x5 square convolution](#head28)
- [ kernel](#head29)
- [an affine operation: y = Wx + b](#head30)
- [Max pooling over a (2, 2) window](#head31)
- [If the size is a square you can only specify a single number](#head32)
- [如果模型中有Batch Normalization和Dropout，需要在训练时添加model.train()，在测试时添加model.eval()。](#head33)
- [Batch Normalization在train时不仅使用了当前batch的均值和方差，也使用了历史batch统计上的均值和方差，](#head34)
- [并做一个加权平均 （momentum参数）。在test时，由于此时batchsize不一定一致，因此不再使用当前batch的](#head35)
- [ 均值和方差，仅使用历史训练时的统计值。](#head36)
- [ Dropout在train时随机选择神经元而predict要使用全部神经元并且要乘一个补偿系数](#head37)
- [ 默认情况下size_average=False，是mini-batchloss的平均值，然而，如果size_average=False，则是mini-batchloss的总和。](#head38)
- [Training settings](#head39)
- [ torch.cuda.manual_seed(args.seed)为当前GPU设置随机种子；如果使用多个GPU，应该使用torch.cuda.manual_seed_all()为所有的GPU设置种子。](#head40)
- [ 训练完成，保存状态字典到linear.pkl](#head41)
- [torch.save(model.state_dict(), './linear.pkl')](#head42)
- [ model.load_state_dict(torch.load('linear.pth'))](#head43)
# <span id="head1"> 1.安装</span>
[https://pytorch.org](https://pytorch.org/)
```pip3 config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple```
---

#　２.基础
##### 创建一个 5x3 矩阵, 但是未初始化:
```torch.empty(5, 3)```
##### <span id="head2"> 创建一个随机初始化的矩阵:</span>
```torch.rand(5, 3)```
##### 创建一个0填充的矩阵，数据类型为long
```torch.zeros(5, 3, dtype=torch.long)```
##### <span id="head3"> 创建tensor并使用现有数据初始化:</span>
```torch.tensor([5.5, 3])```
##### 改变张量的维度和大小
```torch.view```
##### <span id="head4">根据现有的张量创建张量。 这些方法将重用输入张量的属性，例如， dtype，除非设置新的值进行覆盖</span>
```x = x.new_ones(5, 3, dtype=torch.double)      # new_* 方法来创建对象```
>tensor([[1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.]], dtype=torch.float64)

```x = torch.randn_like(x, dtype=torch.float)    # 覆盖 dtype!```
>tensor([[ 0.5691, -2.0126, -0.4064],
        [-0.0863,  0.4692, -1.1209],
        [-1.1177, -0.5764, -0.5363],
        [-0.4390,  0.6688,  0.0889],
        [ 1.3334, -1.1600,  1.8457]])

##### <span id="head5">tensor/NumPy 转换</span>
```a = torch.ones(5)```
>tensor([1., 1., 1., 1., 1.])

```b = a.numpy()```
>[1. 1. 1. 1. 1.]

```
a.add_(1)
print(a)
print(b)
```
>tensor([2., 2., 2., 2., 2.])
[2. 2. 2. 2. 2.]

```
import numpy as np
a = np.ones(5)
b = torch.from_numpy(a)
np.add(a, 1, out=a)
print(a)
print(b)
```
>[2. 2. 2. 2. 2.]
tensor([2., 2., 2., 2., 2.], dtype=torch.float64)

##### <span id="head6">CUDA 张量</span>
```
# is_available 函数判断是否有cuda可以使用
# ``torch.device``将张量移动到指定的设备中
x = torch.randn(1)
if torch.cuda.is_available():
    device = torch.device("cuda")          # a CUDA 设备对象
    y = torch.ones_like(x, device=device)  # 直接从GPU创建张量
    x = x.to(device)                       # 或者直接使用``.to("cuda")``将张量移动到cuda中
    z = x + y
    print(z)
    print(z.to("cpu", torch.double))       # ``.to`` 也会对变量的类型做更改
```
>tensor([0.7632], device='cuda:0')
tensor([0.7632], dtype=torch.float64)

##### <span id="head7">Autograd: 自动求导机制</span>
每个张量都有一个.grad_fn属性，这个属性引用了一个创建了Tensor的Function（除非这个张量是用户手动创建的，即，这个张量的 grad_fn 是 None）。.requires_grad_( ... ) 可以改变现有张量的 requires_grad属性。
```
a = torch.randn(2, 2)
print(a.requires_grad)
a.requires_grad_(True)
print(a.requires_grad)
```
>False
True

```
x = torch.ones(2, 2, requires_grad=True)
print(x)
```
>tensor([[1., 1.], [1., 1.]], requires_grad=True)

```
y = x + 2
print(y)
```
>tensor([[3., 3.], [3., 3.]], grad_fn=<AddBackward>)

```
z = y * y * 3
out = z.mean()
print(z, out)
```
>tensor([[27., 27.],[27., 27.]], grad_fn=<MulBackward0>)
 tensor(27., grad_fn=<MeanBackward0>)

反向传播 因为 out是一个纯量（scalar），out.backward() 等于out.backward(torch.tensor(1))。
```out.backward()```
>print gradients d(out)/dx

```print(x.grad)```
>tensor([[4.5000, 4.5000],[4.5000, 4.5000]])

得到 $o = \frac{1}{4}\sum_i z_i$,$z_i = 3(x_i+2)^2$ and $z_i\bigr\rvert_{x_i=1} = 27$.

因此,$\frac{\partial o}{\partial x_i} = \frac{3}{2}(x_i+2)$, hence$\frac{\partial o}{\partial x_i}\bigr\rvert_{x_i=1} = \frac{9}{2} = 4.5$.

如果.requires_grad=True但是你又不希望进行autograd的计算， 那么可以将变量包裹在 with torch.no_grad()中:
```
print(x.requires_grad)
print((x ** 2).requires_grad)
with torch.no_grad():
	print((x ** 2).requires_grad)
```
>True
True
False

---
#　３.数据集
定义一个数据集
```
from torch.utils.data import Dataset
import pandas as pd
class BulldozerDataset(Dataset):
    def __init__(self, csv_file):
        self.df=pd.read_csv(csv_file)
    # 该方法返回数据集的总长度
    def __len__(self):
        return len(self.df)
    # 该方法定义用索引(0 到 len(self))获取一条数据或一个样本
    def __getitem__(self, idx):
        return self.df.iloc[idx].SalePrice
ds_demo= BulldozerDataset('median_benchmark.csv')
#实现了 __len__ 方法所以可以直接使用len获取数据总数
len(ds_demo)
#用索引可以直接访问对应的数据, 对应 __getitem__ 方法
ds_demo[0]

```


DataLoader为我们提供了对Dataset的读取操作
```
#batch_size, shuffle, num_workers(加载数据的时候使用几个子进程)
dl = torch.utils.data.DataLoader(ds_demo, batch_size=10, shuffle=True, num_workers=0)
# DataLoader返回的是一个可迭代对象，我们可以使用迭代器分次获取数据
# idata=iter(dl)
# print(next(idata))
for i, data in enumerate(dl):
    print(i,data)
```
### <span id="head8">torchvision 是PyTorch中专门用来处理图像的库</span>
torchvision.datasets 可以理解为PyTorch团队自定义的dataset，不仅提供了常用图片数据集，还提供了训练好的模型，可以加载之后，直接使用：
- MNIST- COCO- Captions- Detection- LSUN- ImageFolder- Imagenet-12- CIFAR- STL10- SVHN- PhotoTour
- AlexNet- VGG- ResNet- SqueezeNet- DenseNet
```
import torchvision.datasets as datasets
trainset = datasets.MNIST(root='./data', # 加载的目录
train=True,  # 表示是否加载数据库的训练集，false的时候加载测试集
download=True, # 表示是否自动下载 MNIST 数据集
transform=None) # 表示是否需要对数据进行预处理，none为不进行预处理
```
```
import torchvision.models as models
resnet18 = models.resnet18(pretrained=True)
```
---

# <span id="head9">torchvision.transforms 模块提供了一般的图像转换操作类，用作数据处理和数据增强</span>
```
from torchvision import transforms as transforms
transform = transforms.Compose([
    transforms.RandomCrop(32, padding=4),  #先四周填充0，在把图像随机裁剪成32*32
    transforms.RandomHorizontalFlip(),  #图像一半的概率翻转，一半的概率不翻转
    transforms.RandomRotation((-45,45)), #随机旋转
    transforms.ToTensor(),
    transforms.Normalize((0.4914, 0.4822, 0.4465), (0.229, 0.224, 0.225)), #R,G,B每层的归一化用到的均值和方差
])
```
---

# <span id="head10"> 4.反向传播</span>
#### <span id="head11">当我们调用 loss.backward()时,整张计算图都会 根据loss进行微分，</span>
而且图中所有设置为requires_grad=True的张量 将会拥有一个随着梯度累积的.grad 张量。
relu -> linear  -> MSELoss  -> loss
```
print(loss.grad_fn)  # MSELoss
print(loss.grad_fn.next_functions[0][0])  # Linear
print(loss.grad_fn.next_functions[0][0].next_functions[0][0])  # ReLU
```
><MseLossBackward object at 0x7f3b49fe2470>
<AddmmBackward object at 0x7f3bb05f17f0>
<AccumulateGrad object at 0x7f3b4a3c34e0>

现在，我们将调用loss.backward()，并查看conv1层的偏差（bias）项在反向传播前后的梯度。
```
net.zero_grad()     # 清除梯度
print('conv1.bias.grad before backward')
print(net.conv1.bias.grad)
loss.backward()
print('conv1.bias.grad after backward')
print(net.conv1.bias.grad)
```

>conv1.bias.grad before backward
tensor([0., 0., 0., 0., 0., 0.])
conv1.bias.grad after backward
tensor([ 0.0051,  0.0042,  0.0026,  0.0152, -0.0040, -0.0036])

---

# <span id="head12">5.损失函数(Loss Function)</span>
损失函数（loss function）是用来估量模型的预测值与真实值的不一致程度，它是一个非负实值函数,损失函数越小，模型的鲁棒性就越好。

### <span id="head13"> nn.L1Loss:</span>
输入x和目标y之间差的绝对值，要求 x 和 y 的维度要一样（可以是向量或者矩阵），得到的 loss 维度也是对应一样的

$ loss(x,y)=1/n\sum|x_i-y_i| $


### <span id="head14"> nn.NLLLoss:</span>
**用于多分类的负对数似然损失函数**

$ loss(x, class) = -x[class]$

NLLLoss中如果传递了weights参数，会对损失进行加权，公式就变成了

$ loss(x, class) = -weights[class] * x[class] $

### <span id="head15"> nn.MSELoss:</span>
均方损失函数 ，输入x和目标y之间均方差

$ loss(x,y)=1/n\sum(x_i-y_i)^2 $

### <span id="head16"> nn.CrossEntropyLoss:</span>
多分类用的交叉熵损失函数，LogSoftMax和NLLLoss集成到一个类中，会调用nn.NLLLoss函数,我们可以理解为CrossEntropyLoss()=log_softmax() + NLLLoss()


 $ \begin{aligned} loss(x, class) &= -\text{log}\frac{exp(x[class])}{\sum_j exp(x[j]))}\ &= -x[class] + log(\sum_j exp(x[j])) \end{aligned}  $

 因为使用了NLLLoss，所以也可以传入weight参数，这时loss的计算公式变为：

 $ loss(x, class) = weights[class] * (-x[class] + log(\sum_j exp(x[j]))) $

 所以一般多分类的情况会使用这个损失函数

### <span id="head17"> nn.BCELoss:</span>
计算 x 与 y 之间的二进制交叉熵。

$ loss(o,t)=-\frac{1}{n}\sum_i(t[i]* log(o[i])+(1-t[i])* log(1-o[i])) $ 

与NLLLoss类似，也可以添加权重参数： 

$ loss(o,t)=-\frac{1}{n}\sum_iweights[i]* (t[i]* log(o[i])+(1-t[i])* log(1-o[i])) $

用的时候需要在该层前面加上 Sigmoid 函数。

---

# <span id="head18"> 6.优化算法</span>
### <span id="head19"> torch.optim.SGD</span>
随机梯度下降算法,带有动量（momentum）的算法作为一个可选参数可以进行设置，样例如下：
```
#lr参数为学习率，对于SGD来说一般选择0.1 0.01.0.001，如何设置会在后面实战的章节中详细说明
#如果设置了momentum，就是带有动量的SGD，可以不设置
optimizer = torch.optim.SGD(model.parameters(), lr=0.1, momentum=0.9)
```
### <span id="head20"> torch.optim.RMSprop</span>
除了以上的带有动量Momentum梯度下降法外，RMSprop（root mean square prop）也是一种可以加快梯度下降的算法，利用RMSprop算法，**可以减小某些维度梯度更新波动较大的情况**，使其梯度下降的速度变得更快
```
# 我们的课程基本不会使用到RMSprop所以这里只给一个实例
optimizer = torch.optim.RMSprop(model.parameters(), lr=0.01, alpha=0.99)
```
### <span id="head21"> torch.optim.Adam</span>
Adam 优化算法的基本思想就是将 Momentum 和 RMSprop 结合起来形成的一种适用于不同深度学习结构的优化算法
```
# 这里的lr，betas，还有eps都是用默认值即可，所以Adam是一个使用起来最简单的优化方法
optimizer = torch.optim.Adam(model.parameters(), lr=0.001, betas=(0.9, 0.999), eps=1e-08)
```

---

# <span id="head22"> 7.正则化</span>

### <span id="head23"> L1正则化</span>
损失函数基础上加上权重参数的绝对值

$ L=E_{in}+\lambda{\sum_j} \left|w_j\right|$

### <span id="head24"> L2正则化</span>
损失函数基础上加上权重参数的平方和

$ L=E_{in}+\lambda{\sum_j} w^2_j$

需要说明的是：l1 相比于 l2 会更容易获得稀疏解

---

# <span id="head25"> 8.多GPU数据并行</span>
```
model = Model(input_size, output_size)
if torch.cuda.device_count() > 1:
    print("Let's use", torch.cuda.device_count(), "GPUs!")
    # DataParallel会自动的划分数据，并将作业发送到多个GPU上的多个模型。 并在每个模型完成作业后，收集合并结果并返回。
    model = nn.DataParallel(model)
model.to(device)
for data in rand_loader:
    # 请注意，只调用data.to(device)并没有复制张量到GPU上，而是返回了一个copy。所以你需要把它赋值给一个新的张量并在GPU上使用这个张量。
    input = data.to(device)
    output = model(input)
    print("Outside: input size", input.size(), "output_size", output.size())
```
---

# <span id="head26"> 9.CNN</span>
##### <span id="head27"> 卷积层输出矩阵大小</span>

ensorFlow里面的padding只有两个选项也就是valid和same
pytorch里面的padding么有这两个选项，它是数字0,1,2,3等等，默认是0
所以输出的h和w的计算方式也是稍微有一点点不同的：tf中的输出大小是和原来的大小成倍数关系，不能任意的输出大小；而nn输出大小可以通过padding进行改变



```` Conv2d（in_channels, out_channels, kernel_size, stride=1,padding=0, dilation=1, groups=1,bias=True, padding_mode='zeros')````


##### 池化层的输出大小公式也与卷积层一样，由于没有进行填充，所以p=0，可以简化为  $ \frac{n-f}{s} +1 $
通过减少卷积层之间的连接，降低运算复杂程度
```import torch.nn.functional as F```
```F.max_pool2d(input, kernel_size, stride=None, padding=0, dilation=1, ceil_mode=False, return_indices=False))```

### LeNet-5
[官网](http://yann.lecun.com/exdb/lenet/index.html)
卷积神经网路的开山之作，麻雀虽小，但五脏俱全，卷积层、pooling层、全连接层，这些都是现代CNN网络的基本组件

用卷积提取空间特征；由空间平均得到子样本；用 tanh 或 sigmoid 得到非线性；
用 multi-layer neural network（MLP）作为最终分类器；层层之间用稀疏的连接矩阵，以避免大的计算成本。
```
'''
C1层是一个卷积层，有6个卷积核（提取6种局部特征），核大小为5 * 5
S2层是pooling层，下采样（区域:2 * 2 ）降低网络训练参数及模型的过拟合程度。
C3层是第二个卷积层，使用16个卷积核，核大小:5 * 5 提取特征
S4层也是一个pooling层，区域:2*2
C5层是最后一个卷积层，卷积核大小:5 * 5 卷积核种类:120
最后使用全连接层，将C5的120个特征进行分类，最后输出0-9的概率

'''

import torch.nn as nn
class LeNet5(nn.Module):

    def __init__(self):
        super(LeNet5, self).__init__()
# <span id="head28">1 input image channel, 6 output channels, 5x5 square convolution</span>
# <span id="head29"> kernel</span>
        self.conv1 = nn.Conv2d(1, 6, 5)
        self.conv2 = nn.Conv2d(6, 16, 5)
# <span id="head30">an affine operation: y = Wx + b</span>
        self.fc1 = nn.Linear(16 * 5 * 5, 120) # 这里论文上写的是conv,官方教程用了线性层
        self.fc2 = nn.Linear(120, 84)
        self.fc3 = nn.Linear(84, 10)

    def forward(self, x):
# <span id="head31">Max pooling over a (2, 2) window</span>
        x = F.max_pool2d(F.relu(self.conv1(x)), (2, 2))
# <span id="head32">If the size is a square you can only specify a single number</span>
        x = F.max_pool2d(F.relu(self.conv2(x)), 2)
        x = x.view(-1, self.num_flat_features(x))
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x

    def num_flat_features(self, x):
        size = x.size()[1:]  # all dimensions except the batch dimension
        num_features = 1
        for s in size:
            num_features *= s
        return num_features


net = LeNet5()
print(net)
```

### AlexNet
2012，Alex Krizhevsky 可以算作LeNet的一个更深和更广的版本，可以用来学习更复杂的对象 .[论文](https://papers.nips.cc/paper/4824-imagenet-classification-with-deep-convolutional-neural-networks.pdf)

用rectified linear units（ReLU）得到非线性；
使用 dropout 技巧在训练期间有选择性地忽略单个神经元，来减缓模型的过拟合；
重叠最大池，避免平均池的平均效果；
虽然 AlexNet只有8层，但是它有60M以上的参数总量，Alexnet有一个特殊的计算层，LRN层，做的事是对当前层的输出结果做平滑处理，这里就不做详细介绍了， Alexnet的每一阶段（含一次卷积主要计算的算作一层）可以分为8层：

>con - relu - pooling - LRN ： 要注意的是input层是227*227，而不是paper里面的224，这里可以算一下，主要是227可以整除后面的conv1计算，224不整除。如果一定要用224可以通过自动补边实现，不过在input就补边感觉没有意义，补得也是0，这就是我们上面说的公式的重要性。
conv - relu - pool - LRN ： group=2，这个属性强行把前面结果的feature map分开，卷积部分分成两部分做
conv - relu
conv-relu
conv - relu - pool
fc - relu - dropout ： dropout层，在alexnet中是说在训练的以1/2概率使得隐藏层的某些neuron的输出为0，这样就丢到了一半节点的输出，BP的时候也不更新这些节点，防止过拟合。
fc - relu - dropout
fc - softmax
```
import torchvision
model = torchvision.models.alexnet(pretrained=False) #我们不下载预训练权重
print(model)
```
### VGG
2015，牛津的 VGG。[论文](https://arxiv.org/pdf/1409.1556.pdf)
每个卷积层中使用更小的 3×3 filters，并将它们组合成卷积序列
多个3×3卷积序列可以模拟更大的接收场的效果
每次的图像像素缩小一倍，卷积核的数量增加一倍
>VGG清一色用小卷积核，结合作者和自己的观点，这里整理出小卷积核比用大卷积核的优势：
根据作者的观点，input8 -> 3层conv3x3后，output=2，等同于1层conv7x7的结果； input=8 -> 2层conv3x3后，output=2，等同于2层conv5x5的结果
卷积层的参数减少。相比5x5、7x7和11x11的大卷积核，3x3明显地减少了参数量
通过卷积和池化层后，图像的分辨率降低为原来的一半，但是图像的特征增加一倍，这是一个十分规整的操作: 分辨率由输入的224->112->56->28->14->7, 特征从原始的RGB3个通道-> 64 ->128 -> 256 -> 512
```
import torchvision
model = torchvision.models.vgg16(pretrained=False) #我们不下载预训练权重
print(model)
```
### GoogLeNet (Inception)[](http://localhost:8888/notebooks/chapter2/2.4-cnn.ipynb#GoogLeNet-(Inception))

2014，Google Christian Szegedy [论文](https://arxiv.org/abs/1512.00567)

*   使用1×1卷积块（NiN）来减少特征数量，这通常被称为“瓶颈”，可以减少深层神经网络的计算负担。
*   每个池化层之前，增加 feature maps，增加每一层的宽度来增多特征的组合性

googlenet最大的特点就是包含若干个inception模块，所以有时候也称作 inception net googlenet虽然层数要比VGG多很多，但是由于inception的设计，计算速度方面要快很多。

---

#　Tensorboard
安装
```pip install tensorboard```
```tensorboard --logdir logs``` 即可启动，默认的端口是 6006,在浏览器中打开 ```http://localhost:6006/ ```即可看到web页面。







---

# MNIST数据集手写数字识别
```
import argparse       # Python 命令行解析工具
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms

class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = nn.Conv2d(in_channels=1, out_channels=32, kernel_size=3, stride=1,)
        self.conv2 = nn.Conv2d(in_channels=32, out_channels=64, kernel_size=3, stride=1,)
        self.dropout1 = nn.Dropout2d(0.25)
        self.dropout2 = nn.Dropout2d(0.5)
        self.fc1 = nn.Linear(9216, 128)
        self.fc2 = nn.Linear(128, 10)

    def forward(self, x):
        x = self.conv1(x)
        x = F.relu(x)
        x = self.conv2(x)
        x = F.relu(x)
        x = F.max_pool2d(x, 2)
        x = self.dropout1(x)
        x = torch.flatten(x, 1)
        x = self.fc1(x)
        x = F.relu(x)
        x = self.dropout2(x)
        x = self.fc2(x)
        output = F.log_softmax(x, dim=1)
        return output

def train(args, model, device, train_loader, optimizer, epoch):
# <span id="head33">如果模型中有Batch Normalization和Dropout，需要在训练时添加model.train()，在测试时添加model.eval()。</span>
# <span id="head34">Batch Normalization在train时不仅使用了当前batch的均值和方差，也使用了历史batch统计上的均值和方差，</span>
# <span id="head35">并做一个加权平均 （momentum参数）。在test时，由于此时batchsize不一定一致，因此不再使用当前batch的</span>
# <span id="head36"> 均值和方差，仅使用历史训练时的统计值。</span>
# <span id="head37"> Dropout在train时随机选择神经元而predict要使用全部神经元并且要乘一个补偿系数</span>
    model.train()
    
    for batch_idx, (data, target) in enumerate(train_loader):
        data, target = data.to(device), target.to(device)
        
        optimizer.zero_grad()
        output = model(data)
        """
        pytorch中CrossEntropyLoss是通过两个步骤计算出来的:
               第一步是计算log softmax，第二步是计算cross entropy（或者说是negative log likehood），
               CrossEntropyLoss不需要在网络的最后一层添加softmax和log层，直接输出全连接层即可。
               而NLLLoss则需要在定义网络的时候在最后一层添加log_softmax层(softmax和log层)
        总而言之：CrossEntropyLoss() = log_softmax() + NLLLoss() 
nn.CrossEntropyLoss()
        """
        loss = F.nll_loss(output, target)
        loss.backward()
        optimizer.step()
        
        if batch_idx % args.log_interval == 0:
            print('Train_Epoch:{} [{}/{} ({:.2f}%)] \t loss:{:.6f}'.format(epoch, 
                                                                   batch_idx*len(data), len(train_loader),
                                                                   100.0*batch_idx/len(train_loader),
                                                                   loss.item()))
    
            if args.dry_run:
                break

def test(model, device, test_loader):
    model.eval()
    
    test_loss = 0
    correct = 0
    
    with torch.no_grad():  #
        for data, target in test_loader:
            data, target = data.to(device), target.to(device)
            output = model(data)
# <span id="head38"> 默认情况下size_average=False，是mini-batchloss的平均值，然而，如果size_average=False，则是mini-batchloss的总和。</span>
            test_loss += F.nll_loss(output, target,  reduction='sum').item() # sum up batch loss
            pred = output.argmax(dim=1, keepdim=True)  # get the index of the max log-probability

            correct += pred.eq(target.view_as(pred)).sum().item()

    test_loss /= len(test_loader.dataset)
    print('\n Test: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)\n'.format(test_loss, 
                                                            correct, 
                                                            len(test_loader.dataset),
                                                            100. * correct / len(test_loader.dataset)))

def main():
# <span id="head39">Training settings</span>
    parser = argparse.ArgumentParser(description='PyTorch MNIST Example')
    parser.add_argument('-batch_size', type=int, default=64, metavar='N',
                        help='input batch size for training (default: 64)')
    parser.add_argument('-test_batch_size', type=int, default=1000, metavar='N',
                        help='input batch size for testing (default: 1000)')
    parser.add_argument('-epochs', type=int, default=10, metavar='N',
                        help='number of epochs to train (default: 10)')
    parser.add_argument('-lr', type=float, default=0.01, metavar='LR',
                        help='learning rate (default: 0.01)')
    parser.add_argument('-momentum', type=float, default=0.5, metavar='M',
                        help='SGD momentum (default: 0.5)')
    parser.add_argument('-gamma', type=float, default=0.7, metavar='M',
                        help='Learning rate step gamma (default: 0.7)')
    parser.add_argument('-no_cuda', action='store_true', default=False,
                        help='disables CUDA training')
    parser.add_argument('-dry_run', action='store_true', default=False,
                        help='quickly check a single pass')
    parser.add_argument('-seed', type=int, default=1, metavar='S',
                        help='random seed (default: 1)')
    parser.add_argument('-log_interval', type=int, default=100, metavar='N',
                        help='how many batches to wait before logging training status')
    parser.add_argument('-save_model', action='store_true', default=False,
                        help='For Saving the current Model')
    args = parser.parse_args(args=[])

    torch.manual_seed(args.seed)  #  #为CPU设置种子用于生成随机数，以使得结果是确定的
# <span id="head40"> torch.cuda.manual_seed(args.seed)为当前GPU设置随机种子；如果使用多个GPU，应该使用torch.cuda.manual_seed_all()为所有的GPU设置种子。</span>
    
    kwargs = {'batch_size': args.batch_size}
    use_cuda = not args.no_cuda and torch.cuda.is_available()
    if use_cuda:
        kwargs.update({'num_workers': 1,
                       'pin_memory': True,
                       'shuffle': True},)
        
    transform = transforms.Compose([transforms.ToTensor(),
                        transforms.Normalize((0.1307,), (0.3081,))])

    train_dataset = datasets.MNIST('./data', train=True, download=True, transform=transform,)                                         
    test_dataset = datasets.MNIST('./data', train=False, transform=transform)
    train_loader = torch.utils.data.DataLoader(train_dataset, **kwargs)
    test_loader = torch.utils.data.DataLoader(test_dataset, **kwargs)

    device = torch.device("cuda" if use_cuda else "cpu")
    model = Net().to(device)
    
    optimizer = optim.Adadelta(model.parameters(), lr=args.lr)
    scheduler = optim.lr_scheduler.StepLR(optimizer, step_size=1, gamma=args.gamma)

    for epoch in range(1, args.epochs+1):
        train(args, model, device, train_loader, optimizer, epoch)
        test(model, device, test_loader)
    
# <span id="head41"> 训练完成，保存状态字典到linear.pkl</span>
# <span id="head42">torch.save(model.state_dict(), './linear.pkl')</span>
# <span id="head43"> model.load_state_dict(torch.load('linear.pth'))</span>
    if args.save_model:
        torch.save(model.state_dict(), "mnist_cnn.pt")
        
if __name__ == '__main__':
    main()




```

