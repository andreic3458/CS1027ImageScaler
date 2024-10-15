/*
 * @author Andrei Ciceu
 * CS1027B
 * 04/05/2024
 * This class is for a Quadrant Tree Node, where it stores the values for parent and children nodes,
 * as well as the x and y coordinates of the top left corner of the Quadrant, as well as methods to check if
 * a specific coordinate point is in the quadrant, and if the node is a leaf node for the tree
 */
public class QTreeNode {
    private int x, y;
    private int size;
    private int color;
    QTreeNode parent;
    QTreeNode[] children;

    /*
     * The constructor for QTreeNode without any parameters
     */
    public QTreeNode(){
        parent = null;
        children = new QTreeNode[]{null, null, null, null};
        x = y = size = color = 0;
    }

    /*
     * @param QTreeNode[] theChildren the children for the node
     * @param int xcoord the x-coordinate for the top-left of the quadrant
     * @param int ycoord the y-coordinate for the top-left of the quadrant
     * @param int theSize the size of the quadrant
     * @param int theColor the average color of the quadrant
     * The constructor for QTreeNode with parameters
     */
    public QTreeNode (QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor){
        parent = null;
        children = theChildren;
        x = xcoord;
        y = ycoord;
        size = theSize;
        color = theColor;
    }

    /*
     * @param int xcoord the x-coordinate to be checked
     * @param int ycoord the y-coordinate to be checked
     * @return boolean true if inside the quadrant, false if it isn't
     */
    public boolean contains(int xcoord, int ycoord){
        if ((x<=xcoord)&&(xcoord<x+size)&&(y<=ycoord)&&(ycoord<y+size)){
            return true;
        }
        return false;
    }

    /*
     * @return int returns the x-coordinate
     */
    public int getx(){
        return x;
    }

    /*
     * @return int returns the y-coordinate
     */
    public int gety(){
        return y;
    }

    /*
     * @return int returns the size of the quadrant
     */
    public int getSize(){
        return size;
    }

    /*
     * @return int returns the average color of the quadrant
     */
    public int getColor(){
        return color;
    }

    /*
     * @return QTreeNode returns the parent node
     */
    public QTreeNode getParent(){
        return parent;
    }

    /*
     * @param int index takes the index for the specific child node
     * @return QTreeNode returns the child node for the specified index
     * @throws QTreeException if the index is out of bounds
     */
    public QTreeNode getChild(int index) throws QTreeException{
        if (children==null||index<0||index>children.length-1) throw new QTreeException("index out of bounds");
        return children[index];
    }

    /*
     * @param int newx sets the new x-coordinate value
     */
    public void setx(int newx){
        x = newx;
    }

    /*
     * @param int newy sets the new y-coordinate value
     */
    public void sety(int newy){
        y = newy;
    }

    /*
     * @param int newSize sets the new size of the quadrant
     */
    public void setSize(int newSize){
        size = newSize;
    }

    /*
     * @param int newColor sets the new average color
     */
    public void setColor(int newColor){
        color = newColor;
    }

    /*
     * @param QTreeNode newParent sets the new parent node
     */
    public void setParent (QTreeNode newParent){
        parent = newParent;
    }

    /*
     * @param QTreeNode newChild sets the new child node at the specified index
     * @param int index the index for the new child value
     * @throw QTreeException if the index is out of bounds
     */
    public void setChild(QTreeNode newChild, int index) throws QTreeException{
        if (index<0||index>children.length-1||children==null) throw new QTreeException("index out of bounds");
        children[index] = newChild;
    }

    /*
     * @return boolean returns true if is a leaf node, and false if it isn't
     */
    public boolean isLeaf(){
        if(children==null) return true;
        for (int i = 0; i<children.length; i++){
            if(children[i]==null) return true;
        }
        return false;
    }
}
