package transformation.pattern.activity.SCD;

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
import transformation.pattern.constraints.connections.PatternConnectionConstraintUpdateRecord;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class UpdateRecordActivity extends PatternActivityBuilder {

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
                case "lookup table":
                    map = new MappingComponent(p.getText(), "DBLookup", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
                    break;
                case "dim historic":
                    map = new MappingComponent(p.getText(), "TableOutput", Helper.removePointZero(p.getPosX()), Helper.removePointZero(p.getPosY()));
                    maps.add(map);
            }

        }

        for (Transition t : trans) {

            if (t.getText().toLowerCase().equals("select record to update")) {
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
        
        PatternConnectionConstraintUpdateRecord connectionConstraint = this.patternConnectionConstraintFactory.newPatternConnectionConstraintUpdateRecord();
        
        this.patternConnectionConstraintDirector.setConnectionConstraintBuilder(connectionConstraint);
        this.patternConnectionConstraintDirector.constructConnectionConstraint();
        
        for (MappingComponent i : components) {
            for (MappingComponent j : components) {
                if (!i.getCpnElement().equals(j.getCpnElement())) {
                    List connected = p.connected(i.getCpnElement(), j.getCpnElement());

                    if (connected != null) {
                        if (connected.size() < 4) {

                            String s = i.getCpnElement().toLowerCase().replace(" ", "");
                            s += j.getCpnElement().toLowerCase().replace(" ", "");
                            
                            if (!this.patternConnectionConstraintDirector.verifyConnectionConstraint(i.getCpnElement(), j.getCpnElement())) {
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
