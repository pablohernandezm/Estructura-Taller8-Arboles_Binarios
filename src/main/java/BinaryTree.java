/**
 * Taller 8. Árboles binarios
 * @author Pablo Hernández Meléndez
 * @author Jhoy Luis Castro Casanova
 */
public class BinaryTree {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);

        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);


        tree.printGeneratedData();
    }

    private Node root;
    private int length;
    private int high;

    public enum TRAVERSAL_MODE{
        PREORDER, INORDER, POSTORDER, LEVEL_ORDER
    }

    public BinaryTree(){
        root = null;
        length = 0;
        high=0;
    }

    public void insert(int value){
        if(root == null){
            root = new Node(value);
            length = 1;
            high=1;

            return;
        }

        int newHigh = root.insert(value);

        if (newHigh>this.high){
            this.high = newHigh;
        }

        ++length;
    }

    public StringBuilder Traversal(TRAVERSAL_MODE mode){
        return switch (mode) {
            case PREORDER -> preorder(new StringBuilder(), root);
            case INORDER -> inorder(new StringBuilder(), root);
            case POSTORDER -> postorder(new StringBuilder(), root);
            case LEVEL_ORDER -> levelOrder(root);
        };
    }

    private StringBuilder preorder(StringBuilder sb, Node node){
        if (node == null){
            return sb;
        }
        sb.append(node.getValue()).append(" ");
        preorder(sb, node.getLeft());
        preorder(sb, node.getRight());

        return sb;
    }

    private StringBuilder inorder(StringBuilder sb, Node node){
        if (node == null){
            return sb;
        }
        inorder(sb, node.getLeft());
        sb.append(node.getValue()).append(" ");
        inorder(sb, node.getRight());

        return sb;
    }

    private StringBuilder postorder(StringBuilder sb, Node node){
        if (node == null){
            return sb;
        }
        postorder(sb, node.getLeft());
        postorder(sb, node.getRight());
        sb.append(node.getValue()).append(" ");

        return sb;
    }

    private StringBuilder levelOrder(Node node){
        StringBuilder sb = new StringBuilder();

        if(node == null){
            return sb;
        }

        for (int i = 0; i < high; i++) {
            sb = getCurrentLevel(sb, node, i+1);
        }

        return sb;
    }

    private StringBuilder getCurrentLevel(StringBuilder sb, Node root, int level)
    {
        if (root == null){
            return sb;
        }

        if (level == 1){
            sb.append(root.getValue());
            sb.append(" ");
        }

        else if (level > 1) {
            getCurrentLevel(sb, root.getLeft(), level - 1);
            getCurrentLevel(sb, root.getRight(), level - 1);
        }

        return sb;
    }

    public void printGeneratedData(){
        System.out.println("RECORRIDOS DEL ÁRBOL");
        System.out.println("Preorder:");
        System.out.println(this.Traversal(BinaryTree.TRAVERSAL_MODE.PREORDER));

        System.out.println("Inorder:");
        System.out.println(this.Traversal(BinaryTree.TRAVERSAL_MODE.INORDER));

        System.out.println("Postorder:");
        System.out.println(this.Traversal(BinaryTree.TRAVERSAL_MODE.POSTORDER));

        System.out.println("Level order:");
        System.out.println(this.Traversal(BinaryTree.TRAVERSAL_MODE.LEVEL_ORDER));

        System.out.println("\n".repeat(2));
        System.out.println("DATOS DEL ÁRBOL");
        System.out.println("Aristas: " + this.countEdges(root));
        System.out.println("Altura: " + this.getHigh());
        System.out.println("Cantidad de nodos: " + this.getLength());
        System.out.println("Descripción: \n" + this.describeTree());
    }

    /**
     * Cuenta las aristas de un árbol. Una arista es la conexión entre dos nodos en un árbol.
     * @param node Nodo raíz del árbol
     * @return Cantidad de aristas
     */
    private int countEdges(Node node){
        if (node == null || node.getLeft() == null || node.getRight() == null){
            if (node!=null && node.getLeft()!=null){
                return countEdges(node.getLeft());
            } else if (node!=null && node.getRight()!=null){
                return countEdges(node.getRight());
            }
        } else if(node.getLeft() != null && node.getRight() != null){
            return 1 + countEdges(node.getLeft()) + countEdges(node.getRight());
        }

        return 0;
    }

    public StringBuilder describeTree(){
        StringBuilder sb = new StringBuilder();

        boolean full = (length == (Math.pow(2, high) - 1));
        boolean complete = isComplete(root, 0);

        if (full){
            sb.append("\tÁrbol lleno. Todos sus nodos tienen 0 ó 2 hijos.\n");
        }

        if(complete){
            sb.append("\tÁrbol completo. Todos sus niveles están llenos, excepto posiblemente el último, y el último nivel tiene todos sus nodos de izquierda a derecha.\n");
        }

        if (!full && !complete){
            sb.append("\tNo es ni lleno ni completo.");
        }

        return sb;
    }

    boolean isComplete(Node root, int index)
    {
        if (root == null)
            return true;

        if (index >= length)
            return false;

        return (isComplete(root.left, 2 * index + 1)
                && isComplete(root.right, 2 * index + 2));

    }

    protected static class Node implements Comparable<BinaryTree.Node> {
        private final int value;
        private Node left;
        private Node right;

        private int high=0;

        private int degree=0;

        public Node(int value){
            this.value=value;
            this.high=1;
        }

        private Node(int value, int high){
            this.value = value;
            this.high = high;
        }

        public int insert(int value){
            if (value<this.value){
                return pushLeft(value);
            } else {
                return pushRight(value);
            }
        }

        public int pushLeft(int value){
            if (left==null){
                degree++;
                left = new Node(value, high + 1);
                return left.getHigh();
            }

            return left.insert(value);
        }

        public int pushRight(int value){
            if (right==null){
                degree++;
                right = new Node(value, high + 1);
                return right.getHigh();
            }

            return right.insert(value);
        }

        public int getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public int getHigh() {
            return high;
        }

        @Override
        public int compareTo(Node comparable) {
            return Integer.compare(this.value, comparable.getValue());
        }
    }

    public int getLength() {
        return length;
    }

    public int getHigh() {
        return high;
    }
}
