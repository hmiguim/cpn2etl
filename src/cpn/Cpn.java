/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpn;

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
