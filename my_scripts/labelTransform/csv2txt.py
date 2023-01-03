import pandas as pd
import os
import re
from os import listdir, getcwd
from os.path import join

"""
label
中心横坐标与图像宽度比值
中心纵坐标与图像高度比值
bbox宽度与图像宽度比值
bbox高度与图像高度比值
"""


def convert(size, box):
    # size  xmin,xmax,ymin,ymax
    dw = 1. / size[0]
    dh = 1. / size[1]
    x = (box[0] + box[1]) / 2.0
    y = (box[2] + box[3]) / 2.0
    w = box[1] - box[0]
    h = box[3] - box[2]
    x = x * dw
    w = w * dw
    y = y * dh
    h = h * dh
    return (x, y, w, h)


images_dir = 'notices/images'
df = pd.read_csv('notices/annotations.csv', engine="c")
for i in range(df.shape[0]):
    with open(os.path.join(images_dir, df.iloc[i, 0].replace('png', 'txt')), 'a+') as f1:
        loc = convert([df.iloc[i, 1], df.iloc[i, 2]],
                                    [df.iloc[i, 3], df.iloc[i, 5], df.iloc[i, 6], df.iloc[i, 7]])
        f1.write(str(df.iloc[i, 7]) + " " + " ".join([str(a) for a in loc]) + '\n')
