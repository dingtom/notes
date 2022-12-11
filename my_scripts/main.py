import argparse
import xml.etree.ElementTree as ET
from utilis import *
import argparse

label_list = ['head']


def get_image_txt(opt):
    # 　阶段一：对于数据集进行清洗梳理　
    # 第一步：根据images_label_split中的图像删除多余的xml
    print("--------------------------------1")
    compare_image_label_remove_xml(opt.data_dir)
    # # # 第二步：根据images_label_split中的图像删除多余的image
    print("--------------------------------2")
    compare_image_label_remove_image(opt.data_dir)
    # # 第三步：将各个文件夹中的xml不满足条件的文件删除
    print("--------------------------------3")
    remove_not_satisfied_xml(opt.data_dir)
    # # 第四步：查找xml是否为空，空的话删除xml,也删除对应的image
    print("--------------------------------4")
    remove_image_null_xml(opt.data_dir, label_list)
    # # 第五步：对照image和xml中数据，显示图片看画得框是否正确
    show_label(opt.data_dir, label_list)
    # 阶段二：将数据按照一定比例分成训练、验证集、测试
    # 将train和test随机分开，将image和xml分别保存到train和test所在的文件夹中
    # 根据前面可以得到xml和image,每个场景下选择10%的数据,作为验证集,5%的数据,作为测试集, 生成train和test两个文件夹
    print("--------------------------------5")
    yolov3_get_train_test_file(opt.data_dir, 0.2)
    # 阶段三：将train和test的xml，转换成txt
    # 第一步：将train和test中的xml文件生成txt文件，都放到image_txt文件夹中
    print("--------------------------------6")
    yolov3_get_txt(opt.data_dir, label_list)
    # #  第二步：将所有的image文件一起移动到image_txt中
    print("--------------------------------7")
    yolov3_move_image(opt.data_dir)
    # # 第三步：将train/Annotations和test/Annotations的xml自动生成train.txt和test.txt文件，并保存到train_test_txt中
    print("--------------------------------8")
    yolov3_get_train_test_txt(opt.data_dir)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--data_dir', type=str, default=r'D:\work\data\head', help='data dir')
    opt = parser.parse_args()
    get_image_txt(opt)
