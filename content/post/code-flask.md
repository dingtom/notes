---
        # 文章标题
        title: "code-flask"
        # 分类
        categories: 
            - code
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

```
from flask import Flask

App = Flask(__name__)

@App.route('/')
def index():
    return"hello world"



if __name__ == "__main__":
    App.run(debug=True)
```
