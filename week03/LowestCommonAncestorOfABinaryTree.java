/**
 * @see <a href="https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/">236. 二叉树的最近公共祖先</a>
 *
 * root 是最近公共祖先的情况有以下几种：
 * 1. p、q 分别在 root 的左右两个子树中
 * 2. p or q = root，另一个节点在 root 的其中一个子树中（左/右）
 *
 * 那么，二叉树的最近公共祖先，分解成子问题：
 * 1. 分别获取到左右子树的最近公共祖先情况
 * 2. 判断 p q 如果是分布在两个子树中，则返回 root
 * 3. 判断 p q 如果是在其中一个子树中，则返回该子树的最近公共祖先
 */
public class LowestCommonAncestorOfABinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }

        TreeNode(int x, TreeNode left, TreeNode right) {
            this.val = x;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 递归
     * Time: O(n) n 为二叉树的所有节点数，遍历时，每个节点都会被访问，且只访问一次
     * Space: 树的深度，一般情况下为 O(log n)，最坏情况下退化成链表 O(n)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // terminator
        if (root == null || root == p || root == q) {
            // 当节点为空，说明没找到对应的节点，则返回 null；
            // 当节点等于 p 或 q，说明找到了对应的节点，将当前节点返回；
            return root;
        }
        // current logic
        // drill down
        // 分别获取到左右子树的最近公共祖先情况
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 如果 p q 分布在左右两个子树，此时 root 是最近公共祖先
        if (left != null && right != null) {
            return root;
        }
        // p q 在其中一个子树中，则返回在该子树中取到的最近公共祖先
        // 这里还包含了一种情况，即在左右子树中都没找到，则返回的是 null
        return left == null ? right : left;
        // reverse states
    }
}
