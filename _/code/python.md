- [ 随机数](#head1)
- [ global](#head2)
- [ 内置函数](#head3)
	- [ filter()](#head4)
	- [ map()](#head5)
- [ reduce()](#head6)
	- [ zip()](#head7)
	- [ any、all()](#head8)
- [ 第一次获取到内部的函数，之后可以直接使用这个函数](#head9)
- [ 或者我们也可以写成一句话：](#head10)
- [ @wraps接受一个函数来进行装饰，并加入了复制函数名称、注释文档、参数列表等等的功能。这可以让我们在装饰器里面访问在装饰之前的函数的属性。](#head11)
- [ 打开logfile，并写入内容](#head12)
- [ 现在将日志打到指定的logfile](#head13)
- [Output: myfunc1 was called](#head14)
- [现在一个叫做 out.log 的文件出现了，里面的内容就是上面的字符串](#head15)
- [Output: myfunc2 was called](#head16)
- [现在一个叫做 func2.log 的文件出现了，里面的内容就是上面的字符串](#head17)
- [ 下面定义了一个say实例方法](#head18)
- [和 self 一样，cls 参数的命名也不是规定的（可以随意命名），只是 Python 程序员约定俗称的习惯而已。](#head19)
- [ 将得到”E:/lena“+".jpg"。](#head20)
- [ 列表、字典](#head21)
- [ 问题](#head22)
	- [ 列表和元组的区别](#head23)
	- [ GlL全局解释器锁](#head24)
	- [ python传参数是传值还是传址？](#head25)
	- [ python对象销毁(垃圾回收)](#head26)
	- [ 简述乐观锁和悲观锁](#head27)
	- [ 使用多进程提升cpu密集任务效率](#head28)
	- [ 使用多线程提升IO密集任务效率](#head29)
	- [ 可变数据类型和不可变数据类型](#head30)
	- [深拷贝和浅拷贝的区别 ](#head31)
- [bytes 和 str 的互相转换](#head32)
Python是解释型语言，Python运行前不需要编译。动态类型的，这意味着你不需要在声明变量时指定类型

- == 比较值，is比较ID(内存地址）
- and\or逻辑比较，&\|按位与\或
- 字符串拼接join()快，用加号拼接字符串因为字符串是不可变对象所以每次都会生成新对象
- 支持链式比较（两两相比加and）


# <span id="head1"> 随机数</span>
```
随机种子：random.seed()
随机选择：random.choice(List)
随机打乱：random.shuffle(List)
随机整数：random.randint(a,b),生成区间内的整数
随机采样:random.sample(list18, samples)
随机小数：random.random()
习惯用numpy库，利用np.random.randn(5)生成5个随机小数
```
# <span id="head2"> global</span>
在函数声明 修改全局变量
```
a=4
def f():
    global a 
    a=3 
f()
```

# <span id="head3"> 内置函数</span>
## <span id="head4"> filter()</span>
用于过滤序列，过滤掉不符合条件的元素，返回由符合条件元素组成的新列表。该接收两个参数，第一个为函数，第二个为序列，序列的每个元素作为参数传递给函数进行判，然后返回 True 或 False，最后将返回 True 的元素放到新列表
```
t = filter(lambda x:True if x>3 else False, [0, 2, 3, 4, 5])
print(list(t))
```
## <span id="head5"> map()</span>
接收两个参数，一个是函数，一个是Iterable，map将传入的函数依次作用到序列的每个元素，并把结果作为新的Iterator返回。
```
比如，把这个list所有数字转为字符串：
list(map(str, [1, 2, 3, 4, 5, 6, 7, 8, 9]))
['1', '2', '3', '4', '5', '6', '7', '8', '9']
```
# <span id="head6"> reduce()</span>
把一个函数作用在一个序列[x1, x2, x3, ...]上，这个函数必须接收两个参数，reduce把结果继续和序列的下一个元素做累积计算，其效果就是
```
reduce(f, [x1, x2, x3, x4]) = f(f(f(x1, x2), x3), x4)
```
```
# 比方说对一个序列求和，就可以用reduce实现：
from functools import reduce
def add(x, y):
  return x + y
reduce(add, [1, 3, 5, 7, 9])
25
```
## <span id="head7"> zip()</span>
会将列表按位组合成元组
## <span id="head8"> any、all()</span>
```0/""/False```无义

any(x)判断x对象是否为空对象，如果都为空、0、false，则返回false，如果不都为空、0、false，则返回true  #all(x)
如果all(x)参数x对象的所有元素不为0、''、False或者x为空对象，则返回True，否则返回False
>注意：
>all([])             # 空列表 True 
>all(())             # 空元组 True

# 列表推导式、字典推导式、生成器
![](https://upload-images.jianshu.io/upload_images/18339009-e3e9887c9e9be645.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 闭包(装饰器的本质也是闭包)
一个函数在另一个函数里面，外层函数返回的是里层函数，返回的函数还可以访问它的定义所在的作用域，也就是它带着它的环境。这个被称为闭包。

闭包可以保存环境变量

```
def outer(factor):
    def inner(number):
        return number*factor
    return inner
func_inner = outer(4)
# <span id="head9"> 第一次获取到内部的函数，之后可以直接使用这个函数</span>
print(func_inner)
value = func_inner(5)
print(value)  # 20
# <span id="head10"> 或者我们也可以写成一句话：</span>
value = outer(4)(5)
print(value)  # 20
```

# [函数装饰器](https://www.runoob.com/w3cnote/python-func-decorators.html)

为什么要用装饰器：在写项目的时候不可能一次性把所有的功能都全部写完或则上线前客户需求改变，需要**对函数添加新功能（装饰器函数）。**

开放封闭原则:
**对扩展是开放**：就是对项目的拓展功能是开放的，可以进行添加新的功能
**对修改是封闭的**：就是对原函数的源代码和调用方式是封闭的



使用函数装饰器 A() 去装饰另一个函数 B()，其底层执行了如下 2 步操作：
将 B 作为参数传给 A() 函数；将 A() 函数执行完成的返回值反馈回  B。
```
@A
def B():
=============================
B = A(B)
```
多个装饰器的执行顺序
```
@B
@A
def C()
装饰过程；A->B
执行过程；B->A
```
```
# <span id="head11"> @wraps接受一个函数来进行装饰，并加入了复制函数名称、注释文档、参数列表等等的功能。这可以让我们在装饰器里面访问在装饰之前的函数的属性。</span>
from functools import wraps
 
def logit(logfile='out.log'):
    def logging_decorator(func):
        @wraps(func)  # 在装饰器里面访问在装饰之前的函数的属性。
        def wrapped_function(*args, **kwargs):
            log_string = func.__name__ + " was called"
            print(log_string)
# <span id="head12"> 打开logfile，并写入内容</span>
            with open(logfile, 'a') as opened_file:
# <span id="head13"> 现在将日志打到指定的logfile</span>
                opened_file.write(log_string + '\n')
            return func(*args, **kwargs)
        return wrapped_function
    return logging_decorator
```
``` 
@logit()
def myfunc1():
    pass
 
myfunc1()
# <span id="head14">Output: myfunc1 was called</span>
# <span id="head15">现在一个叫做 out.log 的文件出现了，里面的内容就是上面的字符串</span>
```
```
@logit(logfile='func2.log')
def myfunc2():
    pass
 
myfunc2()
# <span id="head16">Output: myfunc2 was called</span>
# <span id="head17">现在一个叫做 func2.log 的文件出现了，里面的内容就是上面的字符串</span>
```


# 面向对象

实例的变量名如果以__开头，就变成了一个私有变量（private）。

仍然可以通过_Student__name来访问__name变量，表面上看，外部代码“成功”地设置了“__name”变量，但实际上这个“__name”变量和class内部的__name变量不是一个变量

对于静态语言（例如Java）来说，如果需要传入Animal类型，则传入的对象必须是Animal类型或者它的子类，否则，将无法调用run()方法。**对于Python这样的动态语言来说，则不一定需要传入Animal类型。我们只需要保证传入的对象有一个run()方法就可以**了：这就是动态语言的“鸭子类型”

dir()：获得一个对象的所有属性和方法
getattr()、setattr()以及hasattr()，我们可以直接操作一个对象的状态
```
hasattr(obj, 'x') # 有属性'x'吗？
setattr(obj, 'y', 19) # 设置一个属性'y'
getattr(obj, 'z', 404) # 获取属性'z'，如果不存在，返回默认值404
```
还可以尝试给实例绑定一个方法,但是，**给一个实例绑定的方法，对另一个实例是不起作用的**
```
def set_age(self, age): # 定义一个函数作为实例方法
  self.age = age
from types import MethodType
给实例绑定一个方法
s.set_age = MethodType(set_age, s) #
```
为了给所有实例都绑定方法，可以**给class绑定方法：**
```
Student.set_age = set_age
```
如果我们想要限制实例的属性，比如，只允许对Student实例添加name和age属性。
为了达到限制的目的，Python允许在定义class的时候，定义一个特殊的**__slots__变量，来限制该class实例能添加的属性,__slots__定义的属性仅对当前类实例起作用，对继承的子类是不起作用的**：
```
class Student(object):
    __slots__ = ('name', 'age') # 用tuple定义允许绑定的属性名称
```



# 实例方法，类方法，静态方法                  

- 实例方法， 类对象直接调用实例方法

- @classmethod   类方法，使用类名直接调用类方法，**不需要实例化，类方法只能调用类属性，不能调用实例属性**

- @staticmethod  静态方法，不需要实例化，其实就是函数，**和函数唯一的区别是，静态方法定义在类这个空间（类命名空间）中**，而函数则定义在程序所在的空间（全局命名空间）中。
> 静态方法**没有类似 self、cls 这样的特殊参数**，因此 Python 解释器不会对它包含的参数做任何类或对象的绑定。也正因为如此，类的静态方法中**无法调用任何类属性和类方法。**

- @property 把一个方法变成一个静态属性



```
#类构造方法，也属于实例方法
def __init__(self):
     self.name = "C语言中文网"
     self.add = "http://c.biancheng.net"
# <span id="head18"> 下面定义了一个say实例方法</span>
def say(self):
     print("正在调用 say() 实例方法")
#下面定义了一个类方法
@classmethod
def info(cls):
     print("正在调用类方法",cls)
# <span id="head19">和 self 一样，cls 参数的命名也不是规定的（可以随意命名），只是 Python 程序员约定俗称的习惯而已。</span>
@staticmethod
    def info(name,add):
        print(name,add)
```



# 文件操作
**目录下所以有文件路径**
```
result = []
    for root, subdir, files in os.walk('./draft'):
        for f in files:
            path = os.path.join(root, f)
            result.append(path)
```

## os.walk()

```
 for root, dirs, files in os.walk('.'):  
        print(root) #当前目录路径  
        print(dirs) #当前路径下所有子目录  (不包括子目录)
        print(files) #当前路径下所有非目录子文件(不包括子目录)
        print('*'*10)
```
 ## os.path.splitext()
函数将路径拆分为文件名+扩展名
```
os.path.splitext(“E:/lena.jpg”)
# <span id="head20"> 将得到”E:/lena“+".jpg"。</span>
```
## os.path.basename
返回path最后的文件名。如果path以／或\结尾，那么就会返回空值。即os.path.split(path)的第二个元素。
```
path = '/Users/beazley/Data/data.csv'
os.path.basename(path)
#'data.csv'
```
## os.listdir()
所有目录和文件

```
1.创建目录
os.mkdir(“file”)
2.复制文件：
shutil.copyfile(“oldfile”,”newfile”) #oldfile和newfile都只能是文件
shutil.copy(“oldfile”,”newfile”) #oldfile只能是文件夹，newfile可以是文件，也可以是目标目录
3.复制文件夹：
shutil.copytree(“olddir”,”newdir”) #olddir和newdir都只能是目录，且newdir必须不存在
5.重命名文件(目录)
os.rename(“oldname”,”newname”) #文件或目录都是使用这条命令
6.移动文件(目录)
shutil.move(“oldpos”,”newpos”)
7.删除文件
os.remove(“file”)
8.删除目录
os.rmdir(“dir”) #只能删除空目录
shutil.rmtree(“dir”) #空目录、有内容的目录都可以删
```

# 生成器
```
def foo():
    print("starting...")
    while True:
        res = yield 4
        print("res:",res)
g = foo()
print(next(g))
print("*"*20)
print(next(g))
```
```
starting...
4
********************
res: None
4
```

1.程序开始执行以后，**因为foo函数中有yield关键字，所以foo函数并不会真的执行，而是先得到一个生成器g(相当于一个对象).**
2.直到**调用next方法，foo函数正式开始执行**，先执行foo函数中的print方法，然后进入while循环
3.程序遇到yield关键字，然后把yield想想成return,return了一个4之后，程序停止，并没有执行赋值给res操作，此时next(g)语句执行完成，所以输出的前两行（第一个是while上面的print的结果,第二个是return出的结果）是执行print(next(g))的结果，
4.程序执行print("*"*20)，输出20个*
5.又开始执行下面的print(next(g)),这个时候和上面那个差不多，不过不同的是，**这个时候是从刚才那个next程序停止的地方开始执行的**，也就是要执行res的赋值操作，这时候要注意，这个时候赋值操作的右边是没有值的（因为刚才那个是return出去了，并没有给赋值操作的左边传参数），所以这个时候res赋值是None,所以接着下面的输出就是res:None,
6.程序会继续在while里执行，又一次碰到yield,这个时候同样return 出4，然后程序停止，print函数输出的4就是这次return出的4.


# 切片
object[start_index:end_index:step]
![](https://upload-images.jianshu.io/upload_images/18339009-708a7823d1970cd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
print(a[::-1]) ### 取从后向前（相反）的元素
a[3::2]# 从索引3开始隔一取一
```
# 格式化字符串
![](https://upload-images.jianshu.io/upload_images/18339009-10f11f25f2121404.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 传参
```fun(\*args,\*\*kwargs)```
```*args```是用来发送一个非键值对的可变数量的参数列表给一个函数
```**kwargs```允许你将不定长度的键值对，作为参数传递给一个函数。

```
def func1(a,b=1,*c,**d):
    print(a,b,c,d)  
    
l = [3,4]
dic = {'@':2,'#':3}

func1(1,2,l,dic)
func1(1,2,*l,**dic)
func1(1,2,3,4,**dic)

# 1 2 ([3, 4], {'@': 2, '#': 3}) {}
# 1 2 (3, 4) {'@': 2, '#': 3}
# 1 2 (3, 4) {'@': 2, '#': 3}
```


# <span id="head21"> 列表、字典</span>
字典的合并
```
dictl={'a':1,'b':2}
dict2={'c':3,'d':4}
dictl.update(dict2)
print(dictl)
```



# <span id="head22"> 问题</span>
## <span id="head23"> 列表和元组的区别</span>
>1. 列表是动态数组，它们可变且可以重设长度（改变其内部元素的个数）。
>2. 元组是静态数组，它们不可变，且其内部数据一旦创建便无法改变。 但是我们可以将两个元组合并成一个新元组。 
>3. **元组缓存于Python运行时环境，这意味着我们每次使用元组时无须访问内核去分配内存**
>  Python是一门垃圾收集语言，这意味着当一个变量不再被使用时，Python会将该变量使用的内存释放回操作系统，以供其他程序（变量）使用。然而，对于长度为1\~20的元组，**即使它们不在被使用，它们的空间也不会立刻还给系统，而是留待未来使用**。这意味着当未来需要一个同样大小的新的元组时，我们不再需要向操作系统申请一块内存来存放数据，因为我们已经有了预留的空间。 

## <span id="head24"> GlL全局解释器锁</span>
python的全局解释器锁，**同一进程中假如有多个线程运行线程在运行python程序的时候会霸占 python解释器（加了一把锁即GlL)，使该进程内的其他线程无法运行**，等该线程运行完后其他线程才能运行。如果线程运行过程中遇到耗时操作，则解释器锁解开，使其他线程运行。所以在多线程中，线程的运行仍是有先后顺序的，并不是同时进行

多进程中因为每个进程都能被系统分配资源，相当于**每个进程有了一个 python解释器**所以多进程可以实现多个进程的同时运行，缺点是进程系统资源开销大


## <span id="head25"> python传参数是传值还是传址？</span>
Python中函数参数是**引用传递**（注意不是值传递）。对于不可变类型（数值型、字符串、元组），因变量不能修改，所以运算不会影响到变量自身；而对于可变类型（列表字典）来说，函数体运算可能会更改传入的参数变量。
![](https://upload-images.jianshu.io/upload_images/18339009-afd4b5afada2703d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## <span id="head26"> python对象销毁(垃圾回收)</span>
>Python 使用了引用计数这一简单技术来跟踪和回收垃圾。在 Python 内部记录着所有使用中的对象各有多少引用。一个内部跟踪变量，称为一个引用计数器。当对象被创建时， 就创建了一个引用计数， 当这个对象不再需要时， 也就是说，**这个对象的引用计数变为0 时， 它被垃圾回收。但是回收不是"立即"的**， 由解释器在适当的时机，将垃圾对象占用的内存空间回收。循环引用指的是，两个对象相互引用，但是没有其他变量引用他们。这种情况下，仅使用引用计数是不够的。**Python 的垃圾收集器实际上是一个引用计数器和一个循环垃圾收集器。**

## <span id="head27"> 简述乐观锁和悲观锁</span>
悲观锁，就是很悲观，**每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁**，这样别人想拿这个数据就会 block直到它拿到锁。传统的关系型数据库里边就用到了很多这种锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。
乐观锁，就是很乐观，每次去**拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据**，可以使用版本号等机制，乐观锁适用于多读的应用类型，这样可以提高吞吐量



## <span id="head28"> 使用多进程提升cpu密集任务效率</span>
```
import time
import multiprocessing
def muchjob(x):
    time.sleep(5)
    return(x**2)
if __name__ == '__main__':
    # 多进程任务
    pool = multiprocessing.Pool(processes=8)
    result= []
    for i in range(8):
        result.append(pool.apply_async(muchjob, (i,)))
    pool.close()
    pool.join()
    ans = [res.get() for res in result]
    print(ans)
```
## <span id="head29"> 使用多线程提升IO密集任务效率</span>
```
import threading
def writefile(i):
    a = [x**2 for x in range(i)]
# 多线程任务
thread_list= []
for i in range(300):
    t = threading.Thread(target=writefile, args=(i,))
    t.setDaemon(True)  # 设置为守护线程
    thread_list.append(t)
for t in thread_list:
    t.start()  # 启动线程
for t in thread_list:
    t.join()  # 等待子线程结束
```

## <span id="head30"> 可变数据类型和不可变数据类型</span>
**不可变数据类型：数值型、字符串型string和元组tuple**

**不允许变量的值发生变化，如果改变了变量的值，相当于是新建了一个对象，而对于相同的值的对象，在内存中则只有一个对象（一个地址）**，如下图用id()方法可以打印对象的id

![](https://upload-images.jianshu.io/upload_images/18339009-9589825b5b5a5d08.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

可变数据类型：列表list和字典dict；

允许变量的值发生变化，即如果对变量进行append、+=等这种操作后，**只是改变了变量的值，而不会新建一个对象，变量引用的对象的地址也不会变化，不过对于相同的值的不同对象，在内存中则会存在不同的对象**，即每个对象都有自己的地址，相当于内存中对于同值的对象保存了多份，这里不存在引用计数，是实实在在的对象。

![](https://upload-images.jianshu.io/upload_images/18339009-2f0cc1774c1dc642.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## <span id="head31">深拷贝和浅拷贝的区别 </span>

在浅拷贝时，拷贝出来的**新对象的地址和原对象是不一样**的，但是新对象里面的**可变元素（如列表）的地址和原对象里的可变元素的地址是相同的**，浅拷贝它拷贝的是浅层次的数据结构（不可变元素），对象里的可变元素作为深层次的数据结构并没有被拷贝到新地址里面去。

- 赋值： 值相等，地址相等
- copy浅拷贝：值相等，地址不相等(深层地址相等) 
```b=a[:]``` \ ```copy.copy()```
- deepcopy深拷贝：值相等，地址不相等
```b=a[:]``` \ ```copy.deepcopy()```

**Python使用乘号复制双层列表，内层数组指向同一个物理地址**
```python
a = [1, 2, 3]
b = a
b[0] = 9
print(a)   # copy.copy()  [9, 2, 3]  值相等，地址相等
c = a[:]
c[0] = 11
print(a)    #  [9, 2, 3]  拷贝
```





# <span id="head32">bytes 和 str 的互相转换</span>
str.encode('utf-8')
bytes.decode('utf-8')

