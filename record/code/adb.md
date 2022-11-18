[TOC]
# [ubuntu安装adb](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=ubuntu%E5%AE%89%E8%A3%85adb&oq=cmder%25E8%25AE%25BE%25E7%25BD%25AE%25E8%25AF%25AD%25E8%25A8%2580&rsv_pq=8db175f200001e9d&rsv_t=de81Ubap7qPFkQnAoW3Q1cJ1UX1lzoc69kiQ%2Bo1eHqVNuET2O2cFgOV8lwU&rqlang=cn&rsv_enter=1&rsv_dl=tb&inputT=7762&rsv_sug3=110&rsv_sug1=17&rsv_sug7=100&rsv_sug2=0&rsv_sug4=7762)

># 卸载内置手机软件
>
>adb devices 
>
>查看已连接的设备  
>
>adb shell pm list packages > a.txt
>显示所有应用 
>
>
>
>adb shell pm uninstall --user 0 com.miui.contentextension
>
>传送门
>
>adb shell pm uninstall --user 0 com.android.browser
>
>浏览器
>
>adb shell pm uninstall --user 0 com.miui.voicetrigger
>
>语音唤醒
>
>adb shell pm uninstall --user 0 com.miui.personalassistant
>
>智能助理
>
>adb shell pm uninstall --user 0 com.xiaomi.mirror
>
>miui+
>
>adb shell pm uninstall --user 0 com.miui.miservice
>
>服务与反馈
>
>adb shell pm uninstall --user 0 com.miui.vipservice
>
>我的服务
>
>adb shell pm uninstall --user 0 com.xiaomi.aiasst.service
>
>小爱通话
>
>adb shell pm uninstall --user 0 com.miui.systemAdSolution 
>（小米系统广告解决方案，必删）
>adb shell pm uninstall --user 0 com.miui.analytics 
>（小米广告分析，必删）
>adb shell pm uninstall --user 0 com.xiaomi.gamecenter.sdk.service 
>（小米游戏中心服务）
>adb shell pm uninstall --user 0 com.xiaomi.gamecenter 
>（小米游戏中心）
>adb shell pm uninstall --user 0 com.miui.player  
>（小米音乐）
>adb shell pm uninstall --user 0 com.miui.video 
>（小米视频）
>adb shell pm uninstall --user 0 com.miui.notes  
>（小米便签）
>adb shell pm uninstall --user 0 com.android.email  
>（邮件）
>adb shell pm uninstall --user 0 com.xiaomi.scanner 
>（小米扫描）
>adb shell pm uninstall --user 0 com.miui.hybrid 
>（混合器）
>adb shell pm uninstall --user 0 com.miui.bugreport 
>（bug 反馈）
>adb shell pm uninstall --user 0 com.milink.service 
>（米连服务）
>
>adb shell pm uninstall --user 0 com.miui.gallery  
>（相册）
>adb shell pm uninstall --user 0 com.miui.yellowpage 
>（黄页）
>adb shell pm uninstall --user 0 com.xiaomi.payment  
>（小米支付）
>
>adb shell pm uninstall --user 0  com.xiaomi.aiasst.vision
>
>翻译
>
>adb shell pm uninstall --user 0  com.xiaomi.ab
>
>商城
>
>adb shell pm uninstall --user 0 com.xiaomi.migameservice
>
>游戏高能时刻
>
>adb shell pm uninstall --user 0 com.google.android.gsf
>
>adb shell pm uninstall --user 0 com.google.android.gms
>
>adb shell pm uninstall --user 0 com.android.vending
>
>谷歌
>
>adb shell pm uninstall --user 0 com.mipay.wallet  
>（小米钱包）
>adb shell pm uninstall --user 0 com.miui.screenrecorder  
>（屏幕录制）
>adb shell pm uninstall --user 0 com.android.wallpaper  
>（壁纸）
>adb shell pm uninstall --user 0 com.miui.fm  
>（收音机）
>adb shell pm uninstall --user 0 com.android.cellbroadcastreceiver  
>（小米广播）
>adb shell pm uninstall --user 0 com.xiaomi.mitunes  
>（小米助手）








