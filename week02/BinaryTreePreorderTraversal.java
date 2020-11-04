import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @see <a href="https://leetcode-cn.com/problems/binary-tree-preorder-traversal/">144. 二叉树的前序遍历</a>
 */
public class BinaryTreePreorderTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 递归遍历
     * Time: O(n) n 为树的节点数
     * Space: O(h) 取决于递归栈的深度，这里等于树的深度；一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        // 前序遍历
        traversal(root, result);
        return result;
    }
    public void traversal(TreeNode root, List<Integer> result) {
        // 节点为空，结束递归
        if (root == null) {
            return;
        }
        // 先访问根节点
        result.add(root.val);
        // 遍历左子树
        traversal(root.left, result);
        // 遍历右子树
        traversal(root.right, result);
    }

    /**
     * 迭代，使用栈
     * Time: O(n) n 为树的节点数
     * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        // 将根节点存入栈中
        stack.push(root);
        // 只要栈不为空，说明遍历还没有结束
        while (!stack.isEmpty()) {
            // 获取栈顶元素
            TreeNode node = stack.pop();
            // 当前节点视为根节点，先访问根节点
            result.add(node.val);
            // 先将右子节点存到栈中，再存左子节点，访问时，就是先左子节点，再右子节点
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /**
     * 迭代，使用栈
     * Time: O(n) n 为树的节点数
     * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorderTraversal3(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        // 栈，只存右子节点
        Deque<TreeNode> rightStack = new ArrayDeque<TreeNode>();
        // 可以用一个指针 curr 指向 root，不会改变树的结构；也可以直接操作 root，会节省一点空间
        TreeNode curr = root;
        while (curr != null) {
            // 访问当前节点，当前节点视为根节点
            result.add(curr.val);
            // 存在右子节点就存到栈中
            if (curr.right != null) {
                rightStack.push(curr.right);
            }
            // 指针指向左子节点
            curr = curr.left;
            // 如果左子节点不存在，且栈中有右子节点，则将指针指向右子节点
            if (curr == null && !rightStack.isEmpty()) {
                curr = rightStack.pop();
            }
        }
        return result;
    }

    /**
     * 迭代，使用栈
     * Time: O(n) n 为树的节点数
     * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorderTraversal4(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                result.add(root.val);
                // 栈中存入每个的根节点，此时 root 不为空
                stack.push(root);
                // 遍历左子节点
                root = root.left;
            }
            // 取出栈中的元素，此时去遍历他的右子节点
            root = stack.pop();
            root = root.right;
        }
        return result;
    }

    /**
     * 迭代，使用栈
     * Time: O(n) n 为树的节点数
     * Space: O(h) h 为树的深度，一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorderTraversal5(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            // 节点为空，则取栈中的元素
            if (root == null) {
                root = stack.pop();
            }
            result.add(root.val);
            // 在栈中存右子树，和 preorderTraversal3 类似
            if (root.right != null) {
                stack.push(root.right);
            }
            root = root.left;
        }
        return result;
    }
}
