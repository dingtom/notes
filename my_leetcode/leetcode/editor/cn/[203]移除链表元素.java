//给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足
// Node.val == val 的节点，并返回 新的头节点 。
// 
//
// 示例 1： 
//输入：head = [1,2,6,3,4,5,6], val = 6
//输出：[1,2,3,4,5]
// 示例 2：
//输入：head = [], val = 1
//输出：[]
// 示例 3：
//输入：head = [7,7,7,7], val = 7
//输出：[]
//
// 提示： 
// 列表中的节点数目在范围 [0, 104] 内
// 1 <= Node.val <= 50 
// 0 <= val <= 50 
// 
// Related Topics 递归 链表 


//leetcode submit region begin(Prohibit modification and deletion)
// Definition for singly-linked list.

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}

class Solution {
    public ListNode removeElements(ListNode head, int val) {
        /*
        1.递归
        递归的终止条件是 head 为空，此时直接返回 head。
        当 head 不为空时，递归地进行删除操作，然后判断 head
        的节点值是否等于val 并决定是否要删除 head。
         */



//        if (head == null) {
//            return head;
//        }
//        head.next = removeElements(head.next, val);
//        return head.val == val ? head.next : head;


        /*
        2.迭代

         */
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }

        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode ln = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(4,
                                        new ListNode(5)))));
        Solution s = new Solution();
        System.out.println(s.removeElements(ln, 4));

    }
}
//leetcode submit region end(Prohibit modification and deletion)
