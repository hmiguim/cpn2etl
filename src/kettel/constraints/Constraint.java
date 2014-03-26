package kettel.constraints;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class Constraint {
    
    private ArrayList<Keyword> keywords;
    
    public Constraint() {
        this.keywords = new ArrayList<>(); 
    }
    
    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }
    
    public ArrayList<Keyword> getKeywords() {
        return this.keywords;
    }
}
