package cpn;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Place {

    private String id;
    private double posX;
    private double posY;
    private String text;
    private String type;
    private Port portInfo;
    private boolean port;
    
    public Place() {
    }

    public Place(String id, double posX, double posY, String text, String type, Port portInfo, boolean port) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.type = type;
        this.portInfo = portInfo;
    }

    public Place(Place p) {
        this.id = p.getId();
        this.posX = p.getPosX();
        this.posY = p.getPosY();
        this.text = p.getText();
        this.type = p.getType();
        this.portInfo = p.getPort();
        this.port = p.havePort();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Port getPort() {
        return portInfo;
    }

    public void setPortInfo(Port port) {
        this.portInfo = port;
    }

    public boolean havePort() {
        return port;
    }

    public void setPort(boolean port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.posX) ^ (Double.doubleToLongBits(this.posX) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.posY) ^ (Double.doubleToLongBits(this.posY) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.text);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.portInfo);
        hash = 29 * hash + (this.port ? 1 : 0);
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
        final Place other = (Place) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (Double.doubleToLongBits(this.posX) != Double.doubleToLongBits(other.posX)) {
            return false;
        }
        if (Double.doubleToLongBits(this.posY) != Double.doubleToLongBits(other.posY)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.portInfo, other.portInfo)) {
            return false;
        }
        return this.port == other.port;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        str.append("PosX: ").append(this.posX).append(", ");
        str.append("PosY: ").append(this.posY).append(", ");
        str.append("Text: " + "\'").append(this.text).append("\', ");
        if (this.havePort()) {
            str.append("Type: " + "\'").append(this.type).append("\',");
            str.append("Port: {").append(this.portInfo.toString()).append("}];");
        } else {
            str.append("Type: " + "\'").append(this.type).append("\'];");
        }

        return str.toString();
    }

    @Override
    public Place clone() {
        return new Place(this);
    }
}
