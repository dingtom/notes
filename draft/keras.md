
# tensorboard
```
writer=tf.summary.FileWriter('/path/to/logs', tf.get_default_graph())
writer.close()
```
在上面程序的8、9行中，创建一个writer，将tensorboard summary写入文件夹/path/to/logs，
然后运行上面的程序，在程序定义的日志文件夹/path/to/logs目录下，生成了一个新的日志文件events.out.tfevents.1524711020.bdi-172，
tensorboard –logdir /path/to/logs

[https://blog.csdn.net/fendouaini/article/details/80344591](https://blog.csdn.net/fendouaini/article/details/80344591)
[https://blog.csdn.net/fendouaini/article/details/80368770](https://blog.csdn.net/fendouaini/article/details/80368770)

```
cb.append(keras.callbacks.TensorBoard(
log_dir=os.path.join(logs_path, 'TensorBoard'),
histogram_freq=0, 
write_graph=True, 
write_grads=False, 
write_images=False,
embeddings_freq=0, 
embeddings_layer_names=None, 
embeddings_metadata=None, 
embeddings_data=None, 
update_freq='epoch'))
```
>该类在keras.callbacks模块中。它的参数列表如下：  
- log_dir: 用来保存被 TensorBoard 分析的日志文件的文件名。
- histogram_freq:**对于模型中各个层计算激活值和模型权重直方图的频率（训练轮数中**。 该参数用于设置tensorboard面板中的histograms和distributions面板如果设置成 0 ，直方图不会被计算。对于直方图可视化的验证数据（或分离数据）一定要明确的指出。
- write_graph: 是否在 TensorBoard 中可视化图。 如果 write_graph 被设置为 True。
- write_grads: 是否在 TensorBoard 中可视化梯度值直方图。 histogram_freq 必须要大于 0 。
![](https://upload-images.jianshu.io/upload_images/18339009-ee7a2081f8077b9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- batch_size: 用以直方图计算的传入神经元网络输入批的大小。
- write_images: 是否在 TensorBoard 中将模型权重以图片可视化，如果设置为True，日志文件会变得非常大。
![](https://upload-images.jianshu.io/upload_images/18339009-f48bcc99078e89e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- embeddings_freq: 被选中的嵌入层会被保存的频率（在训练轮中）。
- embeddings_layer_names: 一个列表，会被监测层的名字。 如果是 None 或空列表，那么所有的嵌入层都会被监测。
- embeddings_metadata: 一个字典，对应层的名字到保存有这个嵌入层元数据文件的名字。 查看 详情 关于元数据的数据格式。 以防同样的元数据被用于所用的嵌入层，字符串可以被传入。
- embeddings_data: 要嵌入在 embeddings_layer_names 指定的层的数据。 Numpy 数组（如果模型有单个输入）或 Numpy 数组列表（如果模型有多个输入）。 Learn ore about embeddings。
- update_freq: 'batch' 或 'epoch' 或 整数。当使用 'batch' 时，在每个 batch 之后将损失和评估值写入到 TensorBoard 中。同样的情况应用到 'epoch' 中。如果使用整数，例如 10000，这个回调会在每 10000 个样本之后将损失和评估值写入到 TensorBoard 中。注意，频繁地写入到 TensorBoard 会减缓你的训练。

#### 自己定义在tensorboard中显示acc和loss
```
import numpy as np
 
import tensorflow as tf
 
from keras.models import Sequential  # 采用贯序模型
from keras.layers import Input, Dense, Dropout, Activation,Conv2D,MaxPool2D,Flatten
from keras.optimizers import SGD
from keras.datasets import mnist
from keras.utils import to_categorical
from keras.callbacks import TensorBoard
 
 
def create_model():
    model = Sequential()
    model.add(Conv2D(32, (5,5), activation='relu', input_shape=[28, 28, 1])) 
    model.add(Conv2D(64, (5,5), activation='relu'))                          
    model.add(MaxPool2D(pool_size=(2,2)))                                    #池化层
    model.add(Flatten())                                                     #平铺层
    model.add(Dropout(0.5))
    model.add(Dense(128, activation='relu'))
    model.add(Dropout(0.5))
    model.add(Dense(10, activation='softmax'))
 
    return model
 
def compile_model(model):
    model.compile(loss='categorical_crossentropy', optimizer="adam",metrics=['acc']) 
    return model
 
def train_model(model,x_train,y_train,x_val,y_val,batch_size=128,epochs=10):
    train_loss = tf.placeholder(tf.float32, [],name='train_loss') 
    train_acc = tf.placeholder(tf.float32, [],name='train_acc') 
    val_loss = tf.placeholder(tf.float32, [],name='val_loss') 
    val_acc = tf.placeholder(tf.float32, [],name='val_acc') 
    
    #可视化训练集、验证集的loss、acc、四个指标，均是标量scalers
    tf.summary.scalar("train_loss", train_loss) 
    tf.summary.scalar("train_acc", train_acc)
    tf.summary.scalar("val_loss", val_loss)
    tf.summary.scalar("val_acc", val_acc)
  
    merge=tf.summary.merge_all()
 
    batches=int(len(x_train)/batch_size)  #没一个epoch要训练多少次才能训练完样本
 
    with tf.Session() as sess:
        logdir = './logs'
        writer = tf.summary.FileWriter(logdir, sess.graph) 
 
        for epoch in range(epochs): #用keras的train_on_batch方法进行训练 
            print(F"正在训练第 {epoch+1} 个 epoch")
            for i in range(batches):
                #每次训练128组数据
                train_loss_,train_acc_ = model.train_on_batch(x_train[i*128:(i+1)*128:1,...],y_train[i*128:(i+1)*128:1,...]) 
            #验证集只需要每一个epoch完成之后再验证即可
            val_loss_,val_acc_ = model.test_on_batch(x_val,y_val) 
 
            summary=sess.run(merge,feed_dict={train_loss:train_loss_,train_acc:train_acc_,val_loss:val_loss_,val_acc:val_acc_})
            writer.add_summary(summary,global_step=epoch)
 
 
 
if __name__=="__main__":
    (x_train,y_train),(x_test,y_test) = mnist.load_data()  #数据我已经下载好了
    print(np.shape(x_train),np.shape(y_train),np.shape(x_test),np.shape(y_test))  #(60000, 28, 28) (60000,) (10000, 28, 28) (10000,)
 
    x_train=np.expand_dims(x_train,axis=3)
    x_test=np.expand_dims(x_test,axis=3)
    y_train=to_categorical(y_train,num_classes=10)
    y_test=to_categorical(y_test,num_classes=10)
    print(np.shape(x_train),np.shape(y_train),np.shape(x_test),np.shape(y_test)) #(60000, 28, 28, 1) (60000, 10) (10000, 28, 28, 1) (10000, 10)
 
    x_train_=x_train[1:50000:1,...]    #重新将训练数据分成训练集50000组
    x_val_=x_train[50000:60000:1,...]  #重新将训练数据分成测试集10000组
    y_train_=y_train[1:50000:1,...]
    y_val_=y_train[50000:60000:1,...]
    print(np.shape(x_train_),np.shape(y_train_),np.shape(x_val_),np.shape(y_val_),np.shape(x_test),np.shape(y_test))
    #(49999, 28, 28, 1) (49999, 10) (10000, 28, 28, 1) (10000, 10) (10000, 28, 28, 1) (10000, 10)
    
    model=create_model()       #创建模型      
 
    model=compile_model(model) #编译模型
 
    train_model(model,x_train_,y_train_,x_val_,y_val_)
```


#[可视化](https://keras-zh.readthedocs.io/visualization/)
模型可视化

```
from keras.utils import plot_model
keras.utils.plot_model(model, to_file='model.png', show_shapes='Ture', dpi=200)
```
训练历史可视化

```
import matplotlib.pyplot as plt
history = model.fit(x, y, validation_split=0.25, epochs=50, batch_size=16, verbose=1)
# 绘制训练 & 验证的准确率值
plt.plot(history.history['acc'])
```
# 学习率调整      
```
import keras.backend as K
from keras.callbacks import LearningRateScheduler
 
def lr_scheduler(epoch):
    initial_lrate = 0.1
    drop = 0.5
    epochs_drop = 10.0
    lrate = initial_lrate * math.pow(drop, math.floor((1+e )/epochs_drop))
    return lrate
-------------------------------------------------------------------------
    # 每隔2个epoch，学习率减小为原来的1/10
    if epoch % 2 == 0 and epoch != 0:
        lr = K.get_value(model.optimizer.lr)
        K.set_value(model.optimizer.lr, lr * 0.1)
        print("lr changed to {}".format(lr * 0.1))
    return K.get_value(model.optimizer.lr)
 
reduce_lr = keras.callbacks.LearningRateScheduler(lr_scheduler)
model.fit(train_x, train_y, batch_size=32, epochs=300, callbacks=[reduce_lr])
```
```
from keras.callbacks import ReduceLROnPlateau
reduce_lr = ReduceLROnPlateau(monitor='val_loss', patience=2, mode='auto')
model.fit(train_x, train_y, batch_size=32, epochs=300, validation_split=0.1, callbacks=[reduce_lr])
```
>keras.callbacks.ReduceLROnPlateau(monitor='val_loss', factor=0.1, patience=10, verbose=0,, min_delta=0.0001,mode='auto', epsilon=0.0001, cooldown=0, min_lr=0)
monitor：被监测的量
factor：每次减少学习率的因子，学习率将以lr = lr*factor的形式被减少
patience：当patience个epoch过去而模型性能不提升时，学习率减少的动作会被触发
min_delta: 增大或减小的阈值。
mode：‘auto’，‘min’，‘max’之一，在min模式下，如果检测值触发学习率减少。在max模式下，当检测值不再上升则触发学习率减少。
epsilon：阈值，用来确定是否进入检测值的“平原区”
cooldown：学习率减少后，会经过cooldown个epoch才重新进行正常操作会经过cooldown个epoch才会重新计算被监控的指标没有提高(或者减少)的轮次(即patience).设置这个参数是因为减少学习率时, 模型的损失函数可能不在最优解附近,而训练至最优解附近需要一定轮次, 如果不设置则会导致学习率在远离最优解时接连衰减导致训练陷入僵局
min_lr：学习率的下限


# tf.keras.layers中网络配置：

- activation：设置层的激活函数。此参数由内置函数的名称指定，或指定为可调用对象。默认情况下，系统不会应用任何激活函数。

- kernel_initializer 和 bias_initializer：创建层权重（核和偏差）的初始化方案。此参数是一个名称或可调用对象，默认为 "Glorot uniform" 初始化器。
>random_uniform：初始化权重为（-0.05，0.05）之间的均匀随机的微小数值。换句话说，给定区间里的任何值都可能作为权重。
random_normal：根据高斯分布初始化权重，平均值为0，标准差为0.05。如果你不熟悉高斯分布，可以回想一下对称钟形曲线。
zero：所有权重初始化为0。
- kernel_regularizer 和 bias_regularizer：应用层权重（核和偏差）的正则化方案，例如 L1 或 L2 正则化。默认情况下，系统不会应用正则化函数。

- 我们也可以得到网络的变量、权重矩阵、偏置等 
  ```print(layer.variables)  # 包含了权重和偏置```
  ```print(layer.kernel, layer.bias)  # 也可以分别取出权重和偏置```

  ```
  layers.Dense(32, activation='sigmoid')
  layers.Dense(32, activation=tf.sigmoid)
  layers.Dense(32, kernel_initializer='orthogonal')
  layers.Dense(32, kernel_initializer=tf.keras.initializers.glorot_normal)
  layers.Dense(32, kernel_regularizer=tf.keras.regularizers.l2(0.01))
  layers.Dense(32, kernel_regularizer=tf.keras.regularizers.l1(0.01))
  ```

# metric评估

- 1)accuracy
如我们有6个样本，其真实标签y_true为[0, 1, 3, 3, 4, 2]，但被一个模型预测为了[0, 1, 3, 4, 4, 4]，即y_pred=[0, 1, 3, 4, 4, 4]，那么该模型的accuracy=4/6=66.67%。
- 2)binary_accuracy
**它适用于2分类的情况**。从上图中可以看到binary_accuracy的计算除了y_true和y_pred外，还有一个threshold参数，该参数默认为0.5。比如有6个样本，其y_true为[0, 0, 0, 1, 1, 0]，y_pred为[0.2, 0.3, 0.6, 0.7, 0.8, 0.1].具体计算方法为：1）将y_pred中的每个预测值和threshold对比，大于threshold的设为1，小于等于threshold的设为0，得到y_pred_new=[0, 0, 1, 1, 1, 0]；2）将y_true和y_pred_new代入到2.1中计算得到最终的binary_accuracy=5/6=87.5%。
- 3)categorical_accuracy
**针对的是y_true为onehot标签，y_pred为向量的情况。**比如有4个样本，其y_true为[[0, 0, 1], [0, 1, 0], [0, 1, 0], [1, 0, 0]]，y_pred为[[0.1, 0.6, 0.3], [0.2, 0.7, 0.1], [0.3, 0.6, 0.1], [0.9, 0, 0.1]]。具体计算方法为：1）将y_true转为非onehot的形式，即y_true_new=[2, 1, 1, 0]；2）根据y_pred中的每个样本预测的分数得到y_pred_new=[1, 1, 1, 0]；3）将y_true_new和y_pred_new代入到2.1中计算得到最终的categorical_accuracy=75%。
- 4)sparse_categorical_accuracy
**和categorical_accuracy功能一样，只是其y_true为非onehot的形式。**比如有4个样本，其y_true为[2， 1， 1， 0]，y_pred为[[0.1, 0.6, 0.3], [0.2, 0.7, 0.1], [0.3, 0.6, 0.1], [0.9, 0, 0.1]]。具体计算方法为：1）根据y_pred中的每个样本预测的分数得到y_pred_new=[1, 1, 1, 0]；2）将y_true和y_pred_new代入到2.1中计算得到最终的categorical_accuracy=75%。
- 5)top_k_categorical_accuracy
**在categorical_accuracy的基础上加上top_k。**categorical_accuracy要求样本在真值类别上的预测分数是在所有类别上预测分数的最大值，才算预测对，而top_k_categorical_accuracy只要求样本在真值类别上的预测分数排在其在所有类别上的预测分数的前k名就行。比如有4个样本，其y_true为[[0, 0, 1], [0, 1, 0], [0, 1, 0], [1, 0, 0]]，y_pred为[[0.3, 0.6, 0.1], [0.5, 0.4, 0.1], [0.3, 0.6, 0.1], [0.9, 0, 0.1]]。具体计算方法为：1）将y_true转为非onehot的形式，即y_true_new=[2, 1, 1, 0]；2）计算y_pred的top_k的label，比如k=2时，y_pred_new = [[0, 1], [0, 1], [0, 1], [0, 2]]；3）根据每个样本的真实标签是否在预测标签的top_k内来统计准确率，上述4个样本为例，2不在[0, 1]内，1在[0, 1]内，1在[0, 1]内，0在[0, 2]内，4个样本总共预测对了3个，因此k=2时top_k_categorical_accuracy=75%。说明一下，Keras中计算top_k_categorical_accuracy时默认的k值为5。
- 6)sparse_top_k_categorical_accuracy
**和top_k_categorical_accuracy功能一样，只是其y_true为非onehot的形式。**比如有4个样本，其y_true为[2， 1， 1， 0]，y_pred为[[0.3, 0.6, 0.1], [0.5, 0.4, 0.1], [0.3, 0.6, 0.1], [0.9, 0, 0.1]]。计算步骤如下：1）计算y_pred的top_k的label，比如k=2时，y_pred_new = [[0, 1], [0, 1], [0, 1], [0, 2]]；2）根据每个样本的真实标签是否在预测标签的top_k内来统计准确率，上述4个样本为例，2不在[0, 1]内，1在[0, 1]内，1在[0, 1]内，0在[0, 2]内，4个样本总共预测对了3个，因此k=2时top_k_categorical_accuracy=75%。

