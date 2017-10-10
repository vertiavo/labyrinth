import java.util.*;

/**
 * Created by Piotrek on 29/05/2017.
 */
public class BfsAlgorithm {

    public Queue<Vertex> traverse(Vertex root) {
        Queue<Vertex> Q = new LinkedList<>();
        HashSet<Vertex> S = new HashSet<>();
        Queue<Vertex> ret = new LinkedList<>();

        Q.add(root);
        S.add(root);
        Vertex doc=null;

        while (Q.size() > 0) {
            Vertex p = Q.poll();

            if (p.isEnd()) {
                doc = p;
                break;
            }

            for (Vertex vertex : p.neighbours) {
                if (!S.contains(vertex)) {
                    vertex.addEdgeTo(p);
                    Q.add(vertex);
                    S.add(vertex);
                }
            }
        }
        ret.add(doc);

        while(doc!=root){
            ret.add(doc.edgeTo.get(0));
            doc=doc.edgeTo.get((0));
        }

        return ret;

    }

}

