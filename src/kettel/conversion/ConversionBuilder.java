package kettel.conversion;

import cpn.Arc;
import cpn.Page;
import cpn.Place;
import cpn.Transition;
import java.util.ArrayList;
import java.util.Observable;
import kettel.constraints.ConstraintBuilder;
import kettel.constraints.ConstraintDirector;
import kettel.constraints.ConstraintFactory;
import kettel.mapping.Mapping;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;
import utils.Utilities;

/**
 *
 * @author hmg
 */
public class ConversionBuilder {

    protected ConstraintFactory constraintFactory;
    protected ConstraintDirector constraintDirector;

    public ConversionBuilder() {
        this.constraintFactory = ConstraintFactory.newInstance();
        this.constraintDirector = constraintFactory.newConstraintDirector();
    }

    public Mapping convertModule(Transition module) {

        String name = module.getSubPageInfo().getPage().getName();
        ArrayList<MappingComponent> components;
        ArrayList<MappingOrder> orders;

        Mapping map = new Mapping();

        switch (name) {
            case "SKP":

                ConstraintBuilder skp = this.constraintFactory.newSKPConstraintBuilder();

                this.constraintDirector.setConstraintBuilder(skp);
                this.constraintDirector.constructConstraint();

                if (!constraintDirector.verifyConstraint(module.getSubPageInfo().getPage())) {
                    return null;
                }

                components = this.convertComponents(module.getSubPageInfo().getPage());
                map.setComponents(components);
                orders = this.convertOrdersSKP(map);
                map.setOrder(orders);

                break;
            case "SCD/H 1":
                System.out.println("Coiso");
                break;
        }

        return map;
    }

    private ArrayList<MappingOrder> convertOrdersSKP(Mapping map) {

        ArrayList<MappingOrder> orders = new ArrayList<>();
        ArrayList<MappingComponent> components = map.getComponents();

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
                order.setTo(map.findComponent("fact table"));
            }
            if (order.getFrom() == null) {
                order.setFrom(map.findComponent("fact record"));
            }
        }

        return orders;
    }

    private ArrayList<MappingOrder> convertOrders(Page p, Mapping map) {

        MappingOrder mapOrder;

        ArrayList<MappingOrder> maps = new ArrayList<>();

        for (Arc arc : p.getArcs().values()) {

            String orientation = arc.getOrientation();

            switch (orientation) {
                case "PtoT":

                    Place placeEnd = arc.getPlaceEnd();
                    Transition transEnd = arc.getTransEnd();

                    MappingComponent from = map.findComponent(placeEnd.getText());
                    MappingComponent to = map.findComponent(transEnd.getText());

                    mapOrder = new MappingOrder(from, to);

                    maps.add(mapOrder);

                    break;
                case "TtoP":

                    break;
            }

        }

        return maps;
    }

    private ArrayList<MappingComponent> convertComponents(Page p) {

        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        for (Place place : p.getPlaces().values()) {

            if (place.getText().toLowerCase().contains("lookup table")) {
                String[] axis = Utilities.normalizeAxis(place.getPosX(), place.getPosY());
                map = new MappingComponent(place.getText(), "DBLookup", axis[0], axis[1]);
                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact records")) {
                String[] axis = Utilities.normalizeAxis(place.getPosX(), place.getPosY());
                map = new MappingComponent(place.getText(), "TableInput", axis[0], axis[1]);

                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact table")) {
                String[] axis = Utilities.normalizeAxis(place.getPosX(), place.getPosY());
                map = new MappingComponent(place.getText(), "TableOutput", axis[0], axis[1]);

                maps.add(map);
            }

        }
        return maps;
    }
}
