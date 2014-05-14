package transformation.pattern.activity;

import cpn.Place;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pdi.components.notepad.Notepad;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class AuditDataVerificationPatternActivity extends PatternActivityBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        Collection<Place> places = this.activity.getSubPageInfo().getPage().getPlaces().values();
        Collection<Transition> transitions = this.activity.getSubPageInfo().getPage().getTransitions().values();

        places = Helper.normalizePlaces(places);

        for (Place p : places) {
            switch (p.getText().toLowerCase()) {
                case "audit records": {
                    map = new MappingComponent(p.getText(), "TableInput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                }
                case "error log": {
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                }
                case "quarantine table": {
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                }
                case "etl log": {
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                }
                case "verified audit records": {
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                }
            }
        }

        transitions = Helper.normalizeTransitions(transitions);
        for (Transition t : transitions) {
            if (t.getText().toLowerCase().equals("audit data verification")) {
                map = new MappingComponent(t.getText(), "Validator", Helper.removePointZero(t.getPosX()), Helper.removePointZero(t.getPosY()));
                maps.add(map);
            }
        }

        return maps;
    }

    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = this.mapping.getComponents();

        Graph graph = new Graph();

        graph.construct(this.activity.getSubPageInfo().getPage());

        this.activity.getSubPageInfo().getPage().setGraph(graph);

        for (MappingComponent i : components) {
            for (MappingComponent j : components) {
                if (!i.getCpnElement().equals(j.getCpnElement())) {
                    List connected = this.activity.getSubPageInfo().getPage().connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        if (connected.size() == 1) {

                            MappingOrder order = new MappingOrder(i, j);
                            orders.add(order);

                            if (orders.contains(new MappingOrder(j, i))) {

                                String[] middlePoint = Helper.middlePoint(i.getXloc(), i.getYloc(), j.getXloc(), j.getYloc());

                                Notepad note = new Notepad("Warning", middlePoint[0], middlePoint[1]);

                                this.notepads.add(note);

                            }
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
