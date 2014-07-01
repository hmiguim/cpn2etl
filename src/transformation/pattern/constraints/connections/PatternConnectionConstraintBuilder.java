package transformation.pattern.constraints.connections;

/**
 *
 * @author hmg
 */
public abstract class PatternConnectionConstraintBuilder {
    
    protected PatternConnectionConstraint connectionConstraint;
    
    /**
     * Get the {@link PatternConnectionConstraint} builded
     *
     * @return A {@code Constraint}
     */
    public PatternConnectionConstraint getConnectionConstraint() {
        return this.connectionConstraint;
    }

    /**
     * Create a new {@link PatternConnectionConstraint}
     */
    public void createConnectionConstraint() {
        this.connectionConstraint = new PatternConnectionConstraint();
    }

    /**
     * Abstract method to be implemented in each specific constraint. Initialize
     * each constraint with the keywords
     */
    public abstract void buildConnectionConstraint();

    /**
     * Abstract method to be implemented in each specific constraint. Verify if
     * the specific {@code page} parameter is according the pattern
     *
     * @param from 
     * @param to
     * @return {@code true} or {@code false} depending if the constraint is
     * verify or not
     */
    public abstract boolean verifyConnectionConstraint(String from, String to);
}
