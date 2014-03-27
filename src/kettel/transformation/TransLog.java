package kettel.transformation;

import java.util.ArrayList;
import kettel.xml.Field;

/**
 *
 * @author hmg
 */
public class TransLog {

    private ArrayList<Field> fields;
    
    public TransLog() {
        this.fields = new ArrayList<>();
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
}
