```wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel62-3.4.3.tgz```

```tar -xf  mongodb-linux-x86_64-rhel62-3.4.3.tgz -C ~/```


```mv mongodb-linux-x86_64-rhel62-3.4.3/  /usr/local/mongodb```

// 在安装目录下创建 data 文件夹用于存放数据和日志
```mkdir /usr/local/mongodb/data/```
// 在 data 文件夹下创建 db 文件夹，用于存放数据
```mkdir /usr/local/mongodb/data/db/```
// 在 data 文件夹下创建 logs 文件夹，用于存放日志
```mkdir /usr/local/mongodb/data/logs/```
// 在 logs 文件夹下创建 log 文件
```touch /usr/local/mongodb/data/logs/mongodb.log```
// 在 data 文件夹下创建 mongodb.conf 配置文件
``` touch /usr/local/mongodb/data/mongodb.conf```
// 在 mongodb.conf 文件中输入如下内容
```sudo vim ./data/mongodb.conf```
```
#端口号 
port = 27017
#数据目录
dbpath = /usr/local/mongodb/data/db
#日志目录
logpath = /usr/local/mongodb/data/logs/mongodb.log
#设置后台运行
fork = true
#日志输出方式
logappend = true
#开启认证
#auth = true
```
完成 MongoDB 的安装后，启动 MongoDB 服务器：
// 启动 MongoDB 服务器
```sudo /usr/local/mongodb/bin/mongod  -config```
```/usr/local/mongodb/data/mongodb.conf```
// 访问 MongoDB 服务器
```/usr/local/mongodb/bin/mongo```
// 停止 MongoDB 服务器
```sudo /usr/local/mongodb/bin/mongod  -shutdown -config /usr/local/mongodb/data/mongodb.conf```



