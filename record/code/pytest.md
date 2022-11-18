# 执行命令

- 测试文件以 `test_` 开头（以 `_test` 结尾也可以）
- 测试类以 `Test` 开头，并且不能带有 init 方法
- 测试函数以 `test_` 开头
- 断言使用基本的 `assert` 即可

```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-
def add(x, y):
    return x + y

def test_add():
    assert add(1, 10) == 11
    assert add(1, 1) == 2
    assert add(1, 99) == 100

class TestClass:
    def test_one(self):
        x = "this"
        assert "h" in x

    def test_two(self):
        x = "hello"
        assert hasattr(x, "check")  
        
       
# 命令行进入到这个文件所在的路径，可以直接使用 pytest 命令运行，pytest 会找当前目录以及递查找子目录下所有的 `test_*.py` 或 `*_test.py` 的文件，把其当作测试文件。在这些文件里，pytest 会收集符合编写规范的函数，类以及方法，当作测试用例并且执行

# -v
# 打印详细运行日志信息，一般在调试的时候加上这个参数，终端会打印出每条用例的详细日志信息，方便定位问题。

# -s
# 如果想在运行结果中打印 print 输出的代码

# -k
# 跳过运行某个或者某些用例。
# pytest -k '类名' pytest -k '方法名' pytest -k '类名 and not 方法名' //运行类里所有的方法，不包含某个方法

# -x
# 遇到用例失败立即停止运行。

#  --maxfail=[num]
# 用例失败个数达到阀值停止运行。具体用法：

# -m
# 将运行有 @pytest.mark.[标记名] 这个标记的测试用例。
# 比如这个版本只想验证登录功能，那就在所有登录功能的测试用例方法上面加上装饰符 @pytest.mark.login




```

#  setup，teardown

执行用例前后会执行 setup，teardown 来增加用例的前置和后置条件。pytest 框架中使用 setup，teardown 更灵活，按照用例运行级别可以分为以下几类：

