查看内核版本
```uname -a```
# 修改软件源

1. 备份源配置文件

``` sudo cp /etc/apt/sources.list /etc/apt/sources.list_bak```

2. 用编辑器打开源配置文件

```sudo vim /etc/apt/sources.list```

在文件最后面增加一行并保存

```deb http://security.ubuntu.com/ubuntu trusty-security main```

3. 执行以下命令更新配置

```sudo apt-get update```
# 安装新内核

搜索可下载内核

```sudo apt-cache search linux-image```

1. 执行以下命令安装

```sudo apt-get install linux-image-extra-3.16.0-43-generic```

2. 执行以下命令查看是否安装成功

```dpkg -l | grep 3.16.0-43-generic```

3. 用编辑器打开 grub 配置文件

```sudo vim /etc/default/grub```

找到

```GRUB_DEFAULT=0```

修改为：

```GRUB_DEFAULT="Advanced options for Ubuntu>Ubuntu, with Linux 3.16.0-43-generic"```


4. 保存退出，然后执行以下命令更新 Grub 引导

```sudo update-grub```


5. 更新完成后重启系统

```sudo reboot```

# 删除多余内核

1\. 查看所有内核：

```dpkg --get-selections| grep linux```

2. 将其他版本的内核删除，如(对deinstall的需要用dpkg卸载)：

```sudo apt-get remove linux-headers-4.15.0-33```
``` sudo dpkg -P linux-image-4.8.0-36-generic```
