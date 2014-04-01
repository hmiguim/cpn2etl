package kettel.step;

import java.util.ArrayList;
import kettel.xml.Element;

/**
 *
 * @author hmg
 */
public class Step {
    
    private ArrayList<Element> elements;
    
    /**
     * Public Method
     */
    public Step() {
        this.elements = new ArrayList<>();
    }
    
    /**
     * Sets the {@code elements}
     * @param elements the elements to set
     */
    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
     /**
     * Gets the elements
     * @return An ArrayList of {@link Element}
     */
    public ArrayList<Element> getElements() {
        return this.elements;
    }
}
