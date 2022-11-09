![quicker_49402ab9-9843-4939-b810-0dec623ac765.png](https://s2.loli.net/2022/03/31/WTwFk2NeqJ6SRxb.png)

图像分类：图像中的气球是一个类别。
语义分割：分割出气球和背景。
目标检测：图像中有7个目标气球，并且检测出每个气球的坐标位置。
实例分割：图像中有7个不同的气球，在像素层面给出属于每个气球的像素。

![quicker_f5c4e0b4-cbb5-44a4-bb19-7515cb96470d.png](https://s2.loli.net/2022/03/30/SpOhMr68QWbF2yg.png)



高分辨率特征(较浅的卷积层)**感知域较**小，有利于feature map和原图进行对齐的，也就是我说的可以提供更多的位置信息。



低分辨率信息(深层的卷积层)由于**感知域较大**，能够学习到更加**抽象**一些的特征，可以提供更多的上下文信息，即强语义信息，这有利于像素的精确分类。





**上采样**（意义在于将小尺寸的高维度feature map恢复回去）一般包括2种方式：

**Resize**，如双线性插值直接对图像进行缩放（这种方法在原文中提到）

**Deconvolution**（反卷积），也叫Transposed Convolution(转置卷积)，可以理解为卷积的逆向操作。





# 标注工具

## Labelme

支持目标检测、语义分割、实例分割等任务。今天针对分割任务的数据标注进行简单的介绍。开源项目地址：
<https://github.com/wkentaro/labelme>

安装非常简单，直接使用`pip`安装即可：

```
pip install labelme
安装完成后在终端输入`labelme`即可启动：
labelme

# 建议大家按照我提供的目录格式事先准备好数据，然后在该根目录下启动labelme
├── img_data: 存放你要标注的所有图片
├── data_annotated: 存放后续标注好的所有json文件
└── label.txt: 所有类别信息

```

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-02/2022-11-02_10-43-52-902.png)

虽然在labelme中能够在标注时添加标签，但我个人强烈建议事先创建一个`label.txt`标签（放在上述位置中），然后启动labelme时直接读取。标签格式如下：

```
__ignore__
_background_
dog
cat

# 每一行代表一个类型的名称，前两行是固定格式__ignore__和_background_都加上，否则后续使用作者提供的转换脚本（转换成PASCAL VOC格式和MS COCO格式）时会报错。也就是从第三行开始就是我们需要分割的目标类别。这里以分割猫狗为例。

# 在创建好标签后，启动labelme并读取标签文件（注意启动根目录），其中--labels指定了标签文件的路径。
labelme --labels label.txt

# 读取标签后，我们在界面右侧能够看到Label List中已经载入了刚刚我们自己创建的标签文件，并且不同类别用不同的颜色表示。
```

- 养成良好习惯，先将保存路径设置好。

先点击左上角File，Change Output Dir设置标注结果的保存目录，这里就设置成前面说好的data_annotated。
建议将Save With Image Data取消掉，默认是选中的。如果选中，会在保存的标注结果中将图像数据也保存在.json文件中（个人觉得没必要，还占空间）。

- 标注目标

首先点击左侧的CreatePolygons按钮开始绘制多边形，然后用鼠标标记一个一个点把目标边界给标注出来（鼠标放置在第一个点上，点击一下会自动闭合边界）。标注后会弹出一个选择类别的选择框，选择对应类别即可。

如果标注完一个目标后想修改目标边界，可以点击工具左侧的EditPolygons按钮，然后选中要修改的目标，拖拉边界点即可进行微调。如果要在边界上新增点，把鼠标放在边界上点击鼠标右键选择Add Point to Edge即可新增边界点。如果要删除点，把鼠标放在边界点上点击鼠标右键选择Remove Selected Point即可删除边界点

标注完一张图片后，点击界面左侧的`Save`按钮即可保存标注结果，默认每张图片的标注信息都用一个json文件存储。

- 保存json文件格式

标注得到的json文件格式如下，将一张图片中的所有目标的坐标都保存在shapes列表中，列表中每个元素对应一个目标，其中label记录了该目标的类别名称。points记录了一个目标的左右坐标信息。其他信息不在赘述。根据以下信息，其实自己就可以写个脚本取读取目标信息了。

```shell
{
  "version": "4.5.9",
  "flags": {},
  "shapes": [
    {
      "label": "dog",
      "points": [
        [
          108.09090909090907,
          687.1818181818181
        ],
        ....
        [
          538.090909090909,
          668.090909090909
        ],
        [
          534.4545454545454,
          689.0
        ]
      ],
      "group_id": null,
      "shape_type": "polygon",
      "flags": {}
    }
  ],
  "imagePath": "../img_data/1.jpg",
  "imageData": null,
  "imageHeight": 690,
  "imageWidth": 690
}

```

### 格式转换

#### 转换语义分割标签

原作者为了方便，也提供了一个脚本，帮我们方便的将json文件转换成PASCAL VOC的语义分割标签格式。示例项目链接：https://github.com/wkentaro/labelme/tree/master/examples/semantic_segmentation.
  在该链接中有个labelme2voc.py脚本，将该脚本下载下来后，放在上述项目根目录下，执行以下指令即可（注意，执行脚本的根目录必须和刚刚启动labelme的根目录相同，否则会出现找不到图片的错误）。其中data_annotated是刚刚标注保存的json标签文件夹，data_dataset_voc是生成PASCAL VOC数据的目录。

```python
python labelme2voc.py data_annotated data_dataset_voc --labels label.txt
```

执行后会生成如下目录：

- data_dataset_voc/JPEGImages

- data_dataset_voc/SegmentationClass

- data_dataset_voc/SegmentationClassPNG

- data_dataset_voc/SegmentationClassVisualization

- data_dataset_voc/class_names.txt

其中JPEGImages就和之前PASCAL VOC数据讲解中说的一样，就是存储原图像文件。而SegmentationClassPNG就是语义分割需要使用的PNG标签图片。`class_names.txt`存储的是所有的类别信息，包括背景。

#### 转换实例分割标签

原作者为了方便，这里提供了两个脚本，帮我们方便的将json文件转换成PASCAL VOC的实例分割标签格式以及MS COCO的实例分割标签格式。示例项目链接：https://github.com/wkentaro/labelme/tree/master/examples/instance_segmentation.
在该链接中有个labelme2voc.py脚本，将该脚本下载下来后，执行以下指令即可（注意，执行脚本的根目录必须和刚刚启动labelme的根目录相同，否则会出现找不到图片的错误）。其中data_annotated是刚刚标注保存的json标签文件夹，data_dataset_voc是生成PASCAL VOC数据的目录。

```python
labelme2voc.py data_annotated data_dataset_voc --labels label.txt
```

执行后会生成如下目录：

- data_dataset_voc/JPEGImages

- data_dataset_voc/SegmentationClass
- data_dataset_voc/SegmentationClassPNG
- data_dataset_voc/SegmentationClassVisualization
- data_dataset_voc/SegmentationObject
- data_dataset_voc/SegmentationObjectPNG
- data_dataset_voc/SegmentationObjectVisualization
- data_dataset_voc/class_names.txt

除了刚刚讲的语义分割文件夹外，还生成了针对实例分割的标签文件，主要就是SegmentationObjectPNG目录：

在该链接中有个labelme2coco.py脚本，将该脚本下载下来后，执行以下指令即可（注意，执行脚本的根目录必须和刚刚启动labelme的根目录相同，否则会出现找不到图片的错误）。其中data_annotated是刚刚标注保存的json标签文件夹，data_dataset_coco是生成MS COCO数据类型的目录。

```python
python labelme2coco.py data_annotated data_dataset_coco --labels label.txt
```

其中annotations.json就是MS COCO的标签数据文件，如果不了解可以看下我之前写的MS COCO介绍。

# 数据集

### PASCAL VOC

PASCAL VOC挑战赛主要包括以下几类：**图像分类(Object Classification)**，**目标检测(Object Detection)**，**目标分割(Object Segmentation)**，**行为识别(Action Classification)** 等。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-14/2022-10-14_11-23-38-217.png)

