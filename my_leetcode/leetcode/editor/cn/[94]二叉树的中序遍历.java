//给定一个二叉树的根节点 root ，返回它的 中序 遍历。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,null,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1]
//输出：[1]
// 
//
// 示例 4： 
//
// 
//输入：root = [1,2]
//输出：[2,1]
// 
//
// 示例 5： 
//
// 
//输入：root = [1,null,2]
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [0, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 深度优先搜索 二叉树 
// 👍 1327 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    // ------------------------------------------------1
    public List<Integer> inorderTraversal0(TreeNode root) {
        /**
         * 递归
         * 时间：O(n)
         * 空间：O(h) h是二叉树高度
         */
        List<Integer> result = new ArrayList<Integer>();
        getResult(root, result);
        return result;
    }
    private void getResult(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        getResult(node.left, result);
        result.add(node.val);
        getResult(node.right, result);
    }
    // ------------------------------------------------2
    public List<Integer> inorderTraversal(TreeNode root) {
        /**
         * 栈
         * 时间：O(n)
         * 空间：O(h) h是二叉树高度
         */
        List<Integer> result = new ArrayList<Integer>();
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            // 进栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.add(root.val);
            root = root.right;
        }
        return result;

    }
    // -----------------------------------------------3
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();

        /**
         * Morris Tranversal
         * 1,2 都需要空间保存上一层信息
         * 左子树遍历的最后一个节点一定是叶子节点，把它的右孩子指向当前节点，可以节省空间
         */
        TreeNode predecessor = null;
        while (root != null) {
            if (root.left == null) {  // 如果没有左孩子，则直接访问右孩子
                result.add(root.val);
                root = root.right;
            } else {
                // 找到predecessor 节点就是当前 root 节点左子树最右边的节点，是root的前驱节点
                predecessor = root.left;
                while (predecessor.right != null && predecessor != root) {
                    predecessor = predecessor.right;
                }
                // predecessor右指针为空，右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                }
                // predecessor右指针指向root，说明之前已经访问过，
                if (predecessor.right == root){  // 说明左子树已经访问完了，我们需要断开链接
                    result.add(root.val);
                    predecessor.right = null;
                    root = root.right;
                }
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
