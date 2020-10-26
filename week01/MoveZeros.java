import java.util.LinkedList;
import java.util.Queue;

/**
 * 283 移动零
 */
public class MoveZeros {

    public void moveZeroes(int[] nums) {
        int len = nums.length;
        Queue<Integer> zeroIndex = new LinkedList<Integer>();
        int zeroCount = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] == 0) {
                zeroIndex.add(i);
                zeroCount++;
            } else {
                if (!zeroIndex.isEmpty()) {
                    nums[zeroIndex.poll()] = nums[i];
                    zeroIndex.add(i);
                }
            }
        }
        while (zeroCount > 0) {
            nums[len - zeroCount] = 0;
            zeroCount--;
        }
    }

    public void moveZeroes2(int[] nums) {
        int len = nums.length;
        int j = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] != 0) {
                if (i > j) {
                    nums[j] = nums[i];
                    nums[i] = 0;
                }
                j++;
            }
        }
    }

    public static void main(String[] args) {
        MoveZeros solution = new MoveZeros();
        solution.moveZeroes(new int[]{0, 1, 0, 3, 12});
    }
}
