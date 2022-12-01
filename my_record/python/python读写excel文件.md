
xlwt写
```
import xlwt
# 创建一个workbook 设置编码
workbook = xlwt.Workbook(encoding='utf-8')
# 创建一个worksheet
# 如果对同一单元格重复操作会发生overwrite Exception，cell_overwrite_ok为可覆盖
worksheet = workbook.add_sheet('My Worksheet', cell_overwrite_ok=True)
 
# 写入excel
# 参数对应 行, 列, 值
worksheet.write(1, 0, label='this is test')

"""字体"""
# 初始化样式
style = xlwt.XFStyle()
# 为样式创建字体
font = xlwt.Font()
font.name = 'Times New Roman'
# 黑体
font.bold = True
# 下划线
font.underline = True
# 斜体字
font.italic = True
# 设定样式
style.font = font
# 不带样式的写入
worksheet.write(0, 0, 'Unformatted value')
# 带样式的写入
worksheet.write(1, 0, 'Formatted value', style)

"""设置单元格格式"""
# 宽度
worksheet.col(0).width = 3333

# 输入一个日期到单元格:
import datetime
style = xlwt.XFStyle()
style.num_format_str = 'M/D/YY'
# Other options: D-MMM-YY, D-MMM, MMM-YY, h:mm, h:mm:ss, h:mm, h:mm:ss, M/D/YY h:mm, mm:ss, [h]:mm:ss, mm:ss.0
worksheet.write(0, 0, datetime.datetime.now(), style)


"""向单元格添加一个公式"""
worksheet.write(0, 0, 5)  # Outputs 5
worksheet.write(0, 1, 2)  # Outputs 2
worksheet.write(1, 0, xlwt.Formula('A1*B1'))  # Should output "10" (A1[5] * A2[2])
worksheet.write(1, 1, xlwt.Formula('SUM(A1,B1)'))  # Should output "7" (A1[5] + A2[2])

"""向单元格添加一个超链接"""
# Outputs the text "Google" linking to http://www.google.com
worksheet.write(0, 0, xlwt.Formula('HYPERLINK("http://www.google.com";"Google")'))
# link to file
import os
worksheet.write(0, 4, xlwt.Formula("HYPERLINK"+'("'+os.path.join(dirpath,  file)+'";"'+file+'")'))


"""合并列和行"""
worksheet.write_merge(0, 0, 0, 3, 'First Merge')  # Merges row 0's columns 0 through 3.
worksheet.write_merge(1, 2, 0, 3, 'Second Merge')  # Merges row 1 through 2's columns 0 through 3.

"""设置单元格内容的对其方式"""
alignment = xlwt.Alignment()  # Create Alignment
# May be: HORZ_GENERAL, HORZ_LEFT, HORZ_CENTER, HORZ_RIGHT, HORZ_FILLED, HORZ_JUSTIFIED, HORZ_CENTER_ACROSS_SEL, HORZ_DISTRIBUTED
alignment.horz = xlwt.Alignment.HORZ_CENTER
# May be: VERT_TOP, VERT_CENTER, VERT_BOTTOM, VERT_JUSTIFIED, VERT_DISTRIBUTED
alignment.vert = xlwt.Alignment.VERT_CENTER
style = xlwt.XFStyle()  # Create Style
style.alignment = alignment  # Add Alignment to Style
worksheet.write(0, 0, 'Cell Contents', style)

"""添加边框"""
# Create Borders
borders = xlwt.Borders()  
# DASHED虚线 NO_LINE没有  THIN实线
borders.left = xlwt.Borders.DASHED
# May be: NO_LINE, THIN, MEDIUM, DASHED, DOTTED, THICK, DOUBLE, HAIR, MEDIUM_DASHED, THIN_DASH_DOTTED, MEDIUM_DASH_DOTTED, THIN_DASH_DOT_DOTTED, MEDIUM_DASH_DOT_DOTTED, SLANTED_MEDIUM_DASH_DOTTED, or 0x00 through 0x0D.
borders.right = xlwt.Borders.DASHED
borders.top = xlwt.Borders.DASHED
borders.bottom = xlwt.Borders.DASHED
borders.left_colour = 0x40
borders.right_colour = 0x40
borders.top_colour = 0x40
borders.bottom_colour = 0x40
style = xlwt.XFStyle()  # Create Style
style.borders = borders  # Add Borders to Style
worksheet.write(0, 0, 'Cell Contents', style)

"""为单元格设置背景色"""
pattern = xlwt.Pattern()  # Create the Pattern
pattern.pattern = xlwt.Pattern.SOLID_PATTERN  # May be: NO_PATTERN, SOLID_PATTERN, or 0x00 through 0x12
# May be: 8 through 63. 0 = Black, 1 = White, 2 = Red, 3 = Green, 4 = Blue, 5 = Yellow, 6 = Magenta, 7 = Cyan, 16 = Maroon, 17 = Dark Green, 18 = Dark Blue, 19 = Dark Yellow , almost brown), 20 = Dark Magenta, 21 = Teal, 22 = Light Gray, 23 = Dark Gray, the list goes on...
pattern.pattern_fore_colour = 5  
style = xlwt.XFStyle()  # Create the Pattern
style.pattern = pattern  # Add Pattern to Style
worksheet.write(0, 0, 'Cell Contents', style)

# 保存
workbook.save('Excel_test.xls')
```
xlrd读文件
```
import xlrd
data = xlrd.open_workbook('excel_test.xls')
print(data.sheet_names())  # 输出所有页的名称
table = data.sheets()[0]  # 获取第一页
table = data.sheet_by_index(0)  # 通过索引获得第一页
table = data.sheet_by_name('Over')  # 通过名称来获取指定页
nrows = table.nrows  # 为行数，整形
ncolumns = table.ncols  # 为列数，整形
print(table.row_values(0))  # 输出第一行值 为一个列表
# 遍历输出所有行值
for row in range(nrows):
    print(table.row_values(row))
# 输出某一个单元格值
print(table.cell(0, 0).value)
print(table.row(0)[0].value)
```
追写
```
import xlrd
from xlutils.copy import copy
data = xlrd.open_workbook('excel_test.xls', formatting_info=True)
excel = copy(wb=data)  # 完成xlrd对象向xlwt对象转换
excel_table = excel.get_sheet(0)  # 获得要操作的页
table = data.sheets()[0]
nrows = table.nrows  # 获得行数
ncols = table.ncols  # 获得列数
values = ["E", "X", "C", "E", "L"]  # 需要写入的值
for value in values:
    excel_table.write(nrows, 1, value)  # 因为单元格从0开始算，所以row不需要加一
    nrows = nrows+1
excel.save('excel_test.xls')
```






































参考：[https://blog.csdn.net/qq_40676033/article/details/86555425](https://blog.csdn.net/qq_40676033/article/details/86555425)
