//给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。 
// 示例 1：
//输入：s = "(()"
//输出：2
//解释：最长有效括号子串是 "()"
//
// 示例 2： 
//输入：s = ")()())"
//输出：4
//解释：最长有效括号子串是 "()()"
//
// 示例 3： 
//输入：s = ""
//输出：0
//
// 提示： 
// 0 <= s.length <= 3 * 104
// s[i] 为 '(' 或 ')' 
// 
// Related Topics 栈 字符串 动态规划
// 👍 1638 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int longestValidParentheses(String s) {
        /*
        1.栈
        从左到右扫描，始终保持栈底元素为当前已经遍历过的元素中「最后一个没有被匹配的右括号的下标」
        遇到‘（’，当前位置入栈
        遇到‘）’栈顶出栈后
             栈为空，说明当前的‘）’为没有被匹配的‘）’，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的‘）’的下标」
             栈不为空，当前位置减去栈顶存的位置，即为长度，更新最大长度

        空间复杂度：O(n)
        时间复杂度：O(n)
         */
        int maxLen = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(-1);  // 初始化，最后一个没有被匹配的右括号的下标
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
