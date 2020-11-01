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

### 树的遍历

* 先序遍历：根 - 左 - 右
* 中序遍历：左 - 根 - 右
* 后序遍历：左 - 右 - 根

### 树的遍历为什么使用递归的方式

树的结构不太方便遍历，只能通过递归的方式，对树的子节点做相同的访问操作，这里的访问操作就是一个重复的操作，且树的每个节点结构都是一样的，天然就适合使用递归的方式；

除了递归之外，还可以使用迭代的方式对树进行遍历，比如：
* 使用栈来进行深度优先搜索；
* 使用队列来进行广度优先搜索；

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
