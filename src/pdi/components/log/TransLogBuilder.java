package pdi.components.log;

/**
 *
 * @author hmg
 */
public abstract class TransLogBuilder {

    protected TransLog transLog;
    
    /**
     * Get the {@code TransLog} builded
     * @return A {@code TransLog} 
     */
    public TransLog getTransLog() {
        return this.transLog;
    }
    
    /** 
     * Create a new {@code TransLog}
     */
    public void createTransLog() {
        this.transLog = new TransLog();
    }
    
    /**
     * Abstract method to be implemented in each specific TransLog type
     */
    public abstract void buildTransLog();
}
