package kettel.conversion;

/**
 *
 * @author hmg
 */
public class ConversionFactory {
    
    /**
     * Protected constructor
     */
    protected ConversionFactory() { }
    
    /**
     * Obtain a new instance of a {@code ConversionFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code ConversionFactory}
     */
    public static ConversionFactory newInstance() {
        return new ConversionFactory();
    }
    
    /**
     * Creates a new instance of a {@code ConversionBuilder} using the currently
     * configured parameters.
     *
     * @return A new instance of a ConversionBuilder
     */
    public ConversionBuilder newConversionBuilder() {
        return new ConversionBuilder();
    }
}