- 模块级（setup_module/teardown_module）在模块始末调用
- 函数级（setup_function/teardown_function）在函数始末调用(在类外部）
- 类级（setup_class/teardown_class）在类始末调用（在类中）
- 方法级（setup_method/teardown_methond）在方法始末调用（在类中）
- 方法级（setup/teardown）在方法始末调用（在类中）

setup_module > setup_class >setup_method > setup > teardown > teardown_method > teardown_class > teardown_module

```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-

def setup_module():
    print("\nsetup_module，只执行一次，当有多个测试类的时候使用")

def teardown_module():
    print("\nteardown_module，只执行一次，当有多个测试类的时候使用")

class TestPytest1(object):

    @classmethod
    def setup_class(cls):
        print("\nsetup_class1，只执行一次")

    @classmethod
    def teardown_class(cls):
        print("\nteardown_class1，只执行一次")

    def setup_method(self):
        print("\nsetup_method1，每个测试方法都执行一次")

    def teardown_method(self):
        print("teardown_method1，每个测试方法都执行一次")

    def test_three(self):
        print("test_three，测试用例")

    def test_four(self):
        print("test_four，测试用例")

class TestPytest2(object):

    @classmethod
    def setup_class(cls):
        print("\nsetup_class2，只执行一次")

    @classmethod
    def teardown_class(cls):
        print("\nteardown_class2，只执行一次")

    def setup_method(self):
        print("\nsetup_method2，每个测试方法都执行一次")

    def teardown_method(self):
        print("teardown_method2，每个测试方法都执行一次")

    def test_two(self):
        print("test_two，测试用例")

    def test_one(self):
        print("test_one，测试用例")
```

pytest 加载所有的测试用例是乱序的，如果想指定用例的顺序，可以使用 pytest-order 插件，指定用例的执行顺序只需要在测试用例的方法前面加上装饰器 `@pytest.mark.run(order=[num])` 设置order的对应的num值，它就可以按照 num 的大小顺序来执行。

```python
pip install pytest-ordering

import pytest

class TestPytest(object):

    @pytest.mark.run(order=-1)
    def test_two(self):
        print("test_two，测试用例")

    @pytest.mark.run(order=3)
    def test_one(self):
        print("test_one，测试用例")

    @pytest.mark.run(order=1)
    def test_three(self):
        print("\ntest_three，测试用例")

```

使用 `@pytest.fixture` 装饰器来装饰一个方法，被装饰方法的方法名可以作为一个参数传入到测试方法中。可以使用这种方式来完成测试之前的初始化，也可以返回数据给测试函数。

# fixture

如果有这样一个场景，测试用例 1 需要依赖登录功能，测试用例 2 不需要登录功能，测试用例 3 需要登录功能。这种场景 setup，teardown 无法实现，可以使用 pytest fixture 功能，在方法前面加个 `@pytest.fixture` 装饰器，加了这个装饰器的方法可以以参数的形式传入到方法里面执行。

例如在登录的方法，加上 `@pytest.fixture` 这个装饰器后，将这个用例方法名以参数的形式传到方法里，这个方法就会先执行这个登录方法，再去执行自身的用例步骤，如果没有传入这个登录方法，就不执行登录操作，直接执行已有的步骤。

```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-
import pytest

@pytest.fixture()
def login():
    print("这是个登录方法")
    return ('tom','123')

@pytest.fixture()
def operate():
    print("登录后的操作")

def test_case1(login,operate):
    print(login)
    print("test_case1，需要登录")

def test_case2():
    print("test_case2，不需要登录 ")

def test_case3(login):
    print(login)
    print("test_case3，需要登录")
```



## 指定范围共享

fixture 里面有一个参数 scope，通过 scope 可以控制 fixture 的作用范围，根据作用范围大小划分：session> module> class> function，具体作用范围如下：

- function 函数或者方法级别都会被调用(默认)
- class 类级别调用一次
- module 模块级别调用一次
- session 是多个文件调用一次(可以跨.py文件调用，每个.py文件就是module)

例如整个模块有多条测试用例，需要在全部用例执行之前打开浏览器，全部执行完之后去关闭浏览器，打开和关闭操作只执行一次，如果每次都重新执行打开操作，会非常占用系统资源。这种场景除了setup_module,teardown_module 可以实现,还可以通过设置模块级别的 fixture 装饰器(@pytest.fixture(scope="module"))来实现。

```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-
import pytest

# 作用域：module是在模块之前执行， 模块之后执行
@pytest.fixture(scope="module")
def open():
    print("打开浏览器")
    yield

    print("执行teardown !")
    print("最后关闭浏览器")

@pytest.mark.usefixtures("open")
def test_search1():
    print("test_search1")
    raise NameError
    pass

def test_search2(open):
    print("test_search2")
    pass

def test_search3(open):
    print("test_search3")
    pass


```

@pytest.fixture() 如果不写参数，参数默认 `scope='function'`。当 `scope='module'` 时，在当前 `.py` 脚本里面所有的用例开始前只执行一次。scope 巧妙与 yield 组合使用，相当于 setup 和 teardown 方法。还可以使用 `@pytest.mark.usefixtures` 装饰器，传入前置函数名作为参数。



如果多个用例只需调用一次 fixture，可以将 `scope='session'`，并且写到 `conftest.py` 文件里。写到 `conftest.py` 文件可以全局调用这里面的方法。使用的时候不需要导入 `conftest.py` 这个文件。使用 `conftest.py` 的规则：

1. `conftest.py` 这个文件名是固定的，不可以更改。
2. `conftest.py` 与运行用例在同一个包下，并且该包中有 `**init**.py` 文件
3. 使用的时候不需要导入 `conftest.py`，pytest 会自动识别到这个文件
4. 放到项目的根目录下可以全局调用，放到某个 package 下，就在这个 package 内有效。

![quicker_62d04f35-09e6-4124-a3dc-1f051189bf08.png](https://s2.loli.net/2022/03/08/GJonFTPwdHM24ir.png)

打开 cmd，进入目录 test_scope/，执行如下命令：

```
pytest -v -s  
```



在装饰器里面添加一个参数 `autouse='true'`，它会自动应用到所有的测试方法中，只是这里没有办法返回值给测试用例。

```python
# coding=utf-8
import pytest

@pytest.fixture(autouse="true")
def myfixture():
    print("this is my fixture")


class TestAutoUse:
    def test_one(self):
        print("执行test_one")
        assert 1 + 2 == 3

    def test_two(self):
        print("执行test_two")
        assert 1 == 1

    def test_three(self):
        print("执行test_three")
        assert 1 + 1 == 2
```

## 传参

我们在测试过程中会将测试用到的数据以参数的形式传入到测试用例中，并为每条测试数据生成一个测试结果数据。

这时候可以使用 fixture 的参数化功能，在 fixture 方法加上装饰器 `@pytest.fixture(params=[1,2,3])`，就会传入三个数据 1、2、3，分别将这三个数据传入到用例当中。这里可以传入的数据是个列表。传入的数据需要使用一个固定的参数名 `request` 来接收。

```python
import pytest

@pytest.fixture(params=[1, 2, 3])
def data(request):
    return request.param

def test_not_2(data):
    print(f"测试数据：{data}")
    assert data < 5
```



## 分布式

`pytest-xdist` 是 pytest 分布式执行插件，可以多个 CPU 或主机执行，这款插件允许用户将测试并发执行（进程级并发）,插件是动态决定测试用例执行顺序的，为了保证各个测试能在各个独立线程里正确的执行，**应该保证测试用例的独立性**（这也符合测试用例设计的最佳实践）。

```shell
pip install pytest-xdist
#多个 CPU 并行执行用例，需要在 pytest 后面添加 -n 参数，如果参数为 auto，会自动检测系统的 CPU 数目。如果参数为数字，则指定运行测试的处理器进程数。

pytest -n auto   
pytest -n [num]  
```

# 测试报告



```
pip install pytest-html

pytest --html=path/to/html/report.html
```

**结合 pytest-xdist 使用**

```
pytest -v -s -n 3 --html=report.html --self-contained-html 
```

报告会生成在运行脚本的同一路径，需要指定路径添加--html=path/to/html/report.html 这个参数配置报告的路径。如果不添加 `--self-contained-html` 这个参数，生成报告的 CSS 文件是独立的，分享的时候容易千万数据丢失。



# parametrize

parametrize( ) 方法源码：

```
def parametrize(self,argnames, argvalues, indirect=False, ids=None, \
    scope=None):
```

- 主要参数说明

- argsnames ：参数名，是个字符串，如中间用逗号分隔则表示为多个参数名

- argsvalues ：参数值，参数组成的列表，列表中有几个元素，就会生成几条用例

  

  

  使用方法

- 使用 `@pytest.mark.paramtrize()` 装饰测试方法

- `parametrize('data', param)` 中的 “data” 是自定义的参数名，param 是引入的参数列表

- 将自定义的参数名 data 作为参数传给测试用例 test_func

- 然后就可以在测试用例内部使用 data 的参数了

创建测试用例，传入三组参数，每组两个元素，判断每组参数里面表达式和值是否相等，代码如下：

```python
@pytest.mark.parametrize("test_input,expected",[("3+5",8),("2+5",7),("7*5",30)])
def test_eval(test_input,expected):
    # eval 将字符串str当成有效的表达式来求值，并返回结果
    assert eval(test_input) == expected
```

整个执行过程中，pytest 将参数列表 `[("3+5",8),("2+5",7),("7*5",30)]` 中的三组数据取出来，每组数据生成一条测试用例，并且将每组数据中的两个元素分别赋值到方法中，作为测试方法的参数由测试用例使用。



同一个测试用例还可以同时添加多个 `@pytest.mark.parametrize` 装饰器, 多个 parametrize 的所有元素互相组合（类似笛卡儿乘积），生成大量测试用例。

场景：比如登录场景，用户名输入情况有 n 种，密码的输入情况有 m 种，希望验证用户名和密码，就会涉及到 n*m 种组合的测试用例，如果把这些数据一一的列出来，工作量也是非常大的。pytest 提供了一种参数化的方式，将多组测试数据自动组合，生成大量的测试用例。示例代码如下：

```python
@pytest.mark.parametrize("x",[1,2])
@pytest.mark.parametrize("y",[8,10,11])
def test_foo(x,y):
    print(f"测试数据组合x: {x} , y:{y}")
```

测试方法 test_foo( ) 添加了两个 `@pytest.mark.parametrize()` 装饰器，两个装饰器分别提供两个参数值的列表，`2 * 3 = 6` 种结合，pytest 便会生成 6 条测试用例。在测试中通常使用这种方法是所有变量、所有取值的完全组合，可以实现全面的测试。



如果测试数据需要在 fixture 方法中使用，同时也需要在测试用例中使用，可以在使用 parametrize 的时候添加一个参数 `indirect=True`，pytest 可以实现将参数传入到 fixture 方法中，也可以在当前的测试用例中使用。

parametrize 源码：

```
    def parametrize(self,argnames, argvalues, indirect=False, ids=None, scope=None):
```

indirect 参数设置为 True，pytest 会把 argnames 当作函数去执行，将 argvalues 作为参数传入到 argnames 这个函数里。创建“test_param.py”文件，代码如下：

```python
# 方法名作为参数
test_user_data = ['Tome', 'Jerry']
@pytest.fixture(scope="module")
def login_r(request):
    # 通过request.param获取参数
    user = request.param
    print(f"\n 登录用户：{user}")
    return user

@pytest.mark.parametrize("login_r", test_user_data,indirect=True)
def test_login(login_r):
    a = login_r
    print(f"测试用例中login的返回值; {a}")
    assert a != ""
    
    
"""    
plugins: html-2.0.1, rerunfailures-8.0, xdist-1.31.0, ordering-0.6,\
 forked-1.1.3, allure-pytest-2.8.11, metadata-1.8.0
collecting ... collected 2 items

test_mark_paramize.py::test_login[Tome] 
test_mark_paramize.py::test_login[Jerry] 

============================== 2 passed in 0.02s ===============================

Process finished with exit code 0

 登录用户：Tome PASSED           [ 50%]测试用例中login的返回值; Tome


 登录用户：Jerry PASSED           [100%]测试用例中login的返回值; Jerry    
 """
```

当 `indirect=True` 时，会将 `login_r` 作为方法，test_user_data 被当作参数传入到 `login_r` 方法中，生成多条测试用例。通过 return 将结果返回，当调用 `login_r` 可以获取到 `login_r` 这个方法的返回数据。



# yaml

用 `yaml.safe_dump()` 和 `yaml.safe_load()` 函数将 Python 值和 YAML 格式数据相互转换

当数据量非常大的时候，我们可以将数据存放到外部文件中，使用的时候将文件中的数据读取出来，方便测试数据的管理。

```shell
pip install PyYAML

# 创建用例文件以及数据文件来完成数据驱动的测试案例，创建一个文件夹 testdata，在这个文件夹下创建 `data.yml` 和 `test_yaml.py` 文件。

# 创建 data.yml 文件：

-
  - 1
  - 2
-
  - 20
  - 30


# 创建“test_yaml.py”，代码如下：

import pytest
import yaml

@pytest.mark.parametrize("a,b", yaml.safe_load(open("datas.yml",\
encoding='utf-8')))
def test_foo(a,b):
    print(f"a + b = {a + b}")
```



# Allure

Allure 框架是一种灵活的、轻量级、支持多语言测试报告工具，它不仅能够以简洁的 Web 报告形式显示已测试的内容，而且允许参与开发过程的每个人从测试的日常执行中提取最大限度的有用信息。同时支持多种语言包括 Java、Python、JavaScript、Ruby、Groovy、PHP、.Net、 Scala。

```shell
# Mac 可以使用 brew 安装 allure，安装命令如下：
brew install allure 
# 其他操作系统请参考：
https://docs.qameta.io/allure/#_installing_a_commandline
# 与 pytest 结合需要安装 allure-pytest 插件：
pip install allure-pytest
# 查看 Allure 版本：
allure --version



# 第一步：在 pytest 执行测试的时候，指定参数 --alluredir 选项及结果数据保存的目录，代码如下：
pytest --alluredir=tmp/my_allure_results
# 第二步：打开报告，需要启动 allure 服务，在 terminal 中输入 allure serve [path/to/allure_results]，代码如下：
allure serve path/to/allure_results
# 也可以使用 allure generate 生成 HTML 格式的测试结果报告，并使用 allure open 来打开报告。
allure generate ./result/ -o ./report/ --clean
# 上面的命令将 ./result/ 目录下的测试数据生成HTML测试报告到 ./report 路径下，-–clean 选项目的是先清空测试报告目录，再生成新的测试报告，然后使用下面的命令打开报告。
allure open -h 127.0.0.1 -p 8883 ./report/ 
# 上面这个命令则会启动一个 Web 服务将已经生成的测试报告打开。
```



# 模拟百度搜索功能场景，

这里需要创建两个文件，数据文件与用例文件。首先创建数据管理文件  `data/data.yml`，代码如下：

```
- allure
- pytest
- unittest
```

然后，创建用例文件名为 `test_baidudemo.py`，代码如下：

```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-

import allure
import pytest
import yaml
from selenium import webdriver
import time

@allure.testcase("http://www.github.com")
@allure.feature("百度搜索")
@pytest.mark.parametrize('test_data1', yaml.safe_load(open("data/data.yml")))
def test_steps_demo(test_data1):
    with allure.step("打开百度网页"):
        driver = webdriver.Chrome()
        driver.get("http://www.baidu.com")
        driver.maximize_window()

    with allure.step(f"输入搜索词：{test_data1}"):
        driver.find_element_by_id("kw").send_keys(test_data1)
        time.sleep(2)
        driver.find_element_by_id("su").click()
        time.sleep(2)

    with allure.step("保存图片"):
        driver.save_screenshot("./result/b.png")
        allure.attach.file("./result/b.png", \
        attachment_type=allure.attachment_type.PNG)
    with allure.step("关闭浏览器"):
        driver.quit()
        
        
"""
代码解析：
allure.testcase 用例标识，给定用例的链接，可以与用例的管理地址关联。
allure.feature 功能模块划分，方便管理和运行测试用例。
pytest.mark.parametrize 用来参数化测试用例。
allure.step 用来添加测试步骤，在测试报告里面会展示出来这个步骤说明。
执行：
 pytest test_baidudemo.py -s -q --alluredir=./result/

 allure serve ./result/
 """
```





