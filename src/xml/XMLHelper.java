package xml;

import java.io.File;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pdi.components.xml.Field;

/**
 *
 * @author hmg
 */
public class XMLHelper {
      
    public static void finalize(Document document, File file) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, result);

    }
    
    /**
     * Method that returns a field log element according the field object
     *
     * @param nameField Object field with the information obtain on the
     * configuration files
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code field} with all the child nodes
     */
    public static Element createField(Field nameField, Document doc) {

        Element field = doc.createElement("field");
        Element id = doc.createElement("id");
        id.setTextContent(nameField.getId());
        field.appendChild(id);
        Element enable = doc.createElement("enabled");
        enable.setTextContent(nameField.getEnable());
        field.appendChild(enable);
        Element name = doc.createElement("name");
        name.setTextContent(nameField.getName());
        field.appendChild(name);

        return field;
    }
}
