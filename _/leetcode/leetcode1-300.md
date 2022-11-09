- [[33. 搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)](#head1)
- [[81. 搜索旋转排序数组 II](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/)](#head2)
- [[82. 删除排序链表中的重复元素 II](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/)](#head3)
- [[83. 删除排序链表中的重复元素](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/)](#head4)
- [[84. 柱状图中最大的矩形 ](https://leetcode-cn.com/problems/largest-rectangle-in-histogram/)](#head5)
- [[85. 最大矩形](https://leetcode-cn.com/problems/maximal-rectangle/)](#head6)
vscode_leetcode插件java输出乱码

>
>
>settings.json加上
>
>"code-runner.executorMap": {
>        "java": "cd $dir && javac -encoding utf-8 $fileName && java $fileNameWithoutExt"
>    },
>"code-runner.runInTerminal": true

## <span id="head1">[33. 搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)</span>

>在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。

总是有一半是有序的，二分查找

```java
class Solution {
    // 总是有一半是有序
    public int search(int[] nums, int target) {
        int n = nums.length;
        // 数组长度为0/1直接判断，否则二分查找
        if (n == 0) return -1;
        if (n == 1) return nums[0] == target ? 0 : -1;
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) return mid;
            // 首比中间小，说明左边有序
            if (nums[l] <= nums[mid]) {
                // 如果target在左边，移动右指针向左
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
```

## <span id="head2">[81. 搜索旋转排序数组 II](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/)</span>

>已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
>
>在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。
>
>给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。

```java
class Solution {
    public boolean search(int[] nums, int target) {
        int len = num.length;
        if (len == 0) return false;
        if (len == 1) return nums[0] == target;
        int l = 0, r = len - 1;
        while(l <= r) {
            int mid = (l + r) / 2;
            if (target == nums[mid]) retrun true;
            if (nums[l] == nums[mid] && nums[mid] == nums[r]) {
                ++l;
                --r;
            }
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1
                } else {
                    l = mid + 1
                }
            } else {
                if (nums[r] > target && target >= nums[mid]) {
                    l = mid + 1
                } else {
                    r = mid - 1
                }
            }
        }
    }
}
```

## <span id="head3">[82. 删除排序链表中的重复元素 II](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/)</span>

>存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。返回同样按升序排列的结果链表。

我们从指针 cur 指向链表的哑节点，随后开始对链表进行遍历。如果当前 cur.next 与 cur.next.next 对应的元素相同，那么我们就需要将 cur.next 以及所有后面拥有相同元素值的链表节点全部删除。我们记下这个元素值 x，随后不断将 cur.next 从链表中移除，直到 cur.next 为空节点或者其元素值不等于 xx为止。此时，我们将链表中所有元素值为 x 的节点全部删除。

如果当前 cur.next 与cur.next.next 对应的元素不相同，那么说明链表中只有一个元素值为 cur.next 的节点，那么我们就可以将 cur 指向 cur.next。

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        // 链表重复节点的是连续的
        // head 可能重复被删，用dummy指向
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        
        while(cur.next != null && cur.next.next !=null) {
            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;
                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
```



## <span id="head4">[83. 删除排序链表中的重复元素](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/)</span>

>存在一个按升序排列的链表，给你这个链表的头节点 `head` ，请你删除所有重复的元素，使每个元素 **只出现一次** 。

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return head;
        ListNode cur = head;
        
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;
    }
}
```



## <span id="head5">[84. 柱状图中最大的矩形 ](https://leetcode-cn.com/problems/largest-rectangle-in-histogram/)</span>

>给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积。

![](https://assets.leetcode.com/uploads/2021/01/04/histogram.jpg)

暴力解法

```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        int maxarea = 0;
        // 枚举每个柱子
        for(int i = 0; i < len; i++) {
            // 寻找左边界
            int left = 0;
            for(left = i - 1; left >= 0; left--) {
                if(heights[left] < heights[i]) break;
            }
            int right = 0;
            // 寻找右边界
            for(right = i + 1; right < len; right++) {
                if(heights[right] < heights[i]) break;
            }
            // 计算最大面积
            maxarea = Math.max(maxarea, heights[i] * (right - left - 1));
        }
        return maxarea;
    }
}

```

对于暴力求解法2，我们发现对于左右边界的查找是有规律的

1.如果柱子的高度递减，那么每个柱子的左边界就是第一根柱子，右边界就是本身。
2.如果柱子的高度递增，那么每个柱子的右边界就是最后一根柱子，左边界就是本身。

有了这样的思路，那么对于无序的柱子组合，我们可以将其拆分， 保证每一个局部是有序的，然后计算最大面积就容易了。

局部的柱子有序后，那如何保存这些局部的柱子呢？ 我们发现这些柱子，具有最近相关性，所以使用栈保存这些有序的柱子。

遍历每一个柱子，如果当前遍历的柱子比栈顶大，那么当前柱子入栈，说明还没有找到右边界。

如果当前遍历的柱子比栈顶小，那么栈顶的柱子找到了右边界，当前遍历柱子的左边界就是栈中栈顶元素的下一个元素的位置，这时候面积就可以计算了。

大则入栈，小则处理栈中元素计算最大面积，直到栈中没有比当前遍历的柱子更短的柱子，然后将当前遍历的柱子再入栈。

遍历结束后， 栈中可能有数据， 这时候的数据必然是递增的， 同时每根柱子的右边界是柱状图的最后一根柱子，每个柱子的左边界就是他本身

```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        int maxarea = 0;
        Stack<Integer> stk = new Stack<Integer>();

        for(int i = 0; i < len; ++i) {
            // 只要栈不为空，并且当前遍历的数据小于栈顶元素，则说明找到了右边界
            while(!stk.empty() && heights[i] < heights[stk.peek()]) {
                // 右边界 heights[i]
                int h = heights[stk.peek()];
                stk.pop();
                // 出栈后，如果栈为空，说明出栈的柱子目前遍历的最短的柱子，否则计算宽度差
                int w = stk.empty() ? i : i - stk.peek() - 1;
                maxarea = Math.max(h * w, maxarea);
            }
            // 栈为空或者当前遍历的数据大于等于栈顶数据，则入栈，不会执行上面的while循环
            // 保证了栈中的数据总是递增的 比如  0 1 2 2 3 4 4 5 6 ...
            stk.push(i);
        }

        // 处理剩余栈中的数据(递增的数据，右边界是柱状图中最右边的柱子)
        while(!stk.empty()) {
            int h = heights[stk.peek()];
            stk.pop();
            // 出栈后，如果栈为空，说明出栈的柱子目前遍历的最短的柱子，否则计算宽度差
            int w = stk.empty() ? len : len - stk.peek() - 1;
            maxarea = Math.max(h * w, maxarea);
        }

        return maxarea;

    }
}
```

 需要考虑两种特殊的情况：弹栈的时候，栈为空；遍历完成以后，栈中还有元素；

可以在输入数组的两端加上两个高度为 0 （或者是 0.5，只要比 1 严格小都行）的柱形，可以回避上面这两种分类讨论。

这两个站在两边的柱形有一个很形象的名词，叫做哨兵（Sentinel）。

有了这两个柱形：左边的柱形（第 1 个柱形）由于它一定比输入数组里任何一个元素小，它肯定不会出栈，因此栈一定不会为空；右边的柱形（第 2 个柱形）也正是因为它一定比输入数组里任何一个元素小，它会让所有输入数组里的元素出栈（第 1 个哨兵元素除外）。

```java
import java.util.ArrayDeque;
import java.util.Deque;
class Solution {
    public int largestRectangleArea(int[] heights) {
        // 特殊情况判断
        int len = heights.length;
        if (len == 0) return 0;
        if (len == 1) return heights[0];
        // 添加哨兵
        int[] newHeights = new int[len +2];
        newHeights[0] = 0;
        System.arraycopy(heights, 0, newHeights, 1, len);
        newHeights[len +1] = 0;
        len += 2;
        heights = newHeights;
        // 先放入哨兵，在循环里就不用做非空判断
        Deque<Integer> stack = new ArrayDeque<>(len);
        stack.addLast(0);
        
        int area = 0;
        for (int i = 1;  i < len; i++) {
            // 到了当前柱形的高度比它上一个柱形的高度严格小的时候，一定可以确定它之前的某些柱形的最大宽度，并且确定的柱形宽度的顺序是从右边向左边。
            while(heights[i] < heights[stack.peekLast()]) {
                area = Math.max(area, heights[stack.pollLast()] * (i - stack.peekLast() - 1));
            }
            stack.addLast(i);
        }
        return area;

    }
}
```

## <span id="head6">[85. 最大矩形](https://leetcode-cn.com/problems/maximal-rectangle/)</span>

> 给定一个仅包含 `0` 和 `1` 、大小为 `rows x cols` 的二维二进制矩阵，找出只包含 `1` 的最大矩形，并返回其面积。



```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) return 0;
        int n = matrix[0].length;
        int[][] left = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
                }
            }
        }

        int ret = 0;
        for (int j = 0; j < n; j++) { // 对于每一列，使用基于柱状图的方法
            int[] up = new int[m];
            int[] down = new int[m];

            Deque<Integer> stack = new LinkedList<Integer>();
            for (int i = 0; i < m; i++) {
                while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
                    stack.pop();
                }
                up[i] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(i);
            }
            stack.clear();
            for (int i = m - 1; i >= 0; i--) {
                while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
                    stack.pop();
                }
                down[i] = stack.isEmpty() ? m : stack.peek();
                stack.push(i);
            }

            for (int i = 0; i < m; i++) {
                int height = down[i] - up[i] - 1;
                int area = height * left[i][j];
                ret = Math.max(ret, area);
            }
        }
        return ret;
    }
}
```

