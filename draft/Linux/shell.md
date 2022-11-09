`#!/bin/bash` 
! 是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行，即使用哪一种 Shell。
 `chmod +x ./test.sh` 使脚本具有执行权限 
`./test.sh` 执行脚本 注意，一定要写成` ./test.sh` ，而不是`  test.sh` ，运行其它二进制的程序也一样，直接写 ` test.sh` ，linux 系统会去 PATH 里寻找有没有叫 test.sh的，而只有 /bin, /sbin, /usr/bin，/usr/sbin 等在 PATH 里，你的当前目录通常不在 PATH 里，所以写成 ` test.sh` 是会找不到命令的，要用 ` ./test.sh ` 告诉系统说，就在当前目录找。

`/bin/sh test.sh` 这种方式运行的脚本，**不需要在第一行指定解释器信息，写了也没用。**

######变量名和等号之间不能有空格，这可能和你熟悉的所有编程语言都不一样。同时，变量名的命名须遵循如下规则： 命名只能使用英文字母，数字和下划线，首个字符不能以数字开头。 中间不能有空格，可以使用下划线（_）。 不能使用标点符号。 不能使用bash里的关键字（可用help命令查看保留关键字）。

#####除了显式地直接赋值，还可以用语句给变量赋值，如： `for file in `ls /etc`` 或 `for file in $(ls /etc)` 以上语句将 /etc 下目录的文件名循环出来。

#####使用一个定义过的变量，只要在变量名前面加美元符号即可，如：

```
your_name="qinjx"
echo $your_name
echo ${your_name}

```

变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界，比如下面这种情况：

```
for skill in Ada Coffe Action Java; do  echo "I am good at ${skill}Script" ;done

```

如果不给skill变量加花括号，写成`echo "I am good at $skillScript"`，解释器就会把$skillScript当成一个变量（其值为空），代码执行结果就不是我们期望的样子了。

#####使用 readonly 命令可以将变量定义为只读变量，只读变量的值不能被改变。 readonly myUrl #修改的话执行时会报错

#####使用 unset 命令可以删除变量。unset 命令不能删除只读变量。 unset variable_name 变量被删除后不能再次使用。

#####单引号字符串的限制：单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的； 单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。

#####双引号的优点： 双引号里可以有变量 双引号里可以出现转义字符

```
your_name='runoob'
str="Hello, I know you are \"$your_name\"! \n"
echo -e $str

```

#####拼接字符串

```
your_name="runoob"
# 使用双引号拼接
greeting="hello, "$your_name" !"
greeting_1="hello, ${your_name} !"
echo $greeting  $greeting_1
# 使用单引号拼接
greeting_2='hello, '$your_name' !'
greeting_3='hello, ${your_name} !'
echo $greeting_2  $greeting_3
输出结果为：

hello, runoob ! hello, runoob !
hello, runoob ! hello, ${your_name} !

```

#获取字符串长度 string="abcd" echo ${#string} #输出 4

#提取子字符串 以下实例从字符串第 2 个字符开始截取 4 个字符： string="runoob is a great site" echo ${string:1:4} # 输出 unoo

#查找子字符串 查找字符 i 或 o 的位置(哪个字母先出现就计算哪个)： string="runoob is a great site" `echo `expr index "$string" io`` # 输出 4

#####定义数组 在 Shell 中，用括号来表示数组，数组元素用"空格"符号分割开。 例如： array_name=(value0 value1 value2 value3) 还可以单独定义数组的各个分量： array_name[0]=value0 array_name[n]=valuen 可以不使用连续的下标，而且下标的范围没有限制。

#####读取数组元素值的一般格式是：

```
valuen=${array_name[n]}
使用 @ 符号可以获取数组中的所有元素，例如：
echo ${array_name[@]}

```

#####获取数组的长度 获取数组长度的方法与获取字符串长度的方法相同，例如：

```
length=${#array_name[@]}
或者
length=${#array_name[*]}

```

#####遇到大段的代码需要临时注释起来，过一会儿又取消注释，怎么办呢？ 每一行加个#符号太费力了，可以把这一段要注释的代码用一对花括号括起来，定义成一个函数，没有地方调用这个函数，这块代码就不会执行，达到了和注释一样的效果。

