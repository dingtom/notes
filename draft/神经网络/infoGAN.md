![](https://upload-images.jianshu.io/upload_images/18339009-c52910e7276a8366.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>InfoGAN的发布时间应该在是DCGAN之后没多久，可以算是在大部分的GAN模型的前面的。时间上看，InfoGAN应该是在Semi-GAN和Cat-GAN之后提出，在ImprovedGAN和ACGAN之前提出。从算法分类上看，InfoGAN属于半监督模型，但是不同于一般的半监督模型，比如，SemiGAN，CatGAN, ImprovedGAN，ACGAN等。后面的这些模型添加半监督的思路，主要是想要将GAN中D扩张为一个可以分类图像label，而不是单纯的分是否是bogus data（即，是否来自于G）。
>- Semi-GAN：D输出变成K+1。（1为原来的fake or not的判断， K为分类器的目标分类类数）
>- CatGAN：D的输出变成K。结合信息熵，认为概率在每个类上越接近等概率，表示data来自于G。当然，越是集中在某个类别上，这样就可以描述具体的类别了。
> - ImprovedGAN：D的输出变成K。做两层的softmax。一层是在D上做，这里只是将D当做一个分类器来看待。之后，再假设有还有一个类别，即fake，K+1。由于其他K个类别数字都在变，因此假设最后一个类别数值固定也可以再加一层softmax完成。
>- ACGAN：将D分解，D的卷积层作为特征挖掘的层（一般也是这么认为的）。之后，对于这样的特征再做不同的映射。一个将特征映射到K上（分类器）， 一个是将特征映射到0/1上（判别器）。（判别器本质上也是分类器，这里主要是为了区别说明）


但是，会注意到，其实，无论怎么改，大家在半监督的GAN上的挖掘都是停留在D上。而忽视了G（当然也是G上不太好做文章的原因）。
一般来说，G的输入只有z 。GAN的训练方式，是将一个随机变量，通过博弈的方式，让z在G上具有意义。也就是使得没有特定信息的变量z，在通过G的映射之后，变得具有某种含义。这种含义使得z的变化会影响到G的生成效果。**InfoGAN的操作，就是尝试添加其他的输入，使得这参数也具有意义。**

既然要让输入的噪声向量z带有一定的语义信息，那就人为的为它添加上一些限制，于是作者把G的输入看成两部分：
>- 一部分就是噪声z，可以将它看成是不可压缩的噪声向量。
>- 另一部分是若干个离散的和连续的latent variables（潜变量）所拼接而成的向量c，用于代表生成数据的不同语意信息。

以MNIST数据集为例，可以用一个离散的随机变量（0-9，用于表示生成数字的具体数值）和两个连续的随机变量（假设用于表示笔划的粗细与倾斜程度）。所以此时的c由一个离散的向量（长度为10）、两个连续的向量（长度为1）拼接而成，即c长度为12。
![](https://upload-images.jianshu.io/upload_images/18339009-affbd37ec32c7d81.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###### 机器学习中的各种熵
>a) 自信息：事件提供的信息量，与概率成反比
![](https://upload-images.jianshu.io/upload_images/18339009-6ebfba4060211a31?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
b) 信息熵，自信息关于概率的期望，反映不确定度
![](https://upload-images.jianshu.io/upload_images/18339009-a5384a0c529b6eec?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
c) 联合熵，两个事件间的不确定度
![](https://upload-images.jianshu.io/upload_images/18339009-fee61ac4c22785d5?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>d) 条件熵，已知X下，Y的不确定度
![](https://upload-images.jianshu.io/upload_images/18339009-29fc307a34eeee15?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
同时条件熵和联合熵，信息熵的关系如下：
![](https://upload-images.jianshu.io/upload_images/18339009-fe115bd12a450bba?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
e) 交叉熵，衡量两个分布的差异程度
![](https://upload-images.jianshu.io/upload_images/18339009-a6aacccf52b9dd32?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
f) 相对熵，KL散度，**交叉熵和KL散度成正相关**
![](https://upload-images.jianshu.io/upload_images/18339009-a17da3d3cfadf61c?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
g) 互信息，**已知一个变量后，另一个变量减小了多少不确定度**，本文重点
![](https://upload-images.jianshu.io/upload_images/18339009-6c0343ee44831390?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

互信息式中，$H$表示计算熵值，所以$I(X;Y)$是两个熵值的差。$H(X|Y)$衡量的是“给定随机变量的情况下，随机变量$X$的不确定性”。从公式中可以看出，若$X$和$Y$是独立的，此时$H(X)=H(X|Y)$，得到$I(X;Y)=0$，为最小值。若$X$和$Y$有非常强的关联时，即已知$Y$时，$X$没有不确定性，则$H(X|Y)=0$, $I(X;Y)$达到最大值。所以为了让$G(z,c)$和$c$之间产生尽量明确的语义信息，必须要让它们二者的互信息足够的大，所以我们对GAN的损失函数添加一个正则项，就可以改写为：
![](https://upload-images.jianshu.io/upload_images/18339009-beeea3a7453c419b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**注意$I(c;G(z,c))$属于G的损失函数的一部分，所以这里为负号，即让该项越大越好，使得G的损失函数变小。**其中 $\lambda$ 为平衡两个损失函数的权重。但是，在计算$I(c;G(z,c))$的过程中，需要知道后验概率分布$P(c|x)$ ，而这个分布在实际中是很难获取的，因此作者在解决这个问题时采用了变分推理的思想，引入变分分布 $Q(c|x)$来逼近$P(c|x)$
![](https://upload-images.jianshu.io/upload_images/18339009-2d9b41b807e6289d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-baedccfee0e1666b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-a45877a383af6f8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

故$ L_I (G, Q) $是互信息的一个下界。作者指出，用蒙特卡罗模拟（Monte Carlo simulation）去逼近$ L_I (G, Q)$ 是较为方便的，这样我们的优化问题就可以表示为：

![](https://upload-images.jianshu.io/upload_images/18339009-0af2cf52cd0609ac.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


从上图可以清晰的看出，虽然在设计InfoGAN时的数学推导比较复杂，但是网络架构还是非常简单明了的。G和D的网络结构和DCGAN保持一致，均由CNN构成。在此基础上，改动的地方主要有：
>1.G的输入不仅仅是噪声向量z了，而是z和具有语意信息的浅变量c进行拼接后的向量输入给G。
2.D的输出在原先的基础上添加了一个新的输出分支Q，Q和D共享全部分卷积层，然后各自通过不同的全连接层输出不同的内容：Q的输出对应于$X_{fake}$的c的概率分布，D则仍然判别真伪。

**InfoGAN的训练流程**

假设batch_size=m，数据集为MNIST，则根据作者的方法，不可压缩噪声向量的长度为62，离散潜变量的个数为1，取值范围为[0, 9]，代表0-9共10个数字，连续浅变量的个数为2，代表了生成数字的倾斜程度和笔划粗细，最好服从[-2, 2]上的均匀分布，因为这样能够显式的通过改变其在[-2,2]上的数值观察到生成数据相应的变化，便于实验，所以此时输入变量的长度为62+10+2=74。

则在每一个epoch中：
>**先训练判别器k（比如3）次：**
1\. 从噪声分布（比如高斯分布）中随机采样出m个噪声向量：
![](https://upload-images.jianshu.io/upload_images/18339009-c5d01fc306415848.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2.从真实样本x中随机采样出m个样本：
![](https://upload-images.jianshu.io/upload_images/18339009-b349e54ff0e191ce.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3\. 用梯度下降法使损失函数real_loss：$logD(x^{(i)})$与1之间的二分类交叉熵减小（因为最后判别器最后一层的激活函数为sigmoid，所以要与0或者1做二分类交叉熵，这也是为什么损失函数要取log的原因）。
4.用梯度下降法使损失函数fake_loss： $logD(z^{(i)})$与0之间的二分类交叉熵减小。
5\. 所以判别器的总损失函数d_loss:
![](https://upload-images.jianshu.io/upload_images/18339009-42dd10e627a85885.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
即让d_loss减小。注意**在训练判别器的时候生成器中的所有参数要固定住，即不参加训练。**
>---
>**再训练生成器1次：**
1\. 从噪声分布中随机采样出m个噪声向量：
![](https://upload-images.jianshu.io/upload_images/18339009-cb79ec1d72bccb5c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2\. 从离散随机分布中随机采样m个长度为10、one-hot编码格式的向量：
![](https://upload-images.jianshu.io/upload_images/18339009-f452ad27eb0d4b03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3\. 从两个连续随机分布中各随机采样m个长度为1的向量：
![](https://upload-images.jianshu.io/upload_images/18339009-ae5f105206acbc6a.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-4faccda3bb883a53.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4\. 将上面的所有向量进行concat操作，得到长度为74的向量，共m个，并记录每个向量所在的位置，便于计算损失函数。
5\. 此时g_loss由三部分组成：一个是 $logD(z^{(i)})$与1之间的二分类交叉熵、一个是Q分支输出的离散浅变量的预测值和相应的输入部分的交叉熵以及Q分支输出的连续浅变量的预测值和输入部分的互信息，并为这三部分乘上适当的平衡因子，其中互信息项的系数是负的。
6\. 用梯度下降法使越小越好。注意在训练生成器的时候判别器中的所有参数要固定住，即不参加训练。

直到所有epoch执行完毕，训练结束。

**（四）总结**

1.G的输入不再是一个单一的噪声向量，而是噪声向量与潜变量的拼接。
2.对于潜变量来说，G和D组成的大网络就好比是一个AutoEncoder，不同之处只是将信息编码在了图像中，而非向量，最后通过D解码还原回。
3.D的输出由原先的单一分支变为两个不同的分支。
4.从信息熵的角度对噪声向量和潜变量的关系完成建模，并通过数学推导以及实验的方式证明了该方法确实有效。
5.通过潜变量，使得G生成的数据具有一定的可解释性。


















在实现中，$D(x)、G(z, c) $和 $Q(x)$ 分别用一个 CNN (Convolutional Neural Networks)、DCNN (DeConv Neural Networks) 、CNN来实现。

同时，潜码 c 也包含两部分：一部分是类别，服从$ Cat(K = N,p = 1/N)$，其中 N 为类别数量；另一部分是连续的与生成数据有关的参数，服从$ Unif(−1,1)$ 的分布。

在此应指出，$Q(c|x) $可以表示为一个神经网络 Q(x) 的输出。对于输入随机变量 z 和类别潜码 c，实际的$ L_I(G, Q) $可以表示为：
![](https://upload-images.jianshu.io/upload_images/18339009-fce94af7b9cbaa22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中$ · $表示内积（inner product），$c $是一个选择计算哪个$ log $的参数，例如 $c_i = 1$ 而 $c_j = 0(∀j = 1,2,···,i − 1,i + 1,···,n)$，那么 z 这时候计算出的$ L_I(G,Q)$ 就等于$ log(Q(z,c)i)$。这里我们可以消去 $H(c)$，因为$ c $的分布是固定的，即优化目标与 $H(c) $无关：
![](https://upload-images.jianshu.io/upload_images/18339009-85f12073e5d23b30.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


而对于参数潜码，我们假设它符合正态分布，神经网络$ Q(x)$ 则输出其预测出的该潜码的均值和标准差， 我们知道，对于均值$ μ$，标准差$ σ $的随机变量，其概率密度函数为：
![](https://upload-images.jianshu.io/upload_images/18339009-fe235563b7fa0d8c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


要计算参数潜码 c 的![img](https://upload-images.jianshu.io/upload_images/18339009-6917fdf8a0f55091?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) ，就是要计算 $log p(c)$，即：
![](https://upload-images.jianshu.io/upload_images/18339009-98f8751d19aba6f2?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

设$ Q(x) $输出的参数潜码$ c$ 的均值$ μ$，标准差 $σ$ 分别为$ Q(x)μ$ 和 $Q(x)σ$，那么对于参数潜码$ c$：
![](https://upload-images.jianshu.io/upload_images/18339009-e62cda793a5275e3?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 
同样的，我们可以消去 $H(c)$，因为 $c $的分布是固定的，那么：
![](https://upload-images.jianshu.io/upload_images/18339009-73a42b5c861bbfc6?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

读完论文，我们发现，对于类别潜码，这个$ L_I $本质上是$ x$ 与$ G(z, c) $之间的 KL 散度：

![](https://upload-images.jianshu.io/upload_images/18339009-687934ec7ceb6fc5?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

也就是说：

![img](https://upload-images.jianshu.io/upload_images/18339009-c61296d5b3d4e2dc?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

而$ min DKL(c||Q(G(z, c)))$ 意味着减小$ c$ 与$ Q(G(z, c)) $的差别。

![](https://upload-images.jianshu.io/upload_images/18339009-6236068b4c7e8ac0?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

▲ 图7\. 普通GAN和InfoGAN的LI在训练过程中的比较

如果我们不考虑 $Q(x)σ $的影响，$L_I$ 的优化过程：
![](https://upload-images.jianshu.io/upload_images/18339009-7a9eab94cd5cdd22?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

而![img](https://upload-images.jianshu.io/upload_images/18339009-8d9b5fbeb6c73798?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 也意味着减小$ c $与 $Q(G(z, c))μ$ 的差。

再纵观整个模型，我们会发现这一对$ L_I $优化的过程，实质上是以 G 为编码器（Encoder）， Q 为解码器（Decoder），生成的图像作为我们要编码的码（code），训练一个自编码器（Autoencoder），也就是说，作者口中的信息论优化问题，本质上是无监督训练问题。
