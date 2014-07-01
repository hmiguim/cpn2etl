package transformation.pattern.activity.SCD;

import cpn.Page;
import cpn.Place;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pdi.components.notepad.Notepad;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import transformation.pattern.activity.PatternActivityBuilder;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class InsertRecordActivity extends PatternActivityBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        ArrayList<Object> normalized = Helper.normalize(this.activity.getSubPageInfo().getPage());

        ArrayList<Transition> trans = Helper.getTransitions(normalized);
        ArrayList<Place> places = Helper.getPlaces(normalized);

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
            }
        }

        for (Transition t : trans) {

            switch (t.getText().toLowerCase()) {
                case "select record to insert":
                    map = new MappingComponent(t.getText(), "SwitchCase", Helper.removePointZero(t.getPosX()), Helper.removePointZero(t.getPosY()));
                    maps.add(map);
                    break;
                case "assign sk":
                    maps.addAll(this.decomposeTransition(t));
                    break;
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

        graph.constructWithModules(p);        
        
        p.setGraph(graph);
        
        ArrayList<String> test = new ArrayList<>();

        test.add("verifiedauditrecordsgeneratesk");
        test.add("counterlookuptable");
        test.add("selectrecordtoinsertlookuptable");
        test.add("selectrecordtoinsertcounter");

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

    private ArrayList<MappingComponent> decomposeTransition(Transition trans) {

        Collection<Place> places = trans.getSubPageInfo().getPage().getPlaces().values();
        Collection<Transition> transitions = trans.getSubPageInfo().getPage().getTransitions().values();

        ArrayList<MappingComponent> maps = new ArrayList<>();

        MappingComponent map;

        for (Place p : places) {
            switch (p.getText().toLowerCase()) {
                case "counter":
                    map = new MappingComponent(p.getText(), "Sequence", "100", "200");
                    maps.add(map);
                    break;
                case "lookup table":
                    map = new MappingComponent(p.getText(), "TableOutput", "300", "200");
                    maps.add(map);
                    break;
            }
        }

        for (Transition t : transitions) {
            switch (t.getText().toLowerCase()) {
                case "generate sk":
                    map = new MappingComponent(t.getText(), "SetValueField", "200", "200");
                    maps.add(map);
                    break;
            }
        }

        return maps;
    }
}
