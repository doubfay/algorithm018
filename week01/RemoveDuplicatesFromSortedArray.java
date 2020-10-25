package com.algorithm018;

/**
 * 26. 删除排序数组中的重复项
 */
public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }

    public int removeDuplicates2(int[] nums) {
        int i = 0;
        for (int n : nums) {
            if (i == 0 || n > nums[i - 1]) {
                nums[i++] = n;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArray mv = new RemoveDuplicatesFromSortedArray();
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int length = mv.removeDuplicates(nums);
        System.out.println("length: " + length);
        for (int i = 0; i < length; i ++) {
            System.out.print(nums[i] + ", ");
        }
    }
}
