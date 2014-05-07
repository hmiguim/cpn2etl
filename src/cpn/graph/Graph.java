package cpn.graph;

import cpn.Arc;
import cpn.Page;
import java.util.Collection;
import java.util.Objects;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author hmg
 */
public class Graph {

    DirectedGraph<String, DefaultEdge> graph;

    public Graph() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public Graph(Graph graph) {
        this.graph = graph.getGraph();
    }

    public DirectedGraph getGraph() {
        return this.graph;
    }

    public void setPaths(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.graph);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graph other = (Graph) obj;
        return Objects.equals(this.graph, other.graph);
    }

    @Override
    public Graph clone() {
        return new Graph(this);
    }

    public void construct(Page p) {

        Collection<Arc> arcs = p.getArcs().values();

        for (Arc a : arcs) {

            switch (a.getOrientation()) {
                case "PtoT":

                    String place = a.getPlaceEnd().getText() ;
                    String trans  = a.getTransEnd().getText();
                    
                    this.graph.addVertex(place);
                    this.graph.addVertex(trans);

                    this.graph.addEdge(place,trans);

                    break;
                case "TtoP":

                    this.graph.addVertex(a.getTransEnd().getText());
                    this.graph.addVertex(a.getPlaceEnd().getText());

                    this.graph.addEdge(a.getTransEnd().getText(), a.getPlaceEnd().getText());

                    break;
                case "BOTHDIR":
                    this.graph.addVertex(a.getTransEnd().getText());
                    this.graph.addVertex(a.getPlaceEnd().getText());

                    this.graph.addEdge(a.getTransEnd().getText(), a.getPlaceEnd().getText());
                    break;
            }

        }
    }
}
