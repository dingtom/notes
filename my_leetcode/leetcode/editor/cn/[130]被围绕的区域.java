//给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充
//
// 示例 1：
//输入：board = [["X","X","X","X"],
//              ["X","O","O","X"],
//              ["X","X","O","X"],
//              ["X","O","X","X"]]
//输出：[["X","X","X","X"],
//      ["X","X","X","X"],
//      ["X","X","X","X"],
//      ["X","O","X","X"]]
//解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都
//会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
//
// 示例 2： 
//输入：board = [["X"]]
//输出：[["X"]]
//
// 提示： 
// m == board.length
// n == board[i].length 
// 1 <= m, n <= 200 
// board[i][j] 为 'X' 或 'O' 
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 
// 👍 718 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public void solve(char[][] board) {
        int rowLen = board.length;
        if (rowLen == 0) {
            return;
        }
        int colLen = board[0].length;
        for(int i = 0; i < rowLen; i++) {
            dfs(board, i, 0);
            dfs(board, i, colLen - 1);
        }
        for(int i = 1; i < colLen - 1; i++) {
            dfs(board, 0, i);
            dfs(board, rowLen - 1, i);
        }

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }
    public void dfs(char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] != 'O') {
            return;
        }
        board[row][col] = 'A';
        int[] i = {1, -1, 0, 0};
        int[] j = {0, 0, 1, -1};
        for (int index = 0; index < 4; index++) {
            dfs(board, row + i[index], col + j[index]);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
