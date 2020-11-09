import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @see <a href="https://leetcode-cn.com/problems/permutations/">46. 全排列</a>
 * 一共有 n 个位置，第一个位置可以放 n 种，第二个位置放 n - 1 种，以此类推
 * 后面一个位置不能放前面位置放过的元素（互斥，避免重复）
 */
public class Permutations {
    private List<List<Integer>> result;

    public List<List<Integer>> permute(int[] nums) {
        result = new ArrayList<>();
        // 这里用 linkedList 方便元素的 add/remove 操作
        doPermute(0, nums, new LinkedList<>());
        return result;
    }
    /**
     * 递归
     * 交换 nums 数组中的元素，使用过的元素放在数组的左边，没有使用过的在右边
     * count: 已经排列好的个数
     *
     * Time: O(n!)
     * Space: O(n) 栈的深度
     */
    public void doPermute(int count, int[] nums, List<Integer> list) {
        // terminator 当已经排列好的元素个数和给定的数列 nums 的长度相等，说明当前排列完成，结束递归
        if (count == nums.length) {
            // 这里用 new ArrayList 是为了 add 数值，而不是引用
            result.add(new ArrayList<>(list));
            return;
        }
        // 第 count（从 0 开始）个位置，有 n - count 种排列方式，从没排列过的元素中取，下标从 count 开始
        for (int i = count; i < nums.length; i++) {
            // current logic
            list.add(nums[i]);
            // 如果 i 和 count 不相等，交换两者的元素，将排列过的元素放在左边，没排列过的放在右边
            if (i != count) {
                swap(nums, i, count);
            }
            // drill down
            doPermute(count + 1, nums, list);
            // reverse states 回溯时需要和上面做对称的操作
            if (i != count) {
                swap(nums, i, count);
            }
            // 添加时是加的第 count 个，删除时也删除第 count 个
            list.remove(count);
        }
    }
    /**
     * 元素交换
     */
    public void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    /**
     * 递归
     * 用一个 boolean 状态数组标记某个元素是否已经使用过
     */
    public List<List<Integer>> permute2(int[] nums) {
        result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        // 这里用 linkedList 方便元素的 add/remove 操作
        doPermute2(0, nums, used, new LinkedList<>());
        return result;
    }
    private void doPermute2(int count, int[] nums, boolean[] used, List<Integer> list) {
        // terminator
        if (count == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }
        for(int i = 0; i < nums.length; i++) {
            // current logic
            // 当前元素没有使用过，则可以使用
            if (!used[i]) {
                list.add(nums[i]);
                // 修改使用标记
                used[i] = true;
                // drill down
                doPermute2(count + 1, nums, used, list);
                // reverse states 回溯时需要和上面做对称的操作
                used[i] = false;
                list.remove(count);
            }
        }
    }

    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        System.out.println(permutations.permute2(new int[]{1, 2, 3}));
    }
}
