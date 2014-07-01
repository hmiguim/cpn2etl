package transformation.pattern;

import cpn.Page;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import transformation.pattern.constraints.connections.PatternConnectionConstraintCDC;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class CDC_Pattern extends PatternBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        ArrayList<Transition> trans = Helper.getTransitions(Helper.normalize(this.pattern.getSubPageInfo().getPage()));

        for (Transition t : trans) {
            if (t.isSubPage()) {
                map = new MappingComponent(t.getText(), "TransExecutor", Helper.removePointZero(t.getPosX()), Helper.removePointZero(t.getPosY()), t.getSubPageInfo().getName());
                maps.add(map);
            }
        }

        return maps;
    }

    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = this.mapping.getComponents();

        Page p = this.pattern.getSubPageInfo().getPage();

        Graph graph = new Graph();

        graph.construct(p);

        p.setGraph(graph);

        PatternConnectionConstraintCDC connectionConstraintCDC = this.patternConnectionConstraintFactory.newPatternConnectionConstraintCDC();

        connectionConstraintCDC.createConnectionConstraint();
        connectionConstraintCDC.buildConnectionConstraint();

        for (MappingComponent i : components) {
            for (MappingComponent j : components) {

                if (!i.getCpnElement().equals(j.getCpnElement())) {

                    List connected = p.connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {

                        if (!connectionConstraintCDC.verifyConnectionConstraint(i.getCpnElement(), j.getCpnElement())) {
                            MappingOrder order = new MappingOrder(i, j);
                            orders.add(order);
                        }
                    }
                }
            }
        }
        return orders;
    }

    @Override
    public boolean convert() {
        this.mapping.setComponents(this.convertComponents());

        this.mapping.setOrder(this.convertOrders());

        return true;
    }
}
