---
        # 文章标题
        title: "dcoker-Docker"
        # 分类
        categories: 
            - dcoker
        # 发表日期
        date: 2022-12-02T02:11:14+08:00

        # 标签
        #tags:
        # 文章内容摘要
        #description: "{{ .Name }}" 
        # 最后修改日期
        #lastmod: {{ .Date }}
        # 文章内容关键字
        #keywords: "{{replace .Name "-" ","}}"
        # 原文作者
        #author:
        # 原文链接
        #link:
        # 图片链接，用在open graph和twitter卡片上
        #imgs:
        # 在首页展开内容
        #expand: true
        # 外部链接地址，访问时直接跳转
        #extlink:
        # 在当前页面关闭评论功能
        #comment:
        # enable: false
        # 关闭当前页面目录功能
        # 注意：正常情况下文章中有H2-H4标题会自动生成目录，无需额外配置
        #toc: false
        # 绝对访问路径
        #url: "{{ lower .Name }}.html"
        # 开启文章置顶，数字越小越靠前
        #weight: 1
        #开启数学公式渲染，可选值： mathjax, katex
        #math: mathjax
        # 开启各种图渲染，如流程图、时序图、类图等
        #mermaid: true
--- 

## 分区大小

/boot：这个目录下存放的是Ubuntu的引导项，Ubuntu的启动从这里开始，这里我分配了500MB空间，之后我们的EasyBCD也是引用这个文件。

