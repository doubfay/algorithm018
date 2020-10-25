package com.algorithm018;

/**
 * 21 合并两个有序链表
 */
public class MergeTwoSortedLists {
    /**
     * 迭代
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode merged = new ListNode();
        ListNode curr = merged;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = l1 != null ? l1 : l2;
        return merged.next;
    }

    /**
     * 递归
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        // terminator
        if (l1 == null || l2 == null) {
            return l1 != null ? l1 : l2;
        }
        // current logic
        if (l1.val <= l2.val) {
            l1.next = this.mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = this.mergeTwoLists2(l1, l2.next);
            return l2;
        }
        // drill down
        // reverse status
    }
}
