package pdi.transformation;

/**
 *
 * @author hmg
 */
public abstract class TransInfoBuilder {

    protected TransInfo transInfo;
    
    /**
     * Get the {@code TransInfo} builded
     * @return A {@code TransInfo} 
     */
    public TransInfo getTransInfo() {
        return this.transInfo;
    }
    
    /** 
     * Create a new {@code TransInfo}
     */
    public void createTransInfo() {
        this.transInfo = new TransInfo();
    }
    
    /**
     * Abstract method to be implemented in each specific TransInfo type
     */
    public abstract void buildTransInfo();
}
