package kettel.constraints;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class Constraint {
    
    private ArrayList<String> keywords;
    
    public Constraint() {
        this.keywords = new ArrayList<>(); 
    }
    
    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
    
    public ArrayList<String> getKeywords() {
        return this.keywords;
    }
}
