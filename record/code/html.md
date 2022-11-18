![图片45.png](https://i.loli.net/2021/07/08/5gIpDVuSbOefdk4.png)
Web 标准提出的最佳体验方案：**结构、样式、行为相分离**。  
简单理解：**结构写到 HTML 文件中， 表现写到 CSS 文件中， 行为写到 JavaScript 文件中**

# HTML语法规则：

HTML 标签通常是成对出现的，例如 <html> 和 </html> ，我们称为**双标签**。
标签对中的第一个标签是开始标签，第二个标签是结束标签。 
有些特殊的标签必须是单个标签（极少情况），例如 <br />，我们称为**单标签**。 

双标签关系可以分为两类：包含关系和并列关系
    包含标签：

        <head>  
            <title> </title> 
        </head>

​    并列关系：
​         <head> </head>
​         <body> </body>

​    每个网页都会有一个基本的结构标签（也称为骨架标签），页面内容也是在这些基本标签上书写
![基本结构.png](https://i.loli.net/2021/07/08/7B5aIPUqRt8w9M4.png)

## DOCTYPE

```
    <!DOCTYPE html>  
	<html lang="en">
    文档类型声明标签,告诉浏览器这个页面采取html5版本来显示页面.
    en定义语言为英语
    zh-CN定义语言为中文
```
    在<head>标签内，可以通过<meta> 标签的 charset 属性来规定 HTML 文档应该使用哪种字符编码。
    <meta charset=" UTF-8" />。一般情况下，统一使用“UTF-8”编码，尽量统一写成标准的 "UTF-8"
##     标题标签
```
 <h1> - <h6>
```
​    为了使网页更具有语义化，我们经常会在页面中用到标题标签。HTML 提供了 6 个等级的网页标题，即<h1> - <h6> 。

##  段落标签

```
<p>标签
```
用于定义段落，它可以将整个网页分为若干个段落。落和段落之间保有空隙。

## 换行标签

文本强制换行显示，就需要使用换行标签 ``` <br />```

## 文本格式化标签

![格式化标签.png](https://i.loli.net/2021/07/08/VEBygUeTRNdxCXr.png)

## div和span标签

```
没有语义，它们就是一个盒子，用来装内容的。
<div> 标签用来布局，但是现在一行只能放一个<div>。 大盒子
<span> 标签用来布局，一行上可以多个 <span>。小盒子
```
## 图片标签

```<img src="图像URL" />```
src 是<img>标签的必须属性，它用于指定图像文件的路径和文件名。

## 链接标签

``` 
<a href="跳转目标" target="目标窗口的弹出方式"> 文本或图像 </a>
href：用于指定链接目标的url地址
target:用于指定链接页面的打开方式_self默认值，当前窗口打开。 _blank新窗口打开
#：空链接
```
## 锚点链接

点我们点击链接,可以快速定位到页面中的某个位置. 

```
<a href="#two"> 第2集 </a> 
<h3 id="two">第2集介绍</h3>
```
## 注释

```
<!-- 注释语句 -->      快捷键: ctrl +  / 
```



# 表格

​    1.表格主要用于显示、展示数据
​    3.表格的具体用法：

```
<table> </table> 是用于定义表格的标签。
<tr> </tr> 标签用于定义表格中的行，必须嵌套在 <table> </table>标签中。
<td> </td> 用于定义表格中的单元格，必须嵌套在<tr></tr>标签中。
字母 td 指表格数据（table data），即数据单元格的内容。
一般表头单元格位于表格的第一行或第一列，表头单元格里面的文本内容加粗居中显示.
​            <th> 标签表示 HTML 表格的表头部分(table head 的缩写)
```

表格标签这部分属性我们实际开发我们不常用，后面通过 CSS 来设置.

![](https://upload-images.jianshu.io/upload_images/18339009-008b49a53b977198.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

表格结构标签：
        因为表格可能很长,为了更好的表示表格的语义，可以将表格分割成 表格头部和表格主体两大部分.
    在表格标签中，分别用：<thead>标签 表格的头部区域、<tbody>标签 表格的主体区域. 这样可以更好的分清表格结构。

**总结:**

```
1. <thead></thead>：用于定义表格的头部。<thead> 内部必须拥有 <tr> 标签。 一般是位于第一行。
2. <tbody></tbody>：用于定义表格的主体，主要用于放数据本体 。
3.  以上标签都是放在 <table></table> 标签中。
```

合并单元格：

**跨行合并**：rowspan="合并单元格的个数" ，最上侧单元格为目标单元格, 写合并代码

 **跨列合并**：colspan="合并单元格的个数"，最左侧单元格为目标单元格, 写合并代码

<table>
	<thead>
		<tr>
        <th>名字</th><td>性别 </td>
		</tr>
	</thead>
	<tbody>
		<tr>
        	<td>李逵 </td><td> 男</td>
    	</tr>
    	<tr>
    		<td colspan=“2”>？？？？？？？？咋合并</td><td colspan=“2”></td>
    	</tr>
    </tbody>
</table>
# 列表

表格是用来显示数据的，那么列表就是用来布局的。 

无序

```
<ul>标签表示 HTML 页面中项目的无序列表，一般会以项目符号呈现列表项，而列表项使用 <li> 标签定义。无序列表的基本语法格式如下：
```

<ul> 
  <li>列表项1</li>   <li>列表项2</li>   <li>列表项3</li>   ... 
</ul>

```
<ul></ul> 中只能嵌套 <li></li>，直接在 <ul></ul> 标签中输入其他标签或者文字的做法是不被允许的。
无序列表会带有自己的样式属性，但在实际使用时，我们会使用 CSS 来设置。
```

有序

有序列表即为有排列顺序的列表，其各个列表项会按照一定的顺序排列定义。在 HTML 标签中，<ol> 标签用于定义有序列表，列表排序以数字来显示，并且使用 <li> 标签来定义列表项。有序列表的基本语法格式如下：

<ol>   <li>列表项1</li>   <li>列表项2</li>   <li>列表项3</li>   ... </ol>

```
1. <ol></ol>中只能嵌套<li></li>，直接在<ol></ol>标签中输入其他标签或者文字的做法是不被允许的。
2. <li> 与 </li>之间相当于一个容器，可以容纳所有元素。
3. 有序列表会带有自己样式属性，但在实际使用时，我们会使用 CSS 来设置。
```



自定义列表：

```
<dl> 标签用于定义描述列表（或定义列表），该标签会与 <dt>（定义项目/名字）和 <dd>（描述每一个项目/名字）一起使用。语法如下：
```

<dl>   <dt>名词1</dt>   <dd>名词1解释1</dd>   <dd>名词1解释2</dd> <dt>名词2</dt>   <dd>名词2解释1</dd>   <dd>名词2解释2</dd></dl>



# 表单

一个完整的表单通常由表单域、表单控件（也称为表单元素）和 提示信息3个部分构成。
表单域：
    表单域是一个包含表单元素的区域。

```
在 HTML 标签中， <form> 会把它范围内的表单元素信息提交给服务器.
```

<form action=“url地址” method=“提交方式” name=“表单域名称">各种表单元素控件</form>

表单域的常用属性：

![](https://i.loli.net/2021/07/08/zvUZxbSiaRXJKwn.png)

## 表单控件(表单元素)

表单元素中 <input> 标签用于收集用户信息。在 <input> 标签中，包含一个 type 属性，根据不同的 type 属性值，输入字段拥有很多种形式（可以是文本字段、复选框、掩码后的文本控件、单选按钮、按钮等）。

<input type="button"  />

type 属性的属性值及其描述如下：

![](https://upload-images.jianshu.io/upload_images/18339009-e92938bf06a7b3ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

除 type 属性外，<input>标签还有其他很多属性，其常用属性如下：

![](https://upload-images.jianshu.io/upload_images/18339009-d98083cda27a02d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
用于绑定一个表单元素,为 input 元素定义标注 当点击<label> 标签内的文本时，浏览器就会自动将焦点(光标)转到或者选择对应的表单元素上,用来增加用户体验.
语法：

```
<label for="sex">男</label>
<input type="radio" name="sex"  id="sex" />
```

核心： <label> 标签的 for 属性应当与相关元素的 id 属性相同。

## 下拉列表

```

<select>
   <option>选项1</option>
   <option>选项2</option>
   <option>选项3</option>
   ...
 </select>

```

<select>
   <option>选项1</option>
   <option>选项2</option>
   <option>选项3</option>
   ...
 </select>
## 文本框

使用场景: 当用户输入内容较多的情况下，我们就不能使用文本框表单了，此时我们可以使用 <textarea> 标签。该控件常见于留言板，评论。

```
<textarea rows="3" cols="20">   文本内容 </textarea>

<textarea rows="3" cols="20">   文本内容 </textarea>

```

我们在实际开发中不会使用，都是用 CSS 来改变大小。



表单元素我们学习了三大组  input 输入表单元素  select 下拉表单元素  textarea 文本域表单元素.这三组表单元素都应该包含在form表单域里面,并且有 name 属性.

具体代码：

```
	<form>

​		<input type=“text " name=“username”>

​		<select name=“jiguan”>  

​		 <option>北京</option>

​		</select> 

​		<textarea name= "message">

​		</textarea>

​	</form>

```

# 例子

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>综合案例-注册页面</title>
</head>
<body>
    <h4>青春不常在，抓紧谈恋爱</h4>
    <table width="600" >
        <!-- 第一行 -->
        <tr>
            <td>性别:</td>
            <td>
                <input type="radio" name="sex" id="nan"> <label for="nan"> <img src="images/man.jpg" > 男 </label>  
                <input type="radio" name="sex" id="nv"> <label for="nv"><img src="images/women.jpg" > 女</label> 
            </td>
        </tr>
        <!-- 第二行 -->
        <tr>
            <td>生日:</td>
            <td>
                <select>
                    <option>--请选择年份--</option>
                    <option>2001</option>
                    <option>2002</option>
                    <option>2003</option>
                </select>
                <select>
                        <option>--请选择月份--</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                </select>
                <select>
                        <option>--请选择日--</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                </select>
        
            </td>
        </tr>
        <!-- 第三行 -->
        <tr>
            <td>所在地区</td>
            <td><input type="text" value="北京思密达"></td>
        </tr>
        <!-- 第四行 -->
        <tr>
            <td>婚姻状况:</td>
            <td>
                    <input type="radio" name="marry" checked="checked">未婚  <input type="radio" name="marry">  已婚  <input type="radio" name="marry"> 离婚
            </td>
        </tr>
         <!-- 第五行 -->
         <tr>
             <td>学历:</td>
             <td><input type="text" value="博士后"></td>
         </tr>
          <!-- 第六行 -->
        <tr>
            <td>喜欢的类型:</td>
            <td>
                <input type="checkbox" name="love" > 妩媚的 
                <input type="checkbox" name="love" > 可爱的 
                <input type="checkbox" name="love" > 小鲜肉 
                <input type="checkbox" name="love" > 老腊肉 
                <input type="checkbox" name="love"  checked="checked"> 都喜欢 
            </td>
        </tr>
         <!-- 第七行 -->
        <tr>
            <td>个人介绍</td>
            <td>
                <textarea>个人简介</textarea>
            </td>
        </tr>
        <!-- 第八行 -->
        <tr>
            <td></td>
            <td>
                <input type="submit" value="免费注册" >
            </td>
        </tr>
        <tr>
                <td></td>
                <td>
                    <input type="checkbox"  checked="checked">    我同意注册条款和会员加入标准
                </td>
        </tr>
        <tr>
                <td></td>
                <td>
                    <a href="#" > 我是会员，立即登录</a>
                </td>
        </tr>
        <tr>
                <td></td>
                <td>
                    <h5>我承诺</h5>
                    <ul>
                        <li>年满18岁、单身</li>
                        <li>抱着严肃的态度</li>
                        <li>真诚寻找另一半</li>
                    </ul>
                </td>
        </tr>
           
    </table>
</body>
</html>
```











