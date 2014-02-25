/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpn;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Page {

    private String id;
    private String name;
    private LinkedHashMap<String, Arc> arcs;
    private LinkedHashMap<String, Place> places;
    private LinkedHashMap<String, Transition> transitions;

    public Page() {
    }

    public Page(String id, String name, LinkedHashMap<String, Arc> arcs, LinkedHashMap<String, Place> places, LinkedHashMap<String, Transition> transitions) {
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

    public LinkedHashMap<String, Arc> getArcs() {
        return arcs;
    }

    public void setArcs(LinkedHashMap<String, Arc> arcs) {
        this.arcs = arcs;
    }

    public LinkedHashMap<String, Place> getPlaces() {
        return places;
    }

    public void setPlaces(LinkedHashMap<String, Place> places) {
        this.places = places;
    }

    public LinkedHashMap<String, Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(LinkedHashMap<String, Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.arcs);
        hash = 13 * hash + Objects.hashCode(this.places);
        hash = 13 * hash + Objects.hashCode(this.transitions);
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
}
