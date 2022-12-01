`LOAD DATA INFILE`语句允许您从文本文件读取数据，并将文件的数据快速导入数据库的表中。


#　创建将要导入文件的数据对应的数据库表。

```
use testdb;
CREATE TABLE discounts (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    expired_date DATE NOT NULL,
    amount DECIMAL(10 , 2 ) NULL,
    PRIMARY KEY (id)
);
```
# 准备好一个CSV文件，其数据与表的列数和每列中的数据类型相匹配。

#　连接到MySQL数据库服务器的帐户具有`FILE`和`INSERT`权限。

以下语句将数据从`F:/worksp/mysql/discounts.csv`文件导入到`discounts`表。

```
LOAD DATA INFILE 'F:/worksp/mysql/discounts.csv' 
INTO TABLE discounts 
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
```

```FIELD TERMINATED BY```指示的逗号终止
```ENCLOSED BY```指定的双引号括起来。
```IGNORE 1 ROWS```列标题不需要导入到表中，忽略第一行。

现在，我们可以查看表中的数据，查看是否成功导入了数据。
```SELECT COUNT(*) FROM discounts;```


# 导入时转换数据

有时，数据格式与表中的目标列不匹配。在简单的情况下，可以使用`LOAD DATA INFILE`语句中的`SET`子句进行转换。

假设有一个`discount_2.csv`文件中，它存储的过期日期列是`mm/dd/yyyy`格式。其内容如下所示 -

```
id,title,expired date,amout
4,"Item-4","01/04/2018",200
5,"Item-5","01/09/2017",290
6,"Item-6","12/08/2018",122
```

将数据导入表时，必须使用```str_to_date()```函数将其转换为MySQL日期格式，如下所示：

```
LOAD DATA INFILE 'F:/worksp/mysql/discounts_2.csv'
INTO TABLE discounts
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id,title,@expired_date,amount)
SET expired_date = STR_TO_DATE(@expired_date, '%m/%d/%Y');
```


# 将文件从客户端导入远程MySQL数据库服务器

可以使用`LOAD DATA INFILE`语句将数据从客户端(本地计算机)导入远程MySQL数据库服务器。

```
LOAD DATA LOCAL INFILE  'c:/tmp/discounts.csv'
INTO TABLE discounts
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
```
唯一的区别是语句中多了个`LOCAL`选项。如果加载一个大的CSV文件，将会看到使用`LOCAL`选项来加载该文件将会稍微慢些，因为需要时间将文件传输到数据库服务器。

# 报错
>Loading local data is disabled; this must be enabled on both the client and server sides。
那么可能是本地文件没有开启，输入命令：`show variables like 'local_infile';`
![](https://upload-images.jianshu.io/upload_images/18339009-96ff77852bfa6b99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后开启：`set global local_infile=on;`
![](https://upload-images.jianshu.io/upload_images/18339009-430c8f25be79e042.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
不过开启后加载文件仍然报错。
```mysql --local-infile -u username -p```
![](https://upload-images.jianshu.io/upload_images/18339009-9ffccbab7ca05de3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>The MySQL server is running with the --secure-file-priv option so it cannot execute this statement
secure_file_priv 为 NULL 时，表示限制mysqld不允许导入或导出。
>---
>打开my.cnf 或 my.ini，加入以下语句后重启mysql。
secure_file_priv=''