Redis（单节点）环境配置
// 通过 WGET 下载 REDIS 的源码
```wget  http://download.redis.io/releases/redis-4.0.2.tar.gz```
// 将源代码解压到安装目录
``` tar -xf redis-4.0.2.tar.gz -C ~/```
// 进入 Redis 源代码目录，编译安装
``` cd redis-4.0.2/```
// 安装 GCC
``` sudo apt-get install gcc```
// 编译源代码
```make MALLOC=libc```
// 编译安装
```sudo make install```
// 创建配置文件
```sudo cp ~/redis-4.0.2/redis.conf  /etc/ ```
// 修改配置文件中以下内容
```sudo vim /etc/redis.conf```
```
daemonize yes #37 行 #是否以后台 daemon 方式运行，默认不是后台运行
pidfile /var/run/redis/redis.pid #41 行 #redis 的 PID 文件路径（可选）
bind 0.0.0.0 #64 行 #绑定主机 IP，默认值为 127.0.0.1，我们是跨机器运行，所以需要更改
logfile /var/log/redis/redis.log #104 行 #定义 log 文件位置，模式 log信息定向到 stdout，输出到/dev/null（可选）
dir '/usr/local/rdbfile' #188 行 #本地数据库存放路径，默认为./，编译安装默认存在在/usr/local/bin 下（可选）
```
在安装完 Redis 之后，启动 Redis
// 启动 Redis 服务器
```redis-server /etc/redis.conf```
// 连接 Redis 服务器
```redis-cli```
// 停止 Redis 服务器
```redis-cli shutdown```
2.3 ElasticSearch（单节点）环境配置
// 通过 Wget 下载 ElasticSearch 安装包
```wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.2.tar.gz```
修改 Linux 配置参数：
// 修改文件数配置，在文件末尾添加如下配置
```sudo vim /etc/security/limits.conf ```
```
* soft nofile 65536
* hard nofile 131072
* soft nproc 2048
* hard nproc 4096
// 修改* soft nproc 1024 为 * soft nproc 2048
```sudo vim /etc/security/limits.d/90-nproc.conf ```
* soft nproc 2048 #将该条目修改成 2048
```
// 在文件末尾添加：
```sudo vim /etc/sysctl.conf ```
```
vm.max_map_count=655360
```
// 在文件末尾添加：
```sudo sysctl -p```
配置 ElasticSearch：
// 解压 ElasticSearch 到安装目录
```tar -xf elasticsearch-5.6.2.tar.gz -C ./cluster/```
// 进入 ElasticSearch 安装目录
```cd ./cluster/elasticsearch-5.6.2/```
// 创建 ElasticSearch 数据文件夹 data
```mkdir ./cluster/elasticsearch-5.6.2/data/```
// 创建 ElasticSearch 日志文件夹 logs
```mkdir ./cluster/elasticsearch-5.6.2/logs/```
// 修改 ElasticSearch 配置文件
``` sudo vim ./cluster/elasticsearch-5.6.2/config/elasticsearch.yml```
```
cluster.name: es-cluster #设置集群的名称
node.name: es-node #修改当前节点的名称
path.data: /home/bigdata/cluster/elasticsearch-5.6.2/data #修改数据路径
path.logs: /home/bigdata/cluster/elasticsearch-5.6.2/logs #修改日志路径
bootstrap.memory_lock: false#设置 ES 节点允许内存交换
bootstrap.system_call_filter: false#禁用系统调用过滤器
network.host: linux#设置当前主机名称
discovery.zen.ping.unicast.hosts: ["linux"]#设置集群的主机列表
```
完成 ElasticSearch 的配置后：
// 启动 ElasticSearch 服务
```./cluster/elasticsearch-5.6.2/bin/elasticsearch -d```
// 访问 ElasticSearch 服务
```
curl http://localhost:9200/
{
 
"name" : "es-node",
 
"cluster_name" : "es-cluster",
 
"cluster_uuid" : "VUjWSShBS8KM_EPJdIer6g",


"version" : {
 
"number" : "5.6.2",
 
"build_hash" : "57e20f3",
 
"build_date" : "2017-09-23T13:16:45.703Z",
 
"build_snapshot" : false,
 
"lucene_version" : "6.6.1"
 
},
 
"tagline" : "You Know, for Search"
}
```
// 停止 ElasticSearch 服务
```
jps
8514 Elasticsearch
6131 GradleDaemon
8908 Jps
```
```kill -9 8514```
2.4 Azkaban（单节点）环境配置
2.4.1 安装 Git
// 安装 GIT
```sudo apt-install install git ```
// 通过 git 下载 Azkaban 源代码
```git clone https://github.com/azkaban/azkaban.git```
// 进入 azkaban 目录
```cd azkaban/```
// 切换到 3.36.0 版本
```git checkout -b 3.36.0```
2.4.2 编译 Azkaban
详细请参照：https://github.com/azkaban/azkaban
// 安装编译环境
```sudo apt-get install gcc```
```sudo apt-get install -y gcc-c++*```

// 执行编译命令
```./gradlew clean build```
2.4.3 部署 Azkaban Solo
// 将编译好的 azkaban 中的 azkaban-solo-server-3.36.0.tar.gz 拷贝到根目录
```cp ./azkaban-solo-server/build/distributions/azkaban-solo-server-3.36.0.tar.gz ~/```
// 解压 azkaban-solo-server-3.36.0.tar.gz 到安装目录
``` tar -xf azkaban-solo-server-3.36.0.tar.gz  -C ./cluster```
// 启动 Azkaban Solo 单节点服务
```bin/azkaban-solo-start.sh```
// 访问 azkaban 服务，通过浏览器代开 http://10.36.34.100:8081，通过用户名：azkaban，密码 azkaban
登录。

// 关闭 Azkaban 服务
```bin/azkaban-solo-shutdown.sh```
2.5 Spark（单节点）环境配置
// 通过 wget 下载 zookeeper 安装包
``` wget https://d3kbcqa49mib13.cloudfront.net/spark-2.1.1-bin-hadoop2.7.tgz```
// 将 spark 解压到安装目录
``` tar -xf spark-2.1.1-bin-hadoop2.7.tgz -C ./cluster```

