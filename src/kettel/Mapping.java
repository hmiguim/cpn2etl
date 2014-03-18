package kettel;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Mapping {
    
    private String cpnElement;
    private String kettleElement;
    
    
    public Mapping() {
        
    }
    
    public Mapping(String cpnElement, String kettleElement ) {
        this.cpnElement = cpnElement;
        this.kettleElement = kettleElement;
    }
    
    public Mapping(Mapping map) {
        this.cpnElement = map.getCpnElement();
        this.kettleElement = map.getKettleElement();
    }

    public String getCpnElement() {
        return cpnElement;
    }

    public void setCpnElement(String cpnElement) {
        this.cpnElement = cpnElement;
    }

    public String getKettleElement() {
        return kettleElement;
    }

    public void setKettleElement(String kettleElement) {
        this.kettleElement = kettleElement;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.cpnElement);
        hash = 31 * hash + Objects.hashCode(this.kettleElement);
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
        final Mapping other = (Mapping) obj;
        if (!Objects.equals(this.cpnElement, other.cpnElement)) {
            return false;
        }
        return Objects.equals(this.kettleElement, other.kettleElement);
    }

    @Override
    public String toString() {
        return "Mapping{" + "cpnElement=" + cpnElement + ", kettleElement=" + kettleElement + '}';
    }
    
    @Override
    public Mapping clone() {
        return new Mapping(this);
    }
    
}
