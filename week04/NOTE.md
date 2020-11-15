# 学习笔记

[TOC]

## 学习内容

* 深度优先搜索、广度优先搜索
* 贪心算法
* 二分查找

## 深度优先搜索 DFS、广度优先搜索 BFS

DFS、BFS 本质上是遍历，每个节点访问一次，且仅访问一次

根据访问的顺序不同，分为「深度优先」和「广度优先」

### 深度优先

类比树的先序遍历，先按某个分支一直往下遍历，直到找不到后续的节点后，再往上面一个节点的另一个分支遍历，以此类推

* 递归
* 非递归，手动维护一个栈

### 广度优先

层次遍历，遍历根节点后，再遍历和他相连的所有节点，使用 `队列 Queue` 存储每一层的节点

代码模板：
```java
class Node {
    private int val;
    private List<Node> children;

    Node(int val) {
        this.val = val;
        this.children = null;
    }
    
    // 一般在树中不需要这个 visited 来记录访问过的节点，在图中需要标记某个节点是否被访问过，因为图中可能会存在环
    private Set<Node> visited = new HashSet<Node>();

    private List<Integer> dfsResult = new ArrayList<Integer>();
    
    /**
     * 深度优先搜索，递归
     */
    public void dfs(Node root, Set<Node> visited) {
        // terminator
        if (root == null || visited.contains(root)) {
            return;
        }
        // current logic
        visited.add(root);
        dfsResult.add(root.val);
        // drill down
        for (Node node : root.children) {
            dfs(node, visited);
        }
        // reverse states if needed
    }

    /**
     * 深度优先搜索，非递归，使用栈
     */
    public List<Integer> dfsWithStack(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }
        Set<Node> visited = new HashSet<Node>();
        Deque<Node> stack = new ArrayDeque<Node>();
        
        stack.push(root);
        while (!stack.isEmpty) {
            Node node = stack.pop();
            if (visited.contains(node)) {
                continue;
            }
            // process
            visited.add(node);
            result.add(node.val);
            // add children to stack, from the last child to the first child
            for (int i = root.children.size() - 1; i > 0; i--) {
                stack.push(root.children.get(i));
            }
        }
    }

    /**
     * 层序遍历
     */
    public List<List<Integer>> bfs(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Set<Node> visited = new HashSet<Node>();
        Queue<Node> queue = new ArrayDeque<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<Integer>(levelSize);
            
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                if (visited.contains(node)) {
                    continue;
                }
                // process
                visited.add(node);
                level.add(node.val);
                // add children
                for (Node child : node.children) {
                    queue.offer(child);
                }
            }
            result.add(level);
        }
        return result;
    }
}
```

## 贪心算法

### 适用场景 
问题可以分解成子问题，且子问题的最优解，可以推出最终问题的最优解，那么此时贪心算法就是最优的解法

### 贪心算法和动态规划的区别
贪心算法：找到子问题的最优解后，直接去解决下个子问题，**不可回溯**

动态规划：会保存之前处理过的结果，**可以回溯**


## 二分查找

要满足以下条件，才可以使用二分查找：
* 目标函数单调（递增/递减）
* 有上下边界
* 可以直接通过索引访问

代码模板：
```java
class BinarySearch {
    public int binarySearch(int[] array, int target) {
        int left = 0, right = array.length - 1, mid;
        while (left <= right) {
            // avoid over flow
            mid = left + (right - left) / 2;
            if (array[mid] == target) {
                // find the number
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
```

### 进阶
使用二分查找在一个半有序数组中查找

可以套用普通二分查找的模板，只需在判断的条件中做修改；

例：[搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)

### 作业

使用二分查找，寻找一个半有序数组（部分递增） `[4, 5, 6, 7, 0, 1, 2]` 中间无序的地方

思路：     
用 `mid = (left + right) / 2` 位置的值和左、右边界的值做比较，正常情况是 `num[left] < num[mid] < num[right]`
* 如果 `num[left] > num[mid]` 说明无序的位置在左边，`right = mid` 继续查找
* 如果 `num[right] < num[mid]` 说明无序的位置在右边，`left = mid` 继续查找

结束条件：
找到最后，`left` 和 `right` 在相邻的两个位置，此时 `mid === left`（因为 `奇数 + 偶数 = 奇数`，`奇数 / 2` 舍去余数部分，结果是偏左边的）
`right` 即是无序的位置
```
index: 0  1  2  3  4  5  6
array:[4, 5, 6, 7, 0, 1, 2]

1. left = 0, right = 6, mid = 3
array[right] < array[mid]，右边无序

2. left = 3, right = 6, mid = 4
array[left] > array[mid]，左边无序

3. left = 3, right = 4, mid = 3
mid == left, array[right] < array[mid]
返回 right
```

```java
class Search {
    public int searchRotated(int[] array) {
        int left = 0, right = array.length - 1, mid;
        while (left < right) {
            mid = left + (right - left) / 2;
            // 此时 left 和 right 在相邻的位置
            if (mid == left && array[right] < array[mid]) {
                return right;
            }
            if (array[left] > array[mid]) {
                // 左边无序
                right = mid;
            } else if (array[right] < array[mid]) {
                // 右边无序
                left = mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Search search = new Search();
        System.out.println(search.searchRotated(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }
}
```


### 一些个人总结

这周在做题的时候，偶然间看到了 leetCode 中国站第一名，然后发现他之前也是极客时间算法训练营的学员，看了下他的作业，发现他写的更好，我就借鉴了他的写法，用 markdown 来写每个题目，也方便后续查看。
感觉他实在是太厉害了，leetCode 里面所有的题都做过了，而且还会保持每天继续做题，可见他在学习结束之后，依然保持了五毒神掌的做题习惯。

感觉有点惭愧，除了刚开始，因为工作上项目周期的原因，前两周工作不是很忙，就会在工作时间也做题，然后可以做到五毒神掌，也会复习之前的题目。
第三周的时候，工作上忙了起来，然后第三周的周末还因为个人原因，基本也没时间学习，只能匆匆看完视频，只做了两题作业。

这周因为工作时间也要完成工作内容，基本工作日也没有太多时间学习，基本每天做一题群里发的每日一题练习，就没啥时间复习以前的题目了。
感觉自己有点笨，大部分题目想不出基本的解法，也有点依赖题解，一些题目会习惯性想去看题解，怕自己的思路有问题。
然后还有一些题目，会涉及到一些数学知识，用了数学一些公式，解题会比较巧妙，这些基本都不会，有时候会看比较久去理解。
基本每天做一道题，会花比较长的时间去消化，然后也没时间去复习。有点焦虑。

但是看到了其他人那么优秀，我想继续坚持。
