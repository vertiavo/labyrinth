package algorithms;

import java.util.ArrayList;

public class Vertex {
    private int x, y;
    boolean isRoot, isEnd;
    ArrayList<Vertex> edgeTo;
    public ArrayList<Vertex> neighbours;


    public Vertex(int x, int y) {
        setX(x);
        setY(y);
        neighbours = new ArrayList<>();
        isRoot = false;
        isEnd = false;
        edgeTo = new ArrayList<>();

    }


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void addNeighour(Vertex x) {
        neighbours.add(x);
    }

    public void addEdgeTo(Vertex x) {
        edgeTo.add(x);
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean hasConnection(Vertex to) {
        return neighbours.contains(to);
    }

    @Override
    public String toString() {
        return "|" + x + "," + y + "| ";
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}