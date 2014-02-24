/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Arc;
import cpn.Cpn;
import cpn.Place;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        // Initialize the parserFactory
        ParserFactory parserFactory = ParserFactory.newInstance();

        // Create a parser build to the specific path
        ParserBuilder parserBuild = parserFactory.newParserBuilder("/Users/hmg/Documents/Universidade/MSc Dissertation/Files/Surrogate Key Pipeline.xml");

        // Parse the file
        Cpn cpn = parserBuild.parse();

        // Get the parsed elements
        HashMap<String,Arc> arcs = cpn.getArcs();
        HashMap<String,Place> places = cpn.getPlacesPort();
        
        for(Entry<String,Place> entry : places.entrySet()) {
           // StringBuilder str = new StringBuilder("Arc id = " + entry.getValue().getId());
           // str.append("; Text: ").append(entry.getValue().getText());
           // str.append("; Orientation: ").append(entry.getValue().getOrientation());
           // str.append("; Place: ").append(entry.getValue().getPlaceEnd().getText());
           // str.append("; Trans: ").append(entry.getValue().getTransEnd().getText());
            
           // System.out.println(str.toString());
           
            System.out.println(entry.getValue().toString());
        }
    
        
        
        
    }
}
