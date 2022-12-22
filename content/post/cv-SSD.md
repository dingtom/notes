---
    # 文章标题
    title: "cv-SSD"
    # 分类
    categories: 
        - cv
    # 发表日期
    date: 2022-12-01T19:59:47+08:00
    
--- 

# 原理

让图片经过卷积神经网络（VGG）提取特征，生成feature map

抽取其中六层的feature map，然后分别在这些feature map层上面的每一个点构造4个不同尺度大小的default box

然后分别进行检测和分类（各层的个数不同，但每个点都有）将生成的所有default box都集合起来，全部丢到NMS中，输出筛选后的default box。

![](https://gitee.com/dingtom1995/picture/raw/master/2022-11-24/2022-11-24_18-16-33-635.png)




