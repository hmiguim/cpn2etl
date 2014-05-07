package pdi.pattern_constraints;

import cpn.Page;
import cpn.Place;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hmg
 */
public class PatternConstraintSKP extends PatternConstraintBuilder {

    /**
     * Initialize the Surrogate Key Pipelining constraint with the keywords corresponding
     */
    @Override
    public void buildConstraint() {

        ArrayList<Keyword> keywords = new ArrayList<>();

        keywords.add(new Keyword("lookup table", false, 0));
        keywords.add(new Keyword("fact table", false, 0));
        keywords.add(new Keyword("fact record", false, 0));

        this.constraint.setKeywords(keywords);
    }

    
    /**
     * Verify if the {code page} have the elements presents in a Surrogate Key Pipelining
     * @param page The page to be verified
     * @return {@code true} or {@code false} depending if the constraint is verify or not
     */
    @Override
    public boolean verifyConstraint(Page page) {

        ArrayList<String> testing = new ArrayList<>();
        
        Collection<Place> places = page.getPlaces().values();
        
        for (Place p : places ) {
            testing.add(p.getText());
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
