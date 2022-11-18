安装samba服务器。
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


