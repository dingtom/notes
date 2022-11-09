 AutoKeras是一个开源的，基于 Keras 的新型 AutoML 库。AutoKeras 是一个用于自动化机器学习的开源软件库，提供自动搜索深度学习模型的架构和超参数的功能。AutoKeras 采用的架构搜索方法是一种结合了贝叶斯优化的神经架构搜索。它主要关注于降低架构搜索所需要的计算力，并提高搜索结果在各种任务上的性能。

官方网站：https://autokeras.com/
项目github：https://github.com/jhfjhfj1/autokeras


TensorFlow版本：https://github.com/melodyguan/enas
PyTorch 版本：https://github.com/carpedm20/ENAS-pytorch


```!pip install autokeras```

```
from tensorflow.keras.datasets import mnist
import autokeras as ak
from keras.models import load_model
from keras.utils import plot_model
MODEL_DIR = 'my_model.h5'
MODEL_PNG = 'model.png'
IMAGE_SIZE = 28

# 获取本地图片，转换成numpy格式
(train_data, train_labels), (test_data, test_labels) = mnist.load_data()

# 数据进行格式转换
train_data = train_data.astype('float32') / 255
test_data = test_data.astype('float32') / 255
print("train data shape:", train_data.shape)

# 使用图片识别器
clf = ak.ImageClassifier()
# 给其训练数据和标签，训练的最长时间可以设定，假设为1分钟，autokers会不断找寻最优的网络模型
clf.fit(train_data, train_labels, epochs=10)
# 给出评估结果
accuracy = clf.evaluate(test_data, test_labels, batch_size=32)
print("accuracy:", accuracy)

y = clf.predict(test_data, batch_size=32) 
print("predict:", y)

# 导出我们生成的模型
clf.export_keras_model(MODEL_DIR)
# 加载模型
model = load_model(MODEL_DIR)
# 将模型导出成可视化图片
plot_model(model, to_file=MODEL_PNG)

```

[https://blog.csdn.net/lvsolo/article/details/103445431?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522158626893719724848310208%2522%252C%2522scm%2522%253A%252220140713.130056874..%2522%257D&request_id=158626893719724848310208&biz_id=0&utm_source=distribute.pc_search_result.none-task-blog-blog_SOOPENSEARCH-6](https://blog.csdn.net/lvsolo/article/details/103445431?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522158626893719724848310208%2522%252C%2522scm%2522%253A%252220140713.130056874..%2522%257D&request_id=158626893719724848310208&biz_id=0&utm_source=distribute.pc_search_result.none-task-blog-blog_SOOPENSEARCH-6)
