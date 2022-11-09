/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

/* 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
求在该柱状图中，能够勾勒出来的矩形的最大面积。
示例 1：
输入：heights = [2,1,5,6,2,3]
输出：10
示例 2：
输入： heights = [2,4]
输出： 4*/

import java.util.Stack;
import java.util.ArrayDeque;
import java.util.Deque;
// @lc code=start
public class LeetCode84 {
    public int largestRectangleArea1(int[] heights) {
        int len = heights.length;
        int maxarea = 0;
        Stack<Integer> stk = new Stack<Integer>();

        for(int i = 0; i < len; ++i) {
            // 只要栈不为空，并且当前遍历的数据小于栈顶元素，则说明找到了右边界
            while(!stk.empty() && heights[i] < heights[stk.peek()]) {
                // 右边界 heights[i]
                int h = heights[stk.peek()];
                stk.pop();
                // 出栈后，如果栈为空，说明出栈的柱子目前遍历的最短的柱子，否则计算宽度差
                int w = stk.empty() ? i : i - stk.peek() - 1;
                maxarea = Math.max(h * w, maxarea);
            }
            // 栈为空或者当前遍历的数据大于等于栈顶数据，则入栈，不会执行上面的while循环
            // 保证了栈中的数据总是递增的 比如  0 1 2 2 3 4 4 5 6 ...
            stk.push(i);
        }

        // 处理剩余栈中的数据(递增的数据，右边界是柱状图中最右边的柱子)
        while(!stk.empty()) {
            int h = heights[stk.peek()];
            stk.pop();
            // 出栈后，如果栈为空，说明出栈的柱子目前遍历的最短的柱子，否则计算宽度差
            int w = stk.empty() ? len : len - stk.peek() - 1;
            maxarea = Math.max(h * w, maxarea);
        }
        return maxarea;
    }

    public int largestRectangleArea2(int[] heights) {
        // 特殊情况判断
        int len = heights.length;
        if (len == 0) return 0;
        if (len == 1) return heights[0];
        // 添加哨兵
        int[] newHeights = new int[len +2];
        newHeights[0] = 0;
        System.arraycopy(heights, 0, newHeights, 1, len);
        newHeights[len +1] = 0;
        len += 2;
        heights = newHeights;
        // 先放入哨兵，在循环里就不用做非空判断
        Deque<Integer> stack = new ArrayDeque<>(len);
        stack.addLast(0);
        
        int area = 0;
        for (int i = 1;  i < len; i++) {
            // 到了当前柱形的高度比它上一个柱形的高度严格小的时候，一定可以确定它之前的某些柱形的最大宽度，并且确定的柱形宽度的顺序是从右边向左边。
            while(heights[i] < heights[stack.peekLast()]) {
                area = Math.max(area, heights[stack.pollLast()] * (i - stack.peekLast() - 1));
            }
            stack.addLast(i);
        }
        return area;
    }

    public static void main(String[] args) {
        LeetCode84 solution = new LeetCode84();
        int[] heights = {2,1,5,6,2,3};
        int[] heights1 = {2,4};
        System.out.println(solution.largestRectangleArea1(heights));
        System.out.println(solution.largestRectangleArea2(heights));
        System.out.println(solution.largestRectangleArea2(heights1));
    }
}
// @lc code=end

