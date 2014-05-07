package pdi.pattern;

import cpn.Transition;
import pdi.mapping.Mapping;

/**
 *
 * @author hmg
 */
public class PatternDirector {

    private PatternBuilder patternBuilder;
    
     /**
     * Sets the patternBuilder
     * @param patternBuilder The patternBuilder to be set
     */
    public void setPatternBuilder (PatternBuilder patternBuilder) {
        this.patternBuilder = patternBuilder;
    }
    
    /**
     * Gets the mapping
     * @return The {@link Mapping}
     */
    public Mapping getMapping() {
        return this.patternBuilder.getMapping();
    }
    
    /**
     * Public method that construct the Pattern by creating and them build it
     * @param pattern The ETL pattern to be converted
     * @return {@code true} or {@code false} depending if the pattern can be converted
     */
    public boolean constructPattern(Transition pattern) {
        this.patternBuilder.createMapping(pattern);
        return this.patternBuilder.convert();
    }
}
