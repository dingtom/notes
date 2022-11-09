- [ 鼠标操作](#head1)
- [ 键盘操作](#head2)
- [ 提示信息](#head3)
# <span id="head1"> 鼠标操作</span>
```python
import pyautogui
```
获取当前屏幕分辨率
```screenWidth, screenHeight = pyautogui.size()```

获取当前鼠标位置
```currentMouseX, currentMouseY = pyautogui.position()```

2秒钟鼠标移动坐标为100,100位置  绝对移动
```pyautogui.moveTo(x=100, y=100,duration=2, tween=pyautogui.linear)```

鼠标移到屏幕中央。
```pyautogui.moveTo(screenWidth / 2, screenHeight / 2)```

鼠标左击一次
> clicks 点击次数
 interval点击之间的间隔
button 'left', 'middle', 'right' 对应鼠标 左 中 右或者取值(1, 2, or 3)
tween 渐变函数
#
```pyautogui.click(x=None, y=None, clicks=1, interval=0.0, button='left', duration=0.0, tween=pyautogui.linear)```

# 鼠标相对移动 ,向下移动
#pyautogui.moveRel(None, 10)
pyautogui.moveRel(xOffset=None, yOffset=10,duration=0.0, tween=pyautogui.linear)


鼠标当前位置0间隔双击
```pyautogui.doubleClick(x=None, y=None, interval=0.0, button='left', duration=0.0, tween=pyautogui.linear)```

鼠标当前位置3击
```pyautogui.tripleClick(x=None, y=None, interval=0.0, button='left', duration=0.0, tween=pyautogui.linear)```

右击
```pyautogui.rightClick()```

中击
```pyautogui.middleClick()```

鼠标拖拽
```pyautogui.dragTo(x=427, y=535, duration=3,button='left')```

鼠标相对拖拽
```pyautogui.dragRel(xOffset=100,yOffset=100,duration=,button='left',mouseDownUp=False)```

鼠标移动到x=1796, y=778位置按下
```pyautogui.mouseDown(x=1796, y=778, button='left')```

鼠标移动到x=2745, y=778位置松开（与mouseDown组合使用选中）
```pyautogui.mouseUp(x=2745, y=778, button='left',duration=5)```

鼠标当前位置滚轮滚动
```pyautogui.scroll()```
鼠标水平滚动（Linux）
```pyautogui.hscroll()```
鼠标左右滚动（Linux）
```pyautogui.vscroll()```

# <span id="head2"> 键盘操作</span>
模拟输入信息
```pyautogui.typewrite(message='Hello world!',interval=0.5)```
点击ESC
```pyautogui.press('esc')```
按住shift键
```pyautogui.keyDown('shift')```
放开shift键
pyautogui.keyUp('shift')
模拟组合热键
```pyautogui.hotkey('ctrl', 'c')```

|按键	|说明|
|:-:|:-:|
|enter(或return 或 \n)|	回车|
|esc	|ESC键|
|shiftleft, shiftright	|左右SHIFT键|
|altleft, altright	|左右ALT键|
|ctrlleft, ctrlright|	左右CTRL键|
|tab (\t)	|TAB键|
|backspace, delete	|BACKSPACE 、DELETE键|
|pageup, pagedown	|PAGE UP 和 PAGE DOWN键|
|home, end	|HOME 和 END键|
|up, down, left,right|	箭头键|
|f1, f2, f3….	|F1…….F12键|
|volumemute, volumedown,volumeup	|有些键盘没有|
|pause	|PAUSE键|
|capslock, numlock,scrolllock	|CAPS LOCK, NUM LOCK, 和 SCROLLLOCK 键|
|insert|	INS或INSERT键|
|printscreen	|PRTSC 或 PRINT SCREEN键|
|winleft, winright|	Win键|
|command	|Mac OS X command键|

# <span id="head3"> 提示信息</span>
```pyautogui.alert(text='This is an alert box.', title='Test')```
# 选项
 ```pyautogui.confirm('Enter option.', buttons=['A', 'B', 'C'])```
# 输入密码
```
a = pyautogui.password('Enter password (text will be hidden)')
print(a)
```
# prompt
```
a = pyautogui.prompt('input  message')
print(a)
```
# 截屏
```im2 = pyautogui.screenshot('my_screenshot.png')```

#####在当前屏幕中查找指定图片(图片需要由系统截图功能截取的图)
>```coords = pyautogui.locateOnScreen('folder.png')```
获取定位到的图中间点坐标
```x,y=pyautogui.center(coords)```
右击该坐标点
```pyautogui.rightClick(x,y)```


#为所有的PyAutoGUI函数增加延迟。默认延迟时间是0.1秒。
```pyautogui.PAUSE = 0.5```
