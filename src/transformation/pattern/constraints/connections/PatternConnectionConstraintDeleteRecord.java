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
        
        Keyword k1 = new Keyword("slowlychangingdim", "etllog");
        Keyword k2 = new Keyword("slowlychangingdim", "lookuptable");
        
        keywords.add(k1);
        keywords.add(k2);
        
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
