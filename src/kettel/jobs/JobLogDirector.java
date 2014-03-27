package kettel.jobs;

/**
 *
 * @author hmg
 */
public class JobLogDirector {

    private JobLogBuilder jobLogBuilder;
    
    public void setJobLogBuilder(JobLogBuilder jobLogBuilder) {
        this.jobLogBuilder = jobLogBuilder;
    }
    
    public JobLog getJobLog() {
        return this.jobLogBuilder.getJobLog();
    }
    
    public void constructJobLog() {
        this.jobLogBuilder.createJobLog();
        this.jobLogBuilder.buildJobLog();
    }
}
