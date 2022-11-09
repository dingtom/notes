



BERT的全称是: Bidirectional Encoder Representations from Transformers, 如果翻译过来也就是**双向transformer编码表达**, 双向是什么意思呢? 
![](https://upload-images.jianshu.io/upload_images/18339009-d494ae2ffab85cb8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
上图中$E_i$是指的单个字或词, $T_i$指的是最终计算得出的**隐藏层**, 还记得我们在Transformer中讲到的注意力矩阵和注意力加权, 经过这样的操作之后, 序列里面的每一个字, **都含有这个字前面的信息和后面的信息**, 这就是**双向**的理解, 在这里, 一句话中每一个字, 经过注意力机制和加权之后, **当前这个字等于用这句话中其他所有字重新表达了一遍**, 每个字含有了这句话中所有成分的信息.

![Image](https://mmbiz.qpic.cn/mmbiz_jpg/7PuqRWWU6zO4ynwzyItMQBcv4Ph7uS1dRkiaYs5r5MGIgic2nmJLA9Y7qrIZjOP2aJ2licgrFogbapvVbkHHXktnw/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

箭头指示从一层到下一层的信息流。顶部的绿色框表示每个输入单词的最终上下文表示。

![quicker_b1057878-3443-4148-a283-d94bf174b11f.png](https://s2.loli.net/2022/03/26/FUKpz5Z6QJHw1gI.png)

每个输入的Embedding是3个嵌入的组合

**位置嵌入(Position Embeddings)**:BERT学习并使用位置嵌入来表达句子中单词的位置。这些是为了克服Transformer的限制而添加的，Transformer与RNN不同，它不能捕获“序列”或“顺序”信息

**段嵌入(Segment Embeddings)**:BERT还可以将句子对作为任务的输入(可用于问答)。这就是为什么它学习第一和第二句话的独特嵌入，以帮助模型区分它们。在上面的例子中，所有标记为EA的标记都属于句子A(对于EB也是一样)

**目标词嵌入(Token Embeddings)**:这些是从WordPiece词汇表中对特定词汇学习到的嵌入



# 语言模型
什么是语言模型, 其实用一个公式就可以表示$P(c_{1},\ldots ,c_{m})$, 假设我们有一句话, $c_{1}到c_{m}$是这句话里的$m$个字, 而语言模型就是求的是这句话出现的概率是多少.   

比如说在一个语音识别的场景, 机器听到一句话是"wo wang dai san le(我忘带伞了)", 然后机器解析出两个句子, 一个是"我网袋散了", 另一个是"我忘带伞了", 也就是前者的概率大于后者. 然后语言模型就可以判断$P("我忘带伞了") > P("我网袋散了")$, 从而得出这句语音的正确解析结果是"我忘带伞了".



- BERT Base:12层transformer，12个attention heads和1.1亿个参数
- BERT Large:24层transformer，16个attention heads和3.4亿个参数



## BERT语言模型任务一: MASKED LM屏蔽语言建模
在BERT中, Masked LM(Masked language Model)构建了语言模型, 这也是BERT的预训练中任务之一, 简单来说, 就是**随机遮盖或替换**一句话里面任意字或词, 然后让模型通过上下文的理解预测那一个被遮盖或替换的部分, 之后**做$Loss$的时候只计算被遮盖部分的$Loss$**, 其实是一个很容易理解的任务, 实际操作方式如下:   
1. 随机把一句话中$15 \% $的$token$替换成以下内容:   
1) 这些$token$有$80 \% $的几率被替换成$[mask]$;   
2) 有$10 \%$的几率被替换成任意一个其他的$token$;   
3) 有$10 \%$的几率原封不动.
2. 之后让模型**预测和还原**被遮盖掉或替换掉的部分, 模型最终输出的隐藏层的计算结果的维度是:   
$X_{hidden}: [batch\_size, \ seq\_len, \  embedding\_dim]$   
我们初始化一个映射层的权重$W_{vocab}$:   
$W_{vocab}: [embedding\_dim, \ vocab\_size]$   
我们用$W_{vocab}$完成隐藏维度到字向量数量的映射, 只要求$X_{hidden}$和$W_{vocab}$的矩阵乘(点积):   
$X_{hidden}W_{vocab}: [batch\_size, \ seq\_len, \ vocab\_size] $
之后把上面的计算结果在$vocab\_size$(最后一个)维度做$softmax$归一化, 使每个字对应的$vocab\_size$的和为$1$, 我们就可以通过$vocab\_size$里概率最大的字来得到模型的预测结果, 就可以和我们准备好的$Label$做损失($Loss$)并反传梯度了.   
注意做损失的时候, 只计算在第1步里当句中**随机遮盖或替换**的部分, 其余部分不做损失, 对于其他部分, 模型输出什么东西, 我们不在意.
## BERT语言模型任务二: Next Sentence Prediction下一句预测
1. 首先我们拿到属于上下文的一对句子, 也就是两个句子, 之后我们要在这两段连续的句子里面加一些特殊$token$:   
    $[cls]$上一句话,$[sep]$下一句话.$[sep]$   
    也就是在句子开头加一个$[cls]$, 在两句话之中和句末加$[sep]$, 具体地就像下图一样:   
    ![](https://upload-images.jianshu.io/upload_images/18339009-1cf614764e4a2871.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  

2. 我们看到上图中两句话是$[cls]$ my dog is cute $[sep]$ he likes playing $[sep]$, $[cls]$我的狗很可爱$[sep]$他喜欢玩耍$[sep]$, 除此之外, 我们**还要准备同样格式的两句话, 但他们不属于上下文关系的情况**;   
    $[cls]$我的狗很可爱$[sep]$企鹅不擅长飞行$[sep]$, 可见这属于上下句不属于上下文关系的情况;    
    在实际的训练中, 我们**让上面两种情况出现的比例为$1:1$, 也就是一半的时间输出的文本属于上下文关系, 一半时间不是.**

3. 我们进行完上述步骤之后, 还要随机初始化一个可训练的$segment \ embeddings$, 见上图中, 作用就是**用$embeddings$的信息让模型分开上下句**, 我们给上句全$0$的$token$, 下句全$1$的$token$, 让模型得以判断上下句的起止位置, 例如:   
    $[cls]$我的狗很可爱$[sep]$企鹅不擅长飞行$[sep]$   
    $0 \quad  \  0 \ \ 0 \ \  0 \ \ 0 \ \ 0 \ \ 0 \ \  0 \ \ \ 1 \ \  1 \ \ 1 \ \ 1 \ \ 1 \ \ 1 \ \ 1 \ \ 1 $    
    上面$0$和$1$就是$segment \ embeddings$.

4. 注意力机制就是, 让每句话中的每一个字对应的那一条向量里, 都融入这句话所有字的信息, 那么我们在最终隐藏层的计算结果里, 只要取出$[cls]token$所对应的一条向量, 里面就含有整个句子的信息, 因为我们期望这个句子里面所有信息都会往$[cls]token$所对应的一条向量里汇总:   
    模型最终输出的隐藏层的计算结果的维度是:   
    我们$X_{hidden}: [batch\_size, \ seq\_len, \  embedding\_dim]$   
    我们要取出$[cls]token$所对应的一条向量, $[cls]$对应着$\ seq\_len$维度的第$0$条:   
    $cls\_vector = X_{hidden}[:, \ 0, \ :]$   
    $cls\_vector \in \mathbb{R}^{batch\_size, \  embedding\_dim}$   
    之后我们再初始化一个权重, 完成从$embedding\_dim$维度到$1$的映射, 也就是逻辑回归, 之后用$sigmoid$函数激活, 就得到了而分类问题的推断.   
    我们用$\hat{y}$来表示模型的输出的推断, 他的值介于$(0, \ 1)$之间:   
    $\hat{y} = sigmoid(Linear(cls\_vector)) \quad \hat{y} \in (0, \ 1)$

至此$BERT$的训练方法就讲完了, 是不是很简单, 下面我们来为$BERT$的预训练准备数据.

# BERT的预训练, 训练参数
BERT论文中, 推荐的模型参数为: 基准模型$transformer\_block=12, \ embedding\_dimension=768$
$ \ num\_heads=12, \ Total Param eters=110M$
BERT训练技巧:   

1) 因为我们是按单个字为单位训练BERT, 所以在Masked LM里面, 把句子中的英文单词分出来, 将英文单词所在的区域一起遮盖掉, 让模型预测这个部分;    
2) 很多句子里含有数字, 显然在Masked LM中, 让模型准确地预测数据是不现实的, 所以我们把原文中的数字(包括整数和小数)都替换成一个特殊token, #NUM#, 这样模型只要预测出这个地方应该是某些数字就可以来.
# 使用BERT预训练模型进行自然语言的情感分类

