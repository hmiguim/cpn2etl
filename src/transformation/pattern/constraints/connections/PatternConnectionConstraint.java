package transformation.pattern.constraints.connections;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class PatternConnectionConstraint {
    
    private ArrayList<Keyword> keywords;
    
    /**
     * Public Method
     */
    public PatternConnectionConstraint() {
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
     * @return An ArrayList of keywords
     */
    public ArrayList<Keyword> getKeywords() {
        return this.keywords;
    }
}
