package kettel.conversion;

import cpn.Place;
import java.util.ArrayList;
import java.util.Collection;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;

/**
 *
 * @author hmg
 */
public class SCDHConverter extends ConversionBuilder {

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
        
        Collection<Place> values = this.pattern.getSubPageInfo().getPage().getPlaces().values();
        
        // Get the port place
        for (Place p : values) {
            if (p.havePort()) {
                System.out.println(p.getPort().getPlace().getText());
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
