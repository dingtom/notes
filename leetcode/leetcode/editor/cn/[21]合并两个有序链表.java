//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
// 示例 1：
//输入：l1 = [1,2,4], l2 = [1,3,4]
//输出：[1,1,2,3,4,4]
//
// 示例 2： 
//输入：l1 = [], l2 = []
//输出：[]
//
// 示例 3： 
//输入：l1 = [], l2 = [0]
//输出：[0]
//
// 提示： 
// 两个链表的节点数目范围是 [0, 50]
// -100 <= Node.val <= 100 
// l1 和 l2 均按 非递减顺序 排列 
// 
// Related Topics 递归 链表 
// 👍 2191 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        /**
         * 递归
         *
         * 时间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。因为每次调用递归都会去掉 l1 或者 l2 的头节点
         * （直到至少有一个链表为空），函数 mergeTwoList 至多只会递归调用每个节点一次。因此，时间复杂度取决于
         * 合并后的链表长度，即 O(n+m)。
         *
         * 空间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。递归调用 mergeTwoLists 函数时需要消耗栈空间，
         * 栈空间的大小取决于递归调用的深度。结束递归调用时 mergeTwoLists 函数最多调用n+m 次，因此空间复杂度为 O(n+m)。
         */
//        if (list1 == null) {
//            return list2;
//        } else if (list2 == null) {
//            return list1;
//        } else if (list1.val < list2.val) {
//            list1.next = mergeTwoLists(list1.next, list2);
//            return list1;
//        } else {
//            list2.next = mergeTwoLists(list1, list2.next);
//            return list2;
//        }

        /**
         * 迭代
         * 时间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。
         * 时间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。
         */
        ListNode dummyNode = new ListNode(-1);
        ListNode cur = dummyNode;
        while(list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return dummyNode.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
