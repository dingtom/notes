//给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。 
// 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
// 示例 1：
//输入：matrix =
// [[1,2,3],  [1,4,7]
//  [4,5,6],  [2,5,8]
//  [7,8,9]]  [3,6,9]
//输出：
// [[7,4,1],
//  [8,5,2],
//  [9,6,3]]
//
// 示例 2： 
//输入：matrix =
// [[5,1,9,11],
//  [2,4,8,10],
//  [13,3,6,7],
//  [15,14,12,16]]
//输出：
// [[15,13,2,5],
//  [14,3,4,1],
//  [12,6,8,9],
//  [16,7,10,11]]
//
// 提示： 
// n == matrix.length == matrix[i].length
// 1 <= n <= 20 
// -1000 <= matrix[i][j] <= 1000 
// 
//
// 
// Related Topics 数组 数学 矩阵 👍 1317 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public void rotate(int[][] matrix) {
        /*
        先对角线交换，再纵轴交换
        时间复杂度：O(N^2)，
        空间复杂度：O(1)。为原地翻转得到的原地旋转。
         */
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            int len = matrix[i].length - 1;
            for (int j = 0; j < matrix.length/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][len - j];
                matrix[i][len - j] = temp;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
