package pdi.components.xml;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Element {
 
    private String tag;
    private String contextText;
    private ArrayList<Element> elememts;
    private boolean nested;
    
    public Element() { }
    
    public Element(String tag, String contextText) {
        this.tag = tag;
        this.contextText = contextText;
    }
    
    public Element(Element e) {
        this.tag = e.getTag();
        this.contextText = e.getContextText();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContextText() {
        return contextText;
    }

    public void setContextText(String contextText) {
        this.contextText = contextText;
    }

    public ArrayList<Element> getElements() {
        return this.elememts;
    }
    
    public void setElements(ArrayList<Element> elements) {
        this.elememts = elements;
    }
    
    public boolean isNested() {
        return this.nested;
    }
    
    public void setNested(boolean nested) {
        this.nested = nested;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.tag);
        hash = 71 * hash + Objects.hashCode(this.contextText);
        hash = 71 * hash + Objects.hashCode(this.elememts);
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
        final Element other = (Element) obj;
        if (!Objects.equals(this.tag, other.tag)) {
            return false;
        }
        if (!Objects.equals(this.contextText, other.contextText)) {
            return false;
        }
        return Objects.equals(this.elememts, other.elememts);
    }
     
    @Override
    public Element clone() {
        return new Element(this);
    }
}
