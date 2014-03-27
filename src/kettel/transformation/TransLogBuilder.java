package kettel.transformation;

/**
 *
 * @author hmg
 */
public abstract class TransLogBuilder {

    protected TransLog transLog;
    
    public TransLog getTransLog() {
        return this.transLog;
    }
    
    public void createTransLog() {
        this.transLog = new TransLog();
    }
    
    public abstract void buildTransLog();
}
