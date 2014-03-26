package kettel.constraints;

/**
 *
 * @author hmg
 */
public class ConstraintDirector {
    
    private ConstraintBuilder vb;
    
    public void setValidatorBuilder(ConstraintBuilder vb) { this.vb = vb; }
    
    public Constraint getValidator() { return this.vb.getConstraint(); }
    
    public void constructValidator() {
        this.vb.createConstraint();
        this.vb.buildConstraint();       
    }
    
}
