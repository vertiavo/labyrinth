import java.util.ArrayList;

/**
 * Created by Piotrek on 29/05/2017.
 */
public class Vertex {
    int x;
    int y;
    boolean isRoot;
    boolean isEnd;
    ArrayList<Vertex> edgeTo;
    public ArrayList<Vertex> neighbours;


    public Vertex(int x , int y) {

        this.x=x;
        this.y=y;
        neighbours = new ArrayList<>();
        isRoot =false;
        isEnd =false;
        edgeTo = new ArrayList<>();

    }
    public void addNeighour(Vertex x){
        neighbours.add(x);
    }

    public void addEdgeTo(Vertex x){
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

    public boolean hasConnection(Vertex to){
        return neighbours.contains(to);
    }

    @Override
    public String toString() {
        return "|"+x+","+y+"| ";
    }
}