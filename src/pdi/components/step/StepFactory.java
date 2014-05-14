package pdi.components.step;

/**
 *
 * @author hmg
 */
public class StepFactory {

    /**
     * Protected constructor
     */
    protected StepFactory() {
    }

    /**
     * Obtain a new instance of a {@code StepFactory}. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code StepFactory}
     */
    public static StepFactory newInstance() {
        return new StepFactory();
    }

    /**
     * Creates a new instance of a {@code StepDirector} using the currently
     * configured parameters.
     *
     * @return A new instance of a StepDirector
     */
    public StepDirector newStepDirector() {
        return new StepDirector();
    }

    /**
     * Returns a SetpBuilder for the specific {@code type}
     *
     * @param type The type of the various Kettle ETL elements
     * @return The {@code StepBuilder} for the Kettle ETL element that match the
     * {@code type} parameter
     */
    public StepBuilder newStepConfiguration(String type) {

        StepBuilder sb = null;

        switch (type) {
            case "TableInput":
                sb = new TableInputConfiguration();
                break;
            case "TableOutput":
                sb = new TableOutputConfiguration();
                break;
            case "DBLookup":
                sb = new DBLookupConfiguration();
                break;
            case "TransExecutor":
                sb = new TransExecutorConfiguration();
                break;
            case "Validator":
                sb = new ValidatorConfiguration();
                break;
        }
        return sb;
    }

}
