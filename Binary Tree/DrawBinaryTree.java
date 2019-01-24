import java.util.ArrayList;

public class DrawBinaryTree<T extends Comparable<T>> {
    
    class Node<T> {
        final T key;
        Node<T> left, right;
        
        Node(T key) {
            this.key = key;
        }
        
        @Override
        public String toString() {
            return " " + key + " ";
        }
    }
    
    private Node<T> root;
    
    void put(T key) {
        root = put(root, key);
    }
    
    Node<T> put(Node<T> node, T key) {
        if (node == null) return new Node<T>(key);
        
        T nodeVal = node.key;
        int ret = nodeVal.compareTo(key);
        if (ret > 0)
            node.left = put(node.left, key);
        else if (ret < 0)
            node.right = put(node.right, key);
        return node;
    }
    
    
    void drawTree() {
        if (root != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setCanvasSize(1200,600);
            drawTree(root, .45, .95, .25, 0);
        }
    }
    
    void drawTree(Node<T> node, double x, double y, double range, int depth) {
        final int CUTOFF = 10;
        StdDraw.setPenRadius(.007);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(x, y, 0.02);
        StdDraw.text(x, y, node.toString());
        
        if (node.left != null && depth != CUTOFF) {
            StdDraw.line(x-range, y-.10, x-.01, y-.01);
            drawTree(node.left, x-range, y-.12, range*.5, depth+1);
        }
        
        if (node.right != null && depth != CUTOFF) {
            StdDraw.line(x+range, y-.10, x+.01, y-.01);
            drawTree(node.right, x+range, y-.12, range*.5, depth+1);
        }
    }
    
    void draw(T[] data) {
        for (int i=0; i<data.length; i++)
            put(data[i]);
        
        drawTree();
        StdDraw.show();
    }
    
    public static void main(String[] args) {
        Integer[] data = {10, 8, 12, 5, 9, 6, 15, 16, 18, 17};
        //String[] data = {"F", "E", "B", "A", "H", "D", "G"};
        
        //DrawBinaryTree<String> tree = new DrawBinaryTree<String>();
        DrawBinaryTree<Integer> tree = new DrawBinaryTree<Integer>();
        tree.draw(data);
    }
    
}

