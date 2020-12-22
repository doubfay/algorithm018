# 学习笔记

## 学习内容

* 高级动态规划
* 字符串算法
  * 基础
  * 高级
  * 匹配算法

### 高级动态规划

#### 动态规划复习

思想：分治 + 最优子结构（中途可以淘汰次优解）

步骤：
* 寻找最近重复子问题
* 定义 DP 状态数组
* 定义 DP 递推方程

#### 高级动态规划

在：定义 DP 状态数组、定义 DP 递推方程，更加复杂

如果一维状态数组无法解决，可以尝试增加维度

和字符串结合时，例如两个字符串匹配，定义二维的状态数组 `DP[i][j]`，i 表示第一个字符串的 0 ~ i 位，j 表示第二个字符串的 0 ~ j 位；


##### 一些规律

在做 [120. 三角形最小路径和](https://leetcode-cn.com/problems/triangle/) 时，发现了一些规律：
在将 DP 数组从二维优化到一维空间时，要**逆过来**递推才是正确的

比如：定义 `dp[i][j]` 表示三角形第 i 行第 j 列（下标从 0 开始）到最后一行的最小路径和（0 <= j <= i)

**自顶向下**

如果是从上到下，顺着递推 DP 方程，dp 数组是和 i - 1、j - 1，这种 - 1 的格子有关（只能从上一行，或者左上的格子过来），具体方程如下：
* 当 j = 0，`dp[i][0] = dp[i - 1][0] + triangle[i][0]`，第一列，只能从上面一个格子过来
* 当 j = i，`dp[i][i] = dp[i - 1][i - 1] + triangle[i][i]`，对角线，只能从左上的格子过来
* 其他，`dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle[i][j]`，上一个格子和左上格子取较小值

将 dp 数组优化到一维（去掉第一维「行」相关的维度）后的递推方程：
* 当 j = 0，`dp[0] = dp[0] + triangle[i][0]`
* 当 j = i，`dp[i] = dp[i - 1] + triangle[i][i]`
* 其他，`dp[j] = Math.min(dp[j - 1], dp[j]) + triangle[i][j]`

但是能这么优化的前提是，每一列要**从后往前**递推，这样才不会影响到 - 1 的格子

具体分析如下：当在计算 (i, j) 这个位置时
* 如果是从前往后递推每一列，此时已经算完了 j - 1 位置的状态，从 dp[0] ~ dp[j - 1] 都是第 i 行的状态，dp[j] ~ dp[i] 是第 i - 1 行的，此时再计算 dp[j] 就获取不到上一行前一格 (i-1, j-1) 的路径和；
* 如果是从后往前递推每一列，此时已经算完了 j + 1 位置的状态，从 dp[j + 1] ~ dp[i] 都是第 i 行的，dp[0] ~ dp[j] 都还是第 i - 1 行的，此时计算 dp[j] 获取到的是正确的状态；

```java
/**
 * Time: O(n^2)
 * Space: O(n)
 */
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            // 从后往前递推每一列
            dp[i] = dp[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + triangle.get(i).get(j);
            }
            dp[0] += triangle.get(i).get(0);
        }
        // 取到最后一行的最小值，才是对应的最小路径和
        int result = dp[0];
        for (int i = 1; i < n; ++i) {
            result = Math.min(result, dp[i]);
        }
        return result;
    }
}
```

**自底向上**

如果是从下往上，递推 DP 方程，dp 数组反过来是和 i + 1、j + 1，这种 + 1 的格子有关（只能从下一行，或者下一行的后一格过来），此时有个好处是不需要额外处理边界条件，因为每一行的列数是不断变少的，具体方程如下：
* `dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle[i][j]`，从当前格，可以到下一个，或右下格子，取两者的较小值

同理，将 dp 数组优化到一维后的递推方程：
* `dp[j] = Math.min(dp[j], dp[j + 1]) + triangle[i][j]`

同理可得，此时要使每一列的计算的状态正确，每一列要**从前往后**递推，这样取到的 + 1 的格子的状态才是对的

```java
/**
 * Time: O(n^2)
 * Space: O(n)
 */
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            // 这里必须从前往后递推每一列
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
```

也就是说，在将二维的 DP 状态数组优化到一维之后，要想每一步的递推获取到正确的状态，**「递推的方向」要和「递推方程中有关联的方向」相反**，方程是和 - 1 的格子有关，就从后往前递推，方程是和 + 1 的格子有关，就从前往后递推；



### 字符串算法

#### 基础

Java 中字符串是「不可变的」

字符串 == 比较的是引用，即内存地址；
字符串 `equals` 比较的是值；

* 基础操作
* 回文
* 异位词

#### 高级

字符串 + DP / 递归

#### 匹配算法

* 暴力
* Rabin-Karp 算法
* KMP 算法

其中 Rabin-Karp 算法和 KMP 算法，都是基于暴力的匹配算法，做了优化

##### Rabin-Karp 算法

* 匹配字符串 pattern，长度 M，计算出其 Hash 值 hash_pattern
* 目标字符串 target，长度 N

匹配时：
1. 计算 target 长度为 M 的子串的 Hash 值 hash_sub
2. 比较 hash_sub 和 hash_pattern 的值是否相等
   2. 1. 不相等，说明不匹配
   2. 2. 相等，则再使用朴素比较方法，对 target 的子串和 pattern 逐个字符进行比较
3. 如果上述两步，没有找到匹配的串，那么 target 的匹配子串再往后面移动一位

有点类似布隆过滤器

在计算 Hash 值的时候，不能使用系统自带的 Hash 函数，其时间复杂度是 O(M)；

可以自己设计 Hash 函数，将字符串当作是一个 256 进制的数，算出对应的十进制值，再模一个比较大的素数（取余 %，目的是不让计算出的十进制数太大），这样可以在 O(1) 的时间复杂度内求出 Hash 值；

##### KMP 算法

Rabin-Karp 算法在匹配时，还是逐个字符、逐个字符往后移动匹配；

KMP 算法在匹配时，不是只移动一个字符，会根据匹配字符串 pattern 中的前缀、后缀，可能在匹配过程中移动多个字符（避免重复匹配）；
