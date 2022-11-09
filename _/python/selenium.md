各个浏览器驱动下载地址：
https://code.google.com/p/selenium/downloads/list
安装 chrome 浏览器驱动，下载 ChromeDriver_win32.zip(根据自己系统下载不同的版本驱动)，解压 得到 chromedriver.exe 文件放到环境变量 Path 所设置的目录下，如果前面我们已经将（C:\Python27 ） 添加到了环境变量 Path 所设置的目录，可以将 chromedriver.exe 放到 C:\Python27\目录下。

```
# coding = utf-8
from selenium import webdriver
driver = webdriver.Chrome(executable_path='C:\Program Files\Google\Chrome\Application\chromedriver.exe')



driver.get('http://31.13.149.78:8080/jira/secure/Dashboard.jspa')
# driver.set_window_size(480, 800)
# driver.maximize_window() #将浏览器最大化显示
# driver.back()#返回（后退
# driver.forward()
# find_element_by_id()
# find_element_by_name()
# find_element_by_class_name()
# find_element_by_tag_name()
# find_element_by_link_text()
# find_element_by_partial_link_text()
# find_element_by_xpath()
# find_element_by_css_selector()
# find_element_by_xpath("//input[@id=’input’]") #通过自身的 id 属性定位
# find_element_by_xpath("//span[@id=’input-container’]/input") #通过上一级目录的id属性定位
# find_element_by_xpath("//div[@id=’hd’]/form/span/input") #通过上三级目录的 id 属性定位
# find_element_by_xpath("//div[@name=’q’]/form/span/input")#通过上三级目录的 name 属性定位
# XPath可以做布尔逻辑运算，例如： // div[ @ id =’hd’ or@name=’q’]

# clear() 用于清除输入框的默认内容
# submit() 提交表单
# size/text/get_attribute(name)/is_displayed()
# context_click() 右击 
# double_click() 双击 
# drag_and_drop() 拖动 
# move_to_element() 鼠标悬停在一个元素上 
# click_and_hold() 按下鼠标左键在一个元素上
# ---------------------鼠标事件
# from selenium.webdriver.common.action_chains import ActionChains
# #定位到要右击的元素
# right =driver.find_element_by_xpath("xx")
# # #对定位到的元素执行鼠标右键操作
## ActionChains 用于生成用户的行为；所有的行为都存储在 actionchains 对象。通过 perform()执行存储的行为。
# ActionChains(driver).context_click(right).perform()
# double_click(on_element)
# drag_and_drop(source, target)source: 鼠标按下的源元素。target: 鼠标释放的目标元素。
# move_to_element()
#----------------------键盘事件
from selenium.webdriver.common.keys import Keys
driver.find_element_by_id("kw").send_keys(Keys.BACK_SPACE)
#ctrl+a 全选输入框内容
driver.find_element_by_id("kw").send_keys(Keys.CONTROL,'a')
driver.find_element_by_id("su").send_keys(Keys.ENTER)

# #获得前面title，
# driver.title
# driver.current_url
# # ---------------------等待
# from selenium.webdriver.support.ui import WebDriverWait
# implicitly_wait()：是 webdirver 提供的一个超时等待。隐的等待一个元素被发现，或一个命令完成。 如果超出了设置时间的则抛出异常。
# WebDriverWait()：同样也是 webdirver 提供的方法。在设置时间内，默认每隔一段时间检测一次当前 页面元素是否存在，如果超过设置时间检测不到则抛出异常。
# element=WebDriverWait(driver, 10).until(lambda driver : driver.find_element_by_id("kw"))
# # until(method,message=’’)调用该方法提供的驱动程序作为一个参数，直到返回值不为 False。
# element.send_keys("selenium")
# driver.implicitly_wait(30)

driver.find_element_by_id("login-form-username").clear().send_keys('dingwenchao')
driver.find_element_by_id("login-form-password").clear().send_keys('Aa123456!')
driver.find_element_by_id("login").click()
#driver.quit()



```