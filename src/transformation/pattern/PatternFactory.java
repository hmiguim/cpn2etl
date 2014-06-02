package transformation.pattern;

/**
 *
 * @author hmg
 */
public class PatternFactory {

    /**
     * Protected constructor
     */
    protected PatternFactory() {
    }

    /**
     * Obtain a new instance of a {@code PatternFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code PatternFactory}
     */
    public static PatternFactory newInstance() {
        return new PatternFactory();
    }

    /**
     * Creates a new instance of a {@code PatternDirector} using the
     * currently configured parameters.
     *
     * @return A new instance of a PatternDirector
     */
    public PatternDirector newPatternDirector() {
        return new PatternDirector();
    }

    /**
     * Returns a PatternBuilder for the specific {@code type}
     *
     * @param type The type of the various ETL patterns
     * @return The {@code PatternBuilder} for the ETL pattern that match the
     * {@code type} parameter
     */
    public PatternBuilder newPatternBuilder(String type) {
        switch (type) {
            case "SKP":
                return new SKP_Pattern();
            default: 
                if (type.toLowerCase().contains("scd/h")) return new SCDH_Pattern();
                if (type.toLowerCase().contains("cdc")) return new CDC_Pattern();
            break;
        }

        return null;
    }
}
