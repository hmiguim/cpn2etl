/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpn;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Cpn {
    
    private HashMap<String,Arc> arcs = new HashMap<>();
    private HashMap<String,Place> places = new HashMap<>();
    private HashMap<String,Place> places_port = new HashMap<>();
    private HashMap<String,Transition> transitions = new HashMap<>();
    
    
    public Cpn() {  }

    public Cpn(HashMap<String, Arc> arcs, HashMap<String, Place> places, HashMap<String, Transition> transitions) {
        this.arcs = arcs;
        this.places = places;
        this.transitions = transitions;
    }

    public Cpn(HashMap<String, Arc> arcs, HashMap<String, Place> places, HashMap<String, Transition> transitions, HashMap<String, Place> places_port) {
        this.arcs = arcs;
        this.places = places;
        this.transitions = transitions;
        this.places_port = places_port;
    }
    
    public Cpn(Cpn cpn) {
        this.arcs = cpn.getArcs();
        this.places = cpn.getPlaces();
        this.transitions = cpn.getTransitions();
        this.places_port = cpn.getPlacesPort();
    }
    
    public HashMap<String, Arc> getArcs() {
        return arcs;
    }

    public HashMap<String, Place> getPlaces() {
        return places;
    }

    public HashMap<String, Transition> getTransitions() {
        return transitions;
    }
    
    public HashMap<String,Place> getPlacesPort() {
        return places_port;
    }

    public void setArcs(HashMap<String, Arc> arcs) {
        this.arcs = arcs;
    }

    public void setPlaces(HashMap<String, Place> places) {
        this.places = places;
    }

    public void setTransitions(HashMap<String, Transition> transitions) {
        this.transitions = transitions;
    }
    
    public void setPlacesPort(HashMap<String, Place> places_port) {
        this.places_port = places_port;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.arcs);
        hash = 47 * hash + Objects.hashCode(this.places);
        hash = 47 * hash + Objects.hashCode(this.places_port);
        hash = 47 * hash + Objects.hashCode(this.transitions);
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
        final Cpn other = (Cpn) obj;
        if (!Objects.equals(this.arcs, other.arcs)) {
            return false;
        }
        if (!Objects.equals(this.places, other.places)) {
            return false;
        }
        if (!Objects.equals(this.places_port, other.places_port)) {
            return false;
        }
        return Objects.equals(this.transitions, other.transitions);
    }  
    
    @Override
    public Cpn clone() {
        return new Cpn(this);
    }
}
