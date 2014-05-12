package transformation.pattern.activity;

import cpn.Transition;
import java.util.ArrayList;
import pdi.components.notepad.Notepad;
import transformation.mapping.Mapping;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;

/**
 *
 * @author hmg
 */
public abstract class PatternActivityBuilder {

    protected Mapping mapping;
    protected ArrayList<Notepad> notepads;
    protected Transition activity;

    /**
     * Gets the {@link Mapping}
     *
     * @return The {@code Mapping}
     */
    public Mapping getMapping() {
        return this.mapping;
    }
 
    public ArrayList<Notepad> getNotepads() {
        return this.notepads;
    }

    /**
     * Creates a new {@link Mapping} and sets the pattern activity
     *
     * @param activity The ETL pattern activity to be converted
     */
    public void createMapping(Transition activity) {
        this.mapping = new Mapping();
        this.notepads = new ArrayList<>();
        this.activity = activity;
    } 
    
    /**
     * Protected abstract method to be implemented in each specific converter.
     * This in particular is to map various elements from the CPN to Kettle
     *
     * @return An ArrayList of {@link MappingComponent}
     */
    protected abstract ArrayList<MappingComponent> convertComponents();
    
    /**
     * Protected abstract method to be implemented in each specific converter.
     * This in particular is to map various connections between the kettle
     * elements mapped from the CPN
     *
     * @return An ArrayList of {@link MappingOrder}
     */
    protected abstract ArrayList<MappingOrder> convertOrders();
    
    /**
     * Abstract method to be implemented in each specific converter, that will
     * call the {@link convertComponents} method and the {@link convertOrders}
     * method
     *
     * @return {@code true} or {@code false} depending if the pattern can be converted
     */
    public abstract boolean convert();
}
