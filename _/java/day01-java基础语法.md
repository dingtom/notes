- [1. Java概述](#head1)
	- [1.1 Java语言背景介绍（了解）](#head2)
	- [1.2 Java语言跨平台原理（理解）](#head3)
	- [1.3 JRE和JDK（记忆）](#head4)
	- [1.4 JDK的下载和安装（应用）](#head5)
		- [1.4.1 下载](#head6)
		- [1.4.2 安装](#head7)
		- [1.4.3 JDK的安装目录介绍](#head8)
- [ 配置环境变量](#head9)
- [2. 第一个演示程序](#head10)
	- [2.2.1 为什么配置环境变量](#head11)
- [2.3 HelloWorld案例（应用）](#head12)
- [2.3.1 Java程序开发运行流程](#head13)
- [2.3.2 HelloWorld案例的编写](#head14)
- [2.3.3 HelloWorld案例的编译和运行](#head15)
- [2.4 HelloWorld案例详解（理解）](#head16)
- [3. java基础语法](#head17)
- [3.1 注释（理解）](#head18)
- [3.2 关键字（理解）](#head19)
- [3.3 常量（应用）](#head20)
- [3.4 变量的介绍(理解)](#head21)
- [3.5 数据类型（应用）](#head22)
	- [3.5.1 计算机存储单元](#head23)
	- [3.5.2 Java中的数据类型](#head24)
- [3.6 变量（应用）](#head25)
	- [3.6.1 变量的定义](#head26)
	- [3.6.2 变量的修改](#head27)
- [3.7 变量的注意事项(理解)](#head28)
- [3.8 键盘录入（理解）](#head29)
- [3.9 标识符（理解）](#head30)
- [1 类型转换](#head31)
	- [1.1 隐式转换(理解)](#head32)
	- [1.2 强制转换(理解)](#head33)
- [2. 运算符](#head34)
	- [ 字符的“+”操作](#head35)
	- [ 字符串的“+”操作](#head36)
	- [ 自增自减运算符](#head37)
	- [ 赋值运算符](#head38)
	- [ 短路逻辑运算符](#head39)
	- [ 三元运算符](#head40)
- [ if/循环](#head41)
	- [ if语句格式](#head42)
	- [ switch语句](#head43)
	- [ for循环:先判断后执行](#head44)
	- [ while循环：先判断后执行](#head45)
	- [ dowhile循环:先执行后判](#head46)
- [ Random](#head47)
- [ 数组](#head48)
	- [ 数组的动态初始化](#head49)
	- [ 静态初始化](#head50)
- [ 内存分配](#head51)
- [ 方法](#head52)
	- [ 方法重载](#head53)
## <span id="head1">1. Java概述</span>

### <span id="head2">1.1 Java语言背景介绍（了解）</span>

​	JavaSE:  Java 语言的（标准版），用于桌面应用的开发，是其他两个版本的基础

​	JavaME: Java 语言的（小型版），用于嵌入式消费类电子设备

​	JavaEE: Java 语言的（企业版），用于 Web 方向的网站开发

### <span id="head3">1.2 Java语言跨平台原理（理解）</span>

Java程序并非是直接运行的，Java编译器将Java源程序编译成与平台无关的字节码文件(class文件)，然后由Java虚拟机（JVM）对字节码文件解释执行。所以在不同的操作系统下，只需安装不同的Java虚拟机即可实现java程序的跨平台。

### <span id="head4">1.3 JRE和JDK（记忆）</span>

JVM（Java Virtual Machine），Java虚拟机

JRE（Java Runtime Environment），Java运行环境，包含了JVM和Java的核心类库（Java API）

JDK（Java Development Kit）称为Java开发工具，包含了JRE和开发工具

总结：我们只需安装JDK即可，它包含了java的运行环境和虚拟机。

### <span id="head5">1.4 JDK的下载和安装（应用）</span>

#### <span id="head6">1.4.1 下载</span>

通过官方网站获取JDK

[http://www.oracle.com](http://www.oracle.com/)

**注意**：针对不同的操作系统，需要下载对应版本的JDK。

#### <span id="head7">1.4.2 安装</span>

**注意**：安装路径不要包含中文或者空格等特殊字符（使用纯英文目录）。

#### <span id="head8">1.4.3 JDK的安装目录介绍</span>

| 目录名称 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| bin      | 该路径下存放了JDK的各种工具命令。javac和java就放在这个目录。 |
| conf     | 该路径下存放了JDK的相关配置文件。                            |
| include  | 该路径下存放了一些平台特定的头文件。                         |
| jmods    | 该路径下存放了JDK的各种模块。                                |
| legal    | 该路径下存放了JDK各模块的授权文档。                          |
| lib      | 该路径下存放了JDK工具的一些补充JAR包。                       |

## <span id="head9"> 配置环境变量</span>

系统变量：

   1.新建->变量名"JAVA_HOME"，变量值"C:\develop\Java\jdk1.8.0_191"（即JDK的安装路径）

​    2.新建->变量名"CLASSPATH",变量值".;%JAVA_HOME%\lib;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar"

环境变量：

1. 编辑->变量名"Path"，点击"新建"，然后输入上"%JAVA_HOME%\bin"，点击"确定"，再次点击"新建"，然后输入上"%JAVA_HOME%\jre\bin"，点击确定。

## <span id="head10">2. 第一个演示程序</span>

#### <span id="head11">2.2.1 为什么配置环境变量</span>

开发Java程序，需要使用JDK提供的开发工具（比如javac.exe、java.exe等命令），而这些工具在JDK的安装目录的bin目录下，如果不配置环境变量，那么这些命令只可以在该目录下执行。我们不可能把所有的java文件都放到JDK的bin目录下，所以配置环境变量的作用就是可以使bin目录下的java相关命令可以在任意目录下使用。    

### <span id="head12">2.3 HelloWorld案例（应用）</span>

#### <span id="head13">2.3.1 Java程序开发运行流程</span>

开发Java程序，需要三个步骤：编写程序，编译程序，运行程序。

#### <span id="head14">2.3.2 HelloWorld案例的编写</span>

1、新建文本文档文件，修改名称为HelloWorld.java。

2、用记事本打开HelloWorld.java文件，输写程序内容。

~~~java
public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("HelloWorld");
	}
}
~~~

#### <span id="head15">2.3.3 HelloWorld案例的编译和运行</span>

存文件，打开命令行窗口，将目录切换至java文件所在目录，编译java文件生成class文件，运行class文件。

> 编译：javac 文件名.java
>
> 范例：javac HelloWorld.java
>
> 执行：java 类名
>
> 范例：java HelloWorld

### <span id="head16">2.4 HelloWorld案例详解（理解）</span>

![图片1.jpg](https://i.loli.net/2021/08/28/QTfqmDWL7H9GRdX.png)

## <span id="head17">3. java基础语法</span>

### <span id="head18">3.1 注释（理解）</span>

~~~java
// 这是单行注释文字
~~~

~~~java
/*
这是多行注释文字
这是多行注释文字
这是多行注释文字
*/
注意：多行注释不能嵌套使用。
~~~

文档注释。文档注释以`/**`开始，以`*/`结束。（以后讲）

### <span id="head19">3.2 关键字（理解）</span>

关键字是指被java语言赋予了特殊含义的单词。

关键字的特点：

​	关键字的字母全部小写。

​	常用的代码编辑器对关键字都有高亮显示，比如现在我们能看到的public、class、static等。

### <span id="head20">3.3 常量（应用）</span>

常量：在程序运行过程中，其值不可以发生改变的量。

Java中的常量分类：

​	字符串常量  用双引号括起来的多个字符（可以包含0个、一个或多个），例如"a"、"abc"、"中国"等

​	整数常量  整数，例如：-10、0、88等

​	小数常量  小数，例如：-5.5、1.0、88.88等

​	字符常量  用单引号括起来的一个字符，例如：'a'、'5'、'B'、'中'等

​	布尔常量  布尔值，表示真假，只有两个值true和false

​	空常量  一个特殊的值，空值，值为null

除空常量外，其他常量均可使用输出语句直接输出。

~~~java
public class Demo {
    public static void main(String[] args) {
        System.out.println(10); // 输出一个整数
        System.out.println(5.5); // 输出一个小数
        System.out.println('a'); // 输出一个字符
        System.out.println(true); // 输出boolean值true
        System.out.println("欢迎来到黑马程序员"); // 输出字符串
    }
}
~~~

### <span id="head21">3.4 变量的介绍(理解)</span>

变量的定义格式：

​	数据类型 变量名 = 数据值；

​	数据类型：为空间中存储的数据加入类型限制。整数？小数？

​	变量名：自己要为空间起的名字，没有难度

​	数据值： 空间中要存储的数值，没有难度

### <span id="head22">3.5 数据类型（应用）</span>

#### <span id="head23">3.5.1 计算机存储单元</span>

我们知道计算机是可以用来存储数据的，但是无论是内存还是硬盘，计算机存储设备的最小信息单元叫“位（bit）”，我们又称之为“比特位”，通常用小写的字母”b”表示。而计算机中最基本的存储单元叫“字节（byte）”，

通常用大写字母”B”表示，字节是由连续的8个位组成。

除了字节外还有一些常用的存储单位，其换算单位如下：

1B（字节） = 8bit

1KB = 1024B

1MB = 1024KB

1GB = 1024MB

1TB = 1024GB

#### <span id="head24">3.5.2 Java中的数据类型</span>

Java是一个强类型语言，Java中的数据必须明确数据类型。在Java中的数据类型包括基本数据类型和引用数据类型两种。

Java中的基本数据类型：

| 数据类型 | 关键字       | 内存占用 | 取值范围                                                     |
| :------- | ------------ | -------- | :----------------------------------------------------------- |
| 整数类型 | byte         | 1        | -128~127                                                     |
|          | short        | 2        | -32768~32767                                                 |
|          | int(默认)    | 4        | -2的31次方到2的31次方-1                                      |
|          | long         | 8        | -2的63次方到2的63次方-1                                      |
| 浮点类型 | float        | 4        | 负数：-3.402823E+38到-1.401298E-45                                                             正数：   1.401298E-45到3.402823E+38 |
|          | double(默认) | 8        | 负数：-1.797693E+308到-4.9000000E-324                                              正数：4.9000000E-324   到1.797693E+308 |
| 字符类型 | char         | 2        | 0-65535                                                      |
| 布尔类型 | boolean      | 1        | true，false                                                  |

说明：

​	e+38表示是乘以10的38次方，同样，e-45表示乘以10的负45次方。

​	在java中整数默认是int类型，浮点数默认是double类型。

### <span id="head25">3.6 变量（应用）</span>

#### <span id="head26">3.6.1 变量的定义</span>

变量：在程序运行过程中，其值可以发生改变的量。

从本质上讲，变量是内存中的一小块区域，其值可以在一定范围内变化。

变量的定义格式：

```java
数据类型 变量名 = 初始化值; // 声明变量并赋值
int age = 18;
System.out.println(age);
```

或者(扩展)

```java
// 先声明，后赋值（使用前赋值即可）
数据类型 变量名;
变量名 = 初始化值;
double money;
money = 55.5;
System.out.println(money);
```

还可以(扩展)

在同一行定义多个同一种数据类型的变量，中间使用逗号隔开。但不建议使用这种方式，降低程序的可读性。

```java
int a = 10, b = 20; // 定义int类型的变量a和b，中间使用逗号隔开
System.out.println(a);
System.out.println(b);

int c,d; // 声明int类型的变量c和d，中间使用逗号隔开
c = 30;
d = 40;
System.out.println(c);
System.out.println(d);
```

#### <span id="head27">3.6.2 变量的修改</span>

```java
int a = 10;
a = 30;  //修改变量的值
System.out.println(a);
```

变量前面不加数据类型时，表示修改已存在的变量的值。

### <span id="head28">3.7 变量的注意事项(理解)</span>

1. 在同一对花括号中，变量名不能重复。
2. 变量在使用之前，必须初始化（赋值）。
3. 定义long类型的变量时，需要在整数的后面加L（大小写均可，建议大写）。因为整数默认是int类型，整数太大可能超出int范围。
4. 定义float类型的变量时，需要在小数的后面加F（大小写均可，建议大写）。因为浮点数的默认类型是double， double的取值范围是大于float的，类型不兼容。

### <span id="head29">3.8 键盘录入（理解）</span>

我们可以通过 Scanner 类来获取用户的输入。使用步骤如下：

```java
1、导包。Scanner 类在java.util包下，所以需要将该类导入。导包的语句需要定义在类的上面。
import java.util.Scanner; 
2、创建Scanner对象。
Scanner sc = new Scanner(System.in);// 创建Scanner对象，sc表示变量名，其他均不可变
3、接收数据
int i = sc.nextInt(); // 表示将键盘录入的值作为int数返回。
```

### <span id="head30">3.9 标识符（理解）</span>

标识符是用户编程时使用的名字，用于给类、方法、变量、常量等命名。

Java中标识符的组成规则：

​	由字母、数字、下划线“_”、美元符号“$”组成，第一个字符不能是数字。

​	不能使用java中的关键字作为标识符。	

​	标识符对大小写敏感（区分大小写）。

Java中标识符的命名约定：

​	小驼峰式命名：变量名、方法名

​		首字母小写，从第二个单词开始每个单词的首字母大写。

​	大驼峰式命名：类名

​		每个单词的首字母都大写。

​	另外，标识符的命名最好可以做到见名知意

### <span id="head31">1 类型转换</span>

在Java中，一些数据类型之间是可以相互转换的。分为两种情况：自动类型转换和强制类型转换。

#### <span id="head32">1.1 隐式转换(理解)</span>

​	把一个表示数据范围小的数值或者变量赋值给另一个表示数据范围大的变量。这种转换方式是自动的，直接书写即可。例如：

```java
double num = 10; // 将int类型的10直接赋值给double类型
System.out.println(num); // 输出10.0
```

​	类型从小到大关系图：

![图片1.png](https://i.loli.net/2021/09/01/nhowH27fCG9qc1D.png)

说明：

1. **整数默认是int类型**，byte、short和char类型数据参与运算均会自动转换为int类型。

2. boolean类型不能与其他基本数据类型相互转换。

3. 算术表达式中包含不同的基本数据类型的值的时候，整个算术表达式的类型会自动进行提升。

   ```java
   int num1 = 10;
   double num2 = 20.0;
   double num3 = num1 + num2; // 使用double接收，因为num1会自动提升为double类型
   ```

```java
byte b1 = 10;
byte b2 = 20;
byte b3 = b1 + b2; 
// 第三行代码会报错，b1和b2会自动转换为int类型，计算结果为int，int赋值给byte需要强制类型转换。
byte b3 = (byte) (b1 + b2);
byte d = 3 + 4; //正确。常量优化机制

```

常量优化机制：

​	在编译时，**整数常量的计算会直接算出结果**，并且会自动判断该结果是否在byte取值范围内，在：编译通过.不在：编译失败

#### <span id="head33">1.2 强制转换(理解)</span>

​	把一个表示数据范围大的数值或者变量赋值给另一个表示数据范围小的变量。

​	强制类型转换格式：目标数据类型 变量名 = (目标数据类型)值或者变量;

​	例如：

```java
double num1 = 5.5;
int num2 = (int) num1; // 将double类型的num1强制转换为int类型
System.out.println(num2); // 输出5（小数位直接舍弃）
```

### <span id="head34">2. 运算符</span>

#### <span id="head35"> 字符的“+”操作</span>

char类型参与算术运算，使用的是计算机底层对应的十进制数值。需要我们记住三个字符对应的数值：

'a'  --  97		a-z是连续的，所以'b'对应的数值是98，'c'是99，依次递加

'A'  --  65		A-Z是连续的，所以'B'对应的数值是66，'C'是67，依次递加

'0'  --  48		0-9是连续的，所以'1'对应的数值是49，'2'是50，依次递加

```java
// 可以通过使用字符与整数做算术运算，得出字符对应的数值是多少
char ch1 = 'a';
System.out.println(ch1 + 1); // 输出98，97 + 1 = 98
char ch2 = 'A';
System.out.println(ch2 + 1); // 输出66，65 + 1 = 66

char ch3 = '0';
System.out.println(ch3 + 1); // 输出49，48 + 1 = 49
```

#### <span id="head36"> 字符串的“+”操作</span>

当“+”操作中出现字符串时，这个”+”是字符串连接符，而不是算术运算。

~~~java
System.out.println("itheima"+ 666); // 输出：itheima666
~~~

在”+”操作中，如果出现了字符串，就是连接运算符，否则就是算术运算。当连续进行“+”操作时，从左到右逐个执行。

#### <span id="head37"> 自增自减运算符</span>

​	++和-- 既可以放在变量的后边，也可以放在变量的前边。

​	单独使用的时候， ++和-- 无论是放在变量的前边还是后边，结果是一样的。

​	参与操作的时候，如果放在变量的后边，先拿变量参与操作，后拿变量做++或者--。

​	参与操作的时候，如果放在变量的前边，先拿变量做++或者--，后拿变量参与操作。

```java
int x = 10;
int y = x++; // 赋值运算，++在后边，所以是使用x原来的值赋值给y，x本身自增1
System.out.println("x:" + x + ", y:" + y); // x:11，y:10

int m = 10;
int n = ++m; // 赋值运算，++在前边，所以是使用m自增后的值赋值给n，m本身自增1
System.out.println("m:" + m + ", m:" + m); // m:11，m:11
```

 

#### <span id="head38"> 赋值运算符</span>

| 符号 | 作用       | 说明                  |
| ---- | ---------- | --------------------- |
| =    | 赋值       | a=10，将10赋值给变量a |
| +=   | 加后赋值   | a+=b，将a+b的值给a    |
| -=   | 减后赋值   | a-=b，将a-b的值给a    |
| *=   | 乘后赋值   | a*=b，将a×b的值给a    |
| /=   | 除后赋值   | a/=b，将a÷b的商给a    |
| %=   | 取余后赋值 | a%=b，将a÷b的余数给a  |

注意：

**扩展的赋值运算符隐含了强制类型转换。**

~~~java
short s = 10;
s = s + 10; // 此行代码报出，因为运算中s提升为int类型，运算结果int赋值给short可能损失精度
s += 10; // 此行代码没有问题，隐含了强制类型转换，相当于 s = (short) (s + 10);
~~~

逻辑运算符把各个运算的关系表达式连接起来组成一个复杂的逻辑表达式，以判断程序中的表达式是否成立，判断的结果是 true 或 false。

| 符号 | 作用     | 说明                                         |
| ---- | -------- | -------------------------------------------- |
| &    | 逻辑与   | a&b，a和b都是true，结果为true，否则为false   |
| \|   | 逻辑或   | a\|b，a和b都是false，结果为false，否则为true |
| ^    | 逻辑异或 | a^b，a和b结果不同为true，相同为false         |
| !    | 逻辑非   | !a，结果和a的结果正好相反                    |

#### <span id="head39"> 短路逻辑运算符</span>

| 符号 | 作用   | 说明                         |
| ---- | ------ | ---------------------------- |
| &&   | 短路与 | 作用和&相同，但是有短路效果  |
| \|\| | 短路或 | 作用和\|相同，但是有短路效果 |

在逻辑与运算中，只要有一个表达式的值为false，那么结果就可以判定为false了，没有必要将所有表达式的值都计算出来，短路与操作就有这样的效果，可以提高效率。同理在逻辑或运算中，一旦发现值为true，右边的表达式将不再参与运算。

- 逻辑与&，无论左边真假，右边都要执行。

- **短路与&&，如果左边为真，右边执行；如果左边为假，右边不执行。**

- 逻辑或|，无论左边真假，右边都要执行。

- **短路或||，如果左边为假，右边执行；如果左边为真，右边不执行。**

#### <span id="head40"> 三元运算符</span>

三元运算符语法格式：

~~~java
关系表达式 ? 表达式1 : 表达式2
~~~

举例：

~~~java
int a = 10;
int b = 20;
int c = a > b ? a : b; // 判断 a>b 是否为真，如果为真取a的值，如果为假，取b的值
~~~

### <span id="head41"> if/循环</span>

#### <span id="head42"> if语句格式</span>

~~~java
格式：
if (关系表达式) {
    语句体1;	
} else {
    语句体2;	
}
~~~

#### <span id="head43"> switch语句</span>

```java
switch (表达式) {
	case 1:
		语句体1;
		break;
	case 2:
		语句体2;
		break;
	...
	default:
		语句体n+1;
		break;
}
# 和case依次比较，一旦有对应的值，就会执行相应的语句，在执行的过程中，遇到break就会结 束。 如果所有的case都和表达式的值不匹配，就会执行default语句体部分，然后程序结束掉。 
```

#### <span id="head44"> for循环:先判断后执行</span>

```java
for (初始化语句;条件判断语句;条件控制语句) {
	循环体语句;
}
```

* 格式解释：

#### <span id="head45"> while循环：先判断后执行</span>

  ```java
  初始化语句;
  while (条件判断语句) {
  	循环体语句;
      条件控制语句;
  }
  ```

#### <span id="head46"> dowhile循环:先执行后判</span>

```java
初始化语句;
do {
	循环体语句;
	条件控制语句;
}while(条件判断语句);
```

* 跳转控制语句（break）
  * 跳出循环，结束循环
* 跳转控制语句（continue）
  * 跳过本次循环，继续下次循环

### <span id="head47"> Random</span>

```java
1. 导入包
import java.util.Random;
2. 创建对象
Random r = new Random();
3. 产生随机数
int num = r.nextInt(10);
```

### <span id="head48"> 数组</span>

​	数组就是存储数据长度固定的容器，存储多个数据的数据类型要一致。 

示例：

```java
int[] arr;        
double[] arr;      
char[] arr;
```

```java
int arr[];
double arr[];
char arr[];
```

#### <span id="head49"> 数组的动态初始化</span>

​	数组动态初始化就是只给定数组的长度，由系统给出默认初始化值

```java
int[] arr = new int[3]
```

#### <span id="head50"> 静态初始化</span>

```java
int[] arr =  new int[]{1,2,3}
```







### <span id="head51"> 内存分配</span>

我们编写的程序是存放在硬盘中的，在硬盘中的程序是不会运行的。必须放进内存中才能运行，运行完毕后会清空内存。 Java虚拟机要运行程序，必须要对内存进行空间的分配和管理。 

- 目前我们只需要记住两个内存，分别是：栈内存和堆内存

| 区域名称   | 作用                                                       |
| ---------- | ---------------------------------------------------------- |
| 寄存器     | 给CPU使用，和我们开发无关。                                |
| 本地方法栈 | JVM在使用操作系统功能的时候使用，和我们开发无关。          |
| 方法区     | 存储可以运行的class文件。                                  |
| 堆内存     | 存储对象或者数组，new来创建的，都存储在堆内存。            |
| 方法栈     | 方法运行时使用的内存，比如main方法运行，进入方法栈中执行。 |

一个数组内存图

![1591007817165.png](https://i.loli.net/2021/09/01/cSPQvRI3arBDqfx.png)

多个数组指向相同内存图

<img src='https://i.loli.net/2021/09/01/yOaHt5xWkN1pvim.png' title='1591007957052.png' />

两个数组内存图

![1591007925899.png](https://i.loli.net/2021/09/01/t1bSxpA7u5LEZN8.png)

### <span id="head52"> 方法</span>

```java
public static int getMax(int num1, int nmu2) {
    if(a < b){
        return b
    }else{
        return a
    }
}
```

public static 	修饰符，目前先记住这个格式

返回值类型	方法操作完毕之后返回的数据的数据类型, 如果方法操作完毕，没有数据返回，这里写void，而且方法体中一般不写return

 方法名		调用方法时候使用的标识

 参数		由数据类型和变量名组成，多个参数之间用逗号隔开

 方法体		完成功能的代码块

 return		如果方法操作完毕，有数据返回，用于把数据返回给调用者

#### <span id="head53"> 方法重载</span>

方法重载指同一个类中定义的多个方法之间的关系，满足下列条件的多个方法相互构成重载

* 多个方法在同一个类中
* 多个方法具有相同的方法名
* 多个方法的参数不相同，类型不同或者数量不同        



* **基本数据类型的参数，形式参数的改变，不影响实际参数** 

  每个方法在栈内存中，都会有独立的栈空间，方法运行结束后就会弹栈消失

* **对于引用类型的参数，形式参数的改变，影响实际参数的值** 

​           引用数据类型的传参，传入的是地址值，内存中会造成两个引用指向同一个内存的效果，所以即使方法弹栈，堆内存中的数据也已经是改变后的结果 



```java
  public static void main(String[] args) {
        int[] arr = {10, 20, 30};
        # 方法参数传递为引用数据类型 :  传入方法中的, 是内存地址.
        System.out.println("调用change方法前:" + arr[1]);
        change(arr);
        System.out.println("调用change方法后:" + arr[1]);
    }
    public static void main(String[] args) {
        int number = 100;
        # 方法参数传递为基本数据类型 :传入方法中的, 是具体的数值
        System.out.println("调用change方法前:" + number);
        change(number);
        System.out.println("调用change方法后:" + number);
    }
    public static void change(int[] arr) {
        arr[1] = 200;
    }
```

