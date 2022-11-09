



# 汇总

[(10 封私信) 互联网公司最常见的面试算法题有哪些？ - 知乎 (zhihu.com)](https://www.zhihu.com/question/24964987/answer/586425979)

# 模拟

## [134\. 加油站](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/gas-station/)

> 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
> 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
> 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。

如果 sum(gas) < sum(cost) ，那么不可能环行一圈，这种情况下答案是 -1 。
对于加油站 i ，如果 gas[i] - cost[i] < 0 ，则不可能从这个加油站出发，因为在前往 i + 1 的过程中，汽油就不够了。

```python
class Solution:
    def canCompleteCircuit(self, gas, cost):
        n = len(gas)
        total_residue_tank, next_residue_tank = 0, 0
        starting_station = 0
        for i in range(n):
            total_residue_tank += gas[i] - cost[i]  # 所有加的油减去所有的消耗，最后剩下的油
            next_residue_tank += gas[i] - cost[i]  # 到达下一站后油箱里的油
            # 能否到达下一站
            if next_residue_tank < 0:
                # 不能到达下一站以下一站为起点
                starting_station = i + 1
                # 此时油箱里的油为空
                next_residue_tank = 0
        
        return starting_station if total_residue_tank >= 0 else -1
```



## [146\. LRU缓存机制](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/lru-cache/)

> 设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
> 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
> 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。

实现一个可以存储 key-value 形式数据的数据结构，并且可以记录最近访问的 key 值。首先想到的就是用字典来存储 key-value 结构，这样对于查找操作时间复杂度就是 O(1)O(1)。

但是因为字典本身是无序的，所以我们还需要一个类似于队列的结构来记录访问的先后顺序，这个队列需要支持如下几种操作：

在末尾加入一项
去除最前端一项
将队列中某一项移到末尾



```python
# 双向链表
class ListNode:
    def __init__(self, key=None, value=None):
            self.key = key
            self.value = value
            self.nex = None
            self.pre = None

class LRUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.hashmap = {}
        # 新建两个节点 head 和 tail
        self.head = ListNode()
        self.tail = ListNode()
        # 初始化链表为 head <-> tail
        self.head.nex = self.tail
        self.tail.pre  = self.head
    # 因为get与put操作都可能需要将双向链表中的某个节点移到末尾，所以定义一个方法
    def move_to_end(self, key):
        
        node = self.hashmap[key]
        # 先将哈希表key指向的节点node拎出来
        node.pre.nex = node.nex
        node.nex.pre = node.pre
        # 之后将node插入到尾节点前
        node.pre = self.tail.pre
        node.nex = self.tail
        self.tail.pre.nex = node
        self.tail.pre = node
        
    def get(self, key: int) -> int:
        if key in self.hashmap:
            # 如果已经在链表中了久把它移到末尾（变成最新访问的）
            self.move_to_end(key)
        node = self.hashmap.get(key, -1)
        return node if node == -1 else node.value

    def put(self, key: int, value: int) -> None:
        if key in self.hashmap:
            # 如果key本身已经在哈希表中了就不需要在链表中加入新的节点
            # 但是需要更新字典该值对应节点的value
            self.hashmap[key].value = value
            self.move_to_end(key)
        else:
            if len(self.hashmap) == self.capacity:
                # 去掉哈希表对应项
                self.hashmap.pop(self.head.nex.key)
                # 去掉最久没有被访问过的节点，即头节点之后的节点
                self.head.nex = self.head.nex.nex
                self.head.nex.pre = self.head
            # 如果不在的话就插入到尾节点前
            new = ListNode(key, value)
            self.hashmap[key] = new
            new.pre = self.tail.pre
            new.nex = self.tail
            self.tail.pre.nex = new
            self.tail.pre = new
# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
```



## [202\. 快乐数](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/happy-number/)

> 定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。



最终会得到 1。
最终会进入循环。
值会越来越大，最后接近无穷大。

| Digits |    Largest    | Next |
| :----: | :-----------: | :--: |
|   1    |       9       |  81  |
|   2    |      99       | 162  |
|   3    |      999      | 243  |
|   4    |     9999      | 324  |
|   13   | 9999999999999 | 1053 |

对于 3 位数的数字，它不可能大于 243。这意味着它要么被困在243 以下的循环内，要么跌到 1。4 位或 4 位以上的数字在每一步都会丢失一位，直到降到 3 位为止。所以我们知道，最坏的情况下，算法可能会在 243 以下的所有数字上循环，然后回到它已经到过的一个循环或者回到 11。但它不会无限期地进行下去，所以我们排除第三种选择。

```python
# 检测单链表是否有环，用快慢指针
class Solution:
    def get_next(self, num):
        all_squre_sum = 0
        while num > 0:
            num, digit = divmod(num, 10)
            all_squre_sum += digit ** 2
        return all_squre_sum

    def isHappy(self, n: int) -> bool:
        slow_index = n
        fast_index = self.get_next(n)
        while fast_index != 1 and slow_index != fast_index: 
            slow_index = self.get_next(slow_index)
            fast_index = self.get_next(self.get_next(fast_index))
        return fast_index == 1
```

## [289\. 生命游戏](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/game-of-life/)

> 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1即为活细胞（live），或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
> 1.如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
> 2.如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
> 3.如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
> 4.如果死细胞周围正好有三个活细胞，则该位置死细胞复活；

> 根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。

如果你直接根据规则更新原始数组，那么就做不到题目中说的 同步 更新。**假设你直接将更新后的细胞状态填入原始数组，那么当前轮次其他细胞状态的更新就会引用到当前轮已更新细胞的状态，但实际上每一轮更新需要依赖上一轮细胞的状态，是不能用这一轮的细胞状态来更新的。**

**拓展一些复合状态使其包含之前的状态。**

```python
class Solution:
    def gameOfLife(self, board: List[List[int]]) -> None:
        neighbors = [(-1, -1), (-1, 0), (-1, 1), 
                     (0, -1), (0, 1), 
                     (1, -1), (1, 0), (1, 1)]
        rows, cols = len(board), len(board[0])

        # 遍历面板每一个格子里的细胞,根据周围细胞改变联合状态
        for r in range(rows):
            for c in range(cols):
                # 对于每一个细胞统计其八个相邻位置里的活细胞数量
                live_neighbor = 0
                for i in neighbors:
                    if 0 <= r+i[0] < rows and 0 <= c+i[1] < cols and abs(board[r+i[0]][c+i[1]]) == 1:
                        # abs() 因为后面会把1改成-1
                        live_neighbor += 1
                # 规则 2 不造成状态变化

                # 规则 1 或规则 3  活细胞，周围活的少于两个多于三个，死
                if board[r][c] == 1 and (live_neighbor > 3 or live_neighbor < 2):
                    board[r][c] = -1
                # 规则 4 死细胞，周围活的为2或3，活
                if board[r][c] == 0 and live_neighbor == 3:
                    board[r][c] = 2
                    # # 遍历面板每一个格子里的细胞
        # 遍历 board 得到一次更新后的状态
        for r in range(rows):
            for c in range(cols):
                if board[r][c] > 0:
                    board[r][c] = 1
                else:
                    board[r][c] = 0
```

## [371\. 两整数之和](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/sum-of-two-integers/)

**正数的补码就是原码，而负数的补码=原码符号位不变，其他位按位取反后+1。**

> 不使用运算符 + 和 - ，计算两整数 a 、b 之和。

异或的一个重要特性是无进位加法。我们来看一个例子：

> a = 5 = 0101
> b = 4 = 0100
> a ^ b 如下：
> 0 1 0 1
> 0 1 0 0
> 0 0 0 1
> a ^ b 得到了一个无进位加法结果，如果要得到 a + b 的最终值，我们还要找到进位的数，把这二者相加。
> 在位运算中，我们可以使用与操作获得进位：
> a = 5 = 0101
> b = 4 = 0100
> a & b 如下：
> 0 1 0 1
> 0 1 0 0
> 0 1 0 0
> 由计算结果可见，0100 并不是我们想要的进位，1 + 1 所获得的进位应该要放置在它的更高位，即左侧位上，因此我们还要把 0100 左移一位，才是我们所要的进位结果。

总结一下：

> 1. a + b 的问题拆分为 (a 和 b 的无进位结果) + (a 和 b 的进位结果)
> 2. 无进位加法使用异或运算计算得出
> 3. 进位结果使用与运算和移位运算计算得出
> 4. 循环此过程，直到进位为 0

------

在 Python 中，整数不是 32 位的，也就是说你一直循环左移并不会存在溢出的现象，这就需要我们手动对 Python 中的整数进行处理，手动模拟 32 位 INT 整型。

> a=-2  ; b=3 
> a=1 1 1 0  
> b=0 0 1 1
>
> 1. 将输入数字转化成无符号整数
>    a &= 0xF # a = a & 0b1111 = 1110
>    b &= 0xF # b = 0011
> 2. 计算无符号整数相加并的到结果

```python
while b:
    carry = a & b
    a ^= b
    b = carry << 1 & 0xF # 模拟溢出操作
```

> 过程为

|       |  0   |  1   |  2   |  3   |
| :---: | :--: | :--: | :--: | :--: |
| carry |      | 0010 | 0100 | 1000 |
|   a   | 1110 | 1101 | 1001 | 0001 |
|   b   | 0011 | 0100 | 1000 | 0000 |

> 最后结果为 1 是没有问题的
> 但是对于 -2 + -2 , 最后结果为 1110 + 1110 = 1100 (12) 会出现问题
>
> 3. 讲结果根据范围判定,映射为有符号整型
>    首先有符号整数的值域应该为 [-8, 7] 对于初步运算的结果,当结果小于8直接返回就可.
>    对于大于 7 的结果, 可知符号位必为1. 现在的问题转化为, 如何通过位运算把负数转换出来.
>    假设python用的是 8bit 有符号整数,当前结果为0000 1100, 对应8bit有符号整数为12, 但结果应该为-4对应8bit有符号整数为1111 1100
>    通过两步转换可以得到:
>    1.结果 与 0b1111 异或
>    2.对异或结果按位取反
>    ```~(a ^ 0xF)```

```python
# 异或  - -> 无进位加法
# 与，向左移动一位 - - > 进位
class Solution:
    def getSum(self, a: int, b: int) -> int:
        # 截取后32位为无符号整数
        a &= 0xFFFFFFFF
        b &= 0xFFFFFFFF
        # 无符号整数加法
        while b:
            # 模拟进位
            carry = (a & b) << 1
            # 模模拟相加
            a ^= b
            b = carry & 0xFFFFFFFF  # 模拟溢出操作
        # 结果映射为有符号整数 
        return a if a < 0x80000000 else ~(a ^ 0xFFFFFFFF)

```

## [412\. Fizz Buzz](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/fizz-buzz/)

> 写一个程序，输出从 1 到 n 数字的字符串表示。
>
> 1. 如果 n 是3的倍数，输出“Fizz”；
> 2. 如果 n 是5的倍数，输出“Buzz”；
>    3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。

```python
class Solution:
    def fizzBuzz(self, n: int) -> List[str]:
        result_list = []
        # 映射关系放在散列表,可以对散列表添加/删除映射关系 
        key_value = {3: 'Fizz', 5: 'Buzz'}
        for i in range(1, n+1):
            result = ''
            for k in key_value:
                if i % k == 0:
                    result += key_value[k]
            if not result:
                result += str(i)
            result_list.append(result)
        return result_list

```



# 数组

## [376\. 摆动序列](https://leetcode-cn.com/problems/wiggle-subsequence/)

>- 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
>- 例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
>- 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中**删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。**

![](https://upload-images.jianshu.io/upload_images/18339009-f574f7bc028536ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
class Solution:
    def wiggleMaxLength(self, nums: List[int]) -> int:
        if len(nums) < 2: return len(nums)
        up, down = 1, 1
        for i in range(1, len(nums)):
            if nums[i] > nums[i-1]:
                up = down+1  # 找升序，在降序的基础上加1
            elif nums[i] < nums[i-1]:
                down = up+1  # 找降序，在升序的基础上加1
            else:  # 如果等于剔除该元素
                continue
        return max(up, down)
```

# 二分查找
```python
# 返回 x 在 arr 中的索引，如果不存在返回 -1
def binarySearch(arr, l, r, x):
    # 基本判断
    if r >= l:
        mid = int(l + (r - l)/2)
        # 元素整好的中间位置
        if arr[mid] == x:
            return mid
        # 元素小于中间位置的元素，只需要再比较左边的元素
        elif arr[mid] > x:
            return binarySearch(arr, l, mid-1, x)
        # 元素大于中间位置的元素，只需要再比较右边的元素
        else:
            return binarySearch(arr, mid+1, r, x)
    else:
        # 不存在
        return -1

# 测试数组
arr = [2, 3, 4, 10, 40]
x = 10
# 函数调用
result = binarySearch(arr, 0, len(arr)-1, x)

if result != -1:
    print("元素在数组中的索引为 %d" % result)
else:
    print("元素不在数组中")
```

















# 链表



## [24. 反转链表](https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/)

>定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
### 递归
>1. 定义递归函数功能，返回反转后头节点
>2. 寻找结束条件，若是节点为零或者1直接返回
>3. 寻找等价关系，reverse(head) = reverse(head.next);head.next.next=head;head.next=None
>![](https://upload-images.jianshu.io/upload_images/18339009-3dac4084ec28a89d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
 # 如果链表是1->2->3，
    def reverseList(self, head: ListNode) -> ListNode:
        # 递归终止条件是当前为空，或者下一个节点为空
        if not head or not head.next:
            return head
       #   head=1, head.next=2, 此时下一层返回的new_head是3
        new_head = self.reverseList(head.next)  # new_head指向3
        head.next.next = head  # 把2指向1
        head.next = None  # 把1指向None
        return new_head
```
### 双指针
next    -head           -pre         -head
>![](https://upload-images.jianshu.io/upload_images/18339009-05aa21c87f811971.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        if not head or not head.next:
             return head
        pre_index = next_index = None
        while head:  
        # head指向当前节点，一次循环后的下个节点是否为空？为空返回空节点的上一个节点
            # head->1->2->3->4->5  head指向当前节点
            next_index = head.next  # next 的指向
            head.next = pre_index   # head 的指向
            pre_index = head       #  pre 的指向
            head = next_index      # head 的指向
        return pre_index
```


# 找出环的入口点：

>从上面的分析知道，当fast和slow相遇时，slow还没有走完链表
假设fast已经在环内循环了n(1<= n)圈
环长r
假设slow走了s步，则fast走了2s步
$2*s = s + n  * r  $
$ s = n*r $
如果假设整个链表的长度是L
入口和相遇点的距离是x
起点到入口点的距离是a
$a + x = s = n * r$
$ L = a +r$
$a + x = (n - 1) * r + r  = (n - 1) * r + (L - a) $
$a = (n - 1) * r + (L -a -x) $
$a = n * r + x$

从起点到入口的距离a,相遇点到入口的距离x相等。
因此我们就可以分别用一个指针（ptr1, prt2），同时从起点、相遇点出发，每一次操作走一步，直到ptr1 == ptr2，此时的位置也就是入口点！





# 堆栈



# 哈希、队列



# 树、线段树





# 排序、二分检索、滑动窗口

**排序**

假设（不是一般性），某一对整数 a 和 b ，我们的比较结果是 a 应该在 b 前面，这意味着 $a⌢b>b⌢a $，其中$ ⌢$ 表示连接。

如果排序结果是错的，说明存在一个 c ， b 在 c 前面且 c 在 a的前面。这产生了矛盾，因为 $a⌢b>b⌢a $和$b⌢c>c⌢b $意味着$ a⌢c>c⌢a$ 。
换言之，我们的自定义比较方法保证了传递性，所以这样子排序是对的。



*   ## [179\. 最大数](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/largest-number/)
>给定一组非负整数 nums，重新排列它们每位数字的顺序使之组成一个最大的整数。
>注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。

>![](https://upload-images.jianshu.io/upload_images/18339009-72b3b70a8a94fa31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>![](https://upload-images.jianshu.io/upload_images/18339009-58a77e87f7373ef0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```python
class sort_rule(str):
    def __lt__(self, y):
        return self+y > self+x

class Solution:
    def largestNumber(self, nums: List[int]) -> str:
        sorted_nums = ''.join(sorted(map(str, nums), key=sort_rule))
        return '0' if sorted_nums[0] == '0' else sorted_nums
```

*   ## [324\. 摆动排序 II](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/wiggle-sort-ii/)


```python
class Solution:
    def wiggleSort(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        s = len(nums)-len(nums)//2
        nums[0::2], nums[1::2] = sorted(nums)[:s][::-1], sorted(nums)[s:][::-1]
# [::-1]防止连续的数连续的放进去


```
##  [376\. 摆动序列](https://leetcode-cn.com/problems/wiggle-subsequence/)

>如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
>例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
>给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。

```python
class Solution:
    def wiggleMaxLength(self, nums: List[int]) -> int:
        if len(nums) < 2: return len(nums)
        up, down = 1, 1
        for i in range(1, len(nums)):
            if nums[i] > nums[i-1]:
                up = down+1  # 直到遇到一个升序，才在降序的基础上加1
            elif nums[i] < nums[i-1]:
                down = up+1
            else:  # 如果等于剔除该元素
                continue
        return max(up, down)
```



# 动态规划

**案例一、简单的一维 DP**

## [剑指 Offer 10- II. 青蛙跳台阶问题](https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/)

一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

>#####(1)定义数组元素的含义
>跳上一个 i 级的台阶总共有 dp[i] 种跳法
>#####(2)找出数组元素间的关系式
>目的是要求 dp[n]，动态规划的题，就是把一个规模比较大的问题分成几个规模比较小的问题，然后由小的问题推导出大的问题。
>所有可能的跳法的，所以有 dp[n] = dp[n-1] + dp[n-2]。
>#####(3)找出初始条件
>数组是不允许下标为负数的，所以对于 0、1、2，我们必须要直接给出它的数值，相当于初始值，显然，dp[0] = 1，dp[1] = 1, dp[2] = 2。

```python
class Solution:
    def numWays(self, n: int) -> int:
        dp = [1]*(n+1)
        # 初始条件dp[0] = 1，dp[1] = 1
        for i in range(2, n+1):
            dp[i] = dp[i-1]+dp[i-2]
        return dp[-1] % 1000000007 
```

**案例二：二维数组的 DP**

## [62. 不同路径](https://leetcode-cn.com/problems/unique-paths/)

>一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
>机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。问总共有多少条不同的路径？
>![](https://upload-images.jianshu.io/upload_images/18339009-da4eab5a346c6cb5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>**步骤一、定义数组元素的含义**
>dp[i] [j]的含义为：当机器人从左上角走到(i, j) 这个位置时，一共有 dp[i] [j] 种路径。数组是从下标为 0 开始算起的，所以 右下角的位置是 (m-1, n - 1)dp[m-1] [n-1] 就是我们要的答案了。 
>注意，这个网格相当于一个二维数组，所以 dp[m-1] [n-1] 就是我们要找的答案。 
>**步骤二：找出关系数组元素间的关系式**
>由于机器人可以向下走或者向右走，所以有两种方式到达
>一种是从 (i-1, j) 这个位置走一步到达
>一种是从(i, j - 1) 这个位置走一步到达
>因为是计算所有可能的步骤，所以是把所有可能走的路径都加起来，所以关系式是 dp[i] [j] = dp[i-1] [j] + dp[i] [j-1]。
>**步骤三、找出初始值**
>初始值如下：dp[0] [0….n-1] = 1; 相当于最上面一行，机器人只能一直往左走
>dp[0…m-1] [0] = 1;  相当于最左面一列，机器人只能一直往下走


```python
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        dp = [[1]*n for _ in range(m)]  # m行n列
        # 初始值dp[0] [0….n-1] = 1; dp[0…m-1] [0] = 1
        for i in range(1, m):
            for j in range(1, n):
                dp[i][j] = dp[i][j-1]+dp[i-1][j]
        return dp[-1][-1]

# 优化
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        cur = [1] * n
        for i in range(1, m):
            for j in range(1, n):
                cur[j] += cur[j-1]
        return cur[-1]
```

## [64. 最小路径和](https://leetcode-cn.com/problems/minimum-path-sum/)

>给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
>说明：每次只能向下或者向右移动一步。
>![](https://upload-images.jianshu.io/upload_images/18339009-a691fdff2244be63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
>输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
>输出：7
>解释：因为路径 1→3→1→1→1 的总和最小。

```python
class Solution:
    def minPathSum(self, grid: List[List[int]]) -> int:
        m, n = len(grid), len(grid[0])
        # 设置初始状态
        dp = [[grid[0][0]]*n for _ in range(m)]
       # 转移方程
       # 第一行、第一列不同
        for i in range(1, m):
            dp[i][0] = dp[i-1][0]+grid[i][0]
        for j in range(1, n):
            dp[0][j] = dp[0][j-1]+grid[0][j]
        for i in range(1, m):
            for j in range(1, n):
                dp[i][j] += min(dp[i-1][j], dp[i][j-1])
        return dp[-1][-1]
```
优化
```python
class Solution:
    def minPathSum(self, grid: List[List[int]]) -> int:
        m = len(grid)
        n = len(grid[0])
        dp = [0]*n
        for i in range(m):
            for j in range(n):
                if i==0:
                    # 第一行初始化
                    dp[j] = dp[j-1] + grid[i][j]
                else:
                    dp[j] = min(dp[j], dp[j-1]) + grid[i][j]
        return dp[-1]
```

# 图论



# 数学、位运算





# 字符串

```python
string.capitalize() 第一个字符大写
string.count(str, beg=0, end=len(string)) 返回 str 在 string 里面出现的次数，beg 、 end 指定范围
string.endswith(obj, beg=0, end=len(string)) 检查字符串是否以 obj 结束
string.find(str, beg=0, end=len(string)) 返回str开始的索引值，否则返回-1
string.index(str, beg=0, end=len(string)) 跟find()方法一样，只不过如果str不在 string中会报一个异常.
string.isalnum() string 至少有一个字符并且所有字符都是字母或数字则返回 True
string.isalpha()  string 至少有一个字符并且所有字符都是字母则返回 True
string.isdecimal()  string 只包含十进制数字则返回 True 
string.isdigit()  string 只包含数字则返回 True
string.islower() string 都是小写，则返回 True
string.isnumeric()  string 中只包含数字字符，则返回 Tru
string.isspace()  string 中只包含空格，则返回 True
string.istitle()  string 是标题化的(见 title())则返回 True
string.isupper() string 都是大写，则返回 True
string.join(seq) 以 string 作为分隔符，将 seq 中所有的元素(的字符串表示)合并为一个新的字符串
string.lower() 转换 string 中所有大写字符为小写.
string.lstrip() 截掉 string 左边的空格
max(str) 返回字符串 str 中最大的字母。
min(str) 返回字符串 str 中最小的字母。
string.replace(str1, str2, num=string.count(str1)) 把 string 中的 str1 替换成 str2,替换不超过 num 次.
string.split(str="", num=string.count(str)) 以 str 为分隔符切片 string，仅分隔 num+ 个子字符串
string.startswith(obj, beg=0,end=len(string)) 检查字符串是否是以 obj 开头，是则返回 True
string.strip([obj]) 在 string 上执行 lstrip()和 rstrip()
string.swapcase() 翻转 string 中的大小写
string.title() 返回"标题化"的 string,就是说所有单词都是以大写开始，其余字母均为小写(见 istitle())
string.translate(str, del="") 根据 str 给出的表(包含 256 个字符)转换 string 的字符,要过滤掉的字符放到 del 参数中
string.upper() 转换 string 中的小写字母为大写
```
## 【125\. 验证回文串](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/valid-palindrome/)

>给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
>说明：本题中，我们将空字符串定义为有效的回文串。
>输入: "A man, a plan, a canal: Panama"
>输出: true

- 逆字符串

  去除非文字，全转成小写，和自身逆对比

```python
class Solution:
    def isPalindrome(self, s: str) -> bool:
        alnum = ''.join(w.lower() for w in s if w.isalnum())
        return alnum == alnum[::-1]
```

- 使用双指针。初始时，左右指针分别指向两侧，随后我们不断地将这两个指针相向移动，每次移动一步，并判断这两个指针指向的字符是否相同。当这两个指针相遇时，就说明是回文串。

```python
class Solution:
    def isPalindrome(self, s: str) -> bool:
        pre = 0
        nex = len(s) - 1
        while pre < nex:
            # 找到下一个字母或数字
            while pre < nex and not s[pre].isalnum():  # 忽略非字母数字
                pre += 1
            while pre < nex and  not s[nex].isalnum():  # 忽略非字母或数字
                nex -= 1
            # 判断
            if pre < nex:
                if s[pre].lower() != s[nex].lower():  # 忽略大小写
                    return False
                pre += 1
                nex -= 1
        return True
```

## [131\. 分割回文串](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/palindrome-partitioning/)

>给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
>返回 s 所有可能的分割方案。
>示例:
>输入: "aab"
>输出:
>[ ["aa","b"],  ["a","a","b"] ]

![](https://upload-images.jianshu.io/upload_images/18339009-9f5d4cbf37e76e7d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)







## [139\. 单词拆分](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/word-break/)



## [140\. 单词拆分 II](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/word-break-ii/)



## [208\. 实现 Trie (前缀树)](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/implement-trie-prefix-tree/)

>实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。

Trie 是一颗非典型的多叉树模型
一般的多叉树的结点是这样的：
```python
struct TreeNode {
    VALUETYPE value;    //结点值
    TreeNode* children[NUM];    //指向孩子结点
};
```
而 Trie 的结点是这样的(假设只包含'a'~'z'中的字符)：
```python
struct TrieNode {
    bool isEnd; //该结点是否是一个串的结束
    TrieNode* next[26]; //字母映射表
};
```
这时字母映射表next 的妙用就体现了，TrieNode* next[26]中保存了对当前结点而言下一个可能出现的所有字符的链接，因此我们可以通过一个父结点来预知它所有子结点的值：



>示例:
>Trie trie = new Trie();
>trie.insert("apple");
>trie.search("apple");   // 返回 true
>trie.search("app");     // 返回 false
>trie.startsWith("app"); // 返回 true
>trie.insert("app");   
>trie.search("app");     // 返回 true

>说明:
>你可以假设所有的输入都是由小写字母 a-z 构成的。
>保证所有输入均为非空字符串。



## [212\. 单词搜索 II](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/word-search-ii/)

>给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
>单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

>输入: 
>words = ["oath","pea","eat","rain"] 
>board =
>[ [**'o'**,**'a'**,'a','n'],
>    ['e',    **'t'**,     **'a'**,  **'e'**],
>    ['i',  **'h'**,  'k',  'r'],
>    ['i',  'f',  'l',  'v']  ]
>输出: ["eat","oath"]




```

```





## [242\. 有效的字母异位词](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/valid-anagram/)

>给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
>输入: s = "anagram", t = "nagaram"
>输出: true

逐个去除b里的a中字符
```python
class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        if len(s) != len(t):
            return False
        s = list(s)
        t = list(t)
        for i in s:
            try:
                t.remove(i)
            except ValueError as e:
                return False
        return True
```
第一次循环哈希表记录，第二次循环删去哈希表记录，最后统计哈希表每个值是否都为0

```python
class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        if len(s) != len(t):
            return False
        word_count = {}
        # 建立哈希表
        for i in s:
            if i not in word_count:
                word_count[i] = 1
            else:
                word_count[i] += 1
        # 清空哈希表
        for i in t:
            if i in word_count:
                word_count[i] -= 1
            else:
                return False
        # all([]) ture any([]) false
        return not any(list(word_count.values()))  
```
## [387\. 字符串中的第一个唯一字符](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/first-unique-character-in-a-string/)

>给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

哈希表记录每个字符出现次数
```python
class Solution:
    def firstUniqChar(self, s: str) -> int:
        #  count = collections.Counter(s)
        word_count = {}
        # 建立哈希表
        for i in s:
            if i not in word_count:
                word_count[i] = 1
            else:
                word_count[i] +=1
         # 根据哈希表判断出现次数
        for index, word in enumerate(s):
            if word_count[word]  == 1:
                return index
        return -1
```

## [344\. 反转字符串](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/reverse-string/)

>编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
```python
# s[:]=s[::-1]
# 双指针
 l,r=0,len(s)-1
        while l<r:
            s[l],s[r]=s[r],s[l]
            l+=1
            r-=1
```

## [3.无重复字符的最长子串长度](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

>示例 1:
>输入: s = "abcabcbb"
>输出: 3 
>解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

使用数组作为滑动窗口
```python
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        if not s: return 0
        substr = []
        max_len = 0
        for word in s:
            if word not in substr:
                # 扩展窗口
                substr.append(word)
            else:
                # 从窗口中移除重复字符及之前的字符串部分
                substr = substr[substr.index(word)+1:]
                # 扩展窗口
                substr.append(word)
            max_len = max(max_len, len(substr))
        return max_len
```
法1中容器的伸缩涉及内存分配,所以方法2换成位置指针省掉了内存分配

直观的滑动窗口方法需要维护数组的增删，实际上比较耗时。使用双指针（索引），记录滑动窗口起始和结束的索引值，可以减除数组增删操作，提高效率，使用指针位移以及从原数组中截取，代替原来的窗口元素增删操作
```python
def lengthOfLongestSubstring(self, s: str) -> int:
        # 字符串为空则返回零
        if not s: return 0
        max_len = 0   
        left_index, right_index = 0, 0  # 双指针
        for word in s:
            # 如果字符不在滑动窗口中，则直接扩展窗口
            if word not in s[left_index:right_index]:
                # 右指针右移一位
                right_index += 1
            else:
                # 左指针右移 word在substr中的索引 位
                left_index += s[left_index:right_index].index(word) + 1
                # 右指针右移一位
                right_index += 1
            max_len = max(right_index - left_index, max_len)
        return max_len 
```





**Hash（字典），滑动窗口，双指针**
使用字典记录任意字符最近的索引值，**字典查询时间复杂度为O(1)，相比数组查询，效率更高**
该算法的难点在于理解word_index[word] > ignore_end_index如果不大于说明word已经被丢弃；大于说明word未被丢弃需要，更新ignore_end_index

```python
    def lengthOfLongestSubstring(self, s: str) -> int:
        ignore_end_index = -1          # 指向子串左边一个字符，即丢弃的子串的尾部， 初始值为 -1，还没有开始移动
        max_len = 0          # 记录最大的长度
        word_index = {}          # 滑动窗口，任意字符最后出现位置的索引
        for index, word in enumerate(s): 
             # 如果 word出现过 且  最近一次出现的索引大于ignore_end，意味着需要丢弃这个词前面的部分
            # 如果不大于说明word已经被丢弃；大于说明word未被丢弃需要，更新ignore_end_index                   
            if word in word_index and word_index[word] > ignore_end_index:  
                ignore_end_index = word_index[word]  # 新的子串开始
                word_index[word] = index  # 更新word的索引
            else:
                # word未出现过
                word_index[word] = index  # 子串变长
                max_len = max(max_len, index - ignore_end_index)   # 更新最大长度
        return max_len
```