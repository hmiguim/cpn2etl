package kettel.constraints;

import cpn.Page;

/**
 *
 * @author hmg
 */
public abstract class ConstraintBuilder {

    protected Constraint constraint;

    /**
     * Get the {@link Constraint} builded
     *
     * @return A {@code Constraint}
     */
    public Constraint getConstraint() {
        return this.constraint;
    }

    /**
     * Create a new {@link Constraint}
     */
    public void createConstraint() {
        this.constraint = new Constraint();
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
