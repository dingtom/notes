//给你一个大小为 m x n 的二进制矩阵 grid 。 
// 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。
// 你可以假设 grid 的四个边缘都被 0（代表水）包围着。岛屿的面积是岛上值为 1 的单元格的数目。
// 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
//
// 示例 1：
//输入：grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,
//0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,
//0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
//输出：6
//解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
// 
// 示例 2：
//输入：grid = [[0,0,0,0,0,0,0,0]]
//输出：0
// 
// 提示：
// m == grid.length
// n == grid[i].length 
// 1 <= m, n <= 50 
// grid[i][j] 为 0 或 1 
// 
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 
// 👍 676 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     1.dfs
     时间复杂度：O(R×C)。其中 R 是给定网格中的行数，C 是列数。我们访问每个网格最多一次。
     空间复杂度：O(R×C)，递归的深度最大可能是整个网格的大小，因此最大可能使用 O(R×C) 的栈空间。
     */
//    public int maxAreaOfIsland(int[][] grid) {
//        int max = 0;
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[0].length; j++) {
//                max = Math.max(max, dfs(grid, i, j));
//            }
//        }
//        return max;
//    }
//    public int dfs(int[][] grid, int row, int col) {
//        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || grid[row][col] != 1) {
//            return 0;
//        }
//        grid[row][col] = 0;
//        int area = 1;
//        int[] i = {1, -1, 0, 0};
//        int[] j = {0, 0, 1, -1};
//        for (int index = 0; index < 4; index++) {
//            area += dfs(grid, row + i[index], col + j[index]);
//        }
//        return area;
//    }

    /**
     * 2.bfs
     */
    public int maxAreaOfIsland(int[][] grid) {
        int rowLen = grid.length, colLen = grid[0].length, max = 0;
        Queue<Integer> queueRow = new LinkedList<Integer>();
        Queue<Integer> queueCol = new LinkedList<Integer>();

        for (int i = 0; i <= rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                int area = 0;
                queueRow.offer(i);
                queueCol.offer(j);
                while (!queueRow.isEmpty()) {
                    int row = queueRow.poll(), col = queueCol.poll();
                    if (row < 0 || col < 0 || row >= rowLen || col >= colLen || grid[row][col] != 1) {
                        continue;
                    }
                    area++;
                    grid[row][col] = 0;
                    int[] k = {1, -1, 0, 0};
                    int[] l = {0, 0, 1, -1};
                    for (int index = 0; index < 4; index++) {
                        queueRow.offer(row + k[index]);
                        queueCol.offer(col + l[index]);
                    }
                }
                max = Math.max(max, area);
            }
        }
        return max;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] x = {{0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        solution.maxAreaOfIsland(x);
    }
}


//leetcode submit region end(Prohibit modification and deletion)
