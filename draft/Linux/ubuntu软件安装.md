更新显卡驱动
```sudo ubuntu-drivers autoinstall```

deb 安装格式
```sudo dpkg -i <package.deb>```

Ubuntu主机没有开启SSH服务，需要开启openssh-server：

```sudo apt-get install openssh-server```

# 去掉一些没用的插件 

``` sudo apt-get remove libreoffice-common ```

``` sudo apt-get remove unity-webapps-common```  

``` sudo apt-get remove thunderbird totem rhythmbox empathy brasero simple-scan gnome-mahjongg aisleriot    ```

``` sudo apt-get remove gnome-mines cheese transmission-common gnome-orca webbrowser-app gnome-sudoku  landscape-client-ui-install   ```

``` sudo apt-get remove onboard deja-dup   ```

# vmware tool 安装

```cd vmware-tools-distrib```
```sudo ./vmware-install.pl```

安装deb 
sudo apt-get install gdebi

# 先把所有软件源和软件更新到最新 

 sudo apt-get update   

 sudo apt-get upgrade 

# ubuntu-安装pycharm
1）进入[PyCharm官网]点击DOWNLOAD([https://www.jetbrains.com/pycharm/download/#section=linux](https://www.jetbrains.com/pycharm/download/#section=linux)) 
2）解压文件。右键安装包，点击“Extract Here” 
3 )把 pycharm-2019.1.1 文件夹放在Downloads根目录
4 )cd pycharm-2019.1.1/bin
5 )sh ./pycharm.sh 
6 )出现Complete-Installation提示框

# anaconda及tensorflow
## 删除整个anaconda目录：

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

## 安装anaconda
```bash Anaconda3-5.3.1-Linux-x86_64.sh```

naconda会自动将环境变量添加到PATH里面，如果后面你发现输出conda 提示没有该命令，那么你需要source ~/.bashrc 这样就是更新环境变量，就可以正常使用了。 如果发现这样还是没用，那么需要收到添加环境变量 sudo gedit ~/.bashrc，在最后面加上export PATH=/home/tomding/anaconda3/bin:$PATH

保存退出后：source ~/.bashrc 再次输入conda list测试看看，应该就是没有问题啦！

## 添加Jupyter

conda install jupyter 启动 jupyter notebook

## 安装tensorflow

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


# 美化
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

# 终端
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



#  chrome 

 https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb 

``` sudo dpkg -i google-chrome*; sudo apt-get -f install ```

 卸载

```sudo apt-get autoremove google-chrome-stable ```

 # VMware 
[下载VMware for Linux链接](https://download3.vmware.com/software/wkst/file/VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle)

```
 sudo chmod +x ./VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle

 sudo ./VMware-Workstation-Full-12.5.7-5813279.x86_64.bundle
```

VY1DU-2VXDH-08DVQ-PXZQZ-P2KV8
 # 安装jdk： 

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