##### ROC,AUC
```
import tensorflow as tf
from sklearn.metrics import roc_auc_score

def auroc(y_true, y_pred):
    return tf.py_func(roc_auc_score, (y_true, y_pred), tf.double)

# Build Model...

model.compile(loss='categorical_crossentropy', optimizer='adam',metrics=['accuracy', auroc])
--------------------------------------------------------------------------------
class RocAucEvaluation(Callback):
    def __init__(self, validation_data=(), interval=1):
        super(Callback, self).__init__()
        self.interval = interval
        self.x_val,self.y_val = validation_data
    def on_epoch_end(self, epoch, log={}):
        if epoch % self.interval == 0:
            y_pred = self.model.predict_proba(self.x_val, verbose=0)
            score = roc_auc_score(self.y_val, y_pred)
            print('\n ROC_AUC - epoch:%d - score:%.6f \n' % (epoch+1, score))
x_train, x_val, y_train, y_val = train_test_split(x_train_nn, y_train_nn, train_size=0.9, random_state=233)

RocAuc = RocAucEvaluation(validation_data=(y_train,y_label), interval=1)
hist = model.fit(x_train, x_label, batch_size=batch_size, epochs=epochs, validation_data=(y_train, y_label), callbacks=[RocAuc], verbose=2)
```
# 训练
对```.fit```的调用在这里做出两个主要假设：
我们的整个训练集可以放入RAM
没有数据增强（即不需要Keras生成器）

