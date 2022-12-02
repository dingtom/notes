#### 方法一：递归

链表的定义具有递归的性质，因此链表题目常可以用递归的方法求解。这道题要求删除链表中所有节点值等于特定值的节点，可以用递归实现。

对于给定的链表，首先对除了头节点 ![\textit{head} ](./p__textit{head}_.png)  以外的节点进行删除操作，然后判断 ![\textit{head} ](./p__textit{head}_.png)  的节点值是否等于给定的 ![\textit{val} ](./p__textit{val}_.png) 。如果 ![\textit{head} ](./p__textit{head}_.png)  的节点值等于 ![\textit{val} ](./p__textit{val}_.png) ，则 ![\textit{head} ](./p__textit{head}_.png)  需要被删除，因此删除操作后的头节点为 ![\textit{head}.\textit{next} ](./p__textit{head}.textit{next}_.png) ；如果 ![\textit{head} ](./p__textit{head}_.png)  的节点值不等于 ![\textit{val} ](./p__textit{val}_.png) ，则 ![\textit{head} ](./p__textit{head}_.png)  保留，因此删除操作后的头节点还是 ![\textit{head} ](./p__textit{head}_.png) 。上述过程是一个递归的过程。

递归的终止条件是 ![\textit{head} ](./p__textit{head}_.png)  为空，此时直接返回 ![\textit{head} ](./p__textit{head}_.png) 。当 ![\textit{head} ](./p__textit{head}_.png)  不为空时，递归地进行删除操作，然后判断 ![\textit{head} ](./p__textit{head}_.png)  的节点值是否等于 ![\textit{val} ](./p__textit{val}_.png)  并决定是否要删除 ![\textit{head} ](./p__textit{head}_.png) 。

```Java [sol1-Java]
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        head.next = removeElements(head.next, val);
        return head.val == val ? head.next : head;
    }
}
```

```C# [sol1-C#]
public class Solution {
    public ListNode RemoveElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        head.next = RemoveElements(head.next, val);
        return head.val == val ? head.next : head;
    }
}
```

```JavaScript [sol1-JavaScript]
var removeElements = function(head, val) {
    if (head === null) {
            return head;
        }
        head.next = removeElements(head.next, val);
        return head.val === val ? head.next : head;
};
```

```go [sol1-Golang]
func removeElements(head *ListNode, val int) *ListNode {
    if head == nil {
        return head
    }
    head.Next = removeElements(head.Next, val)
    if head.Val == val {
        return head.Next
    }
    return head
}
```

```C++ [sol1-C++]
class Solution {
public:
    ListNode* removeElements(ListNode* head, int val) {
        if (head == nullptr) {
            return head;
        }
        head->next = removeElements(head->next, val);
        return head->val == val ? head->next : head;
    }
};
```

```C [sol1-C]
struct ListNode* removeElements(struct ListNode* head, int val) {
    if (head == NULL) {
        return head;
    }
    head->next = removeElements(head->next, val);
    return head->val == val ? head->next : head;
}
```

**复杂度分析**

- 时间复杂度：*O(n)*，其中 *n* 是链表的长度。递归过程中需要遍历链表一次。

- 空间复杂度：*O(n)*，其中 *n* 是链表的长度。空间复杂度主要取决于递归调用栈，最多不会超过 *n* 层。

#### 方法二：迭代

也可以用迭代的方法删除链表中所有节点值等于特定值的节点。

用 ![\textit{temp} ](./p__textit{temp}_.png)  表示当前节点。如果 ![\textit{temp} ](./p__textit{temp}_.png)  的下一个节点不为空且下一个节点的节点值等于给定的 ![\textit{val} ](./p__textit{val}_.png) ，则需要删除下一个节点。删除下一个节点可以通过以下做法实现：

![\textit{temp}.\textit{next}=\textit{temp}.\textit{next}.\textit{next} ](./p___textit{temp}.textit{next}_=_textit{temp}.textit{next}.textit{next}__.png) 

如果 ![\textit{temp} ](./p__textit{temp}_.png)  的下一个节点的节点值不等于给定的 ![\textit{val} ](./p__textit{val}_.png) ，则保留下一个节点，将 ![\textit{temp} ](./p__textit{temp}_.png)  移动到下一个节点即可。

当 ![\textit{temp} ](./p__textit{temp}_.png)  的下一个节点为空时，链表遍历结束，此时所有节点值等于 ![\textit{val} ](./p__textit{val}_.png)  的节点都被删除。

具体实现方面，由于链表的头节点 ![\textit{head} ](./p__textit{head}_.png)  有可能需要被删除，因此创建哑节点 ![\textit{dummyHead} ](./p__textit{dummyHead}_.png) ，令 ![\textit{dummyHead}.\textit{next}=\textit{head} ](./p__textit{dummyHead}.textit{next}_=_textit{head}_.png) ，初始化 ![\textit{temp}=\textit{dummyHead} ](./p__textit{temp}=textit{dummyHead}_.png) ，然后遍历链表进行删除操作。最终返回 ![\textit{dummyHead}.\textit{next} ](./p__textit{dummyHead}.textit{next}_.png)  即为删除操作后的头节点。

```Java [sol2-Java]
class Solution {
    public ListNode removeElements(ListNode head, int val) {
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
}
```

```C# [sol2-C#]
public class Solution {
    public ListNode RemoveElements(ListNode head, int val) {
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
}
```

```JavaScript [sol2-JavaScript]
var removeElements = function(head, val) {
    const dummyHead = new ListNode(0);
    dummyHead.next = head;
    let temp = dummyHead;
    while (temp.next !== null) {
        if (temp.next.val == val) {
            temp.next = temp.next.next;
        } else {
            temp = temp.next;
        }
    }
    return dummyHead.next;
};
```

```go [sol2-Golang]
func removeElements(head *ListNode, val int) *ListNode {
    dummyHead := &ListNode{Next: head}
    for tmp := dummyHead; tmp.Next != nil; {
        if tmp.Next.Val == val {
            tmp.Next = tmp.Next.Next
        } else {
            tmp = tmp.Next
        }
    }
    return dummyHead.Next
}
```

```C++ [sol2-C++]
class Solution {
public:
    ListNode* removeElements(ListNode* head, int val) {
        struct ListNode* dummyHead = new ListNode(0, head);
        struct ListNode* temp = dummyHead;
        while (temp->next != NULL) {
            if (temp->next->val == val) {
                temp->next = temp->next->next;
            } else {
                temp = temp->next;
            }
        }
        return dummyHead->next;
    }
};
```

```C [sol2-C]
struct ListNode* removeElements(struct ListNode* head, int val) {
    struct ListNode* dummyHead = malloc(sizeof(struct ListNode));
    dummyHead->next = head;
    struct ListNode* temp = dummyHead;
    while (temp->next != NULL) {
        if (temp->next->val == val) {
            temp->next = temp->next->next;
        } else {
            temp = temp->next;
        }
    }
    return dummyHead->next;
}
```

**复杂度分析**

- 时间复杂度：*O(n)*，其中 *n* 是链表的长度。需要遍历链表一次。

- 空间复杂度：*O(1)*。