
public class BinaryTree {

    private BinaryNode root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(Object x) {
        root = new BinaryNode(x);
    }

    // Returns true if the BinaryNode of this tree is null.
    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }

    public int nodeCount() { return BinaryNode.nodeCount(root); }

    public int height() {
        return BinaryNode.height(root);
    }

    public void setRootObj(Object x) throws BinaryTreeException {
        if (root == null) throw new BinaryTreeException("The tree is null.");
        else root.element = x;
    }

    public Object getRootObj() {
        if (root == null) return null;
        else return root.element;
    }

    public void setLeft(BinaryTree t) throws BinaryTreeException {
        if (root == null) throw new BinaryTreeException("The tree is null.");
        else root.left = t.root;
    }

    public BinaryTree getLeft() {
        if (root == null) return null;
        else {
            BinaryTree t = new BinaryTree();
            t.root = root.left;
            return t;
        }
    }

    public void setRight(BinaryTree t) throws BinaryTreeException {
        if (root == null) throw new BinaryTreeException("The tree is null.");
        else root.right = t.root;
    }

    public BinaryTree getRight()  {
        if (root == null) return null;
        else {
            BinaryTree t = new BinaryTree();
            t.root = root.right;
            return t;
      }
    }


    public static BinaryTree insert(BinaryTree t, Object x) throws BinaryTreeException {
        if (t.isEmpty()) {
            return new BinaryTree(x);
        } else {
            if (((Integer) t.getRootObj()).intValue() < ((Integer) x).intValue())
                t.setRight(insert(t.getRight(), x));
            else
                t.setLeft(insert(t.getLeft(), x));
            return t;
        }
    }

    public static void inOrder(BinaryTree t) throws BinaryTreeException {
        if (!t.isEmpty()) {
            inOrder((t.getLeft()));
            System.out.print(t.getRootObj() + " ");
            inOrder(t.getRight());
        }
    }

    public class BinaryTreeException extends Exception {

        public BinaryTreeException() {}

        public BinaryTreeException(String message) {
            super(message);
        }
    }
}