真实世界的数据集通常太大而无法放入内存中
它们也往往具有挑战性，要求我们执行数据增强以避免过拟合并增加我们的模型的泛化能力
在这些情况下，我们需要利用Keras的```.fit_generator```函数：
```
# initialize the number of epochs and batch size
EPOCHS = 100
BS = 32
# construct the training image generator for data augmentation
aug = ImageDataGenerator(rotation_range=20, zoom_range=0.15,
	width_shift_range=0.2, height_shift_range=0.2, shear_range=0.15,
	horizontal_flip=True, fill_mode="nearest")
# train the network
H = model.fit_generator(aug.flow(trainX, trainY, batch_size=BS),
	validation_data=(testX, testY), steps_per_epoch=len(trainX) // BS,
	epochs=EPOCHS)
```

对于寻求对Keras模型进行精细控制（ finest-grained control）的深度学习实践者，您可能希望使用```.train_on_batch```例如数据迭代过程非常复杂并且需要自定义代码。








# 模型评估

print(model.evaluate(x_test,y_test))

y = model.predict_classes(x_test)
print(accuracy_score(y_test,y))

# 构建高级模型

- ###  模型子类化

通过对 tf.keras.Model 进行子类化并定义您自己的前向传播来构建完全可自定义的模型。在 __init__ 方法中创建层并将它们设置为类实例的属性。在 call 方法中定义前向传播

