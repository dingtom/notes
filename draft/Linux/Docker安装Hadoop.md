Docker、Java、Scala、Hadoop、 Hbase、Spark。
集群共有5台机器，主机名分别为 h01、h02、h03、h04、h05。其中 h01 为 master，其他的为 slave。
JDK 1.8、Scala 2.11.6、Hadoop 3.2.1、Hbase 2.1.3、Spark 2.4.0

# Ubuntu 安装 Docker
## 在 Ubuntu 下安装 Docker 的时候需在管理员的账号下操作。
```wget -qO- https://get.docker.com/ | sh```
## 以 sudo 启动 Docker 服务。
```sudo service docker start```
## 显示 Docker 中所有正在运行的容器
```sudo docker ps``` 
现在的 Docker 网络能够提供 DNS 解析功能，使用如下命令为接下来的 Hadoop 集群单独构建一个虚拟的网络。
```sudo docker network create --driver=bridge hadoop```
以上命令创建了一个名为 Hadoop 的虚拟桥接网络，该虚拟网络内部提供了自动的DNS解析服务。## 查看 Docker 中的网络
```sudo docker network ls```
可以看到刚刚创建的名为 hadoop 的虚拟桥接网络。
## 查找 ubuntu 容器
```sudo docker search ubuntu```
## 下载 ubuntu 16.04 版本的镜像文件
```sudo docker pull ubuntu:16.04```
## 根据镜像启动一个容器
```sudo docker run -it ubuntu:16.04 /bin/bash```
可以看出 shell 已经是容器的 shell 了
## 退出容器
``` exit```
## 查看本机上所有的容器
```sudo docker ps -a```
## 启动容器
```sudo docker start fab4da838c2f```
## 关闭容器
```sudo docker stop  fab4da838c2f```


# 安装 Java 与 Scala

在当前容器中将配置配好，导入出为镜像。以此镜像为基础创建五个容器，并赋予 hostname
进入 h01 容器，启动 Hadoop。

## 进入 Ubuntu 容器
```sudo docker exec -it fab4da838c2f /bin/bash```
## 修改 apt 源
备份源
```cp /etc/apt/sources.list /etc/apt/sources_init.list```
先删除旧源文件
```rm /etc/apt/sources.list```
这个时候没有 vim 工具，使用 echo 命令将源写入新文件
```
echo "deb http://mirrors.aliyun.com/ubuntu/ xenial main
deb-src http://mirrors.aliyun.com/ubuntu/ xenial main
deb http://mirrors.aliyun.com/ubuntu/ xenial-updates main
deb-src http://mirrors.aliyun.com/ubuntu/ xenial-updates main
deb http://mirrors.aliyun.com/ubuntu/ xenial universe
deb-src http://mirrors.aliyun.com/ubuntu/ xenial universe
deb http://mirrors.aliyun.com/ubuntu/ xenial-updates universe
deb-src http://mirrors.aliyun.com/ubuntu/ xenial-updates universe
deb http://mirrors.aliyun.com/ubuntu/ xenial-security main
deb-src http://mirrors.aliyun.com/ubuntu/ xenial-security main
deb http://mirrors.aliyun.com/ubuntu/ xenial-security universe
deb-src http://mirrors.aliyun.com/ubuntu/ xenial-security universe"
> /etc/apt/sources.list
```
## 更新源
``` apt update```

## 安装 jdk 1.8
```apt install openjdk-8-jdk```
测试
``` java -version```

## 安装 Scala
``` apt install scala```
测试
```scala```

## 安装 Vim 与 网络工具包
安装 vim，用来编辑文件
```apt install vim```
安装 net-tools
```apt install net-tools```

## 安装 SSH
安装 SSH，并配置免密登录，由于后面的容器之间是由一个镜像启动的，所以在当前容器里配置 SSH 自身免密登录就 OK 了。

安装 SSH
```apt-get install openssh-server```
安装 SSH 的客户端
```apt-get install openssh-client```
进入当前用户的用户根目录
```cd ~```
生成密钥，一直回车就行
``` ssh-keygen -t rsa -P ""```
生成的密钥在当前用户根目录下的 .ssh 文件夹中以 . 开头的文件与文件夹 ls 是看不懂的，需要``` ls -al``` 才能查看。

将公钥追加到 authorized_keys 文件中
``` cat .ssh/id_rsa.pub >> .ssh/authorized_keys```
启动 SSH 服务
``` service ssh start```
免密登录自己
``` ssh 127.0.0.1```
修改 .bashrc 文件，启动 shell 的时候，自动启动 SSH 服务
``` vim ~/.bashrc```
添加一行
```service ssh start```

