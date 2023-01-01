---
        # 文章标题
        title: "code-element"
        # 分类
        categories: 
            - code
        # 发表日期
        date: 2023-01-01T14:28:53+08:00
--- 

# el-table 记住选中状态

```
1.el-table加上行唯一id :row-key="(row) => row.id" 注：id是该行唯一值

2.在选项列中加上 :reserve-selection="true"

3.在完成后，需要清空选中的this.$refs.multipleTable.clearSelection()

this.$refs.table.selection 存储选中的item
```

