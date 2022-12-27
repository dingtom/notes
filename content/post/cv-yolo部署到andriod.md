---
    title: "cv-yolo部署到andriod"
    categories: 
        - cv
    date: 2022-12-25T22:46:12+08:00
---





下载andriod studio；下载安卓代码

```bash
#下载ncnn-yolov5-android源码
git clone https://github.com/nihui/ncnn-android-yolov5
#下载ncnn-android-vulkan包
https://github.com/Tencent/ncnn/releases
#选择ncnn-20210525-android-vulkan.zip下载

#将下载好的ncnn-20210525-android-vulkan.zip解压后放在放在ncnn/cpp下
#将CMakeLists.txt中ncnn_dir  的ncnn-20210525-android-vulkan去掉
```

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_09-21-10-121.png)

这样就能安装到手机了，接下来将模型替换成自己的





导出自己的onnx文件

```bash
#export.py生成onnx文件
python models/export.py --weights yolov5s.pt
#简化onnx文件
python -m onnxsim yolov5s.onnx yolov5s-sim.onnx
```

安装protobuf、ncnn；生成yolov5s.param和yolov5s.bin文件

```bash
#准备基础安装环境
sudo apt install build-essential libopencv-dev cmake
#如果报错就更换镜像源，比如你是Ubuntu20，就搜ubuntu20镜像源更换。

#编译安装protobuf依赖库
git clone -b v3.20.1-rc1 https://github.com/protocolbuffers/protobuf.git 
cd protobuf
./autogen.sh
./configure
make
sudo make install
sudo ldconfig
protoc --version

#编译安装ncnn
git clone https://github.com/Tencent/ncnn.git
cd ncnn
mkdir build
cd build
cmake ..
make
sudo make install

#在ncnn-master/build/tools/onnx下打开终端
./onnx2ncnn yolov5s-sim.onnx yolov5s.param yolov5s.bin
```

将生成的yolov5s.bin、yolov5s.param文件放到assets文件夹下并替换原来的

把yolov5ncnn_jni.cpp文件的class_names改成自己数据集的

编辑yolov5s.param文件，将Reshape 后面对应的0=6400、0=1600、0=400均修改为0=-1，这是为了解决实际中出现的多检测框的问题

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_09-24-35-885.png)

将param文件中permute部分与stride部分中blob_name后面的数字对应起来（修改stride 16和32部分），修改2个 `Permute` 节点的 `output`

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_09-23-26-365.png)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-26/2022-12-26_09-23-47-794.png)