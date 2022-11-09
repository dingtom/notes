- [ Spark](#head1)
- [ Spark四大特点](#head2)
- [ RDD](#head3)
- [ 算子](#head4)
	- [ parallelize](#head5)
	- [ textFile](#head6)
	- [ getNumPartitions](#head7)
- [ Transformation算子](#head8)
- [ flatMap](#head9)
- [ reduceByKey](#head10)
- [ groupBy](#head11)
- [ groupByKey](#head12)
- [ filter](#head13)
- [ distinct](#head14)
- [ union](#head15)
- [ join](#head16)
- [ glom](#head17)
- [ sortBy](#head18)
- [ sortByKey](#head19)
- [ 分区操作算子](#head20)
- [ mapPartitions](#head21)
- [ foreachPartition](#head22)
- [ partitionBy](#head23)
- [ partition](#head24)
- [ coalesce](#head25)
- [ mapValues](#head26)
- [ join](#head27)
- [ Action算子](#head28)
- [ countByKey](#head29)
- [ collect](#head30)
- [ reduce](#head31)
- [ fold](#head32)
- [ first](#head33)
- [ take](#head34)
- [ count](#head35)
- [ takeSample](#head36)
- [ top](#head37)
- [ takeOrdered](#head38)
- [ foreach](#head39)
- [ saveAsTextFile](#head40)
- [ 提交到YARN集群中运行](#head41)
- [ RDD的持久化](#head42)
	- [ 缓存](#head43)
	- [ CheckPoint](#head44)
- [ 共享变量](#head45)
	- [ 广播变量](#head46)
	- [ 累加器](#head47)
- [Spark 内核调度](#head48)
	- [ DAG](#head49)
		- [ Spark是怎么做内存计算的？DAG的作用？Stage阶段划分的作用？](#head50)
		- [ Spark为什么比MapReduce快](#head51)
	- [ Spark的并行](#head52)
	- [ Spark程序的调度](#head53)
- [Spark Shuffle](#head54)
- [ SparkSQL](#head55)
	- [ SparkSQL和Hive的异同](#head56)
	- [ SparkSQL的数据抽象](#head57)
	- [ 读取文件](#head58)
	- [ 方法](#head59)
	- [ 保存](#head60)
	- [ JDBC读写数据库](#head61)
	- [ UDF函数](#head62)
	- [ SparkSQL的运行流程](#head63)
	- [Spark On Hive](#head64)
	- [ 分布式SQL执行引擎](#head65)


# <span id="head1"> Spark</span>

**Spark是一款分布式内存计算的统一分析引擎。其特点就是对任意类型的数据进行自定义计算。**Spark的适用面非常广泛，所以，被称之为 **统一的（适用面广）的分析引擎（数据处理）**



Spark可以计算：结构化、半结构化、非结构化等各种类型的数据结构，同时也支持使用Python、Java、Scala、R以及SQL语言去开发应用程序计算数据。



RDD 是一种分布式内存抽象，其使得程序员能够在大规模集群中做内存运算，并且有一定的容错方式。而这也
是整个 Spark 的核心数据结构，Spark 整个平台都围绕着RDD进行。


| |Hadoop| Spark|
|:-:|:-:|:-:|
|类型 |基础平台, 包含计算, 存储, 调度 |纯计算工具（分布式）|
|场景 |海量数据批处理（磁盘迭代计算）|海量数据的批处理（内存迭代计算、交互式计算）、海量数据流计算|
|价格 |对机器要求低, 便宜 |对内存有要求, 相对较贵|
|编程范式 |Map+Reduce, API 较为底层, 算法适应性差| RDD组成DAG有向无环图, API 较为顶层, 方便使用|
|数据存储结构| MapReduce中间计算结果在HDFS磁盘上, 延迟大 |RDD中间运算结果在内存中 , 延迟小|
|运行方式| Task以进程方式维护, 任务启动慢 |Task以线程方式维护, 任务启动快，可批量创建提高并行能力|

尽管Spark相对于Hadoop而言具有较大优势，但Spark并不能完全替代Hadoop
- 在计算层面，Spark相比较MR（MapReduce）有巨大的性能优势，但至今仍有许多计算工具基于MR构架，比如非常成熟的Hive

- Spark仅做计算，而Hadoop生态圈不仅有计算（MR）也有存储（HDFS）和资源管理调度（YARN），HDFS和YARN仍是许多大数据体系的核心架构。

  


# <span id="head2"> Spark四大特点</span>
- 速度快

  由于Apache Spark支持内存计算，并且通过DAG（有向无环图）执行引擎支持无环数据流，所以官方宣称其在内存中的运算速度要比Hadoop的MapReduce快100倍，在硬盘中要快10倍。

  Spark 借鉴了 MapReduce 思想发展而来，保留了其分布式并行计算的优点并改进了其明显的缺陷。让中间数据存储在内存中提高了运行速度、并提供丰富的操作数据的API提高了开发速度。

- 易于使用

  Spark 的版本已经更新到 Spark 3.2.0（截止日期2021.10.13），支持了包括 Java、Scala、Python 、R和SQL语言在内的多种语言。为了兼容Spark2.x企业级应用场景，Spark仍然持续更新Spark2版本。

- 通用性强

  在 Spark 的基础上，Spark 还提供了包括Spark SQL、Spark Streaming、MLib 及GraphX在内的多个工具库，我们可以在一个应用中无缝
  地使用这些工具库。

-  运行方式

  Spark 支持多种运行方式，包括在 Hadoop 和 Mesos 上，也支持 Standalone的独立运行模式，同时也可以运行在云Kubernetes（Spark 2.3开始支持）上。对于数据源而言，Spark 支持从HDFS、HBase、Cassandra 及 Kafka 等多种途径获取数据。

  



Hadoop的基于进程的计算和Spark基于线程方式优缺点？

> 答案：Hadoop中的MR中每个map/reduce task都是一个java进程方式运行，好处在于进程之间是互相独立的，每个task独享进程资源，没有互相干扰，监控方便，但是问题在于task之间不方便共享数据，执行效率比较低。比如多个map task读取不同数据源文件需要将数据源加载到每个map task中，造成重复加载和浪费内存。而基于线程的方式计算是为了数据共享和提高执行效率，Spark采用了线程的最小的执行单位，但缺点是线程之间会有资源竞争。
>
> - 线程是CPU的基本调度单位
> - 一个进程一般包含多个线程, 一个进程下的多个线程共享进程的资源
> - 不同进程之间的线程相互不可见
> - 线程不能独立执行
> - 一个线程可以创建和撤销另外一个线程

Spark解决什么问题？

> 海量数据的计算，可以进行离线批处理以及实时流计算

Spark有哪些模块？

> 核心SparkCore、SQL计算（SparkSQL）、流计算（SparkStreaming）、图计算（GraphX）、机器学习（MLlib）
>
> 
>
> - Spark Core：Spark的核心，Spark核心功能均由Spark Core模块提供，是Spark运行的基础。Spark Core以RDD为数据抽象，提供Python、Java、Scala、R语言的API，可以编程进行海量离线数据批处理计算。
> - SparkSQL：基于SparkCore之上，提供结构化数据的处理模块。SparkSQL支持以SQL语言对数据进行处理，SparkSQL本身针对离线计算场景。同时基于SparkSQL，Spark提供了Structured Streaming模块，可以以SparkSQL为基础，进行数据的流式计算。
> - SparkStreaming：以SparkCore为基础，提供数据的流式计算功能。
> - MLlib：以SparkCore为基础，进行机器学习计算，内置了大量的机器学习库和API算法等。方便用户以分布式计算的模式进行机器学习计算。
> - GraphX：以SparkCore为基础，进行图计算，提供了大量的图计算API，方便用于以分布式计算模式进行图计算。



Spark特点有哪些？

> 速度快、使用简单、通用性强、多种模式运行

Spark的运行模式？

> • 本地模式
> • 集群模式（StandAlone、YARN、K8S）
> • 云模式
>
> 
>
> - 本地模式（单机）
>   本地模式就是以一个独立的进程，通过其内部的多个线程来模拟整个Spark运行时环境
> - Standalone模式（集群）
>   Spark中的各个角色以独立进程的形式存在，并组成Spark集群环境
> - Hadoop YARN模式（集群）
>   Spark中的各个角色运行在YARN的容器内部，并组成Spark集群环境
>
> - Kubernetes模式（容器集群）
> Spark中的各个角色运行在Kubernetes的容器内部，并组成Spark集群环境
> - 云服务模式（运行在云平台上）



Spark的运行角色（对比YARN）？

> > 从2个层面划分：
> > 资源管理层面：
> >
> > - 管理者： Spark是 Master角色，YARN是 Resourcemanager
> > - 工作中： Spark是 Workers角色，YARN是 Nodemanager
> >
> > 从任务执行层面：
> >
> > - 某任务管理者： Spark是 Driver角色，YARN是 Applicationmaster
> > - 某任务执行者： Spark是 Executor角色，YARN是容器中运行的具体工作进程
> >
> > 注：正常情况下Executor是干活的角色，不过在特殊场景下（Local模式）Driver可以即管理又干活
>
> 
>
> > YARN主要有4类角色，从2个层面去看：
> >
> > 资源管理层面
> >
> > - 集群资源管理者（Master）：ResourceManager
> >
> > - 单机资源管理者（Worker）：NodeManager
> >
> > 任务计算层面
> > - 单任务管理者（Master）：ApplicationMaster
> > - 单任务执行者（Worker）：Task（容器内计算框架的工作角色）



# <span id="head3"> RDD</span>

RDD（Resilienlt Distributed Dataset）叫做弹性分布式数据集，是Spark中最基本的数据抽象，代表一个**不可变、可分区、里面的元素可并行计算**的集合。

- Dataset：一个数据集合，用于存放数据的
- Distributed：RDD中的数据是分布式存储的，可用于分布式计算。
- Resilient：RDD中的数据可以存储在内存中或者磁盘中。
- 所有的运算以及操作都建立在 RDD 数据结构的基础之上。

RDD的五大特性

- RDD是有分区的

  RDD的分区是RDD数据存储的最小单位，l一份RDD的数据，本质上是分隔成了多个分区

- RDD的方法会作用在其所有的分区上

- RDD之间是有依赖关系(RDD有血缘关系)

- Key-Value型的RDD可以有分区器

  默认分区器：Hash分区规则，可以手动设置一个分区器(rdd.partitionBy的方法来设置)

- DD的分区规划，会尽量靠近数据所在的服务器

  在初始RDD（读取数据的时候）规划的时候分区会尽量规划到存储数据所在的服务器上因为这样可以走本地读取，避免网铬读取

# <span id="head4"> 算子</span>

分布式集合对象上的API称之为算子

```python
# 这里可以选择本地PySpark环境执行Spark代码，也可以使用虚拟机中PySpark环境，通过os可以配置
os.environ['SPARK_HOME'] = '/export/servers/spark'
# PYSPARK_PYTHON = "/root/anaconda3/envs/pyspark_env/bin/python"
# 当存在多个版本时，不指定很可能会导致出错
# os.environ["PYSPARK_PYTHON"] = PYSPARK_PYTHON
# os.environ["PYSPARK_DRIVER_PYTHON"] = PYSPARK_PYTHON
if __name__ == '__main__':
    print('PySpark First Program')
# TODO: 当应用运行在集群上的时候，MAIN函数就是Driver Program，必须创建SparkContext对象
# 创建SparkConf对象，设置应用的配置信息，比如应用名称和应用运行模式
conf = SparkConf().setAppName("miniProject").setMaster("local[*]")
# TODO: 构建SparkContext上下文实例对象，读取数据和调度Job执行
sc = SparkContext(conf=conf)
```



### <span id="head5"> parallelize</span>

演示通过并行化集合的方式去创建RDD, 本地集合 -> 分布式对象(RDD)

```python
rdd = sc.parallelize([1, 2, 3, 4, 5, 6, 7, 8, 9])
```

### <span id="head6"> textFile</span>

读取数据

```python
# 读取本地文件数据
file_rdd1 = sc.textFile("../data/input/words.txt")
# 加最小分区数参数的测试
file_rdd2 = sc.textFile("../data/input/words.txt", 3)
# 最小分区数是参考值, Spark有自己的判断, 你给的太大Spark不会理会
file_rdd3 = sc.textFile("../data/input/words.txt", 100)
# 读取HDFS文件数据测试
hdfs_rdd = sc.textFile("hdfs://n1:8020/pydata/input/words.txt")
```

### <span id="head7"> getNumPartitions</span>

获取分区数



## <span id="head8"> Transformation算子</span>

定义：RDD的算子，**返回值仍旧是一个RDD的**，称之为转换算子



特性：这类算子是Lazy懒加载的。如果没action.算子，Transformation算子是不工作的。

### <span id="head9"> flatMap</span>

对rdd执行map操作，然后进行解除嵌套操作

![quicker_43d4dca9-e229-44d1-b559-f2b6600ceeae.png](https://s2.loli.net/2022/02/26/Xsl7TpUPnqAudxa.png)

### <span id="head10"> reduceByKey</span>

针对KV型RDD,自动按照key分组，然后根据你提供的聚合逻辑，完成**组内数据**(valve)的聚合操作

![quicker_bf607a63-1c54-43cb-99a6-27b1a0b0cdcd.png](https://s2.loli.net/2022/02/23/WuPSmhzI9igHZb8.png)

### <span id="head11"> groupBy</span>

将rdd的数据进行分组

拿到你的返回值后，将所有相同返回值的放入一个组中。每一个组是一个二元元组，**key就是返回值，所有同组的数据放入一个迭代器对象中作为value**

```python
result = rdd.groupBy(lambda t: t[0])
print(result.map(lambda t:(t[0], list(t[1]))).collect())

[('b', [('b', 1), ('b', 2), ('b', 3)]), ('a', [('a', 1), ('a', 1)])]
```

### <span id="head12"> groupByKey</span>

针对KV型RDD,自动按照key分组

### <span id="head13"> filter</span>

过滤想要的数据进行保留

### <span id="head14"> distinct</span>

对RDD数据进行去重，返回新RDD

### <span id="head15"> union</span>

2个rdd合并成1个rdd返回，**只合并，不会去重**

### <span id="head16"> join</span>

对两个RDD执行JOIN操作(可实现SQL的内外连接)**注意：join算子只能用于二元元组**

```python
    rdd1 = sc.parallelize([
        (1001, "zhangsan"),
        (1002, "lisi"),
        (1003, "wangwu"),
        (1004, "zhaoliu")
    ])
    rdd2 = sc.parallelize([
        (1001, "销售部"),
        (1002, "科技部")
    ])
    print(rdd1.join(rdd2).collect())
    print(rdd1.leftOuterJoin(rdd2).collect())
    print(rdd1.rightOuterJoin(rdd2).collect())
    
    [(1001, ('zhangsan', '销售部')), (1002, ('lisi', '科技部'))]
[(1004, ('zhaoliu', None)), (1001, ('zhangsan', '销售部')), (1002, ('lisi', '科技部')), (1003, ('wangwu', None))]
[(1001, ('zhangsan', '销售部')), (1002, ('lisi', '科技部'))]
```

intersection

求RDD之间的交集

### <span id="head17"> glom</span>

将RDD的数据，加上嵌套

```python
rdd = sc.parallelize([1, 2, 3, 4, 5, 6, 7, 8, 9], 2)
  print(rdd.glom().map(lambda x: x).collect())
    
 [[1, 2, 3, 4], [5, 6, 7, 8, 9]]   
```

### <span id="head18"> sortBy</span>

对RDD数据进行排序，基于你指定的排序依据。

```python
# 按照value 数字进行排序
# 参数1函数, 表示的是 ,  告知Spark 按照数据的哪个列进行排序
# 参数2: True表示升序 False表示降序
# 参数3: 排序的分区数
"""注意: 如果要全局有序, 排序分区数请设置为1"""
print(rdd.sortBy(lambda x: x[1], ascending=True, numPartitions=1).collect())


```



### <span id="head19"> sortByKey</span>

针对KV型RDD,按照key进行排序

```python
# ascending:升序or降序，True升序，Falsel降序，默认是升序
# numPartitions:按照几个分区进行排序，如果全局有序，设置1
# keyfunc:排序前处理一下key，让Key以你处理的样子进行排序（不影响数据本身）
print(rdd.sortByKey(ascending=True, numPartitions=1, keyfunc=lambda key: str(key).lower()).collect())

```

## <span id="head20"> 分区操作算子</span>

### <span id="head21"> mapPartitions</span>

一次被传递的是一整个分区的数据作为一个迭代器(一次性list)对象传入过来

map一次处理一条，六次，mapPartitions三次

![quicker_3aff15d9-b9da-4cde-94bf-40473841c484.png](https://s2.loli.net/2022/02/27/jFSWn9bfDaEcmBX.png)

![quicker_6e6cf5b0-0d4c-45d6-b22d-0e320bafe6f5.png](https://s2.loli.net/2022/02/26/7payLHS1lV54EXZ.png)

```python
rdd = sc.parallelize([1, 3, 2, 4, 7, 9, 6], 3)
def process(iter):
    result = list()
    for it in iter:
        result.append(it * 10)
        print(result)
        return result
    print(rdd.mapPartitions(process).collect())

    [20, 40]
    [70, 90, 60]
    [10, 30, 20, 40, 70, 90, 60]

```

### <span id="head22"> foreachPartition</span>

和普通foreach一致，一次处理的是一整个分区数据(就是一个没有返回值的mapPartitions)

### <span id="head23"> partitionBy</span>

对RDD进行自定义分区操作

```python
# 参数1重新分区后有几个分区
# 参数2自定义分区规则，函数传入
# 返回值是int分区编号从0开始
def process(k):
    if 'hadoop' == k or 'hello' == k: return 0
    if 'spark' == k: return 1
    return 2
print(rdd.partitionBy(3, process).glom().collect())
[[('hadoop', 1), ('hello', 1), ('hadoop', 1)], [('spark', 1), ('spark', 1)], [('flink', 1)]]
```

### <span id="head24"> partition</span>

对RDD的分区执行重新分区（仅数量)

### <span id="head25"> coalesce</span>

对分区进行数量增减

```python
# 参数1，分区数
# 参数2,True or False。True表示允许shuffle,也就是可以加分区。False表示不允许shuffle,也就是不劭加分区，False是默认

rdd = sc.parallelize([1, 2, 3, 4, 5], 3)
# repartition 修改分区
print(rdd.repartition(1).getNumPartitions())
print(rdd.repartition(5).getNumPartitions())
# coalesce 修改分区
print(rdd.coalesce(5).getNumPartitions())
print(rdd.coalesce(5, shuffle=True).getNumPartitions())
1
5
3
5
```

如果你改分区了会影响并行计算（内存迭代的并行管道数量）后面学。分区如果增加，极大可能导致shuffle。对比repartition,一般使用coalesce较多，因为加分区要写参数2这样避免写repartition的时候手抖了加分区了。

### <span id="head26"> mapValues</span>

针对二元元组RDD,对其内部的二元元组的Valve执行map操作

```python
rdd = sc.parallelize([('hadoop', 1), ('spark', 1), ('hello', 1), ('flink', 1), ('hadoop', 1), ('spark', 1)])
print(rdd.mapValues(lambda x: x * 10).collect())
[('hadoop', 10), ('spark', 10), ('hello', 10), ('flink', 10), ('hadoop', 10), ('spark', 10)]
```

### <span id="head27"> join</span>





## <span id="head28"> Action算子</span>

定义：**返回值不是rdd的就是action.算子**

### <span id="head29"> countByKey</span>

统计key出现的次数(一般适用于KV型RDD)，返回字典

```python
result = rdd2.countByKey()
print(result)
print(type(result))

defaultdict(<class 'int'>, {'hello': 3, 'spark': 1, 'hadoop': 1, 'flink': 1})
<class 'collections.defaultdict'>
```

### <span id="head30"> collect</span>

将RDD各个分区内的数据，统一收集到Driver中，形成一个List对象

### <span id="head31"> reduce</span>

对RDD数据集按照你传入的逻辑进行聚合

```python
rdd = sc.parallelize([1, 2, 3, 4, 5])
print(rdd.reduce(lambda a, b: a + b))
15
```

### <span id="head32"> fold</span>

和reduce一样，接受传入逻辑进行聚合，聚合是带有初始值的

```python
这个初始值聚合，会作用在：分区内聚合、分区间聚合
比如：[[1,2,3]，[4,5,6]，[7,8,9]]
数据分布在3个分区
分区1 123聚合的时候带上10作为初始值得到16
分区2 456聚合的时候带上10作为初始值得到25
分区3 789聚合的时候带上10作为初始值得到34
3个分区的结果做聚合也带上初始值10，所以结果是：10+16+25+34=85

rdd = sc.parallelize([1, 2, 3, 4, 5, 6, 7, 8, 9], 3)
print(rdd.fold(10, lambda a, b: a + b))
85
```

### <span id="head33"> first</span>

取出RDD的第一个元素

### <span id="head34"> take</span>

取RDD的前N个元素，组合成ist返回给你

### <span id="head35"> count</span>

计算RDD有多少条数据，返回值是一个数字

### <span id="head36"> takeSample</span>

随机抽样RDD的数据

```python
# 参数1:TrUe表示运行取同一个数据，False表示不允许取同一个数据。
# 参数2：抽样要几个
# 参数3：随机数种子，这个参数传入一个数字即可，随意给

rdd = sc.parallelize([1, 3, 5, 3, 1, 3, 2, 6, 7, 8, 6], 1)
print(rdd.takeSample(False, 5, 1))
```

### <span id="head37"> top</span>

对RDD数据集进行降序排序，取前N个

### <span id="head38"> takeOrdered</span>

对RDD进行排序取前N个

```python
# 参数1要几个数据
# 参数2对排序的数据进行更改(不会更改数据本身，只是在排序的时候换个样子)

rdd = sc.parallelize([1, 3, 2, 4, 7, 9, 6], 1)
print(rdd.takeOrdered(3))
print(rdd.takeOrdered(3, lambda x: -x))
print(rdd.top(3))

[1, 2, 3]
[9, 7, 6]
[9, 7, 6]
```

### <span id="head39"> foreach</span>

对RDD的每一个元素，执行你提供的逻辑的操作(和mp一个意思)，**但是这个方法没有返回值**

### <span id="head40"> saveAsTextFile</span>

将RDD的数据写入文本文件中。支持本地写出，hdfs等文件系统。

**写出数据是跳过Driver的每个分区直接写出**，每个分区所在的Executor]直接控制数据写出到目标文件系统中所以才会**一个分区产生1个结果文件**



**foreach、saveAsTextFile这两个算子是分区(Executor)直接执行的跳过Driver**,.由分区所在的Executorj直接执行。其余的Action.算子都会将结果发送至Driver





- reduceByKey 和 groupByKey的区别?

  reduceByKey自带聚合逻辑, groupByKey不带。如果做数据聚合reduceByKey的效率更好, 因为可以**先聚合后shuffle再最终聚合, 传输的IO小**



# <span id="head41"> 提交到YARN集群中运行</span>

在PyCharml中直接执行

```python
# 加入环境变量，让pycharmi直接提交yarn的时候，知道nadoop的配置在哪，可以去读取yarn的信息
os.environ['HADOOP_CONF_DIR'] = "/export/server/hadoop/etc/hadoop"

conf = SparkConf().setAppName("test-yarn-1").setMaster("yarn")

    
# 如果提交到集群运行, 除了主代码以外, 还依赖了其它的代码文件.需要设置一个参数, 来告知spark ,还有依赖文件要同步上传到集群中
# 参数1做: spark.submit.pyFiles
# 参数2的值可以是 单个.py文件,   也可以是.zip压缩包(有多个依赖文件的时候可以用zip压缩后上传)
conf.set("spark.submit.pyFiles", "defs_19.py")


```

在服务器上通过spark-submit提交到集群运行

```python
#--py-f1Les可以帮你指定你依赖的其它python代码，支持。z1p（一堆），也可以单个。py文件都行。
/export/server/spark/bin/spark-submit --master yarn --py-files ./defs.zip ./main.py

# 要注意代码中：
# master部分删除
# 读取的文件路径改为hdfs才可以

'''榨干集群性能提交,先查看集群的资源有多少：'''

# 查看CPU有几核：
cat /proc/cpuinfo | grep processor | wc -l
# 查看内存有多大：
free -g
# 通过命令，计算得知，当前我集群3台服务器总共提供：16G物理内存+6核心CPU的计算资源简单规划：这个Spark任务需要：
# 吃掉6核CPU,吃掉12个GB内存,规划后，希望使用6个executor来干活，每个executorl吃掉1核CPU2G内存

bin/spark-submit --master yarn --py-files /root/defs.py
--executor-memory 2g
--executor-cores 1
--num-executors 6
/root/main.py
#每个executorl吃2g内存，吃1个cpu核心，总共6个executor
```



# <span id="head42"> RDD的持久化</span>



RDD的数据是**过程数据**，RDD之间进行相互迭代计算(Transformation的转换)，当执行开启后**，新RDD的生成，代表老RDD的消失，RDD的数据只在处理的过程中存在**，一旦处理完成，就不见了。

这个特性可以最大化的利用资源，老旧DD没用了就从内存中清理，给后续的计算腾出内存空间。



![quicker_961662a0-d755-40c4-b30d-eb7fcee9cc6f.png](https://s2.loli.net/2022/02/26/q7c3OtKLJuUkT4d.png)

## <span id="head43"> 缓存</span>

对于上述的场景，肯定要执行优化，优化就是：RDD3如果不消失，那么RDD1→RDD2→RDD3这个链条就不会执行2次，或者更多次

缓存的API

```python
#RDD3被2次使用，可以加入缓存进行优化
rdd3.cache（）#缓存到内存中
rdd3.persist(StorageLevel.MEMORY_ONLY)#仅内存缓存
rdd3.persist(StorageLevel.MEMORY_ONLY_2)#仅内存缓存，2个副本
rdd3.persist(StorageLevel.DISK_ONLY)#仅缓存硬盘上
rdd3.persist(StorageLevel.DISK_ONLY_2)#仅缓存硬盘上，2个副本
rdd3.persist(StorageLevel.DISK_ONLY_3)#仅缓存硬盘上，3个副本
rdd3.persist(StorageLevel.MEMORY_AND_DISK)#先放内存，不够放硬盘
rdd3.pers1st(StorageLevel.MEMORY_AND_DISK2)#先放内存，不够放硬盘，2个副本
rdd3.persist(StorageLevel.OFF_HEAP)#堆外内存（系统内存）
#如上API,自行选择使用即可
#股建议使用rdd3.persist(StorageLevel.MEMORY_AND_DISK)
#如果内存比较小的集群，建议使用rdd3.persist(StorageLevel.DISK0NLY)或者就别用缓存了用CheckPoint
#主动清理缓存的API
rdd.unpersist（）
```



缓存技术可以将过程RDD数据，**分散存储**持久化**保存到内存或者硬盘上**。但是，这个保存在设定上是认为**不安全**的
缓存的数据在设计上是认为有丢失风险的。所以，缓存有一个特点就是：其**保留RDD之间的血缘（依赖）关系**
一旦缓存丢失，可以基于血缘关系的记录，重新计算这个RDD的数据

![quicker_2f5166c8-78f5-4282-84fb-72c53b7288d7.png](https://s2.loli.net/2022/02/26/lfdeBkY7WtR54Uz.png)

RDD是将自己分区的数据，每个**分区自行将其数据保存在其所在的Executor内存和硬盘上，这是分散存储**



## <span id="head44"> CheckPoint</span>

也是将RDD的数据，保存起来。但是它**仅支持硬盘存储**、它被设计认为是**安全的、不保留血缘关系，集中收集各个分区数据进行存储**。

 ![quicker_a810d31c-37ce-41eb-a6c7-85fd86579471.png](https://s2.loli.net/2022/02/25/hDcKR7LfmNwzIFl.png)

- CheckPoint不管分区数量多少，风险是一样的，缓存分区越多，风险越高
-  CheckPoint支持写入HDFS,缓存不行，HDFS是高可靠存储，CheckPoint被认为是安全的。
-  CheckPointz不支持内存，缓存可以，缓存如果写内存性能比CheckPoint要好一些



```python
#设置CheckPoint第一件事情，选择CP的保存路径
#如果是Local模式，可以支持本地文件系统，如果在集群运行，千万要用HDFS
sc.setCheckpointDir("hdfs://node1:8020/output/bj52ckp")
#用的时候，直接调用checkpoint算子即可。
rdd.checkpoint（）

rdd.unpersist()
```

CheckPoint是一种重量级的使用，也就是RDD的重新计算成本很高的时候，我们采用CheckPointh比较合适。或者数据量很大，用CheckPointh比较合适.**如果数据量小，或者RDD重新计算是非常快的，用CheckPointi没啥必要，直接缓存即可。**



Cachei和CheckPoint两个API都不是Action类型，想要它俩工作，必须在后面接上Action。



- **CheckPoint不管分区数量多少，风险是一样的，缓存分区越多，风险越高**
- **CheckPoint支持写入HDFS,缓存不行，HDFS是高可靠存储，CheckPoint被认为是安全的。**
- **CheckPointz不支持内存，缓存可以，缓存如果写内存性能比CheckPoint要好一些**
- **CheckPoint因为设计认为是安全的，所以不保留血缘关系，而缓存因为设计上认为不安全，所以保留**



# <span id="head45"> 共享变量</span>



## <span id="head46"> 广播变量</span>



分布式集合RDD和本地集合进行关联使用的时候降低内存占用以及减少网络IO传输，提高性能



**本地list对象，被发送到每个分区的处理线程上使用，也就是一个executor内，其实存放了2份一样的数据**。**executor是进程，进程内资源共享**，这2份数据没有必要，造成了内存浪费。



![quicker_2fe3f89c-1f12-42f7-a92a-fbd11c66c44e.png](https://s2.loli.net/2022/02/27/MnDZ4CAHF9tV8Id.png)

如果将本地list对象标记为广播变量对象，那么当上述场景出现的时候，Spark只会：给每个Executor来一份数据，而不是像原本那样，每一个分区的处理线程都来一份。节省内存。

```python 
#1,将本地机list标记成广播变量即可
broadcast = sc.broadcast(stu_info_list)
#2.使用广播变量，从broadcast对象中取出本地凯ist对象即可
value =  broadcast.value
#也就是先放进去broadcast内部，然后从proadcast内部在取出来用，中间传输的是broadcasti这个对象了.只要中间传输的是broadcast对象，spark就会留意，只会给每个Executor发一份了，而不是傻傻的哪个分区要都给，
```

## <span id="head47"> 累加器</span>

分布式代码执行中，进行全局累加

![quicker_5ad8353a-49ac-41f3-9d6c-779fd836d5a1.png](https://s2.loli.net/2022/02/27/k9imS5du1rXKxGC.png)



count来自driver对象，**当在分布式的map算子中需要count对象的时候，driver会将count对象发送给每一个executor一份（复制发送）**，每个executor各自收到一个，在最后执行print(count)的时候，这个被打印的count依旧是driver的那个，所以，不管executor中累加到多少，都和driver这个count无关

```python 
sc.accumulator
# 这个对象可以从各个Executort中收集它们的执行结果，作用回自己身上。
```

![quicker_fabaa4b2-8d28-4dcf-9e52-6df75596cb20.png](https://s2.loli.net/2022/02/27/2Iv1yrHRaDeJsAK.png)

也就是，使用累加器的时候，要注意，因为rdd是过程数据，如果rdd被多次使用，可能会重新构建此rdd，如果累加器累加代码，存在重新构建的步骤中，累加器累加代码就可能被多次执行

如何解决：加缓存或者CheckPoint即可。











# <span id="head48">Spark 内核调度</span>



## <span id="head49"> DAG</span>

DAG（有向无环图）是Spark代码的逻辑执行图，这个DAG的最终作用是；为了构建物理上的Spark详细执行计划而生，所以，由于Spark是分布式（多分区）的，那么DAG和分区之间也是有关联的。



- 一个Spark环境可以运行多个Application
- 一个代码运行起来，会成为一个Application
- Application内部可以有多个Job
- 每个Job由一个Action产生，并且每个Job有自己的DAG执行图
- 一个Job的DAG图会基于宽窄依赖划分成不同的阶段
- 不同阶段内基于分区数量，形成多个并行的内存迭代管道
- 每一个内存迭代管道形成一个Task(DAG调度器划分将Job内划分出具体的task任务，一个Job被划分出来的task在逻辑上称之为这个job的taskset)





在SparkRDD前后之间的关系，分为：

- 窄依赖：父RDD的一个分区，全部将数据发给子RDD的一个分区
- 宽依赖（shuffle）：父RDD的一个分区，将数据发给子RDD的多个分区
  

**从后向前，遇到宽依赖就划分出一个阶段。称之为stage，在stage的内部，一定都是：窄依赖**

![quicker_959a4989-ad2f-4f11-b402-0ee3d2696e61.png](https://s2.loli.net/2022/02/27/Qby3GMPrqSCuHxi.png)





![quicker_7043d0d6-61e5-415e-918c-fddc644ddb02.png](https://s2.loli.net/2022/02/27/txZNWu6Ozn32KqQ.png)

Spark默认受到全局并行度的限制，除了个别算子有特殊分区情况，大部分的算子，都会遵循全局并行度的要求，来规划自己的分区数。如果全局并行度是3，其实大部分算子分区都是3(否则可能影响内存计算管道长度)

注意：S**park我们一般推荐只设置全局并行度，不要再算子上设置并行度除了一些排序算子外，计算算子就让他默认开分区就可以了。**



### <span id="head50"> Spark是怎么做内存计算的？DAG的作用？Stage阶段划分的作用？</span>

- Spark会产生DAG图
- DAG图会基于分区和宽窄依赖关系划分阶段
- 一个阶段的内部都是窄依赖，窄依赖内，如果形成前后1:1的分区对应关系，就可以产生许多内存迭代计算的管道
- 这些内存迭代计算的管道，就是一个个具体的执行Task
- 一个Task是一个具体的线程，任务跑在一个线程内，就是走内存计算了。
  

### <span id="head51"> Spark为什么比MapReduce快</span>

- 编程模型上Spark占优（算子够多）

Spark的算子丰富，MapReduce.算子匮乏(Map和Reduce),MapReduce这个编程模型，很难在一套MR中处理复杂的任务,很多的复杂任务，是需要写多个MapReduce进行串联。多个MR串联通过磁盘交互数据

- 算子交互上，和计算上可以尽量多的内存计算而非磁盘迭代

Spark可以执行内存迭代，算子之间形成DAG基于依赖划分阶段后，在阶段内形成内存迭代管道。但是MapReduce的Map和Reduce之间的交互依旧是通过硬盘来交互的



## <span id="head52"> Spark的并行</span>

在同一时间内，有多少个task在同时运行。在有了6个task并行的前提下，rdd的分区就被规划成6个分区了。

```python
# 优先级从高到低：代码中、客户端提交参数中、配置文件中、默认(1，但是不会全部以1来跑，多数时候基于读取文件的分片数量来作为默认并行度)

'''全局并行度是推荐设置，不要针对RDD改分区，可能会影响内存迭代管道的构建，或者会产生额外的Shuffle'''

# 配置文件中：conf/spark-defaults.conf中设置
spark.default.parallelism 100
# 在客户端提交参数中：
bin/spark-submit --conf "spark.default.parallelism=100"
# 在代码中设置：
conf = SaprkConf()
conf.set("spark.default.parallelism","100")

'''针对RDD的并行度设置-不推荐'''

# 只能在代码中写，算子：
repartition
coalesce
partitionBy
```

集群中如何规划并行度

结论：设置为CPU总核心的2~10倍

比如集群可用CPU核心是100个，我们建议并行度是200~1000
确保是CPU核心的整数倍即可，最小是2倍，最大一般10倍或更高适量）均可

## <span id="head53"> Spark程序的调度</span>

流程如图：

- Driver被构建出来
- 构建Spark Context（执行环境入口对象）
- 基于DAG Scheduler(DAG调度器)构建逻辑Task分配
- 基于Task Scheduler(Task调度器)将逻辑Task分配到各个Executor.上干活，并监控它们
- Worker(Executor),被Task Scheduler管理监控，听从它们的指令干活，并定期汇报进度



DAG调度器工作内容：将逻辑的DAG图进行处理，最终得到**逻辑上的Task划分**

Task调度器工作内容：基于DAG Scheduler的产出，来**规划这些逻辑的task,应该在哪些物理的executor.上运行，以及监控管理它们的运行**



# <span id="head54">Spark Shuffle</span>

Spark在DAG调度阶段会将一个Job划分为多个Stage，上游Stage做map工作，下游Stage做reduce工作，其本质上还是MapReduce计算框架。Shuffle是连接map和reduce之间的桥梁，它将map的输出对应到reduce输入中，涉及到序列化反序列化、跨节点网络IO以及磁盘读写IO等。



Spark的Shuffle分为Write和Read两个阶段，分属于两个不同的Stage，前者是Parent Stage的最后一步，后者是Child Stage的第一步。



执行Shuffle的主体是Stage中的并发任务，这些任务分ShuffleMapTask和ResultTask两种，ShuffleMapTask要进行
Shuffle，ResultTask负责返回计算结果，一个Job中只有最后的Stage采用ResultTask，其他的均为ShuffleMapTask
。如果要按照map端和reduce端来分析的话，ShuffleMapTask可以即是map端任务，又是reduce端任务，因为
Spark中的Shuffle是可以串行的；ResultTask则只能充当reduce端任务的角色。







# <span id="head55"> SparkSQL</span>

SparkSQL 是Spark的一个模块, 用于处理海量结构化数据



- 融合性
  SQ可以无缝集成在代码中，随时用SQL处理数据
- 统一数据访问
  一套标准API可读写不同数据源
- Hive兼容
  可以使用SparkSQL直接计算并生成Hive数据表
- 标准化连接
  支持标准化DBC1ODBC连接，方便和各种数据库进行数据交互。

## <span id="head56"> SparkSQL和Hive的异同</span>

Hive和Spark均是：“分布式SQL计算引擎”均是构建大规模结构化数据计算的绝佳利器，同时SparkSQL拥有更好的性能。

都可以运行在YARN上

| SparkSQL          | Hive              |
| ----------------- | ----------------- |
| 内存计算          | 磁盘迭代          |
| 无元数据管理      | Metastore         |
| SQL/代码混合执行  | SQL               |
| 底层运行Spark RDD | 底层运行MapReduce |

## <span id="head57"> SparkSQL的数据抽象</span>

SparkSQL 其实有3类数据抽象对象

- SchemaRDD对象（已废弃）
- DataSet对象：可用于Java、Scala语言
- DataFrame对象：可用于Java、Scala、Python、R



- Pandas-DataFrame

  二维表数据结构

  单机（本地）集合

- SparkCore-RDD

  无标准数据结构，存储什么数据均可

  分布式集合（分区）

- SparkSQL-DataFrame

  二维表数据结构

  分布式集合（分区）

## <span id="head58"> 读取文件</span>

```python
# 构建SparkSession执行环境入口对象
spark = SparkSession.builder.appName("test").\
	master("local[*]").\
    config("spark.sql.shuffle.partitions", 2).\
	getOrCreate()
        """
    spark.sql.shuffle.partitions 在sql计算中, shuffle算子阶段默认的分区数是200个.
    对于集群模式来说, 200个默认也算比较合适
    如果在local下运行, 200个很多, 在调度上会带来额外的损耗
    所以在local下建议修改比较低 比如2\4\10均可
    这个参数和Spark RDD中设置并行度的参数 是相互独立的.
    """
        
# 通过SparkSession对象 获取 SparkContext对象
sc = spark.sparkContext

# 基于RDD转换成DataFrame
rdd = sc.textFile("../data/input/sql/people.txt").\
	map(lambda x: x.split(",")).\
	map(lambda x: (x[0], int(x[1])))
# 构建DataFrame对象
# 参数1 被转换的RDD
# 参数2 指定列名, 通过list的形式指定, 按照顺序依次提供字符串名称即可
df = spark.createDataFrame(rdd, schema=['name', 'age'])

# toDF的方式构建DataFrame
df1 = rdd.toDF(["name", "age"])

# 基于Pandas的DataFrame构建SparkSQL的DataFrame对象
pdf = pd.DataFrame(
    {
        "id": [1, 2, 3],
        "name": ["张大仙", "王晓晓", "吕不为"],
        "age": [11, 21, 11]
    }
)
df = spark.createDataFrame(pdf)
    
# 构建表结构的描述对象: StructType对象
schema = StructType().add("name", StringType(), nullable=True).\
	add("age", IntegerType(), nullable=False)
# 基于StructType对象去构建RDD到DF的转换
df = spark.createDataFrame(rdd, schema=schema)

# 构建StructType, text数据源, 读取数据的特点是, 将一整行只作为`一个列`读取, 默认列名是value 类型是String
schema = StructType().add("data", StringType(), nullable=True)
df = spark.read.format("text").\
	schema(schema=schema).\
	load("../data/input/sql/people.txt")

# JSON类型自带有Schema信息
df = spark.read.format("json").load("../data/input/sql/people.json")        

# 读取CSV文件   
df = spark.read.format("csv").\
        option("sep", ";").\
        option("header", True).\  
        option("encoding", "utf-8").\
        schema(StructType([
        StructField("name", StringType()),
        StructField("age", IntegerType()),
        StructField("job", StringType())])).\
        load("../data/input/sql/people.csv")    

# 读取parquet类型的文件
df = spark.read.format("parquet").load("../data/input/sql/users.parquet") 
```

## <span id="head59"> 方法</span>

```python
# 打印DataFrame的表结构
df.printSchema()

# 打印df中的数据
# 参数1 表示 展示出多少条数据, 默认不传的话是20
# 参数2 表示是否对列进行截断, 如果列的数据长度超过20个字符串长度, 后续的内容不显示以...代替
# 如果给False 表示不阶段全部显示, 默认是True
df.show(20, False)

# SQL 风格
spark.sql("""
        SELECT * FROM score WHERE name='语文' LIMIT 5
    """).show()

# DSL 风格
    df.groupBy("movie_id").\
        agg(
            F.count("movie_id").alias("cnt"),
            F.round(F.avg("rank"), 2).alias("avg_rank")
        ).where("cnt > 100").\
        orderBy("avg_rank", ascending=False).\
        limit(10).\
        show()
        # agg: 它是GroupedData对象的API, 作用是 在里面可以写多个聚合
        # alias: 它是Column对象的API, 可以针对一个列 进行改名
        # first: DataFrame的API, 取出DF的第一行数据,
        

 # Column对象的获取
id_column = df['id']
subject_column = df['subject']
# DLS风格演示
df.select(["id", "subject"]).show()
df.select("id", "subject").show()
df.select(id_column, subject_column).show()
# filter API
df.filter("score < 99").show()
df.filter(df['score'] < 99).show()
# where API
df.where("score < 99").show()
df.where(df['score'] < 99).show()
# group By API
df.groupBy("subject").count().show()
df.groupBy(df['subject']).count().show()
# df.groupBy API的返回值 GroupedData
# GroupedData对象 不是DataFrame
# 它是一个 有分组关系的数据结构, 有一些API供我们对分组做聚合
# SQL: group by 后接上聚合: sum avg count min man
# GroupedData 类似于SQL分组后的数据结构, 同样有上述5种聚合方法
# GroupedData 调用聚合方法后, 返回值依旧是DataFrame
# GroupedData 只是一个中转的对象, 最终还是要获得DataFrame的结果


# 注册成临时表
df.createTempView("score") # 注册临时视图(表)
df.createOrReplaceTempView("score_2") # 注册 或者 替换  临时视图
df.createGlobalTempView("score_3") # 注册全局临时视图 全局临时视图在使用的时候 需要在前面带上global_temp. 前缀
# 可以通过SparkSession对象的sql api来完成sql语句的执行
spark.sql("SELECT subject, COUNT(*) AS cnt FROM score GROUP BY subject").show()
spark.sql("SELECT subject, COUNT(*) AS cnt FROM score_2 GROUP BY subject").show()
spark.sql("SELECT subject, COUNT(*) AS cnt FROM global_temp.score_3 GROUP BY subject").show()


# withColumn方法
# 方法功能: 对已存在的列进行操作, 返回一个新的列, 如果名字和老列相同, 那么替换, 否则作为新列存在
df2 = df.withColumn("value", F.explode(F.split(df['value'], " ")))
withColumnRenamed("value", "word")
    

# 数据清洗: 数据去重
# dropDuplicates 是DataFrame的API, 可以完成数据去重
# 无参数使用, 对全部的列 联合起来进行比较, 去除重复值, 只保留一条
df.dropDuplicates().show()
df.dropDuplicates(['age', 'job']).show()

# 数据清洗: 数据去重
# dropDuplicates 是DataFrame的API, 可以完成数据去重
# 无参数使用, 对全部的列 联合起来进行比较, 去除重复值, 只保留一条
df.dropDuplicates().show()
df.dropDuplicates(['age', 'job']).show()

# 缺失值处理也可以完成对缺失值进行填充
# DataFrame的 fillna 对缺失的列进行填充
df.fillna("loss").show()
# 指定列进行填充
df.fillna("N/A", subset=['job']).show()
# 设定一个字典, 对所有的列 提供填充规则
df.fillna({"name": "未知姓名", "age": 1, "job": "worker"}).show()

spliti：切分字符串
F.split(被切分的列，切分字符串)
explode：数组转列
F.explode（被转换的列）
```

## <span id="head60"> 保存</span>

```python
# Write text 写出, 只能写出一个列的数据, 需要将df转换为单列df
df.select(F.concat_ws("---", "user_id", "movie_id", "rank", "ts")).\
        write.\
        mode("overwrite").\
        format("text").\
        save("../data/output/sql/text")

# Write csv
df.write.mode("overwrite").\
        format("csv").\
        option("sep", ";").\
        option("header", True).\
        save("../data/output/sql/csv")

# Write json
df.write.mode("overwrite").\
        format("json").\
        save("../data/output/sql/json")

# Write parquet
df.write.mode("overwrite").\
        format("parquet").\
        save("../data/output/sql/parquet")
```

## <span id="head61"> JDBC读写数据库</span>



对于windows:系统（使用本地解释器）(以Anaconda环境演示)
将jar包放在：Anaconda3的安装路径下\envs\虚拟环境\Lib\site-packages\pyspark\jars
对于Linux系统（使用远程解释器执行）(以Anaconda环境演示)
将jar包放在：Anaconda:3的安装路径下/envs/虚拟环境/Lib/python3.8/site-packages/pyspark/jars

```python
 # 1. 写出df到mysql数据库中
df.write.mode("overwrite").\
        format("jdbc").\
        option("url", "jdbc:mysql://n1:3306/bigdata?useSSL=false&useUnicode=true").\  
        # 使用useSSL=false确保连接可以正常连接（不使用SSL安全协议进行连接
        # 使用useUnicode=true来确保传输中不出现乱码
        option("dbtable", "movie_data").\
        # dbtable属性：指定写出的表名
        option("user", "root").\
        option("password", "123456").\
        save()
        # save()不要填参数，没有路径，是写出数据库

df2 = spark.read.format("jdbc"). \
        option("url", "jdbc:mysql://n1:3306/bigdata?useSSL=false&useUnicode=true"). \
        option("dbtable", "movie_data"). \
        # 读出来是自带schema,不需要设置schema,因为数据库就有schema
        option("user", "root"). \
        option("password", "123456"). \
        load()
        # load（）不需要加参数，没有路径，从数据库中读取的啊

df2.printSchema()
df2.show()
```

## <span id="head62"> UDF函数</span>

回顾Hive中自定义函数有三种类型：

- UDF(User-Defined-Function)函数

  一对一的关系，输入一个值经过函数以后输出一个值；

  在Hive中继承UDF类，方法名称为evaluate,返回值不能为void,其实就是实现一个方法；

- UDAF(User-Defined Aggregation Function)聚合函数

  多对一的关系，输入多个值输出一个值，通常与groupBy联合使用；

- UDTF(User-Defined Table-Generating Functions)函数

  一对多的关系，输入一个值输出多个值（一行变为多行）；

  用户自定义生成函数，有点像flatMap;



```python
sparksession.udf.register(参数1，参数2，参数3）
#  参数1：UDF名称，可用于SQL风格
#  参数2：被注册成UDF的方法名
#  参数3：声明UDF的返回值类型

udf对象 = F.udf(参数1， 参数2)
#  参数1：被注册成UDF的方法名
#  参数2：声明UDF的返回值类型
#  udf对象： 返回值对象，是一个UDF对象，可用于DSL风格
#  其中F是：from pyspark.sql import functions as F

# 构建一个RDD
rdd = sc.parallelize([["hadoop spark flink"], ["hadoop flink java"]])
df = rdd.toDF(["line"])
# 注册UDF, UDF的执行函数定义
def split_line(data):
    return data.split(" ")  # 返回值是一个Array对象
# TODO1 方式1 构建UDF
spark.udf.register("udf1", split_line, ArrayType(StringType()))
# SQL风格
df.createTempView("lines")
spark.sql("SELECT udf1(line) FROM lines").show(truncate=False)
# TODO 2 方式2的形式构建UDF
udf3 = F.udf(split_line, ArrayType(StringType()))
df.select(udf3(df['line'])).show(truncate=False)
                          
                          
                          
                          
    rdd = sc.parallelize([[1], [2], [3]])
    df = rdd.toDF(["num"])
    # 注册UDF
    def process(data):
        return {"num": data, "letters": string.ascii_letters[data]}
    """
    UDF的返回值是字典的话, 需要用StructType来接收
    """
    udf1 = spark.udf.register("udf1", process,
                              StructType().add("num", IntegerType(), nullable=True).\
                              add("letters", StringType(), nullable=True))
    df.selectExpr("udf1(num)").show(truncate=False)     
+---------+
|udf1(num)|
+---------+
|[1,b]    |
|[2,c]    |
|[3,d]    |
+---------+                          
                          

# 模拟UDAF 实现聚合
rdd = sc.parallelize([1, 2, 3, 4, 5], 3)
df = rdd.map(lambda x: [x]).toDF(['num'])

# 折中的方式 就是使用RDD的mapPartitions 算子来完成聚合操作
# 如果用mapPartitions API 完成UDAF聚合, 一定要单分区
single_partition_rdd = df.rdd.repartition(1)

def process(iter):
    sum = 0
    for row in iter:
        sum += row['num']

    return [sum]    # 一定要嵌套list, 因为mapPartitions方法要求的返回值是list对象

print(single_partition_rdd.mapPartitions(process).collect())                          
```



## <span id="head63"> SparkSQL的运行流程</span>



- RDD的运行会完全按照开发者的代码执行， 如果开发者水平有限，RDD的执行效率也会受到影响。

- 而SparkSQL会对写完的代码，执行“自动优化”， 以提升代码运行效率，避免开发者水平影响到代码执行效率。

- 为什么SparkSQL可以自动优化而RDD不可以？

​      RDD：内含数据类型不限格式和结构

​      DataFrame：100% 是二维表结构，可以被针对

- SparkSQL的自动优化，依赖于：Catalyst优化器



为了解决过多依赖Hive的问题，SparkSQL使用了一个新的SQL优化器替代Hive中的优化器，这个优化器就是Catalyst,.整个
SparkSQL的架构大致如下：

![quicker_b07a1325-9e59-4a0c-afba-140fc97781c6.png](https://s2.loli.net/2022/03/02/6uJdIHO8CqjZR4S.png)



![quicker_f1977f58-1c16-47d0-aacc-118b086edf58.png](https://s2.loli.net/2022/03/02/THNICV93ZSPMwae.png)

![quicker_c4104bcb-d353-463e-9cb1-28973c05df9e.png](https://s2.loli.net/2022/03/02/rGavj4XQfqEiY3P.png)

![quicker_53076345-f6b1-408b-833e-0d71ff1b3491.png](https://s2.loli.net/2022/03/02/oE1fRmpl5aP6HuA.png)



![quicker_2ac0ae6b-4bf7-456c-bc6b-d400721650c3.png](https://s2.loli.net/2022/03/02/LQsdebPEinUhxMH.png)

catalyst的各种优化细节非常多，大方面的优化点有2个：

- 谓词下推(Predicate Pushdown)\断言下推：将逻辑判断提前到前面，以减少shuffle阶段的数据量(行过滤，提前执行where)
- 列值裁剪(Column Pruning):将加载的列进行裁剪，尽量减少被处理数据的宽度(列过滤，提前规划select的字段数量，非常合适的存储系统：parquet)



## <span id="head64">Spark On Hive</span>



对于Spark来说，自身是一个执行引擎，但是Spark自己没有元数据管理功能，Spark完全有能力将SQL变成RDD提交

但是问题是，Person的数据在哪？Person有哪些字段？字段啥类型？Spark完全不知道了。不知道这些东西，如何翻译RDD运行。



在SparkSQL代码中可以写SQL那是因为，表是来自DataFrame注册的。DataFrame中有数据，有字段，有类型，足够Spark用来翻译RDD用。如果以不写代码的角度来看，SELECT*FROM person WHERE age>10。 spark无法翻译，因为没有元数据



![quicker_d86db96d-7abe-43ae-932f-97d6eacb6f23.png](https://s2.loli.net/2022/03/02/AaXZPsI1mk6Bf9i.png)



Spark On Hive就是把Hive的MetaStore服务拿过来给Spark做元数据管理用而已。



```python
 spark = SparkSession.builder.\
        appName("test").\
        master("local[*]").\
        config("spark.sql.shuffle.partitions", 2).\
        config("spark.sql.warehouse.dir", "hdfs://n1:8020/user/hive/warehouse").\
        config("hive.metastore.uris", "thrift://n1:9083").\
        enableHiveSupport().\
        getOrCreate()
    sc = spark.sparkContext
    
    spark.sql("SELECT * FROM student").show()

```

## <span id="head65"> 分布式SQL执行引擎</span>

Spark中有一个服务叫做：ThriftServer服务，可以启动并监听在10000端口，这个服务对外提供功能，我们可以用数据库工具或者代码连接上来直接写SQL即可操作spark



![quicker_68317947-afa8-4b40-abe3-2d0bef4e0b18.png](https://s2.loli.net/2022/03/03/Cg7LVHutzTpmYS3.png)

```shell
#如果是你们自己的虚拟机
#直接在r00t账户下启动即可
$SPARK_HOME/sbin/start-thriftserver.sh \
--hiveconf hive.server2.thrift.port=10000 \
--hiveconf hive.server2.thrift.bind.host=n1 \
--master local[2]
#masteri选择local,每一条sql都是local进程执行
#master选择yarn,每一条sql都是在YARN集群中执行


# 为了安装pyhive包需要安装一堆inux软件，执行如下命令进行linux:软件安装：
yum install zlib-devel bzip2-devel openssl-devel ncurses-devel sqlite-devel readline-devel tk-devel libffi-devel gcc make gcc-c++ python-devel cyrus-sasl-devel cyrus-sasl-plain cyrus-sasl-gssapi -y
# 安装好前置依赖软件后，安装pyhive包
/export/server/anaconda3/envs/pyspark/bin/python -m pip install -i https://pypi.tuna.tsinghua.edu.cn/simple pyhive pymysql sasl thrift thrift_sasl
```



```python
from pyhive import hive
if __name__ == '__main__':
    # 获取到Hive(Spark ThriftServer的链接)
    conn = hive.Connection(host="n1", port=10000, username="hadoop")
    # 获取一个游标对象
    cursor = conn.cursor()
    # 执行SQL
    cursor.execute("SELECT * FROM student")
    # 通过fetchall API 获得返回值
    result = cursor.fetchall()
    print(result)
j
```












































