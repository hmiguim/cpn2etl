package kettel.constraints;

import cpn.Page;
import cpn.Transition;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hmg
 */
public class Constraint_SCDH extends ConstraintBuilder {

    /**
     * Initialize the Surrogate Key Pipelining constraint with the keywords corresponding
     */
    @Override
    public void buildConstraint() {

        ArrayList<Keyword> keywords = new ArrayList<>();

        keywords.add(new Keyword("audit data verification", false, 0));
        keywords.add(new Keyword("delete record", false, 0));
        keywords.add(new Keyword("update record", false, 0));
        keywords.add(new Keyword("insert record", false, 0));

        this.constraint.setKeywords(keywords);
    }

    /**
     * Verify if the {code page} have the elements presents in a Slowly Changing Dimension
     * @param page The page to be verified
     * @return {@code true} or {@code false} depending if the constraint is verify or not
     */
    @Override
    public boolean verifyConstraint(Page page) {
    ArrayList<String> testing = new ArrayList<>();
        
        Collection<Transition> transitions = page.getTransitions().values();
        
        for (Transition t : transitions ) {
            testing.add(t.getText());
        }

        for (String str : testing) {
            for (Keyword key : this.constraint.getKeywords()) {
                if (str.toLowerCase().contains(key.getKeyword())) {
                    key.setKeywordPresence(true);
                    key.setCount(key.getCount()+1);
                }
            }
        }

        for (Keyword key : this.constraint.getKeywords()) {
            if (!key.isKeywordPresence()) return false;
        }
        
        return true;
    }
    
}
