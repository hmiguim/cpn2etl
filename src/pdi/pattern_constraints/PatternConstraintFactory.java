package pdi.pattern_constraints;

/**
 *
 * @author hmg
 */
public class PatternConstraintFactory {
    
    /**
     * Protected constructor
     */
    protected PatternConstraintFactory() { }
    
    /**
     * Obtain a new instance a {@code PatternConstraintFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code PatternConstraintFactory}
     */
    public static PatternConstraintFactory newInstance() {
        return new PatternConstraintFactory();
    }
    
    /**
     * Creates a new instance of a {@code PatternConstraintDirector} using the currently
     * configured parameters.
     * @return A new instance of a {@code PatternConstraintDirector}
     */
    public PatternConstraintDirector newPatternConstraintDirector() {
        return new PatternConstraintDirector();
    }
    
    /**
     * Creates a new instance of a {@code ConstraintBuilder} using the currently
     * configured parameters.
     * @return A new instance of a {@code SKPConstraint}
     */
    public PatternConstraintBuilder newSKPConstraintBuilder() {
        return new PatternConstraintSKP();
    }
    
    /**
     * Creates a new instance of a {@code ConstraintBuilder} using the currently
     * configured parameters.
     * @return A new instance of a {@code ConstraintBuilder}
     */
    public PatternConstraintBuilder newSCDHConstraintBuilder() {
        return new PatternConstraintSCDH();
    }
}