# 安装 Hadoop
## 下载 Hadoop 
```wget http://mirrors.hust.edu.cn/apache/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz```
## 解压
到 /usr/local 目录下面并重命名文件夹
```tar -zxvf hadoop-3.2.1.tar.gz -C /usr/local/```
```cd /usr/local/```
```mv hadoop-3.2.1 hadoop``` 
##  添加环境变量
```vim /etc/profile```
追加以下内容，JAVA_HOME 为 JDK 安装路径，使用 apt 安装就是这个，用 ```update-alternatives --config java ```可查看
```
#java
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export JRE_HOME=${JAVA_HOME}/jre    
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib    
export PATH=${JAVA_HOME}/bin:$PATH
#hadoop
export HADOOP_HOME=/usr/local/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
export HADOOP_COMMON_HOME=$HADOOP_HOME 
export HADOOP_HDFS_HOME=$HADOOP_HOME 
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_YARN_HOME=$HADOOP_HOME 
export HADOOP_INSTALL=$HADOOP_HOME 
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native 
export HADOOP_CONF_DIR=$HADOOP_HOME 
export HADOOP_LIBEXEC_DIR=$HADOOP_HOME/libexec 
export JAVA_LIBRARY_PATH=$HADOOP_HOME/lib/native:$JAVA_LIBRARY_PATH
export HADOOP_CONF_DIR=$HADOOP_PREFIX/etc/hadoop
export HDFS_DATANODE_USER=root
export HDFS_DATANODE_SECURE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export HDFS_NAMENODE_USER=root
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root
```
使环境变量生效
```source /etc/profile```
## 修改配置文件
在目录 /usr/local/hadoop/etc/hadoop 下，修改 hadoop-env.sh 文件，在文件末尾添加以下信息
```cd /usr/local/hadoop/etc/hadoop```
```vim hadoop-env.sh```
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export HDFS_NAMENODE_USER=root
export HDFS_DATANODE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root
```
修改 core-site.xml，修改为
```vim core-site.xml```
```
<configuration>
    <property>
        <name>fs.default.name</name>
        <value>hdfs://h01:9000</value>
    </property>
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/hadoop3/hadoop/tmp</value>
    </property>
</configuration>
```
```chmod 777 /home/hadoop3/hadoop/tmp```
修改 hdfs-site.xml，修改为
```vim hdfs-site.xml```
```
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>2</value>
    </property>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>/home/hadoop3/hadoop/hdfs/name</value>
    </property>
    <property>
        <name>dfs.namenode.data.dir</name>
        <value>/home/hadoop3/hadoop/hdfs/data</value>
    </property>
