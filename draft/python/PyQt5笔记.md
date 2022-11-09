原文[https://github.com/maicss/PyQt5-Chinese-tutorial/blob/master](https://github.com/maicss/PyQt5-Chinese-tutorial/blob/master)


# PyQt5是由一系列Python模块组成。超过620个类，6000函数和方法。能在诸如Unix、Windows和Mac OS等主流操作系统上运行。PyQt5有两种证书，GPL和商业证书。

PyQt5类分为很多模块，主要模块有：

- QtCore 包含了核心的非GUI的功能。主要和时间、文件与文件夹、各种数据、流、URLs、mime类文件、进程与线程一起使用。
- QtGui 包含了窗口系统、事件处理、2D图像、基本绘画、字体和文字类。
- QtWidgets类包含了一系列创建桌面应用的UI元素。
- QtMultimedia包含了处理多媒体的内容和调用摄像头API的类。
- QtBluetooth模块包含了查找和连接蓝牙的类。
- QtNetwork包含了网络编程的类，这些工具能让TCP/IP和UDP开发变得更加方便和可靠。
- QtPositioning包含了定位的类，可以使用卫星、WiFi甚至文本。
- Engine包含了通过客户端进入和管理Qt Cloud的类。
- QtWebSockets包含了WebSocket协议的类。
- QtWebKit包含了一个基WebKit2的web浏览器。
- QtWebKitWidgets包含了基于QtWidgets的WebKit1的类。
- QtXml包含了处理xml的类，提供了SAX和DOM API的工具。
- QtSvg提供了显示SVG内容的类，Scalable Vector Graphics (SVG)是一种是一种基于可扩展标记语言（XML），用于描述二维矢量图形的图形格式（这句话来自于维基百科）。
- QtSql提供了处理数据库的工具。
- QtTest提供了测试PyQt5应用的工具。





查看帮助文档可以发现，setMenuBar、addToolBar、setCentralWidget、setStatusBar几种行为只有QMainWindow类具有。
因此，在继承自QWidget类的用户类中无法创建菜单栏等几种行为。
这就是QMainWindow和QWidget的主要区别。



## 例1，简单的窗口
![image.png](https://upload-images.jianshu.io/upload_images/18339009-125496b7ee0d0d09.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python

# 每个PyQt5应用都必须创建一个应用对象。
    # sys.argv是一组命令行参数的列表。Python可以在shell里运行，这个参数提供对脚本控制的功能。
    app = QApplication(sys.argv)
    
    # QWidge控件是一个用户界面的基本控件，它提供了基本的应用构造器。
    # 默认情况下，构造器是没有父级的，没有父级的构造器被称为窗口（window）。
    w = QWidget()
    w.resize(250, 150)
    w.move(300, 300)
    w.setWindowTitle('Simple')
    w.show()
    # 最后，我们进入了应用的主循环中，事件处理器这个时候开始工作。主循环从窗口上接收事件，并把事件传入到派发到应用控件里。当调用`exit()`方法或直接销毁主控件时，主循环就会结束。`sys.exit()`方法能确保主循环安全退出。外部环境能通知主控件怎么结束。
    # 
    # `exec_()`之所以有个下划线，是因为`exec`是一个Python的关键字。
    sys.exit(app.exec_())
```


## 例2，带窗口图标

窗口图标通常是显示在窗口的左上角，标题栏的最左边。下面的例子就是怎么用PyQt5创建一个这样的窗口。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-fe5e863ff995c98b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



```python
class Example(QWidget):
    def __init__(self):
        super().__init__()
        # 设置位置及大小
        self.setGeometry(300, 300, 300, 220)
        self.setWindowTitle('Icon')
        self.setWindowIcon(QIcon('0.png'))
        self.show()
        
        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = Example()
    sys.exit(app.exec_())
```


## 例3，提示框
![image.png](https://upload-images.jianshu.io/upload_images/18339009-ff82b488388a6fa4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        # 这个静态方法设置了提示框的字体，我们使用了10px的SansSerif字体。
        QToolTip.setFont(QFont('SansSerif', 10))
        # 调用setTooltip()创建提示框可以使用富文本格式的内容。鼠标悬停会显示设置的内容
        self.setToolTip('This is a <b>QWidget</b> widget')
        
        # 创建一个按钮，并且为按钮添加了一个提示框。
        # 创建一个继承自`QPushButton`的按钮。第一个参数是按钮的文本，第二个参数是按钮的父级组件，这个例子中，父级组件就是我们创建的继承自`Qwidget`的`Example`类。
        btn = QPushButton('Button', self)
        btn.setToolTip('This is a <b>QPushButton</b> widget')
        btn.resize(btn.sizeHint())
        btn.move(50, 50)
```


## 例4，关闭窗口

关闭一个窗口最直观的方式就是点击标题栏的那个叉，这个例子里，我们展示的是如何用程序关闭一个窗口。这里我们将接触到一点single和slots的知识。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-5faab3c17bf0e610.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        qbtn = QPushButton('Quit', self)
        # 事件传递系统在PyQt5内建的single和slot机制里面。点击按钮之后，信号会被捕捉并给出既定的反应。`QCoreApplication`包含了事件的主循环，它能添加和删除所有的事件，`instance()`创建了一个它的实例。`QCoreApplication`是在`QApplication`里创建的。 点击事件和能终止进程并退出应用的quit函数绑定在了一起。在发送者和接受者之间建立了通讯，发送者就是按钮，接受者就是应用对象。
        qbtn.clicked.connect(QCoreApplication.instance().quit)
        qbtn.resize(qbtn.sizeHint())
        qbtn.move(50, 50)
```


## 例5，消息盒子

默认情况下，我们点击标题栏的×按钮，QWidget就会关闭。但是有时候，我们修改默认行为。比如，如果我们打开的是一个文本编辑器，并且做了一些修改，我们就会想在关闭按钮的时候让用户进一步确认操作。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-0138164ff6ea81a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('Message box')
        self.show()

    # 如果关闭QWidget，就会产生一个QCloseEvent，并且把它传入到closeEvent函数的event参数中。
    # 改变控件的默认行为，就是替换掉默认的事件处理。
    
    # 我们创建了一个消息框，上面有俩按钮：Yes和No.
    # 第一个字符串显示在消息框的标题栏，第二个字符串显示在对话框，第三个参数是消息框的俩按钮，
    # 最后一个参数是默认按钮，这个按钮是默认选中的。返回值在变量`reply`里。
    def closeEvent(self, event):
        reply = QMessageBox.question(self, 'Message',
                                     "Are you sure to quit?", QMessageBox.Yes |
                                     QMessageBox.No, QMessageBox.No)

        if reply == QMessageBox.Yes:
            event.accept()
        else:
            event.ignore()
```


## 例6，窗口居中

```python
self.resize(250, 150)
        self.center()

        self.setWindowTitle('Center')
        self.show()

    def center(self):

        # 获得主窗口所在的框架。
        qr = self.frameGeometry()
        # 获取显示器的分辨率，然后得到屏幕中间点的位置。
        cp = QDesktopWidget().availableGeometry().center()
        # 然后把主窗口框架的中心点放置到屏幕的中心位置。
        qr.moveCenter(cp)
        # 然后通过move函数把主窗口的左上角移动到其框架的左上角，这样就把窗口居中了。
        self.move(qr.topLeft())
```
# 1. 菜单和工具栏


## 主窗口
`QMainWindow`提供了主窗口的功能，使用它能创建一些简单的状态栏、工具栏和菜单栏。

主窗口是下面这些窗口的合称，所以教程在最下方。

## 状态栏
状态栏是用来显示应用的状态信息的组件。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-b1ffd0d7b9417db5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```python
import sys
from PyQt5.QtWidgets import QMainWindow, QApplication

class Example(QMainWindow):
    def __init__(self):
        super().__init__()
        self.statusBar().showMessage('Ready')  # 创建状态栏。第一次调用创建一个状态栏，返回一个状态栏对象。`showMessage()`方法在状态栏上显示一条信息。
        self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('Statusbar')


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = Example()
    ex.show()
    sys.exit(app.exec_())
```




## 菜单栏
菜单栏是非常常用的。是一组命令的集合（Mac OS下状态栏的显示不一样，为了得到最相似的外观，我们增加了一句`menubar.setNativeMenuBar(False)`)。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-b78996afa5ff325f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```python

        # QAction是菜单栏、工具栏或者快捷键的动作的组合。

        # 前面两行，我们创建了一个图标、一个exit的标签和一个快捷键组合，都执行了一个动作。
        exit_act = QAction(QIcon('0.png'), 'Exit', self)
        exit_act.setShortcut('Ctrl+Q')
        # 第三行，创建了一个状态栏，当鼠标悬停在菜单栏的选项时候，能显示当前状态
        exit_act.setStatusTip('Exit application')
        # 当执行这个指定的动作时，就触发了一个事件。这个事件跟QApplication的quit()行为相关联，所以这个动作就能终止这个应用。
        exit_act.triggered.connect(qApp.quit)

        self.statusBar()

        # menuBar()创建菜单栏。这里创建了一个菜单栏，并在上面添加了一个file菜单，并关联了点击退出应用的事件。
        menubar = self.menuBar()
        file_menu = menubar.addMenu('&File')
        file_menu.addAction(exit_act)

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('Toolbar')

```



## 子菜单
子菜单是嵌套在菜单里面的二级或者三级等的菜单。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-00447cb6f51b03ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```python
        menubar = self.menuBar()
        fileMenu = menubar.addMenu('File')

        # 使用QMenu创建一个Import菜单项。
        impMenu = QMenu('Import', self)
        # 使用addAction添加Import mail动作。
        impAct = QAction('Import mail', self)
        impMenu.addAction(impAct)

        newAct = QAction('New', self)
        fileMenu.addAction(newAct)
        fileMenu.addMenu(impMenu)
```



## 勾选菜单
下面是一个能勾选菜单的例子
![image.png](https://upload-images.jianshu.io/upload_images/18339009-c6350cfe15f0b059.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```python
# 本例创建了一个行为菜单。这个行为／动作能切换状态栏显示或者隐藏。
        self.statusbar = self.statusBar()
        self.statusbar.showMessage('Ready')

        menubar = self.menuBar()
        viewMenu = menubar.addMenu('View')

        # 用checkable选项创建一个能选中的菜单。
        viewStatAct = QAction('View statusbar', self, checkable=True)
        viewStatAct.setStatusTip('View statusbar')
        viewStatAct.setChecked(True)
        viewStatAct.triggered.connect(self.toggleMenu)

        viewMenu.addAction(viewStatAct)

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('Check menu')

    def toggleMenu(self, state):

        if state:
            self.statusbar.show()
        else:
            self.statusbar.hide()
```




## 右键菜单
右键菜单也叫弹出框（！？），是在某些场合下显示的一组命令。例如，Opera浏览器里，网页上的右键菜单里会有刷新，返回或者查看页面源代码。如果在工具栏上右键，会得到一个不同的用来管理工具栏的菜单。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-f5d412ab2e1d80d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 还是使用contextMenuEvent()方法实现这个菜单。
        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('Submenu')

    def contextMenuEvent(self, event):

        cmenu = QMenu(self)

        newAct = cmenu.addAction("New")
        opnAct = cmenu.addAction("Open")
        quitAct = cmenu.addAction("Quit")
        # 使用exec_()方法显示菜单。从鼠标右键事件对象中获得当前坐标。
        # mapToGlobal()方法把当前组件的相对坐标转换为窗口（window）的绝对坐标。
        action = cmenu.exec_(self.mapToGlobal(event.pos()))

        if action == quitAct:
            qApp.quit()
```


## 工具栏
菜单栏包含了所有的命令，工具栏就是常用的命令的集合。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-d87e7882c390b42c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 上面的例子中，我们创建了一个工具栏。这个工具栏只有一个退出应用的动作。
# 和上面的菜单栏差不多，这里使用了一个行为对象，这个对象绑定了一个标签，一个图标和一个快捷键。这些行为被触发的时候，会调用`QtGui.QMainWindow`的quit方法退出应用。
        exitAct = QAction(QIcon('0.png'), 'Exit', self)
        exitAct.setShortcut('Ctrl+Q')
        exitAct.triggered.connect(qApp.quit)

        self.toolbar = self.addToolBar('Exit')
        self.toolbar.addAction(exitAct)
```



## 主窗口

主窗口就是上面三种栏目的总称，现在我们把上面的三种栏在一个应用里展示出来。

![image.png](https://upload-images.jianshu.io/upload_images/18339009-ec734103e702d445.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```python
# 上面的代码创建了一个很经典的菜单框架，有右键菜单，工具栏和状态栏。
        
        # 这里创建了一个文本编辑区域，并把它放在QMainWindow的中间区域。这个组件或占满所有剩余的区域。
        textEdit = QTextEdit()
        self.setCentralWidget(textEdit)

        exitAct = QAction(QIcon('0.png'), 'Exit', self)
        exitAct.setShortcut('Ctrl+Q')
        exitAct.setStatusTip('Exit application')
        exitAct.triggered.connect(self.close)

        self.statusBar()

        menubar = self.menuBar()
        fileMenu = menubar.addMenu('&File')
        fileMenu.addAction(exitAct)

        toolbar = self.addToolBar('Exit')
        toolbar.addAction(exitAct)
```


# 布局管理

在一个GUI程序里，布局是一个很重要的方面。布局就是如何管理应用中的元素和窗口。有两种方式可以搞定：绝对定位和PyQt5的layout类

## 绝对定位

每个程序都是以像素为单位区分元素的位置，衡量元素的大小。所以我们完全可以使用绝对定位搞定每个元素和窗口的位置。但是这也有局限性：

* 元素不会随着我们更改窗口的位置和大小而变化。
* 不能适用于不同的平台和不同分辨率的显示器
* 更改应用字体大小会破坏布局
* 如果我们决定重构这个应用，需要全部计算一下每个元素的位置和大小

下面这个就是绝对定位的应用
![image.png](https://upload-images.jianshu.io/upload_images/18339009-dcfdecd56d94b674.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
import sys
from PyQt5.QtWidgets import QWidget, QLabel, QApplication
class Example(QWidget):
    def __init__(self):
        super().__init__()

        lbl1 = QLabel('Zetcode', self)
        # 我们使用move()方法定位了每一个元素，使用x、y坐标。x、y坐标的原点是程序的左上角。
        lbl1.move(15, 10)

        lbl2 = QLabel('tutorials', self)
        lbl2.move(35, 40)

        lbl3 = QLabel('for programmers', self)
        lbl3.move(55, 70)

        self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('Absolute')
        self.show()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = Example()
    sys.exit(app.exec_())
```



## 盒布局

使用盒布局能让程序具有更强的适应性。这个才是布局一个应用的更合适的方式。`QHBoxLayout`和`QVBoxLayout`是基本的布局类，分别是水平布局和垂直布局。

如果我们需要把两个按钮放在程序的右下角，创建这样的布局，我们只需要一个水平布局加一个垂直布局的盒子就可以了。再用弹性布局增加一点间隙。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-5a3c2dd2cd3dd783.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
okButton = QPushButton("OK")
        cancelButton = QPushButton("Cancel")

        # 创建一个水平布局，增加两个按钮和弹性空间。stretch函数在两个按钮前面增加了一些弹性空间。
        # 下一步我们把这些元素放在应用的右下角。
        hbox = QHBoxLayout()
        hbox.addStretch(1)
        hbox.addWidget(okButton)
        hbox.addWidget(cancelButton)

        #   为了布局需要，我们把这个水平布局放到了一个垂直布局盒里面。
        #   弹性元素会把所有的元素一起都放置在应用的右下角。
        vbox = QVBoxLayout()
        vbox.addStretch(1)
        vbox.addLayout(hbox)

        self.setLayout(vbox)
```


## 栅格布局

最常用的还是栅格布局了。这种布局是把窗口分为行和列。创建和使用栅格布局，需要使用QGridLayout模块。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-47432c498b2904f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 创建一个QGridLayout实例，并把它放到程序窗口里。
        grid = QGridLayout()
        self.setLayout(grid)

        # 这是我们将要使用的按钮的名称。
        names = ['Cls', 'Bck', '', 'Close',
                 '7', '8', '9', '/',
                 '4', '5', '6', '*',
                 '1', '2', '3', '-',
                 '0', '.', '=', '+']

        # 创建按钮位置列表。
        positions = [(i, j) for i in range(5) for j in range(4)]
        
        # 创建按钮，并使用addWidget()方法把按钮放到布局里面。
        for position, name in zip(positions, names):
            if name == '':
                continue
            button = QPushButton(name)
            grid.addWidget(button, *position)


```



## 制作提交反馈信息的布局

组件能跨列和跨行展示，这个例子里，我们就试试这个功能。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-9425dff552c78c1f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
 # 我们创建了一个有三个标签的窗口。两个行编辑和一个文版编辑。
        title = QLabel('Title')
        author = QLabel('Author')
        review = QLabel('Review')

        titleEdit = QLineEdit()
        authorEdit = QLineEdit()
        reviewEdit = QTextEdit()

        # 创建标签之间的空间。
        grid = QGridLayout()
        grid.setSpacing(10)

        grid.addWidget(title, 1, 0)
        grid.addWidget(titleEdit, 1, 1)
        grid.addWidget(author, 2, 0)
        grid.addWidget(authorEdit, 2, 1)
        grid.addWidget(review, 3, 0)
        grid.addWidget(reviewEdit, 3, 1, 5, 1)
```



# 事件和信号

## 事件
> signals and slots 被其他人翻译成信号和槽机制，(⊙o⊙)…我这里还是不翻译好了。

所有的应用都是事件驱动的。事件大部分都是由用户的行为产生的，当然也有其他的事件产生方式，比如网络的连接，窗口管理器或者定时器等。调用应用的exec_()方法时，应用会进入主循环，主循环会监听和分发事件。

在事件模型中，有三个角色：

- 事件源
- 事件
- 事件目标

事件源就是发生了状态改变的对象。事件是这个对象状态改变的内容。事件目标是事件想作用的目标。事件源绑定事件处理函数，然后作用于事件目标身上。

PyQt5处理事件方面有个signal and slot机制。Signals and slots用于对象间的通讯。事件触发的时候，发生一个signal，slot是用来被Python调用的（相当于一个句柄？这个词也好恶心，就是相当于事件的绑定函数）slot只有在事件触发的时候才能调用。

## Signals & slots
下面是signal & slot的演示
![image.png](https://upload-images.jianshu.io/upload_images/18339009-c73c093c0214111b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        
# 上面的例子中，显示了QtGui.QLCDNumber和QtGui.QSlider模块，我们能拖动滑块让数字跟着发生改变。
        lcd = QLCDNumber(self)
        sld = QSlider(Qt.Horizontal, self)

        vbox = QVBoxLayout()
        vbox.addWidget(lcd)
        vbox.addWidget(sld)

        self.setLayout(vbox)
        # 这里是把滑块的变化和数字的变化绑定在一起。
        # sender是信号的发送者，receiver是信号的接收者，slot是对这个信号应该做出的反应。
        sld.valueChanged.connect(lcd.display)
```


## 重构事件处理器

在PyQt5中，事件处理器经常被重写（也就是用自己的覆盖库自带的）。

```python
# 这个例子中，我们替换了事件处理器函数keyPressEvent()。
        self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('Event handler')
        self.show()
        
    def keyPressEvent(self, e):
        # 此时如果按下ESC键程序就会退出。
        if e.key() == Qt.Key_Escape:
            self.close()
```


## 事件对象

事件对象是用python来描述一系列的事件自身属性的对象。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-71eefc2a60728752.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        # 这个示例中，我们在一个组件里显示鼠标的X和Y坐标。
        grid = QGridLayout()
        grid.setSpacing(10)

        # X Y坐标显示在QLabel组件里
        x = 0
        y = 0
        self.text = "x: {0},  y: {1}".format(x, y)
        self.label = QLabel(self.text, self)
        grid.addWidget(self.label, 0, 0, Qt.AlignTop)

        # 事件追踪默认没有开启，当开启后才会追踪鼠标的点击事件。
        self.setMouseTracking(True)

        self.setLayout(grid)

        self.setGeometry(300, 300, 350, 200)
        self.setWindowTitle('Event object')
        self.show()

    def mouseMoveEvent(self, e):
        # e代表了事件对象。里面有我们触发事件（鼠标移动）的事件对象。
        # x()和y()方法得到鼠标的x和y坐标点，然后拼成字符串输出到QLabel组件里。
        x = e.x()
        y = e.y()
        text = "x: {0},  y: {1}".format(x, y)
        self.label.setText(text)
```
  

## 事件发送

有时候我们会想知道是哪个组件发出了一个信号，PyQt5里的`sender()`方法能搞定这件事。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-41c44ee9c00c7ed7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
btn1 = QPushButton("Button 1", self)
        btn1.move(30, 50)

        btn2 = QPushButton("Button 2", self)
        btn2.move(150, 50)
        # 这个例子里有俩按钮，buttonClicked()方法决定了是哪个按钮能调用sender()方法。
        # 两个按钮都和同一个slot绑定。
        btn1.clicked.connect(self.buttonClicked)
        btn2.clicked.connect(self.buttonClicked)

        self.statusBar()

        self.setGeometry(300, 300, 290, 150)
        self.setWindowTitle('Event sender')
        self.show()

    def buttonClicked(self):
        # 我们用调用sender()方法的方式决定了事件源。状态栏显示了被点击的按钮。
        sender = self.sender()
        self.statusBar().showMessage(sender.text() + ' was pressed')
```


## 信号发送

`QObject`实例能发送事件信号。下面的例子是发送自定义的信号。

```python
# 我们创建了一个叫closeApp的信号，这个信号会在鼠标按下的时候触发，事件与QMainWindow绑定。
class Communicate(QObject):
    # Communicate类创建了一个pyqtSignal()属性的信号。
    closeApp = pyqtSignal()


class Example(QMainWindow):

    def __init__(self):
        super().__init__()

        # closeApp信号QMainWindow的close()方法绑定。
        self.c = Communicate()
        self.c.closeApp.connect(self.close)

        self.setGeometry(300, 300, 290, 150)
        self.setWindowTitle('Emit signal')
        self.show()

    def mousePressEvent(self, event):
        
        # 点击窗口的时候，发送closeApp信号，程序终止。
        self.c.closeApp.emit()
```


# 对话框

对话框是一个现代GUI应用不可或缺的一部分。对话是两个人之间的交流，对话框就是人与电脑之间的对话。对话框用来输入数据，修改数据，修改应用设置等等。

## 输入文字

`QInputDialog`提供了一个简单方便的对话框，可以输入字符串，数字或列表。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-ee678cbba6897ff7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        # 这个示例有一个按钮和一个输入框，点击按钮显示对话框，输入的文本会显示在输入框里。
        self.btn = QPushButton('Dialog', self)
        self.btn.move(20, 20)
        self.btn.clicked.connect(self.showDialog)

        self.le = QLineEdit(self)
        self.le.move(130, 22)

        self.setGeometry(300, 300, 290, 150)
        self.setWindowTitle('Input dialog')
        self.show()


    def showDialog(self):
        # 这是显示一个输入框的代码。第一个参数是输入框的标题，第二个参数是输入框的占位符。
        # 对话框返回输入内容和一个布尔值，如果点击的是OK按钮，布尔值就返回True。
        text, ok = QInputDialog.getText(self, 'Input Dialog',
                                        'Enter your name:')
        # 把得到的字符串放到输入框里。
        if ok:
            self.le.setText(str(text))
```


## 选取颜色

QColorDialog提供颜色的选择。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-a5cda97fb5f70963.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 例子里有一个按钮和一个QFrame，默认的背景颜色为黑色，我们可以使用QColorDialog改变背景颜色。

        self.btn = QPushButton('Dialog', self)
        self.btn.move(20, 20)

        self.btn.clicked.connect(self.showDialog)

        self.frm = QFrame(self)
        # 初始化QtGui.QFrame的背景颜色。
        col = QColor(0, 0, 0)
        self.frm.setStyleSheet("QWidget { background-color: %s }" % col.name())
        self.frm.setGeometry(130, 22, 100, 100)

        self.setGeometry(300, 300, 250, 180)
        self.setWindowTitle('Color dialog')
        self.show()

    def showDialog(self):
        # 弹出一个QColorDialog对话框。
        col = QColorDialog.getColor()
        # 我们可以预览颜色，如果点击取消按钮，没有颜色值返回，如果颜色是我们想要的，就从取色框里选择这个颜色。
        if col.isValid():
            self.frm.setStyleSheet("QWidget { background-color: %s }"
                                   % col.name())
```


## 选择字体

`QFontDialog`能做字体的选择。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-d55eda9b5acdcbc7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
# 我们创建了一个有一个按钮和一个标签的QFontDialog的对话框，我们可以使用这个功能修改字体样式。
        vbox = QVBoxLayout()

        btn = QPushButton('Dialog', self)
        btn.setSizePolicy(QSizePolicy.Fixed,
                          QSizePolicy.Fixed)
        btn.move(20, 20)
        btn.clicked.connect(self.showDialog)

        vbox.addWidget(btn)

        self.lbl = QLabel('Knowledge only matters', self)
        self.lbl.move(130, 20)
        vbox.addWidget(self.lbl)
        self.setLayout(vbox)

        self.setGeometry(300, 300, 250, 180)
        self.setWindowTitle('Font dialog')
        self.show()


    def showDialog(self):
        # 弹出一个字体选择对话框。getFont()方法返回一个字体名称和状态信息。状态信息有OK和其他两种。
        font, ok = QFontDialog.getFont()
        # 如果点击OK，标签的字体就会随之更改。
        if ok:
            self.lbl.setFont(font)
```

## 选择文件

`QFileDialog`给用户提供文件或者文件夹选择的功能。能打开和保存文件。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-06f34014ffa37219.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 本例中有一个菜单栏，一个置中的文本编辑框，一个状态栏。
# 点击菜单栏选项会弹出一个QtGui.QFileDialog对话框，在这个对话框里，你能选择文件，然后文件的内容就会显示在文本编辑框里。

        # 这里设置了一个文本编辑框，文本编辑框是基于QMainWindow组件的。
        self.textEdit = QTextEdit()
        self.setCentralWidget(self.textEdit)

        # 设置菜单、状态栏
        openFile = QAction(QIcon('0.png'), 'Open', self)
        openFile.setShortcut('Ctrl+O')
        self.statusBar()
        openFile.setStatusTip('Open new File')
        openFile.triggered.connect(self.showDialog)
        menubar = self.menuBar()
        fileMenu = menubar.addMenu('&File')
        fileMenu.addAction(openFile)

        self.setGeometry(300, 300, 350, 300)
        self.setWindowTitle('File dialog')
        self.show()


    def showDialog(self):
        # 弹出QFileDialog窗口。getOpenFileName()方法的第一个参数是说明文字，第二个参数是默认打开的文件夹路径。默认情况下显示所有类型的文件。
        fname = QFileDialog.getOpenFileName(self, 'Open file', '/home')
        # 读取选中的文件，并显示在文本编辑框内（但是打开HTML文件时，是渲染后的结果，汗）。
        if fname[0]:
            f = open(fname[0], 'r')
            with f:
                data = f.read()
                self.textEdit.setText(data)

```

# 控件

控件就像是应用这座房子的一块块砖。PyQt5有很多的控件，比如按钮，单选框，滑动条，复选框等等。在本章，我们将介绍一些很有用的控件：`QCheckBox`，`ToggleButton`，`QSlider`，`QProgressBar`和`QCalendarWidget`，`QPixmap`，`QLineEdit`，`QSplitter`，和`QComboBox`。。

## QCheckBox

`QCheckBox`组件有俩状态：开和关。通常跟标签一起使用，用在激活和关闭一些选项的场景。

![image.png](https://upload-images.jianshu.io/upload_images/18339009-60b95023b626b4ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 这个例子中，有一个能切换窗口标题的单选框。
        # 这个是QCheckBox的构造器。
        cb = QCheckBox('Show title', self)
        cb.move(20, 20)
        # 要设置窗口标题，我们就要检查单选框的状态。默认情况下，窗口没有标题，单选框未选中。
        cb.toggle()
        # 把changeTitle()方法和stateChanged信号关联起来。这样，changeTitle()就能切换窗口标题了。
        cb.stateChanged.connect(self.changeTitle)

        self.setGeometry(300, 300, 250, 150)
        self.setWindowTitle('QCheckBox')
        self.show()

    # 控件的状态是由changeTitle()方法控制的，如果空间被选中，我们就给窗口添加一个标题，如果没被选中，就清空标题。
    def changeTitle(self, state):
        if state == Qt.Checked:
            self.setWindowTitle('QCheckBox')
        else:
            self.setWindowTitle(' ')
```


## 切换按钮

切换按钮就是`QPushButton`的一种特殊模式。 它只有两种状态：按下和未按下。我们再点击的时候切换两种状态，有很多场景会使用到这个功能。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-cdb1ec99ce3e32d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 我们创建了一个切换按钮和一个QWidget，并把QWidget的背景设置为黑色。
# 点击不同的切换按钮，背景色会在红、绿、蓝之间切换（而且能看到颜色合成的效果，而不是单纯的颜色覆盖）。

        # 创建一个QPushButton，然后调用它的setCheckable()的方法就把这个按钮变成了切换按钮。
        redb = QPushButton('Red', self)
        redb.setCheckable(True)
        redb.move(10, 10)
        # 把点击信号和我们定义好的函数关联起来，这里是把点击事件转换成布尔值
        redb.clicked[bool].connect(self.setColor)

        greenb = QPushButton('Green', self)
        greenb.setCheckable(True)
        greenb.move(10, 60)
        greenb.clicked[bool].connect(self.setColor)

        blueb = QPushButton('Blue', self)
        blueb.setCheckable(True)
        blueb.move(10, 110)
        blueb.clicked[bool].connect(self.setColor)

        self.square = QFrame(self)
        self.square.setGeometry(150, 20, 100, 100)
        # 设置颜色为黑色。
        self.col = QColor(0, 0, 0)
        self.square.setStyleSheet("QWidget { background-color: %s }" %
                                  self.col.name())

        self.setGeometry(300, 300, 280, 170)
        self.setWindowTitle('Toggle button')
        self.show()


    def setColor(self, pressed):
        # 获取被点击的按钮。
        source = self.sender()

        if pressed:
            val = 255
        else:
            val = 0
        # 如果是标签为“red”的按钮被点击，就把颜色更改为预设好的对应颜色。
        if source.text() == "Red":
            self.col.setRed(val)
        elif source.text() == "Green":
            self.col.setGreen(val)
        else:
            self.col.setBlue(val)

        self.square.setStyleSheet("QFrame { background-color: %s }" %
                                  self.col.name())

```

## 滑块

`QSlider`是个有一个小滑块的组件，这个小滑块能拖着前后滑动，这个经常用于修改一些具有范围的数值，比文本框或者点击增加减少的文本框（spin box）方便多了。


```python
# 本例用一个滑块和一个标签展示。标签为一个图片，滑块控制标签（的值）。

        # 创建一个水平的QSlider。
        sld = QSlider(Qt.Horizontal, self)
        sld.setFocusPolicy(Qt.NoFocus)
        sld.setGeometry(30, 40, 100, 30)
        # 把valueChanged信号跟changeValue()方法关联起来。
        sld.valueChanged[int].connect(self.changeValue)

        # 创建一个QLabel组件并给它设置一个静音图标。
        self.label = QLabel(self)
        self.label.setPixmap(QPixmap('mute.png'))
        self.label.setGeometry(160, 40, 80, 30)

        self.setGeometry(300, 300, 280, 170)
        self.setWindowTitle('QSlider')
        self.show()


    def changeValue(self, value):
        # 根据音量值的大小更换标签位置的图片。这段代码是：如果音量为0，就把图片换成 mute.png。
        if value == 0:
            self.label.setPixmap(QPixmap('mute.png'))
        elif 0 < value <= 30:
            self.label.setPixmap(QPixmap('min.png'))
        elif 30 < value < 80:
            self.label.setPixmap(QPixmap('med.png'))
        else:
            self.label.setPixmap(QPixmap('max.png'))
```


## 进度条

进度条是用来展示任务进度的（我也不想这样说话）。它的滚动能让用户了解到任务的进度。`QProgressBar`组件提供了水平和垂直两种进度条，进度条可以设置最大值和最小值，默认情况是0~99。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-76a22cdde60a4813.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 我们创建了一个水平的进度条和一个按钮，这个按钮控制进度条的开始和停止。

        # 新建一个QProgressBar构造器。
        self.pbar = QProgressBar(self)
        self.pbar.setGeometry(30, 40, 200, 25)

        self.btn = QPushButton('Start', self)
        self.btn.move(40, 80)
        self.btn.clicked.connect(self.doAction)

        # 用时间控制进度条。
        self.timer = QBasicTimer()
        self.step = 0

        self.setGeometry(300, 300, 280, 170)
        self.setWindowTitle('QProgressBar')
        self.show()


    def timerEvent(self, e):
        # 每个QObject和又它继承而来的对象都有一个timerEvent()事件处理函数。
        # 为了触发事件，我们重载了这个方法。
        if self.step >= 100:
            self.timer.stop()
            self.btn.setText('Finished')
            return
        self.step = self.step + 1
        self.pbar.setValue(self.step)


    def doAction(self):
        # 里面的doAction()方法是用来控制开始和停止的。
        if self.timer.isActive():
            self.timer.stop()
            self.btn.setText('Start')
        else:
            # 调用start()方法加载一个时间事件。这个方法有两个参数：过期时间和事件接收者。
            self.timer.start(100, self)
            self.btn.setText('Stop')
```


## 日历

`QCalendarWidget`提供了基于月份的日历插件，十分简易而且直观。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-93b3e2f8a9d1bd66.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 这个例子有日期组件和标签组件组成，标签显示被选中的日期。

        vbox = QVBoxLayout(self)
        # 创建一个QCalendarWidget。
        cal = QCalendarWidget(self)
        cal.setGridVisible(True)
        # 选择一个日期时，QDate的点击信号就触发了，把这个信号和我们自己定义的showDate()方法关联起来。
        cal.clicked[QDate].connect(self.showDate)
        vbox.addWidget(cal)

        self.lbl = QLabel(self)
        date = cal.selectedDate()
        self.lbl.setText(date.toString())
        vbox.addWidget(self.lbl)

        self.setLayout(vbox)

        self.setGeometry(300, 300, 350, 300)
        self.setWindowTitle('Calendar')
        self.show()

    def showDate(self, date):
        # 使用selectedDate()方法获取选中的日期，然后把日期对象转成字符串，在标签里面显示出来。
        self.lbl.setText(date.toString())
```


## 图片

`QPixmap`是处理图片的组件。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-0772bea9d73d83e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 本例中，我们使用`QPixmap`在窗口里显示一张图片。

        hbox = QHBoxLayout(self)
        # 创建一个QPixmap对象，接收一个文件作为参数。
        pixmap = QPixmap("0.png")

        # 把QPixmap实例放到QLabel组件里。
        lbl = QLabel(self)
        lbl.setPixmap(pixmap)
        hbox.addWidget(lbl)
        self.setLayout(hbox)

        self.move(300, 200)
        self.setWindowTitle('Red Rock')
        self.show()

```

## 行编辑

`QLineEdit`组件提供了编辑文本的功能，自带了撤销、重做、剪切、粘贴、拖拽等功能。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-efbac129000b9f31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 例子中展示了一个编辑组件和一个标签，我们在输入框里键入的文本，会立即在标签里显示出来。

        self.lbl = QLabel(self)
        # 创建一个QLineEdit对象。
        qle = QLineEdit(self)
        qle.move(60, 100)
        self.lbl.move(60, 40)
        # 如果输入框的值有变化，就调用onChanged()方法。
        qle.textChanged[str].connect(self.onChanged)

        self.setGeometry(300, 300, 280, 170)
        self.setWindowTitle('QLineEdit')
        self.show()


    def onChanged(self, text):
        # 在onChanged()方法内部，我们把文本框里的值赋值给了标签组件，
        # 然后调用adjustSize()方法让标签自适应文本内容。
        self.lbl.setText(text)
        self.lbl.adjustSize()
```

## QSplitter

`QSplitter`组件能让用户通过拖拽分割线的方式改变子窗口大小的组件。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-33dbafee6e07d805.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 本例中我们展示用两个分割线隔开的三个`QFrame`组件。
        hbox = QHBoxLayout(self)

        # 为了更清楚的看到分割线，我们使用了设置好的子窗口样式。
        topleft = QFrame(self)
        topleft.setFrameShape(QFrame.StyledPanel)

        topright = QFrame(self)
        topright.setFrameShape(QFrame.StyledPanel)

        bottom = QFrame(self)
        bottom.setFrameShape(QFrame.StyledPanel)

        # 创建一个`QSplitter`组件，并在里面添加了两个框架。
        splitter1 = QSplitter(Qt.Horizontal)
        splitter1.addWidget(topleft)
        splitter1.addWidget(topright)

        # 也可以在分割线里面再进行分割。
        splitter2 = QSplitter(Qt.Vertical)
        splitter2.addWidget(splitter1)
        splitter2.addWidget(bottom)

        hbox.addWidget(splitter2)
        self.setLayout(hbox)

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('QSplitter')
        self.show()
```

## 下拉选框

`QComboBox`组件能让用户在多个选择项中选择一个。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-a5958002e5f3e43e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 本例包含了一个QComboBox和一个QLabel。下拉选择框有五个选项，都是Linux的发行版名称，标签内容为选定的发行版名称。

        # 创建一个QComboBox组件和五个选项。
        self.lbl = QLabel("Ubuntu", self)

        combo = QComboBox(self)
        combo.addItem("Ubuntu")
        combo.addItem("Mandriva")
        combo.addItem("Fedora")
        combo.addItem("Arch")
        combo.addItem("Gentoo")

        combo.move(50, 50)
        self.lbl.move(50, 150)

        # 在选中的条目上调用onActivated()方法。
        combo.activated[str].connect(self.onActivated)

        self.setGeometry(300, 300, 300, 200)
        self.setWindowTitle('QComboBox')
        self.show()


    def onActivated(self, text):
        # 在方法内部，设置标签内容为选定的字符串，然后设置自适应文本大小。
        self.lbl.setText(text)
        self.lbl.adjustSize()
```
# 拖拽

在GUI里，拖放是指用户点击一个虚拟的对象，拖动，然后放置到另外一个对象上面的动作。
拖放能让用户很直观的操作很复杂的逻辑。

一般情况下，我们可以拖放两种东西：数据和图形界面。把一个图像从一个应用拖放到另外一个应用上的实质是操作二进制数据。把一个表格从Firefox上拖放到另外一个位置 的实质是操作一个图形组。

## 简单的拖放
![image.png](https://upload-images.jianshu.io/upload_images/18339009-d4a21df52b4b5075.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```python
# 本例使用了`QLineEdit`和`QPushButton`。把一个文本从编辑框里拖到按钮上，更新按钮上的标签（文字）。

# 为了完成预定目标，我们要重构一些方法。首先用QPushButton上构造一个按钮实例。
class Button(QPushButton):
    def __init__(self, title, parent):
        super().__init__(title, parent)
        # 激活组件的拖拽事件。
        self.setAcceptDrops(True)

    # 首先，我们重构了dragEnterEvent()方法。设定好接受拖拽的数据类型（plain text）。
    def dragEnterEvent(self, e):
        if e.mimeData().hasFormat('text/plain'):
            e.accept()
        else:
            e.ignore()

    # 然后重构dropEvent()方法，更改按钮接受鼠标的释放事件的默认行为。
    def dropEvent(self, e):
        self.setText(e.mimeData().text())


class Example(QWidget):
    def __init__(self):
        super().__init__()
        # QLineEdit默认支持拖拽操作，所以我们只要调用setDragEnabled()方法使用就行了。
        edit = QLineEdit('', self)
        edit.setDragEnabled(True)
        edit.move(30, 65)

        button = Button("Button", self)
        button.move(190, 65)

        self.setWindowTitle('Simple drag and drop')
        self.setGeometry(300, 300, 300, 150)
```


## 拖放按钮组件

这个例子展示怎么拖放一个button组件。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-2ba6d98daef957fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python

# 例子中，窗口上有一个QPushButton组件。左键点击按钮，控制台就会输出press。右键可以点击然后拖动按钮。

# 从QPushButton继承一个Button类，然后重构QPushButton的两个方法:
# mouseMoveEvent()和mousePressEvent().mouseMoveEvent()是拖拽开始的事件。
class Button(QPushButton):

    def __init__(self, title, parent):
        super().__init__(title, parent)

    def mouseMoveEvent(self, e):
        # 我们只劫持按钮的右键事件，左键的操作还是默认行为。
        if e.buttons() != Qt.RightButton:
            return

        # 创建一个QDrag对象，用来传输MIME-based数据。
        mimeData = QMimeData()
        drag = QDrag(self)
        drag.setMimeData(mimeData)
        drag.setHotSpot(e.pos() - self.rect().topLeft())
        # 拖放事件开始时，用到的处理函数式start().
        dropAction = drag.exec_(Qt.MoveAction)

    def mousePressEvent(self, e):
        # 左键点击按钮，会在控制台输出“press”。
        # 注意，我们在父级上也调用了mousePressEvent()方法，不然的话，我们是看不到按钮按下的效果的。
        super().mousePressEvent(e)
        if e.button() == Qt.LeftButton:
            print('press')


class Example(QWidget):

    def __init__(self):
        super().__init__()

        self.setAcceptDrops(True)

        self.button = Button('Button', self)
        self.button.move(100, 65)

        self.setWindowTitle('Click or Move')
        self.setGeometry(300, 300, 280, 150)

    def dragEnterEvent(self, e):
        e.accept()

    def dropEvent(self, e):
        # 在dropEvent()方法里，我们定义了按钮按下后和释放后的行为，获得鼠标移动的位置，然后把按钮放到这个地方。
        position = e.pos()
        self.button.move(position)
        # 指定放下的动作类型为moveAction。
        e.setDropAction(Qt.MoveAction)
        e.accept()
```
# 绘图

PyQt5绘图系统能渲染矢量图像、位图图像和轮廓字体文本。一般会使用在修改或者提高现有组件的功能，或者创建自己的组件。使用PyQt5的绘图API进行操作。

绘图由`paintEvent()`方法完成，绘图的代码要放在`QPainter`对象的`begin()`和`end()`方法之间。是低级接口。

## 文本涂鸦

我们从画一些Unicode文本开始。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-d063c81bc7d293cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        self.text = "Лев Николаевич Толстой\nАнна Каренина"
        
        self.setGeometry(300, 300, 280, 170)
        self.setWindowTitle('Drawing text')
        self.show()

    def paintEvent(self, event):
        # QPainter是低级的绘画类。
        # 所有的绘画动作都在这个类的begin()和end()方法之间完成，绘画动作都封装在drawText()内部了。
        qp = QPainter()
        qp.begin(self)
        self.drawText(event, qp)
        qp.end()

    def drawText(self, event, qp):
        # 为文字绘画定义了笔和字体。
        qp.setPen(QColor(168, 34, 3))
        qp.setFont(QFont('Decorative', 10))
        # drawText()方法在窗口里绘制文本，rect()方法返回要更新的矩形区域。
        qp.drawText(event.rect(), Qt.AlignCenter, self.text)

```


## 点的绘画

点是最简单的绘画了。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-fa847503a6e0bf47.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
self.setGeometry(300, 300, 300, 190)
        self.setWindowTitle('Points')
        self.show()

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        self.drawPoints(qp)
        qp.end()

    def drawPoints(self, qp):
        # 设置笔的颜色为红色，使用的是预定义好的颜色。
        qp.setPen(Qt.red)
        # 每次更改窗口大小，都会产生绘画事件，从size()方法里获得当前窗口的大小，然后把产生的点随机的分配到窗口的所有位置上。
        size = self.size()

        for i in range(1000):
            x = random.randint(1, size.width()-1)
            y = random.randint(1, size.height()-1)
            qp.drawPoint(x, y)

```


# 颜色

颜色是一个物体显示的RGB的混合色。RBG值的范围是0\~255。我们有很多方式去定义一个颜色，最常见的方式就是RGB和16进制表示法，也可以使用RGBA，增加了一个透明度的选项，透明度值的范围是0~1，0代表完全透明。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-ae2418f9fa383346.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
        self.setGeometry(300, 300, 350, 100)
        self.setWindowTitle('Colours')
        self.show()

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        self.drawRectangles(qp)
        qp.end()

    def drawRectangles(self, qp):
        # 使用16进制的方式定义一个颜色。
        col = QColor(0, 0, 0)
        col.setNamedColor('#d4d4d4')
        qp.setPen(col)
        # 定义了一个笔刷，并画出了一个矩形。笔刷是用来画一个物体的背景。
        # drawRect()有四个参数，分别是矩形的x、y、w、h。 然后用笔刷和矩形进行绘画。
        qp.setBrush(QColor(200, 0, 0))
        qp.drawRect(10, 15, 90, 60)

        qp.setBrush(QColor(255, 80, 0, 160))
        qp.drawRect(130, 15, 90, 60)

        qp.setBrush(QColor(25, 0, 90, 200))
        qp.drawRect(250, 15, 90, 60)


```


## QPen

`QPen`是基本的绘画对象，能用来画直线、曲线、矩形框、椭圆、多边形和其他形状。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-5ac003de5cf62ed8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 在这个例子里，我们用不同的笔画了6条直线。PyQt5有五个预定义的笔，另外一个笔的样式使我们自定义的。
        self.setGeometry(300, 300, 280, 270)
        self.setWindowTitle('Pen styles')
        self.show()

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        self.drawLines(qp)
        qp.end()

    def drawLines(self, qp):
        # 新建一个QPen对象，设置颜色黑色，宽2像素，这样就能看出来各个笔样式的区别。Qt.SolidLine是预定义样式的一种。
        pen = QPen(Qt.black, 2, Qt.SolidLine)
        qp.setPen(pen)
        qp.drawLine(20, 40, 250, 40)

        pen.setStyle(Qt.DashLine)
        qp.setPen(pen)
        qp.drawLine(20, 80, 250, 80)

        pen.setStyle(Qt.DashDotLine)
        qp.setPen(pen)
        qp.drawLine(20, 120, 250, 120)

        pen.setStyle(Qt.DotLine)
        qp.setPen(pen)
        qp.drawLine(20, 160, 250, 160)

        pen.setStyle(Qt.DashDotDotLine)
        qp.setPen(pen)
        qp.drawLine(20, 200, 250, 200)
        
        # 这里我们自定义了一个笔的样式。定义为Qt.CustomDashLine然后调用setDashPattern()方法。
        # 数字列表是线的样式，要求必须是个数为奇数，奇数位定义的是空格，偶数位为线长，数字越大，
        # 空格或线长越大，比如本例的就是1像素线，4像素空格，5像素线，4像素空格。
        pen.setStyle(Qt.CustomDashLine)
        pen.setDashPattern([1, 4, 5, 4])
        qp.setPen(pen)
        qp.drawLine(20, 240, 250, 240)

```


## QBrush

`QBrush`也是图像的一个基本元素。是用来填充一些物体的背景图用的，比如矩形，椭圆，多边形等。有三种类型：预定义、渐变和纹理。

```python

self.setGeometry(300, 300, 355, 280)
        self.setWindowTitle('Brushes')
        self.show()

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        self.drawBrushes(qp)
        qp.end()

    def drawBrushes(self, qp):
        # 创建了一个笔刷对象，添加笔刷样式，然后调用drawRect()方法画图。
        brush = QBrush(Qt.SolidPattern)
        qp.setBrush(brush)
        qp.drawRect(10, 15, 90, 60)

        brush.setStyle(Qt.Dense1Pattern)
        qp.setBrush(brush)
        qp.drawRect(130, 15, 90, 60)

        brush.setStyle(Qt.Dense2Pattern)
        qp.setBrush(brush)
        qp.drawRect(250, 15, 90, 60)

        brush.setStyle(Qt.DiagCrossPattern)
        qp.setBrush(brush)
        qp.drawRect(10, 105, 90, 60)

        brush.setStyle(Qt.Dense5Pattern)
        qp.setBrush(brush)
        qp.drawRect(130, 105, 90, 60)

        brush.setStyle(Qt.Dense6Pattern)
        qp.setBrush(brush)
        qp.drawRect(250, 105, 90, 60)

        brush.setStyle(Qt.HorPattern)
        qp.setBrush(brush)
        qp.drawRect(10, 195, 90, 60)

        brush.setStyle(Qt.VerPattern)
        qp.setBrush(brush)
        qp.drawRect(130, 195, 90, 60)

        brush.setStyle(Qt.BDiagPattern)
        qp.setBrush(brush)
        qp.drawRect(250, 195, 90, 60)
```

## 贝塞尔曲线

可以使用PyQt5的`QPainterPath`创建贝塞尔曲线。绘画路径是由许多构建图形的对象，具体表现就是一些线的形状，比如矩形，椭圆，线和曲线。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-667e8628be526c50.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```python

self.setGeometry(300, 300, 380, 250)
        self.setWindowTitle('Bézier curve')
        self.show()

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        qp.setRenderHint(QPainter.Antialiasing)
        self.drawBezierCurve(qp)
        qp.end()

    def drawBezierCurve(self, qp):
        # 用QPainterPath路径创建贝塞尔曲线。使用cubicTo()方法生成，分别需要三个点：起始点，控制点和终止点。
        path = QPainterPath()
        path.moveTo(30, 30)
        path.cubicTo(30, 30, 200, 350, 350, 30)
        # drawPath()绘制最后的图像。
        qp.drawPath(path)
```


# 自定义控件

PyQt5有丰富的组件，但是肯定满足不了所有开发者的所有需求，PyQt5只提供了基本的组件，像按钮，文本，滑块等。如果你还需要其他的模块，应该尝试自己去自定义一些。

自定义组件使用绘画工具创建，有两个基本方式：根据已有的创建或改进；通过自己绘图创建。

## Burning widget

这个组件我们会在Nero，K3B，或者其他CD/DVD烧录软件中见到。
![image.png](https://upload-images.jianshu.io/upload_images/18339009-560b26f54e2f36b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# 本例中，我们使用了QSlider和一个自定义组件，由进度条控制。显示的有物体（也就是CD/DVD）的总容量和剩余容量。
# 进度条的范围是1~750。如果值达到了700（OVER_CAPACITY），就显示为红色，代表了烧毁了的意思。
# 烧录组件在窗口的底部，这个组件是用QHBoxLayout和QVBoxLayout组成的。


class Communicate(QObject):
    updateBW = pyqtSignal(int)


class BurningWidget(QWidget):
    def __init__(self):
        super().__init__()
        # 修改组件进度条的高度，默认的有点小。
        self.setMinimumSize(1, 30)
        self.value = 75
        self.num = [75, 150, 225, 300, 375, 450, 525, 600, 675]

    def setValue(self, value):
        self.value = value

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        self.drawWidget(qp)
        qp.end()

    def drawWidget(self, qp):
        MAX_CAPACITY = 700
        OVER_CAPACITY = 750
        # 使用比默认更小一点的字体，这样更配。
        font = QFont('Serif', 7, QFont.Light)
        qp.setFont(font)

        # 动态的渲染组件，随着窗口的大小而变化，这就是我们计算窗口大小的原因。
        # 最后一个参数决定了组件的最大范围，进度条的值是由窗口大小按比例计算出来的。
        # 最大值的地方填充的是红色。注意这里使用的是浮点数，能提高计算和渲染的精度。
        # 绘画由三部分组成，黄色或红色区域和黄色矩形，然后是分割线，最后是添上代表容量的数字。
        size = self.size()
        w = size.width()
        h = size.height()
        step = int(round(w / 10))
        till = int(((w / OVER_CAPACITY) * self.value))
        full = int(((w / OVER_CAPACITY) * MAX_CAPACITY))

        if self.value >= MAX_CAPACITY:
            qp.setPen(QColor(255, 255, 255))
            qp.setBrush(QColor(255, 255, 184))
            qp.drawRect(0, 0, full, h)
            qp.setPen(QColor(255, 175, 175))
            qp.setBrush(QColor(255, 175, 175))
            qp.drawRect(full, 0, till-full, h)
        else:
            qp.setPen(QColor(255, 255, 255))
            qp.setBrush(QColor(255, 255, 184))
            qp.drawRect(0, 0, till, h)

        pen = QPen(QColor(20, 20, 20), 1,
                   Qt.SolidLine)
        qp.setPen(pen)
        qp.setBrush(Qt.NoBrush)
        qp.drawRect(0, 0, w-1, h-1)

        j = 0

        for i in range(step, 10*step, step):
            qp.drawLine(i, 0, i, 5)
            # 这里使用字体去渲染文本。必须要知道文本的宽度，这样才能让文本的中间点正好落在竖线上。
            metrics = qp.fontMetrics()
            fw = metrics.width(str(self.num[j]))
            qp.drawText(i-fw/2, h/2, str(self.num[j]))
            j = j + 1


class Example(QWidget):
    def __init__(self):
        super().__init__()

        OVER_CAPACITY = 750

        sld = QSlider(Qt.Horizontal, self)
        sld.setFocusPolicy(Qt.NoFocus)
        sld.setRange(1, OVER_CAPACITY)
        sld.setValue(75)
        sld.setGeometry(30, 40, 150, 30)

        self.c = Communicate()
        self.wid = BurningWidget()
        self.c.updateBW[int].connect(self.wid.setValue)

        sld.valueChanged[int].connect(self.changeValue)
        hbox = QHBoxLayout()
        hbox.addWidget(self.wid)
        vbox = QVBoxLayout()
        vbox.addStretch(1)
        vbox.addLayout(hbox)
        self.setLayout(vbox)

        self.setGeometry(300, 300, 390, 210)
        self.setWindowTitle('Burning widget')
        self.show()

    def changeValue(self, value):
        # 拖动滑块的时候，调用了changeValue()方法。
        # 这个方法内部，我们自定义了一个可以传参的updateBW信号。
        # 参数就是滑块的当前位置。这个数值之后还用来于Burning组件，然后重新渲染Burning组件。
        self.c.updateBW.emit(value)
        self.wid.repaint()

```
# qt与matplot结合
```

import sys
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FC
from PyQt5.QtWidgets import QApplication, QPushButton, QMainWindow, QGridLayout, QWidget


class QtDraw(QMainWindow):
    flag_btn_start = True

    def __init__(self):
        super(QtDraw, self).__init__()
        self.init_ui()

    def init_ui(self):
        self.resize(800, 600)
        self.setWindowTitle('PyQt5 Draw')

        # TODO:这里是结合的关键
        self.fig = plt.Figure()
        # 定义画布
        self.canvas = FC(self.fig)

        # 定义按键,并与画图连接
        self.btn_start = QPushButton(self)
        self.btn_start.setText('draw')
        self.btn_start.clicked.connect(self.slot_btn_start)

        # 把画布按键都放到QVBoxLayout
        widget = QWidget()
        layout = QGridLayout()
        layout.addWidget(self.canvas)
        layout.addWidget(self.btn_start)
        widget.setLayout(layout)
        self.setCentralWidget(widget)

    def slot_btn_start(self):
        try:
            ax = self.fig.add_subplot(111)
            x = np.linspace(0, 100, 100)
            y = np.random.random(100)
            ax.cla()  # TODO:删除原图，让画布上只有新的一次的图
            ax.plot(x, y)
            self.canvas.draw()  # TODO:这里开始绘制
        except Exception as e:
            print(e)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = QtDraw()
    w.show()
    sys.exit(app.exec_())
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-ba5c4a4a873dc7cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





