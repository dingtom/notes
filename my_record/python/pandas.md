```
import pandas as pd
a = [1, 2, 3, 4]
p = [1, 2, 3, 4]
r = [1, 2, 3, 4]
f = [1, 2, 3, 4]
df = pd.DataFrame({'a':a, 'p':p, 'r':r, 'f':f}, index=['LSTM_FCN', 'LSTM_MCNN', 'BiLSTM_FCN','BiLSTM_MCNN'])
print(df)
df.to_csv("./result/result.csv", index=True)

result = all_metric(y_trues, y_preds, method_name=method_name)
df = pd.read_csv('./result/result.csv', index_col=0)
df.loc[method_name] = result
print(df)
df.to_csv("./result/result.csv", index=True)
plot＿confusion_matrix(y_trues, y_preds, svg_name='./result/{}'.format(method_name))
```
# 导入数据
- pd.read_csv(filename,encoding='gbk',sep='\t')：从CSV文件导入数据

读取tsv文件
```data = pd.read_csv(filename, sep='\t').values```
- pd.read_table(filename)：从限定分隔符的文本文件导入数据
- pd.read_excel(filename)：从Excel文件导入数据
- pd.read_sql(query, connection_object)：从SQL表/库导入数据
- pd.read_json(json_string)：从JSON格式的字符串导入数据
- pd.read_html(url)：解析URL、字符串或者HTML文件，抽取其中的tables表格
- pd.read_clipboard()：从你的粘贴板获取内容，并传给read_table()
- pd.DataFrame(dict)：从字典对象导入数据，Key是列名，Value是数据


