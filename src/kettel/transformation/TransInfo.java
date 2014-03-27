package kettel.transformation;

import java.util.ArrayList;
import kettel.xml.Element;

/**
 *
 * @author hmg
 */
public class TransInfo {

    private ArrayList<Element> element;

    public TransInfo() {
        this.element = new ArrayList<>();
    }

    public ArrayList<Element> getElement() {
        return element;
    }

    public void setElement(ArrayList<Element> element) {
        this.element = element;
    }
}
