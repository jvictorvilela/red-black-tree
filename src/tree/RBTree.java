package tree;

/**
 *
 * @author victor
 */
public class RBTree {
    
    // colors
    static final boolean BLACK = false;
    static final boolean RED = true;
    
    private Node nil = new Node(null, null, null, 0, BLACK, true); // Nó externo
    private Node root;
    
    public RBTree() {
        this.root = nil;
    }
    
    public RBTree(int key) {
        Node node = new Node(nil, nil, nil, key, BLACK, false);
        this.root = node;
    }
    
    
    // Rotações
    
    private void leftRotate(Node x) {
        System.out.println("Rotação a esquerda");
        if (!x.getRight().isNil()) {
            Node y = x.getRight();
            x.setRight(y.getLeft());
            if (!y.getLeft().isNil()) {
                y.getLeft().setParent(x);
            }
            y.setParent(x.getParent());
            if (x.getParent().isNil()) {
                this.root = y;
            } else if (x.getKey() == x.getParent().getLeft().getKey()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
            y.setLeft(x);
            x.setParent(y);
        }
    }
    
    private void rightRotate(Node x) {
        System.out.println("Rotação a direita");
        if (!x.getLeft().isNil()) {
            Node y = x.getLeft();
            x.setLeft(y.getRight());
            if (!y.getRight().isNil()) {
                y.getRight().setParent(x);
            }
            y.setParent(x.getParent());
            if (x.getParent().isNil()) {
                this.root = y;
            } else if (x.getKey() == x.getParent().getRight().getKey()) {
                x.getParent().setRight(y);
            } else {
                x.getParent().setLeft(y);
            }
            y.setRight(x);
            x.setParent(y);
        }
    }
    
    public void insert(int x) {
        Node node = new Node(this.nil, this.nil, this.nil, x, RED, false);
        insertNode(node);
    }
    
    private void insertNode(Node node) {
        Node x = this.root;
        Node y = this.nil;
        
        while (!x.isNil()) {
            y = x;
            if (node.getKey() < x.getKey()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        node.setParent(y);
        if (y == this.nil) {
            this.root = node;
        } else if (node.getKey() < y.getKey()) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }
        node.setColor(RED);
        
        //
        System.out.println("Nó "+node.getKey()+" inserido");
        //
        insertFixup(node);
    }
    
    private void insertFixup(Node node) {
        Node y;
        while (node.getParent().getColor() == RED) {
            if (node.getParent().getKey() == node.getParent().getParent().getLeft().getKey()) {
                y = node.getParent().getParent().getRight();
                if (y.getColor() == RED) { // caso 01
                    node.getParent().setColor(BLACK);
                    y.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.getKey() == node.getParent().getRight().getKey()) {
                        node = node.getParent();
                        leftRotate(node);   
                    }
                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    rightRotate(node.getParent().getParent());
                }
            } else { //
                y = node.getParent().getParent().getLeft();
                if (y.getColor() == RED) { // caso 01
                    node.getParent().setColor(BLACK);
                    y.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.getKey() == node.getParent().getLeft().getKey()) {
                        node = node.getParent();
                        rightRotate(node);  // 
                    }
                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    leftRotate(node.getParent().getParent());
                }
            }
        }
        this.root.setColor(BLACK);
    }
    
    private void transplant(Node u, Node v) { // substitui u por v
        if (u.getParent().isNil()) {
            this.root = v;
        } else if (u.getKey() == u.getParent().getLeft().getKey()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }
    
    private void deleteNode(Node node) {
        Node y = node;
        Boolean yColor = y.getColor();
        Node x;
        
        if (node.getLeft().isNil()) {
            x = node.getRight();
            transplant(node, node.getRight());
        } else if (node.getRight().isNil()) {
            x = node.getLeft();
            transplant(node, node.getLeft());
        } else {
            y = getLessNode(node.getRight());
            yColor = y.getColor();
            x = y.getRight();
            if (y.getParent().getKey() == node.getKey()) {
                x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(node.getRight());
                y.getRight().setParent(y);
            }
            transplant(node, y);
            y.setLeft(node.getLeft());
            y.getLeft().setParent(y);
            y.setColor(node.getColor());
        }
        if (yColor == BLACK) {
            deleteFixup(x);
        }
    }
    
    public void delete(int x) {
        delete(this.root, x);
    }
    
    private void delete(Node node, int x) {
        if (node.isNil()) {
            return;
        }
        if (x == node.getKey()) {
            deleteNode(node);
            return;
        }
        delete(node.getLeft(), x);
        delete(node.getRight(), x);
    }
    
    private void deleteFixup(Node x) {
        Node w;
        while (!x.getParent().isNil() && x.getColor() == BLACK) { // while (x != T.raiz and x.cor == PRETO)
            if (x.getKey() == x.getParent().getLeft().getKey()) {
                w = x.getParent().getRight();
                if (w.getColor() == RED) {
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                
                if (w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK) {
                    w.setColor(RED);
                    x = x.getParent();
                } else if (w.getRight().getColor() == BLACK) {
                    w.getLeft().setColor(BLACK);
                    w.setColor(RED);
                    rightRotate(w);
                    w = x.getParent().getRight();
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(BLACK);
                }
                w.getRight().setColor(BLACK);
                leftRotate(x.getParent());
                x = this.root;
            } else {
                w = x.getParent().getLeft();
                if (w.getColor() == RED) {
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                
                if (w.getRight().getColor() == BLACK && w.getLeft().getColor() == BLACK) {
                    w.setColor(RED);
                    x = x.getParent();
                } else if (w.getLeft().getColor() == BLACK) {
                    w.getRight().setColor(BLACK);
                    w.setColor(RED);
                    leftRotate(w);
                    w = x.getParent().getLeft();
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(BLACK);
                }
                w.getLeft().setColor(BLACK);
                rightRotate(x.getParent());
                x = this.root;
            }
        }
    }
    
    private Node getLessNode(Node node) { // Método recursivo que retorna o menor nó da sub-árvore.
        if (node.getLeft().isNil() == true) {
            return node;
        }
        return getLessNode(node.getLeft());
    }
    
    public void printTree(Node node) {
        if (!node.isNil()) {
            System.out.println(node.getKey()+" - "+node.getColorName());
            printTree(node.getLeft());
            printTree(node.getRight());
        }
    }
    
    public Node getRoot() {
        return this.root;
    }
    
}
