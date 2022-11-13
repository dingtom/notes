- [ 生成目录](#head1)
- [To Do List](#head2)
- [ 横线](#head3)
- [ 删除线](#head4)
- [ 列表点号](#head5)
- [ 表格](#head6)
- [ 标注颜色](#head7)
- [ 设置背景](#head8)
- [ 设置文字背景](#head9)
- [ 区块引用](#head10)
- [ 链接](#head11)
- [ 锚点](#head12)
- [ 注脚](#head13)
- [ 定义图片尺寸](#head14)
- [ 图片居中](#head15)
- [ 插入视频](#head16)
- [ 插入音乐](#head17)
- [ 设置字体](#head18)
- [ emoj表情](#head19)
- [ 首行缩进](#head20)
- [ 绘图](#head21)
- [ 甘特图](#head22)
- [ 流程图](#head23)
	- [ 指向流程(连接元素)：标识（类别）->下一个标识](#head24)
# <span id="head1"> 生成目录</span>
``` 
[TOC]
```
[TOC]
# <span id="head2">To Do List</span>
```
- [x] 学习python基础 【减号+[+空格或x+]+空格】
- [ ] 学习python网络编程
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-58fe77302ab071f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# <span id="head3"> 横线</span>
```
---
```
----

# <span id="head4"> 删除线</span>
```
~~哈哈~~
```
~~哈哈~~


# <span id="head5"> 列表点号</span>
```
- 
```
- 


# <span id="head6"> 表格</span>
```
|Theme name|Value|Directive|
|:-:|:-:|:-:|
|***Default***|default|`<!-- $theme: default -->`
|**Gaia**|gaia|`<!-- $theme: gaia -->`
```
|Theme name|Value|Directive|
|:-:|:-:|:-|
|***Default***|default|`<!-- $theme: default -->`
|**Gaia**|gaia|`<!-- $theme: gaia -->`
```
<table>
    <tr><th rowspan=4>会员登记表</th><th>姓名</th><th colspan=3>个人信息</th></tr>
    <tr><td>张三</td><td>男</td><td>25</td><td>未婚</td></tr>
    <tr><td>李四</td><td>女</td><td>35</td><td>已婚</td></tr>
    <tr><td>王五</td><td>男</td><td>45</td><td>离异</td></tr>
</table>
```


# <span id="head7"> 标注颜色</span>
```
==word==
```
==word==

# <span id="head8"> 设置背景</span>
```
![bg](http://img0.imgtn.bdimg.com/it/u=1377132983,1746819781&fm=26&gp=0.jpg)
```
[图片上传失败...(image-cc6f84-1606352227647)]

# <span id="head9"> 设置文字背景</span>
```
<span style="background-color:yellow;">yellow marker highlight</span>
```
<span style="background-color:yellow;">yellow marker highlight</span>

# <span id="head10"> 区块引用</span>
```
* python
> 一种编程语言

> 一级引用
>> 二级引用
>>> 三级引用

```
* python
> 一种编程语言

> 一级引用
> > 二级引用
> >
> > > 三级引用

# <span id="head11"> 链接</span>
```
[Yuki Hattori](https://github.com/yhatt)
```
[Yuki Hattori](https://github.com/yhatt)
# <span id="head12"> 锚点</span>
跳转需要按住ctrl
```
# titleA //这是个一级标题，锚点

[titleA](#titleA) //这是锚点引用格式
```
[生成目录](#生成目录) //这是锚点引用格式





首先是建立一个跳转的连接：

```
[说明文字](#jump)1
```

然后标记要跳转到的位置：

```
<span id = "jump">跳转到的位置</span>
```

# <span id="head13"> 注脚</span>

```
使用 Markdown[^1]可以效率的书写文档, 直接转换成 HTML[^2], 你可以使用 Leanote[^Le] 编辑器进行书写。
[^1]:Markdown是一种纯文本标记语言
[^2]:HyperText Markup Language 超文本标记语言
[^Le]:开源笔记平台，支持Markdown和笔记直接发为博文
```
使用 Markdown[^1]可以效率的书写文档, 直接转换成 HTML[^2], 你可以使用 Leanote[^Le] 编辑器进行书写。
[^1]:Markdown是一种纯文本标记语言
[^2]:HyperText Markup Language 超文本标记语言
[^Le]:开源笔记平台，支持Markdown和笔记直接发为博文


# <span id="head14"> 定义图片尺寸</span>
```
<img width = '150' height ='150' src ="https://tse2-mm.cn.bing.net/th?id=OIP.rF3VYN1CRvtyWBPU0I7kyQDMEy&p=0&pid=1.1"/>
```
<img width = '150' height ='150' src ="https://tse2-mm.cn.bing.net/th?id=OIP.rF3VYN1CRvtyWBPU0I7kyQDMEy&p=0&pid=1.1"/>

# <span id="head15"> 图片居中</span>
```
<div align=center><img width = '150' height ='150' src ="https://tse2-mm.cn.bing.net/th?id=OIP.rF3VYN1CRvtyWBPU0I7kyQDMEy&p=0&pid=1.1"/></div>
```
<div align=center><img width = '150' height ='150' src ="https://tse2-mm.cn.bing.net/th?id=OIP.rF3VYN1CRvtyWBPU0I7kyQDMEy&p=0&pid=1.1"/></div>
# <span id="head16"> 插入视频</span>
```
<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=330 height=86 src="//music.163.com/outchain/player?type=2&id=528478901&auto=1&height=66"></iframe>
```
<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=330 height=86 src="//music.163.com/outchain/player?type=2&id=528478901&auto=1&height=66"></iframe>
# <span id="head17"> 插入音乐</span>
```
<iframe width="560" height="315" src="https://www.youtube.com/embed/Ilg3gGewQ5U" frameborder="0" allowfullscreen></iframe>
```
<iframe width="560" height="315" src="https://www.youtube.com/embed/Ilg3gGewQ5U" frameborder="0" allowfullscreen></iframe>
# <span id="head18"> 设置字体</span>
```
<font face="黑体">我是黑体字</font>
<font face="微软雅黑">我是微软雅黑</font>
<font face="STCAIYUN">我是华文彩云</font》
<font face="黑体" color=green size=5>我是黑体，绿色，尺寸为5</font>
```
<font face="黑体">我是黑体字</font>
<font face="微软雅黑">我是微软雅黑</font>
<font face="STCAIYUN">我是华文彩云</font》
<font face="黑体" color=green size=5>我是黑体，绿色，尺寸为5</font>

# <span id="head19"> emoj表情</span>
https://github.com/guodongxiaren/README/blob/master/emoji.md

# <span id="head20"> 首行缩进</span>
```
半方大的空白&ensp;或&#8194;
全方大的空白&emsp;或&#8195;
不断行的空白格&nbsp;或&#160;
```
flow
st=>start: Start:>https://www.zybuluo.com
io=>inputoutput: verification
op=>operation: Your Operation
cond=>condition: Yes or No?
sub=>subroutine: Your Subroutine
e=>end
st->io->op->cond
cond(yes)->e
cond(no)->sub->io



# <span id="head21"> 绘图</span>
[https://blog.csdn.net/qq_18150255/article/details/88043774](https://blog.csdn.net/qq_18150255/article/details/88043774)
[https://blog.csdn.net/weixin_30307267/article/details/99385290](https://blog.csdn.net/weixin_30307267/article/details/99385290)

# <span id="head22"> 甘特图</span>
```
gantt
dateFormat YYYY-MM-DD
title “所见”微信小程序-项目进度甘特图
需求分析 :active, des2, 2019-04-10, 5d
可行性分析 :active, des2, 2019-04-13, 4d
项目开发计划 :active, des2, 2019-04-16, 5d
系统设计 :active, des2, 2019-04-19, 7d
开发实现 :active, des2, 2019-04-26, 14d
UI设计 :active, des2, 2019-04-26, 7d
数据库设计 :active, des2, 2019-05-05, 5d
系统测试 :active, des2, 2019-05-10, 5d
操作手册 :active, des2, 2019-05-15, 5d
用户手册 :active, des2, 2019-05-15, 5d
完善项目文档/准备答辩 : des3, after des2, 10d
```
# <span id="head23"> 流程图</span>
流程图代码分两块，上面一块是创建你的流程（创建元素），然后隔一行，创建流程的走向(连接元素)

#####创建流程（元素）：tag=>type: content:>url
**tag 是流程图中的标签**，在第二段连接元素时会用到。名称可以任意，一般为流程的英文缩写和数字的组合。
**type 用来确定标签的类型**，=>后面表示类型。由于标签的名称可以任意指定，所以要依赖type来确定标签的类型
标签有6种类型：start 、end 、operation 、subroutine 、condition 、inputoutput
**content 是流程图文本框中的描述内容**，: 后面表示内容，中英文均可。特别注意，冒号与文本之间一定要有个空格
**url是一个连接**，与框框中的文本相绑定，:>后面就是对应的 url 链接，点击文本时可以通过链接跳转到 url 指定页面
##### <span id="head24"> 指向流程(连接元素)：标识（类别）->下一个标识</span>
使用 -> 来连接两个元素
对于**condition类型**，有yes和no两个分支，如示例中的cond(yes)和cond(no)
每个元素可以制定分支走向，默认向下，也可以用right指向右边，如示例中cond2(yes,right)。

流程图元素
```flow
star1=>start: 开始 
end1=>end: 登录 
in1=>inputoutput: 输入用户名密码 
sub1=>subroutine: 数据库查询子类 
cond1=>condition: 是否有此用户 
cond2=>condition: 密码是否正确 
ope1=>operation: 读入用户信息
star1->in1->sub1->cond1 
cond1(yes,right)->cond2 
cond1(no)->in1(right) 
cond2(yes,right)->ope1->end1
cond2(no)->in1
```

![image.png](https://upload-images.jianshu.io/upload_images/18339009-04ebbcf73810a895.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
