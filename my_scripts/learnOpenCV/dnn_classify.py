import numpy as np
import time
import cv2

image = cv2.imread('./smallcat.jpeg')


# 将图像转成张量
# blobFromImage(image,
#               scalefactor=1.0, // 缩放
#               size=Size(),  // 图像size
#               mean_Scalar(),  // 消除光照变化
#               swapRB=false,   // RB是否交换 opencv BGR
#               crop=false.…)  // 图像裁剪
# (1, 3, 224, 224)
blob = cv2.dnn.blobFromImage(image, 1, (224, 224), (104, 117, 123))

# 导入模型API参数
# readNetFromTensorFlow(model,config)
# readNetFromCaffe/Darknet(config,model)
# readNet(model,[config,[framework]])
config = './model/bvlc_googlenet.prototxt'
model = './model/bvlc_googlenet.caffemodel'
net = cv2.dnn.readNetFromCaffe(config, model)

# 将张量送入网络并执行
net.setInput(blob)
start = time.time()
preds = net.forward()  # (1， 100)
end = time.time()
print("[INFO] classification took {:.5} seconds".format(end - start))

# 读取类目
classes = []
path = './model/synset_words.txt'
with open(path, 'rt') as f:
    classes = [x[x.find(" ") + 1:] for x in f]

# probabilitiy first) and grab the top-5 predictions
idxs = np.argsort(preds[0])[::-1][:5]

# loop over the top-5 predictions and display them
for (i, idx) in enumerate(idxs):
    # draw the top prediction on the input image
    if i == 0:
        text = "Label: {}, {:.2f}%".format(classes[idx],
                                           preds[0][idx] * 100)
        cv2.putText(image, text, (5, 25), cv2.FONT_HERSHEY_SIMPLEX,
                    0.7, (0, 0, 255), 2)
    print("[INFO] {}. label: {}, probability: {:.5}".format(i + 1,
                                                            classes[idx], preds[0][idx]))
cv2.imshow("Image", image)
cv2.waitKey(0)
