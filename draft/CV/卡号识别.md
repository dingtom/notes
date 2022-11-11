```python
# 导入工具包
from imutils import contours # 也可以用自己写的方法
import numpy as np
import argparse
import cv2


# 设置参数
ap = argparse.ArgumentParser()
ap.add_argument("-i", "--image", default='./images/1.png',
                help="path to input image")
ap.add_argument("-t", "--template", default='./images/template.png',
                help="path to template OCR-A image")
ap.add_argument("-s", "--save", default=True,
                help="save every image")
args = vars(ap.parse_args())

def cv_show(name, image):
    cv2.imshow(name, image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# 轮廓排序
def sort_contours(origin_counters, method="left-to-right"):
    reverse = False
    order = 0
    if method == "right-to-left" or method == "bottom-to-top":
        reverse = True
    if method == "top-to-bottom" or method == "bottom-to-top":
        order = 1
    boxes = [cv2.boundingRect(counter) for counter in origin_counters]  # x,y,h,w
    ordered_counters, boxes = zip(*sorted(zip(origin_counters, boxes), key=lambda b: b[1][order], reverse=reverse))
    return counters, boxes

# 设置图像大小
def resize(origin_image, width=None, height=None, inter=cv2.INTER_AREA):
    (h, w) = origin_image.shape[:2]
    if width is None and height is None:
        return origin_image
    if width is None:
        r = height / float(h)
        dim = (int(w * r), height)
    else:
        r = width / float(w)
        dim = (width, int(h * r))
    resized_image = cv2.resize(origin_image, dim, interpolation=inter)
    return resized_image
```

# 读取模板图像,把所有数字模板放到一个数组

```python 
img = cv2.imread(args["template"])
cv2.imwrite('template.jpg', img) if args["save"] else cv_show('template', img)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/2gray.jpg)

```python 
# 灰度图
ref = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
cv2.imwrite('2gray.jpg', ref) if args["save"] else cv_show('gray', ref)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/2gray.jpg)

```python 
# 二值图像
ref = cv2.threshold(ref, 10, 255, cv2.THRESH_BINARY_INV)[1]
cv2.imwrite('3binary.jpg', ref) if args["save"] else cv_show('binary', ref)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/3binary.jpg)

```python 
# 画出轮廓
counters, hierarchy = cv2.findContours(ref.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
cv2.drawContours(img, counters, 1, (0, 0, 255), 3)
cv2.imwrite('4counters.jpg', img) if args["save"] else cv_show('counters', img)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/4counters.jpg)

```python 
# 把每个数字模板分割存下来
# counters = sort_contours(counters, method="left-to-right")[0]  # 排序，从左到右，从上到下
counters = contours.sort_contours(counters, method="left-to-right")[0]  # 排序，从左到右，从上到下
templateDigits = []
for (i, c) in enumerate(counters):
    # 计算外接矩形并且resize成合适大小
    (x, y, w, h) = cv2.boundingRect(c)
    roi = ref[y:y + h, x:x + w]
    templateDigits.append(cv2.resize(roi, (57, 88)))
```

# 读取卡图像预处理

```python

# 初始化几个结构化内核，构造了两个这样的内核 - 一个矩形和一个正方形。
# 我们将使用矩形的一个用于Top-hat形态运算符，将方形一个用于闭操作。
rectKernel = cv2.getStructuringElement(cv2.MORPH_RECT, (9, 3))
sqKernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
# 读取输入图像，预处理
image = cv2.imread(args["image"])
cv2.imwrite('5image.jpg', image) if args["save"] else cv_show('image', image)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/5image.jpg)

```python 
# 灰度处理
image = resize(image, width=300)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imwrite('6gray2.jpg', gray) if args["save"] else cv_show('gray2', gray)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/6gray2.jpg)

# 边缘检测，提取出数字轮廓区域

```python 
# 礼帽操作，突出更明亮的区域
# 礼帽：噪音提取   黑帽：空洞提取
tophat = cv2.morphologyEx(gray, cv2.MORPH_TOPHAT, rectKernel)
cv2.imwrite('7tophat.jpg', tophat) if args["save"] else cv_show('tophat', tophat)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/10thresh.jpg)

```python 
# 计算沿x方向的渐变在计算gradX   数组中每个元素的绝对值之后 ，
# 我们采取一些步骤将值缩放到范围[0-255]（因为图像当前是浮点数据类型）。
# 要做到这一点，我们计算 最小/最大归一化 最后一步是将gradX转换   为 uint8   ，其范围为[0-255]
gradX = cv2.Sobel(tophat, ddepth=cv2.CV_32F, dx=1, dy=0,  # ksize=-1相当于用3*3的
                  ksize=-1)
