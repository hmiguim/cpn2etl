package kettel.conversion;

import cpn.Place;
import java.util.ArrayList;
import kettel.constraints.ConstraintBuilder;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;
import utils.Utilities;

/**
 *
 * @author hmg
 */
public class SKPConverter extends ConversionBuilder {

    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        for (Place place : this.module.getSubPageInfo().getPage().getPlaces().values()) {

            if (place.getText().toLowerCase().contains("lookup table")) {
                String[] axis = Utilities.normalizeAxis(place.getPosX(), place.getPosY());
                map = new MappingComponent(place.getText(), "DBLookup", axis[0], axis[1]);
                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact records")) {
                map = new MappingComponent(place.getText(), "TableInput", "30", "223");
                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact table")) {
                map = new MappingComponent(place.getText(), "TableOutput", "461", "11");
                maps.add(map);
            }

        }
        return maps;
    }

    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        
        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = this.mapping.getComponents();

        for (MappingComponent component : components) {
            switch (component.getKettleElement()) {
                case "DBLookup":
                    orders.add(new MappingOrder(component, null));
                    orders.add(new MappingOrder(null, component));
                    break;
            }
        }

        for (MappingOrder order : orders) {
            if (order.getTo() == null) {
                order.setTo(this.mapping.findComponent("fact table"));
            }
            if (order.getFrom() == null) {
                order.setFrom(this.mapping.findComponent("fact record"));
            }
        }

        return orders;
    }

    @Override
    public boolean convert() {

        ConstraintBuilder skp = this.constraintFactory.newSKPConstraintBuilder();

        this.constraintDirector.setConstraintBuilder(skp);
        this.constraintDirector.constructConstraint();
        
        if (!constraintDirector.verifyConstraint(this.module.getSubPageInfo().getPage())) {
            return false;
        } 
        
        this.mapping.setComponents(this.convertComponents());
        this.mapping.setOrder(this.convertOrders());
        
        return true;
    }

}
