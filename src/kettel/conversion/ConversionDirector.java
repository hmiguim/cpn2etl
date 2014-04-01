package kettel.conversion;

import cpn.Transition;
import kettel.mapping.Mapping;

/**
 *
 * @author hmg
 */
public class ConversionDirector {

    private ConversionBuilder conversionBuilder;
    
     /**
     * Sets the conversionBuilder
     * @param conversionBuilder The conversionBuilder to be set
     */
    public void setConversionBuilder (ConversionBuilder conversionBuilder) {
        this.conversionBuilder = conversionBuilder;
    }
    
    /**
     * Gets the mapping
     * @return The {@link Mapping}
     */
    public Mapping getMapping() {
        return this.conversionBuilder.getMapping();
    }
    
    /**
     * Public method that construct the Conversion by creating and them build it
     * @param pattern The ETL pattern to be converted
     * @return {@code true} or {@code false} depending if the pattern can be converted
     */
    public boolean constructConversion(Transition pattern) {
        this.conversionBuilder.createMapping(pattern);
        return this.conversionBuilder.convert();
    }
}
