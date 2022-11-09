- [ 数据库事务ACID四大特性](#head1)
- [ 事务并发所引起的问题：](#head2)
	- [ mysql的4种事务隔离级别](#head3)
- [ 优化技巧](#head4)
- [delete和truncate table](#head5)
- [ 顺序](#head6)
	- [ [获取员工其当前的薪水比其manager当前薪水还高的相关信息](https://www.nowcoder.com/practice/f858d74a030e48da8e0f69e21be63bef?tpId=82&tqId=29777&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)](#head7)
	- [ [查找所有员工自入职以来的薪水涨幅情况](https://www.nowcoder.com/practice/fc7344ece7294b9e98401826b94c6ea5?tpId=82&tqId=29773&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)](#head8)
	- [ [对所有员工的薪水按照salary进行按照1-N的排名，相同salary并列且按照emp_no升序排列](https://www.nowcoder.com/practice/b9068bfe5df74276bd015b9729eec4bf?tab=answerKey)](#head9)
	- [ [汇总各个部门当前员工的title类型的分配数目，即结果给出部门编号dept_no、dept_name、其部门下所有的员工的title以及该类型title对应的数目count，结果按照dept_no升序排](https://www.nowcoder.com/practice/4bcb6a7d3e39423291d2f7bdbbff87f8?tpId=82&tqId=29778&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)](#head10)
	- [ 复购率](#head11)
	- [ SQL取出所有用户对商品的行为特征](#head12)




# <span id="head1"> 数据库事务ACID四大特性</span>

“一致性”为最终目标，其他特性都是为达到这个目标而采取的措施和手段。

- 原子性：事务中包括的所有操作要么都做，要么都不做
- 持久性：事务一旦提交.对数据库的**改变是永久的**
- 隔离性：一个事务内部的操作及使用的数据**对并发的其他事务是隔离的**
- 一致性：事务必须使数据库从一个一致性状态变到另一个一致性状态

# <span id="head2"> 事务并发所引起的问题：</span>

- 脏读：**一个事务读到另外一个事务还没有提交的数据**，我们称之为脏读。（进行存款事务时候，还没有存完，允许查询事务）

事务B执行过程中修改了数据X，在未提交前，事务A读取了X，而事务B却回滚了，这样事务A就形成了脏读。

- 不可重复读(Unrepeatable Read)：在数据库访问中，**一个事务范围内两个相同的查询却返回了不同数据**。这是由于查询时系统中其他事务修改的提交而引起的。

事务A首先读取了一条数据，然后执行逻辑的时候，事务B将这条数据改变了，然后事务A再次读取的时候，发现数据不匹配了，就是所谓的不可重复读了。

- 幻读(phantom read)：是指当事务不是独立执行时发生的一种现象，例如第**一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行**。同时，**第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据**。那么，以后就会发生操作**第一个事务的用户发现表中还有没有修改的数据行**，就好象发生了幻觉一样.

###### <span id="head3"> mysql的4种事务隔离级别</span>

1、**未提交读**(Read Uncommitted)：允许脏读，也就是可能读取到其他会话中未提交事务修改的数据
2、**提交读**(Read Committed)：只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)
3、**可重复读**(Repeated Read)：可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读，但是innoDB解决了幻读
4、**串行读**(Serializable)：完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞

# <span id="head4"> 优化技巧</span>

- 明知只有一条查询结果，那请使用 “LIMIT 1”
“LIMIT 1”可以避免全表扫描，找到对应结果就不会再继续扫描了。
- 尽量避免使用 “SELECT *”
如果不查询表中所有的列，尽量避免使用 SELECT *，因为它会进行全表扫描，不能有效利用索引，增大了数据库服务器的负担，以及它与应用程序客户端之间的网络IO开销。


# <span id="head5">delete和truncate table</span>
*   如果一个表中有自增字段，使用truncate table和没有WHERE子句的delete删除所有记录后，这个自增字段将起始值恢复成1.如果你不想这样做的话，可以在delete语句中加上永真的WHERE，  `delete FROM table1 WHERE 1`;



# <span id="head6"> 顺序</span>

![](https://upload-images.jianshu.io/upload_images/18339009-cd689024f076a36a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 **聚合语句(sum,min,max,avg,count)要比having子句优先执行**，所以**having后面可以使用聚合函数**。而where子句在查询过程中执行优先级别优先于聚合语句(sum,min,max,avg,count)，**所有where条件中不能使用聚合函数。**

#　题

## <span id="head7"> [获取员工其当前的薪水比其manager当前薪水还高的相关信息](https://www.nowcoder.com/practice/f858d74a030e48da8e0f69e21be63bef?tpId=82&tqId=29777&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)</span>

第一列给出员工的emp_no，
第二列给出其manager的manager_no，
第三列给出该员工当前的薪水emp_salary,
第四列给该员工对应的manager当前的薪水manager_salary

>![](https://upload-images.jianshu.io/upload_images/18339009-39333fa6d74d5805.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-a9e25a3783e84111.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-75751865ddaaffb9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```sql
SELECT es.emp_no, ms.emp_no, es.salary, ms.salary
/*员工工资表*/
FROM (SELECT e.emp_no, e.dept_no, s.salary 
      FROM dept_emp AS e 
      INNER JOIN salaries AS s 
      ON e.emp_no=s.emp_no 
      /*当前？*/
      WHERE e.to_date='9999-01-01' AND s.to_date='9999-01-01') AS es
/*经理工资表*/
INNER JOIN 
    (SELECT m.emp_no, m.dept_no, s.salary 
     FROM dept_manager AS m 
     INNER JOIN salaries AS s 
     ON m.emp_no=s.emp_no 
     WHERE m.to_date='9999-01-01' AND s.to_date='9999-01-01') AS ms
ON es.dept_no=ms.dept_no
WHERE es.salary>ms.salary;
```

---
## <span id="head8"> [查找所有员工自入职以来的薪水涨幅情况](https://www.nowcoder.com/practice/fc7344ece7294b9e98401826b94c6ea5?tpId=82&tqId=29773&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)</span>
给出员工编号emp_no以及其对应的薪水涨幅growth，并按照growth进行升序，以上例子输出为
（注:可能有employees表和salaries表里存在记录的员工，有对应的员工编号和涨薪记录，但是已经离职了，离职的员工salaries表的最新的to_date!='9999-01-01'，这样的数据不显示在查找结果里面，以上emp_no为2的就是这样的）

>![](https://upload-images.jianshu.io/upload_images/18339009-b796d8e60ae91ad8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```sql
SELECT cs.emp_no, (cs.salary-fs.salary) AS growth
FROM (SELECT emp_no, salary 
      FROM salaries 
      WHERE to_date='9999-01-01') AS cs /*当前的工资*/
INNER JOIN 
    (SELECT e.emp_no, s.salary
     FROM salaries AS s
     INNER JOIN employees AS e
     ON s.emp_no=e.emp_no 
     WHERE s.from_date=e.hire_date)AS fs /*刚入职的工资*/
ON cs.emp_no=fs.emp_no 
ORDER BY growth ASC;
```

---
## <span id="head9"> [对所有员工的薪水按照salary进行按照1-N的排名，相同salary并列且按照emp_no升序排列](https://www.nowcoder.com/practice/b9068bfe5df74276bd015b9729eec4bf?tab=answerKey)</span>
>![](https://upload-images.jianshu.io/upload_images/18339009-0f4de11380f1c1c5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```sql
SELECT s1.emp_no, s1.salary, 
/*计算比自己工资高的人数，如果自己的工资是最高的，即没有人比自己工资高，那么计算后结果为 0。
如果自己是第二高，那么结果为 1。最后在结果上 + 1，就是上面所说的会跳数字方式。*/
    (SELECT COUNT(DISTINCT s2.salary)+1
    FROM salaries AS s2
    WHERE s2.salary>s1.salary AND s2.to_date='9999-01-01') AS rank
FROM salaries AS s1
WHERE s1.to_date='9999-01-01' 
ORDER BY rank, s1.emp_no;
```
窗口函数
```sql
SELECT emp_no, salary, 
    DENSE_RANK() OVER (ORDER BY salary DESC) AS rank
FROM salaries
WHERE to_data='9999-01-01'
ORDER BY rank, emp_no;
```
---

## <span id="head10"> [汇总各个部门当前员工的title类型的分配数目，即结果给出部门编号dept_no、dept_name、其部门下所有的员工的title以及该类型title对应的数目count，结果按照dept_no升序排](https://www.nowcoder.com/practice/4bcb6a7d3e39423291d2f7bdbbff87f8?tpId=82&tqId=29778&rp=1&ru=%2Fta%2Fsql&qru=%2Fta%2Fsql%2Fquestion-ranking&tab=answerKey)</span>
>![](https://upload-images.jianshu.io/upload_images/18339009-4a2525b404de3e2b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
先将dept_emp和titles内联，生成带emp_no, dept_no, title 的员工信息表，再和departments右联。
```sql
select d.dept_no, d.dept_name, et.title, count(*)
from (select e.emp_no, e.dept_no, t.title 
      from dept_emp as e 
      inner join titles as t 
      on e.emp_no=t.emp_no
      where e.to_date='9999-01-01' and t.to_date='9999-01-01') as et
left join departments as d
on et.dept_no=d.dept_no 
group by d.dept_no, et.title
```

## <span id="head11"> 复购率</span>
>![](https://upload-images.jianshu.io/upload_images/18339009-a756bd5b97c99b4e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```date_format(paidtime, '%Y-%m-01')```
```date_sub(t2.m, interval 1 month)```
```SQL
select t1.pt, count(t1.pt), count(t2.pt) 
from(select userid, date_format(paidtime, '%Y-%m-01') as pt 
         from orderinfo 
         where ispaid='已支付'
         group by userid, pt) as t1
left join(select userid, date_format(paidtime, '%Y-%m-01') as pt 
            from orderinfo 
            where ispaid='己支付'
            group by userid, pt) as t2
on t1.userid=t2.userid and t1.pt=date_sub(t2.pt, interval 1 month)
group by t1.pt;
```





## <span id="head12"> SQL取出所有用户对商品的行为特征</span>

 请用一句SQL取出所有用户对商品的行为特征，特征分为已购买、购买未收藏、收藏未购买、收藏且购买（输出结果如下表）

>![订单事务表orders](https://upload-images.jianshu.io/upload_images/18339009-7831de101e45b22f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![收藏事务表favorites](https://upload-images.jianshu.io/upload_images/18339009-ab2ca246d7e0337b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```case when pay time is not null then 1 else 0 end```

```sql
(select a.user_id,a.item_id, 
(CASE when a.item_id is not null then 1 else 0 end) as '已购买',
(CASE when a.item_id is not null and b.item_id is null then 1 else 0 end) as '购买未收藏',
(CASE when a.item_id is not null and b.item_id is not null then 1 else 0 end) as '收藏且购买',
(CASE when a.item_id is null and b.item_id is not null then 1 else 0 end) as '收藏未购买'
from orders a 
left join favorites b 
on a.user_id  = b.user_id and a.item_id = b.item_id )
union
(select b.user_id,b.item_id, 
(CASE when a.item_id is not null then 1 else 0 end) as '已购买',
(CASE when a.item_id is not null and b.item_id is null then 1 else 0 end) as '购买未收藏',
(CASE when a.item_id is not null and b.item_id is not null then 1 else 0 end) as '收藏且购买',
(CASE when a.item_id is null and b.item_id is not null then 1 else 0 end) as '收藏未购买'
from orders a 
right join favorites b 
on a.user_id  = b.user_id and a.item_id = b.item_id ); 
```

![left join favorites](https://img-blog.csdnimg.cn/20210411004513446.png)

left join favorites

![](https://i.loli.net/2021/05/14/D9gmnJFCNa8EosM.png)

![](https://i.loli.net/2021/05/14/x8R5CY2QWdbaotn.png)

![](https://i.loli.net/2021/05/14/EgBVdvmcrkPAzlK.png)

## 有一张学生成绩表sc（sno 学号，class 课程，score 成绩），请查询出每个学生的英语、数学的成绩（行转列，一个学生只有一行记录）

```if(class='english',score,0)```
```in('english','math')```

```sql
select sno,
sum(if(class='english',score,0)) as english,
sum( if(class='math',score,0) ) as math
from sc
where class in('english','math')
group by sno
```
## 各科成绩前两名的记录
```sql
select * 
from (select *, row_number() over (partition by 课程号 order by 成绩 desc) as 排名
      from score) as a
where 排名<=2;
```
>![](https://upload-images.jianshu.io/upload_images/18339009-e4f5c75a57625921.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## 连续登录问题

假设有一张含两列(用户id、登陆日期)的表，查询每个用户连续登陆的天数、最早登录时间、最晚登录时间和登录次数。
第一步，先用row_number（）函数排序，然后用登录日期减去排名，得到辅助列日期，**如果辅助列日期是相同的话，证明用户是连续登录。**

>![](https://upload-images.jianshu.io/upload_images/18339009-dbfb648c3f8026d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>![](https://upload-images.jianshu.io/upload_images/18339009-e8ee880d3d447c6b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>第二步，用user_id和辅助列作为分组依据，分到一组的就是连续登录的用户。在每一组中最小的日期就是最早的登陆日期，最大的日期就是最近的登陆日期，对每个组内的用户进行计数,就是用户连续登录的天数。
>![](https://upload-images.jianshu.io/upload_images/18339009-29ca17ec06cbc202.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>![](https://upload-images.jianshu.io/upload_images/18339009-663d74d2e2a7a2c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>## 求解连续登录五天的用户
>
>第一步，用lead函数进行窗口偏移，查找每个用户5天后的登陆日期是多少，如果是空值，说明他没有登录。运行的代码为
>![](https://upload-images.jianshu.io/upload_images/18339009-f9e15d92b0b016a8?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>在lead函数里，为何偏移行数的参数设置为4而不是5呢，这是因为求解的是连续登录5天的用户，包括当前行在内一共是5行，所以应该向下偏移4行。运行的结果如下：
>![](https://upload-images.jianshu.io/upload_images/18339009-3dfad55da90d5528?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>第二步，用datediff函数计算 （日期-第五次登陆日期）+1是否等于5，等于5证明用户是连续5天登录的，为空值或者大于5都不是5天连续登陆的用户。
>第三步，用where设定条件，差值=5筛选连续登录的用户。
>![](https://upload-images.jianshu.io/upload_images/18339009-13b2e5abdf484efe?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>用lead函数求解连续登录的问题还有一个好处就是当表中的数据不在同一个月份时也可以完美的解决，不用再考虑月份带来的影响。

---
## 2018年9月1日当天，总在线时长超过20分钟以上的用户的付费总金额
A表为游戏登出表，用户每次下线时记录一条：dt（登出日期），servertime（登出时间），userid（用户id），onlinetime（当次在线时长（单位：秒），int）；
B表为游戏充值表，用户每次充值记录一条：dt（充值日期），servertime（充值时间），userid（用户id），money（充值金额，int）


```sql
SELECT A.userid, SUM(IFNULL(B.money, 0))
FROM A
LEFT JOIN B 
ON A.dt = B.dt AND A.userid = B.userid
WHERE A.dt = '2018-09-01'
GROUP BY A.userid
HAVING SUM(A.onlinetime) > 20*60;
```
