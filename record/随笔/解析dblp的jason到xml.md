```
import json
import xlwt


def foo(var):
    return {
        'authors': 0,
        'title': 1,
        'venue': 2,
        'volume': 3,
        'number': 4,
        'pages': 5,
        'year': 6,
        'type': 7,
        'key': 8,
        'doi': 9,
        'ee': 10,
        'url': 11
    }.get(var, 12)    # 'error'为默认返回值，可自设置


with open('api (1).json') as f:
    result = a = json.loads(f.read())
    print(type(result['result']['hits']['hit']))

    xls = xlwt.Workbook()
    sht1 = xls.add_sheet('Sheet1', cell_overwrite_ok=True)
    clo = 0
    for key in result['result']['hits']['hit'][0]['info'].keys():
        sht1.write(0, clo, key)
        clo += 1

    info_1000 = result['result']['hits']['hit']
    for i in range(len(info_1000)):
        info_dict = result['result']['hits']['hit'][i]['info']
        for k, v in info_dict.items():
            sht1.write(i+1, foo(k), str(v))
    xls.save('./paper1.xls')
```
