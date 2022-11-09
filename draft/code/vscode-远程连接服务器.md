# ssh
```ssh user_name@server_ip -L 127.0.0.1:12345:127.0.0.1:1111```


1.安装Remote Development插件
2.点击 远程资源管理器，鼠标移到 SSH TARGETS 点击设置
![](https://upload-images.jianshu.io/upload_images/18339009-1fab03576641c92d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
点击config文件
![](https://upload-images.jianshu.io/upload_images/18339009-477e43e2758b7520.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
填写自己的服务器配置，Host为在VS Code内显示的名称，可以随意填写，Hostname是远程服务器的公网IP地址，User 是用于登录的用户名称。
![](https://upload-images.jianshu.io/upload_images/18339009-7f4cfce9d852a657.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3.VS Code 设置，搜索 Show Login Terminal，勾选下方出现的 ‘Always reveal the SSH login terminal’。
4.在刚才配置的远程连接条目上的按钮，点击 Connect to Host in New Window
![](https://upload-images.jianshu.io/upload_images/18339009-bc2813e17b66ecd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5.
