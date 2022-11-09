/*
 * @lc app=leetcode.cn id=85 lang=java
 *
 * [85] 最大矩形
 */

/* 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
示例 1：
输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
输出：6 
示例 2：
输入：matrix = []
输出：0
示例 3：
输入：matrix = [["0"]]
输出：0
示例 4：
输入：matrix = [["1"]]
输出：1 */

import java.util.Deque;
import java.util.ArrayDeque;

// @lc code=start
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) return 0;
        int n = matrix[0].length;
        int[][] left = new int[m][n];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                //
                if (matrix[i][j] == '1') left[i][j] = ( i== 0 ? 0 : left[i-1][j]) + 1;
            }
        }

        int area = 0;
        for (int i = 0;  i < m; i++) { // 对于每一列，使用基于柱状图的方法
            int len = n;
            // 添加哨兵
            int[] newHeights = new int[len +2];
            newHeights[0] = 0;
            System.arraycopy(left[i], 0, newHeights, 1, len);
            newHeights[len +1] = 0;
            len += 2;
            // 先放入哨兵，在循环里就不用做非空判断
            Deque<Integer> stack = new ArrayDeque<>(len);
            stack.addLast(0);
            for (int j = 1; j < n; j++) {
                // 到了当前柱形的高度比它上一个柱形的高度严格小的时候，一定可以确定它之前的某些柱形的最大宽度，并且确定的柱形宽度的顺序是从右边向左边。
                while(newHeights[j] < newHeights[stack.peekLast()]) {
                    System.out.println(i+"-----------"+j);
                    int height = newHeights[stack.pollLast()];
                    int width = j - stack.peekLast() - 1;
                    System.out.println(height + "-" + width);
                    area = Math.max(area, height * width);
                }
                stack.addLast(j);
            }
        }
        return area;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] matrix1 = {
                { '1', '0', '1', '0', '0' },
                { '1', '0', '1', '1', '1' },
                { '1', '1', '1', '1', '1' },
                { '1', '0', '0', '1', '0' } };
        char[][] matrix2 = {};
        char[][] matrix3 = { { '0' } };
        char[][] matrix4 = { { '1' } };

        System.out.println(solution.maximalRectangle(matrix1));
//        System.out.println(solution.maximalRectangle(matrix2));
//        System.out.println(solution.maximalRectangle(matrix3));
//        System.out.println(solution.maximalRectangle(matrix4));
    }
}
// @lc code=end
