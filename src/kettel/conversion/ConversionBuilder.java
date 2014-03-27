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
    protected Transition module;

    public Mapping getMapping() {
        return this.mapping;
    }

    public void createMapping(Transition module) {
        this.mapping = new Mapping();
        this.constraintFactory = ConstraintFactory.newInstance();
        this.constraintDirector = constraintFactory.newConstraintDirector();
        this.module = module;
    }

    protected abstract ArrayList<MappingComponent> convertComponents();

    protected abstract ArrayList<MappingOrder> convertOrders();

    public abstract boolean convert();
}
