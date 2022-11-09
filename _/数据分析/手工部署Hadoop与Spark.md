- [ 一、制定部署规划。](#head1)
- [ 二、准备机器。](#head2)
	- [ 1.修改机器名](#head3)
	- [ 2.关闭防火墙](#head4)
	- [ 5.关闭桌面](#head5)
- [ 三、配置集群环境。](#head6)
	- [ 1.为每台机器添加整个集群的域名映射](#head7)
- [ 编译、运行或者打包Java程序。](#head8)
- [ 四、下载组件。](#head9)
- [ 五、部署前置组件。](#head10)
- [ 六、部署组件。](#head11)
- [ 七、启动HDFS](#head12)
	- [ 统一启动](#head13)
- [ 八、测试HDFS](#head14)
![](https://upload-images.jianshu.io/upload_images/18339009-1cc2290f6130911d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Hadoop与Spark之间区别联系类似WPS与Word，即Hadoop与Spark功能相同，一个性能较高、另一个性能较普通而已。

![](https://upload-images.jianshu.io/upload_images/18339009-4f2cb43916fe2838.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-d78635d0b18699f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可按如下步骤手工部署：

# <span id="head1"> 一、制定部署规划。</span>
![](https://upload-images.jianshu.io/upload_images/18339009-0520aee60761c7bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
以下均可：一是云创云端容器云；二是四台实体机；三是在一台高配物理机上使用VMware虚拟出四台虚拟机；四是在一台中配置机上使用Docker虚拟出四台虚拟机。

# <span id="head2"> 二、准备机器。</span>
##### <span id="head3"> 1.修改机器名</span>
- ```su - root ``` 
切换成root用户修改机器名
- ```hostnamectl set-hostname slave1``` 
编辑存储机器名文件(CentOS7.x版本命令)
- ```vim /etc/sysconfig/network```     
编辑存储机器名文件(CentOS6.x版本命令)
	将```“HOSTNAME=localhost.localdomain”```中```“localhost.localdomain”```替换成```slave1```。
**注意一是重启机器后更名操作才会生效，二是必须以root权限执行（即要么以allen用户登录切换成root，要么直接root用户登录）。**
##### <span id="head4"> 2.关闭防火墙</span>
默认情况下CentOS防火墙iptables会阻断进程间(不同机器)通信，用户可永久关闭防火墙或设置防火墙开放Hadoop相关端口，为简单起见，使用永久关闭命令。
- ```systemctl disable firewalld ```
永久关闭iptables，重启后生效(CentOS7.x版本命令)
```chkconfig –level 35 iptables off```
永久关闭iptables，重启后生效(CentOS6.x版本命令)
**注意一是重启机器后防火墙才会真的关闭，二是必须以root权限执行。**
##### 3.安装JDK
Hadoop运行时需1.7及以上的Oracle版JDK环境支撑，选用当前最稳定版“jdk-8u161-linux-x64.rpm”，请读者自行下载并拷贝至CentOS的某目录，如以下假定jdk安装文件位于CentOS机的“/root/tools/jdk-8u161-linux-x64.rpm”。
- ```java  -version ``` 
查看java是否安装
- ```rpm  -ivh  /root/tools/jdk-8u161-linux-x64.rpm  ```
以root权限,rpm方式安装
- ```java  -version ```  
验证java是否安装成功
**安装后无需重启立刻生效，不过必须以root权限执行。**

##### 4.添加域名映射
- ```ifconfig ```
查看slave1机器IP地址
首先使用上述命令查看本机当前IP。假如命令输出该机IP地址为“192.168.1.100”，又知该机主机名为slave1，则域名映射应为：```192.168.1.100  slave1```
- 接着编辑域名映射文件“/etc/hosts”，将上述内容追加到文件末尾，注意是追加，切不可删除原有内容。
```vim /etc/hosts ```
编辑域名映射文件
**注意一是无需重启立刻生效，二是必须root权限执行，三是追加，不可删除原内容。**

##### <span id="head5"> 5.关闭桌面</span>
```systemctl  set-default  multi-user.target```
关闭桌面
**注意一是非必须(仅为节省内存)，二是必须root权限执行，三是通过“startx”命令可从命令行进入桌面。**

#####6.重启生效
```reboot ```
不管三七二一，重启生效一切操作
重启好机器后，可通过如下命令逐一收获结果：
```hostname```
查看本机主机名,结果应为slave1
```systemctl status firewalld```
查看防火墙状态,结果应有incative字样
```java  -version```
查看jdk版本,结果应有1.8.0_161
```ping  slave1```
查看域名映射,结果应可以ping的通
# <span id="head6"> 三、配置集群环境。</span>
指的是对集群中的每台机器，设置其相应的“主机名”、“关闭每台机器的防火墙”、“为每台机器安装JDK”、“为每台机器添加整个集群的域名映射”。显然，除“为每台机器添加整个集群的域名映射”外，其余三项设置实质上就是前面的任务，即对新创建的五台CentOS机器，逐一执行。
##### <span id="head7"> 1.为每台机器添加整个集群的域名映射</span>
- 依次在集群各(此处为五台)机器上执行如下命令查看IP地址，并同时记录每台机器主机名。
```ifconfig```
查看master、slave机器IP地址
按如下格式汇总各机IP与主机名(假定如下即为这五台机器对应的IP地址)：
```
192.168.1.100  master 
192.168.1.101  slave1 
192.168.1.102  slave2 
192.168.1.102  slave3
192.168.1.102  slave4
```
- 将上述(全部)内容追到到每台机器的“/etc/hosts”文件里。
```vim /etc/hosts ```                         
#编辑master、 slave的域名映射文件
**注意一是无需重启立刻生效；二是必须root权限执行；三是追加，不可删除原内容；四是集群中每台机器都要添加。**
- 完成上述操作后，即完成“集群四项设置”。其中前三项设置各机可自行验证，验证方法同实验三；第四项“为每台机器添加整个集群的域名映射”验证方法需小改如下：
```ping master``` 
在slave2上ping机器master、slave1、slave2、slave3、slave4
以上以slave2机为例，若slave2可ping通集群内所有(包括自身)机器，则slave2机“添加整个集群的域名映射”设置成功。若集群内所有机均可ping通自身及其它机器，则“为每台机器添加整个集群的域名映射”设置成功



##### 2.配置SSH免密登录
Hadoop的基础是分布式文件系统HDFS，HDFS集群有两类节点以管理者-工作者的模式运行，即一个namenode（管理者）和多个datanode（工作者）。在Hadoop启动以后，namenode通过SSH来启动和停止各个节点上的各种守护进程，这就需要在这些节点之间执行指令时采用无需输入密码的认证方式，因此，我们需要将SSH配置成使用无需输入root密码的密钥文件认证方式。
- ```ssh-keygen```
root用户,master机,生成公私钥，生成master服务器密钥。

- ```ssh-copy-id master```
将master服务器公钥拷贝至master服务器本身。

- 公钥拷贝完成后，可以在master服务器上直接执行命令```ssh master```
查看是否可以免密登录master服务器：
```ssh master```
root用户,登录本机网络地址
```exit```
退出本次登录
```ssh-copy-id slave1```
将master服务器公钥拷贝至slave1服务器。

- 公钥拷贝完成后，可以在master服务器上直接执行命令```ssh master```
查看是否可以免密登录slave1~2服务器：
```ssh  localhost```
root用户,登录本机环回地址
```ssh  slave1```
root用户,从master远程登录slave1、2
- 其余服务器按照同样的方式配置ssh免密登录，完成后验证是否可以互相之间实现SSH免密登录。

# <span id="head8"> 编译、运行或者打包Java程序。</span>
使用vi编辑器编写HelloWorld.java：
```
public class HelloWorld {
    public static void main(String args[]) {
        System.out.println("Hello World!");
    }
}
```
- 编译Java程序
```javac HelloWorld.java ```
使用javac命令编译该程序，生成HelloWorld.class文件
- 运行Java程序
```java HelloWorld``` 
使用java命令执行该程序，输出Hello World!
- 打包Java程序
```jar -cvf HelloWorld.jar HelloWorld.class ```
由于打包时并没有指定manifest文件，因此该jar包无法直接运行：
```java -jar HelloWorld.jar ```
- 如果出错，修改META-INF文件
（a）```jar xvf HelloWorld.jar``` 
解压jar包 生成META-INF文件夹，
（b）```vim MANIFEST.MF```
在第3行加上 Main-Class: HelloWorld 
- 打包携带manifest文件的Java程序
打包时，加入-m参数，并指定manifest文件名：
```jar -cvfm HelloWorld.jar META-INF/MANIFEST.MF HelloWorld.class ```
之后，即可使用java命令直接运行该jar包：
```java -jar HelloWorld.jar ```



# <span id="head9"> 四、下载组件。</span>
下载Hadoop。请至http://archive.apache.org/dist/主页下载hadoop-2.7.4.tar.gz并以root用户拷贝至各机“/root/tools/hadoop-2.7.4.tar.gz”。。
# <span id="head10"> 五、部署前置组件。</span>
![](https://upload-images.jianshu.io/upload_images/18339009-b0663a9090197f4e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
所谓的“部署前置组件”指的是部署本组件的依赖组件，如部署Hive前需提前部署Hadoop；部署Spark前需提前部署HDFS（当指定Spark使用HDFS时）。

# <span id="head11"> 六、部署组件。</span>
解压Hadoop（五台机器均需解压Hadoop）；配置HDFS（五台均配置 hadoop-env.sh ,core-site.xml）；格式化主节点（仅安装时主节点执行一次，其它情况均不执行）。
- 解压Hadoop
以root用户登录各机，在root的默认HOME目录（即/root）下解压Hadoop。
```tar  -zxvf  /root/tools/hadoop-2.7.4.tar.gz  ```
master、slave上root用户解压Hadoop
- 配置HDFS
配置hadoop-env.sh，编辑文件
``` vim /root/hadoop-2.7.4/etc/hadoop/hadoop-env.sh```
找到如下一行：
```export  JAVA_HOME=${JAVA_HOME}```
将这行内容修改为：
```export  JAVA_HOME=/usr/java/jdk1.8.0_161```
这里的“/usr/java/jdk1.8.0_161”即为JDK安装位置，如有不同，请读者务必根据实际情况更改。
**注意一是以root用户登录；二是五台机器均需执行；三是务必根据实际填写正确的JDK目录。**
- 配置core-site.xml
编辑各机文件
```vim /root/hadoop-2.7.4/etc/hadoop/core-site.xml```
将如下内容嵌入此文件的configuration标签间（即<configuration>与</configuration>间）。
```
<property><name>hadoop.tmp.dir</name><value>/root/hadoopdata</value></property>
<property><name>fs.defaultFS</name><value>hdfs://master:8020</value></property>
```
**注意一是以root用户登录；二是五台机器均需执行；三是仅在<configuration>与</configuration>间添加上述内容，切不可删除configuration外原有内容。**




- 格式化主节点
在master机上，执行下述命令，完成格式化HDFS主服务元数据存储区操作：
```/root/hadoop-2.7.4/bin/hdfs  namenode  -format```
格式化主节点命名空间
**注意一是以root用户操作；二是仅master机执行；三是仅安装时主节点执行一次，其它情况均不执行。**


# <span id="head12"> 七、启动HDFS</span>
总原则为“主节点启动主服务、从节点启动从服务”
>实际操作命令见如下两步骤。
>- 在master机上使用下述命令启动HDFS主服务namenode：
```/root/hadoop-2.7.4/sbin/hadoop-daemon.sh start namenode```
master启动主服务
>- 在slave1、slave2、slave3、slave4上使用下述命令启动HDFS从服务datanode 
```/root/hadoop-2.7.4/sbin/hadoop-daemon.sh start datanode ```
slave1、2、3启动从服务
**注意一是以root用户操作；二是master机仅启动namenode；三是所有slave都需启动datanode；四是使用的脚本名为“hadoop-daemon.sh”，而不是“hadoop-daemons.sh” 。**
#### <span id="head13"> 统一启动</span>
- 修改slave文件
```vim /root/hadoop-2.7.4/etc/hadoop/slaves```
添加slave1、slave2，Localhost不删，把master也作为slave
- 统一启动命令
```/root/hadoop-2.7.4/sbin/start-dfs.sh```
# <span id="head14"> 八、测试HDFS</span>
- 方法一 Web页面测试
任选集群内某机，打开该机默认浏览器Firefox并输入如下地址，即可进入HDFS的Web统计界面。
```http://master:50070 ```
HDFS web页面网址
若读者是在“您的终端电脑”上打开该网址，显然“您的终端电脑”必须能将主机名master映射成该机IP。此时请读者务必根据实际情况修改如下内容并将其追加到“您的终端电脑”的“C:\Windows\system32\drivers\etc\hosts”文件里，注意是追加不是覆盖。
```
192.168.1.100  master
192.168.1.101  slave1
```
- 方法二 JPS进程测试
请在五台机器上分别执行下述命令(两命令功能相同，择一即可)，查看HDFS进程：
``` /usr/java/jdk1.8.0_161/bin/jps```
jps命令查看java进程
```ps –ef | grep java```
ps命令查看java进程
master机可看到类似如下信息(进程号，进程名)：
```
2347 NameNode                                       #分布式存储HDFS集群主服务
slave1、slave2、slave3、slave4机可看到类似如下信息(进程号，进程名)：
4021 DataNode                                        #分布式存储HDFS集群从服务
```
>[no datanode to stop](http://blog.sina.com.cn/s/blog_6d932f2a0101fsxn.html)

- 方法三 上传文件测试
显然，最稳妥的测试方式是将某文件“放”入HDFS集群，已确定该集群可存储文件，如下步骤即实现该测试：
Step1 确定HDFS集群当前无文件
浏览器依次进入http://master:50070 >“左上角”>“Brose the file system”，显然，当前HDFS集群无任何文件与文件夹。
Step2 使用如下命令在HDFS里新建“/in”目录
``` /root/hadoop-2.7.4/bin/hdfs  dfs  -mkdir  /in  ```
在HDFS存储区新建in目录
请注意该命令虽在master机shell环境下执行，但其不是在master机根目录下新建的文件夹“in”，而是在HDFS存储区的根目录下新建的“in”目录。命令执行后，请读者再次执行Step1，从HDFS Web页面查看HDFS存储区是否出现“in”目录。
Step3 上传文件至HDFS里的“/in”目录
```/root/hadoop-2.7.4/bin/hdfs  dfs  -put  / root/hadoop-2.7.4/etc/hadoop/*   /in```
上述命令将master机本地文件夹“/root/hadoop-2.7.4/etc/hadoop/”下所有文件拷贝至HDFS集群的“/in”目录下。命令执行后，请读者按照Step1定位到HDFS Web页面“in”目录下，查看文件。
# 九、关闭该HDFS集群
总原则为“主节点关闭主服务、从节点关闭从服务”，实际操作命令参见如下两步骤。此外，本步骤最简单的验证方法为关闭前浏览器可打开“http://master:50070”，关闭后浏览器打不开“http://master:50070”。
-  在master机上使用下述命令关闭HDFS主服务namenode：
``` /root/hadoop-2.7.4/sbin/hadoop-daemon.sh stop namenode```
master关闭主服务
-  在slave1、slave2、slave3、slave4上使用下述命令关闭HDFS从服务datanode 
``` /root/hadoop-2.7.4/sbin/hadoop-daemon.sh stop datanode```
slave1、2、3关闭从服务
注意一是以root用户操作；二是master机仅关闭namenode；三是所有slave都需关闭datanode；四是使用的脚本名为“hadoop-daemon.sh”，而不是“hadoop-daemons.sh” 。


