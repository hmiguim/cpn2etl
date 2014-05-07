package pdi.jobs;

import java.util.ArrayList;
import pdi.xml.Field;

/**
 *
 * @author hmg
 */
public class JobLog {

    private ArrayList<Field> fields;
    
    /**
     * Public Method
     */
    public JobLog() {
        this.fields = new ArrayList<>();
    }
    
    /**
     * Sets the {@code fields}
     * @param fields the fields to set
     */
    public void setLog(ArrayList<Field> fields) {
        this.fields = fields;
    }
    
    /**
     * Gets the fields
     * @return An ArrayList of {@link Field}
     */
    public ArrayList<Field> getLog() {
        return this.fields;
    }
}
