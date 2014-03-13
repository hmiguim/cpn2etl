package kettel.jobs;

/**
 *
 * @author hmg
 */
public class JobsLogTableFactory {

    /**
     * Protected constructor
     */
    protected JobsLogTableFactory() {
    }

    /**
     * Obtain a new instance of a {@code JobsLogTableFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code JobsLogTableFactory}
     */
    public static JobsLogTableFactory newInstance() {
        return new JobsLogTableFactory();
    }

    /**
     * Creates a new instance of a {@code JobLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobLogTable
     */
    public JobLogTable logTable() {
        return new JobLogTable();
    }

    /**
     * Creates a new instance of a {@code JobEntryLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a JobEntryLogTable
     */
    public JobEntryLogTable entryLogTable() {
        return new JobEntryLogTable();
    }

    /**
     * Creates a new instance of a {@code JobChannelLogTable} using the
     * currently configured parameters.
     *
     * @return A new instance of a JobChannelLogTable
     */
    public JobChannelLogTable channelLogTable() {
        return new JobChannelLogTable();
    }
}
