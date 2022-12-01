**训练复杂的卷积神经网络需要非常多的标注数据。**

**所谓迁移学习，就是讲一个问题上训练好的模型通过简单的调整使其适用于一个新的问题。**

根据论文DeCAF:A Deep Convolutional Activation Feature for Generic Visual Recognition中的结论，**可以保留训练好的Inception-v3模型中所有卷积层的参数，只是替换最后一层全连接层。在最后这一层全连接之前的网络层称之为瓶颈层（bottleneck）。**

**将新的图像通过训练好的卷积神经网络直到瓶颈处的过程可以看成事对图像进行特征提取的过程。**在训练好的Inception-v3模型中，因为将瓶颈处的输出再通过一个单层的全连接层神经网络可以很好地区分1000种类别的图像，**所以有理由认为瓶颈处输出的节点向量可以被作为任何图像的一个更加精简且表达能力更强的特征向量。**

![](https://upload-images.jianshu.io/upload_images/18339009-af8e9229f8b647d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
蓝色点，卷积层间有联合依赖和适应性，不能破坏
![](https://upload-images.jianshu.io/upload_images/18339009-27bf894fcf3fe706.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
























输入数据文件夹包含了5个子文件夹，每一个子文件的名称为一种花的名称，代表不同的类别。平均每一种花有734张图片，每一张图片都是RGB色彩模式的，大小也不相同。和之前的样例不同，在这一节中给出的程序将直接处理没有整理过的图像数据。

```
import glob
import os.path
import random
import numpy as np
import tensorflow as tf
from tensorflow.python.platform import gfile

# 模型和样本路径的设置
BOTTLENECK_TENSOR_SIZE = 2048
BOTTLENECK_TENSOR_NAME = 'pool_3/_reshape:0'
JPEG_DATA_TENSOR_NAME = 'DecodeJpeg/contents:0'

MODEL_DIR = '../../datasets/inception_dec_2015'
MODEL_FILE= 'tensorflow_inception_graph.pb'

CACHE_DIR = '../../datasets/bottleneck'
INPUT_DATA = '../../datasets/flower_photos'

VALIDATION_PERCENTAGE = 10
TEST_PERCENTAGE = 10

# 神经网络参数的设置
LEARNING_RATE = 0.01
STEPS = 4000
BATCH = 100


# 把样本中所有的图片列表并按训练、验证、测试数据分开
def create_image_lists(testing_percentage, validation_percentage):
    result = {}
    sub_dirs = [x[0] for x in os.walk(INPUT_DATA)]
    is_root_dir = True
    for sub_dir in sub_dirs:
        if is_root_dir:
            is_root_dir = False
            continue

        extensions = ['jpg', 'jpeg', 'JPG', 'JPEG']

        file_list = []
        dir_name = os.path.basename(sub_dir)
        for extension in extensions:
            file_glob = os.path.join(INPUT_DATA, dir_name, '*.' + extension)
            file_list.extend(glob.glob(file_glob))
        if not file_list: continue

        label_name = dir_name.lower()
        
        # 初始化
        training_images = []
        testing_images = []
        validation_images = []
        for file_name in file_list:
            base_name = os.path.basename(file_name)
            
            # 随机划分数据
            chance = np.random.randint(100)
            if chance < validation_percentage:
                validation_images.append(base_name)
            elif chance < (testing_percentage + validation_percentage):
                testing_images.append(base_name)
            else:
                training_images.append(base_name)

        result[label_name] = {
            'dir': dir_name,
            'training': training_images,
            'testing': testing_images,
            'validation': validation_images,
            }
    return result


# 定义函数通过cache/input路径、类别名称、图片编号和所属数据集获取一张图片的bottleneck/input地址。
def get_image_path(image_lists, image_dir, label_name, index, category):
    label_lists = image_lists[label_name]
    category_list = label_lists[category]
    mod_index = index % len(category_list)
    base_name = category_list[mod_index]
    sub_dir = label_lists['dir']
    full_path = os.path.join(image_dir, sub_dir, base_name)
    return full_path


#  定义函数获取Inception-v3模型处理之后的特征向量的文件地址。
def get_bottleneck_path(image_lists, label_name, index, category):
    return get_image_path(image_lists, CACHE_DIR, label_name, index, category) + '.txt'


# 定义函数使用加载的训练好的Inception-v3模型处理一张图片，得到这个图片的特征向量。
def process_image_to_bottleneck(sess, image_data, image_data_tensor, bottleneck_tensor):

    bottleneck_values = sess.run(bottleneck_tensor, {image_data_tensor: image_data})

    bottleneck_values = np.squeeze(bottleneck_values)
    return bottleneck_values


# 定义函数先试图寻找已经计算且保存下来的特征向量，如果找不到则先计算这个特征向量，然后保存到文件。
def get_or_create_bottleneck(sess, image_lists, label_name, index, category, jpeg_data_tensor, bottleneck_tensor):
    # 得到子文件夹图像列表
    label_lists = image_lists[label_name]
    sub_dir = label_lists['dir']
    sub_dir_path = os.path.join(CACHE_DIR, sub_dir)
    # 如果cache文件夹中中不存在该子目录则新建
    if not os.path.exists(sub_dir_path): os.makedirs(sub_dir_path)
    # 获取Inception-v3模型处理之后的特征向量的文件地址
    bottleneck_path = get_bottleneck_path(image_lists, label_name, index, category)
    # 如果未处理过将原始图片数据送入模型进行处理;并将每个数据用逗号分开
    if not os.path.exists(bottleneck_path):
        image_path = get_image_path(image_lists, INPUT_DATA, label_name, index, category)
        image_data = gfile.FastGFile(image_path, 'rb').read()
        bottleneck_values = process_image_to_bottleneck(sess, image_data, jpeg_data_tensor, bottleneck_tensor)
        bottleneck_string = ','.join(str(x) for x in bottleneck_values)
        with open(bottleneck_path, 'w') as bottleneck_file:
            bottleneck_file.write(bottleneck_string)
    # 如果处理过直接读取
    else:
        with open(bottleneck_path, 'r') as bottleneck_file:
            bottleneck_string = bottleneck_file.read()
        bottleneck_values = [float(x) for x in bottleneck_string.split(',')]
    return bottleneck_values


# 随机获取数据。
def get_random_cached_bottlenecks(sess, image_lists, n_classes, how_many, category, jpeg_data_tensor, bottleneck_tensor):
    """
    label is subdir_name.lower()
    train,validate获取随机一个batch的图片,test获得全部图片
    """
    
    bottlenecks = []
    ground_truths = []
    if category == 'testing':
        label_name_list = list(image_lists.keys())
        for label_index, label_name in enumerate(label_name_list):
            for image_index, _ in enumerate(image_lists[label_name][category]):
                 makedata()
    else:
        for _ in range(how_many):
            label_index = random.randrange(n_classes)
            label_name = list(image_lists.keys())[label_index]
            image_index = random.randrange(65536)
            makedata()
    def makedata():
        bottleneck = get_or_create_bottleneck(sess, image_lists, label_name, image_index, category, jpeg_data_tensor, bottleneck_tensor)
        ground_truth = np.zeros(n_classes, dtype=np.float32)
        ground_truth[label_index] = 1.0
        bottlenecks.append(bottleneck)
        ground_truths.append(ground_truth)
    return bottlenecks, ground_truths


# 定义主函数。
def main(_):
    # 整理好的数据
    image_lists = create_image_lists(TEST_PERCENTAGE, VALIDATION_PERCENTAGE)
    # 子文件夹的数量
    n_classes = len(image_lists.keys())
    
    # 读取已经训练好的Inception-v3模型。
    # tf.gfile.FastGFile(path,decodestyle) 函数功能：实现对图片的读取。 函数参数：(1)path：图片所在路径 (2)decodestyle:图片的解码方式。(‘r’:UTF-8编码; ‘rb’:非UTF-8编码)
    with gfile.FastGFile(os.path.join(MODEL_DIR, MODEL_FILE), 'rb') as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())
    # 得到的与return_element中的名称相对应的操作和/或张量对象的列表
    bottleneck_tensor, jpeg_data_tensor = tf.import_graph_def(
        graph_def, return_elements=[BOTTLENECK_TENSOR_NAME, JPEG_DATA_TENSOR_NAME])

    # 定义新的神经网络输入
    bottleneck_input = tf.placeholder(tf.float32, [None, BOTTLENECK_TENSOR_SIZE], name='BottleneckInputPlaceholder')
    ground_truth_input = tf.placeholder(tf.float32, [None, n_classes], name='GroundTruthInput')
    
    # 定义一层全链接层
    with tf.name_scope('final_training_ops'):
        weights = tf.Variable(tf.truncated_normal([BOTTLENECK_TENSOR_SIZE, n_classes], stddev=0.001))
        biases = tf.Variable(tf.zeros([n_classes]))
        logits = tf.matmul(bottleneck_input, weights) + biases
        final_tensor = tf.nn.softmax(logits)
        
    # 定义交叉熵损失函数。
    cross_entropy = tf.nn.softmax_cross_entropy_with_logits(logits=logits, labels=ground_truth_input)
    cross_entropy_mean = tf.reduce_mean(cross_entropy)
    train_step = tf.train.GradientDescentOptimizer(LEARNING_RATE).minimize(cross_entropy_mean)
    
    # 计算正确率。
    with tf.name_scope('evaluation'):
        correct_prediction = tf.equal(tf.argmax(final_tensor, 1), tf.argmax(ground_truth_input, 1))
        evaluation_step = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

    with tf.Session() as sess:
        init = tf.global_variables_initializer()
        sess.run(init)
        # 训练过程。
        for i in range(STEPS):
            
            train_bottlenecks, train_ground_truth = get_random_cached_bottlenecks(
                sess, image_lists, n_classes, BATCH, 'training', jpeg_data_tensor, bottleneck_tensor)
            sess.run(train_step, feed_dict={bottleneck_input: train_bottlenecks, ground_truth_input: train_ground_truth})

            if i % 100 == 0 or i + 1 == STEPS:
                validation_bottlenecks, validation_ground_truth = get_random_cached_bottlenecks(
                    sess, image_lists, n_classes, BATCH, 'validation', jpeg_data_tensor, bottleneck_tensor)
                validation_accuracy = sess.run(evaluation_step, feed_dict={
                    bottleneck_input: validation_bottlenecks, ground_truth_input: validation_ground_truth})
                print('After %d steps: Validation accuracy on random sampled %d examples = %.1f%%' %
                    (i, BATCH, validation_accuracy * 100))
            
        # 在最后的测试数据上测试正确率。
        test_bottlenecks, test_ground_truth = get_random_cached_bottlenecks(
            sess, image_lists, n_classes, BATCH, 'testing', jpeg_data_tensor, bottleneck_tensor)
        test_accuracy = sess.run(evaluation_step, feed_dict={
            bottleneck_input: test_bottlenecks, ground_truth_input: test_ground_truth})
        print('Final test accuracy = %.1f%%' % (test_accuracy * 100))

if __name__ == '__main__':
    main(_)
```


```
### 1. 定义需要使用到的常量

import glob
import os.path
import numpy as np
import tensorflow as tf
from tensorflow.python.platform import gfile

# 原始输入数据的目录，这个目录下有5个子目录，每个子目录底下保存这属于该
# 类别的所有图片。
INPUT_DATA = '../../datasets/flower_photos'
# 输出文件地址。我们将整理后的图片数据通过numpy的格式保存。
OUTPUT_FILE = '../../datasets/flower_processed_data.npy'

# 测试数据和验证数据比例。
VALIDATION_PERCENTAGE = 10
TEST_PERCENTAGE = 10

### 2. 定义数据处理过程

# 读取数据并将数据分割成训练数据、验证数据和测试数据。
def create_image_lists(sess, testing_percentage, validation_percentage):
    sub_dirs = [x[0] for x in os.walk(INPUT_DATA)]
    is_root_dir = True
    
    # 初始化各个数据集。
    training_images = []
    training_labels = []
    testing_images = []
    testing_labels = []
    validation_images = []
    validation_labels = []
    current_label = 0
    
    # 读取所有的子目录。
    for sub_dir in sub_dirs:
        if is_root_dir:
            is_root_dir = False
            continue

        # 获取一个子目录中所有的图片文件。
        extensions = ['jpg', 'jpeg', 'JPG', 'JPEG']
        file_list = []
        dir_name = os.path.basename(sub_dir)
        for extension in extensions:
            file_glob = os.path.join(INPUT_DATA, dir_name, '*.' + extension)
            file_list.extend(glob.glob(file_glob))
        if not file_list: continue
        print("processing:", dir_name)
        
        i = 0
        # 处理图片数据。
        for file_name in file_list:
            i += 1
            # 读取并解析图片，将图片转化为299*299以方便inception-v3模型来处理。
            image_raw_data = gfile.FastGFile(file_name, 'rb').read()
            image = tf.image.decode_jpeg(image_raw_data)
            if image.dtype != tf.float32:
                image = tf.image.convert_image_dtype(image, dtype=tf.float32)
            image = tf.image.resize_images(image, [299, 299])
            image_value = sess.run(image)
            
            # 随机划分数据聚。
            chance = np.random.randint(100)
            if chance < validation_percentage:
                validation_images.append(image_value)
                validation_labels.append(current_label)
            elif chance < (testing_percentage + validation_percentage):
                testing_images.append(image_value)
                testing_labels.append(current_label)
            else:
                training_images.append(image_value)
                training_labels.append(current_label)
            if i % 200 == 0:
                print(i, "images processed.")
        current_label += 1
    
    # 将训练数据随机打乱以获得更好的训练效果。
    state = np.random.get_state()
    np.random.shuffle(training_images)
    np.random.set_state(state)
    np.random.shuffle(training_labels)
    
    return np.asarray([training_images, training_labels,
                       validation_images, validation_labels,
                       testing_images, testing_labels])

### 3. 运行数据处理过程

with tf.Session() as sess:
    processed_data = create_image_lists(sess, TEST_PERCENTAGE, VALIDATION_PERCENTAGE)
    # 通过numpy格式保存处理后的数据。
    np.save(OUTPUT_FILE, processed_data)

### 1. 定义训练过程中将要使用到的常量。
**因为GitHub无法保存大于100M的文件，所以在运行时需要先自行从Google下载inception_v3.ckpt文件。**

import glob
import os.path
import numpy as np
import tensorflow as tf
from tensorflow.python.platform import gfile
import tensorflow.contrib.slim as slim

# 加载通过TensorFlow-Slim定义好的inception_v3模型。
import tensorflow.contrib.slim.python.slim.nets.inception_v3 as inception_v3

# 处理好之后的数据文件。
INPUT_DATA = '../../datasets/flower_processed_data.npy'
# 保存训练好的模型的路径。
TRAIN_FILE = 'train_dir/model'
# 谷歌提供的训练好的模型文件地址。因为GitHub无法保存大于100M的文件，所以
# 在运行时需要先自行从Google下载inception_v3.ckpt文件。
CKPT_FILE = '../../datasets/inception_v3.ckpt'

# 定义训练中使用的参数。
LEARNING_RATE = 0.0001
STEPS = 300
BATCH = 32
N_CLASSES = 5

# 不需要从谷歌训练好的模型中加载的参数。
CHECKPOINT_EXCLUDE_SCOPES = 'InceptionV3/Logits,InceptionV3/AuxLogits'
# 需要训练的网络层参数明层，在fine-tuning的过程中就是最后的全联接层。
TRAINABLE_SCOPES='InceptionV3/Logits,InceptionV3/AuxLogit'

### 2. 获取所有需要从谷歌训练好的模型中加载的参数。

def get_tuned_variables():
    # 不需要从谷歌训练好的模型中加载的参数。
    exclusions = [scope.strip() for scope in CHECKPOINT_EXCLUDE_SCOPES.split(',')]

    variables_to_restore = []
    # 枚举inception-v3模型中所有的参数，然后判断是否需要从加载列表中移除。
    for var in slim.get_model_variables():
        excluded = False
        for exclusion in exclusions:
            if var.op.name.startswith(exclusion):
                excluded = True
                break
        if not excluded:
            variables_to_restore.append(var)
    return variables_to_restore

### 3. 获取所有需要训练的变量列表。

def get_trainable_variables():    
    scopes = [scope.strip() for scope in TRAINABLE_SCOPES.split(',')]
    variables_to_train = []
    
    # 枚举所有需要训练的参数前缀，并通过这些前缀找到所有需要训练的参数。
    for scope in scopes:
        variables = tf.get_collection(tf.GraphKeys.TRAINABLE_VARIABLES, scope)
        variables_to_train.extend(variables)
    return variables_to_train

### 4. 定义训练过程。

def main():
    # 加载预处理好的数据。
    processed_data = np.load(INPUT_DATA)
    training_images = processed_data[0]
    n_training_example = len(training_images)
    training_labels = processed_data[1]
    
    validation_images = processed_data[2]
    validation_labels = processed_data[3]
    
    testing_images = processed_data[4]
    testing_labels = processed_data[5]
    print("%d training examples, %d validation examples and %d testing examples." % (
        n_training_example, len(validation_labels), len(testing_labels)))

    # 定义inception-v3的输入，images为输入图片，labels为每一张图片对应的标签。
    images = tf.placeholder(tf.float32, [None, 299, 299, 3], name='input_images')
    labels = tf.placeholder(tf.int64, [None], name='labels')
    
    # 定义inception-v3模型。因为谷歌给出的只有模型参数取值，所以这里
    # 需要在这个代码中定义inception-v3的模型结构。虽然理论上需要区分训练和
    # 测试中使用到的模型，也就是说在测试时应该使用is_training=False，但是
    # 因为预先训练好的inception-v3模型中使用的batch normalization参数与
    # 新的数据会有出入，所以这里直接使用同一个模型来做测试。
    with slim.arg_scope(inception_v3.inception_v3_arg_scope()):
        logits, _ = inception_v3.inception_v3(
            images, num_classes=N_CLASSES, is_training=True)
    
    trainable_variables = get_trainable_variables()
    # 定义损失函数和训练过程。
    tf.losses.softmax_cross_entropy(tf.one_hot(labels, N_CLASSES), logits, weights=1.0)
    total_loss = tf.losses.get_total_loss()
    train_step = tf.train.RMSPropOptimizer(LEARNING_RATE).minimize(total_loss)
    
    # 计算正确率。
    with tf.name_scope('evaluation'):
        correct_prediction = tf.equal(tf.argmax(logits, 1), labels)
        evaluation_step = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
                
    # 定义加载Google训练好的Inception-v3模型的Saver。
    load_fn = slim.assign_from_checkpoint_fn(
      CKPT_FILE,
      get_tuned_variables(),
      ignore_missing_vars=True)
    
    # 定义保存新模型的Saver。
    saver = tf.train.Saver()
    
    with tf.Session() as sess:
        # 初始化没有加载进来的变量，一定要在模型加载之前否则会重新赋值
        init = tf.global_variables_initializer()
        sess.run(init)
        
        # 加载谷歌已经训练好的模型。
        print('Loading tuned variables from %s' % CKPT_FILE)
        load_fn(sess)
            
        start = 0
        end = BATCH
        for i in range(STEPS):            
            _, loss = sess.run([train_step, total_loss], feed_dict={
                images: training_images[start:end], 
                labels: training_labels[start:end]})

            if i % 30 == 0 or i + 1 == STEPS:
                saver.save(sess, TRAIN_FILE, global_step=i)
                
                validation_accuracy = sess.run(evaluation_step, feed_dict={
                    images: validation_images, labels: validation_labels})
                print('Step %d: Training loss is %.1f Validation accuracy = %.1f%%' % (
                    i, loss, validation_accuracy * 100.0))
                            
            start = end
            if start == n_training_example:
                start = 0
            
            end = start + BATCH
            if end > n_training_example: 
                end = n_training_example
            
        # 在最后的测试数据上测试正确率。
        test_accuracy = sess.run(evaluation_step, feed_dict={
            images: testing_images, labels: testing_labels})
        print('Final test accuracy = %.1f%%' % (test_accuracy * 100))

### 5. 运行训练过程。

if __name__ == '__main__':
    main()
```

迁移学习，是指利用数据、任务、或模型之间的相似性，将在旧领域学习过的模型，应用于新领域的一种学习过程。

迁移学习最权威的综述文章是香港科技大学杨强教授团队的A survey on transfer learning[Pan and Yang, 2010]。

# 大数据与少标注之间的矛盾。

尽管我们可以获取到海量的数据，这些数据往往是很初级的原始形态，很少有数据被加以正确的人工标注。数据的标注是一个耗时且昂贵的操作，目前为止，尚未有行之有效的方式来解决这一问题。这给机器学习和深度学习的模型训练和更新带来了挑战。反过来说，特定的领域，因为没有足够的标定数据用来学习，使得这些领域一直不能很好的发展。

**迁移数据标注，利用迁移学习的思想，我们可以寻找一些与目标数据相近的有标注的数据，从而利用这**
**些数据来构建模型，增加我们目标数据的标注。**

# 大数据与弱计算之间的矛盾。

绝大多数普通用户是不可能具有这些强计算能力的。这就引发了大数据和弱计算之间的矛盾。

**模型迁移，利用迁移学习的思想，我们可以将那些大公司在大数据上训练好的模型，迁移到我们的任务中。针对于我们的任务进行微调，从而我们也可以拥有在大数据上训练好的模型。**

# 普适化模型与个性化需求之间的矛盾。

目前的情况是，我们对于每一个通用的任务都构建了一个通用的模型。这个模型可以解决绝大多数的公共问题。但是具体到每个个体、每个需求，都存在其唯一性和特异性，一个普适化的通用模型根本无法满足。

**自适应学习，我们利用迁移学习的思想，进行自适应的学习。考虑到不同用户之间的相似性和差异性，我们对普适化模型进行灵活的调整，以便完成我们的任务。**

# 特定应用的需求。

比如推荐系统的冷启动问题。一个新的推荐系统，没有足够
的用户数据，如何进行精准的推荐? 一个崭新的图片标注系统，没有足够的标签，如何进行
精准的服务？现实世界中的应用驱动着我们去开发更加便捷更加高效的机器学习方法来加
以解决。

**为了满足特定领域应用的需求，我们可以利用上述介绍过的手段，从数据和模型方法上**
**进行迁移学习。**

| 比较项目 | 传统机器学习 | 迁移学习|
| -------- | ------------ | ------------------------|
| 数据分布 | 训练和测试数据服从相同的分布 | 训练和测试数据服从不同的分布 |
| 数据标注 | 需要足够的数据标注来训练模型 | 不需要足够的数据标注 |
| 模型 | 每个任务分别建模模型 | 可以在不同任务之间迁移 |



- 按照目标领域有无标签，迁移学习可以分为以下三个大类：

1. 监督迁移学习(Supervised Transfer Learning)
2. 半监督迁移学习(Semi-Supervised Transfer Learning)
3. 无监督迁移学习(Unsupervised Transfer Learning)

- 按学习方法的分类形式，最早在迁移学习领域的权威综述文章[Pan and Yang, 2010] 给出定义。它将迁移学习方法分为以下四个大类：

1. 基于样本的迁移学习方法(Instance based Transfer Learning)，简单来说就是通过权重重用，对源域和目标域的样例进行迁移。就是说直接对不同的样本赋予不同权重，比如说相似的样本，我就给它高权重，这样我就完成了迁移，非常简单非常非常直接。
2. 基于特征的迁移学习方法(Feature based Transfer Learning)，，就是更进一步对特征进行变换。意思是说，假设源域和目标域的特征原来不在一个空间，或者说它们在原来那个空间上不相似，那我们就想办法把它们变换到一个空间里面，那这些特征不就相似了？这个思路也非常直接。这个方法是用得非常多的，一直在研究，目前是感觉是研究最热的。
3. 基于模型的迁移学习方法(Model based Transfer Learning)，就是说构建参数共享的模型。这个主要就是在神经网络里面用的特别多，因为神经网络的结构可以直接进行迁移。比如说神经网络最经典的finetune 就是模型参数迁移的很好的体现。
4. 基于关系的迁移学习方法(Relation based Transfer Learning)，，这个方法用的比较少，这个主要就是说挖掘和利用关系进行类比迁移。比如老师上课、学生听课就可以类比为公司开会的场景。



- 按照特征的属性进行分类，也是一种常用的分类方法。这在最近的迁移学习综述[Weiss et al., 2016]
  中给出。按照特征属性，迁移学习可以分为两个大类：

1. 同构迁移学习(Homogeneous Transfer Learning)
2. 异构迁移学习(Heterogeneous Transfer Learning)

如果特征语义和维度都相同，那么就是同构；反之，如果特征完全不相同，那么就是异构。举个例子来说，不同图片的迁移，就可以认为是同构；而图片到文本的迁移，则是异构的。

**时间序列行为识别(Activity Recognition) 主要通过佩戴在用户身体上的传感器，研究用户的行**
**为。行为数据是一种时间序列数据。不同用户、不同环境、不同位置、不同设备，都会导致**
**时间序列数据的分布发生变化。此时，也需要进行迁移学习。图12展示了同一用户不同位**
**置的信号差异性。在这个领域，华盛顿州立大学的Diane Cook 等人在2013 年发表的关于**
**迁移学习在行为识别领域的综述文章[Cook et al., 2013] 是很好的参考资料。**

领域(Domain): 是进行学习的主体。领域主要由两部分构成：数据和生成这些数据的概率分布。通常我们用花体D 来表示一个domain，用大写斜体P 来表示一个概率分布。

任务(Task): 是学习的目标。任务主要由两部分组成：标签和标签对应的函数。通常我们用花体Y 来表示一个标签空间，用f(*) 来表示一个学习函数。

找到相似性(不变量)，是进行迁移学习的核心。有了这种相似性后，下一步工作就是，如何度量和利用这种相似性。度量工作的目标有两点：一是很好地度量两个领域的相似性，不仅定性地告诉我们它们是否相似，更定量地给出相似程度。二是以度量为准则，通过我们所要采用的学习手段，增大两个领域之间的相似性，从而完成迁移学习。

# 深度迁移学习

由于深度学习直接对原始数据进行学习，所以其对比非深度方法还有两个优势：自动化
地提取更具表现力的特征，以及满足了实际应用中的端到端(End-to-End) 需求。.

深度迁移学习方法(BA、DDC、DAN) 对比传统迁移学习方法(TCA、GFK 等)，在精度上具有无可匹敌的优势。

深度神经网络前面几层都学习到的是通用的特征（general feature）；随着网络层次的加深，后面的网络更偏重于学习任务特定的特征（specific feature）。这非常好理解，我们也都很好接受。那么问题来了：如何得知哪些层能够学习到general feature，哪些层能够学习到specific feature。更进一步：如果应用于迁移学习，如何决定该迁移哪些层、固定哪些层？





#一个典型的迁移学习过程是这样的。**首先通过transfer learning对新的数据集进行训练，训练过一定epoch之后，改用fine tune方法继续训练，同时降低学习率**。这样做是因为如果一开始就采用fine tune方法的话，网络还没有适应新的数据，那么在进行参数更新的时候，比较大的梯度可能会导致原本训练的比较好的参数被污染，反而导致效果下降。借助setup_to_transfer_learning与setup_to_fine_tune这两个函数，我们先只训练模型的顶层，再训练模型的大多数层，进而在提高模型训练效果的同时，降低训练时间。

# [例子](https://blog.csdn.net/juezhanangle/article/details/78747252)


第一种即所谓的**transfer learning**，迁移训练时，移掉最顶层，*比如ImageNet训练任务的顶层就是一个1000输出的全连接层，换上新的顶层*，*比如输出为10的全连接层，然后**训练的时候，只训练最后两层，即原网络的倒数第二层和新换的全连接输出层*。可以说transfer learning将底层的网络当做了一个特征提取器来使用。

第二种叫做**fine tune**，和transfer learning一样，*换一个新的顶层，但是这一次在训练的过程中，所有的（或大部分）其它层都会经过训练。也就是底层的权重也会随着训练进行调整。*


模型的预训练权重将下载到~/.keras/models/并在载入模型时自动载入。模型的官方下载路径：https://github.com/fchollet/deep-learning-models/releases

**notop**：指模型不包含最后的3个全连接层。用来做fine-tuning专用，专门开源了这类模型。

```python
keras.applications. mobilenet. MobileNet (
	include_top=True,   # 是否保留顶层的3个全连接网络  pop函数，去掉最后一层。1old_model.layers.pop()
	weights='imagenet',  # None代表随机初始化，即不加载预训练权重。'imagenet'代表加载预训练权重
    input_tensor=None,  # 可填入Keras tensor作为模型的图像输出tensor
    input_shape=None,  # 可选，仅当include_top=False有效，应为长为3的tuple，指明输入图片的shape，图片的宽高必须大于71，如(150,150,3)
    pooling=None,   # 当include_top=False时，该参数指定了池化方式。None代表不池化，最后一个卷积层的输出为4D张量。‘avg’代表全局平均池化，‘max’代表全局最大值池化。
    classes=1000  # 可选，图片分类的类别数，仅当include_top=True并且不加载预训练权重时可用。)
```

```python
from keras.applications.mobilenet import MobileNet
##方式（1）
base_model = MobileNet(weights='imagenet',include_top=False)
##方式（2）
base_model = MobileNet(weights='G:\mobilenet_1_0_128_tf_no_top.h5')
x = base_model.output
x = GlobalAveragePooling2D()(x)
x = Dense(1024,activation='relu')(x) #we add dense layers so that the model can learn more complex functions and classify for better results.
x = Dense(1024,activation='relu')(x) #dense layer 2
x = Dense(512,activation='relu')(x) #dense layer 3
preds = Dense(3,activation='softmax')(x) #final layer with softmax activation

model = Model(inputs=base_model.input,outputs=preds)
model.summary()

print(len(model.layers))
for layer in model.layers[:20]:
    layer.trainable=False
for layer in model.layers[20:]:
    layer.trainable=True
    
from keras.preprocessing.image import img_to_array, array_to_img
def preprocess_input_new(x):
    img = preprocess_input(img_to_array(x))
    return array_to_img(img)
# train_datagen = ImageDataGenerator(rescale = 1./255,horizontal_flip = True,fill_mode = "nearest",zoom_range = 0.3,width_shift_range = 0.3,height_shift_range=0.3,rotation_range=30)
train_datagen=ImageDataGenerator(preprocessing_function=preprocess_input_new) 


#included in our dependencies
train_generator=train_datagen.flow_from_directory('./train/', #  data folder
                                                 target_size=(224,224),
                                                 color_mode='rgb',
                                                 batch_size=32,
                                                 class_mode='categorical',
                                                 shuffle=True)
test_generator = gen.flow_from_directory("test", 
                                         image_size, 
                                         shuffle=False,
                                         batch_size=batch_size, 
                                         class_mode=None)  # 测试集由于没有label，生成test_generator的函数需加参数class_mode=None。
# 早停法是一种被广泛使用的方法，在很多案例上都比正则化的方法要好。是在训练中计算模型在验证集上的表现，当模型在验证集上的表现开始下降的时候，停止训练，这样就能避免继续训练导致过拟合的问题。其主要步骤如下：
# 1. 将原始的训练数据集划分成训练集和验证集
# 2. 只在训练集上进行训练，并每隔一个周期计算模型在验证集上的误差
# 3. 当模型在验证集上（权重的更新低于某个阈值；预测的错误率低于某个阈值；达到一定的迭代次数），则停止训练
# 4. 使用上一次迭代结果中的参数作为模型的最终参数
checkpoint = ModelCheckpoint("vgg16_1.h5",
                             monitor='val_acc',
                             verbose=1, 
                             save_best_only=True,
                             save_weights_only=False, 
                             mode='auto', 
                             period=1)
early = EarlyStopping(monitor='val_acc', 
                      min_delta=0, 
                      patience=10, 
                      verbose=1, 
                      mode='auto')    
model.compile(optimizer='Adam',loss='categorical_crossentropy',metrics=['accuracy'])
step_size_train=train_generator.n//train_generator.batch_size
model.fit_generator(generator=train_generator,
                   steps_per_epoch=step_size_train,
                   epochs=5，
                   validation_data = validation_generator,
                   callbacks = [checkpoint, early])
```
# 借助setup_to_transfer_learning与setup_to_fine_tune这两个函数，我们先只训练模型的顶层，再训练模型的大多数层，进而在提高模型训练效果的同时，降低训练时间。
```
def setup_to_transfer_learning(model,base_model):
	# 这个函数将base_model的所有层都设置为不可训练，顶层默认为可训练。
    for layer in base_model.layers:
         layer.trainable = False
     model.compile(optimizer='adam',
                   loss='categorical_crossentropy',
                   metrics=['accuracy'])
 
def setup_to_fine_tune(model,base_model):
	# 这个函数将base_model中的前几层设置为不可训练，后面的所有层都设置为可训练。具体应确定到第几层，还需通过模型结构与不断调试来确定。
	GAP_LAYER = 17 
    for layer in base_model.layers[:GAP_LAYER+1]:
    	layer.trainable = False
    for layer in base_model.layers[GAP_LAYER+1:]:
        layer.trainable = True
    model.compile(optimizer=Adagrad(lr=0.0001),
                  loss='categorical_crossentropy',
                  metrics=['accuracy'])

setup_to_transfer_learning(model,base_model)
history_tl = model.fit_generator(generator=train_generator,
                                 steps_per_epoch=800,
                                 epochs=2,
                                 validation_data=val_generator,
                                 validation_steps=12,
                                 class_weight='auto')
model.save('./flowers17_iv3_tl.h5')
setup_to_fine_tune(model,base_model)
history_ft = model.fit_generator(generator=train_generator,
                                 steps_per_epoch=800,
                                 epochs=2,
                                 validation_data=val_generator,
                                 validation_steps=1,
                                 class_weight='auto')
model.save('./flowers17_iv3_ft.h5')
```

（2）加载权重到不同的网络结构

```python
#old model
model = Sequential()
model.add(Dense(2, input_dim=3, name="dense_1"))
model.add(Dense(3, name="dense_2"))
model.save_weights(fname)
# new model
model = Sequential()
model.add(Dense(2, input_dim=3, name="dense_1"))  # will be loaded
model.add(Dense(10, name="new_dense"))  # will not be loaded
# load weights from first model; will only affect the first layer, dense_1.
model.load_weights(fname, by_name=True)


layer_dict = dict([(layer.name, layer) for layer in model.layers])
import h5py
weights_path = 'vgg19_weights.h5' 
# ('https://github.com/fchollet/deep-learning-models/releases/download/v0.1/vgg19_weights_tf_dim_ordering_tf_kernels.h5)
f = h5py.File(weights_path)
list(f["model_weights"].keys())
layer_names = [layer.name for layer in model.layers]
for i in layer_dict.keys():
    weight_names = f["model_weights"][i].attrs["weight_names"]
    weights = [f["model_weights"][i][j] for j in weight_names]
    index = layer_names.index(i)
    model.layers[index].set_weights(weights)
```

**新数据集较小，和原数据集相似**，如果我们尝试训练整个网络，容易导致过拟合。由于新数据和原数据相似，因此我们期望卷积网络中的高层特征和新数据集相关。因此，建议冻结所有卷积层，只训练分类器（比如，线性分类器）：

for layer in model.layers:

	layer.trainable = False

**新数据集较大，和原数据集相似**，由于我们有更多数据，我们更有自信，如果尝试对整个网络进行精细调整，不会导致过拟合。

```
for layer in model.layers:   
	layer.trainable = True  # 其实默认值就是True
```

**新数据集很小，但和原数据很不一样**，由于数据集很小，我们大概想要从靠前的层提取特征，然后在此之上训练一个分类器：（假定你对h5py有所了解）

**新数据集很大，和原数据很不一样**，由于你有一个很大的数据集，你可以设计你自己的网络，或者使用现有的网络。你可以基于随机初始化权重或预训练网络权重初始化训练网络。一般选择后者。
# keras 样例由imagenet花到猫狗
```
# 基于VGG16迁移学习
# 通过Keras的ImageDataGenerator加载数据集
# 加载VGG16模型但是不包括输出层
input_tensor = keras.Input(shape=(64, 64, 3))
vgg_model = keras.applications.VGG16(weights='imagenet', include_top=False, input_tensor=input_tensor)
layer_dict = dict([(layer.name, layer) for layer in vgg_model.layers])
print(len(layer_dict))
vgg_model.summary()
num_classes = 3
data_generator = keras.preprocessing.image.ImageDataGenerator(
        rescale=1./255,
        shear_range=0.2,
        zoom_range=0.2,
        horizontal_flip=True)
train_generator = data_generator.flow_from_directory(
        '/content/drive/My Drive/transferlearndata/train',
        target_size=(64, 64),
        batch_size=4,
        shuffle=True,
        class_mode='categorical')
print(train_generator.classes)
print(train_generator.class_indices)

data_generator = keras.preprocessing.image.ImageDataGenerator(rescale=1./255)
validation_generator = data_generator.flow_from_directory(
        '/content/drive/My Drive/transferlearndata/validate',
        target_size=(64, 64),
        batch_size=4,
        class_mode='categorical')
# 构建迁移学习网络使用VGG6的前面两个权重block，
# 依赖block2_pool的输出，输入张量（64x64x3）
# 构建网络的层
x = vgg_model.output
x = keras.layers.BatchNormalization()(x)
x = keras.layers.Flatten()(x)
x = keras.layers.Dense(4096, activation='relu')(x)
x = keras.layers.Dropout(0.25)(x)
x = keras.layers.Dense(num_classes, activation=tf.nn.softmax)(x)
my_model = keras.Model(inputs=vgg_model.input, outputs=x)
my_model.summary()
# 是否fine-tuning整个网络或者几层
for layer in my_model.layers[:-10]:
    layer.trainable = False
# 编译与训练
my_model.compile(
    loss='categorical_crossentropy',
    optimizer=keras.optimizers.Adam(0.0001),
    metrics=['accuracy'])
my_model.fit_generator(train_generator, epochs=10, validation_data=validation_generator)

# 保存整个模型
my_model.save("my_transfer_vgg.h5")
# 加载与使用
flower_dict = ['cats', 'dogs', 'horses']
new_model = keras.models.load_model("my_transfer_vgg.h5")
root_dir = "/content/drive/My Drive/transferlearndata/test"
for file in os.listdir(root_dir):
    src = cv2.imread(os.path.join(root_dir, file))
    img = cv2.resize(src, (64, 64))
    img = np.expand_dims(img, 0)
    result = new_model.predict(img)
    index = np.argmax(result)
    print(result.shape, index, flower_dict[index])
    cv2.putText(src, flower_dict[index],(50, 50), cv2.FONT_HERSHEY_PLAIN, 2.0, (0, 0, 255), 2, 8)
    cv2.imshow("input", src)
    cv2.waitKey(0)
cv2.destroyAllWindows()
``` 
