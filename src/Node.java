import java.util.Comparator;
// Name : Tharuka Gamage
// Student No: 20212177

public class Node implements Cloneable, Comparator<Node> {

    private GridLocation coordinate;
    private char nodeType;

    private Node parent;

    private int g, h, f;

    public Node(int x, int y) {
        this.coordinate = new GridLocation(x, y);
    }

    public void setNodeType(char nodeType) {
        this.nodeType = nodeType;
    }

    public char getNodeType() {
        return nodeType;
    }

    public GridLocation getCoordinate() {
        return coordinate;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Node getParent() {
        return parent;
    }

    public int getF() {
        return g + h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void findG() {
        if (parent == null)
            return;

        if (coordinate.getX() == parent.coordinate.getX()) {
            g = Math.abs(coordinate.getY() - parent.getCoordinate().getY()) + parent.getG();
        }

        if (coordinate.getY() == parent.coordinate.getY()) {
            g = Math.abs(coordinate.getX() - parent.getCoordinate().getX()) + parent.getG();
        }

    }

    public void findH(Node endNode) {
        h = Math.abs(coordinate.getX() - endNode.coordinate.getX()) + Math.abs(coordinate.getY() - endNode.coordinate.getY());

    }


   public int findF(Node endNode){
        findG();
        findH(endNode);

       return f = g + h ;
   }

    protected Object clone() {
        Node newNode = new Node(coordinate.getX(), coordinate.getY());
        newNode.setG(g);
        newNode.setH(h);
        newNode.setF(f);
        newNode.setNodeType(nodeType);
        newNode.setParent(parent);
        return newNode;

    }

    @Override
    public boolean equals(Object obj) {
        return this.coordinate.equals(((Node) obj).getCoordinate());
    }

    @Override
    public String toString() {
        String temp = "";

        if(parent==null){
            if(nodeType=='F'){
                temp = "Done";
            }else if (nodeType =='S'){
                temp = "Start at";
            }
        }else{
            if(parent.coordinate.getY() == this.coordinate.getY()){
                if(parent.coordinate.getX() > this.coordinate.getX()){
                    temp = "Move left to";
                }else{
                    temp = "Move right to";
                }
            }
            if(parent.coordinate.getX()== this.coordinate.getX()){
                if(parent.coordinate.getY() > this.coordinate.getY()){
                    temp = "Move up to";
                }else{
                    temp = "Move down to";
                }
            }



        }
        return temp + " : " + this.coordinate;
    }

    @Override
    public int compare(Node o1, Node o2) {
        return o1.getF() - o2.getF();
    }

}