![](https://upload-images.jianshu.io/upload_images/18339009-4f2065c06379b8e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如上图, 我尝试$mean \ max \ pool$的一种把隐藏层的序列转换为一条向量的方式, 其实就是沿着$sequence \ length$的维度分别求均值和$max$, 之后拼起来成为一条向量, 之后同样映射成一个值再激活, 伪代码如下:   
$X_{hidden}: [batch\_size, \ seq\_len, \  embedding\_dim]$   
$mean\_pooled = mean(X_{hidden}, \ dimension=seq\_len)  \quad [batch\_size, \  embedding\_dim]$
$max\_pooled = max(X_{hidden}, \ dimension=seq\_len)  \quad [batch\_size, \  embedding\_dim]$
$mean\_max\_pooled = concatenate(mean\_pooled, \ max\_pooled, \ dimension=embedding\_dim ) \quad [batch\_size, \  embedding\_dim * 2]$   
上式中$mean\_max\_pooled$也就是我们得到的一句话的数学表达, 含有这句话的信息, 其实这也是一种$DOC2VEC$的方法, 也就是把一句话转换成一条向量, 而且无论这句话有多长, 转换出来向量的维度都是一样的, 之后可以用这些向量做一些分类聚类等任务.   
下一步我们同样做映射, 之后用$sigmoid$激活:   
$\hat{y} = sigmoid(Linear(mean\_max\_pooled)) \quad \hat{y} \in (0, \ 1)$   
怎样理解这样的操作呢, 隐藏层就是一句话的数学表达, 我们求均值和最大值正数学表达对这句话的平均响应, 和最大响应, 之后我们用线性映射来识别这些响应, 从而得到模型的推断结果.  

我们还用了$weight  \ decay$的方式, 其实就是$L2 \ normalization$, 在PyTorch里有接口可以直接调用, 一会会说到, 其实$L2$正则的作用就是防止参数的值变得过大或过小, 我们可以设想一下, 由于我们的训练数据很少, 所以实际使用模型进行推断的时候有些字和词或者句子结构的组合模型都是没见过的, 模型里面参数的值很大的话会造成遇到某一些特别的句子或者词语的时候, 模型对句子的响应过大, 导致最终输出的值偏离实际, 其实我们希望模型更从容淡定一些, 所以我们加入$L2 \ normalization$.   

除此之外, 我们预训练的BERT有6个transformer block, 我们在情感分析的时候, 只用了3个, 因为后面实在是参数太多, 容易导致过拟合, 所以在第三个transformer block之后, 就截出隐藏层进行$pooling$了, 后面的transformer block都没有用到.   

再除此之外, 我使用了$dropout$机制, $dropout$设为了$0.4$, 因为模型参数是在是太多, 所以在训练的时候直接让$40\%$的参数失能, 防止过拟合.   

经过以上方法, 模型训练集和测试机的$AUC$都达到了$0.95$以上, 而且经过实际的测试, 模型也可以基本比较正确的分辨出语句的情感极性.   

5) **阈值微调:**   
经过模型的推断, 输出的值介于0到1之间, 我们可以认为只要这个值在0.5以上, 就是正样本, 如果在0.5以下, 就是副样本, 其实这是不一定的, 0.5通常不是最佳的分类边界, 所以我写了一个用来寻找最佳阈值的脚本, 在./metrics/\_\_init\_\_.py里面.   
这个脚本的方法是从0.01到0.99定义99个阈值, 高于阈值算正样本, 低于算副样本, 然后与测试集计算$f1 \ score$, 之后选出可以使$f1 \ score$最高的阈值, 在训练中, 每一个$epoch$都会运行一次寻找阈值的脚本.   
# 问题

