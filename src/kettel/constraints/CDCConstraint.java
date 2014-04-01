/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel.constraints;

import cpn.Page;

/**
 *
 * @author hmg
 */
public class CDCConstraint extends ConstraintBuilder {

    /**
     * Initialize the Change Data Capture constraint with the keywords corresponding
     */
    @Override
    public void buildConstraint() {
    }

    /**
     * Verify if the {code page} have the elements presents in a Change Data Capture
     * @param page The page to be verified
     * @return {@code true} or {@code false} depending if the constraint is verify or not
     */
    @Override
    public boolean verifyConstraint(Page page) {
        System.out.println("Coiso");
        return true;
    }
    
}
