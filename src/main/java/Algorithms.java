import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by vertiavo on 16.05.17.
 *
 * Legenda labiryntu:
 * 0 to gdy puste pole
 * 1 gdy przeszkoda
 * 2 start
 * 3 koniec
 */

public class Algorithms {

    private int n;
    public int startX, startY;
    private int[][] guiArray;
    private boolean[][] visited;
    private List<Point> localPoints;
    private List<Point> points;
    private boolean foundWay;

    public Algorithms(int[][] guiArray) {
        this.guiArray = guiArray;
        this.n = guiArray.length;
        this.foundWay = false;
        this.localPoints = new ArrayList<>();
        this.points = new ArrayList<>();
        this.visited = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], false);
        }

        // Wypelnienie tablicy list incydencji, znalezienie punktow start i koniec
        initialization();
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

    public void DFS(int i, int j) {
        visited[i][j] = true;
        localPoints.add(new Point(i, j));

        if (guiArray[i][j] == 3) {
            foundWay = true;
        }

        // W wewnętrznym if najpierw jest sprawdzane otoczenie obecnego wierzchołka na obecnośc punktu końcowego
        // Potem następuje przeszukiwanie możliwej drogi
        if (i == 0 && j == 0) {                         // lewy gorny rog
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i == 0 && j < (n - 1)) {               // gorna linia
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i == 0 && j == (n - 1)) {              // prawy gorny rog
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i < (n - 1) && j == 0) {               // lewy brzeg
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i < (n - 1) && j < (n - 1)) {            // poza brzegami
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i < (n - 1) && j == (n - 1)) {           // prawy brzeg
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i + 1][j] == 3 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i + 1][j] == 0 && !visited[i + 1][j] && !foundWay)
                DFS(i + 1, j);

        } else if (i == (n - 1) && j == 0) {              // lewy dolny rog
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);

        } else if (i == (n - 1) && j < (n - 1)) {           // dolna linia
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 3 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
            if (guiArray[i][j + 1] == 0 && !visited[i][j + 1] && !foundWay)
                DFS(i, j + 1);

        } else if (i == (n - 1) && j == (n - 1)) {          // prawy dolny rog
            if (guiArray[i - 1][j] == 3 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 3 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);

            if (guiArray[i - 1][j] == 0 && !visited[i - 1][j] && !foundWay)
                DFS(i - 1, j);
            if (guiArray[i][j - 1] == 0 && !visited[i][j - 1] && !foundWay)
                DFS(i, j - 1);
        }

        if (foundWay && points.isEmpty()) {
            points.addAll(localPoints);
        } else {
            localPoints.remove(localPoints.size() - 1);
        }
    }

    public List<Point> getPoints() {
        return points;
    }

}
