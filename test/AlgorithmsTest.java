import org.junit.Test;

/**
 * Created by vertiavo on 21.05.17.
 */
public class AlgorithmsTest {

    @Test
    public void test1() {
        int tab[][] = {
                {2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 1},
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 ,1, 0, 1},
                {1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1 ,1, 1, 1, 0, 1},
                {1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0 ,0, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1 ,0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1 ,0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 ,1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 3},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1, 1, 1}
        };

        int tab2[][] = {
                {2, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0 ,0, 0, 0, 0, 3},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1}
        };

        Algorithms algorithm = new Algorithms(tab);
    }

}