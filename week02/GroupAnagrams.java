import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://leetcode-cn.com/problems/group-anagrams/">49. 字母异位词分组</a>
 */
public class GroupAnagrams {
    /**
     * 遍历 strs，对每个字符串进行排序，将排序后对字符串作为 key 存到 map 中
     * Time: O(nKlogK) n 是 strs 数组的长度，K 是最长字符串的长度
     * Space:
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length <= 0) {
            return new ArrayList<>(0);
        }
        Map<String, List> map = new HashMap<>();
        for (String str : strs) {
            // sort
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            // match map key
            String key = String.valueOf(chars);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            // matched, add str to list
            map.get(key).add(str);
        }
        return new ArrayList(map.values());
    }
}
