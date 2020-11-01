import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://leetcode-cn.com/problems/two-sum/">1. 两数之和</a>
 */
public class TwoSum {
    /**
     * 使用哈希表存储 nums 中的数字，如果 target - num 在哈希表中出现，则返回
     * Time: 最坏情况 O(n)，最好情况前面两个就找到 O(1)
     * Space: 最坏情况 O(n)，最好情况 O(1)
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[0];
        }
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            // 判断是否存在 target - nums[i]，存在则说明找到了对应的数，返回相应的下标
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{hashMap.get(target - nums[i]), i};
            }
            // 将对应的数字作为 key，下标作为 value 存入，方便匹配，和获取下标
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int[] sum = twoSum.twoSum(new int[]{2, 7, 11, 15}, 9);
        for (int i : sum) {
            System.out.println(i);
        }
    }
}
