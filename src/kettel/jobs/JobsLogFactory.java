package kettel.jobs;

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

    public JobLogDirector newJobLogDirector() {
        return new JobLogDirector();
    }
    
    /**
     * Creates a new instance of a {@code JobLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobLogTable
     */
    public JobLogConfiguration logTable() {
        return new JobLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code JobEntryLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobEntryLogTable
     */
    public JobEntryLogConfiguration entryLogTable() {
        return new JobEntryLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code JobChannelLogTable} using the
     * currently configured parameters.
     *
     * @return A new instance of a JobChannelLogTable
     */
    public JobChannelLogConfiguration channelLogTable() {
        return new JobChannelLogConfiguration();
    }
}
