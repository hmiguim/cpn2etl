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
    private String filename;
    
    
    public MappingComponent() {
        
    }
    
    public MappingComponent(String cpnElement, String kettleElement, String xloc, String yloc) {
        this.cpnElement = cpnElement;
        this.kettleElement = kettleElement;
        this.xloc = xloc;
        this.yloc = yloc;
    }
    
    public MappingComponent(String cpnElement, String kettleElement, String xloc, String yloc, String filename) {
        this.cpnElement = cpnElement;
        this.kettleElement = kettleElement;
        this.xloc = xloc;
        this.yloc = yloc;
        this.filename = filename;
    }
    
    public MappingComponent(MappingComponent map) {
        this.cpnElement = map.getCpnElement();
        this.kettleElement = map.getKettleElement();
        this.xloc = map.getXloc();
        this.yloc = map.getYloc();
        this.filename = map.getFilename();
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.cpnElement);
        hash = 17 * hash + Objects.hashCode(this.kettleElement);
        hash = 17 * hash + Objects.hashCode(this.xloc);
        hash = 17 * hash + Objects.hashCode(this.yloc);
        hash = 17 * hash + Objects.hashCode(this.filename);
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
        if (!Objects.equals(this.yloc, other.yloc)) {
            return false;
        }
        return Objects.equals(this.filename, other.filename);
    }

    @Override
    public String toString() {
        return "MappingComponent{" + "cpnElement=" + cpnElement + ", kettleElement=" + kettleElement + ", xloc=" + xloc + ", yloc=" + yloc + ", filename=" + filename + '}';
    }
    
    @Override
    public MappingComponent clone() {
        return new MappingComponent(this);
    }
    
}
