/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Cpn {

    private LinkedHashMap<String, Page> pages;

    public Cpn() { }

    public Cpn(LinkedHashMap<String, Page> pages) {
        this.pages = pages;
    }

    public Cpn(Cpn cpn) {
        this.pages = cpn.getPages();
    }

    public LinkedHashMap<String, Page> getPages() {
        return pages;
    }

    public void setPages(LinkedHashMap<String, Page> pages) {
        this.pages = pages;
    }
    
    public LinkedHashMap<String,Transition> getModules() {
        
        LinkedHashMap<String,Transition> results = new LinkedHashMap<>();
        
        for(Entry<String,Page> entry : pages.entrySet()) {
            LinkedHashMap<String,Transition> trans = entry.getValue().getTransitions();
            
            for(Entry<String,Transition> entry_trans : trans.entrySet()) {
                if (entry_trans.getValue().haveSubPage()) {
                    results.put(entry_trans.getValue().getId(), entry_trans.getValue());
                }
            }
        }
        
        return results;
    }
    
    public Stats stats() {
        
        Stats cpnStats = new Stats();
        
        cpnStats.setPages(this.pages.size());
        
        int count_arcs = 0;
        int count_places = 0;
        int count_transitions = 0;
        int count_places_in = 0;
        int count_places_out = 0;
        int count_places_io = 0;
        int count_unique_places = 0;
        
        ArrayList<String> pageRefs = new ArrayList<>();
        
        Collection<Page> pages_collection = this.pages.values();
        
        for(Page p : pages_collection) {
            count_arcs += p.getArcs().size();
            count_places += p.getPlaces().size();
            
            Collection<Transition> transitions = p.getTransitions().values();
            
            for (Transition t : transitions) {
                if(t.haveSubPage()) {
                    if (!pageRefs.contains(t.getSubPageInfo().getPageRef())) pageRefs.add(t.getSubPageInfo().getPageRef());
                }
                else count_transitions++;
            }
            
            Collection<Place> places = p.getPlaces().values();
            for (Place place : places) {
                if (place.havePort()) {
                    switch (place.getPort().getType()) {
                        case "In" : count_places_in++;
                            break;
                        case "Out": count_places_out++;
                            break;
                        case "I/O": count_places_io++;
                            break;
                    }
                } else count_unique_places++;
            }
        }
        
        cpnStats.setArcs(count_arcs);
        cpnStats.setModules(this.getModules().size());
        cpnStats.setPlaces(count_places);
        cpnStats.setPlacesIO(count_places_io);
        cpnStats.setPlacesInput(count_places_in);
        cpnStats.setPlacesOutput(count_places_out);
        cpnStats.setSubPages(pageRefs.size());
        cpnStats.setTransitions(count_transitions);
        cpnStats.setUniquePlaces(count_unique_places);
        
        return cpnStats;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.pages);
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
        return Objects.equals(this.pages, other.pages);
    }

    @Override
    public Cpn clone() {
        return new Cpn(this);
    }
}
