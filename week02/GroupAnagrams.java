import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://leetcode-cn.com/problems/group-anagrams/">49. 字母异位词分组</a>
 * 具体思路可以类比 @see <a href="https://leetcode-cn.com/problems/valid-anagram/">242. 有效的字母异位词</a>
 */
public class GroupAnagrams {
    /**
     * 1. 遍历 strs，对每个字符串进行排序，将排序后对字符串作为 key 存到 map 中
     * 如果能在 map 中找到对应排序后的 key，那么这个字符串就是对应 key 的字母异位词
     *
     * Time: O(nKlogK) n 是 strs 数组的长度，K 是最长字符串的长度
     * Space: O(nK)
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length <= 0) {
            return new ArrayList<>(0);
        }
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            // sort
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            // match map key
            String key = String.valueOf(chars);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            // matched, add str to list
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 2. 遍历 strs，对每个字符串做处理
     * 因为是固定的 26 个小写字母，使用数组作为简易的哈希表存储每个字母出现的次数
     * 字母异位词的每个字母出现次数是相同的，以此作为 key 存在 map 中
     *
     * Time: O(nK) n 是 strs 数组的长度，K 是最长字符串的长度
     * Space: O(n)
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        if (strs == null || strs.length <= 0) {
            return new ArrayList<>(0);
        }
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            // 使用数组存储每个字母出现的次数
            int[] anagram = new int[26];
            for (char c : str.toCharArray()) {
                anagram[c - 'a']++;
            }
            // 将数组转成字符串后，作为 key
            String key = Arrays.toString(anagram);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            // add str to list
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        GroupAnagrams groupAnagrams = new GroupAnagrams();
        System.out.println(groupAnagrams.groupAnagrams2(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }
}
