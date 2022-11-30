//给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。 
// 示例 1：
//输入：head = [1,2,2,1]
//输出：true
// 示例 2：
//输入：head = [1,2]
//输出：false
//
// 提示： 
// 链表中节点数目在范围[1, 105] 内
// 0 <= Node.val <= 9 
// 
// 进阶：你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
// Related Topics 栈 递归 链表 双指针 
// 👍 1226 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

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
    public boolean isPalindrome(ListNode head) {
        /*
        1.将值复制到数组中后用双指针法

        时间复杂度：O(n)，其中 n 指的是链表的元素个数。
        第一步： 遍历链表并将值复制到数组中，O(n)。
        第二步：双指针判断是否为回文，执行了 O(n/2) 次的判断，即 O(n)。
        总的时间复杂度：O(2n) = O(n)O。

         */

//        List<Integer> vals = new ArrayList<Integer>();
//        ListNode cur = head;
//        while (cur != null) {
//            vals.add(cur.val);
//            cur = cur.next;
//        }
//        int left = 0;
//        int right = vals.size() - 1;
//        while (left < right) {
//            if (!vals.get(left).equals(vals.get(right))) return false;
//            ++left;
//            --right;
//        }
//        return true;

        /*
        2. 快慢指针找到中点，反转后面一半，头、中一起向后走比较
        时间复杂度：O(n)，其中 n 指的是链表的大小。
        空间复杂度：O(1)。我们只会修改原本链表中节点的指向，而在堆栈上的堆栈帧不超过 O(1)。
         */
        if (head == null) {
            return true;
        }
        ListNode endOfFirst = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(endOfFirst.next);
        ListNode first = head, second = secondHalfStart;
        while (second != null) {
            // 长度为奇数,前面一半长一个  second 先到终点
            if (first.val != second.val) {
                endOfFirst.next = reverseList(secondHalfStart);
                return false;
            }
            first = first.next;
            second = second.next;
        }
        endOfFirst.next = reverseList(secondHalfStart);
        return true;

    }

    private ListNode reverseList(ListNode head) {
        ListNode next = null, newHead = null;
        while (head != null) {
            next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
