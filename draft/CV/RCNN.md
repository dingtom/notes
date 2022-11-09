# RCNN

Rich feature hierarchies for accurate object detection and semantic segmentation

![quicker_c1ad4403-0644-4f2c-9c03-d992d8562203.png](https://s2.loli.net/2022/04/07/o8liUxaNdj9FIzk.png)

![quicker_067ff2c9-ab0f-4314-a9db-a53dd0a83a94.png](https://s2.loli.net/2022/04/07/oKuvLPgi5ADb7pJ.png)



 ![quicker_dfb07b53-43be-4933-88df-561cbbb5f1e3.png](https://s2.loli.net/2022/04/07/gpUdbo72TVmHlYX.png)

![quicker_479d50a7-4a3b-4dff-be22-188f25069f32.png](https://s2.loli.net/2022/04/07/PjMAYOV6oeSruFn.png)

![quicker_e761c557-a693-4863-b30c-5d34c82120b0.png](https://s2.loli.net/2022/04/07/XxkdHQlJsiMNEVu.png)



![quicker_68bac4f3-5d77-4889-8178-0b714fb3d86e.png](https://s2.loli.net/2022/04/07/8FnxDPWLibSOjmz.png)





R-CNN存在的问题：
1.测试速度慢：
测试一张图片约53s(CPU)。用Selective Search:算法提取候选框用时约2秒，一张图像内候选框之间存在大量重叠，提取特征操作冗余。
2.训练速度慢：
过程及其繁琐
3.训练所需空间大：
对于SVM和bbox回归训练，需要从每个图像中的每个目标候选框提取特征，并写入磁盘。对于非常深的网络，如VGG16,从VOC07训练集上的5k图像上提取的特征需要数百G的存储空间。







# Fast R-CNN

![quicker_0e95a67e-f355-4f89-abf6-f35cf0c5e46f.png](https://s2.loli.net/2022/04/10/xZXS4OR5HCVqyG6.png)



![quicker_84701ddc-94bf-4b1f-b1a7-a4ee5732b02a.png](https://s2.loli.net/2022/04/07/ycaDreBUHEAw7IZ.png)

![quicker_01a28c84-f147-4426-b4f6-301d9f54442d.png](https://s2.loli.net/2022/04/10/bBvxLrz2uSGCihE.png)

![quicker_e7a8a33f-02aa-4307-91de-eac378ad2546.png](https://s2.loli.net/2022/04/10/a74RrdbF8CJkQXL.png)

![quicker_83f5fa00-6bfc-400e-9a75-28ec065fc61f.png](https://s2.loli.net/2022/04/10/exPgBzsr2LyaMtG.png)

![quicker_503048ec-844c-4406-929f-3d87d0501bb0.png](https://s2.loli.net/2022/04/10/aZQDlFLXiI8Adrb.png)

![quicker_adc966b4-9102-4f42-9ecb-8459c4f0c9e4.png](https://s2.loli.net/2022/04/10/WgIEdSZbYiTQuPN.png)

![quicker_62d7523f-a815-4201-a959-0fdddcfe2093.png](https://s2.loli.net/2022/04/10/3tIHqgNdApV1KQS.png)

![quicker_d1456a9d-dfd7-4ff7-86f8-62d87e86afb3.png](https://s2.loli.net/2022/04/10/dG4kFvquaLB6DVP.png)

![quicker_f9fd5c22-36e0-482a-a624-c77c072320d3.png](https://s2.loli.net/2022/04/10/tsAWyxjfJ6mEL8n.png)

![quicker_81ef3ccf-dd70-49e3-bfcf-d61b0946eba9.png](https://s2.loli.net/2022/04/10/VETgxuFNq3nvkJt.png)

艾佛森括号：u>=1 为1 其他值为0，负样本没有边界框回归损失



# Faster  R-CNN



![quicker_3a5b7a49-f525-4aa5-9c33-b8a13cf74307.png](https://s2.loli.net/2022/04/10/cD8dnsNEzvWkmlq.png)

![quicker_77ebcc9a-e64b-479e-896b-9675df2ed9f7.png](https://s2.loli.net/2022/04/10/dhim1UjwHq5cvBF.png)

![1649600095104](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1649600095104.png)



![quicker_81a430e4-8a4d-40cd-96ad-57d21a7c01c0.png](https://s2.loli.net/2022/04/10/NViFXvhO6nsmQtb.png)



![quicker_bd246811-cdc4-4f04-97c3-ffe92d2d1c51.png](https://s2.loli.net/2022/04/10/QdjFwnUJPhWrHxX.png)



![quicker_ed15dba1-2efc-4dec-8f91-367deb040ccb.png](https://s2.loli.net/2022/04/10/SpZvmlFORdAIDPH.png)



![quicker_9d146f5a-62d3-4e0b-9e60-44bb1be0d3f2.png](https://s2.loli.net/2022/04/10/eAIoXxvhcD1kNsO.png)



![quicker_a8e4a35f-4aa0-4fc9-8008-1a0f465390c9.png](https://s2.loli.net/2022/04/10/GwNrKbLz8cCRZnm.png)





![quicker_75e21f36-65b5-4909-981c-8878690cff57.png](https://s2.loli.net/2022/04/17/uDJ6ZaRGhtMFerY.png)



官方用的二交叉熵

![1650126072214](C:\Users\WENCHAO\AppData\Roaming\Typora\typora-user-images\1650126072214.png)





