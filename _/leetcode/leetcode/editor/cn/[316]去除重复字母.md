//给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证
// 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
// 注意：该题与 1081 https://leetcode-cn.com/problems/smallest-subsequence-of-distinct-characters 相同
//
// 示例 1： 
//输入：s = "bcabc"
//输出："abc"
//
// 示例 2： 
//输入：s = "cbacdcbc"
//输出："acdb" 
//
// 提示：
// 1 <= s.length <= 104
// s 由小写英文字母组成 
// 
// Related Topics 栈 贪心 字符串 单调栈 
// 👍 652 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 字典序：是指按照单词出现在字典的顺序比较两个字符串的方法。例如"abc"的字典序在"acdb"的前面。
     * 给定一个字符串 s，如何去掉其中的一个字符 ch，使得得到的字符串字典序最小呢？
     * 答案是：找出最小的满足 s[i]>s[i+1]的下标i，并去除字符[i]。为了叙述方便，下文中称这样的字符为「关键字符」。
     * 不断进行这样的循环在字符串 s 中找到「关键字符」，去除它。
     * 但是这种朴素的解法会创建大量的中间字符串，我们有必要寻找一种更优的方法。
     * 我们从前向后扫描原字符串。每扫描到一个位置，我们就尽可能地处理所有的「关键字符」。
     * 假定在扫描位置 s[i−1] 之前的所有「关键字符」都已经被去除完毕，在扫描字符 s[i] 时，
     * 新出现的「关键字符」只可能出现在s[i] 或者其后面的位置。
     *
     * 于是，我们使用单调栈来维护去除「关键字符」后得到的字符串，如果栈顶字符大于当前字符 s[i]，
     * 说明栈顶字符为「关键字符」，故应当被去除。
     * 去除后，新的栈顶字符就与 s[i] 相邻了，我们继续比较新的栈顶字符与 s[i] 的大小。
     * 重复上述操作，直到栈为空或者栈顶字符不大于 s[i]。
     *
     * 我们还遗漏了一个要求：原字符串 s 中的每个字符都需要出现在新字符串中，且只能出现一次。
     * 为了让新字符串满足该要求，之前讨论的算法需要进行以下两点的更改。
     *
     * - 在考虑字符 s[i] 时，如果它已经存在于栈中，则不能加入字符 s[i]。为此，需要记录每个字符是否出现在栈中。
     * - 在弹出栈顶字符时，如果字符串在后面的位置上再也没有这一字符，则不能弹出栈顶字符。
     * 为此，需要记录每个字符的剩余数量，当这个值为 0 时，就不能弹出栈顶字符了。
     *
     * 时间复杂度：O(N)，其中 N 为字符串长度。代码中虽然有双重循环，但是每个字符至多只会入栈、出栈各一次。
     * 空间复杂度：O(∣Σ∣)，其中 Σ 为字符集合，本题中字符均为小写字母，所以 ∣Σ∣=26。
     * 由于栈中的字符不能重复，因此栈中最多只能有∣Σ∣ 个字符，
     * 另外需要维护两个数组，分别记录每个字符是否出现在栈中以及每个字符的剩余数量。
     */

    public String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        int[] lastIndex = new int[26];
        for (int i = 0; i < chars.length; i++) {
            // 记录每个元素最后一次出现的位置
            lastIndex[chars[i] - 'a'] = i;
        }
        Deque<Character> stack = new LinkedList<>();
        // 某一个字符是否在栈中出现
        boolean[] isStacked = new boolean[26];
        for (int i = 0; i < chars.length; i++) {
            // 该字符如果已经在栈中舍弃当前字符
            if (isStacked[chars[i] - 'a']) {
                continue;
            }
            // 当前字符在栈顶元素之前，且栈顶元素在后面还有
            while (!stack.isEmpty() && stack.peek() > chars[i] && lastIndex[stack.peek() - 'a'] > i) {
                //移除栈顶元素,该字符没有在栈中出现
                Character c = stack.pop();
                isStacked[c - 'a'] = false;
            }
            stack.push(chars[i]);
            isStacked[chars[i] - 'a'] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
