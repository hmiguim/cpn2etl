/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

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
public class ParserFactory {
    
    private final DocumentBuilderFactory builderFactory;
    private final DocumentBuilder builder;
    
    public static ParserFactory newInstance() throws ParserConfigurationException {
        return new ParserFactory();
    }
    
    public ParserBuilder newParserBuilder(String path) throws FileNotFoundException, SAXException, IOException {
        return new ParserBuilder(path,builder);
    }
    
    public Document newDocument() {
        return builder.newDocument();
    }
    
    private ParserFactory() throws ParserConfigurationException { 
        builderFactory = DocumentBuilderFactory.newInstance();
        builder =  builderFactory.newDocumentBuilder();
    }
}
