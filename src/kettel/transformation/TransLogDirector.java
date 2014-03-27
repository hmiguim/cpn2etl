package kettel.transformation;

/**
 *
 * @author hmg
 */
public class TransLogDirector {

    private TransLogBuilder transLogBuilder;
    
    public void setTransLogBuilder (TransLogBuilder transLogBuilder) {
        this.transLogBuilder = transLogBuilder;
    }
    
    public TransLog getTransLog() {
        return this.transLogBuilder.getTransLog();
    }
    
    public void constructTransLog() {
        this.transLogBuilder.createTransLog();
        this.transLogBuilder.buildTransLog();
    }
}
