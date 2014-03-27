package kettel.transformation;

/**
 *
 * @author hmg
 */
public abstract class TransInfoBuilder {

    protected TransInfo transInfo;
    
    public TransInfo getTransInfo() {
        return this.transInfo;
    }
    
    public void createTransInfo() {
        this.transInfo = new TransInfo();
    }
    
    public abstract void buildTransInfo();
}
