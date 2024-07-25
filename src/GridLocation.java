// Name : Tharuka Gamage
// Student No: 20212177
public class GridLocation implements Cloneable{
     int x, y;

    public GridLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    @Override
    public String toString() {
        int _x = x;
        int _y = y;

        return "(" + _x + ", " + _y + ")";
    }

    @Override
    public boolean equals(Object _a) {
        GridLocation a = (GridLocation) _a;
        return this.x == a.x && this.y == a.y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        GridLocation c = new GridLocation(x, y);
        return c;

    }




}
