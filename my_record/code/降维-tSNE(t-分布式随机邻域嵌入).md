PCA是一种线性算法。 它**不能解释特征之间的复杂多项式关系。** 另一方面，t-SNE是基于在邻域图上随机游走的概率分布，**可以在数据中找到其结构关系。**

![](https://upload-images.jianshu.io/upload_images/18339009-b3c08590661bd020.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-a43ddc06adefee6f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

该算法计算成对的条件概率，并试图最小化较高和较低维度的概率差的总值。 这涉及大量的计算。 

非线性降维算法t-SNE通过基于具有多个特征的数据点的相似性识别观察到的模式来找到数据中的规律。它不是一个聚类算法，而是一个降维算法。这是因为当它把高维数据映射到低维空间时，原数据中的特征值不复存在。所以不能仅基于t-SNE的输出进行任何推断。



