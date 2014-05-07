package pdi.mapping;

import java.util.ArrayList;

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

    /**
     * Find the specific component for the {@code text} parameter
     *
     * @param text text to be search
     * @return The MappingComponent that contains the {@code text} parameter. In
     * case of none matching returns null
     */
    public MappingComponent findComponent(String text) {
        for (MappingComponent c : this.components) {
            if (c.getCpnElement().toLowerCase().contains(text)) {
                return c;
            }
        }
        return null;
    }
}
