/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package etl;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import xml.XMLFactory;

/**
 *
 * @author hmg
 */
public class Constructor {
    
    private final XMLFactory document;
    
    public Constructor() throws ParserConfigurationException {
        document = XMLFactory.newInstance();
    }
    
    public void create(String name) {
        Document doc = document.newDocument();
    }
    
}
