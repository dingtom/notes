- [ CSI_Activity_Recognition_based-on-CLDNN](#head1)
- [ CsiGAN](#head2)
- [CSIwork ](#head3)
- [Dataflow network framework](#head4)
- [ FallDetection](#head5)
- [ SignFi](#head6)
- [ CSI_Activity_Recognition_based-on-CLDNN](#head7)
- [ CsiGAN](#head8)
- [CSIwork ](#head9)
- [Dataflow network framework](#head10)
- [ FallDetection](#head11)
- [ SignFi](#head12)
- [## CSI_Activity_Recognition_based-on-CLDNN](#head13)
- [ CsiGAN](#head14)
- [CSIwork ](#head15)
- [Dataflow network framework](#head16)
- [ FallDetection](#head17)
- [ SignFi](#head18)
- [ 实时显示](#head19)
	- [ csitool-for-realview](#head20)
	- [Athero-CSI-tool-Python-RemoteReceive-Liveview-AmplitudeScaled ](#head21)
	- [ CSI_monitor_demo](#head22)
	- [ The-Real-time-Visualization-System-Based-on-CSI](#head23)
	- [# CSIPlotter](#head24)
## <span id="head1"> CSI_Activity_Recognition_based-on-CLDNN</span>
[cldnn](http://m.elecfans.com/article/744476.html) 
有数据

## <span id="head2"> CsiGAN</span>
[GAN](https://www.cnblogs.com/bonelee/p/9166084.html)
有数据，对计算性能要求高

## <span id="head3">CSIwork </span>
davidbrave,last month
MUSIC估计函数
成员函数:
套接字:按套接字从AP获取数据包
csi smooth:为了更好的eig，更多的信息在SpotFi
csi extend:Intel5300 NIC在一个数据包中收集30个子载波，实际56个子载波索引为[-28.]1]和[1……这个功能是通过LP恢复子载波信息
multipath remove: zero the tailed FFT components
MUSIC aoa 估计
pyqtgraph 实时图

## <span id="head4">Dataflow network framework</span>
DanielHaimanot,2 years ago
文件夹框架/示例中的示例网络:
实时信号绘图仪
在示例SharedMemBlock.py中，块之间具有共享内存通道的网络
在StreamingBlock.py中使用流媒体通道的网络
在框架/块组件中实现块和通道类。
更多的信息和更大的网络实现来了!
这是流式绘图仪的例子:

## <span id="head5"> FallDetection</span>
mabagheri, 2 months ago

## <span id="head6"> SignFi</span>
yongsen,last year
有大量数据

## <span id="head7"> CSI_Activity_Recognition_based-on-CLDNN</span>
[cldnn](http://m.elecfans.com/article/744476.html) 

有数据

## <span id="head8"> CsiGAN</span>
[GAN](https://www.cnblogs.com/bonelee/p/9166084.html)

有数据，对计算性能要求高

## <span id="head9">CSIwork </span>
davidbrave,last month

MUSIC估计函数

成员函数:

套接字:按套接字从AP获取数据包
csi smooth:为了更好的eig，更多的信息在SpotFi

csi extend:Intel5300 NIC在一个数据包中收集30个子载波，实际56个子载波索引为[-28.]1]和[1……这个功能是通过LP恢复子载波信息

multipath remove: zero the tailed FFT components

MUSIC aoa 估计

pyqtgraph 实时图

## <span id="head10">Dataflow network framework</span>
DanielHaimanot,2 years ago

文件夹框架/示例中的示例网络:
实时信号绘图仪
在示例SharedMemBlock.py中，块之间具有共享内存通道的网络
在StreamingBlock.py中使用流媒体通道的网络
在框架/块组件中实现块和通道类。
更多的信息和更大的网络实现来了!
这是流式绘图仪的例子:

## <span id="head11"> FallDetection</span>
mabagheri, 2 months ago

## <span id="head12"> SignFi</span>
yongsen,last year

## <span id="head13">## CSI_Activity_Recognition_based-on-CLDNN</span>
[cldnn](http://m.elecfans.com/article/744476.html) 

有数据

## <span id="head14"> CsiGAN</span>
[GAN](https://www.cnblogs.com/bonelee/p/9166084.html)

有数据，对计算性能要求高

## <span id="head15">CSIwork </span>
davidbrave,last month

MUSIC估计函数

成员函数:

套接字:按套接字从AP获取数据包
csi smooth:为了更好的eig，更多的信息在SpotFi

csi extend:Intel5300 NIC在一个数据包中收集30个子载波，实际56个子载波索引为[-28.]1]和[1……这个功能是通过LP恢复子载波信息

multipath remove: zero the tailed FFT components

MUSIC aoa 估计

pyqtgraph 实时图

## <span id="head16">Dataflow network framework</span>
DanielHaimanot,2 years ago

文件夹框架/示例中的示例网络:
实时信号绘图仪
在示例SharedMemBlock.py中，块之间具有共享内存通道的网络
在StreamingBlock.py中使用流媒体通道的网络
在框架/块组件中实现块和通道类。
更多的信息和更大的网络实现来了!
这是流式绘图仪的例子:

## <span id="head17"> FallDetection</span>
mabagheri, 2 months ago

## <span id="head18"> SignFi</span>
yongsen,last year

# <span id="head19"> 实时显示</span>
## <span id="head20"> csitool-for-realview</span>
是服务器上运行的工具，用于csi-tool 802.11n上的realview。在测试场景中，有三个pc，分别用于Tx和RX，另一个用于realview。当Rx接收到任何数据包时，我们接收到的数据包由Rx传输，注意传输是通过UDP协议完成的。

## <span id="head21">Athero-CSI-tool-Python-RemoteReceive-Liveview-AmplitudeScaled </span>
laster year
这个CSI live view工具是基于开源项目Atheros CSI工具(http://pdcc.ntu.edu.sg/wands//)开发的。我们添加套接字函数来将收集到的数据传输到远程服务器。在服务器上，我们使用python实时绘制CSI。
在这个工具中，我们开发了dBm中幅度与功率的比例函数。
有关详细信息，请参阅使用文档。

## <span id="head22"> CSI_monitor_demo</span>
4 months ago
未测试

## <span id="head23"> The-Real-time-Visualization-System-Based-on-CSI</span>
较好 有本科论文

## <span id="head24"># CSIPlotter</span>
7months ago
mode: 
**rssi** 类型；接受；发射数；子载波数均不可设置，仅实时显示rssi值。
**subcarrier**图中显示了指定的TX-RX子载波振幅或相位值。注意，在显示CSI时，TX设置是必不可少的。
**antenna pair mode**，图中将显示指定的TX-RX 30子载波幅值或相位值。TX也很重要。
**all csi mode**，将显示所有csi值。在这种模式下，TX也不会出错。组合框类型包含决定显示数据类型的振幅和相位。
 create table pkuvalue select `value`,count(0) from pkubase group by `value` order by count(0) desc;
