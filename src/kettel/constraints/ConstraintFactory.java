package kettel.constraints;

/**
 *
 * @author hmg
 */
public class ConstraintFactory {
    
    /**
     * Protected constructor
     */
    protected ConstraintFactory() { }
    
    /**
     * Obtain a new instance a {@code ConstraintFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code ConstraintFactory}
     */
    public static ConstraintFactory newInstance() {
        return new ConstraintFactory();
    }
    
    /**
     * Creates a new instance of a {@code ConstraintDirector} using the currently
     * configured parameters.
     * @return A new instance of a {@code ConstraintDirector}
     */
    public ConstraintDirector newConstraintDirector() {
        return new ConstraintDirector();
    }
    
    /**
     * Creates a new instance of a {@code ConstraintBuilder} using the currently
     * configured parameters.
     * @return A new instance of a {@code SKPConstraint}
     */
    public ConstraintBuilder newSKPConstraintBuilder() {
        return new SKPConstraint();
    }
    
    /**
     * Creates a new instance of a {@code ConstraintBuilder} using the currently
     * configured parameters.
     * @return A new instance of a {@code ConstraintBuilder}
     */
    public ConstraintBuilder newCDCConstraintBuilder() {
        return new CDCConstraint();
    }
}
