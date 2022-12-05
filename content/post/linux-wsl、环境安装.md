

---
    title: "linux-wsl、深度学习环境安装"
    categories: 
        - linux
    date: 2022-12-05T20:43:26+08:00
---

# wsl

```shell
wsl -l  
#列出所有已安装虚拟机
wsl -l -o 
#列出网上可用的系统
wsl -t ubuntu 
#关闭ubuntu
wsl --shutdown 
#关闭所有系统及虚拟机引擎
wsl -d ubuntu 
#启动ubuntu并进行终端
wsl -u root 
#以root身份支行
wsl --install 
#安装默认虚拟机(ubuntu)
wsl 
#启动默认虚拟机并进入终端
wsl -s 
#虚拟机名 进入虚拟机
wsl --unregister ubuntu  
#卸载虚拟机
wsl --set-default-version 2 
#将wsl2设置为默认版本

#换源
sudo apt-key adv --fetch-keys http://mirrors.aliyun.com/nvidia-cuda/ubuntu1804/x86_64/7fa2af80.pub;
sudo sh -c 'echo "deb http://mirrors.aliyun.com/nvidia-cuda/ubuntu1804/x86_64 /" > /etc/apt/sources.list.d/cuda.list';
sudo apt update
sudo apt upgrade

```

## 安装WSL的CUDA驱动

https://developer.nvidia.com/cuda/wsl

## 设置cuda环境变量

```bash
#在主目录下的~/.bashrc文件添加如下路径：
sudo su -
vim ~/.bashrc

#末尾添加并保存：
export CUDA_HOME=/usr/local/cuda
export PATH=$PATH:$CUDA_HOME/bin
export LD_LIBRARY_PATH=/usr/local/cuda-10.1/lib64${LD_LIBRARY_PATH:+:${LD_LIBRARY_PATH}}
# 然后更新配置文件：
source ~/.bashrc

#如果提示缺少相应的依赖库，直接执行如下代码自动安装相应的依赖库
sudo apt-get install freeglut3-dev build-essential libx11-dev libxmu-dev libxi-dev libgl1-mesa-glx libglu1-mesa libglu1-mesa-dev

#查看cuda是否安装成功：
nvcc -V
```

## 安装cuda-toolkit

[最新版CUDA](https://developer.nvidia.com/cuda-downloads?target_os=Linux&target_arch=x86_64&Distribution=WSL-Ubuntu&target_version=2.0)

[CUDA历史版本(根据个人需要)](https://developer.nvidia.com/cuda-toolkit-archive)

![](https://gitee.com/tomding1995/picture/raw/master/2022-12-05/2022-12-05_21-02-44-536.png)

## 安装cudnn



```bash
#cuda和cudnn对应版本
https://developer.nvidia.com/cudnn
#cudnn_download
https://developer.nvidia.com/rdp/cudnn-archive
#然后打开终端执行：
tar -zxvf cudnn-10.2-linux-x64-v8.0.4.30.tgz
sudo cp -P cuda/lib64/libcudnn* /usr/local/cuda-11.0/lib64/
sudo cp  cuda/include/cudnn.h /usr/local/cuda-11.0/include/
#为所有用户设置读取权限：
sudo chmod a+r /usr/local/cuda-11.0/include/cudnn.h 
sudo chmod a+r /usr/local/cuda-11.0/lib64/libcudnn*
```

## 安装anaconda

```python
# https://mirrors.tuna.tsinghua.edu.cn/anaconda/archive/
bash Anaconda3xxx.sh

sudo vim ~/.bashrc
export PATH=/home/tomding/anaconda3/bin:$PATH
source ~/.bashrc 

#创建虚拟环境：
conda create -n tf
#复制环境
conda create -n tf_new --clone tf
#删除
conda remove -n tf --all
#删除没有用的包
conda clean -p    
#删除tar包  
conda clean -t      
#激活虚拟环境：
source activate tf
#如果要退出：输入
source deactivate tf
```

### 更换源

```shell
pip install  gensim  -i https://pypi.douban.com/simple

# 清华源
conda config --add channels https://pypi.tuna.tsinghua.edu.cn/simple
# 阿里源
conda config --add channels https://mirrors.aliyun.com/pypi/simple/
#豆瓣源
conda config --add channels http://pypi.douban.com/simple/ 
#中科大源
conda config --add channels https://pypi.mirrors.ustc.edu.cn/simple/
# 设置搜索时显示通道地址
conda config --set show_channel_urls yes
#显示镜像源
conda config --show-sources
#删除镜像源
conda config --remove channels https://pypi.mirrors.ustc.edu.cn/simple/
#删除清华源改回默认源
conda config --remove-key channels

# 或修改.condarc文件
channels:
  - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
  - defaults
show_channel_urls: true

# win pip永久提速
#1. 打开cmd输入set命令查看用户目录USERPROFILE；
#2. 目录中创建一个pip目录,建一个文件 pip.ini
#3. 在 pip.ini 文件输入：

[global]
index-url = https://pypi.douban.com/simple
[install]
trusted-host = pypi.douban.com
12345

# Linux pip永久提速

#1. 打开terminal
#2. 输入命令：
cd home
mkdir .pip
vim .pip/pip.conf

[global］
index-url = https://pypi.doubanio.com/simple/
timeout = 1000
【install】
use-mirrors = true
mirrors = https://pypi.doubanio.com//
123456
```

### 卸载conda

```shell
# 卸载
rm -rf /home/txp/anaconda3
# 打开终端并输入：
sudo gedit ~/.bashrc
#在.bashrc文件末尾删除之前添加的路径：
export PATH=/home/lq/anaconda3/bin:$PATH
#保存并关闭文件
source ~/.bashrc
#关闭终端，然后再重启一个新的终端
```

# pytoch

```python
# https://pytorch.org/
import torch
x = torch.rand(5,3)
print(x, torch.cuda.is_available())
```

