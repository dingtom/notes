//给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。 
//
// 整数除法仅保留整数部分。 
//
// 示例 1： 
//输入：s = "3+2*2"
//输出：7
// 
// 示例 2：
//输入：s = " 3/2 "
//输出：1
// 
// 示例 3：
//输入：s = " 3+5 / 2 "
//输出：5
// 
// 提示：
// 1 <= s.length <= 3 * 105
// s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开 
// s 表示一个 有效表达式 
// 表达式中的所有整数都是非负整数，且在范围 [0, 231 - 1] 内 
// 题目数据保证答案是一个 32-bit 整数 
//
// Related Topics 栈 数学 字符串 
// 👍 519 👎 0


import java.util.Deque;
import java.util.LinkedList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int calculate(String s) {

        /*
         * 栈
         * 时间复杂度：O(n)O(n)，其中 nn 为字符串 ss 的长度。需要遍历字符串 ss 一次，计算表达式的值。
         * 空间复杂度：O(n)O(n)，其中 nn 为字符串 ss 的长度。空间复杂度主要取决于栈的空间，栈的元素个数不超过 nn。
         */
        Deque<Integer> stack = new LinkedList<Integer>();
        char preSign = '+';
        int num = 0;
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            // 记录数字
            if (Character.isDigit(s.charAt(i))) {
                // 字符数字相加等于ASCII码加数字  1,a,A 48,65,97
                num = num * 10 + s.charAt(i) - '0';
            }
            // 数字结束
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == len - 1) {
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    default:
                        stack.push(stack.pop() / num);
                }
                preSign = s.charAt(i);
                num = 0;
            }
        }
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.calculate(" 3+5 / 2 "));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
