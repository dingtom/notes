---
    # 文章标题
    title: cv-SSD.md
    # 分类
    categories: cv
    # 标签
    #tags:
    # 文章内容摘要
    #description: "{{ .Name }}"
    
    # 发表日期
    #date: {{ .Date }}
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




# 原理

让图片经过卷积神经网络（VGG）提取特征，生成feature map

抽取其中六层的feature map，然后分别在这些feature map层上面的每一个点构造4个不同尺度大小的default box

然后分别进行检测和分类（各层的个数不同，但每个点都有）将生成的所有default box都集合起来，全部丢到NMS中，输出筛选后的default box。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-24/2022-11-24_18-16-33-635.png)




