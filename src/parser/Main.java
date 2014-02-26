/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Cpn;
import java.io.IOException;
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
    //    LinkedHashMap<String, Page> pages = cpn.getPages();
        
      //  for(Entry<String,Page> entry : pages.entrySet()) {
            
        //    for(Entry<String,Arc> arc_entry : entry.getValue().getArcs().entrySet()) {
          //      System.out.println(arc_entry.getValue().getText());
            //}
            
        //}
    }
}
