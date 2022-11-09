//给你一个整数数组 nums 和一个整数 target 。 
// 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
//
// 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，
// 然后串联起来得到表达式 "+2-1" 。
// 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
//
// 示例 1：
//输入：nums = [1,1,1,1,1], target = 3
//输出：5
//解释：一共有 5 种方法让最终目标和为 3 。
//-1 + 1 + 1 + 1 + 1 = 3
//+1 - 1 + 1 + 1 + 1 = 3
//+1 + 1 - 1 + 1 + 1 = 3
//+1 + 1 + 1 - 1 + 1 = 3
//+1 + 1 + 1 + 1 - 1 = 3
// 
// 示例 2：
//输入：nums = [1], target = 1
//输出：1
//
// 提示：
// 1 <= nums.length <= 20
// 0 <= nums[i] <= 1000 
// 0 <= sum(nums[i]) <= 1000 
// -1000 <= target <= 1000 
// 
// Related Topics 数组 动态规划 回溯 
// 👍 1026 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    int count = 0;
    public int findTargetSumWays(int[] nums, int target) {
        /*
        1.深度优先遍历，回溯
        时间复杂度：O(2^n)，其中 n 是数组 nums 的长度。回溯需要遍历所有不同的表达式，共有 2^n种不同的表达式，
        每种表达式计算结果需要 O(1)O(1) 的时间，因此总时间复杂度是 O(2^n)
        空间复杂度：O(n)，其中 n 是数组 nums 的长度。空间复杂度主要取决于递归调用的栈空间，栈的深度不超过 n。
         */
//        backtrack(nums, target, 0, 0);
//        return count;
//

        /*
        2.动态规划
        记数组的元素和为sum，添加- 号的元素之和为neg，则其余添加+ 的元素之和为sum−neg，得到的表达式的结果为
        (sum−neg)−neg=sum−2⋅neg=target             neg=(sum-target)/2

        定义二维数组dp，其中p[i][j] 表示在数组nums的前i个数中选取元素，使得这些元素之和等于j的方案数。
        假设数组nums的长度为n，则最终答案为dp[n][neg]。

        时间复杂度：O(n×(sum−target))，
        空间复杂度：O(sum−target)
        */
        int sum = 0;
        for (int i: nums) {
            sum += i;
        }
        int diff = sum - target;
        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }
        int len = nums.length, neg = diff / 2;
        int[][] dp = new int[len + 1][neg + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= len; i++) {
            int num = nums[i - 1];
            for (int j = 0; j <= neg; j++) {
                dp[i][j] = dp[i - 1][j];
//                如果j<num，则不能选num，
                if (j >= num) {
                    dp[i][j] += dp[i - 1][j - num];
                }
            }
        }
        return dp[len][neg];

//        nt neg = diff / 2;
//        int[] dp = new int[neg + 1];
//        dp[0] = 1;
//        for (int num : nums) {
//            for (int j = neg; j >= num; j--) {
//                dp[j] += dp[j - num];
//            }
//        }
//        return dp[neg];

    }
    public void backtrack(int[] nums, int target, int index, int sum) {
//        从零开始每个元素前面都加一个运算符
        if (index == nums.length) {
            if (sum == target) {
                count++;
            }
        } else {
            backtrack(nums, target, index + 1, sum + nums[index]);
            backtrack(nums, target, index + 1, sum - nums[index]);
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)
