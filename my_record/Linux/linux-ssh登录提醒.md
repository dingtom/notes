```sudo apt install heirloom-mailx```
```sudo vim /etc/nail.rc```
```
set from=178@sina.cn //发信人邮箱
set smtp=smtps://smtp.sina.com //发信人邮箱的SMTP地址
set smtp-auth-user=178@sina.cn //发信人邮箱登陆账号
set smtp-auth-password=9f8f7149d10d0b1f //发信人邮箱的授权密码
set smtp-auth=login     //认证方式
set ssl-verify=ignore //忽略证书警告
set nss-config-dir=/etc/pki/nssdb //证书所在目录
```

测试
```echo "send mail test" | mail 2524@qq.com```


# 如何为root用户启用电子邮件警报

以root用户身份登录，然后将以下一行脚本添加到root用户“ .bashrc”文件中以实现此目的：
```vim /root/.bashrc ```
```echo 'ALERT - SSH root shell access found on '$HOSTNAME' on:' `date` `who` | mail -s "Alert: SSH root shell access" 2524@qq.com```
```source .bashrc```

# 如何为特定用户启用电子邮件警报

```vim /home/bob/.bashrc ```

```echo 'ALERT - '$USER' shell access found on '$HOSTNAME' on:' `date` `who` | mail -s "Alert: User shell access" 2524@qq.com```

# 如何为所有用户启用电子邮件警报
```vim /etc/bashrc```
```echo 'ALERT - '$USER' shell access found on '$HOSTNAME' on:' `date` `who` | mail -s "Alert: User shell access"  2524@qq.com```


