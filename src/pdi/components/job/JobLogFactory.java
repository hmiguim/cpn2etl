package pdi.components.job;

import pdi.components.job.JobLogDirector;
import pdi.components.job.JobLogConfiguration;
import pdi.components.job.JobEntryLogConfiguration;
import pdi.components.job.JobChannelLogConfiguration;

/**
 *
 * @author hmg
 */
public class JobLogFactory {

    /**
     * Protected constructor
     */
    protected JobLogFactory() {
    }

    /**
     * Obtain a new instance of a {@code JobsLogTableFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code JobsLogTableFactory}
     */
    public static JobLogFactory newInstance() {
        return new JobLogFactory();
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
