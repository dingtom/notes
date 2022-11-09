- [ css语法规范](#head1)
- [ css基础选择器](#head2)
	- [ 标签选择器：](#head3)
	- [ 通配符选择器：](#head4)
	- [ 子元素](#head5)
	- [ 选择器总结](#head6)
		- [ ![基础选择器总结.png](https://i.loli.net/2021/07/12/H5zkcC6NgxqtVvp.png)](#head7)
- [ css样式表：](#head8)
	- [ 行内样式表（行内式）](#head9)
	- [ 内部样式表（嵌入式）](#head10)
	- [ 外部样式表（链接式）](#head11)
		- [ emmet语法](#head12)
			- [ 1、简介](#head13)
			- [ 2、快速生成HTML结构语法](#head14)
			- [ 3、快速生成CSS样式语法](#head15)
			- [ 4、快速格式化代码](#head16)
- [ css的复合选择器](#head17)
	- [ 1、什么是复合选择器？](#head18)
	- [2、后代选择器 (重要）](#head19)
	- [3、子选择器 (重要）](#head20)
	- [4、并集选择器 (重要）](#head21)
	- [ 5、伪类选择器](#head22)
		- [ 6、链接伪类选择器](#head23)
	- [7、:focus 伪类选择器](#head24)
		- [ 复合选择器总结](#head25)
	- [ 模糊选择](#head26)
- [ css的显示模式](#head27)
	- [ 什么是元素的显示模式](#head28)
	- [ 块元素](#head29)
	- [ 行内元素](#head30)
	- [ 行内块元素](#head31)
	- [ 元素显示模式总结](#head32)
	- [ 元素显示模式的转换](#head33)
		- [ 单行文字垂直居中的代码](#head34)
	- [ 背景平铺](#head35)
	- [ 背景图片位置](#head36)
	- [ 背景图片固定](#head37)
	- [ 背景样式合写](#head38)
	- [ 背景色半透明](#head39)
- [ css三大特性](#head40)
	- [ 层叠性](#head41)
	- [ 继承性](#head42)
	- [ 优先级](#head43)
- [ 盒子模型](#head44)
	- [ 网页布局的本质](#head45)
	- [盒子模型（Box Model）组成](#head46)
	- [ 内边距（padding）](#head47)
	- [ 外边距（margin）](#head48)
- [ 其他样式	](#head49)
	- [ 1、圆角边框](#head50)
	- [ 2、盒子阴影](#head51)
	- [ 3、文字阴影](#head52)
- [ 浮动](#head53)
	- [ 1、传统网页布局的三种方式](#head54)
	- [ 2、标准流（普通流/文档流）](#head55)
	- [ 3、为什么需要浮动？](#head56)
	- [ 4、什么是浮动？](#head57)
	- [ 5、浮动特性](#head58)
	- [ 6、浮动元素经常和标准流父级搭配使用](#head59)
- [ 清除浮动](#head60)
	- [ 1、为什么需要清除浮动？](#head61)
	- [ 2、清除浮动本质](#head62)
	- [ 3、清除浮动样式](#head63)
	- [ 4、清除浮动的多种方式](#head64)
		- [ 4.1、额外标签法](#head65)
		- [4.2、父级添加 overflow 属性](#head66)
		- [ 4.3、父级添加after伪元素](#head67)
		- [ 4.4、父级添加双伪元素](#head68)
- [ flex](#head69)
- [ position](#head70)
- [ 居中](#head71)
- [ 底部固定](#head72)
- [Media Query](#head73)
- [content ](#head74)
- [ box-sizing](#head75)
- [opacity ](#head76)
- [ backface-visibility:hidden](#head77)
- [transform ](#head78)
- [ word-wrap](#head79)
- [ word-break](#head80)
- [ white-space](#head81)

​       CSS 是层叠样式表 ( Cascading Style Sheets ) 的简称.

​    CSS 最大价值: 由 HTML 专注去做结构呈现，样式交给 CSS，即 结构 ( HTML ) 与样式( CSS ) 相分离



# <span id="head1"> css语法规范</span>
CSS 规则由两个主要的部分构成：选择器以及一条或多条声明。

​        /* 选择器 {样式} */

​        /* 给谁改样式 { 改什么样式} */

![css属性规则.png](https://i.loli.net/2021/07/11/6OwJpnETo1evst3.png) 



# <span id="head2"> css基础选择器</span>

基础选择器又包括：标签选择器、类选择器、id 选择器和通配符选择器

## <span id="head3"> 标签选择器：</span>

​    标签选择器（元素选择器）是指用 HTML 标签名称作为选择器，按标签名称分类，**为页面中某一类标签指定统一的 CSS 样式。**

​    标签选择器{
​        属性：属性值
​        ...
​    }

##　类选择器


​    .类名 {
​        属性1: 属性值1;  
​        ...
​    } 
​    结构需要用class属性来调用  class  类的意思

     <div class="类名" "类名2"> 变红色 </div>
​    如果想要差异化选择不同的标签，单独选一个或者某几个标签，可以使用类选择器。

​    我们可以给一个标签指定多个类名，从而达到更多的选择目的。 这些类名都可以选出这个标签.

   在标签class 属性中写多个类名中间必须用空格分开，例如：class="box red/green" 所有box一样，不同颜色不同

##　id选择器：

​    id 选择器可以为标有特定 id 的 HTML 元素指定特定的样式。
​         #id名 {
属性1: 属性值1;  
...
​        } 

注意：**id 属性只能在每个 HTML 文档中出现一次**

**id选择器和类选择器的区别：**

​    1.类选择器（class）好比人的名字，一个人可以有多个名字，同时一个名字也可以被多个人使用。
​    2.id 选择器好比人的身份证号码，全中国是唯一的，不得重复。
​    3.**id 选择器和类选择器最大的不同在于使用次数上。**
​    4.类选择器在修改样式中用的最多，id 选择器一般用于页面唯一性的元素上，经常和 JavaScript 搭配使用。

## <span id="head4"> 通配符选择器：</span>

```
        * {
            属性1: 属性值1;  
            ...
                }
```
通配符选择器不需要调用， **自动就给所有的元素使用样式**，特殊情况才使用

## <span id="head5"> 子元素</span>

li:first-child: 选择li父元素的第一个li子元素

last-child,nth-child(3),nth-last-child(3),

## <span id="head6"> 选择器总结</span>

###### <span id="head7"> ![基础选择器总结.png](https://i.loli.net/2021/07/12/H5zkcC6NgxqtVvp.png)</span>

# <span id="head8"> css样式表：</span>

## <span id="head9"> 行内样式表（行内式）</span>

行内样式表（内联样式表）是在元素标签内部的 style 属性中设定 CSS 样式。适合于修改简单样式.

            <div style="color: red; font-size: 12px;">青春不常在，抓紧谈恋爱</div>
​        1.style 其实就是标签的属性
​        2.可以控制当前的标签设置样式
​        3.由于书写繁琐，并且没有体现出结构与样式相分离的思想，所以不推荐大量使用，只有对当前元素添加简单样式的时候，可以考虑使用

## <span id="head10"> 内部样式表（嵌入式）</span>

​        内部样式表（内嵌样式表）是写到html页面内部. 是将所有的 CSS 代码抽取出来，单独放到一个 <style> 标签中
​        语法：

            <style>
                div {
                color: red;
                font-size: 12px;
                }
            </style>

​        1.<style> 标签理论上可以放在 HTML 文档的任何地方，但一般会放在文档的<head>标签中
​        2.通过此种方式，可以方便控制当前整个页面中的元素样式设置
​        3.代码结构清晰，但是并没有实现结构与样式完全分离

## <span id="head11"> 外部样式表（链接式）</span>

​    实际开发都是外部样式表. 适合于样式比较多的情况. 核心是:样式单独写到CSS 文件中，之后把CSS文件引入到 HTML 页面中使用.

       1. 新建一个后缀名为 .css 的样式文件，把所有 CSS 代码都放入此文件中。
       2. 在 HTML 页面中，使用<link> 标签引入这个文件。
     
           <link rel="stylesheet"  href="css文件路径">

### <span id="head12"> emmet语法</span>

###### <span id="head13"> 1、简介</span>

​		Emmet语法的前身是Zen coding,它使用缩写,来提高html/css的编写速度, Vscode内部已经集成该语法。

​		快速生成HTML结构、快速生成CSS样式语法

​		快速生成CSS样式语法

###### <span id="head14"> 2、快速生成HTML结构语法</span>

- 生成标签 直接**输入标签名 按tab键**即可   比如  div   然后tab 键， 就可以生成 <div></div>
- 如果想要生成多个相同标签  加上 * 就可以了 比如   **div*3  就可以快速生成3个div**
- 如果有**父子级关系的标签，可以用 >**  比如   ul > li就可以了
- 如果有**兄弟关系的标签，用  +**  就可以了 比如 div+p  
- 如果生成带有类名/id名字的，  直接写  .demo/#two   然后tab 键就可以了
- 如果生成的**div 类名是有顺序的， 可以后面加自增符号  $** 
- 如果想要在生成的**标签内部写内容可以用  div{文字}**  
- ```div{$}*5       .demo$*5                 ```

###### <span id="head15"> 3、快速生成CSS样式语法</span>

CSS 基本采取简写形式即可

​		比如 w200   按tab  可以 生成  width: 200px;

​		比如 lh26px   按tab  可以生成  line-height: 26px;

###### <span id="head16"> 4、快速格式化代码</span>

Vscode  快速格式化代码:   shift+alt+f

也可以设置 当我们 保存页面的时候自动格式化代码:

1）文件 ------.>【首选项】---------->【设置】；

2）搜索emmet.include;

3）在settings.json下的【工作区设置】中添加以下语句：

​		"editor.formatOnType": true,

​		"editor.formatOnSave": true

# <span id="head17"> css的复合选择器</span>

## <span id="head18"> 1、什么是复合选择器？</span>

​		在 CSS 中，可以根据选择器的类型把选择器分为**基础选择器**和**复合选择器**，复合选择器是建立在基础选择器之上，对基本选择器进行组合形成的。 
​		常用的复合选择器包括：**后代选择器、子选择器、并集选择器、伪类选择器**等等

## <span id="head19">2、后代选择器 (重要）</span>

​		后代选择器又称为包含选择器，可以选择父元素里面子元素。

```
元素1 元素2(样式声明}
```

上述语法表示选择元素 1 里面的所有元素 2 (后代元素)。

**语法说明**：

- 元素1 和 元素2 中间用空格隔开
- 元素1 是父级，元素2 是子级，最终选择的是元素2
- 元素2 可以是儿子，也可以是孙子等，只要是元素1 的后代即可
- 元素1 和 元素2 可以是任意基础选择器

## <span id="head20">3、子选择器 (重要）</span>

​		（简单理解就是选亲儿子元素）

```
元素1 > 元素2{样式声明}
```

​		上述语法表示选择元素1 里面的所有直接后代(子元素) 元素2。

**语法说明**：

- 元素1 和 元素2 中间用 大于号 隔开
- 元素1 是父级，元素2 是子级，最终选择的是元素2
- 元素2 必须是亲儿子，其孙子、重孙之类都不归他管. 你也可以叫他 亲儿子选择器

## <span id="head21">4、并集选择器 (重要）</span>

​		并集选择器可以选择多组标签, 同时为他们定义相同的样式，通常用于集体声明。并集选择器是各选择器通过英文逗号（,）连接而成，任何形式的选择器都可以作为并集选择器的一部分。

```
元素1，元素2{样式声明}
```

​		上述语法表示选择元素1 和 元素2。

**语法说明**：

- 元素1 和 元素2 中间用逗号隔开
- 逗号可以理解为和的意思
- 并集选择器通常用于集体声明





## <span id="head22"> 5、伪类选择器</span>

​		伪类选择器用于**向某些选择器添加特殊的效果**，比如给链接添加特殊效果，或选择第1个，第n个元素。

###### <span id="head23"> 6、链接伪类选择器</span>

**语法：**

​		伪类选择器书写最大的特点是用冒号（:）表示，比如 :hover 、 :first-child 。

​		a:link	没有点击过的(访问过的)链接
​		a:visited	点击过的(访问过的)链接
​		a:hover	鼠标经过的那个链接
​		a:active	鼠标正在按下还没有弹起鼠标的那个链接

**链接伪类选择器注意事项**

​		为了确保生效，**请按照 LVHA 的循顺序声明 :link－:visited－:hover－:active。**

​		记忆法：love hate 或者 lv 包包 hao 。

​		因为 a 链接在浏览器中具有默认样式，所以我们实际工作中都需要给链接单独指定样式。

**链接伪类选择器实际工作开发中的写法**：

```
/*a是标签选择器所有的连接*/
a {
	color: gray;
}
/* :hover是链接伪类选择器鼠标经过*/
a:hover {
	co1or: red;/*鼠标经过的时候，由原来的灰色变成了红色*/
}

```

## <span id="head24">7、:focus 伪类选择器</span>

​		:focus 伪类选择器用于选取获得焦点的表单元素。焦点就是光标，一般情况 <input> 类表单元素才能获取

```
input: focus{
	background-color:yellow;
}
```

###### <span id="head25"> 复合选择器总结</span>

![](https://i.loli.net/2021/07/08/xUlBFwXmCKghfo8.png)

## <span id="head26"> 模糊选择</span>

```html
// class 属性值包含 "test" 的所有 div 元素
div[class*="test"]
.show-grid下的class属性值包含 "test" 
.show-grid [class*="test"]
// class属性值以 "test" 开头 
div[class^="test"]
// class属性值以 "test" 结尾
div[class$="test"]
```



# <span id="head27"> css的显示模式</span>

## <span id="head28"> 什么是元素的显示模式</span>

**定义：**

​		元素显示模式就是元素（标签）以什么方式进行显示，比如<div>自己占一行，比如一行可以放多个<span>。

**作用：**

​		网页的标签非常多，在不同地方会用到不同类型的标签，了解他们的特点可以更好的布局我们的网页。

## <span id="head29"> 块元素</span>

**常见的块元素**：

```
<h1>~<h6>、<p>、<div>、<ul>、<ol>、<li>
```

​		<div> 标签是最典型的块元素。

**块级元素的特点**：

- 自己独占一行。
- 高度，宽度、外边距以及内边距都可以控制。
- 宽度默认是容器（父级宽度）的100%。
- 是一个容器及盒子，里面**可以放行内或者块级元素。**

**注意：**

​		**文字类的元素内不能放块级元素**

```
<p> 标签主要用于存放文字，因此 <p> 里面不能放块级元素，特别是不能放<div> 
同理， <h1>~<h6>等都是文字类块级标签，里面也不能放其他块级元素
```



## <span id="head30"> 行内元素</span>

**常见的行内元素：**

```
<a>、<strong>、<b>、<em>、<i>、<del>、<s>、<ins>、<u>、<span>
```

​		<span> 标签是最典型的行内元素。有的地方也将行内元素称为内联元素。

**行内元素的特点：**

- 相邻行内元素在一行上，一行可以显示多个。

- **高、宽直接设置是无效的。**

- 默认宽度就是它本身内容的宽度。

- 行内元素只能容纳文本或其他行内元素。

  

**注意：**
		链接里面不能再放链接
		特殊情况链接 <a> 里面可以放块级元素，但是给 <a> 转换一下块级模式最安全



## <span id="head31"> 行内块元素</span>

**常见的行内块标签**：

```
<img />、<input />、<td>
```

​		**它们同时具有块元素和行内元素的特点。有些资料称它们为行内块元素。**

**行内块元素的特点**：

- 和相邻行内元素（行内块）在一行上，但是他们之间会有空白缝隙。
- 一行可以显示多个（行内元素特点）。
- 默认宽度就是它本身内容的宽度（行内元素特点）。
- 高度，行高、外边距以及内边距都可以控制（块级元素特点）。

## <span id="head32"> 元素显示模式总结</span>

![1570870718415.png](https://i.loli.net/2021/07/12/YGDSukyfRTAKCWp.png)

​		学习元素显示模式的主要目的就是分清它们各自的特点，当我们网页布局的时候，在合适的地方用合适的标签元素。

## <span id="head33"> 元素显示模式的转换</span>

**简单理解**: 

​		一个模式的元素需要另外一种模式的特性
​		比如想要增加链接 <a> 的触发范围。   

**转换方式**

- 转换为块元素：display:block;
- 转换为行内元素：display:inline;
- 转换为行内块：display: inline-block;

### <span id="head34"> 单行文字垂直居中的代码</span>

**解决方案**:    

​		让文字的行高等于盒子的高度  就可以让文字在当前盒子内垂直居中

```
 div {
            width: 200px;
            height: 40px;
            background-color: pink;
            line-height: 40px;
        }
```

**简单理解**: 

​		行高的上空隙和下空隙把文字挤到中间了，

​		如果行高小于盒子高度,文字会偏上，

​		如果行高大于盒子高度,则文字偏下。

## <span id="head35"> 背景平铺</span>

```
            /* 1.背景图片不平铺 */
            background-repeat: no-repeat;
            /* 2.默认的情况下,背景图片是平铺的 */
            background-repeat: repeat;
            /* 3. 沿着x轴平铺 */
            background-repeat: repeat-x;
            /* 4. 沿着Y轴平铺 */
            background-repeat: repeat-y;
```

## <span id="head36"> 背景图片位置</span>

```

```

​		如果指定的两个值是精确单位和方位名词混合使用，则第一个值是 x 坐标，第二个值是 y 坐标

## <span id="head37"> 背景图片固定</span>

​	background-attachment:scroll背景图像是随对象内容滚动
​	background-attachment:fixed背员图像固定

## <span id="head38"> 背景样式合写</span>

```
    /* background-image: url(images/bg.jpg);
    background-repeat: no-repeat;
    background-position: center top; */
    /* 把背景图片固定住 */
    /* background-attachment: fixed;
    background-color: black; */
    background: black url(images/bg.jpg) no-repeat fixed center top;

```

## <span id="head39"> 背景色半透明</span>

```
            background: rgba(0, 0, 0, .3);
```

- 最后一个参数是 alpha 透明度，取值范围在 0~1之间
- 我们习惯把 0.3 的 0 省略掉，写为 background: rgba(0, 0, 0, .3);

# <span id="head40"> css三大特性</span>

## <span id="head41"> 层叠性</span>

​		相同选择器给设置相同的样式，此时一个样式就会覆盖（层叠）另一个冲突的样式。层叠性主要解决样式冲突的问题

​		层叠性原则:

- 样式冲突，遵循的原则是就近原则，哪个样式离结构近，就执行哪个样式
- 样式不冲突，不会层叠

```
       div {
           color: red;
           font-size: 12px;
       }
       div {         /* 被覆盖 */
           color: black;
       }
```



## <span id="head42"> 继承性</span>

​		CSS中的继承: 子标签会继承父标签的某些样式，如文本颜色和字号。恰当地使用继承可以简化代码，降低 CSS 样式的复杂性。

```
    <style>
        div {
            color: pink;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div><      !-- 继承 -->
        <p>龙生龙，凤生凤，老鼠生的孩子会打洞</p>
    </div>
</body>
```



子元素可以继承父元素的样式：

​	（text-，font-，line-这些元素开头的可以继承，以及color属性）



行高的继承性：

```css
 body {
   font:12px/1.5 Microsoft YaHei；
 }
```

- 行高可以跟单位也可以不跟单位
- 如果子元素没有设置行高，则会继承父元素的行高为 1.5
- 此时子元素的行高是：当前子元素的文字大小 * 1.5
- body 行高 1.5  这样写法最大的优势就是里面子元素可以根据自己文字大小自动调整行高

## <span id="head43"> 优先级</span>

当同一个元素指定多个选择器，就会有优先级的产生。

- 选择器相同，则执行层叠性
- 选择器不同，则根据选择器权重执行



选择器优先级计算表格：

![1571490129794.png](https://i.loli.net/2021/07/12/5dRxEVyBoh8NLrb.png)



```
  div {
            color: pink!important;
        }
```



优先级注意点:

1. 权重是有4组数字组成,但是不会有进位。

2. **行内>id选择器>类选择器>元素选择器,** 以此类推..

3. 等级判断从左向右，如果某一位数值相同，则判断下一位数值。

4. 可以简单记忆法:  通配符和继承权重为0, 标签选择器为1,类(伪类)选择器为 10, id选择器 100, 行内样式表为 1000, !important 无穷大.

5. 继承的权重是0， **如果该元素没有直接选中，不管父元素权重多高，子元素得到的权重都是 0。**

   ```
     /* 父亲的权重是 100  */
           #father {
               color: red!important;
           }
           /* p继承的权重为 0 */
           p {
               color: pink;
           }
       <div id="father">
           <p>你还是很好看</p>  ！！！粉色
       </div>
   ```

   ```
           .nav {
               color: red;
           }
           /* 继承的权重是0 */
           li {
               color: pink;
           }
       
       <ul class="nav">
           <li>人生四大悲</li>  !!! 粉色
   ```

   

权重叠加：如果是复合选择器，则会有权重叠加，需要计算权重。

```
      /* 复合选择器会有权重叠加的问题 */
       /* 权重虽然会叠加,但是永远不会有进位 */
       /* ul li 权重  0,0,0,1 + 0,0,0,1  =  0,0,0,2     2 */
        ul li {
            color: green;
        }
        /* li 的权重是 0,0,0,1    1 */
        li {
            color: red;
        }
        /* .nav li  权重    0,0,1,0  +  0,0,0,1  =  0,0,1,1    11 */          ！！！权重最高
        .nav li {
            color: pink;    
```



# <span id="head44"> 盒子模型</span>

## <span id="head45"> 网页布局的本质</span>

网页布局的核心本质： 就是利用 CSS 摆盒子。



网页布局过程：

1. 先准备好相关的网页元素，网页元素基本都是盒子 Box 。
2. 利用 CSS 设置好盒子样式，然后摆放到相应位置。
3. 往盒子里面装内容

## <span id="head46">盒子模型（Box Model）组成</span>

​		盒子模型：把 HTML 页面中的布局元素看作是一个矩形的盒子，也就是一个盛装内容的容器。

​		CSS 盒子模型本质上是一个盒子，封装周围的 HTML 元素，它包括：**边框**、**外边距**、**内边距**、和 **实际内容**

![1571492536942.png](https://i.loli.net/2021/07/12/zpE6AfXL9OqdlMB.png)

**表格的细线边框**

border-collapse 属性控制浏览器绘制表格边框的方式。它控制相邻单元格的边框。

```css
 border-collapse: collapse;  表示相邻边框合并在一起
```

**边框会影响盒子实际大小**

**边框会额外增加盒子的实际大小。**因此我们有两种方案解决：

- 测量盒子大小的时候,不量边框。
- 如果测量的时候包含了边框,则需要 width/height 减去边框宽度

## <span id="head47"> 内边距（padding）</span>



**内边距会影响盒子实际大小**

- 如果盒子**已经有了宽度和高度，此时再指定内边框，会撑大盒子。**
- 如何盒子本身没有指定width/height属性, 则此时padding不会撑开盒子大小。
- 保证盒子跟效果图大小保持一致，则让 width/height 减去多出来的内边距大小即可。

## <span id="head48"> 外边距（margin）</span>

**外边距典型应用**

外边距可以让块级盒子水平居中的两个条件：

- 盒子必须指定了宽度（width）。
- 盒子左右的外边距都设置为 auto 。

常见的写法，以下三种都可以：

```css
margin-left: auto;   margin-right: auto;
margin: auto;
margin: 0 auto;
```

注意：以上方法是让**块级元素水平居中**，行**内元素或者行内块元素水平居中给其父元素添加 text-align:center 即可。**

**外边距合并**

使用 margin 定义块元素的垂直外边距时，可能会出现外边距的合并。

主要有两种情况:

1、相邻块元素垂直外边距的合并

​		当上下相邻的两个块元素（兄弟关系）相遇时，如果上面的元素有下外边距 margin-bottom，下面的元素有上外边距 margin-top ，则他们之间的**垂直间距不是 margin-bottom 与 margin-top 之和。取两个值中的较大者**这种现象被称为相邻块元素垂直外边距的合并。

解决方案：
		**尽量只给一个盒子添加 margin 值。**

2、嵌套块元素垂直外边距的塌陷

​		对于两个嵌套关系（父子关系）的块元素，父元素有上外边距同时子元素也有上外边距，此时父元素会塌陷较大的外边距值。

![1571494373778.png](https://i.loli.net/2021/07/12/pixzYQ9ECf7BJIU.png)

解决方案：

- 可以为父元素定义上边框。
- 可以为父元素定义上内边距。
- 可以为父元素添加 overflow:hidden。

​		注意：行内元素为了照顾兼容性，尽量只设置左右内外边距，不要设置上下内外边距。但是转换为块级和行内块元素就可以了

# <span id="head49"> 其他样式	</span>

## <span id="head50"> 1、圆角边框</span>

```
width: 200px;
height: 200px;
/* 50% 就是宽度和高度的一半  等价于 100px */
border-radius: 50%;

width: 300px;
height: 100px;
/* 圆角矩形设置为高度的一半 */
border-radius: 50px;

/* border-radius: 10px 20px 30px 40px; */
border-top-left-radius: 20px;
```



- 参数值可以为数值或百分比的形式
- 如果是正方形，想要设置为一个圆，把数值修改为高度或者宽度的一半即可，或者直接写为 50%
- 该属性是一个简写属性，可以跟四个值，分别代表左上角、右上角、右下角、左下角
- 分开写：border-top-left-radius、border-top-right-radius、border-bottom-right-radius 和border-bottom-left-radius

## <span id="head51"> 2、盒子阴影</span>



```css
 /* 原先盒子没有影子,当我们鼠标经过盒子就添加阴影效果 */
box-shadow: 10px 10px 10px -4px rgba(0, 0, 0, .3);

box-shadow: h-shadow v-shadow blur spread color inset; 
```

![1571541874805.png](https://i.loli.net/2021/07/13/8iOLaDFPtj4S1zh.png)

## <span id="head52"> 3、文字阴影</span>

在 CSS3 中，我们可以使用 text-shadow 属性将阴影应用于文本。
语法：

```css
text-shadow: h-shadow v-shadow blur color;
text-shadow: 5px 5px 6px rgba(0, 0, 0, .3);

```



# <span id="head53"> 浮动</span>

## <span id="head54"> 1、传统网页布局的三种方式</span>

​	CSS 提供了三种传统布局方式(简单说,就是盒子如何进行排列顺序)：

- 普通流（标准流）
- 浮动
- 定位

注意：实际开发中，一个页面基本都包含了这三种布局方式（后面移动端学习新的布局方式） 。

## <span id="head55"> 2、标准流（普通流/文档流）</span>

所谓的标准流:  就是标签按照规定好默认方式排列

1. 块级元素会独占一行，从上向下顺序排列。常用元素：div、hr、p、h1~h6、ul、ol、dl、form、table
2. 行内元素会按照顺序，从左到右顺序排列，碰到父元素边缘则自动换行。常用元素：span、a、i、em 等 

以上都是标准流布局，我们前面学习的就是标准流，标准流是最基本的布局方式。

## <span id="head56"> 3、为什么需要浮动？</span>

​		总结： 有很多的布局效果，标准流没有办法完成，此时就可以利用浮动完成布局。 因为浮动可以改变元素标签默认的排列方式.

​		**浮动最典型的应用：可以让多个块级元素一行内排列显示。**

​		网页布局第一准则：**多个块级元素纵向排列找标准流，多个块级元素横向排列找浮动**。

## <span id="head57"> 4、什么是浮动？</span>

​		float 属性用于创建浮动框，将其移动到一边，直到左边缘或右边缘触及包含块或另一个浮动框的边缘。

语法：

```css
float: none/left/right(默认元素不浮动，元素向左浮动，元素向右浮动)
```

## <span id="head58"> 5、浮动特性</span>

加了浮动之后的元素,会具有很多特性,需要我们掌握的.

1、浮动元素会**脱离标准流**(脱标：**浮动的盒子不再保留原先的位置**)

![1571544653264.png](https://i.loli.net/2021/07/13/hMRV7lLImiDaZW4.png)

2、浮动的元素会**一行内显示并且元素顶部对齐**

​		浮动的元素是互相贴靠在一起的（不会有缝隙），如果父级宽度装不下这些浮动的盒子，多出的盒子会另起一行对齐。

3、浮动的元素会**具有行内块元素的特性**

​		如果行内元素有了浮动，则不需要转換块行内块元素就可以直接给高度和宽度

​	如果块级盒子没有设置宽度，默认宽度和父级一样宽，但是添加浮动后，它的大小根据内容来決定
​	浮动的盒子中间是没有缝隙的，是紧挨着一起的

## <span id="head59"> 6、浮动元素经常和标准流父级搭配使用</span>

为了约束浮动元素位置, 我们网页布局一般采取的策略是:

​		先用标准流父元素排列上下位置, 之后内部子元素采取浮动排列左右位置. 

1、浮动和标准流的父盒子搭配。

先用标准流的父元素排列上下位置, 之后内部子元素采取浮动排列左右位置

2、一个元素浮动了，理论上其余的兄弟元素也要浮动。

一个盒子里面有多个子盒子，如果其中一个盒子浮动了，其他兄弟也应该浮动，以防止引起问题。

**浮动的盒子只会影响浮动盒子后面的标准流**,不会影响前面的标准流.

# <span id="head60"> 清除浮动</span>

## <span id="head61"> 1、为什么需要清除浮动？</span>

​		由于父级盒子很多情况下，不方便给高度，但是子盒子浮动又不占有位置，最后父级盒子高度为 0 时，就会影响下面的标准流盒子。

![1571555883628.png](https://i.loli.net/2021/07/18/S3qrI2TE9oLtZJu.png)

## <span id="head62"> 2、清除浮动本质</span>

清除浮动的本质是清除浮动元素造成的影响：浮动的子标签无法撑开父盒子的高度

注意：

- 如果父盒子本身有高度，则不需要清除浮动
- 清除浮动之后，父级就会根据浮动的子盒子自动检测高度。
- 父级有了高度，就不会影响下面的标准流了

## <span id="head63"> 3、清除浮动样式</span>

```css
clear:属性值;

left不允许左侧有浮动元素（清除左侧浮动的影响）
rigth不允许右侧有浮动元素（清除右侧浮动的影响）
both同时清除左右两侧浮动的影响
```

我们实际工作中， 几乎只用 clear: both; **清除浮动的策略是:  闭合浮动.** 			闭合浮动.  只让浮动在父盒子内部影响,不影响父盒子外面的其他盒子.



## <span id="head64"> 4、清除浮动的多种方式</span>

### <span id="head65"> 4.1、额外标签法</span>

额外标签法也称为隔墙法，是 W3C 推荐的做法。

使用方式：

​		额外标签法会**在浮动元素末尾添加一个空的标签。**

```html
例如 <div style="clear:both"></div>，或者其他标签（如<br />等）。
```

​		优点： 通俗易懂，书写方便

​		缺点： 添加许多无意义的标签，结构化较差

​		注意： 要求**这个新的空标签必须是块级元素。**

### <span id="head66">4.2、父级添加 overflow 属性</span>

可以给父级添加 overflow 属性，将其属性值设置为 hidden、 auto 或 scroll 。

```css
overflow:hidden | auto | scroll;
```

优点：代码简洁

缺点：无法显示溢出的部分

注意：是给父元素添加代码

### <span id="head67"> 4.3、父级添加after伪元素</span>

:after 方式是额外标签法的升级版。给父元素添加：

```css
 .clearfix:after {  
   content: ""; 
   display: block; 
   height: 0; 
   clear: both; 
   visibility: hidden;  
 } 
 .clearfix {  /* IE6、7 专有 */ 
   *zoom: 1;
 }   
```

优点：没有增加标签，结构更简单

缺点：照顾低版本浏览器

代表网站： 百度、淘宝网、网易等

### <span id="head68"> 4.4、父级添加双伪元素</span>

给父元素添加

```css
 .clearfix:before,.clearfix:after {
   content:"";
   display:table; 
 }
 .clearfix:after {
   clear:both;
 }
 .clearfix {
    *zoom:1;
 }   
```

优点：代码更简洁

缺点：照顾低版本浏览器

代表网站：小米、腾讯等

# <span id="head69"> flex</span>





# <span id="head70"> position</span>

1.     Absolute：绝对定位，是相对于最近的且不是static定位的父元素来定位

2. Fixed：绝对定位，是相对于浏览器窗口来定位的，是固定的，不会跟屏幕一起滚动。

3. Relative：相对定位，是相对于其原本的位置来定位的。

4. Static：默认值，没有定位。

5. Inherit：继承父元素的position值。

   

首先设置4个div：

<img src="https://pic.rmb.bdstatic.com/bjh/1fbe092fc8e8033c3dba4057253c522a.jpeg" alt="image.png" title="image.png" />

给第二个div设置absolute:

```
height:100px;
background-color: blueviolet;         
position:relative;
left:50px;
top:50px;
```


<img src="https://pic.rmb.bdstatic.com/bjh/5ecc8eb00774af44c4e43158214aa862.jpeg" alt="image.png" title="image.png" />

第二个div设置了absolute,则该div的宽度就由文本决定，且**下面的div会上移占据之前第二个div的位置**，top和left是相对于**离它最近且不是static定位的父元素**来定位的，在此div2因为没有父元素，所以第二个div相对于根元素即html元素来定位。

将第二个div设置为relative：

```
 height:100px;
 background-color: blueviolet;         
 position:relative;
 left:50px;
 top:50px;
```

![image.png](https://pic.rmb.bdstatic.com/bjh/9e39778f1b401f4684afb445c923f527.jpeg)

设置**relative的div不会影响其他div的位置，且top和left是相对于它原本自身的位置来定位。**

给第二个div添加一个父div：

```
.container1{
position:absolute;
height:200px;
background-color: greenyellow;          

.div2{
height:100px;
background-color: blueviolet;
position:absolute;
top:50px;
left:50px;
```

![image.png](https://pic.rmb.bdstatic.com/bjh/0479a64bb5f87c52a149deb43ea3233a.jpeg)

div2的父div设置为absolute，下面的div3,div4会上移，div2也设置为absolute，div2就会相对于父div来定位。



若将div2即第二个div的absolute改为relative：

![image.png](https://pic.rmb.bdstatic.com/bjh/370ff1f35e2fe76925133e02b56d0234.jpeg)

注意，上面两个图的第二个div与父div的上边距是不同的，第一个是**absolute相对父div来定位**，第二个是**relative相对原来本身的位置来定位**。可能此时你会注意到两个图的第二个div的宽度不同，在没有给div设置宽度的情况下，第一个是设为**absolute，所以宽度为文本宽度**，第二个是**relative，所以宽度与父元素宽度相同**。

若保持上面的两种情况，都将第二个div的宽度设为500px，得到效果如下

![image.png](https://pic.rmb.bdstatic.com/bjh/ee948091eeecc2477209511c6f3e560a.jpeg)

由上图可以知道，**absolute定位的子元素宽度不会影响父元素的宽，而relative定位的子元素会撑大父元素**。

Absolution：元素会脱离文档流，定位是相对于离它最近的且不是static定位的父元素而言，若该元素没有设置宽度，则宽度由元素里面的内容决定，且宽度不会影响父元素，定位为absolution后，原来的位置相当于是空的，下面的的元素会来占据。

Relative：元素仍处于文档流中

，定位是相对于原本自身的位置，若没有设置宽度，则宽度为父元素的宽度，该元素的大小会影响父元素的大小。

# <span id="head71"> 居中</span>

文字居中

```
display: flex;
align-items: center;
text-align: center;
vertical-align: middle;这个适用于行内元素的垂直居中，块元素不可以。不起作用设置‘行高’
-----------
hieght: 100px;  //子元素为单行文本
lin-height: 100px
```

块居中

```
display: flex;
align-items: center;/*垂直居中*/
/*justify-content: center;*//*水平居中*/


```



# <span id="head72"> 底部固定</span>

```html
<template>
  <div class="content">
    <div class="main"></div>
    <div class="footer"></div>
  </div>
</template>

<style scoped>
  .content {
    /*相对定位*/
    position: relative;
    width: 100%;
    height: 100%;
    background-color: #F4F5F6;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .main {
    width: 100%;
    height: calc(100vh - 42px);
  }

  .footer {
    /*绝对定位*/
    position: absolute;
    width: 100%;
    height: 42px;
    bottom: 0;
    background-color: cornflowerblue;
  }
</style>
```











# <span id="head73">Media Query</span>

使用 @media 查询，你可以针对不同的媒体类型定义不同的样式。

```
/* 当浏览器的可视区域小于980px */
@media screen and ( max-width: 980px 
```



# <span id="head74">content </span>

```html
// 每个链接后的括号内加上网址（href 属性）：
a:after {
  content: " (" attr(href) ")";
}
<p><a href="https://www.runoob.com">菜鸟教程</a> - 免费的编程学习网站。</p>

// 加上字符
content; '\e63a'
font-family: "iconfont"!important
```

# <span id="head75"> box-sizing</span>

 元素的总高度和宽度包含内边距和边框(padding 与 border) :

```html
box-sizing: content-box (默认)高度和宽度只应用于元素的内容
box-sizing: border-box:高度和宽度应用于元素的所有部分: 内容、内边距和边框

```

# <span id="head76">opacity </span>

设置一个div元素的透明度级别




# <span id="head77"> backface-visibility:hidden</span>

隐藏旋转 div 元素的背面

# <span id="head78">transform </span>

旋转 div 元素

```html
matrix(n,n,n,n,n,n)	定义 2D 转换，使用六个值的矩阵。
```

# <span id="head79"> word-wrap</span>

允许长的内容可以自动换行

| normal     | 只在允许的断字点换行（浏览器保持默认处理）。 |
| ---------- | -------------------------------------------- |
| break-word | 在长单词或 URL 地址内部进行换行。            |

# <span id="head80"> word-break</span>

断行规则。

| normal    | 使用浏览器默认的换行规则。     |
| --------- | ------------------------------ |
| break-all | 允许在单词内换行。             |
| keep-all  | 只能在半角空格或连字符处换行。 |

# <span id="head81"> white-space</span>

元素内的空白怎样处理。

| normal   | 默认。空白会被浏览器忽略。                                   |
| -------- | ------------------------------------------------------------ |
| pre      | 空白会被浏览器保留。其行为方式类似 HTML 中的 <pre> 标签。    |
| nowrap   | 文本不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 |
| pre-wrap | 保留空白符序列，但是正常地进行换行。                         |
| pre-line | 合并空白符序列，但是保留换行符。                             |
| inherit  | 规定应该从父元素继承 white-space 属性的值。                  |





```caa
文字太多时省略,,,
word-break: keepp-all;
white-spave: nowrap;
overflow: hidden;
text-overflow: ellipse;


图片悬浮放大
img {
	transition: all 0.5s;
}
img:hover {
	transform: scale(1.50);
}

图片设置为正方形
img {
width: 10ps;
height: 10px;
object-fit: cover;
}
```

