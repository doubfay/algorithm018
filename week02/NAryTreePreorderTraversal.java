import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/">589. N叉树的前序遍历</a>
 */
public class NAryTreePreorderTraversal {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 使用递归
     * Time: O(n) n 为树的节点数
     * Space: O(h) 取决于递归栈的深度，这里等于树的深度；一般情况是 O(log n)；最坏情况，树退化成链表，空间复杂度为 O(n)
     */
    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        traversal(root, result);
        return result;
    }

    public void traversal(Node root, List<Integer> result) {
        // 节点为空则结束递归
        if (root == null) {
            return;
        }
        // 前序遍历先存入根节点的值
        result.add(root.val);
        if (root.children != null) {
            // 存在子节点，则依次遍历子节点
            for (Node node : root.children) {
                traversal(node, result);
            }
        }
    }
}
