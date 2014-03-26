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
public class CDC extends ConstraintBuilder {

    @Override
    public void buildConstraint() {
    }

    @Override
    public boolean verifyConstraint(Page p) {
        System.out.println("Coiso");
        return true;
    }
    
}
