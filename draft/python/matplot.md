|   |   |
|:-:|:-:|
|柱形图|主要用于比较各组数据之间的差别或数据变化情况。|
|折线图|趋势分析|
|饼图|主要用于各部分占整体的多少说明。|
|散点图|查找变量之间的相关性。|
|雷达图|各项指标整体情况分析。|
|矩形树图|各个子项目占整体的多少。|
|桑基图|电商进行流量来源去向分析|
|漏斗图|基于用户行为步骤，查看转化率情况。|
|箱线图|提供有关数据位置和分散情况的关键信息|
>![](https://upload-images.jianshu.io/upload_images/18339009-c790fca5003a8055.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-6a8671ea6a2e9fd2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-9521417629e3eca8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




![](https://upload-images.jianshu.io/upload_images/18339009-c787f514bf4767ac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/18339009-c2d69f34fd1ab865.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
RGB=220,38,36 Hex=#dc2624
RGB=43,71,80 Hex=#2b4750
RGB=69,160,162 Hex=#45a0a2
RGB=232,122,89 Hex=#e87a59
RGB=125,202,169 Hex=#7dcaa9
RGB=100,158,125 Hex=#649e7d 
RGB=220,128,24 Hex=#dc8018
RGB=200,159,145Hex=#c89f91
RGB=108,109,108 Hex=#6c6d6c 
RGB=79,98,104 Hex=#4f6268
RGB=199,204,207 Hex=#c7cccf
```
![](https://upload-images.jianshu.io/upload_images/18339009-b9ec584723ad01df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 基础 (primitives) 类：
线 (line), 点 (marker), 文字 (text), 图例 (legend), 网格 (grid), 标题 (title), 图片 (image) 等。

# 容器 (containers) 类：
图 (figure), 坐标系 (axes), 坐标轴 (axis) 和刻度 (tick)



# 显示中文
```
plt.rcParams['font.sans-serif'] = [u'SimHei']
plt.rcParams['axes.unicode_minus'] = False
```
# 添加标题
```
plt.title('这是一个示例标题')
```
# 添加文字
```
plt.text(-2.5,30,'function y=x*x')
```
# 添加注释
xy：为备注的坐标点。xytext：备注文字的坐标(默认为xy的位置)。arrowprops：在xy和xytext之间绘制一个箭头
```
plt.annotate('这是一个示例注释',xy=(0,1),xytext=(-2,22),arrowprops={'headwidth':10,'facecolor':'r'})
```
# 设置坐标轴名称
```
plt.xlabel('示例x轴')
plt.ylabel('示例y轴')
```
# 添加图例
```
plt.legend(['生活','颜值','工作','金钱'])
```
# 显示数学公式
```
plt.text(2,4,r'$ \alpha \beta \pi \lambda \omega $',size=25)
```
# 显示网格
```
plt.grid(color='g',linewidth='1',linestyle='-.')
```
# 调整坐标轴刻度
同时调整x轴和y轴：
```
plt.locator_params(nbins=20)
```
只调整x轴：
```
plt.locator_params(‘'x',nbins=20)
```
只调整y轴：
```
plt.locator_params(‘'y',nbins=20)
```
# x轴和y轴分别显示20个
```
plt.locator_params(nbins=20)
```
# 调整坐标轴范围
显示坐标轴，plt.axis(),4个数字分别代表x轴和y轴的最小坐标，最大坐标
调整x为10到25
```plt.xlim(xmin=10,xmax=25)```
# 调整日期自适应
```
x=pd.date_range('2020/01/01',periods=30)
y=np.arange(0,30,1)
plt.plot(x,y)
plt.gcf().autofmt_xdate()
```
# 添加双坐标轴
```
y1=x*x
y2=np.log(x)
p1 = plt.plot(x,y1)
# 添加一个坐标轴，默认0到1
plt.twinx()
p2 = plt.plot(x,y2,'r')
plt.show()

plt.legend(p1+p2, ['pv', 'uv'])

```
# 填充区域
```
x=np.linspace(0,5*np.pi,1000)
y1=np.sin(x)
y2=np.sin(2*x)
plt.plot(x,y1)
plt.plot(x,y2)
plt.fill(x,y1,'g')
plt.fill(x,y2,'r')
# fill_beween填充函数交叉区域
plt.fill_between(x,y1,y2,where=y1>y2,interpolate=True)
```
# 切换样式,matplotlib支持多种样式，可以查看所有的样式
```
plt.style.available 
```
```
plt.style.use('ggplot')

```



# 图
###### 图中添加文字
```plt.text(0.5, 0.5, 'Figure', ha='center', va='center', size=20, alpha=.5)```

alpha 设置字体透明度 (0.5 是半透明)

###### 图中添加图片
```
from PIL import Image
# Image.open() 将图片转成像素存在 ndarray 中
im = np.array(Image.open('Houston Rockets.png'))
plt.imshow(im)
```
###### 在图中可以添加基本元素「折线」。
```
plt.figure()
plt.plot([0,1],[0,1])
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-9de4d6936957be09.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 坐标系 & 子图
画东西，看起来是在图 (Figure) 里面进行的，实际上是在坐标系 (Axes) 里面进行的。**一幅图中可以有多个坐标系**，因此在坐标系里画东西更方便 (有些设置使用起来也更灵活)。
### 子图
```
 fig, axes=plt.subplots(nrows=1, ncols=2, figsize=(14,6))
axes[0].scatter(p_AMZN,p_BABA)
```
```
plt.subplot(2,1,1)
plt.xticks([]),plt.yticks([])
plt.text(0.5,0.5,'subplot(2,1,1)",ha='center',va='center',size=20,alpha=.5)
plt.subplot(2,1,2)
plt.xticks([]),plt.yticks([])
plt.text(0.5,0.5,'subplot(2,1,2)',ha='center',va='center',size=20,alpha=.5)
plt.show()
```

### 坐标系
坐标系比子图更通用，有两种生成方式
用 gridspec 包加上 subplot() 
用 plt.axes()

##### 不规则网格
```
import matplotlib.gridspec as gridspec
G = gridspec.GridSpec(3, 3)
ax1 = plt.subplot(G[0, :])
plt.xticks([]), plt.yticks([])
plt.text(0.5, 0.5, 'Axes 1', ha='center', va='center', size=20, alpha=.5)
ax2 = plt.subplot(G[1, :-1])
plt.xticks([]), plt.yticks([])
plt.text(0.5, 0.5, 'Axes 2', ha='center', va='center', size=20, alpha=.5)
ax3 = plt.subplot(G[1:, -1])
plt.xticks([]), plt.yticks([])
plt.text(0.5,0.5,'Axes 3', ha='center', va='center', size=20, alpha=.5)
ax4 = plt.subplot(G[-1, 0])
plt.xticks([]), plt.yticks([])
plt.text(0.5, 0.5, 'Axes 4', ha='center', va='center', size=20, alpha=.5)
ax5 = plt.subplot(G[-1, -2])
plt.xticks([]), plt.yticks([])
plt.text(0.5, 0.5, 'Axes 5', ha='center', va='center', size=20, alpha=.5)
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-5690dfecb9e8ce4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 大图套小图
```
plt.axes([l,b,w,h]) 
其中 [l, b, w, h] 可以定义坐标系
l 代表坐标系左边到 Figure 左边的水平距离
b 代表坐标系底边到 Figure 底边的垂直距离
w 代表坐标系的宽度
h 代表坐标系的高度
如果 l, b, w, h 都小于 1，那它们是标准化 (normalized) 后的距离。比如 Figure 底边长度为 10， 坐标系底边到它的垂直距离是 2，那么 b = 2/10 = 0.2。
```
```
plt.axes([0.1, 0.1, 0.8, 0.8])
plt.xticks([]), plt.yticks([])
plt.text(0.6, 0.6, 'axes([0.1,0.1,0.8,0.8])', ha='center', va='center', size=20, alpha=.5)
plt.axes([0.2, 0.2, 0.3, 0.3])
plt.xticks([]), plt.yticks([])
plt.text(0.5, 0.5, 'axes([0.2,0.2,0.3,0.3])', ha='center', va='center', size=11, alpha=.5)
plt.show()
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-181871f7556b9758.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 重叠图
```
plt.axes([0.1,0.1,0.5,0.5])
plt.xticks([]),plt.yticks([])
plt.text(0.1,0.1,'axes([0.1,0.1,0.5,0.5])',ha='left',va='center',size=16,alpha=.5)
plt.axes([0.2,0.2,0.5,0.5])
plt.xticks([]),plt.yticks([])
plt.text(0.1, 0.1,'axes([0.2,0.2,0.5,0.5])',ha='left',va='center',size=16,alpha=.5)
plt.axes([0.3,0.3,0.5,0.5])
plt.xticks([]),plt.yticks([])
plt.text(0.1,0.1,'axes([e.3,e.3,0.5,0.5])',ha='left',va='center',size=16,alpha=.5)
plt.axes([0.4,0.4,0.5,0.5])
plt.xticks([]),plt.yticks([])
plt.text(0.1,0.1,'axes([0.4,0.4,0.5,0.5])',ha='left',va='center',size=16,alpha=.5)
plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-5d875b555311de88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 坐标轴
个坐标系 (Axes)，通常是二维，有两条坐标轴 (Axis)：
横轴：XAxis，纵轴：YAxis

每个坐标轴都包含两个元素：
容器类元素「刻度」，该对象里还包含**刻度本身和刻度标签**
基础类元素「标签」，该对象包含的是**坐标轴标签**


```
fig, ax = plt.subplots()
ax.set_xlabel('Label on x-axis')
ax.set_ylabel('Label on y-axis')
for label in ax.xaxis.get_ticklabels():
    # label is a Text instance
    label.set_color('teal')
    label.set_rotation(45)
    label.set_fontsize(20)
for line in ax.yaxis.get_ticklines():
    # line is a Line2D instance 
    line.set_color('r_hex)
    line.set_markersize(500)
    line.set_markeredgewidth(30)
    plt. show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-3e75e9ebb89e7124.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 刻度
刻度 (Tick) 其实在坐标轴那节已经讲过了，它核心内容就是:
一条短线 (刻度本身)
一串字符 (刻度标签)
```
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
fig, axes = plt. subplots(nrows=2, ncols=3, figsize=(8, 4))
axes[0, 0].set_title('original')
# 隐藏刻度线
axes[0, 1].spines['right']. set_color('none')
axes[0, 1].spines['left']. set_color('none')
axes[0, 1].spines['top']. set_color('none')
axes[0, 1].set_title('Handle Spines')
# 设置y轴没有刻度标签
axes[0, 2].yaxis.set_major_locator(ticker.NullLocator())
# 设置x轴的刻度标签在刻度线下面
axes[0, 2].xaxis.set_ticks_position('bottom')
axes[0, 2].set_title('Handle Tick Labels')
# 设置设置刻度长和宽
axes[1, 0].tick_params(which='major', width=2.00)
axes[1, 0].tick_params(which='major', length=10)
# 
axes[1, 0].tick_params(which='minor', width=0.75)
axes[1, 0].tick_params(which=' minor', length=2.5)
axes[1, 0].set_title('Handle Tick width/Length')
# 设置刻度范围
axes[1, 1].set_xlim(0, 5)
axes[1, 1].set_ylim(0, 1)
axes[1, 1].set_title('Handle Axis Limit')
# 设置背景
axes[1, 2].patch.set_color('black')
axes[1, 2].patch.set_alpha(0.3)
axes[1, 2].set_title('Handle Patch Color')
plt.tight_layout()
plt.show()
```
![ ](https://upload-images.jianshu.io/upload_images/18339009-834112bd9fa8f591.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

不同的 locator() 可以生成不同的刻度对象，我们来研究以下 8 种：
>NullLocator(): 空刻度
MultipleLocator(a): 刻度间隔 = 标量 a
FixedLocator(a): 刻度位置由数组 a 决定
LinearLocator(a): 刻度数目 = a, a 是标量
IndexLocator(b, o): 刻度间隔 = 标量 b，偏移量 = 标量 o
AutoLocator(): 根据默认设置决定
MaxNLocator(a): 最大刻度数目 = 标量 a
LogLocator(b, n): 基数 = 标量 b，刻度数目 = 标量 n

![image.png](https://upload-images.jianshu.io/upload_images/18339009-29471e158f1794fa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/18339009-e03ffa57629e723b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/18339009-40a148216a0bae6c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/18339009-c5c5b272bcc84022.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
上图其实包含 8 个子图，但只含有 x 轴，这也是为什么要先定一个 setup(ax) 函数来只保留 x 轴。


### 用 plt.rcParams 可查看上图的所有默认属性 (非常多的属性值)。
```plt.rcParams```
看完上面的属性值后，斯蒂文决定在图表尺寸 (figsize)，每英寸像素点 (dpi)，线条颜色 (color)，线条风格 (linestyle)，线条宽度 (linewidth)，横纵轴刻度 (xticks, yticks)，横纵轴边界 (xlim, ylim) 做改进。那就先看看它们的默认属性值是多少。
```
print( 'figure size:', plt.rcParams['figure.figsize'] )
print( 'figure dpi:',plt.rcParams['figure.dpi'] )
print( 'line color:',plt.rcParams['lines.color'] )
print( 'line style:',plt.rcParams['lines.linestyle'] )
print( 'line width:',plt.rcParams['lines.linewidth'] )

fig = plt.figure()
ax = fig.add_subplot(1, 1, 1)
ax.plot()

print( 'xticks:', ax.get_xticks() )
print( 'yticks:', ax.get_yticks() )
print( 'xlim:', ax.get_xlim() )
print( 'ylim:', ax.get_ylim() )
```
![](https://upload-images.jianshu.io/upload_images/18339009-9851d6be960d91e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
fig=plt.figure(figsize=(16,6), dpi=100)
ax=fig.add_subplot(1,1,1)
# x 是日期 (回顾 spx 是一个 DataFrame，行标签是日期)。
x=spx.index
y=spx.values
ax.plot(x,y, color='dt_hex', linewidth=2, linestyle='-')
# 设置横轴的边界，下界是 - 1，上界是 len(x) +1。
ax.set_xlim(-1,len(x)+1)
ax.set_ylim(y.min()*0.8,y.max()*1.2)
# 设置横轴「数值刻度」为 range(0,len(x), 40)，即 0, 40, 80, ....
ax.set_xticks(range(0, len(x), 40))
# 刻度标签，即格式为 %Y-%m-%d 的日期。由于日期个数比较多，而且日期字符比较长，直接在图中显示出来会相互重叠非常难看。这里调节参数 rotation = 90 使得日期逆时针转了 90 度，看上图效果好多了。
ax.set_xticklabels([x[i]. strftime('%Y-%m-%d') for i in ax.get_xticks()], rotation=90)
```

##### 同一副图两个坐标系
```
fig=plt.figure(figsize=(16,6), dpi=180)
ax1=fig.add_subplot(1,1,1)
x=spx.index
y1=spx.values
y2=vix.values
ax1.plot(y1, color='dt_hex', linewidth=2, linestyle'-',label='s8pse0')
ax1.set_xlim(-1, len(x)-1)
axl.setylim(np.vstack([y1,y2]).min()*0.8, np.vstack([y1,y2]). max()*1.2)
xtick-range(0, len(x),40)
x_label=[x[i]. strftime('%V-%m-%d') for i in xtick]
axl.set_xticks(x_tick)
ax1.set_xticklabels(x_label, rotation=90)
ax1.legend(loc='upper left', frameon=True)
# Add a second axes 
ax2=ax1.twinx()
ax2.plot(y2, color='', linewidth=2, linestyle='-', label='VIx')
ax2.legend(loc=' upper right', frameon=True)
```

##### 某点设置标注
```
fig=plt. figure(figsize=(16,6), dpi=100)
crisis_data=[(datetime(2007,10,11),'Peak of bull market'),
            (datetime(2008,3,12),'8ear Stearns Fails'),
            (datctime(2008,9,15),' Lehman Bankruptcy'),
            (datetime(2009,1,20),' RBS Sell-off'),
            (datetime(2009,4,2),'620Summit')]
ax1=fig.add_subplot(1,1,1)
x=spx.index
y1=spx.values
axl.plot(y1, color='dt_hex', linewidth=2, linestyle='-', label='s& ps8e')
ax1.set_xlim(1, len(x)+1)
ax1.setylim(np.vstack([y1,y2]). min()*0.8, np.vstack([y1,y2]). max()*1.2)
xtick=range(0, len(x),40)
x_1abel=[x[i].strftime('Y-m-d') for i in x_tick]
ax1.set_xticks(x_tick)
ax1.set_xticklabels(x_label, rotation=90)
ax1.legend(loc='upper 1eft',frameon=True)
for date,label in crisis_data:
    date=date.strftime('%Y-%m-%d')
    xi=x.get_loc(date)
    yi=spx.asof(date)
    axl.scatter(xi,yi,880,color='r_hex')
    axl.annotate(label,
                 xy=(xi,yi+60),
                 xytext=(xi,yi+300),
                 arrowprops=dict(facecolor='black',
                                 headwidth=4,
                                 width=1,
                                 headlength=6),
                 horizontalalignment='left',
                 verticalalignment='top')
init_tick=list(range(0,len(x),40))
impt_tick=[]
impt_date=[]
for i, label in enumerate(axl.get_xticklabels()): 
    if i>=len(init_tick):
        label. set_color('r_hex')
        label. set_fontweight('bold")
    else:
        labe1.set_fontsize(9)
```


金融数据练习
【干货】一文掌握Matplotlib的使用方法

```python
from yahoofinancials import YahooFinancials
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
```


```python
# 从 API 获取数据，股票用的是股票代号 (stock code)，而货币用的该 API 要求的格式，
# 比如「欧元美元」用 EURUSD=X，而不是市场常见的 EURUSD，而「美元日元」用 JPY=X 而不是 USDJPY。
start_date='2018-04-29'
end_date='2019-04-29'
stock_code=['NVDA','AMZN','BABA','FB','AAPL']
currency_code=['EURUSD=X','JPY=X','CNY=X']
```


```python
stock=YahooFinancials(stock_code)
currency=YahooFinancials(currency_code)
stock_daily=stock.get_historical_price_data(start_date, end_date,'daily')
currency_daily=currency.get_historical_price_data(start_date, end_date,'daily')
```


```python
stock_daily
```
把上面的「原始数据」转换成 DataFrame
```python
def data_converter(price_data, code, asset):
    # convert raw data to dataframe 
    if asset=='FX': 
        # 如果 Asset 是股票类，直接用其股票代码；如果 Asset 是汇率类，一般参数写成 EURUSD 或 USDJPY
        # 如果是 EURUSD，转换成 EURUSD=X
        # 如果是 USDJPY，转换成 JPY=X
        code=str(code[3:] if code[:3]=='USD' else code)+ '=X'
    columns=['open','close','low','high'] 
    price_dict=price_data[code]['prices']
    index=[ p['formatted_date'] for p in price_dict]
    price=[[p[c] for c in columns] for p in price_dict]
    data=pd.DataFrame(price, 
                      index=pd.Index(index, name='date'), 
                      columns=pd.Index(columns, name='OHLC'))
    return data
```


```python
EURUSD=data_converter(currency_daily,'EURUSD','FX')
EURUSD.head(3).append(EURUSD.tail(3))
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-093753c575abe7d7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



```python
NVDA=data_converter(stock_daily,'NVDA','EQ')
NVDA.head(3).append(NVDA.tail(3))
```
![image.png](https://upload-images.jianshu.io/upload_images/18339009-984a2592a01201b8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


##### 直方图 (histogram chart)又称质量分布图，是一种统计报告图
```hist()``` 里的参数有
p_NVDA：Series，也可以是 list 或者 ndarray
bins：分成多少堆
colors：用之前定义的深青色


```python
p_NVDA=NVDA['close']
date=p_NVDA.index 
price=p_NVDA.values 
r_NVDA=pd.Series(np.log(price[1:]/price[:-1]), index=date[1:])
fig=plt.figure(figsize=(8,4))
plt.hist(r_NVDA, bins=30)
plt.xlabel('Nvidia Price')
plt.ylabel('Number of Days Observed')
plt.title('Frequency Distribution of Nvidia Prices, Apr-2018 to Apr-2019')
plt.show()
```



##### 散点图 (scatter chart)  用两组数据构成多个坐标点
``` scatter() ```里的参数有
p_AMZN (r_AMZN)：Series，也可以是 list 或者 ndarray
p_BABA (r_BABA)：Series，也可以是 list 或者 ndarray
colors：用之前定义的深青色和红色
```python
AMZN=data_converter(stock_daily,'AMZN', 'EQ') 
BABA=data_converter(stock_daily,'BABA','EQ')
p_AMZN=AMZN['close']
p_BABA=BABA['close']
date=p_AMZN.index 
price=p_AMZN.values 
r_AMZN=pd.Series(np.log(price[1:]/price[:-1]), index=date[1:])
date=p_BABA.index 
price=p_BABA.values 
r_BABA=pd.Series(np.log(price[1:]/price[:-1]), index=date[1:])
```

##### 折线图 (line chart) 显示随时间而变化的连续数据
```python
fig, axes=plt.subplots(nrows=1, ncols=2, figsize=(14,6))
axes[0].scatter(p_AMZN,p_BABA)
axes[0].set_xlabel('Amazon Price')
axes[0].set_title('Daily Prices from Apr-2018 to Apr-2019')
axes[1].scatter(r_AMZN,r_BABA)
axes[1].set_xlabel('Amazon Log-Return')
axes[1].set_ylabel('Alibaba Log-Return')
axes[1].set_title('Daily Returns from Apr-2018 to Apr-2019')
plt.show()
# 亚马逊和阿里巴巴在这端时期的表现正相关，如果做线性回归是一条斜率为正的线。
```





```python
curr='EURUSD'
EURUSD=data_converter(currency_daily,curr,'FX')
rate=EURUSD['close']
fig=plt.figure(figsize=(16,6))
ax=fig.add_subplot(1,1,1)
ax.set_title(curr + '-Moving Average')
ax.set_xticks(range(0, len(rate.index),10))
ax.set_xticklabels([ rate.index[i] for i in ax.get_xticks()], rotation=90)
ax.plot(rate, linewidth=2, label='Close')
# 用 rolling(n) 函数对 rate 求 n 天移动均值。
MA_20=rate.rolling(20).mean() 
MA_60=rate.rolling(60).mean()
ax.plot(MA_20, linewidth=2, label='MA20')
ax.plot(MA_60, linewidth=2, label='MA60')
ax.legend(loc=0)
plt.show()
```




##### 饼状图 (pie chart) 是一个划分为几个扇形的圆形统计图表，用于描述量、频率或百分比之间的相对关系。
```python
# 我们来看看如何画出一个股票投资组合在 2019 年 4 月 26 日的饼状图，假设组合里面有 100 股英伟达，20 股亚马逊，50 股阿里巴巴，30 股脸书和 40 股苹果 (一个科技股爱好者的组合 )。
# 算组合里五支股票在 2019 年 4 月 26 日的市值 (market value, MV)。
stock_list=['NVDA','AMZN','BABA','FB','AAPL']
date='2019-04-26'
MV=[data_converter(stock_daily, code, 'EQ')['close'][date] for code in stock_list ]
MV=np.array(MV)*np.array([100,20,50,30,40])
# print(MV) # [17808.99963379 39012.60009766  9354.49981689  5744.70016479 8172.00012207]
```


```python
# 首先按市值大小按升序排序。
idx = MV.argsort()[::-1]  # [1 0 2 4 3]
MV = MV[idx] # idx为 np.ndarray 可直接生成新的数组
stock_list = [ stock_list[i] for i in idx ]
fig=plt.figure(figsize=(16,6))
ax=fig.add_subplot(1,1,1)
# startangle = 90 是说第一片扇形 counterclock = False 是说顺时针拜访每块扇形
ax.pie(MV, labels=stock_list,autopct='%.0f%%',startangle=90,counterclock=False) 
plt.show()
```

```python
fig=plt.figure(figsize=(8,6))
ax=fig.add_subplot(1,1,1)
pct_MV=MV/np.sum(MV)
index=np.arange(len(pct_MV))
ax.bar(index,pct_MV)
ax.set_xticks(index)
ax.set_xticklabels(stock_list)
ax.set_ylim(0,np.max(pct_MV)*1.1)
for x,y in zip(index,pct_MV): 
    ax.text(x+0.04,y+0.05/100,'{0:.0%}'.format(y),ha='center',va='bottom')
plt.show()
```


```python
# 如果柱状很多时，或者标签名字很长时，用横向柱状图 (horizontal bar chart)，函数为 ax.barh()。代码和上面非常类似，就是把横轴和纵轴的调换了一下。
fig=plt.figure(figsize=(8,6))
ax=fig.add_subplot(1,1,1)
pct_MV=MV[::-1]/np.sum(MV)
index=np.arange(len(pct_MV))
ax.barh(index,pct_MV)
ax.set_yticks(index)
ax.set_yticklabels(stock_list[::-1])
ax.set_xlim(0,np.max(pct_MV)*1.1)
for x,y in zip(pct_MV, index): 
    ax.text(x+0.04,y,'{0:.0%}'.format(x),ha='center',va='bottom')
plt.show()
```


# 动画
###### 假
```
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
from mpl_toolkits.mplot3d import Axes3D

# 解决中文乱码问题
# 查看字体包位置 fc-list :lang=zh | grep Songti
myfont = fm.FontProperties(fname=r"C:/Windows/fonts/simsun.ttc", size=14)
matplotlib.rcParams["axes.unicode_minus"] = False
# plt.ion()：打开交互模式
# plt.ioff()：关闭交互模式
# plt.clf()：清除当前的Figure对象
# plt.cla()：清除当前的Axes对象
# plt.pause()：暂停功能

def simple_plot():
    """
    simple plot
    """
    # 生成画布
    plt.figure(figsize=(8, 6), dpi=80)
    # 打开交互模式
    plt.ion()

    # 循环
    for index in range(100):
        # 清除原有图像
        plt.cla()
        # 设定标题等
        plt.title("动态曲线图", fontproperties=myfont)
        plt.grid(True)
        # 生成测试数据
        x = np.linspace(-np.pi + 0.1*index, np.pi+0.1*index, 256, endpoint=True)
        y_cos, y_sin = np.cos(x), np.sin(x)
        # 设置X轴
        plt.xlabel("X轴", fontproperties=myfont)
        plt.xlim(-4 + 0.1*index, 4 + 0.1*index)
        plt.xticks(np.linspace(-4 + 0.1*index, 4+0.1*index, 9, endpoint=True))
        # 设置Y轴
        plt.ylabel("Y轴", fontproperties=myfont)
        plt.ylim(-1.0, 1.0)
        plt.yticks(np.linspace(-1, 1, 9, endpoint=True))
        # 画两条曲线
        plt.plot(x, y_cos, "b--", linewidth=2.0, label="cos示例")
        plt.plot(x, y_sin, "g-", linewidth=2.0, label="sin示例")
        # 设置图例位置,loc可以为[upper, lower, left, right, center]
        plt.legend(loc="upper left", prop=myfont, shadow=True)
        # 暂停
        plt.pause(0.1)

    # 关闭交互模式
    plt.ioff()
    # 图形显示
    plt.show()
simple_plot()
```

##### FuncAnimation
```
# fig 绘制动图的画布名称
# func自定义动画函数，即下边程序定义的函数update
# frames动画长度，一次循环包含的帧数（每一帧就是每一个画面），在函数运行时，其值会传递给函数update(n)的形参“n”，注意这个就是循环的基准参数，多次循环就是多个值的列表即可。
# init_func自定义开始帧，即传入刚定义的函数init,初始化函数，如设置图标大小，刻度等
# interval更新频率，以ms计
# blit选择更新所有点（False），还是仅更新产生变化的点(True)，看情况设置，但mac用户请选择False，否则无法显示。
```
```
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
# 修改输出方式 还有就是保存的图片直接用pycharm打开是静态图，使用系统自带的图片编辑器打开是动态的。
import matplotlib
matplotlib.use('TkAgg')

# 用来正常显示中文标签
plt.rcParams['font.sans-serif'] = ['SimHei']
# 用来正常显示负号
plt.rcParams['axes.unicode_minus'] = False
fig, ax = plt.subplots()
ax.set_aspect('auto')  # 控制长宽比
# ax.grid() # 是否要格子背景
# 在30处设置基准线
ax.axhline(30, linestyle='--', color='black')
# 设置图片标题
ax.set_title('横坐标动态更新的animation实现')
# 设置横坐标名称
ax.set_xlabel('动态变化的横轴')
xdata = [0 for _ in range(0, 32)]  # -40-0
ydata = (np.zeros(32, dtype=int)).tolist()
ln, = ax.plot([], [], 'r-', animated=False)

def init():
    ax.set_ylim(-1, 1)
    ln.set_data([], [])
    return ln,

def update(frame):
    del xdata[0]
    del ydata[0]

    xdata.append(frame)
    ydata.append(np.sin(frame))
    print('frame', frame, np.sin(frame))
    print('min(xdata), max(xdata)',min(xdata), max(xdata))
    print('xdata[0], ydata[0]',xdata[0], ydata[0])

    # 设置x轴的范围
    ax.set_xlim(min(xdata), max(xdata))  # -39 -1
    ticks = np.linspace(min(xdata), max(xdata)+1, 40).tolist()
    # 更新刻度，刻度只要早x轴的范围内就可以
    ax.set_xticks(ticks)
    # 设置刻度标签
    ax.set_xticklabels(ticks, rotation=90)
    # 重新渲染子图
    ax.figure.canvas.draw()
    ln.set_data(xdata, ydata)
    return ln,
# fig 绘制动图的画布名称
# func自定义动画函数，即下边程序定义的函数update
# frames动画长度，一次循环包含的帧数（每一帧就是每一个画面），在函数运行时，其值会传递给函数update(n)的形参“n”，注意这个就是循环的基准参数，多次循环就是多个值的列表即可。
# init_func自定义开始帧，即传入刚定义的函数init,初始化函数，如设置图标大小，刻度等
# interval更新频率，以ms计
# blit选择更新所有点（False），还是仅更新产生变化的点(True)，看情况设置，但mac用户请选择False，否则无法显示。

ani = FuncAnimation(fig,
                    update,
                    frames=np.linspace(0, 20, 128).tolist(),
                    interval=6,
                    blit=False,
                    init_func=init,
                    repeat=False)
plt.show()
```
```
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation

# 修改输出方式 还有就是保存的图片直接用pycharm打开是静态图，使用系统自带的图片编辑器打开是动态的。
import matplotlib
matplotlib.use('TkAgg')
# fig, ax = plt.subplots()  生成子图，相当于fig = plt.figure(),ax = fig.add_subplot()

# 用来正常显示中文标签
plt.rcParams['font.sans-serif'] = ['SimHei']
# 用来正常显示负号
plt.rcParams['axes.unicode_minus'] = False

fig = plt.figure(figsize=(10, 4))
ax = fig.add_subplot(111, autoscale_on=False, xlim=(0, 40), ylim=(0, 40))
ax.set_aspect('auto')  # 控制长宽比
# ax.grid() # 是否要格子背景
# 在30处设置基准线
ax.axhline(30, linestyle='--', color='black')
# 设置图片标题
ax.set_title('横坐标动态更新的animation实现')
# 设置横坐标名称
ax.set_xlabel('动态变化的横轴')
ax.set_ylabel(r'$Y\ data$')
line, = ax.plot([], [], 'o-', lw=2)

# 由于animation必须先有指定的数据或者指定的数据大小，但是我们的数据是一个一个显示的，初期是么有数据的
# 我们又必须提前填充指定个数的数据，这里我们填充x、y刻度以外的数据，然后想办法不显示
# 我们如何不显示呢？由于这些数据本质上还是要输出的，只是不让我们看见，数据既然要输出我们就要正确的设置这些数据对应的刻度
# 既然刻度不能做出改变，我们只能在刻度标签做手脚，我们让数据中x轴标签对应的刻度小于0时，标签显示空字符串
thisx = [i for i in range(-40, 0)]  # -40-0
thisy = (np.zeros(40, dtype=int) - 1).tolist()   # print(type(thisy),len(thisy))  # <class 'list'> 40个0


def init():
    line.set_data([], [])
    return line


def update(*args):
    # 这种操作之前一定要确保len(thisy) = len(thisx)
    del thisx[0]
    del thisy[0]

    thisx.append(max(thisx) + 1)   # 0 -1 ... -39
    thisy.append(np.random.randint(0, 40, 1))

    # 设置x轴的范围
    ax.set_xlim(min(thisx), max(thisx))  # -39 -1
    ticks = [i for i in range(min(thisx), max(thisx)+1)]
    # 更新刻度，刻度只要早x轴的范围内就可以
    ax.set_xticks(ticks)
    # 设置刻度标签
    ax.set_xticklabels(ticks, rotation=320)
    # 重新渲染子图
    ax.figure.canvas.draw()
    line.set_data(thisx, thisy)
    return line


ani = animation.FuncAnimation(fig,
                              update,
                              interval=600,
                              blit=False,
                              init_func=init)
# ani.save('jieky_animation.gif', writer='pillow')
plt.show()
```
机器学习可视化
```
# coding: utf-8
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
from scipy.interpolate import spline
import matplotlib
matplotlib.use('TkAgg')

training_epochs = 200
display_step = 10
train_X = np.linspace(0, 10, 1000)
noise = np.random.normal(0, 1, train_X.shape)
train_Y = train_X * 1 - 2 + noise

X = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32)
W = tf.Variable(-1., name="weight")
b = tf.Variable(1., name="bias")

y = tf.add(tf.multiply(X, W), b)

cost = tf.reduce_sum(tf.pow(y - Y, 2))
optimizer = tf.train.AdamOptimizer(learning_rate=0.99, beta1=0.9, beta2=0.99).minimize(cost)

c_trace = []
W_trace = []
b_trace = []
y_trace = []

with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    for epoch in range(training_epochs):
        sess.run(optimizer, feed_dict={X: train_X, Y: train_Y})
        if epoch % display_step == 0:
            c_tmp = sess.run(cost, feed_dict={X: train_X, Y: train_Y})
            W_tmp = sess.run(W)
            b_tmp = sess.run(b)
            y_tmp = sess.run(y, feed_dict={X: train_X})
            print("Epoch: %04d" % (epoch + 1), "cost=", "{:.9f}".format(c_tmp), "W=", W_tmp, "b=", b_tmp)
            c_trace.append(c_tmp)
            W_trace.append(W_tmp)
            b_trace.append(b_tmp)
            y_trace.append(y_tmp)
    print("Optimization Finished!")
    print("cost=", sess.run(cost, feed_dict={X: train_X, Y: train_Y}), "W=", sess.run(W), "b=", sess.run(b))


fig, ax = plt.subplots()
l1 = ax.scatter(train_X, train_Y, color='red', label=r'$Original\ data$')
ax.set_xlabel(r'$X\ data$')
ax.set_ylabel(r'$Y\ data$')


def update(i):
    try:
        ax.lines.pop(0)
    except Exception:
        pass
    line, = ax.plot(train_X, y_trace[i], 'g--', label=r'$Fitting\ line$', lw=2)
    if i == len(y_trace) - 1:
        xnew = np.linspace(0, 10, np.max(c_trace) - np.min(c_trace))
        # 插值  scipy.interpolate.spline(xk, yk, xnew）
        smooth = spline(np.linspace(0, 10, np.size(c_trace)), c_trace, xnew)
        twinax = ax.twinx()
        twinax.set_ylabel(r'Cost')
        costline, = twinax.plot(xnew, smooth, 'b', label=r'$Cost\ line$', lw=2)
        plt.text(5, 5, c_trace)
        plt.legend(handles=[l1, line, costline], loc='upper center')
    return line,


ani = animation.FuncAnimation(fig, update, frames=len(y_trace), interval=100, repeat=False)
ani.save('linearregression.gif', writer='imagemagick')
plt.show()
```
散点图
```scatter(x, y, s=None, c=None, marker=None, cmap=None, norm=None, vmin=None, vmax=None, alpha=None, linewidths=None, verts=None, edgecolors=None, *, plotnonfinite=False, data=None, **kwargs)```
柱状图
```barh(y, width, height=0.8, left=None, *, align='center', **kwargs)```
图片
```imshow(X, cmap=None, norm=None, aspect=None, interpolation=None,alpha=None, vmin=None,vmax=None, origin=None,extent=None,shape=cbook.deprecation._deprecated_parameter,filternorm=1,filterrad=4.0,imlim=cbook.deprecation._deprecated_parameter,resample=None, url=None, *, data=None, **kwargs)```

```pcolormesh(X,Y,Z cmap=None, vmin=None, vmax=None)```
X，Y：指的是二维网格面每一个点的横纵坐标
Z:(X,Y)坐标处的颜色值



# 三维图
```
# 3D绘图示意
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
fig = plt.figure()
ax = Axes3D(fig)

x = np.arange(-4, 4, 0.25)
y = np.arange(-4, 4, 0.25)
x, y = np.meshgrid(x, y)
r = np.sqrt(x**2 + y**2)
z = np.sin(r)

ax.plot_surface(x, y, z, rstride=1,   # row 行步长
                 cstride=2,           # colum 列步长
                 cmap=plt.cm.hot)      # 渐变颜色
ax.contourf(x, y, z,
            zdir='z',  # 使用数据方向
            offset=-2,  # 填充投影轮廓位置
            cmap=plt.cm.hot)
ax.set_zlim(-2, 2)

plt.show()
```
![](https://upload-images.jianshu.io/upload_images/18339009-3c5a2fa06b42737a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# cmap
Sequential：顺序。通常使用单一色调，逐渐改变亮度和颜色渐渐增加。应该用于表示有顺序的信息。
![](https://upload-images.jianshu.io/upload_images/18339009-19f890e483cafd5b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-40883c8855df2435.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-1ad5d6549bc53dd6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Diverging：发散。改变两种不同颜色的亮度和饱和度，这些颜色在中间以不饱和的颜色相遇；当绘制的信息具有关键中间值（例如地形）或数据偏离零时，应使用此值
![](https://upload-images.jianshu.io/upload_images/18339009-43b6cf8d49bd84d6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Cyclic：循环。改变两种不同颜色的亮度，在中间和开始/结束时以不饱和的颜色相遇。应该用于在端点处环绕的值，例如相角，风向或一天中的时间。
![](https://upload-images.jianshu.io/upload_images/18339009-83eef93cde25bba6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Qualitative：定性。常是杂色，用来表示没有排序或关系的信息。
![](https://upload-images.jianshu.io/upload_images/18339009-5a4dfd1f3aa24aa3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Miscellaneous：杂色。
![](https://upload-images.jianshu.io/upload_images/18339009-0cd4627cc7f0c8e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# linestyle
![](https://upload-images.jianshu.io/upload_images/18339009-624dffb7b7ee1d97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
