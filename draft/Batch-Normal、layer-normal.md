Batch Normalization是2015年一篇论文中提出的~~数据归一化方法~~，往往用在深度神经网络中~~激活层之前~~。其作用可以加快模型训练时的~~收敛速度~~，使得模型训练过程更加~~稳定，避免梯度爆炸或者梯度消失~~。并且起到一定的~~正则~~化作用，几乎代替了Dropout。

![quicker_5f02f8ae-dcfb-4ef7-b8cf-69f43426d72b.png](https://s2.loli.net/2022/03/23/XVCN5UjncI98BPO.png)


# batch norm 的作用

不同 batch 的数据，由于加入了 batch norm，中间层会更加稳定，有助于加速网络的学习。梯度爆炸和梯度消失现象也得到了一些缓解

测试过程中，不能通过单独样本的数据**计算均值和方差**，我们可以通过让训练过程中的每一个 mini- batch 的均值和方差数据，计算指数加权平均，从而得到完整样本的均值和方差的一个估计。在测试过程中，使用该值作为均值和方差，从而完成计算。

## BatchNorm 的缺点：

1.需要较大的 batch 以体现整体数据分布
2.训练阶段需要保存每个 batch 的均值和方差，以求出整体均值和方差在 infrence 阶段使用
3.不适用于可变长序列的训练，如 RNN

# Layer Normalization

Batch Normalization 是对这**批样本的同一维度特征做归一化**，Layer Normalization 是对这**单个样本的所有维度特征做归一化**。

Layer Normalization 是一个独立于 batch size 的算法，所以无论一个 batch 样本数多少都不会影响参与 LN 计算的数据量，从而解决 BN 的两个问题。LN 的做法是**根据样本的特征数做归一化**。

但在大批量的样本训练时，效果没 BN 好。实践证明，LN 用于 RNN 进行 Normalization 时，取得了比 BN 更好的效果。但用于 CNN 时，效果并不如 BN 明显。