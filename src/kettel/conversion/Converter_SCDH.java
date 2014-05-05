package kettel.conversion;

import cpn.Arc;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;
import utils.Utilities;

/**
 *
 * @author hmg
 */
public class Converter_SCDH extends ConversionBuilder {

    /**
     * Map various elements from the CPN model to Kettle. This one in particular
     * correspond to the Slow Changing Dimension with History
     *
     * @return An ArrayList of {@link MappingComponent}
     */
    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        Collection<Transition> trans = this.pattern.getSubPageInfo().getPage().getTransitions().values();

        for (Transition t : trans) {
            if (t.haveSubPage()) {
                String[] normalizeAxis = Utilities.normalizeAxis(t.getPosX(), t.getPosY());
                map = new MappingComponent(t.getText(), "TransExecutor", normalizeAxis[0], normalizeAxis[1]);
                maps.add(map);
            }
        }

        return maps;
    }

    /**
     * Map the various connections between the kettle elements mapped from the
     * CPN pattern This in particular correspond to the Slow Changing Dimension
     * with History
     *
     * @return An ArrayList of {@link MappingOrder}
     */
    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = this.mapping.getComponents();

        Collection<Arc> arcs = this.pattern.getSubPageInfo().getPage().getArcs().values();

        Graph graph = new Graph();

        graph.construct(this.pattern.getSubPageInfo().getPage());

        this.pattern.getSubPageInfo().getPage().setGraph(graph);

        for (MappingComponent i : components) {
            for (MappingComponent j : components) {

                if (!i.getCpnElement().equals(j.getCpnElement())) {

                    boolean connected = this.pattern.getSubPageInfo().getPage().connected(i.getCpnElement(), j.getCpnElement());

                    if (connected) {
                        MappingOrder order = new MappingOrder(i, j);
                        orders.add(order);
                    }
                }
            }
        }

        for (MappingOrder o : orders) {
            System.out.println("From: " + o.getFrom().getCpnElement() + " To: " + o.getTo().getCpnElement());
        }

        System.exit(0);

        return orders;
    }

    /**
     * Method that calls the methods {@code convertComponents} and
     * {@code convertOrders}, but first verify if the pattern is in fact a SCD/H
     * pattern. In case it be convert the components and the connections
     * otherwise don't convert and returns {@code false}
     *
     * @return {@code true} or {@code false} depending if the pattern can be
     * converted
     */
    @Override
    public boolean convert() {

        this.mapping.setComponents(this.convertComponents());

        this.convertOrders();

        return true;
    }

}
