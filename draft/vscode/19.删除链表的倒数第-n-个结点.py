'''
Author: dingtom 2524370217@qq.com
Date: 2022-09-02 15:58:02
LastEditors: dingtom 2524370217@qq.com
LastEditTime: 2022-09-02 15:58:23
FilePath: \draft\leetcode\vscode\19.删除链表的倒数第-n-个结点.py
Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
'''
#
# @lc app=leetcode.cn id=19 lang=python3
#
# [19] 删除链表的倒数第 N 个结点
#

# @lc code=start
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def removeNthFromEnd(self, head: Optional[ListNode], n: int) -> Optional[ListNode]:
# @lc code=end
