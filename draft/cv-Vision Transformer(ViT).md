

下图是原论文中给出的关于Vision Transformer(ViT)的模型框架。简单而言，模型由三个模块组成：

Linear Projection of Flattened Patches(Embedding层)
Transformer Encoder(图右侧有给出更加详细的结构)
MLP Head（最终用于分类的层结构）

![1649037251470](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1649037251470.png)





![vit-b/16](https://img-blog.csdnimg.cn/20210704124600507.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3NTQxMDk3,size_16,color_FFFFFF,t_70#pic_center)

# Embedding层结构详解



对于标准的Transformer模块，要求输入的是token（向量）序列，即二维矩阵[num_token, token_dim]。对于图像数据而言，其数据格式为[H, W, C]是三维矩阵明显不是Transformer想要的。所以需要先通过一个Embedding层来对数据做个变换。

![1649037040722](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1649037040722.png)

如下图所示，首先将一张图片按给定大小分成一堆Patches。以ViT-B/16为例，将输入图片(224x224)按照16x16大小的Patch进行划分，划分后会得$(224/16)^2=196$个Patches。接着通过线性映射将每个Patch映射到一维向量中，以ViT-B/16为例，每个Patche数据shape为[16, 16, 3]通过映射得到一个长度为768的向量（后面都直接称为token）。[16, 16, 3] -> [768]

![1649037131840](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1649037131840.png)



**在代码实现中，直接通过一个卷积层来实现**。 以ViT-B/16为例，直接使用一个卷积核大小为16x16，步距为16，卷积核个数为768的卷积来实现。通过卷积[224, 224, 3] -> [14, 14, 768]，然后把H以及W两个维度展平即可[14, 14, 768] -> [196, 768]，此时正好变成了一个二维矩阵，正是Transformer想要的。**在输入Transformer Encoder之前注意需要加上[class]token以及Position Embedding**。 在原论文中，作者说参考BERT，在刚刚得到的一堆tokens中插入一个专门用于分类的[class]token，这个[class]token是一个可训练的参数，数据格式和其他token一样都是一个向量，以ViT-B/16为例，就是一个长度为768的向量，与之前从图片中生成的tokens拼接在一起，Cat([1, 768], [196, 768]) -> [197, 768]。然后关于Position Embedding就是之前Transformer中讲到的Positional Encoding，这里的Position Embedding采用的是一个可训练的参数（1D Pos. Emb.），是直接叠加在tokens上的（add），所以shape要一样。以ViT-B/16为例，刚刚拼接[class]token后shape是[197, 768]，那么这里的Position Embedding的shape也是[197, 768]。
![quicker_fdcbad9b-241c-4c80-9b78-c9bde9b1ec97.png](https://s2.loli.net/2022/04/06/3WKT7YVBwothkxG.png)





## MLP Head详解

上面通过Transformer Encoder后输出的shape和输入的shape是保持不变的，以ViT-B/16为例，输入的是[197, 768]输出的还是[197, 768]。



注意，在Transformer Encoder后其实还有一个Layer Norm没有画出来，后面有我自己画的ViT的模型可以看到详细结构。这里我们只是需要分类的信息，所以我们只需要提取出[class]token生成的对应结果就行，即[197, 768]中抽取出[class]token对应的[1, 768]。接着我们通过MLP Head得到我们最终的分类结果。MLP Head原论文中说在训练ImageNet21K时是由Linear+tanh激活函数+Linear组成。但是迁移到ImageNet1K上或者你自己的数据上时，只用一个Linear即可。




## Hybrid模型详解

在论文4.1章节的Model Variants中有比较详细的讲到Hybrid混合模型，就是**将传统CNN特征提取和Transformer进行结合**。

下图绘制的是以ResNet50作为特征提取器的混合模型，但这里的Resnet与之前讲的Resnet有些不同。首先这里的R50的卷积层采用的StdConv2d不是传统的Conv2d，然后将所有的BatchNorm层替换成GroupNorm层。在原Resnet50网络中，stage1重复堆叠3次，stage2重复堆叠4次，stage3重复堆叠6次，stage4重复堆叠3次，但在这里的R50中，把stage4中的3个Block移至stage3中，所以stage3中共重复堆叠9次。

通过R50 Backbone进行特征提取后，得到的特征矩阵shape是[14, 14, 1024]，接着再输入Patch Embedding层，注意Patch Embedding中卷积层Conv2d的kernel_size和stride都变成了1，只是用来调整channel。后面的部分和前面ViT中讲的完全一样，就不在赘述。



![quicker_567c3e3f-e010-41ee-87e9-9c30d0bcadc4.png](https://s2.loli.net/2022/04/06/XMYJBoktFP5ax9K.png)





下表是论文用来对比ViT，Resnet（和刚刚讲的一样，使用的卷积层和Norm层都进行了修改）以及Hybrid模型的效果。通过对比发现，**在训练epoch较少时Hybrid优于ViT，但当epoch增大后ViT优于Hybrid。**



![quicker_32df4176-e598-46a5-a966-c486c1d518ac.png](https://s2.loli.net/2022/04/06/Jq8rgvsjV7tULka.png)





# ViT模型搭建参数

在论文的Table1中有给出三个模型（Base/ Large/ Huge）的参数，在源码中除了有Patch Size为16x16的外还有32x32的。其中的Layers就是Transformer Encoder中重复堆叠Encoder Block的次数，Hidden Size就是对应通过Embedding层后每个token的dim（向量的长度），MLP size是Transformer Encoder中MLP Block第一个全连接的节点个数（是Hidden Size的四倍），Heads代表Transformer中Multi-Head Attention的heads数。



| Model     | Patch Size | Layers | Hidden Size D | MLP size | Heads | Params |
| --------- | ---------- | ------ | ------------- | -------- | ----- | ------ |
| ViT-Base  | 16x16      | 12     | 768           | 3072     | 12    | 86M    |
| ViT-Large | 16x16      | 24     | 1024          | 4096     | 16    | 307M   |
| ViT-Huge  | 14x14      | 32     | 1280          | 5120     | 16    | 632M   |



















