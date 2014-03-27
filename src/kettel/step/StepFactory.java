package kettel.step;

/**
 *
 * @author hmg
 */
public class StepFactory {

    protected StepFactory() {
    }

    public static StepFactory newInstance() {
        return new StepFactory();
    }

    public StepDirector newStepDirector() {
        return new StepDirector();
    }

    public StepBuilder newStepConfiguration(String type) {
        
        StepBuilder sb = null;
        
        switch (type) {
            case "TableInput":
                sb = new TableInputConfiguration();
                break;
            case "TableOutput":
                sb = new TableOutputConfiguration();
                break;
            case "DBLookup" :
                sb = new DBLookupConfiguration();
                break;
        }
        return sb;
    }

}