</configuration>
```
修改 mapred-site.xml，修改为
```vim mapred-site.xml```
```
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
    <property>
        <name>mapreduce.application.classpath</name>
        <value>
            /usr/local/hadoop/etc/hadoop,
            /usr/local/hadoop/share/hadoop/common/*,
            /usr/local/hadoop/share/hadoop/common/lib/*,
            /usr/local/hadoop/share/hadoop/hdfs/*,
            /usr/local/hadoop/share/hadoop/hdfs/lib/*,
            /usr/local/hadoop/share/hadoop/mapreduce/*,
            /usr/local/hadoop/share/hadoop/mapreduce/lib/*,
            /usr/local/hadoop/share/hadoop/yarn/*,
            /usr/local/hadoop/share/hadoop/yarn/lib/*
        </value>
    </property>
</configuration>
```
修改 yarn-site.xml，修改为
```vim yarn-site.xml```
```
<configuration>
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>h01</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
</configuration>
```
修改 worker 为
```vim workers```
```
h01
h02
h03
h04
h05
```
此时，hadoop已经配置好了

## 在 Docker 中启动集群
### 将当前容器导出为镜像
```exit```
```sudo docker commit -m "hadoop" -a "tomding" fab4da838c2f newuhadoop```
查看镜像
```sudo docker images```
### 启动 5 个终端
启动 h01 做 master 节点，所以暴露了端口，以供访问 web 页面，--network hadoop 参数是将当前容器加入到名为 hadoop 的虚拟桥接网络中，此网站提供自动的 DNS 解析功能
```sudo docker run -it --network hadoop -h "h01" --name "h01" -p 9870:9870 -p 8088:8088 newuhadoop /bin/bash```
```sudo docker run -it --network hadoop -h "h02" --name "h02" newuhadoop /bin/bash```
```sudo docker run -it --network hadoop -h "h03" --name "h03" newuhadoop /bin/bash```
```sudo docker run -it --network hadoop -h "h04" --name "h04" newuhadoop /bin/bash```
```sudo docker run -it --network hadoop -h "h05" --name "h05" newuhadoop /bin/bash```
### 在 h01 主机中，启动 Haddop 集群
先进行格式化操作，不格式化操作，hdfs会起不来
```cd /usr/local/hadoop/bin```
```./hadoop namenode -format```
进入 hadoop 的 sbin 目录
```cd /usr/local/hadoop/sbin/```
启动
``` ./start-all.sh```
查看分布式文件系统的状态
```cd /usr/local/hadoop/bin```
```./hadoop dfsadmin -report```

## 运行内置WordCount例子
把license作为需要统计的文件
```cd /usr/local/hadoop```
```cat LICENSE.txt > file1.txt```
在 HDFS 中创建 input 文件夹
```cd /usr/local/hadoop/bin```
``` ./hadoop fs -mkdir /input```
上传 file1.txt 文件到 HDFS 中
```./hadoop fs -put ../file1.txt /input```
查看 HDFS 中 input 文件夹里的内容
``` ./hadoop fs -ls /input```
运作 wordcount 例子程序
```./hadoop jar ../share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar wordcount /input /output```
查看 HDFS 中的 /output 文件夹的内容
``` ./hadoop fs -ls /output```
查看 part-r-00000 文件的内容
```./hadoop fs -cat /output/part-r-00000```
Hadoop 部分结束了

# 安装 Hbase
在 Hadoop 集群的基础上安装 Hbase
## 下载 Hbase 2.1.3
root@h01:~# ```wget http://archive.apache.org/dist/hbase/2.1.3/hbase-2.1.3-bin.tar.gz```
## 解压
到 /usr/local 目录下面
``` tar -zxvf hbase-2.1.3-bin.tar.gz -C /usr/local/```
## 修改环境变量文件
```vim /etc/profile```
```
export HBASE_HOME=/usr/local/hbase-2.1.3
export PATH=$PATH:$HBASE_HOME/bin
```
```source /etc/profile```
使用 ssh h02/3/4/5 进入其他四个容器，依次在 /etc/profile 文件后追加那两行环境变量

在目录 /usr/local/hbase-2.1.3/conf 修改配置
```cd /usr/local/hbase-2.1.3/conf```
修改 hbase-env.sh，追加
```vim hbase-env.sh```
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export HBASE_MANAGES_ZK=true
```
## 修改配置文件
修改 hbase-site.xml 为
```vim hbase-site.xml```

```
<configuration>
        <property>
                <name>hbase.rootdir</name>
                <value>hdfs://h01:9000/hbase</value>
        </property>
        <property>
                <name>hbase.cluster.distributed</name>
                <value>true</value>
        </property>
        <property>
                <name>hbase.master</name>
                <value>h01:60000</value>
        </property>
        <property>
                <name>hbase.zookeeper.quorum</name>
                <value>h01,h02,h03,h04,h05</value>
        </property>
        <property>
                <name>hbase.zookeeper.property.dataDir</name>
                <value>/home/hadoop/zoodata</value>
        </property>
</configuration>
```
修改 regionservers 文件为
```vim regionservers```
```
h01
h02
h03
h04
h05
```
使用 scp 命令将配置好的 Hbase 复制到其他 4 个容器中
```scp -r /usr/local/hbase-2.1.3 root@h02:/usr/local/```
```scp -r /usr/local/hbase-2.1.3 root@h03:/usr/local/```
```scp -r /usr/local/hbase-2.1.3 root@h04:/usr/local/```
``` scp -r /usr/local/hbase-2.1.3 root@h05:/usr/local/```
## 启动 Hbase
```cd /usr/local/hbase-2.1.3/bin```
 ```./start-hbase.sh ```
打开 Hbase 的 shell
```hbase shell```

# 安装 Spark
在 Hadoop 的基础上安装 Spark

## 下载 Spark 2.4.0
```wget https://archive.apache.org/dist/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz```

## 解压
到 /usr/local 目录下面
``` tar -zxvf spark-2.4.0-bin-hadoop2.7.tgz  -C /usr/local/```
修改文件夹的名字
``` cd /usr/local/```
``` mv spark-2.4.0-bin-hadoop2.7 spark-2.4.0```
## 修改 环境变量
```vim /etc/profile```
```
export SPARK_HOME=/usr/local/spark-2.4.0
export PATH=$PATH:$SPARK_HOME/bin
```
```source /etc/profile```

使用 ssh h02/3/4/5 可进入其他四个容器，依次在 /etc/profile 文件后追加那两行环境变量

在目录 /usr/local/spark-2.4.0/conf 修改配置
```cd /usr/local/spark-2.4.0/conf ```
修改文件名
``` mv spark-env.sh.template spark-env.sh```
修改 spark-env.sh，追加
```vim spark-env.sh```
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export HADOOP_HOME=/usr/local/hadoop
export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop
export SCALA_HOME=/usr/share/scala

export SPARK_MASTER_HOST=h01
export SPARK_MASTER_IP=h01
export SPARK_WORKER_MEMORY=4g
```
修改文件名
``` mv slaves.template slaves``` 
修改 slaves 如下
```vim slaves```
```
h01
h02
h03
h04
h05
```
使用 scp 命令将配置好的 Hbase 复制到其他 4 个容器中
``` scp -r /usr/local/spark-2.4.0 root@h02:/usr/local/``` 
``` scp -r /usr/local/spark-2.4.0 root@h03:/usr/local/``` 
```  scp -r /usr/local/spark-2.4.0 root@h04:/usr/local/``` 
```  scp -r /usr/local/spark-2.4.0 root@h05:/usr/local/``` 

## 启动 Spark
```cd /usr/local/spark-2.4.0/sbin```
```./start-all.sh ```

其他
3.1 HDFS 重格式化问题
参考 https://blog.csdn.net/gis_101/article/details/52821946
~~重新格式化意味着集群的数据会被全部删除，格式化前需考虑数据备份或转移问题~~；
先删除主节点（即namenode节点），Hadoop的临时存储目录tmp、namenode存储永久性元数据目录dfs/name、Hadoop系统日志文件目录log 中的内容 （注意是删除目录下的内容不是目录）；
删除所有数据节点(即datanode节点) ，Hadoop的临时存储目录tmp、namenode存储永久性元数据目录dfs/name、Hadoop系统日志文件目录log 中的内容；

格式化一个新的分布式文件系统：
```cd /usr/local/hadoop/bin```
```./hadoop namenode -format```

注意事项
>Hadoop的临时存储目录tmp（即core-site.xml配置文件中的hadoop.tmp.dir属性，默认值是/tmp/hadoop-{user.name}），
如果没有配置hadoop.tmp.dir属性，那么hadoop格式化时将会在/tmp目录下创建一个目录，例如在cloud用户下安装配置hadoop，那么Hadoop的临时存储目录就位于/tmp/hadoop-cloud目录下Hadoop的namenode元数据目录（即hdfs-site.xml配置文件中的dfs.namenode.name.dir属性，默认{hadoop.tmp.dir}/dfs/name），
同样如果没有配置该属性，那么hadoop在格式化时将自行创建。必须注意的是在格式化前必须清楚所有子节点（即DataNode节点）dfs/name下的内容，否则在启动hadoop时子节点的守护进程会启动失败。这是由于，每一次format主节点namenode，dfs/name/current目录下的VERSION文件会产生新的clusterID、namespaceID。
但是如果子节点的dfs/name/current仍存在，hadoop格式化时就不会重建该目录，因此形成子节点的clusterID、namespaceID与主节点（即namenode节点）的clusterID、namespaceID不一致。最终导致hadoop启动失败。

进入：
```sudo docker run -it --network hadoop -h "h01" --name "h01" -p 9870:9870 -p 8088:8088 master /bin/bash```
```sudo docker run -it --network hadoop -h "h02" --name "h02" slave1 /bin/bash```
```sudo docker run -it --network hadoop -h "h03" --name "h03" slave2 /bin/bash```
```sudo docker run -it --network hadoop -h "h04" --name "h04" slave3 /bin/bash```
```sudo docker run -it --network hadoop -h "h05" --name "h05" slave14 /bin/bash```
退出：
```sudo docker commit -m "master" -a "tomding" h01 master```
```sudo docker commit -m "slave1" -a "tomding" h02 slave1```
```sudo docker commit -m "slave2" -a "tomding" h03 slave2```
```sudo docker commit -m "slave3" -a "tomding" h04 slave3```
```sudo docker commit -m "slave4" -a "tomding" h05 slave4```

