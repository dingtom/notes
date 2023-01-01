---
        # 文章标题
        title: "code-正则表达式"
        # 分类
        categories: 
            - code
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

**()内的内容表示的是一个子表达式**，()本身不匹配任何东西，也不限制匹配任何东西，只是把括号内的内容作为同一个表达式来处理，例如(ab){1,3}，就表示ab一起连续出现最少1次，最多3次。如果没有括号的话，ab{1,3},就表示a，后面紧跟的b出现最少1次，最多3次。



**[]表示匹配的字符在[]中，**

**并且只能出现一次，并特殊字符写在[]会被当成普通字符来匹配。**

例如[(a)]，会匹配(、a、)、这三个字符。



###### 字符串前加 u



字符串以 Unicode 格式 进行编码，一般用在中文字符串前面
b：后面字符串是bytes 类型。
r: 去掉反斜杠的转义机制。

# 例子
```
0.中文： \u4e00-\u9fa5 
1.Email地址： ^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$ 
2. 域名： [a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.? 
3. InternetURL： [a-zA-z]+://[^\s]*  或  ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$ 
4. 手机号码： ^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$ 
5. 身份证号(15位、18位数字)： ^\d{15}|\d{18}$ 
6. 短身份证号码(数字、字母x结尾)： ^([0-9]){7,18}(x|X)?$  或  ^\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$ 
7. 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)： ^[a-zA-Z][a-zA-Z0-9_]{4,15}$ 
8. 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)： ^[a-zA-Z]\w{5,17}$ 
```

---

```
. 匹配除换行符以外的任意字符 
\d 匹配任意数字，等价于 [0-9]
\D 匹配任意非数字
\w 匹配字母，数字或下划线字符，等价于[A-Za-z0-9_]
\W 匹配所有与\w不匹配的字符； 
\n, \t, 等.	匹配一个换行符。匹配一个制表符。等

\1…\9	匹配第n个分组的内容。

\10	匹配第n个分组的内容，如果它经匹配。否则指的是八进制字符码的表达式。
```
# 非打印字符
```
\cx	匹配由x指明的控制字符。例如， \cM 匹配一个 Control-M 或回车符。x 的值必须为 A-Z 或 a-z 之一。否则，将 c 视为一个原义的 'c' 字符。
\f	匹配一个换页符。等价于 \x0c 和 \cL。
\n	匹配一个换行符。等价于 \x0a 和 \cJ。
\r	匹配一个回车符。等价于 \x0d 和 \cM。
\s	匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。注意 Unicode 正则表达式会匹配全角空格符。
\S	匹配任何非空白字符。等价于 [^ \f\n\r\t\v]。
\t	匹配一个制表符。等价于 \x09 和 \cI。
\v	匹配一个垂直制表符。等价于 \x0b 和 \cK。
```
# 定位符
```
\b 匹配模式必须出现在目标字符串的开头或结尾的两个边界之一 
\B 匹配对象必须位于目标字符串的开头和结尾两个边界之内，
\A	匹配字符串开始
\Z	匹配字符串结束，如果是存在换行，只匹配到换行前的结束字符串。
\z	匹配字符串结束
\G	匹配最后匹配完成的位置。
\b	匹配一个单词边界，也就是指单词和空格间的位置。例如， 'er\b' 可以匹配"never" 中的 'er'，但不能匹配 "verb" 中的 'er'。
\B	匹配非单词边界。'er\B' 能匹配 "verb" 中的 'er'，但不能匹配 "never" 中的 'er'。
^ 匹配字符串的开始 
[^ 非！！！！！！！！！！！！！！
$ 匹配字符串的结束 
```

