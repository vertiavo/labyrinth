import java.util.*;

/**
 * Created by Piotrek on 29/05/2017.
 */
public class BfsAlgorithm {


        public Queue<Vertex> Traverse(Vertex root)
        {
            Queue<Vertex> Q = new LinkedList<Vertex>();
            HashSet<Vertex> S = new HashSet<Vertex>();
          // LinkedList<Vertex>S=new LinkedList<Vertex>();
            Queue<Vertex> ret = new LinkedList<Vertex>();
            Q.add(root);
            S.add(root);
            Vertex doc=null;
            while (Q.size() > 0) {
                Vertex p = Q.poll();
                if (p.isEnd()) {
                    doc = p;
                    break;
                }
                for (Vertex vertex : p.Neighbours) {
                    if (!S.contains(vertex)) {
                        vertex.AddEdgeTo(p);
                        Q.add(vertex);
                        S.add(vertex);
                    }
                }
            }
            ret.add(doc);
            while(doc!=root){
                ret.add(doc.EdgeTo.get(0));
                doc=doc.EdgeTo.get((0));
            }
           // ret.add(root);
           // Collections.reverse(Q);
            return ret;


        }

    }

