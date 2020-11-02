# 学习笔记

##  本周学习内容
* set
* map
* 树、二叉树、二叉搜索树
* 堆、二叉堆
* 图

## 哈希表、set

### set

set 中存储不重复的元素，Java 中底层使用 HashMap 实现，只存入到对应的 key，在 value 中存入一个无意义的 dummy 值；

```java
public class HashSet<E> {
    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();
    
    public HashSet() {
        map = new HashMap<>();
    }
    
    // 插入值
    public boolean add(E e) {
        // 在 key 中存入对应的值，在 value 中存入一个无意义的 dummy 值
        return map.put(e, PRESENT)==null;
    }
}
```

### 哈希表

哈希表存储的是 `<key, value>` 形式的键-值对，且 key 不重复；

哈希表在生活中有比较多的应用，比如用户信息存储，把用户名或身份证号作为 key，用户的其他信息存在对应的下标之中；

map 底层使用数组实现，通过`哈希函数`将 key 映射到对应的数组下标 index 中，然后将值存入到对应的位置；  
选用合适的`哈希函数`，可以减少`哈希碰撞`——不同的 key 值可能会映射到相同的 index 中；  
如果发生了`哈希碰撞`，工业级使用比较多的方法，就是在发生碰撞的下标下，使用链表存储多个元素，即`拉链式解决冲突法`，Java 中在 Java 8 之后如果链表长度大于等于 8，就改成使用红黑树来存储；  
一般在 map 中查找元素的时间复杂是 O(1)，如果`哈希函数`设计的不好，`哈希碰撞`频繁发生，那么在这个下标中查找元素，就要遍历链表或者红黑树，此时查找元素的时间复杂度就会退化，链表查找元素时间复杂度是 O(n)，红黑树查找元素时间复杂度是 O(log n)；

### HashMap 源码分析

以下基于 OpenJDK 11 进行分析

```java
public class HashMap<K,V> {
    // 在 map 中存入值
    public V put(K key, V value) {
        // hash
        return putVal(hash(key), key, value, false, true);
    }
    
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
}
```

## 树、二叉树、二叉搜索树

**链表是特殊的树**
**树是特殊的图**

### 二叉树

二叉树节点的代码实现：

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode() {}
    
    TreeNode(int val) {
        this.val = val;
    }
    
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

### 二叉搜索树

1. 空树
2. 根节点的值大于左子树上<span color="orange">**所有节点**</span>的值，根节点的值小于右子树上<span color="orange">**所有节点**</span>的值；且所有的子树都满足这个特点，都是二叉搜索树；

**二叉搜索树的中序遍历的结果，是一个升序序列**

### 树的递归遍历

* 先序遍历：根 - 左 - 右
```java
public void preorder(TreeNode root, List<Integer> result) {
    if (root == null) {
        return;
    }
    result.add(root.val);
    preorder(root.left);
    preorder(root.right);
}
```
* 中序遍历：左 - 根 - 右
```java
public void inorder(TreeNode root, List<Integer> result) {
    if (root == null) {
        return;
    }
    inorder(root.left);
    result.add(root.val);
    inorder(root.right);
}
```
* 后序遍历：左 - 右 - 根
```java
public void postorder(TreeNode root, List<Integer> result) {
    if (root == null) {
        return;
    }
    postorder(root.left);
    postorder(root.right);
    result.add(root.val);
}
```

### 树的遍历为什么使用递归的方式

树的结构不太方便遍历，只能通过递归的方式，对树的子节点做相同的访问操作，这里的访问操作就是一个重复的操作，且树的每个节点结构都是一样的，天然就适合使用递归的方式；

### 树的迭代遍历

除了递归之外，还可以使用迭代的方式对树进行遍历，比如：
* 使用栈来进行先序/中序/后续/深度优先遍历；
* 使用队列来进行广度优先遍历；

先序遍历：根 - 左 - 右
```java

```

中序遍历：左 - 根 - 右
```java
/**
 * 迭代，使用栈，手动模拟递归
 * Time: O(n) n 为树的节点数
 * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
 */
public List<Integer> inorderTraversal2(TreeNode root) {
    if (root == null) {
        return new ArrayList<>(0);
    }
    List<Integer> result = new ArrayList<Integer>();
    Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
    // 指针指向根节点
    TreeNode curr = root;
    // 当节点不为空，或栈中元素不为空时，说明还有节点没有访问，继续遍历
    while (curr != null || !stack.isEmpty()) {
        // 将根节点和左子节点存入栈中，循环中，只要能一直找到左子节点，就一直存入
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        // 当前节点为空，说明上一个遍历到的节点是这一部分遍历的最后一个节点，弹出栈顶元素，指针指向该节点
        curr = stack.pop();
        // 访问节点
        result.add(curr.val);
        // 此时该节点和它的左子节点，都已经被访问，指针指向右子节点
        curr = curr.right;
    }
    return result;
}

/**
 * 迭代，使用栈，手动模拟递归
 * Time: O(n) n 为树的节点数
 * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
 */
public List<Integer> inorderTraversal3(TreeNode root) {
    if (root == null) {
        return new ArrayList<>(0);
    }
    List<Integer> result = new ArrayList<Integer>();
    Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
    // 指针指向根节点
    TreeNode curr = root;
    // 当节点不为空，或栈中元素不为空时，说明还有节点没有访问，继续遍历
    while (curr != null || !stack.isEmpty()) {
        if (curr != null) {
            // 如果当前节点不为空时，当前节点可以视为根节点，将根节点先存入栈中，待左子树遍历完后再访问
            stack.push(curr);
            // 指针指向左子节点，循环中，只要一直能找到左子节点，就会一直将节点存入栈中
            curr = curr.left;
        } else {
            // 如果当前节点为空，说明上一个遍历到的节点是这一部分遍历的最后一个节点，弹出栈顶元素，指针指向该节点
            curr = stack.pop();
            // 访问节点
            result.add(curr.val);
            // 此时该节点和它的左子节点，都已经被访问，指针指向右子节点
            curr = curr.right;
        }
    }
    return result;
}
```

后序遍历：左 - 右 - 根
```java

```

## 堆、二叉堆

### 堆

堆是一个抽象的数据结构，可以在 O(1) 时间复杂度内快速查找最大值/最小值，本身可以有多种实现；  
根元素最大的堆叫`大顶堆/大根堆`，跟元素最小的堆叫`小顶堆/小根堆`；

### 二叉堆

二叉堆是最简单的堆的实现方式，但并不是最优的实现，其插入元素/删除元素的时间复杂度都是 O(log n)，相比于其他的实现，时间复杂度较高；

Java 中可以直接使用优先队列 priority queue

二叉堆是一棵`完全二叉树`，底层可以用数组来实现；且树的每个节点的值都 >= 其自节点的值；

* 下标为 i 的节点的左子节点下标：`i * 2 + 1`
* 下标为 i 的节点的右子节点下标：`i * 2 + 2`
* 下标为 i 的节点的父节点下标：`(i - 1) / 2`

## 图

由`点`和`边`组成，`边`可以有向，可以无向，也可以有权，可以无权；

图中可能存在环，而树中不存在环，这是两者最大的区别；
所以在遍历树的时候，访问的节点不会重复；而在对图进行遍历的时候，访问的节点可能重复，要避免之前访问的点，要**使用 set 去存储已经访问过的节点**
