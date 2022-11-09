- [[134\. 加油站](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/gas-station/)](#head1)
- [[146\. LRU缓存机制](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/lru-cache/)](#head2)
- [ [202\. 快乐数](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/happy-number/)](#head3)
- [ [289\. 生命游戏](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/game-of-life/)](#head4)
- [ [371\. 两整数之和](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/sum-of-two-integers/)](#head5)
- [ [412\. Fizz Buzz](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/fizz-buzz/)](#head6)

# <span id="head1">[134\. 加油站](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/gas-station/)</span>
>在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。


如果 sum(gas) < sum(cost) ，那么不可能环行一圈，这种情况下答案是 -1 。
对于加油站 i ，如果 gas[i] - cost[i] < 0 ，则不可能从这个加油站出发，因为在前往 i + 1 的过程中，汽油就不够了。

```
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



# <span id="head2">[146\. LRU缓存机制](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/lru-cache/)</span>

>设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。


实现一个可以存储 key-value 形式数据的数据结构，并且可以记录最近访问的 key 值。首先想到的就是用字典来存储 key-value 结构，这样对于查找操作时间复杂度就是 O(1)O(1)。

但是因为字典本身是无序的，所以我们还需要一个类似于队列的结构来记录访问的先后顺序，这个队列需要支持如下几种操作：

在末尾加入一项
去除最前端一项
将队列中某一项移到末尾



```
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




# <span id="head3"> [202\. 快乐数](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/happy-number/)</span>
>定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。



最终会得到 1。
最终会进入循环。
值会越来越大，最后接近无穷大。

|Digits|	Largest|	Next|
|:-:|:-:|:-:|
|1	|9	|81|
|2	|99	|162|
|3	|999	|243|
|4	|9999	|324|
|13	|9999999999999	|1053|
对于 3 位数的数字，它不可能大于 243。这意味着它要么被困在243 以下的循环内，要么跌到 1。4 位或 4 位以上的数字在每一步都会丢失一位，直到降到 3 位为止。所以我们知道，最坏的情况下，算法可能会在 243 以下的所有数字上循环，然后回到它已经到过的一个循环或者回到 11。但它不会无限期地进行下去，所以我们排除第三种选择。

```
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

# <span id="head4"> [289\. 生命游戏](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/game-of-life/)</span>

>给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1即为活细胞（live），或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
1.如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
2.如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
3.如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
4.如果死细胞周围正好有三个活细胞，则该位置死细胞复活；

>根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。


如果你直接根据规则更新原始数组，那么就做不到题目中说的 同步 更新。**假设你直接将更新后的细胞状态填入原始数组，那么当前轮次其他细胞状态的更新就会引用到当前轮已更新细胞的状态，但实际上每一轮更新需要依赖上一轮细胞的状态，是不能用这一轮的细胞状态来更新的。**

**拓展一些复合状态使其包含之前的状态。**
```
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

# <span id="head5"> [371\. 两整数之和](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/sum-of-two-integers/)</span>
**正数的补码就是原码，而负数的补码=原码符号位不变，其他位按位取反后+1。**

>不使用运算符 + 和 - ，计算两整数 a 、b 之和。

异或的一个重要特性是无进位加法。我们来看一个例子：
>a = 5 = 0101
b = 4 = 0100
a ^ b 如下：
0 1 0 1
0 1 0 0
0 0 0 1
a ^ b 得到了一个无进位加法结果，如果要得到 a + b 的最终值，我们还要找到进位的数，把这二者相加。
在位运算中，我们可以使用与操作获得进位：
>a = 5 = 0101
b = 4 = 0100
a & b 如下：
0 1 0 1
0 1 0 0
0 1 0 0
由计算结果可见，0100 并不是我们想要的进位，1 + 1 所获得的进位应该要放置在它的更高位，即左侧位上，因此我们还要把 0100 左移一位，才是我们所要的进位结果。

总结一下：
>1. a + b 的问题拆分为 (a 和 b 的无进位结果) + (a 和 b 的进位结果)
>2. 无进位加法使用异或运算计算得出
>3. 进位结果使用与运算和移位运算计算得出
>4. 循环此过程，直到进位为 0
---
在 Python 中，整数不是 32 位的，也就是说你一直循环左移并不会存在溢出的现象，这就需要我们手动对 Python 中的整数进行处理，手动模拟 32 位 INT 整型。

>a=-2  ; b=3 
a=1 1 1 0  
b=0 0 1 1
>1. 将输入数字转化成无符号整数
a &= 0xF # a = a & 0b1111 = 1110
b &= 0xF # b = 0011
>2. 计算无符号整数相加并的到结果
```
while b:
    carry = a & b
    a ^= b
    b = carry << 1 & 0xF # 模拟溢出操作
```
>过程为

| |0	|1	|2	|3|
|:-:|:-:|:-:|:-:|:-:|
|carry|		|0010|	0100|	1000|
|a	|1110|	1101|	1001|	0001|
|b	|0011|	0100|	1000|	0000|
>最后结果为 1 是没有问题的
但是对于 -2 + -2 , 最后结果为 1110 + 1110 = 1100 (12) 会出现问题
>3. 讲结果根据范围判定,映射为有符号整型
首先有符号整数的值域应该为 [-8, 7] 对于初步运算的结果,当结果小于8直接返回就可.
对于大于 7 的结果, 可知符号位必为1. 现在的问题转化为, 如何通过位运算把负数转换出来.
假设python用的是 8bit 有符号整数,当前结果为0000 1100, 对应8bit有符号整数为12, 但结果应该为-4对应8bit有符号整数为1111 1100
>通过两步转换可以得到:
>1.结果 与 0b1111 异或
>2.对异或结果按位取反
>```~(a ^ 0xF)```
```
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


# <span id="head6"> [412\. Fizz Buzz](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/fizz-buzz/)</span>
>写一个程序，输出从 1 到 n 数字的字符串表示。
>1. 如果 n 是3的倍数，输出“Fizz”；
>2. 如果 n 是5的倍数，输出“Buzz”；
>3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。


```
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

















