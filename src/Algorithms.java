import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.awt.Point;
import java.util.*;

/**
 * Created by vertiavo on 16.05.17.
 * <p>
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

    /**
     * @param guiArray Tablica z labiryntem otrzymana od użytkownika
     */
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

    /**
     * Metoda odnajdująca punkt startowy
     */
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

    /**
     * Metoda realizująca rekurencyjny algorytm DFS
     *
     * @param i Współrzędna X punktu startowego
     * @param j Współrzędna Y punktu startowego
     */
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
            return;
        } else {
            localPoints.remove(localPoints.size() - 1);
        }
    }

    public void BFS(int i, int j) {
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(i, j));
        List<Point> visitedNodes = new LinkedList<Point>();
        List<Point> toremove = new LinkedList<Point>();
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            if (guiArray[p.x][p.y] == 3) {
                foundWay = true;
                return;
            } else if (marked(points, p)) {             //jeśli została odwiedzona to usuwamy z listy wszystko po pierwszym wystąpieniu elementu
                int tempIndex = 0;
                for (Point point : points) {
                    if (point.getX() == p.getX() && p.getY() == point.getY())
                        tempIndex = points.indexOf(point);
                }
                points = points.subList(0, tempIndex);
            }


            visited[p.x][p.y] = true;
            points.add(p);

            if (p.x > 0 && !(guiArray[p.x - 1][p.y] == 1) && !visited[p.x - 1][p.y])
                queue.offer(new Point(p.x - 1, p.y));
            if (p.x < n - 1 && !(guiArray[p.x + 1][p.y] == 1) && !visited[p.x + 1][p.y])
                queue.offer(new Point(p.x + 1, p.y));
            if (p.y > 0 && !(guiArray[p.x][p.y - 1] == 1) && !visited[p.x][p.y - 1])
                queue.offer(new Point(p.x, p.y - 1));
            if (p.y < n - 1 && !(guiArray[p.x][p.y + 1] == 1) && !visited[p.x][p.y + 1])
                queue.offer(new Point(p.x, p.y + 1));


        }

    }

    private boolean marked(List<Point> markedList, Point point) {
        for (Point p : markedList) {
            if (p.getX() == point.getX() && p.getY() == point.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda zwracająca listę punktów powstałych w wyniku rozwiązania algorytmu
     *
     * @return Lista punktów tworzących ścieżkę od startu do wyjścia
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * Metoda zwracająca wartość zmiennej foundWay
     *
     * @return Zmienna informująca, czy algorytm odnalazł drogę do wyjścia
     */

    public boolean foundWay() {
        return foundWay;
    }
}