**Bert为什么三个embedding可以相加？**

这是一个很有意思的问题，苏剑林老师给出的回答，真的很妙：

> **Embedding的数学本质，就是以one hot为输入的单层全连接。**
> **也就是说，世界上本没什么Embedding，有的只是one hot。**



**在这里想用一个简单的例子再尝试理解一下：**

假设 token Embedding 矩阵维度是 [4,768]；position Embedding 矩阵维度是 [3,768]；segment Embedding 矩阵维度是 [2,768]。

对于一个字，假设它的 token one-hot 是[1,0,0,0]；它的 position one-hot 是[1,0,0]；它的 segment one-hot 是[1,0]。

那这个字最后的 word Embedding，就是上面三种 Embedding 的加和。

如此得到的 word Embedding，和concat后的特征：[1,0,0,0,1,0,0,1,0]，再过维度为 [4+3+2,768] = [9, 768] 的全连接层，得到的向量其实就是一样的。



**再换一个角度理解：**

直接将三个one-hot 特征 concat 起来得到的 [1,0,0,0,1,0,0,1,0] 不再是one-hot了，但可以把它映射到三个one-hot 组成的特征空间，空间维度是 4\*3\*2=24 ，那在新的特征空间，这个字的one-hot就是[1,0,0,0,0...] (23个0)。

此时，Embedding 矩阵维度就是 [24,768]，最后得到的 word Embedding 依然是和上面的等效，**但是三个小 Embedding 矩阵的大小会远小于新特征空间对应的 Embedding 矩阵大小**。

当然，在相同初始化方法前提下，两种方式得到的 word Embedding 可能方差会有差别，但是，BERT还有Layer Norm，会把 Embedding 结果统一到相同的分布。

BERT的三个Embedding相加，本质可以看作一个**特征的融合**，强大如 BERT 应该可以学到融合后特征的语义信息的。





**为什么Bert中要用BPE这样的subword Token**

能很好的解决单词上的OOV，在语义粒度是比较合适的表达。

