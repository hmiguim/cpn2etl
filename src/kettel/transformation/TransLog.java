package kettel.transformation;

import java.util.ArrayList;
import kettel.xml.Field;

/**
 *
 * @author hmg
 */
public class TransLog {

    private ArrayList<Field> fields;
    
    /**
     * Public constructor
     */
    public TransLog() {
        this.fields = new ArrayList<>();
    }

    /**
     * Gets the fields
     *
     * @return An ArrayList of {@link Field}
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Sets the {@code fields}
     *
     * @param fields the fields to set
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
}
