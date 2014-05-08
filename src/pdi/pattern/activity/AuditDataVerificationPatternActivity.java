package pdi.pattern.activity;

import cpn.Place;
import cpn.Transition;
import cpn.graph.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pdi.mapping.MappingComponent;
import pdi.mapping.MappingOrder;
import utils.Utilities;

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

        places = Utilities.normalizePlaces(places);        
      
        for (Place p : places) {
            switch (p.getText().toLowerCase()) {
                case "audit records":
                    {
                        map = new MappingComponent(p.getText(),"TableInput",Utilities.removePointZero(p.getPosX()),Utilities.removePointZero(p.getPosY()));
                        maps.add(map);
                        break;
                    }
                case "error log":
                    {
                        map = new MappingComponent(p.getText(),"TableOutput",Utilities.removePointZero(p.getPosX()),Utilities.removePointZero(p.getPosY()));
                        maps.add(map);
                        break;
                    }
                case "quarantine table":
                    {
                        map = new MappingComponent(p.getText(),"TableOutput",Utilities.removePointZero(p.getPosX()),Utilities.removePointZero(p.getPosY()));
                        maps.add(map);
                        break;
                    }
                case "etl log":
                    {
                        map = new MappingComponent(p.getText(),"TableOutput",Utilities.removePointZero(p.getPosX()),Utilities.removePointZero(p.getPosY()));
                        maps.add(map);
                        break;
                    }
                case "verified audit records":
                    {
                        map = new MappingComponent(p.getText(),"TableOutput",Utilities.removePointZero(p.getPosX()),Utilities.removePointZero(p.getPosY()));
                        maps.add(map);
                        break;
                    }
            }
        }

        transitions = Utilities.normalizeTransitions(transitions);
        
        for (Transition t : transitions) {
            if (t.getText().toLowerCase().equals("audit data verification")) {
                map = new MappingComponent(t.getText(), "Validator",Utilities.removePointZero(t.getPosX()),Utilities.removePointZero(t.getPosY()));
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
