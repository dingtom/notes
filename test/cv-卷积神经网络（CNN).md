---
    # 文章标题
    title: cv-卷积神经网络（CNN).md
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




![](https://upload-images.jianshu.io/upload_images/18339009-4c00b0451280e2f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-3adce96cd54eb3f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 卷积层
**卷积提取底层特征减少神经网络中参数个数**

![](https://upload-images.jianshu.io/upload_images/18339009-9ff2bf3400efde22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![左侧小矩阵的尺寸为过滤器的尺寸，而右侧单位矩阵的深度为过滤器的深度
](https://upload-images.jianshu.io/upload_images/18339009-83bf8425d394ccdd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-bdf823c5a2c28959.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 为了避免尺寸的变化可以在当前层的矩阵的边界加入全０填充（zero-padding）．否则中间的像素会多次进入卷积野而边上的进入次数少
- 还可以通过设置过滤器移动步长调整结果矩阵的大小

# 池化层
**防止过拟合、下采样、降维、去除冗余信息、对特征进行压缩、简化网络复杂度、减小计算量、减小内存消耗**
**实现非线性，扩大感知野**
**实现不变性，（平移不变性、旋转不变性和尺度不变性）**

![池化函数使用某一位置的相邻输出的总体统计特征来代替网络在该位置的输出。](https://upload-images.jianshu.io/upload_images/18339009-8b04d457c6018029.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




**加速卷积运算**
im2col
![](https://upload-images.jianshu.io/upload_images/18339009-6e6de559a8775ac5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-2eec3ec53bdba4a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-5924a4c0dbe607ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
FFT（不常用）
![](https://upload-images.jianshu.io/upload_images/18339009-60402719caab3bfa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-0585eb6db952abd3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-ff535562ada70c9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




## 卷积层输出矩阵大小
padding = “SAME”$n_{output}=[  n_{input}  ]$

padding = “VALID”$n_{\text {output}}=\left[\frac{n_{\text {input}}- \text{kernel_size} +1}{s}\right]$

## 池化层的输出大小公式也与卷积层一样，由于没有进行填充，所以p=0，可以简化为  $ \frac{n-  \text{kernel_size} }{s} +1 $




## 一个卷积核对应一个feature map
![](https://upload-images.jianshu.io/upload_images/18339009-c7cd01c735e57672.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-e6be19d861ab23cf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)







