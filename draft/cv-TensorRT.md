# TensorRT

TensorRT支持几乎所有主流深度学习框架，将python框架转换成C++的TensorRT，从而可以加速推理。

- 算子融合(层与张量融合)：简单来说就是通过融合一些计算op或者去掉一些多余op来减少数据流通次数以及显存的频繁使用来提速
- 量化：量化即IN8量化或者FP16以及TF32等不同于常规FP32精度的使用，这些精度可以显著提升模型执行速度并且不会保持原先模型的精度
- 内核自动调整：根据不同的显卡构架、SM数量、内核频率等(例如1080TI和2080TI)，选择不同的优化策略以及计算方式，寻找最合适当前构架的计算方式
- 动态张量显存：我们都知道，显存的开辟和释放是比较耗时的，通过调整一些策略可以减少模型中这些操作的次数，从而可以减少模型运行的时间
- 多流执行：使用CUDA中的stream技术，最大化实现并行操作

当然，**TensorRT主要缺点是与特定GPU绑定，在不同型号上转换出来的模型不能通用**(这一点笔者暂未去从实践证实)

TensorRT官方在其[仓库](https://github.com/NVIDIA/TensorRT/tree/master/tools)提供了三个开源工具，之后有需要可以使用。


三个工具大致用途\[1\]：

- ONNX GraphSurgeon  
    可以修改我们导出的ONNX模型，增加或者剪掉某些节点，修改名字或者维度等等
- Polygraphy  
    各种小工具的集合，例如比较ONNX和trt模型的精度，观察trt模型每层的输出等等，主要用-来debug一些模型的信息
- PyTorch-Quantization  
    可以在Pytorch训练或者推理的时候加入模拟量化操作，从而提升量化模型的精度和速度，并且支持量化训练后的模型导出ONNX和TensorRT

Open Neural Network Exchange（ONNX，开放神经网络交换）格式，是微软和Facebook提出用来表示深度学习模型的开放格式，定义了一组和环境，平台均无关的标准格式，可使模型在不同框架之间进行转移\[2\]。

典型的几个线路\[3\]：

- Pytorch -> ONNX -> TensorRT
- Pytorch -> ONNX -> TVM
- TF -> onnx -> ncnn
- Pytorch -> ONNX -> tensorflow

ONNX结构是将每一个网络的每一层或者每一个算子当作节点Node，再由这些Node去构建一个Graph，最后将Graph和这个onnx模型的其他信息结合在一起，生成一个model。

可以通过在线网站[https://netron.app/](https://netron.app/)来查看ONNX模型结构。

# 实践上手

- 操作系统：Windows10
- 显卡：RTX2060
- CUDA版本：11.6
- Pytorch版本：1.7.1
- Python版本：3.8

## 查看CUDA版本

TensortRT非常依赖CUDA版本，在安装之前，需要先查看本机安装好的CUDA版本，查看方式有多种，第一种方式可以通过NVIDIA 控制面板查看；第二种方式可以通过在控制台输入`nvcc -V`进行查看。

## 安装TensorRT

首先需要到[Nvidia官网](https://developer.nvidia.com/nvidia-tensorrt-8x-download)去下载对应Cuda版本的TensorRT安装包。

我这里下载的是红框选中的这一个，这个版本支持CUDA11.0-11.7

```c
cd TensorRT-8.4.3.1\python
pip install tensorrt-8.4.3.1-cp38-none-win_amd64.whl

cd TensorRT-8.4.3.1\graphsurgeon
pip install graphsurgeon-0.4.6-py2.py3-none-any.whl

cd TensorRT-8.4.3.1\onnx_graphsurgeon
pip install onnx_graphsurgeon-0.3.12-py2.py3-none-any.whl

cd TensorRT-8.4.3.1\uff
pip install uff-0.6.9-py2.py3-none-any.whl
```

然后需要移动安装包里的一些文件：

```
将TensorRT-8.4.3.1\include中头文件拷贝到C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v11.6\include
将TensorRT-8.4.3.1\lib中所有lib文件拷贝到C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v11.6\lib\x64
将TensorRT-8.4.3.1\lib中所有dll文件拷贝到C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v11.6\bin
```

注：这里的v11.6根据自己的Cuda版本号即可

之后，需要手动将`C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v11.6\bin`路径添加到用户Path环境变量中

之后进行验证：

```python
python
import tensorrt
print(tensorrt.__version__)
```

在`import`时发生报错，

> FileNotFoundError: Could not find: nvinfer.dll. Is it on your PATH?

此时只需要将缺少的文件找到，然后添加到上面的`bin`目录下即可，我这里是在安装的torch中lib文件下找到的部分文件，缺什么移什么即可。

如无报错，再次验证，可以输出tensorrt版本：

下面运行安装包里面的一个sample.py文件，以确保tensorrt能够正常工作。  
进入到下图所示的路径，运行`sample.py`，如果正常输出，则代表tensorrt安装成功。

如果提示没装pycuda，还需要再安装一下

```c
pip install pycuda
```

## [YOLOv5](https://so.csdn.net/so/search?q=YOLOv5&spm=1001.2101.3001.7020)使用TensorRT加速

这部分内容看到不少博客都是用Cmake编译生成yolov5的VS工程，非常繁琐麻烦，主要是这些博文写作时间较早。  
而在YOLOv5 6.0版本更新后，官方新增了一个`export.py`文件，支持大部分框架模型的导出，包括TensorRT。

下面我所使用的是[YOLOv5官方](https://github.com/ultralytics/yolov5)最新的6.2版本。

下面直接导出官方提供的yolov5s模型试试，终端输入：

```c
python export.py --weights yolov5s.pt --data data/coco128.yaml --include engine --device 0 --half
```

注：这里的`--half`表示半精度模型，使用半精度可以加快推理速度，但会损失一定精度，直接导出可以不加

初次导出，遇到如下报错

> ONNX: export failure 0.4s: Exporting the operator silu to ONNX opset version 12 is not supported.

这个报错需要修改pytorch的激活函数，找到该函数位置：`D:\anaconda\envs\pytorch\Lib\sitepackages\torch\nn\modules\activation.py`(此处结合自己的anaconda实际安装位置来更改)

修改代码如下：

```python
class SiLU(Module):

    __constants__ = ['inplace']
    inplace: bool
 
    def __init__(self, inplace: bool = False):
        super(SiLU, self).__init__()
        self.inplace = inplace
 
    def forward(self, input: Tensor) -> Tensor:
        # ------------------------------------- #
        # 把F.silu替换掉，修改后如下
        return input * torch.sigmoid(input)
 
        #原来的代码
        return F.silu(input, inplace=self.inplace)
```

再次导出，不再报错。

值得注意的是，YOLOv5并不会直接导出TensorRT模型，而是会先导出ONNX模型，然后将ONNX模型转换成TensorRT模型，因此导出完成后，会在模型位置处生成`yolov5s.onnx`和`yolov5s.engine`，`yolov5s.engine`就是可以用来直接推理的TensorRT模型。

经过实测，不添加半精度导出yolov5s模型花费时间99.5s，添加半精度之后，导出yolov5s模型花费时间404.2s。

# 实验结果

## 图片检测

首先是来检测一下图片的推理速度，首先修改detect.py，统计程序花费时间。

```python
if __name__ == "__main__":
    begin_time = time.time()
    opt = parse_opt()
    main(opt)
    end_time = time.time()
    print("程序花费时间{}秒".format(end_time-begin_time))
```

然后依次使用不同模型进行推理

```c
python detect.py --weights yolov5s.pt
python detect.py --weights yolov5s.engine
python val.py --weights yolov5s.pt
python val.py --weights yolov5s.engine
```

这里数据源选择的是coco128中128张图片，整体实验结果如下表所示：

|       模型       | 推理花费时间(s) | AP50  |
| :--------------: | --------------- | ----- |
|     原始模型     | 8.39            | 71.4% |
| TensorRT(全精度) | 5.45            | 71.1% |
| TensorRT(半精度) | 4.83            | 70.8% |

从数据可以发现，使用TensorRT加速之后，模型推理速度提升了约35%，但是模型精度并没有下降太多。coco数据集128张图片，模型训练和检测都是用同一份数据，这可能会对AP产生一定影响，于是再换用Visdrone数据集在进行实验。

下面对Visdrone数据集进行实验，使用yolov5m模型，训练100个epoch.

使用`VisDrone2019-DET-test-dev`中的1610张图片进行验证和检测：

验证命令：

```python
python val.py --data data/VisDrone.yaml --weights runs/train/exp3/weights/best.engine --batch-size 4 --task test
```

使用`VisDrone2019-DET-test-dev`中的

检测命令：

```python
python detect.py --weights runs/train/exp3/half/best.engine --source D:/Desktop/Work/Dataset/VisDrone/VisDrone2019-DET-test-dev/images --data data/VisDrone.yaml
```

实验结果如下表所示：

|     **模型**     | **验证花费时间(s)** | **P** | **R** | **AP50** | **推理花费时间(s)** |
| :--------------: | :-----------------: | :---: | :---: | :------: | :-----------------: |
|     yolov5m      |       101.28        | 43.1% | 34.9% |  32.0%   |       157.51        |
|       onnx       |       156.16        | 42.8% | 34.9% |  32.0%   |       158.97        |
| TensorRT(全精度) |       124.37        | 42.8% | 34.9% |  32.0%   |       144.93        |
| TensorRT(半精度) |       127.85        | 42.8% | 34.8% |  31.9%   |       139.97        |

由表可见，使用TensorRT加速之后，推理速度提升约了10%，同时精度只掉了0.3%，AP50基本上变化不大。

下面选一张图片来直观对比一下，左侧图为原始模型推理图，右侧图为TensorRT(半精度) 推理图，两者大致上差异不大，各有各的漏检对象。

## 视频检测

视频检测用了王者荣耀数据集做一个实验，比较了常规检测，tensorrt和onnx推理速度和帧率。推理所花费的时间分别为：

- 常规推理 程序花费时间20.88s
- onnx推理 程序花费时间25.68s
- tensorrt推理 程序花费时间16.03s

视频帧率如下视频所示：

YOLOv5：使用TensorRT加速效果对比

综合比较来看，tensorrt的推理速度最快，并且帧率会比原本检测帧率提升约20%左右，而用onnx模型推理，速度和帧率反而会变慢，这一点我是存在一些疑问的，看到一些博客说onnx模型对比pytorch模型速度更快，但我的实验结果并非如此，个人猜测是pytorch对模型优化已经做得比较出色，onnx模型作为一个通用标准模型，为了翻译功能，损失了部分性能。