#####多行注释还可以使用以下格式：

```
:<<EOF
注释内容...
注释内容...
注释内容...
EOF

```

#####Shell 传递参数

```
echo "Shell 传递参数实例！"
echo "执行的文件名：$0";！！！！！！！！！！！！！！！！！！！！！！
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";
为脚本设置可执行权限，并执行脚本，输出结果如下所示：
chmod +x test.sh 
./test.sh 1 2 3
Shell 传递参数实例！
执行的文件名：./test.sh
第一个参数为：1
第二个参数为：2
第三个参数为：3

```

```
$10 不能获取第十个参数，获取第十个参数需要${10}。当n>=10时，需要使用${n}来获取参数。

```

#####另外，还有几个特殊字符用来处理

```
$# 传递到脚本的参数个数 

$*  以一个单字符串显示所有向脚本传递的参数。如"$*"用「"」
括起来的情况、以"$1 $2 … $n"的形式输出所有参数。

$$  脚本运行的当前进程ID号 

$!  后台运行的最后一个进程的ID号 

$@  与$*相同，但是使用时加引号，并在引号中返回每个参数。如"$@"用「"」括起来的情况
以"$1" "$2" … "$n" 的形式输出所有参数
 $* 与 $@ 区别：
相同点：都是引用所有参数。
不同点：只有在双引号中体现出来。假设在脚本运行时写了三个参数 1、2、3，，则 " * " 等
价于 "1 2 3"（传递了一个参数），而 "@" 等价于 "1" "2" "3"（传递了三个参数）。

$- 显示Shell使用的当前选项，与[set命令]
(https://www.runoob.com/linux/linux-comm-set.html)功能相同。 

$?  显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。 

```

#Shell 基本运算符 #####算术运算符

```
条件表达式要放在方括号之间，并且要有空格，例如: [$a==$b] 是错误的，必须写成
 [ $a == $b ]。表达式和运算符之间要有空格，例如 2+2 是不对的，必须写成 2 + 2，
这与我们熟悉的大多数编程语言不一样。完整的表达式要被 ` ` 包含，注意这个字符不是常用
的单引号，在 Esc 键下边。

```

```
加法	`expr $a + $b` 结果为 30。
减法	`expr $a - $b` 结果为 -10。
乘法	`expr $a \* $b` 结果为  200。!   !  !   !   !
除法	`expr $b / $a` 结果为 2。
取余	`expr $b % $a` 结果为 0。
赋值	a=$b 将把变量 b 的值赋给 a。
相等。用于比较两个数字，相同则返回 true。	[ $a == $b ] 返回 false。
不相等。用于比较两个数字，不相同则返回 true。	[ $a != $b ] 返回 true。

```

#####关系运算符,关系运算符只支持数字，不支持字符串，除非字符串的值是数字。

```
-eq   检测两个数是否相等，相等返回 true。                 [ $a -eq $b ] 

-ne	检测两个数是否不相等，不相等返回 true。	            [ $a -ne $b ] 

-gt	检测左边的数是否大于右边的，如果是，则返回 true。   	[ $a -gt $b ] 

-lt	检测左边的数是否小于右边的，如果是，则返回 true。   	[ $a -lt $b ] 

-ge	检测左边的数是否大于等于右边的，如果是，则返回 true。	[ $a -ge $b ] 

-le	检测左边的数是否小于等于右边的，如果是，则返回 true。	[ $a -le $b ] 

```

#####布尔运算符

```
!	非运算，表达式为 true 则返回 false，否则返回 true。   	[ ! false ] 
-o	或运算，有一个表达式为 true 则返回 true。	                        [ $a -lt 20 -o $b -gt 100 ] 
-a	与运算，两个表达式都为 true 才返回 true。	                        [ $a -lt 20 -a $b -gt 100 ] 

```

#####逻辑运算符，表达式外有两个大括号! ! ! ! ! ! ! ! ! ! ! !

```
&&	逻辑的 AND	[[ $a -lt 100 && $b -gt 100 ]]     !　！　！！　！！
||	逻辑的 OR	[[ $a -lt 100 || $b -gt 100 ]]   

```

##字符串运算符

