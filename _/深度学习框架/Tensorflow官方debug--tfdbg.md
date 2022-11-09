- [## tfgdb常用命令总结](#head1)
example code：
```
import numpy as np
import tensorflow as tf
from tensorflow.python import debug as tf_debug
 
xs = np.linspace(-0.5, 0.49, 100)
x = tf.placeholder(tf.float32, shape=[None], name="x")
y = tf.placeholder(tf.float32, shape=[None], name="y")
k = tf.Variable([0.0], name="k")
y_hat = tf.multiply(k, x, name="y_hat")
sse = tf.reduce_sum((y - y_hat) * (y - y_hat), name="sse")
train_op = tf.train.GradientDescentOptimizer(learning_rate=0.02).minimize(sse)
 
sess = tf.Session()
sess.run(tf.global_variables_initializer())
 
sess = tf_debug.LocalCLIDebugWrapperSession(sess,ui_type="readline")
for _ in range(10):
	sess.run(y_hat,feed_dict={x:xs,y:10*xs})
	sess.run(train_op, feed_dict={x: xs, y: 42 * xs})
```
guidance：
```
第一步，启动Python调试，
$> python tfdbgdemo.py –debug
 
...
TTTTTT FFFF DDD  BBBB   GGG
  TT   F    D  D B   B G
  TT   FFF  D  D BBBB  G  GG
  TT   F    D  D B   B G   G
  TT   F    DDD  BBBB   GGG
 
TensorFlow version: 1.10.0
 
======================================
Session.run() call #1:
 
Fetch(es):
  y_hat:0
 
Feed dict:
  x:0
  y:0
======================================
 
Select one of the following commands to proceed ---->
  run:
    Execute the run() call with debug tensor-watching
  run -n:
    Execute the run() call without debug tensor-watching
  run -t <T>:
    Execute run() calls (T - 1) times without debugging, then execute run() once more with debugging and drop back to the CLI
  run -f <filter_name>:
    Keep executing run() calls until a dumped tensor passes a given, registered filter (conditional breakpoint mode)
    Registered filter(s):
        * has_inf_or_nan
  invoke_stepper:
    Use the node-stepper interface, which allows you to interactively step through nodes involved in the graph run() call and inspect/modify their values
 
For more details, see help..
 
 
第二步，运行程序
tfdbg> run
run-end: run #1: 1 fetch (y_hat:0); 2 feeds
3 dumped tensor(s):
 
t (ms)    Size (B) Op type    Tensor name
[0.000]   170      VariableV2 k:0
[33.354]  180      Identity   k/read:0
[55.531]  576      Mul        y_hat:0
 
 
第三步，打印想看的变量
tfdbg> pt y_hat:0
Tensor "y_hat:0:DebugIdentity":
  dtype: float32
  shape: (100,)
 
array([-0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0.,
       -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0.,
       -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0.,
       -0., -0., -0., -0., -0., -0., -0., -0., -0., -0., -0.,  0.,  0.,
        0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,
        0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,
        0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,
        0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.,  0.], dtype=float32)

```
运行时可能出现的问题
No module named _curses
首先这个问题产生的 根本原因 是 curses 库不支持 windows。所以我们在下载完成python后（python 是自带 curses 库的），虽然在  python目录\Lib  中可以看到 curses 库，但其实我们是不能使用的。会产生如上的错误。在提示的文件 __init__ 文件中也确实可以找到  from _curses import *  这句话。

要解决这个问题，我们就需要使用一个 unofficial curses（非官方curses库）来代替 python 自带的curses库。也就是 whl 包。

用我自己的例子，我下载的是 python3.5.1 版本，在  https://link.jianshu.com/?t=http://www.lfd.uci.edu/~gohlke/pythonlibs/#curses  python whl包下载 中找到 curses ，然后下载与自己python版本对应的 whl 包（如我的就是 curses-2.2-cp35-cp35m-win_amd64.whl），我是windows10-64bit，需要下载 amd64 的版本，但是具体下载哪个版本可以测试一下，只要经测试发现使用那个版本时在安装的时候会报一个环境不支持的错误就对了。

### <span id="head1">## tfgdb常用命令总结</span>

tfgdb使用步进制的方法运行，其实和gdb，pdb很像，每调用一次run函数就会停止并输出当前的tensor节点的值，以方便我们进行观察和调试。在界面中可以直接使用鼠标点击界面上方的菜单栏进行命令的执行，比如选中一个节点后，点击list_inputs就会显示该节点的输入列表。下面总结一下常用的几个命令的含义：

run：运行一次sess.run()。-t可以运行很多次。-n运行结束。-f运行到filter条件出现，比如nan，inf等

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-ca097f629b9f52b9?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

list_inputs：打印节点的输入信息，简写为li。常用的包括-d和-r两个参数，分别用来限制显示的层数和方式。

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-14f3c4df3234f105?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

list_outputs：打印节点的输出信息，简写为lo。后面常用的参数跟list_input一样。

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-302d1958603e4b30?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

list_tensors：展示所有的Tensor信息，简单可以理解为主界面，简写为lt。可以使用-f，-n，-t操作进行限制输出tensor所满足的条件。-s，-r用来控制显示信息排序或者倒序。

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-10bdb01b0b1330a2?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

node_info：打印节点信息，简写为ni。

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-f3f2bb510f055f7d?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

print_tensor：打印Tensor的值，简写为pt。

![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-e898c7d55d72a864?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

/ : 查找。譬如：/inf就是查找当前打印的值中的inf，并将其高亮显示。

此外还可以看一下官网上面给出的常用命令组合，如下图所示： 
![这里写图片描述](https://upload-images.jianshu.io/upload_images/15873283-66067590df98b1d1?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 