该数据集有20个分类：
Person:person
Animal:bird,cat,cow,dog,horse,sheep
Vehicle:aeroplane,bicycle,boat,bus,car,motorbike,train
Indoor:bottle,chair,dining table,potted plant,sofa,tv/monitor

http://host.robots.ox.ac.uk/pascal/VOC/voc2012/

下载后将文件进行解压，解压后的文件目录结构如下所示：

```
VOCdevkit
    └── VOC2012
         ├── Annotations               所有的图像标注信息(XML文件)
         ├── ImageSets    
         │   ├── Action                人的行为动作图像信息
         │   ├── Layout                人的各个部位图像信息
         │   │
         │   ├── Main                  目标检测分类图像信息
         │   │     ├── train.txt       训练集(5717)
         │   │     ├── val.txt         验证集(5823)
         │   │     └── trainval.txt    训练集+验证集(11540)
         │   │
         │   └── Segmentation          目标分割图像信息
         │         ├── train.txt       训练集(1464)
         │         ├── val.txt         验证集(1449)
         │         └── trainval.txt    训练集+验证集(2913)
         │ 
         ├── JPEGImages                所有图像文件
         ├── SegmentationClass         语义分割png图（基于类别）
         └── SegmentationObject        实例分割png图（基于目标）
```

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-14/2022-10-14_11-39-50-410.png)

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-14/2022-10-14_17-39-54-719.png)

