//给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。 
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
// 此外，你可以假设该网格的四条边均被水包围。
//
// 示例 1： 
//输入：grid = [
//  ["1","1","1","1","0"],
//  ["1","1","0","1","0"],
//  ["1","1","0","0","0"],
//  ["0","0","0","0","0"]
//]
//输出：1
//
// 示例 2： 
//输入：grid = [
//  ["1","1","0","0","0"],
//  ["1","1","0","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//]
//输出：3
// 提示：
// m == grid.length
// n == grid[i].length 
// 1 <= m, n <= 300 
// grid[i][j] 的值为 '0' 或 '1' 
// 
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 
// 👍 1517 👎 0


import java.util.LinkedList;
import java.util.Queue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /*
    1.深度优先搜索
    我们可以将二维网格看成一个无向图，竖直或水平相邻的 11 之间有边相连。
    为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 11，则以
    其为起始节点开始进行深度优先搜索。在深度优先搜索的过程中，每个搜索到的 11 都会被重新标记为 00。
    最终岛屿的数量就是我们进行深度优先搜索的次数。
    时间复杂度：O(MN)O(MN)，其中 MM 和 NN 分别为行数和列数。
    空间复杂度：O(MN)O(MN)，在最坏情况下，整个网格均为陆地，深度优先搜索的深度达到 M NMN。
     */

//    void dfs(char[][] grid, int row, int col) {
//        int rowLen = grid.length;
//        int colLen = grid[0].length;
//        if (row < 0 || col < 0 || row >= rowLen || col >= colLen || grid[row][col] == '0') {
//            return;
//        }
//        grid[row][col] = '0';
//        dfs(grid, row - 1, col);
//        dfs(grid, row + 1, col);
//        dfs(grid, row, col - 1);
//        dfs(grid, row, col + 1);
//    }
//    public int numIslands(char[][] grid) {
//        if (grid == null || grid.length == 0) {
//            return 0;
//        }
//        int islandNum = 0;
//        for (int row = 0; row < grid.length; row++) {
//            for (int col = 0; col < grid[0].length; col++) {
//                if (grid[row][col] == '1') {
//                    islandNum++;
//                    dfs(grid, row, col);
//                }
//            }
//        }
//        return islandNum;
//    }


    /*
    2.广度优先搜索
    同样地，我们也可以使用广度优先搜索代替深度优先搜索。
    为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 1，则将其加入队列，开始进行广度优先搜索。
    在广度优先搜索的过程中，每个搜索到的 1 都会被重新标记为 0。直到队列为空，搜索结束。
    最终岛屿的数量就是我们进行广度优先搜索的次数。
    时间复杂度：O(MN)，其中 M 和 N 分别为行数和列数。
    空间复杂度：O(min(M,N))，在最坏情况下，整个网格均为陆地，队列的大小可以达到min(M,N)。
     */

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int islandNum = 0;
        int rowLen = grid.length;
        int colLen = grid[0].length;
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                if (grid[row][col] == '1') {
                    islandNum++;
                    grid[row][col] = '0';
                    Queue<Integer> neighbors = new LinkedList<>();
                    neighbors.add(row * colLen + col);
                    while (!neighbors.isEmpty()) {
                        int id = neighbors.remove();
                        int r = id / colLen, c = id % colLen;
                        if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                            grid[r - 1][c] = '0';
                            neighbors.add((r - 1) * colLen + c);
                        }
                        if (r + 1 < rowLen && grid[r + 1][c] == '1') {
                            grid[r + 1][c] = '0';
                            neighbors.add((r + 1) * colLen + c);
                        }
                        if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                            grid[r][c - 1] = '0';
                            neighbors.add(r * colLen + c - 1);
                        }
                        if (c + 1 < colLen && grid[r][c + 1] == '1') {
                            grid[r][c + 1] = '0';
                            neighbors.add(r * colLen + c + 1);
                        }
                    }

                }
            }
        }
        return islandNum;
    }



}
//leetcode submit region end(Prohibit modification and deletion)
