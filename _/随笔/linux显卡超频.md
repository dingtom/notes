- [1. 安装X以及在X中加入GPU选项](#head1)
- [2. 启动 X 并检测 GPU 参数](#head2)
- [3. 超频及降电](#head3)


# <span id="head1">1. 安装X以及在X中加入GPU选项</span>

Nvidia 超频必须调用 X，原因不明。最轻量的方式就是安装 lightdm. 如果在安装过程中有交互窗口要求选择默认桌面，请选择lightdm 而不是 gdm.

apt install xorg lightdm lightdm-gtk-greeter
调用Nvidia软件生成Xorg配置

nvidia-xconfig --cool-bits=24 --enable-all-gpus
Cool-bits 用于开启 CLI控制、风扇控制、内存频率控制等操作，24 代表开启所有功能，具体位表可在[1]查看。--enable-all-gpus 参数代表对主机上的所有显卡都启用。

期望输出如下

Using X configuration file: "/etc/X11/xorg.conf".
Backed up file '/etc/X11/xorg.conf' as '/etc/X11/xorg.conf.backup'
New X configuration file written to '/etc/X11/xorg.conf'

如果出现 Unable to count GPU / Unable to locate/open X configuration file 等错误，则需要在重启主机后重新执行上述命令。

接下来编辑 /etc/X11/xorg.conf 文件，找到 Options Cool-bits 行，加入以下选项，以允许在没有连接显示器的情况下运行 X (Headless X server).

    Option         "AllowEmptyInitialConfiguration" "True"
    Option         "ConnectedMonitor" "DFP-0"
    Option         "Interactive" "False"
    锁定xorg文件防止lightdm, nvidia等软件修改
chattr +i /etc/X11/xorg.conf
此时需要重启主机

reboot

# <span id="head2">2. 启动 X 并检测 GPU 参数</span>
主机重启完成后，执行以下指令启动 X

systemctl start lightdm

如果出现错误，请查看 /var/log/Xorg.0.log 文件。

查看 GPU 优先模式，[gpu:0] 代表第0个GPU，编号与 nvidia-smi 命令输出结果对应。

DISPLAY=:0 XAUTHORITY=/var/run/lightdm/root/:0 nvidia-settings -q '[gpu:0]/GPUPerfModes'
以下是一张 RTX 2060 的输出例子

Attribute 'GPUPerfModes' (henry-Default-string:0[gpu:0]): perf=0, nvclock=300, nvclockmin=300, nvclockmax=645, nvclockeditable=1, memclock=405, memclockmin=405, memclockmax=405,
  memclockeditable=1, memTransferRate=810, memTransferRatemin=810, memTransferRatemax=810, memTransferRateeditable=1 ; perf=1, nvclock=300, nvclockmin=300, nvclockmax=2100, nvclockeditable=1,
  memclock=810, memclockmin=810, memclockmax=810, memclockeditable=1, memTransferRate=1620, memTransferRatemin=1620, memTransferRatemax=1620, memTransferRateeditable=1 ; perf=2, nvclock=300,
  nvclockmin=300, nvclockmax=2100, nvclockeditable=1, memclock=5001, memclockmin=5001, memclockmax=5001, memclockeditable=1, memTransferRate=10002, memTransferRatemin=10002,
  memTransferRatemax=10002, memTransferRateeditable=1 ; perf=3, nvclock=300, nvclockmin=300, nvclockmax=2100, nvclockeditable=1, memclock=7301, memclockmin=7301, memclockmax=7301,
  memclockeditable=1, memTransferRate=14602, memTransferRatemin=14602, memTransferRatemax=14602, memTransferRateeditable=1 ; perf=4, nvclock=300, nvclockmin=300, nvclockmax=2100,
  nvclockeditable=1, memclock=7501, memclockmin=7501, memclockmax=7501, memclockeditable=1, memTransferRate=15002, memTransferRatemin=15002, memTransferRatemax=15002, memTransferRateeditable=1

在这里，每个优先级别均以分号;隔开，在这里我们关心 memclockeditable 和 nvclockeditable 参数，分别代表内存和核心是否可以更改。如果值为 1 则代表可以更改。

perf 代表不同的级别，显卡正常工作于最高级别（在这个例子里面为4），当显卡过热的时候，会降级到级别 3，如果再过热则降到 2，以此类推。因此，对于普通超频操作，我们只需要更改最高级别的参数，以免损坏显卡。

-q 查询参数还能查询其他值，例如 '-q gpus' 输出所有 GPU, '-q fans' 输出风扇状态，'-q all' 输出所有参数等。

# <span id="head3">3. 超频及降电</span>
执行以下指令即可对GPU超频

DISPLAY=:0 XAUTHORITY=/var/run/lightdm/root/:0 nvidia-settings -a '[gpu:0]/GPUMemoryTransferRateOffset[4]=200'

DISPLAY 及/var/run/lightdm/root/:0 是我们在第2步启动的lightdm，默认编号是 0. 如果主机启用了多个显示，则需要按需更改。

[gpu:0]/GPUMemoryTransferRateOffset[4]=100 代表在第 0 个 GPU 的第 4 个级别上对内存频率增加 100。 这里也可以使用 '[gpu:0]/GPUGraphicsClockOffset[4]=50' 代表在第 0 个GPU 的第 4 个级别对核心频率增加 50. 增加值建议从小值开始，慢慢往上加。

使用以下指令可以更改 GPU 风扇速度，对于超频一般不需要更改 GPU 风扇，默认自动就好。

DISPLAY=:0 XAUTHORITY=/var/run/lightdm/root/:0 nvidia-settings -a "[gpu:0]/GPUFanControlState=1"
DISPLAY=:0 XAUTHORITY=/var/run/lightdm/root/:0 nvidia-settings -a "[gpu:0]/GPUTargetFanSpeed=80"

使用 nvidia-smi 查看电量情况

nvidia-smi

RTX 2060 输出例子
```
 +-----------------------------------------------------------------------------+
| NVIDIA-SMI 460.32.03    Driver Version: 460.32.03    CUDA Version: 11.2     |
|-------------------------------+----------------------+----------------------+
| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
|                               |                      |               MIG M. |
|===============================+======================+======================|
|   0  GeForce RTX 2060    On   | 00000000:02:00.0 Off |                  N/A |
| 66%   59C    P2   127W / 160W |   4476MiB /  5934MiB |    100%      Default |
|                               |                      |                  N/A |
+-------------------------------+----------------------+----------------------+
```
使用以下指令可以降低最大电量。

nvidia-smi -i 0 -pl 130
其中 0 为第 0 个 GPU，130 为新设定最大电量值。省略 -i 参数则对所有 GPU 应用。