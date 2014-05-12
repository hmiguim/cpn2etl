package transformation.pattern;

import cpn.Transition;
import java.util.ArrayList;
import transformation.mapping.Mapping;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import transformation.pattern.constraints.PatternConstraintDirector;
import transformation.pattern.constraints.PatternConstraintFactory;

/**
 *
 * @author hmg
 */
public abstract class PatternBuilder {

    protected Mapping mapping;
    protected PatternConstraintFactory patternConstraintFactory;
    protected PatternConstraintDirector patternConstraintDirector;
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
        this.patternConstraintFactory = PatternConstraintFactory.newInstance();
        this.patternConstraintDirector = this.patternConstraintFactory.newPatternConstraintDirector();
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
