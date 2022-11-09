- [ **排序**](#head1)
## <span id="head1"> **排序**</span>

假设（不是一般性），某一对整数 a 和 b ，我们的比较结果是 a 应该在 b 前面，这意味着 $a⌢b>b⌢a $，其中$ ⌢$ 表示连接。

如果排序结果是错的，说明存在一个 c ， b 在 c 前面且 c 在 a的前面。这产生了矛盾，因为 $a⌢b>b⌢a $和$b⌢c>c⌢b $意味着$ a⌢c>c⌢a$ 。
换言之，我们的自定义比较方法保证了传递性，所以这样子排序是对的。



*   [179\. 最大数](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/largest-number/)
>给定一组非负整数 nums，重新排列它们每位数字的顺序使之组成一个最大的整数。
注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。

>![](https://upload-images.jianshu.io/upload_images/18339009-72b3b70a8a94fa31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-58a77e87f7373ef0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
class sort_rule(str):
    def __lt__(self, y):
        return self+y > self+x

class Solution:
    def largestNumber(self, nums: List[int]) -> str:
        sorted_nums = ''.join(sorted(map(str, nums), key=sort_rule))
        return '0' if sorted_nums[0] == '0' else sorted_nums
```

*   [324\. 摆动排序 II](https://link.zhihu.com/?target=https%3A//leetcode-cn.com/problems/wiggle-sort-ii/)


```
class Solution:
    def wiggleSort(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        s = len(nums)-len(nums)//2
        nums[0::2], nums[1::2] = sorted(nums)[:s][::-1], sorted(nums)[s:][::-1]
# [::-1]防止连续的数连续的放进去


```
 [376\. 摆动序列](https://leetcode-cn.com/problems/wiggle-subsequence/)

>如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。

```
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
