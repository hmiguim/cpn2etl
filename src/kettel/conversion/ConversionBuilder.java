package kettel.conversion;

import cpn.Transition;
import java.util.ArrayList;
import kettel.constraints.ConstraintDirector;
import kettel.constraints.ConstraintFactory;
import kettel.mapping.Mapping;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;

/**
 *
 * @author hmg
 */
public abstract class ConversionBuilder {

    protected Mapping mapping;
    protected ConstraintFactory constraintFactory;
    protected ConstraintDirector constraintDirector;
    protected Transition pattern;

    /**
     * Gets the {@link Mapping}
     *
     * @return The {@code Mapping}
     */
    public Mapping getMapping() {
        return this.mapping;
    }

    /**
     * Creates a new {@link Mapping} and sets the pattern
     *
     * @param pattern The ETL pattern to be converted
     */
    public void createMapping(Transition pattern) {
        this.mapping = new Mapping();
        this.constraintFactory = ConstraintFactory.newInstance();
        this.constraintDirector = constraintFactory.newConstraintDirector();
        this.pattern = pattern;
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
