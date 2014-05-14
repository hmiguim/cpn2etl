package transformation.pattern.activity;

/**
 *
 * @author hmg
 */
public class PatternActivityFactory {

    /**
     * Protected constructor
     */
    protected PatternActivityFactory() {
    }

    /**
     * Obtain a new instance of a {@code PatternActivityFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code PatternActivityFactory}
     */
    public static PatternActivityFactory newInstance() {
        return new PatternActivityFactory();
    }

    /**
     * Creates a new instance of a {@code PatternActivityDirector} using the
     * currently configured parameters.
     *
     * @return A new instance of a PatternActivityDirector
     */
    public PatternActivityDirector newPatternActivityDirector() {
        return new PatternActivityDirector();
    }

    /**
     * Returns a PatternActivityBuilder for the specific {@code type}
     *
     * @param type The type of the various ETL patterns activities
     * @return The {@code PatternActivity} for the ETL pattern activity that
     * match the {@code type} parameter
     */
    public PatternActivityBuilder newPatternActivityBuilder(String type) {

        switch (type) {
            case "Audit Data Verification":
                return new AuditDataVerificationActivity();

            case "Delete Record":
                return new DeleteRecordActivity();

            case "Update Record":
                break;
            case "Insert Record":
                break;
        }

        return null;
    }
}