# 先安装adb
1.[adb下载](https://blog.csdn.net/weixin_41948075/article/details/88857134)


2.在变量里找到PATH，添加你的adb.exe的路径
# 手机连接adb
查看已连接的设备：```adb devices ```

会显示serial number 和 device

有线：

手机打开开发者调试，打开cmd窗口，输入adb version 即可看到你的adb版本

无线：

手机先有线连接
确定手机和电脑端的wifi通讯端口:```adb tcpip 5555 ```
查看手机ip：```adb shell netcfg```
拔掉usb线，再```adb connect 192.168.0.117```

# adb常用命令 
adb 命令如下：
```
adb [-d|-e|-s <serialNumber>] <command>
```

如果只有一个设备直接使用 adb <command> 
多个设备-s <serialNumber>	指定相应 serialNumber 号的设备/模拟器为命令目标




# 文件
操作文件和文件夹有时会出现权限不够，Read-only file system。就需要adb remount 操作，获得权限。

创建xxx的文件夹

```mkdir xxx```

删除名字为xxx的文件夹及其里面的所有文件

```rm -r xxx```

删除文件xxx

```rm xxx ```

删除xxx的文件夹 

```rmdir xxx ```

推送文件到设备：```adb push  C:\Users\csi\Videos\a.png /sdcard/Download/browser ```

本机路径容易获取，但是手机路径如何获取呢？可以使用```adb shell```然后利用``ls``列出文件，最后要退出adb shell
因为adb shell不能执行adb phsh/pull命令

拉取文件到电脑：```adb pull /sdcard/Download/browser/UNv1.0.en-zh.tar.gz.00 C:\Users\csi\Videos```

# 应用

查看已安装应用

```adb shell pm list package```

列出第三方应用

```adb shell pm list package -3```

-s：列出系统应用
-f：列出应用包名及对应的apk名及存放位置
-i：列出应用包名及其安装来源:
参数组合使用，例如查找第三方应用tencent的包名、存放位置及安装来源：
```adb shell pm list package -f -3 -i tencent```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-368bbd6b2e0bea85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

列出对应包名的 .apk 位置

```adb shell pm path com.tencent.mm```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-7e95b16440d71836.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

安装应用 :``` adb install [-r] xxx.apk```

-r	允许覆盖安装。
-s	将应用安装到 sdcard。
-d	允许降级覆盖安装。
apk路径则可以直接将apk文件拖进cmd窗口，记得加空格。

pm install , 安装应用

目标 apk 若是存放在 PC 端，用 adb install 安装
目标 apk 若是存放于 Android 设备上，用 pm install 安装
如：
```pm uninstall ```, 卸载应用，同 adb uninstall , 后面跟的参数都是应用的包名

清除应用数据与缓存

```adb shell pm clear （apk包名）``` 
![image.png](https://upload-images.jianshu.io/upload_images/18339009-e645fc3cc1d99cee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

设置应用安装位置

```pm set-install-location ```

获取应用安装位置

``` pm get-install-location ```

卸载应用 ```adb uninstall （应用的包名） ```

-k 卸载应用但保留数据和缓冲 
![image.png](https://upload-images.jianshu.io/upload_images/18339009-f19e1b8d313ae484.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```adb shell pm uninstall --user 0 com.miui.systemAdSolution```

删除系统应用：

```adb remount ```（重新挂载系统分区，使系统分区重新可写）。
有些设备并不能直接adb remount，必须要先以root身份进入，先执行```adb root```，在执行adb remount
```adb shell```
```cd system/app/```
```ls```
```rm   ？？.apk```

adb 命令是 adb 这个程序自带的一些命令，而 adb shell 则是调用的 Android 系统中的命令，这些 Android 特有的命令都放在了 Android 设备的 system/bin 目录下。

查看wifi密码: 

```adb shell cat /data/misc/wifi/*.conf```

am start :启动一个应用，这里以启动我们设备的“设置”应用为例

```adb shell am start -n com.android.settings/.Settings```

若不清楚“设置”的包名，可以通过命令，先找出当前开启的设备，然后复制路径即可

```adb shell dumpsys activity | find “mFocusedActivity”```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-34bdd00fcd737fa4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1.先打开app界面

查看当前app的入口
```adb shell dumpsys window windows | findstr "Current"```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-e291939379b3e2e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2.打开App方式 1

```adb shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n com.eg.android.AlipayGphone/com.eg.android.AlipayGphone.AlipayLogin```
方式2
```adb shell am start -n  com.eg.android.AlipayGphone/com.eg.android.AlipayGphone.AlipayLogin```

杀死某个进程：(三个步骤)

```adb shell```
```ps```查看进程命令
```kill pid```

强制停止应用：

```adb shell am force-stop com.eg.android.AlipayGphone```
# 交互

###### input命令
完整 help 信息如下：
```
Usage: input [<source>] <command> [<arg>...]

The sources are:
      mouse
      keyboard
      joystick
      touchnavigation
      touchpad
      trackball
      stylus
      dpad
      gesture
      touchscreen
      gamepad

The commands and default sources are:
      text <string> (Default: touchscreen)
      keyevent [--longpress] <key code number or name> ... (Default: keyboard)
      tap <x> <y> (Default: touchscreen)
      swipe <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)
      press (Default: trackball)
      roll <dx> <dy> (Default: trackball)

```

比如使用 `adb shell input keyevent <keycode>` 命令，不同的 keycode 能实现不同的功能，完整的 keycode 列表详见 [KeyEvent](https://link.jianshu.com/?t=https%3A%2F%2Fdeveloper.android.com%2Freference%2Fandroid%2Fview%2FKeyEvent.html)，摘引部分我觉得有意思的如下：

| keycode | 含义 |
| --- | --- |
| 3 | HOME 键 |
| 4 | 返回键 |
| 5 | 打开拨号应用 |
| 6 | 挂断电话 |
| 24 | 增加音量 |
| 25 | 降低音量 |
| 26 | 电源键 |
| 27 | 拍照（需要在相机应用里） |
| 64 | 打开浏览器 |
| 82 | 菜单键 |
| 85 | 播放/暂停 |
| 86 | 停止播放 |
| 87 | 播放下一首 |
| 88 | 播放上一首 |
| 122 | 移动光标到行首或列表顶部 |
| 123 | 移动光标到行末或列表底部 |
| 126 | 恢复播放 |
| 127 | 暂停播放 |
| 164 | 静音 |
| 176 | 打开系统设置 |
| 187 | 切换应用 |
| 207 | 打开联系人 |
| 208 | 打开日历 |
| 209 | 打开音乐 |
| 210 | 打开计算器 |
| 220 | 降低屏幕亮度 |
| 221 | 提高屏幕亮度 |
| 223 | 系统休眠 |
| 224 | 点亮屏幕 |
| 231 | 打开语音助手 |
| 276 | 如果没有 wakelock 则让系统休眠 |

下面是 `input` 命令的一些用法举例。

###### 电源键
```adb shell input keyevent 26```
###### 菜单键
```adb shell input keyevent 82```
###### HOME 键
```adb shell input keyevent 3```
###### 返回键
```adb shell input keyevent 4```
###### 音量控制
增加音量：
```adb shell input keyevent 24```
降低音量：
```adb shell input keyevent 25```
静音：
```adb shell input keyevent 164```
###### 媒体控制
播放/暂停：
```adb shell input keyevent 85```
停止播放：
```adb shell input keyevent 86```
播放下一首：
```adb shell input keyevent 87```
播放上一首：
```adb shell input keyevent 88```
恢复播放：
```adb shell input keyevent 126```
暂停播放：
```adb shell input keyevent 127```
###### 点亮/熄灭屏幕
可以通过上文讲述过的模拟电源键来切换点亮和熄灭屏幕，但如果明确地想要点亮或者熄灭屏幕，那可以使用如下方法。

点亮屏幕：
```adb shell input keyevent 224```
熄灭屏幕：
```adb shell input keyevent 223```



###### 发送文本内容，但是改命令无法发送中文
```adb shell input text [文本内容（英文）]```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-31891c94b1253cc6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###### input tap ：点击屏幕
点击屏幕上坐标为 （200 200 ）的位置
```adb shell input tap 200 200```

###### input swipe ：滑动屏幕
从（520 300）滑到（ 520 1000）
```adb shell input swipe 520 300 520 1000```

###### input keyevent 点击按键
```adb shell input keyevent```

###### 截屏：```adb shell screencap /sdcard/a.png ```

###### 录制屏幕
```adb shell screenrecord /sdcard/filename.mp4```
需要导出到电脑：
```adb pull /sdcard/filename.mp4```
--size WIDTHxHEIGHT	视频的尺寸，比如 1280x720，默认是屏幕分辨率。
--bit-rate RATE	视频的比特率，默认是 4Mbps。
--time-limit TIME	录制时长，单位秒。
--verbose	输出更多信息。

###### 打印 Android 的系统日志: ```adb logcat```
可以通过重定向来将日志保存到指定的文件中 比如将其保存到G盘的一个文件adb logcat > G:\log\logcat.txt

###### 显示屏参数
```adb shell dumpsys window displays```

###### 型号
```adb shell getprop ro.product.model```

###### 电池状况
```adb shell dumpsys battery```


### adbd cannot run as root in production builds问题

###### 验证你的手机是否已经root了
```adb shell```
```su```
行命令后，$ 变为 # 即 表示root 成功
###### 安装adbd-insecure.apk
adb install adbd-insecure.apk
###### 设置
打开应用将Enable insecure adbd 和 enable at boot 勾选上，设置好之后重进键入：adb root即可
下载地址：[adbd-insecure.apk](https://pan.baidu.com/s/1hr6al4S)

###### 查看当前分区挂载情况。
```mount```

找到其中我们关注的带 /system 的那一行：
```
/dev/block/platform/msm_sdcc.1/by-name/system /system ext4 ro,seclabel,relatime,data=ordered 0 0
```
###### 重新挂载。
```
mount -o remount,rw -t yaffs2 /dev/block/platform/msm_sdcc.1/by-name/system /system
```
这里的 /dev/block/platform/msm_sdcc.1/by-name/system 就是我们从上一步的输出里得到的文件路径。如果输出没有错误的话，可以对 /system 下的文件操作了

###### 设置系统日期和时间
注：需要 root 权限。
```adb shell date -s 20160823.131500```
表示将系统日期和时间更改为 2016 年 08 月 23 日 13 点 15 分 00 秒。

######重启手机
```adb reboot```

######使用 Monkey 进行压力测试
Monkey 可以生成伪随机用户事件来模拟单击、触摸、手势等操作，可以对正在开发中的程序进行随机压力测试。
```adb shell monkey -p <packagename> -v 500```
表示向 <packagename> 指定的应用程序发送 500 个伪随机事件。

# 刷机相关命令
重启到 Recovery 模式
```adb reboot recovery```

从 Recovery 重启到 Android
```adb reboot```

重启到 Fastboot 模式
```adb reboot bootloader```

通过 sideload 更新系统
如果我们下载了 Android 设备对应的系统更新包到电脑上，那么也可以通过 adb 来完成更新。
以 Recovery 模式下更新为例：重启到 Recovery 模式。
```adb reboot recovery```
在设备的 Recovery 界面上操作进入 Apply update-Apply from ADB。
注：不同的 Recovery 菜单可能与此有差异，有的是一级菜单就有 Apply update from ADB。

通过 adb 上传和更新系统。
```adb sideload <path-to-update.zip>```

查看实时资源占用情况

```adb shell top```

" daemon not running. starting it now on port 5037 ADB server didn't ACK

一般出现这种情况都是因为其他其他程序占用了5037端口（比如豌豆荚，刷机精灵等），
通过以下命令可以找出哪个进程占用了5037端口
```netstat -ano | findstr "5037"```
