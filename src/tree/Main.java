package tree;

/**
 *
 * @author victor
 */
public class Main {
    public static void main(String[] args) {
        RBTree tree = new RBTree(30);
        tree.insert(15);
        tree.insert(70);
        tree.insert(13);
        tree.insert(30);
        tree.insert(10);
        tree.insert(50);

        tree.printTree(tree.getRoot());
    }
}
