---
        # 文章标题
        title: "other-cmder"
        # 分类
        categories: 
            - other
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

# 快捷键

ctr + r       历史命令搜索

# alias

config文件夹下文件添加







# 集成到ide

## pycharm

termal页面shell path

```
"cmd.exe" /k ""C:\software\cmder\vendor\init.bat""  
```



## vscode

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

