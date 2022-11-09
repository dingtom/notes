[菜鸟python正则](https://www.runoob.com/python3/python3-reg-expressions.html)
制作markdwon上标
```
import re
with open(r'm:\Desktop\contrast.txt', 'r') as f:
    content = f.readlines()
    new_f = open(r'm:\Desktop\contrast(1).txt', 'w')
    for i in content:
        index = re.search(r'\d+', i).group()
        print(index)
        new_i = re.sub(r'\[.?\d+\]:?', '[^'+index+']: ', i)
        new_f.write(new_i)
    new_f.close()

```
文献名excel填充
```
import xlwt,xlrd
import os
from xlutils.copy import copy
import re
data = xlrd.open_workbook(r'M:\Desktop\论文总结.xlsx')
excel = copy(wb=data) # 完成xlrd对象向xlwt对象转换
excel_table = excel.get_sheet(0) # 获得要操作的页
table = data.sheets()[0]
nrows = table.nrows # 获得行数
ncols = table.ncols # 获得列数
for dirpath, dirnames, files in os.walk(r'm:\Desktop\csi\paper'):
    print(files) #当前路径下所有非目录子文件

years = []
titles = []
for i in files:
    excel_table.write(nrows,1,  re.search(r'\d+ ', i).group()) # 因为单元格从0开始算，所以row不需要加一
    excel_table.write(nrows,4, xlwt.Formula("HYPERLINK"+'("'+os.path.join(dirpath,  i)+'";"'+re.search(r'[^\d].+\.', i).group()+'")'))
    # "HYPERLINK" +  '("'+tmp_str_path+'";"'+tmp_st2+'")'
    nrows = nrows+1
excel.save('excel_test.xls')
```
