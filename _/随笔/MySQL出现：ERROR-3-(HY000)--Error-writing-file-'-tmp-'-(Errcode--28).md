- [ 原因：](#head1)
- [ 解决办法：](#head2)
# <span id="head1"> 原因：</span>
/tmp文件夹空间不足
# <span id="head2"> 解决办法：</span>
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
