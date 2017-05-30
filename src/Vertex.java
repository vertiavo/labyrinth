import java.util.ArrayList;

/**
 * Created by Piotrek on 29/05/2017.
 */
public class Vertex{
    int x;
    int y;
    boolean IsRoot;
    boolean IsEnd;
    ArrayList<Vertex>EdgeTo;
    public ArrayList<Vertex> Neighbours;


    public Vertex(int x , int y){

        this.x=x;
        this.y=y;
        Neighbours=new ArrayList<Vertex>();
        IsRoot=false;
        IsEnd=false;
        EdgeTo=new ArrayList<Vertex>();

    }
    public void AddNeighour(Vertex x){
        Neighbours.add(x);
    }
    public void AddEdgeTo(Vertex x){
        EdgeTo.add(x);
    }
    public void setRoot(boolean root) {
        IsRoot = root;
    }

    public void setEnd(boolean end) {
        IsEnd = end;
    }

    public boolean isRoot() {
        return IsRoot;
    }

    public boolean isEnd() {
        return IsEnd;
    }
    public boolean HasConnection(Vertex to){
        return Neighbours.contains(to);
    }
    @Override
    public String toString() {
        return "|"+x+","+y+"| ";
    }
}