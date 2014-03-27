package kettel.conversion;

import cpn.Transition;
import kettel.mapping.Mapping;

/**
 *
 * @author hmg
 */
public class ConversionDirector {

    private ConversionBuilder conversionBuilder;
    
    public void setConversionBuilder (ConversionBuilder conversionBuilder) {
        this.conversionBuilder = conversionBuilder;
    }
    
    public Mapping getMapping() {
        return this.conversionBuilder.getMapping();
    }
    
    public boolean constructConversion(Transition module) {
        this.conversionBuilder.createMapping(module);
        return this.conversionBuilder.convert();
    }
}
