package main;

import javax.xml.parsers.ParserConfigurationException;
import kettel.constraints.SKPConstraint;
import kettel.constraints.Constraint;
import kettel.constraints.ConstraintBuilder;
import kettel.constraints.ConstraintDirector;
import kettel.constraints.ConstraintFactory;

/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) throws ParserConfigurationException {
        //new gui.Main().setVisible(true);
        
        ConstraintFactory constraintFactory = ConstraintFactory.newInstance();
        
        ConstraintDirector constraintBuilders = constraintFactory.newConstraintDirector();
        
        ConstraintBuilder skp = constraintFactory.newSKPConstraintBuilder();
        
        constraintBuilders.setValidatorBuilder(skp);
        constraintBuilders.constructValidator();
        
        Constraint constraint = constraintBuilders.getValidator();
        
        for (String s : constraint.getKeywords()) {
            System.out.println(s);
        }
        
    }
}