```
=	检测两个字符串是否相等，相等返回 true。	        [ $a = $b ] 
!=	检测两个字符串是否相等，不相等返回 true。	     [ $a != $b ] 
-z	检测字符串长度是否为0，为0返回 true。	        [ -z $a ] 
-n	检测字符串长度是否为0，不为0返回 true。	         [ -n "$a" ] 
$	检测字符串是否为空，不为空返回 true。	         [ $a ]   !!!!!!!!!!!

```

##文件测试运算符

```
-b file	检测文件是否是块设备文件，如果是，则返回 true。	[ -b $file ] 
-c file	检测文件是否是字符设备文件，如果是，则返回 true。	[ -c $file ] 
-d file	检测文件是否是目录，如果是，则返回 true。	[ -d $file ] 
-f file	检测文件是否是普通文件（既不是目录，也不是设备文件），如果是，则返回 true。	[ -f $file ] 
-g file	检测文件是否设置了 SGID 位，如果是，则返回 true。	[ -g $file ]
-k file	检测文件是否设置了粘着位(Sticky Bit)，如果是，则返回 true。	[ -k $file ] 
-p file	检测文件是否是有名管道，如果是，则返回 true。	[ -p $file ] 
-u file	检测文件是否设置了 SUID 位，如果是，则返回 true。	[ -u $file ] 
-r file	检测文件是否可读，如果是，则返回 true。	[ -r $file ] 
-w file	检测文件是否可写，如果是，则返回 true。	[ -w $file ] 
-x file	检测文件是否可执行，如果是，则返回 true。	[ -x $file ] 
-s file	检测文件是否为空（文件大小是否大于0），不为空返回 true。	[ -s $file ] 
-e file	检测文件（包括目录）是否存在，如果是，则返回 true。	[ -e $file ] 
-S: 判断某文件是否 socket。
-L: 检测文件是否存在并且是一个符号链接。

```

#echo命令 #####显示变量 read 命令从标准输入中读取一行,并把输入行的每个字段的值指定给 shell 变量

```
read name 
echo "$name It is a test"
[root@www ~]# sh test.sh
OK                     #标准输入
OK It is a test        #输出

```

#####显示换行 echo -e "OK! \n" # -e 开启转义 echo -e "OK! \c" # -e 开启转义 \c 不换行 echo -n "haod" #输出后不换行 #####显示结果定向至文件 echo "It is a test" > myfile

#####原样输出字符串，不进行转义或取变量(用单引号) `echo '$name\"'`

##显示命令执行结果 echo `date` #显示时间

#printf 命令 接下来,我来用一个脚本来体现printf的强大功能：

```
#!/bin/bash
printf "%-10s %-8s %-4s\n" 姓名 性别 体重kg  
printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234 
printf "%-10s %-8s %-4.2f\n" 杨过 男 48.6543 
printf "%-10s %-8s %-4.2f\n" 郭芙 女 47.9876 
执行脚本，输出结果如下所示：

姓名     性别   体重kg
郭靖     男      66.12
杨过     男      48.65
郭芙     女      47.99

```

%s %c %d %f都是格式替代符

%-10s 指一个宽度为10个字符（-表示左对齐，没有则表示右对齐），任何字符都会被显示在10个字符宽的字符内，如果不足则自动以空格填充，超过也会将内容全部显示出来。

%-4.2f 指格式化为小数，其中.2指保留2位小数。

```
#!/bin/bash
# author:菜鸟教程
# url:www.runoob.com

# format-string为双引号
printf "%d %s\n" 1 "abc"

# 单引号与双引号效果一样 
printf '%d %s\n' 1 "abc" 

# 没有引号也可以输出
printf %s abcdef

# 格式只指定了一个参数，但多出的参数仍然会按照该格式输出，format-string 被重用
printf %s abc def

printf "%s\n" abc def

printf "%s %s %s\n" a b c d e f g h i j

# 如果没有 arguments，那么 %s 用NULL代替，%d 用 0 代替
printf "%s and %d \n" 
执行脚本，输出结果如下所示：

1 abc
1 abc
abcdefabcdefabc
def
a b c
d e f
g h i
j  
 and 0

```

#####流程控制 `if [ $int == $anwser ]; then echo "right";else echo "wrong";fi`

`for var in item; do command; done` item是数组不用写括号

