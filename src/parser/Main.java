/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Cpn;
import cpn.Page;
import cpn.Transition;
import java.io.IOException;
import java.util.LinkedHashMap;
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
        ParserBuilder parserBuild = parserFactory.newParserBuilder("/Users/hmg/Desktop/etl.xml");

        // Parse the file
        Cpn cpn = parserBuild.parse();

        // Get the parsed elements
        LinkedHashMap<String, Page> pages = cpn.getPages();

        
        int count_places = 0;
        int count_transitions = 0;
        int count_arcs = 0;
        for (Entry<String, Page> entry : pages.entrySet()) {
            
            Page p = entry.getValue();
            
            count_places += p.getPlaces().size();
            count_arcs += p.getArcs().size();
            
            for (Entry<String,Transition> trans_entry : p.getTransitions().entrySet()) {
                if (!trans_entry.getValue().isSubPage()) {
                    count_transitions += 1;
                }
            }
        } 
        
        System.out.println("Pages: " + pages.size());
        
        System.out.println("Places: " + count_places);
        
        System.out.println("Transitions: " + count_transitions);
        
        System.out.println("Arcs: " + count_arcs);
    }
}
