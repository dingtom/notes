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
