package transformation.pattern;

import cpn.Place;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import transformation.pattern.constraints.PatternConstraintBuilder;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class SCDH_Pattern extends PatternBuilder {

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
        
        ArrayList<Object> normalized = Helper.normalize(this.pattern.getSubPageInfo().getPage());
        
        ArrayList<Transition> trans = Helper.getTransitions(normalized);
        
        for (Transition t : trans) {
            if (t.isSubPage()) {
                map = new MappingComponent(t.getText(), "TransExecutor", Helper.removePointZero(t.getPosX()), Helper.removePointZero(t.getPosY()) ,t.getSubPageInfo().getName());
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

        Graph graph = new Graph();

        graph.construct(this.pattern.getSubPageInfo().getPage());

        this.pattern.getSubPageInfo().getPage().setGraph(graph);

        for (MappingComponent i : components) {
            for (MappingComponent j : components) {

                if (!i.getCpnElement().equals(j.getCpnElement())) {

                    List connected = this.pattern.getSubPageInfo().getPage().connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        MappingOrder order = new MappingOrder(i, j);
                        orders.add(order);
                    }
                }
            }
        }

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
        
        PatternConstraintBuilder scd = this.patternConstraintFactory.newSCDHConstraintBuilder();

        this.patternConstraintDirector.setConstraintBuilder(scd);
        this.patternConstraintDirector.constructConstraint();

        if (!this.patternConstraintDirector.verifyConstraint(this.pattern.getSubPageInfo().getPage())) {
            return false;
        }

        this.mapping.setComponents(this.convertComponents());
        this.mapping.setOrder(this.convertOrders());

        return true;
    }

}
