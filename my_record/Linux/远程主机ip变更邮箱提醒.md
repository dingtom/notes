


```python
import socket
import smtplib
from email.header import Header
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from time import asctime

def send_an_email(email_content): # email_content是一个字符串
    mail_host = "smtp.sina.com" # 这个去邮箱找，这里用的sina邮箱
    mail_user = '17839906091@sina.cn'
    mail_auth_code = '9f8f7149d10d0b1f'
    mail_sender = mail_user # 用mail_user 作为发送人
    mail_receivers = ['2524370217@qq.com']
    
    message = MIMEMultipart()
    message['From'] = Header(mail_sender)  # 寄件人
    message['Subject'] = Header("主题名字")
    message.attach(MIMEText(asctime(), 'plain', 'utf-8'))
    # message.attach(MIMEText('<html><h1>你好<h1></html>', 'html', 'utf-8'))
    message.attach(MIMEText(email_content, 'plain', 'utf-8'))
    print("message is {}".format(message.as_string())) # debug用
    
    smtpObj = smtplib.SMTP(mail_host)
    # smtpObj.set_debuglevel(1) # 同样是debug用的
    smtpObj.login(mail_user, mail_auth_code) # 登陆
    smtpObj.sendmail(mail_sender, mail_receivers, message.as_string()) # 真正发送邮件就是这里

def get_temp_ip(current_ip):
    # 失败时可尝试删除/var/tmp/ip.json
    temp_ip_json_path = "/var/tmp/ip.json"

    if not os.path.exists(temp_ip_json_path):
        print("No {}, dump it.".format(temp_ip_json_path))
        with open(temp_ip_json_path, 'w') as jo:
            json.dump(current_ip, jo)
            return True, current_ip
    else:
        with open(temp_ip_json_path, 'r') as jo:
            origin_ip = json.load(jo)
        if origin_ip == current_ip:
            print("Current ip {} do not change, no need to send".format(current_ip))
            return False, current_ip
        else:
            print("The ip updated from {} to {}, update it.".format(origin_ip, current_ip))
            os.remove(temp_ip_json_path)
            with open(temp_ip_json_path, 'w') as jo:
                json.dump(current_ip, jo)
                return True, current_ip


def get_ip():
    hostname = socket.gethostname()
    addr_infos = socket.getaddrinfo(hostname, None)
    ips = set([addr_info[-1][0] for addr_info in addr_infos])
    global_ips = [ip for ip in ips if ip.startswith("24")]
    whether_to_send, send_ip = get_temp_ip(global_ips)
    send_ip = json.dumps(send_ip)
    return whether_to_send, send_ip

if __name__ == "__main__":
    whether_to_send, global_ips = get_ip()
    if whether_to_send:
        send_an_email(global_ips)
    else:
        print("wait and no send")
```


还要给脚本添加上可执行权限：

```sudo chmod +x send_ip_to_mailbox.py```
使用到linux的排程工具crond将任务排期自动执行，这里配置的是每隔1分钟执行一次ipv6地址检查。



# 企业微信消息

```python
import requests
class SendWeiXinWork:
    def __init__(self):
        self.CORP_ID = "ww1f8de63d33f2ea65"  # 企业号的标识
        self.SECRET = "0y3EgnN3AgjGawbH3dg-zv9im7yGZunNrcscNfxm4ag"  # 管理组凭证密钥
        self.AGENT_ID = 1000002  # 应用ID
        self.token = self.get_token()

    def get_token(self):
        url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken"
        data = {
            "corpid": self.CORP_ID,
            "corpsecret": self.SECRET
        }
        req = requests.get(url=url, params=data)
        res = req.json()
        if res["errmsg"] == "ok":
            return res["access_token"]
        else:
            return res

    def send_message(self, to_user, content):
        url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s" % self.token
        data = {
            "touser": '@all',  # 发送个人就填用户账号
            # "toparty": to_user,  # 发送组内成员就填部门ID
            "msgtype": "text",
            "agentid": self.AGENT_ID,
            "text": {"content": content},
            "safe": "0"
        }

        req = requests.post(url=url, json=data)
        res = req.json()
        if res["errmsg"] == "ok":
            print("send message sucessed")
        else:
            print(res["errmsg"])
```

# selenium

```python
 # 第一步：导入selenium
    from selenium import webdriver
    import time

    option = webdriver.ChromeOptions()

    option.add_argument('--user-data-dir=C:/Users/WENCHAO/AppData/Local/Google/Chrome/User Data1')
    
    # 第二步：打开谷歌浏览器
    # driver = webdriver.Chrome(executable_path="./chromedriver.exe")
    driver = webdriver.Chrome(chrome_options=option)
    driver.maximize_window()  # 屏幕最大化

    # 第三步：打开百度
    driver.get("https://www.google.com/")

    # 第四步：输入搜索关键字
    element1 = driver.find_element_by_id("kw")
    element1.send_keys("hello selenium!")
    time.sleep(3)

    # 第五步： 点击搜索按钮
    element2 = driver.find_element_by_id("su")
    element2.click()

    # 最后一步: 结束测试
    driver.quit()
```

