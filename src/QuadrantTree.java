/*
 * @author Andrei Ciceu
 * CS1027B
 * 04/05/2024
 * This class is for a Quadrant Tree, used to compress pictures. The methods in it include returning the root,
 * listing all the pixels at a specific level, finding all pixels that have a similar color, and finding a specific node
 */
public class QuadrantTree {
    private QTreeNode root;

    /*
     * @param int[][] thePixels is the pixels of the current image
     * The constructor for the Quadrant Tree
     */
    public QuadrantTree(int[][] thePixels) {
        root = new QTreeNode();
        root.setSize(thePixels.length);
        root.setColor(Gui.averageColor(thePixels, 0, 0, root.getSize()));
        buildTree(root, thePixels);
    }

    /*
     * @param QTreeNode r is the current node in the recursive calls
     * @param int[][] thePixels is the pixels of the current image
     * This private method helps with building the tree
     */
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
            r.setChild(node4, 3);
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

    /*
     * @return QTreeNode returns the root of the tree
     */
    public QTreeNode getRoot() {
        return root;
    }

    /*
     * @param QTreeNode r is the current node in the recursive calls
     * @param int theLevel is the level in the tree where you need to find all nodes
     * @return ListNode<QTreeNode> is the list of all the pixels at the specified level
     */
    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        if (r == null) {
            return null;
        }

        if (theLevel == 0 || r.isLeaf()) {
            ListNode<QTreeNode> node = new ListNode<QTreeNode>(r);
            return node;
        }

        ListNode<QTreeNode> list = new ListNode<QTreeNode>(null);
        list.setNext(combineList(getPixels(r.getChild(0), theLevel),
                getPixels(r.getChild(1), theLevel),
                getPixels(r.getChild(2), theLevel),
                getPixels(r.getChild(3), theLevel)));

        return list.getNext();
    }

    /*
     * @param ListNode<QTreeNode> node1 node 1 of four sent to be combined to one listnode
     * @param ListNode<QTreeNode> node2 node 2 of four sent to be combined to one listnode
     * @param ListNode<QTreeNode> node3 node 3 of four sent to be combined to one listnode
     * @param ListNode<QTreeNode> node4 node 4 of four sent to be combined to one listnode
     * @return ListNode<QTreeNode> is the combined list of the four listnodes sent
     */
    private ListNode<QTreeNode> combineList(ListNode<QTreeNode> node1, ListNode<QTreeNode> node2, ListNode<QTreeNode> node3, ListNode<QTreeNode> node4) {
        ListNode<QTreeNode> head = new ListNode<QTreeNode>(null);
        ListNode<QTreeNode> curr = head;

            while (node1 != null) {
                curr.setNext(new ListNode<QTreeNode>(node1.getData()));
                curr = curr.getNext();
                node1 = node1.getNext();
            }

            while (node2 != null) {
                curr.setNext(new ListNode<QTreeNode>(node2.getData()));
                curr = curr.getNext();
                node2 = node2.getNext();
            }

            while (node3 != null) {
                curr.setNext(new ListNode<QTreeNode>(node3.getData()));
                curr = curr.getNext();
                node3 = node3.getNext();
            }

            while (node4 != null) {
                curr.setNext(new ListNode<QTreeNode>(node4.getData()));
                curr = curr.getNext();
                node4 = node4.getNext();
            }

        return head.getNext();
    }

    /*
     * @param QTreeNode r is the current node in the recursive calls
     * @param int theColor is the color that is to be looked for in the nodes
     * @param int theLevel is the level that the color is to be found at
     * @return Duple returns a duple with all nodes containing a similar color and the number of nodes
     */
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        Duple dup;

        if (r == null) {
            return null;
        }

        if (theLevel == 0 || r.isLeaf()) {
            if (Gui.similarColor(theColor, r.getColor())) {
                return new Duple(new ListNode<QTreeNode>(r), 1);
            } else {
                return new Duple();
            }
        } else {
            Duple[] dupleList = new Duple[4];
            for (int i = 0; i < 4; i++) {
                dupleList[i] = findMatching(r.getChild(i), theColor, theLevel - 1);
            }
            dup = combineDuple(dupleList);

        }
        return dup;
    }

    /*
     * @param Duple[] dupleList list of duples sent from findMatching to be combined to one
     * @return returns a combined duple from the other duples
     */
    private Duple combineDuple(Duple[] dupleList) {
        ListNode<QTreeNode> head = new ListNode<QTreeNode>(null);
        ListNode<QTreeNode> curr = head;
        Duple duple = new Duple();
        int count = 0;

        for (int i = 0; i < 4; i++) {
            ListNode<QTreeNode> listnode = dupleList[i].getFront();
            count += dupleList[i].getCount();
            while (listnode != null) {
                curr.setNext(new ListNode<QTreeNode>(listnode.getData()));
                curr = curr.getNext();
                listnode = listnode.getNext();
            }
        }

        duple.setFront(head.getNext());
        duple.setCount(count);

        return duple;
    }

    /*
     * @param QTreeNode r is for current node in the recursive calls
     * @param int theLevel is the level where the node is located
     * @param int x is the x-coordinate of the node
     * @param int y is the y-coordinate of the node
     * @return QTreeNode returns the node found at the specified level
     */
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        if (r == null || theLevel < 0) {
            return null;
        }

        if (theLevel == 0 && r.contains(x, y)) {
            return r;
        }

        if (r.isLeaf() && !r.contains(x, y)) {
            return null;
        }

        if ((x < r.getx() + r.getSize() / 2) && (y < r.gety() + r.getSize() / 2)) {
            return findNode(r.getChild(0), theLevel - 1, x, y);
        } else if ((x >= r.getx() + r.getSize() / 2) && (y < r.gety() + r.getSize() / 2)) {
            return findNode(r.getChild(1), theLevel - 1, x, y);
        } else if ((x < r.getx() + r.getSize() / 2) && (y >= r.gety() + r.getSize() / 2)) {
            return findNode(r.getChild(2), theLevel - 1, x, y);
        } else {
            return findNode(r.getChild(3), theLevel - 1, x, y);
        }
    }

}
