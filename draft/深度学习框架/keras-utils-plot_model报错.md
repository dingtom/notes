OSError: `pydot` failed to call GraphViz.Please install GraphViz (https://www.graphviz.org/) and ensure that its executables are in the $PATH.
或
Failed to import pydot. You must install pydot and graphviz

# win10
####1.安装 GraphViz
[下载msi文件安装](https://graphviz.gitlab.io/_pages/Download/Download_windows.html)

与python关联
```pip install graphviz```
####2.添加环境变量
######用户变量Path添加：
```C:\programfile\graphviz\bin```(这个为你安装的graphviz路径下的bin路径)
######系统变量Path添加：
```C:\programfile\graphviz\bin\dot.exe```
######命令行输入
```dot -version```查看路径是否为安装目录
![](https://upload-images.jianshu.io/upload_images/18339009-6c6e90b865877661.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
######3.安装pydot
```pip install pydot```
```pip install pydot_ng```

修改py文件
找到E:\anaconda3\envs\tensorflow\Lib\site-packages\pydot.py
修改```return '.bat' if is_anacoda() else '.exe'```
为```       return '.bat' if not is_anacoda() else '.exe'```
