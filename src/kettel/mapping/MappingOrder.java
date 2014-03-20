/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel.mapping;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class MappingOrder {
    
    private MappingComponent from;
    private MappingComponent to;
    
    public MappingOrder() {}
    
    public MappingOrder(MappingComponent from, MappingComponent to) {
        this.from = from;
        this.to = to;
    }
    
    public MappingOrder(MappingOrder map) {
        this.from = map.getFrom();
        this.to = map.getTo();
    }

    public MappingComponent getFrom() {
        return from;
    }

    public void setFrom(MappingComponent from) {
        this.from = from;
    }

    public MappingComponent getTo() {
        return to;
    }

    public void setTo(MappingComponent to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.from);
        hash = 41 * hash + Objects.hashCode(this.to);
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
        final MappingOrder other = (MappingOrder) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        return Objects.equals(this.to, other.to);
    }

    @Override
    public String toString() {
        return "MappingOrder{" + "from=" + from + ", to=" + to + '}';
    }
    
    @Override
    public MappingOrder clone() {
        return new MappingOrder(this);
    }
    
}
