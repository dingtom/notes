- [ 准备](#head1)
- [ 开始](#head2)
- [ 最后](#head3)
原文地址：https://blog.csdn.net/weixin_39510813/article/details/78387334?fps=1&locationNum=7


这里是我的Ubuntu系统下现在的空间大小：

![image](https://upload-images.jianshu.io/upload_images/18339009-8548858385e03e20?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

一旦达到97%左右，系统会警告磁盘空间不足，在我的台式机上我已经扩展过了，今天扩展我的笔记本上的虚拟机，以此提供本篇博客的素材。

## <span id="head1"> 准备</span>

我们首先需要咋vm虚拟机上进行磁盘的扩展：

![image](https://upload-images.jianshu.io/upload_images/18339009-63785a9437476e45?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在虚拟机Ubuntu系统处右键然后选择设置，选中磁盘，选择扩展磁盘容量，发现需要先关闭虚拟机，OK，先关闭虚拟机Ubuntu。

设置磁盘大小后点击扩展：

![image](https://upload-images.jianshu.io/upload_images/18339009-9e7c27d8f5b6e794?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

之后发现扩展成功，虚拟机vm提示从客户机操作系统内部对磁盘重新进行分区和扩展文件系统：

![image](https://upload-images.jianshu.io/upload_images/18339009-de09082afa3027f3?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head2"> 开始</span>

OK，我们对Windows磁盘进行分区等操作时一般会借助于一些软件进行磁盘分区的合并会拆分等，同样，我们本次在Ubuntu下也借助于该类型的软件帮助我们更好的实现磁盘的重新分区以及扩展文件系统，我们使用的软件是gparted，对于该软件这里不多做介绍，感兴趣的可以自行搜索。

打开我的计算机Ubuntu，打开终端，输入sudo apt-get install gparted安装gparted，然后sudo gparted运行，结果如下：

![image](https://upload-images.jianshu.io/upload_images/18339009-c69b4ef94528d01c?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

发现我们的为19G，交换分区大概1G，这就是我们之前的20G，在我们在vm为该Ubuntu扩容了磁盘到50G后，在最下面又出现了30G的未分配空间。

![image](https://upload-images.jianshu.io/upload_images/18339009-e13adb40562b76ee?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

大致说明一下，交换分区简单来说就是用于内存不足时作为虚拟内存来使用的，一般swap大小不要超过2G，我们这里设置为2G。

OK，首先禁用交换分区然后删掉原来的扩展分区，这样我们的50G磁盘就只有主分区和未分配了：

禁用——删除

![image](https://upload-images.jianshu.io/upload_images/18339009-ed6ef7f66be1f50a?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

然后增加主分区大小到48G：

![image](https://upload-images.jianshu.io/upload_images/18339009-696ebe2876c85da5?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

接着再新建扩展分区：

![image](https://upload-images.jianshu.io/upload_images/18339009-75dc682a17d4628d?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

增加逻辑分区，也就是交换分区：

大小默认即可，类型选择为逻辑分区，文件系统选择linux-swap。

![image](https://upload-images.jianshu.io/upload_images/18339009-bc8edd103cbab34c?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

然后选择gparted的应用全部：

![image](https://upload-images.jianshu.io/upload_images/18339009-23f6761771419ed4?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结果如下：

![image](https://upload-images.jianshu.io/upload_images/18339009-90822275e5b9adcd?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

OK，扩展磁盘完成，我们df再看一下：

![image](https://upload-images.jianshu.io/upload_images/18339009-c57164db020bff66?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

但是我们新建的扩展分区和交换分区可能没有挂载到文件系统，先查看一下交换分区：

![image](https://upload-images.jianshu.io/upload_images/18339009-b90e1cb6515d9a6b?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

发现交换分区为0，看来我们确实需要重新挂载或开启一下，我们的交换分区在dev/sda5：

sudo swapon /dev/sda5

然后查看一下，大小以字节为单位：

![image](https://upload-images.jianshu.io/upload_images/18339009-c2bd25a21a9c93df?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

看来是开启成功了。

## <span id="head3"> 最后</span>

交换分区的开启是暂时的，没有设置到开机启动项中，因为我们并不总是需要开启虚拟内存，平时1G的内存空间完全满足了。

开机启动交换分区的办法 
sudo vim /etc/fstab
最后一行添加
 /dev/sda5 swap swap defaults 0 0
