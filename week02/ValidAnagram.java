import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://leetcode-cn.com/problems/valid-anagram/">242. 有效的字母异位词</a>
 * 字母异位词：字母相同，但排列不同的字符串
 */
public class ValidAnagram {
    /**
     * 1. 对两个字符串进行排序，再比较是否相等
     * Time: O(nlogn)
     * Space: O(n)
     */
    public boolean isAnagram1(String s, String t) {
        // validate
        if (s == null) {
            return t == null;
        }
        if (t == null || s.length() != t.length()) {
            return false;
        }
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        // sort
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        // compare
        return Arrays.equals(sArray, tArray);
    }

    /**
     * 2. 使用 map，存储字符串中的每个字符，然后进行比较
     * Time: O(n)
     * Space: O(1)
     */
    public boolean isAnagram2(String s, String t) {
        // validate
        if (s == null) {
            return t == null;
        }
        if (t == null || s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        // 将 s 的每一位都存到 map 中
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // 遍历 t，看是否存在对应的字符
        for (char c : t.toCharArray()) {
            if (map.getOrDefault(c, 0) <= 0) {
                return false;
            }
            map.put(c, map.get(c) - 1);
        }
        return true;
    }

    /**
     * 3. 因为题目中比较的是小写字母，可以使用数组来作为一个简易的哈希表
     * Time: O(n)
     * Space: O(1)
     */
    public boolean isAnagram3(String s, String t) {
        // validate
        if (s == null) {
            return t == null;
        }
        if (t == null || s.length() != t.length()) {
            return false;
        }
        // 使用数组作为简易的哈希表，用来存储已经出现过的字母
        int[] anagram = new int[26];
        for (int i = 0; i < s.length(); i++) {
            // s 中出现过的就 + 1
            anagram[s.charAt(i) - 'a']++;
            // t 中出现过的就 - 1
            anagram[t.charAt(i) - 'a']--;
        }
        // 数组中如果有不为 0 的数字，说明没有匹配上
        for (int num : anagram) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 4. 同 3
     * Time: O(n)
     * Space: O(1)
     */
    public boolean isAnagram4(String s, String t) {
        // validate
        if (s == null) {
            return t == null;
        }
        if (t == null || s.length() != t.length()) {
            return false;
        }
        // 使用数组作为简易的哈希表，用来存储已经出现过的字母
        int[] anagram = new int[26];
        for (int i = 0; i < s.length(); i++) {
            // s 中出现过的就 + 1
            anagram[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            // 在查找 t 中是否有相同字母的时候，直接判断 - 1 之后的值是否小于 0，小于 0 说明没有不是字母异位词
            if (--anagram[t.charAt(i) - 'a'] < 0){
                return false;
            }
        }
        return true;
    }
}
