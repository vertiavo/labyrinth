import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vertiavo on 16.05.17.
 * 0 to gdy puste pole
 * 1 gdy przeszkoda
 * 2 start
 * 3 koniec
 */

public class Algorithms {

    private int n;
    private int index;
    private int startX, startY;
    private int[][] guiArray;
    private boolean[][] visited;
    private List<Point> points;

    public Algorithms(int[][] guiArray) {
        this.guiArray = guiArray;
        this.n = guiArray.length;
        this.points = new ArrayList<>();
        this.visited = new boolean[n][n];
        this.index = 0;

        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], false);
        }

        // Wypelnienie tablicy list incydencji, znalezienie punktow start i koniec
        initialization();

        // Uruchomienie algorytmu z punktu start
        DFS(startX, startY);
    }

    private void initialization() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (guiArray[i][j] == 2) {
                    startX = j;
                    startY = i;
                }
            }
        }
    }

    private void DFS(int v, int k) {
        visited[v][k] = true;
        points.add(new Point(v, k));

        if (guiArray[v][k] == 3)
            return;

        for (int i = v; i < n; i++) {
            for (int j = k; j < n; j++) {
                if (i == 0 && j == 0) {                         // lewy gorny rog
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i + 1, j);

                } else if (i == 0 && j < (n-1)) {               // gorna linia
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i+1, j);

                } else if (i == 0 && j == (n-1)) {              // prawy gorny rog
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i+1, j);

                } else if (i < (n-1) && j == 0) {               // lewy brzeg
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i+1, j);

                } else if (i < (n-1) && j < (n-1)) {            // poza brzegami
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i+1, j);

                } else if (i < (n-1) && j == (n-1)) {           // prawy brzeg
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                    if ((guiArray[i+1][j] == 0 || guiArray[i+1][j] == 3) && !visited[i+1][j])
                        DFS(i+1, j);

                } else if (i == (n-1) && j == 0) {              // lewy dolny rog
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);

                } else if (i == (n-1) && j < (n-1)) {           // dolna linia
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                    if ((guiArray[i][j+1] == 0 || guiArray[i][j+1] == 3) && !visited[i][j+1])
                        DFS(i, j+1);

                } else if (i == (n-1) && j == (n-1)) {          // prawy dolny rog
                    if ((guiArray[i-1][j] == 0 || guiArray[i-1][j] == 3) && !visited[i-1][j])
                        DFS(i-1, j);
                    if ((guiArray[i][j-1] == 0 || guiArray[i][j-1] == 3) && !visited[i][j-1])
                        DFS(i, j-1);
                }
            }
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
