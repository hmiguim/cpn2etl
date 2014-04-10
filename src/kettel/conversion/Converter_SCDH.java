package kettel.conversion;

import cpn.Place;
import cpn.Transition;
import java.util.ArrayList;
import java.util.Collection;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;

/**
 *
 * @author hmg
 */
public class Converter_SCDH extends ConversionBuilder {

    /**
     * Map various elements from the CPN model to Kettle This in particular
     * correspond to the Slow Changing Dimension with History
     *
     * @return An ArrayList of {@link MappingComponent}
     */
    @Override
    protected ArrayList<MappingComponent> convertComponents() {
        MappingComponent map;

        ArrayList<MappingComponent> maps = new ArrayList<>();
        
        Collection<Place> places = this.pattern.getSubPageInfo().getPage().getPlaces().values();
        Collection<Transition> trans = this.pattern.getSubPageInfo().getPage().getTransitions().values();
        // Get the port place
        for (Place p : places) {
            if (p.havePort()) {
                System.out.println("Type: " + p.getPort().getType() + "\nPort Place: " + p.getPort().getPlace().getText());
            }
        }
        for (Transition t : trans) {
            if (t.haveSubPage()) {
                System.out.println(t.getText());
            }
        }
        System.exit(0);
        return maps;
    }

    /**
     * Map the various connections between the kettle elements mapped from the
     * CPN pattern This in particular correspond to the Slow Changing Dimension with History
     *
     * @return An ArrayList of {@link MappingOrder}
     */
    @Override
    protected ArrayList<MappingOrder> convertOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Method that calls the methods {@code convertComponents} and
     * {@code convertOrders}, but first verify if the pattern is in fact a SCD/H
     * pattern. In case it be convert the components and the connections
     * otherwise don't convert and returns {@code false}
     *
     * @return {@code true} or {@code false} depending if the pattern can be
     * converted
     */
    @Override
    public boolean convert() {
        
        this.convertComponents();
        
        return true;
    }
    
}
