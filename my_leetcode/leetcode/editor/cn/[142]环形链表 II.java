// 给定一个链表的头节点 head ，返回链表开始入环的第一个节点。
// 如果链表无环，则返回 null。
// 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
// 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到
// 链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。
// 注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
// 不允许修改 链表。
//
// 示例 1：
//输入：head = [3,2,0,-4], pos = 1
//输出：返回索引为 1 的链表节点
//解释：链表中有一个环，其尾部连接到第二个节点。
//
// 示例 2： 
//输入：head = [1,2], pos = 0
//输出：返回索引为 0 的链表节点
//解释：链表中有一个环，其尾部连接到第一个节点。
//
// 示例 3：
//输入：head = [1], pos = -1
//输出：返回 null
//解释：链表中没有环。
// 
// 提示：
// 链表中节点的数目范围在范围 [0, 104] 内
// -105 <= Node.val <= 105 
// pos 的值为 -1 或者链表中的一个有效索引 
//
// 进阶：你是否可以使用 O(1) 空间解决此题？ 
// Related Topics 哈希表 链表 双指针 
// 👍 1374 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.HashSet;

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        /**
         * 哈希记录是否出现
         */
//        ListNode pos = head;
//        Set<ListNode> visited = new HashSet<ListNode>();
//        while (pos != null) {
//            if (visited.contains(pos)) {
//                return pos;
//            } else {
//                visited.add(pos);
//            }
//            pos = pos.next;
//        }

        /**
         * 头节点到入环节点长度为a; 入环节点到快慢指针相遇节点长度为b; 相遇节点到入环节点长度为c
         * 快指针走的长度是慢指针的两倍 a+n(b+c)+b = 2(a+b) ->   (n-1)(b+c) + c = a
         * 相遇点到入环点的距离加上 n−1 圈的环长，恰好等于从链表头部到入环点的距离。
         * 因此，当发现 slow 与fast 相遇时，我们再额外使用一个指针 ptr。起始，它指向链表头部；
         * 随后，它和 slow 每次向后移动一个位置。最终，它们会在入环点相遇。
         *
         *
         * 时间复杂度：O(N)，其中 N 为链表中节点的数目。在最初判断快慢指针是否相遇时，slow 指针
         * 走过的距离不会超过链表的总长度；随后寻找入环点时，走过的距离也不会超过链表的总长度。
         * 因此，总的执行时间为 O(N)+O(N)=O(N)。
         * 空间复杂度：O(1)。我们只使用了slow,fast,ptr 三个指针。
         */
        if (head == null || head.next == null) {
            return null;
        }
        ListNode fast = head, slow = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
        
    }
}
//leetcode submit region end(Prohibit modification and deletion)
