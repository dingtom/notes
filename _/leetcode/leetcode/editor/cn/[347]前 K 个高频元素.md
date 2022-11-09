//给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。 
//
// 示例 1: 
//输入: nums = [1,1,1,2,2,3], k = 2
//输出: [1,2]
//
// 示例 2: 
//输入: nums = [1], k = 1
//输出: [1] 
//
// 提示： 
// 1 <= nums.length <= 105
// k 的取值范围是 [1, 数组中不相同的元素的个数] 
// 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的 
// 
// 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
// Related Topics 数组 哈希表 分治 桶排序 计数 快速选择 排序 堆（优先队列） 
// 👍 990 👎 0


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        /*
        首先遍历整个数组，并使用哈希表记录每个数字出现的次数，并形成一个「出现次数数组」。找出原数组的前 k 个高频元素，就相当于找出「出现次数数组」的前 k 大的值。

        时间复杂度：O(Nlogk)，其中 N为数组的长度。我们首先遍历原数组，并使用哈希表记录出现次数，每个元素需要 O(1) 的时间，共需 O(N) 的时间。
        随后，我们遍历「出现次数数组」，由于堆的大小至多为 k，因此每次堆操作需要 O(logk) 的时间，共需 O(Nlogk) 的时间。二者之和为 O(Nlogk)。
        空间复杂度：O(N)。哈希表的大小为 O(N)，而堆的大小为 O(k)，共计为 O(N)。

         */
        Map<Integer, Integer> occurences = new HashMap<Integer, Integer>();
        for (int num : nums) {
            occurences.put(num, occurences.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<int[]> minHeap = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return m[1] - n[1];
            }
        });

        for (Map.Entry<Integer, Integer> entry : occurences.entrySet()) {
            int num = entry.getKey(), count = entry.getValue();
            if (minHeap.size() == k && count <= minHeap.peek()[1]) {
                continue;
            }
            minHeap.offer(new int[]{num, count});
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        int[] result = new int[k];
        for (int i = 0; i < k; ++i) {
            result[i] = minHeap.poll()[0];
        }
        return result;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
