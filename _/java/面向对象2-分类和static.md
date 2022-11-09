- [2.分类思想 ](#head1)
	- [2.1分类思想概述 (理解) ](#head2)
	- [2.2黑马信息管理系统 (理解)](#head3)
- [ 3.分包思想](#head4)
	- [3.3包的注意事项 (理解) ](#head5)
	- [3.4类与类之间的访问 (理解) ](#head6)
- [ 4.黑马信息管理系统](#head7)
	- [4.1系统介绍 (理解) ](#head8)
	- [4.2学生管理系统 (应用) ](#head9)
		- [ 4.2.1需求说明](#head10)
		- [ 4.2.2实现步骤](#head11)
	- [ ](#head12)
		- [4.3.1需求说明 ](#head13)
		- [4.3.2实现步骤 ](#head14)
- [ static关键字](#head15)
	- [5.1static关键字概述 (理解)](#head16)
	- [5.2static修饰的特点 (记忆) ](#head17)
	- [5.3static关键字注意事项 (理解)](#head18)
## <span id="head1">2.分类思想 </span>

### <span id="head2">2.1分类思想概述 (理解) </span>

分工协作,专人干专事

### <span id="head3">2.2黑马信息管理系统 (理解)</span>

+ Student类  标准学生类,封装键盘录入的学生信息(id , name , age , birthday)

+ StudentDao类  Dao : (Data Access Object 缩写) 用于访问存储数据的数组或集合

+ StudentService类  用来进行业务逻辑的处理(例如: 判断录入的id是否存在)

+ StudentController类  和用户打交道(接收用户需求,采集用户信息,打印数据到控制台)

  ![01_黑马信息管理系统分类](.\img\01_黑马信息管理系统分类.png)

## <span id="head4"> 3.分包思想</span>

如果将所有的类文件都放在同一个包下,不利于管理和后期维护,所以,对于不同功能的类文件,可以放在不同的包下进行管理

+ 包，本质上就是文件夹

+ 创建包，多级包之间使用 " . " 进行分割
多级包的定义规范：公司的网站地址翻转(去掉www)：com.itheima.其他的包名
  
+ 字母都是小写


### <span id="head5">3.3包的注意事项 (理解) </span>

+ package语句必须是程序的**第一条可执行的代码** 
+ package语句在一个java文件中只**能有一个** 
+ 如果没有package,默认表示无包名 

### <span id="head6">3.4类与类之间的访问 (理解) </span>

+ 同一个包下的访问，不需要导包，直接使用即可

+ 不同包下的访问

  1.import 导包后访问

  2.通过全类名（包名 + 类名）访问

+ 注意：import 、package 、class 三个关键字的摆放位置存在顺序关系

  package 必须是程序的第一条可执行的代码

  import 需要写在 package 下面

  class 需要在 import 下面

## <span id="head7"> 4.黑马信息管理系统</span>

### <span id="head8">4.1系统介绍 (理解) </span>

![02_黑马信息管理系统介绍](.\img\02_黑马信息管理系统介绍.png)

### <span id="head9">4.2学生管理系统 (应用) </span>

#### <span id="head10"> 4.2.1需求说明</span>

+ 添加学生: 键盘录入学生信息(id，name，age，birthday)

  使用数组存储学生信息,要求学生的id不能重复

+ 删除学生: 键盘录入要删除学生的id值,将该学生从数组中移除,如果录入的id在数组中不存在,需要重新录入

+ 修改学生: 键盘录入要修改学生的id值和修改后的学生信息

  将数组中该学生的信息修改,如果录入的id在数组中不存在,需要重新录入

+ 查询学生: 将数组中存储的所有学生的信息输出到控制台

#### <span id="head11"> 4.2.2实现步骤</span>

+ 环境搭建实现步骤 
  | 包                                       | 存储的类                   | 作用                   |
  | --------------------------------------- | ---------------------- | -------------------- |
  | com.itheima.edu.info.manager.domain     | Student.java           | 封装学生信息               |
  | com.itheima.edu.info.manager.dao        | StudentDao.java        | 访问存储数据的数组，进行赠删改查（库管） |
  | com.itheima.edu.info.manager.service    | StudentService.java    | 业务的逻辑处理（业务员）         |
  | com.itheima.edu.info.manager.controller | StudentController.java | 和用户打交道（客服接待）         |
  | com.itheima.edu.info.manager.entry      | InfoManagerEntry.java  | 程序的入口类，提供一个main方法    |

+ 菜单搭建实现步骤 

  + 需求
    + 黑马管理系统菜单搭建
    + 学生管理系统菜单搭建
  + 实现步骤
    1. 展示欢迎页面,用输出语句完成主界面的编写
    2. 获取用户的选择,用Scanner实现键盘录入数据
    3. 根据用户的选择执行对应的操作,用switch语句完成操作的选择

+ 添加功能实现步骤 

  ![03_添加功能需求分析](.\img\03_添加功能需求分析.png)


+ 添加功能优化:判断id是否存在

  ![04_判断id是否存在](.\img\04_判断id是否存在.png)

+ 查询功能实现步骤

  ![05_查询功能需求分析](.\img\05_查询功能需求分析.png)

+ 删除功能实现步骤

  ![06_删除功能需求分析](.\img\06_删除功能需求分析.png)

+ 修改功能实现步骤 

  ![07_修改功能需求分析](.\img\07_修改功能需求分析.png)

+ 系统优化 

  + 把updateStudent和deleteStudentById中录入学生id代码抽取到一个方法(inputStudentId)中
    该方法的主要作用就是录入学生的id，方法的返回值为String类型


  + 把addStudent和updateStudent中录入学生信息的代码抽取到一个方法(inputStudentInfo)中
    该方法的主要作用就是录入学生的信息，并封装为学生对象，方法的返回值为Student类型 

### <span id="head12"> </span>

#### <span id="head13">4.3.1需求说明 </span>

+ 添加老师: 通过键盘录入老师信息(id，name，age，birthday)

  使用数组存储老师信息,要求老师的id不能重复

+ 删除老师: 通过键盘录入要删除老师的id值,将该老师从数组中移除,如果录入的id在数组中不存在,需要重新录入

+ 修改老师: 通过键盘录入要修改老师的id值和修改后的老师信息

  将数组中该老师的信息修改,如果录入的id在数组中不存在,需要重新录入

+ 查询老师: 将数组中存储的所有老师的信息输出到控制台

#### <span id="head14">4.3.2实现步骤 </span>

+ 环境搭建实现步骤

  | 包                                       | 存储的类                                     | 作用                   |
  | --------------------------------------- | ---------------------------------------- | -------------------- |
  | com.itheima.edu.info.manager.domain     | Student.java   Teacher.java              | 封装学生信息  封装老师信息       |
  | com.itheima.edu.info.manager.dao        | StudentDao.java  TeacherDao.java         | 访问存储数据的数组,进行赠删改查（库管） |
  | com.itheima.edu.info.manager.service    | StudentService.java  TeacherService.java | 业务的逻辑处理（业务员）         |
  | com.itheima.edu.info.manager.controller | StudentController.java  TeacherController.java | 和用户打交道（客服接待）         |
  | com.itheima.edu.info.manager.entry      | InfoManagerEntry.java                    | 程序的入口类,提供一个main方法    |

+ 菜单搭建实现步骤

  1. 展示欢迎页面,用输出语句完成主界面的编写
  2. 获取用户的选择,用Scanner实现键盘录入数据
  3. 根据用户的选择执行对应的操作,用switch语句完成操作的选择

+ 添加功能实现步骤

  ![10_添加老师功能实现步骤](.\img\10_添加老师功能实现步骤.png)

+ 查询功能实现步骤

  ![11_查询老师功能实现步骤](.\img\11_查询老师功能实现步骤.png)

+ 删除功能实现步骤

  ![12_删除老师功能实现步骤](.\img\12_删除老师功能实现步骤.png)

+ 修改功能实现步骤

  ![13_修改老师功能实现步骤](.\img\13_修改老师功能实现步骤.png)

+ 系统优化

  + 把updateTeacher和deleteTeacherById中录入老师id代码抽取到一个方法(inputTeacherId)中
    该方法的主要作用就是录入老师的id,方法的返回值为String类型
  + 把addTeacher和updateTeacher中录入老师信息的代码抽取到一个方法(inputTeacherInfo)中
    该方法的主要作用就是录入老师的信息,并封装为老师对象,方法的返回值为Teacher类型

## <span id="head15"> static关键字</span>

### <span id="head16">5.1static关键字概述 (理解)</span>

static 关键字是静态的意思,是Java中的一个修饰符,可以修饰成员方法,成员变量

### <span id="head17">5.2static修饰的特点 (记忆) </span>

+ **被类的所有对象共享**，是我们判断是否使用静态关键字的条件

+ **随着类的加载而加载，优先于对象存在**，对象需要类被加载后，才能创建，**可以通过类名调用**，也可以通过对象名调用


### <span id="head18">5.3static关键字注意事项 (理解)</span>

+ **静态方法中是没有this关键字** 
+ **静态方法只能访问静态的成员**，非静态方法可以访问静态的成员，也可以访问非静态的成员