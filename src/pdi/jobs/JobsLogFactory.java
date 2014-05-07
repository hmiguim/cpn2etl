package pdi.jobs;

/**
 *
 * @author hmg
 */
public class JobsLogFactory {

    /**
     * Protected constructor
     */
    protected JobsLogFactory() {
    }

    /**
     * Obtain a new instance of a {@code JobsLogTableFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code JobsLogTableFactory}
     */
    public static JobsLogFactory newInstance() {
        return new JobsLogFactory();
    }

    /**
     * Creates a new instance of a {@code JobLogDirector} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobLogDirector
     */
    public JobLogDirector newJobLogDirector() {
        return new JobLogDirector();
    }

    /**
     * Creates a new instance of a {@code JobLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobLogConfiguration
     */
    public JobLogConfiguration logTable() {
        return new JobLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code JobEntryLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobEntryLogConfiguration
     */
    public JobEntryLogConfiguration entryLogTable() {
        return new JobEntryLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code JobChannelLogConfiguration} using the
     * currently configured parameters.
     *
     * @return A new instance of a JobChannelLogConfiguration
     */
    public JobChannelLogConfiguration channelLogTable() {
        return new JobChannelLogConfiguration();
    }
}
