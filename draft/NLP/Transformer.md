transformer编码器(理论部分):

## transformer模型的直觉, 建立直观认识;
首先来说一下**transformer**和**LSTM**的最大区别, 就是LSTM的训练是迭代的, 是一个接一个字的来, 当前这个字过完LSTM单元, 才可以进下一个字, 而**transformer的训练是并行**, 就是所有字是全部同时训练的, 这样就大大加快了计算效率, **transformer使用了位置嵌入positional encoding来理解语言的顺序, 使用自注意力机制和全连接层来进行计算**



![quicker_0e9d0278-26e7-422f-a6f3-bf2b561d7ff9.png](https://s2.loli.net/2022/03/11/EWlJSZenk1zH5Ix.png)

transformer模型主要分为两大部分, 分别是编码器和解码器, 编码器负责把自然语言序列映射成为隐藏层(下图中第2步用九宫格比喻的部分), 含有自然语言序列的数学表达. 然后解码器把隐藏层再映射为自然语言序列, 从而使我们可以解决各种问题, 如情感分类, 命名实体识别, 语义关系抽取, 摘要生成, 机器翻译等等, 下面我们简单说一下下图的每一步都做了什么:   
1. 输入自然语言序列到编码器: Why do we work?(为什么要工作);

2. 编码器输出的隐藏层, 再输入到解码器;

3. 输入<start>(起始)符号到解码器;

4. 得到第一个字"为";

5. 将得到的第一个字"为"落下来再输入到解码器;

6. 得到第二个字"什";

7. 将得到的第二字再落下来, 直到解码器输出

8. <end>

9. (终止符), 即序列生成完成.
    ![](https://upload-images.jianshu.io/upload_images/18339009-b34e4ca63d74c65e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    
    
    其中编码器由N个相同的层堆叠在一起(我们后面的实验取N=6)，每一层又有两个子层。
    
    第一个子层是一个`Multi-Head Attention`(多头的自注意机制)，第二个子层是一个简单的`Feed Forward`(全连接前馈网络)。两个子层都`添加了一个残差连接+layer normalization的操作`。
    
    模型的解码器同样是堆叠了N个相同的层，不过和编码器中每层的结构稍有不同。对于解码器的每一层，除了编码器中的两个子层`Multi-Head Attention`和`Feed Forward`，解码器还包含一个子层`Masked Multi-Head Attention`，如图中所示每个子层同样也用了residual以及layer normalization。
    
    模型的输入由`Input Embedding`和`Positional Encoding`(位置编码)两部分组合而成，模型的输出由Decoder的输出简单的经过softmax得到。
    
    
    
    
    
    BERT预训练模型只用到了编码器的部分, 也就是先用编码器训练一个语言模型, 然后再把它适配给其他五花八门的任务
    ![](https://upload-images.jianshu.io/upload_images/18339009-b59e938bf16980b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





## 模型输入

输入部分包含两个模块，`Embedding` 和 `Positional Encoding`。

编码器和解码器两个部分都包含输入，且两部分的输入的结构是相同的，只是推理时的用法不同，**编码器只推理一次，而解码器是类似RNN那样循环推理，不断生成预测结果的。**

假设我们现在做的是一个法语-英语的机器翻译任务，想把`Je suis étudiant`翻译为`I am a student`。

那么我们输入给编码器的就是时间步数为3的embedding数组，编码器只进行一次并行推理，即获得了对于输入的法语句子所提取的若干特征信息。

而对于解码器，是循环推理，逐个单词生成结果的。最开始，由于什么都还没预测，我们会将编码器提取的特征，以及一个句子起始符传给解码器，解码器预期会输出一个单词`I`。然后有了预测的第一个单词，我们就将`I`输入给解码器，会再预测出下一个单词`am`，再然后我们将`I am`作为输入喂给解码器，以此类推直到预测出句子终止符完成预测。





### Embedding层

Embedding层的作用是将某种格式的输入数据，例如文本，转变为模型可以处理的向量表示，来描述原始数据所包含的信息。

`Embedding`层输出的可以理解为当前时间步的特征，如果是文本任务，这里就可以是`Word Embedding`，如果是其他任务，就可以是任何合理方法所提取的特征。



### positional encoding, 位置嵌入(或位置编码)
`Positional Encodding`位置编码的作用是为模型提供当前时间步的前后出现顺序的信息。因为Transformer不像RNN那样的循环结构有前后不同时间步输入间天然的先后顺序，所有的时间步是同时输入，并行推理的，因此在时间步的特征中融合进位置编码的信息是合理的。位置编码可以有很多选择，可以是固定的，也可以设置成可学习的参数。

Transformer原版的位置编码也就是正余弦函数编码，表达的是绝对位置信息，同时包含相对位置信息。但是经过线性变化，相对位置信息消失。基于此，需要对位置编码进行优化。

现在定义一个位置嵌入的概念, 也就是positional  encoding, 位置嵌入的维度为[max  sequence  length,  embedding  dimension]​, 嵌入的维度同词向量的维度, ​max sequence  length​属于超参数, 指的是限定的最大单个句长.   

注意, 我们一般**以字为单位训练transformer模型, 也就是说我们不用分词了**, 首先我们要初始化字向量为[vocab size,  embedding  dimension]$, $vocab  size$为总共的字库数量, $embedding  dimension​为字向量的维度, 也是每个字的数学表达.    

![quicker_9ab3aa7f-44d8-40e4-b208-4e514d3c4afc.png](https://s2.loli.net/2022/03/10/DuknCZl7JzOsR51.png)

向量$PE_{pos}$也就是第pos个时间步的位置编码，上式中pos指的是句中字的位置, 取值范围是[0, max sequence length), i指的是词向量的维度, 取值范围是[0, embedding  dimension)，可以看出，对于pos+k位置的位置向量某一维2i或2i+1而言，可以表示为，pos位置与k位置的位置向量的2i与2i+1维的线性组合，这样的线性组合意味着位置向量中蕴含了相对位置信息。(**但是这种相对位置信息会在注意力机制那里消失**)



上面有sin和cos​一组公式, 也就是对应着embedding dimension维度的一组奇数和偶数的序号的维度, 例如$0, 1$一组, $2, 3$一组, 分别用上面的sin和​cos函数做处理, 从而产生不同的周期性变化, 而位置嵌入在embedding dimension​维度上随着维度序号增大, 周期变化会越来越慢, 而产生一种包含位置信息的纹理, 就像论文原文中第六页讲的, 位置嵌入函数的周期从$2 \pi$到$10000 * 2 \pi$变化, 而每一个位置在embedding dimension​维度上都会得到不同周期的sin和​cos​函数的取值组合, 从而产生独一的纹理位置信息, 模型从而学到位置之间的依赖关系和自然语言的时序特性.   

## self attention mechanism

![](https://upload-images.jianshu.io/upload_images/18339009-784ce40612e805ef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![](https://upload-images.jianshu.io/upload_images/18339009-73f42d688096ea1e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### Attention Mask
注意, 在上面self  attention的计算过程中, 我们通常使用mini  batch来计算, 也就是一次计算多句话, 也就是X的维度是[batch  size,  sequence length], sequence length是句长, 而一个mini  batch是由多个不等长的句子组成的, 我们就需要按照这个mini batch中最大的句长对剩余的句子进行补齐长度, 我们一般用$0$来进行填充, 这个过程叫做padding. 

但这时在进行softmax的时候就会产生问题, 回顾softmax函数$\sigma ( {z} )_{i}={\frac {e^{z_{i}}}{\sum _{j=1}^{K}e^{z_{j}}}}$, $e^0$是1, 是有值的, 这样的话softmax中被padding的部分就参与了运算, 就等于是让无效的部分参与了运算, 会产生很大隐患, 这时就需要做一个$mask$让这些无效区域不参与运算, 我们一般给无效区域加一个很大的负数的偏置, 也就是:
$$z_{illegal} = z_{illegal} + bias_{illegal}$$
$$bias_{illegal} \to -\infty$$
$$e^{z_{illegal}} \to 0 $$
经过上式的masking我们使无效区域经过softmax计算之后还几乎为$0$, 这样就避免了无效区域参与计算.
![](https://upload-images.jianshu.io/upload_images/18339009-1d52bf916130ef4b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![quicker_c94fe78d-41e7-48f1-8651-df5c6761da66.png](https://s2.loli.net/2022/03/10/AaCZ5sLYSXnrmbM.png)

## Layer Normalization和残差连接

![quicker_c60259a2-b2ad-4708-8d7e-de70e40ebab1.png](https://s2.loli.net/2022/03/10/KZp1YElCIVkceoH.png)

### 残差连接:  

我们在上一步得到了经过注意力矩阵加权之后的$V$, 也就是$Attention(Q, \ K, \ V)$, 我们对它进行一下转置, 使其和$X_{embedding}$的维度一致, 也就是[batch size, sequence length, embedding dimension]​, 然后把他们加起来做残差连接, 直接进行元素相加, 因为他们的维度一致:   
$$X_{embedding} + Attention(Q, \ K, \ V)$$
在之后的运算里, 每经过一个模块的运算, 都要把运算之前的值和运算之后的值相加, 从而得到残差连接, 训练的时候可以使梯度直接走捷径反传到最初始层:
$$X + SubLayer(X) $$

### LayerNorm   

- Batch Normalization

  BN的主要思想就是：在每一层的每一批数据上进行归一化。我们可能会对输入数据进行归一化，但是经过该网络层的作用后，我们的数据已经不再是归一化的了。随着这种情况的发展，数据的偏差越来越大，反向传播需要考虑到这些大的偏差，这就迫使我们只能使用较小的学习率来防止梯度消失或者梯度爆炸。BN的具体做法就是对每一小批数据，在批这个方向上做归一化。

- Layer normalization

  它也是归一化数据的一种方式，在每一个样本上计算均值和方差，而不是BN那种在批方向计算均值和方差！

Layer Normalization的作用是把神经网络中隐藏层归一为标准正态分布, 也就是$i.i.d$独立同分布, 以起到加快训练速度, 加速收敛的作用:
以矩阵的行$(row)$为单位求均值;
$$\mu_{i}=\frac{1}{m} \sum^{m}_{i=1}x_{ij}$$
以矩阵的行$(row)$为单位求方差;
$$\sigma^{2}_{j}=\frac{1}{m} \sum^{m}_{i=1}
(x_{ij}-\mu_{j})^{2}$$

然后用**每一行**的**每一个元素**减去**这行的均值**, 再除以**这行的标准差**, 从而得到归一化后的数值, $\epsilon$是为了防止除$0$; 之后引入两个可训练参数$\alpha, \ \beta$来弥补归一化的过程中损失掉的信息, 注意$\odot$表示元素相乘而不是点积, 我们一般初始化$\alpha$为全$1$, 而$\beta$为全$0$.

$$LayerNorm(x)=\alpha \odot \frac{x_{ij}-\mu_{i}}
{\sqrt{\sigma^{2}_{i}+\epsilon}} + \beta $$



## decoder

encoder的输出需要注意的细节点在于它需要和decoder做交互，所以它的输出为K/V矩阵，记住这个
细节点，Q矩阵来自decoder模块，K/V矩阵来自encoder。



**mask 表示掩码，它对某些值进行掩盖，使其在参数更新时不产生效果。**Transformer 模型里面涉及两种 mask，分别是 padding mask 和 sequence mask。其中，padding mask 在所有的 scaled dot-product attention 里面都需要用到，而 sequence mask 只有在 decoder 的 self-attention 里面用到。

**padding mask**

什么是 padding mask 呢？因为每个批次输入序列长度是不一样的也就是说，我们要对输入序列进行对齐。具体来说，就是给在较短的序列后面填充 0。但是如果输入的序列太长，则是截取左边的内容，把多余的直接舍弃。因为这些填充的位置，其实是没什么意义的，所以我们的attention机制不应该把注意力放在这些位置上，所以我们需要进行一些处理。

具体的做法是，把这些位置的值加上一个非常大的负数(负无穷)，这样的话，经过 softmax，这些位置的概率就会接近0！

而我们的 padding mask 实际上是一个张量，每个值都是一个Boolean，值为 false 的地方就是我们要进行处理的地方。

**Sequence mask**

文章前面也提到，sequence mask 是为了使得 decoder 不能看见未来的信息。也就是对于一个序列，在 time_step 为 t 的时刻，我们的解码输出应该只能依赖于 t 时刻之前的输出，而不能依赖 t 之后的输出。因此我们需要想一个办法，把 t 之后的信息给隐藏起来。

那么具体怎么做呢？也很简单：产生一个上三角矩阵，上三角的值全为0。把这个矩阵作用在每一个序列上，就可以达到我们的目的。

- 对于 decoder 的 self-attention，里面使用到的 scaled dot-product attention，同时需要padding mask 和 sequence mask 作为 attn_mask，具体实现就是两个mask相加作为attn_mask。
- 其他情况，attn_mask 一律等于 padding mask。




## transformer encoder整体结构

经过上面3个步骤, 我们已经基本了解到来$transformer$编码器的主要构成部分, 我们下面用公式把一个$transformer \ block$的计算过程整理一下:    
1). 字向量与位置编码:   
$$X = EmbeddingLookup(X) + PositionalEncoding $$
$$X \in \mathbb{R}^{batch \ size  \ * \  seq. \ len. \  * \  embed. \ dim.} $$
2). 自注意力机制:   
$$Q = Linear(X) = XW_{Q}$$ 
$$K = Linear(X) = XW_{K} $$
$$V = Linear(X) = XW_{V}$$
$$X_{attention} = SelfAttention(Q, \ K, \ V) $$
3). 残差连接与$Layer \ Normalization$
$$X_{attention} = X + X_{attention} $$
$$X_{attention} = LayerNorm(X_{attention}) $$
4). 下面进行$transformer \ block$结构图中的**第4部分**, 也就是$FeedForward$, 其实就是两层线性映射并用激活函数激活, 比如说$ReLU$:   
$$X_{hidden} = Activate(Linear(Linear(X_{attention}))) $$
5). 重复3).:
$$X_{hidden} = X_{attention} + X_{hidden}$$
$$X_{hidden} = LayerNorm(X_{hidden})$$
$$X_{hidden} \in \mathbb{R}^{batch \ size  \ * \  seq. \ len. \  * \  embed. \ dim.} $$

## 小结 
我们到现在位置已经讲完了transformer的编码器的部分, 了解到了transformer是怎样获得自然语言的位置信息的, 注意力机制是怎样的, 其实举个语言情感分类的例子, 我们已经知道, **经过自注意力机制, 一句话中的每个字都含有这句话中其他所有字的信息**, 那么我们可不可以添加一个空白字符到句子最前面, 然后让句子中的所有信息向这个空白字符汇总, 然后再映射成想要分的类别呢? 这就是**BERT**, 我们下次会讲到.

在**BERT的预训练中, 我们给每句话的句头加一个特殊字符, 然后句末再加一个特殊字符, 之后模型预训练完毕之后, 我们就可以用句头的特殊字符的$hidden \ state$完成一些分类任务了.**



我们观察模型的结构图，Transformer模型包含哪些模块？笔者将其分为以下几个部分：

![Image](https://mmbiz.qpic.cn/sz_mmbiz_jpg/gYUsOT36vfrc46CPgjmcu492RZKlbMIeKcBYEayBkpjfx2gBAe81a1WicMMBLeicjH2KR46KzkUgOgZd37K9ZXfQ/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

接下来我们首先逐个讲解，最后将其拼接完成模型的复现。

## 2. config

下面是这个Demo所用的库文件以及一些超参的信息。**单独实现一个Config类保存的原因是，方便日后复用。直接将模型部分复制，所用超参保存在新项目的Config类中即可**。这里不过多赘述。

```
import torch
import torch.nn as nn
import numpy as np
import math


class Config(object):
    def __init__(self):
        self.vocab_size = 6

        self.d_model = 20
        self.n_heads = 2

        assert self.d_model % self.n_heads == 0
        dim_k  = d_model % n_heads
        dim_v = d_model % n_heads

        self.padding_size = 30
        self.UNK = 5
        self.PAD = 4

        self.N = 6
        self.p = 0.1

config = Config()
```

## 3. Embedding

Embedding部分接受原始的文本输入(batch_size*seq_len,例:[[1,3,10,5],[3,4,5],[5,3,1,1]])，叠加一个普通的Embedding层以及一个Positional Embedding层，输出最后结果。

![Image](https://mmbiz.qpic.cn/sz_mmbiz_jpg/gYUsOT36vfrc46CPgjmcu492RZKlbMIeRvv8HfxstEoLyON70u2GpEicmBTB1sdwxhSy35AYCHgeIEH32c1Sq7g/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

在这一层中，输入的是一个list: [batch_size * seq_len]，输出的是一个tensor:[batch_size * seq_len * d_model]

普通的 Embedding 层想说两点：

- 采用`torch.nn.Embedding`实现embedding操作。需要关注的一点是论文中提到的Mask机制，包括padding_mask以及sequence_mask(具体请见文章开头给出的理论讲解那篇文章)。在文本输入之前，我们需要进行padding统一长度，padding_mask的实现可以借助`torch.nn.Embedding`中的`padding_idx`参数。
- 在padding过程中，短补长截

```
class Embedding(nn.Module):
    def __init__(self,vocab_size):
        super(Embedding, self).__init__()
        # 一个普通的 embedding层，我们可以通过设置padding_idx=config.PAD 来实现论文中的 padding_mask
        self.embedding = nn.Embedding(vocab_size,config.d_model,padding_idx=config.PAD)


    def forward(self,x):
        # 根据每个句子的长度，进行padding，短补长截
        for i in range(len(x)):
            if len(x[i]) < config.padding_size:
                x[i].extend([config.UNK] * (config.padding_size - len(x[i]))) # 注意 UNK是你词表中用来表示oov的token索引，这里进行了简化，直接假设为6
            else:
                x[i] = x[i][:config.padding_size]
        x = self.embedding(torch.tensor(x)) # batch_size * seq_len * d_model
        return x
```

关于Positional Embedding，我们需要参考论文给出的公式。说一句题外话，在作者的实验中对比了Positional Embedding与单独采用一个Embedding训练模型对位置的感知两种方式，模型效果相差无几。

![Image](https://mmbiz.qpic.cn/sz_mmbiz_jpg/gYUsOT36vfrc46CPgjmcu492RZKlbMIe0MYRn97p9ffMJQhuOGqPmQteX8JmS68FMbZ0aW1WicKLRWmzniaxC1Ng/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

![Image](https://mmbiz.qpic.cn/sz_mmbiz_jpg/gYUsOT36vfrc46CPgjmcu492RZKlbMIe6Or5eHwicIvULxyKID6h02DXNYgsTTOic8SrjINQHUXwb7yCPnWwUqaA/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

```
class Positional_Encoding(nn.Module):

    def __init__(self,d_model):
        super(Positional_Encoding,self).__init__()
        self.d_model = d_model


    def forward(self,seq_len,embedding_dim):
        positional_encoding = np.zeros((seq_len,embedding_dim))
        for pos in range(positional_encoding.shape[0]):
            for i in range(positional_encoding.shape[1]):
                positional_encoding[pos][i] = math.sin(pos/(10000**(2*i/self.d_model))) if i % 2 == 0 else math.cos(pos/(10000**(2*i/self.d_model)))
        return torch.from_numpy(positional_encoding)
```

## 4. Encoder

![Image](https://mmbiz.qpic.cn/sz_mmbiz_jpg/gYUsOT36vfrc46CPgjmcu492RZKlbMIeg0ro35dM5XZ2lcD86piak3qumv08nxrXgvaaTyTOvWic4EWB0qYGoKzg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

### Muti_head_Attention

**就是说不仅仅只初始化一组Q、K、V的矩阵，而是初始化多组，tranformer是使用了8组，**所以最后得到的结果是8个矩阵。

Encoder 中的 Muti_head_Attention 不需要Mask，为了避免模型信息泄露的问题，Decoder 中的 Muti_head_Attention 需要Mask。这一节中我们重点讲解Muti_head_Attention中Mask机制的实现。

如果读者阅读了我们的上一篇文章，可以发现下面的代码有一点小小的不同，主要体现在 `forward` 函数的参数。

- `forward` 函数的参数从 x 变为 x,y：请读者观察模型架构，Decoder需要接受Encoder的输入作为公式中的`V`,即我们参数中的y。在普通的自注意力机制中，我们在调用中设置`y=x`即可。
- requires_mask：是否采用Mask机制，在Decoder中设置为True

```
class Mutihead_Attention(nn.Module):
    def __init__(self,d_model,dim_k,dim_v,n_heads):
        super(Mutihead_Attention, self).__init__()
        self.dim_v = dim_v
        self.dim_k = dim_k
        self.n_heads = n_heads

        self.q = nn.Linear(d_model,dim_k)
        self.k = nn.Linear(d_model,dim_k)
        self.v = nn.Linear(d_model,dim_v)

        self.o = nn.Linear(dim_v,d_model)
        self.norm_fact = 1 / math.sqrt(d_model)

    def generate_mask(self,dim):
        # 此处是 sequence mask ，防止 decoder窥视后面时间步的信息。
        # padding mask 在数据输入模型之前完成。
        matirx = np.ones((dim,dim))
        mask = torch.Tensor(np.tril(matirx))

        return mask==1

    def forward(self,x,y,requires_mask=False):
        assert self.dim_k % self.n_heads == 0 and self.dim_v % self.n_heads == 0
        # size of x : [batch_size * seq_len * batch_size]
        # 对 x 进行自注意力
        Q = self.q(x).reshape(-1,x.shape[0],x.shape[1],self.dim_k // self.n_heads) # n_heads * batch_size * seq_len * dim_k
        K = self.k(x).reshape(-1,x.shape[0],x.shape[1],self.dim_k // self.n_heads) # n_heads * batch_size * seq_len * dim_k
        V = self.v(y).reshape(-1,y.shape[0],y.shape[1],self.dim_v // self.n_heads) # n_heads * batch_size * seq_len * dim_v
        # print("Attention V shape : {}".format(V.shape))
        attention_score = torch.matmul(Q,K.permute(0,1,3,2)) * self.norm_fact
        if requires_mask:
            mask = self.generate_mask(x.shape[1])
            attention_score.masked_fill(mask,value=float("-inf")) # 注意这里的小Trick，不需要将Q,K,V 分别MASK,只MASKSoftmax之前的结果就好了
        output = torch.matmul(attention_score,V).reshape(y.shape[0],y.shape[1],-1)
        # print("Attention output shape : {}".format(output.shape))

        output = self.o(output)
        return output
```

### Feed Forward

![Image](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)img

这一部分实现很简单，两个Linear中连接Relu即可，目的是为模型增添非线性信息，提高模型的拟合能力。

```
class Feed_Forward(nn.Module):
    def __init__(self,input_dim,hidden_dim=2048):
        super(Feed_Forward, self).__init__()
        self.L1 = nn.Linear(input_dim,hidden_dim)
        self.L2 = nn.Linear(hidden_dim,input_dim)

    def forward(self,x):
        output = nn.ReLU()(self.L1(x))
        output = self.L2(output)
        return output
```

### Add & LayerNorm

这一节我们实现论文中提出的残差连接以及LayerNorm。

论文中关于这部分给出公式：

![Image](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)

代码中的dropout，在论文中也有所解释，对输入layer_norm的tensor进行dropout，对模型的性能影响还是蛮大的。

代码中的参数`sub_layer` ，可以是Feed Forward，也可以是Muti_head_Attention。

```
class Add_Norm(nn.Module):
    def __init__(self):
        self.dropout = nn.Dropout(config.p)
        super(Add_Norm, self).__init__()

    def forward(self,x,sub_layer,**kwargs):
        sub_output = sub_layer(x,**kwargs)
        # print("{} output : {}".format(sub_layer,sub_output.size()))
        x = self.dropout(x + sub_output)

        layer_norm = nn.LayerNorm(x.size()[1:])
        out = layer_norm(x)
        return out
```

OK，Encoder中所有模块我们已经讲解完毕，接下来我们将其拼接作为Encoder

```
class Encoder(nn.Module):
    def __init__(self):
        super(Encoder, self).__init__()
        self.positional_encoding = Positional_Encoding(config.d_model)
        self.muti_atten = Mutihead_Attention(config.d_model,config.dim_k,config.dim_v,config.n_heads)
        self.feed_forward = Feed_Forward(config.d_model)

        self.add_norm = Add_Norm()


    def forward(self,x): # batch_size * seq_len 并且 x 的类型不是tensor，是普通list

        x += self.positional_encoding(x.shape[1],config.d_model)
        # print("After positional_encoding: {}".format(x.size()))
        output = self.add_norm(x,self.muti_atten,y=x)
        output = self.add_norm(output,self.feed_forward)

        return output
```

## 5.Decoder

在 Encoder 部分的讲解中，我们已经实现了大部分Decoder的模块。Decoder的Muti_head_Attention引入了Mask机制，Decoder与Encoder 中模块的拼接方式不同。以上两点读者在Coding的时候需要注意。

```
class Decoder(nn.Module):
    def __init__(self):
        super(Decoder, self).__init__()
        self.positional_encoding = Positional_Encoding(config.d_model)
        self.muti_atten = Mutihead_Attention(config.d_model,config.dim_k,config.dim_v,config.n_heads)
        self.feed_forward = Feed_Forward(config.d_model)
        self.add_norm = Add_Norm()

    def forward(self,x,encoder_output): # batch_size * seq_len 并且 x 的类型不是tensor，是普通list
        # print(x.size())
        x += self.positional_encoding(x.shape[1],config.d_model)
        # print(x.size())
        # 第一个 sub_layer
        output = self.add_norm(x,self.muti_atten,y=x,requires_mask=True)
        # 第二个 sub_layer
        output = self.add_norm(output,self.muti_atten,y=encoder_output,requires_mask=True)
        # 第三个 sub_layer
        output = self.add_norm(output,self.feed_forward)


        return output
```

## 6.Transformer

至此，所有内容已经铺垫完毕，我们开始组装Transformer模型。论文中提到，Transformer中堆叠了6个我们上文中实现的Encoder 和 Decoder。这里笔者采用`nn.Sequential`实现了堆叠操作。

Output模块的 Linear 和 Softmax 的实现也包含在下面的代码中

```
class Transformer_layer(nn.Module):
    def __init__(self):
        super(Transformer_layer, self).__init__()
        self.encoder = Encoder()
        self.decoder = Decoder()

    def forward(self,x):
        x_input,x_output = x
        encoder_output = self.encoder(x_input)
        decoder_output = self.decoder(x_output,encoder_output)
        return (encoder_output,decoder_output)


class Transformer(nn.Module):
    def __init__(self,N,vocab_size,output_dim):
        super(Transformer, self).__init__()
        self.embedding_input = Embedding(vocab_size=vocab_size)
        self.embedding_output = Embedding(vocab_size=vocab_size)

        self.output_dim = output_dim
        self.linear = nn.Linear(config.d_model,output_dim)
        self.softmax = nn.Softmax(dim=-1)
        self.model = nn.Sequential(*[Transformer_layer() for _ in range(N)])


    def forward(self,x):
        x_input , x_output = x
        x_input = self.embedding_input(x_input)
        x_output = self.embedding_output(x_output)

        _ , output = self.model((x_input,x_output))

        output = self.linear(output)
        output = self.softmax(output)

        return output
```

# 代码

```python
"""
文件说明：
"""

import torch
import torch.nn as nn
import numpy as np
import math


class Config(object):
    def __init__(self):
        self.vocab_size = 6
        self.d_model = 20
        self.n_heads = 2
        assert self.d_model % self.n_heads == 0
        dim_k  = self.d_model // self.n_heads
        dim_v = self.d_model // self.n_heads
        self.padding_size = 30
        self.UNK = 5
        self.PAD = 4
        self.N = 6
        self.p = 0.1
config = Config()


class Embedding(nn.Module):
    def __init__(self,vocab_size):
        super(Embedding, self).__init__()
        # 一个普通的 embedding层，我们可以通过设置padding_idx=config.PAD 来实现论文中的 padding_mask
        self.embedding = nn.Embedding(vocab_size,config.d_model,padding_idx=config.PAD)
    def forward(self,x):
        # 根据每个句子的长度，进行padding，短补长截
        for i in range(len(x)):
            if len(x[i]) < config.padding_size:
                x[i].extend([config.UNK] * (config.padding_size - len(x[i]))) 
                # 注意 UNK是你词表中用来表示oov的token索引，这里进行了简化，直接假设为6
            else:
                x[i] = x[i][:config.padding_size]
        x = self.embedding(torch.tensor(x)) # batch_size * seq_len * d_model
        return x



class Positional_Encoding(nn.Module):

    def __init__(self,d_model):
        super(Positional_Encoding,self).__init__()
        self.d_model = d_model

    def forward(self,seq_len,embedding_dim):
        positional_encoding = np.zeros((seq_len,embedding_dim))
        for pos in range(positional_encoding.shape[0]):
            for i in range(positional_encoding.shape[1]):
                positional_encoding[pos][i] = math.sin(pos/(10000**(2*i/self.d_model))) if i % 2 == 0 else math.cos(pos/(10000**(2*i/self.d_model)))
        return torch.from_numpy(positional_encoding)


class Mutihead_Attention(nn.Module):
    def __init__(self,d_model,dim_k,dim_v,n_heads):
        super(Mutihead_Attention, self).__init__()
        self.dim_v = dim_v
        self.dim_k = dim_k
        self.n_heads = n_heads

        self.q = nn.Linear(d_model,dim_k)
        self.k = nn.Linear(d_model,dim_k)
        self.v = nn.Linear(d_model,dim_v)

        self.o = nn.Linear(dim_v,d_model)
        self.norm_fact = 1 / math.sqrt(d_model)

    def generate_mask(self,dim):
        # 此处是 sequence mask ，防止 decoder窥视后面时间步的信息。
        # padding mask 在数据输入模型之前完成。
        matirx = np.ones((dim,dim))
        mask = torch.Tensor(np.tril(matirx))

        return mask==1

    def forward(self,x,y,requires_mask=False):
        assert self.dim_k % self.n_heads == 0 and self.dim_v % self.n_heads == 0
        # size of x : [batch_size * seq_len * batch_size]
        # 对 x 进行自注意力
        Q = self.q(x).reshape(-1,x.shape[0],x.shape[1],self.dim_k // self.n_heads) # n_heads * batch_size * seq_len * dim_k
        K = self.k(x).reshape(-1,x.shape[0],x.shape[1],self.dim_k // self.n_heads) # n_heads * batch_size * seq_len * dim_k
        V = self.v(y).reshape(-1,y.shape[0],y.shape[1],self.dim_v // self.n_heads) # n_heads * batch_size * seq_len * dim_v
        # print("Attention V shape : {}".format(V.shape))
        attention_score = torch.matmul(Q,K.permute(0,1,3,2)) * self.norm_fact
        if requires_mask:
            mask = self.generate_mask(x.shape[1])
            # masked_fill 函数中，对Mask位置为True的部分进行Mask
            attention_score.masked_fill(mask,value=float("-inf")) # 注意这里的小Trick，不需要将Q,K,V 分别MASK,只MASKSoftmax之前的结果就好了
        output = torch.matmul(attention_score,V).reshape(y.shape[0],y.shape[1],-1)
        # print("Attention output shape : {}".format(output.shape))

        output = self.o(output)
        return output


class Feed_Forward(nn.Module):
    def __init__(self,input_dim,hidden_dim=2048):
        super(Feed_Forward, self).__init__()
        self.L1 = nn.Linear(input_dim,hidden_dim)
        self.L2 = nn.Linear(hidden_dim,input_dim)

    def forward(self,x):
        output = nn.ReLU()(self.L1(x))
        output = self.L2(output)
        return output

class Add_Norm(nn.Module):
    def __init__(self):
        self.dropout = nn.Dropout(config.p)
        super(Add_Norm, self).__init__()

    def forward(self,x,sub_layer,**kwargs):
        sub_output = sub_layer(x,**kwargs)
        # print("{} output : {}".format(sub_layer,sub_output.size()))
        x = self.dropout(x + sub_output)

        layer_norm = nn.LayerNorm(x.size()[1:])
        out = layer_norm(x)
        return out


class Encoder(nn.Module):
    def __init__(self):
        super(Encoder, self).__init__()
        self.positional_encoding = Positional_Encoding(config.d_model)
        self.muti_atten = Mutihead_Attention(config.d_model,config.dim_k,config.dim_v,config.n_heads)
        self.feed_forward = Feed_Forward(config.d_model)

        self.add_norm = Add_Norm()


    def forward(self,x): # batch_size * seq_len 并且 x 的类型不是tensor，是普通list

        x += self.positional_encoding(x.shape[1],config.d_model)
        # print("After positional_encoding: {}".format(x.size()))
        output = self.add_norm(x,self.muti_atten,y=x)
        output = self.add_norm(output,self.feed_forward)

        return output

# 在 Decoder 中，Encoder的输出作为Query和KEy输出的那个东西。即 Decoder的Input作为V。此时是可行的
# 因为在输入过程中，我们有一个padding操作，将Inputs和Outputs的seq_len这个维度都拉成一样的了
# 我们知道，QK那个过程得到的结果是 batch_size * seq_len * seq_len .既然 seq_len 一样，那么我们可以这样操作
# 这样操作的意义是，Outputs 中的 token 分别对于 Inputs 中的每个token作注意力

class Decoder(nn.Module):
    def __init__(self):
        super(Decoder, self).__init__()
        self.positional_encoding = Positional_Encoding(config.d_model)
        self.muti_atten = Mutihead_Attention(config.d_model,config.dim_k,config.dim_v,config.n_heads)
        self.feed_forward = Feed_Forward(config.d_model)
        self.add_norm = Add_Norm()

    def forward(self,x,encoder_output): # batch_size * seq_len 并且 x 的类型不是tensor，是普通list
        # print(x.size())
        x += self.positional_encoding(x.shape[1],config.d_model)
        # print(x.size())
        # 第一个 sub_layer
        output = self.add_norm(x,self.muti_atten,y=x,requires_mask=True)
        # 第二个 sub_layer
        output = self.add_norm(x,self.muti_atten,y=encoder_output,requires_mask=True)
        # 第三个 sub_layer
        output = self.add_norm(output,self.feed_forward)
        return output

class Transformer_layer(nn.Module):
    def __init__(self):
        super(Transformer_layer, self).__init__()
        self.encoder = Encoder()
        self.decoder = Decoder()

    def forward(self,x):
        x_input,x_output = x
        encoder_output = self.encoder(x_input)
        decoder_output = self.decoder(x_output,encoder_output)
        return (encoder_output,decoder_output)

class Transformer(nn.Module):
    def __init__(self,N,vocab_size,output_dim):
        super(Transformer, self).__init__()
        self.embedding_input = Embedding(vocab_size=vocab_size)
        self.embedding_output = Embedding(vocab_size=vocab_size)

        self.output_dim = output_dim
        self.linear = nn.Linear(config.d_model,output_dim)
        self.softmax = nn.Softmax(dim=-1)
        self.model = nn.Sequential(*[Transformer_layer() for _ in range(N)])


    def forward(self,x):
        x_input , x_output = x
        x_input = self.embedding_input(x_input)
        x_output = self.embedding_output(x_output)

        _ , output = self.model((x_input,x_output))

        output = self.linear(output)
        output = self.softmax(output)

        return output
```



# 问题



## 为什么上面那个公式要对QK进行scaled

让softmax输入的数据分布变好，数值进入梯度敏感区间，能防止梯度消失，让模型好训练。



## self-attention一定要这样表达吗？

不需要，能刻画相关性，相似性等建模方式都可以。最好速度快，模型好学，表达能力够。



## 有其他方法不用除根号$d_k$吗？

有，同上，只要能做到每层参数的梯度保持在训练敏感的范围内，不要太大，不要太小。那么这个网络就比较好训练。方式有，比较好的初始化方法，类似于google的T5模型，就在初始化把这个事情干了。

![quicker_b088c34f-878b-4801-8cc5-a6ababc0d2a8.png](https://s2.loli.net/2022/03/31/ArjfHzP7aebCGX8.png)

**在数量级较大时，softmax将几乎全部的概率分布都分配给了最大值对应的标签**。

假设向量  q和 k 的各个分量是互相独立的随机变量，均值是0，方差是1，那么点积  的均值是0，方差是$d_k$  。**方差越大也就说明，点积的数量级越大（以越大的概率取大值）**。那么一个自然的做法就是把方差稳定到1，做法是将点积除以 根号$d_k$



## 为什么transformer用Layer Norm有什么用？

让神经网络各层参数输入的数据分布变好，数值进入梯度敏感区间，能防止梯度消失，让模型好训练。



## 为什么不用BN？

两个，NLP不定长，好多位置填0，影响其他样本非0参数的计算。

Transformer的模型比较大，BS拉不大，容易变得不稳定



## Bert为什么要搞一个position embedding？

增强表达能力（位置上的）。因为transformer对位置不敏感，需要显示标示



## transformer为什么要用三个不一样的QKV？

增强网络的容量和表达能力。



## 为什么要多头？

增强网络的容量和表达能力。

将模型分为多个头，**形成多个子空间，可以让模型去关注不同方面的信息**，最后再将各个方面的信息综合起来。其实直观上也可以想到，如果自己设计这样的一个模型，必然也不会只做一次attention，多次attention综合的结果至少能够起到增强模型的作用，也可以类比CNN中同时使用多个卷积核的作用，直观上讲，多头的注意力**有助于网络捕捉到更丰富的特征/信息。**

## Transformer相比于RNN/LSTM，有什么优势？为什么？

1.RNN系列的模型，**并行计算能力很差**。RNN并行计算的问题就出在这里，因为 T 时刻的计算依赖 T-1 时刻的隐层计算结果，而 T-1 时刻的计算依赖 T-2 时刻的隐层计算结果，如此下去就形成了所谓的序列依赖关系。

2.Transformer的**特征抽取能力**比RNN系列的模型要好。

但是值得注意的是，并不是说Transformer就能够完全替代RNN系列的模型了，任何模型都有其适用范围，同样的，RNN系列模型在很多任务上还是首选，熟悉各种模型的内部原理，知其然且知其所以然，才能遇到新任务时，快速分析这时候该用什么样的模型，该怎么做好。

## 为什么说Transformer可以代替seq2seq？

**seq2seq缺点：**这里用代替这个词略显不妥当，seq2seq虽已老，但始终还是有其用武之地，seq2seq最大的问题在于**将Encoder端的所有信息压缩到一个固定长度的向量中**，并将其作为Decoder端首个隐藏状态的输入，来预测Decoder端第一个单词(token)的隐藏状态。在输入序列比较长的时候，这样做显然会损失Encoder端的很多信息，而且这样一股脑的把该固定向量送入Decoder端，Decoder端不能够关注到其想要关注的信息。

**Transformer优点：**transformer不但对seq2seq模型这两点缺点有了实质性的改进(多头交互式attention模块)，而且还引入了self-attention模块，让源序列和目标序列首先“自关联”起来，这样的话，源序列和目标序列自身的embedding表示所蕴含的信息更加丰富，而且后续的FFN层也增强了模型的表达能力，并且Transformer并行计算的能力是远远超过seq2seq系列的模型，因此我认为这是transformer优于seq2seq模型的地方。



## 计算attention score的时候如何对padding做mask操作？

padding位置置为负无穷(一般来说-1000就可以)。