//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。 
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
//
// 示例 1: 
//输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
//
// 示例 2: 
//输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4 
//
// 提示： 
// 1 <= k <= nums.length <= 104
// -104 <= nums[i] <= 104 
// 
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 
// 👍 1458 👎 0


import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findKthLargest(int[] nums, int k) {


        /*
        我们可以用最小堆的优先队列：

        遍历数组中的元素，同时把元素放入优先队列中。
        如果优先队列中的元素数量为k，我们后面只放入比队列头(堆顶)大的元素。
        当优先队列元素超出k个时，移除队列头(堆顶)元素。
        每轮迭代结束时，优先队列中的元素最多不超过k个。
        当遍历完所有元素后，选出优先队列中的第一个元素，就是第k个最大元素。
        时间复杂度：O(nlogk)
        空间复杂度：优先级队列所占用的空间 O(k)
         */

/*        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if (minHeap.size() == k && nums[i] <= minHeap.peek()) {
                continue;
            }
            minHeap.offer(nums[i]);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.peek();*/

        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        //建堆完毕后，nums【0】为最大元素。逐个删除堆顶元素，直到删除了k-1个。
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            //先将堆的最后一个元素与堆顶元素交换，相当于删除堆顶元素，此时长度变为nums.length-2。即下次循环的i
            swap(nums, 0, i);
            --heapSize;
            // 由于此时堆的性质被破坏，需对此时的根节点进行向下调整操作。
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    public void buildMaxHeap(int[] a, int heapSize) {
        //从最后一个父节点位置开始调整每一个节点的子树。数组长度为heasize，
        // 因此最后一个节点的位置为heapsize-1，所以父节点的位置为heapsize-1-1/2。
        for (int i = (heapSize - 2) / 2; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        // left和right表示当前父节点i的两个左右子节点。
        int left = i * 2 + 1, right = i * 2 + 2, largest = i;
        //如果左子点在数组内，且比当前父节点大，则将最大值的指针指向左子点。
        if (left < heapSize && a[left] > a[largest]) {
            largest = left;
        }
        //如果右子点在数组内，且比当前父节点大，则将最大值的指针指向右子点。
        if (right < heapSize && a[right] > a[largest]) {
            largest = right;
        }
        //如果最大值的指针不是父节点，则交换父节点和当前最大值指针指向的子节点。
        if (largest != i) {
            swap(a, i, largest);
            //由于交换了父节点和子节点，因此可能对子节点的子树造成影响，所以对子节点的子树进行调整。
            maxHeapify(a, largest, heapSize);
        }
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
