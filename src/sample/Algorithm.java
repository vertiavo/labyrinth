package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vertiavo on 16.05.17.
 */
public class Algorithm {

    private int n;
    private int[][] array;
    private boolean[] visited;
    private List<Integer> result;

    public Algorithm(int n, int[][] array) {
        this.n = n;
        this.array = array;
        this.visited = new boolean[n];
        this.result = new ArrayList<>();

        Arrays.fill(visited, false);
    }

    public void DFS(int v) {
        this.visited[v] = true;
        result.add(v);

        for (int i = 0; i < n; i++) {
            if (this.array[v][i] == 1 && !this.visited[i])
                DFS(i);
        }
    }

    public List<Integer> getResult() {
        return result;
    }
}
