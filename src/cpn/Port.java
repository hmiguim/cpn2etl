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
    private Place place;

    public Port() {
    }

    public Port(String id, String type, Place place) {
        this.id = id;
        this.type = type;
        this.place = place;
    }

    public Port(Port p) {
        this.id = p.getId();
        this.type = p.getType();
        this.place = p.getPlace();
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + Objects.hashCode(this.place);
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
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.place, other.place);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        if (this.place == null) {
            str.append("Type: " + "\'").append(this.type).append("\']");
        } else {
            str.append("Type: " + "\'").append(this.type).append("\', ");
            str.append("Place: {").append(this.place.toString()).append("}];");
        }
        return str.toString();
    }

    @Override
    public Port clone() {
        return new Port(this);
    }
}
