//给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
// 找出 candidates 中可以使数字和为目标数 target 的
// 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。 
// candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
// 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
//
// 示例 1： 
//输入：candidates = [2,3,6,7], target = 7
//输出：[[2,2,3],[7]]
//解释：
//2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
//7 也是一个候选， 7 = 7 。
//仅有这两种组合。 
//
// 示例 2： 
//输入: candidates = [2,3,5], target = 8
//输出: [[2,2,2,2],[2,3,3],[3,5]] 
//
// 示例 3： 
//输入: candidates = [2], target = 1
//输出: []
//
// 提示： 
// 1 <= candidates.length <= 30
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都 互不相同 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯 👍 1969 👎 0



//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 结果
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    // 路径
    List<Integer> path = new ArrayList<Integer>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtrack(candidates, target, 0);  // index候选数组的下标
        return result;
    }
    public void backtrack(int[] candidates, int target, int index) {
        //             递归边界
        //当前方案不合法，返回
        if (target < 0) {
            return;
        }
        // 方案合法，记录该方案
        if (target == 0) {
            result.add(new ArrayList<Integer>(path));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[index] <= target) {
                path.add(candidates[index]);
                backtrack(candidates, target - candidates[index], index);// 因为可以重复使用，所以还是i
                path.remove(path.size()-1); //回溯，恢复现场
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
