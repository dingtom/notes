![](https://upload-images.jianshu.io/upload_images/18339009-4c00b0451280e2f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-3adce96cd54eb3f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 卷积层
**卷积提取底层特征减少神经网络中参数个数**

![](https://upload-images.jianshu.io/upload_images/18339009-9ff2bf3400efde22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![左侧小矩阵的尺寸为过滤器的尺寸，而右侧单位矩阵的深度为过滤器的深度
](https://upload-images.jianshu.io/upload_images/18339009-83bf8425d394ccdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-bdf823c5a2c28959.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 为了避免尺寸的变化可以在当前层的矩阵的边界加入全０填充（zero-padding）．否则中间的像素会多次进入卷积野而边上的进入次数少
- 还可以通过设置过滤器移动步长调整结果矩阵的大小

# 池化层
**防止过拟合、下采样、降维、去除冗余信息、对特征进行压缩、简化网络复杂度、减小计算量、减小内存消耗**
**实现非线性，扩大感知野**
**实现不变性，（平移不变性、旋转不变性和尺度不变性）**

![池化函数使用某一位置的相邻输出的总体统计特征来代替网络在该位置的输出。](https://upload-images.jianshu.io/upload_images/18339009-8b04d457c6018029.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




**加速卷积运算**
im2col
![](https://upload-images.jianshu.io/upload_images/18339009-6e6de559a8775ac5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-2eec3ec53bdba4a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-5924a4c0dbe607ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
FFT（不常用）
![](https://upload-images.jianshu.io/upload_images/18339009-60402719caab3bfa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-0585eb6db952abd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-ff535562ada70c9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




## 卷积层输出矩阵大小
padding = “SAME”$n_{output}=[  n_{input}  ]$

padding = “VALID”$n_{\text {output}}=\left[\frac{n_{\text {input}}- \text{kernel_size} +1}{s}\right]$

## 池化层的输出大小公式也与卷积层一样，由于没有进行填充，所以p=0，可以简化为  $ \frac{n-  \text{kernel_size} }{s} +1 $




## 一个卷积核对应一个feature map
![](https://upload-images.jianshu.io/upload_images/18339009-c7cd01c735e57672.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-e6be19d861ab23cf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# LeNet-5实现
```
import tensorflow as tf

# 1. 设定神经网络的参数
INPUT_NODE = 784
OUTPUT_NODE = 10

IMAGE_SIZE = 28
NUM_CHANNELS = 1
NUM_LABELS = 10

CONV1_DEEP = 32
CONV1_SIZE = 5

CONV2_DEEP = 64
CONV2_SIZE = 5

FC_SIZE = 512

# 2. 定义前向传播的过程
def inference(input_tensor, train, regularizer):
    with tf.variable_scope('layer1-conv1'):
        conv1_weights = tf.get_variable(
            "weight", [CONV1_SIZE, CONV1_SIZE, NUM_CHANNELS, CONV1_DEEP],
            initializer=tf.truncated_normal_initializer(stddev=0.1))
        conv1_biases = tf.get_variable("bias", [CONV1_DEEP], initializer=tf.constant_initializer(0.0))
        conv1 = tf.nn.conv2d(input_tensor, conv1_weights, strides=[1, 1, 1, 1], padding='SAME')
        relu1 = tf.nn.relu(tf.nn.bias_add(conv1, conv1_biases))

    with tf.name_scope("layer2-pool1"):
        pool1 = tf.nn.max_pool(relu1, ksize = [1,2,2,1],strides=[1,2,2,1],padding="SAME")

    with tf.variable_scope("layer3-conv2"):
        conv2_weights = tf.get_variable(
            "weight", [CONV2_SIZE, CONV2_SIZE, CONV1_DEEP, CONV2_DEEP],
            initializer=tf.truncated_normal_initializer(stddev=0.1))
        conv2_biases = tf.get_variable("bias", [CONV2_DEEP], initializer=tf.constant_initializer(0.0))
        conv2 = tf.nn.conv2d(pool1, conv2_weights, strides=[1, 1, 1, 1], padding='SAME')
        relu2 = tf.nn.relu(tf.nn.bias_add(conv2, conv2_biases))

    with tf.name_scope("layer4-pool2"):
        pool2 = tf.nn.max_pool(relu2, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')
        pool_shape = pool2.get_shape().as_list()
        nodes = pool_shape[1] * pool_shape[2] * pool_shape[3]
        reshaped = tf.reshape(pool2, [pool_shape[0], nodes])

    with tf.variable_scope('layer5-fc1'):
        fc1_weights = tf.get_variable("weight", [nodes, FC_SIZE],
                                      initializer=tf.truncated_normal_initializer(stddev=0.1))
        if regularizer != None: tf.add_to_collection('losses', regularizer(fc1_weights))
        fc1_biases = tf.get_variable("bias", [FC_SIZE], initializer=tf.constant_initializer(0.1))

        fc1 = tf.nn.relu(tf.matmul(reshaped, fc1_weights) + fc1_biases)
        if train: fc1 = tf.nn.dropout(fc1, 0.5)

    with tf.variable_scope('layer6-fc2'):
        fc2_weights = tf.get_variable("weight", [FC_SIZE, NUM_LABELS],
                                      initializer=tf.truncated_normal_initializer(stddev=0.1))
        if regularizer != None: tf.add_to_collection('losses', regularizer(fc2_weights))
        fc2_biases = tf.get_variable("bias", [NUM_LABELS], initializer=tf.constant_initializer(0.1))
        logit = tf.matmul(fc1, fc2_weights) + fc2_biases

    return logit
```



Text-CNN的原理。
利用Text-CNN模型来进行文本分类。 
[https://blog.csdn.net/yanyiting666/article/details/88547000](https://blog.csdn.net/yanyiting666/article/details/88547000)

![image.png](https://upload-images.jianshu.io/upload_images/18339009-2a3c54643144d52d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

