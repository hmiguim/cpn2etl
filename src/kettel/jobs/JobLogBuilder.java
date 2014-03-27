package kettel.jobs;

/**
 *
 * @author hmg
 */
public abstract class JobLogBuilder {

    protected JobLog joblog;
    
    public JobLog getJobLog() {
        return this.joblog;
    }
    
    public void createJobLog() {
        this.joblog = new JobLog();
    }
    
    public abstract void buildJobLog();
}
