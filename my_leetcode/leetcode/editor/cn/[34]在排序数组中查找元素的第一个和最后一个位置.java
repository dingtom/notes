//给定一个按照升序排列的整数数组 nums，和一个目标值 target。
// 找出给定目标值在数组中的开始位置和结束位置。
// 如果数组中不存在目标值 target，返回 [-1, -1]。
// 进阶：
// 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
//
// 示例 1： 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//输入：nums = [], target = 0
//输出：[-1,-1] 
//
// 提示： 
// 0 <= nums.length <= 10⁵
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
// Related Topics 数组 二分查找 👍 1679 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] searchRange(int[] nums, int target) {
        /*
        1.二分查找，先找第一个>=target的为头,再找第一个>target的-1为尾
         */
        int head = bSearch(nums, target);
        int tail = bSearch(nums, target + 1);
        // 特殊情况head在最后一个
        if(head == nums.length || nums[head] != target) {
            return new int[]{-1, -1};
        }
        return  new int[]{head, tail-1};
    }
    public int bSearch(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (nums[mid] >= target) { // target 在左边或中间
                r = mid;
            } else{  // target 在右边
                l = mid + 1;
            }
        }
        return l;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

