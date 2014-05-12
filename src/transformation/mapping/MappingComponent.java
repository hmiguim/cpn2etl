package transformation.mapping;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class MappingComponent {
    
    private String cpnElement;
    private String kettleElement;
    private String xloc;
    private String yloc;
    
    
    public MappingComponent() {
        
    }
    
    public MappingComponent(String cpnElement, String kettleElement, String xloc, String yloc) {
        this.cpnElement = cpnElement;
        this.kettleElement = kettleElement;
        this.xloc = xloc;
        this.yloc = yloc;
    }
    
    public MappingComponent(MappingComponent map) {
        this.cpnElement = map.getCpnElement();
        this.kettleElement = map.getKettleElement();
        this.xloc = map.getXloc();
        this.yloc = map.getYloc();
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

    public String getXloc() {
        return xloc;
    }

    public void setXloc(String xloc) {
        this.xloc = xloc;
    }

    public String getYloc() {
        return yloc;
    }

    public void setYloc(String yloc) {
        this.yloc = yloc;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.cpnElement);
        hash = 11 * hash + Objects.hashCode(this.kettleElement);
        hash = 11 * hash + Objects.hashCode(this.xloc);
        hash = 11 * hash + Objects.hashCode(this.yloc);
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
        final MappingComponent other = (MappingComponent) obj;
        if (!Objects.equals(this.cpnElement, other.cpnElement)) {
            return false;
        }
        if (!Objects.equals(this.kettleElement, other.kettleElement)) {
            return false;
        }
        if (!Objects.equals(this.xloc, other.xloc)) {
            return false;
        }
        return Objects.equals(this.yloc, other.yloc);
    }

    @Override
    public String toString() {
        return "Mapping{" + "cpnElement=" + cpnElement + ", kettleElement=" + kettleElement + ", xloc=" + xloc + ", yloc=" + yloc + '}';
    }
    
    @Override
    public MappingComponent clone() {
        return new MappingComponent(this);
    }
    
}
