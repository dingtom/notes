---
        # 文章标题
        title: "other-Windows命令别名"
        # 分类
        categories: 
            - other
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

##### 新建bat文件
新建文件cmd_auto.bat , 输入自己需要的常用命令的别名。
```
@echo off
doskey ls=dir /b $*
doskey act=activate tensorflow-gpu $*
doskey tb=tensorboard --logdir $*
doskey pi=pip install $*
```
#####  修改注册表，使cmd启动时自动执行该bat文件
win+r，键入regedit，进入地址：计算机\HKEY_CURRENT_USER\Software\Microsoft\Command Processor
右边空白处右键新建->字符串值。
双击编辑该值，数值名随意，数值数据里填刚才新建的bat文件的路径
