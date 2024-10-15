public class QuadrantTree {
    private QTreeNode root;

    public QuadrantTree(int[][] thePixels) {
        root = new QTreeNode();
        root.setSize(thePixels.length);
        root.setColor(Gui.averageColor(thePixels, 0, 0, root.getSize()));
        buildTree(root, thePixels);
    }

    private void buildTree(QTreeNode r, int[][] thePixels) {
        if (r.getSize() == 1 && r.getParent() == null) {
            return;
        }
        int newSize = r.getSize() / 2;
        if (newSize == 1) {
            QTreeNode node1 = new QTreeNode(null, r.getx(), r.gety(), newSize, 0);
            node1.setColor(Gui.averageColor(thePixels, node1.getx(), node1.gety(), newSize));
            node1.setParent(r);
            r.setChild(node1, 0);
            QTreeNode node2 = new QTreeNode(null, r.getx() + newSize, r.gety(), newSize, 0);
            node2.setColor(Gui.averageColor(thePixels, node2.getx(), node2.gety(), node2.getSize()));
            node2.setParent(r);
            r.setChild(node2, 1);
            QTreeNode node3 = new QTreeNode(null, r.getx(), r.gety() + newSize, newSize, 0);
            node3.setColor(Gui.averageColor(thePixels, node3.getx(), node3.gety(), node3.getSize()));
            node3.setParent(r);
            r.setChild(node3, 2);
            QTreeNode node4 = new QTreeNode(null, r.getx() + newSize, r.gety() + newSize, newSize, 0);
            node4.setColor(Gui.averageColor(thePixels, node4.getx(), node4.gety(), node4.getSize()));
            node4.setParent(r);
            r.setChild(node2, 3);
            return;
        }
        QTreeNode[] nodes = new QTreeNode[4];
        for (int i = 0; i < 4; i++) {
            nodes[i] = new QTreeNode();
            nodes[i].setSize(newSize);
            if (i == 0) {
                nodes[i].setx(r.getx());
                nodes[i].sety(r.gety());
            } else if (i == 1) {
                nodes[i].setx(r.getx() + newSize);
                nodes[i].sety(r.gety());
            } else if (i == 2) {
                nodes[i].setx(r.getx());
                nodes[i].sety(r.gety() + newSize);
            } else {
                nodes[i].setx(r.getx() + newSize);
                nodes[i].sety(r.gety() + newSize);
            }
            nodes[i].setColor(Gui.averageColor(thePixels, nodes[i].getx(), nodes[i].gety(), newSize));
            nodes[i].setParent(r);
            r.setChild(nodes[i], i);
            buildTree(nodes[i], thePixels);
        }
    }

    public QTreeNode getRoot() {
        return root;
    }

    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        if (r == null) {
            return null;
        }

        if (theLevel == 0 || r.isLeaf()) {
            ListNode<QTreeNode> node = new ListNode<QTreeNode>(r);
            return node;
        }

        ListNode<QTreeNode> list = new ListNode<QTreeNode>(null);
        list.setNext(concatenateList(getPixels(r.getChild(0), theLevel - 1), getPixels(r.getChild(1), theLevel - 1),
                getPixels(r.getChild(2), theLevel - 1), getPixels(r.getChild(3), theLevel - 1)));

        return list;
    }

    private ListNode<QTreeNode> concatenateList(ListNode<QTreeNode> node1, ListNode<QTreeNode> node2, ListNode<QTreeNode> node3, ListNode<QTreeNode> node4) {
        ListNode<QTreeNode> head = new ListNode<QTreeNode>(null);
        ListNode<QTreeNode> curr = head;

        curr.setNext(node1);
        curr = curr.getNext();
        curr.setNext(node2);
        curr = curr.getNext();
        curr.setNext(node3);
        curr = curr.getNext();
        curr.setNext(node4);
        curr = curr.getNext();

        return head.getNext();
    }

    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {

    }

    public static void main(String[] args) {
        boolean testPassed = true;
		int[][] pixels = new int[32][32];
		for (int i = 0; i < 32; ++i)
			for (int j = 0; j < 32; ++j)
				pixels[i][j] = i;
		try {		
			QuadrantTree tree = new QuadrantTree(pixels);
			QTreeNode root = tree.getRoot();			
			ListNode<QTreeNode> list = tree.getPixels(root,0);
			if (length(list) != 1) testPassed = false;
			list = tree.getPixels(root,5);
            System.out.println(numNodes(root));
            System.out.println(length(list));
			if (length(list) != 1024) testPassed = false;
		} catch (Exception e) {ex(e); testPassed = false;}
        
    }

    private static int length(ListNode<QTreeNode> list) {
		int c = 0;
		while (list != null) {
			++c;
			list = list.getNext();
		}
		return c;
	}

    private static void ex (Exception e) {
		System.out.println("Your code has crashed. The following exception was thrown:");
		System.out.println(e.getMessage());
	}

    private static int numNodes (QTreeNode r) {
		int c = 1;
		if (r == null) return 0;
		else if (r.isLeaf()) return 1;
		else {
			for (int i = 0; i < 4; ++i)
				c = c + numNodes(r.getChild(i));
		}
		return c;
	}
}