# **Sobel边缘检测算法**比较简单，实际应用中效率**比canny边缘检测效率要高**，但是边缘**不如Canny检测的准确**，但是很多实际应用的场合，sobel边缘却是首选，Sobel算子是**高斯平滑与微分操作的结合体，所以其抗噪声能力很强，用途较多**。尤其是效率要求较高，而对细纹理不太关心的时候。

gradX = np.absolute(gradX)
(minVal, maxVal) = (np.min(gradX), np.max(gradX))
gradX = (255 * ((gradX - minVal) / (maxVal - minVal)))
gradX = gradX.astype("uint8")
print(np.array(gradX).shape)
cv2.imwrite('8gradX.jpg', gradX) if args["save"] else cv_show('gradX', gradX)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/8gradX.jpg)

```python 
# 通过闭操作（先膨胀，再腐蚀）将数字连在一起
gradX = cv2.morphologyEx(gradX, cv2.MORPH_CLOSE, rectKernel)
cv2.imwrite('9close.jpg', gradX) if args["save"] else cv_show('close', gradX)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/9close.jpg)

```python 
# THRESH_OTSU会自动寻找合适的阈值，适合双峰，需把阈值参数设置为0
thresh = cv2.threshold(gradX, 0, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]
cv2.imwrite('10thresh.jpg', thresh) if args["save"] else cv_show('thresh', thresh)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/10thresh.jpg)

```python 
# 再来一个闭操作
thresh = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, sqKernel)  # 再来一个闭操作
cv2.imwrite('11close2.jpg', thresh) if args["save"] else cv_show('close2', thresh)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/11close2.jpg)

```python 
# 计算轮廓
threshCnts, hierarchy = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
cur_img = image.copy()
cv2.drawContours(cur_img, threshCnts, -1, (0, 0, 255), 3)
cv2.imwrite('12cur_img.jpg', cur_img) if args["save"] else cv_show('cur_img', cur_img)
```

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/12cur_img.jpg)

# 基于规则筛选出4位数字卡号区域

```python 
locs = []
# 遍历轮廓
for (i, c) in enumerate(threshCnts):
    # 计算矩形
    (x, y, w, h) = cv2.boundingRect(c)
    ar = w / float(h)
    # 选择合适的区域，因为信用卡使用了一个固定大小的字体与4组4位数字，我们可以基于高宽比
    if 2.5 < ar < 4.0:
        # 轮廓可以进一步调整最小/最大宽度
        if (40 < w < 55) and (10 < h < 20):
            # 符合的留下来
            locs.append((x, y, w, h))

# 将符合的轮廓从左到右排序
locs = sorted(locs, key=lambda x: x[0])
output = []

```

# 遍历每一个轮廓中的数字

```python
for (i, (gX, gY, gW, gH)) in enumerate(locs):
    groupOutput = []
    # 根据坐标在原图提取每个数组的图像
    group = gray[gY - 5:gY + gH + 5, gX - 5:gX + gW + 5]
    cv2.imwrite('13-{}gray.jpg'.format(i), group) if args["save"] else cv_show('gray', group)
    # 预处理，二值化
    group = cv2.threshold(group, 0, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]
    cv2.imwrite('14-{}threshold.jpg'.format(i), img) if args["save"] else cv_show('threshold', group)
    # 计算每一组的轮廓
    digitCnts, hierarchy = cv2.findContours(group.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    digitCnts = contours.sort_contours(digitCnts, method="left-to-right")[0]
    # 对比组中的每一个数具体是什么值
    for j, c in enumerate(digitCnts):
        # 找到当前数的轮廓，resize成合适的的大小
        (x, y, w, h) = cv2.boundingRect(c)
        roi = group[y:y + h, x:x + w]
        roi = cv2.resize(roi, (57, 88))
        cv2.imwrite('15-{}-{}roi.jpg'.format(i, j), roi) if args["save"] else cv_show('roi', roi)
        
        scores = []
        for digit in templateDigits:
            # 模板匹配
            result = cv2.matchTemplate(roi, digit, cv2.TM_CCOEFF)
            (_, score, _, _) = cv2.minMaxLoc(result)
            scores.append(score)

        # 得到最合适的数字
        groupOutput.append(str(np.argmax(scores)))
    # 画出来
    cv2.rectangle(image, (gX - 5, gY - 5),
                  (gX + gW + 5, gY + gH + 5), (0, 0, 255), 1)
    cv2.putText(image, "".join(groupOutput), (gX, gY - 15),
                cv2.FONT_HERSHEY_SIMPLEX, 0.65, (0, 0, 255), 2)

    # 得到结果
    output.extend(groupOutput)

# 打印结果
print("Credit Card #: {}".format("".join(output)))
cv2.imwrite('result.jpg', image) if args["save"] else cv_show('result', image)

cv2.waitKey(0)
```



![13-0gray](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/13-0gray.jpg)

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/14-0threshold.jpg)

![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/15-0-0roi.jpg)



![](https://gitee.com/dingtom1995/template-matching-ocr/raw/master/result.jpg)