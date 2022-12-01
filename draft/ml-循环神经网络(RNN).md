# RNN
神经网络只能单独的取处理一个个的输入，前一个输入和后一个输入是完全没有关系的。但是，某些任务需要能够更好的处理序列的信息，即前面的输入和后面的输入是有关系的。

基础的神经网络只在层与层之间建立了权连接，**RNN最大的不同之处就是在层之间的神经元之间也建立的权连接。**


![RNN时间维度权值共享，CNN空间维度权值共享](https://upload-images.jianshu.io/upload_images/18339009-e0bfd84cbf5b873f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![将输入截断成小片段输入RNN](https://upload-images.jianshu.io/upload_images/18339009-0866a2838f94c9c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![语言模型：用于由上文预测下文](https://upload-images.jianshu.io/upload_images/18339009-54085bad8a0926c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image Captioning CNN得到的向量V输入RNN](https://upload-images.jianshu.io/upload_images/18339009-59102f9ebe861276.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-79739fa6d2c113eb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![](https://upload-images.jianshu.io/upload_images/18339009-0d31094fca6c985b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-bcb943cd30cd17c4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-fb1429457073005b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# RNN存在的问题
>**梯度消失的问题**
RNN网络的**激活函数一般选用双曲正切**，而不是sigmod函数，（RNN的激活函数除了双曲正切，RELU函数也用的非常多）原因在于RNN网络在求解时涉及时间序列上的大量求导运算，使用sigmod函数容易出现梯度消失，且sigmod的导数形式较为复杂。事实上，即使使用双曲正切函数，传统的RNN网络依然存在梯度消失问题。
无论是梯度消失还是梯度爆炸，都是源于**网络结构太深，造成网络权重不稳定**，从本质上来讲是因为梯度反向传播中的连乘效应，类似于：$0.99^{100}=0.36$，于是梯度越来越小，开始消失，另一种极端情况就是$1.1^{100}=13780$。
**长期依赖的问题**还有一个问题是无法“记忆”长时间序列上的信息，这个bug直到LSTM上引入了单元状态后才算较好地解决
![](https://upload-images.jianshu.io/upload_images/18339009-bd0f9824aa206013.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# LSTM
下面来了解一下LSTM（long short-term memory）。长短期记忆网络是RNN的一种变体，**RNN由于梯度消失的原因只能有短期记忆，LSTM网络通过精妙的门控制将短期记忆与长期记忆结合起来，并且一定程度上解决了梯度消失的问题。**
![](https://upload-images.jianshu.io/upload_images/18339009-4477ce50e52769e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> $f_{t}=\sigma\left(W_{f} \cdot\left[h_{t-1}, x_{t}\right]+b_{f}\right)$
> $i_{t}=\sigma\left(W_{i} \cdot \left[h_{t-1}, x_{t}\right]+b_{i}\right)$
> $o_{t}=\sigma\left(W_{o}\left[h_{t-1}, x_{t}\right]+b_{o}\right)$
> $\tilde{C}_{t}=\tanh \left(W_{C} \cdot\left[h_{t-1}, x_{t}\right]+b_{C}\right)$
> $C_{t}=f_{t} ⊙C_{t-1}+i_{t} ⊙ \tilde{C}$ 
> $h_{t}=o_{t} ⊙ \tanh \left(C_{t}\right)$

**遗忘门：**主要控制**是否遗忘上一层的记忆细胞状态**， **输入分别是当前时间步序列数据，上一时间步的隐藏状态**，进行矩阵相乘，经**sigmoid激活后，获得一个值域在[0, 1]**的输出F，**再跟上一层记忆细胞进行对应元素相乘**，输出F中越接近0，代表需要遗忘上层记忆细胞的元素。
**候选记忆细胞：**这里的区别在于将sigmoid函数**换成tanh激活函数，因此输出的值域在[-1, 1]。**
**输入门：**与遗忘门类似，也是**经过sigmoid激活后，获得一个值域在[0, 1]的输出**。它用于**控制当前输入X经过候选记忆细胞如何流入当前时间步的记忆细胞**。 如果输入门输出接近为0，而遗忘门接近为1，则当前记忆细胞一直保存过去状态
**输出门：**也是通过**sigmoid激活，获得一个值域在[0,1]的输出**。主要**控制记忆细胞到下一时间步隐藏状态的信息流动**

>其中，四个蓝色的小矩形就是普通神经网络的隐藏层结构，其中第一、二和四的激活函数是sigmoid，第三个的激活函数是tanh。**t时刻的输入X和t-1时刻的输出h(t-1)进行拼接**，然后输入cell中，其实可以这样理解，我们的输入X(t)分别feed进了四个小蓝矩形中，每个小黄矩形中进行的运算和正常的神经网络的计算一样（矩阵乘法），有关记忆的部分完全由各种门结构来控制（就是0和1），同时在输入时不仅仅有原始的数据集，同时还加入了上一个数据的输出结果，也就是h(t-1)，那么讲来LSTM和正常的神经网络类似，只是在输入和输出上加入了一点东西。cell中可以大体分为两条横线，上面的横线用来控制长时记忆，下面的横线用来控制短时记忆。
![](https://upload-images.jianshu.io/upload_images/18339009-6c1db768e97d7731.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>![](https://upload-images.jianshu.io/upload_images/18339009-abb67948303b6369.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


不过LSTM**依旧不能解决梯度“爆炸”的问题**。但似乎梯度爆炸相对于梯度消失，问题没有那么严重。一般靠裁剪后的优化算法即可解决，比如gradient clipping（如果梯度的范数大于某个给定值，将梯度同比收缩）。
梯度剪裁的方法一般有两种：
1.一种是当梯度的某个维度绝对值大于某个上限的时候，就剪裁为上限。
2.另一种是梯度的L2范数大于上限后，让梯度除以范数，避免过大。


LSTM是一种拥有三个“门”的特殊网络结构，包括遗忘门、输入门、输出门。**所谓“门”结构就是一个使用sigmoid神经网络和一个按位做乘法的操作**，这两个操作合在一起就是一个“门”结构。



# LSTM与GRU的区别

LSTM与GRU二者结构十分相似，**不同在于**：

1. **新的记忆都是根据之前状态及输入进行计算**，但是**GRU中有一个重置门控制之前状态的进入量**，而在LSTM里没有类似门；
2. **产生新的状态方式不同**，LSTM有两个不同的门，分别是遗忘门(forget gate)和输入门(input gate)，而GRU只有一种更新门(update gate)；
3. **LSTM对新产生的状态可以通过输出门(output gate)进行调节**，而GRU对输出无任何调节。
4. **GRU的优点更加简单**，所以更容易创建一个更大的网络，而且它只有两个门，在计算性上也运行得更快，然后它可以扩大模型的规模。 
5. LSTM更加强大和灵活，因为它有三个门而不是两个。



# GRU结构
GRU是LSTM网络的一种效果很好的变体，它较LSTM网络的结构更加简单，而且效果也很好，因此也是当前非常流形的一种网络。GRU既然是LSTM的变体，因此也是可以解决RNN网络中的长依赖问题。
在LSTM中引入了三个门函数：输入门、遗忘门和输出门来控制输入值、记忆值和输出值。而在**GRU模型中只有两个门：分别是更新门和重置门**。具体结构如下图所示：

![](https://upload-images.jianshu.io/upload_images/18339009-1148da5d5fe3836f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![](https://gitee.com/kkweishe/images/raw/master/ML/2019-8-16_13-58-58.png)



> $$R_t=\sigma(X_tW_{xr}+H_{t-1}W_{hr}+b_r))$$
>
> $Z_t=\sigma(X_tW_{xz}+H_{t-1}W_{hz}+b_z))$
>
> $\tilde{H}_t=tanh(X_tW_{xh}+(R_t⊙H_{t-1})W_{hh}+b_h))$



**更新⻔可以控制隐藏状态应该如何被包含当前时间步信息的候选隐藏状态所更新，**

**重置⻔可以⽤来丢弃与预测⽆关的历史信息。**

我们对⻔控循环单元的设计稍作总结：

- 重置⻔有助于捕捉时间序列⾥短期的依赖关系；
- 更新⻔有助于捕捉时间序列⾥⻓期的依赖关系。


# BILSTM
如果能像访问过去的上下文信息一样，访问未来的上下文，这样对于许多序列标注任务是非常有益的。例如，**在最特殊字符分类的时候，如果能像知道这个字母之前的字母一样，知道将要来的字母，这将非常有帮助。**

双向循环神经网络（BRNN）的基本思想是提出**每一个训练序列向前和向后分别是两个循环神经网络（RNN），而且这两个都连接着一个输出层。**这个结构**提供给输出层输入序列中每一个点的完整的过去和未来的上下文信息**。下图展示的是一个沿着时间展开的双向循环神经网络。六个独特的权值在每一个时步被重复的利用，六个权值分别对应：输入到向前和向后隐含层（w1, w3），隐含层到隐含层自己（w2, w5），向前和向后隐含层到输出层（w4, w6）。**值得注意的是：向前和向后隐含层之间没有信息流，这保证了展开图是非循环的。**
>[tf.nn.rnn_cell.BasicLSTMCell()](https://tensorflow.google.cn/api_docs/python/tf/nn/rnn_cell/BasicLSTMCell?hl=en)来实现BILSTM
![image.png](https://upload-images.jianshu.io/upload_images/18339009-00f5836861f76cb3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 深层循环神经网络（DRNN）
> [tf.rnn_cell.MultiRNNCell(lstm * number_of_layer)](https://tensorflow.google.cn/api_docs/python/tf/nn/rnn_cell/MultiRNNCell?hl=en)来构建DRNN，其中number_of_layer表示了有多少层
 [tf.nn.rnn_cell.DropoutWrapper](https://tensorflow.google.cn/api_docs/python/tf/nn/rnn_cell/DropoutWrapper?hl=en)来实现dropout功能
![image.png](https://upload-images.jianshu.io/upload_images/18339009-ed0824fa3d0cc8ba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)









# tensorflow lstm预测正弦函数
```+python
import numpy as np
import tensorflow as tf
from tensorflow.contrib.learn.python.learn.estimators.estimator import SKCompat
from tensorflow.python.ops import array_ops as array_ops_
import matplotlib.pyplot as plt
learn = tf.contrib.learn

#### 1. 设置神经网络的参数。

HIDDEN_SIZE = 30
NUM_LAYERS = 2

TIMESTEPS = 10
TRAINING_STEPS = 3000
BATCH_SIZE = 32

TRAINING_EXAMPLES = 10000
TESTING_EXAMPLES = 1000
SAMPLE_GAP = 0.01

#### 2. 定义生成正弦数据的函数。

def generate_data(seq):
    X = []
    y = []

    for i in range(len(seq) - TIMESTEPS - 1):
        X.append([seq[i: i + TIMESTEPS]])
        y.append([seq[i + TIMESTEPS]])
    return np.array(X, dtype=np.float32), np.array(y, dtype=np.float32)

#### 3. 定义lstm模型。

def lstm_model(X, y):/;;;p
    lstm_cell = tf.nn.rnn_cell.BasicLSTMCell(HIDDEN_SIZE)
    cell = tf.nn.rnn_cell.MultiRNNCell([lstm_cell] * NUM_LAYERS)
    x_ = tf.unpack(X, axis=1)

    output, _ = tf.nn.rnn(cell, x_, dtype=tf.float32)
    output = output[-1]
    
    # 通过无激活函数的全联接层计算线性回归，并将数据压缩成一维数组的结构。
    predictions = tf.contrib.layers.fully_connected(output, 1, None)
    predictions = array_ops_.squeeze(predictions, squeeze_dims=[1])
    loss = tf.contrib.losses.mean_squared_error(predictions, y)
    
    train_op = tf.contrib.layers.optimize_loss(
        loss, tf.contrib.framework.get_global_step(),
        optimizer="Adagrad", learning_rate=0.1)

    return predictions, loss, train_op

#### 4. 进行训练。

# 封装之前定义的lstm。
regressor = SKCompat(learn.Estimator(model_fn=lstm_model,model_dir="Models/model_2"))

# 生成数据。
test_start = TRAINING_EXAMPLES * SAMPLE_GAP
test_end = (TRAINING_EXAMPLES + TESTING_EXAMPLES) * SAMPLE_GAP
train_X, train_y = generate_data(np.sin(np.linspace(
    0, test_start, TRAINING_EXAMPLES, dtype=np.float32)))
test_X, test_y = generate_data(np.sin(np.linspace(
    test_start, test_end, TESTING_EXAMPLES, dtype=np.float32)))

# 拟合数据。
regressor.fit(train_X, train_y, batch_size=BATCH_SIZE, steps=TRAINING_STEPS)

# 计算预测值。
predicted = [[pred] for pred in regressor.predict(test_X)]

# 计算MSE。
rmse = np.sqrt(((predicted - test_y) ** 2).mean(axis=0))
print ("Mean Square Error is: %f" % rmse[0])

#### 5. 画出预测值和真实值的曲线。

plot_predicted, = plt.plot(predicted, label='predicted')
plot_test, = plt.plot(test_y, label='real_sin')
plt.legend([plot_predicted, plot_test],['predicted', 'real_sin'])
plt.show()
```
