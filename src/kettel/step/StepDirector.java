package kettel.step;

/**
 *
 * @author hmg
 */
public class StepDirector {
    
    private StepBuilder stepBuilder;
    
    public void setStepBuilder(StepBuilder sb) {
        this.stepBuilder = sb;
    }
    
    public Step getStep() {
        return this.stepBuilder.getStep();
    }
    
    public void constructStep() {
        this.stepBuilder.createStep();
        this.stepBuilder.buildStep();
    }
    
}
