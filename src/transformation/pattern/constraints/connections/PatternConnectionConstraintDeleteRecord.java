package transformation.pattern.constraints.connections;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class PatternConnectionConstraintDeleteRecord extends PatternConnectionConstraintBuilder {
    
    @Override
    public void buildConnectionConstraint() {
        ArrayList<Keyword> keywords = new ArrayList<>();
        
        keywords.add(new Keyword("slowlychangingdim", "etllog"));
        keywords.add(new Keyword("slowlychangingdim", "lookuptable"));
        
        this.connectionConstraint.setKeywords(keywords);
    }

    @Override
    public boolean verifyConnectionConstraint(String from, String to) {
        
        from = from.replace(" ","").toLowerCase();
        to = to.replace(" ", "").toLowerCase();
        
        Keyword test = new Keyword(from, to);
        
        return (this.connectionConstraint.getKeywords().contains(test));     
    }
}
