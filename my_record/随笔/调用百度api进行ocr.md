```pip install baidu-aip```
```
from PIL import Image
from PIL import ImageGrab
import keyboard
import time
from aip import AipOcr

# 1.截图
keyboard.wait(hotkey="ctrl+d")  # 输入键盘的触发事件
keyboard.wait(hotkey="ctrl+c")
time.sleep(0.1)

# 2.保存图片
image = ImageGrab.grabclipboard()  # 把图片从剪切板保存到当前路径
image.save("screen.png")

# 3.识别图片上的内容
# [https://login.bce.baidu.com/?account=&redirect=http%3A%2F%2Fconsole.bce.baidu.com%2F%3F_%3D1573266309149#/index/overview](https://login.bce.baidu.com/?account=&redirect=http%3A%2F%2Fconsole.bce.baidu.com%2F%3F_%3D1573266309149#/index/overview)

APP_ID = '17732244'
API_KEY = 'K32y2jSOxgkq6LSynoG5MStB'
SECRET_KEY = 'ASkTsum1Zt8kTFQGABly2FrHviGFkUIA'
client = AipOcr(APP_ID, API_KEY, SECRET_KEY)

# 读取图片
with open("screen.png", 'rb') as f:
    image = f.read()
    # 调用百度API通用文字识别（高精度版），提取图片中的内容
    text = client.basicAccurate(image)
    result = text["words_result"]
    for i in result:
        print(i["words"])
    # print(text)
```
