- [ 二分查找](#head1)
[376\. 摆动序列](https://leetcode-cn.com/problems/wiggle-subsequence/)
>- 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
>- 例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
>- 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中**删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。**

![](https://upload-images.jianshu.io/upload_images/18339009-f574f7bc028536ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
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

# <span id="head1"> 二分查找</span>
```
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
