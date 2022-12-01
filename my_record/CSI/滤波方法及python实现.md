对滤波的[总结](https://zhuanlan.zhihu.com/p/55543887)：**对特定频率进行有效提取，并对提取部分进行特定的处理（增益，衰减，滤除）的动作被叫做滤波。**

最常用的滤波器类型有三种：**通过式（Pass），搁架式（Shelving）和参量式（Parametric）。**滤波器都有一个叫**参考频率（Reference Frequency）的东西**，在不同类型的滤波器中，具体的叫法会有所不同。

通过式滤波器可以让参考频率一侧的频率成分完全通过该滤波器，同时对另一侧的频率成分做线性的衰减，就是，一边让通过，一边逐渐被滤除。在信号学中，通过的区域被称为通带，滤除的区域被叫做阻带，在通过式滤波器中，参考频率通常被称为截止频率。
![](https://upload-images.jianshu.io/upload_images/18339009-c91f08961370faad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

高通滤波器（high-pass filters）：让截止频率后的高频区域通过，另一侧滤除，低通滤波器（low-pass filters）：让截止频率前的低频区域通过，另一侧滤除，通

以下是高通滤波器与低通滤波器的核心参数：
![](https://upload-images.jianshu.io/upload_images/18339009-c2954dfd215df893.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**截止频率（Cut-off frequency）**：决定了通带（通过的频率部分）与阻带（阻止的频率部分）的分界曲线，截止频率的位置并非是在曲线开始弯曲的那个点，而是在-3dB的位置。以图2左侧的高通滤波器为例，截止频率点之上的部分频率并没有全部被通过，而是有个曲线，在曲线回归平直后其频率才被完全通过。至于为什么要将-3dB的位置设为截止频率，是因为-3dB对于滤波器的设计而言是个非常重要的位置，如果设为其他位置，则会让通过式滤波器的设计变得尤为复杂。

**斜率（Slope）**：表示的是通带与阻带的分界曲线的倾斜程度，也就是说斜率决定了分界曲线是偏向平缓的，还是偏向垂直的，斜率越大（更陡峭），人工处理的痕迹就越明显。斜率的单位为dB/oct，中文称为分贝每倍频程。虽然绕口，但其实很简单，如6dB/oct，意思为一个倍频程的距离会产生6dB的衰减，数字滤波器常见的斜率选择有6dB/oct，12dB/oct，18dB/oct，24dB/oct，30dB/oct等等（图3）。
>另外，倍频程其实在P7中有提及到，在此再次说明一下，在频谱图上，两个音程关系为八度的音之间的频率点的距离被称为倍频程，如标准音A为440Hz,其相对高八度音的A（高音A）为880Hz，那么440Hz到880Hz就是一个倍频程的关系。以上纯概念的东西搞清楚了之后，我们再举例说说斜率是怎样影响音色的，如一个截止频率为800hz的低通滤波器，斜率24dB/oct就会比6dB/oct的声音更闷，因为24dB/oct的斜率更加陡峭，高频被滤除的更加干净，所以声音就更加闷（人工处理的痕迹就更明显）。

# scipy.signal滤波函数
```scipy.signal.filtfilt(b, a, x, axis=-1, padtype='odd', padlen=None, method='pad', irlen=None)```

###### 输入参数：
- b: 滤波器的分子系数向量
- a: 滤波器的分母系数向量
- x: 要过滤的数据数组。（array型）
- axis: 指定要过滤的数据数组x的轴
- padtype: 必须是“奇数”、“偶数”、“常数”或“无”。这决定了用于过滤器应用的填充信号的扩展类型。{‘odd’, ‘even’, ‘constant’, None}
- padlen：在应用滤波器之前在轴两端延伸X的元素数目。此值必须小于要滤波元素个数- 1。（int型或None）
- method：确定处理信号边缘的方法。当method为“pad”时，填充信号；填充类型padtype和padlen决定，irlen被忽略。当method为“gust”时，使用古斯塔夫森方法，而忽略padtype和padlen。{“pad” ，“gust”}
- irlen：当method为“gust”时，irlen指定滤波器的脉冲响应的长度。如果irlen是None，则脉冲响应的任何部分都被忽略。对于长信号，指定irlen可以显著改善滤波器的性能。（int型或None）

# .滤波器构造函数(仅介绍Butterworth滤波器)
```scipy.signal.butter(N, Wn, btype='low', analog=False, output='ba')```

##### 输入参数：

- N:滤波器的阶数，就是指过滤谐波的次数，一般来讲，同样的滤波器，其阶数越高，滤波效果就越好，但是，阶数越高，成本也就越高
- Wn：归一化截止频率。计算公式Wn=2*截止频率/采样频率。***注意：根据采样定理，采样频率要大于两倍的信号本身最大的频率，才能还原信号截止频率一定小于信号本身最大的频率**，所以Wn一定在0和1之间）。当构造带通滤波器或者带阻滤波器时，Wn为长度为2的列表。

这里假设采样频率为1000hz,信号本身最大的频率为500hz，要滤除10hz以下和400hz以上频率成分，即截至频率为10hz和400hz,则wn1=2\*10/1000=0.02,wn2=2\*400/1000=0.8。Wn=[0.02,0.8]
- btype : 滤波器类型{‘lowpass’, ‘highpass’, ‘bandpass’, ‘bandstop’},
- output : 输出类型{‘ba’, ‘zpk’, ‘sos’},

##### 输出参数：
- b，a: IIR滤波器的分子（b）和分母（a）多项式系数向量。output='ba'
- z,p,k: IIR滤波器传递函数的零点、极点和系统增益. output= 'zpk'
- sos: IIR滤波器的二阶截面表示。output= 'sos'

```
  b, a = signal.butter(5, 4*2/30, 'low')
  #b, a = signal.butter(5, [0.2*2/20, 5*2/20], "bandpass")
  data = signal.lfilter(b, a, data_csi) 
```
