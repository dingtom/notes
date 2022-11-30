//请根据每日 气温 列表 temperatures ，请计算在每一天需要等几天才会有更高的温度。如果气温在这之后都不会升高，
// 请在该位置用 0 来代替。
// 示例 1:
//输入: temperatures = [73,74,75,71,69,72,76,73]
//输出: [1,1,4,2,1,1,0,0]
//
// 示例 2: 
//输入: temperatures = [30,40,50,60]
//输出: [1,1,1,0]
//
// 示例 3: 
//输入: temperatures = [30,60,90]
//输出: [1,1,0] 
//
// 提示：
// 1 <= temperatures.length <= 105
// 30 <= temperatures[i] <= 100 
// 
// Related Topics 栈 数组 单调栈 
// 👍 1006 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        /**
         * 左->右 下一个更大         递增栈即将入栈的元素
         * 正向遍历温度列表。对于温度列表中的每个元素 temperatures[i]，如果栈为空，则直接将 i 进栈，
         * 如果栈不为空，则比较栈顶元素 prevIndex 对应的温度 temperatures[prevIndex] 和当前温度
         * temperatures[i]，如果 temperatures[i] > temperatures[prevIndex]，则将 prevIndex 移除，
         * 并将 prevIndex 对应的等待天数赋为 i - prevIndex，重复上述操作直到栈为空或者栈顶元素对应的
         * 温度小于等于当前温度，然后将 i 进栈。
         * 
         * 时间复杂度：O(n)，其中 n 是温度列表的长度。正向遍历温度列表一遍，对于温度列表中的每个下标，最多有一次进栈和出栈的操作。
         * 空间复杂度：O(n)，其中 n 是温度列表的长度。需要维护一个单调栈存储温度列表中的下标。
         */
        int len = temperatures.length;
        int[] ans = new int[len];
        Deque<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < len; i++) {
            int temp = temperatures[i];
            while (!stack.isEmpty() && temp > temperatures[stack.peek()] ) {
                int prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
