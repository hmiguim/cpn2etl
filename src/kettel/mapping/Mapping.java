package kettel.mapping;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author hmg
 */
public class Mapping {

    private ArrayList<MappingComponent> components;
    private ArrayList<MappingOrder> orders;

    public Mapping() {
    }

    public Mapping(ArrayList<MappingComponent> components, ArrayList<MappingOrder> orders) {
        this.components = components;
        this.orders = orders;
    }

    public Mapping(Mapping map) {
        this.components = map.getComponents();
        this.orders = map.getOrders();
    }

    public ArrayList<MappingComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<MappingComponent> components) {
        this.components = components;
    }

    public ArrayList<MappingOrder> getOrders() {
        return orders;
    }

    public void setOrder(ArrayList<MappingOrder> order) {
        this.orders = order;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.components);
        hash = 67 * hash + Objects.hashCode(this.orders);
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
        if (!Objects.equals(this.components, other.components)) {
            return false;
        }
        return Objects.equals(this.orders, other.orders);
    }

    @Override
    public String toString() {
        return "Mapping{" + "components=" + components + ", orders=" + orders + '}';
    }

    @Override
    public Mapping clone() {
        return new Mapping(this);
    }

    public MappingComponent findComponent(String text) {
        for (MappingComponent c : this.components) {
            if (c.getCpnElement().toLowerCase().contains(text)) {
                return c;
            }
        }
        return null;
    }
}
