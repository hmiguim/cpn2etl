/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author hmg
 */
public class XMLFactory {
    
    private final DocumentBuilderFactory builderFactory;
    private final DocumentBuilder builder;
    
    public static XMLFactory newInstance() throws ParserConfigurationException {
        return new XMLFactory();
    }
    
    public XMLParser newXMLParser(String path) throws FileNotFoundException, SAXException, IOException {
        return new XMLParser(path,builder);
    }
    
    public XMLBuilder newXMLBuilder() throws ParserConfigurationException {
        return new XMLBuilder(builder);
    }
    
    private XMLFactory() throws ParserConfigurationException { 
        builderFactory = DocumentBuilderFactory.newInstance();
        builder =  builderFactory.newDocumentBuilder();
    }
}
