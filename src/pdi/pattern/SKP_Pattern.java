package pdi.pattern;

import cpn.Place;
import java.util.ArrayList;
import pdi.pattern_constraints.PatternConstraintBuilder;
import pdi.mapping.MappingComponent;
import pdi.mapping.MappingOrder;
import utils.Utilities;

/**
 *
 * @author hmg
 */
public class SKP_Pattern extends PatternBuilder {

    /**
     * Map various elements from the CPN model to Kettle This in particular
     * correspond to the Surrogate Key Pipelining
     *
     * @return An ArrayList of {@link MappingComponent}
     */
    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();

        for (Place place : this.pattern.getSubPageInfo().getPage().getPlaces().values()) {

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

    /**
     * Map the various connections between the kettle elements mapped from the
     * CPN pattern This in particular correspond to the Surrogate Key Pipelining
     *
     * @return An ArrayList of {@link MappingOrder}
     */
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

    /**
     * Method that calls the methods {@code convertComponents} and
     * {@code convertOrders}, but first verify if the pattern is in fact a SKP
     * pattern. In case it be convert the components and the connections
     * otherwise don't convert and returns {@code false}
     *
     * @return {@code true} or {@code false} depending if the pattern can be
     * converted
     */
    @Override
    public boolean convert() {

        PatternConstraintBuilder skp = this.patternConstraintFactory.newSKPConstraintBuilder();

        this.patternConstraintDirector.setConstraintBuilder(skp);
        this.patternConstraintDirector.constructConstraint();

        if (!this.patternConstraintDirector.verifyConstraint(this.pattern.getSubPageInfo().getPage())) {
            return false;
        }

        this.mapping.setComponents(this.convertComponents());
        this.mapping.setOrder(this.convertOrders());

        return true;
    }

}
