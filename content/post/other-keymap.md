---
        # 文章标题
        title: "other-keymap"
        # 分类
        categories: 
            - other
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

# chrome

开一个新的标签页【Ctrl+T】
不小心关闭了一个或者多个标签页【Ctrl+Shift+T】
打开的是新的窗口【Ctrl+N】
【Ctrl+Tab】标签页之间切换；
【Alt+Tab】窗口之间切换。
【Ctrl+Shift+Delete】即可调出清除缓存窗口。
一个标签页切换多层历史记录【Alt】组合左右方向键。

# win视图

【田 + Tab】

- 打开「任务视图」

【田 + Ctrl + D】

- 创建新虚拟桌面

【田 + Ctrl + F4】

- 删除当前虚拟桌面

【田 + Ctrl + ← /→】

- 左右切换虚拟桌面 (老板键)





【田 + 数字】

- 切换到任务栏上第 N 个程序

AIt+Esc是按顺序切换程序窗口，比如第一个打开的是浏览器，第
二个打开的是视频播放器，第三个打开的是音乐播放器。当前的程序是浏览器，按At+ESc就会切换到播放器，再按
At+Esc又会切换到音乐播放器，再按At+ESc又会切换到浏览器界面。



# idea

## eclipse keymap

1. Ctrl + .                                                        将光标移动至当前文件中的下一个报错处或警告处

2. Ctrl + E                                                       最近打开的文件

3. Ctrl + O                                                       快速outline

4. Ctrl + L                                                        到指定的第n行

5. Ctrl + M                                                      编辑器窗口最大化

6. Ctrl + D                                                       删除当前行

7. Ctrl + H                                                       指定目录内代码批量查找

8. ctrl+K                                                       	查找下一个

9. ctrl+shift+K	                                             查找上一个

10. ctrl+F4	                                                     关闭当前代码选项卡

11. ctrl+pageup/down                                     选项卡切换

12. Ctr + ← →                                                   上、下一个单词

13. Ctr + ↑ ↓                                                        界面上下滑动

14. alt+shift+↑     	                                           一次选中单词，两次选中行

15. Alt + ↑ ↓                                                         当前行和上/下行交互位置

16. Alt + ← →                                                     上/ 下一个编辑的位置

17. Alt + /                                                             内容辅助

18. Ctrl+Alt+ ↓                                                     复制当前行

19. ctrl+alt+L	                                                   格式化代码

20. shift+tab/tab	                                            减少/扩大缩进（可以在代码中减少行缩进）

21. Shift + Enter /Ctrl + Shift + Enter               前行之下/上创建一个空白行

22. home /end                                                    行首/尾

23. shift + home/end                                          选中光标到行尾的内容

24. ctr + home /end                                           顶部/尾部

    ------

25. Ctrl + Shift + R    打开资源（不只是用来寻找Java文件）

26. Ctrl + Shift + T    打开类型

27. ctrl+shift+alt+N	通过一个字符快速查找位置

28. alt+F1	查找代码在其他界面模块的位置，颇为有用

29. F5	单步跳入

30. F6	单步跳过

31. F7	单步返回

32. F8	继续

33. ctr + num_- 折叠当前块

34. ctr + num_* 展开



## 正则替换

```
id 这是学号

^(.*)d.+是(.*)$
$1 \n  hi $2

i 
  hi 学号
  

```



## 注释

```
函数注释
websotrm：函数前/** + enter
pycharm: 函数名下""" + enter
```



格式化

webstorm 代码格式化设置与eslint standard一致

```js
Editor->CodeStyle->JavaScript

**Tabs and Indent**

Tab Szie/Indent/Continuation indent:全2

**Spaces** 

Within

Object literal braces/ES6 inport/export braces:对勾

**Punctuation**

Don’t use always

Use single quotes always

Trailing comma Keep



Editor->CodeStyle->HTML

Tab Szie/Indent/Continuation indent:全2

**Other**

do not indent children of里加入script标签
```

## Live Template

```js
类注释 Editor->File and Code Templates->Class
/**
 * $description
 * @author dingwenchao
 * @date ${YEAR}.${MONTH}.${DAY} ${TIME}
 */
 
方法注释 Editor->Live Templates-> + -> Templates Group -> + ->Live Templates
Abbreviation: * 
Template text:
*
 * $description$
 * @author dingwenchao
 * @date $date$
 * @param $params$ 
 * @return $return$
 */
 
 name           expression    defalut value
 description    complete()
 date           				date()
 params							methodParameters()
 return         				methodReturnType()
 
```





# Jupyter 

Jupyter Notebook 有两种键盘输入模式。编辑模式，允许你往单元中键入代码或文本；这时的单元框线是绿色的。命令模式，键盘输入运行程序命令；这时的单元框线是灰色。

## 命令模式 (按键 Esc 开启)快捷键：

- Enter : 转入编辑模式
- Shift-Enter : 运行本单元，选中下个单元
- Ctrl-Enter : 运行本单元
- Alt-Enter : 运行本单元，在其下插入新单元
- Y : 单元转入代码状态
- M :单元转入markdown状态
- R : 单元转入raw状态
- 1设定 1 级标题，2设定 2 级标题
- Up : 选中上方单元
- Down : 选中下方单元
- Shift-Down : 扩大选中上方单元
- Shift-Down :扩大选中下方单元
- A : 在上方插入新单元
- B : 在下方插入新单元
- D +D: 删除选中的单元
- Z :恢复删除的最后一个单元
- Shift-M : 合并选中的单元
- L : 显示行号
- O : 转换输出
- Shift-O : 转换输出滚动
- H : 显示快捷键帮助
- Shift-Space : 向上滚动
- Space : 向下滚动
- 0 : 重启Notebook内核

编辑模式 ( Enter 键启动)下快捷键

- Tab : 代码补全或缩进

- Shift-Tab : 缩进

- Ctrl-Home : 跳到单元开头

- Ctrl-Down : 跳到单元末尾 

- Esc : 进入命令模式

- Ctrl-Shift-- : 分割单元

  

