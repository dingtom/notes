- [ 2创建和安装新的驱动](#head1)
ap模式

###1 安装必要的工具
```sudo apt-get install gcc make linux-headers-$(uname -r) git-core```

```sudo apt-get install iw ```

```echo iface wlan0 inet manual | sudo tee -a /etc/network/interfaces ```

```sudo restart network-manager```

```echo blacklist iwldvm | sudo tee -a /etc/modprobe.d/csitool.conf ```

```echo blacklist iwlwifi | sudo tee -a /etc/modprobe.d/csitool.conf```

### <span id="head1"> 2创建和安装新的驱动</span>
```CSITOOL_KERNEL_TAG=csitool-$(uname -r | cut -d . -f 1-2) ```

加速github下载速度
```git config --global http.postBuffer 524288000```

```git clone https://github.com/dhalperi/linux-80211n-csitool.git ```

```cd linux-80211n-csitool ```

```git checkout ${CSITOOL_KERNEL_TAG}```

我没找到对应的内核版本标签这个tip没有执行
```UBUNTU_KERNEL_TAG=Ubuntu-3.13.0-32.57```
注意：正确选择Ubuntu系统的内核版本标签，标签查询链接为http://people.canonical.com/~kernel/info/kernel-version-map.html

```. /etc/lsb-release```
```git remote add ubuntu git://kernel.ubuntu.com/ubuntu/ubuntu-${DISTRIB_CODENAME}.git```
```git pull --no-edit ubuntu ${UBUNTU_KERNEL_TAG}```

```make -C /lib/modules/$(uname -r)/build M=$(pwd)/drivers/net/wireless/iwlwifi modules```

```sudo make -C /lib/modules/$(uname -r)/build M=$(pwd)/drivers/net/wireless/iwlwifi INSTALL_MOD_DIR=updates modules_install```

```sudo depmod ```

```cd ..```

###3安装新的固件
```git clone  https://github.com/dhalperi/linux-80211n-csitool-supplementary.git```

```for file in /lib/firmware/iwlwifi-5000-*.ucode; do sudo mv $file $file.orig; done```

```sudo cp linux-80211n-csitool-supplementary/firmware/iwlwifi-5000-2.ucode.sigcomm2010 /lib/firmware/```

```sudo ln -s iwlwifi-5000-2.ucode.sigcomm2010 /lib/firmware/iwlwifi-5000-2.ucode```

###4创建Logging程序
```make -C linux-80211n-csitool-supplementary/netlink```

###5更新无线网卡模块
```sudo modprobe -r iwlwifi mac80211```

```sudo modprobe iwlwifi connector_log=0x1```

！注意：每次reboot系统后需要重新执行

###6命令行启动无线网卡
```sudo ip link set wlan0 up```

###7探测AP、连接非加密AP、分配地址
！此时断开网线
```sudo iw dev wlan0 scan ```
```sudo iw wlan0 connect -w MERCURY_42E6```

```sudo dhclient wlan0```

###8启动捕获程序
```sudo linux-80211n-csitool-supplementary/netlink/log_to_file csi.dat```
另开一个终端查看网关，并ping默认网关
```route```

```sudo ping 192.168.0.1  -i 0.05```

# Monitor模式
Monitor模式可以调制发送速率、发包数量、发送天线个数、HT模式、short/long guard interval等

## 首先,修改linux-80211n-csitool-supplementary/injection文件夹中的脚本
修改setup_monitor_csi.sh
```
#!/usr/bin/sudo /bin/bash
service network-manager stop
SLEEP_TIME=2
WLAN_INTERFACE=$1
if [ "$#" -ne 3 ]; then
    echo "Going to use default settings!"
    chn=64
    bw=HT20
else
    chn=$2
    bw=$3
fi
echo "Bringing $WLAN_INTERFACE down....."
ifconfig $WLAN_INTERFACE down
while [ $? -ne 0 ]
do
    ifconfig $WLAN_INTERFACE down
done
sleep $SLEEP_TIME
echo "Set $WLAN_INTERFACE into monitor mode....."
iwconfig $WLAN_INTERFACE mode monitor
while [ $? -ne 0 ]
do
    iwconfig $WLAN_INTERFACE mode monitor
done
sleep $SLEEP_TIME
echo "Bringing $WLAN_INTERFACE up....."
ifconfig $WLAN_INTERFACE up
while [ $? -ne 0 ]
do
    ifconfig $WLAN_INTERFACE up
done
sleep $SLEEP_TIME
echo "Set channel $chn $bw....."
iw $WLAN_INTERFACE set channel $chn $bw
```
修改setup_inject.sh
```
#!/usr/bin/sudo /bin/bash
service network-manager stop
WLAN_INTERFACE=$1
SLEEP_TIME=2
modprobe iwlwifi debug=0x40000
if [ "$#" -ne 3 ]; then
    echo "Going to use default settings!"
    chn=64
    bw=HT20
else
    chn=$2
    bw=$3
fi
sleep $SLEEP_TIME
ifconfig $WLAN_INTERFACE 2>/dev/null 1>/dev/null
while [ $? -ne 0 ]
do
    ifconfig $WLAN_INTERFACE 2>/dev/null 1>/dev/null
done
sleep $SLEEP_TIME
echo "Add monitor mon0....."
iw dev $WLAN_INTERFACE interface add mon0 type monitor
sleep $SLEEP_TIME
echo "Bringing $WLAN_INTERFACE down....."
ifconfig $WLAN_INTERFACE down
while [ $? -ne 0 ]
do
    ifconfig $WLAN_INTERFACE down
done
sleep $SLEEP_TIME
echo "Bringing mon0 up....."
ifconfig mon0 up
while [ $? -ne 0 ]
do
    ifconfig mon0 up
done
sleep $SLEEP_TIME
echo "Set channel $chn $bw....."
iw mon0 set channel $chn $bw
 ```
 
 

###1 先安装ap模式
 ```sudo apt-get install libpcap-dev```

###2 接受\发送均需
``` git clone https://github.com/dhalperi/lorcon-old.git``` 

``` cd lorcon-old``` 

``` ./configure``` 

``` make``` 

``` sudo make install``` 

``` cd ~``` 

``` cd linux-80211n-csitool-supplementary/injection``` 

``` make``` 
###3 接收端操作
```cd linux-80211n-csitool-supplementary/injection```

```./setup_monitor_csi.sh wlan0 13 HT20 ```
参数wlan0是网卡名称，可运行iwconfig命令查看；13为2.4G频段信道编号，如果只填了网卡名称，信道编号和HT模式会使用默认值64+HT20（5G频段）。

```sudo /home/csi/linux-80211n-csitool-supplementary/netlink/log_to_file log.dat   ```
等待接收数据，这里的csi是你的计算机名称
### 4 发送端操作
```cd linux-80211n-csitool-supplementary/injection/```

```./setup_inject.sh wlan0 13 HT20```
下载后需覆盖原脚本，wlan0为你的网卡名称，13为信道编号setup_inject.sh也是基于类似的理念修改的：添加运行说明；添加网卡名为参数；添加stop network-manager
```sudo echo 0x4101 | sudo tee /sys/kernel/debug/ieee80211/phy0/iwlwifi/iwldvm/debug/monitor_tx_rate ```

0x4101这部分参数设置具体参见下图，该图引自CSI Tool安装使用讲解

比如0x1c101表示支持选择三根天线发送、OFDM下的HT20模式，不过Rate Selection部分对应速率尚不明。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-b1361b8fb4048d33.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```sudo ./random_packets 100000 100 1  ```
第一个参数：包的数量 第二个参数：包的长度 第三个参数：包与包间delay（微秒）。

