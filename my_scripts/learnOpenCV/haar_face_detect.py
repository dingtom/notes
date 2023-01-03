import cv2
import numpy as np

"""
haar 人脸识别
"""

# 第一步，创建Haar级联器
facer = cv2.CascadeClassifier('./haarcascades/haarcascade_frontalface_default.xml')
eye = cv2.CascadeClassifier('./haarcascades/haarcascade_eye.xml')
mouth = cv2.CascadeClassifier('./haarcascades/haarcascade_mcs_mouth.xml')
nose = cv2.CascadeClassifier('./haarcascades/haarcascade_mcs_nose.xml')

# 第二步，导入人脸识别的图片并将其灰度化
img = cv2.imread('./p3.png')

gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# 第三步，进行人脸识别
# [[x,y,w,h]]

# 检测出的人脸上再检测眼睛
# detectMultiScale(img,
# double scaleFactor 1.1, // 扫描时图像缩小、放大
# int minNeighbors 3,  // 目标至少被检测到minNeighbors次才会被认为是目标
faces = facer.detectMultiScale(gray, 1.1, 3)
i = 0
j = 0
for (x, y, w, h) in faces:
    cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 255), 2)
    roi_img = img[y:y + h, x:x + w]
    eyes = eye.detectMultiScale(roi_img, 1.1, 3)
    for (x1, y1, w1, h1) in eyes:
        cv2.rectangle(roi_img, (x1, y1), (x1 + w1, y1 + h1), (0, 255, 0), 2)
        roi_eye = roi_img[y1:y1 + h1, x1:x1 + w1]
        eyename = 'eye' + str(j)
        j = j + 1
        cv2.imshow(eyename, roi_eye)

    i = i + 1
    winname = 'face' + str(i)
    cv2.imshow(winname, roi_img)

# mouths = mouth.detectMultiScale(gray, 1.1, 3)
# for (x,y,w,h) in mouths:
#     cv2.rectangle(img, (x, y), (x+w, y+h), (255, 0, 0), 2)

# noses = nose.detectMultiScale(gray, 1.1, 3)
# for (x,y,w,h) in noses:
#     cv2.rectangle(img, (x, y), (x+w, y+h), (255, 255, 0), 2)

cv2.imshow('img', img)

cv2.waitKey()
