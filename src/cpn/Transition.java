package cpn;

import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Transition {

    private String id;
    private double posX;
    private double posY;
    private String text;
    private boolean subPage;
    private SubPage subPageInfo;
    
    public Transition() {
    }

    public Transition(String id, double posX, double posY, String text, boolean subpage) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.subPage = subpage;
    }

    public Transition(String id, double posX, double posY, String text, boolean subpage, SubPage subPageInfo) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.subPage = subpage;
        this.subPageInfo = subPageInfo;
    }

    public Transition(Transition t) {
        this.id = t.getId();
        this.posX = t.getPosX();
        this.posY = t.getPosY();
        this.text = t.getText();
        this.subPage = t.isSubPage();
        this.subPageInfo = t.getSubPageInfo();
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

    public boolean isSubPage() {
        return subPage;
    }

    public void setSubPage(boolean subPage) {
        this.subPage = subPage;
    }

    public SubPage getSubPageInfo() {
        return subPageInfo;
    }

    public void setSubPageInfo(SubPage subPageInfo) {
        this.subPageInfo = subPageInfo;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.posX) ^ (Double.doubleToLongBits(this.posX) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.posY) ^ (Double.doubleToLongBits(this.posY) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.text);
        hash = 71 * hash + (this.subPage ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.subPageInfo);
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
        final Transition other = (Transition) obj;
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
        if (this.subPage != other.subPage) {
            return false;
        }
        return Objects.equals(this.subPageInfo, other.subPageInfo);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[ID: " + this.id + ", ");
        str.append("PosX: ").append(this.posX).append(", ");
        str.append("PosY: ").append(this.posY).append(", ");
        str.append("Text: " + "\'").append(this.text).append("\']");

        return str.toString();
    }

    @Override
    public Transition clone() {
        return new Transition(this);
    }
}
