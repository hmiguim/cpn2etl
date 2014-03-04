/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cpn.Cpn;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import xml.XMLBuilder;
import xml.XMLParser;
import xml.XMLFactory;

/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerConfigurationException, TransformerException {

        if (args.length == 0) {
            badUsage();
        } else if (args.length > 2 || args.length <= 1) {
            badUsage();
        } else if (args[0].equals("--help") || args[0].equals("-h")) {
            badUsage();
        }

        String path = args[0];
        String out = args[1];

        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            badUsage();
        }

        int i = f.getName().lastIndexOf('.');
        if (i > 0) {
            String extension = f.getName().substring(i + 1);
            if (!extension.equalsIgnoreCase("xml")) {
                badUsage();
            }
        }

        // Initialize the parserFactory
        XMLFactory xmlFactory = XMLFactory.newInstance();

        // Create a parser build to the specific path
        XMLParser parser = xmlFactory.newXMLParser(path);

        // Parse the file
        Cpn cpn = parser.parse();

        System.out.println(cpn.stats());

        XMLBuilder builder = xmlFactory.newXMLBuilder();

        builder.construct(out);
    }

    private static void badUsage() {
        System.out.println("usage: java -jar cpn2etl.jar [path] [output name]");
        exit(0);
    }
}
