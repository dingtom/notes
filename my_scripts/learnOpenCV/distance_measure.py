#!/usr/bin/env python
# -*- encoding: utf-8 -*-
import cv2

# 焦距/物距=像宽/物宽
win_width = 1920
win_height = 1080
mid_width = int(win_width / 2)
mid_height = int(win_height / 2)

focal_distance = 2810.0
real_width = 11.69
image_width = 1

capture = cv2.VideoCapture(0)
capture.set(3, win_width)
capture.set(4, win_height)

while True:
    ret, frame = capture.read()
    frame = cv2.flip(frame, 1)
    if not ret:
        break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (5, 5), 0)
    ret, binary = cv2.threshold(gray, 127, 255, 0)
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    binary = cv2.dilate(binary, kernel, iterations=2)  # 形态学膨胀
    contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    # cv2.drawContours(frame, contours, -1, (0, 255, 0), 2)
    for c in contours:
        if cv2.contourArea(c) < 2000:  # 对于矩形区域，只显示大于给定阈值的轮廓，所以一些微小的变化不会显示。对于光照不变和噪声低的摄像头可不设定轮廓最小尺寸的阈值
            continue

        x, y, w, h = cv2.boundingRect(c)  # 该函数计算矩形的边界框

        if x > mid_width or y > mid_height:
            continue
        if (x + w) < mid_width or (y + h) < mid_height:
            continue
        if h > w:
            continue
        if x == 0 or y == 0:
            continue
        if x == win_width or y == win_height:
            continue

        image_width = w
        cv2.rectangle(frame, (x + 1, y + 1), (x + image_width - 1, y + h - 1), (0, 255, 0), 2)

    dis_inch = (real_width * focal_distance) / (image_width - 2)
    dis_cm = dis_inch * 2.54
    # os.system("cls")
    # print("Distance : ", dis_cm, "cm")
    frame = cv2.putText(frame, "%.2fcm" % (dis_cm), (5, 25), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)
    frame = cv2.putText(frame, "+", (mid_width, mid_height), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (0, 255, 0), 2)

    cv2.namedWindow('res', 0)
    cv2.namedWindow('gray', 0)
    cv2.resizeWindow('res', win_width, win_height)
    cv2.resizeWindow('gray', win_width, win_height)
    cv2.imshow('res', frame)
    cv2.imshow('gray', binary)
    key = cv2.waitKey(100) & 0xFF
    if key == ord('q'):
        break

cv2.destroyAllWindows()