```python
class MyModel(tf.keras.Model):
    def __init__(self, num_classes=10):
        super(MyModel, self).__init__(name='my_model')
        self.num_classes = num_classes
        self.layer1 = layers.Dense(32, activation='relu')
        self.layer2 = layers.Dense(num_classes, activation='softmax')
    def call(self, inputs):
        h1 = self.layer1(inputs)
        out = self.layer2(h1)
        return out
    def compute_output_shape(self, input_shape):
        shape = tf.TensorShape(input_shape).as_list()
        shape[-1] = self.num_classes
        return tf.TensorShape(shape)
model = MyModel(num_classes=10)
```


- ### 自定义层

通过对 tf.keras.layers.Layer 进行子类化并实现以下方法来创建自定义层：

-  \_\_init__()函数，你可以在其中执行所有与输入无关的初始化 
-  build： 可以获得输入张量的形状， 创建层的权重。使用 add_weight 方法添加权重。
-  call： 构建网络结构， 定义前向传播。
-  compute_output_shape：指定在给定输入形状的情况下如何计算层的输出形状。
   或者，可以通过实现 get_config 方法和 from_config 类方法序列化层。

```python
class MyLayer(layers.Layer):
    def __init__(self, output_dim, **kwargs):
        super(MyLayer, self).__init__(**kwargs)
        self.output_dim = output_dim
    def build(self, input_shape):
        shape = tf.TensorShape((input_shape[1], self.output_dim))
        self.kernel = self.add_weight(name='kernel1', shape=shape,
                                   initializer='uniform', trainable=True)
        super(MyLayer, self).build(input_shape)
    def call(self, inputs):
        return tf.matmul(inputs, self.kernel)
    def compute_output_shape(self, input_shape):
        shape = tf.TensorShape(input_shape).as_list()
        shape[-1] = self.output_dim
        return tf.TensorShape(shape)
    def get_config(self):
        base_config = super(MyLayer, self).get_config()
        base_config['output_dim'] = self.output_dim
        return base_config
    @classmethod
    def from_config(cls, config):
        return cls(**config)
model = tf.keras.Sequential([
    MyLayer(10),
    layers.Activation('softmax')])

```
---

