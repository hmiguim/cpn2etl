package kettel.conversion;

/**
 *
 * @author hmg
 */
public class ConversionFactory {

    /**
     * Protected constructor
     */
    protected ConversionFactory() {
    }

    /**
     * Obtain a new instance of a {@code ConversionFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code ConversionFactory}
     */
    public static ConversionFactory newInstance() {
        return new ConversionFactory();
    }

    /**
     * Creates a new instance of a {@code ConversionDirector} using the
     * currently configured parameters.
     *
     * @return A new instance of a ConversionDirector
     */
    public ConversionDirector newConversionDirector() {
        return new ConversionDirector();
    }

    /**
     * Returns a ConversionBuilder for the specific {@code type}
     *
     * @param type The type of the various ETL patterns
     * @return The {@code ConversionBuilder} for the ETL pattern that match the
     * {@code type} parameter
     */
    public ConversionBuilder newConversionBuilder(String type) {
        switch (type) {
            case "SKP":
                return new SKPConverter();
        }

        return null;
    }
}
