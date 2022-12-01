参考了https://blog.csdn.net/qq_20386411/article/details/83384614

使用python调用matlab API接口进行数据分析
######找到matlab安装目录下自带的setup.py的路径我的路径如下
D:\MATLAB\R2018a\extern\engines\python
######打开cmd进入到刚才找到的路径
######*管理员权限执行！！！*      pyhton setup.py install


#具体代码
1.将csiTool中的matlab文件夹下的文件拷到pycharm的工作目录下，我的拷出来不行，于是从Github中重新下载了下来，考进去成功！

```
csi_trace=read_bf_file('csi.dat');
csi_entry=csi_trace{1}
```
```
timestamp_low: 4            (In the sample trace, timestamp_low is invalid and always 4.)
       bfee_count: 72
              Nrx: 3
              Ntx: 1
           rssi_a: 33
           rssi_b: 37
           rssi_c: 41
            noise: -127
              agc: 38
             perm: [3 2 1]
             rate: 256
              csi: [1x3x30 double]
```
timestamp_low：NIC网卡1MHz时钟的低32位。它大约4300s（72min）重复一回。 （从0-2^32需要4300s），时间戳，相连两包此值差单位为微秒，通过验证发现100hz的发包频率此差值为10000,20hz的发包频率此差值为50000，此参数可以确定出波形的横轴时间。
**当发包频率过大会出现接收端停止收数的情况：20Hz正常，200Hz和1000Hz的采样频率，收端最多接收一分多钟的的数据（原因是recv函数接收数据阻塞导致）
 1、将包长变小后还是有同样的问题。
 2、由数据速率引起的，它太高，程序无法处理（可以尝试设置非阻塞）**

0x4101决定了主机仅一根天线发送数据，因此此处的Ntx值为1，另外采用的是OFDM模式 如果想要收端接收到发端两根天线的数据（CSI的格式为2×3×30），只需要把第14、15位均设置为1，即在发送端的脚本里面设置为0xC101 注：关于0x4101的具体说明如下

bfee_count：驱动记录并发送到用户控件的波束测量值的总数。内核和用户空间中netlink频道是有损的，可以用该变量来检测被丢弃的测量值。 

Nrx：接收端使用的天线数量。 

Ntx：发送端使用的天线数量。 

rssi_a, rssi_b, rssi_c：由接收端NIC测量出的RSSI值。 

perm：展示NIC如何将3个接收天线的信号排列到3个RF链上，上图中的数据表示天线A被发送到RF链A，天线B被发送到RF链B，天线C被发送到RF链C。 

rate：发包频率。 

csi：CSI值

