- [ 内网](#head1)
# <span id="head1"> 内网</span>

1.生成配置文件

``` jupyter notebook --generate-config```

2.创建远程登录密码
   ```jupyter notebook password```
   注意，此方法生成的密码文件为/root/.jupyter/jupyter_notebook_config.json （如果是以root用户登录，其他用户路径同上说明），此为json文件，而且json文件里的密码的优先级要高于配置文件（jupyter_notebook_config.py）里的密码设置。

3.修改配置文件
```vim ~/.jupyter/jupyter_notebook_config.py ```

```
c.NotebookApp.ip='*'
c.NotebookApp.password = '密码'
c.NotebookApp.open_browser = False
c.NotebookApp.port =8888 #可自行指定一个端口, 访问时使用该端口
c.NotebookApp.allow_remote_access = True 
```
4.启动
```jupyter notebook```

5.远程登录
输入http://服务器的 IP 地址:8888，输入密码登录。

# 外网访问本地的Jupyter Notebook

**下载并解压holer软件包**

Holer软件包：[holer-xxx.tar.gz](https://yq.aliyun.com/go/articleRenderRedirect?url=https%3A%2F%2Fgithub.com%2Fwisdom-projects%2Fholer%2Ftree%2Fmaster%2FBinary%2FGo)

**获取holer access key信息**

在[holer官网上](https://yq.aliyun.com/go/articleRenderRedirect?url=http%3A%2F%2Fwdom.net)申请专属的holer access key或者使用[开源社区上公开的access key信息](https://yq.aliyun.com/go/articleRenderRedirect?url=https%3A%2F%2Fgithub.com%2Fwisdom-projects%2Fholer)。
例如申请得到的holer信息如下，这里以此holer信息为例：

```
---------------------------------------------
Holer Client : holerdemo@gmail.com
Access Key : 6688daebe02846t88s166733595eee5d
---------------------------------------------
Domain Name : holer65004.wdom.net
Internet Address : holer.org:65004
Local Address : 127.0.0.1:8888
---------------------------------------------
```

**启动holer服务**

**Windows**系统平台：
打开CMD窗口进入可执行程序所在的目录下，执行命令：
`holer-windows-xxx.exe -k 6688daebe02846t88s166733595eee5d`
**Linux**或者其他系统平台：
执行命令`nohup ./holer-xxx-xxx -k 6688daebe02846t88s166733595eee5d`

**访问映射后的公网地址**

浏览器里输入如下URL，就可在公网上也能访问到本地的Jupyter Notebook了。
`http://holer65004.wdom.net`或者`http://holer.org:65004`
















