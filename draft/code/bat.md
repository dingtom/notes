
```
2>nul 是屏蔽操作失败显示的信息，如果成功依旧显示。

> nul 是屏蔽操作成功显示的信息，但是出错还是会显示(即1>nul)

pause  它会提示“请按任意键继续...” 

@echo off　　　　　　　　　　　不显示后续命令行及当前命令行 　

dir c:\*.* >a.txt　　　　　　　将c盘文件列表写入a.txt 　

call c:\ucdos\ucdos.bat　　　　调用ucdos 

echo 你好 　　　　　　　　　　 显示"你好" 

rem 准备运行wps 　　　　　　　 注释：准备运行wps 　

```

类似watch

```
@ECHO OFF
:loop
  cls
  %*
  timeout /t 1 > NUL
goto loop
```

