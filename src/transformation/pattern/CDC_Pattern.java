package transformation.pattern;

import cpn.Page;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import pdi.components.notepad.Notepad;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
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

        ArrayList<Object> normalize = Helper.normalize(this.pattern.getSubPageInfo().getPage().getPlaces().values(), this.pattern.getSubPageInfo().getPage().getTransitions().values());

        ArrayList<Transition> transitions = Helper.getTransitions(normalize);

        for (Transition t : transitions) {
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

        ArrayList<String> test = new ArrayList<>();

        test.add("readtransactionlogupdateaudittables");
        
        for (MappingComponent i : components) {
            for (MappingComponent j : components) {

                if (!i.getCpnElement().equals(j.getCpnElement())) {

                    List connected = p.connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        String s = i.getCpnElement().toLowerCase().replace(" ", "");
                            s += j.getCpnElement().toLowerCase().replace(" ", "");
                            
                            if (!test.contains(s)) {
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
