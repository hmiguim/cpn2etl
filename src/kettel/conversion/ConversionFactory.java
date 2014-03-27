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

    public ConversionBuilder newConversionBuilder(String name) {
        switch (name) {
            case "SKP":
                return this.newSKPConverter();
        }

        return null;
    }

    private SKPConverter newSKPConverter() {
        return new SKPConverter();
    }
}
