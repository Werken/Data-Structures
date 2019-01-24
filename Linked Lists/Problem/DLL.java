// Garrett Ewens
// Data Structures
// October 9, 2018
// javac DLL.java
// java DLL

import java.util.*;

// To print out the address, you may use
// Integer.toHexString(node.hashCode())

class DLLNode<T>
{
    private T info;
    private DLLNode<T> next, prev;

    // add copy constructor here
    public DLLNode(DLLNode<T> init) {
		DLLNode<T> head = new DLLNode<T>((T)null);
		while (init != null) {
			head.setNext(new DLLNode(init.getData()));
			head.getNext().setPrev(head);

			init = init.getNext();
			if (head.getNext() != null) {
				head = head.getNext();
			}
		}
		while (head.getPrev().getData() != null) {
			head = head.getPrev();
		}
		this.info = (T)head.getData();
		next = (DLLNode<T>)head.getNext();
		prev = (DLLNode<T>)head.getPrev();
	}

    public DLLNode(T info) {
        this.info = info;
        next = null;  // link to the next node
        prev = null;  // link to the previous node
    }

    public void setNext(DLLNode<T> next) {
        this.next = next;
    }
    public void setPrev(DLLNode<T> prev) {
        this.prev = prev;
    }

    public DLLNode<T> getNext() {
        return this.next;
    }

    public DLLNode<T> getPrev() {
        return this.prev;
    }

    public T getData() {return this.info;}
}

public class DLL<T>{

    // add your code here
    public DLLNode<T> head;

    public DLL() {
		this.head = new DLLNode<T>((T)null);
	}

	public DLL(DLLNode<T> init) {
		this.head = init;
	}


    public static <T> void Traverse(DLLNode<T> nodes) {
		while (nodes != null) {
			if (nodes.getData() == null)
				continue;
			System.out.print(nodes.getData() + " (" + Integer.toHexString(nodes.hashCode()) + ") ");
			nodes = nodes.getNext();
		}
		System.out.println();
	}

	public <T> void InsertFront(DLLNode fnode) {
		if (this.head.getData() == null) {
			fnode.setPrev(this.head);
			this.head = fnode;
			return;
		}

		fnode.setNext(this.head);
		fnode.setPrev(new DLLNode<T>((T)null));
		this.head = fnode;
	}

	public <T> void InsertEnd(DLLNode enode) {
		enode.setNext(null);

		if (this.head.getData() == null) {
			enode.setPrev(this.head);
			this.head = enode;
			return;
		}

		while (this.head.getNext() != null) {
			this.head = this.head.getNext();
		}

		this.head.setNext(enode);

		enode.setPrev(this.head);
		while (this.head.getPrev().getData() != null) {
			this.head = this.head.getPrev();
		}
	}

	public <T> void InsertInterior(DLLNode inode, DLLNode prev) {
		while (this.head != prev) {
			this.head = this.head.getNext();
		}

		inode.setNext(this.head.getNext());

		inode.setPrev(this.head);

		this.head.setNext(inode);

		if (inode.getNext() != null) {
			DLLNode<T> tmp = inode.getNext();
			tmp.setPrev(inode);
		}

		while (this.head.getPrev().getData() != null) {
			this.head = this.head.getPrev();
		}
	}

    public <T> void Deletion(DLLNode dnode) {
		while (this.head != dnode) {
			this.head = this.head.getNext();
		}

		if (this.head.getPrev() != null)
			this.head.getPrev().setNext(this.head.getNext());

		if (this.head.getNext() != null) {
			this.head.getNext().setPrev(this.head.getPrev());
		}

		if (this.head.getPrev().getData() != null) {
			this.head = this.head.getPrev();
		}
		else if (this.head.getNext().getData() != null)
			this.head = this.head.getNext();


		while (this.head.getPrev().getData() != null) {
			this.head = this.head.getPrev();
		}
	}
	public static <T> DLL Duplicate(DLLNode nodes) {
		DLL dup = new DLL();
		while (nodes != null) {
			dup.InsertEnd(new DLLNode(nodes.getData()));
			nodes = nodes.getNext();
		}

		return dup;
	}
	public static <T> void Reverse(DLLNode nodes) {
		while (nodes.getNext() != null) {
			nodes = nodes.getNext();
		}

		while (nodes.getPrev() != null) {
			if (nodes.getData() == null)
				continue;
			System.out.print(nodes.getData() + " (" + Integer.toHexString(nodes.hashCode()) + ") ");
			nodes = nodes.getPrev();
		}
		System.out.println();
	}


    public static void main(String[] args) {

        DLLNode<String> node1, node2, node3, node4, node5, node6, head;

        // allocate nodes
        node1 = new DLLNode<String>("A");
        node2 = new DLLNode<String>("B");
        node3 = new DLLNode<String>("C");
        node4 = new DLLNode<String>("D");
        node5 = new DLLNode<String>("E");
        node6 = new DLLNode<String>("F");

        DLL list = new DLL();

        // A B C
        // Insert A to the front
        list.InsertFront(node1);
        // Insert B to the end
        list.InsertEnd(node2);
        // Insert C to the end
        list.InsertEnd(node3);

   		// A B D C
        // Insert D between B and C
        list.InsertInterior(node4, node2);

    	// A B D C E F
        // Insert E to the end
        list.InsertEnd(node5);
        // Insert F to the end
        list.InsertEnd(node6);

    	// A D C E F
        //  Delete B
        list.Deletion(node2);



        System.out.println("Original List");
        Traverse(list.head);
        System.out.println();

        System.out.println("Duplicated List");
        DLL dup = Duplicate(list.head);
        Traverse(dup.head);
        System.out.println();

        System.out.println("Reversed List");
        Reverse(dup.head);
        System.out.println();

        System.out.println("Original List");
        Traverse(list.head);
        System.out.println();


        // BONUS POINTS
        @SuppressWarnings({"unchecked", "unsafe"})
        DLLNode<String> newHead = new DLLNode(list.head.getNext());
        System.out.println("Copy Constructor");
        DLL newList = new DLL(newHead);
        Traverse(newList.head);
    }
}
