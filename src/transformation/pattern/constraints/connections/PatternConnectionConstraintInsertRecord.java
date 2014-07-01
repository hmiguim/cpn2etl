/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation.pattern.constraints.connections;

import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class PatternConnectionConstraintInsertRecord extends PatternConnectionConstraintBuilder {

    @Override
    public void buildConnectionConstraint() {
        ArrayList<Keyword> keywords = new ArrayList<>();
        
        keywords.add(new Keyword("verifiedauditrecords", "generatesk"));
        keywords.add(new Keyword("counter", "lookuptable"));
        keywords.add(new Keyword("selectrecordtoinsert","lookuptable"));
        keywords.add(new Keyword("selectrecordtoinsert","counter"));
        
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
