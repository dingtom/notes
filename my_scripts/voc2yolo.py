import xml.etree.ElementTree as ET
import os
import random


def convert(size, box):
    dw = 1./size[0]
    dh = 1./size[1]
    x = (box[0] + box[1])/2.0
    y = (box[2] + box[3])/2.0
    w = box[1] - box[0]
    h = box[3] - box[2]
    x = x*dw
    w = w*dw
    y = y*dh
    h = h*dh
    return (x,y,w,h)

def convert_annotation(txt_path,xml_path):
    with open(xml_path, 'r') as xml_file:
        tree=ET.parse(xml_file)
        root = tree.getroot()
        size = root.find('size')
        w = int(size.find('width').text)
        h = int(size.find('height').text)

        for obj in root.iter('object'):
            difficult = obj.find('difficult').text
            cls = obj.find('name').text
            if cls not in classes or int(difficult) == 1:
                continue
            cls_id = classes.index(cls)
            xmlbox = obj.find('bndbox')
            b = (float(xmlbox.find('xmin').text), float(xmlbox.find('xmax').text), float(xmlbox.find('ymin').text), float(xmlbox.find('ymax').text))
            bb = convert((w,h), b)
            with open(txt_path, 'a') as txt_file:
                txt_file.write(str(cls_id) + " " + " ".join([str(a) for a in bb]) + '\n')


classes = ['aeroplane','bicycle','bird','boat','bottle','bus','car','cat','chair','cow','diningtable','dog','horse','motorbike','person','pottedplant','sheep','sofa','train','tvmonitor']
data_dir = 'D:\work\data\VOC'
val_ratio = 0.3
test_ratio = 0.3
image_type = '.jpg'
xml_dir = os.path.join(data_dir, 'Annotations')
txt_dir = os.path.join(data_dir, 'labels')

train_file = open(os.path.join(data_dir, 'train.txt'), 'w')
val_file = open(os.path.join(data_dir, 'val.txt'), 'w')
test_file = open(os.path.join(data_dir, 'test.txt'), 'w')

files = os.listdir(xml_dir)
for f in files:
    f_name = os.path.splitext(f)[0]
    convert_annotation(os.path.join(txt_dir, f.replace('xml', 'txt')), os.path.join(xml_dir, f))

    path = os.path.join(data_dir, 'JPEGImages', f_name+image_type)+'\n'
    r = random.random()
    if r < val_ratio:
        val_file.write(path)
    elif val_ratio < r < val_ratio + test_ratio:
        test_file.write(path)
    else:
        train_file.write(path)
train_file.close()
val_file.close()
