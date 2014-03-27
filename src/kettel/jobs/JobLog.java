package kettel.jobs;

import java.util.ArrayList;
import kettel.Field;

/**
 *
 * @author hmg
 */
public class JobLog {

    private ArrayList<Field> fields;
    
    public JobLog() {
        this.fields = new ArrayList<>();
    }
    
    public void setLog(ArrayList<Field> fields) {
        this.fields = fields;
    }
    
    public ArrayList<Field> getLog() {
        return this.fields;
    }
}
