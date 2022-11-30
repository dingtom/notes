//给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在
// 最初空栈上进行的推入push 和弹出 pop 操作序列的结果时
//，返回 true；否则，返回 false 。 
//
// 示例 1： 
//输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
//输出：true
//解释：我们可以按以下顺序执行：
//push(1), push(2), push(3), push(4), pop() -> 4,
//push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
// 
// 示例 2：
//输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
//输出：false
//解释：1 不能在 2 之前弹出。
//
// 提示：
// 1 <= pushed.length <= 1000
// 0 <= pushed[i] <= 1000 
// pushed 的所有元素 互不相同 
// popped.length == pushed.length 
// popped 是 pushed 的一个排列 
// 
// Related Topics 栈 数组 模拟 
// 👍 220 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        /*
        将 pushed 队列中的每个数都 push 到栈中，同时检查这个数是不是 popped 序列中下一个要 pop 的值，如果是就把它 pop 出来。
        最后，检查不是所有的该 pop 出来的值都是 pop 出来了。
        时间复杂度：O(N)O(N)，其中 NN 是 pushed 序列和 popped 序列的长度。
        空间复杂度：O(N)O(N)。
         */
        int len = pushed.length;
        Stack<Integer> stack = new Stack();
        int index = 0;
        for (int x: pushed) {
            stack.push(x);
            while (!stack.isEmpty() && stack.peek() == popped[index]) {
                stack.pop();
                index++;
            }
        }
        return index == len;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
