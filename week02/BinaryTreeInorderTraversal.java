import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @see <a href="https://leetcode-cn.com/problems/binary-tree-inorder-traversal/">94. 二叉树的中序遍历</a>
 */
public class BinaryTreeInorderTraversal {
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
     * 使用递归
     * Time: O(n) n 为树的节点数
     * Space: O(h) 取决于递归栈的深度，这里等于树的深度；一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }
        List<Integer> result = new ArrayList<Integer>();
        // 遍历
        traversal(root, result);
        return result;
    }

    public void traversal(TreeNode root, List<Integer> result) {
        // 如果节点为 null 结束递归
        if (root == null) {
            return;
        }
        // 先遍历左子树
        if (root.left != null) {
            traversal(root.left, result);
        }
        // 访问根节点
        result.add(root.val);
        // 最后遍历右子树
        if (root.right != null) {
            traversal(root.right, result);
        }
    }

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

    public static void main(String[] args) {
        BinaryTreeInorderTraversal traversal = new BinaryTreeInorderTraversal();
        TreeNode left = new TreeNode(2, new TreeNode(4), new TreeNode(5));
        TreeNode right = new TreeNode(3, null, new TreeNode(6));
        TreeNode root = new TreeNode(1, left, right);
        System.out.println(traversal.inorderTraversal3(root));
    }
}
