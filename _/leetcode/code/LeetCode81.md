/*
 * @lc app=leetcode.cn id=81 lang=java
 * [81] 搜索旋转排序数组 II
 */

/* 
已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，使数组变为
[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。

给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。
如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。

示例 1：
输入：nums = [2,5,6,0,0,1,2], target = 0
输出：true
示例 2：
输入：nums = [2,5,6,0,0,1,2], target = 3
输出：false
 */

// @lc code=start
public class LeetCode81 {
    public boolean search(int[] nums, int target) {
        int len = nums.length;
        // 数组长度为0/1直接判断，否则二分查找
        if (len == 0) return false;
        if (len == 1) return nums[0] == target;
        int l = 0, r = len - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (target == nums[mid]) return true;
            // 可能会有 a[l]=a[mid]=a[r]
            if (nums[l] == nums[mid] && nums[mid] == nums[r]) {
                ++l;
                --r;
            } else if (nums[l] <= nums[mid]) {    //!!!!!!!!!!!!等号
                // 如果target在左边，移动右指针向左
                if (nums[l] <= target && target < nums[mid]) {  // ！！！！！！！！！！！等号
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[len - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LeetCode81 solution = new LeetCode81();
        int[] nums1 = {2, 5, 6, 0, 0, 1, 2}, nums2 = {2, 5, 6, 0, 0, 1, 2};
        int target1 = 3, target2 = 0;
        System.out.println(solution.search(nums1, target1));
        System.out.println(solution.search(nums2, target2));

    }
}