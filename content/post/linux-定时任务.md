---
        # 文章标题
        title: "dcoker-Docker"
        # 分类
        categories: 
            - dcoker
        # 发表日期
        date: 2022-12-02T02:11:14+08:00

        # 标签
        #tags:
        # 文章内容摘要
        #description: "{{ .Name }}" 
        # 最后修改日期
        #lastmod: {{ .Date }}
        # 文章内容关键字
        #keywords: "{{replace .Name "-" ","}}"
        # 原文作者
        #author:
        # 原文链接
        #link:
        # 图片链接，用在open graph和twitter卡片上
        #imgs:
        # 在首页展开内容
        #expand: true
        # 外部链接地址，访问时直接跳转
        #extlink:
        # 在当前页面关闭评论功能
        #comment:
        # enable: false
        # 关闭当前页面目录功能
        # 注意：正常情况下文章中有H2-H4标题会自动生成目录，无需额外配置
        #toc: false
        # 绝对访问路径
        #url: "{{ lower .Name }}.html"
        # 开启文章置顶，数字越小越靠前
        #weight: 1
        #开启数学公式渲染，可选值： mathjax, katex
        #math: mathjax
        # 开启各种图渲染，如流程图、时序图、类图等
        #mermaid: true
--- 

# python

```python
import os, time

while 1:
    timing = time.strftime('%H_%M', time.localtime(time.time()))
    # print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) )
    if timing == '12_00':
        print("开始运行脚本:")
        # runner.run(alltestnames)  # 执行测试用例
        print("运行完成退出")
        break
    else:
        time.sleep(5)
        print(timing)
```

# at 

- at now+5 minutes
  at> python  /home/fnngj/test/fiele.py
  Ctrl+d 保存退出

- at-l  查看创建的任务

- atrm10   10 是任务的“编号”，atrm 用于删除已经创建的任务

- at 5:30pm 

  at 17:30 

  at 17:20 today 

  at now+3 hours 

  at now+180 minutes 

  at 17:30 14.1.11 

  at 17:30 1.11.14





# cron



## 安装

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

开启日志

系统默认是不打开cron日志的
```sudo vim /etc/rsyslog.d/50-default.conf```
找到cron.*，把前面的#去掉
```sudo service rsyslog restart```
```service cron restart```
```cat /var/log/cron.log```

## 命令

每条cron命令空一格后加上```2>&1```取消执行完命令后发一封邮件

```* * * * * yourname /abs_path_to_send_ip_to_mailbox.py```

> minute         hour         day         month         dayofweek         command
>
> - 星号（\*）可以用来代表所有有效的值。
> - 整数间的短线（\-）指定一个整数范围。譬如，1-4 
> - 用逗号（\,）隔开的一系列值指定一个列表。譬如，3, 6,
> - 正斜线（\/）可以用来指定间隔频率。在范围后加上 /<integer> 意味着在范围内可以跳过 integer。譬如，0-59/2可以用来在分钟字段定义每两分钟。间隔频率值还可以和星号一起使用。例如，*/3 的值可以用在月份字段中表示每三个月运行一次任务。
> - 开头为井号（#）的行是注释，不会被处理



```shell
30  7 * * * zut_csi pkill -f ...csi/anaconda3/lib/jupyter
04  0  * * * zut_csi cd ./anaconda3/lib;bash run.sh
```






