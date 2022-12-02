//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
//
// 求在该柱状图中，能够勾勒出来的矩形的最大面积。
//
// 示例 1:
//输入：heights = [2,1,5,6,2,3]
//输出：10
//解释：最大的矩形为图中红色区域，面积为 10
//
// 示例 2：
//输入： heights = [2,4]
//输出： 4
//
// 提示：
// 1 <= heights.length <=105
// 0 <= heights[i] <= 104
//
// Related Topics 栈 数组 单调栈
// 👍 1703 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int largestRectangleArea(int[] heights) {
        /*


        
        为什么要在原数组首尾都加上一个 0 值？
        在数组开头加一个 0 是因为 0 比数组所有元素都小，可以保证栈内元素永远不会全部弹出（至少会有一个 0 值），否则在算宽度时需要判断栈是否为空；
        在数组结尾加一个 0 是因为 0 比数组所有元素都小，可以保证最后一次循环栈内元素全部出栈，元素都参与了面积计算，否则还需要对遍历结束后栈内剩余的元素再进行一次面积计算。
         */
        // 特殊情况判断
        int len = heights.length;
        if (len == 0) return 0;
        if (len == 1) return heights[0];
        // 添加哨兵
        int[] newHeights = new int[len +2];
        System.arraycopy(heights, 0, newHeights, 1, len);
        len += 2;
        heights = newHeights;
        // 先放入哨兵，在循环里就不用做非空判断
        Deque<Integer> stack = new LinkedList<>(len);
        stack.push(0);

        int area = 0;
        for (int i = 1;  i < len; i++) {
            // 到了当前柱形的高度比它上一个柱形的高度严格小的时候，
            // 一定可以确定它之前的某些柱形的最大宽度，并且确定的
            // 柱形宽度的顺序是从右边向左边。
            while(heights[i] < heights[stack.peek()]) {
                area = Math.max(area, heights[stack.pop()] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        return area;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
