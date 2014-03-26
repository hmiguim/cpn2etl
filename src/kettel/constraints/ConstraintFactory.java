package kettel.constraints;

/**
 *
 * @author hmg
 */
public class ConstraintFactory {
    
    protected ConstraintFactory() { }
    
    public static ConstraintFactory newInstance() {
        return new ConstraintFactory();
    }
    
    public ConstraintDirector newConstraintDirector() {
        return new ConstraintDirector();
    }
    
    public ConstraintBuilder newSKPConstraintBuilder() {
        return new SKPConstraint();
    }
    
    public ConstraintBuilder newCDCConstraintBuilder() {
        return new CDC();
    }
}
