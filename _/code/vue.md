- [ 下载Node.js并安装](#head1)
- [ 生命周期](#head2)
- [ 基础语法](#head3)
	- [ el:挂载点](#head4)
	- [ data:数据对象](#head5)
	- [ v-text:设置标签的文本值](#head6)
	- [ v-html:设置标签的innerHTML](#head7)
	- [ v-on:为元素绑定事件](#head8)
		- [ 事件修饰符](#head9)
	- [ v-show:根据表达值的真假,切换元素的显示和隐藏](#head10)
	- [ v-if:根据表达值的真假,切换元素的显示和隐藏(操纵dom元素)](#head11)
	- [ v-bind设置元素的属性(如:src,title,class)](#head12)
		- [ v-bind绑定class](#head13)
		- [ v-bind绑定style](#head14)
	- [ v-for:根据数据生成列表结构](#head15)
		- [ 组件的key属性](#head16)
		- [ 非响应式](#head17)
	- [  v-model:获取和设置表单元素的值(双向数据绑定)](#head18)
		- [ **:model是v-bind:model的缩写：这种只是将父组件的数据传递到了子组件，并没有实现子组件和父组件数据的双向绑定**.，当然**引用类型除外**，子组件改变了引用类型的数据的话，父组件的数据也会跟着改变。](#head19)
		- [ 修饰符](#head20)
	- [ v-once:元素和组件(组件后面才会学习)只渲染一次](#head21)
	- [ 计算属性](#head22)
- [ 组件](#head23)
	- [ 全局组件和局部组件](#head24)
	- [ 注册组件语法糖](#head25)
	- [ 模板的分离写法](#head26)
	- [ **组件数据的存放**](#head27)
	- [ 父子组件的通信](#head28)
		- [ 父组件向子组件传递数据](#head29)
		- [ 父组件向子组件传递方法](#head30)
		- [ 子级向父级传递](#head31)
		- [ 例子](#head32)
		- [ watch](#head33)
	- [ 父子组件的访问方式](#head34)
		- [ \$children](#head35)
		- [ $parent](#head36)
	- [ 兄弟组件通信](#head37)
	- [ slot](#head38)
		- [ 使用slot](#head39)
		- [ 具名插槽](#head40)
		- [ 作用域插槽slot-scope](#head41)
- [ 模块化](#head42)
- [ Webpack](#head43)
	- [ webpack.config.js](#head44)
	- [ loader](#head45)
	- [ ES6语法处理](#head46)
	- [ 引入vue.js](#head47)
	- [ .vue文件封装处理](#head48)
	- [ plugin](#head49)
	- [ 配置分离](#head50)
- [Vue Cli](#head51)
- [ vue-router](#head52)
	- [ URL的hash](#head53)
	- [ HTML5的history模式：pushState](#head54)
		- [history.pushState(data, title, url)      ](#head55)
		- [ history.replaceState()](#head56)
		- [ history.go()](#head57)
	- [ vue-router](#head58)
	- [ 例子](#head59)
	- [ router-link/view组件](#head60)
	- [ linkActiveClass](#head61)
	- [ 路由代码跳转](#head62)
	- [ 动态路由](#head63)
	- [ 懒加载](#head64)
	- [ \$router和\$route](#head65)
	- [ 嵌套路由](#head66)
		- [ 嵌套默认路径](#head67)
	- [ 传递参数](#head68)
		- [ params的类型:](#head69)
		- [ query的类型:](#head70)
	- [ 导航守卫](#head71)
	- [ keep-alive遇见vue-router](#head72)
- [ Promise](#head73)
	- [ Promise三种状态](#head74)
	- [ Promise链式调用](#head75)
	- [async \ await](#head76)
- [ Vuex](#head77)
	- [ 单/界面的状态管理](#head78)
	- [ State单一状态树](#head79)
- [ axios](#head80)
	- [ axios使用](#head81)
	- [ axios+vue](#head82)
	- [ 发送并发请求](#head83)
	- [ 全局配置](#head84)
	- [ axios的实例](#head85)
	- [ axios封装](#head86)
	- [ 拦截器](#head87)
- [ 过滤器](#head88)
- [ npm](#head89)
	- [ sass](#head90)
	- [ eslint](#head91)
	- [ less-loader](#head92)
		- [ url-loader](#head93)
- [ Vue.nextTick](#head94)
- [ img的src属性绑定](#head95)
- [ watch](#head96)
	- [ 监听变量](#head97)
	- [ 监听对象具体属性](#head98)
- [ 恢复初始数据](#head99)
- [ 搜索，翻页用不同的表单](#head100)
- [ 删完最后一页的数据后跳到上一页](#head101)
- [ 拖拽排序](#head102)

# <span id="head1"> 下载Node.js并安装</span>

 首先上Node.js官网找到了本机操作系统对应的Node.js版本进行了安装包的下载。
官网：https://nodejs.org/en/download/

镜像：[Index of /nodejs/v11.10.0/ (nju.edu.cn)](http://mirrors.nju.edu.cn/nodejs/v11.10.0/)

验证是否安装成功:

```
npm -v
node -v
```

配置环境变量

```
在nodejs安装路径下，新建node_global和node_cache两个文件夹

#命令行设置缓存文件夹路径
npm config set cache “D:\nodeJs\node_cache”
#设置全局模块存放路径
npm config set cache “D:\nodeJs\node_global”

创建系统环境变量
NODE_PATH：D:\nodejs
编辑系统环境变量Path，加入路径D:\nodejs
将用户环境变量的Path加入
%NODE_PATH%\node_global
%NODE_PATH%\node_modules
%NODE_PATH%

```

安装淘宝镜像（类似于阿里云的maven中央仓库镜像）

```
首先切换淘宝镜像：
npm config set registry https://registry.npm.taobao.org
验证是否成功：
npm config get registry
安装命令：npm install -g cnpm --registry=https://registry.npm.taobao.org

验证命令：cnpm -v
```

安装webpack

```
cnpm install webpack -g
```

安装Vue

```
cnpm install vue -g
```



安装vue命令行工具，即vue-cli 脚手架

```
cnpm install @vue-cli -g
```





```css
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
  <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
  <script>
    var app = new Vue({
      el:"#app",
      data:{
        message:" 你好 小黑! "
      }
    })
  </script>
```

# <span id="head2"> 生命周期</span>

| 生命周期钩子  | 组件状态                                                     | 最佳实践                                                   |
| ------------- | ------------------------------------------------------------ | ---------------------------------------------------------- |
| beforeCreate  | 实例初始化之后，this指向创建的实例，不能访问到data、 computed、 watch、 methods和dom | 常用于初始化非响应式变量                                   |
| created       | 实例创建完成，可访向data、 computed、 watch、methods上的方法和数据，未挂载到DOM,不能访问到$el属性，$ref属性内容为空数组 | 常用于简单的ajax请求，页面的初始化                         |
| beforeMount   | 在挂载开始之前被调用， beforeMount之前，会找到对应的 template,编译为虚拟dom，放到render中准备渲染 在这里更新data | -                                                          |
| mounted       | 渲染出真实dom ，实例挂载到DOM上，此时可以通过 DOM API获取到DOM节点，$ref属性可以访问 | 常用于获取 Vnode信息和操作，ajax请求                       |
| beforeUpdate  | 响应式数据更新时调用，发生在虚拟DOM打补丁之前，重新生成dom树 根据diff（本质是调用patch函数）算法，对比上一次dom树 | 适合在更新之前访问现有的DOM,比如手动移除已添加的事件监听器 |
| updated       | 虚拟DOM重新渲染和打补丁之后调用，组件DOM已经更新，可执行依赖于DOM的操作 | 避免在这个钩子函数中操作数据，可能陷入死循环               |
| beforeDestroy | 实例销毁之前调用。这一步，实例仍然完全可用，this仍能获取到实例 | 常用于销毁定时器、解绑全局事件、销毁插件对象等操           |
| destroyed     | 实例销毁后调用，调用后，Vue实例指示的所有东西都会解绑定，所有的事件监听器会被移除，所有的子实例也会被销毁，只剩下dom空壳 | -                                                          |

注意:
`created`阶段的`ajax`请求与`mounted`请求的区别：前者页面视图未出现，如果请求信息过多，页面会长时间处于白屏状态。

- 在mounted()钩子函数中，this 指向的是window作用域

**单个组件的生命周期**

1. 初始化组件时，仅执行了`beforeCreate/Created/beforeMount/mounted`四个钩子函数
2. 当改变data中定义的变量（响应式变量）时，会执行`beforeUpdate/updated`钩子函数
3. 当切换组件（当前组件未缓存）时，会执行`beforeDestory/destroyed`钩子函数
4. 初始化和销毁时的生命钩子函数均只会执行一次，`beforeUpdate/updated`可多次执行

# <span id="head3"> 基础语法</span>

## <span id="head4"> el:挂载点</span>

vue**实例的作用范围是什么呢**
vue会管理el选项命中的元素及其内部的后代元素
**是否可以使用其他的选择器**？
可以使用其他的选择器但是建议使用ID选择
**是否可以设置其他的**dom元素呢？
可以使用其他的双标签不能使用HTML和BODY

## <span id="head5"> data:数据对象</span>

Vue中用到的数据定义在data中
data中可以写复杂类型的数据
渲染复杂类型数据时遵守is的语法即可

## <span id="head6"> v-text:设置标签的文本值</span>

```css
<div id="app">
	<h2 v-text="message+'!'">深圳</h2>
    <h2>{{ message +'!'}}深圳</h2>   //差值表达式
</div>
var app = new Vue({
	el:"#app",
	data:{
		message:"黑马程序员"
	}
})
```

- v-text指令的作用是设置标签的内容( textcontent)，默认写法会替换全部内容使用差值表达式{{}}可以替换指定内容
  内部支持写表达式

## <span id="head7"> v-html:设置标签的innerHTML</span>

- v-html指令的作用是:设置元素的innerHTML

- 内容中有html结构会被解析为标签，v-text指令无论内容是什么,只会解析为文本，解析文本使用v-text,需要解析html结构使用v-html

```css
   <div id="app">
          <p v-html="content"></p>
    </div>
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script>
        var app = new Vue({
            el:"#app",
            data:{
                content:"<a href='#'>黑马程序员</a>"
            }
        })
    </script>
```

## <span id="head8"> v-on:为元素绑定事件</span>

- 指令可以简写为@

- 绑定的方法定义在methods属性中，方法中通过this,关键字获取data中的数据

- 调用的方法有参数<button @click="btn1Click()" 要加括号，没有参数，绑定时可加可不加（）
  
- v-on省略了小括号, 但是方法本身是需要一个参数的, 这个时候, Vue会默认将浏览器生产的event事件对象作为参数传入到方法

  方法定义时, 我们需要event对象, 同时又需要其他参数。在调用方式, 如何手动的获取到浏览器参数的event对象: $event

  ![image.png](https://pic.rmb.bdstatic.com/bjh/79ae47767baca1fb81f4c87564aca456.jpeg)

- 事件的后面跟上 .修饰符 可以对事件进行限制，.enter 可以限制触发的按键为回车


### <span id="head9"> 事件修饰符</span>
https://cn.vuejs.org/v2/api/#v-on

.stop - 调用 event.stopPropagation()。

.prevent - 调用 event.preventDefault()。阻止默认行为
.{keyCode | keyAlias} - 只当事件是从特定键触发时才触发回调。
.native - 监听组件根元素的原生事件。
.once - 只触发一次回调。

## <span id="head10"> v-show:根据表达值的真假,切换元素的显示和隐藏</span>

**原理是修改元素的display,实现显示隐藏**

指令后面的内容,最终都会解析为布尔值，值为true元素显示,值为false元素隐藏，数据改变之后,对应元素的显示状态会同步更新

```css
	<div id="app">
      <input type="button" value="切换显示状态" @click="changeIsShow">
      <input type="button" value="累加年龄" @click="addAge">
      <img v-show="isShow" src="./img/monkey.gif" alt="">
      <img v-show="age>=18" src="./img/monkey.gif" alt="">
    </div>
    <!-- 1.开发环境版本，包含了有帮助的命令行警告 -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script>
      var app = new Vue({
        el:"#app",
        data:{
          isShow:false,
          age:1
        },
        methods: {
          changeIsShow:function(){
            this.isShow = !this.isShow;
          },
          addAge:function(){
            this.age++;
          }
        },
      })
    </script>
```

## <span id="head11"> v-if:根据表达值的真假,切换元素的显示和隐藏(操纵dom元素)</span>

本质是通过操纵dom元素来切换显示状态

**频繁的切换v-show,反之使用v-if,前者的切换消耗小**。表达式的值为true,元素存在于dom树中,为false,从dom树中移除

![image.png](https://pic.rmb.bdstatic.com/bjh/f0bb27a83e199641f15c9b7526327e07.jpeg)



案例：登录时，可以切换使用用户账号登录还是邮箱地址登录。

![image.png](https://pic.rmb.bdstatic.com/bjh/50e149950f4ba1d10fe0323c755c9cad.jpeg)



如果我们在有输入内容的情况下，切换了类型，我们会发现文字依然显示之前的输入的内容。但是按道理讲，我们应该切换到另外一个input元素中了。在另一个input元素中，我们并没有输入内容。

**为什么会出现这个问题呢？**

这是因为**Vue在进行DOM渲染时，出于性能考虑，会尽可能的复用已经存在的元素**，而不是重新创建新的元素。在上面的案例中，Vue内部会发现原来的input元素不再使用，直接作为else中的input来使用了。
**解决方案：**

如果我们不希望Vue出现类似重复利用的问题，可以给对应的input添加key



## <span id="head12"> v-bind设置元素的属性(如:src,title,class)</span>

完整写法是 v-bind:属性名, 简写的话可以直接省略v-bind，**只保留 :属性名**

### <span id="head13"> v-bind绑定class</span>

 需要动态的增删class建议使用对象的方式

```js
:class="{'reasonType-tags': true, 'active': reasonTypeIndex === index}
```



```css

<!-- alt= 如果无法显示图像，浏览器将显示替代文本，就像这样： -->
<!-- "javascript:void(0)"让超链接去执行一个js函数，而不是去跳转到一个地址，让超链接去执行一个js函数，而不是去跳转到一个地址， -->

  <h2 class="title" v-bind:class="{active: isActive, line: isLine}">{{message}}</h2>
<script>
  const app = new Vue({
    el: '#app',
    data: {
      message: '你好啊',
      isActive: true,
      isLine: true
    },
```

很多时候，我们希望动态的来切换class，比如：当数据为某个状态时，字体显示红色。

```css
用法一：直接通过{}绑定一个类
<h2 :class="{'active': isActive}">Hello World</h2>

用法二：也可以通过判断，传入多个值
<h2 :class="{'active': isActive, 'line': isLine}">Hello World</h2>

用法三：和普通的类同时存在，并不冲突
注：如果isActive和isLine都为true，那么会有title/active/line三个类
<h2 class="title" :class="{'active': isActive, 'line': isLine}">Hello World</h2>

用法四：如果过于复杂，可以放在一个methods或者computed中
注：classes是一个计算属性
<h2 class="title" :class="classes">Hello World</h2>

用法一：直接通过{}绑定一个类
<h2 :class="['active']">Hello World</h2>

用法二：也可以传入多个值
<h2 :class=“[‘active’, 'line']">Hello World</h2>

用法三：和普通的类同时存在，并不冲突
注：会有title/active/line三个类
<h2 class="title" :class=“[‘active’, 'line']">Hello World</h2>

用法四：如果过于复杂，可以放在一个methods或者computed中
注：classes是一个计算属性
<h2 class="title" :class="classes">Hello World</h2>


```

### <span id="head14"> v-bind绑定style</span>

```
对象语法
:style="{color: currentColor, fontSize: fontSize + 'px'}"
style后面跟的是一个对象类型
对象的key是CSS属性名称
对象的value是具体赋的值，值可以来自于data中的属性

数组语法
<div v-bind:style="[baseStyles, overridingStyles]"></div>
style后面跟的是一个数组类型
多个值以，分割即可

```



## <span id="head15"> v-for:根据数据生成列表结构</span>



数组经常和v-for结合使用，**语法是( item,index ) in 数据**。遍历对象(vaule, key, index)

item 和 index 可以结合其他指令一起使用，数组长度的更新会同步到页面上,是响应式的

### <span id="head16"> 组件的key属性</span>

**为什么需要这个key属性呢（了解）？**

这个其实和Vue的虚拟DOM的Diff算法有关系。当某一层有很多相同的节点时，也就是列表节点时，我们希望插入一个新的节点。我们希望可以在B和C之间加一个F，Diff算法默认执行起来是这样的。即把C更新成F，D更新成C，E更新成D，最后再插入E，是不是很没有效率？

![image.png](https://pic.rmb.bdstatic.com/bjh/28bd29889c70bda92f861c00b47c4989.jpeg)

所以我们需要使用key来给每个节点做一个唯一标识，Diff算法就可以正确的识别此节点。找到正确的位置区插入新的节点。

所以一句话，**key的作用主要是为了高效的更新虚拟DOM。**

因为Vue是响应式的，所以当数据发生变化时，Vue会自动检测数据变化，视图会发生对应的更新。**Vue中包含了一组观察数组编译的方法，使用它们改变数组也会触发视图的更新。**pop(删最后一个)、push(最后面添加)、shift(删第一个)、unshift(最前面添加)、sort()、reverse()、splice()



### <span id="head17"> 非响应式</span>

```
 this.letters[0] = 'bbbbbb';  //通过素引值修改数组中的元素，不刷新
// 刷新可以用；this.letters.splice(0, 1, 'bbbbbb')

```

vue是检测不到数据变化的。共有下面三种情况：

- 当你利用索引直接设置一个数组项时，例如：vm.items[indexOfItem] = newValue
- 当你修改数组的长度时，例如：vm.items.length = newLength
- 由于 JavaScript 的限制，Vue不能检测对象属性的添加或删除
  

```
Vue.set(this.letters, 0, 'bbbbbb')// set(要修改的对象, 索引值, 修改后的值)
```





## <span id="head18">  v-model:获取和设置表单元素的值(双向数据绑定)</span>

绑定的数据会和表单元素值相关联

绑定的数据←→表单元素的值

**v-model通常用于双向数据绑定 ,也可以实现子组件到父组件数据的双向数据绑定**

### <span id="head19"> **:model是v-bind:model的缩写：这种只是将父组件的数据传递到了子组件，并没有实现子组件和父组件数据的双向绑定**.，当然**引用类型除外**，子组件改变了引用类型的数据的话，父组件的数据也会跟着改变。</span>

**原理**

```
<input type="text" v-model="message">
等同于
<input type="text" v-bind:value="message" v-on:input="message = $event.target.value">
```

### <span id="head20"> 修饰符</span>

**lazy修饰符：**
默认情况下，v-model默认是在input事件中同步输入框的数据的。也就是说，一旦有数据发生改变对应的data中的数据就会自动发生改变。lazy修饰符可以**让数据在失去焦点或者回车时才会更新**：
**number修饰符**：
默认情况下，在输入框中无论我们输入的是字母还是数字，都会被当做字符串类型进行处理。但是如果我们希望处理的是数字类型，那么最好直接将内容当做数字处理。
number修饰符可以**让在输入框中输入的内容自动转成数字类型：**
**trim修饰符：**
如果输入的内容首尾有很多空格，通常我们希望将其去除。trim修饰符可以**过滤内容左右两边的空格**

```css
<!--1.修饰符: lazy-->
<input type="text" v-model.lazy="message">
<h2>{{message}}</h2>
<!--2.修饰符: number-->
<input type="number" v-model.number="age">
<h2>{{age}}-{{typeof age}}</h2>
<!--3.修饰符: trim-->
<input type="text" v-model.trim="name">
<h2>您输入的名字:{{name}}</h2>
```




## <span id="head21"> v-once:元素和组件(组件后面才会学习)只渲染一次</span>

该指令后面不需要跟任何表达式(比如之前的v-for后面是由跟表达式的)

该指令表示元素和组件(组件后面才会学习)只渲染一次，不会随着数据的改变而改变。

## <span id="head22"> 计算属性</span>

我们知道，在模板中可以直接通过插值语法显示一些data中的数据。但是在某些情况，我们可能需要对数据进行一些转化后再显示，或者需要将多个数据结合起来进行显示。比如我们有firstName和lastName两个变量，我们需要显示完整的名称。但是如果多个地方都需要显示完整的名称，我们就需要写多个{{firstName}} {{lastName}}

methods和computed看起来都可以实现我们的功能，**计算属性会进行缓存，如果多次使用时，计算属性只会调用一次。**每个计算属性都包含一个getter和一个setter

在上面的例子中，我们只是使用getter来读取。在某些情况下，你也可以提供一个setter方法（不常用）。

![image.png](https://pic.rmb.bdstatic.com/bjh/e34283bd8e91d2b21f07f37594ff5277.jpeg)



```js
计算属性传值

{{计算属性名（xx）}}

computed: {
    计算属性名（）{
        return val=> {
            
        }
    }    
}
```




# <span id="head23"> 组件</span>

```css
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>

<div id="app">
  <!--                                3.使用组件-->
  <my-cpn></my-cpn>
</div>

<my-cpn></my-cpn>

<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<script>
  //                                 1.创建组件构造器对象
  const cpnC = Vue.extend({
    template: `
      <div>
        <p>我是内容, 呵呵呵呵</p>
      </div>`
  })
  //                                   2.注册组件
  Vue.component('my-cpn', cpnC)

  const app = new Vue({
    el: '#app',
    data: {
      message: '你好啊'
    }
  })
</script>

</body>
</html>  
```

## <span id="head24"> 全局组件和局部组件</span>

![quicker_a86c850a-2fa5-4565-b3dc-f83f216fa4c4.png](https://i.loli.net/2021/07/22/jZIzT91bwdEeSgl.png)

调用Vue.component()注册组件时，组件的注册是全局的。这意味着该组件可以在任意Vue示例下使用。如果我们注册的组件是挂载在某个实例中, 那么就是一个局部组件

![quicker_05ceda94-16ac-453d-a847-af270e569fbc.png](https://i.loli.net/2021/07/22/IErNfhb2aXV6WGS.png)

父组件和子组件

![quicker_defc7970-e841-4356-a243-9f9540f377cb.png](https://i.loli.net/2021/07/22/Eo6WvxLVJmzpwkj.png)

父子组件错误用法：以子标签的形式在Vue实例中使用，因为当子组件注册到父组件的components时，Vue会编译好父组件的模块，该模板的内容已经决定了父组件将要渲染的HTML（相当于父组件中已经有了子组件中的内容了）

<child-cpn></child-cpn>是只能在父组件中被识别的。类似这种用法，<child-cpn></child-cpn>是会被浏览器忽略的。

## <span id="head25"> 注册组件语法糖</span>

![quicker_db55b245-2123-4a1d-b3fc-c146c37679b1.png](https://i.loli.net/2021/07/22/68sTtvCWSIZi1gB.png)

![quicker_2c8d6cd4-ec79-48f4-a96a-33af360f1fbb.png](https://i.loli.net/2021/07/22/hZ3vE9qAgKNOGIs.png)

## <span id="head26"> 模板的分离写法</span>

![image.png](https://pic.rmb.bdstatic.com/bjh/8f0c5c4d338001225fe175d84867418c.jpeg)

## <span id="head27"> **组件数据的存放**</span>

组件不可以访问Vue实例数据

![image.png](https://pic.rmb.bdstatic.com/bjh/853278a5dc1a14fcb0a38df1b56d85dd.jpeg)







组件对象也有一个data属性(也可以有methods等属性，下面我们有用到)，只是这个**data属性必须是一个函数**，而且这个函数返回一个对象，对象内部保存着数据

为什么data在组件中必须是一个函数呢?首先，如果不是一个函数，Vue直接就会报错。其次，原因是在于**Vue让每个组件对象都返回一个新的对象，因为如果是同一个对象的，组件在多次使用后会相互影响**。

![image.png](https://pic.rmb.bdstatic.com/bjh/8cac460db419e3be94a8c71cb9dd4fc2.jpeg)



## <span id="head28"> 父子组件的通信</span>

**我们提到了子组件是不能引用父组件或者**Vue**实例的数据的**。但是，在开发中，往往一些数据确实需要从上层传递
**如何进行父子组件间的通信呢**？
通过props向子组件传递数据，通过事件向父组件发送消息。真实的开发中，**Vue实例和子组件的通信和父组件和子组件的通信过程是一样的。**

![image.png](https://pic.rmb.bdstatic.com/bjh/e4bfdddc623cf6a232b96cf7a57b2d12.jpeg)



### <span id="head29"> 父组件向子组件传递数据</span>

子组件使用选项**props来声明需要从父级接收到的数据**。props的值有两种方式：

方式一：字符串数组，数组中的字符串就是传递时的名称。
方式二：对象，对象可以设置传递时的类型，也可以设置默认值等。

![image.png](https://pic.rmb.bdstatic.com/bjh/e49f4279fcd4cf9138b337633d63c7da.jpeg)



在前面，我们的props选项是使用一个数组。我们说过，除了数组之外，我们也可以使用对象，当**需要对props进行类型等验证、默认值时，就需要对象写法了。**

验证都支持哪些数据类型呢？String\Number\Boolean\Array\Object\Date\Function\Symbol

![image.png](https://pic.rmb.bdstatic.com/bjh/2d7f4ac9e27892af067ddb40b3ffb7fd.jpeg)

props不支持驼峰，需要改写

```css
<div id="app">
  <cpn :c-info="info" :child-my-message="message" v-bind:class></cpn>
</div>
<script>
  const cpn = {
    template: '#cpn',
    props: {
      cInfo: {
        type: Object,
        default() {
          return {}
        }
      },
      childMyMessage: {
        type: String,
        default: ''
      }
    }
  }
```

### <span id="head30"> 父组件向子组件传递方法</span>

```
1、在子组件内通过绑定事件机制：@自定义方法名=“父组件方法名”
2、子组件方法中，通过this.emit(“自定义方法名”)，触发
3、子组件调用方法myclick
```



### <span id="head31"> 子级向父级传递</span>

还有一种比较常见的是子组件传递数据或事件到父组件中。我们应该如何处理呢？这个时候，**我们需要使用自定义事件来完成。**之前学习的v-on不仅仅可以用于监听DOM事件，也可以用于组件间的自定义事件。

**自定义事件的流程**：**在子组件中，通过$emit()来触发事件。在父组件中，通过v-on来监听子组件事件。**

我们来看一个简单的例子：
我们之前做过一个两个按钮+1和-1，点击后修改counter。我们整个**操作的过程还是在子组件中完成，但是之后的展示交给父组件**。这样，我们就需要将子组件中的counter，传给父组件的某个属性，比如total。

![image.png](https://pic.rmb.bdstatic.com/bjh/c63b921ae68b9215301def024f1433fd.jpeg)

### <span id="head32"> 例子</span>

父子组件通信，父组件将num1通过props给子组件，子组件把props的数据复制一份通过input进行更改，并调用emit来修改父组件的num1

![1635848935525](C:\Users\dingwenchao\AppData\Roaming\Typora\typora-user-images\1635848935525.png)



### <span id="head33"> watch</span>

监听属性的改变

```js
// 父组件 
父组件{{fu_a}}

data() {
    return {
        fu_a: 1,
    }        

methods: {
    zi_change_fu(value) {
        this.fu_a = value
    },

// 子组件
子组件输入<input type="text" v-model="zi_a">
    
props: {
	fu_a: {}
},
data() {
    return {
        zi_a: this.fu_a
    }
},
watch: {
    zi_a(value) {
        this.$emit('numChange', value)
     }
},    
```



## <span id="head34"> 父子组件的访问方式</span>

### <span id="head35"> \$children</span>

有时候我们需要父组件直接访问子组件，子组件直接访问父组件，或者是子组件访问根组件。

**父组件访问子组件：使用\$children或\$refs reference(引用)。子组件访问父组件：使用$parent**

我们先来看下\$children的访问。**this.\$children是一个数组类型，它包含所有子组件对象。**我们这里通过一个遍历，取出所有子组件的message状态。

![image.png](https://pic.rmb.bdstatic.com/bjh/4351769184af2b97bcef9e4009fa4c89.jpeg)



\$children的缺陷：**通过\$children访问子组件时，是一个数组类型，访问其中的子组件必须通过索引值**。但是当子组件过多，我们需要拿到其中一个时，往往不能确定它的索引值，甚至还可能会发生变化。有时候，我们想明确获取其中一个特定的组件，这个时候就可以使用$refs

**$refs**

$refs和ref指令通常是一起使用的。**首先，我们通过ref给某一个子组件绑定一个特定的ID。其次，通过this.\$refs.ID就可以访问到该组件了。**

![image.png](https://pic.rmb.bdstatic.com/bjh/3dc67e05506973a106937558863fd584.jpeg)



​        this.$refs.saveTagInput.$refs.input.focus();

### <span id="head36"> $parent</span>

如果我们想在子组件中直接访问父组件，可以通过\$parent，访问根组件\$root

注意事项：

尽管在Vue开发中，我们允许通过$parent来访问父组件，**但是在真实开发中尽量不要这样做。子组件应该尽量避免直接访问父组件的数据**，因为这样耦合度太高了。如果我们将子组件放在另外一个组件之内，很可能该父组件没有对应的属性，往往会引起问题。

另外，更不好做的是通过$parent直接修改父组件的状态，那么父组件中的状态将变得飘忽不定，很不利于我的调试和维护。

![image.png](https://pic.rmb.bdstatic.com/bjh/d1243a35c70c8701b4e6eeb494623b18.jpeg)

## <span id="head37"> 兄弟组件通信</span>

```js
// bus.js
import Vue from 'vue';
const bus = new Vue();
export default bus;

// a.vue
import bus from '@/utils/bus';
bus.$emit('timeStartEnd', this.meetingForm.timeStart, this.meetingForm.timeEnd);

// b.vue  
import bus from '@/utils/bus';    

bus.$off('timeStartEnd').$on('timeStartEnd', (timeStart, timeEnd) => {
        this.timeMeetEnd = timeEnd,
        this.timeMeetStart = timeStart
    });

beforeDestroy() {
    bus.$off('timeStartEnd');
},    
```



vue路由切换

new_beforeCreate->new_created->new_beforeMounted->lod_beforeDestroy->old_desotroyd->new_mounted

```js
// bus.js
import Vue from 'vue';
const bus = new Vue();
export default bus;

// a.vue
import bus from '@/utils/bus';
beforeDestroy() {
bus.$emit('timeStartEnd', this.meetingForm.timeStart, this.meetingForm.timeEnd);
},
// b.vue  
import bus from '@/utils/bus';    
created() {
    bus.$off('timeStartEnd').$on('timeStartEnd', (timeStart, timeEnd) => {
        this.timeMeetEnd = timeEnd,
        this.timeMeetStart = timeStart
    });
},
beforeDestroy() {
    bus.$off('timeStartEnd');
},    
```



## <span id="head38"> slot</span>

**组件的插槽也是为了让我们封装的组件更加具有扩展性。让使用者可以决定组件内部的一些内容到底展示什么。**

移动开发中，几乎每个页面都有导航栏。导航栏我们必然会封装成一个插件，比如nav-bar组件。一旦有了这个组件，我们就可以在多个页面中复用了。但是，每个页面的导航不完全一样

最好的封装方式就是将共性抽取到组件中，将不同暴露为插槽。一旦我们预留了插槽，就可以让使用者根据自己的需求，决定插槽中插入什么内容。是搜索框，还是文字，还是菜单。由调用者自己来决定。

### <span id="head39"> 使用slot</span>

在子组件中，使用特殊的元素<slot>就可以**为子组件开启一个插槽。该插槽插入什么内容取决于父组件如何使用**。

父组件标签中的内容会替换子组件<slot>中的内容

![image.png](https://pic.rmb.bdstatic.com/bjh/e247a2b0c67e430ca7e1007d27144bf4.jpeg)

### <span id="head40"> 具名插槽</span>

当子组件的功能复杂时，子组件的插槽可能并非是一个。这个时候，我们就需要给插槽起一个名字。只要给slot元素一个name属性即可```<slot name='myslot'></slot>```

父组件用时```<span slot='left'></span>```

![image.png](https://pic.rmb.bdstatic.com/bjh/29bb716cbd8fdf910e0fefb8a8cd7385.jpeg)

### <span id="head41"> 作用域插槽slot-scope</span>


**父组件替换插槽的标签**，**但是内容由子组件来提供**。

我们先提一个需求：

子组件中包括一组数据，比如：pLanguages: ['JavaScript', 'Python', 'Swift', 'Go', 'C++']，需要在多个界面进行展示：某些界面是以水平方向一一展示的，某些界面是以列表形式展示的，某些界面直接展示一个数组，内容在子组件，希望父组件告诉我们如何展示，怎么办呢？利用slot作用域插槽就可以了

![image.png](https://pic.rmb.bdstatic.com/bjh/5a89436ce944e2420cc6d7eb6af20056.jpeg)





# <span id="head42"> 模块化</span>

export指令用于导出变量，也可以输出函数或者输出类

```
export let name = 'hi'
export let age = 1

let name = 'hi'
let age = 1
export { name, age }
```

某些情况下，一个模块中包含某个的功能，我们并不希望给这个功能命名，而且让导入者可以自己来命名,这个时候就可以使用export default

**需要注意**：export default在**同一个模块中，不允许同时存在多个**。

````
// info.js
export default function () {
	console.log('hi')
}
// main.js
import myFunc from './info.js'
myFunc()
````

我们使用export指令导出了模块对外提供的接口，下面我们就可以通过import命令来加载对应的这个模块了

首先，我们需要在HTML代码中引入两个js文件，并且类型需要设置为module

import指令用于导入模块中的内容，比如main.js的代码

```
<script src='info.js' type='module'></script>
```

如果我们希望某个模块中所有的信息都导入，一个个导入显然有些麻烦，通过```*```可以导入模块中所有的export变量，但是通常情况下我们需要给起一个别名，方便后续的使用

```
import { name, age } from './info.js'
import * from './info.js'
```

# <span id="head43"> Webpack</span>

webpack就是一个模块化的打包工具，所以它支持我们代码中写模块化，可以对模块化的代码进行处理。

## <span id="head44"> webpack.config.js</span>

另外，如果在处理完所有模块之间的关系后，使用webpack的指令将多个js打包到一个js文件中，引入时就变得非常方便了。

```
// main.js
// 2.使用ES6的模块化的规范
import {name} from "./info";
console.log(name);
// info.js
export const name = 'why';
// webpack 打包
webpack src/main.js dist/bundle.js
// 写上入口和出口作为参数
```

打包后会在dist文件下，生成一个bundle.js文件，webpack处理了项目直接文件依赖后生成的一个js文件，我们只需要将这个js文件在index.html中引入即可

```
<script src="./dist/bundle.js"></script>
```

每次使用webpack的命令都需要写上入口和出口作为参数，就非常麻烦，创建一个webpack.config.js文件，将这两个参数写到配置中，在运行时，直接读取

```css
const path = require('path')

module.exports = {
    //入ロ:可以是字符串/数组/对象，这里我们入口只有一个，所以写一个字符串即可
  entry: './src/main.js',
  output: {
    // 出口： 通常是一个对象包含path、filename两个属性
    path: path.resolve(__dirname, 'dist'),
    // path通常是绝对路径
    filename: 'bundle.js'
    // 默认情况下，webpack会将生成的路径直接返回给使用者
但是，我们整个程序是打包在dist文件夹下的，所以这里我们需要在路径下再添加一个dist/,后面url的东西会自动加上这个path
    publicPath: 'dist/'
  },
  // npm run test 的映射脚本，会优先在本地找依赖而不是全局依赖
  module: {
    rules: [
      {  
        test: /\.css$/,
        // css-loader只负责将css文件进行加载
        // style-loader负责将样式添加到DOM中
        // 使用多个loader时, 是从右向左
        use: [ 'style-loader', 'css-loader' ]
      },{
        test: /\.less$/,
        use: [{ loader: "style-loader", // creates style nodes from JS strings
        }, { loader: "css-loader" // translates CSS into CommonJS
        }, { loader: "less-loader", // compiles Less to CSS
        }]
      }，
      --------------
      test: /\.(png|jpg|gif|jpeg)$/,
        use: [{ loader: 'url-loader',
            options: {
              // 当加载的图片, 小于limit时, 会将图片编译成base64字符串形式.
              // 当加载的图片, 大于limit时, 需要使用file-loader模块进行加载.
              limit: 13000,
              // 修改文件名称，webpack自动生成32位hash值图片名。img：文件要打包到的文件夹。name：获取图片原来的名字，放在该位置。hash:8：为了防止图片名称冲突，依然使用hash，但是我们只保留8位。ext：使用图片原来的扩展名
              name: 'img/[name].[hash:8].[ext]'
         -------ES6语法转成ES5
        test: /\.js$/,
        // exclude: 排除
        // include: 包含
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
              // 安装的和推荐的不一样这里也不一样
            presets: ['es2015']
        ------------ .vue文件封装处理
        test: /\.vue$/,
        use: ['vue-loader']
    ]
  }
  resolve: {
  // alias: 别名 指定版本
   extensions: ['.js', '.css', '.vue'],
   alias: { 'vue$': 'vue/dist/vue.esm.js' }
  }
}
```

因为一个项目往往依赖特定的webpack版本，全局的版本可能很这个项目的webpack版本不一致，导出打包出现问题。所以通常一个项目，都有自己局部的webpack。

```
npm i webpack3.6.0 -S
// Vue CLI3中已经升级到webpack4，但是它将配置文件隐藏了起来，所以查看起来不是很方便。

通过node_modules/.bin/webpack启动webpack打包
```

每次执行都敲这么一长串有没有觉得不方便呢？我们**可以在package.json的scripts中定义自己的执行脚本**。
package.json中的**scripts的脚本在执行时，会按照一定的顺序寻找命令对应的位置。首先，会寻找本地的node_modules/.bin路径中对应的命令。**如果没有找到，会去全局的环境变量中寻找。

```
npm run build

"scripts": {
	"build": "webpack",
```

## <span id="head45"> loader</span>

webpack会自动处理js之间相关的依赖。但是，在开发中我们不仅仅有基本的js代码处理，我们也需要加载css、图片，也包括一些高级的将ES6转成ES5代码，将TypeScript转成ES5代码，将scss、less转成css，将.jsx、.vue文件转成js文件等等。对于webpack本身的能力来说，对于这些转化是不支持的。那怎么办呢？给webpack扩展对应的loader就可以啦。

loader使用过程：
步骤一：通过npm安装需要使用的loader
步骤二：在webpack.config.js中的modules关键字下进行配置

```
npm i -S style-loader
npm i -s css-loader
```

![quicker_6641193d-0133-426a-8604-869cf115c0fd.png](https://i.loli.net/2021/08/26/pAYfPo1gK8U4B9l.png)



## <span id="head46"> ES6语法处理</span>

阅读webpack打包的js文件，发现写的ES6语法并没有转成ES5，那么就意味着可能一些对ES6还不支持的浏览器没有办法很好的运行我们的代码。

在前面我们说过，如果希望**将ES6的语法转成ES5，那么就需要使用babel。**

```
npm install --save-dev babel-loader@7 babel-core babel-preset-es2015
```

## <span id="head47"> 引入vue.js</span>

```
npm i -S vue
```

  1. runtime-only代码中，不可以有任何的 template(上面的resolve可以解决)

2. runtime-compllek代码中，可以有 template.因为有 compilers可以用于编译 Template

   如果在之后的开发中，你依然使用template，就需要选择Runtime-Compiler。如果你之后的开发中，使用的是.vue文件夹开发，那么可以选择Runtime-only

   ![image.png](https://pic.rmb.bdstatic.com/bjh/1220e0dbeb35366fd081e6e85a6e6686.jpeg)

   ```
   // runtime-compiler(v1)
   // template -> ast -> render -> vdom -> UI
   
   // runtime-only(v2)(1.性能更高 2.下面的代码量更少)
   // render -> vdom -> UI
   ```

   ![image.png](https://pic.rmb.bdstatic.com/bjh/59de652714cce4da068cc8e127876e62.jpeg)

el用于指定Vue要管理的DOM，可以帮助解析其中的指令、事件监听等等。而如果Vue实例中同时指定了template，那么template模板的内容会替换掉挂载的对应el的模板。

## <span id="head48"> .vue文件封装处理</span>

```
npm install vue-loader vue-template-compiler --save-dev
```

## <span id="head49"> plugin</span>

plugin是插件的意思，通常是用于对某个现有的架构进行扩展。webpack中的插件，就是对webpack现有功能的各种扩展，比如打包优化，文件压缩等等。
**loader和plugin区别**
loader主要用于转换某些类型的模块，它是一个转换器。
plugin是插件，它是对webpack本身的扩展，是一个扩展器。

**plugin的使用过程：**
步骤一：通过npm安装需要使用的plugins(某些webpack已经内置的插件不需要安装)
步骤二：在webpack.config.js中的plugins中配置插件。

```css
const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const UglifyjsWebpackPlugin = require('uglifyjs-webpack-plugin')

plugins: [
      new webpack.BannerPlugin('最终版权归aaa所有'),
      new HtmlWebpackPlugin({
          // 这里的template表示根据什么模板来生成index.html
          // 另外，我们需要删除之前在output中添加的publicPath属性,否则插入的script标签中的src可能会有问题

        template: 'index.html'
      }),
      new UglifyjsWebpackPlugin()
  ],
devServer: {
    contentBase: './dist',
    inline: true
  }
```

添加版权的Plugin。该插件名字叫BannerPlugin，属于webpack自带的插件。

打包html的plugin。自动生成一个index.html文件(可以指定模板来生成)。将打包的js文件，自动通过script标签插入到body中

```
npm install html-webpack-plugin --save-dev
```

js压缩的Plugin

在项目发布之前，我们必然需要对js等文件进行压缩处理

```
npm install uglifyjs-webpack-plugin@1.1.1 --save-dev
```

## <span id="head50"> 配置分离</span>

生产、开发配置分离

```
npm i -S webpack-merge
```



![image.png](https://pic.rmb.bdstatic.com/bjh/bf3ec132d98d303ac41d6883aa01d94f.jpeg)

```css
package.js
 "scripts": {
    "build": "webpack --config ./build/prod.config.js",
    "dev": "webpack-dev-server --open --config ./build/dev.config.js"
  },
```



# <span id="head51">Vue Cli</span>

安装Vue脚手架npm install -g @vue/cli

Vue CLI2初始化项目vue init webpack my-project
Vue CLI3初始化项目vue create my-project

vue-cli 3 是基于 webpack 4 打造，vue-cli 2 还是 webapck 3
vue-cli 3 的设计原则是“0配置”，移除的配置文件根目录下的，build和config等目录
vue-cli 3 提供了 vue ui 命令，提供了可视化配置，更加人性化
移除了static文件夹，新增了public文件夹，并且index.html移动到public中。该文件夹原封不动到dist



![image.png](https://pic.rmb.bdstatic.com/bjh/fbb1be351f5ed8a707a6dca05563d792.jpeg)

![image.png](https://pic.rmb.bdstatic.com/bjh/0d951a5aa62806520dd146c256a00ac0.jpeg)

.vuerc删除默认配置

![image.png](https://pic.rmb.bdstatic.com/bjh/e486f22edbd62be7d80df481f73b71d0.jpeg)

![image.png](https://pic.rmb.bdstatic.com/bjh/63aeda8b8a83c939368259e947ebc440.jpeg)

vue.config.js中的配置和node_modules默认配置合并作为webpack的配置



![image.png](https://pic.rmb.bdstatic.com/bjh/9d44b55729ebce364bdc59892efd6917.jpeg)

![image.png](https://pic.rmb.bdstatic.com/bjh/dd2c6780f194c4a6773e11355bf7a3e8.jpeg)

# <span id="head52"> vue-router</span>

改变URL，但是页面不进行整体的刷新。

## <span id="head53"> URL的hash</span>

URL的hash也就是锚点(#), 本质上是改变window.location的href属性.

通过直接赋值location.hash来改变href, 但是页面不发生刷新

![quicker_069f91fd-1016-4cf3-9a9b-23fe0517526e.png](https://i.loli.net/2021/07/22/xn8gklJR9jNHyLc.png)

## <span id="head54"> HTML5的history模式：pushState</span>

history接口是HTML5新增的, 它有五种模式改变URL而不刷新页面.

### <span id="head55">history.pushState(data, title, url)      </span>

​     压栈可以返回

![quicker_9d509388-b3ca-4de4-904f-48620c2af751.png](https://i.loli.net/2021/07/22/pvOXsViJjb9tlyg.png)

history.back() 出栈返回到/foo



### <span id="head56"> history.replaceState()</span>

不能返回

![quicker_015aa8ab-0723-4e10-a881-5fa319d62d09.png](https://i.loli.net/2021/07/22/Jiu6QvIjF1T3yLU.png)

### <span id="head57"> history.go()</span>

相当于history.back

```
this.$router.go(-1) //返回上一层
```

![quicker_e11b3c67-62a4-4c57-85cf-4310dc2ec5a2.png](https://i.loli.net/2021/07/22/yU2knxbjS8Ct7Yc.png)

因为 history.back() 等价于 history.go(-1)

history.forward() 则等价于 history.go(1)

等同于浏览器界面的前进后退。

## <span id="head58"> vue-router</span>

步骤一: 安装vue-router
npm install vue-router --save
步骤二: 在模块化工程中使用它(因为是一个插件, 所以可以通过Vue.use()来安装路由功能)
第一步：导入路由对象，并且调用 Vue.use(VueRouter)
第二步：创建路由实例，并且传入路由映射配置 

第三步：在Vue实例中挂载创建的路由实例



使用vue-router的步骤:
第一步: 创建路由组件
第二步: 配置路由映射: 组件和路径映射关系
第三步: 使用路由: 通过<router-link>和<router-view>



## <span id="head59"> 例子</span>

src/router/index.js  1.创建router实例           4.配置组件和路径的映射关系

```css
// 配置路由相关的信息
import VueRouter from 'vue-router'
import Vue from 'vue'

import Home from '../components/Home'
import About from '../components/About'

// 1.通过Vue.use(插件), 安装插件
Vue.use(VueRouter)

// 1.定义路由
const routes = [

// 4.配置组件和路径的映射关系
  {
    path: '',
    // redirect重定向      默认没有显示首页组件, 必须让用户点击才可以.redirect是重定向, 也就是我们将根路径重定向到/home的路径下, 这样就可以得到我们想要的结果了.
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home
  },
  {
    path: '/about',
    component: About
  },

]

// 1.创建VueRouter实例
const router = new VueRouter({
  // 配置路由和组件之间的应用关系
  routes,
  mode: 'history',
//默认情况下, 路径的改变使用的URL的hash.如果希望使用HTML5的history模式, 进行如下配置
      //localhost:8080/about 没有#
  linkActiveClass: 'active'
})

// 1.将router对象传入到Vue实例
export default router


```

src/main.js  2.挂载到vue实例

```css
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})

```


src/component/about.vue  3.创建路由组件

```css
<template>
  <div>
    <h2>我是关于</h2>
    <p>我是关于内容, 呵呵呵</p>
  </div>
</template>

<script>
  export default {
    name: "About"
  }
</script>

<style scoped>

</style>

```



src/component/home.vue    3.创建路由组件

```css
<template>
  <div>
    <h2>我是首页</h2>
    <p>我是首页内容, 哈哈哈</p>
  </div>
</template>

<script>
  export default {
    name: "Home"
  }
</script>

<style scoped>

</style>

```

src/App.vue  5.

```css
<template>
  <div id="app">
    <h2>我是APP组件</h2>
    <!--<router-link to="/home" tag="button" replace active-class="active">首页</router-link>-->
    <!--<router-link to="/about" tag="button" replace active-class="active">关于</router-link>-->
    <!--<router-link to="/home" tag="button" replace>首页</router-link>-->
    <!--<router-link to="/about" tag="button" replace>关于</router-link>-->
	
//<router-link>: 该标签是一个vue-router中已经内置的组件, 它会被渲染成一个<a>标签.

    <button @click="homeClick">首页</button>
    <button @click="aboutClick">关于</button>

    <router-view></router-view>


        	
  </div>
</template>

<script>
export default {
  name: 'App',
  methods: {
    homeClick() {
      // 通过代码的方式修改路由 vue-router
      // push => pushState
      // this.$router.push('/home')
      this.$router.replace('/home')
      console.log('homeClick');
    },
    aboutClick() {
      // this.$router.push('/about')
      this.$router.replace('/about')
      console.log('aboutClick');
    },

  }
}
</script>

<style>
  /*.router-link-active {*/
    /*color: #f00;*/
  /*}*/

  .active {
    color: #f00;
  }

</style>

```

<router-view>: 该标签会根据当前的路径, 动态渲染出不同的组件.在路由切换时, 切换的是<router-view>挂载的组件, 其他内容不会发生改变.

<router-view>: 该标签会根据当前的路径, 动态渲染出不同的组件.

网页的其他内容, 比如顶部的标题/导航, 或者底部的一些版权信息等会和<router-view>处于同一个等级.

在路由切换时, 切换的是<router-view>挂载的组件, 其他内容不会发生改变.

![quicker_4dd2c74b-0557-4b24-8222-de441a117aaf.png](https://i.loli.net/2021/07/22/17QiHZlJKY3mp6z.png)



## <span id="head60"> router-link/view组件</span>

![quicker_692d4b88-a5c3-489f-9416-522ae23e0681.png](https://i.loli.net/2021/07/22/8Jsq2cAk1MaN3ew.png)

```
<router-link to='/home' tag='li'>

```

属性: to, 用于指定跳转的路径

tag: tag可以指定<router-link>之后渲染成什么组件, 比如上面的代码会被渲染成一个<li>元素, 而不是<a>
replace: replace不会留下history记录, 所以指定replace的情况下, 后退键返回不能返回到上一个页面中
active-class: 当<router-link>对应的路由匹配成功时, 会自动给当前元素设置一个router-link-active的class, 设置active-class可以修改默认的名称.**在进行高亮显示的导航菜单或者底部tabbar时, 会使用到该类.**	但是通常不会修改类的属性, 会直接使用默认的router-link-active即可. 

​	![quicker_deabb020-fffb-4d63-8bea-470698c54f55.png](https://i.loli.net/2021/07/22/fbBFv1JYaNqMC6S.png)

## <span id="head61"> linkActiveClass</span>

exact-active-class类似于active-class, 只是在精准匹配下（路由活跃后）才会出现的class.

![quicker_5351ab94-46a2-46db-8d47-0838cedd8303.png](https://i.loli.net/2021/07/22/mvy38KEHIxsntVF.png)

## <span id="head62"> 路由代码跳转</span>

![quicker_3676c09a-585d-45f0-92a3-667b60260587.png](https://i.loli.net/2021/07/22/mJEZv2adVU6lTh1.png)

## <span id="head63"> 动态路由</span>



router/index.js

```
  {
    path: '/user/:id',
    component: User
  },
```

component/User.vue

```
  <div>
    <h2>我是关于</h2>
    <p>我是关于内容, 呵呵呵</p>
    <h2>{{$route.params.id}}</h2>
  </div>
```

App.vue

```
 <router-link to="/about/123">user name</router-link>

```



```js
                getDepartments().then(response => {
                    if (response && response.status === 200) {
                        if (response.data) {
                            this.deps = response.data
                        }
                    }
                }).catch(error => {
                    console.log('get deps',error)
                })
```



## <span id="head64"> 懒加载</span>

路由中通常会定义很多不同的页面.这个页面最后被打包在哪里呢? 一般情况下, 是放在一个js文件中.但是, 页面这么多放在一个js文件中, 必然会造成这个页面非常的大.如果我们一次性从服务器请求下来这个页面, 可能需要花费一定的时间, 甚至用户的电脑上还出现了短暂空白的情况.

路由懒加载主要作用就是**将路由对应的组件打包成一个个的js代码块**。只有在这个路由被访问到的时候, 才加载对应的组件

**打包的内容**

app自己写的业务代码

manifest为打包的代码进行底层支撑

vendor第三方的东西

![quicker_d5eb4ceb-f42c-4bc0-8511-38478a8e40c5.png](https://i.loli.net/2021/07/22/NxHr816ujEyoTq2.png)

```css
方式一: 结合Vue的异步组件和Webpack的代码分析.
const Home = resolve => { require.ensure(['../components/Home.vue'], () => { resolve(require('../components/Home.vue')) })};
方式二: AMD写法
const About = resolve => require(['../components/About.vue'], resolve);
方式三: 在ES6中, 我们可以有更加简单的写法来组织Vue异步组件和Webpack的代码分割.
const Home = () => import('../components/Home.vue')
{
 path: '/home'
 component: Home
}

```



## <span id="head65"> \$router和\$route</span>

```
$router为VueRouter实例，想要导航到不同URL，则使用$router.push方法
$route为当前router跳转对象里面可以获取name、path、query、params等 
```

## <span id="head66"> 嵌套路由</span>

比如在home页面中, 我们希望通过/home/news和/home/message访问一些内容.

一个路径映射一个组件, 访问这两个路径也会分别渲染两个组件.



创建对应的子组件, 并且**在路由映射中配置对应的子路由**.
**在上级组件内部使用<router-view>标签**

![image-20210722142703876](C:\Users\WenChao Ding\AppData\Roaming\Typora\typora-user-images\image-20210722142703876.png)

### <span id="head67"> 嵌套默认路径</span>

默认显示message

![quicker_c3e5becd-b1c5-4eb2-b425-d981c093a0d8.png](https://i.loli.net/2021/07/22/LZHVu2R4PbD9aNF.png)

## <span id="head68"> 传递参数</span>

点击我的，跳转到我的详情，传入的userid

### <span id="head69"> params的类型:</span>

配置路由格式: /router/:id
传递的方式: 在path后面跟上对应的值（to:）传递后形成的路径: /router/123, /router/abc

### <span id="head70"> query的类型:</span>

配置路由格式: /router, 也就是普通配置
传递的方式: 对象中使用query的key作为传递方式
传递后形成的路径: /router?id=123, /router?id=abc

**传递参数方式一****: <router-link>**

![quicker_fe512a71-c838-4bc2-a431-fa9c21ca188e.png](https://i.loli.net/2021/07/22/kMov6nuQ8a9AKFT.png)

**传递参数方式二****: JavaScript****代码**

![quicker_800ffb95-2933-4cfa-9de9-35008b22e580.png](https://i.loli.net/2021/07/22/73GeAxobJUs6MHy.png)

## <span id="head71"> 导航守卫</span>



**在一个**SPA**应用中**, **如何改变网页的标题呢**?
网页标题是通过<title>来显示的, 但是SPA只有一个固定的HTML, 切换不同的页面时, 标题并不会改变.
但是我们可以通过JavaScript来修改<title>的内容.window.document.title = '新的标题'.
那么在Vue项目中, 在哪里修改? 什么时候修改比较合适呢?
**什么是导航守卫**?
vue-router提供的导航守卫主要用来**监听路由的进入和离开的.**
**vue-router提供了beforeEach和afterEach的钩子函数, 它们会在路由即将改变前和改变后触发.**



**我们可以利用beforeEach来完成标题的修改.**
首先, 我们可以在钩子当中定义一些标题, 可以利用meta来定义
其次, 利用导航守卫,修改我们的标题.

![image.png](https://pic.rmb.bdstatic.com/bjh/370d2597aee0a548c47552431789912a.jpeg)

**导航钩子的三个参数解析**:
to: 即将要进入的目标的路由对象.
from: 当前导航即将要离开的路由对象.
next: 调用该方法后, 才能进入下一个钩子.

补充一:如果是后置钩子, 也就是afterEach, 不需要主动调用next()函数.
补充二: 上面我们使用的导航守卫, 被称之为全局守卫.

路由独享的守卫.
组件内的守卫.

[官网进行学习](https://router.vuejs.org/zh/guide/advanced/navigation-guards.html)





## <span id="head72"> keep-alive遇见vue-router</span>

keep-alive 是 Vue 内置的一个组件，可以使被包含的组件保留状态，或避免重新渲染。它们有两个非常重要的属性:

- include - 字符串或正则表达，只有匹配的组件会被缓存

- exclude - 字符串或正则表达式，任何匹配的组件都不会被缓存


router-view 也是一个组件，如果直接被包在 keep-alive 里面，所有路径匹配到的视图组件都会被缓存：

```
<keep-alive>
	<router-view>
		// 所有路径匹配到的视图组件都会被缓存
	</router-view>
</keep-alive>
```



# <span id="head73"> Promise</span>

Promise是异步编程的一种解决方案。



当一个操作开始执行后，主程序无需等待它的完成，可以继续向下执行。此时该操作可以跟主程序同时（并发）执行。这种操作我们就称之为**异步操作**



常见的异步操作例如：添加定时器 setTimeout/setInterval、执行某个动画 animate、发起网络请求 request

**网络请求的回调地狱**

![image.png](https://pic.rmb.bdstatic.com/bjh/47ba11b7ce9377e6e28f4660ff2d6ac9.jpeg)

我们需要通过一个url1从服务器加载一个数据data1，data1中包含了下一个请求的url2，我们需要通过data1取出url2，从服务器加载数据data2，data2中包含了下一个请求的url3，我们需要通过data2取出url3，从服务器加载数据data3，data3中包含了下一个请求的url4，发送网络请求url4，获取最终的数据data4

正常情况下，不会有什么问题，可以正常运行并且获取我们想要的结果。但是，这样额代码难看而且不容易维护。我们更加期望的是一种更加优雅的方式来进行这种异步操作。

**定时器异步事件解析**

![image.png](https://pic.rmb.bdstatic.com/bjh/8ca2e2a1a0bc98d8380c97543a1f688b.jpeg)

这是我们过去的处理方式，我们将它换成Promise代码这个例子会让我们感觉脱裤放屁

![image.png](https://pic.rmb.bdstatic.com/bjh/89e0fc790a3755b32a456a7218b2191b.jpeg)

- new Promise很明显是创建一个Promise对象
- 小括号中((resolve, reject) => {})也很明显就是一个函数，而且我们这里用的是之前刚刚学习过的箭头函数。
- resolve和reject它们两个也是函数，通常情况下，我们会根据请求数据的成功和失败来决定调用哪一个。
- **如果是成功的，那么通常我们会调用resolve(messsage)，这个时候，我们后续的then会被回调。**
  **如果是失败的，那么通常我们会调用reject(error)，这个时候，我们后续的catch会被回调。** 

## <span id="head74"> Promise三种状态</span>

当我们开发中有异步操作时, 就可以给异步操作包装一个Promise
异步操作之后会有三种状态我们一起来看一下这三种状态:

- pending：等待状态，比如正在进行网络请求，或者定时器没有到时间。
- fulfill：满足状态，当我们主动回调了resolve时，就处于该状态，并且会回调.then()
- reject：拒绝状态，当我们主动回调了reject时，就处于该状态，并且会回调.catch()

![](https://pic.rmb.bdstatic.com/bjh/49d0311697473b5a0b524466dee11213.jpeg)

## <span id="head75"> Promise链式调用</span>

我们在看Promise的流程图时，发现无论是then还是catch都可以返回一个Promise对象。所以，我们的代码其实是可以进行链式调用的：这里我们直接通过Promise包装了一下新的数据，将Promise对象返回了

Promise.resovle()：将数据包装成Promise对象，并且在内部回调resolve()函数
Promise.reject()：将数据包装成Promise对象，并且在内部回调reject()函数

![image.png](https://pic.rmb.bdstatic.com/bjh/004c99089e2fc2b520020d56110ffb3b.jpeg)

Promise是怎么知道第一个函数什么时候结束的？ 然后再开始执行下一个？我们**调用了一个resolve()函数，这句代码非常的关键。这其实就是在通知Promise**，当前这个函数结束啦，你可以开始执行下一个。 这时Promise就会去执行then里面的函数了。

**那如果我有ajaxA、ajaxB、ajaxC三个异步任务，想按照先A后B再C的顺序执行，像这样写行吗？**

new Promise(A).then(B); 这句话表示， 只能保证A和B的顺序，一旦A执行完，B开始后，这次承诺也就兑现了，Promise对象也就失效了

```css
new Promise(函数1(resolve){
    ajaxA("xxxx", function(){
        resolve();//通知Promise该任务结束
    })    
}).then(函数2(){
    //在函数2开始运行后，第一次创建的Promise对象完成使命，已经不能再继续工作。
    //此时，我们创建并返回了新的Promise对象
    return new Promise(function(resolve){
        ajaxB("xxxx", function(){
            resolve();//通知新的Promise对象该任务结束
        })    
    })
}).then(函数3(){ //尽管这里使用了链式调用，但负责执行函数3的，已经是新的Promise对象了
    // 如果，我们还有ajaxD需要顺序调用
    // 那就必须在这里重新new Promise()对象了
    ajaxC("xxx", function(){     })
})  
```

 如果我有 A,B,C 三个异步任务，ABC同时开始执行，当A,B,C三个任务全部都结束时，执任务D，

```css
Promise.all([new Promise(A), new Promise(B), new Promise(C)])
.then(function(){
    D();
});
```

那如果我希望A,B,C 其中任意一个任务完成，就马上开始任务D，该怎么做？

```
Promise.race([new Promise(A), new Promise(B), new Promise(C)])
.then(function(){
   D();
});
```



## <span id="head76">async \ await</span>

```css
//假设有4个异步方法要按顺序调用
new Promise(function(resolve){
    ajaxA("xxxx", ()=> { resolve(); })    
}).then(function(){
    return new Promise(function(resolve){
        ajaxB("xxxx", ()=> { resolve(); })    
    })
}).then(function(){
    return new Promise(function(resolve){
        ajaxC("xxxx", ()=> { resolve(); })    
    })
}).then(function(){
    ajaxD("xxxx");
});  

```

!!!!!!!!语法上不够简洁, 我们可以稍微改造一下

```css

//将请求改造成一个通用函数
function request(options) {
    //.....
    return new Promise(....); //使用Promise执行请求,并返回Promise对象
}
//于是我们就可以来发送请求了
request("http://xxxxxx")
.then((data)=>{
    //处理data
})
request("ajaxA")
.then((data)=>{
   //处理data
   return request("ajaxB")
})
.then((data)=>{
   //处理data
   return request("ajaxC")
})
.then((data)=>{
   //处理data
   return request("ajaxD")
})


```

!!!!!!!!如果我能像使用同步代码那样, 使用Promise就好了于是, async \ await出现了

```css
async function load(){
        //请求失败后的处理, 可以使用try-catch来进行
    //注意当一个函数被async修饰以后, 它的返回值会被自动处理成Promise对象
    await request("ajaxA");         
    //那么这里就是在等待ajaxA请求的完成
    await request("ajaxB");
    await request("ajaxC");         
    //后一个请求需要前一个请求的结果怎么办呢?    request("ajaxB", data1);
    //await不仅等待Promise完成, 而且还拿到了resolve方法的参数
    await request("ajaxD");
}

await关键字使用的要求非常简单, 后面调用的函数要返回一个Promise对象
load()这个函数已经不再是普通函数, 它出现了await这样"阻塞式"的操作
因此async关键字在这是不能省略的
```





# <span id="head77"> Vuex</span>

官方解释：Vuex 是一个专为 Vue.js 应用程序开发的**状态管理模式**。
它采用 **集中式存储管理** 应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化。
Vuex 也集成到 Vue 的官方调试工具 devtools extension，提供了诸如零配置的 time-travel 调试、状态快照导入导出等高级调试功能。
**状态管理到底是什么？**
你可以简单的将其看成把需要多个组件共享的变量全部存储在一个对象里面。然后，将这个对象放在顶层的Vue实例中，让其他组件可以使用。那么，多个组件是不是就可以共享这个对象中的所有变量属性
**Vuex就是为了提供这样一个在多个组件间共享状态的插件，用它就可以了。**



**什么状态时需要我们在多个组件间共享的呢**？

如果你做过大型开放，你一定遇到过多个状态，在多个界面间的共享问题。
比如商品的收藏、购物车中的物品等等。这些状态信息，我们都可以放在统一的地方，对它进行保存和管理，而且它们还是响应式的

## <span id="head78"> 单/界面的状态管理</span>

![image.png](https://pic.rmb.bdstatic.com/bjh/18d90940b7f793dd934133d6d9d19514.jpeg)

当我们使用devtools时, 可以devtools可以帮助我们捕捉mutation的快照。但是如果是异步操作, 那么devtools将不能很好的追踪这个操作什么时候会被完成.

Vue已经帮我们做好了单个界面的状态管理，但是如果是多个界面呢？

- 多个视图都依赖同一个状态（一个状态改了，多个界面需要进行更新）

- 不同界面的Actions都想修改同一个状态（Home.vue需要修改，Profile.vue也需要修改这个状态）

也就是说对于某些状态(状态1/状态2/状态3)来说只属于我们某一个试图，但是也有一些状态(状态a/状态b/状态c)属于多个试图共同想要维护的

- 状态1/状态2/状态3你放在自己的房间中，你自己管理自己用，没问题。

- 但是状态a/状态b/状态c我们希望交给一个大管家来统一帮助我们管理！！！

- 没错，Vuex就是为我们提供这个大管家的工具。

全局单例模式（大管家）

- 我们现在要做的就是将共享的状态抽取出来，交给我们的大管家，统一进行管理。
- 之后，你们每个试图，按照我规定好的规定，进行访问和修改等操作。
- 这就是Vuex背后的基本思想。

[![image.png](https://pic.rmb.bdstatic.com/bjh/08d7543a75b4054c2e3ad90c78ccc045.jpeg)

创建一个文件夹store，并且在其中创建一个index.js文件

main.js文件，导入store对象，并且放在new Vue中。这样，在其他Vue组件中，我们就可以通过this.$store的方式，获取到这个store对象了

![image.png](https://pic.rmb.bdstatic.com/bjh/4cbab20df9d836c1a855f3145bdd4faf.jpeg)

我们通过提交mutation的方式，而非直接改变store.state.count。这是因为Vuex可以更明确的追踪状态的变化，**所以不要直接改变store.state.count的值。**



## <span id="head79"> State单一状态树</span>

**Vuex提出使用单一状态树, 什么是单一状态树呢？**
英文名称是Single Source of Truth，也可以翻译成单一数据源。

**但是，它是什么呢？我们来看一个生活中的例子。**
我们知道，在国内我们有很多的信息需要被记录，比如上学时的个人档案，工作后的社保记录，公积金记录，结婚后的婚姻信息，以及其他相关的户口、医疗、文凭、房产记录等等（还有很多信息）。这些信息被分散在很多地方进行管理，有一天你需要办某个业务时(比如入户某个城市)，你会发现你需要到各个对应的工作地点去打印、盖章各种资料信息，最后到一个地方提交证明你的信息无误。

**这个和我们在应用开发中比较类似：**

- 如果你的状态信息是保存到多个Store对象中的，那么之后的管理和维护等等都会变得特别困难。
- 所以Vuex也使用了单一状态树来管理应用层级的全部状态。
- 单一状态树能够让我们最直接的方式找到某个状态的片段，而且在之后的维护和调试过程中，也可以非常方便的管理和维护。

```css
<!-- <h3>当前最新的count值为：{{$store.state.count}}</h3> -->
{{$store.getters.showNum}}

btnHandler2() {
	// commit 的作用，就是调用 某个 mutation 函数
	this.$store.commit('addN', 3)
},

// 异步地让 count 自增 +1
btnHandler3() {
	// 这里的 dispatch 函数，专门用来触发 action
	this.$store.dispatch('addNAsync', 5)
}


// main.js
import store from './store'
new Vue({
  store,
  render: h => h(App)
}).$mount('#app')


// store.js
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    count: 0
  },
  // 只有 mutations 中定义的函数，才有权利修改 state 中的数据
    // 参数被称为是mutation的载荷(Payload)
  mutations: {
    addN(state, step) {
      state.count += step
    }
  },
  actions: {
    addNAsync(context, step) {
      setTimeout(() => {
        // 在 actions 中，不能直接修改 state 中的数据；
        // 必须通过 context.commit() 触发某个 mutation 才行
        context.commit('addN', step)
      }, 1000)
    }
  },
  getters: {
    showNum(state) {
      return '当前最新的数量是【' + state.count + '】'
    }
  }
})
```



Vuex的store中的state是响应式的, 当state中的数据发生改变时, Vue组件会自动更新.这就要求我们必须遵守一些Vuex对应的规则:提前在store中初始化好所需的属性.
当给state中的对象添加新属性时, 使用下面的方式:

- 方式一: 使用Vue.set(obj, 'newProp', 123)
- 方式二: 用新对象给旧对象重新赋值

当我们的项目增大时, Vuex管理的状态越来越多, 需要更新状态的情况越来越多, 那么意味着Mutation中的方法越来越多. 一种很常见的方案就是使用**常量**替代Mutation事件的类型.我们可以将这些常量放在一个单独的文件中, 方便管理以及让整个app所有的事件类型一目了然.

![quicker_469eaaf7-e750-4bec-af44-b4cce7859203.png](https://i.loli.net/2021/08/26/7vEhnDzu4rsBITa.png)

希望在Vuex中进行一些异步操作, 比如网络请求, 必然是异步的. 这个时候怎么处理呢?
Action类似于Mutation, 但是是用来代替Mutation进行异步操作的.context是和store对象具有相同方法和属性的对象.也就是说, 我们可以通过context去进行commit相关的操作, 也可以获取context.state等.。但是注意, 这里它们并不是同一个对象



在Vue组件中, 如果我们调用action中的方法, 那么就需要使用dispatch，

![quicker_fc798e70-cd2d-45ee-8e52-24d41b34e5b5.png](https://i.loli.net/2021/08/26/1V4eFiBOoYUjmQ2.png)



在Action中, 我们可以将异步操作放在一个Promise中, 并且在成功或者失败后, 调用对应的resolve或reject.

![quicker_ba4a4baa-51ed-4fbd-8f91-e71ba8cd3437.png](https://i.loli.net/2021/08/26/gXxyzY9bVtaT8QZ.png)

当应用变得非常复杂时,store对象就有可能变得相当臃肿.为了解决这个问题, Vuex允许我们将store分割成模块(Module), 而每个模块拥有自己的state、mutation、action、getters等

mutation和getters接收的第一个参数是局部状态对象。虽然, 我们的doubleCount和increment都是定义在对象内部的.
但是在调用的时候, 依然是通过this.$store来直接调用的.

![quicker_0e3d2f58-d3ba-4548-9a89-be3923771e1c.png](https://i.loli.net/2021/08/26/PuzGYKSW9Ve3oCl.png)





![quicker_fd25da4c-33d8-4e70-bef8-a9e34270cf2c.png](https://i.loli.net/2021/08/26/WhxT8YNXF3ryb2V.png)

# <span id="head80"> axios</span>

功能特点:
在浏览器中发送 XMLHttpRequests 请求、在 node.js 中发送 http请求、支持 Promise API、拦截请求和响应、转换请求和响应数据

支持多种请求方式:

axios(config)、axios.request(config)、axios.get(url[, config])、axios.delete(url[, config])、axios.head(url[, config])、axios.post(url[, data[, config]])、axios.put(url[, data[, config]])、axios.patch(url[, data[, config]])



axios必须先导入才可以使用

使用get或post方法即可发送对应的请求

then方法中的回调函数会在请求成功或失败时触发

通过回调函数的形参可以获取响应内容,或错误信息。

文档传送门https://github.com/axios/axios

axios回调函数中的this已经改变,无法访问到data中数据，把this保存起来,回调函数中直接使用保存的this即可

axios回调函数中this指向改变了，需要额外的保存一份



```css
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    axios.get("地址?key=value&key2=values").then(function(response){},function(err){})
    axios.post("地址",{key:value,key2:value2}).then(function(response){},function(err){})

```

## <span id="head81"> axios使用</span>

```css
<body>
    <input type="button" value="get请求" class="get">
    <input type="button" value="post请求" class="post">
    <!-- 官网提供的 axios 在线地址 -->
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script>
        /*
            接口1:随机笑话
            请求地址:https://autumnfish.cn/api/joke/list
            请求方法:get
            请求参数:num(笑话条数,数字)
            响应内容:随机笑话
        */
        document.querySelector(".get").onclick = function () {
            axios.get("https://autumnfish.cn/api/joke/list?num=6")
            .then(function (response) {
                console.log(response);
              },function(err){
                  console.log(err);
              })
        }
        /* 
             接口2:用户注册
             请求地址:https://autumnfish.cn/api/user/reg
             请求方法:post
             请求参数:username(用户名,字符串)
             响应内容:注册成功或失败
         */
        document.querySelector(".post").onclick = function () {
            axios.post("https://autumnfish.cn/api/user/reg",{username:"盐焗西兰花"})
            .then(function(response){
                console.log(response);
                console.log(this.skill);
            },function (err) {
                console.log(err);
              })
          }

    </script>
</body>

```

## <span id="head82"> axios+vue</span>

```css
<body>
    <div id="app">
        <input type="button" value="获取笑话" @click="getJoke">
        <p> {{ joke }}</p>
    </div>
    <!-- 官网提供的 axios 在线地址 -->
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script>
        /*
            接口:随机获取一条笑话
            请求地址:https://autumnfish.cn/api/joke
            请求方法:get
            请求参数:无
            响应内容:随机笑话
        */
        var app = new Vue({
            el:"#app",
            data:{
                joke:"很好笑的笑话"
            },
            methods: {
                getJoke:function(){
                    // console.log(this.joke);
                    var that = this;
                    axios.get("https://autumnfish.cn/api/joke").then(function(response){
                        // console.log(response)
                        console.log(response.data);
                        // console.log(this.joke);
                        that.joke = response.data;
                    },function (err) {  
                        console.log(err)
                    })
                }
            },
        })

    </script>
</body>
```

## <span id="head83"> 发送并发请求</span>

使用axios.all, 可以放入多个请求的数组.axios.all([]) 返回的结果是一个数组，使用 axios.spread 可将数组 [res1,res2] 展开为 res1, res2

![image.png](https://pic.rmb.bdstatic.com/bjh/a11cfe6390cc9327018ae17ef8ff8807.jpeg)

## <span id="head84"> 全局配置</span>

![image.png](https://pic.rmb.bdstatic.com/bjh/206b3d86e7e9783b266245ed986a05ba.jpeg)



**请求地址** url: '/user',
**请求类型** method: 'get',
**请根路径** baseURL: 'http://www.mt.com/api',
**请求前的数据处理** transformRequest:[function(data){}],
**请求后的数据处理** transformResponse: [function(data){}],
**自定义的请求头** headers:{'x-Requested-With':'XMLHttpRequest'},
**URL查询对象** params:{ id: 12 },

**查询对象序列化函数**p aramsSerializer: function(params){ }
**request body** data: { key: 'aa'},
**超时设置s** timeout: 1000,
**跨域是否带Token** withCredentials: false,
**自定义请求处理** adapter: function(resolve, reject, config){},
**身份验证信息** auth: { uname: '', pwd: '12'},
**响应的数据格式 json / blob /document /arraybuffer / text / stream** responseType: 'json',

## <span id="head85"> axios的实例</span>

为什么要创建axios的实例呢?

当我们从axios模块中导入对象时, 使用的实例是默认的实例。当给该实例设置一些默认配置时, 这些配置就被固定下来了.

但是后续开发中, 某些配置可能会不太一样。比如某些请求需要使用特定的baseURL或者timeout或者content-Type等。这个时候, 我们就可以创建新的实例, 并且传入属于该实例的配置信息.

![image.png](https://pic.rmb.bdstatic.com/bjh/48de66afd1c9fecb9b575a537e4eb561.jpeg)

## <span id="head86"> axios封装</span>

![image.png](https://pic.rmb.bdstatic.com/bjh/fff9e15d7124e9eb1dd059caa0efb4f7.jpeg)

## <span id="head87"> 拦截器</span>

axios提供了拦截器，用于我们在发送每次请求或者得到相应后，进行对应的处理。

![image.png](https://pic.rmb.bdstatic.com/bjh/e3282c91d0d8e190f2164999c754512b.jpeg)

**请求拦截可以做到的事情**：

![image.png](https://pic.rmb.bdstatic.com/bjh/389ee079b6b96e7182fe424e3756b8a3.jpeg)



请求拦截中错误拦截较少，通常都是配置相关的拦截。可能的错误比如请求超时，可以将页面跳转到一个错误页面中

**响应拦截中完成的事情**：

响应的成功拦截中，主要是对数据进行过滤。

响应的失败拦截中，可以根据status判断报错的错误码，跳转到不同的错误提示页面。

![image.png](https://pic.rmb.bdstatic.com/bjh/0d61ffcabdae31abaee1ebcc99107f78.jpeg)





# <span id="head88"> 过滤器</span>

价格数字保留两位小数，前面加￥

```css
<h2>总价格: {{totalPrice | showPrice}}</h2>
filters: {
showPrice(price) {
return '¥' + price.toFixed(2)
}
```

# <span id="head89"> npm</span>

```
--save || -S // 运行依赖（发布）
–save-dev || -D //开发依赖（辅助）
```

安装模块到项目node_modules目录下。它们会把依赖包添加到package.json 文件。运行 npm install 初始化项目时，会将模块下载到项目目录下。

–save ： dependencies 键下，发布后还需要依赖的模块，譬如像jQuery库或者Angular框架类似的，我们在开发完后后肯定还要依赖它们，否则就运行不了。

–save-dev ： devDependencies 键下，开发时的依赖比如安装 js的压缩包gulp-uglify 因为我们在发布后用不到它，而只是在我们开发才用到它。

```
npm install moduleName -g 
```

安装模块到全局，不会在项目node_modules目录中保存模块包。

不会修改package.json文件。

## <span id="head90"> sass</span>

安装

```js
cnpm i -D sass
```

使用 sass

```html
<style lang="scss" scoped></style>
```

## <span id="head91"> eslint</span>

取消eslint检查， 根目录创建 **vue.config.js** 文件

```
module.exports = {
  lintOnSave: false,  // ！！！
  devServer: {  //  ？？？
    overlay: {
      warnings: true,
      errors: true
    }
  }
}
```

## <span id="head92"> less-loader</span>

```
npm i -S less-loader less
```

TypeError: this.getOptions is not a function  at Object.lessLoader 在vue项目中less报错问题解决

less版本问题，卸载重新安装指定版本号cnpm install less@3.9.0 -D
cnpm install less-loader@5.0.0 -D

### <span id="head93"> url-loader</span>

```
npm i -S url-loader  file-loader
```

图片处理，我们使用url-loader来处理，大于8kb的图片，会通过file-loader进行处理



# <span id="head94"> Vue.nextTick</span>

在下次 `DOM` 更新循环结束之后执行延迟回调。在修改数据之后立即使用这个方法，获取更新后的 `DOM`。



**什么时候需要用Vue.nextTick():**

- 你在`Vue`生命周期的**created()钩子函数进行的`DOM`操作一定要放在`Vue.nextTick()`的回调函数中**。原因是什么呢，原因是在`created()`钩子函数执行的时候`DOM` 其实并未进行任何渲染，而此时进行`DOM`操作无异于徒劳，所以此处一定要将`DOM`操作的`js`代码放进`Vue.nextTick()`的回调函数中。与之对应的就是mounted钩子函数，因为该钩子函数执行时所有的`DOM`挂载和渲染都已完成，此时在该钩子函数中进行任何`DOM`操作都不会有问题 。
- 在数据变化后要执行的某个操作，当你设置 `vm.someData = 'new value'`，`DOM`并不会马上更新，而是在异步队列被清除，也就是下一个事件循环开始时执行更新时才会进行必要的`DOM`更新。如果此时你想要根据更新的 `DOM` 状态去做某些事情，就会出现问题。为了在数据变化之后等待 `Vue` 完成更新 `DOM` ，**可以在数据变化之后立即使用 `Vue.nextTick(callback)` 。这样回调函数在 `DOM` 更新完成后就会调用。**
-  **mounted 不会承诺所有的子组件也都一起被挂载**。如果你希望等到整个视图都渲染完毕，可以用 `vm.$nextTick` 替换掉 `mounted`：





# <span id="head95"> img的src属性绑定</span>

```js
<img :src="imgUrl">

data() {
imgUrl:require('./assets/logo.png')
}

```



# <span id="head96"> watch</span>

## <span id="head97"> 监听变量</span>

```kotlin
  watch: {
   a(val, oldVal){//普通的watch监听a
         console.log("a: "+val, oldVal);
     }
  }
```

## <span id="head98"> 监听对象具体属性</span>

```jsx
watch: {
　　'queryData.name'(newValue, oldValue) {
　　　　console.log(newValue)
    }
}

也可以借助 computed 计算属性来完成。
computed: {
  getName: function() {
    return this.queryData.name
  }
}
```

watch 的用法有个特点，就是当值第一次绑定的时候，不会执行监听函数，只有值发生改变才会执行。如果我们需要在最初绑定值得时候也执行函数，就需要用到 immediate 属性。

当需要监听复杂的数据类型（对象）的改变时，普通的 watch 方法无法坚挺到对象内部属性的改变，只有 data 中的数据才能监听到变化，此时就需要使用 deep 属性对对象进行深度监听。

```kotlin
 name: {
      handler (val) {
        this.watchName = val;
      },
      immediate: true；
      deep: true //当对象属性较多时，每个属性值得变化都会执行 handler 。如果只需要监听对象中一个属性的值，则可以使用字符串的形式监听对象属性
}
```

# <span id="head99"> 恢复初始数据</span>

1. this.$options.data() 这个是vue原始的数据，就是你页面刚加载时的data

2. this.$data 这个是现在阶段的vue数据，就是你改变data的数据

   ```js
   this.base = this.$options.data().base
   
   Object.assign(this.$data, this.$options.data())
   ```

   

# <span id="head100"> 搜索，翻页用不同的表单</span>
```js
if (type === 'search') {
	this.refs.searchFrom.validate(valid =>{
		if (valid) this.searchFormRecorder = _.cloneDeep(this。searchform)
	}
}

querylistkeyword(this.searchFormRecorder).then(response=> {

})
```

# <span id="head101"> 删完最后一页的数据后跳到上一页</span>

```js
batchdeleteknowledge(this.selectedlist).then(response => {
	if(response & response.status == 200){
		this.ssuccesspop('成功')
		//删掉最后一页的数据，页码减一
		this.total -= this.deletetype = 'batch' ? this.selectedList.length: 1
		const pageNum = Math.ceil(this.total / this.searchForm.pagesize)
		// 当前页数大于实际页数，改当前页为最后一页
		if (pageNum < this.searchForm.pageNum) this.searchForm.pageNum = pageNum
		this.getData('search')
}).finally(() => {
	this.selectedList = []
})


```

# <span id="head102"> 拖拽排序</span>

```js
sortFile() {


}
const that = this
const element = document.querySelectorall('file-list')[0]
new Sortable(element,
onend: function(event){
	//入到新的位置
	that.form.attachmentList.splice(event.newindex, 0, that.form.attachmentList.splice(event.oldlndex, 1)[0])
	const attachmentList = that.form.attachmentList.slice(0)
	that.$nextTick(() => {
		that.form.attachmentList = attachmentList
	}
}

```

