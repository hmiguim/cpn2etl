package transformation.pattern.constraints.connections;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Keyword {
    
    private String from;
    private String to;
   
    public Keyword() { }
    
    public Keyword(String from, String to) {
        this.from = from;
        this.to = to;
    }
    
    public Keyword(Keyword key) {
        this.from = key.getFrom();
        this.to = key.getTo();
    }
    
    public String getFrom() {
        return this.from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getTo() {
        return this.to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.from);
        hash = 59 * hash + Objects.hashCode(this.to);
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
        final Keyword other = (Keyword) obj;
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        return Objects.equals(this.to, other.to);
    }

    @Override
    public String toString() {
        return "Keyword{" + "from=" + from + ", to=" + to + '}';
    }
    
    
    @Override
    public Keyword clone() {
        return new Keyword(this);
    }
    
}
