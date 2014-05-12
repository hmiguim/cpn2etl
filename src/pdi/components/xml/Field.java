package pdi.components.xml;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Field {
    
    private String id;
    private String enable;
    private String name;
    
    public Field() { }
    
    public Field(String id, String enable, String name) {
        this.id = id;
        this.enable = enable;
        this.name = name;
    }
    
    public Field(Field f) {
        this.id = f.getId();
        this.enable = f.getEnable();
        this.name = f.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.enable);
        hash = 67 * hash + Objects.hashCode(this.name);
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
        final Field other = (Field) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.enable, other.enable)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Field{" + "id=" + id + ", enable=" + enable + ", name=" + name + '}';
    }
    
    @Override
    public Field clone() {
        return new Field(this);
    }
}
