package kettel.step;

/**
 *
 * @author hmg
 */
public class StepDirector {

    private StepBuilder stepBuilder;

    /**
     * Sets the StepBuilder
     *
     * @param stepBuilder The stepBuilder to be set
     */
    public void setStepBuilder(StepBuilder stepBuilder) {
        this.stepBuilder = stepBuilder;
    }

    /**
     * Gets the Step
     *
     * @return The {@code Step}
     */
    public Step getStep() {
        return this.stepBuilder.getStep();
    }

    /**
     * Public method that construct the Step by creating and them build it
     */
    public void constructStep() {
        this.stepBuilder.createStep();
        this.stepBuilder.buildStep();
    }

}