3374094 
###MATLAB命令
```
a=[1 2 3;4 5 6]        #矩阵

A'                            #A的共轭转置矩阵

ones(2,3)                #一矩阵

b=1:0.1:2                #集合间距为0.1

zeros(2,3)               #零矩阵

rand(2,3)              #随机矩阵，数值介于0和1之间

randn(2,3)             #矩阵(一个平均值为0的高斯分布，方差或者等于1的标准偏差）

eye(2)                 #单位阵

hist()                   #直方图

size()                   #矩阵的尺寸

size(A, 1)            #矩阵第一维的尺度

length(A)            #由于矩阵A是一个
3×2的矩阵，因此最大的维度应该是3，因此该命令会返回3。

who                 # 命令，能显示出工作空间中的所有变量

whos              #命令，能更详细地进行查看

load('featureX.dat')   # 加载数据文件，变量名等于文件名

clear               #命令，删除所有变量，加变量名删除特定

 save hello.mat v           #这个命令会将变量v存成一个叫 hello.mat 的文件，按二进制存储

save hello.txt v -ascii       # 这样就会把数据存成一个文本文档，或者将数据的 ascii 码存成文本文档

键入 A(3,2)      # 这将索引到 矩阵的 (3,2) 元素。

键入A(2,:)       #返回第二行的所有元素

A([1 3],:)        #取的是A矩阵的第一行和第三行的每一列

A = [A, [100, 101,102]]      #这样做的结果是在原矩阵的右边附加了一个新的列矩阵

A(:)              #这是一个很特别的语法结构，意思是把 A中的所有元素放入一个单独的列向量，这样我们就得到了一个 9×1 的向量

A.*B            #这么做将矩阵 A中的每一个元素与矩阵 B中的对应元素相乘

A.^2              #这将对矩阵中每一个元素平方

1./A               #得到中每一个元素的倒数。同样地，这里的点号还是表示对每一个元素进行操作。

exp(A)            #自然数e的幂次运算

abs(A)            #取绝对值

val=max(A)            #默认情况下max(A)返回的是每一列的最大值，如果你想要找出整个矩阵A的最大值，你可以输入max(max(A))

[val, ind] =max(A)        #这将返回矩阵中的最大值存入，以及该值对应的索引

A<3                      #这将进行逐元素的运算，所以元素小于3的返回1，否则返回0。

find(A<3)                #这将告诉我A中的哪些元素是小于3的。

A = magic(3)           #magic 函数将返回一个矩阵，称为魔方阵或幻方 (magic squares)，它们具有以下这样的数学性质：它们所有的行和列和对角线加起来都等于相同的值。

[r,c] = find(A>=7)       #这将找出所有矩阵中大于等于7的元素，r 和c分别表示行和列

 sum(a)                      #就把 a 中每列所有元素加起来了。

sum(a,2)                   #求每行的和

prod(a)                       #prod 意思是product(乘积)，它将返回这四个元素的乘积。

floor(a)                      #向下四舍五入，因此对于 a中的元素0.5将被下舍入变成0。

ceil(a)                      #向上四舍五入，所以0.5将上舍入变为最接近的整数，也就是1。

type(3)                    #这通常得到一个3×3的矩阵

max(rand(3),rand(3))          #这样做的结果是返回两个3×3的随机矩阵，并且逐元素比较取最大值。

max(A,[],1)                           #这样做会得到每一列的最大值。

max(A,[],2)                         #这将得到每一行的最大值。

sum(sum(A.*eye(9)

hold on                              #函数的功能是将新的图像绘制在旧的之上。

再加上命令xlabel('time')， 来标记X轴即水平轴，输入ylabel('value')，来标记垂直轴的值。

legend('sin','cos')            #表示这两条曲线表示的内容。
title('myplot')                  #显示这幅图的标题。

print(gcf,'-dpng','abc.png')        #保存为png格式的图片到当前路径

squeeze()                        #用于删除矩阵中的单一维,对二维矩阵无效。

close会让这个图像关掉。

键入figure(1); plot(t, y1);将显示第一张图，键入figure(2); plot(t, y2); 将显示第二张图（同时显示两张图）

subplot(1,2,1)，它将图像分为一个1*2的格子，也就是前两个参数，然后它使用第一个格子，也就是最后一个参数1的意思

axis([0.5 1 -1 1])也就是设置了轴的范围。横轴的范围调整至0.5到1，竖轴的范围为-1到1。

Clf清除一幅图像。

我有时用一个巧妙的方法来可视化矩阵，也就是imagesc(A)命令
我还可以使用函数colorbar，让我用一个更复杂的命令 imagesc(A)，colorbar，colormap gray    它生成了一个颜色图像，一个灰度分布图，并在右边也加入一个颜色条。所以这个颜色条显示不同深浅的颜色所对应的值。
```
```
csi_trace = read_bf_file('csi6.dat');%数据读取
for l=1:1  %取50个数据包的数据
	csia=get_scaled_csi(csi_trace{l});%提取csi矩阵
	for i=1:1  %1个发射天线
		for j=1:3   %2个接收天线
			for k=1:30    %30个子载波数据
				B(i,j,k)=csia(i,j,k);				
			end
		end
	end
	plot(db(abs(squeeze(B).')))
    %squeeze通过移除第一个单维度将csi变成3*30的矩阵
    %db将线性空间变成以十为底的对数空间
    %.'转置得到30*3的矩阵
    %plot(Y)如果Y是m×n的数组，以1:m为X横坐标，Y中的每一列元素为Y坐标，绘制n条曲线
	hold on%当前轴及图像保持而不被刷新，准备接受此后将绘制的图形，多图共存
end
legend('RX Antenna A', 'RX Antenna B','RX Antenna C', 'Location', 'SouthEast' );
xlabel('Subcarrier index');
ylabel('SNR [dB]')
hold off;
%get_eff_SNRs() 它接受一个CSI矩阵作为输入，并返回一个线性(幂)空间中有效信噪比值的74矩阵。
%这4列对应的有效信噪比使用四种802.11调制方案，即BPSK/QPSK/16QAM/64QAM。
%7行对应于7个可能的天线选择，包括3个天线和1、2或3个空间流。
%特别是，前3行对应于天线A、B或c的单流传输，后3行对应于天线AB、AC或BC的双流传输。最后一行对应于使用所有天线的3流传输
```
