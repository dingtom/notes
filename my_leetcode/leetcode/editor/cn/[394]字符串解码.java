//给定一个经过编码的字符串，返回它解码后的字符串。 
//
// 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。 
// 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
// 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
//
// 示例 1： 
// 输入：s = "3[a]2[bc]"
//输出："aaabcbc"
//
// 示例 2： 
// 输入：s = "3[a2[c]]"
//输出："accaccacc"
// 
// 示例 3：
// 输入：s = "2[abc]3[cd]ef"
//输出："abcabccdcdcdef"
// 
// 示例 4：
// 输入：s = "abc3[cd]xyz"
//输出："abccdcdcdxyz"
// 
// Related Topics 栈 递归 字符串 
// 👍 1002 👎 0


import java.util.Collection;
import java.util.LinkedList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    int ptr;

    public String decodeString(String s) {
        LinkedList<String> stack = new LinkedList<>();
        ptr = 0;
        while (ptr < s.length()) {
            char cur = s.charAt(ptr);
            if (Character.isDigit(cur)) {
//                如果当前的字符为数位，解析出一个数字（连续的多个数位）并进栈
                String digits = getDigits(s);
                stack.addLast(digits);
            } else if (Character.isLetter(cur) || cur == '[') {
//                如果当前的字符为字母或者左括号，直接进栈
                stack.addLast(String.valueOf(s.charAt(ptr++)));
            } else {
//                如果当前的字符为右括号，开始出栈，一直到左括号出栈，出栈序列反转后拼接成一个字符串，
//                此时取出栈顶的数字（此时栈顶一定是数字，想想为什么？），就是这个字符串应该出现的次数，
//                我们根据这个次数和字符串构造出新的字符串并进栈
                ++ptr;
                LinkedList<String> sub = new LinkedList<>();
                while (!"[".equals(stack.peekLast())) {
                    sub.addLast(stack.removeLast());
                }
                Collections.reverse(sub);
                stack.removeLast();
                int time = Integer.parseInt(stack.removeLast());
                StringBuffer sb = new StringBuffer();
                String segment = getString(sub);
                while (time-- > 0) {
                    sb.append(segment);
                }
                stack.addLast(sb.toString());
            }
        }
//        重复如上操作，最终将栈中的元素按照从栈底到栈顶的顺序拼接起来，就得到了答案。
//        注意：这里可以用不定长数组来模拟栈操作，方便从栈底向栈顶遍历。
        return getString(stack);

    }
    
    public String getDigits(String s) {
        StringBuffer ret = new StringBuffer();
        while (Character.isDigit(s.charAt(ptr))) {
            ret.append(s.charAt(ptr++));
        }
        return ret.toString();
    }
    public String getString(LinkedList<String> ls) {
        StringBuffer ret = new StringBuffer();
        for (String s : ls) {
            ret.append(s);
        }
        return ret.toString();
    }

}
//leetcode submit region end(Prohibit modification and deletion)
