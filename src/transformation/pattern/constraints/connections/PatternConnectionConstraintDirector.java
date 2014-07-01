package transformation.pattern.constraints.connections;

/**
 *
 * @author hmg
 */
public class PatternConnectionConstraintDirector {
    
    private PatternConnectionConstraintBuilder connectionConstraintBuilder;
    
    /**
     * Sets the connectionConstraintBuilder
     * @param connectionConstraintBuilder The connectionConstraintBuilder to be set
     */
    public void setConnectionConstraintBuilder(PatternConnectionConstraintBuilder connectionConstraintBuilder) {
        this.connectionConstraintBuilder = connectionConstraintBuilder;
    }
    
    /** Gets the constraint
     * @return The {@code ConnectionConstraint}
     */
    public PatternConnectionConstraint getConnectionConstraint() {
        return this.connectionConstraintBuilder.getConnectionConstraint();
    }
    
    /** 
     * Public method that construct the ConnectionConstraint by creating and them build it
     */
    public void constructConnectionConstraint() {
        this.connectionConstraintBuilder.createConnectionConstraint();
        this.connectionConstraintBuilder.buildConnectionConstraint();
    }
    
    /**
     * Method that calls the verifyConnectionConstraint method to the concrete implementation
     * @param from
     * @param to
     * @return {@code true} or {@code false} depending if the constraint is verify or not
     */
    public boolean verifyConnectionContraint(String from, String to) {
        return this.connectionConstraintBuilder.verifyConnectionConstraint(from,to);
    }
}
