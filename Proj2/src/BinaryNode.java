
public class BinaryNode {
    Object element;
    BinaryNode left, right;     // Package level protection.

    BinaryNode(Object e) {
        this(e, null, null);
    }

    BinaryNode(Object e, BinaryNode ln, BinaryNode rn) {
        element = e;
        left = ln;
        right = rn;
    }

    // Returns how many children a BinaryNode has.
    static int nodeCount(BinaryNode n) {
        if (n == null) return 0;
        else return 1 + nodeCount(n.left) + nodeCount(n.right);
    }

    // Returns the height of a given node (-1 if the node is empty).
    static int height(BinaryNode n) {
        if (n == null) return -1;
        else return 1 + Math.max(height(n.left), height(n.right));
    }

    // String representation of node is string represenation of its data.
    public String toString() {
        return element.toString();
    }
}
