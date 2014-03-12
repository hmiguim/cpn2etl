/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author hmg
 */
public class XMLFactory {

    public static XMLFactory newInstance() {
        return new XMLFactory();
    }

    public XMLParser newXMLParser(File file) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
        return new XMLParser(file);
    }

    public XMLBuilder newXMLBuilder() throws ParserConfigurationException {
        return new XMLBuilder();
    }

    protected XMLFactory() { }
}
