# 安装 jupyter

```shell
conda install ipython
conda install jupyter
```
# 换主题

```shell
pip install jupyterthemes

查看可用的 Jupyter 主题
jt -l

更换 Jupyter 主题
jt -t onedork -f fira -fs 13 -cellw 90% -ofs 11 -dfs 11 -T -T -m 10

-t 主题 -f(字体)  -fs(字体大小) -cellw(占屏比或宽度)  -ofs(输出段的字号)  -T(显示工具栏)  -T(显示自己主机名)

 恢复 Jupyter 默认风格
jt -r
```

# 画图
```shell
from jupyterthemes import jtplot
jtplot.style() 

```

![](https://upload-images.jianshu.io/upload_images/18339009-8515131bfec37dc8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 安装插件

```shell
pip install jupyter_contrib_nbextensions && jupyter contrib nbextension install

pip install jupyter_contrib_nbextensions && jupyter contrib nbextension install --user


###### Table of Contents 
通过 TOC 链接你可以定位到页面中的任何位置。
###### Autopep8
获得简洁代码
###### variable inspector
跟踪你的工作空间
###### ExecuteTime
显示单元格的运行时间和耗时
###### Collapsible headings
收起/放下Notebook中的某些内容
###### Code folding
折叠代码。
###### Notify
在任务处理完后及时向你发送通知。

```



#　无法导包

在jupyter中添加tensorflow核

```shell

conda install  -n tomding ipykernel

python -m ipykernel install --user --name tomding --display-name tomding

conda install -n pytorch ipykernel

python -m ipykernel install --user --name pytorch --display-name pytorch

```



# 远程

# 内网


``` shell
1.生成配置文件
jupyter notebook --generate-config

2.创建远程登录密码
jupyter notebook password

   注意，此方法生成的密码文件为/root/.jupyter/jupyter_notebook_config.json （如果是以root用户登录，其他用户路径同上说明），此为json文件，而且json文件里的密码的优先级要高于配置文件（jupyter_notebook_config.py）里的密码设置。

3.修改配置文件
vim ~/.jupyter/jupyter_notebook_config.py 

c.NotebookApp.ip='*'
c.NotebookApp.password = '密码'
c.NotebookApp.open_browser = False
c.NotebookApp.port =8888 #可自行指定一个端口, 访问时使用该端口
c.NotebookApp.allow_remote_access = True 


4.启动
jupyter notebook

5.远程登录
输入http://服务器的 IP 地址:8888，输入密码登录。

```