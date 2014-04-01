package kettel.transformation;

/**
 *
 * @author hmg
 */
public class TransInfoDirector {
    
    private TransInfoBuilder transInfoBuilder;
    
    /**
     * Sets the TransInfoBuilder
     *
     * @param transInfoBuilder The transInfoBuilder to be set
     */
    public void setTransLogBuilder (TransInfoBuilder transInfoBuilder) {
        this.transInfoBuilder = transInfoBuilder;
    }
    
    /**
     * Gets the TransInfo
     *
     * @return The {@code TransInfo}
     */
    public TransInfo getTransInfo() {
        return this.transInfoBuilder.getTransInfo();
    }
    
    /**
     * Public method that construct the TransInfo by creating and them build it
     */
    public void constructTransInfo() {
        this.transInfoBuilder.createTransInfo();
        this.transInfoBuilder.buildTransInfo();
    }
}