# 损失函数

>• mean_ squared_ error 或mse 。
• mean_absolute_error 或mae 。
• mean_ absolute_percentage_error 或mape 。
• mean_squared_logarithmic_error 或msle 。
• squared_hinge 。
• hinge 。
• binary_ crossentropy 。
• categorical_ crossentropy 。
• sparse_ categorical_ crossentrop 。
• kullback_lei bier_ divergence 。
• poisson 。
• cosine_proximity 。

**注意**：当使用categorical_crossentropy 作为目标函数时，标签应该为多类模式，即one-hot 形式编码的向量，而不是单个数值。用户可以使用工具中的to_ categorical 函数完成该转换.
```
from keras.utils.np_utils import to_categorical
int_labels= [1,2,3]
categorical_labels=to_categorical(int_labels, num classes=None)
print(categorical_labels)
```
---
# 优化器函数
选定了整个深度网络的损失函数，紧接着需要考虑的就是优化器的选择。因为有了训练目标，剩下最重要的就是达成该目标的方法



---

# 保存和恢复

##### 权重保存

```
model.save_weights('./model.h5')
model.load_weights('./model.h5')
```

##### 保存网络结构

  这样导出的模型并未包含训练好的参数

```python
# 序列化成json
import json
with open('model_struct.json', 'w') as ff:
    json_config = am.model.to_json()
    ff.write(json_string)  # 保存模型信息
with open('model_struct.json', 'r') as ff:
   json_config = ff.read()
reinitialized_model = keras.models.model_from_json(json_config)
new_prediction = reinitialized_model.predict(x_test)

# 其他形式
config = model.get_config()
reinitialized_model = keras.Model.from_config(config)
new_prediction = reinitialized_model.predict(x_test)
# 保持为yaml格式  #需要提前安装pyyaml
yaml_str = model.to_yaml()
print(yaml_str)
fresh_model = tf.keras.models.model_from_yaml(yaml_str)
```

##### 保存整个模型

内容包括：架构;权重（在训练期间学到的）;训练配置（你传递给编译的），如果有的话;优化器及其状态（如果有的话）（这使您可以从中断的地方重新启动训练）
```
model.save('all_model.h5')
new_model = keras.models.load_model('the_save_model.h5')

new_prediction = new_model.predict(x_test)
np.testing.assert_allclose(predictions, new_prediction, atol=1e-6) # 预测结果一样

# 保存为SavedModel文件  
keras.experimental.export_saved_model(model, 'saved_model')
new_model = keras.experimental.load_from_saved_model('saved_model')
```
##### checkpoint
```
checkpoint_path = os.path.join(os.path.join(logs_path, 'Checkpoint'), 'weights-improvement-{epoch:02d}-{loss:.2f}.hdf5')
cb.append(keras.callbacks.ModelCheckpoint(checkpoint_path, monitor='loss', verbose=0, save_best_only=True, save_weights_only=False, mode='auto'))
# 恢复至最近的checkpoint
latest=tf.train.latest_checkpoint(checkpoint_dir)
model = create_model()
model.load_weights(latest)
```
# 模型集成

```
import numpy as np
from tensorflow.keras.wrappers.scikit_learn import KerasClassifier
from sklearn.ensemble import VotingClassifier
from sklearn.metrics import accuracy_score

def mlp_model():
    model = keras.Sequential([
    layers.Dense(64, activation='relu', input_shape=(784,)),
    layers.Dropout(0.2),
    layers.Dense(64, activation='relu'),
    layers.Dropout(0.2),
    layers.Dense(64, activation='relu'),
    layers.Dropout(0.2),
    layers.Dense(10, activation='softmax')
    ])
    model.compile(optimizer=keras.optimizers.SGD(),
             loss=keras.losses.SparseCategoricalCrossentropy(),
             metrics=['accuracy'])
    return model
# 下面是使用投票的方法进行模型集成
model1 = KerasClassifier(build_fn=mlp_model, epochs=100, verbose=0)
model2 = KerasClassifier(build_fn=mlp_model, epochs=100, verbose=0)
model3 = KerasClassifier(build_fn=mlp_model, epochs=100, verbose=0)
ensemble_clf = VotingClassifier(estimators=[('model1', model1), ('model2', model2), ('model3', model3)], voting='soft')
ensemble_clf.fit(x_train, y_train)
y_pred = ensemble_clf.predict(x_test)
print('acc: ', accuracy_score(y_pred, y_test))

```




