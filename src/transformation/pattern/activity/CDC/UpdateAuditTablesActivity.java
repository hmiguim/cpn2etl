package transformation.pattern.activity.CDC;

import cpn.Page;
import cpn.Place;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
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
public class UpdateAuditTablesActivity extends PatternActivityBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        ArrayList<Object> normalized = Helper.normalize(this.activity.getSubPageInfo().getPage());
        
        ArrayList<Transition> trans = Helper.getTransitions(normalized);
        ArrayList<Place> places = Helper.getPlaces(normalized);
        
        for (Place p : places) {
            switch(p.getText().toLowerCase()) {
                
                case "row": 
                    map = new MappingComponent(p.getText(), "TableInput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "end time":
                    map = new MappingComponent(p.getText(), "TableInput",Helper.removePointZero(p.getPosX()),Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "tlog prog":
                    map = new MappingComponent(p.getText(), "TableOutput",Helper.removePointZero(p.getPosX()),Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "audit table":
                    map = new MappingComponent(p.getText(), "TableOutput",Helper.removePointZero(p.getPosX()),Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
            }
        }
        
        for (Transition t : trans) {
            switch (t.getText().toLowerCase()) {
                case "update audit table":
                    map = new MappingComponent(t.getText(), "ExecSQL", Helper.removePointZero(t.getPosX()),Helper.removePointZero(t.getPosY()));
                    maps.add(map);
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

        graph.construct(p);

        p.setGraph(graph);

        for (MappingComponent i : components) {
            for (MappingComponent j : components) {
                if (!i.getCpnElement().equals(j.getCpnElement())) {
                    List connected = p.connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        if (connected.size() < 2) {

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
