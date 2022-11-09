//给定一个 m x n 的非负整数矩阵来表示一片大陆上各个单元格的高度。
// “太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。
//
// 规定水流只能按照上、下、左、右四个方向流动，且只能从高到低或者在同等高度上流动。 
//
// 请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。 
//
// 
//
// 提示： 
//
// 
// 输出坐标的顺序不重要 
// m 和 n 都小于150 
// 
//
// 
//
// 示例： 
//
// 
//
// 
//给定下面的 5x5 矩阵:
//
//  太平洋 ~   ~   ~   ~   ~ 
//       ~  1   2   2   3  (5) *
//       ~  3   2   3  (4) (4) *
//       ~  2   4  (5)  3   1  *
//       ~ (6) (7)  1   4   5  *
//       ~ (5)  1   1   2   4  *
//          *   *   *   *   * 大西洋
//
//返回:
//
//[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (上图中带括号的单元).
// 
//
// 
// Related Topics 深度优先搜索 广度优先搜索 数组 矩阵 
// 👍 326 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        //太平洋的节点记录矩阵
        int[][] ao = new int[m][n];
        //大西洋的节点记录矩阵
        int[][] pa = new int[m][n];
        //1. 从上下边界开始两大洋的回流搜索，变动的是列
        for(int i=0;i<n;i++){
            dfs(heights,pa,0,i);
            dfs(heights,ao,m-1,i);
        }
        //2. 从左右边界开始两大洋的回流搜索，变动的是行
        for(int i=0;i<m;i++){
            dfs(heights,pa,i,0);
            dfs(heights,ao,i,n-1);
        }
        //3. 输出交叠的坐标
        List<List<Integer>> cnt = new ArrayList<List<Integer>>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(ao[i][j]==1&&pa[i][j]==1){
                    cnt.add(Arrays.asList(i,j));
                }
            }
        }
        return cnt;
    }

    public static void dfs(int[][] heights,int[][] tmp,int cur_i,int cur_j){
        //标记可以从海洋流回经过的节点
        tmp[cur_i][cur_j]=1;
        //开始深度优先搜索当前坐标的4个方向
        //1. 设置更新的坐标
        //上下移动
        int[] di=new int[]{1,-1,0,0};
        //左右移动
        int[] dj=new int[]{0,0,1,-1};
        int new_i=0;
        int new_j=0;
        //2. 更新坐标并递归搜索
        for(int index=0;index<4;index++){
            new_i=cur_i+di[index];
            new_j=cur_j+dj[index];
            //判断下标是否越界
            if(new_i<0||new_j<0||new_i>=heights.length||new_j>=heights[0].length){
                continue;
            }
            if(heights[cur_i][cur_j]<=heights[new_i][new_j]&&tmp[new_i][new_j]!=1){
                dfs(heights,tmp,new_i,new_j);
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
