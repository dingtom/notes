---
    # 文章标题
    title: "ml-Seq2Seq-模型及-Attention-机制"
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

# 什么是 Seq2Seq ？
Seq2Seq 任务指的是输入和输出都是序列的任务。例如说英语翻译成中文。

Seq2Seq任务最常见的是使用Encoder+Decoder的模式。**在 Encoder 中，将可变长度的序列转变为固定长度的向量表达，Decoder 将这个固定长度的向量转换为可变长度的目标的信号序列。***
![](https://upload-images.jianshu.io/upload_images/18339009-473f0ec884e42b28.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-ff73b994698fd694.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



Encoder-Decoder 有很多弊端
- 训练速度慢，计算无法并行化（根本原因是encoder和decoder阶段中的RNN/LSTM/GRU的结构，由于decoder实际上是一个语言模型，因此其时间复杂度为O(n)）；
- Encoder 将输入编码为固定大小状态向量（hidden state）的过程实际上是一个“信息有损压缩”的过程。**如果信息量越大，那么这个转化向量的过程对信息造成的损失就越大。**
- 随着 sequence length的增加，**意味着时间维度上的序列很长，RNN 模型也会出现梯度弥散**。
- **基础的模型连接 Encoder 和 Decoder 模块的组件仅仅是一个固定大小的状态向量，这使得Decoder无法直接去关注到输入信息的更多细节**。

# 基本的Attention原理。

单纯的Encoder-Decoder 框架并不能有效的聚焦到输入目标上，这使得像 seq2seq 的模型在独自使用时并不能发挥最大功效。比如说在上图中，编码器将输入编码成上下文变量 C，在解码时每一个输出 Y 都会不加区分的使用这个 C 进行解码。而**注意力模型要做的事就是根据序列的每个时间步将编码器编码为不同 C，在解码时，结合每个不同的 C 进行解码输出，这样得到的结果会更加准确**，如下所示：
![](https://upload-images.jianshu.io/upload_images/18339009-5932ca5bcacc9dd4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

