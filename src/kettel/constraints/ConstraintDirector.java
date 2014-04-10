package kettel.constraints;

import cpn.Page;

/**
 *
 * @author hmg
 */
public class ConstraintDirector {

    private ConstraintBuilder constraintBuilder;
    
    /**
     * Sets the constraintBuilder
     * @param constraintBuilder The constraintBuilder to be set
     */
    public void setConstraintBuilder(ConstraintBuilder constraintBuilder) {
        this.constraintBuilder = constraintBuilder;
    }
    
    /**
     * Gets the constraint
     * @return The {@code Constraint}
     */
    public Constraint getConstraint() {
        return this.constraintBuilder.getConstraint();
    }

    /**
     * Public method that construct the Constraint by creating and them build it
     */
    public void constructConstraint() {
        this.constraintBuilder.createConstraint();
        this.constraintBuilder.buildConstraint();
    }
    
    /**
     * Method that calls the verifyConstraint method to the concrete implementation
     * @param page Page to be verified
     * @return {@code true} or {@code false} depending if the constraint is verify or not
     */
    public boolean verifyConstraint(Page page) {
        return this.constraintBuilder.verifyConstraint(page);
    }

}
