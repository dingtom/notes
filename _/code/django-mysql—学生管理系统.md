- [ 在terminal中输入](#head1)
- [每次新创建的app都要在setting.py中 INSTALLED_APPS注册一下否则templates目录下的下的模板无法正常渲染](#head2)
'''实现增删改查四个功能'''
# <span id="head1"> 在terminal中输入</span>
python manage.py startapp studentapp(创建firstapp)
# <span id="head2">每次新创建的app都要在setting.py中 INSTALLED_APPS注册一下否则templates目录下的下的模板无法正常渲染</span>

#1.先建一个mysql数据库（库名：student）
#2.在models中创建类
```
from django.db import models
class Student(models.Model):
    id=models.IntegerField(primary_key=True)
    name=models.CharField(max_length=20)
    age=models.IntegerField()
    sex=models.CharField(max_length=100)
    grade=models.IntegerField()
    hobby=models.TextField()
```
#3.在setting.py文件中配置数据库

```
DATABASES = {
    'default': {
                 'ENGINE': 'django.db.backends.mysql',
                 'NAME': 'student',
                 'USER': 'root',
                 'PASSWORD': '123456',
                 'HOST': '127.0.0.1',
                 'PORT': '3306',
                 }
}
```
#4. 生成表
在terminal中输入以下命令：
```python manage.py makemigrations```
（负责收集models.py文件中数据模型的变化，如果这里的文件内容没有发生任何变化，会提示：No changes detected）
```python manage.py migrate```
将数据模型生成为数据库中的表。

#5.创建static文件夹将.css文件复制到文件夹下，在setting中设置
```
STATICFILES_DIRS=[os.path.join(BASE_DIR,'static')]
```
#6将.HTML文件复制到templates中并修改(可通过继承精简代码）
```
'''base.html'''
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    {% block title %}{% endblock %}{#留着子模板填充#}
</head>
<body>
     <h1 align="center">学生管理系统</h1>
     <center>{% block  content %}{% endblock %}</center>{#居中#}
</body>
</html>
‘’‘first.html’‘’
{% extends 'base.html' %}
{% block title %}{#填充父模板#}
    <title>欢迎登陆</title>
{% endblock %}
{% block content %}
    <button onclick="window.location.href = '/add/'">添加学生</button>
    <button onclick="window.location.href = '/delete/'" >删除学生</button>
    <button onclick="window.location.href = '/update/'" >修改学生</button>
    <button onclick="window.location.href = '/select/'" >查询学生</button>
{% endblock %}
'''add.html'''
{% extends 'base.html' %}
{% block title %}
    <title>添加</title>
{% endblock %}
{% block content %}
   <form  action="{{ url }}" method="post">
    {% csrf_token %}
    <label>
        <span>学号 :</span>
        <input id="id" type="text" name="id" placeholder="请输入要添加的学号"/>
    </label>
       <br>
    <label>
        <span>姓名 :</span>
        <input id="name" type="text" name="name" placeholder="请输入姓名"/>
    </label>
       <br>
    <label>
        <span>年龄 :</span>
        <input id="age" type="text" name="age" placeholder="请输入年龄"/>
    </label>
       <br>
    <label>
        <span>性别 :</span>
        <input id="sex" type="text" name="sex"  placeholder="请输入性别"/>
    </label>
       <br>
    <label>
        <span>年级 :</span>
        <input id="message" type="text" name="grade"  placeholder="请输入年级"/>
    </label>
       <br>
    <label>
        <span>爱好 :</span>
        <textarea id="hobby" type="text" name="hobby"  placeholder="请输入爱好"></textarea>
    </label>
    <br>
    <label>
        <span></span>
        <input type="submit"  value="{{ submit_type }}"/>
    </label>
</form>
{% endblock %}
'''delete.html'''
{% extends 'base.html' %}
{% block title %}
    <title>删除</title>
{% endblock %}
{% block content %}
<form  action="{{ url }}" method="post">
    {% csrf_token %}
    <label>
        <span>学号 :</span>
        <input id="id" type="text" name="id" placeholder="请输入要删除的学号"/>
    </label>
    <br>
   <label>
        <span></span>
        <input type="submit"  value="{{ submit_type }}"/>
    </label>
</form>
{% endblock %}
'''update.html'''
{% extends 'base.html' %}
{% block title %}
    <title>修改</title>
{% endblock %}
{% block content %}
    <form  action="{{ url }}" method="post">
    {% csrf_token %}
    <label>
        <span>学号 :</span>
        <input id="id" type="text" name="id" placeholder="请输入要修改的学号"/>
    </label>
        <br>
    <label>
        <span>姓名 :</span>
        <input id="name" type="text" name="name" placeholder="请输入修改后的姓名"/>
    </label>
        <br>
    <label>
        <span>年龄 :</span>
        <input id="age" type="text" name="age" placeholder="请输入修改后的年龄"/>
    </label>
        <br>
    <label>
        <span>性别 :</span>
        <input id="sex" type="text" name="sex"  placeholder="请输入修改后的性别"/>
    </label>
        <br>
    <label>
        <span>年级 :</span>
        <input id="message" type="text" name="grade"  placeholder="请输入修改后的年级"/>
    </label>
        <br>
    <label>
        <span>爱好 :</span>
        <textarea id="hobby" type="text" name="hobby"  placeholder="请输入修改后的爱好"></textarea>
    </label>
    <br>
    <label>
        <span></span>
        <input type="submit" value="{{ submit_type }}"/>
    </label>
</form>
{% endblock %}
'''select.html'''
{% extends 'base.html' %}
{% block title %}
    <title>查询</title>
{% endblock %}
{% block content %}
   <form  action="{{ url }}" method="post">
    {% csrf_token %}
    <label>
        <span>学号 :</span>
        <input id="id" type="text" name="id" placeholder="请输入要查询的学号"/>
    </label>
   <br>
   <label>
        <span></span>
        <input type="submit"class="button" value="{{ submit_type }}"/>
    </label>
</form>
    {% if s %}
      <div>学号：{{ s.id }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp姓名：{{ s.name }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年龄:{{ s.age }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp性别:{{ s.sex }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年级:{{ s.grade }}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp爱好:{{ s.hobby }}.
      </div>
   {% endif %}
{% endblock %}
'''success.html'''
{% extends 'base.html' %}
{% block title %}
    <title>操作成功</title>
{% endblock %}
{% block content %}
         <h2>操作成功</h2>
         <button onclick="window.location.href = '/first/'" >返回首页</button>
{% endblock %}
```
#7.在views中编写功能函数
```
from django.shortcuts import render
from .models import Student
def first(request):
    return render(request,template_name='first.html')
def success(request):
    return render(request,template_name='success.html')
def add(request):
    if request.method=='GET':
        context={
            'submit_type':'添加',
            'url':'/add/'
        }
        return render(request, template_name='add.html', context=context)
    elif request.method=='POST':
        id=request.POST.get('id')
        name=request.POST.get('name')
        age=request.POST.get('age')
        sex=request.POST.get('sex')
        grade=request.POST.get('grade')
        hobby=request.POST.get('hobby')
        s=Student(id=id,name=name,age=age,sex=sex,grade=grade,hobby=hobby)
        s.save()
        return render(request,template_name='success.html')
def delete(request):
    if request.method=='GET':
        context={
            'submit_type':'删除',
            'url':'/delete/'
        }
        return render(request, template_name='delete.html', context=context)
    elif request.method=='POST':
        id = request.POST.get('id')
        s = Student.objects.get(id=id)
        s.delete()
        return render(request, template_name='success.html')

def update(request):
    if request.method=='GET':
        context={
            'submit_type':'修改',
            'url':'/update/'
        }
        return render(request, template_name='update.html', context=context)
    elif request.method=='POST':
        id = request.POST.get('id')
        name = request.POST.get('name')
        age = request.POST.get('age')
        sex = request.POST.get('sex')
        grade = request.POST.get('grade')
        hobby = request.POST.get('hobby')
        s=Student.objects.get(id=id)
        s.name=name
        s.age=age
        s.sex=sex
        s.grade=grade
        s.hobby=hobby
        s.save()
        return render(request,template_name='success.html')
def select(request):
    if request.method == 'GET':
        context = {
            'submit_type': '查询',
            'url': '/select/'
        }
        return render(request, template_name='select.html', context=context)
    elif request.method == 'POST':
        id = request.POST.get('id')
        s = Student.objects.get(id=int(id))
        return render(request,template_name='select.html',context={'s':s,'submit_type':'查询','url':'/select/'})
```
#8.urls中设置路由
```
from django.contrib import admin
from django.urls import path
from studentapp.views import add,delete,update,select,first
urlpatterns = [
    path('admin/', admin.site.urls),
    path('add/', add),
    path('delete/', delete),
    path('update/', update),
    path('select/', select),
    path('first/', first),
    path('success/', success)
]

```
#8.启动项目
```python manage.py runserver```
#9.登录
[http://127.0.0.1:8000/first/](http://127.0.0.1:8000/first/)

![图片.png](https://upload-images.jianshu.io/upload_images/15873283-5f1e6f0546410768.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



