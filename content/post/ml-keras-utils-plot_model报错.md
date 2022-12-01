---
    # 文章标题
    title: "ml-keras-utils-plot_model报错"
    # 分类
    categories: 
        - ml
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
    
    # 标签
    #tags:
    # 文章内容摘要
    #description: "{{ .Name }}" 
    # 最后修改日期
    #lastmod: {{ .Date }}
    # 文章内容关键字
    #keywords: "{{replace .Name "-" ","}}"
    # 原文作者
    #author:
    # 原文链接
    #link:
    # 图片链接，用在open graph和twitter卡片上
    #imgs:
    # 在首页展开内容
    #expand: true
    # 外部链接地址，访问时直接跳转
    #extlink:
    # 在当前页面关闭评论功能
    #comment:
    # enable: false
    # 关闭当前页面目录功能
    # 注意：正常情况下文章中有H2-H4标题会自动生成目录，无需额外配置
    #toc: false
    # 绝对访问路径
    #url: "{{ lower .Name }}.html"
    # 开启文章置顶，数字越小越靠前
    #weight: 1
    #开启数学公式渲染，可选值： mathjax, katex
    #math: mathjax
    # 开启各种图渲染，如流程图、时序图、类图等
    #mermaid: true
--- 

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