如果IOU>0.5则预测为正样本，检测到同一目标多个检测，第一个检测为正样本，其余视为负样本

### MS COCO

MS COCO是一个非常大型且常用的数据集，其中包括了目标检测，分割，图像描述等。其主要特性如下：

- Object segmentation: 目标级分割
- Recognition in context: 图像情景识别
- Superpixel stuff segmentation: 超像素分割
- 330K images (>200K labeled): 超过33万张图像，标注过的图像超过20万张
- 1.5 million object instances: 150万个对象实例
- 80 object categories: 80个目标类别
- 91 stuff categories: 91个材料类别
- 5 captions per image: 每张图像有5段情景描述
- 250,000 people with keypoints: 对25万个人进行了关键点标注

http://cocodataset.org/

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-14/2022-10-14_11-54-59-495.png)



MS COCO标注文件格式

官网有给出一个关于标注文件的格式说明，可以通过以下链接查看：
<https://cocodataset.org/#format-data>

![](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1665884955753.png)





下面是使用`pycocotools`读取图像以及对应bbox信息的简单示例：

```
Linux系统安装pycocotools：
pip install pycocotools  
Windows系统安装pycocotools：
pip install pycocotools-windows
```

```python
import os
from pycocotools.coco import COCO
from PIL import Image, ImageDraw
import matplotlib.pyplot as plt

json_path = "/data/coco2017/annotations/instances_val2017.json"
img_path = "/data/coco2017/val2017"

# load coco data
coco = COCO(annotation_file=json_path)

# get all image index info
ids = list(sorted(coco.imgs.keys()))
print("number of images: {}".format(len(ids)))

# get all coco class labels
coco_classes = dict([(v["id"], v["name"]) for k, v in coco.cats.items()])

# 遍历前三张图像
for img_id in ids[:3]:
    # 获取对应图像id的所有annotations idx信息
    ann_ids = coco.getAnnIds(imgIds=img_id)

    # 根据annotations idx信息获取所有标注信息
    targets = coco.loadAnns(ann_ids)

    # get image file name
    path = coco.loadImgs(img_id)[0]['file_name']

    # read image
    img = Image.open(os.path.join(img_path, path)).convert('RGB')
    draw = ImageDraw.Draw(img)
    # draw box to image
    for target in targets:
        x, y, w, h = target["bbox"]
        x1, y1, x2, y2 = x, y, int(x + w), int(y + h)
        draw.rectangle((x1, y1, x2, y2))
        draw.text((x1, y1), coco_classes[target["category_id"]])

    # show image
    plt.imshow(img)
    plt.show()

```

下面是使用`pycocotools`读取图像segmentation信息的简单示例：

```python

import os
import random

import numpy as np
from pycocotools.coco import COCO
from pycocotools import mask as coco_mask
from PIL import Image, ImageDraw
import matplotlib.pyplot as plt

random.seed(0)

json_path = "/data/coco2017/annotations/instances_val2017.json"
img_path = "/data/coco2017/val2017"

# random pallette
pallette = [0, 0, 0] + [random.randint(0, 255) for _ in range(255*3)]

# load coco data
coco = COCO(annotation_file=json_path)

# get all image index info
ids = list(sorted(coco.imgs.keys()))
print("number of images: {}".format(len(ids)))

# get all coco class labels
coco_classes = dict([(v["id"], v["name"]) for k, v in coco.cats.items()])

# 遍历前三张图像
for img_id in ids[:3]:
    # 获取对应图像id的所有annotations idx信息
    ann_ids = coco.getAnnIds(imgIds=img_id)
    # 根据annotations idx信息获取所有标注信息
    targets = coco.loadAnns(ann_ids)

    # get image file name
    path = coco.loadImgs(img_id)[0]['file_name']
    # read image
    img = Image.open(os.path.join(img_path, path)).convert('RGB')
    img_w, img_h = img.size

    masks = []
    cats = []
    for target in targets:
        cats.append(target["category_id"])  # get object class id
        polygons = target["segmentation"]   # get object polygons
        rles = coco_mask.frPyObjects(polygons, img_h, img_w)
        mask = coco_mask.decode(rles)
        if len(mask.shape) < 3:
            mask = mask[..., None]
        mask = mask.any(axis=2)
        masks.append(mask)

    cats = np.array(cats, dtype=np.int32)
    if masks:
        masks = np.stack(masks, axis=0)
    else:
        masks = np.zeros((0, height, width), dtype=np.uint8)

    # merge all instance masks into a single segmentation map
    # with its corresponding categories
    target = (masks * cats[:, None, None]).max(axis=0)
    # discard overlapping instances
    target[masks.sum(0) > 1] = 255
    target = Image.fromarray(target.astype(np.uint8))

    target.putpalette(pallette)
    plt.imshow(target)
    plt.show()

```

