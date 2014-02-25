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
        ParserBuilder parserBuild = parserFactory.newParserBuilder("/Users/hmg/Desktop/HierarchicalProtocol.xml");

        // Parse the file
        Cpn cpn = parserBuild.parse();

        // Get the parsed elements
        LinkedHashMap<String, Page> pages = cpn.getPages();

        
        int count_places = 0;
        for (Entry<String, Page> entry : pages.entrySet()) {
            
            System.out.println("Page: " + entry.getValue().getName() + "; ID: " + entry.getValue().getId());
            
            for(Entry<String,Transition> transition : entry.getValue().getTransitions().entrySet()) {
                System.out.println("Transition: " + transition.getValue().getText());
            }
            
            count_places += entry.getValue().getTransitions().size();
            
        }
        
        System.out.println(count_places);

    }
}
