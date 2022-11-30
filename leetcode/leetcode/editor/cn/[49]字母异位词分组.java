//给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。 
//
// 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。 
//
// 
//
// 示例 1: 
//
// 
//输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出: [["bat"],["nat","tan"],["ate","eat","tea"]] 
//
// 示例 2: 
//
// 
//输入: strs = [""]
//输出: [[""]]
// 
//
// 示例 3: 
//
// 
//输入: strs = ["a"]
//输出: [["a"]] 
//
// 
//
// 提示： 
//
// 
// 1 <= strs.length <= 10⁴ 
// 0 <= strs[i].length <= 100 
// strs[i] 仅包含小写字母 
// 
// Related Topics 数组 哈希表 字符串 排序 👍 1159 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /*
    hasmap记录相同字符的单词，使用26位数组记录每个单词每个字符出现的次数，作为哈希表key
    时间复杂度：O(n(k+∣Σ∣))，其中 n 是 strs 中的字符串的数量，
    k 是 strs 中的字符串的的最大长度，Σ 是字符集。
    需要遍历 n 个字符串，对于每个字符串，需要 O(k) 的时间计算每个字母出现的次数
    ，O(∣Σ∣) 的时间生成哈希表的键，以及 O(1) 的时间更新哈希表，因此总时间复杂度是 O(n(k+∣Σ∣))。
    空间复杂度：O(n(k+∣Σ∣))，
    需要用哈希表存储全部字符串，而记录每个字符串中每个字母出现次数的数组需要的空间为
     O(∣Σ∣)，在渐进意义下小于 O(n(k+∣Σ∣))，可以忽略不计。

     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] count = new int[26];
            for (int i = 0; i < str.length(); i++) {
                count[str.charAt(i) - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append((char) count[i]);
            }
            String key = sb.toString();
            if (map.containsKey(key)) {
                map.get(key).add(str);
            }else {
                List<String> temp = new ArrayList<String>();
                temp.add(str);
                map.put(key, temp);
            }
        }
        return new ArrayList<List<String>>(map.values());


    }
}
//leetcode submit region end(Prohibit modification and deletion)
