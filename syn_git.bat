@echo off   % 关闭回显%
python toc.py
	    ::echo off将echo状态设置为off表示关闭其他所有命令(不包括本身这条命令)的回显%
                    ::@的作用就是关闭紧跟其后的一条命令的回显%
                    ::C:\Users\liang\Desktop> 就是echo off 命令的回显%
title GIT一键提交
color 3
echo 当前目录是：%cd%
echo;

set /p declation=输入提交的commit信息:
echo;


set /p shutdownTime=设置多少秒后关机:
echo;


echo 本地主分支拉取远程主分支：git pull origin master
git pull origin master
echo;

echo 开始添加变更：git add .
git add .
git commit -m "%declation%"
echo;


 
echo 将变更情况提交到远程自己分支：git push
git push
echo;
				
echo 2分钟后关机
shutdown -s -t %shutdownTime%


