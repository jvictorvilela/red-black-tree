package tree;

/**
 *
 * @author victor
 */
public class Node {
    static final boolean BLACK = false;
    static final boolean RED = true;
    private Node left;
    private Node right;
    private Node parent;
    private int key;
    private boolean color;
    boolean nil;
    
    public Node(Node left, Node right, Node parent, int key, boolean color, boolean nil) {
        if (nil) {
            this.left = null;
            this.right = null;
            this.parent = null;
            this.key = 0;
            this.color = BLACK;
            this.nil = true;
        } else {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.key = key;
            this.color = color;
            this.nil = false;
        }
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isNil() {
        return nil;
    }
    
    public boolean getColor() {
        return color;
    }
    
    public void setColor(boolean color) {
        this.color = color;
    }
    
    public String getColorName() {
        if (this.color) {
            return "RED";
        } else {
            return "BLACK";
        }
    }
    
}