# MLP样例
```
import tensorflow as tf
import numpy as np
from tensorflow import keras
from tensorflow.keras import layers
print(tf.__version__)
print(tf.keras.__version__)
# 生成数据
train_x = np.random.random((1000, 72))
train_y = np.random.random((1000, 10))
val_x = np.random.random((200, 72))
val_y = np.random.random((200, 10))
test_x = np.random.random((1000, 72))
test_y = np.random.random((1000, 10))
dataset = tf.data.Dataset.from_tensor_slices((train_x, train_y))
dataset = dataset.batch(32).repeat()
val_dataset = tf.data.Dataset.from_tensor_slices((val_x, val_y))
val_dataset = val_dataset.batch(32).repeat()
test_data = tf.data.Dataset.from_tensor_slices((test_x, test_y))
test_data = test_data.batch(32).repeat()
# 模型堆叠
model = tf.keras.Sequential([
    layers.Dense(32, activation='relu', input_shape=(72,)),
    layers.BatchNormalization(),  
    layers.Dropout(0.2),
    layers.Dense(32, activation='relu'),
    layers.BatchNormalization(),
    layers.Dropout(0.2),
    layers.Dense(10, activation='softmax')])
model.compile(optimizer=keras.optimizers.SGD(0.1),
             loss=tf.keras.losses.categorical_crossentropy,
             metrics=[tf.keras.metrics.categorical_accuracy])
model.summary()
# 网络图
# !sudo apt-get install graphvizf
# keras.utils.plot_model(model, 'model_info.png', show_shapes=True)
history = model.fit(train_data, epochs=10, steps_per_epoch=30)
# 画出学习曲线
# print(history.history.keys())
plt.plot(history.history['categorical_accuracy'])
plt.plot(history.history['loss'])
plt.legend(['categorical_accuracy', 'loss'], loc='upper left')
plt.show()
# 评估与预测
result = model.predict(test_data, steps=30)
print(result)
loss, accuracy = model.evaluate(test_data, steps=30)
print('test loss:', loss)
print('test accuracy:', accuracy)
```