#### 验证目标检测任务mAP

![](https://gitee.com/dingtom1995/picture/raw/master/2022-10-16/2022-10-16_11-19-56-917.png)

根据官方文档给的预测结果格式可以看到，我们需要以列表的形式保存结果，列表中的每个元素对应一个检测目标（每个元素都是字典类型），每个目标记录了四个信息：

- image_id记录该目标所属图像的id（int类型）
- category_id记录预测该目标的类别索引，注意这里索引是对应stuff中91个类别的索引信息（int类型）
- bbox记录预测该目标的边界框信息，注意对应目标的[xmin, ymin, width, height] (list[float]类型)
- score记录预测该目标的概率（float类型）



AP@.5(IOU=0.5)；AP@.75(IOU=0.75)

mAP=(mAP.5:.05:.95) /10      （ start from 0.5 to 0.95 with a step size of 0.05十个mAP平均）

# FCN

FCN首先将一幅RGB图像输入到卷积神经网络后，经过多次卷积以及池化过程得到一系列的特征图，然后**利用反卷积层对最后一个卷积层得到的特征图进行上采样**，使得上采样后特征图与原图像的大小一样，从而实现对特征图上的每个像素值进行预测的同时保留其在原图像中的空间位置信息，最后对上采样特征图进行逐像素分类，逐个像素计算softmax分类损失。

主要特点：

- 不含全连接层（FC）的全卷积（Fully Conv）网络。从而可适应任意尺寸输入。
- 引入增大数据尺寸的反卷积（Deconv）层。能够输出精细的结果。
- 结合不同深度层结果的跳级（skip）结构。同时确保鲁棒性和精确性。

![quicker_fdb13fc8-47b1-42ca-8b6d-ad8d4ce801c2.png](https://s2.loli.net/2022/04/01/aAzVlHI98TebNSZ.png)

反卷积和卷积类似，都是相乘相加的运算。只不过后者是多对一，前者是一对多。而反卷积的前向和反向传播，只用颠倒卷积的前后向传播即可。如下图所示：

![quicker_e53dd05f-9c15-4350-95b3-a538a553abab.png](https://s2.loli.net/2022/04/01/FCKN4js6LuwVXEl.png)要注意的是，unpooling和反卷积是有几种不同的操作的。反池化很好理解，其中一种比较常用的操作是只需记住池化过程中的位置（最大值出现的地方），在unpooling的过程中，将对应位置置相应的值，其余位置置0即可。另外还有一种操作就是将所有unpooling的值全部填充下采样（pooling）的值。



经过全卷积后的结果进行反卷积，基本上就能实现语义分割了，但是得到的结果通常是比较粗糙的。

图中，image是原图像，conv1,conv2..,conv5为卷积操作，pool1,pool2,..pool5为pool操作（pool就是使得图片变为原图的1/2），注意con6-7是最后的卷积层，最右边一列是upsample后的end to end结果。**必须说明的是图中nx是指对应的特征图上采样n倍（即变大n倍），并不是指有n个特征图，如32x upsampled 中的32x是图像只变大32倍，不是有32个上采样图像，又如2x conv7是指conv7的特征图变大2倍。**

**（1）FCN-32s过程**

只需要留意第一行，网络里面有5个pool，所以conv7的特征图是原始图像1/32，可以发现最左边image的是32x32（假设以倍数计），同时我们知道在FCN中的卷积是不会改变图像大小（或者只有少量像素的减少，特征图大小基本不会小很多）。看到pool1是16x16，pool2是8x8，pool3是4x4，pool4是2x2，pool5是1x1，所以conv7对应特征图大小为1x1，然后再经过32x upsampled prediction 图片变回32x32。FCN作者在这里增加一个卷积层，卷积后的大小为输入图像的**32**(2^5)倍，我们简单假设这个卷积核大小也为32，这样就是需要通过反馈训练32x32个权重变量即可让图像实现end to end，完成了一个32s的upsample。FCN作者称做后卷积，他也提及可以称为反卷积。事实上在源码中卷积核的大小为64，同时没有偏置bias。还有一点就是FCN论文中最后结果都是21×*，这里的21是指FCN使用的数据集分类，总共有21类。

**（2）FCN-16s过程**

现在我们把1,2两行一起看，忽略32x upsampled prediction，说明FCN-16s的upsample过程。FCN作者在conv7先进行一个2x conv7操作，其实这里也只是增加1个卷积层，这次卷积后特征图的大小为conv7的**2**倍，可以从pool5与2x conv7中看出来。此时2x conv7与pool4的大小是一样的，FCN作者提出对pool4与2x conv7进行一个fuse操作（**事实上就是将pool4与2x conv7相加，另一篇博客说是拼接，个人认为是拼接**）。fuse结果进行16x upsampled prediction，与FCN-32s一样，也是增加一个卷积层，卷积后的大小为输入图像的**16**(2^4)倍。我们知道pool4的大小是2x2，放大16倍，就是32x32，这样最后图像大小也变为原来的大小，至此完成了一个16s的upsample。现在我们可以知道，FCN中的upsample实际是通过增加卷积层，通过bp反馈的训练方法训练卷积层达到end to end，这时**卷积层的作用可以看作是pool的逆过程**。

**（3）FCN-8s过程**

这是我们看第1行与第3行，忽略32x upsampled prediction。conv7经过一次4x upsample，即使用一个卷积层，特征图输出大小为conv7的4倍，所得4x conv7的大小为4x4。然后pool4需要一次2x upsample，变成2x pool4，大小也为4x4。再把4x conv7，2x pool4与pool3进行fuse，得到求和后的特征图。最后增加一个卷积层，使得输出图片大小为pool3的8倍，也就是8x upsampled prediction的过程，得到一个end to end的图像。实验表明**FCN-8s优于FCN-16s，FCN-32s**。 我们可以发现，如果继续仿照FCN作者的步骤，我们可以对pool2，pool1实现同样的方法，可以有FCN-4s，FCN-2s，最后得到end to end的输出。这里作者给出了明确的结论，超过FCN-8s之后，结果并不能继续优化。

![quicker_56103cc9-bdb3-4cab-bb6a-c12d18d7965a.png](https://s2.loli.net/2022/04/01/GMbESzDXdZeiwaq.png)

图中，image是原图像，conv1,conv2..,conv5为卷积操作，pool1,pool2,..pool5为pool操作（pool就是使得图片变为原图的1/2），注意con6-7是最后的卷积层，最右边一列是upsample后的end to end结果。**必须说明的是图中nx是指对应的特征图上采样n倍（即变大n倍），并不是指有n个特征图，如32x upsampled 中的32x是图像只变大32倍，不是有32个上采样图像，又如2x conv7是指conv7的特征图变大2倍。**

**（1）FCN-32s过程**

只需要留意第一行，网络里面有5个pool，所以conv7的特征图是原始图像1/32，可以发现最左边image的是32x32（假设以倍数计），同时我们知道在FCN中的卷积是不会改变图像大小（或者只有少量像素的减少，特征图大小基本不会小很多）。看到pool1是16x16，pool2是8x8，pool3是4x4，pool4是2x2，pool5是1x1，所以conv7对应特征图大小为1x1，然后再经过32x upsampled prediction 图片变回32x32。FCN作者在这里增加一个卷积层，卷积后的大小为输入图像的**32**(2^5)倍，我们简单假设这个卷积核大小也为32，这样就是需要通过反馈训练32x32个权重变量即可让图像实现end to end，完成了一个32s的upsample。FCN作者称做后卷积，他也提及可以称为反卷积。事实上在源码中卷积核的大小为64，同时没有偏置bias。还有一点就是FCN论文中最后结果都是21×*，这里的21是指FCN使用的数据集分类，总共有21类。

**（2）FCN-16s过程**

现在我们把1,2两行一起看，忽略32x upsampled prediction，说明FCN-16s的upsample过程。FCN作者在conv7先进行一个2x conv7操作，其实这里也只是增加1个卷积层，这次卷积后特征图的大小为conv7的**2**倍，可以从pool5与2x conv7中看出来。此时2x conv7与pool4的大小是一样的，FCN作者提出对pool4与2x conv7进行一个fuse操作（**事实上就是将pool4与2x conv7相加，另一篇博客说是拼接，个人认为是拼接**）。fuse结果进行16x upsampled prediction，与FCN-32s一样，也是增加一个卷积层，卷积后的大小为输入图像的**16**(2^4)倍。我们知道pool4的大小是2x2，放大16倍，就是32x32，这样最后图像大小也变为原来的大小，至此完成了一个16s的upsample。现在我们可以知道，FCN中的upsample实际是通过增加卷积层，通过bp反馈的训练方法训练卷积层达到end to end，这时**卷积层的作用可以看作是pool的逆过程**。

**（3）FCN-8s过程**

这是我们看第1行与第3行，忽略32x upsampled prediction。conv7经过一次4x upsample，即使用一个卷积层，特征图输出大小为conv7的4倍，所得4x conv7的大小为4x4。然后pool4需要一次2x upsample，变成2x pool4，大小也为4x4。再把4x conv7，2x pool4与pool3进行fuse，得到求和后的特征图。最后增加一个卷积层，使得输出图片大小为pool3的8倍，也就是8x upsampled prediction的过程，得到一个end to end的图像。实验表明**FCN-8s优于FCN-16s，FCN-32s**。 我们可以发现，如果继续仿照FCN作者的步骤，我们可以对pool2，pool1实现同样的方法，可以有FCN-4s，FCN-2s，最后得到end to end的输出。这里作者给出了明确的结论，超过FCN-8s之后，结果并不能继续优化。

结合上述的FCN的全卷积与upsample，在upsample最后加上softmax，就可以对不同类别的大小概率进行估计，实现end to end。最后输出的图是一个概率估计，对应像素点的值越大，其像素为该类的结果也越大。**FCN的核心贡献在于提出使用卷积层通过学习让图片实现end to end分类。**



> **事实上，FCN有一些短处**，例如使用了较浅层的特征，因为fuse操作会加上较上层的pool特征值，导致高维特征不能很好得以使用，同时也因为使用较上层的pool特征值，导致FCN对图像大小变化有所要求，如果测试集的图像远大于或小于训练集的图像，FCN的效果就会变差。

# SegNet

Segnet是用于进行像素级别图像分割的全卷积网络，分割的核心组件是一个encoder 网络，及其相对应的decoder网络，后接一个象素级别的分类网络。

encoder网络：其结构与VGG16网络的前13层卷积层的结构相似。

decoder网络：作用是将由encoder的到的低分辨率的feature maps 进行映射得到与输入图像feature map相同的分辨率进而进行像素级别的分类。

最终解码器的输出被送入soft-max分类器以独立的为每个像素产生类别概率。

![quicker_15980490-03ee-472c-8b84-902828eeac1f.png](https://s2.loli.net/2022/04/01/VN5MAh1e46WJUBK.png)

Segnet的亮点：**decoder利用与之对应的encoder阶段中进行max-pooling时的pooling index 进行非线性上采样**，这样做的好处是上采样阶段就不需要进行学习。 上采样后得到的feature maps 是非常稀疏的，因此，需要进一步选择合适的卷积核进行卷积得到dense featuremaps 。

![quicker_70e69d53-897c-4edc-b1a3-da05921c3a62.png](https://s2.loli.net/2022/04/01/5u3fUAXbPiFWe1B.png)





#  UNet

![quicker_e2b7c8fb-0081-4b01-b46c-69b87f6858f4.png](https://s2.loli.net/2022/03/31/JFk612wB48r9mvl.png)



1. 首先进行Conv+Pooling下采样；
2. 然后反卷积进行上采样，crop之前的低层feature map，进行融合；
3. 再次上采样。
4. 重复这个过程，直到获得输出388x388x2的feature map，
5. 最后经过softmax获得output segment map。总体来说与FCN思路非常类似。



**UNet的encoder下采样4次，一共下采样16倍，对称地，其decoder也相应上采样4次，将encoder得到的高级语义特征图恢复到原图片的分辨率。**



它采用了与FCN不同的**特征融合**方式：

1. FCN采用的是**逐点相加**，对应tensorflow的tf.add()函数
2. U-Net采用的是**channel维度拼接融合**，对应tensorflow的tf.concat()函数

 Unet结构特点

> UNet相比于FCN和Deeplab等，共进行了4次上采样，并在同一个stage使用了skip connection，而不是直接在高级语义特征上进行监督和loss反传，这样就保证了最后恢复出来的特征图融合了更多的low-level的feature，也使得不同scale的feature得到了的融合，从而可以进行多尺度预测和DeepSupervision。4次上采样也使得分割图恢复边缘等信息更加精细。

为什么适用于医学图像？

> \1. 因为医学图像边界模糊、梯度复杂，需要较多的高分辨率信息。高分辨率用于精准分割。
> \2. 人体内部结构相对固定，分割目标在人体图像中的分布很具有规律，语义简单明确，低分辨率信息能够提供这一信息，用于目标物体的识别。
>
> 
>
> UNet结合了**低分辨率信息（提供物体类别识别依据）和高分辨率信息**（提供精准分割定位依据），完美适用于医学图像分割。

#  DeepLab

基于全卷积对称语义分割模型得到的分割结果比较粗糙，忽略了像素与像素之间的空间一致性关系。于是Google提出了一种新的扩张卷积语义分割模型，考虑了像素与像素之间的空间一致性关系，可以在不增加数量的情况下增加感受野。

![quicker_4964e7e0-fcc8-4e8d-9159-998a8891536c.png](https://s2.loli.net/2022/04/01/V1MXwxTdqUYjEly.png)

- Deeplabv1是由深度卷积网路和概率图模型级联而成的语义分割模型，由于深度卷积网路在重复最大池化和下采样的过程中会丢失很多的细节信息，所以采用**扩张卷积算法增加感受野以获得更多上下文信**息。考虑到深度卷积网路在图像标记任务中的空间不敏感性限制了它的定位精度，采用了**完全连接条件随机场（Conditional Random Field， CRF）来提高模型捕获细节的能力**。
- Deeplabv2予以分割模型增加了ASPP（Atrous spatial pyramid pooling）结构，利用多个不同采样率的扩张卷积提取特征，再将特征融合以捕获不同大小的上下文信息。
- Deeplabv3语义分割模型，在ASPP中加入了全局平均池化，同时在平行扩张卷积后添加批量归一化，有效地捕获了全局语义信息。
- DeepLabV3+语义分割模型在Deeplabv3的基础上增加了编-解码模块和Xception主干网路，增加编解码模块主要是为了恢复原始的像素信息，使得分割的细节信息能够更好的保留，同时编码丰富的上下文信息。增加Xception主干网络是为了采用深度卷积进一步提高算法的精度和速度。在inception结构中，先对输入进行1\*1卷积，之后将通道分组，分别使用不同的3\*3卷积提取特征，最后将各组结果串联在一起作为输出。

> 主要特点：
>
> - 在多尺度上为分割对象进行带洞空间金字塔池化（ASPP）
> - 通过使用DCNNs（空洞卷积）提升了目标边界的定位
> - 降低了由DCNN的不变性导致的定位准确率



# RefineNet

RefineNet采用了通过细化中间激活映射并分层地将其链接到结合多尺度激活，同时防止锐度损失。网络由独立的RefineNet模块组成，每个模块对应于ResNet。

每个RefineNet模块由三个主要模块组成，即剩余卷积单元（RCU），多分辨率融合（MRF）和链剩余池（CRP）。RCU块由一个自适应块组成卷积集，微调预训练的ResNet权重对于分割问题。MRF层融合不同的激活物使用卷积上采样层来创建更高的分辨率地图。最后，在CRP层池中使用多种大小的内核用于从较大的图像区域捕获背景上下文。

![quicker_2cd234a9-55a2-4f01-9df6-f668a22809c5.png](https://s2.loli.net/2022/04/01/n4CqyKYlxwZm6D9.png)

>主要特点：
>
>- 提出一种多路径refinement网络，称为RefineNet。这种网络可以使用各个层级的features，使得语义分割更为精准。
>- RefineNet中所有部分都利用resdiual connections (identity mappings)，使得梯度更容易短向或者长向前传，使端对端的训练变得更加容易和高效。
>- 提出了一种叫做chained residual pooling的模块，它可以从一个大的图像区域捕捉背景上下文信息。
>
>

#  PSPNet

深度卷积神经网络的每一层特征对语义分割都有影响，如何将高层特征的语义信息与底层识别的边界与轮廓信息结合起来是一个具有挑战性的问题。

金字塔场景稀疏网络语义分割模型（Pyramid Scene Parsing Network，PSP）首先结合预训练网络 ResNet和扩张网络来提取图像的特征，得到原图像 1/8 大小的特征图，然后，采用金字塔池化模块将特征图同时通过四个并行的池化层得到四个不同大小的输出，将四个不同大小的输出分别进行上采样，还原到原特征图大小，最后与之前的特征图进行连接后经过卷积层得到最后的预测分割图像。

- PSPNet为像素级场景解析提供了有效的全局上下文先验
- 金字塔池化模块可以收集具有层级的信息，比全局池化更有代表性
- 在计算量方面，我们的PSPNet并没有比原来的空洞卷积FCN网络有很大的增加
- 在端到端学习中，全局金字塔池化模块和局部FCN特征可以被同时训练

![quicker_94da9d25-ad62-4f89-81d5-fff7e145a90e.png](https://s2.loli.net/2022/04/01/VIU1rKW4LQJhZFt.png)



>主要特点：
>
>- 金字塔场景解析网络是建立在FCN之上的基于像素级分类网络。将大小不同的内核集中在一起激活地图的不同区域创建空间池金字塔。
>- 特性映射来自网络被转换成不同分辨率的激活，并经过多尺度处理池层，稍后向上采样并与原始层连接进行分割的feature map。
>- 学习的过程利用辅助分类器进一步优化了像ResNet这样的深度网络。不同类型的池模块侧重于激活的不同区域地图。
>
>





# 基于全卷积的GAN语义分割模型

生成对抗网络模型（Generative Adversarial Nets，GAN）同时训练生成器 G 和判别器 D，判别器用来预测给定样本是来自于真实数据还是来自于生成模型。

利用对抗训练方法训练语义分割模型，将传统的多类交叉熵损失与对抗网络相结合，首先对对抗网络进行预训练，然后使用对抗性损失来微调分割网络，如下图所示。左边的分割网络将 RGB 图像作为输入，并产生每个像素的类别预测。右边的对抗网络将标签图作为输入并生成类标签（1 代表真实标注，0 代表合成标签）。

![quicker_e26e71f8-565c-4c58-9a87-bc6a2e90f368.png](https://s2.loli.net/2022/04/01/u6DhPVCH3jqMIip.png)









# 基于全卷积语义分割模型对比

| 名称           | 优点                                                         | 缺点                                                         |
| :------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| FCN            | 可以接受任意大小的图像输入；避免了采用像素块带来的重复存储和计算的问题 | 得到的结果不太精确，对图像的细节不敏感，没有考虑像素与像素之间的关系，缺乏空间一致性 |
| SegNet         | 使用去池化对特征图进行上采样，在分割中保持细节的完整性；去掉全连接层，拥有较少的参数 | 当对低分辨率的特征图进行去池化时，会忽略邻近像素的信息       |
| Deconvnet      | 对分割的细节处理要强于 FCN，位于低层的filter 能捕获目标的形状信息，位于高层的 filter能够捕获特定类别的细节信息，分割效果更好 | 对细节的处理难度较大                                         |
| U-net          | 简单地将编码器的特征图拼接至每个阶段解码器的上采样特征图，形成了一个梯形结构；采用跳跃连接架构，允许解码器学习在编码器池化中丢失的相关性 | 在卷积过程中没有加pad，导致在每一次卷积后，特征长度就会减少两个像素，导致网络最后的输出与输入大小不一样 |
| DeepLab        | 使用了空洞卷积；全连接条件随机场                             | 得到的预测结果只有原始输入的 1/8 大小                        |
| RefineNet      | 带有解码器模块的编码器-解码器结构；所有组件遵循残差连接的设计方式 | 带有解码器模块的编码器-解码器结构；所有组件遵循残差连接的设计方式 |
| PSPNet         | 提出金字塔模块来聚合背景信息；使用了附加损失                 | 采用四种不同的金字塔池化模块，对细节的处理要求较高           |
| GCN            | 提出了带有大维度卷积核的编码器-解码器结构                    | 计算复杂，具有较多的结构参数                                 |
| DeepLabV3 ASPP | 采用了Multigrid；在原有的网络基础上增加了几个 block；提出了ASPP，加入了   BN | 不能捕捉图像大范围信息，图像层的特征整合只存在于 ASPP中      |
| GAN            | 提出将分割网络作为判别器，GAN   扩展训练数据，提升训练效果；将判别器改造为 FCN，从将判别每一个样本的真假变为每一个像素的真假 | 没有比较与全监督+半监督精调模型的实验结果，只体现了在本文中所提创新点起到了一定的作用，但并没有体现有效的程度 |











# 图像增强

```python
#                                      OpenCV
# 水平翻转
plt.imshow(cv2.flip(img, 0)
# 水平翻转
plt.imshow(cv2.flip(img, 0)
# 随机裁剪
x, y = np.random.randint(0, 256), np.random.randint(0, 256)
plt.imshow(img[x:x+256, y:y+256])

#                                    albumentations           
# 水平翻转
augments = A.HorizontalFlip(p=1)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
# 随机裁剪
augments = A.RandomCrop(p=1, height=256, width=256)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
# 旋转
augments = A.ShiftScaleRotate(p=1)(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']

Compose            
transforms：转换类的数组，list类型
bbox_params：用于 bounding boxes 转换的参数，BboxPoarams 类型
keypoint_params：用于 keypoints 转换的参数， KeypointParams 类型
additional_targets：key新target 名字，value 为旧 target 名字的 dict，如 {'image2': 'image'}，dict 类型
p：使用这些变换的概率，默认值为 1.0           
组合与随机选择（Compose & OneOf）           
image4 = Compose([
        RandomRotate90(),
        # 翻转
        Flip(),
        Transpose(),
        OneOf([
            # 高斯噪点
            IAAAdditiveGaussianNoise(),
            GaussNoise(),
        ], p=0.2),
        OneOf([
            # 模糊相关操作
            MotionBlur(p=.2),
            MedianBlur(blur_limit=3, p=0.1),
            Blur(blur_limit=3, p=0.1),
        ], p=0.2),
        ShiftScaleRotate(shift_limit=0.0625, scale_limit=0.2, rotate_limit=45, p=0.2),
        OneOf([
            # 畸变相关操作
            OpticalDistortion(p=0.3),
            GridDistortion(p=.1),
            IAAPiecewiseAffine(p=0.3),
        ], p=0.2),
        OneOf([
            # 锐化、浮雕等操作
            CLAHE(clip_limit=2),
            IAASharpen(),
            IAAEmboss(),
            RandomBrightnessContrast(),            
        ], p=0.3),
        HueSaturationValue(p=0.3),
    ], p=1.0)

augments = image4(image=img, mask=mask)
img_aug, mask_aug = augments['image'], augments['mask']
           
```



