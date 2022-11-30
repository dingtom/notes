//给你一个长度为 n 的整数数组 nums 和 一个目标值 target。
// 请你从 nums 中选出三个整数，使它们的和与 target 最接近。
// 返回这三个数的和。
// 假定每组输入只存在恰好一个解。
//
// 示例 1： 
//输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
// 示例 2：
//输入：nums = [0,0,0], target = 1
//输出：0
// 提示：
// 3 <= nums.length <= 1000
// -1000 <= nums[i] <= 1000 
// -10⁴ <= target <= 10⁴ 
// 
// Related Topics 数组 双指针 排序 👍 1140 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        /* 先排序，再遍历数组元素a,a是b数组最后一个是c对向移动bc,找到最合适的
         * 时间复杂度：O(N^2)
         * 空间复杂度：O(logN)。排序需要使用 O(logN) 的空间
         */
        Arrays.sort(nums);
        int best = 0, bestSub = Integer.MAX_VALUE, len = nums.length;
        for (int a = 0; a < len - 2; a++) {
            int b = a + 1, c = len - 1;
            while (b < c) {
                int sum = nums[a] + nums[b] + nums[c];
                if (sum == target) {
                    return sum;
                }

                if (Math.abs(target - sum) < bestSub ) {
                    bestSub = Math.abs(target - sum);
                    best = sum;
                }

                if (sum < target) {
                    b++;
                } else {
                    c--;
                }
            }
        }
        return best;
    }
    public static void main(String[] args) {
        int[] ns = {-1,2,1,-4};
        int t = 1;
        Solution s = new Solution();
        System.out.println(s.threeSumClosest(ns, t));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
