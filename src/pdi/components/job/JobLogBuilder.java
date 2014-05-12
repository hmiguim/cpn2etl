package pdi.components.job;

import pdi.components.job.JobLog;

/**
 *
 * @author hmg
 */
public abstract class JobLogBuilder {

    protected JobLog joblog;
    
    /**
     * Get the {@code JobLog} builded
     * @return A {@code JobLog} 
     */
    public JobLog getJobLog() {
        return this.joblog;
    }
    
    /** 
     * Create a new {@code JobLog}
     */
    public void createJobLog() {
        this.joblog = new JobLog();
    }
    
    /**
     * Abstract method to be implemented in each specific JobLog type
     * Build the JobLog with the fields
     */
    public abstract void buildJobLog();
}
