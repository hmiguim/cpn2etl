package kettel.conversion;

import cpn.Arc;
import cpn.Page;
import cpn.Place;
import cpn.Transition;
import java.text.DecimalFormat;
import java.util.ArrayList;
import kettel.mapping.Mapping;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;

/**
 *
 * @author hmg
 */
public class ConversionBuilder {

    public ConversionBuilder() {
    }

    public Mapping convertModule(Transition module) {

        String name = module.getSubPageInfo().getPage().getName();
        ArrayList<MappingComponent> components;
        ArrayList<MappingOrder> orders;

        Mapping map = new Mapping();

        switch (name) {
            case "SKP":
                components = this.convertComponents(module.getSubPageInfo().getPage());
                map.setComponents(components);
                orders = this.convertOrdersSKP(map);
                map.setOrder(orders);
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
                map = new MappingComponent(place.getText(), "DBLookup", this.normalizeXAxis(place.getPosX()), this.normalizeYAxis(place.getPosY()));
                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact records")) {
                map = new MappingComponent(place.getText(), "TableInput", this.normalizeXAxis(place.getPosX()), this.normalizeYAxis(place.getPosY()));

                maps.add(map);
            } else if (place.getText().toLowerCase().contains("fact table")) {
                map = new MappingComponent(place.getText(), "TableOutput", this.normalizeXAxis(place.getPosX()), this.normalizeYAxis(place.getPosY()));

                maps.add(map);
            }

        }
        return maps;
    }

    private String normalizeXAxis(double x) {

        String str;

        if (x < 0) {
            x *= -1;
        } else if (x == 0) {
            x += 60;
        }

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(x);

        return str;

    }

    private String normalizeYAxis(double y) {

        String str;

        if (y < 0) {
            y *= -1;
        }

        DecimalFormat df = new DecimalFormat("#");
        str = df.format(y);

        return str;
    }
}
