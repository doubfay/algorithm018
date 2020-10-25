# 学习笔记

[toc]

## 数据结构与算法脑图

[链接](https://mubu.com/doc/pouvgOY3B0)

## 源码分析

以下基于 Java 8 的源码进行分析

### Queue
Queue 以接口的方式实现，具体的实现方式有多种

Queue 中提供的方法如下：
  <tr>
    <td></td>
    <td ALIGN=CENTER><em>Throws exception</em></td>
    <td ALIGN=CENTER><em>Returns special value</em></td>
  </tr>
  <tr>
    <td><b>Insert</b></td>
    <td>add(e)</td>
    <td>offer(e)</td>
  </tr>
  <tr>
    <td><b>Remove</b></td>
    <td>remove()</td>
    <td>poll()</td>
  </tr>
  <tr>
    <td><b>Examine</b></td>
    <td>element()</td>
    <td>peek()</td>
  </tr>
</table>

以双端队列 `ArrayDeque.java` 中的实现为例进行分析
队列的特性是 FIFO 先进先出，只在队列尾部入队，只在队列头部出队  
所以用双端队列实现 Queue 的时候，`add/offer` 操作都是用 `addLast` 来实现；`remove/poll/peek` 操作用 `pollFirst/peekFirst` 实现；
```java
// add 方法在队列的尾部新增数据，调用了 addLast 方法
public boolean add(E e) {
    addLast(e);
    return true;
}

public void addLast(E e) {
    // 抛出异常
    if (e == null)
        throw new NullPointerException();
    // 在 elements 数组的 tail 位置（尾部）增加元素
    elements[tail] = e;
    // 如果队列满了（数组中循环存储 tail + 1 和元素个数取余 = head），则扩容
    if ( (tail = (tail + 1) & (elements.length - 1)) == head)
        doubleCapacity();
}

public boolean offer(E e) {
    return offerLast(e);
}

public boolean offerLast(E e) {
    addLast(e);
    return true;
}

public E remove() {
    return removeFirst();
}

public E removeFirst() {
    E x = pollFirst();
    if (x == null)
        throw new NoSuchElementException();
    return x;
}

public E pollFirst() {
    int h = head;
    @SuppressWarnings("unchecked")
    // 取 elements 数组的 head 位置（头部）的元素
    E result = (E) elements[h];
    // Element is null if deque empty
    if (result == null)
        return null;
    // 清空 head 位置的元素
    elements[h] = null;     // Must null out slot
    // head 位置后移一位
    head = (h + 1) & (elements.length - 1);
    return result;
}

public E poll() {
    return pollFirst();
}

public E element() {
    return getFirst();
}

public E getFirst() {
    @SuppressWarnings("unchecked")
    // 取 elements 数组的 head 位置（头部）的元素
    E result = (E) elements[head];
    if (result == null)
        throw new NoSuchElementException();
    return result;
}

public E peek() {
    return peekFirst();
}

public E peekFirst() {
    // elements[head] is null if deque empty
    return (E) elements[head];
}
```

### Priority Queue

## 每日一题

### 题目

Day 1: [70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

Day 2: [66. 加一](https://leetcode-cn.com/problems/plus-one/)

Day 3: [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)、[925. 长按键入](https://leetcode-cn.com/problems/long-pressed-name/)

Day 4: [24. 两两交换链表中的节点](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)

Day 5: [21. 合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)、[234. 回文链表](https://leetcode-cn.com/problems/palindrome-linked-list/)

Day 6: [299. 猜数字游戏](https://leetcode-cn.com/problems/bulls-and-cows/)、[283. 移动零](https://leetcode-cn.com/problems/move-zeroes/)

Day 7: [641. 设计循环双端队列](https://leetcode-cn.com/problems/design-circular-deque/)

### 总结
1. 有重复操作的问题，都可以使用递归来解决

关于递归的使用，看了一篇博客写的不错：[三道题套路解决递归问题](https://lyl0724.github.io/2020/01/25/1/)

`70. 爬楼梯`、`24. 两两交换链表中的节点`、`21. 合并两个有序链表` 都可以分解成多个重复操作，都能使用递归来解决。

`70. 爬楼梯`：爬到第 n 级台阶，可以分解成爬到第 n-1 级台阶后再爬一阶 + 爬到第 n-2 级台阶后再爬两阶；对应重复的操作可以归纳为 **`f(n) = f(n-1) + f(n-2)`**

```java
class Solution {
    // 便于理解，单纯递归，会超时，有很多重复计算
    public int badFibonacci(int n) {
        if (n <= 2) {
            return n;
        }
        return badFibonacci(n-1) + badFibonacci(n-2);
    }
    
    // 优化后的递归，保存前两次计算后的值
    public int getFibonacci(int n, int a, int b) {
        if (n <= 0) {
            return a;
        }
        return getFibonacci(n-1, b, a+b);
    }
    
    // 调用
    public int climbStairs(int n) {
        return getFibonacci(n, 1, 1);
    }
}
```

`24. 两两交换链表中的节点`：两个节点互换，继续指向后续互换好的节点；这里重复的操作就是**两两节点互换**

`21. 合并两个有序链表`：比较两个链表节点中数值的大小，取到值小的那个节点，该节点在前面，其 next 指针指向后面得到的较小节点；这里的重复操作就是**找到值小的节点，排在前面**

2. 链表中使用 dummy 节点

dummy 节点是新建的一个节点，用于指向链表的头节点 head
> `dummy.next = head;`

使用了 dummy 节点后，第一个有效节点的插入和删除逻辑，就不需要单独处理

3. 长按键入，遍历
