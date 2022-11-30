//给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
//
//
//
//
// 示例 1：
//
//
//输入：head = [1,2,3,4,5]
//输出：[5,4,3,2,1]
//
//
// 示例 2：
//
//
//输入：head = [1,2]
//输出：[2,1]
//
//
// 示例 3：
//
//
//输入：head = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 链表中节点的数目范围是 [0, 5000]
// -5000 <= Node.val <= 5000
//
//
//
//
// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
//
//
// Related Topics 递归 链表
// 👍 2192 👎 0


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
    public ListNode reverseList(ListNode head) {
        /*
        1.迭代
         */
//        if (head == null || head.next == null) return head;
//        ListNode newHead = null;
//        ListNode next = null;
//        while (head != null) {
//            next = head.next;  // 存储后一个节点
//            head.next = newHead;  // 将当前节点的 next 指针改为指向前一个节点
//            newHead = head;  // 往后移动
//            head = next;
//        }
//        return newHead;  // 返回新的头引用
//    }
        /*
        2.递归
        
         */
         if (head == null || head.next == null) return head;
         ListNode newHead = reverseList(head.next);  // 递归到最后一个，头节点为最后一个
         head.next.next = head;  // 当前节点后一个指向当前节点
         head.next = null;       // 当前节点指向空
         return newHead;
     }

//    public static void main(String[] args) {
//        ListNode ln = new ListNode(1,
//                new ListNode(2,
//                        new ListNode(3,
//                                new ListNode(4,
//                                        new ListNode(5)))));
//        Solution s = new Solution();
//        System.out.println(s.removeElements(ln, 4));
//
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
