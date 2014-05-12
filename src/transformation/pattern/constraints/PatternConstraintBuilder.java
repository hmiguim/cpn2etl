package transformation.pattern.constraints;

import cpn.Page;

/**
 *
 * @author hmg
 */
public abstract class PatternConstraintBuilder {

    protected PatternConstraint constraint;

    /**
     * Get the {@link PatternConstraint} builded
     *
     * @return A {@code Constraint}
     */
    public PatternConstraint getConstraint() {
        return this.constraint;
    }

    /**
     * Create a new {@link PatternConstraint}
     */
    public void createConstraint() {
        this.constraint = new PatternConstraint();
    }

    /**
     * Abstract method to be implemented in each specific constraint. Initialize
     * each constraint with the keywords
     */
    public abstract void buildConstraint();

    /**
     * Abstract method to be implemented in each specific constraint. Verify if
     * the specific {@code page} parameter is according the pattern
     *
     * @param page CPN Page submitted to dissection
     * @return {@code true} or {@code false} depending if the constraint is
     * verify or not
     */
    public abstract boolean verifyConstraint(Page page);
}
