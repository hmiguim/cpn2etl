package transformation.pattern.constraints;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class PatternConstraint {
    
    private ArrayList<Keyword> keywords;
    
    /**
     * Public Method
     */
    PatternConstraint() {
        this.keywords = new ArrayList<>(); 
    }
    
    /**
     * Sets the keywords
     * @param keywords the keywords to set
     */
    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }
    
    /**
     * Gets the keywords
     * @return An ArrayList of {@link Keywords}
     */
    public ArrayList<Keyword> getKeywords() {
        return this.keywords;
    }
}
