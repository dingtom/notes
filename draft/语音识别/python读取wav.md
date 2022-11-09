```
import numpy as np
import scipy.io as scio
import os
import  wave
import matplotlib.pyplot as plt
import json
from scipy.fftpack import fft
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
from random import shuffle
def read_wav_data(filename):
    '''
    读取一个wav文件，返回声音信号的时域谱矩阵和播放时间
    '''
    wav = wave.open(filename,"rb") # 打开一个wav格式的声音文件流
    num_frame = wav.getnframes() # 获取帧数
    num_channel=wav.getnchannels() # 获取声道数
    framerate=wav.getframerate() # 获取帧速率
    num_sample_width=wav.getsampwidth() # 获取实例的比特宽度，即每一帧的字节数
    print('帧数',num_frame,'  声道数',num_channel,'  帧速率',framerate,'  实例的比特宽度，即每一帧的字节数',
    num_sample_width)
    str_data = wav.readframes(num_frame) # 读取全部的帧
    wav.close() # 关闭流
    wave_data = np.fromstring(str_data, dtype = np.short) # 将声音文件数据转换为数组矩阵形式
    print( wave_data.shape)
    wave_data.shape = -1, num_channel # 按照声道数将数组整形，单声道时候是一列数组，双声道时候是两列的矩阵
    print( wave_data.shape)
    wave_data = wave_data.T # 将矩阵转置
    print( wave_data.shape)
    return wave_data, framerate  

def wav_show(wave_data, fs): # 显示出来声音波形
    time = np.arange(0, len(wave_data)) * (1.0/fs)  # 计算声音的播放时间，单位为秒
    # 画声音波形
    plt.plot(time, wave_data)  
    plt.show()  

if(__name__=='__main__'):
    wave_data, fs = read_wav_data("test.wav")  
    wav_show(wave_data[0],fs)
    wav_show(wave_data[1],fs)  # 如果是双声道则保留这一行，否则删掉这一行
```
```
import scipy.io.wavfile as wav
sampling_rate, wavsignal = wav.read(file)
```
