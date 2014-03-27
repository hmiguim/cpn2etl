package kettel.transformation;

/**
 *
 * @author hmg
 */
public class TransInfoDirector {
    
    private TransInfoBuilder transInfoBuilder;
    
    public void setTransLogBuilder (TransInfoBuilder transInfoBuilder) {
        this.transInfoBuilder = transInfoBuilder;
    }
    
    public TransInfo getTransInfo() {
        return this.transInfoBuilder.getTransInfo();
    }
    
    public void constructTransInfo() {
        this.transInfoBuilder.createTransInfo();
        this.transInfoBuilder.buildTransInfo();
    }
}
