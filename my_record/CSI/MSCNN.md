![](https://upload-images.jianshu.io/upload_images/18339009-b42f272e36330836.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
# encoding=utf-8
import os
import numpy as np
from keras.utils import np_utils
import pandas as pd
from sklearn import preprocessing
import keras


def get_datasets_path(base_path):
    datasets_used = [
        'Adiac']  # , 'Beef', 'CBF', 'ChlorineConcentration', 'CinC_ECG_torso', 'Coffee', 'Cricket_X', 'Cricket_Y', 'Cricket_Z', 'DiatomSizeReduction', 'ECGFiveDays', 'FaceAll', 'FaceFour', 'FacesUCR', '50words', 'FISH', 'Gun_Point', 'Haptics', 'InlineSkate', 'ItalyPowerDemand', 'Lighting2', 'Lighting7', 'MALLAT', 'MedicalImages', 'MoteStrain', 'NonInvasiveFatalECG_Thorax1', 'NonInvasiveFatalECG_Thorax2', 'OliveOil', 'OSULeaf', 'SonyAIBORobotSurface', 'SonyAIBORobotSurfaceII', 'StarLightCurves', 'SwedishLeaf', 'Symbols', 'synthetic_control', 'Trace', 'TwoLeadECG', 'Two_Patterns', 'UWaveGestureLibrary_X', 'UWaveGestureLibrary_Y', 'UWaveGestureLibrary_Z', 'wafer', 'WordSynonyms', 'yoga']
    data_list = os.listdir(base_path)
    return ["%s\%s\%s_" % (base_path, data, data) for data in datasets_used if data in data_list]


# 使用滑动窗口在时间序列上截取数据，并共享同一个类别标签，同时增加了测试集和训练集的规模。
def data_crop(data, label, wl):
    data_dim = data.shape[1]
    crop_data, crop_label = data[:, :wl], label
    
    for i in range(1, data_dim-wl+1):  # print(data_dim-wl+1)  # data_dim-wl+1=crop_times=10  裁剪几次 wl=167窗口长度
        _data = data[:, i:i+wl]
        # print('_data', _data.shape)  # (389, 167)
        crop_data = np.concatenate((crop_data, _data), axis=0)
        crop_label = np.concatenate((crop_label, label), axis=0)
    # print('data_crop', crop_data.shape, crop_label.shape)  # data_crop (3890, 167)
    return crop_data, crop_label


# 加载原始、加窗处理后的数据  crop_times裁剪几次
def load_data_label(path, crop_times=0):
    data_label = pd.read_csv(path, sep='\t').values
    # the first column is label, and the rest are datas
    data, label = data_label[:, 1:], data_label[:, 0]
    # print('origin:', data.shape, label.shape)  # origin: (389, 176)   data_label  (389, 177)
    if crop_times > 0:
        wl = data_label.shape[1]-crop_times # 窗口的大小  print(wl)  #177-10=167
        data, label = data_crop(data, label, wl)  # (3890, 167)
    return data, label


# 降采样  使用一组降采样因子 k1, k2, k3，每隔 ki-1 个数据取一个。
def down_sampling(data, rates):
    ds_data_dim = []
    ds_data = []
    # down sampling by rate k
    for k in rates:
        if k > data.shape[1] / 3:
            break
        _data = data[:, ::k]  # temp after down sampling
        ds_data.append(_data)
#         print('ds', k, _data.shape)
#         ds 2 (3890, 84)
#         ds 3 (3890, 56)
#         ds 4 (3890, 42)
#         ds 5 (3890, 34)
        ds_data_dim.append(_data.shape[1])  # remark the length info
    return ds_data, ds_data_dim

# 滑动平均  使用一组滑动窗口l1, l2, l3，每li个数据取平均。
def moving_average(data, moving_wl):
    num, data_dim = data.shape[0], data.shape[1]
    ma_data = []
    ma_data_dim = []
    for wl in moving_wl:
        if wl > data.shape[1] / 3:
            break
        _data = np.zeros((num, data_dim-wl+1))
        for i in range(data_dim - wl + 1):
            _data[:, i] = np.mean(data[:, i: i + wl], axis=1)
        ma_data.append(_data)
#         print('mv', wl, _data.shape)
#         mv 5 (3890, 163)
#         mv 8 (3890, 160)
#         mv 11 (3890, 157)
        ma_data_dim.append(_data.shape[1])
    return ma_data, ma_data_dim


def get_mcnn_input(origin_data, label):
    # Multi-Scale 表示对原始数据降采样后的序列
    ms_branch, ms_dim = down_sampling(origin_data, rates=[2, 3, 4, 5])
    # Multi-Frequency 表示对原始数据滑动平均后的序列
    mf_branch, mf_dim = moving_average(origin_data, moving_wl=[5, 8, 11])
#     print(np.unique(label))
#     [ 1.  2.  3.  4.  5.  6.  7.  8.  9. 10. 11. 12. 13. 14. 15. 16. 17. 18.
#  19. 20. 21. 22. 23. 24. 25. 26. 27. 28. 29. 30. 31. 32. 33. 34. 35. 36.
#  37.]
    label = np_utils.to_categorical(label)  # one hot
    datas = [origin_data, *ms_branch, *mf_branch]
    datas = [data.reshape(data.shape + (1,)) for data in datas]
#     print([data.shape for data in datas], label.shape)
#     [(3890, 167, 1), (3890, 84, 1), (3890, 56, 1), (3890, 42, 1), (3890, 34, 1), 
#      (3890, 163, 1), (3890, 160, 1), (3890, 157, 1)] 
#     (3890, 38)
    data_dim = [origin_data.shape[1], *ms_dim, *mf_dim]
#     print([data for data in data_dim])
#     [167, 84, 56, 42, 34, 163, 160, 157]
    return datas, label, data_dim


def get_mcnn_model(data_dim, class_num, conv_size,pooling_factor):
    # conv_size = 3
    # pooling_factor = 10
    input_layers = [keras.layers.Input(shape=(bra_len, 1)) for bra_len in data_dim]
    # local convolution
    local_layers = []
    for i in range(len(input_layers)):
        _local = keras.layers.Conv1D(padding='same', kernel_size=conv_size, filters=256, activation='relu')(input_layers[i])
        pooling_size = (_local.shape[1] - conv_size + 1) // pooling_factor
        pooling_size = 2
        _local = keras.layers.MaxPooling1D(pool_size=pooling_size)(_local)
        local_layers.append(_local)
    merged = keras.layers.concatenate(local_layers, axis=1)

    # fully convolution
    conved = keras.layers.Conv1D(padding='valid', kernel_size=conv_size, filters=256, activation='relu')(merged)
    pooled = keras.layers.MaxPooling1D(pool_size=5)(conved)

    x = keras.layers.Flatten()(pooled)
    x = keras.layers.Dense(256, activation='relu')(x)
    x = keras.layers.Dense(256, activation='relu')(x)
    x = keras.layers.Dense(class_num, activation='softmax')(x)
    mcnn = keras.Model(inputs=input_layers, outputs=x)
    #mcnn.summary()
    mcnn.compile(loss='categorical_crossentropy', optimizer='Adam', metrics=['accuracy'])
    return mcnn


def main(data_path, conv_size,pooling_factor):
    # get the origin time series
    origin_train_data, train_label = load_data_label(data_path + 'Adiac_TRAIN.tsv', crop_times=10)
    origin_test_data, test_label = load_data_label(data_path + 'Adiac_TEST.tsv', crop_times=10)
    origin_train_data = preprocessing.normalize(origin_train_data, norm='l2')
    origin_test_data = preprocessing.normalize(origin_test_data, norm='l2')
    # get the transform sequences
    train_data, train_label, data_dim = get_mcnn_input(origin_train_data, train_label)
    #     [(3890, 167, 1), (3890, 84, 1), (3890, 56, 1), (3890, 42, 1), (3890, 34, 1), 
    #      (3890, 163, 1), (3890, 160, 1), (3890, 157, 1)] 
    #     (3890, 38)
    test_data, test_label, data_dim = get_mcnn_input(origin_test_data, test_label)
    class_num = train_label.shape[1]  # 38
    
    mcnn = get_mcnn_model(data_dim, class_num, conv_size,pooling_factor)
    
    cb = []
    def step_decay(ep, lr):
        lr = lr/2
        return lr
    # cb.append(keras.callbacks.LearningRateScheduler(step_decay, verbose=1))
    cb.append(keras.callbacks.ReduceLROnPlateau(monitor='loss', factor=0.1, patience=5, verbose=1, mode='auto', min_delta=0.0001, cooldown=0, min_lr=0))
    #checkpoint_path = os.path.join(logs_path, 'weights-improvement-{epoch:02d}-{loss:.2f}.hdf5')
    #cb.append(keras.callbacks.ModelCheckpoint(checkpoint_path, monitor='loss', verbose=0, save_best_only=True, save_weights_only=False, mode='auto'))
    #cb.append(keras.callbacks.TensorBoard(log_dir='logs', histogram_freq=0, write_graph=True, write_grads=False, write_images=False, embeddings_freq=0, embeddings_layer_names=None, embeddings_metadata=None, embeddings_data=None, update_freq='epoch'))
    # 当监测值不再改善时，该回调函数将中止训练可防止过拟合
    cb.append(keras.callbacks.EarlyStopping(monitor='val_accuracy', patience=10, verbose=1, mode='auto'))

    mcnn.fit(train_data, train_label, batch_size=16, verbose=0, validation_split=0.3, epochs=100, callbacks=cb)

    test_loss, test_acc = mcnn.evaluate(test_data, test_label, verbose=0)
    # print("Test loss - %(loss).2f, Test accuracy - %(acc).2f%%" % { 'loss': test_loss,                                                                            'acc': test_acc * 100})
    label_pred = mcnn.predict(test_data)
    return test_loss, test_acc

temp = []
flag = None
base_path = r'../input/adiac-test-train/' 
for i in range(1, 10):
    for j in range(1, 20):
        test_loss, test_acc = main(base_path,i,j)
        temp.append(test_acc)
````
