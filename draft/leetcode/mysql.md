[TOC]
# 安装 
## win
>https://dev.mysql.com/downloads/mysql/
[http://mirrors.sohu.com/mysql/MySQL-8.0/](http://mirrors.sohu.com/mysql/MySQL-8.0/)
配置环境变量path
![](https://upload-images.jianshu.io/upload_images/18339009-6c5c4e1ade3e95ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-b6f751a6e0727c08.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
新建一个my.ini 用记事本打开
```js
[mysqld]
# 设置mysql的安装目录
basedir=D:\\software\\java\\mysql-5.7.28-winx64
# 切记此处一定要用双斜杠\\，单斜杠这里会出错。
# 设置mysql数据库的数据的存放目录
datadir=D:\\software\\java\\mysql-5.7.28-winx64\\Data
# 此处同上
# 允许最大连接数
max_connections=200
# 允许连接失败的次数。这是为了防止有人从该主机试图攻击数据库系统
max_connect_errors=10
# 服务端使用的字符集默认为UTF8
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 默认使用“mysql_native_password”插件认证
default_authentication_plugin=mysql_native_password
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8
[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3306
default-character-set=utf8
```
**管理员权限**下运行cmd

```
mysqld -install
# 执行初始化代码（会在根目录创建data文件夹，并创建root用户）
mysqld --initialize-insecure --user=mysql
# 启动mysql服务
net start mysql
# 关闭服务
net stop mysql
```
>![](https://upload-images.jianshu.io/upload_images/18339009-3b3965a4ecafa1f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>

绿色版

```
------------在D:\mysql\创建my.ini 

-----------zip版本不带默认的数据库文件，需进行数据库初始化，使用CMD管理员打开并
cd d:\mysql\bin
mysqld --defaults-file=d:\mysql\my.ini --initialize --console
！！！会创建默认数据库并设置默认的root密码，localhost：后面为默认的密码。

-----------初始化完成之后，需要启动mysql服务，输入一下命令:
"d:\mysql\bin\mysqld" --console    

-----------现在如果关闭了窗口，mysql服务就停止了，不适用与服务器环境，所以需要将mysql作为windows服务进行开机启动。先停止mysql。
"d:\mysql\bin\mysqld" -u root shutdown

-----------安装为windows服务，可以在install后面指定服务名，如果涉及一个服务器安装多个服务，可以在后面写mysql5或者mysql8

cd d:\mysql\bin
mysqld  --install

-----------将d:\mysql\bin 目录增加到系统环境变量path


-----------允许所有计算机远程链接mysql服务器，执行下面命令后可以从其他服务器远程mysql啦，程序也可以链接了。
use mysql
update user set host = '%' where user = 'root';
flush privileges;
```




##  centos

```
下载命令：
wget https://dev.mysql.com/get/mysql80-community-release-el7-2.noarch.rpm
用 yum 命令安装下载好的 rpm 包.
yum -y install mysql80-community-release-el7-2.noarch.rpm
安装 MySQL 服务器.
yum -y install mysql-community-server

启动 MySQL
systemctl start  mysqld.service
查看 MySQL 运行状态, Active 后面的状态代表启功服务后为 active (running), 停止后为 inactive (dead), 运行状态如图：
systemctl status mysqld.service

重新启动 Mysql 和停止 Mysql 的命令如下：
service mysqld restart  #重新启动 Mysql
systemctl stop mysqld.service   #停止 Mysql

此时 MySQL 已经开始正常运行, 不过要想进入 MySQL 还得先找出此时 root 用户的密码, 通过如下命令可以在日志文件中找出密码： 为了加强安全性, MySQL8.0 为 root 用户随机生成了一个密码, 在 error log 中, 关于 error log 的位置, 如果安装的是 RPM 包, 则默认是/var/log/mysqld.log.  只有启动过一次 mysql 才可以查看临时密码
使用命令:
grep 'temporary password' /var/log/mysqld.log

登录 root 用户
mysql -u root -p
修改密码为"123", 注意结尾要有分号, 表示语句的结束.
ALTER USER 'root'@'localhost' IDENTIFIED BY '123';

MySQL 完整的初始密码规则可以通过如下命令查看：
SHOW VARIABLES LIKE 'validate_password%'
如果想要设置简单的密码必须要修改约束, 修改两个全局参数：
validate_password_policy代表密码策略, 默认是 1：符合长度, 且必须含有数字, 小写或大写字母, 特殊字符. 设置为 0 判断密码的标准就基于密码的长度了. 一定要先修改两个参数再修改密码
set global validate_password.policy=0;
validate_password_length代表密码长度, 最小值为 4
set global validate_password.length=4; 

但此时还有一个问题, 就是因为安装了 Yum Repository, 以后每次 yum 操作都会自动更新, 如不需要更新, 可以把这个 yum 卸载掉：
[root@localhost ~]# yum -y remove mysql80-community-release-el7-2.noarch

设置表名为大小写不敏感:
1.用root帐号登录, 使用命令
systemctl stop mysqld.service
停止MySQL数据库服务，修改vi /etc/my.cnf，在[mysqld]下面添加
 lower_case_table_names=1
这里的参数 0 表示区分大小写，1 表示不区分大小写. 2.做好数据备份，然后使用下述命令删除数据库数据(删除后, 数据库将恢复到刚安装的状态)
rm -rf /var/lib/mysql
3.重启数据库 ,此时数据库恢复到初始状态.
service mysql start
4.重复安装时的操作, 查找临时密码
grep 'temporary password' /var/log/mysqld.log
5.修改密码. 密码8位以上, 大小写符号数据. 如想要使用简单密码, 需按照上述安装流程中的步骤进行操作.
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '****';update user set host = "%" where user='root';
6.刷新MySQL的系统权限相关表
FLUSH PRIVILEGES;
此时, MySQL的表名的大小写不再敏感.

设置表名为大小写不敏感:
1.用root帐号登录后, 使用命令
systemctl stop mysqld.service
停止MySQL数据库服务，修改vi /etc/my.cnf，在[mysqld]下面添加
 lower_case_table_names=1
这里的参数 0 表示区分大小写，1 表示不区分大小写. 2.做好数据备份，然后使用下述命令删除数据库数据(删除后, 数据库将恢复到刚安装的状态)
rm -rf /var/lib/mysql
3.重启数据库 ,此时数据库恢复到初始状态.
service mysql start
4.重复安装时的操作, 查找临时密码
grep 'temporary password' /var/log/mysqld.log
5.修改密码. 密码8位以上, 大小写符号数据. 如想要使用简单密码, 需按照上述安装流程中的步骤进行操作.
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '****';update user set host = "%" where user='root';
6.刷新MySQL的系统权限相关表
FLUSH PRIVILEGES;
此时, MySQL的表名的大小写不再敏感.
```



## ubuntu
```
sudo apt-get install mysql-server
#sudo apt-get install mysql-client
```
**配置远程连接**

```
1.将bind-address = 127.0.0.1注释掉
sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf
2.登录进数据库：
mysql -uroot -p123456
3.然后，切换到数据库mysql。SQL如下：
use mysql;
4.删除匿名用户。SQL如下：
delete from user where user='';
5.给root授予在任意主机（%）访问任意数据库的所有权限。SQL语句如下：
create user 'root'@'%' identified by '123456';
grant all privileges on *.* to 'root'@'%';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456'; 
6.退出数据库
exit
7.重启数据库：
sudo service mysql restart
```

**删除 mysql**

```
sudo apt-get autoremove --purge mysql-server
sudo apt-get remove mysql-server
sudo apt-get autoremove mysql-server
sudo apt-get remove mysql-common
```
**清理残留数据**
```
dpkg -l |grep ^rc|awk '{print $2}' |sudo xargs dpkg -P
```

进入mysql
```
sudo mysql -uroot -p 
```
配置 MySQL 的管理员密码：
```
sudo mysqladmin -u root password newpassword
```

安装MySQL-workbench
```
sudo apt-get install mysql-workbench
```
一旦安装完成，MySQL 服务器应该自动启动。您可以在终端提示符后运行以下命令来检查 MySQL 服务器是否正在运行：
```
sudo netstat -tap | grep mysql
```
当您运行该命令时，您可以看到类似下面的行：
tcp 0 0 localhost.localdomain:mysql *:* LISTEN -
如果服务器不能正常运行，您可以通过下列命令启动它：
```
sudo /etc/init.d/mysql restart
```

# 重置密码

```
net stop mysql，停止MySQL服务，

开启跳过密码验证登录的MySQL服务
 mysqld --console --skip-grant-tables --shared-memory 

再打开一个新的cmd，无密码登录MySQL
mysql -u root -p

密码置为空
    use mysql
    update user set authentication_string='' where user='root';
出mysql，执行命令：

    quit

关闭以-console --skip-grant-tables --shared-memory 启动的MySQL服务，

打开命令框，输入：net start mysql  启动MySQL服务，一管理员的身份运行cmd。

步骤4密码已经置空，所以无密码状态登录MySQL，输入登录命令：mysql -u root -p

利用上一篇博客中更改密码的命令，成功修改密码

ALTER USER 'root'@'localhost' IDENTIFIED BY '123';
```



# DBMS的种类

用来管理数据库的计算机系统称为数据库管理系统（Database Management System，DBMS）。

DBMS 主要通过数据的保存格式（数据库的种类）来进行分类，现阶段主要有以下 5 种类型.

- 层次数据库（Hierarchical Database，HDB）

- 关系数据库（Relational Database，RDB）

  - Oracle Database：甲骨文公司的RDBMS
  - SQL Server：微软公司的RDBMS
  - DB2：IBM公司的RDBMS
  - PostgreSQL：开源的RDBMS
  - MySQL：开源的RDBMS

  如上是5种具有代表性的RDBMS，其特点是由行和列组成的二维表来管理数据，这种类型的 DBMS 称为关系数据库管理系统（Relational Database Management System，RDBMS）。

- 面向对象数据库（Object Oriented Database，OODB）

- XML数据库（XML Database，XMLDB）

- 键值存储系统（Key-Value Store，KVS），举例：MongoDB



# 基本书写规则

- SQL语句要以分号（ ; ）结尾
- SQL 不区分关键字的大小写，但是插入到表中的数据是区分大小写的
- win 系统默认不区分表名及字段名的大小写
- linux / mac 默认严格区分表名及字段名的大小写 * 本教程已统一调整表名及字段名的为小写，以方便初学者学习使用。
- 注释是SQL语句中用来标识说明或者注意事项的部分。分为1行注释"-- "和多行注释两种"/* */"。


## 数据类型的指定

数据库创建的表，所有的列都必须指定数据类型，每一列都不能存储与该列数据类型不符的数据。

四种最基本的数据类型

- INTEGER 型

用来指定存储整数的列的数据类型（数字型），不能存储小数。

- CHAR 型

用来存储定长字符串，当列中存储的字符串长度达不到最大长度的时候，使用半角空格进行补足，由于会浪费存储空间，所以一般不使用。

- VARCHAR 型

用来存储可变长度字符串，定长字符串在字符数未达到最大长度时会用半角空格补足，但可变长字符串不同，即使字符数未达到最大长度，也不会用半角空格补足。

- DATE 型

用来指定存储日期（年月日）的列的数据类型（日期型）。

## 约束的设置

约束是除了数据类型之外，对列中存储的数据进行限制或者追加条件的功能。

`NOT NULL`是非空约束，即该列必须输入数据。

`PRIMARY KEY`是主键约束，代表该列是唯一值，可以通过该列取出特定的行的数据。

## 执行顺序

在加入窗口函数的基础上SQL的执行顺序也会发生变化，具体的执行顺序如下（window就是窗口函数）
![](https://upload-images.jianshu.io/upload_images/18339009-cd689024f076a36a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## DDL

DDL（Data Definition Language，数据定义语言） 用来**创建或者删除存储数据用的数据库以及数据库中的表**等对象。DDL 包含以下几种指令。

- CREATE ： 创建数据库和表等对象
- DROP ： 删除数据库和表等对象
- ALTER ： 修改数据库和表等对象的结构

## DML

DML（Data Manipulation Language，数据操纵语言） 用来**查询或者变更**表中的记录。DML 包含以下几种指令。

- SELECT ：查询表中的数据
- INSERT ：向表中插入新数据
- UPDATE ：更新表中的数据
- DELETE ：删除表中的数据

## DCL

DCL（Data Control Language，数据控制语言） 用来**确认或者取消对数据库中的数据进行的变更**。除此之外，还可以对 RDBMS 的用户是否有权限操作数据库中的对象（数据库表等）进行设定。DCL 包含以下几种指令。

- COMMIT ： 确认对数据库中的数据进行的变更
- ROLLBACK ： 取消对数据库中的数据进行的变更
- GRANT ： 赋予用户操作权限
- REVOKE ： 取消用户的操作权限



# 增删改查库

## 增加(CREATE)
```mysql
CREATE DATABASE < 数据库名称 > default character utf8;
```
## 删除(DROP)
```mysql
DROP DATABASE 库名 ;
```
## 修改(ALTER) 
```mysql
ALTER DATABASE 库名  default character gbk;
```
## 查询(SHOW)
```mysql
SHOW DATABASE 
```
## 选择数据库
```mysql
USE 库名 ;
```

# 增删改查表


## 新建表(CREATE) 
```mysql
CREATE TABLE < 表名 >
( < 列名 1> < 数据类型 > < 该列所需约束 > ,
  < 列名 2> < 数据类型 > < 该列所需约束 > ,
  < 列名 3> < 数据类型 > < 该列所需约束 > ,
  < 列名 4> < 数据类型 > < 该列所需约束 > ,
  < 该表的约束 1> , < 该表的约束 2> ,……);
  
CREATE TABLE 表名 (
  id int(10) unsigned NOT NULL COMMENT 'Id',
  username varchar(64) NOT NULL DEFAULT 'default' COMMENT '用户名',
  password varchar(64) NOT NULL DEFAULT 'default' COMMENT '密码',
  email varchar(64) NOT NULL DEFAULT 'default' COMMENT '邮箱'
) COMMENT='用户表';

```
**复制表**

```mysql
CREATE TABLE 新表
AS
SELECT * FROM 旧表 
```

## 删除(DROP)

ALTER TABLE 语句和 DROP TABLE 语句一样，执行之后无法恢复。

```mysql
DROP TABLE user;
```
## 修改表名称(ALTER  RENAME)
``` sql
ALTER TABLE user RENAME user_new;
```
# 增删改查字段

## 添加字段(ALTER ADD COLUMN)
```mysql
ALTER TABLE user ADD COLUMN age int(3);
```

## 删除字段(ALTER DROP COLUMN)  
```mysql
ALTER TABLE user DROP COLUMN age;
```
## 修改字段类型(ALTER MODIFY)
```mysql
ALTER TABLE user MODIFY 字段名 新的字段类型;
```
mysql 设置字段 not null 变成可以null

```mysql
ALTER TABLE 表名 MODIFY 字段名 VARCHAR(20) DEFAULT NULL
```
## 修改字段名称(ALTER CHANGE)
``` sql
ALTER TABLE user CHANGE 旧字段名 新字段名 字段类型;
```

## 显示表字段信息
```mysql
DESC user;
```

# 管理数据
## 添加主键(ALTER ADD PRIMARY KEY)
```mysql
ALTER TABLE user ADD PRIMARY KEY (id);
```
**删除主键**(ALTER DROP PRIMARY KEY)

```mysql
ALTER TABLE user DROP PRIMARY KEY;
```
查询表的大小：
```mysql
use information_schema;  
select data_length,index_length 
from tables 
where table_schema=库 and table_name = 表 ;
```

## 添加数据(INSERT INTO)
插入完整的行
```mysql
INSERT INTO user VALUES (10, 'root', 'root', 'xxxx@163.com');

-- 多行INSERT （ DB2、SQL、SQL Server、 PostgreSQL 和 MySQL多行插入）
INSERT INTO productins VALUES ('0002', '打孔器', '办公用品', 500, 320, '2009-09-11'),
                              ('0003', '运动T恤', '衣服', 4000, 2800, NULL),
                              ('0004', '菜刀', '厨房用具', 3000, 2800, '2009-09-20');  
-- Oracle中的多行INSERT
INSERT ALL INTO productins VALUES ('0002', '打孔器', '办公用品', 500, 320, '2009-09-11')
           INTO productins VALUES ('0003', '运动T恤', '衣服', 4000, 2800, NULL)
           INTO productins VALUES ('0004', '菜刀', '厨房用具', 3000, 2800, '2009-09-20')
SELECT * FROM DUAL;  
-- DUAL是Oracle特有（安装时的必选项）的一种临时表A。因此“SELECT *FROM DUAL” 部分也只是临时性的，并没有实际意义。  
```
插入行的一部分
```mysql
INSERT INTO user(username, password, email) VALUES ('admin', 'admin', 'xxxx@163.com');
```
**插入查询出来的数据**

```mysql
INSERT INTO user(字段) SELECT 字段 FROM account;
```
```mysql
SELECT * INTO new_表名 FROM 表名

// MySQL 数据库不支持 SELECT ... INTO 语句，但支持 INSERT INTO ... SELECT 。
INSERT INTO p1  SELECT * FROM product;
```

## 删除(DELETE FROM)
### DELETE FROM
1)可以**带条件删除**2）只能删除表的数据，**不能删除表的约束**3)删除的数据**可以回滚（事务）**
```mysql
DELETE FROM user WHERE username='robot';
```
### truncate
1）不能带条件删除 2）可以删除表的数据，也可以删除表的约束 3）不能回滚

```mysql
TRUNCATE TABLE user;
```

>总结：
>1、在速度上，一般来说，drop> truncate > delete。
>2、在使用drop和truncate时一定要注意，虽然可以恢复，但为了减少麻烦，还是要慎重。
>3、如果想删除部分数据用delete，注意带上where子句，回滚段要足够大；
>如果想**删除表**，当然用drop； 
>如果想**保留表而将所有数据删除**，如果**和事务无关**，用truncate即可；
>如果**和事务有关，或者想触发trigger**，还是用delete；
>如果是整理表内部的碎片，可以用truncate跟上reuse stroage，再重新导入/插入数据。
>

希望删除表结构时，用 drop;
希望保留表结构，但要删除所有记录时， 用 truncate;
希望保留表结构，但要删除部分记录时， 用 delete。

## 修改(UPDATE SET)
``` sql
UPDATE user
SET username='robot', password='robot'
WHERE username = 'root';

-- 合并后的写法
UPDATE product
   SET sale_price = sale_price * 10,
       purchase_price = purchase_price / 2
 WHERE product_type = '厨房用具';  
```
## 查询
所有字段：
``` sql
select * from 表;
```
指定字段：
``` sql
select 字段 from 表;
```
指定别名： 
```mysql
select 字段1 as 别名 from 表;
```
合并列：
``` sql
select (字段1+字段2) as “和” from 表;
```
去重： 
```mysql
select distinct 字段 from 表;
```

# 特殊字符
## 逻辑

**and、 or、in、not  in** 

```select * from 表 where 条件1 and/or 条件2```
```IN ("A", "B", "C")```     ––使用IN 和 NOT IN 时是无法选取出NULL数据的。

## 比较

 **> 、 <  、>= 、 <= 、 =、  !=、 <>、 between  and** 

```select * from 表 where 字段>=条件;```

## 模糊 

**like  、%（替换任意个字符）、  _（替换一个字符）**

```SELECT * FROM student WHERE NAME LIKE '李%';```

## 谓词

返回值为真值的函数。包括`TRUE / FALSE / UNKNOWN`。

谓词主要有以下几个：

- LIKE

- BETWEEN

- IS NULL、IS NOT NULL  

  **NULL 只能使用 IS NULL 和 IS NOT NULL 来进行判断。**

- IN

- EXISTS

  谓词的作用就是 **“判断是否存在满足某种条件的记录”**。如果存在这样的记录就返回真（TRUE），如果不存在就返回假（FALSE）。

##  判断

**case when 条件 then 真的操作 else 假的操作 end**

**if(条件, 真的操作, 假的操作)**

```mysql
CASE WHEN product_type = '衣服' THEN CONCAT('A ： ',product_type)
 WHEN product_type = '办公用品'  THEN CONCAT('B ： ',product_type)
 WHEN product_type = '厨房用具'  THEN CONCAT('C ： ',product_type)
 ELSE NULL
END
```

## 聚合函数： 
聚合函数的使用前提是结果集已经确定，而WHERE还处于确定结果集的过程中，所以相互矛盾会引发错误。 如果**想指定条件，可以在SELECT，HAVING（下面马上会讲）以及ORDER BY子句中使用聚合函数。**

```sum()、avg() 、 max()  、min() 、 count()```

**count()**

> COUNT(常量) 和 COUNT(*)表示的是直接查询符合条件的数据库表的行数。而COUNT(列名)表示的是查询符合条件的列的值不为NULL的行数。

```SELECT SUM(servlet) AS 'servlet的总成绩' FROM student;```
```SELECT COUNT(*) FROM student;```

```mysql
-- 计算去除重复数据后的数据行数
SELECT COUNT(DISTINCT product_type)
 FROM product;
```

```concat```将A和B按顺序连接在一起的字符串
```split(str, regex)```将string类型数据按regex提取，分隔后转换为array。
```substr（str,0,len) ```截取字符串从0位开始的长度为len个字符。
`SUBSTRING_INDEX (原始字符串， 分隔符，n)`获取原始字符串按照分隔符分割后，第 n 个分隔符之前（或之后）的子字符串，支持正向和反向索引，索引起始值分别为 1 和 -1。
```LOCATE()```查找某字符在长字符中的位置
```LEFT()、RIGHT()```左边或者右边的字符
```LOWER()、UPPER()```转换为小写或者大写
```LTRIM()、RTIM()```去除左边或者右边的空格
```LENGTH()```长度
```SOUNDEX()```转换为语音值
其中， SOUNDEX() 可以将一个字符串转换为描述其语音表示的字母数字模式。

```mysql
SELECT *
FROM mytable
WHERE SOUNDEX(col1) = SOUNDEX('apple')
```

常用的日期提取函数包括 ```year()/month()/day()/hour()/minute()/second()```

CURRENT_DATE -- 获取当前日期
CURRENT_TIME -- 当前时间
CURRENT_TIMESTAMP -- 当前日期和时间
EXTRACT(日期元素 FROM 日期)-- 截取日期元素
```EXTRACT(YEAR   FROM CURRENT_TIMESTAMP) AS year```


```AddDate()```增加一个日期（天、周等）
```AddTime()```增加一个时间（时、分等）
```CurDate()```返回当前日期
```CurTime()```返回当前时间
```Date()```返回日期时间的日期部分
```to_date("1970-01-01 00:00:00")```把时间的字符串形式转化为时间类型，再进行后续的计算；
```datediff(enddate,stratdate) ```计算两个时间的时间差（day)；
```date_sub(stratdate,days) ```返回开始日期startdate减少days天后的日期。

```mysql
SELECT ADDDATE("2017-06-15", INTERVAL 10 DAY);
```
```date_add(startdate,days) ```返回开始日期startdate增加days天后的日期。
```Date_Format()```返回一个格式化的日期或时间串
```date(paidTime).date_format(paidTime，%Y-%m-d%)```

`CAST（转换前的值 AS 想要转换的数据类型）` -- 将NULL转换为其他值

```
SELECT CAST('0001' AS SIGNED INTEGER) AS int_col;
```

`COALESCE(数据1，数据2，数据3……)` -- 返回可变参数 A 中左侧开始第 1个不是NULL的值



```SIN()```正弦
```COS()```余弦
```TAN()```正切
```ABS()```绝对值
```SQRT()```平方根
```MOD()```余数
```EXP()```指数
```PI()```圆周率
```RAND()```随机数
```percentile()``` 百分位函数



## 分组查询：group by 

使用COUNT等聚合函数时，**SELECT子句中如果出现列名，只能是GROUP BY子句中指定的列名**（也就是聚合键）。

SELECT子句中可以通过AS来指定别名，但在**GROUP BY中不能使用别名**。因为在DBMS中 ,SELECT子句在GROUP BY子句后执行。

**group by 后可加聚合函数，where 后不能加聚合函数**

```mysql
-- 按照商品种类统计数据行数
SELECT product_type, COUNT(*)
  FROM product
 GROUP BY product_type;
--- error
SELECT product_name, COUNT(*)
  FROM product
 GROUP BY purchase_price;
```

## 分组后筛选： having 

WHERE子句只能指定记录（行）的条件，而不能用来指定组的条件（例如，“数据行数为 2 行”或者“平均值为 500”等）。

可以使用**数字、聚合函数和GROUP BY中指定的列名**（聚合键）。

```mysql
SELECT product_type, COUNT(*)
  FROM product
 GROUP BY product_type
HAVING COUNT(*) = 2;

-- 错误形式（因为product_name不包含在GROUP BY聚合键中）
SELECT product_type, COUNT(*)
  FROM product
 GROUP BY product_type
HAVING product_name = '圆珠笔';
```

## DISTINCT 唯一

- 想要计算值的种类时，可以在COUNT函数的参数中使用DISTINCT。

```
SELECT DISTINCT Director FROM movies ASC;
```

## 分页查询：limit offset
起始行,查询行数起始行从0开始
把结果集分页，每页3条记录。要获取第1页的记录

```
SELECT * FROM student LIMIT 3 OFFSET 0;
```

## 排序： order by 

- ORDER BY中列名可使用别名

asc: 正序（默认）desc：反序
```mysql
SELECT * 
FROM movies 
ORDER BY Director ASC,Year DESC 
LIMIT 10 OFFSET 0;
```

# 视图

视图是一个**虚拟的表**，不同于直接操作数据表，视图是依据SELECT语句来创建的，**将频繁使用的SELECT语句保存以提高效率**。

1. 简单

   对于使用视图的用户不需要关心表的结构、关联条件和筛选条件。因为这张虚拟表中保存的就是已经过滤好条件的结果集
2. 安全

   视图可以设置权限 , 致使访问视图的用户只能访问他们被允许查询的结果集
3. 数据独立

   一旦视图的结构确定了，可以屏蔽表结构变化对用户的影响，源表增加列对视图没有影响；源表修改列名，则可以通过修改视图来解决，不会造成对访问者的影响

```mysql
CREATE VIEW <视图名称>(<列名1>,...) AS <SELECT语句>

-- 创建一个视图。将查询出来的结果保存到这张虚拟表中，查询城市和所属国家
CREATE
VIEW
	city_country (city_id,city_name,cid,country_name) 
AS
	SELECT t1.*,t2.country_name FROM city t1,country t2 WHERE t1.cid=t2.id;
	
-- 查询视图。查询这张虚拟表，就等效于查询城市和所属国家
SELECT * FROM city_country;
-- 查询所有数据表，视图也会查询出来
SHOW TABLES;


```

 需要注意的是**视图名在数据库中需要是唯一的，不能与其他视图和表重名。在创建视图时也尽量使用限制不允许通过视图来修改表**

视图不仅可以基于真实表，我们也可以在视图的基础上继续创建视图。虽然在视图上继续创建视图的语法没有错误，但是我们还是应该尽量避免这种操作。这是因为对多数 DBMS 来说， **多重视图会降低 SQL 的性能**。

需要注意的是**在一般的DBMS中定义视图时不能使用ORDER BY语句**。下面这样定义视图是错误的。

```mysql
CREATE VIEW productsum (product_type, cnt_product)
AS
SELECT product_type, COUNT(*)
  FROM product
 GROUP BY product_type
 ORDER BY product_type;
```

为什么不能使用 ORDER BY 子句呢？这是**因为视图和表一样，数据行都是没有顺序的**。

*在 MySQL中视图的定义是允许使用 ORDER BY 语句的，但是若从特定视图进行选择，而该视图使用了自己的 ORDER BY 语句，则视图定义中的 ORDER BY 将被忽略。*

## 修改视图

因为视图是一个虚拟表，所以对视图的操作就是对底层基础表的操作，所以在修改时只有满足底层基本表的定义才能成功修改。

对于一个视图来说，如果包含以下结构的任意一种都是不可以被更新的：

- 聚合函数 SUM()、MIN()、MAX()、COUNT() 等。
- DISTINCT 关键字。
- GROUP BY 子句。
- HAVING 子句。
- UNION 或 UNION ALL 运算符。
- FROM 子句中包含多个表。

视图归根结底还是从表派生出来的，因此，如果原表可以更新，那么 视图中的数据也可以更新。反之亦然，如果视图发生了改变，而原表没有进行相应更新的话，就无法保证数据的一致性了。



```mysql
-- 修改视图2的列名city_id为id
ALTER
VIEW
	city_country2 (id,city_name,cid,country_name)
AS
	SELECT t1.*,t2.country_name FROM city t1,country t2 WHERE t1.cid=t2.id;


-- 修改视图表中的城市名称北京为北京市
UPDATE city_country 
SET city_name='北京市' 
WHERE city_name='北京';
```

## 删除视图

```mysql
DROP VIEW <视图名1> [ , <视图名2> …]
-- 注意：需要有相应的权限才能成功删除。
DROP VIEW city_country;
```



## 子查询和视图的关系

子查询就是将用来定义视图的 SELECT 语句直接用于 FROM 子句当中。其中AS studentSum可以看作是子查询的名称，而且由于子查询是一次性的，所以子查询不会像视图那样保存在存储介质中， 而是在 SELECT 语句执行之后就消失了。

# 连接查询（多表查询）
## 笛卡尔积查询

把两个表中具有相同 主键ID的数据连接起来

**单纯的select * from a,b是笛卡尔乘积**。比如a表有5条数据，b表有3条数据，那么最后的结果有5*3=15条数据。

但是如果对两个表进行关联:select * from a,b where a.id = b.id 意思就变了，此时就等价于：select * from a inner join b on a.id = b.id。即就是**内连接**。
![](https://upload-images.jianshu.io/upload_images/18339009-a79c08f891013866.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

INNER JOIN 产生的结果是AB的交集。
FULL [OUTER] JOIN 产生A和B的并集。
LEFT [OUTER] JOIN 产生表A的完全集，而B表中匹配的则有值，没有匹配的则以null值取代。
RIGHT [OUTER] JOIN 产生表B的完全集，而A表中匹配的则有值，没有匹配的则以null值取代

CROSS JOIN 把表A和表B的数据进行一个N*M的组合，即笛卡尔积。

## 内连接查询

**INNER JOIN：只保留两张表中完全匹配的结果集**

```mysql
-- 标准语法
SELECT 列名 FROM 表名1 [INNER] JOIN 表名2 ON 条件;
-- 隐式内连接语法
SELECT 列名 FROM 表名1,表名2 WHERE 条件;


SELECT p.LastName, p.FirstName, o.OrderNo
FROM Persons p
INNER JOIN Orders o
ON p.Id_P=o.Id_P and 1=1　　
ORDER BY p.LastName
```
![](https://upload-images.jianshu.io/upload_images/18339009-798cff79aafdee78.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 左连接查询

- 查询左表的全部数据，和左右两张表有交集部分的数据

```mysql
-- 标准语法
SELECT 列名 FROM 表名1 LEFT [OUTER] JOIN 表名2 ON 条件;

SELECT p.LastName, p.FirstName, o.OrderNo
FROM Persons p
LEFT JOIN Orders o
ON p.Id_P=o.Id_P
ORDER BY p.LastName
```
![](https://upload-images.jianshu.io/upload_images/18339009-7951f83402cc6026.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 右连接

- 查询右表的全部数据，和左右两张表有交集部分的数据

```mysql
SELECT p.LastName, p.FirstName, o.OrderNo
FROM Persons p
RIGHT JOIN Orders o
ON p.Id_P=o.Id_P
ORDER BY p.LastName
```
![](https://upload-images.jianshu.io/upload_images/18339009-fd7eaa8cc12522ed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 全连接查询

**FULL JOIN ,返回左表和右表中所有的行。**

```mysql
SELECT p.LastName, p.FirstName, o.OrderNo
FROM Persons p
FULL JOIN Orders o
ON p.Id_P=o.Id_P
ORDER BY p.LastName
```
![](https://upload-images.jianshu.io/upload_images/18339009-e11d28c6df0d7187.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 子查询

- 查询语句中嵌套了查询语句。我们就将嵌套查询称为子查询！


```mysql
 -- 结果是单行单列的可以作为条件，使用运算符进行判断！
 -- 根据查询出来的最高年龄，查询姓名和年龄
 SELECT NAME,age FROM USER WHERE age = (SELECT MAX(age) FROM USER);
 
 
 -- 结果是多行单列的可以作为条件，使用运算符in或not in进行判断！
 -- 根据id查询订单
SELECT number,uid FROM orderlist WHERE uid IN (SELECT id FROM USER WHERE NAME='张三' OR NAME='李四');

-- 结果是多行多列的可以作为一张虚拟表参与查询！
-- 查询订单表中id大于4的订单信息和所属用户信息
SELECT * FROM USER u,(SELECT * FROM orderlist WHERE id>4) o WHERE u.id=o.uid;
```

## 自关联查询



- 同一张表中有数据关联。可以多次查询这同一个表！

```mysql
  -- 查询所有员工的姓名及其直接上级的姓名，没有上级的员工也需要查询
  /*
  分析：
  	员工姓名 employee表        直接上级姓名 employee表
  	条件：employee.mgr = employee.id
  	查询左表的全部数据，和左右两张表交集部分数据，使用左外连接
  */
  SELECT
  	t1.name,	-- 员工姓名
  	t1.mgr,		-- 上级编号
  	t2.id,		-- 员工编号
  	t2.name     -- 员工姓名
  FROM
  	employee t1  -- 员工表
  LEFT OUTER JOIN
  	employee t2  -- 员工表
  ON
  	t1.mgr = t2.id;
```

# UNION 
将两个或更多查询的结果组合起来，并生成一个结果集，通常**返回的列名取自第一个查询。**

所有查询的**列数和列顺序**必须相同。每个查询中涉及表的**列的数据类型**必须相同或兼容。

**默认会去除相同行**，如果需要保留相同行，使用 UNION ALL。

**只能包含一个 ORDER BY 子句，并且必须位于语句的最后。**

在一个查询中从不同的表返回结构数据。
对一个表执行多个查询，按一个查询返回数据。
组合查询

```mysql
SELECT cust_name, cust_contact, cust_email
FROM customers
WHERE cust_state IN ('IL', 'IN', 'MI')
UNION
SELECT cust_name, cust_contact, cust_email
FROM customers
WHERE cust_name = 'Fun4All';
````


## JOIN vs UNION
JOIN 中连接表的列可能不同，但在 UNION 中，**所有查询的列数和列顺序必须相同。**



**UNION 将查询之后的行放在一起（垂直放置），但 JOIN 将查询之后的列放在一起（水平放置）**



# 远程连接
```shell
## 查看当前的用户
USE mysql;
SELECT * FROM user;

## 修改密码
@前用户名@后地址（ % 代表可以任意ip访问）
mysql ALTER USER "root"@"localhost" IDENTIFIED  BY "root";

## 创建新用户
CREATE USER 'new_user'@'%' IDENTIFIED BY 'passwd';

## 给用户赋权限
这里我赋的是全部的权限，*.* 表示数据库库的所有库和表，对应权限存储在mysql.user表中
GRANT ALL ON *.* TO 'new_user'@'%';


GRANT SELECT, UPDATE ON day16.employee TO 'eric'@'localhost' IDENTIFIED BY '123456';

## 刷新权限
flush privileges;

## 取消远程控制
update user set host='localhost' where user='用户名';
```

## 删除用户
```delete from user where user="用户名" and host='host权限（localhost/%）';```

>--开放3306端口--
1.控制面板—系统和安全—windows防火墙—攻击设置—入栈规则
2.新建规则—选择端口
![](https://upload-images.jianshu.io/upload_images/18339009-e649fed7e43e6b95.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3.指定开放的端口
![](https://upload-images.jianshu.io/upload_images/18339009-a40b2644f02b64a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4.允许连接，一直点下一步即可
![](https://upload-images.jianshu.io/upload_images/18339009-4610a319e4a4f648.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)






# PyMySQL模块
是默认开启MySQL的事务功能的，
因此，进行 "增"、 "删"、"改"的时候，一定要使用db.commit()提交事务
一定要使用try…except…语句，因为万一没插入成功，其余代码都无法执行。当语句执行不成功，
我们就db.rollback()回滚到操作之前的状态；当语句执行成功，我们就db.commit()提交事务。

```python
import pymysql 
# 使用pymysql连接上mysql数据库服务器，创建了一个数据库对象；
db=pymysql.connect(host='localhost',user='root', password='',
                   port=3306, db='test', charset='utf8')
# 开启mysql的游标功能，创建一个游标对象；
cursor = db.cursor()

# 建表语句；
sql = """create table person(
        id int auto_increment primary key not null,
        name varchar(10) not null,
        age int not null) charset=utf8"""
# 执行sql语句；
cursor.execute(sql)

# 一次性插入一条数据；
name = "猪八戒"
age = 8000
sql = """
insert into person(name,age) values ("猪八戒",8000)
"""
try:
    cursor.execute(sql)
    db.commit()
    print("插入成功")
except:
    print("插入失败")
    db.rollback()

# 要执行的SQL语句；
sql = "select * from person"
# execute(query, args)：执行单条sql语句，接收的参数为sql语句本身和使用的参数列表，返回值为受影响的行数；
# executemany(query, args)：执行单条sql语句，但是重复执行参数列表里的参数，返回值为受影响的行数；
cursor.execute(sql)
# fetchone()：返回一条结果行；
# fetchmany(size)：接收size条返回结果行。如果size的值大于返回的结果行的数量，则会返回cursor.arraysize条数据；
# fetchall()：接收全部的返回结果行；
data = cursor.fetchone()
print(data)
#关闭游标
cursor.close()    
# 关闭数据库连接
db.close()
```
# pandas
中的read_sql()方法，将提取到的数据直接转化为DataFrame，进行操作
```python
df1 = pd.read_sql("select * from student where ssex='男'",db)
display(df1)
df2 = pd.read_sql("select * from student where ssex='女'",db)
display(df2)
```

# 数据约束（表约束）

| 约束                          | 说明           |
| ----------------------------- | -------------- |
| PRIMARY KEY                   | 主键约束       |
| PRIMARY KEY AUTO_INCREMENT    | 主键、自动增长 |
| UNIQUE                        | 唯一约束       |
| NOT NULL                      | 非空约束       |
| FOREIGN KEY                   | 外键约束       |
| FOREIGN KEY ON UPDATE CASCADE | 外键级联更新   |
| FOREIGN KEY ON DELETE CASCADE | 外键级联删除   |

- 主键约束特点
  - 主键约束包含：非空和唯一两个功能
  - 一张表只能有一个列作为主键

```mysql
-- 删除主键
 ALTER TABLE student DROP PRIMARY KEY;
 -- 添加主键
 ALTER TABLE student MODIFY id INT PRIMARY EKY;

-- 删除唯一约束
ALTER TABLE student DROP INDEX id;
-- 添加唯一约束
ALTER TABLE student MODIFY id INT UNIQUE;
 
 -- 删除自动增长
ALTER TABLE student MODIFY id INT;
-- 添加自动增长
ALTER TABLE student MODIFY id INT AUTO_INCREMENT;

-- 删除非空约束
ALTER TABLE student MODIFY id INT;
-- 添加非空约束
ALTER TABLE student MODIFY id INT NOT NULL;
```

- 外键约束

  - 让表和表之间产生关系，从而保证数据的准确性！

```mysql
CONSTRAINT 外键名 FOREIGN KEY (本表外键列名) REFERENCES 主表名(主表主键列名)

-- 删除外键
ALTER TABLE orderlist DROP FOREIGN KEY ou_fk1;
-- 添加外键约束
ALTER TABLE orderlist ADD CONSTRAINT ou_fk1 FOREIGN KEY (uid) REFERENCES USER(id);
```

```mysql
-- 创建user用户表
CREATE TABLE USER(
	id INT PRIMARY KEY AUTO_INCREMENT,    -- id
	NAME VARCHAR(20) NOT NULL             -- 姓名
);
-- 添加用户数据
INSERT INTO USER VALUES (NULL,'张三'),(NULL,'李四'),(NULL,'王五');

-- 创建orderlist订单表
CREATE TABLE orderlist(
	id INT PRIMARY KEY AUTO_INCREMENT,    -- id
	number VARCHAR(20) NOT NULL,          -- 订单编号
	uid INT,                              -- 外键字段，订单所属用户
	CONSTRAINT ou_fk1 FOREIGN KEY (uid) REFERENCES USER(id)   -- 添加外键约束
);
-- 添加订单数据
INSERT INTO orderlist VALUES (NULL,'hm001',1),(NULL,'hm002',1),
(NULL,'hm003',2),(NULL,'hm004',2),
(NULL,'hm005',3),(NULL,'hm006',3);

-- 添加一个订单，但是没有所属用户。无法添加
INSERT INTO orderlist VALUES (NULL,'hm007',8);
-- 删除王五这个用户，但是订单表中王五还有很多个订单呢。无法删除
DELETE FROM USER WHERE NAME='王五';
```

- 级联更新和级联删除
  - 当我想把user用户表中的某个用户删掉，我希望该用户所有的订单也随之被删除
  - 当我想把user用户表中的某个用户id修改，我希望订单表中该用户所属的订单用户编号也随之修改

```mysql
-- 添加外键约束，同时添加级联更新和级联删除
ALTER TABLE orderlist ADD CONSTRAINT ou_fk1 FOREIGN KEY (uid) REFERENCES USER(id) ON UPDATE CASCADE ON DELETE CASCADE;

-- 将王五用户的id修改为5    订单表中的uid也随之被修改
UPDATE USER SET id=5 WHERE id=3;
-- 将王五用户删除     订单表中该用户所有订单也随之删除
DELETE FROM USER WHERE id=5;
```





### 多表设计

- 一对一
人和身份证！
在任意一个表建立外键，去关联另外一个表的主键

- 一对多

一个用户可以有多个订单！。一个分类下可以有多个商品！
在多的一方，建立外键约束，来关联另一方主键

- 多对多

学生和课程。一个学生可以选择多个课程，一个课程也可以被多个学生选择！
需要借助第三张表中间表，中间表至少包含两个列，这两个列作为中间表的外键，分别关联两张表的主键

```mysql
-- 创建student表
CREATE TABLE student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20)
);
-- 添加数据
INSERT INTO student VALUES (NULL,'张三'),(NULL,'李四');

-- 创建course表
CREATE TABLE course(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(10)
);
-- 添加数据
INSERT INTO course VALUES (NULL,'语文'),(NULL,'数学');

-- 创建中间表
CREATE TABLE stu_course(
	id INT PRIMARY KEY AUTO_INCREMENT,
	sid INT, -- 用于和student表的id进行外键关联
	cid INT, -- 用于和course表的id进行外键关联
	CONSTRAINT sc_fk1 FOREIGN KEY (sid) REFERENCES student(id), -- 添加外键约束
	CONSTRAINT sc_fk2 FOREIGN KEY (cid) REFERENCES course(id)   -- 添加外键约束
);
-- 添加数据
INSERT INTO stu_course VALUES (NULL,1,1),(NULL,1,2),(NULL,2,1),(NULL,2,2);
```

# 窗口函数

开窗函数的引入是为了**既显示聚合前的数据又显示聚合后的数据**。即在每一行的最后一列添加聚合函数的结果。

**应用：1、topN问题或者组内排序问题2、连续登录问题**

**!!!!!!!!!!!!!窗口函数原则上只能写在select子句中**

- 原则上，窗口函数只能在SELECT子句中使用。 
- 窗口函数OVER 中的ORDER BY 子句并不会影响最终结果的排序。其只是用来决定窗口函数按何种顺序计算。

```mysql
-- 聚合类型SUM\MIN\MAX\AVG\COUNT
sum() OVER([PARTITION BY xxx][ORDER BY xxx [DESC]])
-- 排序类型：ROW_NUMBER\RANK\DENSE_RANK
ROW_NUMBER() OVER([PARTITION BY xxx][ORDER BY xxx [DESC]])
-- 分区类型：NTILE
NTILE(number) OVER([PARTITION BY xxx][ORDER BY xxx [DESC]])

-- PARTITON BY 是用来分组，即选择要看哪个窗口，类似于 GROUP BY 子句的分组功能，但是 PARTITION BY 子句并不具备 GROUP BY 子句的汇总功能，并不会改变原始表中记录的行数。

-- ORDER BY 是用来排序，即决定窗口内，是按那种规则(字段)来排序的。
```

## 执行顺序

在加入窗口函数的基础上SQL的执行顺序也会发生变化，具体的执行顺序如下（window就是窗口函数）
![](https://upload-images.jianshu.io/upload_images/18339009-cd689024f076a36a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![专用窗口函数例如rank、row_number、lag和lead等，在窗口函数中有静态函数和动态函数的分类](https://upload-images.jianshu.io/upload_images/18339009-fbbe65cca6ca527b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```rank()```排序相同时会重复，总数不会变 ，意思是会出现1、1、3这样的排序结果；
```dense_rank()``` 排序相同时会重复，总数会减少，意思是会出现1、1、2这样的排序结果。
```row_number()``` 则在排序相同时不重复，会根据顺序排序。

```mysql
SELECT  product_id
       ,product_name
       ,sale_price
       ,SUM(sale_price) OVER (ORDER BY product_id) AS current_sum
       ,AVG(sale_price) OVER (ORDER BY product_id) AS current_avg  
  FROM product;  
```

![quicker_203f8ccd-3534-4102-ac55-d8d8f48b90d6.png](https://s2.loli.net/2022/03/02/tEda1hNxDkmPYp4.png)
从上面的例子可以看出，在没有partition by 的情况下，是把整个表作为一个大的窗口，SUM（）相当于向下累加，AVG（）相当于求从第一行到当前行的平均值，**其他的聚合函数均是如此**。

注意点：

1 、在使用专用的窗口函数时，例如rank、lag等，rank（）括号里是不需要指定任何字段的，直接空着就可以；
2 、在使用聚合函数做窗口函数时，SUM（）括号里必须有字段，得指定对哪些字段执行聚合的操作。在学习的初期很容易弄混，不同函数括号里是否需写相应的字段名；




## 滑动窗口函数

### Preceding

![](https://upload-images.jianshu.io/upload_images/18339009-5f33b14e770215a7?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-c048cc9e17b7073b?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Rows 2 preceding 中文的意思是之前的两行，preceding可以把它理解为不含当前行情况下截止到之前几行。根据上图可以看出在每一行，都会求出**当前行附近的3行(当前行+附近2行)数据的平均值**，这种方法也叫作**移动平均**。

### Following

Rows 2 following 中文意思是之后的两行，跟preceding正好相反，Preceding是向前，following是向后。
![](https://upload-images.jianshu.io/upload_images/18339009-93d5c51eb26202a6?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### preceding跟following相结合

![](https://upload-images.jianshu.io/upload_images/18339009-4b2be94447f8744d?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-85623bf00d8f8866?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从以上的运行结果可以看出是把每一行（当前行）的前一行和后一行作为汇总的依据。







## ROLLUP - 计算合计及小计

常规的GROUP BY 只能得到每个分类的小计，有时候还需要计算分类的合计，可以用 ROLLUP关键字。

```mysql
SELECT  product_type
       ,regist_date
       ,SUM(sale_price) AS sum_price
FROM product
GROUP BY product_type, regist_date WITH ROLLUP;  
```

![quicker_71be3383-3f87-4e5e-bc91-3cf92d194d7a.png](https://s2.loli.net/2022/03/02/VQFXiarmzDpNgh2.png)



# 备份与还原

## 命令行方式

```shell
# 备份(不需要登陆)
mysqldump -u root -p 数据库名 > 保存路径
# 恢复
mysql -u root -p
drop database 已备份数据库名;
create database 已备份数据库名;
use 已备份数据库名;
source 保存路径;
```

# 存储过程和函数

存储过程和函数是  **事先经过编译并存储在数据库中的一段 SQL 语句的集合**



存储过程和函数的好处

- 存储过程和函数可以重复使用，减轻开发人员的工作量。类似于java中方法可以**多次调用**
- **减少网络流量**，存储过程和函数位于服务器上，调用的时候只需要传递名称和参数即可
- 减少数据在数据库和应用服务器之间的传输，可以提高数据处理的效率
- 将一些业务逻辑在数据库层面来实现，可以**减少代码层面的业务处理**

**函数必须有返回值，存储过程没有返回值**

```mysql
-- 修改分隔符为$
DELIMITER$
-- 创建存储过程，封装分组查询学生总成绩的sql语句
CREATE PROCEDURE stu_group()
BEGIN
	SELECT gender,SUM(score) getSum FROM student GROUP BY gender ORDER BY getSum ASC;
END$

-- 修改分隔符为分号
DELIMITER ;

-- 调用stu_group存储过程
CALL stu_group();

-- 查询数据库中所有的存储过程 标准语法
SELECT * FROM mysql.proc WHERE db='数据库名称';

-- 删除stu_group存储过程
DROP PROCEDURE stu_group;

```

## 存储过程语法

### 变量

```mysql
-- 定义变量
DECLARE 变量名 数据类型 [DEFAULT 默认值];
-- 变量赋值
SET 变量名 = 变量值;
SELECT 列名 INTO 变量名 FROM 表名 [WHERE 条件];


-- 定义两个int变量，用于存储男女同学的总分数
DELIMITER $

CREATE PROCEDURE pro_test3()
BEGIN
		-- 注意： DECLARE定义的是局部变量，只能用在BEGIN END范围之内
	DECLARE men,women INT DEFAULT 10;  -- 定义变量
	SET men = 1;       -- 为变量赋值
	SELECT NAME;                -- 查询变量
	SELECT SUM(score) INTO men FROM student WHERE gender='男';    -- 计算男同学总分数赋值给men
	SELECT SUM(score) INTO women FROM student WHERE gender='女';  -- 计算女同学总分数赋值给women
	
	SELECT men,women;           -- 查询变量
END$

DELIMITER ;
```

### if

```mysql
-- IF
IF 判断条件1 THEN 执行的sql语句1;
[ELSEIF 判断条件2 THEN 执行的sql语句2;]
...
[ELSE 执行的sql语句n;]
END IF;


/*
	定义一个int变量，用于存储班级总成绩
	定义一个varchar变量，用于存储分数描述
	根据总成绩判断：
		380分及以上    学习优秀
		320 ~ 380     学习不错
		320以下       学习一般
*/
DELIMITER $

CREATE PROCEDURE pro_test4()
BEGIN
	-- 定义总分数变量
	DECLARE total INT;
	-- 定义分数描述变量
	DECLARE description VARCHAR(10);
	-- 为总分数变量赋值
	SELECT SUM(score) INTO total FROM student;
	-- 判断总分数
	IF total >= 380 THEN 
		SET description = '学习优秀';
	ELSEIF total >= 320 AND total < 380 THEN 
		SET description = '学习不错';
	ELSE 
		SET description = '学习一般';
	END IF;
	
	-- 查询总成绩和描述信息
	SELECT total,description;
END$

DELIMITER ;
```

### 输入参数

```mysql
DELIMITER $

-- 标准语法
CREATE PROCEDURE 存储过程名称([IN|OUT|INOUT] 参数名 数据类型)
BEGIN
	执行的sql语句;
END$
/*
	IN:代表输入参数，需要由调用者传递实际数据。默认的
	OUT:代表输出参数，该参数可以作为返回值
	INOUT:代表既可以作为输入参数，也可以作为输出参数
*/
DELIMITER ;

@变量名:  这种变量要在变量名称前面加上“@”符号，叫做用户会话变量，代表整个会话过程他都是有作用的，这个类似于全局变量一样。

@@变量名: 这种在变量前加上 "@@" 符号, 叫做系统变量 


/*
	输入总成绩变量，代表学生总成绩
	输出分数描述变量，代表学生总成绩的描述
	根据总成绩判断：
		380分及以上  学习优秀
		320 ~ 380    学习不错
		320以下      学习一般
*/
DELIMITER $

CREATE PROCEDURE pro_test6(IN total INT,OUT description VARCHAR(10))
BEGIN
	-- 判断总分数
	IF total >= 380 THEN 
		SET description = '学习优秀';
	ELSEIF total >= 320 AND total < 380 THEN 
		SET description = '学习不错';
	ELSE 
		SET description = '学习一般';
	END IF;
END$

DELIMITER ;

-- 调用pro_test6存储过程
CALL pro_test6(310,@dscription);

-- 查询总成绩描述
SELECT @description;
```

### case

```mysql
-- 标准语法
CASE 表达式
WHEN 值1 THEN 执行sql语句1;
[WHEN 值2 THEN 执行sql语句2;]
...
[ELSE 执行sql语句n;]
END CASE;


/*
	输入总成绩变量，代表学生总成绩
	定义一个varchar变量，用于存储分数描述
	根据总成绩判断：
		380分及以上  学习优秀
		320 ~ 380    学习不错
		320以下      学习一般
*/
DELIMITER $

CREATE PROCEDURE pro_test7(IN total INT)
BEGIN
	-- 定义变量
	DECLARE description VARCHAR(10);
	-- 使用case判断
	CASE
	WHEN total >= 380 THEN
		SET description = '学习优秀';
	WHEN total >= 320 AND total < 380 THEN
		SET description = '学习不错';
	ELSE 
		SET description = '学习一般';
	END CASE;
	
	-- 查询分数描述信息
	SELECT description;
END$

DELIMITER ;

-- 调用pro_test7存储过程
CALL pro_test7(390);
CALL pro_test7((SELECT SUM(score) FROM student));
```

### while

```mysql
-- 标准语法
初始化语句;
WHILE 条件判断语句 DO
	循环体语句;
	条件控制语句;
END WHILE;

/*
	计算1~100之间的偶数和
*/
DELIMITER $

CREATE PROCEDURE pro_test8()
BEGIN
	-- 定义求和变量
	DECLARE result INT DEFAULT 0;
	-- 定义初始化变量
	DECLARE num INT DEFAULT 1;
	-- while循环
	WHILE num <= 100 DO
		-- 偶数判断
		IF num%2=0 THEN
			SET result = result + num; -- 累加
		END IF;
		
		-- 让num+1
		SET num = num + 1;         
	END WHILE;
	
	-- 查询求和结果
	SELECT result;
END$

DELIMITER ;

-- 调用pro_test8存储过程
CALL pro_test8();
```

### repeat

注意：repeat循环是条件满足则停止。while循环是条件满足则执行

```mysql
-- 标准语法
初始化语句;
REPEAT
	循环体语句;
	条件控制语句;
	UNTIL 条件判断语句
END REPEAT;

/*
	计算1~10之间的和
*/
DELIMITER $

CREATE PROCEDURE pro_test9()
BEGIN
	-- 定义求和变量
	DECLARE result INT DEFAULT 0;
	-- 定义初始化变量
	DECLARE num INT DEFAULT 1;
	-- repeat循环
	REPEAT
		-- 累加
		SET result = result + num;
		-- 让num+1
		SET num = num + 1;
		
		-- 停止循环
		UNTIL num>10
	END REPEAT;
	
	-- 查询求和结果
	SELECT result;
END$

DELIMITER ;

-- 调用pro_test9存储过程
CALL pro_test9();
```



### loop

注意：loop可以实现简单的循环，但是退出循环需要使用其他的语句来定义。我们可以使用leave语句完成！
**如果不加退出循环的语句，那么就变成了死循环。**

```mysql
-- 标准语法
初始化语句;
[循环名称:] LOOP
	条件判断语句
		[LEAVE 循环名称;]
	循环体语句;
	条件控制语句;
END LOOP 循环名称;

-- 注意：loop可以实现简单的循环，但是退出循环需要使用其他的语句来定义。我们可以使用leave语句完成！
--      如果不加退出循环的语句，那么就变成了死循环。


/*
	计算1~10之间的和
*/
DELIMITER $

CREATE PROCEDURE pro_test10()
BEGIN
	-- 定义求和变量
	DECLARE result INT DEFAULT 0;
	-- 定义初始化变量
	DECLARE num INT DEFAULT 1;
	-- loop循环
	l:LOOP
		-- 条件成立，停止循环
		IF num > 10 THEN
			LEAVE l;
		END IF;
	
		-- 累加
		SET result = result + num;
		-- 让num+1
		SET num = num + 1;
	END LOOP l;
	
	-- 查询求和结果
	SELECT result;
END$

DELIMITER ;

-- 调用pro_test10存储过程
CALL pro_test10();
```

### 游标

- 游标可以遍历返回的多行结果，每次拿到一整行数据
- 在存储过程和函数中可以使用游标对结果集进行循环的处理
- 简单来说游标就**类似于集合的迭代器遍历**
- **MySQL中的游标只能用在存储过程和函数中**

```mysql
-- 创建游标
DECLARE 游标名称 CURSOR FOR 查询sql语句;
-- 打开游标
OPEN 游标名称;
-- 使用游标获取数据
FETCH 游标名称 INTO 变量名1,变量名2,...;
-- - 关闭游标
CLOSE 游标名称;


/*
	将student表中所有的成绩保存到stu_score表中
*/
DELIMITER $

CREATE PROCEDURE pro_test11()
BEGIN
	-- 定义成绩变量
	DECLARE s_score INT;
    -- 定义标记变量
	DECLARE flag INT DEFAULT 0;
	-- 创建游标,查询所有学生成绩数据
	DECLARE stu_result CURSOR FOR SELECT score FROM student;
    -- 游标结束后，将标记变量改为1
	DECLARE EXIT HANDLER FOR NOT FOUND SET flag = 1;
	
	-- 开启游标
	OPEN stu_result;
	
	-- 循环使用游标
	REPEAT
		-- 使用游标，遍历结果,拿到数据
		FETCH stu_result INTO s_score;
		-- 将数据保存到stu_score表中
		INSERT INTO stu_score VALUES (NULL,s_score);
	UNTIL flag=1
	END REPEAT;
	
	-- 关闭游标
	CLOSE stu_result;
END$

DELIMITER ;

-- 调用pro_test11存储过程
CALL pro_test11();

-- 查询stu_score表
SELECT * FROM stu_score;
```

## 存储函数

存储函数有返回值，存储过程没有返回值(参数的out其实也相当于是返回数据了)

```mysql
DELIMITER $

-- 标准语法
CREATE FUNCTION 函数名称([参数 数据类型])
RETURNS 返回值类型
BEGIN
	执行的sql语句;
	RETURN 结果;
END$

DELIMITER ;

-- 调用存储函数
SELECT 函数名称(实际参数);
-- 删除存储函
DROP FUNCTION 函数名称;


/*
	定义存储函数，获取学生表中成绩大于95分的学生数量
*/
DELIMITER $

CREATE FUNCTION fun_test1()
RETURNS INT
BEGIN
	-- 定义统计变量
	DECLARE result INT;
	-- 查询成绩大于95分的学生数量，给统计变量赋值
	SELECT COUNT(*) INTO result FROM student WHERE score > 95;
	-- 返回统计结果
	RETURN result;
END$

DELIMITER ;

-- 调用fun_test1存储函数
SELECT fun_test1();
```

# 触发器

- 触发器是与表有关的数据库对象，可以**在 insert/update/delete 之前或之后，触发并执行触发器中定义的SQL语句**。触发器的这种特性可以协助应用**在数据库端确保数据的完整性 、日志记录 、数据校验等操作** 。
- 使用别名 NEW 和 OLD 来引用触发器中发生变化的记录内容，这与其他的数据库是相似的。现在触发器还只支持行级触发，不支持语句级触发。

| 触发器类型      | OLD的含义                      | NEW的含义                      |
| --------------- | ------------------------------ | ------------------------------ |
| INSERT 型触发器 | 无 (因为插入前状态无数据)      | NEW 表示将要或者已经新增的数据 |
| UPDATE 型触发器 | OLD 表示修改之前的数据         | NEW 表示将要或已经修改后的数据 |
| DELETE 型触发器 | OLD 表示将要或者已经删除的数据 | 无 (因为删除后状态无数据)      |

```mysql
DELIMITER $

CREATE TRIGGER 触发器名称
BEFORE|AFTER INSERT|UPDATE|DELETE
ON 表名
[FOR EACH ROW]  -- 行级触发器
BEGIN
	触发器要执行的功能;
END$

DELIMITER ;
```

## 触发器演示

通过触发器记录账户表的数据变更日志。包含：增加、修改、删除

- 创建账户表

```mysql
-- 创建db9数据库
CREATE DATABASE db9;

-- 使用db9数据库
USE db9;

-- 创建账户表account
CREATE TABLE account(
	id INT PRIMARY KEY AUTO_INCREMENT,	-- 账户id
	NAME VARCHAR(20),					-- 姓名
	money DOUBLE						-- 余额
);
-- 添加数据
INSERT INTO account VALUES (NULL,'张三',1000),(NULL,'李四',2000);
```

- 创建日志表

```mysql
-- 创建日志表account_log
CREATE TABLE account_log(
	id INT PRIMARY KEY AUTO_INCREMENT,	-- 日志id
	operation VARCHAR(20),				-- 操作类型 (insert update delete)
	operation_time DATETIME,			-- 操作时间
	operation_id INT,					-- 操作表的id
	operation_params VARCHAR(200)       -- 操作参数
);
```

- 创建INSERT触发器

```mysql
-- 创建INSERT触发器
DELIMITER $

CREATE TRIGGER account_insert
AFTER INSERT
ON account
FOR EACH ROW
BEGIN
	INSERT INTO account_log VALUES (NULL,'INSERT',NOW(),new.id,CONCAT('插入后{id=',new.id,',name=',new.name,',money=',new.money,'}'));
END$

DELIMITER ;

-- 向account表添加记录
INSERT INTO account VALUES (NULL,'王五',3000);

-- 查询account表
SELECT * FROM account;

-- 查询日志表
SELECT * FROM account_log;
```

- 创建UPDATE触发器

```mysql
-- 创建UPDATE触发器
DELIMITER $

CREATE TRIGGER account_update
AFTER UPDATE
ON account
FOR EACH ROW
BEGIN
	INSERT INTO account_log VALUES (NULL,'UPDATE',NOW(),new.id,CONCAT('修改前{id=',old.id,',name=',old.name,',money=',old.money,'}','修改后{id=',new.id,',name=',new.name,',money=',new.money,'}'));
END$

DELIMITER ;

-- 修改account表
UPDATE account SET money=3500 WHERE id=3;

-- 查询account表
SELECT * FROM account;

-- 查询日志表
SELECT * FROM account_log;
```

- 创建DELETE触发器

```mysql
-- 创建DELETE触发器
DELIMITER $

CREATE TRIGGER account_delete
AFTER DELETE
ON account
FOR EACH ROW
BEGIN
	INSERT INTO account_log VALUES (NULL,'DELETE',NOW(),old.id,CONCAT('删除前{id=',old.id,',name=',old.name,',money=',old.money,'}'));
END$

DELIMITER ;

-- 删除account表数据
DELETE FROM account WHERE id=3;

-- 查询account表
SELECT * FROM account;

-- 查询日志表
SELECT * FROM account_log;
```

  - 查看、删除触发器

```mysql
SHOW TRIGGERS;
DROP TRIGGER account_delete;
```

# 事务



**一条或多条 SQL 语句组成一个执行单元**，其特点是**这个单元要么同时成功要么同时失败**，单元中的每条 SQL 语句都相互依赖，形成一个整体，如果某条 SQL 语句执行失败或者出现错误，那么整个单元就会回滚，撤回到事务最初的状态，如果单元中所有的 SQL 语句都执行成功，则事务就顺利执行。



操作事务的三个步骤

1. 开启事务：记录回滚点，并通知服务器，将要执行一组操作，要么同时成功、要么同时失败
2. 执行sql语句：执行具体的一条或多条sql语句
3. 结束事务(提交|回滚)
   - 提交：没出现问题，数据进行更新
   - 回滚：出现问题，数据恢复到开启事务时的状态

```mysql
-- 开启事务
START TRANSACTION;

-- 张三给李四转账500元
-- 1.张三账户-500
UPDATE account SET money=money-500 WHERE NAME='张三';
-- 2.李四账户+500
-- 出错了...
UPDATE account SET money=money+500 WHERE NAME='李四';

-- 回滚事务(出现问题)
ROLLBACK;

-- 提交事务(没出现问题)
COMMIT;

-- 修改为手动提交
SET @@AUTOCOMMIT=0;

-- 查看提交方式
SELECT @@AUTOCOMMIT;  -- 1代表自动提交    0代表手动提交
```

## 事务的四大特征(ACID)

- 原子性(atomicity)
  - 原子性是指事务包含的所有操作**要么全部成功，要么全部失败回滚**，因此事务的操作如果成功就必须要完全应用到数据库，如果操作失败则不能对数据库有任何影响
- 一致性(consistency)
  - 一致性是指事务必须使数据库从一个一致性状态变换到另一个一致性状态，也就是说**一个事务执行之前和执行之后都必须处于一致性状态**
  - 拿转账来说，假设张三和李四两者的钱加起来一共是2000，那么不管A和B之间如何转账，转几次账，**事务结束后两个用户的钱相加起来应该还得是200**0，这就是事务的一致性
- 隔离性(isolcation)
  - 隔离性是当多个用户并发访问数据库时，比如操作同一张表时，数据库为每一个用户开启的事务，不能被其他事务的操作所干扰，**多个并发事务之间要相互隔离**
- 持久性(durability)
  - **持久性是指一个事务一旦被提交了，那么对数据库中的数据的改变就是永久性的**，即便是在数据库系统遇到故障的情况下也不会丢失提交事务的操作

## 事务的隔离级别

### 隔离级别的概念

- 多个客户端操作时 ,各个客户端的事务之间应该是隔离的，相互独立的 , 不受影响的。
- 而如果**多个事务操作同一批数据时，则需要设置不同的隔离级别** , 否则就会产生问题 。

- 四种隔离级别

| 1     | 读未提交     | read uncommitted    |
| ----- | ------------ | ------------------- |
| **2** | **读已提交** | **read committed**  |
| **3** | **可重复读** | **repeatable read** |
| **4** | **串行化**   | **serializable**    |

- 可能引发的问题

| 问题           | 现象                                                         |
| -------------- | ------------------------------------------------------------ |
| **脏读**       | **是指在一个事务处理过程中读取了另一个未提交的事务中的数据 , 导致两次查询结果不一致** |
| **不可重复读** | **是指在一个事务处理过程中读取了另一个事务中修改并已提交的数据, 导致两次查询结果不一致** |
| **幻读**       | **select 某记录是否存在，不存在，准备插入此记录，但执行 insert 时发现此记录已存在，无法插入。或不存在执行delete删除，却发现删除成功** |

```mysql
-- 修改数据库隔离级别为read uncommitted
SET GLOBAL TRANSACTION ISOLATION LEVEL read uncommitted;

-- 查看隔离级别
SELECT @@TX_ISOLATION;   -- 修改后需要断开连接重新开
```

### 事务隔离级别演示

- 脏读的问题

  - 窗口1

  ```mysql
  -- 查询账户表
  select * from account;
  
  -- 设置隔离级别为read uncommitted
  set global transaction isolation level read uncommitted;
  
  -- 开启事务
  start transaction;
  
  -- 转账
  update account set money = money - 500 where id = 1;
  update account set money = money + 500 where id = 2;
  
  -- 窗口2查询转账结果 ,出现脏读(查询到其他事务未提交的数据)
  
  -- 窗口2查看转账结果后，执行回滚
  rollback;
  ```

  - 窗口2

  ```mysql
  -- 查询隔离级别
  select @@tx_isolation;
  
  -- 开启事务
  start transaction;
  
  -- 查询账户表
  select * from account;
  ```

- 解决脏读的问题和演示不可重复读的问题

  - 窗口1

  ```mysql
  -- 设置隔离级别为read committed
  set global transaction isolation level read committed;
  
  -- 开启事务
  start transaction;
  
  -- 转账
  update account set money = money - 500 where id = 1;
  update account set money = money + 500 where id = 2;
  
  -- 窗口2查看转账结果，并没有发生变化(脏读问题被解决了)
  
  -- 执行提交事务。
  commit;
  
  -- 窗口2查看转账结果，数据发生了变化(出现了不可重复读的问题，读取到其他事务已提交的数据)
  ```

  - 窗口2

  ```mysql
  -- 查询隔离级别
  select @@tx_isolation;
  
  -- 开启事务
  start transaction;
  
  -- 查询账户表
  select * from account;
  ```

- 解决不可重复读的问题

  - 窗口1

  ```mysql
  -- 设置隔离级别为repeatable read
  set global transaction isolation level repeatable read;
  
  -- 开启事务
  start transaction;
  
  -- 转账
  update account set money = money - 500 where id = 1;
  update account set money = money + 500 where id = 2;
  
  -- 窗口2查看转账结果，并没有发生变化
  
  -- 执行提交事务
  commit;
  
  -- 这个时候窗口2只要还在上次事务中，看到的结果都是相同的。只有窗口2结束事务，才能看到变化(不可重复读的问题被解决)
  ```

  - 窗口2

  ```mysql
  -- 查询隔离级别
  select @@tx_isolation;
  
  -- 开启事务
  start transaction;
  
  -- 查询账户表
  select * from account;
  
  -- 提交事务
  commit;
  
  -- 查询账户表
  select * from account;
  ```

- 幻读的问题和解决

  - 窗口1

  ```mysql
  -- 设置隔离级别为repeatable read
  set global transaction isolation level repeatable read;
  
  -- 开启事务
  start transaction;
  
  -- 添加一条记录
  INSERT INTO account VALUES (3,'王五',1500);
  
  -- 查询账户表，本窗口可以查看到id为3的结果
  SELECT * FROM account;
  
  -- 提交事务
  COMMIT;
  ```

  - 窗口2

  ```mysql
  -- 查询隔离级别
  select @@tx_isolation;
  
  -- 开启事务
  start transaction;
  
  -- 查询账户表，查询不到新添加的id为3的记录
  select * from account;
  
  -- 添加id为3的一条数据，发现添加失败。出现了幻读
  INSERT INTO account VALUES (3,'测试',200);
  
  -- 提交事务
  COMMIT;
  
  -- 查询账户表，查询到了新添加的id为3的记录
  select * from account;
  ```

  - 解决幻读的问题

  ```mysql
  /*
  	窗口1
  */
  -- 设置隔离级别为serializable
  set global transaction isolation level serializable;
  
  -- 开启事务
  start transaction;
  
  -- 添加一条记录
  INSERT INTO account VALUES (4,'赵六',1600);
  
  -- 查询账户表，本窗口可以查看到id为4的结果
  SELECT * FROM account;
  
  -- 提交事务
  COMMIT;
  
  
  
  /*
  	窗口2
  */
  -- 查询隔离级别
  select @@tx_isolation;
  
  -- 开启事务
  start transaction;
  
  -- 查询账户表，发现查询语句无法执行，数据表被锁住！只有窗口1提交事务后，才可以继续操作
  select * from account;
  
  -- 添加id为4的一条数据，发现已经存在了，就不会再添加了！幻读的问题被解决
  INSERT INTO account VALUES (4,'测试',200);
  
  -- 提交事务
  COMMIT;
  ```

### 隔离级别总结

|      | 隔离级别             | 名称     | 出现脏读 | 出现不可重复读 | 出现幻读 | 数据库默认隔离级别  |
| ---- | -------------------- | -------- | -------- | -------------- | -------- | ------------------- |
| 1    | **read uncommitted** | 读未提交 | 是       | 是             | 是       |                     |
| 2    | **read committed**   | 读已提交 | 否       | 是             | 是       | Oracle / SQL Server |
| 3    | **repeatable read**  | 可重复读 | 否       | 否             | 是       | MySQL               |
| 4    | **serializable **    | 串行化   | 否       | 否             | 否       |                     |

> 注意：隔离级别从小到大安全性越来越高，但是效率越来越低 , 所以不建议使用READ UNCOMMITTED 和 SERIALIZABLE 隔离级别.

# 索引

- MySQL数据库中的索引：是帮助MySQL高效获取数据的一种数据结构！所以，索引的本质就是数据结构。

- 在表数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式指向数据， 这样就可以在这些数据结构上实现高级查找算法，这种数据结构就是索引。

- 一张数据表，用于保存数据。   一个索引配置文件，用于保存索引，每个索引都去指向了某一个数据(表格演示)

## 索引的分类

- 功能分类 
  - 普通索引： 最基本的索引，它没有任何限制。
  - 唯一索引：索引列的值必须**唯一，但允许有空值**。如果是组合索引，则列值组合必须唯一。
  - 主键索引：一种**特殊的唯一索引，不允许有空值**。一般在建表时同时创建主键索引。
  - 组合索引：顾名思义，就是将单列索引进行组合。
  - 外键索引：**只有InnoDB引擎**支持外键索引，用来保证数据的一致性、完整性和实现级联操作。
  - 全文索引：快速匹配全部文档的方式。InnoDB引擎5.6版本后才支持全文索引。MEMORY引擎不支持。
- 结构分类
  - B+Tree索引 ：MySQL使用最频繁的一个索引数据结构，是InnoDB和MyISAM存储引擎默认的索引类型。
  - Hash索引 : MySQL中Memory存储引擎默认支持的索引类型。

- 注意：如果一个表中有一列是主键，那么就会默认为其创建主键索引！(主键列不需要单独创建索引)

```mysql
-- 创建索引
CREATE [UNIQUE|FULLTEXT] INDEX 索引名称
[USING 索引类型]  -- 默认是B+TREE
ON 表名(列名...);


-- 为student表中姓名列创建一个普通索引
CREATE INDEX idx_name ON student(NAME);
-- 为student表中年龄列创建一个唯一索引
CREATE UNIQUE INDEX idx_age ON student(age);
-- 查看student表中的索引
SHOW INDEX FROM student;

-- -------------------alter语句添加索引
-- 普通索引
ALTER TABLE 表名 ADD INDEX 索引名称(列名);
-- 组合索引
ALTER TABLE 表名 ADD INDEX 索引名称(列名1,列名2,...);
-- 主键索引
ALTER TABLE 表名 ADD PRIMARY KEY(主键列名); 
-- 外键索引(添加外键约束，就是外键索引)
ALTER TABLE 表名 ADD CONSTRAINT 外键名 FOREIGN KEY (本表外键列名) REFERENCES 主表名(主键列名);
-- 唯一索引
ALTER TABLE 表名 ADD UNIQUE 索引名称(列名);
-- 全文索引(mysql只支持文本类型)
ALTER TABLE 表名 ADD FULLTEXT 索引名称(列名);


-- 为student表中name列添加全文索引
ALTER TABLE student ADD FULLTEXT idx_fulltext_name(name);
-- 查看student表中的索引
SHOW INDEX FROM student;

-- 删除student表中的idx_score索引
DROP INDEX idx_score ON student;
-- 查看student表中的索引
SHOW INDEX FROM student;



```

## 索引的实现原理

- 索引是在MySQL的存储引擎中实现的，所以每种存储引擎的索引不一定完全相同，也不是所有的引擎支持所有的索引类型。这里我们主要介绍**InnoDB引擎的实现的B+Tree索引**。
- B+Tree是一种树型数据结构，是B-Tree的变种。通常使用在数据库和操作系统中的文件系统，特点是能够**保持数据稳定有序**。我们逐步的来了解一下。

### 磁盘存储

- 系统从**磁盘读取数据到内存时是以磁盘块（block）为基本单位**
- 位于**同一个磁盘块中的数据会被一次性读取出来**，而不是需要什么取什么。
- InnoDB存储引擎中有页（Page）的概念，**页是其磁盘管理的最小单位**。InnoDB存储引擎中默认每个页的大小为16KB。
- InnoDB引擎将若干个地址连接磁盘块，以此来达到页的大小16KB，在查询数据时如果一个页中的每条数据都能有助于定位数据记录的位置，这将会减少磁盘I/O次数，提高查询效率。

### BTree

- BTree结构的数据可以让系统高效的找到数据所在的磁盘块。为了描述BTree，首先定义一条记录为一个二元组[key, data] ，key为记录的键值，对应表中的主键值，data为一行记录中除主键外的数据。对于不同的记录，key值互不相同。BTree中的每个节点根据实际情况可以包含大量的关键字信息和分支，如下图所示为一个3阶的BTree： 

  ![05.png](https://s2.loli.net/2022/02/06/U7EhQ6vYgN2KRWS.png)

查找顺序：

```mysql
模拟查找15的过程 : 

1.根节点找到磁盘块1，读入内存。【磁盘I/O操作第1次】
	比较关键字15在区间（<17），找到磁盘块1的指针P1。
2.P1指针找到磁盘块2，读入内存。【磁盘I/O操作第2次】
	比较关键字15在区间（>12），找到磁盘块2的指针P3。
3.P3指针找到磁盘块7，读入内存。【磁盘I/O操作第3次】
	在磁盘块7中找到关键字15。
	
-- 分析上面过程，发现需要3次磁盘I/O操作，和3次内存查找操作。
-- 由于内存中的关键字是一个有序表结构，可以利用二分法查找提高效率。而3次磁盘I/O操作是影响整个BTree查找效率的决定因素。BTree使用较少的节点个数，使每次磁盘I/O取到内存的数据都发挥了作用，从而提高了查询效率。
```

### B+Tree

- B+Tree是在BTree基础上的一种优化，使其更适合实现外存储索引结构，InnoDB存储引擎就是用B+Tree实现其索引结构。
- 从上一节中的BTree结构图中可以看到每个节点中不仅包含数据的key值，还有data值。而每一个页的存储空间是有限的，**如果data数据较大时将会导致每个节点（即一个页）能存储的key的数量很小，当存储的数据量很大时同样会导致B-Tree的深度较大，增大查询时的磁盘I/O次数**，进而影响查询效率。在B+Tree中，所有数据记录节点都是按照键值大小顺序存放在同一层的叶子节点上，而非叶子节点上只存储key值信息，这样可以大大加大每个节点存储的key值数量，降低B+Tree的高度。
- B+Tree相对于BTree区别：
  - **非叶子节点只存储键值信息。数据记录都存放在叶子节点中**。
  - **所有叶子节点之间都有一个连接指针**。
  - 将上一节中的BTree优化，由于B+Tree的非叶子节点只存储键值信息，假设每个磁盘块能存储4个键值及指针信息，则变成B+Tree后其结构如下图所示：

![06.png](https://s2.loli.net/2022/02/06/u7dmevbAjzVg5il.png)

通常在B+Tree上有两个头指针，**一个指向根节点，另一个指向关键字最小的叶子节点**，而且**所有叶子节点（即数据节点）之间是一种链式环结构**。因此可以对B+Tree进行两种查找运算：

- 【有范围】对于主键的范围查找和分页查找
- 【有顺序】从根节点开始，进行随机查找

实际情况中每个节点可能不能填充满，因此在数据库中，**B+Tree的高度一般都在2\~4层**。MySQL的InnoDB存储引擎在设计时是将**根节点常驻内存的**，也就是说查找某一键值的行记录时**最多只需要1~3次磁盘I/O操作**。

## 索引的设计原则

索引的设计可以遵循一些已有的原则，创建索引的时候请尽量考虑符合这些原则，便于提升索引的使用效率，更高效的使用索引。

- 创建索引时的原则
  - 对**查询频次较高，且数据量比较大的表建立索引**。
  - **使用唯一索引，区分度越高，使用索引的效率越高**。
  - 索引字段的选择，最佳候选列应当**从where子句的条件中提取**，如果where子句中的组合比较多，那么应当挑选最常用、过滤效果最好的列的组合。
  - **使用短索引，提升索引访问的I/O效率**，也可以提升总体的访问效率。假如构成索引的字段总长度比较短，那么在给定大小的存储块内可以存储更多的索引值，相应的可以有效的提升MySQL访问索引的I/O效率。
  - 索引可以有效的提升查询数据的效率，但索引数量不是多多益善，**索引越多，维护索引的代价自然也就水涨船高**。对于插入、更新、删除等DML操作比较频繁的表来说，索引过多，会引入相当高的维护代价，降低DML操作的效率，增加相应操作的时间消耗。另外索引过多的话，MySQL也会犯选择困难病，虽然最终仍然会找到一个可用的索引，但无疑提高了选择的代价。
- 联合索引的特点

在mysql建立联合索引时会遵循最左前缀匹配的原则，即最左优先，在检索数据时从联合索引的最左边开始匹配，

```mysql
ALTER TABLE user ADD INDEX index_three(name,address,phone);

-- 联合索引index_three实际建立了(name)、(name,address)、(name,address,phone)三个索引。所以下面的三个SQL语句都可以命中索引。

SELECT * FROM user WHERE address = '北京' AND phone = '12345' AND name = '张三';
SELECT * FROM user WHERE name = '张三' AND address = '北京';
SELECT * FROM user WHERE name = '张三';

-- 上面三个查询语句执行时会依照最左前缀匹配原则，检索时分别会使用索引
(name,address,phone)
(name,address)
(name)
进行数据匹配。
索引的字段可以是任意顺序的，如：
-- 优化器会帮助我们调整顺序，下面的SQL语句都可以命中索引
SELECT * FROM user WHERE address = '北京' AND phone = '12345' AND name = '张三';

-- 联合索引中最左边的列不包含在条件查询中，下面的SQL语句就不会命中索
SELECT * FROM user WHERE address='北京' AND phone='12345';
```

# 锁

## 锁的概念

- 在我们学习事务的时候，讲解过事务的隔离性，可能会出现脏读、不可重复读、幻读的问题，当时我们的解决方式是通过修改事务的隔离级别来控制，**但是数据库的隔离级别我们并不推荐修改**。所以，锁的作用也可以解决掉之前的问题！

- 锁机制 : 数据库为了保证数据的一致性，而使各种共享的资源在被并发访问时变得有序所设计的一种规则。

- 举例，在电商网站购买商品时，商品表中只存有1个商品，而此时又有两个人同时购买，那么谁能买到就是一个关键的问题。

  这里会用到事务进行一系列的操作：

  1. 先从商品表中取出物品的数据
  2. 然后插入订单
  3. 付款后，再插入付款表信息
  4. 更新商品表中商品的数量

  以上过程中，使用锁可以对商品数量数据信息进行保护，实现隔离，即只允许第一位用户完成整套购买流程，而其他用户只能等待，这样就解决了并发中的矛盾问题。

- 在数据库中，数据是一种供许多用户共享访问的资源，如何保证数据并发访问的一致性、有效性，是所有数据库必须解决的一个问题，MySQL由于自身架构的特点，在不同的存储引擎中，都设计了面对特定场景的锁定机制，所以引擎的差别，导致锁机制也是有很大差别的。

## 锁的分类

- 按操作分类：
  - 共享锁：也叫读锁。**数据可以被多个事务查间，但是不能修改**
  - 排他锁：也叫写锁。**当前的操作没有完成前，不能被其他事务加锁查询或修改**
  - 注意：锁的兼容性
    - 共享锁和共享锁     兼容
    - 共享锁和排他锁     冲突
    - 排他锁和排他锁     冲突
    - 排他锁和共享锁     冲突
- 按粒度分类：
  - 表级锁：操作时，会锁定整个表。开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突概率高，并发度最低。偏向于MyISAM存储引擎！
  - 行级锁：**操作时，会锁定当前操作行。开销大，加锁慢；会出现死锁；锁定粒度小，发生锁冲突的概率低，并发度高。偏向于InnoDB存储引擎！**
  - 页级锁：锁的粒度、发生冲突的概率和加锁的开销介于表锁和行锁之间，会出现死锁，并发性能一般。
- 按使用方式分类：
  - 悲观锁：**每次查询数据时都认为别人会修改**，很悲观，所以**查询时加锁**。
  - 乐观锁：**每次查询数据时都认为别人不会修改**，很乐观，但是**更新时会判断一下在此期间别人有没有去更新这个数据**
- 不同存储引擎支持的锁

| 存储引擎 | 表级锁 | 行级锁   | 页级锁   |
| -------- | ------ | -------- | -------- |
| MyISAM   | 支持   | 不支持   | 不支持   |
| InnoDB   | 支持   | **支持** | 不支持   |
| MEMORY   | 支持   | 不支持   | 不支持   |
| BDB      | 支持   | 不支持   | **支持** |

## 演示InnoDB锁

```mysql
-- 共享锁
SELECT语句 LOCK IN SHARE MODE;
-- 排他锁
SELECT语句 FOR UPDATE;
```


- 数据准备

```mysql
-- 创建db13数据库
CREATE DATABASE db13;

-- 使用db13数据库
USE db13;

-- 创建student表
CREATE TABLE student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(10),
	age INT,
	score INT
);
-- 添加数据
INSERT INTO student VALUES (NULL,'张三',23,99),(NULL,'李四',24,95),
(NULL,'王五',25,98),(NULL,'赵六',26,97);
```


```mysql
-- 窗口1
/*
	共享锁：数据可以被多个事务查询，但是不能修改
*/
-- 开启事务
START TRANSACTION;

-- 查询id为1的数据记录。加入共享锁
SELECT * FROM student WHERE id=1 LOCK IN SHARE MODE;

-- 查询分数为99分的数据记录。加入共享锁(注意：InnoDB引擎如果不采用带索引的列。则会提升为表锁)
SELECT * FROM student WHERE score=99 LOCK IN SHARE MODE;

-- 提交事务
COMMIT;
```

```mysql
-- 窗口2
-- 开启事务
START TRANSACTION;

-- 查询id为1的数据记录(普通查询，可以查询)
SELECT * FROM student WHERE id=1;

-- 查询id为1的数据记录，并加入共享锁(可以查询。共享锁和共享锁兼容)
SELECT * FROM student WHERE id=1 LOCK IN SHARE MODE;

-- 修改id为1的姓名为张三三(不能修改，会出现锁的情况。只有窗口1提交事务后，才能修改成功)
UPDATE student SET NAME='张三三' WHERE id = 1;

-- 修改id为2的姓名为李四四(修改成功，InnoDB引擎默认是行锁)
UPDATE student SET NAME='李四四' WHERE id = 2;

-- 修改id为3的姓名为王五五(注意：InnoDB引擎如果不采用带索引的列。则会提升为表锁)
UPDATE student SET NAME='王五五' WHERE id = 3;

-- 提交事务
COMMIT;
```

```mysql
-- 窗口1
/*
	排他锁：加锁的数据，不能被其他事务加锁查询或修改
*/
-- 开启事务
START TRANSACTION;

-- 查询id为1的数据记录，并加入排他锁
SELECT * FROM student WHERE id=1 FOR UPDATE;

-- 提交事务
COMMIT;
```

```mysql
-- 窗口2
-- 开启事务
START TRANSACTION;

-- 查询id为1的数据记录(普通查询没问题)
SELECT * FROM student WHERE id=1;

-- 查询id为1的数据记录，并加入共享锁(不能查询。因为排他锁不能和其他锁共存)
SELECT * FROM student WHERE id=1 LOCK IN SHARE MODE;

-- 查询id为1的数据记录，并加入排他锁(不能查询。因为排他锁不能和其他锁共存)
SELECT * FROM student WHERE id=1 FOR UPDATE;

-- 修改id为1的姓名为张三(不能修改，会出现锁的情况。只有窗口1提交事务后，才能修改成功)
UPDATE student SET NAME='张三' WHERE id=1;

-- 提交事务
COMMIT;
```

## 演示MyISAM锁

```mysql
-- 读锁。加锁
LOCK TABLE 表名 READ;
-- 解锁(将当前会话所有的表进行解锁)
UNLOCK TABLES;

-- 写锁。加锁
LOCK TABLE 表名 WRITE;
-- 解锁(将当前会话所有的表进行解锁)
UNLOCK TABLES;
```

```mysql
-- 创建product表
CREATE TABLE product(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20),
	price INT
)ENGINE = MYISAM;  -- 指定存储引擎为MyISAM

-- 添加数据
INSERT INTO product VALUES (NULL,'华为手机',4999),(NULL,'小米手机',2999),
(NULL,'苹果',8999),(NULL,'中兴',1999);
```

```mysql
-- 窗口1
/*
	读锁：所有连接只能读取数据，不能修改
*/
-- 为product表加入读锁
LOCK TABLE product READ;

-- 查询product表(查询成功)
SELECT * FROM product;

-- 修改华为手机的价格为5999(修改失败)
UPDATE product SET price=5999 WHERE id=1;

-- 解锁
UNLOCK TABLES;
```

```mysql
-- 窗口2
-- 查询product表(查询成功)
SELECT * FROM product;

-- 修改华为手机的价格为5999(不能修改，窗口1解锁后才能修改成功)
UPDATE product SET price=5999 WHERE id=1;
```

```mysql
-- 窗口1
/*
	写锁：其他连接不能查询和修改数据
*/
-- 为product表添加写锁
LOCK TABLE product WRITE;

-- 查询product表(查询成功)
SELECT * FROM product;

-- 修改小米手机的金额为3999(修改成功)
UPDATE product SET price=3999 WHERE id=2;

-- 解锁
UNLOCK TABLES;
```

```mysql
-- 窗口2
-- 查询product表(不能查询。只有窗口1解锁后才能查询成功)
SELECT * FROM product;

-- 修改小米手机的金额为2999(不能修改。只有窗口1解锁后才能修改成功)
UPDATE product SET price=2999 WHERE id=2;
```

## 演示悲观锁和乐观锁

- 我们之前所学的行锁，表锁不论是读写锁都是悲观锁。

  

- 悲观锁和乐观锁使用前提

  - 对于**读的操作远多于写的操作**的时候，这时候一个更新操作加锁会阻塞所有的读取操作，降低了吞吐量。最后还要释放锁，锁是需要一些开销的，这时候可以选择**乐观锁**。
  - 如果是**读写比例差距不是非常大或者系统没有响应不及时，吞吐量瓶颈的问题**，那就不要去使用乐观锁，它增加了复杂度，也带来了业务额外的风险。这时候可以选择**悲观锁**。

- 乐观锁的实现方式

  - 版本号

    - 给数据表中添加一个version列，每次更新后都将这个列的值加1。
    - 读取数据时，将版本号读取出来，在执行更新的时候，比较版本号。
    - 如果相同则执行更新，如果不相同，说明此条数据已经发生了变化。
    - 用户自行根据这个通知来决定怎么处理，比如重新开始一遍，或者放弃本次更新。

    ```mysql
    -- 创建city表
    CREATE TABLE city(
    	id INT PRIMARY KEY AUTO_INCREMENT,  -- 城市id
    	NAME VARCHAR(20),                   -- 城市名称
    	VERSION INT                         -- 版本号
    );
    
    -- 添加数据
    INSERT INTO city VALUES (NULL,'北京',1),(NULL,'上海',1),(NULL,'广州',1),(NULL,'深圳',1);
    
    -- 修改北京为北京市
    -- 1.查询北京的version
    SELECT VERSION FROM city WHERE NAME='北京';
    -- 2.修改北京为北京市，版本号+1。并对比版本号
    UPDATE city SET NAME='北京市',VERSION=VERSION+1 WHERE NAME='北京' AND VERSION=1;
    ```

  - 时间戳

    - 和版本号方式基本一样，给数据表中添加一个列，名称无所谓，数据类型需要是timestamp
    - 每次更新后都将最新时间插入到此列。
    - 读取数据时，将时间读取出来，在执行更新的时候，比较时间。
    - 如果相同则执行更新，如果不相同，说明此条数据已经发生了变化。

## 锁的总结

- 表锁和行锁

  - 行锁：锁的粒度更细，加行锁的性能损耗较大。并发处理能力较高。InnoDB引擎默认支持！
  - 表锁：锁的粒度较粗，加表锁的性能损耗较小。并发处理能力较低。InnoDB、MyISAM引擎支持！

- InnoDB锁优化建议

  - 尽量通过带索引的列来完成数据查询，从而避免InnoDB无法加行锁而升级为表锁。

  - 合理设计索引，索引要尽可能准确，尽可能的缩小锁定范围，避免造成不必要的锁定。
  - 尽可能减少基于范围的数据检索过滤条件。
  - 尽量控制事务的大小，减少锁定的资源量和锁定时间长度。
  - 在同一个事务中，尽可能做到一次锁定所需要的所有资源，减少死锁产生概率。
  - 对于非常容易产生死锁的业务部分，可以尝试使用升级锁定颗粒度，通过表级锁定来减少死锁的产生。

# 存储引擎

### MySQL体系结构

- 体系结构的概念

  - 任何一套系统当中，每个部件都能起到一定的作用！

- MySQL的体系结构

![02.png](https://s2.loli.net/2022/02/06/a9R3d7XYUIHPib5.png)

- 体系结构详解
  - 客户端连接
    - 支持接口：支持的客户端连接，例如C、Java、PHP等语言来连接MySQL数据库
  - 第一层：网络连接层
    - 连接池：管理、缓冲用户的连接，线程处理等需要缓存的需求。
    - 例如：当客户端发送一个请求连接，会从连接池中获取一个连接进行使用。
  - 第二层：核心服务层
    - 管理服务和工具：系统的管理和控制工具，例如备份恢复、复制、集群等。 
    - SQL接口：接受SQL命令，并且返回查询结果。
    - 查询解析器：验证和解析SQL命令，例如过滤条件、语法结构等。 
    - 查询优化器：在执行查询之前，使用默认的一套优化机制进行优化sql语句
    - 缓存：如果缓存当中有想查询的数据，则直接将缓存中的数据返回。没有的话再重新查询！
  - 第三层：存储引擎层
    - 插件式存储引擎：管理和操作数据的一种机制，包括(存储数据、如何更新、查询数据等)
  - 第四层：系统文件层
    - 文件系统：配置文件、数据文件、日志文件、错误文件、二进制文件等等的保存

### MySQL存储引擎

- 引擎的概念

  - 生活中，引擎就是整个机器运行的核心，不同的引擎具备不同的功能。

- MySQL存储引擎的概念
  - MySQL数据库使用不同的机制存取表文件 , 机制的差别在于不同的**存储方式、索引技巧、锁定水平以及广泛的不同的功能和能力**，在MySQL中 , 将这些不同的技术及配套的功能称为**存储引擎**
  - 在关系型数据库中数据的存储是以表的形式存进行储的，所以存储引擎也可以称为**表类型**（即存储和操作此表的类型）。
  - **Oracle , SqlServer等数据库只有一种存储引擎** , 而MySQL针对不同的需求, 配置MySQL的不同的存储引擎 , 就会让数据库采取了不同的处理数据的方式和扩展功能。
  - 通过选择不同的引擎 ,能够获取最佳的方案 ,  也能够获得额外的速度或者功能，提高程序的整体效果。所以了解引擎的特性 , 才能贴合我们的需求 , 更好的发挥数据库的性能。
- MySQL支持的存储引擎
  - MySQL5.7支持的引擎包括：InnoDB、MyISAM、MEMORY、Archive、Federate、CSV、BLACKHOLE等
  - 其中较为常用的有三种：InnoDB、MyISAM、MEMORY

### 常用引擎的特性对比

- 常用的存储引擎
  - MyISAM存储引擎
    - 访问快,不支持事务和外键。表结构保存在.frm文件中，表数据保存在.MYD文件中，索引保存在.MYI文件中。
  - InnoDB存储引擎(MySQL5.5版本后默认的存储引擎)
    - 支持事务 ,占用磁盘空间大 ,支持并发控制。表结构保存在.frm文件中，如果是共享表空间，数据和索引保存在 innodb_data_home_dir 和 innodb_data_file_path定义的表空间中，可以是多个文件。如果是多表空间存储，每个表的数据和索引单独保存在 .ibd 中。
  - MEMORY存储引擎
    - 内存存储 , 速度快 ,不安全 ,适合小量快速访问的数据。表结构保存在.frm中。
- 特性对比

| 特性         | MyISAM                       | InnoDB        | MEMORY             |
| ------------ | ---------------------------- | ------------- | ------------------ |
| 存储限制     | 有(平台对文件系统大小的限制) | 64TB          | 有(平台的内存限制) |
| **事务安全** | **不支持**                   | **支持**      | **不支持**         |
| **锁机制**   | **表锁**                     | **表锁/行锁** | **表锁**           |
| B+Tree索引   | 支持                         | 支持          | 支持               |
| 哈希索引     | 不支持                       | 不支持        | 支持               |
| 全文索引     | 支持                         | 支持          | 不支持             |
| **集群索引** | **不支持**                   | **支持**      | **不支持**         |
| 数据索引     | 不支持                       | 支持          | 支持               |
| 数据缓存     | 不支持                       | 支持          | N/A                |
| 索引缓存     | 支持                         | 支持          | N/A                |
| 数据可压缩   | 支持                         | 不支持        | 不支持             |
| 空间使用     | 低                           | 高            | N/A                |
| 内存使用     | 低                           | 高            | 中等               |
| 批量插入速度 | 高                           | 低            | 高                 |
| **外键**     | **不支持**                   | **支持**      | **不支持**         |

```mysql
-- 查询数据库支持的存储引擎
SHOW ENGINES;

-- 表含义:
  - support : 指服务器是否支持该存储引擎
  - transactions : 指存储引擎是否支持事务
  - XA : 指存储引擎是否支持分布式事务处理
  - Savepoints : 指存储引擎是否支持保存点
  
  
-- 查看db9数据库所有表的存储引擎
SHOW TABLE STATUS FROM db9;  
-- 查看db9数据库中stu_score表的存储引擎
SHOW TABLE STATUS FROM db9 WHERE NAME = 'stu_score';


-- 创建engine_test表，指定存储引擎为MyISAM
CREATE TABLE engine_test(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(10)
)ENGINE = MYISAM;

-- 查询engine_test表的引擎
SHOW TABLE STATUS FROM db11 WHERE NAME = 'engine_test';

-- 修改engine_test表的引擎为InnoDB
ALTER TABLE engine_test ENGINE = INNODB;
-- 查询engine_test表的引擎
SHOW TABLE STATUS FROM db11 WHERE NAME = 'engine_test';
```

#### 总结：引擎的选择

- MyISAM ：由于MyISAM不支持事务、不支持外键、支持全文检索和表级锁定，读写相互阻塞，读取速度快，节约资源，所以如果应用是以**查询操作和插入操作为主**，只有**很少的更新和删除**操作，并且**对事务的完整性、并发性要求不是很高**，那么选择这个存储引擎是非常合适的。
- InnoDB : 是MySQL的默认存储引擎， 由于InnoDB支持事务、支持外键、行级锁定 ，支持所有辅助索引(5.5.5后不支持全文检索)，高缓存，所以用于对事务的完整性有比较高的要求，在并发条件下要求数据的一致性，读写频繁的操作，那么InnoDB存储引擎是比较合适的选择，比如BBS、计费系统、充值转账等
- MEMORY：将所有数据保存在RAM中，在需要快速定位记录和其他类似数据环境下，可以提供更快的访问。MEMORY的缺陷就是对表的大小有限制，太大的表无法缓存在内存中，其次是要确保表的数据可以恢复，数据库异常终止后表中的数据是可以恢复的。**MEMORY表通常用于更新不太频繁的小表，用以快速得到访问结果。**
- 总结：针对不同的需求场景，来选择最适合的存储引擎即可！如果不确定、则使用数据库默认的存储引擎！