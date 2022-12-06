---
        # 文章标题
        title: "linux-Linux命令"
        # 分类
        categories: 
            - linux
        # 发表日期
        date: 2022-12-02T03:11:14+08:00

---

```Ctrl+c```       强制中断程序的执行
```Ctrl+z```	将程序挂起，```fg```/```bg```继续任务

```Ctrl+d```	键盘输入结束或退出终端
```Ctrl+s```	中断控制台输出
```ctrl+q```        恢复控制台输出
```ctrl+l```          清屏

```ctrl+左右键``` 在单词之间跳转

```Ctrl+a```	将光标移至输入行头，相当于Home键
```Ctrl+e```	将光标移至输入行末，相当于End键

```Ctrl+u```：删除当前光标前面的文字

```Ctrl+k```	删除从光标所在位置到行末
```Alt+Backspace```	向前删除一个`单词`
命令 --help  查看一些它的某个命令具体参数的作用
man 命令   显示系统手册页中的内容

一行中运行多个命令。
Command 1 ; Command 2 首先运行Command1，然后运行Command2
Command 1 && Command 2 当Command1运行成功并结束，然后运行Command2
Command 1 || Command 2 当Command1运行失败时才运行Command2


# 文件
![](https://upload-images.jianshu.io/upload_images/18339009-be4aa0903719bf65.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>bin (binaries)存放二进制可执行文件
sbin (super user binaries)存放二进制可执行文件，只有root才能访问
etc (etcetera)存放系统配置文件
usr (unix shared resources)用于存放共享的系统资源
home 存放用户文件的根目录
root 超级用户目录
dev (devices)用于存放设备文件
lib (library)存放跟文件系统中的程序运行所需要的共享库及内核模块
mnt (mount)系统管理员安装临时文件系统的安装点
boot 存放用于系统引导时使用的各种文件
tmp (temporary)用于存放各种临时文件
var (variable)用于存放运行时需要改变数据的文件

# 文件增删改查
##  mkdir
创建目录和父目录a,b,c,d
``` mkdir -p a/b/c/d ```
>-p 建立多级目录
-m 在建立目录的时候给目录赋于权限值

## ls
>-a 这个选项能显示.开头的隐藏文件
-l 除文件名称外，亦将文件型态、权限、拥有者、文件大小等资讯详细列出
-t 将文件依建立时间之先后次序列出
-i 显示每个文件的inode号
-m 所有项目以逗号分隔，并填满整行行宽
-R 同时列出所有子目录层
-h 将列出文件的大小以人性化格式输出
--color=tty(2个-号)显示文件的时候以色彩提示
 -full-time显示文件的详细访问时间
-lc查看文件状态修改时间



##  rmdir 
删除空目录
>-p 删除多级空目录

## cp
拷贝文件夹a到/tmp目录
``` cp -rvf a/ /tmp/```
>-r 复制整个目录里的内容
-p 复制文件属性
-u 有差异才复制
-i 文件已存在询问
-v 在复制文件的时候显示进度
-f 在复制的时候如果碰到目的文件名有重复就将原先的删除

#### mv
>重命名 ```mv a.txt b.txt```
>移动文件a到/tmp目录，并重命名为b
```mv -vf a /tmp/b```
>-i 如果目的地有相同文件名时会出现提示
-v 在搬移文件时显示进度，在移动多文件时非常有用
-u 当移动时只有源文件比目的文件新的时候才会移动
-f 强制覆盖已有的文件


#### touch 
>创建文件
```touch asd.txt ```
创建多个文件
```touch love_{1..10}_shiyanlou.txt```

#### rm
>删除机器上的所有文件
 rm -rvf /
>-i 在删除文件之前需要手工确认
-v 在删除文件的时候显示信息
-r 删除目录
 -f 忽略提示



#### tr 
>删除、转换一段文本信息中的某些文字
-d	删除匹配的字符，注意不是全词匹配也不是按字符顺序匹配
-s	去除指定的在输入文本中连续并重复的字符
>删除 "hello shiyanlou" 中所有的'o','l','h'
```echo 'hello shiyanlou' | tr -d 'olh'```
将"hello" 中的ll,去重为一个l
```echo 'hello' | tr -s 'l'```
将输入文本，全部转换为大写或小写输出
```echo 'input some text here' | tr '[:lower:]' '[:upper:]'```
上面的'[:lower:]' '[:upper:]'你也可以简单的写作'[a-z]' '[A-Z]',当然反过来将大写变小写也是可以的

#### jion
>将两个文件中包含相同内容的那一行合并在一起。
-t	指定分隔符，默认为空格
-i	忽略大小写的差异
-1	指明第一个文件要用哪个字段来对比，默认对比第一个字段
-2	指明第二个文件要用哪个字段来对比，默认对比第一个字段
>将/etc/passwd与/etc/group两个文件合并，指定以':'作为分隔符, 分别比对第4和第3个字段
``` sudo join -t':' -1 4 /etc/passwd -2 3 /etc/group```

#### paste
>在不对比数据的情况下，简单地将多个文件合并一起，以Tab隔开。
-d	指定合并的分隔符，默认为 Tab
-s	不合并到一行，每个文件为一行

```paste -d '+' file1 file2``` 合并两个文件或两栏的内容，中间用"+"区分

#### cat 
>第一个字节开始正向查看文件的内容
如果文件很大的话，cat命令的输出结果会疯狂在终端上输出，可以多次按ctrl+c终止。
>-b 显示文件内容的时候显示行数
>-n 显示文件内容包括空行
-s 将多个空行合并成一个空行输出

#### tac
 从最后一行开始反向查看一个文件的内容

## 统计当前目录下文件的个数（不包括目录）
```ls -l | grep "^-" | wc -l```
统计当前目录下文件的个数（包括子目录）
```ls -lR| grep "^-" | wc -l```
查看某目录下文件夹(目录)的个数（包括子目录）
```ls -lR | grep "^d" | wc -l```


#### less
针对比较大的文件，我们就可以使用less命令打开某个文件。

按n(N)向下(上)查找。

#### more 
查看文本文件命令

用空格向下翻页，用b向上翻页

>+行数直接从给定的行数开始显示
-s 将多个空行压缩成一个空行
-p 清除屏幕后再显示

####  nano 
文本编辑器
命令格式：nano [选项] [行号] 文件名

####  head 

查看文件头部
>-n <行数> 显示文件的最前指定的行
-c <字节数> 显示文件前N个字节数里的内容
-q 不输出文件头的内容
-v 输出文件头的内容

#### tail 
查看文件尾部

>-f 循环读取
-c <字节数> 显示文件前N个字节数里的内容
-q 不输出文件头的内容
-n <行数> 指定所显示的行数
-v 输出文件头的内容

```tail -f access.log```




#### ln 
生成链接文件

命令格式：ln [选项] 源文件 链接文件

>-f 删除已存在的目的文件
-i 如果碰到有重复名字的提示如何操作
-v 显示操作信息
-s 软链接选项

`ln -s redis-5.0.0 redis`



####  file 

查看文件的类型


#### stat 
显示文件或文件系统状态

命令格式：stat [选项] <文件/目录>

>-f:查看指定的文件系统

stat可查看：
文件名 2. 文件尺寸 3. I节点号 4. 创建时间/访问时间/状态(属 主、组、权限)修改时间 5.权限 6.链接文件个数 7. 属主及属组等

#### whereis
显示一个二进制文件、源码或man的位置

#### which
显示一个二进制文件或可执行文件的完整路径


#### find 
最常用的命令，命令的路径是作为第一个参数的， 基本命令格式为 find [path][option] [action] 。
```sudo find /etc -name sources.list```

>```find / -name file1``` 从 '/' 开始进入根文件系统搜索文件和目录
```find / -user user1 ```搜索属于用户 'user1' 的文件和目录
```find /usr/bin -type f -atime +100 ```搜索在过去100天内未被使用过的执行文件
```find /usr/bin -type f -mtime -10 ```搜索在10天内被创建或者修改过的文件
```find ~ -size +1500c ``` 查找大于1500字节的文件
```find ~ -size 1500c```查找等于1500字节的文件
```find ~ -size -1500c```查找小于1500字节的文件
```find ~ -size +512k```查找大于512k字节的文件
```find ~ -size -1G```查找小于1G字节的文件
```find ~ -size +10```查找大于10块的文件
```find / -empty```查找文件/目录字节为0的文件(即空文件)


#### grep
功能: 通过正则表达式查找文件中的关键字

>-i:忽略大小写
>-c:打印匹配的行数
>-C:打印出匹配的上下文(上N行,下N行)的多少行-l:列出匹配的文件名
>-L:列出不匹配的文件名
>-n：打印包含匹配项的行和行标
>-w:仅匹配指定的单词而非关键字
>-e:索引匹配字串
>-r:递归查询
>-v:不输出匹配的行
>-A <行号>:显示所找的匹配字段，并显示下面指定的行数的信息
>-B <行号>:显示所找的匹配字段，并显示上面指定的行数的信息

>```grep -rn --color POST access.log```查看nginx日志中的POST请求。
```grep -rn --color Exception -A10 -B2   error.log```看某个异常前后相关的内容
```grep ^Aug /var/log/messages``` 在文件 '/var/log/messages'中查找以"Aug"开始的词汇


#### 正则表达式表示方法:

>\\忽略正则表达式中特殊字符的原有含义
^ 匹配正则表达式的开始行
$ 匹配正则表达式的结束行
< 从匹配正则表达式的行开始
> 到匹配正则表达式的行结束
[ ] 单个字符；如[A] 即A符合要求
[ n - m ] 范围 ；如[A-H]即包含A至H都符合要求
. 所有的单个字符
\* 所有字符，长度可以为0

​	`cat redis.conf | grep -v "#" | grep -v "^$" > redis-6379.conf`去除注释、空格，导出到文档中

#### cut

打印每一行的某一字段


>-d “n”:定义分界符,即点位
-f 取第几位的字符
-c:仅显示行中指定范围的字符

打印/etc/passwd文件中以:为分隔符的第 1 个字段和第 6 个字段分别表示用户名和其家目录：
```cut /etc/passwd -d ':' -f 1,6```
 前五个（包含第五个）
```cut /etc/passwd -c -5```
前五个之后的（包含第五个）
```cut /etc/passwd -c 5-```

#### sort 
排序
-r反转
-t参数用于指定字段的分隔符
-k 字段号用于指定对哪一个字段进行排序
-n 按照数字排序,默认情况下是以字典序排序的
```cat /etc/passwd | sort -t':' -k 3 -n```


#### wc 
计数工具
行数
```wc -l /etc/passwd```
单词数
```wc -w /etc/passwd```
字节数
```wc -c /etc/passwd```
字符数
```wc -m /etc/passwd```
最长行字节数，西文字符来说，一个字符就是一个字节，但对于中文字符一个汉字是大于 2 个字节的，具体数目是由字符编码决定的
```wc -L /etc/passwd```

#### uniq
-c:在数据行前出现的次数
-d:只打印重复的行,重复的行只显示一次
-f:忽略行首的几个字段
-i:忽略大小写
-s:忽略行首的几个字母
-u:只打印唯一的行

用于过滤或者输出重复行。
因为uniq命令只能去连续重复的行，不是全文去重，所以要达到预期效果，我们先排序：

```history | cut -c 8- | cut -d ' ' -f 1 | sort | uniq```
使用频率前三的命令
```history |cut -c 8-|sort|uniq -dc|sort -rn -k1 |head -3```

#### 下载

```wget url```

```sudo apt-get install axel```

```axel -n 10 -o /tmp/ [http://testdownload.net/test.tar.gz](http://testdownload.net/test.tar.gz)```
-n 指定线程数
-o 指定另存为目录
-s 指定每秒的最大比特数
-q 静默模式



# 系统

## ssh

```text
ssh [选项] [参数]
```

### `命令选项`

- -1：强制使用ssh协议版本1；
- -2：强制使用ssh协议版本2；
- -4：强制使用IPv4地址；
- -6：强制使用IPv6地址；
- -A：开启认证代理连接转发功能；
- -a：关闭认证代理连接转发功能；
- -b：使用本机指定地址作为对应连接的源ip地址；
- -C：请求压缩所有数据；
- -F：指定ssh指令的配置文件；
- -f：后台执行ssh指令；
- -g：允许远程主机连接主机的转发端口；
- -i：指定身份文件；
- -l：指定连接远程服务器登录用户名；
- -N：不执行远程指令；
- -o：指定配置选项；
- -p：指定远程服务器上的端口；
- -q：静默模式；
- -X：开启X11转发功能；
- -x：关闭X11转发功能；
- -y：开启信任X11转发功能。

```bash
# 安装 OpenSSH Server
sudo apt-get install openssh-server
# SSH 默认连接到目标主机的 22 端口上-p 指定端口号为 23.
ssh -p 23 rumenz@test.com 
# 远程执行命令
ssh rumenz@test.com "ls -l"
# 生成SSH密钥和公钥
ssh-keygen -t rsa  # 最后在`~/.ssh`目录下会生成`id_rsa`(秘钥),`id_rsa.pub`(公钥)两个文件

# 拷贝本机的公钥到服务器
ssh-copy-id rumenz@test.com
#  输入远程用户的密码后，SSH公钥就会自动上传了．SSH公钥保存在远程Linux服务器的`~/.ssh/authorized_keys`文件中．
```



除了登陆外还有三种代理功能：

- 正向代理（-L）：相当于 iptable 的 port forwarding
- 反向代理（-R）：相当于 [frp](https://www.zhihu.com/search?q=frp&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A"57630633"}) 或者 ngrok
- socks5 代理（-D）：相当于 ss/ssr

`正向代理：`

所谓“正向代理”就是在本地启动端口，把本地端口数据转发到远端。

用法1：远程端口映射到其他机器

Host_local 上启动一个 Port_local 端口，映射到 Host_remote:Port_remote 上，在 Host_local 上运行：

```bash
Host_local$ ssh -L 0.0.0.0:Port_local:Host_remote:Port_remote user@Host_remote
```

这时访问 Host_local:Port_local 相当于访问 Host_remote:Port_remote（和 iptable 的 port-forwarding 类似）。

用法2：本地端口通过跳板映射到其他机器

Host_local 上启动一个 Port_local 端口，通过 HostB 转发到 Host_remote:Port_remote上，在 Host_local 上运行：

```bash
Host_local$ ssh -L 0.0.0.0:Port_local:Host_remote:Port_remote  user@HostB
```

这时访问 Host_local:Port_local 相当于访问 Host_remote:Port_remote。

两种用法的区别是，第一种用法本地到跳板机的数据是明文的，而第二种用法数据被 ssh 加密传输给 HostB 又转发给 Host_remote:Port_remote。


`反向代理：`

所谓“反向代理”就是让远端启动端口，把远端端口数据转发到本地。

Host_local 将自己可以访问的 HostB:PortB 暴露给外网服务器 Host_remote:Port_remote，在 Host_local 上运行：

```bash
Host_local$ ssh -R Host_remote:Port_remote:HostB:PortB  user@Host_remote
```

那么链接 Host_remote:Port_remote 就相当于链接 HostB:PortB。使用时需修改 Host_remote 的 /etc/ssh/sshd_config，添加：

```apacheconf
GatewayPorts yes
```

相当于内网穿透，比如 Host_local 和 HostB 是同一个内网下的两台可以互相访问的机器，Host_remote是外网跳板机，Host_remote不能访问 Host_local，但是 Host_local 可以访问 Host_remote。

那么通过在内网 Host_local 上运行 `ssh -R` 告诉 Host_remote，创建 Port_remote端口监听，把该端口所有数据转发给我（Host_local），我会再转发给同一个内网下的 HostB:PortB。

同内网下的 Host_local/HostB 也可以是同一台机器，换句话说就是`内网 Host_local 把自己可以访问的端口暴露给了外网 Host_remote。`

## 用户

```who am i```
查看用户
![](https://upload-images.jianshu.io/upload_images/18339009-f5fab39403516fcd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

输出的第一列表示打开当前伪终端的用户的用户名，第二列的 pts/0 中 pts 表示伪终端，所谓伪是相对于 /dev/tty 设备而言的，还记得上一节讲终端时的那七个使用 [Ctrl]+[Alt]+[F1]～[F7] 进行切换的 /dev/tty 设备么，这是“真终端”，伪终端就是当你在图形用户界面使用 /dev/tty7 时每打开一个终端就会产生一个伪终端，pts/0 后面那个数字就表示打开的伪终端序号，第三列则表示当前伪终端的启动时间。

sudo passwd lilei  设置用户lilei的密码
sudo adduser lilei 创建新用户lilei，默认为新用户在 /home 目录下创建一个工作目录
su -l lilei   切换到lilei用户
sudo deluser lilei --remove-home  删除用户

##  groups 
groups shiyanlou
![](https://upload-images.jianshu.io/upload_images/18339009-190c8e54549c1881.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其中冒号之前表示用户，后面表示该用户所属的用户组。这里可以看到 shiyanlou 用户属于 shiyanlou 用户组，每次新建用户如果不指定用户组的话，默认会自动创建一个与用户名相同的用户组
```cat /etc/group | grep -E "shiyanlou"```
查看自己属于哪些用户组
![](https://upload-images.jianshu.io/upload_images/18339009-51df186681c8e87f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
group_name:password:GID:user_list

``` sudo usermod -G sudo lilei```
将其它用户加入 sudo 用户组
以直接使用 root 用户为其它用户添加用户组，或者用其它已经在 sudo 用户组的用户使用 sudo 命令获取权限来执行该命令。






## 文件权限操作

```ls -l a.txt```

drwxr-xr-x  2 root root 4.0K  3月  23  2017  a.txt

>第1位：文件类型（d 目录，- 普通文件，l 链接文件）
r 可读权限，w可写权限，x可执行权限（也可以用二进制表示 111 110 100 --> 764）
第2-4位：所属用户权限，用u（user）表示
第5-7位：所属组权限，用g（group）表示
第8-10位：其他用户权限，用o（other）表示
第2-10位：表示所有的权限，用a（all）表示

2是纯数字 ，表示 文件链接个数  
第一个“root” 表示文件的所有者   
第二个“root” 表示为文件的所在群组   
“4.0K”，表示为文件长度（大小）  
“3月  23  2017”，表示文件最后更新（修改）时间  
“etc” 表示文件的名称

#### chown
改变文件的所属用户和所属组。
>-R 修改指定目录及其子目录


>```chown -R xjj:xjj a```修改a目录的用户和组为 xjj
```chown user1 file1``` 改变一个文件的所有人属性
```chown -R user1 directory1``` 改变一个目录的所有人属性并同时改变改目录下所有文件的属性
```chown user1:group1 file1 ```改变一个文件的所有人和群组属性
#### chmod 
改变文件的访问权限。

u表示当前用户  user（用户）
g表示同组用户group（用户组）
o表示其他用户others（其他用户）
a表示所有用户
\+ 和 - 分别表示增加和去掉相应的权限。
r表示可读
w表示可写
x表示可执行

用数字来表示权限（r=4，w=2，x=1，-=0）,
7：表示可读可写可执行，4+2+1,
6：表示可读可写，4+2
-R 修改指定目录及其子目录

>```chmod a+x a.sh  ```给a.sh文件增加执行权限（这个太常用了)
```chmod u+x file  ```给file的属主增加执行权限
```chmod 751 file      ```给file的属主分配读、写、执行(7)的权限，给file的所在组分配读、执行(5)的权限，给其他用户分配执行(1)的权限
```chmod u=rwx,g=rx,o=x file   ``` 上例的另一种形式
```chmod =r file         ```为所有用户分配读权限
```chmod 444 file    ``` 同上例
```chmod a-wx,a+r  file  ```同上例
```chmod -R u+r directory  ``` 递归地给directory目录下所有文件和子目录的属主分配读的权限
```chmod 4755 ``` 设置用ID，给属主分配读、写和执行权限，给组和其他用户分配读、执行的权限。


####df(report file system disk space usage)
显示文件系统磁盘空间的使用情况
>-h 以人类可读的方式显示，KD，Mb，GB等
#### du  (estimate file space usage)
显示指定的目录及其子目录已使用的磁盘空间的总和
>-s显示指定目录的总和
-a  显示目录中所有文件的大小。
-h以人类可读的方式显示，Kb，Mb，G8等
-d参数指定查看目录的深度 # 只查看1级目录的信息
 -d 0 ~
查看文件大小
```du -h file```
#### dd
转换和复制文件,也可以用在备份硬件的引导扇区、获取一定数量的随机数据或者空数据等任务中。

dd默认从标准输入中读取，并写入到标准输出中，但可以用选项if（input file，输入文件）和of（output file，输出文件）改变。

bs（block size）用于指定块大小（缺省单位为 Byte，也可为其指定如'K'，'M'，'G'等单位），
count用于指定块数量。
conv=ucase  将输出的英文字符转换为大写再写入文件

输出到test文件
```dd of=test bs=10 count=1 ```
输出到标准输出
```dd if=/dev/stdin of=/dev/stdout bs=10 count=1```









#### mount

-o 操作选项
-t 文件系统类型
-w|--rw|--ro权限

从/dev/zero设备创建一个容量为 256M 的空文件：
```dd if=/dev/zero of=virtual.img bs=1M count=256```
格式化磁盘：
```sudo mkfs.ext4 virtual.img ```
挂载我们创建的虚拟磁盘镜像到/mnt目录：
```mount -o loop -t ext4 virtual.img /mnt```

卸载已挂载磁盘
```sudo umount /mnt```



#### free 
显示当前内存和交换空间的使用情况




#### date
显示系统当前时间

#### top
显示当前系统中耗费资源最多的进程
查看某个进程中的线程状态
```top -H -p pid```

#### ps
较少单独使用，配参数根据需求，ps ef或者ps-aux
>-e 显示所有选程，环境变量
-f 全格式显示
-a 显示所有用户的所有进程（包括其它用户）
-u 按用户名和启动时间的顺序来显示进程
-x 显示无控制终端的进程
-l ：较长，较详细地将PID的信息列出

```ps aux```查看系统所有的进程数据
```ps ax ``` 查看不与terminal有关的所有进程
```ps -lA ``` 查看系统所有的进程数据
```ps axjf ``` 查看连同一部分进程树状态

`ps -ef | grep redis-`查看redis服务

#### kill
>kill -9 pid强制杀死一个进程

pkill -f "进程名"
kill -9 $(pidof 进程名关键字)

#### ifconfig 
网卡网络配置，常用于查看当前IP地址
ifconfig etho 192.168.12.22 修改系统IP（重品后失效）

#### ping 
ping baidu.com 测试网络的连通

#### hostname
查看主机名

#### 关机
>```sudo shutdown now```
```sudo reboot now```
```sudo shutdown -c ```取消关机   
```sudo shutdown -r ```重启
```sudo shutdown -f　```强行关闭应用程序
```sudo shutdown -l　```注销当前用户
```sudo shutdown -t``` 设置关机倒计时
```sudo shutdown -h hours:minutes ```按预定时间关闭系统



#### export
设定一些环境变量，env命令能看到当前系统中所有的环境变量。比如，下面设置的就是jdk的。
```export PATH=$PATH:/home/xjj/jdk/bin```
```source ```命令来让其立即生效
set	显示当前 Shell 所有变量，包括`其内建环境变量，用户自定义变量及导出的环境变量。`
env	显示与当前用户相关的环境变量，还可以让命令在指定环境中运行。
export	显示从 Shell 中导出成环境变量的变量，也能通过它将自定义变量导出为环境变量。

/etc/bashrc（有的 Linux 没有这个文件） 和 /etc/profile ，它们分别存放的是 shell 变量和环境变量。
```unset ```
命令删除一个环境变量

# 压、解缩
#### gzip 
>压缩文件成者文件夹
-d解压缩文件
```gzip file1 ```压缩一个叫做 'file1'的文件
```gzip -9 file1 ```最大程度压缩

#### zip
>-r 参数表示递归打包包含子目录的全部内容，9 压缩级别最大1 最小, 
-q 参数表示为安静模式，即不向屏幕输出信息，
-o，表示输出文件,
-l 参数将 LF 转换为 CR+LF 来达到win/linux兼容。

>``` zip -r  -9 -q -o -l shiyanlou.zip /home/shiyanlou/Desktop```将目录 /home/shiyanlou/Desktop 打包成一个文件
```zip file1.zip file1 ```创建一个zip格式的压缩包
```zip -r file1.zip file1 file2 dir1``` 将几个文件和目录同时压缩成一个zip格式的压缩包

#### unzip
>-q 使用安静模式
-d指定路径
-l 不解压只想查看压缩包的内容
-O（英文字母，大写 o）参数指定编码类型：(win默认会采用 GBK , Linux 默认 UTF-8 )
unzip -O GBK 中文压缩文件.zip

``` unzip -q shiyanlou.zip -d ziptest```将文件解压到指定目录：

#### tar 
>-P 保留绝对路径符
>-j   bzip2进行压缩
>-z  gzip进行压缩
>-v压缩的过程中显示文件
>-c 表示创建一个 tar 包文件
>-f 用于指定创建的文件名，件名必须紧跟在 -f 参数之后
>-p在其他主机还原时希望保留文件的属性
>-h 备份链接指向的
>
>源文件而不是链接本身
>-x解包一个文件
>-C 指定保存路径
>-t只查看不解包文件 

```tar -jcv -f filename.tar.bz2``` 压缩
```tar -jtv -f filename.tar.bz2``` 查询
```tar -jxv -f filename.tar.bz2 -C dirname```解压



#### rar
rar a file1.rar test_file 创建一个叫做 'file1.rar' 的包
rar a file1.rar file1 file2 dir1 同时压缩 'file1', 'file2' 以及目录 'dir1'
rar x file1.rar 解压rar包




# VIM
![](https://upload-images.jianshu.io/upload_images/18339009-de0ac15d12403184.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
修改文本
![](https://upload-images.jianshu.io/upload_images/18339009-b6f40fe5095da55e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 定位命令
![image](https://upload-images.jianshu.io/upload_images/18339009-40f8d2a91c245485?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
替换和取消命令
![](https://upload-images.jianshu.io/upload_images/18339009-3192c0b29770869f?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 删除命令
![](https://upload-images.jianshu.io/upload_images/18339009-52358b7bd810c2b8?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
常用快捷键
![](https://upload-images.jianshu.io/upload_images/18339009-c8dc68e3552b4958?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### alias 
```alias a='find . -size +10M -type f -print0 | xargs -0 ls -Ssh | sort -z'```


 #### 关闭防火墙
systemctl stop firewalld.service
 #### 禁止防火墙开机启动
systemctl disable firewalld.service
 #### 关闭SELinux
nano -w /etc/selinux/config
将SELINUX=enforcing改为SELINUX=disabled保存并退出:^x->y->回车


# 计划任务

查看添加了哪些任务```crontab -l```
添加一个计划任务```crontab -e```
删除任务```crontab -r```
```分 小时 天 月 星期 user-name command to be executed```

启动 rsyslog，以便我们可以通过日志中的信息来了解我们的任务是否真正的被执行了
```sudo apt-get install -y rsyslog```
```sudo service rsyslog start```( Ubuntu 会默认自行启动不需要手动启动)
```sudo cron －f &```(实验环境中 crontab 也是不被默认启动的，同时不能在后台由 upstart 来管理，所以需要我们来启动它)


每分钟我们会在/home/shiyanlou 目录下创建一个以当前的年月日时分秒为名字的空白文件
```*/1 * * * * touch /home/shiyanlou/$(date +\%Y\%m\%d\%H\%M\%S)```
>“ % ” 在 crontab 文件中，有结束命令行、换行、重定向的作用，前面加 ” \ ” 符号转义，否则，“ % ” 符号将执行其结束命令行或者换行的作用，并且其后的内容会被做为标准输入发送给前面的命令。

查看到执行任务命令之后在日志中的信息反馈
```sudo tail -f /var/log/syslog```


#### 有选择的执行命令

```which cowsay>/dev/null && echo "exist" || echo "not exist"```
```&&就是用来实现选择性执行的，它表示如果前面的命令执行结果（不是表示终端输出的内容，而是表示命令执行状态的结果）返回 0 则执行后面的，否则不执行，你可以从$?环境变量获取上一次命令的返回结果```
```||在这里就是与&&相反的控制效果，当上一条命令执行结果为 ≠0($?≠0)时则执行它后面的命令```
echo $?


#### 修改root密码
ubuntu中修改root账号的密码命令：```sudo passwd root```

# 修改内核启动顺序

```shell
显示内核的启动顺序
grep menuentry /boot/grub/grub.cfg
修改内核配置文件
sudo vim /etc/default/grub

GRUB_DEFAULT=0 改为 GRUB_DEFAULT=6或者改为GRUB_DEFAULT=”Ubuntu，Linux 4.4.0-21-generic“ 报错根据提示信息修改

更新
sudo update-grub
```
# 清除旧版本内核

```shell
查看当前使用内核版本号
uname -a
查看所有的版本
sudo dpkg --get-selections | grep linux
删除
sudo apt-get purge linux-headers-4.15.0-74 linux-headers-4.15.0-74-generic linux-image-4.15.0-74-generic linux-modules-4.15.0-74-generic linux-modules-extra-4.15.0-74-generic

标记deinstall的内核文件进行删除
sudo dpkg -P linux-image-4.4.0-138-generic

更新引导
sudo update-grub
```




