//给定一个字符串，请将字符串里的字符按照出现的频率降序排列。 
//
// 示例 1: 
//输入:
//"tree"
//
//输出:
//"eert"
//解释:
//'e'出现两次，'r'和't'都只出现一次。
//因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
//
// 示例 2: 
//输入:
//"cccaaa"
//输出:
//"cccaaa"
//解释:
//'c'和'a'都出现三次。此外，"aaaccc"也是有效的答案。
//注意"cacaca"是不正确的，因为相同的字母必须放在一起。
//
// 示例 3: 
//输入:
//"Aabb"
//输出:
//"bbAa"
//解释:
//此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
//注意'A'和'a'被认为是两种不同的字符。
// 
// Related Topics 哈希表 字符串 桶排序 计数 排序 堆（优先队列） 
// 👍 368 👎 0


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String frequencySort(String s) {
        /*
        首先遍历字符串，用哈希表记录每个字符出现的次数
        然后把哈希表中的键值对加到堆中，按照value形成最大堆
        循环弹出堆中的value最大的字符，将字符根据其出现次数加到res中
        时间复杂度：O(n+klogk)
        空间复杂度：O(n)
         */
        Map<Character, Integer> occurences = new HashMap<>();
        for (char c : s.toCharArray()) {
            occurences.put(c, occurences.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(((o1, o2) -> o2.getValue() - o1.getValue()));
        maxHeap.addAll(occurences.entrySet());

        StringBuilder  sb = new StringBuilder ();
        while(!maxHeap.isEmpty()){
            Map.Entry<Character, Integer> entry = maxHeap.poll();
            char c = entry.getKey();
            int count = entry.getValue();
            sb.append(String.valueOf(c).repeat(Math.max(0, count)));
        }
        return sb.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
