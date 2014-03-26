package kettel.constraints;

import cpn.Page;

/**
 *
 * @author hmg
 */
public class ConstraintDirector {

    private ConstraintBuilder constraintBuilder;

    public void setConstraintBuilder(ConstraintBuilder constraintBuilder) {
        this.constraintBuilder = constraintBuilder;
    }

    public Constraint getConstraint() {
        return this.constraintBuilder.getConstraint();
    }

    public void constructConstraint() {
        this.constraintBuilder.createConstraint();
        this.constraintBuilder.buildConstraint();
    }
    
    public boolean verifyConstraint(Page page) {
        return this.constraintBuilder.verifyConstraint(page);
    }

}
