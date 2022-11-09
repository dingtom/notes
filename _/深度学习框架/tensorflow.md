- [ 数据集](#head1)
- [ 生成数据](#head2)
- [ ckpt文件保存方法,模型持久化](#head3)
- [ bp文件保存加载方法](#head4)
- [ 多线程](#head5)
- [ 多线程队列操作](#head6)
	- [1. 创建文件列表，通过文件列表创建输入文件队列，读取文件为本章第一节创建的文件。](#head7)
	- [3. 解析TFRecord文件里的数据。](#head8)
- [ 读取文件。](#head9)
- [ 解析读取的样例。](#head10)
	- [4. 将文件以100个为一组打包。](#head11)
- [tf.train.shuffle_batch组合样例提供给神经网络输入层  不需要feed_dict?????????](#head12)
- [ min_after_dequeue出队时队列中元素的最少个数（队列元素太少shuffle没用）](#head13)
- [ capacity队列大小（太大占用内存，太小容易因为没有数据而block）](#head14)
- [ batch_size一个batch中样例的个数](#head15)
	- [5. 训练模型。](#head16)
- [ 模型相关的参数](#head17)
- [ 计算交叉熵及其平均值](#head18)
- [ 损失函数的计算](#head19)
- [ 优化损失函数](#head20)
- [ 初始化会话，并开始训练过程。](#head21)
- [ 循环的训练神经网络。](#head22)
- [ 从数组创建数据集。](#head23)
- [ 读取文本文件里的数据。](#head24)
- [ 解析TFRecord文件里的数据](#head25)
- [ map()对数据集中每一条数据调用解析方法](#head26)
- [ 使用initializable_iterator来动态初始化数据集。](#head27)
- [ one_shot_iterator数据集所有参数必须已经确定](#head28)
- [ 首先初始化iterator，并给出input_files的值。](#head29)
- [ 遍历所有数据一个epoch。当遍历结束时，程序会抛出OutOfRangeError。](#head30)
	- [1. 列举输入文件。](#head31)
- [输入数据使用本章第一节（1. TFRecord样例程序.ipynb）生成的训练和测试数据。](#head32)
	- [2. 定义解析TFRecord文件的parser方法。](#head33)
- [ 解析一个TFRecord的方法。](#head34)
	- [3. 定义训练数据集。](#head35)
- [ 定义读取训练数据的数据集。](#head36)
- [ 预处理，lambda返回元组](#head37)
- [ 对数据进行shuffle和batching操作。](#head38)
- [ 重复NUM_EPOCHS个epoch。](#head39)
- [ 定义数据集迭代器。](#head40)
	- [4. 定义神经网络结构和优化过程。](#head41)
- [ 定义神经网络的结构以及优化过程。这里与7.3.4小节相同。](#head42)
- [ 计算交叉熵及其平均值](#head43)
- [ 损失函数的计算](#head44)
- [ 优化损失函数](#head45)
	- [4. 定义测试用数据集。](#head46)
- [ 定义测试用的Dataset。](#head47)
- [ 定义测试数据上的迭代器。](#head48)
- [ 定义测试数据上的预测结果。](#head49)
- [ 声明会话并运行神经网络的优化过程。](#head50)
- [ 初始化变量。](#head51)
- [ 初始化训练数据的迭代器。](#head52)
- [ 循环进行训练，直到数据集完成输入、抛出OutOfRangeError错误。](#head53)
- [ 初始化测试数据的迭代器。](#head54)
- [ 获取预测结果。](#head55)
- [ 计算准确率](#head56)
	- [1. 定义神经网络的参数。](#head57)
	- [2. 定义训练的过程并保存TensorBoard的log文件。](#head58)
- [ 输入数据的命名空间。](#head59)
- [ 处理滑动平均的命名空间。](#head60)
- [ 计算损失函数的命名空间。](#head61)
- [ 定义学习率、优化方法及每一轮执行训练的操作的命名空间。](#head62)
- [ 训练模型。](#head63)
- [ 配置运行时需要记录的信息。](#head64)
- [ 运行时记录运行信息的proto。](#head65)
	- [3. 主函数。](#head66)
	- [1. 读取数据集，第一次TensorFlow会自动下载数据集到下面的路径中。](#head67)
	- [2. 数据集会自动被分成3个子集，train、validation和test。以下代码会显示数据集的大小。](#head68)
	- [3. 查看training数据集中某个成员的像素矩阵生成的一维数组和其属于的数字标签。](#head69)
	- [4. 使用mnist.train.next_batch来实现随机梯度下降。](#head70)
- [ 从train的集合中选取batch_size个训练数据。](#head71)
- [ 下载数据](#head72)
- [print(mnist.train.num_examples, mnist.validation.images[2])](#head73)
- [batch_size = 100](#head74)
- [x, y = mnist.train.next_batch(batch_size)](#head75)
- [ 1.设置输入和输出节点的个数,配置神经网络的参数。](#head76)
- [ 模型相关的参数](#head77)
- [3. 定义训练过程。](#head78)
- [ 生成隐藏层/输出层的参数。](#head79)
- [ 计算不含滑动平均类的前向传播结果](#head80)
- [ 定义训练轮数及相关的滑动平均类](#head81)
- [ 优化所有可训练变量](#head82)
- [ 计算交叉熵及其平均值#当问题只有一个正确答案时可以用这个函数加快交叉熵计算](#head83)
- [ 损失函数的计算](#head84)
- [ 设置指数衰减的学习率。](#head85)
- [ 优化损失函数](#head86)
- [ 反向传播更新参数和更新每一个参数的滑动平均值，为了一次完成多个操作有以下两种机制](#head87)
- [ 计算正确率,tf.equal返回的是布尔值](#head88)
- [ 初始化会话，并开始训练过程。](#head89)
- [ 循环的训练神经网络。](#head90)
	- [1. 在上下文管理器“foo”中创建变量“v”。](#head91)
- [v = tf.get_variable("v", [1])](#head92)
- [v = tf.get_variable("v", [1])  在bar空间中没有v](#head93)
	- [2. 嵌套上下文管理器中的reuse参数的使用。](#head94)
	- [3. 通过variable_scope来管理变量。](#head95)
	- [4. 我们可以通过变量的名称来获取变量。](#head96)
	- [1. 保存计算两个变量和的模型。](#head97)
	- [2. 加载保存了两个变量和的模型。](#head98)
	- [3. 直接加载持久化的图。](#head99)
	- [4. 变量重命名。](#head100)
	- [1. 使用滑动平均。](#head101)
	- [2. 保存滑动平均模型。](#head102)
- [保存的时候会将v:0  v/ExponentialMovingAverage:0这两个变量都存下来。](#head103)
	- [3. 加载滑动平均模型。](#head104)
- [ 通过变量重命名将原来变量v的滑动平均值直接赋值给v。](#head105)
	- [1. 定义神经网络结构相关的参数。](#head106)
	- [2. 通过tf.get_variable函数来获取变量。](#head107)
	- [3. 定义神经网络的前向传播过程。](#head108)
	- [1. 定义神经网络结构相关的参数。](#head109)
	- [2. 定义训练过程。](#head110)
- [ 定义输入输出placeholder。](#head111)
- [ 定义损失函数、学习率、滑动平均操作以及训练过程。](#head112)
- [ 初始化TensorFlow持久化类。](#head113)
	- [3. 主程序入口。](#head114)
	- [1. 每10秒加载一次最新的模型](#head115)
- [ 加载的时间间隔。](#head116)
	- [ 主程序](#head117)




**TensorFlow操作自动将NumPy ndarrays转换为Tensors。 NumPy操作自动将Tensors转换为NumPy ndarrays。**
ndarray = np.ones([2,2])
tensor = tf.multiply(ndarray, 36)
print(tensor)
print(np.add(tensor, 1))
**将张量显式转换为numpy类型**
print(tensor.numpy())

**如果存在GPU,强制使用GPU**
if tf.test.is_gpu_available():
    print('On GPU:')
    with tf.device.endswith('GPU:0'):
        x = tf.random.uniform([1000, 1000])
**使用断言验证当前是否为GPU0**
    assert x.device.endswith('GPU:0')

# <span id="head1"> 数据集</span>
创建源数据集 使用其中一个工厂函数（如Dataset.from_tensors，Dataset.from_tensor_slices）或使用从TextLineDataset或TFRecordDataset等文件读取的对象创建源数据集。
```
ds_tensors = tf.data.Dataset.from_tensor_slices([6,5,4,3,2,1])
with open(filename, 'w') as f:
    f.write("""Line1 
               Line2
               Line 3""")
# 获取TextLineDataset数据集实例
ds_file = tf.data.TextLineDataset(filename)
# 使用map，batch和shuffle等转换函数将转换应用于数据集记录。
ds_tensors = ds_tensors.map(tf.square).shuffle(2).batch(2)
ds_file = ds_file.batch(2)
# tf.data.Dataset对象支持迭代循环记录：
for x in ds_tensors:
    print(x)
for x in ds_file:
    print(x)
```
>ds_tensors中的元素：
tf.Tensor([36 25], shape=(2,), dtype=int32)
tf.Tensor([16  9], shape=(2,), dtype=int32)
tf.Tensor([4 1], shape=(2,), dtype=int32)
ds_file中的元素：
tf.Tensor([b'Line 1' b'Line 2'], shape=(2,), dtype=string)
tf.Tensor([b'Line 3'], shape=(1,), dtype=string)



# <span id="head2"> 生成数据</span>
```tf.random_normal()``` 生成正态分布随机数 
```tf.truncated_normal() ```生成去掉过大偏离点的正态分布随机数 
```tf.random_uniform() ```生成均匀分布随机数 
```tf.random_gamma()``` Gamma分布
```tf.zeros ```表示生成全 0 数组 
```tf.ones``` 表示生成全 1 数组 
```tf.fill ```表示生成全定值数组 
```tf.constant ```表示生成直接给定值的数组 
>举例
① w=tf.Variable(tf.random_normal([2,3],stddev=2, mean=0, seed=1))，表示生成正态分布随机数，形状两行三列，标准差是 2，均值是 0，随机种子是 1。
② w=tf.Variable(tf.truncated_normal([2,3],stddev=2, mean=0, seed=1))，表示去掉偏离过大的正态分布，也就是如果随机出来的数据偏离平均值超过两个标准差，这个数据将重新生成。
③w=np.random_uniform([2,3],minval=0,maxval=1,dtype=tf.int32，seed=1),表示从一个均匀分布**[minval maxval)**中随机采样
**shape不能定义为单个数形式**
④ tf.zeros([3,2],int32)表示生成[[0,0],[0,0],[0,0]]；
tf.ones([3,2],int32)表示生成[[1,1],[1,1],[1,1]；
tf.fill([3,2],6)表示生成[[6,6],[6,6],[6,6]]；
tf.constant([3,2,1])表示             生成[3,2,1]。


```tf.cl ip_by_value```第二三个参数代表限定的最大值和最小值，不符合的将被该值代替

```*```对应元素相乘```tf.matmul```矩阵乘法




#图像预处理
```
import matplotlib.pyplot as plt
import tensorflow as tf
import numpy as np
image_raw_data = tf.gfile.FastGFile("../../datasets/cat.jpg",'rb').read()
with tf.Session() as sess:
    # 解码
    decode_data = tf.image.decode_jpeg(image_raw_data)
    # print(decode_data.eval())
    print(decode_data.shape)
    plt.imshow(decode_data.eval())
    plt.show()
    
    # 将图像的三维矩阵按照jpeg格式编码存入文件
    encode_data = tf.image.encode_jpeg(decode_data)
    with tf.gfile.GFile("../../datasets/cat(1).jpg",'wb') as g:
        g.write(encode_data.eval())
        
    # 调整图片大小
    
    # 如果直接以0-255范围的整数数据输入resize_images，那么输出将是0-255之间的实数，
    # 不利于后续处理。本书建议在调整图片大小前，先将图片转为0-1范围的实数。
    image_float = tf.image.convert_image_dtype(decode_data, tf.float32)
    # method为调整图像的算法0：双线性插值法1：最近邻居法2：双三次插值法3：面积插值法
    resized_data = tf.image.resize_images(image_float, [300, 300], method=0)
    plt.imshow(resized_data.eval())
    plt.show()
    
    # 裁剪和填充图片
    cropped_data = tf.image.resize_image_with_crop_or_pad(decode_data, 1000, 1000)
    padded_data = tf.image.resize_image_with_crop_or_pad(decode_data, 3000, 3000)
    plt.imshow(cropped_data.eval())
    plt.imshow(padded_data.eval())
    plt.show()
    
    # 按比例在中心裁剪图像
    central_cropped = tf.image.central_crop(decode_data, 0.5)
    plt.imshow(central_cropped.eval())
    plt.show()
    
    # 翻转图片
    # 上下翻转
    #flipped1 = tf.image.flip_up_down(decode_data)
    # 左右翻转
    #flipped2 = tf.image.flip_left_right(decode_data)
    #对角线翻转
    transposed = tf.image.transpose_image(decode_data)
    plt.imshow(transposed.eval())
    plt.show() 
    # 以一定概率上下翻转图片。
    #flipped = tf.image.random_flip_up_down(img_data)
    
    
    
    #  图片亮度、对比度、色相、饱和度调整
    
    # 在进行一系列图片调整前，先将图片转换为实数形式，有利于保持计算精度。
    image_float = tf.image.convert_image_dtype(decode_data, tf.float32)
    
    # 将图片的亮度-0.5。
    #adjusted = tf.image.adjust_brightness(image_float, -0.5)
    
    # 在[-max_delta, max_delta)的范围随机调整图片的亮度。
    adjusted = tf.image.random_brightness(image_float, max_delta=0.5)
    
    # 将图片的对比度+5
    #adjusted = tf.image.adjust_contrast(image_float, 5)
    
    # 在[lower, upper]的范围随机调整图的对比度。
    #adjusted = tf.image.random_contrast(image_float, lower, upper)
    
    adjusted = tf.image.adjust_hue(image_float, 0.1)
    
    # 在[-max_delta, max_delta]的范围随机调整图片的色相。max_delta的取值在[0, 0.5]之间。
    #adjusted = tf.image.random_hue(image_float, max_delta) 
    
    # 将图片的饱和度-5。
    #adjusted = tf.image.adjust_saturation(image_float, -5)
    
    # 在[lower, upper]的范围随机调整图的饱和度。
    #adjusted = tf.image.random_saturation(image_float, lower, upper)
    
    # 将代表一张图片的三维矩阵中的数字均值变为0，方差变为1。
    #adjusted = tf.image.per_image_standardization(image_float)
    
    # 在最终输出前，将实数取值截取到0-1范围内
    adjusted = tf.clip_by_value(adjusted, 0.0, 1.0)
    plt.imshow(adjusted.eval())
    plt.show()
    
    
    
    #  添加标注框并裁减
    # 一个标注匡为[Ymin, Xmin, Ymax, Xmax]
    boxes = tf.constant([[[0.05, 0.05, 0.9, 0.7], [0.35, 0.47, 0.5, 0.56]]])
    
    # sample_distorted_bounding_box要求输入图片必须是实数类型。
    image_float = tf.image.convert_image_dtype(decode_data, tf.float32)
    
    # sample_distorted_bounding_box随机截取图像，min_object_covered=0.4截取部分至少包含某个标注框0.4的内容
    begin, size, bbox_for_draw = tf.image.sample_distorted_bounding_box(
        tf.shape(image_float), bounding_boxes=boxes, min_object_covered=0.4)
    
    # 截取后的图片
    distorted_image = tf.slice(image_float, begin, size)
    plt.imshow(distorted_image.eval())
    plt.show()

    # 在原图上用标注框画出截取的范围。由于原图的分辨率较大（2673x1797)，生成的标注框 
    # 在Jupyter Notebook上通常因边框过细而无法分辨，这里为了演示方便先缩小分辨率。
    image_small = tf.image.resize_images(image_float, [180, 267], method=0)
    # draw_bounding_boxes的输入是一个batch的数据也就是多张图像组成的四维矩阵
    batchced_img = tf.expand_dims(image_small, 0)
    # 标注框变成了一个
    image_with_box = tf.image.draw_bounding_boxes(batchced_img, bbox_for_draw)
    print(bbox_for_draw.eval())
    plt.imshow(image_with_box[0].eval())
    plt.show()
    
    
#### 1. 创建队列，并操作里面的元素。

# 先进先出队列，最多保存两个元素RandomShuffleQueue
q = tf.FIFOQueue(2, "int32")
# 初始化队列
init = q.enqueue_many(([0, 10],))
x = q.dequeue()
y = x + 1
q_inc = q.enqueue([y])
with tf.Session() as sess:
    init.run()
    for _ in range(5):
        v, _ = sess.run([x, q_inc])
        print (v)
```
# <span id="head3"> ckpt文件保存方法,模型持久化</span>
```
# 保存计算模型。
saver.save(sess, os.path.join(self.MODEL_SAVE_PATH, self.MODEL_NAME), global_step=global_step)
# 加载模型。
saver.restore(sess, "Saved_model/model.ckpt")
#  直接加载持久化的图。
saver = tf.train.import_meta_graph("Saved_model/model.ckpt.meta")
with tf.Session() as sess:
    saver.restore(sess, "Saved_model/model.ckpt")
    print sess.run(tf.get_default_graph().get_tensor_by_name("add:0")) 
# 变量重命名。
v1 = tf.Variable(tf.constant(1.0, shape=[1]), name = "other-v1")
v2 = tf.Variable(tf.constant(2.0, shape=[1]), name = "other-v2")
saver = tf.train.Saver({"v1": v1, "v2": v2})
```
# <span id="head4"> bp文件保存加载方法</span>
```
#### 1. pb文件的保存方法。

import tensorflow as tf
from tensorflow.python.framework import graph_util

v1 = tf.Variable(tf.constant(1.0, shape=[1]), name = "v1")
v2 = tf.Variable(tf.constant(2.0, shape=[1]), name = "v2")
result = v1 + v2

init_op = tf.global_variables_initializer()
with tf.Session() as sess:
    sess.run(init_op)
    graph_def = tf.get_default_graph().as_graph_def()
    output_graph_def = graph_util.convert_variables_to_constants(sess, graph_def, ['add'])
    with tf.gfile.GFile("Saved_model/combined_model.pb", "wb") as f:
           f.write(output_graph_def.SerializeToString())

#### 2. 加载pb文件。

from tensorflow.python.platform import gfile
with tf.Session() as sess:
    model_filename = "Saved_model/combined_model.pb"
   
    with gfile.FastGFile(model_filename, 'rb') as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())

    result = tf.import_graph_def(graph_def, return_elements=["add:0"])
    print sess.run(result)
```
# <span id="head5"> 多线程</span>
TensorFlow 提供了tf.Coordinator和tf.QueueRunner两个类来完成多线程协同的功能。
tf.Coordinator 主要用于协同多个线程一起停止，并提供了should_stop、request_stop和join三个函数。在启动线程之前，需要先声明一个tfCoordinator类，并将这个类传入每一个创建的线程中。启动的线程需要一直查询f.Coordinator类中提供的should_stop函数，当这个函数的返回值为True时，则当前线程也需要退出。每一个启动的线程都可以通过调用request_stop函数来通知其他线程退出。当某一个线程调用request_stop函数之后，should_stop函数的返回值将被设置为True，这样其他的线程就可以同时终止了。以下程序展示了如何使用tf.Coordinator。
```
import numpy as np
import threading
import time
# 这个程序每隔1秒判断是否需要停止并打印自己的ID。
def MyLoop(coord, worker_id):
    # should_stop为True当前进程退出
    while not coord.should_stop():
        if np.random.rand()<0.1:
            print ("Stoping from id: %d\n" % worker_id)
            # request_stop通知其他线程停止should_stop被设置为True
            coord.request_stop()
        else:
            print ("Working on id: %d\n" % worker_id)
        time.sleep(1)
# 创建、启动并退出线程。
# Coordinator类协同多个线程
coord = tf.train.Coordinator()
threads = [threading.Thread(target=MyLoop, args=(coord, i, )) for i in xrange(5)]
# 启动所有线程
for t in threads:t.start()
# 等待所有线程退出
coord.join(threads)
```
# <span id="head6"> 多线程队列操作</span>

```
import tensorflow as tf
# 定义队列及其操作FIF OQueue ，它的实现的是一个先进先出队列。RandomShuffieQueue
# 会将队列中的元素打乱，每次出队列操作得到的是从当前队列所有元素中随机选择的一个。
q = tf.FIFOQueue(100,'float')
enqueue_op = q.enqueue(tf.random_normal([1]))
# tf.train.QueueRunner创建多个线程运行队列的入队操作
#  *5表示启动了5个线程，线程中运行的是enqueue_op操作
qr = tf.train.QueueRunner(q, [enqueue_op]*5)
# 将定义好的QueueRunner加入TensorFlow计算图的指定集合，默认为tf.GraphKeys.QUEUE_RUNNERS
tf.train.add_queue_runner(qr)
out_tensor = q.dequeue()
with tf.Session() as sess:
    coord = tf.train.Coordinator()
    # 使用QueueRunner时需要明确调用start_queue_runners来启动所有线程
    threads =  tf.train.start_queue_runners(sess=sess, coord=coord)
    for _ in range(3):
        print(sess.run(out_tensor)[0])
    coord.request_stop()
    coord.join(threads)
```

#输入文件队列
```
import tensorflow as tf

# 创建TFRecord文件的帮助函数。生成样例数据
def _int64_feature(value):
    return tf.train.Feature(int64_list=tf.train.Int64List(value=[value]))
# 模拟海量数据下将数据写入不同文件，
# num_shards：共写入多少个文件
# instance_per_shard:每个文件中有多少数据
num_shards = 2
instance_per_shard = 2
for i in range(num_shards):
    filename = ('/path/to/data.tfrecoders-{}.5d-of-{}.5d'.format(i,num_shards))
    with tf.python_io.TFRecordWriter(filename) as writer:
        # 将数据封装成Example 结构并写入TFRecord 文件。
        for j in range(instance_per_shard):
            example = tf.train.Example(features=tf.train.Features(feature={
                'i': _int64_feature(i),
                'j': _int64_feature(j)
            }))
        writer.write(example.SerializeToString())
# 获取符合一个正则表达式的所有文件得到文件列表
files = tf.train.match_filenames_once('path/to/data.tfrecoders-*')
# 使用初始化时提供的文件列表创建一个输入队列
# 当一个输入队列中的所有文件都被处理完后，
# 初始化时的文件列表又重新加入队列设置
# num_epochs参数限制加载初始文件列表的最大论数
filename_queue = tf.train.string_input_producer(files, shuffle=False, num_epochs=100)
reader = tf.TFRecordReader()
_, serialized_example = reader.read(filename_queue)
features = tf.parse_single_example(serialized_example, features={'i':tf.FixedLenFeature([], tf.int64), 'j': tf.FixedLenFeature([], tf.int64)})
with tf.Session() as sess:
    tf.local_variables_initializer().run()
    print(files)
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(sess=sess, coord=coord)
    for i in range(6):
        print(sess.run([features['i'], features['j']]))
    coord.request_stop()
    coord.join(threads)
  ```
#输入数据处理框架
```
import tensorflow as tf

#### <span id="head7">1. 创建文件列表，通过文件列表创建输入文件队列，读取文件为本章第一节创建的文件。</span>
files = tf.train.match_filenames_once("output.tfrecords")
filename_queue = tf.train.string_input_producer(files, shuffle=False) 

#### <span id="head8">3. 解析TFRecord文件里的数据。</span>
# <span id="head9"> 读取文件。</span>
reader = tf.TFRecordReader()
_,serialized_example = reader.read(filename_queue)
# <span id="head10"> 解析读取的样例。</span>
features = tf.parse_single_example(
    serialized_example,
    features={
        'image_raw':tf.FixedLenFeature([],tf.string),
        'pixels':tf.FixedLenFeature([],tf.int64),
        'label':tf.FixedLenFeature([],tf.int64)
    })
decoded_images = tf.decode_raw(features['image_raw'],tf.uint8)
retyped_images = tf.cast(decoded_images, tf.float32)
images = tf.reshape(retyped_images, [784])
labels = tf.cast(features['label'],tf.int32)
#pixels = tf.cast(features['pixels'],tf.int32)

#### <span id="head11">4. 将文件以100个为一组打包。</span>
min_after_dequeue = 10000
batch_size = 100
capacity = min_after_dequeue + 3 * batch_size
# <span id="head12">tf.train.shuffle_batch组合样例提供给神经网络输入层  不需要feed_dict?????????</span>
# <span id="head13"> min_after_dequeue出队时队列中元素的最少个数（队列元素太少shuffle没用）</span>
# <span id="head14"> capacity队列大小（太大占用内存，太小容易因为没有数据而block）</span>
# <span id="head15"> batch_size一个batch中样例的个数</span>
image_batch, label_batch = tf.train.shuffle_batch([images, labels], 
                                                    batch_size=batch_size, 
                                                    capacity=capacity, 
                                                    min_after_dequeue=min_after_dequeue)

#### <span id="head16">5. 训练模型。</span>
def inference(input_tensor, weights1, biases1, weights2, biases2):
    layer1 = tf.nn.relu(tf.matmul(input_tensor, weights1) + biases1)
    return tf.matmul(layer1, weights2) + biases2
# <span id="head17"> 模型相关的参数</span>
INPUT_NODE = 784
OUTPUT_NODE = 10
LAYER1_NODE = 500
REGULARAZTION_RATE = 0.0001   
TRAINING_STEPS = 5000        
weights1 = tf.Variable(tf.truncated_normal([INPUT_NODE, LAYER1_NODE], stddev=0.1))
biases1 = tf.Variable(tf.constant(0.1, shape=[LAYER1_NODE]))
weights2 = tf.Variable(tf.truncated_normal([LAYER1_NODE, OUTPUT_NODE], stddev=0.1))
biases2 = tf.Variable(tf.constant(0.1, shape=[OUTPUT_NODE]))
y = inference(image_batch, weights1, biases1, weights2, biases2)
# <span id="head18"> 计算交叉熵及其平均值</span>
cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=label_batch)
cross_entropy_mean = tf.reduce_mean(cross_entropy)
# <span id="head19"> 损失函数的计算</span>
regularizer = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
regularaztion = regularizer(weights1) + regularizer(weights2)
loss = cross_entropy_mean + regularaztion
# <span id="head20"> 优化损失函数</span>
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(loss)
# <span id="head21"> 初始化会话，并开始训练过程。</span>
with tf.Session() as sess:
    sess.run((tf.global_variables_initializer(),
              tf.local_variables_initializer()))
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(sess=sess, coord=coord)
# <span id="head22"> 循环的训练神经网络。</span>
    for i in range(TRAINING_STEPS):
        if i % 1000 == 0:
            print("After %d training step(s), loss is %g " % (i, sess.run(loss)))
        sess.run(train_step) 
    coord.request_stop()
    coord.join(threads)       
```
#数据集基本使用方法
```
# <span id="head23"> 从数组创建数据集。</span>
input_data =[1, 2, 4, 5, 6]
dataset = tf.data.Dataset.from_tensor_slices(input_data)
iterator = dataset.make_one_shot_iterator()
x = iterator.get_next()
y = x**2
with tf.Session() as sess:
    for i in range(len(input_data)):
        print(sess.run(y))
# <span id="head24"> 读取文本文件里的数据。</span>
with open('./test1.txt','w') as file:
    file.write('File1, line1. \n')
    file.write("File1, line2.\n")
with open("./test2.txt", "w") as file:
    file.write("File2, line1.\n") 
    file.write("File2, line2.\n")
input_files = ['./test1.txt', './test2.txt']   
dataset = tf.data.TextLineDataset(input_files)
iterator = dataset.make_one_shot_iterator()
x = iterator.get_next()
with tf.Session() as sess:
    for i in range(4):
        print(sess.run(x))
# <span id="head25"> 解析TFRecord文件里的数据</span>
def parser(record):
    features = tf.parse_single_example(record, features={
        'image_raw': tf.FixedLenFeature([], tf.string),
        'pixels': tf.FixedLenFeature([], tf.int64),
        'label': tf.FixedLenFeature([], tf.int64)
    })
    decoded_images = tf.decode_raw(feature['image_raw'], tf.uint8)
    retype_images = tf.cast(decoded_images, tf.float32)
    images = tf.reshape(retype_images, [784])
    labels = tf.cast(features['label'], tf.int32)
    return images, labels
input_files = ['output.tfrecords']
dataset = tf.data.TFRecordDataset(input_files)
# <span id="head26"> map()对数据集中每一条数据调用解析方法</span>
dataset = dataset.map(parser)
iterator = dataset.make_one_shot_iterator()
image, label = iterator.get_next()
with tf.Session() as sess:
    for i in range(10):
        x, y = sess.run([image, label]) 
        print(y)
# <span id="head27"> 使用initializable_iterator来动态初始化数据集。</span>
input_files = tf.placeholder(tf.string)
dataset = tf.data.TFRecordDataset(input_files)
dataset = dataset.map(parser)

# <span id="head28"> one_shot_iterator数据集所有参数必须已经确定</span>
iterator = dataset.make_initializable_iterator()
image, label = iterator.get_next()

with tf.Session() as sess:
# <span id="head29"> 首先初始化iterator，并给出input_files的值。</span>
    sess.run(iterator.initializer,
             feed_dict={input_files: ["output.tfrecords"]})
# <span id="head30"> 遍历所有数据一个epoch。当遍历结束时，程序会抛出OutOfRangeError。</span>
    while True:
        try:
            x, y = sess.run([image, label])
        except tf.errors.OutOfRangeError:
            break 
```
#数据集高层操作
```
import tempfile
import tensorflow as tf

#### <span id="head31">1. 列举输入文件。</span>
# <span id="head32">输入数据使用本章第一节（1. TFRecord样例程序.ipynb）生成的训练和测试数据。</span>
train_files = tf.train.match_filenames_once("output.tfrecords")
test_files = tf.train.match_filenames_once("output_test.tfrecords")

#### <span id="head33">2. 定义解析TFRecord文件的parser方法。</span>
# <span id="head34"> 解析一个TFRecord的方法。</span>
def parser(record):
    features = tf.parse_single_example(
        record,
        features={
            'image_raw':tf.FixedLenFeature([],tf.string),
            'pixels':tf.FixedLenFeature([],tf.int64),
            'label':tf.FixedLenFeature([],tf.int64)
        })
    decoded_images = tf.decode_raw(features['image_raw'],tf.uint8)
    retyped_images = tf.cast(decoded_images, tf.float32)
    images = tf.reshape(retyped_images, [784])
    labels = tf.cast(features['label'],tf.int32)
    #pixels = tf.cast(features['pixels'],tf.int32)
    return images, labels

#### <span id="head35">3. 定义训练数据集。</span>
image_size = 299          # 定义神经网络输入层图片的大小。
batch_size = 100          # 定义组合数据batch的大小。
shuffle_buffer = 10000   # 定义随机打乱数据时buffer的大小。
# <span id="head36"> 定义读取训练数据的数据集。</span>
dataset = tf.data.TFRecordDataset(train_files)
dataset = dataset.map(parser)
# <span id="head37"> 预处理，lambda返回元组</span>
dataset = dataset.map(lambda image, label:( preprocess_for_train(image, image_size, image_size, None), label))
# <span id="head38"> 对数据进行shuffle和batching操作。</span>
dataset = dataset.shuffle(shuffle_buffer).batch(batch_size)
# <span id="head39"> 重复NUM_EPOCHS个epoch。</span>
NUM_EPOCHS = 10
dataset = dataset.repeat(NUM_EPOCHS)
# <span id="head40"> 定义数据集迭代器。</span>
iterator = dataset.make_initializable_iterator()
image_batch, label_batch = iterator.get_next()

#### <span id="head41">4. 定义神经网络结构和优化过程。</span>
# <span id="head42"> 定义神经网络的结构以及优化过程。这里与7.3.4小节相同。</span>
def inference(input_tensor, weights1, biases1, weights2, biases2):
    layer1 = tf.nn.relu(tf.matmul(input_tensor, weights1) + biases1)
    return tf.matmul(layer1, weights2) + biases2
INPUT_NODE = 784
OUTPUT_NODE = 10
LAYER1_NODE = 500
REGULARAZTION_RATE = 0.0001   
TRAINING_STEPS = 5000        
weights1 = tf.Variable(tf.truncated_normal([INPUT_NODE, LAYER1_NODE], stddev=0.1))
biases1 = tf.Variable(tf.constant(0.1, shape=[LAYER1_NODE]))
weights2 = tf.Variable(tf.truncated_normal([LAYER1_NODE, OUTPUT_NODE], stddev=0.1))
biases2 = tf.Variable(tf.constant(0.1, shape=[OUTPUT_NODE]))
y = inference(image_batch, weights1, biases1, weights2, biases2)   
# <span id="head43"> 计算交叉熵及其平均值</span>
cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=label_batch)
cross_entropy_mean = tf.reduce_mean(cross_entropy) 
# <span id="head44"> 损失函数的计算</span>
regularizer = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
regularaztion = regularizer(weights1) + regularizer(weights2)
loss = cross_entropy_mean + regularaztion
# <span id="head45"> 优化损失函数</span>
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(loss)


#### <span id="head46">4. 定义测试用数据集。</span>
# <span id="head47"> 定义测试用的Dataset。</span>
test_dataset = tf.data.TFRecordDataset(test_files)
test_dataset = test_dataset.map(parser)
test_dataset = test_dataset.batch(batch_size)
# <span id="head48"> 定义测试数据上的迭代器。</span>
test_iterator = test_dataset.make_initializable_iterator()
test_image_batch, test_label_batch = test_iterator.get_next()
# <span id="head49"> 定义测试数据上的预测结果。</span>
test_logit = inference(test_image_batch, weights1, biases1, weights2, biases2)
predictions = tf.argmax(test_logit, axis=-1, output_type=tf.int32)
# <span id="head50"> 声明会话并运行神经网络的优化过程。</span>
with tf.Session() as sess:  
# <span id="head51"> 初始化变量。</span>
    sess.run((tf.global_variables_initializer(),
              tf.local_variables_initializer()))
# <span id="head52"> 初始化训练数据的迭代器。</span>
    sess.run(iterator.initializer)
# <span id="head53"> 循环进行训练，直到数据集完成输入、抛出OutOfRangeError错误。</span>
    while True:
        try:
            sess.run(train_step)
        except tf.errors.OutOfRangeError:
            break
    test_results = []
    test_labels = []
# <span id="head54"> 初始化测试数据的迭代器。</span>
    sess.run(test_iterator.initializer)
# <span id="head55"> 获取预测结果。</span>
    while True:
        try:
            pred, label = sess.run([predictions, test_label_batch])
            test_results.extend(pred)
            test_labels.extend(label)
        except tf.errors.OutOfRangeError:
            break

# <span id="head56"> 计算准确率</span>
correct = [float(y == y_) for (y, y_) in zip (test_results, test_labels)]
accuracy = sum(correct) / len(correct)
print("Test accuracy is:", accuracy) 
```









#tensorboard
启动TensorBoard:  `tensorboard --logdir=logdir`
浏览器访问：  http://DESKTOP-6E45MO9:6006
mnist_train可视化
```
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import mnist_inference

#### <span id="head57">1. 定义神经网络的参数。</span>
BATCH_SIZE = 100
LEARNING_RATE_BASE = 0.8
LEARNING_RATE_DECAY = 0.99
REGULARIZATION_RATE = 0.0001
TRAINING_STEPS = 3000
MOVING_AVERAGE_DECAY = 0.99

#### <span id="head58">2. 定义训练的过程并保存TensorBoard的log文件。</span>
def train(mnist):
# <span id="head59"> 输入数据的命名空间。</span>
    with tf.name_scope('input'):
        x = tf.placeholder(tf.float32, [None, mnist_inference.INPUT_NODE], name='x-input')
        y_ = tf.placeholder(tf.float32, [None, mnist_inference.OUTPUT_NODE], name='y-input')
    regularizer = tf.contrib.layers.l2_regularizer(REGULARIZATION_RATE)
    y = mnist_inference.inference(x, regularizer)
    global_step = tf.Variable(0, trainable=False)
# <span id="head60"> 处理滑动平均的命名空间。</span>
    with tf.name_scope("moving_average"):
        variable_averages = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
        variables_averages_op = variable_averages.apply(tf.trainable_variables())
# <span id="head61"> 计算损失函数的命名空间。</span>
    with tf.name_scope("loss_function"):
        cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))
        cross_entropy_mean = tf.reduce_mean(cross_entropy)
        loss = cross_entropy_mean + tf.add_n(tf.get_collection('losses'))
# <span id="head62"> 定义学习率、优化方法及每一轮执行训练的操作的命名空间。</span>
    with tf.name_scope("train_step"):
        learning_rate = tf.train.exponential_decay(
            LEARNING_RATE_BASE,
            global_step,
            mnist.train.num_examples / BATCH_SIZE, LEARNING_RATE_DECAY,
            staircase=True)
        train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
        with tf.control_dependencies([train_step, variables_averages_op]):
            train_op = tf.no_op(name='train')
    writer = tf.summary.FileWriter("/path/to/log", tf.get_default_graph())
# <span id="head63"> 训练模型。</span>
    with tf.Session() as sess:
        tf.global_variables_initializer().run()
        for i in range(TRAINING_STEPS):
            xs, ys = mnist.train.next_batch(BATCH_SIZE)
            if i % 1000 == 0:
# <span id="head64"> 配置运行时需要记录的信息。</span>
                run_options = tf.RunOptions(trace_level=tf.RunOptions.FULL_TRACE)                
# <span id="head65"> 运行时记录运行信息的proto。</span>
                run_metadata = tf.RunMetadata()
                _, loss_value, step = sess.run(
                    [train_op, loss, global_step], feed_dict={x: xs, y_: ys},
                    options=run_options, run_metadata=run_metadata)
                writer.add_run_metadata(run_metadata=run_metadata, tag=("tag%d" % i), global_step=i)
                print("After %d training step(s), loss on training batch is %g." % (step, loss_value))
            else:
                _, loss_value, step = sess.run([train_op, loss, global_step], feed_dict={x: xs, y_: ys})              
    writer.close()
    
#### <span id="head66">3. 主函数。</span>
def main(argv=None): 
    mnist = input_data.read_data_sets("../../datasets/MNIST_data", one_hot=True)
    train(mnist)
if __name__ == '__main__':
    main()
```

##读取文件
```
#### <span id="head67">1. 读取数据集，第一次TensorFlow会自动下载数据集到下面的路径中。</span>
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("../../datasets/MNIST_data/", one_hot=True)
#### <span id="head68">2. 数据集会自动被分成3个子集，train、validation和test。以下代码会显示数据集的大小。</span>
print('train data size',mnist.train.num_examples )
print ("Validating data size: ", mnist.validation.num_examples)
print ("Testing data size: ", mnist.test.num_examples)
#### <span id="head69">3. 查看training数据集中某个成员的像素矩阵生成的一维数组和其属于的数字标签。</span>
print("Example training data: ", mnist.train.images[0] )
print("Example training data label: ", mnist.train.labels[0])
#### <span id="head70">4. 使用mnist.train.next_batch来实现随机梯度下降。</span>
batch_size = 100
# <span id="head71"> 从train的集合中选取batch_size个训练数据。</span>
xs, ys = mnist.train.next_batch(batch_size)    
print("X shape:", xs.shape )                    
print("Y shape:", ys.shape ) #X shape: (100, 784)Y shape: (100, 10)
```
##训练全模型神经网络
```
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
# <span id="head72"> 下载数据</span>
mnist = input_data.read_data_sets("../../datasets/MNIST_data/", one_hot=True)
# <span id="head73">print(mnist.train.num_examples, mnist.validation.images[2])</span>
# <span id="head74">batch_size = 100</span>
# <span id="head75">x, y = mnist.train.next_batch(batch_size)</span>

# <span id="head76"> 1.设置输入和输出节点的个数,配置神经网络的参数。</span>
INPUT_NODE = 784     # 输入节点
OUTPUT_NODE = 10     # 输出节点
LAYER1_NODE = 500    # 隐藏层节点
BATCH_SIZE = 100     # 每次batch打包的样本个数
# <span id="head77"> 模型相关的参数</span>
LEARNING_RATE_BASE = 0.8
LEARNING_RATE_DECAY = 0.99    # 指数衰减
REGULARAZTION_RATE = 0.0001  # 正则
TRAINING_STEPS = 5000
MOVING_AVERAGE_DECAY = 0.99  # 滑动平均

# <span id="head78">3. 定义训练过程。</span>
x = tf.placeholder(tf.float32, [None, INPUT_NODE], name='x-input')
y_ = tf.placeholder(tf.float32, [None, OUTPUT_NODE], name='y-input')
# <span id="head79"> 生成隐藏层/输出层的参数。</span>
weights1 = tf.Variable(tf.truncated_normal([INPUT_NODE, LAYER1_NODE], stddev=0.1))
weights2 = tf.Variable(tf.truncated_normal([LAYER1_NODE, OUTPUT_NODE], stddev=0.1))
biases1 = tf.Variable(tf.constant(0.1, shape=[LAYER1_NODE]))
biases2 = tf.Variable(tf.constant(0.1, shape=[OUTPUT_NODE]))
# <span id="head80"> 计算不含滑动平均类的前向传播结果</span>
layer1 = tf.nn.relu(tf.matmul(x, weights1) + biases1)
y = tf.matmul(layer1, weights2) + biases2
# <span id="head81"> 定义训练轮数及相关的滑动平均类</span>
global_step = tf.Variable(0, trainable=False)
ema = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
# <span id="head82"> 优化所有可训练变量</span>
ema_op = ema.apply(tf.trainable_variables())
layer1 = tf.nn.relu(tf.matmul(x, ema.average(weights1)) + ema.average(biases1))
ma_y = tf.matmul(layer1, ema.average(weights2)) + ema.average(biases2)
# <span id="head83"> 计算交叉熵及其平均值#当问题只有一个正确答案时可以用这个函数加快交叉熵计算</span>
cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))
cross_entropy_mean = tf.reduce_mean(cross_entropy)
# <span id="head84"> 损失函数的计算</span>
regular = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
regularization = regular(weights1) + regular(weights2)
loss = cross_entropy_mean + regularization
# <span id="head85"> 设置指数衰减的学习率。</span>
learning_rate = tf.train.exponential_decay(
    LEARNING_RATE_BASE,
    global_step,
    mnist.train.num_examples / BATCH_SIZE,
    LEARNING_RATE_DECAY,
    staircase=True)
# <span id="head86"> 优化损失函数</span>
train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
# <span id="head87"> 反向传播更新参数和更新每一个参数的滑动平均值，为了一次完成多个操作有以下两种机制</span>
train_op = tf.group(train_step, ema_op)

# <span id="head88"> 计算正确率,tf.equal返回的是布尔值</span>
correct_prediction = tf.equal(tf.argmax(ma_y, 1), tf.argmax(y_, 1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
# <span id="head89"> 初始化会话，并开始训练过程。</span>
with tf.Session() as sess:
    tf.global_variables_initializer().run()
    validate_feed = {x: mnist.validation.images, y_: mnist.validation.labels}
    test_feed = {x: mnist.test.images, y_: mnist.test.labels}
# <span id="head90"> 循环的训练神经网络。</span>
    for i in range(TRAINING_STEPS):
        if i % 1000 == 0:
            validate_acc = sess.run(accuracy, feed_dict=validate_feed)
            print("After %d training step(s), validation accuracy using average model is %g " % (i, validate_acc))
        xs, ys = mnist.train.next_batch(BATCH_SIZE)
        sess.run(train_op, feed_dict={x: xs, y_: ys})
    test_acc = sess.run(accuracy, feed_dict=test_feed)
    print(("After %d training step(s), test accuracy using average model is %g" % (TRAINING_STEPS, test_acc)))

```
#变量管理
```
import tensorflow as tf

#### <span id="head91">1. 在上下文管理器“foo”中创建变量“v”。</span>
with tf.variable_scope("foo"):
    v = tf.get_variable("v", [1], initializer=tf.constant_initializer(1.0))                        
#with tf.variable_scope("foo"):
# <span id="head92">v = tf.get_variable("v", [1])</span>
#重复Variable foo/v already exists, disallowed. Did you mean to set reuse=True or reuse=tf.AUTO_REUSE in VarScope?    
with tf.variable_scope("foo", reuse=True):
    v1 = tf.get_variable("v", [1])    
print( v == v1)#reuse为True可直接获得变量True
#with tf.variable_scope("bar", reuse=True):
# <span id="head93">v = tf.get_variable("v", [1])  在bar空间中没有v</span>

#### <span id="head94">2. 嵌套上下文管理器中的reuse参数的使用。</span>
with tf.variable_scope("root"):
    print tf.get_variable_scope().reuse   #False     
    with tf.variable_scope("foo", reuse=True):
        print tf.get_variable_scope().reuse   #True        
        with tf.variable_scope("bar"):
            print tf.get_variable_scope().reuse  #True             
   
#### <span id="head95">3. 通过variable_scope来管理变量。</span>
v1 = tf.get_variable("v", [1])
print(v1.name)          #v:0
with tf.variable_scope("foo"):
    with tf.variable_scope("bar"):
        v3 = tf.get_variable("v", [1])
        print(v3.name)   #foo/bar/v:0
        
#### <span id="head96">4. 我们可以通过变量的名称来获取变量。</span>
with tf.variable_scope("",reuse=True):
    v5 = tf.get_variable("foo/bar/v", [1])
    print v5 == v3
    v6 = tf.get_variable("v1", [1])     
    print v6 == v4#True  True
```
###优化mnist前向传播
```
def inference(input_tensor,reuse=False):
    with tf.variable_scope("layer1",reuse=reuse):
        weights=tf.get_variable("weights",[INPUT_NODE,LAYER1_NODE],initializer=tf.truncated_normal_initializer(stddev=0.1))
        biases=tf.getf_variable("biases",[LAYER1_NODE],initializer=tf.constant_initializer(0.0))
        layer1=tf.nn.relu(tf.matmul(input_tensor,weights)+biases)
    with tf.variable_scope("layer2",reuse=reuse):
        weights=tf.get_variable("weights",[LAYER1_NODE,OUTPUT_NODE],initializer=tf.truncated_normal_initializer(stddev=0.1))
        biases=tf.getf_variable("biases",[OUTPUT_NODE],initializer=tf.constant_initializer(0.0))
        layer2=tf.matmul(layer1,weights)+biases
        ruturn layer2
x=tf.placeholder(tf.float32,[None,INPUT_NODE],name='x-input')
y=inference(x)
#若要使用训练好的神经网络直接调用inference(new_x,True)
```

#ckpt文件保存
###model.ckpt.meta保存计算图的结构，model.ckpt保存了每一个变量的取值，checkpooint保存了一个目录下所有的模型文件列表
```
import tensorflow as tf

#### <span id="head97">1. 保存计算两个变量和的模型。</span>
v1 = tf.Variable(tf.constant(1.0, shape=[1]), name = "v1")
v2 = tf.Variable(tf.constant(2.0, shape=[1]), name = "v2")
result = v1 + v2
init_op = tf.global_variables_initializer()
saver = tf.train.Saver()
with tf.Session() as sess:
    sess.run(init_op)
    saver.save(sess, "Saved_model/model.ckpt")

#### <span id="head98">2. 加载保存了两个变量和的模型。</span>
with tf.Session() as sess:
    saver.restore(sess, "Saved_model/model.ckpt")
    print(sess.run(result))  

#### <span id="head99">3. 直接加载持久化的图。</span>
saver = tf.train.import_meta_graph("Saved_model/model.ckpt.meta")
with tf.Session() as sess:
    saver.restore(sess, "Saved_model/model.ckpt")
    print sess.run(tf.get_default_graph().get_tensor_by_name("add:0"))  #[ 3.]

#### <span id="head100">4. 变量重命名。</span>
v1 = tf.Variable(tf.constant(1.0, shape=[1]), name = "other-v1")
v2 = tf.Variable(tf.constant(2.0, shape=[1]), name = "other-v2")
saver = tf.train.Saver({"v1": v1, "v2": v2})
```

#滑动平均类的保存
```
#### <span id="head101">1. 使用滑动平均。</span>

v = tf.Variable(0, dtype=tf.float32, name="v")
for variables in tf.global_variables(): print (variables.name)  #v:0
ema = tf.train.ExponentialMovingAverage(0.99)
ema_op = ema.apply(tf.global_variables())
#声明滑动平均模型后会自动生成一个影子变量
for variables in tf.global_variables(): print (variables.name)  #v:0  v/ExponentialMovingAverage:0

#### <span id="head102">2. 保存滑动平均模型。</span>

saver = tf.train.Saver()
with tf.Session() as sess:
    init_op = tf.global_variables_initializer()
    sess.run(init_op)
    
    sess.run(tf.assign(v, 10))
    sess.run(maintain_averages_op)
# <span id="head103">保存的时候会将v:0  v/ExponentialMovingAverage:0这两个变量都存下来。</span>
    saver.save(sess, "Saved_model/model2.ckpt")
    print (sess.run([v, ema.average(v)]) ) #[10.0, 0.099999905]

#### <span id="head104">3. 加载滑动平均模型。</span>

v = tf.Variable(0, dtype=tf.float32, name="v")

# <span id="head105"> 通过变量重命名将原来变量v的滑动平均值直接赋值给v。</span>
saver = tf.train.Saver({"v/ExponentialMovingAverage": v})
with tf.Session() as sess:
    saver.restore(sess, "Saved_model/model2.ckpt")
    print (sess.run(v))  #0.099999905
```

#神经网络最佳样例
inference
```
import tensorflow as tf

#### <span id="head106">1. 定义神经网络结构相关的参数。</span>
INPUT_NODE = 784
OUTPUT_NODE = 10
LAYER1_NODE = 500

#### <span id="head107">2. 通过tf.get_variable函数来获取变量。</span>
def get_weight_variable(shape, regularizer):
    weights = tf.get_variable("weights", shape, initializer=tf.truncated_normal_initializer(stddev=0.1))
    if regularizer != None: tf.add_to_collection('losses', regularizer(weights))
    return weights

#### <span id="head108">3. 定义神经网络的前向传播过程。</span>
def inference(input_tensor, regularizer):
    with tf.variable_scope('layer1'):

        weights = get_weight_variable([INPUT_NODE, LAYER1_NODE], regularizer)
        biases = tf.get_variable("biases", [LAYER1_NODE], initializer=tf.constant_initializer(0.0))
        layer1 = tf.nn.relu(tf.matmul(input_tensor, weights) + biases)
        
    with tf.variable_scope('layer2'):
        weights = get_weight_variable([LAYER1_NODE, OUTPUT_NODE], regularizer)
        biases = tf.get_variable("biases", [OUTPUT_NODE], initializer=tf.constant_initializer(0.0))
        layer2 = tf.matmul(layer1, weights) + biases

    return layer2
```
train
```
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import mnist_inference
import os

#### <span id="head109">1. 定义神经网络结构相关的参数。</span>
BATCH_SIZE = 100
LEARNING_RATE_BASE = 0.8
LEARNING_RATE_DECAY = 0.99
REGULARIZATION_RATE = 0.0001
TRAINING_STEPS = 30000
MOVING_AVERAGE_DECAY = 0.99
MODEL_SAVE_PATH = "MNIST_model/"
MODEL_NAME = "mnist_model"

#### <span id="head110">2. 定义训练过程。</span>
def train(mnist):
# <span id="head111"> 定义输入输出placeholder。</span>
    x = tf.placeholder(tf.float32, [None, mnist_inference.INPUT_NODE], name='x-input')
    y_ = tf.placeholder(tf.float32, [None, mnist_inference.OUTPUT_NODE], name='y-input')

    regularizer = tf.contrib.layers.l2_regularizer(REGULARIZATION_RATE)
    y = mnist_inference.inference(x, regularizer)
    global_step = tf.Variable(0, trainable=False)

# <span id="head112"> 定义损失函数、学习率、滑动平均操作以及训练过程。</span>
    variable_averages = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
    variables_averages_op = variable_averages.apply(tf.trainable_variables())
    cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(y, tf.argmax(y_, 1))
    cross_entropy_mean = tf.reduce_mean(cross_entropy)
    loss = cross_entropy_mean + tf.add_n(tf.get_collection('losses'))
    learning_rate = tf.train.exponential_decay(
        LEARNING_RATE_BASE,
        global_step,
        mnist.train.num_examples / BATCH_SIZE, LEARNING_RATE_DECAY,
        staircase=True)
    train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
    with tf.control_dependencies([train_step, variables_averages_op]):
        train_op = tf.no_op(name='train')

# <span id="head113"> 初始化TensorFlow持久化类。</span>
    saver = tf.train.Saver()
    with tf.Session() as sess:
        tf.global_variables_initializer().run()

        for i in range(TRAINING_STEPS):
            xs, ys = mnist.train.next_batch(BATCH_SIZE)
            _, loss_value, step = sess.run([train_op, loss, global_step], feed_dict={x: xs, y_: ys})
            if i % 1000 == 0:
                print("After %d training step(s), loss on training batch is %g." % (step, loss_value))
                saver.save(sess, os.path.join(MODEL_SAVE_PATH, MODEL_NAME), global_step=global_step)

#### <span id="head114">3. 主程序入口。</span>
def main(argv=None):
    mnist = input_data.read_data_sets("../../../datasets/MNIST_data", one_hot=True)
    train(mnist)

if __name__ == '__main__':
    main()
```
evaluate
```
import time
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import mnist_inference
import mnist_train

#### <span id="head115">1. 每10秒加载一次最新的模型</span>
# <span id="head116"> 加载的时间间隔。</span>
EVAL_INTERVAL_SECS = 10
def evaluate(mnist):
    with tf.Graph().as_default() as g:
        x = tf.placeholder(tf.float32, [None, mnist_inference.INPUT_NODE], name='x-input')
        y_ = tf.placeholder(tf.float32, [None, mnist_inference.OUTPUT_NODE], name='y-input')
        validate_feed = {x: mnist.validation.images, y_: mnist.validation.labels}

        y = mnist_inference.inference(x, None)
        correct_prediction = tf.equal(tf.argmax(y, 1), tf.argmax(y_, 1))
        accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

        variable_averages = tf.train.ExponentialMovingAverage(mnist_train.MOVING_AVERAGE_DECAY)
        variables_to_restore = variable_averages.variables_to_restore()
        saver = tf.train.Saver(variables_to_restore)

        while True:
            with tf.Session() as sess:
                ckpt = tf.train.get_checkpoint_state(mnist_train.MODEL_SAVE_PATH)
                if ckpt and ckpt.model_checkpoint_path:
                    saver.restore(sess, ckpt.model_checkpoint_path)
                    global_step = ckpt.model_checkpoint_path.split('/')[-1].split('-')[-1]
                    accuracy_score = sess.run(accuracy, feed_dict=validate_feed)
                    print("After %s training step(s), validation accuracy = %g" % (global_step, accuracy_score))
                else:
                    print('No checkpoint file found')
                    return
            time.sleep(EVAL_INTERVAL_SECS)

### <span id="head117"> 主程序</span>
def main(argv=None):
    mnist = input_data.read_data_sets("../../../datasets/MNIST_data", one_hot=True)
    evaluate(mnist)

if __name__ == '__main__':
    main()
```














```
import tensorflow as tf
a=tf.constant(1,name='a')
b=tf.constant(1,name='a')
result = a + b
#查看a所在的图和默认图是否一样
print(a.graph is tf.get_default_graph())

#不同图中的张量和运算不共享
g1=tf.Graph()
#在图g1中定义变量v
with g1.as_default():
    v=tf.get_variable("v",shape=[1],initializer=tf.zeros_initializer)
with tf.Session(graph=g1) as sess:
    tf.global_variables_initializer().run()
    with tf.variable_scope("",reuse=True):
        print(sess.run(tf.get_variable("v")))

#制定运算的设备
g=tf.Graph()
with g.device('/gpu:0'):
    result=a+b

a = tf.constant([1.0, 2.0], name="a")
b = tf.constant([2.0, 3.0], name="b")
#Tensor("节点名称:当前张量来自节点的第几个输出", shape=(2,), dtype=float32)
print(result)

#指定默认会话
sess=tf.Session()
with sess.as_default():
    print(result.eval())
#其他两种
print(sess.run(result))
print(result.eval(session=sess))
#interactiveSession自动将生成的会话注册为默认会话
sess=tf.InteractiveSession()
print(result.eval())
#释放资源
sess.close()
#任何会话都可通过ConfigProto配置会话
config=tf.ConfigProto(allow_soft_placement=True,log_device_placement=True)#第一个参数为真代表GPU上的运算可放到CPU
#第二个参数代表日志会记录每个节点放在那个设备上
sess1=tf.Session(config=config)
```

#TFRecord生成

#####（1）tf.train.Example(features = None)
写入tfrecords文件
features ： tf.train.Features类型的特征实例
return ： example协议格式块
#####（2）tf.train.Features(feature = None)
构造每个样本的信息键值对
feature : 字典数据，key为要保存的名字，value为tf.train.Feature实例
return ： Features类型
#####（3）tf.train.Feature(**options) 
options可以选择如下三种格式数据：

bytes_list = tf.train.BytesList(value = [Bytes])
int64_list = tf.train.Int64List(value = [Value])
float_list = tf.trian.FloatList(value = [Value])
 ```
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import numpy as np
# 定义函数转化变量类型。
def _int64_feature(value):
    return tf.train.Feature(int64_list=tf.train.Int64List(value=[value]))

def _bytes_feature(value):
    return tf.train.Feature(bytes_list=tf.train.BytesList(value=[value]))

# 将数据转化为tf.train.Example格式。
def _make_example(pixels, label, image):
    image_raw = image.tostring()
    example = tf.train.Example(features=tf.train.Features(feature={
        'pixels': _int64_feature(pixels),
        'label': _int64_feature(np.argmax(label)),
        'image_raw': _bytes_feature(image_raw)
    }))
    return example

# 读取mnist训练数据。
mnist = input_data.read_data_sets("../../datasets/MNIST_data",dtype=tf.uint8, one_hot=True)
images = mnist.train.images
labels = mnist.train.labels
pixels = images.shape[1]
num_examples = mnist.train.num_examples

# 输出包含训练数据的TFRecord文件。
with tf.python_io.TFRecordWriter("output.tfrecords") as writer:
    for index in range(num_examples):
        example = _make_example(pixels, labels[index], images[index])
        writer.write(example.SerializeToString())
print("TFRecord训练文件已保存。")


# 读取文件。
reader = tf.TFRecordReader()
filename_queue = tf.train.string_input_producer(["output.tfrecords"])
_,serialized_example = reader.read(filename_queue)

# 解析读取的样例。   parse_exampele解析多个数据
features = tf.parse_single_example(
    serialized_example,
    features={
        'image_raw':tf.FixedLenFeature([],tf.string),
        'pixels':tf.FixedLenFeature([],tf.int64),
        'label':tf.FixedLenFeature([],tf.int64)
    })

images = tf.decode_raw(features['image_raw'],tf.uint8)
labels = tf.cast(features['label'],tf.int32)
pixels = tf.cast(features['pixels'],tf.int32)

sess = tf.Session()

# 启动多线程处理输入数据。
coord = tf.train.Coordinator()
threads = tf.train.start_queue_runners(sess=sess,coord=coord)

for i in range(10):
    image, label, pixel = sess.run([images, labels, pixels])
    print(image.shape, label, pixel) # (784,) 7 784  (784,) 3 784  ......
```
