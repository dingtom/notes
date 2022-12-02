---
    # 文章标题
    title: "ml-gpu版pytorch安装"
    # 分类
    categories: 
        - ml
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
    
--- 




# 驱动安装

[NVIDIA 驱动程序下载]([官方 GeForce 驱动程序 | NVIDIA](https://www.nvidia.cn/geforce/drivers/))



```

查看显卡型号
lspci | grep -i nvidia

安装驱动
sudo bash NVIDIA-Linux-x86_64-455.23.04.run

查看显卡信息
nvidia-smi
```

# 卸载显卡驱动重新安装

命令行界面
Ctrl+Alt+F1

```shell
sudo apt-get --purge remove nvidia*
sudo apt autoremove
# To remove CUDA Toolkit:
sudo apt-get --purge remove "*cublas*" "cuda*"
# To remove NVIDIA Drivers:
sudo apt-get --purge remove "*nvidia*"
```

# 安装cuda

[驱动和cuda对应版本](https://docs.nvidia.com/cuda/cuda-toolkit-release-notes/index.html)

[cuda_download](https://developer.nvidia.com/cuda-toolkit-archive)

```shell
wget https://developer.download.nvidia.com/compute/cuda/repos/ubuntu2004/x86_64/cuda-ubuntu2004.pin
sudo mv cuda-ubuntu2004.pin /etc/apt/preferences.d/cuda-repository-pin-600
wget https://developer.download.nvidia.com/compute/cuda/11.1.0/local_installers/cuda-repo-ubuntu2004-11-1-local_11.1.0-455.23.05-1_amd64.deb
sudo dpkg -i cuda-repo-ubuntu2004-11-1-local_11.1.0-455.23.05-1_amd64.deb
sudo apt-key add /var/cuda-repo-ubuntu2004-11-1-local/7fa2af80.pub
sudo apt-get update
sudo apt-get -y install cuda
```



`查看cuda版本`


```
nvcc -V
```
不显示

>首先，查看cuda的bin目录下是否有nvcc：
```ls /usr/local/cuda/bin```
如果存在，直接将cuda路径加入系统路径即可：
```vim ~/.bashrc```进入配置文件；
添加以下两行：
```export PATH=/usr/local/cuda/bin:$PATH```
```export LD_LIBRARY_PATH=/usr/local/cuda/lib64:$LD_LIBRARY_PATH```
然后更新配置文件：
```source ~/.bashrc```

conda 安装cuda

```
conda install cudatoolkit=10.1 -c https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/linux-64/
```



# 安装cudnn

[cuda和cudnn对应版本](https://developer.nvidia.com/cudnn)

![](https://upload-images.jianshu.io/upload_images/18339009-532f67fce582eae0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[cudnn_download]((https://developer.nvidia.com/rdp/cudnn-archive#a-collapse742-10))

安装cuDNN比较简单，解压后把相应的文件拷贝到对应的CUDA目录下即可

```shell
tar -xvf cudnn-8.0-linux-x64-v5.1.tgz
sudo cp cuda/include/cudnn.h /usr/local/cuda/include/
sudo cp cuda/lib64/libcudnn* /usr/local/cuda/lib64/
sudo chmod a+r /usr/local/cuda/include/cudnn.h
sudo chmod a+r /usr/local/cuda/lib64/libcudnn*
```

conda安装cudnn

```conda install cudnn=7.6.5 -c https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/linux-64/```



# 安装anaconda
在 [清华大学开源软件镜像站](https://mirrors.tuna.tsinghua.edu.cn/help/anaconda/)下载

```ls```

```bash  Anaconda3-5.3.1-Linux-x86_64.sh```

一路回车


# 卸载
```rm -rf /home/txp/anaconda3```

打开终端并输入：

 ```sudo gedit ~/.bashrc```

在.bashrc文件末尾删除之前添加的路径：

```export PATH=/home/lq/anaconda3/bin:$PATH```

保存并关闭文件

```source ~/.bashrc```

 关闭终端，然后再重启一个新的终端
# 安装pytorch

```
conda create -n pytorch
```


>3070环境安装
>CUDA11.1和Cudnn8.0.4从官网安装
>使用```conda install pytorch torchvision cudatoolkit=11 -c pytorch-nightly```来安装pytorch
>使用tensorflow的话 可以先```conda install cudatoolkit=11``` 然后再```pip install tensorflow-gpu==1.15```
>先装好tf就无法安装cudatoolkit11，conda自带的是cudatoolkit10；
>先装好cudatoolkit11，再用conda装tf可能会报错，用pip不会

[pytorch安装命令](https://pytorch.org/)
```conda install pytorch torchvision cudatoolkit=10.1```


测试是否安装好
 ```
import torch
print(torch.__version__)
print(torch.cuda.is_available())
 ```
[手动下载安装具体步骤](https://codingchaozhang.blog.csdn.net/article/details/99688839?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param)


# 安装tensorflow

1、创建虚拟环境：
conda create -n tensorflow
复制环境
conda create -n tensorflow_new --clone Tensorflow_old
删除
conda remove -n rcnn --all
所有环境
删除没有用的包
conda clean -p    
删除tar包  
conda clean -t      
2、激活虚拟环境：
source activate tensorflow
PS:如果要退出：输入
source deactivate tensorflow
3、下面我们在虚拟环境里安装

安装CPU版本：
```
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple/ --upgrade tensorflow     # CPU版本
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple/ --upgrade tensorflow-gpu #  GPU版本
```
# 更换源

```
阿里云 
http://mirrors.aliyun.com/pypi/simple/
中国科技大学 
https://pypi.mirrors.ustc.edu.cn/simple/
豆瓣(douban) 
http://pypi.douban.com/simple/
清华大学 
https://pypi.tuna.tsinghua.edu.cn/simple/
中国科学技术大学 
http://pypi.mirrors.ustc.edu.cn/simple/

pip install  gensim  -i https://pypi.douban.com/simple
```

添加Anaconda的清华镜像

```shell
# 清华源
conda config --add channels https://pypi.tuna.tsinghua.edu.cn/simple
# 设置搜索时显示通道地址
conda config --set show_channel_urls yes
# 阿里源
conda config --add channels https://mirrors.aliyun.com/pypi/simple/
#豆瓣源
conda config --add channels http://pypi.douban.com/simple/ 
#中科大源
conda config --add channels https://pypi.mirrors.ustc.edu.cn/simple/

#显示镜像源
conda config --show-sources
#删除镜像源
conda config --remove channels https://pypi.mirrors.ustc.edu.cn/simple/
#删除清华源改回默认源
conda config --remove-key channels

或修改.condarc文件
channels:
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
  - defaults
show_channel_urls: true
```

pip永久提速

1. 打开cmd输入set命令查看用户目录USERPROFILE；
2. 目录中创建一个pip目录,建一个文件 pip.ini
3. 在 pip.ini 文件输入：

```cpp
[global]
index-url = https://pypi.douban.com/simple

[install]
trusted-host = pypi.douban.com
12345
```

`Mac 和 Linux 配置`

1. 打开terminal
2. 输入命令：

```cpp
mkdir .pip
vim .pip/pip.conf
```

（这两步是在home目录下新建文件: .pip/pip.conf）

```cpp
[global］
index-url = https://pypi.doubanio.com/simple/
timeout = 1000
【install】
use-mirrors = true
mirrors = https://pypi.doubanio.com//
123456
```

按ESC退出插入模式后，直接输入 :wq 回车，这样就会保存并退出刚才创建的文件和输入的内容了。