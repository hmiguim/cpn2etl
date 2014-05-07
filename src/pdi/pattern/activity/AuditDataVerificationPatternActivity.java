package pdi.pattern.activity;

import cpn.Place;
import cpn.Transition;
import java.util.ArrayList;
import java.util.Collection;
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

        for (Place p : places) {
            if (p.getText().toLowerCase().contains("audit records")) {
                String[] axis = Utilities.normalizeAxis(p.getPosX(), p.getPosY());
                map = new MappingComponent(p.getText(),"TableInput",axis[0],axis[1]);
                maps.add(map);
            } else if (p.getText().toLowerCase().contains("error log")) {
                String[] axis = Utilities.normalizeAxis(p.getPosX(), p.getPosY());
                map = new MappingComponent(p.getText(), "TableOutput", axis[0], axis[1]);
                maps.add(map);
            } else if (p.getText().toLowerCase().contains("quarantine table")) {
                String[] axis = Utilities.normalizeAxis(p.getPosX(), p.getPosY());
                map = new MappingComponent(p.getText(), "TableOutput", axis[0],axis[1]);
                maps.add(map);
            } else if (p.getText().toLowerCase().contains("etl log")) {
                String[] axis = Utilities.normalizeAxis(p.getPosX(), p.getPosY());
                map = new MappingComponent(p.getText(), "TableOutput", axis[0],axis[1]);
                maps.add(map);
            } else if (p.getText().toLowerCase().contains("verified audit records")) {
                String[] axis = Utilities.normalizeAxis(p.getPosX(), p.getPosY());
                map = new MappingComponent(p.getText(), "TableOutput", axis[0],axis[1]);
                maps.add(map);
            }
        }

        for (Transition t : transitions) {
            if (t.getText().toLowerCase().contains("audit data verification")) {
                String[] axis = Utilities.normalizeAxis(t.getPosX(), t.getPosY());
                map = new MappingComponent(t.getText(), "Validator", axis[0],axis[1]);
                maps.add(map);
            }
        }
        
        return maps;
    }

    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean convert() {
    
        this.mapping.setComponents(this.convertComponents());
        
        return true;
    }
    
    
    
}
