/*
 * @lc app=leetcode.cn id=83 lang=java
 *
 * [83] 删除排序链表中的重复元素
 */

/* 在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
示例 1：
输入：head = [1,1,2]
输出：[1,2]
示例 2：
输入：head = [1,1,2,3,3]
输出：[1,2,3]
 */

// @lc code=start
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
public class Solution {
    // 每个元素只出现一次
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        ListNode cur = head;

        while (cur.next != null) {
            (cur.val == cur.next.val ? cur.next = cur.next.next : cur = cur.next;
        }
        return head;
    }
}
// @lc code=end

