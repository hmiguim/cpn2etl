package pdi.transformation;

import java.util.ArrayList;
import pdi.xml.Element;

/**
 *
 * @author hmg
 */
public class TransInfo {

    private ArrayList<Element> elements;

    /**
     * Public constructor
     */
    public TransInfo() {
        this.elements = new ArrayList<>();
    }

    /**
     * Gets the elements
     *
     * @return An ArrayList of {@link Element}
     */
    public ArrayList<Element> getElement() {
        return elements;
    }

    /**
     * Sets the {@code elements}
     *
     * @param elements the elements to set
     */
    public void setElement(ArrayList<Element> elements) {
        this.elements = elements;
    }
}
