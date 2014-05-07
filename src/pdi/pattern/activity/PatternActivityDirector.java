package pdi.pattern.activity;

import cpn.Transition;
import pdi.mapping.Mapping;

/**
 *
 * @author hmg
 */
public class PatternActivityDirector {
    
    private PatternActivityBuilder patternActivity;
    
    /**
     * Sets the patternActivity
     * @param patternActivity The patternActivity to be set
     */
    public void setPatternActivityBuilder (PatternActivityBuilder patternActivity) {
        this.patternActivity = patternActivity;
    }
    
    /**
     * Gets the mapping
     * @return The {@link Mapping}
    */
    public Mapping getMapping() {
        return this.patternActivity.getMapping();
    }
    
    /**
     * Public method that construct the patternActivity by creating and them build it
     * @param activity The ETL pattern activity to be converted
     * @return {@code true} or {@code false} depending if the pattern activity can be converted
     */
    public boolean constructPatternActivity(Transition activity) {
        this.patternActivity.createMapping(activity);
        return this.patternActivity.convert();
    }
}
