package sample;

import org.junit.jupiter.api.Test;
import sample.Algorithm;

/**
 * Created by vertiavo on 16.05.17.
 */
public class AlgorithmTest {

    @Test
    public void test1() {
        int[][] array = {
               //1  2  3  4  5  6  7  8  9  10
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // 1
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, // 2
                {0, 1, 0, 0, 0, 1, 1, 0, 0, 0}, // 3
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // 4
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // 5
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 6
                {0, 0, 1, 0, 0, 1, 0, 0, 0, 1}, // 7
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 8
                {0, 0, 1, 0, 1, 0, 0, 1, 0, 0}, // 9
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0} // 10
        };

        Algorithm algorithm = new Algorithm(10, array);
        algorithm.DFS(0);

        // Oczekiwany output: 0, 1, 4, 7, 3, 5, 8, 2, 6, 9
        System.out.println(algorithm.getResult());
    }

}