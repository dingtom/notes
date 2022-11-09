```
from flask import Flask

App = Flask(__name__)

@App.route('/')
def index():
    return"hello world"



if __name__ == "__main__":
    App.run(debug=True)
```
