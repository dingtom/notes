//给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。 
// 示例 1:
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//
// 示例 2: 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//
// 示例 3: 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
// 提示：
// 0 <= s.length <= 5 * 104
// s 由英文字母、数字、符号和空格组成 
// 
// Related Topics 哈希表 字符串 滑动窗口 
// 👍 6996 👎 0


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lengthOfLongestSubstring(String s) {
        /**
         * 使用双指针做滑动窗口，使用哈希表记录出现过的字符
         * 时间复杂度：O(N)，其中 N 是字符串的长度。左指针和右指针分别会遍历整个字符串一次。
         * 空间复杂度：O(min(m,n))，m字符集大小，n长度
         */
        int len = s.length();
        int left = 0, right = 0, maxLen = 0;
        Set<Character> recorder = new HashSet<Character>();
        while (left < len && right < len) {
            if (!recorder.contains(s.charAt(right))) {
                recorder.add(s.charAt(right++));
                maxLen = Math.max(maxLen, right - left);
            } else {  // right已经有了就移除left，然后重试
                recorder.remove(s.charAt(left++));
            }
        }

    public static void main(String[] args) {
        String str = new String("abcabc");
        Solution s = new Solution();
        System.out.println(s.lengthOfLongestSubstring(str));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
