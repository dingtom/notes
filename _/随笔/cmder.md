- [ 快捷键](#head1)
- [ alias](#head2)
- [ 集成到ide](#head3)
	- [ pycharm](#head4)
	- [ vscode](#head5)
# <span id="head1"> 快捷键</span>

ctr + r       历史命令搜索

# <span id="head2"> alias</span>

config文件夹下文件添加







# <span id="head3"> 集成到ide</span>

## <span id="head4"> pycharm</span>

termal页面shell path

```
"cmd.exe" /k ""C:\software\cmder\vendor\init.bat""  
```



## <span id="head5"> vscode</span>

settingjson添加

```
{
  "terminal.integrated.shell.windows": "cmd.exe",
  "terminal.integrated.shellArgs.windows": [
      "/k",
      "C:software/cmder/vendor/init.bat"
  ]

}
```

