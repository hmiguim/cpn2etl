package transformation.pattern.activity;

import cpn.Page;
import cpn.Place;
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
public class SCD_DeleteRecordActivity extends PatternActivityBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        ArrayList<Object> objs = Helper.normalize(this.activity.getSubPageInfo().getPage().getPlaces().values(), this.activity.getSubPageInfo().getPage().getTransitions().values());

        ArrayList<Place> places = Helper.getPlaces(objs);
        ArrayList<Transition> transitions = Helper.getTransitions(objs);

        for (Place p : places) {
            switch (p.getText().toLowerCase()) {
                case "verified audit records":
                    map = new MappingComponent(p.getText(), "TableInput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "etl log":
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "slowly changing dim":
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "lookup table":
                    map = new MappingComponent(p.getText(), "DBLookup", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
            }

        }

        for (Transition t : transitions) {

            if (t.getText().toLowerCase().equals("select record to delete")) {
                map = new MappingComponent(t.getText(), "SwitchCase", Helper.removePointZero(t.getPosX()), Helper.removePointZero(t.getPosY()));
                maps.add(map);
            }
            
        }

        return maps;
    }

    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = this.mapping.getComponents();

        Page p = this.activity.getSubPageInfo().getPage();
        
        Graph graph = new Graph();

        graph.construct(p);

        p.setGraph(graph);
        
        ArrayList<String> test = new ArrayList<>();

        test.add("slowlychangingdimetllog");
        test.add("slowlychangingdimlookuptable");
        
        for (MappingComponent i : components) {
            for (MappingComponent j : components) {
                if (!i.getCpnElement().equals(j.getCpnElement())) {
                    List connected = p.connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        if (connected.size() < 4) {

                            String s = i.getCpnElement().toLowerCase().replace(" ", "");
                            s += j.getCpnElement().toLowerCase().replace(" ", "");
                            
                            if (!test.contains(s)) {
                                MappingOrder order = new MappingOrder(i, j);
                                orders.add(order);
                            }
                            
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