# 限定符
```
+ 表示重复一次或者多次 
* 表示重复零次或者多次 
? 出现零次或一次。
{n,}	n 是一个非负整数。至少匹配n 次
{n}	n 是一个非负整数。匹配确定的 n 次
```
# 选择
用圆括号 () 将所有选择项括起来，相邻的选择项之间用 | 分隔。
```
()表示捕获分组，()会把每个分组里的匹配的值保存起来。（**使相关的匹配会被缓存**）
(?:)表示非捕获分组，和捕获分组唯一的区别在于，非捕获分组匹配的值不会保存起来
(?=) 正向预查，在任何开始匹配圆括号内的正则表达式模式的位置来匹配搜索字符串
(?!) 负向预查，在任何开始不匹配该正则表达式模式的位置来匹配搜索字符串

```
>![](https://upload-images.jianshu.io/upload_images/18339009-6da716c8218cd5d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
import re
a="123abc456ww"
pattern="([0-9]*)([a-z]*)([0-9]*)"
print(re.search(pattern, a).group(0, 1, 2, 3))
# ('123abc456', '123', 'abc', '456')   
# ！！！！！！！！！！！！！使相关的匹配会被缓存
pattern="(?:[0-9]*)([a-z]*)([0-9]*)"
print(re.search(pattern, a).group(0, 1, 2))
# ('123abc456', 'abc', '456')
```
>exp1(?=exp2)：查找 exp2 前面的 exp1。
![](https://upload-images.jianshu.io/upload_images/18339009-ba6b8a1cf3d82f02.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
(?<=exp2)exp1：查找 exp2 后面的 exp1。
![](https://upload-images.jianshu.io/upload_images/18339009-eb79bd31dd29146b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
exp1(?!exp2)：查找后面不是 exp2 的 exp1。
![](https://upload-images.jianshu.io/upload_images/18339009-0f9f077d1be0b902.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
(?<!exp2)exp1：查找前面不是 exp2 的 exp1。
![](https://upload-images.jianshu.io/upload_images/18339009-2390821f0939ccbc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)











# 内联匹配模式
通常用内联匹配模式代替使用枚举值RegexOptions指定的全局匹配模式，写起来更简洁。
```
  (?i) 表示所在位置右侧的表达式开启忽略大小写模式
  (?s) 表示所在位置右侧的表达式开启单行模式。
  更改句点字符 (.) 的含义，以使它与每个字符（而不是除 \n 之外的所有字符）匹配。
  注意：(?s)通常在匹配有换行的文本时使用
  (?m) 表示所在位置右侧的表示式开启指定多行模式。
  更改 ^ 和 $ 的含义，以使它们分别与任何行的开头和结尾匹配，
  而不只是与整个字符串的开头和结尾匹配。
  注意：(?m)只有在正则表达式中涉及到多行的“^”和“$”的匹配时，才使用Multiline模式。
  上面的匹配模式可以组合使用，比如(?is),(?im)。
  另外，还可以用(?i:exp)或者(?i)exp(?-i)来指定匹配的有效范围。
```








# python
```js
re.(function)(pattern[, flags])
pattern : 一个字符串形式的正则表达式
flags : 可选，表示匹配模式，比如忽略大小写，多行模式等，具体参数为：
re.I 忽略大小写
re.L 表示特殊字符集 \w, \W, \b, \B, \s, \S 依赖于当前环境
re.M 多行模式
re.S 即为 . 并且包括换行符在内的任意字符（. 不包括换行符）
re.U 表示特殊字符集 \w, \W, \b, \B, \d, \D, \s, \S 依赖于 Unicode 字符属性数据库
re.X 为了增加可读性，忽略空格和 # 后面的注释
```

## findall()

返回string中所有与pattern相匹配的全部字串，返回形式为数组。



参数：
string : 待匹配的字符串。
pos : 可选参数，指定字符串的起始位置，默认为 0。
endpos : 可选参数，指定字符串的结束位置，默认为字符串的长度。

```shown_offset = re.findall(re.compile(r'<ul.*?id(.*?)">', re.S), response.text)[0]```


## finditer()
和 findall 类似，在字符串中找到正则表达式所匹配的所有子串，并把它们作为一个迭代器返回。
```js
re.finditer(pattern, string, flags=0)
参数：
pattern	匹配的正则表达式
string	要匹配的字符串。
flags	标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等
```
## search() 与 match()
- search()会扫描整个string查找匹配，只返回第一个
- match（）从首字母开始开始匹配，string如果包含pattern子串，则匹配成功（**可能有多个匹配**），返回Match对象，失败则返回None，若要完全匹配，pattern要以$结尾。
- match()/search()返回的是Match对象，finditer()返回的也是Match对象的迭代器，获取匹配结果需要调用Match对象的group()、groups或group(index)方法。

```js
语法：re.search/match(pattern, string, flags=0)
pattern	匹配的正则表达式
string	要匹配的字符串。
flags	标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等

匹配方法	描述
group(num=0)	母串中与模式pattern匹配的子串
groups()	所有group组成的一个元组
start([group])	方法用于获取分组匹配的子串在整个字符串中的起始位置（子串第一个字符的索引），参数默认值为 0
end([group])	方法用于获取分组匹配的子串在整个字符串中的结束位置（子串最后一个字符的索引+1），参数默认值为 0
span([group])	方法返回 (start(group), end(group)
                      
                      
                      import re
 
#             1.匹配结果
res = re.search("python","Life is short. I learn python!")
print(res)
#输出结果:<_sre.SRE_Match object; span=(23, 29), match='python'>
print(res.group())  #获取匹配数据
#输出结果:python
#             2.未匹配到结果
res = re.search("java","Life is short. I learn python!")
print(res)
#输出结果:None
#             3.是否区分大小写匹配对比
res = re.search("Python","Life is short. I learn python!")
print(res)
#输出结果:None
res = res = re.search("Python","Life is short. I learn python!",re.I)
print(res)
#输出结果:<_sre.SRE_Match object; span=(23, 29), match='python'>
print(res.group())
#输出结果:python
#             4.输出匹配位置和原配字符串
res = re.search("python","Life is short. I learn python!")
print(res.start())  #匹配字符串的开始位置
#输出结果:23
print(res.end())    #匹配字符串的结束位置
#输出结果:29
print(res.span())   #匹配字符串的元组(开始位置+结束位置)
#输出结果:(23, 29)
print(res.string)   #匹配字符串
#输出结果:Life is short. I learn python!
 
#定义一个正则对象调用
pat = re.compile("[0-9]") #匹配单个数字
res = pat.search("abcd23ef4gh!")
print(res)
#输出结果:<_sre.SRE_Match object; span=(4, 5), match='2'>
```
```js
import re
# re.search
ret = re.search('h..','hello hello world')
print(ret) # 输出结果：<_sre.SRE_Match object; span=(0, 3), match='hel'>
ret1 = re.search('h..','hello hello world').group()
print(ret1) # 输出结果：hel 只输出第一个符合条件的结果
# re.match
ret = re.match('asd','asdhskdjfksji')
print(ret) # 输出结果：<_sre.SRE_Match object; span=(0, 3), match='asd'>返回的是一个对象。
ret1 = re.match('asd','asdhskdjfasdksjiasd').group()
print(ret1) # 输出结果：asd 调用.group()方法，只返回匹配的第一个结果。
```
## split() 

分隔符 对比字符串里边的split方法。

split 方法按照能够匹配的子串将字符串分割后返回列表，它的使用形式如下：
```python
re.split(pattern, string[, maxsplit=0, flags=0])
参数	描述
pattern	匹配的正则表达式
string	要匹配的字符串。
maxsplit	分隔次数，maxsplit=1 分隔一次，默认为 0，不限制次数。
flags	标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等。
```
```python
import re
s = 'helloworld hellobeijing'
ret = re.split('hello',s)
print(ret)
['', 'world ', 'beijing']
```
如果使用带括号的正则表达式则可以将正则表达式匹配的内容也添加到列表内，例如
```python
>>>re.split(r'(\W+)','hello, world')
>>>['hello',', ','world']
```
## sub() 替换

类似字符串中的replace()方法。

re.sub用于替换字符串中的匹配项。
```python
re.sub(pattern, repl, string, count=0, flags=0)
参数：
pattern : 正则中的模式字符串。
repl : 替换的字符串，也可为一个函数。
string : 要被查找替换的原始字符串。
count : 模式匹配后替换的最大次数，默认 0 表示替换所有的匹配。
```
```python
import re
s = 'helloworld hellobeijing'
ret = re.sub('hello','goodbye',s)
print(ret)
goodbyeworld goodbyebeijing
```
## compile(strPattern[,flag]):

这个方法是Pattern类的工厂方法，用于将字符串形式的正则表达式编译为Pattern对象

生成一个正则表达式（ Pattern ）对象**可重用**
```python
语法格式为：
re.compile(pattern[, flags])
参数：
pattern : 一个字符串形式的正则表达式
flags : 可选，表示匹配模式，比如忽略大小写，多行模式等，具体参数为：
re.I 忽略大小写
re.L 表示特殊字符集 \w, \W, \b, \B, \s, \S 依赖于当前环境
re.M 多行模式
re.S 即为 . 并且包括换行符在内的任意字符（. 不包括换行符）
re.U 表示特殊字符集 \w, \W, \b, \B, \d, \D, \s, \S 依赖于 Unicode 字符属性数据库
re.X 为了增加可读性，忽略空格和 # 后面的注释
```
```python
import re
pattern = re.compile('he.{3}')
pattern.match(s)
<re.Match object; span=(0, 5), match='hello'>
```
