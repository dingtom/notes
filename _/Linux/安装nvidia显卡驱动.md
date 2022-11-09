- [ 安装cuda](#head1)
	- [ 查看cuda版本](#head2)
- [ 安装cudnn](#head3)
查看显卡型号

```lsb_release -a```

更新PCI ID数据库

```update-pciids```
```lspci | grep -i nvidia```

驱动安装

```wget https://us.download.nvidia.com/XFree86/Linux-x86_64/455.23.04/NVIDIA-Linux-x86_64-455.23.04.run```。

查看TMPDIR目录是否报错

```echo $TMPDIR```

安装驱动

```sudo bash NVIDIA-Linux-x86_64-455.23.04.run```

禁用nouveau

```lsmod | grep nouveau```

```vim /etc/modprobe.d/blacklist.conf```添加
```blacklist nouveau```

```sudo update-initramfs -u```

```sudo reboot now```

查看显卡信息

```nvidia-smi```

# <span id="head1"> 安装cuda</span>
[driver_version and cuda_version](https://docs.nvidia.com/cuda/cuda-toolkit-release-notes/index.html)

[cuda_download](https://developer.nvidia.com/cuda-toolkit-archive)

###### <span id="head2"> 查看cuda版本</span>
```nvcc -V```
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
# <span id="head3"> 安装cudnn</span>
[download](https://developer.nvidia.com/rdp/cudnn-archive)
```tar -xvf cudnn-8.0-linux-x64-v5.1.tgz```
安装cuDNN比较简单，解压后把相应的文件拷贝到对应的CUDA目录下即可

```sudo cp cuda/include/cudnn.h /usr/local/cuda/include/```
```sudo cp cuda/lib64/libcudnn* /usr/local/cuda/lib64/```
```sudo chmod a+r /usr/local/cuda/include/cudnn.h```
```sudo chmod a+r /usr/local/cuda/lib64/libcudnn*```

# 卸载显卡驱动重新安装
命令行界面
Ctrl+Alt+F1

```sudo apt-get --purge remove nvidia*```

```sudo apt autoremove```

To remove CUDA Toolkit:
```sudo apt-get --purge remove "*cublas*" "cuda*"```
To remove NVIDIA Drivers:
```sudo apt-get --purge remove "*nvidia*"```



