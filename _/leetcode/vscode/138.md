/*
 * @Author: your name
 * @Date: 2021-12-20 11:15:03
 * @LastEditTime: 2021-12-29 18:05:53
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \vscode\138.复制带随机指针的链表.java
 */
/*
 * @lc app=leetcode.cn id=138 lang=java
 *
 * [138] 复制带随机指针的链表
 */

/* 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。

构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。

用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
val：一个表示 Node.val 的整数。
random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
你的代码 只 接受原链表的头节点 head 作为传入参数。


示例 1：
输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
示例 2：
输入：head = [[1,1],[2,1]]
输出：[[1,1],[2,1]]
示例 3：
输入：head = [[3,null],[3,0],[3,null]]
输出：[[3,null],[3,0],[3,null]]
示例 4：
输入：head = []
输出：[]
解释：给定的链表为空（空指针），因此返回 null。
 */
// @lc code=start
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    /* 用哈希表记录每一个节点对应新节点的创建情况。遍历该链表的过程中，
    我们检查「当前节点的后继节点」和「当前节点的随机指针指向的节点」
    的创建情况。如果这两个节点中的任何一个节点的新节点没有被创建，我
    们都立刻递归地进行创建。 */
    Map<Node, Node> cachedNode = new HashMap<Node, Node>();

    public Node copyRandomList1(Node head) {
        if (head == null) return null;
        /* 当我们拷贝完成，回溯到当前层时，我们即可完成当前节点的指针赋值。
        注意一个节点可能被多个其他节点指向，因此我们可能递归地多次尝试
        拷贝某个节点，为了防止重复拷贝，我们需要首先检查当前节点是否被
        拷贝过，如果已经拷贝过，我们可以直接从哈希表中取出拷贝后的节点
        的指针并返回即可。 */
        if (!cachedNode.containsKey(head)) {
            Node headNew = new Node(head.val);
            cachedNode.put(head, headNew);
            headNew.next = copyRandomList(head.next);
            headNew.random = copyRandomList(head.random);
        }
        return cachedNode.get(head);
    }

    public Node copyRandomList(Node head) {
        /* 首先遍历原链表，每遍历到一个节点，都新建一个相同val的节点，
        然后使用HashMap存放原链表到新链表的键值对。第二次遍历时通过HashMap，
        建立新链表节点之间的next和random关系。 */
        if (head == null) return null;
        Node cur = head;
        HashMap<Node, Node> map = new HashMap<>();
        while(cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while(cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }


}
// @lc code=end

