//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。 
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
// 示例 1：
//输入：x = 123
//输出：321
//
// 示例 2： 
//输入：x = -123
//输出：-321
//
// 示例 3： 
//输入：x = 120
//输出：21
// 
// 示例 4：
//输入：x = 0
//输出：0
//
// 提示： 
// -231 <= x <= 231 - 1
// 
// Related Topics 数学 
// 👍 3362 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int reverse(int x) {
        /*
        MAX=2147483647  MIN=-2147483648
        循环取余*10 ， * 10 前判断 reverse若超过MAX/10则溢出，reverse等于MAX/10时
        mod大于7则溢出，但是pop=8原数为8463847412溢出不能输入所以pop不可能大于7
         */
        int reverse = 0;
        while (x !=0) {
            int mod = x % 10;
            x /= 10;
            if (reverse > Integer.MAX_VALUE / 10) {
                return 0;
            }
            if (reverse < Integer.MIN_VALUE / 10) {
                return 0;
            }
            reverse = reverse * 10 + mod;
        }
        return reverse;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
