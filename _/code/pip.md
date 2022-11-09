- [ 从本地安装](#head1)
- [ 从依赖包列表中安装](#head2)
- [ pip部分国内镜像](#head3)
# <span id="head1"> 从本地安装</span>
前提你得保证你已经下载 pkg 包到 /local/wheels 目录下
```pip install --no-index --find-links=/local/wheels pkg```
# 导出依赖包列表
```pip freeze >requirements.txt```
# <span id="head2"> 从依赖包列表中安装</span>
```pip install -r requirements.txt```
# 升级
``` pip install --upgrade pkg```
# <span id="head3"> pip部分国内镜像</span>

```
http://mirrors.aliyun.com/pypi/simple

https://pypi.tuna.tsinghua.end.cn/simple

https://pypi.mirrors.ustc.end.cn/simple

http://pypi.douban.com/simple
```
windows操作系统下，按快捷键win+R并打开%HOMEPATH%，在该用户目录下创建pip文件夹，再在pip文件夹中创建一个空pip.txt文件，粘贴下面代码,重命名修改为pip.ini文件。
Linux操作系统下，用户目录下创建pip.conf文件。
```
[global]
timeout = 6000
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
trusted-host = pypi.tuna.tsinghua.edu.cn
```
