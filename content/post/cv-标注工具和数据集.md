---
    title: "cv-标注工具和数据集"
    categories: 
        - cv
    date: 2022-12-18T15:39:56+08:00
---





# 标注工具



## EISeg

[PaddleSeg](https://github.com/PaddlePaddle/PaddleSeg/tree/release/2.2/contrib/EISeg)

```bash
pip install paddlepaddle
pip install eiseg
eiseg

```

- 启动`EISeg`后，在右上角选择模型类型。

这里以下载针对通用场景的高精度模型为例`hrnet18_ocr64_cocolvis`，点击对应权重下载地址进行[下载](https://github.com/PaddlePaddle/PaddleSeg/tree/release/2.2/contrib/EISeg)。

- 添加类别标签

在工具右侧点击添加标签按钮，添加自己分割任务中的目标类别。

- 导出类别标签

然后建议导出标签信息（比如保存到`label.txt`文件中），方便下次使用

- 标注图片

鼠标左键点一下增加一个正样本（图中会出现一个绿色的星号），然后模型会自动帮我们去抠出它认为的目标。

鼠标右键，增加一个负样本点（图中会出现一个红色的星号），然后网络会把标注有负样本的区域给去掉。

标注完成后点击空格，会生成该目标的多边形边界（注意里面还包含了目标的bbox信息，即目标检测任务的信息），我们可以在该边界上新增（鼠标在**边缘上**左键双击）或者删除点（鼠标在**边缘点上**左键双击），并且拖动这些点的位置进行微调。

标注完一张图片后点击保存按钮，然后会在当前文件夹下生成一个`label`文件夹



### 生成的标注信息

打开目标标注信息的目录，可以看到针对每张图片会默认会生成3张图片和一个`coco.json`文件（这个文件是所有图片共用的，和COCO的json的文件一样）。

- 其中`1.png`是之前在PASCAL VOC数据中介绍的语义分割mask标签文件（注意，这个文件并没有加上调色板信息。它就是一个灰度图，灰度值对应类别id）可参考我之前写的。
- `1_pseudo.png`是一个彩色mask标签文件（其实只要`1.png`就够了），方便可视化。
- `1_foreground.png`就是把前景抠出来了，也是方便可视化，感觉没啥用。
- `coco.json`是MS COCO的数据格式，具体参考我之前讲的MS COCO数据集，注意里面还包含了目标的bbox信息，即目标检测任务的信息（说明目标检测任务也能用）。

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-18/2022-12-18_19-31-03-556.png)







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

PASCAL VOC挑战赛主要包括以下几类：`图像分类(Object Classification)`，`目标检测(Object Detection)`，`目标分割(Object Segmentation)`，`行为识别(Action Classification)` 等。

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

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-07/2022-12-07_23-39-56-649.png)



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



 




