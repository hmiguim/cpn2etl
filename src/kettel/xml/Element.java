package kettel.xml;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Element {
 
    private String tag;
    private String contextText;
    
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.tag);
        hash = 79 * hash + Objects.hashCode(this.contextText);
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
        return Objects.equals(this.contextText, other.contextText);
    }

    @Override
    public String toString() {
        return "Element{" + "tag=" + tag + ", contextText=" + contextText + '}';
    }
    
    @Override
    public Element clone() {
        return new Element(this);
    }
}
