package kettel.jobs;

/**
 *
 * @author hmg
 */
public class JobLogDirector {

    private JobLogBuilder jobLogBuilder;

    /**
     * Sets the JobLogBuilder
     *
     * @param jobLogBuilder The jobLogBuilder to be set
     */
    public void setJobLogBuilder(JobLogBuilder jobLogBuilder) {
        this.jobLogBuilder = jobLogBuilder;
    }

    /**
     * Gets the jobLog
     *
     * @return The {@code JobLog}
     */
    public JobLog getJobLog() {
        return this.jobLogBuilder.getJobLog();
    }

    /**
     * Public method that construct the JobLog by creating and them build it
     */
    public void constructJobLog() {
        this.jobLogBuilder.createJobLog();
        this.jobLogBuilder.buildJobLog();
    }
}
