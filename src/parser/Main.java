/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Place;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        try {
            FileInputStream file = new FileInputStream(new File("/Users/hmg/Desktop/SimpleProtocol.xml"));
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
             
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
             
            Document xmlDocument = builder.parse(file);
 
            XPath xPath =  XPathFactory.newInstance().newXPath();
            
            XPathExpression expr = xPath.compile("//place/type/text"); 
            NodeList nodes = (NodeList) expr.evaluate(xmlDocument,XPathConstants.NODESET);
            Place p = new Place();
            for (int i=0; i<nodes.getLength();i++) {
                Node node = nodes.item(i).getParentNode().getParentNode();
                p.setId(node.getAttributes().getNamedItem("id").getTextContent());
                NodeList childs = node.getChildNodes();
                for (int j=0;j<childs.getLength();j++) {
                    switch (childs.item(j).getNodeName()) {
                        case "posattr":
                            double x = Double.parseDouble(childs.item(j).getAttributes().getNamedItem("x").getTextContent());
                            double y = Double.parseDouble(childs.item(j).getAttributes().getNamedItem("y").getTextContent());
                            p.setPosX(x);
                            p.setPosY(y);
                            break;
                        case "text":
                            p.setText(childs.item(j).getTextContent());
                            break;
                    }
                }
                p.setType(nodes.item(i).getTextContent());
                System.out.println(p);
            }

        } catch (FileNotFoundException e) {
            
        }
    }
}
