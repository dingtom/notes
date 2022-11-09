- [ 递归](#head1)
- [ 双指针](#head2)
- [ 找出环的入口点：](#head3)
[24. 反转链表](https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/)
>定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
###### <span id="head1"> 递归</span>
>1. 定义递归函数功能，返回反转后头节点
>2. 寻找结束条件，若是节点为零或者1直接返回
>3. 寻找等价关系，reverse(head) = reverse(head.next);head.next.next=head;head.next=None
>![](https://upload-images.jianshu.io/upload_images/18339009-3dac4084ec28a89d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
 # 如果链表是1->2->3，
    def reverseList(self, head: ListNode) -> ListNode:
        # 递归终止条件是当前为空，或者下一个节点为空
        if not head or not head.next:
            return head
       #   head=1, head.next=2, 此时下一层返回的new_head是3
        new_head = self.reverseList(head.next)  # new_head指向3
        head.next.next = head  # 把2指向1
        head.next = None  # 把1指向None
        return new_head
```
###### <span id="head2"> 双指针</span>
next    -head           -pre         -head
>![](https://upload-images.jianshu.io/upload_images/18339009-05aa21c87f811971.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```
# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        if not head or not head.next:
             return head
        pre_index = next_index = None
        while head:  
        # head指向当前节点，一次循环后的下个节点是否为空？为空返回空节点的上一个节点
            # head->1->2->3->4->5  head指向当前节点

            next_index = head.next  # next 的指向
            head.next = pre_index   # head 的指向
            pre_index = head       #  pre 的指向
            head = next_index      # head 的指向
        return pre_index
```


# <span id="head3"> 找出环的入口点：</span>

>从上面的分析知道，当fast和slow相遇时，slow还没有走完链表
假设fast已经在环内循环了n(1<= n)圈
环长r
假设slow走了s步，则fast走了2s步
$2*s = s + n  * r  $
$ s = n*r $
如果假设整个链表的长度是L
入口和相遇点的距离是x
起点到入口点的距离是a
$a + x = s = n * r$
$ L = a +r$
$a + x = (n - 1) * r + r  = (n - 1) * r + (L - a) $
$a = (n - 1) * r + (L -a -x) $
$a = n * r + x$

从起点到入口的距离a,相遇点到入口的距离x相等。
因此我们就可以分别用一个指针（ptr1, prt2），同时从起点、相遇点出发，每一次操作走一步，直到ptr1 == ptr2，此时的位置也就是入口点！
