/*
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
 */

/* 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
示例 1：
输入：head = [1,2,3,4,5], k = 2
输出：[4,5,1,2,3]
示例 2：
输入：head = [0,1,2], k = 4
输出：[2,0,1] */

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
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        // 特殊情况
        if (head == null || head.next == null || k == 0) return head;
        // 尾节点指向头节点,形成环
        int len = 1;
        ListNode tail = head, newTail = head, newHead = head;
        while(tail.next != null) {
            tail = tail.next;
            ++len;
        } 
        tail.next = head;
        // 新的尾节点位置
        for(int i = 0; i < (len - k % len - 1); i++) {
            newTail = newTail.next;
        }
        newHead = newTail.next;
        newTail.next = null;
        return newHead;
    }
}
// @lc code=end