![](https://upload-images.jianshu.io/upload_images/18339009-61254d915205a592.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

swap：该目录是Ubuntu的系统交换区，只有在系统内存不足的情况下，操作系统才会调用这篇区域，我设置了32GB
![](https://upload-images.jianshu.io/upload_images/18339009-298e27fef3e26c97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

/：该目录是Linux的根目录，类似于windows的C盘，Ubuntu中安装的软件都存放在该目录下，我分配了30GB
![](https://upload-images.jianshu.io/upload_images/18339009-9f52596b010cfdc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

/home：该目录是个人目录，类似于windows的D、E、F盘，要多分一些空间，这里我把剩余空间全都给了它。
![](https://upload-images.jianshu.io/upload_images/18339009-3df88dd1100330ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 更新

更新显卡驱动
```sudo ubuntu-drivers autoinstall```

deb 安装格式
```sudo dpkg -i <package.deb>```

Ubuntu主机没有开启SSH服务，需要开启openssh-server：

```sudo apt-get install openssh-server```

## 去掉一些没用的插件 

``` sudo apt-get remove libreoffice-common ```

``` sudo apt-get remove unity-webapps-common```  

``` sudo apt-get remove thunderbird totem rhythmbox empathy brasero simple-scan gnome-mahjongg aisleriot    ```

``` sudo apt-get remove gnome-mines cheese transmission-common gnome-orca webbrowser-app gnome-sudoku  landscape-client-ui-install   ```

``` sudo apt-get remove onboard deja-dup   ```

## vmware tool 安装

```cd vmware-tools-distrib```
```sudo ./vmware-install.pl```

安装deb 
sudo apt-get install gdebi

## 先把所有软件源和软件更新到最新 

 sudo apt-get update   

 sudo apt-get upgrade 

## ubuntu-安装pycharm
1）进入[PyCharm官网]点击DOWNLOAD([https://www.jetbrains.com/pycharm/download/#section=linux](https://www.jetbrains.com/pycharm/download/#section=linux)) 
2）解压文件。右键安装包，点击“Extract Here” 
3 )把 pycharm-2019.1.1 文件夹放在Downloads根目录
4 )cd pycharm-2019.1.1/bin
5 )sh ./pycharm.sh 
6 )出现Complete-Installation提示框

## anaconda及tensorflow
### 删除整个anaconda目录：

由于Anaconda的安装文件都包含在一个目录中，所以直接将该目录删除即可。到包含整个anaconda目录的文件夹下，删除整个Anaconda目录：
``` rm -rf anaconda文件夹名```
建议清理下.bashrc中的Anaconda路径：
1.到根目录下，打开终端并输入：      
``` sudo gedit ~/.bashrc```

2.在.bashrc文件末尾用#号注释掉之前添加的路径(或直接删除)：       
```#export PATH=/home/lq/anaconda3/bin:$PATH ```     
 保存并关闭文件

3.使其立即生效，在终端执行：    
  ```source ~/.bashrc      ```
4.关闭终端，然后再重启一个新的终端，这一步很重要，不然在原终端上还是绑定有anaconda

### 安装anaconda
```bash Anaconda3-5.3.1-Linux-x86_64.sh```

naconda会自动将环境变量添加到PATH里面，如果后面你发现输出conda 提示没有该命令，那么你需要source ~/.bashrc 这样就是更新环境变量，就可以正常使用了。 如果发现这样还是没用，那么需要收到添加环境变量 sudo gedit ~/.bashrc，在最后面加上export PATH=/home/tomding/anaconda3/bin:$PATH

保存退出后：source ~/.bashrc 再次输入conda list测试看看，应该就是没有问题啦！

### 添加Jupyter

conda install jupyter 启动 jupyter notebook

### 安装tensorflow

1、创建虚拟环境：

```conda create -n tensorflow```
2、激活虚拟环境：

```source activate tensorflow```
PS:如果要退出：输入

```source deactivate tensorflow ```
3、下面我们在虚拟环境里安装tensorflow:

```
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
conda config --set show_channel_urls yes

//删除清华源改回默认源
conda config --remove-key channels
```

安装CPU版本：
```pip install tensorflow==2.0.0 -i https://pypi.tuna.tsinghua.edu.cn/simple```
安装GPU版本：
```conda install tensorflow-gpu ```


## 美化
安装unity-tweak-tool

```
sudo apt-get install unity-tweak-tool
```

主题: flatabulous-theme

```
sudo add-apt-repository ppa:noobslab/themes
sudo apt-get update
sudo apt-get install flatabulous-theme
```

图标: paper-icon-theme

```
sudo add-apt-repository ppa:snwh/pulp
sudo apt-get update
sudo apt-get install paper-icon-theme
```


字体: fonts-wqy-microhei

```
sudo apt-get install fonts-wqy-microhei
```

## 终端
1、首先在终端里面用 gedit 打开配置文件（~/.bashrc），如：
```gedit ~/.bashrc```

2、在最后添加如下代码：
```
export PS1="\[\033[01;31m\]\u\[\033[00m\]@\[\033[01;32m\]10.185.25.224\[\033[00m\][\[\033[01;33m\]\t\[\033[00m\]]:\[\033[01;34m\]\w\[\033[00m\]\n$ "
```

或者
```
export PS1="\[\033[01;31m\]\u\[\033[00m\]@\[\033[01;32m\]\h\[\033[00m\][\[\033[01;33m\]\t\[\033[00m\]]:\[\033[01;34m\]\w\[\033[00m\]\n$ "
```

安装oh-my-zsh终端:

```
sudo apt-get install zsh
sudo apt-get install git
sudo wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh -O - | sh
cp ~/.oh-my-zsh/templates/zshrc.zsh-template ~/.zshrc
```



chrome 

 https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb 

``` sudo dpkg -i google-chrome*; sudo apt-get -f install ```

 卸载

```sudo apt-get autoremove google-chrome-stable ```

## VMware 

[下载VMware for Linux链接](https://download3.vmware.com/software/wkst/file/VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle)

```
 sudo chmod +x ./VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle

 sudo ./VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle
```

VY1DU-2VXDH-08DVQ-PXZQZ-P2KV8

安装jdk： 

1.官网下载JDK　　　
     地址: http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html选择相应的 .gz包下载 

2. 解压缩,放到指定目录(以jdk-8u202-linux-x64.gz为例)创建目录:
```sudo mkdir /usr/lib/jvm```
加压缩到该目录:
```sudo tar -zxvf jdk-8u202-linux-x64.tar.gz -C /usr/lib/jvm```
3.修改环境变量:　　
```sudo vim ~/.bashrc```
文件的末尾追加下面内容:
```
#set oracle jdk environment
export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_60  ## 这里要注意目录要换成自己解压的jdk 目录
export JRE_HOME=${JAVA_HOME}/jre  
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib  
export PATH=${JAVA_HOME}/bin:$PATH 
```
使环境变量马上生效

``` source ~/.bashrc```





## 两个屏幕设置分屏

查看已连接的屏幕
```xrandr```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-1435fe55eb595944.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
设置
```xrandr --output VGA1 --right-of DP2  --auto```
ok



## 修改网卡名称

查看网卡的名称
```ifconfig```

```sudo vi /etc/default/grub```
修改参数**GRUB_CMDLINE_LINUX**=**"net.ifnames=0 biosdevname=0"**
![image.png](https://upload-images.jianshu.io/upload_images/18339009-f46b869af44675b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

使之生效
```update-grub```

```sudo vi /etc/network/interfaces```

增加两行eth0为想要修改的名字

```
auto eth0

iface eth0 inet dhcp
```

重启

## 虚拟机扩容

原文地址：https://blog.csdn.net/weixin_39510813/article/details/78387334?fps=1&locationNum=7

这里是我的Ubuntu系统下现在的空间大小：

![image](https://upload-images.jianshu.io/upload_images/18339009-8548858385e03e20?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

一旦达到97%左右，系统会警告磁盘空间不足，在我的台式机上我已经扩展过了，今天扩展我的笔记本上的虚拟机，以此提供本篇博客的素材。

### 准备

我们首先需要咋vm虚拟机上进行磁盘的扩展：

![image](https://upload-images.jianshu.io/upload_images/18339009-63785a9437476e45?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在虚拟机Ubuntu系统处右键然后选择设置，选中磁盘，选择扩展磁盘容量，发现需要先关闭虚拟机，OK，先关闭虚拟机Ubuntu。

设置磁盘大小后点击扩展：

![image](https://upload-images.jianshu.io/upload_images/18339009-9e7c27d8f5b6e794?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

之后发现扩展成功，虚拟机vm提示从客户机操作系统内部对磁盘重新进行分区和扩展文件系统：

![image](https://upload-images.jianshu.io/upload_images/18339009-de09082afa3027f3?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 开始

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

### 最后

交换分区的开启是暂时的，没有设置到开机启动项中，因为我们并不总是需要开启虚拟内存，平时1G的内存空间完全满足了。

开机启动交换分区的办法 
sudo vim /etc/fstab
最后一行添加
 /dev/sda5 swap swap defaults 0 0





## 内核安装

查看内核版本
```uname -a```



### 修改软件源

1. 备份源配置文件

``` sudo cp /etc/apt/sources.list /etc/apt/sources.list_bak```

2. 用编辑器打开源配置文件

```sudo vim /etc/apt/sources.list```

在文件最后面增加一行并保存

```deb http://security.ubuntu.com/ubuntu trusty-security main```

3. 执行以下命令更新配置

```sudo apt-get update```

### 安装新内核

搜索可下载内核

```sudo apt-cache search linux-image```

1. 执行以下命令安装

```sudo apt-get install linux-image-extra-3.16.0-43-generic```

2. 执行以下命令查看是否安装成功

```dpkg -l | grep 3.16.0-43-generic```

3. 用编辑器打开 grub 配置文件

```sudo vim /etc/default/grub```

找到

```GRUB_DEFAULT=0```

修改为：

```GRUB_DEFAULT="Advanced options for Ubuntu>Ubuntu, with Linux 3.16.0-43-generic"```

4. 保存退出，然后执行以下命令更新 Grub 引导

```sudo update-grub```

5. 更新完成后重启系统

```sudo reboot```

### 删除多余内核

1\. 查看所有内核：

```dpkg --get-selections| grep linux```

2. 将其他版本的内核删除，如(对deinstall的需要用dpkg卸载)：

```sudo apt-get remove linux-headers-4.15.0-33```
``` sudo dpkg -P linux-image-4.8.0-36-generic```





## 安装samba服务器

```sudo apt-get install samba samba-common```
创建一个用于分享的samba目录。
```sudo mkdir /home/zut_csi/tomding/share```
给创建的这个目录设置权限
```sudo chmod 777 /home/zut_csi/tomding/share```
添加用户(下面的zut_csi是我的用户名，之后会需要设置samba的密码)。
```sudo smbpasswd -a zut_csi```
配置samba的配置文件。
```sudo vim /etc/samba/smb.conf```

在配置文件smb.conf的最后添加下面的内容：

```
[share]     # win添加网络地址  \\10.36.34.100\share     []为添加网络地址的文件目录
comment = share folder
browseable = yes
path = /home/zut_csi/tomding/share
create mask = 0700
directory mask = 0700
valid users = zut_csi
force user = zut_csi
force group = zut_csi
public = yes
browseable = yes
read only = no
available = yes
writable = yes
security = share
```

重启samba服务器。
```sudo service smbd restart```

