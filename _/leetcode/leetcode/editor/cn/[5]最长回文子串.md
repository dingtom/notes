//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 
// 👍 4597 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
         if (s == null || s.length() < 2) {
            return s;
        }
        /*
         * 时间复杂度：O(n^2)，其中 n 是字符串的长度。动态规划的状态总数为 O(n^2)，对于每个状态，我们需要转移的时间为 O(1)。
         * 空间复杂度：O(n^2)，即存储动态规划状态需要的空间。
         */



//        /** 扩展中心，从一个字符或者两个字符中间向两边扩散，共有n+n-1个扩展中心
//         * 时间复杂度：O(n^2)
//         * 空间复杂度：O(1)
//         */

//        // 子串起始位置
//        int start = 0, end = 0;
//        for (int i = 0; i < s.length(); i++) {
//            // 从当前位置开始扩展得到最长子串长度
//            int odd = expandAroundCenter(s, i, i);
//            // 两个字符中间扩展
//            int even = expandAroundCenter(s,i, i + 1);
//            int len = Math.max(odd, even);
//            // 更新子串起始位置
//            if (len > end - start) {
//                start = i - (len - 1) / 2;
//                end = i + len / 2;
//            }
//        }
//        return s.substring(start, end + 1);
//    }
//    public int expandAroundCenter(String s, int left, int right) {
//        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
//            left--;
//            right++;
//        }
//        // 跳出while前left,right移动了
//        return right - left - 2 + 1;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
