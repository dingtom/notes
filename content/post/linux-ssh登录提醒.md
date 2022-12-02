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


