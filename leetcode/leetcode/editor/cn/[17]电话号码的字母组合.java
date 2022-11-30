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


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 数字到号码的映射
    private String[] map = {"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    // 路径
    private StringBuilder path = new StringBuilder();
    // 结果集
    private List<String> result = new ArrayList<String>();
    /*
    回溯
    时间复杂度：O(3^m \times 4^n)，其中 m 是输入中对应 3 个字母的数字个数，
    n是输入中对应 4 个字母的数字个数，，需要遍历每一种字母组合。

    空间复杂度：O(m+n)，其中 m 是输入中对应 3 个字母的数字个数，n 是输入中对应 4 个字母的数字个数
    空间复杂度主要取决于哈希表以及回溯过程中的递归调用层数，
    哈希表的大小与输入无关，可以看成常数，递归调用层数最大为 m+n。
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null ||digits.length() == 0) {
            return result;
        }
        backtrack(digits, 0); // index计数器
        return result;
    }
    private void backtrack(String digits, int index) {
        //             递归边界
        if (path.length() == digits.length()) {
            result.add(path.toString());
            return;
        }
        String characters = map[digits.charAt(index) - '2'];
        for (char c: characters.toCharArray()) {
            path.append(c);
            backtrack(digits,index + 1);
            path.deleteCharAt(path.length() - 1);//回溯，恢复现场
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
