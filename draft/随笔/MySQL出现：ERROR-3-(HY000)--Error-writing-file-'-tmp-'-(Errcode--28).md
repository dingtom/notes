# 原因：
/tmp文件夹空间不足
# 解决办法：
1.查看tmpdir位置
```show variables like 'tmpdir';```
2.创建新的tmpdir位置
```mkdir /home/csi/tmp ```
3.更改tmpdir位置
```sudo vim /etc/mysql/my.cnf```
将[mysqld]里的
tmdir改成自己新建的
4.查看tmpdir位置
```show variables like 'tmpdir';```
