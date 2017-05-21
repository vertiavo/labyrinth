import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Piotrek on 21/05/2017.
 */
public class DFSAlgorithm {
     int[][] maze ;
    public  boolean[][] visited ;
     ArrayList<Point> neighbors ;
    public DFSAlgorithm(int[][]labyrinth){
        maze=labyrinth;
        visited= new boolean[maze.length][maze[0].length];
        neighbors = new ArrayList<Point>();
    }
   /* public static void main(String[] args) {

    }*/

    public  boolean dfs(int[][] maze, Point p){
        neighbors = getNeighbors(maze,p);
        if (maze[p.x][p.y] == 3){
            System.out.println("FOUND IT");
            return true;
        }
        if (neighbors.isEmpty()){
            return false;
        }
        for (int i=0;i<neighbors.size();i++){
            System.out.println("Nieghbors: " + neighbors);
            System.out.println(i + "(" + p.x + "," + p.y + ")");
            visited[neighbors.get(i).x][neighbors.get(i).y] = true;
            if(dfs(maze, neighbors.get(i))){
                return true;
            }
        }
        return false;
    }

    public  ArrayList<Point> getNeighbors(int[][] maze, Point p){
        ArrayList<Point> neighbors = new ArrayList<Point>();
        Point left = new Point();
        Point right = new Point();
        Point down = new Point();
        Point up = new Point();
        down.x = p.x - 1;
        down.y = p.y;
        if (valid(maze,down)) neighbors.add(down);
        up.x = p.x + 1;
        up.y = p.y;
        if (valid(maze,up)) neighbors.add(up);
        left.x = p.x;
        left.y = p.y - 1;
        if (valid(maze,left)) neighbors.add(left);
        right.x = p.x;
        right.y = p.y + 1;
        if (valid(maze,right)) neighbors.add(right);
        return neighbors;
    }

    public  boolean valid(int[][] maze, Point p){
        if (inMaze(maze,p) && canGo(maze,p) && visited[p.x][p.y] == false) return true;
        else return false;
    }

    public  boolean inMaze(int[][] maze, Point p){
        if (p.x < maze[0].length && p.x > -1 && p.y < maze.length && p.y > -1){
            return true;
        } else return false;
    }

    public  boolean canGo(int[][] maze, Point p){
        if (maze[p.x][p.y] != 1 && maze[p.x][p.y] != 4) return true;
        else return false;
    }

    public  Point getOgre(int[][] maze){
        Point ogre = new Point();
        for (int i=0;i<maze.length;i++){
            for (int j=0;j<maze[i].length;j++){
                if (maze[i][j] == 2){
                    ogre.x = j;
                    ogre.y = i;
                }
            }
        }
        return ogre;
    }
}