```
echo '按下 <CTRL-D> 退出'
echo -n '输入你最喜欢的名字: '
while read NAME
do
    echo "是的！$NAME 是一个好名字"
done

```

#####while后的条件需要加括号 read 读取键盘输入 <CTRL-D> 退出

```
#!/bin/bash
int=1
while([ $int -lt 5 ])   ! ! ! !  ! ! ! ! ! !
do
    echo $int
    let int++
done

```

let 命令是 BASH 中用于计算的工具，用于执行一个或多个表达式，变量计算中不需要加上 $ 来表示变量。 let int++ #case

```
echo '输入 1 到 4 之间的数字:'
echo '你输入的数字为:'
read aNum
case $aNum in
    1)  echo '你选择了 1'
    ;;
    2)  echo '你选择了 2'
    ;;
    3)  echo '你选择了 3'
    ;;
    4)  echo '你选择了 4'
    ;;
    *)  echo '你没有输入 1 到 4 之间的数字'
    ;;
esac

```

case工作方式如上所示。取值后面必须为单词in，每一模式必须以右括号结束。取值可以为变量或常数。匹配发现取值符合某一模式后，其间所有命令开始执行直至 ;;。如果无一匹配模式，使用星号 * 捕获该值，再执行后面的命令。

```
funWithReturn(){
    echo "这个函数会对输入的两个数字进行相加运算..."
    echo "输入第一个数字: "
    read aNum
    echo "输入第二个数字: "
    read anotherNum
    echo "两个数字分别为 $aNum 和 $anotherNum !"
    return $(($aNum+$anotherNum))
}
funWithReturn
echo "输入的两个数字之和为 $? !"

```

#####return返回的值需要两个括号 函数返回值在调用该函数后通过 $? 来获得。 #####输入/输出重定向 command > file 将输出重定向到 file。 command < file 将输入重定向到 file。 command >> file 将输出以追加的方式重定向到 file。 n > file 将文件描述符为 n 的文件重定向到 file。 n >> file 将文件描述符为 n 的文件以追加的方式重定向到 file。 n >& m 将输出文件 m 和 n 合并。 n <& m 将输入文件 m 和 n 合并。 << tag 将开始标记 tag 和结束标记 tag 之间的内容作为输入。

```
接着以上实例，我们需要统计 users 文件的行数,执行以下命令：

$ wc -l users
       2 users
也可以将输入重定向到 users 文件：

$  wc -l < users
       2 
注意：上面两个例子的结果不同：第一个例子，会输出文件名；
第二个不会，因为它仅仅知道从标准输入读取内容

```

#####重定向深入讲解 一般情况下，每个 Unix/Linux 命令运行时都会打开三个文件：

标准输入文件(stdin)：stdin的文件描述符为0，Unix程序默认从stdin读取数据。 标准输出文件(stdout)：stdout 的文件描述符为1，Unix程序默认向stdout输出数据。 标准错误文件(stderr)：stderr的文件描述符为2，Unix程序会向stderr流中写入错误信息。 默认情况下，command > file 将 stdout 重定向到 file，command < file 将stdin 重定向到 file。

如果希望 stderr 重定向到 file，可以这样写：

command < file1 >file2 command 命令将 stdin 重定向到 file1，将 stdout 重定向到 file2。 #Here Document 它的基本的形式如下：

command << delimiter document delimiter 它的作用是将两个 delimiter 之间的内容(document) 作为输入传递给 command。

注意：

结尾的delimiter 一定要顶格写，前面不能有任何字符，后面也不能有任何字符，包括空格和 tab 缩进。 开始的delimiter前后的空格会被忽略掉。

```
wc -l << EOF
    欢迎来到
    菜鸟教程
    www.runoob.com
EOF
3          # 输出结果为 3 行

```

#####/dev/null 文件 如果希望执行某个命令，但又不希望在屏幕上显示输出结果，那么可以将输出重定向到 /dev/null： command > /dev/null /dev/null 是一个特殊的文件，写入到它的内容都会被丢弃；如果尝试从该文件读取内容，那么什么也读不到。但是 /dev/null 文件非常有用，将命令的输出重定向到它，会起到"禁止输出"的效果。


![image.png](https://upload-images.jianshu.io/upload_images/18339009-ae38c3e76a5bcb42.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
