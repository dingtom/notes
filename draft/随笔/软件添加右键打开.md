
1. win+R -> regedit

2. 计算机\HKEY_LOCAL_MACHINE\SOFTWARE\Classes\Directory\shell 

3. 新建项（软件名）

4. 默认值数据改为：Open Folder as PyCharm Community Edition Project

5. 新建字符串值（名称：Icon 数据：软件安装路径（快捷方式右键属性目标）） 

6. 新建项command 默认值数据改为软件安装路径+%1

   ```
   "C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.3\bin\idea64.exe" "%1"
   ```

   


