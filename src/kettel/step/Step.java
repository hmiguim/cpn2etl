package kettel.step;

import java.util.ArrayList;
import kettel.Element;

/**
 *
 * @author hmg
 */
public class Step {
    
    private ArrayList<Element> elements;
    
    public Step() {
        this.elements = new ArrayList<>();
    }
    
    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
    
    public ArrayList<Element> getElements() {
        return this.elements;
    }
}
