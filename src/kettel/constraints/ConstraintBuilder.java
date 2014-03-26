package kettel.constraints;

/**
 *
 * @author hmg
 */
public abstract class ConstraintBuilder {

    protected Constraint constraint;
    
    /**
     * Get the {@code Constraint} builded
     * @return A {@code Constraint} 
     */
    public Constraint getConstraint() {
        return this.constraint;
    }

    /** 
     * Create a new {@code Constraint}
     */
    public void createConstraint() {
        this.constraint = new Constraint();
    }

    /**
     * Abstract method to be implemented in each specific constraint
     */
    public abstract void buildConstraint();
}
