package kettel.constraints;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class SKPConstraint extends ConstraintBuilder {

    @Override
    public void buildConstraint() {
        
        ArrayList<String> keywords = new ArrayList<>();
        
        keywords.add("lookup table");
        keywords.add("fact table");
        keywords.add("fact records");
        
        this.constraint.setKeywords(keywords);   
    }
    
}
