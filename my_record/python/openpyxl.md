```python
from openpyxl import load_workbook, Workbook
from openpyxl.styles import Font, Border, Side, colors, Alignment, PatternFill
from copy import copy

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 把1的数据添加到2里

fill_color = PatternFill('solid', fgColor="1874CD")

wb = []
sheet = []
excelList = [r'D:\Desktop\朱昱雯\9.17\a.xlsx', 'result.xlsx']
sheetName = ['total', 'Sheet']
for i in range(len(excelList)):
    wb.append(load_workbook(excelList[i]))
    sheet.append(wb[i][sheetName[i]])
source_sheet, target_sheet = sheet[0], sheet[1]

for i in range(1, target_sheet.max_row+1):
    method = target_sheet.cell(row=i, column=2).value
    # module, url, method, params = line[0], line[1], line[2], line[3]
    print(source_sheet.max_row, target_sheet.max_row)
    for j in range(1, source_sheet.max_row+1):
        # module1, url1, method1, params1, status, response, comment = line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]
        method1 = source_sheet.cell(row=j, column=2).value
        if (method == method1):
            print(method1)
            if (target_sheet.cell(row=i, column=7).value == None):
                target_sheet.cell(row=i, column=7).value = source_sheet.cell(row=j, column=7).value
            # target_sheet.cell(row=i, column=4).value = source_sheet.cell(row=j, column=4).value
            # target_sheet.cell(row=i, column=5).value = source_sheet.cell(row=j, column=5).value
            # target_sheet.cell(row=i, column=6).value = source_sheet.cell(row=j, column=6).value

            # for k in range(1, 1000):
            #     target_sheet.cell(row=i, column=k).fill = copy(source_sheet.cell(row=j, column=k).fill)
            # print('find', method)
            break
    else:
        print('not fond', method)
        target_sheet.cell(row=i, column=3).fill = fill_color
wb[1].save('result1.xlsx')
```

