//给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
//
// 返回这三个数的和。
//
// 假定每组输入只存在恰好一个解。
//
//
//
// 示例 1：
//
//
//输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
//
//
// 示例 2：
//
//
//输入：nums = [0,0,0], target = 1
//输出：0
//
//
//
//
// 提示：
//
//
// 3 <= nums.length <= 1000
// -1000 <= nums[i] <= 1000
// -10⁴ <= target <= 10⁴
//
// Related Topics 数组 双指针 排序 👍 1140 👎 0

//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
//
// 示例 1：
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
//
// 示例 2：
//输入：digits = ""
//输出：[]
//
// 示例 3：
//输入：digits = "2"
//输出：["a","b","c"]
//
// 提示：
// 0 <= digits.length <= 4
// digits[i] 是范围 ['2', '9'] 的一个数字。
//
// Related Topics 哈希表 字符串 回溯
// 👍 1701 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    private String[] map = {"ab","de","ghi","jkl","mno","pqrs","tuv","wxyz"};
    private StringBuilder sb = new StringBuilder();
    private List<String> result = new ArrayList<String>();
    /*
    回溯
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null ||digits.length() == 0) {
            return result;
        }
        backtrack(digits, 0);
        return result;
    }
    private void backtrack(String digits, int index) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }
        String cs = map[digits.charAt(index) - '2'];
        for (char c: cs.toCharArray()) {
            sb.append(c);
            backtrack(digits, index + 1);
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString()
            );
        }
    }

    public static void main(String[] args) {
        String ns = "23";
        int t = 1;
        Solution s = new Solution();
        s.letterCombinations(ns);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
