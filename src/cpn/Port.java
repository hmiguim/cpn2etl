/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpn;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Port {
    
    private String id;
    private String type;

    public Port() {
        this.id = "-1";
    }
    
    public Port(String id, String type) {
        this.id = id;
        this.type = type;
    }
    
    public Port(Port t) {
        this.id = t.getId();
        this.type = t.getType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.type);
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
        final Port other = (Port) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        str.append("Type: " + "\'").append(this.type).append("\']");

        return str.toString();
    }
    
    @Override
    public Port clone() {
        return new Port(this);
    }
}
