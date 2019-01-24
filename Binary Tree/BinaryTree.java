// Garrett Ewens
// Data Structures
// October 27, 2018
// javac BinaryTree.java DrawBinaryTree.java
// java BinaryTree

import java.util.ArrayList;

public class BinaryTree<T extends Comparable<T>> {

    class Node<T> {
        T key;
        Node<T> left, right;

        public Node(T info) {
            key = info;
            left = null;
            right = null;
        }
    }

    Node<T> gRoot;

    BinaryTree() {
        gRoot = null;
    }

    void Preorder(Node<T> node, ArrayList<T> ary) {
        if (node == null) return;

        System.out.println(node.key);
        ary.add(node.key);
        Preorder(node.left, ary);
        Preorder(node.right, ary);
    }

    void PrintPreorder(ArrayList<T> ary) {
        Preorder(gRoot, ary);

        DrawBinaryTree<T> theTree = new DrawBinaryTree<T>();
        @SuppressWarnings("unchecked")
        T[] data = (T[]) new Comparable[ary.size()];

        for (int i=0; i<ary.size(); i++) {
            data[i] = ary.get(i);
        }
        theTree.draw(data);
    }


    // Add your code here - Add and Remove
    public void Add(T info) {
		gRoot = Insert(gRoot, info);
	}
	public Node<T> Insert(Node<T> root, T info) {
		if (root == null) {
			return new Node<T>(info);
		}
		if (info.compareTo(root.key) > 0) {
			root.right = Insert(root.right, info);
		}
		else {
			root.left = Insert(root.left, info);
		}
		return root;
	}
	public void Remove(T info) {
		gRoot = Delete(gRoot, info);
	}
	public Node<T> Delete(Node<T> root, T info) {
		if (root == null) {
			return null;
		}
		if (info.compareTo(root.key) > 0) {
			root.right = Delete(root.right, info);
		}
		else if (info.compareTo(root.key) < 0) {
			root.left = Delete(root.left, info);
		}
		else {
			if (root.left == null) {
				return root.right;
			}
			else if (root.right == null) {
				return root.left;
			}
			root.key = FindPredecessor(root.left);
			root.left = Delete(root.left, root.key);
		}
		return root;
	}
	T FindPredecessor(Node<T> root) {
		T minv = root.key;
		while (root.right != null) {
			minv = root.right.key;
			root = root.right;
		}
		return minv;
	}

	public int getTreeHeight(Node<T> root) {
		// Add your code here
		if (root == null) {
			return -1;
		}
		else {
			int leftHeight = getTreeHeight(root.left);
			int rightHeight = getTreeHeight(root.right);
			if (leftHeight > rightHeight) {
				return (leftHeight + 1);
			}
			else {
				return (rightHeight + 1);
			}
		}
	}
	public void storeNode(Node<T> root, ArrayList<Node> nodes) {
		if (root == null) {
			return;
		}
		// Sort nodes Inorder (Left, Root, Right
		storeNode(root.left, nodes);
		nodes.add(root);
		storeNode(root.right, nodes);
	}
	public Node<T> buildTreeHelper(ArrayList<Node> nodes, int start, int end) {
		if (start > end) {
			return null;
		}
		int mid = (start + end) / 2;
		Node<T> node = nodes.get(mid);
		// Inorder Traversal
		node.left = buildTreeHelper(nodes, start, mid - 1);
		node.right = buildTreeHelper(nodes, mid + 1, end);
		return node;
	}
	public Node<T> buildTree(Node<T> root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		storeNode(root, nodes);
		int n = nodes.size();
		return buildTreeHelper(nodes, 0, n - 1);
	}


    public static void keyPress() {
        System.out.println("Press a key to continue ...");
        try{System.in.read();}
        catch(Exception e){}
    }

    public static void main(String[] args) {

        BinaryTree<Integer> tree = new BinaryTree<Integer>();

        tree.Add(10);
        tree.Add(8);
        tree.Add(12);
        tree.Add(5);
        tree.Add(9);
        tree.Add(6);
        tree.Add(15);
        tree.Add(16);
        tree.Add(18);
        tree.Add(17);


        // Create the getTreeHeight Method
        System.out.println("The height of the tree is " + tree.getTreeHeight(tree.gRoot));
        tree.PrintPreorder(new ArrayList<Integer>());
        keyPress();


        tree.Remove(8);


        // Print out the tree height
        System.out.println("The height of the tree is " + tree.getTreeHeight(tree.gRoot));
        tree.PrintPreorder(new ArrayList<Integer>());
        keyPress();

        // create a balance tree here
        BinaryTree<Integer> btree = new BinaryTree<Integer>();
        // Add your code here
        //Skips previous keyPress(); event if not added
        keyPress();
        btree.gRoot = btree.buildTree(tree.gRoot);


        System.out.println();
        System.out.println("------------------------------------");

        // Print out the tree height
        System.out.println("The height of the tree is " + btree.getTreeHeight(btree.gRoot));
        btree.PrintPreorder(new ArrayList<Integer>());

    }
}

