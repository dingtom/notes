| | |
|:-:|:-:|
|jieba.cut(x)|精确模式，返回一个可迭代的数据类型|
|jieba.cut(x，cut_all=True)|全模式，输出文本s中所有可能单词|
|jieba.cut_for_search(x)|搜索引擎模式，适合搜索引擎建立索引的分词结果|
|jieba.lcut(x)|精确模式，返回一个列表类型，建议使用|
|jieba.lcut(x，cut_all=True)|全模式，返回一个列表类型，建议使用|
|jieba.lcut_for_search(x)|搜索引擎模式，返回一个列表类型，建议使用|
|jieba.add_word(x)|向分词词典中增加新词w|

```
import jieba
s ="中国特色社会主义进入新时代，我国社会主要矛盾已经转化为人民日益增长的美好\
生活需要和不平衡不从分的发展之间的矛盾。"
n = len(s)
m = len(jieba.lcut(s))
print("中文字符数{}，中文词语数{}。".format(n,m))
 
print("**************01***************")
k = jieba.lcut(s)
print(k)
print("**************02***************")
k1 = jieba.lcut(s,cut_all = True)
print(k1)
print("***************03**************")
k2 = jieba.lcut_for_search(s)
print(k2)
```
![](https://upload-images.jianshu.io/upload_images/18339009-7fafdb2e33be8434.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
import  jieba
txt = open("D:\\三国演义.txt", "r", encoding='utf-8').read()
words = jieba.lcut(txt)     # 使用精确模式对文本进行分词
counts = {}     # 通过键值对的形式存储词语及其出现的次数

for word in words:
    if  len(word) == 1:    # 单个词语不计算在内
        continue
    else:
        counts[word] = counts.get(word, 0) + 1    # 遍历所有词语，每出现一次其对应的值加 1
        
items = list(counts.items())#将键值对转换成列表
items.sort(key=lambda x: x[1], reverse=True)    # 根据词语出现的次数进行从大到小排序

for i in range(15):
    word, count = items[i]
    print("{0:<5}{1:>5}".format(word, count))

```
