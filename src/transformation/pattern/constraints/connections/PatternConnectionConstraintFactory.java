package transformation.pattern.constraints.connections;

/**
 *
 * @author hmg
 */
public class PatternConnectionConstraintFactory {
    
    /**
     * Protected constructor
     */
    protected PatternConnectionConstraintFactory() { }
    
    /**
     * Obtain a new instance a {@code PatternConnectionConstraintFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code PatternConnectionConstraintFactory}
     */
    public static PatternConnectionConstraintFactory newInstance() {
        return new PatternConnectionConstraintFactory();
    }
    
    /**
     * Creates a new instance of a {@code PatternConnectionConstraintDirector} using the currently
     * configured parameters.
     * @return A new instance of a {@code PatternConnectionConstraintDirector}
     */
    public PatternConnectionConstraintDirector newPatternConnectionConstraintDirector() {
        return new PatternConnectionConstraintDirector();
    }
    
    public PatternConnectionConstraintCDC newPatternConnectionConstraintCDC() {
        return new PatternConnectionConstraintCDC();
    }
    
    public PatternConnectionConstraintDeleteRecord newPatternConnectionConstraintDeleteRecord() {
        return new PatternConnectionConstraintDeleteRecord();
    }
}
