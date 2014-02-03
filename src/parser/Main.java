/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Arc;
import cpn.Place;
import cpn.Transition;
import java.io.IOException;
import java.util.HashMap;
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
        ParserBuilder parserBuild = parserFactory.newParserBuilder("/Users/hmg/Desktop/SimpleProtocol.xml");

        // Parse the file
        parserBuild.parse();

        // Get the parsed elements
        HashMap<String, Place> places = parserBuild.getPlaces();
        HashMap<String, Transition> transitions = parserBuild.getTransitions();
        HashMap<String, Arc> arcs = parserBuild.getArcs();

    }
}
