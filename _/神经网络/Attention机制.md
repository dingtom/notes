- [Attention ](#head1)
	- [ 通用形式](#head2)
	- [ 分类](#head3)
		- [ 关注范围](#head4)
			- [ 全局注意力](#head5)
			- [ 局部注意力](#head6)
			- [ 硬注意力](#head7)
			- [ 稀疏注意力](#head8)
			- [ 结构注意力](#head9)
		- [ 组合方式](#head10)
			- [ 层级注意力](#head11)
			- [ 双向注意力](#head12)
			- [ 多头注意力](#head13)
	- [ 自注意力](#head14)
		- [ 自注意力机制](#head15)
- [ 总结](#head16)

https://github.com/CyberZHG/keras-self-attention/blob/master/README.zh-CN.md
https://github.com/CyberZHG/keras-self-attention/blob/master/keras_self_attention/seq_self_attention.py

用Attention机制的原因是考虑到RNN（或者LSTM，GRU等）的计算限制为是顺序的，也就是说RNN相关算法只能从左向右依次计算或者从右向左依次计算，这种机制带来了两个问题：

1.  时间片 *t* 的计算依赖 *t-1* 时刻的计算结果，这样**限制了模型的并行能力；**
2.  顺序计算的过程中信息会丢失，尽管LSTM等门机制的结构一定程度上缓解了长期依赖的问题，**但是对于特别长期的依赖现象,LSTM依旧无能为力。**









# <span id="head1">Attention </span>
![](https://upload-images.jianshu.io/upload_images/18339009-a4e0fedeb414aca9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## <span id="head2"> 通用形式</span>
![](https://upload-images.jianshu.io/upload_images/18339009-ede3ccdaa82f4810.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果将下游任务抽象成查询（query），就可以归纳出注意力机制的通用形式，即将源文本看成是键-值<Key,Value>对序列。给定Target中的某个元素Query（解码器隐层向量 $s_{t-1}$ ）用$K=（k_1，…，k_N）$和$V=（v_1，…，v_N）$分别表示键序列和值序列，用 $Q=（q_1，…，q_M）$表示查询序列，那么针对查询$q_t$的注意力可以被描述为键-值对序列在该查询上的映射。如图2所示，计算过程可分为三步：
- 计算查询 $q_t$和每个键 $k_i$的注意力得分 $e_{ti}$，常用的计算方法包括点积、缩放点积、拼接以及相加等，如公式（1）所示；
- 使用 Softmax 等函数对注意力得分做归一化处理，得到每个键的权重$ α_{ti}$，如公式（2）所示；
- 将权重$ α_{ti}$和其对应的值$ v_i$加权求和作为注意力输出，如公式（3）所示模型输出的注意力是源文本序列基于查询 $q_t$的表示，不同的查询会给源文本序列带来不同的权重分布。
![](https://upload-images.jianshu.io/upload_images/18339009-abd39baf986400fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-d792b865a3618a94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head3"> 分类</span>

### <span id="head4"> 关注范围</span>
|注意力 |     关注范围|
|:-:|:-:|
|  全局注意力 |    全部元素|  
|  局部注意力  |   以对齐位置为中心的窗口|  
|  硬注意力    |  一个元素|  
|  稀疏注意力 |   稀疏分布的部分元素|  
|  结构注意力  |   结构上相关的一系列元素|  


#### <span id="head5"> 全局注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-f793257ac4ad0311.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### <span id="head6"> 局部注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-57ffee086fdafc02.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### <span id="head7"> 硬注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-e02f1c94e9657b3e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### <span id="head8"> 稀疏注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-b12de5f47f732559.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### <span id="head9"> 结构注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-2bbd796c8f791d15.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### <span id="head10"> 组合方式</span>
#### <span id="head11"> 层级注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-e6dbd47038d548d6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-e7e9381a65fcbd6e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### <span id="head12"> 双向注意力</span>
![](https://upload-images.jianshu.io/upload_images/18339009-9251c37e5ec1c6e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-faa1bea4b8d72ff1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-79c720997d172703.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### <span id="head13"> 多头注意力</span>

![](https://upload-images.jianshu.io/upload_images/18339009-545a2735c5bd6fdb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-ea638c381cf29b8c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head14"> 自注意力</span>
### <span id="head15"> 自注意力机制</span>
![](https://upload-images.jianshu.io/upload_images/18339009-978848a3ce30e4ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-0f88d6ccaecf7d1c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-050f12f047a4174a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-cd967218e97f2fe4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-4a6bdd3849b846e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



在self-attention中，每个单词有3个不同的向量，它们分别是Query向量 $Q$，Key向量$K$和Value向量$V$。它们是通过3个不同的权值矩阵由嵌入向量$X$乘以三个不同的权值矩阵$W_Q,W_K,W_V$  得到，其中三个矩阵的尺寸也是相同的。

**Attention的计算方法，整个过程可以分成7步：**
1. 将输入单词转化成嵌入向量；
2.  根据嵌入向量得到 三个向量$Q,K,V$ ；
3.  为每个向量计算一个attention score：$Q*K$；
4.  为了梯度的稳定，Transformer使用了score归一化，即除以 $\sqrt{d_k}$  ；
5.  对score施以softmax激活函数；
6.  softmax点乘Value值$V$ ，得到加权的每个输入向量的评分 weighted values $V$ ；
7.  相加之后得到最终的输出结果$Z=\sum(v)$。
```
class AttentionLayer(Layer):
 """
        # Input shape  3D tensor with shape: `(samples, steps, features)`.
        # Output shape 3D tensor with shape: `(samples, steps, output_dim)`.
"""
    def __init__(self, output_dim, **kwargs):
        self.output_dim = output_dim
        super(AttentionLayer, self).__init__(**kwargs)
    def build(self, input_shape):
        # 为该层创建一个可训练的权重
        #inputs.shape = (batch_size, time_steps, seq_len)
        self.kernel = self.add_weight(name='kernel', shape=(3, input_shape[2], self.output_dim), initializer='uniform', trainable=True)
        super(AttentionLayer, self).build(input_shape)  # 一定要在最后调用它
    def call(self, x):
        WQ = K.dot(x, self.kernel[0])  # (None, input_shape[1]，input_shape[2])   (input_shape[2], output_dim)  (None, input_shape[1]，output_dim)
        WK = K.dot(x, self.kernel[1])  # (None, input_shape[1]，output_dim)
        WV = K.dot(x, self.kernel[2])  # (None, input_shape[1]，output_dim)
        score = K.batch_dot(WQ, K.permute_dimensions(WK, [0, 2, 1])) / (input_shape[0]**0.5)  #  (None, input_shape[1], input_shape[1])
        alpha = K.softmax(score)  
        V = K.batch_dot(alpha, WV)  # (None, input_shape[1], input_shape[1])  (None, input_shape[1]，output_dim) (None, input_shape[1]，output_dim)
        return V
```


# <span id="head16"> 总结</span>
优点：（1）虽然Transformer最终也没有逃脱传统学习的套路，Transformer也只是一个全连接（或者是一维卷积）加Attention的结合体。但是其设计已经足够有创新，因为其抛弃了在NLP中最根本的RNN或者CNN并且取得了非常不错的效果，算法的设计非常精彩，值得每个深度学习的相关人员仔细研究和品位。（2）Transformer的设计最大的带来性能提升的**关键是将任意两个单词的距离是1，**这对解决NLP中棘手的长期依赖问题是非常有效的。（3）Transformer不仅仅可以应用在NLP的机器翻译领域，甚至可以不局限于NLP领域，是非常有科研潜力的一个方向。（4）算法的并行性非常好，符合目前的硬件（主要指GPU）环境。

缺点：（1）粗暴的抛弃RNN和CNN虽然非常炫技，但是它也使模型**丧失了捕捉局部特征的能力**，RNN + CNN + Transformer的结合可能会带来更好的效果。（2）**Transformer失去的位置信息其实在NLP中非常重要**，而论文中在特征向量中加入Position Embedding也只是一个权宜之计，并没有改变Transformer结构上的固有缺陷。+


给定一个在每个时间步产生隐藏状态$h_t$的模型，基于注意的模型计算一个“上下文”向量$c_t$作为状态序列h的加权平均值

$c_{t}=\sum_{j=1}^{T} \alpha_{t j} h_{j}$


式中，$T$是输入序列中的时间步总数，$α_{tj}$是针对每个状态$h_j$在每个时间步$t$处计算的权重。然后使用这些上下文向量来计算新的状态序列$s$，其中$s_t$依赖于$s_{t−1}$、$c_t$和$t−1$处的模型输出。然后通过以下公式计算权重$α_{tj}$：
$e_{t j}=a\left(s_{t-1}, h_{j}\right), \alpha_{t j}=\frac{\exp \left(e_{t j}\right)}{\sum_{k=1}^{T} \exp \left(e_{t k}\right)}$

其中，$a$是一个学习函数，可以认为是给定$h_j$值和先前状态$s_{t−1}$计算$h_j$的标量重要性值。该公式允许新的状态序列$s$更直接地访问整个状态序列$h$。

![](https://upload-images.jianshu.io/upload_images/18339009-f1ef9cc0c7443b7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
import numpy
import keras
import tensorflow as tf
from keras import backend as K
from keras import activations
from keras.engine.topology import Layer
from keras.preprocessing import sequence
from keras.models import Sequential
from keras.models import Model
from keras.layers import Input, Dense, Embedding, LSTM, Bidirectional
K.clear_session()
tf.compat.v1.disable_eager_execution()

class AttentionLayer(Layer):
    """
    # Input shape  3D tensor with shape: `(samples, steps, hidden_size)`.
    # Output shape 2D tensor with shape: `(samples, hidden_size)`.
    """
    def __init__(self, attention_size=None, **kwargs):
        self.attention_size = attention_size
        super(AttentionLayer, self).__init__(**kwargs)
        
    def get_config(self):
        config = super().get_config()
        config['attention_size'] = self.attention_size
        return config
        
    def build(self, input_shape):
        assert len(input_shape) == 3
        
        self.time_steps = input_shape[1]
        hidden_size = input_shape[2]
        if self.attention_size is None:
            self.attention_size = hidden_size
            
        self.W = self.add_weight(name='att_weight', shape=(hidden_size, self.attention_size), initializer='uniform', trainable=True)
        self.b = self.add_weight(name='att_bias', shape=(self.attention_size,), initializer='uniform', trainable=True)
        self.V = self.add_weight(name='att_var', shape=(self.attention_size,), initializer='uniform', trainable=True)
        super(AttentionLayer, self).build(input_shape)
    
    def call(self, inputs):
        self.V = K.reshape(self.V, (-1, 1))   # (attention_size，1)
        score =   K.dot(K.tanh(K.dot(inputs, self.W) + self.b), self.V) #  (None, 30, hidden_size)   (hidden_size, attention_size)   (None, 30, attention_size)
        alpha = K.softmax(score, axis=1)                                #   //       (None, 30, attention_size) (attention_size，1) (None, 30, 1)
        outputs = K.sum(alpha * inputs, axis=1)   #   (None, 30, 1)   (None, 30, hidden_size)        (None, hidden_size)
        return outputs
    def compute_output_shape(self, input_shape):
        return input_shape[0], input_shape[2]
```

