package kettel.step;

/**
 *
 * @author hmg
 */
public abstract class StepBuilder {
    
    protected Step step;
    
    public Step getStep() {
        return this.step;
    }
    
    public void createStep() {
        this.step = new Step();
    }
    
    public abstract void buildStep();
}
