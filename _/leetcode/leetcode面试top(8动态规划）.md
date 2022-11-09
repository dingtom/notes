- [案例一、简单的一维 DP](#head1)
- [案例二：二维数组的 DP](#head2)
##### <span id="head1">案例一、简单的一维 DP</span>
[剑指 Offer 10- II. 青蛙跳台阶问题](https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/)
一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

>#####(1)定义数组元素的含义
>跳上一个 i 级的台阶总共有 dp[i] 种跳法
>#####(2)找出数组元素间的关系式
>目的是要求 dp[n]，动态规划的题，就是把一个规模比较大的问题分成几个规模比较小的问题，然后由小的问题推导出大的问题。
所有可能的跳法的，所以有 dp[n] = dp[n-1] + dp[n-2]。
>#####(3)找出初始条件
>数组是不允许下标为负数的，所以对于 0、1、2，我们必须要直接给出它的数值，相当于初始值，显然，dp[0] = 1，dp[1] = 1, dp[2] = 2。
```
class Solution:
    def numWays(self, n: int) -> int:
        dp = [1]*(n+1)
        # 初始条件dp[0] = 1，dp[1] = 1
        for i in range(2, n+1):
            dp[i] = dp[i-1]+dp[i-2]
        return dp[-1] % 1000000007 
```

#### <span id="head2">案例二：二维数组的 DP</span>
[62. 不同路径](https://leetcode-cn.com/problems/unique-paths/)
>一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。问总共有多少条不同的路径？
>![](https://upload-images.jianshu.io/upload_images/18339009-da4eab5a346c6cb5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>**步骤一、定义数组元素的含义**
dp[i] [j]的含义为：当机器人从左上角走到(i, j) 这个位置时，一共有 dp[i] [j] 种路径。数组是从下标为 0 开始算起的，所以 右下角的位置是 (m-1, n - 1)dp[m-1] [n-1] 就是我们要的答案了。 
注意，这个网格相当于一个二维数组，所以 dp[m-1] [n-1] 就是我们要找的答案。 
>**步骤二：找出关系数组元素间的关系式**
由于机器人可以向下走或者向右走，所以有两种方式到达
一种是从 (i-1, j) 这个位置走一步到达
一种是从(i, j - 1) 这个位置走一步到达
因为是计算所有可能的步骤，所以是把所有可能走的路径都加起来，所以关系式是 dp[i] [j] = dp[i-1] [j] + dp[i] [j-1]。
>**步骤三、找出初始值**
初始值如下：dp[0] [0….n-1] = 1; 相当于最上面一行，机器人只能一直往左走
dp[0…m-1] [0] = 1;  相当于最左面一列，机器人只能一直往下走
```
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

[64. 最小路径和](https://leetcode-cn.com/problems/minimum-path-sum/)
>给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
说明：每次只能向下或者向右移动一步。
>![](https://upload-images.jianshu.io/upload_images/18339009-a691fdff2244be63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
输出：7
解释：因为路径 1→3→1→1→1 的总和最小。

```
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
```
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


 