# 多输入与多输出网络
```
import numpy as np
# 载入输入数据
title_data = np.random.randint(num_words, size=(1280, 10))
body_data = np.random.randint(num_words, size=(1280, 100))
tag_data = np.random.randint(2, size=(1280, num_tags)).astype('float32')
# 标签
priority_label = np.random.random(size=(1280, 1))
department_label = np.random.randint(2, size=(1280, num_departments))
# 超参
num_words = 2000
num_tags = 12
num_departments = 4
# 输入
title_input = keras.Input(shape=(None,), name='title')
body_input = keras.Input(shape=(None,), name='body')
tag_input = keras.Input(shape=(num_tags,), name='tag')
# 嵌入层
title_feat = layers.Embedding(num_words, 64)(title_input)
body_feat = layers.Embedding(num_words, 64)(body_input)
# 特征提取层
title_feat = layers.Embedding(num_words, 64)(title_input)
body_feat = layers.LSTM(32)(body_feat)
features = layers.concatenate([title_feat,body_feat, tag_input])
# 分类层
priority_pred = layers.Dense(1, activation='sigmoid', name='priority')(features)
department_pred = layers.Dense(num_departments, activation='softmax', name='department')(features)
# 构建模型
model = keras.Model(inputs=[body_input, title_input, tag_input], outputs=[priority_pred, department_pred])
model.summary()
keras.utils.plot_model(model, 'multi_model.png', show_shapes=True)
model.compile(optimizer=keras.optimizers.RMSprop(1e-3),
             loss={'priority': 'binary_crossentropy', 'department': 'categorical_crossentropy'},
             loss_weights=[1., 0.2])
# 训练
history = model.fit(
    {'title': title_data, 'body':body_data, 'tag':tag_data},
    {'priority':priority_label, 'department':department_label},
    batch_size=32,
    epochs=5)
```
![](https://upload-images.jianshu.io/upload_images/18339009-b1fd2d5f2ce1181a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

layers.Conv2D```((filters,kernel_size,strides=(1, 1),padding='valid',data_format=None,dilation_rate=(1,1),activation=None,use_bias=True,kernel_initializer='glorot_uniform',bias_initializer='zeros',kernel_regularizer=None,bias_regularizer=None,activity_regularizer=None,kernel_constraint=None,bias_constraint=None,**kwargs)```

layers.MaxPooling2D```(pool_size=(2, 2),strides=None,padding='valid',data_format=None,**kwargs)```

# 小型残差网络
```
inputs = keras.Input(shape=(32,32,3), name='img')
h1 = layers.Conv2D(32, 3, activation='relu')(inputs)
h1 = layers.Conv2D(64, 3, activation='relu')(h1)
block1_out = layers.MaxPooling2D(3)(h1)

h2 = layers.Conv2D(64, 3, activation='relu', padding='same')(block1_out)
h2 = layers.Conv2D(64, 3, activation='relu', padding='same')(h2)
block2_out = layers.add([h2, block1_out])

h3 = layers.Conv2D(64, 3, activation='relu', padding='same')(block2_out)
h3 = layers.Conv2D(64, 3, activation='relu', padding='same')(h3)
block3_out = layers.add([h3, block2_out])

h4 = layers.Conv2D(64, 3, activation='relu')(block3_out)
h4 = layers.GlobalMaxPool2D()(h4)
h4 = layers.Dense(256, activation='relu')(h4)
h4 = layers.Dropout(0.5)(h4)
outputs = layers.Dense(10, activation='softmax')(h4)

model = keras.Model(inputs, outputs, name='small resnet')
model.summary()
keras.utils.plot_model(model, 'small_resnet_model.png', show_shapes=True)
(x_train, y_train), (x_test, y_test) = keras.datasets.cifar10.load_data()
x_train = x_train.astype('float32') / 255
x_test = y_train.astype('float32') / 255
y_train = keras.utils.to_categorical(y_train, 10)
y_test = keras.utils.to_categorical(y_test, 10)

model.compile(optimizer=keras.optimizers.RMSprop(1e-3),
             loss='categorical_crossentropy',
             metrics=['acc'])
model.fit(x_train, y_train,
         batch_size=64,
         epochs=1,
         validation_split=0.2)
#model.predict(x_test, batch_size=32)
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-b6a89c81f65a069f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 封装被sklearn调用
```
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.cross_validation import StratifiedKFold
from sklearn.cross_validation import cross_val_score
import numpy
import pandas
# Function to create model, required for KerasClassifier
def create_model():
    model = Sequential()
    model.add(Dense(12, input_dim=8, init='uniform', activation='relu')) 		model.add(Dense(8, init='uniform', activation='relu')) 
    model.add(Dense(1, init='uniform', activation='sigmoid'))
    model.compile(loss='binary_crossentropy', optimizer='adam', metrics=			['accuracy']) return model
# fix random seed for reproducibility
seed = 7
numpy.random.seed(seed)
# load pima indians dataset
dataset = numpy.loadtxt("pima-indians-diabetes.csv", delimiter=",")
# split into input (X) and output (Y) variables
X = dataset[:,0:8]
Y = dataset[:,8]
# Keras的KerasClassifier和KerasRegressor两个类接受build_fn参数，传入编译好的模型。我们加入nb_epoch=150和batch_size=10这两个参数这两个参数会传入模型的fit()方法。
model = KerasClassifier(build_fn=create_model, nb_epoch=150, batch_size=10)
# 用scikit-learn的StratifiedKFold类进行10折交叉验证，测试模型在未知数据的性能，并使用cross_val_score()函数检测模型，打印结果。
# StratifiedKFold用法类似Kfold，但是他是分层采样，确保训练集，测试集中各类别样本的比例与原始数据集中相同
kfold = StratifiedKFold(n_splits=10, shuffle=True, random_state=seed)
results = cross_val_score(model, X, Y, cv=kfold, , verbose=0, cv=5, n_jobs=-1, scoring=make_scorer(mean_absolute_error))
print("Results: %.2f%% (%.2f%%)" % (results.mean()*100, results.std()*100))

```
```
# 使用网格搜索调整深度学习模型的参数
model = KerasClassifier(build_fn=create_model)
param_grid = {'optimizers': ['rmsprop', 'adam'],
        'kernel_initializer': ['glorot_uniform', 'normal', 'uniform'],
        'use_bias': ['True', 'False'],
        'epochs': np.array([50, 100, 150]),
        'batch_size': np.array([5, 10, 20])}
# GridSearchCV会对每组参数（2×3×3×3）进行训练，进行3折交叉检验。
grid = GridSearchCV(estimator=model, param_grid=param_grid)
grid_result = grid.fit(X, Y)
# summarize results
print("Best: %f using %s" % (grid_result.best_score_, grid_result.best_params_))
for params, mean_score, scores in grid_result.grid_scores_:
    print("%f (%f) with: %r" % (scores.mean(), scores.std(), params))
# Best: 0.751302 using {'init': 'uniform', 'optimizer': 'rmsprop', 'nb_epoch': 150, 'batch_size': 5}
#0.653646 (0.031948) with: {'init': 'glorot_uniform', 'optimizer': 'rmsprop', 'nb_epoch': 50, 'batch_size': 5}
#0.665365 (0.004872) with: {'init': 'glorot_uniform', 'optimizer': 'adam', 'nb_epoch': 50, 'batch_size': 5}
# 0.683594 (0.037603) with: {'init': 'glorot_uniform', 'optimizer': 'rmsprop', 'nb_epoch': 100, 'batch_size': 5}


```

# [卷积](https://keras-zh.readthedocs.io/layers/convolutional/)、[池化](https://keras-zh.readthedocs.io/layers/pooling/)

#### 二维卷积：
```keras.layers.Conv2D(filters, kernel_size, strides=(1, 1), padding='valid', data_format=None, dilation_rate=(1, 1), activation=None, use_bias=True, kernel_initializer='glorot_uniform', bias_initializer='zeros', kernel_regularizer=None, bias_regularizer=None, activity_regularizer=None, kernel_constraint=None, bias_constraint=None)```
Conv2D输入：(samples, rows, cols, channels)
kernel：尺寸(k, k)， 数量filters
输出：（samples, new_rows, new_cols, filters)， new_rows=(rows-k+2padding)/strides+1
备注：实际计算时，kernel维度为(k,k,channels)，会包含所有channels维度，因此若filters=1，即只有一个卷积核，则输出为(samples, new_rows,new_cols,1)
####一维卷积：
```keras.layers.Conv1D(filters, kernel_size, strides=1, padding='valid', data_format='channels_last', dilation_rate=1, activation=None, use_bias=True, kernel_initializer='glorot_uniform', bias_initializer='zeros', kernel_regularizer=None, bias_regularizer=None, activity_regularizer=None, kernel_constraint=None, bias_constraint=None)```
Conv1D输入：(batch_size, steps, input_dim)
行向量代表单个时间步，单个时间步包含特征维度input_dim
列向量代表单个特征维度，单个特征维度包含时间步长steps
kernel：尺寸k， 数量filters
输出：(batch_size, new_steps, filters)，new_steps=(steps-k+2padding)/strides+1
备注：实际计算时，kernel维度为(k,input_dim)，会包含所有input_dim（这里的input_dim与Conv2D中的channels类似）
####一维最大池化
```keras.layers.MaxPooling1D(pool_size=2, strides=None, padding='valid', data_format='channels_last')```
 输入为 3D 张量，尺寸为： (batch_size, steps, features)
 输出为 3D 张量，尺寸为： (batch_size, downsampled_steps, features)
Maxpooling1D(3,2))池化核大小为3，步长为2，(8-3＋1)/2=3,
注意:若model.add(Maxpooling1D(2))，则池化核大小为2，步长也为2。


学习[https://www.jianshu.com/p/3a8b310227e6](https://www.jianshu.com/p/3a8b310227e6)

```
"""
训练
"""
from keras.datasets import mnist
from keras.utils import to_categorical
from keras.models import Sequential
from keras.layers import Conv2D, MaxPool2D, Flatten, Dropout, Dense
from keras.losses import categorical_crossentropy
from keras.optimizers import Adadelta

(x_train, y_train), (x_test, y_test) = mnist.load_data()
# 将原始的特征矩阵做数据处理形成模型需要的数据
x_train = x_train.reshape(-1, 28, 28, 1)
# 对数据进行归一化处理
x_train = x_train.astype('float32')
x_train /= 255
# 对标签one-hot处理
y_train = to_categorical(y_train, 10)

x_test = x_test.reshape(-1, 28, 28, 1)
x_test = x_test.astype('float32')
x_test /= 255
y_test = to_categorical(y_test, 10)

model = Sequential()
model.add(Conv2D(32, (5,5), activation='relu', input_shape=[28, 28, 1]))
model.add(Conv2D(64, (5,5), activation='relu'))
model.add(MaxPool2D(pool_size=(2,2)))
model.add(Flatten())
model.add(Dropout(0.5))
model.add(Dense(128, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(10, activation='softmax'))

model.compile(loss=categorical_crossentropy,
             optimizer=Adadelta(),
             metrics=['accuracy'])

batch_size = 100
epochs = 1
model.fit(x_train, y_train,
         batch_size=batch_size,
         epochs=epochs)

```

```
"""
预测
"""
# verbose：日志显示 fit 中默认为 1 
# 0 为不在标准输出流输出日志信息 1 为输出进度条记录, ,2 为每个epoch输出一行记录
# evaluate 中默认为0 
# 0 为不在标准输出流输出日志信息  1 为输出进度条记录
loss, accuracy = model.evaluate(x_train, y_train, verbose=1)
print('train data loss:%.4f accuracy:%.4f' %(loss, accuracy))
loss, accuracy = model.evaluate(x_test, y_test, verbose=1)
print('test data loss:%.4f accuracy:%.4f' %(loss, accuracy))
```

```
"""
画出预测结果
"""
import math
import matplotlib.pyplot as plt
import numpy as np
import random

def draw(position, image, title, isTrue):
    # 设置子图位置
    plt.subplot(*position)
    plt.imshow(image.reshape(-1, 28), cmap='gray_r')
    plt.axis('off')
    if not isTrue:
        plt.title(title, color='red')
    else:
        plt.title(title)
        
def show_result(batch_size, test_X, test_y):
    selected_index = random.sample(range(len(test_y)), k=batch_size)
    images = test_X[selected_index]
    labels = test_y[selected_index]
    predict_labels = model.predict(images)
    image_numbers = images.shape[0]
    row_number = math.ceil(image_numbers ** 0.5)
    column_number = row_number
    # 设置图片大小
    plt.figure(figsize=(row_number+8, column_number+8))
    for i in range(row_number):
        for j in range(column_number):
            index = i * column_number + j
            if index < image_numbers:
                position = (row_number, column_number, index+1)
                image = images[index]
                actual = np.argmax(labels[index])
                predict = np.argmax(predict_labels[index])
                isTrue = actual==predict
                title = 'actual:%d\npredict:%d' %(actual,predict)
                draw(position, image, title, isTrue)

show_result(100, x_test, y_test)
plt.show()
```
