为了使树莓派能够方便的移动到不同的地方，我们设置了动态IP, 而有时需要快速SSH链接树莓派，我们除了可以通过VNC远程桌面查看IP, 还可以通过开机获取IP，然后通过微信推送到我的手机。

一.获取IP地址
之前的文章中已经提到在树莓派中获取IP可以使用下面方法：
```
import socket, fcntl, struct
def get_ip2(ifname):
   s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
   return socket.inet_ntoa(fcntl.ioctl(s.fileno(), 0x8915, struct.pack('256s', bytes(ifname[:15],'utf-8')))[20:24])
ip = get_ip2('wlan0')
```
二.微信推送
A. Server酱
官网：https://sct.ftqq.com/apps

可以通过几种微信和邮件的方式推送你想推送的消息，最方便的是通过企业微信的方式。先扫描微信登陆Server酱后就可以得到一个Sendkey, 我们需要这个Sendkey加入地址后，通过调用地址来向微信推送消息。

进入消息通道，通过推荐的企业微信下面的说明可以看到使用流程

a.设置企业微信
1.注册企业微信
自己可以在企业微信官网注册一个账号，填入电话号码和真实姓名姓名就可以了，可以在我的企业看到企业ID，这个ID后面需要用到。

2.企业微信自建一个应用
在首页应用管理，创建应用，填写一些基本信息后就可以创建应用，得到AgentId， Secret两个数据

b.设置消息通道
在企业ID，应用ID, 应用三个地方分别填上得到的数据，并保存。

c.调用方法
在Server酱首页Sendkey, 可以看到调用方法，可以先在他们的页面测试一下是否可以在微信里面收到推送。

我们这里采用PYTHON代码如下：
```
import requests
api = r'https://sctapi.ftqq.com/YOURSENDKEY.send'
title = ''.join(['IP:', ip])
content = ''

data = {
  "text":title,
  "desp":content
}
req = requests.post(api,data = data)
```

三.关注微信插件
在企业微信首页微信插件页面填写一些信息，然后微信扫描二维码即可关注微信插件，然后我们就可以接受推送消息了。



三.开机启动
命令```sudo vim /etc/rc.local```， 在 exit 0前面添加一行：

``` su pi -c "python3 代码的绝对地址 &"```

保存退出

注意：由于开机时由于没有网络，得不到IP地址，代码会出错，所以我们需要等待片刻等链接网络后再去获取地址

所以我加了一个循环：
```
while True:
   try:
       ip = get_ip2('wlan0')
   except OSError:
       continue
   break
```
四.全部代码
```
# encoding:utf-8
import requests
import socket, fcntl, struct


#获取IP地址
def get_ip2(ifname):
   s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
   return socket.inet_ntoa(fcntl.ioctl(s.fileno(), 0x8915, struct.pack('256s', bytes(ifname[:15],'utf-8')))[20:24])  

#通过循环等待树莓派连上网络
while True:
   try:
       ip = get_ip2('wlan0')
   except OSError:
       continue
   break

#调用server API推送IP地址到微信企业号
api = r'https://sctapi.ftqq.com/SCT955TTzcEVqGbpPuOhzXlWfEPpVMM9.send'
title = ''.join(['IP:', ip])
content = ''

data = {
  "text":title,
  "desp":content
}
req = requests.post(api,data = data)
```
