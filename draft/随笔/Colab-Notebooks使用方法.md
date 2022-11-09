自动连接
浏览器检查，console运行
```
function ClickConnect(){
console.log(“Working”);
document.querySelector(“colab-toolbar-button#connect”).shadowRoot.querySelector("#connect").click()
}
setInterval(ClickConnect,60000)
```

查看使用中的GPU

```
watch -n 5 nvidia-smi （其中，5表示每隔6秒刷新一次终端的显示结果）
nvidia-smi（显示一次当前GPU占用情况）
nvidia-smi -l（每秒刷新一次并显示）
```

```
import tensorflow as tf
tf.test.gpu_device_name()
```
 ```
import pynvml
pynvml.nvmlInit()
# 这里的1是GPU id
handle = pynvml.nvmlDeviceGetHandleByIndex(0)
meminfo = pynvml.nvmlDeviceGetMemoryInfo(handle)
print('GPU Memory-Usage total:{:.2f}(GiB) used:{:.2f}% free:{:.2f}(MiB)'.format(meminfo.total/(1024**3), meminfo.used/meminfo.total*100, meminfo.free/(1024**2))) #显卡剩余显存大小
 ```


```
from tensorflow.python.client import device_lib
device_lib.list_local_devices()
```
RAM内存
```!cat /proc/meminfo```
查看CPU
```!cat /proc/cpuinfo```

使用TPU\模型转换
```
TPU_ADDRESS = TPU_WORKER = 'grpc://' + os.environ['COLAB_TPU_ADDR']
tf.logging.set_verbosity(tf.logging.INFO)
tpu = tf.contrib.cluster_resolver.TPUClusterResolver(TPU_ADDRESS)
tf.config.experimental_connect_to_cluster(tpu)
tf.tpu.experimental.initialize_tpu_system(tpu)
strategy = tf.contrib.tpu.TPUDistributionStrategy(tpu)
tpu_model = tf.contrib.tpu.keras_to_tpu_model(model, strategy=strategy)
```
>1.TPU只兼容tensorflow.python.keras.XXX 格式的库，调用了标准keras的mode需要修改keras库的引用
>2.目前标准TPU对keras标准的优化器（keras.optimizers）支持有限，有时候可能使用tf.train.的优化器进行编译，如果没有特殊需求，建议还是使用keras自己的优化器
>3.batch_size=128 * 8，batch_size 设置为模型输入 batch_size 的八倍，这是为了使输入样本在 8 个 TPU 核心上均匀分布并运行。 
>TPU模型的保存与恢复
>保存之前需要先将其转换为CPU模型，可以保存在前面挂载的云盘的文件夹中：

```
save_model =  model.sync_to_cpu()
save_model.save('/content/best.hdf5')
```
读取模型以后，需要重新将其转换为TPU模型，可能需要重新编译优化器：
```
model = models.load_model('/content/best.hdf5')
model = tf.contrib.tpu.keras_to_tpu_model(
	model,
    strategy=tf.contrib.tpu.TPUDistributionStrategy(
    	tf.contrib.cluster_resolver.TPUClusterResolver(TPU_WORKER)))
model.compile(optimizer=tf.train.RMSPropOptimizer(learning_rate=1e-3), loss='categorical_crossentropy', metrics=['caccuracy'])
```
```
%tensorflow_version 2.x
import tensorflow as tf
print(tf.__version__)
from tensorflow.keras import *  
import os


MAX_LEN = 300
BATCH_SIZE = 32
(x_train,y_train),(x_test,y_test) = datasets.reuters.load_data()
print(x_train.shape,y_train.shape,x_test.shape,y_test.shape)
print(x_train[0])
x_train = preprocessing.sequence.pad_sequences(x_train,maxlen=MAX_LEN)
x_test = preprocessing.sequence.pad_sequences(x_test,maxlen=MAX_LEN)

MAX_WORDS = x_train.max()+1
CAT_NUM = y_train.max()+1

ds_train = tf.data.Dataset.from_tensor_slices((x_train,y_train)).shuffle(buffer_size=1000).batch(BATCH_SIZE).prefetch(tf.data.experimental.AUTOTUNE).cache()
ds_test = tf.data.Dataset.from_tensor_slices((x_test,y_test)).shuffle(buffer_size = 1000).batch(BATCH_SIZE).prefetch(tf.data.experimental.AUTOTUNE).cache()


tf.keras.backend.clear_session()

resolver = tf.distribute.cluster_resolver.TPUClusterResolver(tpu='grpc://' + os.environ['COLAB_TPU_ADDR'])
tf.config.experimental_connect_to_cluster(resolver)
tf.tpu.experimental.initialize_tpu_system(resolver)
strategy = tf.distribute.experimental.TPUStrategy(resolver)
with strategy.scope():
    model = models.Sequential()
    model.add(layers.Embedding(MAX_WORDS,7,input_length=MAX_LEN))
    model.add(layers.Conv1D(filters = 64,kernel_size = 5,activation = "relu"))
    model.add(layers.MaxPool1D(2))
    model.add(layers.Conv1D(filters = 32,kernel_size = 3,activation = "relu"))
    model.add(layers.MaxPool1D(2))
    model.add(layers.Flatten())
    model.add(layers.Dense(CAT_NUM,activation = "softmax"))
    print(0)
    model.compile(optimizer=optimizers.Nadam(),
          loss=losses.SparseCategoricalCrossentropy(from_logits=True),
          metrics=[metrics.SparseCategoricalAccuracy(),metrics.SparseTopKCategoricalAccuracy(5)])
    print(1)
    model.fit(ds_train, validation_data=ds_test, epochs=10)
    print(2)
```




