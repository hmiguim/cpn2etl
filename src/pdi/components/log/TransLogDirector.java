package pdi.components.log;

/**
 *
 * @author hmg
 */
public class TransLogDirector {

    private TransLogBuilder transLogBuilder;
    
    /**
     * Sets the TransLogBuilder
     *
     * @param transLogBuilder The transLogBuilder to be set
     */
    public void setTransLogBuilder (TransLogBuilder transLogBuilder) {
        this.transLogBuilder = transLogBuilder;
    }
    
    /**
     * Gets the TransLog
     *
     * @return The {@code TransLog}
     */
    public TransLog getTransLog() {
        return this.transLogBuilder.getTransLog();
    }
    
    /**
     * Public method that construct the TransLog by creating and them build it
     */
    public void constructTransLog() {
        this.transLogBuilder.createTransLog();
        this.transLogBuilder.buildTransLog();
    }
}
