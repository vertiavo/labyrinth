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
    private int start;
    private int finish;
    private int[][] guiArray;
    private int[][] algorithmArray;
    private boolean[] visited;
    private List<Integer> result;

    public Algorithms(int[][] guiArray) {
        this.guiArray = guiArray;
        this.n = guiArray.length;
        this.algorithmArray = new int[n*n][4];
        this.visited = new boolean[n*n];
        this.result = new ArrayList<>();

        Arrays.fill(visited, false);
        //Arrays.fill(algorithmArray, 1);

        // Wypelnienie tablicy list incydencji, znalezienie punktow start i koniec
        initialization();

        // Uruchomienie algorytmu z punktu start
        DFS(start);
    }

    private void initialization() {
        int line = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (guiArray[i][j] == 2) {
                    start = line;
                } else if (guiArray[i][j] == 3) {
                    finish = line;
                }

                // Pierwszy adres w tablicy algorithmArray = wiersz
                // Drugi adres w tablicy algorithmArray = odpowiedni kierunek tak jak nizej
                //      0
                //  1   x   2
                //      3

                if (i == 0 && j == 0) {                             // lewy gorny rog
                    algorithmArray[line][0] = 1;
                    algorithmArray[line][1] = 1;
                    algorithmArray[line][2] = guiArray[i][j+1];
                    algorithmArray[line][3] = guiArray[i+1][j];
                } else if (i == 0 && j < (n*n - 1)) {               // gorna linia
                    algorithmArray[line][0] = 1;
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = guiArray[i][j+1];
                    algorithmArray[line][3] = guiArray[i+1][j];
                } else if (i == 0 && j == (n*n - 1)) {              // prawy gorny rog
                    algorithmArray[line][0] = 1;
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = 1;
                    algorithmArray[line][3] = guiArray[i+1][j];
                } else if (i < (n*n-1) && j == 0) {                 // lewy brzeg
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = 1;
                    algorithmArray[line][2] = guiArray[i][j + 1];
                    algorithmArray[line][3] = guiArray[i + 1][j];
                } else if (i < (n*n-1) && j < (n*n-1)) {            // poza brzegami
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = guiArray[i][j + 1];
                    algorithmArray[line][3] = guiArray[i + 1][j];
                } else if (i < (n*n-1) && j == (n*n-1)) {           // prawy brzeg
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = 1;
                    algorithmArray[line][3] = guiArray[i + 1][j];
                } else if (i == (n*n-1) && j == 0) {                // lewy dolny rog
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = 1;
                    algorithmArray[line][2] = guiArray[i][j + 1];
                    algorithmArray[line][3] = 1;
                } else if (i == (n*n-1) && j < (n*n-1)) {           // dolna linia
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = guiArray[i][j + 1];
                    algorithmArray[line][3] = 1;
                } else if (i == (n*n-1) && j == (n*n-1)) {          // prawy dolny rog
                    algorithmArray[line][0] = guiArray[i-1][j];
                    algorithmArray[line][1] = guiArray[i][j-1];
                    algorithmArray[line][2] = 1;
                    algorithmArray[line][3] = 1;
                }

                line++;
            }
        }
    }

    private void DFS(int v) {
        this.visited[v] = true;
        result.add(v);

        if (v == finish)
            return;

        for (int i = 0; i < 4; i++) {
            if ((this.algorithmArray[v][i] == 0 || this.algorithmArray[v][i] == 2) && !this.visited[i])
                DFS(i);
        }
    }

    public List<Integer> getResult() {
        return result;
    }
}
