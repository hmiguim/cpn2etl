package kettel.step;

/**
 *
 * @author hmg
 */
public abstract class StepBuilder {
    
    protected Step step;
    
    /**
     * Get the {@code Step} builded
     * @return A {@code Step} 
     */
    public Step getStep() {
        return this.step;
    }
    
    /** 
     * Create a new {@code Step}
     */
    public void createStep() {
        this.step = new Step();
    }
    
    /**
     * Abstract method to be implemented in each specific Step type
     */
    public abstract void buildStep();
}
