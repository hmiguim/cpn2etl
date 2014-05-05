package cpn;

import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.jgrapht.alg.BellmanFordShortestPath;

/**
 *
 * @author hmg
 */
public class Page {

    private String id;
    private String name;
    private HashMap<String, Arc> arcs;
    private HashMap<String, Place> places;
    private HashMap<String, Transition> transitions;
    private Graph graph;

    public Page() {
        this.graph = new Graph();
    }

    public Page(String id, String name, HashMap<String, Arc> arcs, HashMap<String, Place> places, HashMap<String, Transition> transitions) {
        this.id = id;
        this.name = name;
        this.arcs = arcs;
        this.places = places;
        this.transitions = transitions;
    }

    public Page(Page p) {
        this.id = p.getId();
        this.name = p.getName();
        this.arcs = p.getArcs();
        this.places = p.getPlaces();
        this.transitions = p.getTransitions();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Arc> getArcs() {
        return arcs;
    }

    public void setArcs(HashMap<String, Arc> arcs) {
        this.arcs = arcs;
    }

    public HashMap<String, Place> getPlaces() {
        return places;
    }

    public void setPlaces(HashMap<String, Place> places) {
        this.places = places;
    }

    public HashMap<String, Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(HashMap<String, Transition> transitions) {
        this.transitions = transitions;
    }
    
    public Graph getGraph() {
        return this.graph;
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.arcs);
        hash = 13 * hash + Objects.hashCode(this.places);
        hash = 13 * hash + Objects.hashCode(this.transitions);
        hash = 13 * hash + Objects.hashCode(this.graph);
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
        final Page other = (Page) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.arcs, other.arcs)) {
            return false;
        }
        if (!Objects.equals(this.places, other.places)) {
            return false;
        }
        if (!Objects.equals(this.graph, other.graph)) {
            return false;
        }

        return Objects.equals(this.transitions, other.transitions);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        str.append("Name: " + "\'").append(this.name).append("\']");
        return str.toString();
    }

    @Override
    public Page clone() throws CloneNotSupportedException {
        return new Page(this);
    }

    /**
     * Method that produces a HashMap with the pair {@code <String,Transition>}
     * from the {@link Page} {@code p} parameter, given all the modules that
     * page have. In case of the inexistence of modules on that page this method
     * returns a 0-length HashMap.
     *
     * @return A HashMap with the results of the dissection
     */
    public ArrayList<Transition> getModulesPerPage() {

        ArrayList<Transition> results = new ArrayList<>();

        for (Transition t : this.getTransitions().values()) {
            if (t.haveSubPage()) {
                results.add(t);
            }
        }

        return results;
    }

    public boolean connected(String a, String b) {
        
        List findPathBetween = BellmanFordShortestPath.findPathBetween(this.graph.getGraph(), a, b);
        
        if (findPathBetween == null) return false;
        else {
            return !findPathBetween.isEmpty();
        }
        
    }
}