// 进入 spark 安装目录
```cd ./cluster/spark-2.1.1-bin-hadoop2.7/```
// 复制 slave 配置文件
```cp ./conf/slaves.template ./conf/slaves```
 
// 修改 slave 配置文件
```vim ./conf/slaves```
在文件最后将本机主机名进行添加
```
linux  #
```
// 复制 Spark-Env 配置文件
```cp ./conf/spark-env.sh.template ./conf/spark-env.sh ```
```
SPARK_MASTER_HOST=linux #添加 spark master 的主机名
SPARK_MASTER_PORT=7077 #添加 spark master 的端口号
```
安装完成之后，启动 Spark
// 启动 Spark 集群
```sbin/start-all.sh```
// 访问 Spark 集群，浏览器访问 http://linux:8080
// 关闭 Spark 集群
```sbin/stop-all.sh```
2.6 Zookeeper（单节点）环境配置
// 通过 wget 下载 zookeeper 安装包
```wget  http://archive.apache.org/dist/zookeeper/zookeeper-3.4.10/zookeeper-3.4.10.tar.gz```
// 将 zookeeper 解压到安装目录
```tar -xf zookeeper-3.4.10.tar.gz -C ./cluster```
// 进入 zookeeper 安装目录
```cd ./cluster/zookeeper-3.4.10/```
// 创建 data 数据目录
```mkdir data/```
// 复制 zookeeper 配置文件
```cp ./conf/zoo_sample.cfg ./conf/zoo.cfg ```
// 修改 zookeeper 配置文件
```vim conf/zoo.cfg```
```
dataDir=/home/bigdata/cluster/zookeeper-3.4.10/data #将数据目录地址修
改为创建的目录
```
// 启动 Zookeeper 服务
```bin/zkServer.sh start```
// 查看 Zookeeper 服务状态
```bin/zkServer.sh status```
>ZooKeeper JMX enabled by default
Using config: 
/home/bigdata/cluster/zookeeper-3.4.10/bin/../conf/zoo.cfg
Mode: standalone

// 关闭 Zookeeper 服务
```bin/zkServer.sh stop```
2.7 Flume-ng（单节点）环境配置
// 通过 wget 下载 zookeeper 安装包
```wget http://archive.apache.org/dist/flume/1.8.0/apache-flume-1.8.0-bin.tar.gz```
// 将 zookeeper 解压到安装目录
```tar –xf apache-flume-1.8.0-bin.tar.gz –C ./cluster```
// 等待项目部署时使用

2.8 Kafka（单节点）环境配置
// 通过 wget 下载 zookeeper 安装包
```wget http://archive.apache.org/dist/kafka/0.10.2.1/kafka_2.12-0.10.2.1.tgz```
// 将 kafka 解压到安装目录
```tar -xf kafka_2.12-0.10.2.1.tgz -C ./cluster```
// 进入 kafka 安装目录
```cd ./cluster/kafka_2.12-0.10.2.1/ ```
// 修改 kafka 配置文件
```vim config/server.properties```
```
host.name=linux #修改主机名
port=9092 #修改服务端口号
zookeeper.connect=linux:2181#修改 Zookeeper 服务器地址
```
// 启动 kafka 服务 !!! 启动之前需要启动 Zookeeper 服务
```bin/kafka-server-start.sh -daemon ./config/server.properties```
// 关闭 kafka 服务
```bin/kafka-server-stop.sh```
// 创建 topic
```bin/kafka-topics.sh --create  --zookeeper linux:2181 --replication-factor 1 --partitions 1 --topic recommender```
// kafka-console-producer
```bin/kafka-console-producer.sh  --broker-list linux:9092 --topic recommender```
// kafka-console-consumer
```bin/kafka-console-consumer.sh --bootstrap-server linux:9092 --topic recommender```



















































