/**
 * Created by Alex Mancheno on 4/2/17.
 */

 //Node class used for LargeNumbers
public class Node {
    private int data;
    private Node next;

    public Node(int e) {
        data = e;
        next = null;
    }

    public int getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }

    public String toString() { return Integer.toString(data); }
}
