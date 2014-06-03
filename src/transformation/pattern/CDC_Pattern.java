package transformation.pattern;

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
        ArrayList<Place> places = Helper.getPlaces(normalize);
        
        for (Place p : places) {
            switch(p.getText().toLowerCase()) {
                case "transact log":
                    map = new MappingComponent(p.getText(), "TableInput",Helper.removePointZero(p.getPosX()),Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    case "audit table":
                        map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()),Helper.removePointZero(p.getPosY()));
                        maps.add(map);
            }
        }
        
        for (Transition t : transitions) {
            if (t.isSubPage()) {
                maps.addAll(this.decomposeTransition(t));
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
        
        return true;
    }

    private ArrayList<MappingComponent> decomposeTransition(Transition trans) {

        ArrayList<Object> normalized = Helper.normalize(trans.getSubPageInfo().getPage().getPlaces().values(), trans.getSubPageInfo().getPage().getTransitions().values());
        
        ArrayList<Place> places = Helper.getPlaces(normalized);
        ArrayList<Transition> transitions = Helper.getTransitions(normalized);
        
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
