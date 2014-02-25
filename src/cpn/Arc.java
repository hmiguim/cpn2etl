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
public class Arc {

    private String id;
    private String orientation; //PtoT: place to transition; TtoP: transition to place; BOTHDIR: both
    private Place placeEnd;
    private Transition transEnd;
    private String text;

    public Arc() { }

    public Arc(String id, String orientation, Place placeEnd, Transition transEnd, String text) {
        this.id = id;
        this.orientation = orientation;
        this.placeEnd = placeEnd;
        this.transEnd = transEnd;
        this.text = text;
    }

    public Arc(Arc a) {
        this.id = a.getId();
        this.orientation = a.getOrientation();
        this.placeEnd = a.getPlaceEnd();
        this.transEnd = a.getTransEnd();
        this.text = a.getText();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Place getPlaceEnd() {
        return placeEnd;
    }

    public void setPlaceEnd(Place placeEnd) {
        this.placeEnd = placeEnd;
    }

    public Transition getTransEnd() {
        return transEnd;
    }

    public void setTransEnd(Transition transEnd) {
        this.transEnd = transEnd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.orientation);
        hash = 37 * hash + Objects.hashCode(this.placeEnd);
        hash = 37 * hash + Objects.hashCode(this.transEnd);
        hash = 37 * hash + Objects.hashCode(this.text);
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
        final Arc other = (Arc) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.orientation, other.orientation)) {
            return false;
        }
        if (!Objects.equals(this.placeEnd, other.placeEnd)) {
            return false;
        }
        if (!Objects.equals(this.transEnd, other.transEnd)) {
            return false;
        }
        return Objects.equals(this.text, other.text);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        str.append("Orientation: ").append(this.orientation).append(", ");
        str.append("Text: \'").append(this.text).append("\', ");
        str.append("Place: {").append(this.placeEnd.toString()).append("}, ");
        str.append("Transtition: {").append(this.transEnd.toString()).append("}];");

        return str.toString();
    }

    @Override
    public Arc clone() {
        return new Arc(this);
    }
}