# 生成DataFrame
```
iris_data = pd.DataFrame( iris.data, 
                          columns=iris.feature_names )
iris_data['species'] = iris.target_names[iris.target]
iris_data.head(3).append(iris_data.tail(3))
```
# 分箱
![](https://upload-images.jianshu.io/upload_images/18339009-d2719f3e88f408c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
group_names = ['不及格','及格','良','优秀']
cuts = pd.cut(scores,grades,labels=group_names)
```
将成绩均匀的分在四个**等长**的箱子中，precision=2的选项将精度控制在两位
```
cuts = pd.cut(scores,4,precision=2)
```
qcut()可以生成指定的箱子数，然后使每个箱子都具有相同数量的数据
![](https://upload-images.jianshu.io/upload_images/18339009-ad5f446a6a601983.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

pandas.melt(frame, id_vars=None, value_vars=None, var_name=None, value_name='value', col_level=None)
>frame:要处理的数据集。
id_vars:不需要被转换的列名。
value_vars:需要转换的列名，如果剩下的列全部都要转换，就不用写了。
var_name和value_name是自定义设置对应的列名。
col_level :如果列是MultiIndex，则使用此级别。
```
d = {'col1': ['a','a','a','b','b'], 'col2': [2,2,2,2,2],'col3':['c','c','c','d','d']}
df = pd.DataFrame(data=d)
pd.melt(df,id_vars=['col2'],value_vars=['col1'],var_name='hi',value_name='hello')
```
![](https://upload-images.jianshu.io/upload_images/18339009-168b049b21e595bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 取行、列
loc函数：通过行索引 "Index" 中的具体值来取行数据（如取"Index"为"A"的行）
iloc函数：通过行号来取行数据（如取第二行的数据）
```
data=pd.DataFrame(np.arange(16).reshape(4,4),index=list('abcd'),columns=list('ABCD'))

取索引为'a'的行
In[2]: data.loc['a']
取第一行数据，索引为'a'的行就是第一行，所以结果相同
In[3]: data.iloc[0]

取'A'列所有行，多取几列格式为 data.loc[:,['A','B']]
In[4]:data.loc[:,['A']] 
取第0列所有行，多取几列格式为 data.iloc[:,[0,1]]
In[5]:data.iloc[:,[0]] 
```
# 删除插入
```
删除gender列，不改变原来的data数据，返回删除后的新表data_2。axis为1表示删除列，0表示删除行。inplace为True表示直接对原表修改。
data_2 = data.drop('gender', axis=1, inplace=False)
改变某一列的位置。如：先删除gender列，然后在原表data中第0列插入被删掉的列。
data.insert(0, '性别',data.pop('gender'))
pop返回删除的列，插入到第0列，并取新名为'性别'
直接在原数据上删除列
del data['性别']
```
# one-hot
pd.get_dummies(
    data,
    prefix=None,
    prefix_sep='_',
    dummy_na=False,
    columns=None,
    sparse=False,
    drop_first=False,
    dtype=None,
) 
```
pd.get_dummies(all_df['MSSubClass'], prefix='MSSubClass').head()
```
![](https://upload-images.jianshu.io/upload_images/18339009-9d78e4d363ae8c22.png?imageMogr2/auto-orient/strip%7CimageView2/2/ew/1240)



# pandas.melt(frame, id_vars=None, value_vars=None, var_name=None, value_name='value', col_level=None)
参数解释：
frame:要处理的数据集。
id_vars:不需要被转换的列名。
value_vars:需要转换的列名，如果剩下的列全部都要转换，就不用写了。
var_name和value_name是自定义设置对应的列名。
col_level :如果列是MultiIndex，则使用此级别。
![](https://upload-images.jianshu.io/upload_images/18339009-6b8b66c6ce774522.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
pd.melt(df,id_vars=['col2'],value_vars=['col1'],var_name='hi',value_name='hello')
```
![](https://upload-images.jianshu.io/upload_images/18339009-0c007a280ea972d1?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# profile
数据整体概览、变量探索、相关性计算、缺失值情况和抽样展示
```
profile= pandas_profiling.ProfileReport(adult)
profile.to_file(outputfile = "output_file.html")
```
![](https://upload-images.jianshu.io/upload_images/18339009-1604c9299fdf19b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 合并
https://blog.csdn.net/weixin_42782150/article/details/89546357
###### pd.concat([df1,df2],sort=False)
axis :默认为0，为按行拼接；1 为按列拼接
ignore_index: 默认为False,会根据索引进行拼接；True 则会忽略原有索引，重建新索引
join: 为拼接方式，包括 inner,outer
sort: True 表示按索引排序
![](https://upload-images.jianshu.io/upload_images/18339009-24ec28fcd08631b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/100)
![](https://upload-images.jianshu.io/upload_images/18339009-73d272714742292f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000)

###### merge函数
how：数据合并的方式。
>left：基于左dataframe列的数据合并；right：基于右dataframe列的数据合并；outer：基于列的数据外合并（取并集）；inner：基于列的数据内合并（取交集）；默认为'inner'。

on：基于相同列的合并
left_on/right_on：左/右dataframe合并的列名。
left_index/right_index：是否以index作为数据合并的列名，True表示是。
sort：根据dataframe合并的keys排序，默认是。
suffixes：若有相同列且该列没有作为合并的列，可通过suffixes设置该列的后缀名，一般为元组和列表类型
![](https://upload-images.jianshu.io/upload_images/18339009-551adec0b607cbcc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000)
![](https://upload-images.jianshu.io/upload_images/18339009-3b4cb84774c928c9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000)

# Groupby
```
#创建一个DataFrame
import pandas as pd
import numpy as np
rng=np.random.RandomState(0)
df=pd.DataFrame({'key':list('ABCABC'),
'data1':range(6),
'data2':rng.randint(0,10,6)})
 
#输出的结果是这样的
 ```
>   data1  data2 key
      0      5   A
     1      0   B
     2      3   C
     3      3   A
      4      7   B
>      5      9   C

然后我们分别用apply,agg,transform三个函数去处理分组对象:
```
#创建一个分组的迭代器
grouped=df.groupby('key') 
#补充一下，如果大家想看grouped到底把原DataFrame分成什么样子了，可以试试以下代码（实际是一个含分组标签和分组DataFrame的元祖，括号使用的是元组的拆分属性）
for (key,group) in grouped:
	print(key)
	print(group)
 ```
>A
   data1  data2 key
0      0      5   A
3      3      3   A
B
   data1  data2 key
1      1      0   B
4      4      7   B
C
   data1  data2 key
2      2      3   C
5      5      9   C
 
```
#使用apply
grouped.apply(sum)
```
>key data1  data2 key               
A        3      8  AA
B        5      7  BB
C        7     12  CC
```
#使用agg
grouped.agg(sum)
 ```
>key    data1  data2           
A        3      8
B        5      7
C        7     12
 
```
#使用transform
grouped.transform(sum)
 ```
>data1  data2
       3            8
         5            7
         7         12
       3          8
         5          7
         7         12
```
grouped['data1'].count()
```
>key
A    2
B    2
C    2

# sample
(n=None, frac=None, replace=False, weights=None, random_state=None, axis=None)
n，要抽取的行数
random_state，确保可重复性
frac，抽取行的比例
replace，是否为有放回抽样True:有放回抽样
weights，字符索引或概率数组axis=0:为行字符索引或概率数组axis=1:为列字符索引或概率数组
axis，选择抽取数据的行还是列axis=0:抽取行axis=1:抽取列










######导出数据
df.to_csv(filename)：导出数据到CSV文件
df.to_excel(filename)：导出数据到Excel文件
df.to_sql(table_name, connection_object)：导出数据到SQL表
df.to_json(filename)：以Json格式导出数据到文本文件
######创建测试对象

pd.DataFrame(np.random.rand(20,5))：创建20行5列的随机数组成的DataFrame对象
pd.Series(my_list)：从可迭代对象my_list创建一个Series对象
df.index = pd.date_range('1900/1/30', periods=df.shape[0])：增加一个日期索引

######查看、检查数据
df.head(n)：查看DataFrame对象的前n行
df.tail(n)：查看DataFrame对象的最后n行
df.shape：查看行数和列数
df.info()：查看索引、数据类型和内存信息
df.describe()：查看数值型列的汇总统计
s.value_counts(dropna=False)：查看Series对象的唯一值和计数
df.apply(pd.Series.value_counts)：查看DataFrame对象中每一列的唯一值和计数

######数据选取

s.iloc[0]：按位置选取数据
s.loc['index_one']：按索引选取数据

df.query("(age==18)&(sex=='male)")条件选取


######数据清理

df.columns = ['a','b','c']：重命名列名
pd.isnull()
pd.notnull()
df.dropna()：删除所有包含空值的行
df.dropna(axis=1)：删除所有包含空值的列
df.dropna(axis=1,thresh=n)：删除所有小于n个非空值的行
df.fillna(x)：用x替换DataFrame对象中所有的空值
s.astype(float)：将Series中的数据类型更改为float类型
s.replace(1,'one')：用‘one’代替所有等于1的值
s.replace([1,3],['one','three'])：用'one'代替1，用'three'代替3
df.rename(columns=lambda x: x + 1)：批量更改列名
df.rename(columns={'old_name': 'new_ name'})：选择性更改列名
df.set_index('column_one')：更改索引列
df.rename(index=lambda x: x + 1)：批量重命名索引

######数据处理：Filter、Sort和GroupBy
s.T转置
df[df[col] > 0.5]：选择col列的值大于0.5的行

df.sort_values(col1)：按照列col1排序数据，默认升序排列
df.sort_values([col1,col2], ascending=[True,False])：先按列col1升序排列，后按col2降序排列数据

df.groupby(col)：返回一个按列col进行分组的Groupby对象
df.groupby([col1,col2])：返回一个按多列进行分组的Groupby对象
df.groupby(col1)[col2]：返回按列col1进行分组后，列col2的均值
df.pivot_table(index=col1, values=[col2,col3], aggfunc=max)：创建一个按列col1进行分组，并计算col2和col3的最大值的数据透视表
df.groupby(col1).agg(np.mean)：返回按列col1分组的所有列的均值
data.apply(np.mean)：对DataFrame中的每一列应用函数np.mean
data.apply(np.max,axis=1)：对DataFrame中的每一行应用函数np.max

######数据合并
df.concat([df1, df2],axis=1)：将df2中的列添加到df1的尾部
df1.join(df2,on=col1,how='inner')：对df1的列和df2的列执行SQL形式的join

######数据统计
df.describe()：查看数据值列的汇总统计
df.mean()：返回所有列的均值
df.corr()：返回列与列之间的相关系数
df.count()：返回每一列中的非空值的个数
df.median()：返回每一列的中位数

df.unique()所有值
df.cumsum()累加
pd.cut(df.salary, bins=10)分桶
