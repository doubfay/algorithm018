import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/">105. 从前序与中序遍历序列构造二叉树</a>
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }
    }

    private Map<Integer, Integer> inMap;

    /**
     * 递归
     * Time: O(n) n 为树的节点数
     * Space: O(n) map 中的额外空间，以及树的深度 h，h <= n
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        inMap = new HashMap<Integer, Integer>(inorder.length);
        // 将中序遍历的数组存到 map 中，方便获取对应节点的下标
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length, inorder, 0, inorder.length);
    }
    public TreeNode build(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        // terminator
        if (preStart == preEnd) {
            // 终止条件：子树长度是 0，preStart = preEnd
            return null;
        }
        // current logic
        // 先序遍历，开始下标中的第一个节点就是根节点
        TreeNode root = new TreeNode(preorder[preStart]);
        // 获取根节点在中序遍历中的下标位置，则这个下标的左边是左子树，右边是右子树
        int inIndex = inMap.get(preorder[preStart]);
        // 左子树的节点数
        int leftLen = inIndex - inStart;
        // drill down
        // 组装左右子树
        // 左子树：前序：根节点后面一个 -> 根节点后面一个 + 左子树的节点数；中序：开始的位置 -> 开始的位置 + 左子树的节点数；
        root.left = build(preorder, preStart + 1, preStart + 1 + leftLen, inorder, inStart, inStart + leftLen);
        // 右子树：前序：左子树结束的位置（根节点后面一个 + 左子树的节点数）-> 结束下标的位置；中序：根节点在中序遍历的下标位置后面一位 -> 结束下标的位置
        root.right = build(preorder, preStart + leftLen + 1, preEnd, inorder, inIndex + 1, inEnd);
        // reverse states if needed
        return root;
    }
}
