- [ 安装](#head1)
- [ 开启日志](#head2)
- [ 命令](#head3)


# <span id="head1"> 安装</span>

``` sudo apt-get install cron```

```service cron start```  启动服务
```service cron stop``` 关闭服务
```service cron restart``` 重启服务
```service cron reload ```重新载入配置
```service cron status``` 查看crond状态

```crontab -e ```     编辑创建一个定时服务
```crontab -l    ```   查看当前用户的定时任务
```crontab -r  ```    删除当前用户的定时任务

或者
```sudo vim /etc/crontab```

```cat /etc/crontab```
python脚本需要指定python 环境，不要有输出
```
* * * * * zut_csi /home/zut_csi/anaconda3/envs/tomding/bin/python /home/zut_csi/tomding/pymail.py 2>&1
* * * * * zut_csi /home/zut_csi/tomding/auto_run.sh 2>&1
*/1 * * * * zut_csi echo `date` >> /home/zut_csi/tomding/a.txt 2>&1
```
```cat /tomding/a.txt```

# <span id="head2"> 开启日志</span>
系统默认是不打开cron日志的
```sudo vim /etc/rsyslog.d/50-default.conf```
找到cron.*，把前面的#去掉
```sudo service rsyslog restart```
```service cron restart```
```cat /var/log/cron.log```

# <span id="head3"> 命令</span>
每条cron命令空一格后加上```2>&1```取消执行完命令后发一封邮件

```* * * * * yourname /abs_path_to_send_ip_to_mailbox.py```
>minute         hour         day         month         dayofweek         command
>- 星号（\*）可以用来代表所有有效的值。
>- 整数间的短线（\-）指定一个整数范围。譬如，1-4 
>- 用逗号（\,）隔开的一系列值指定一个列表。譬如，3, 6,
>- 正斜线（\/）可以用来指定间隔频率。在范围后加上 /<integer> 意味着在范围内可以跳过 integer。譬如，0-59/2可以用来在分钟字段定义每两分钟。间隔频率值还可以和星号一起使用。例如，*/3 的值可以用在月份字段中表示每三个月运行一次任务。
>- 开头为井号（#）的行是注释，不会被处理



```shell
30  7 * * * zut_csi pkill -f ...csi/anaconda3/lib/jupyter
04  0  * * * zut_csi cd ./anaconda3/lib;bash run.sh
```

