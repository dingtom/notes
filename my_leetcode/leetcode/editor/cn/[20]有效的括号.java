//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 
//
// 有效字符串需满足： 
// 左括号必须用相同类型的右括号闭合。
// 左括号必须以正确的顺序闭合。 
//
// 示例 1：
//输入：s = "()"
//输出：true
// 
// 示例 2：
//输入：s = "()[]{}"
//输出：true
//
// 示例 3： 
//输入：s = "(]"
//输出：false
// 
// 示例 4：
//输入：s = "([)]"
//输出：false
// 
//
// 示例 5： 
//输入：s = "{[]}"
//输出：true 
//
// 提示：
// 1 <= s.length <= 104
// s 仅由括号 '()[]{}' 组成 
// 
// Related Topics 栈 字符串 
// 👍 2896 👎 0


import java.util.HashMap;
import java.util.LinkedList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isValid(String s) {
        /*
         * 栈
         * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
         * 空间复杂度：O(n + |\Sigma|)，其中 \Sigma表示字符集，本题中字符串只包含 6 种括号，
         * |\Sigma| = 6∣。栈中的字符数量为O(n)，而哈希表使用的空间为 O(|\Sigma|)，
         * 相加即可得到总空间复杂度。
         */
        int len = s.length();
        if (len % 2 == 1) return false;
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};

        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            // 左括号进栈，右括号出栈
            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) return false;
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
