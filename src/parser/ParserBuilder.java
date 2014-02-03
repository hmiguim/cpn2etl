/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Arc;
import cpn.Place;
import cpn.Transition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author hmg
 */
public class ParserBuilder {

    private FileInputStream file;
    private DocumentBuilder builder;
    private Document xmlDocument;
    private HashMap<String, Place> places;
    private HashMap<String, Transition> transitions;
    private HashMap<String, Arc> arcs;

    public ParserBuilder() {

    }

    public ParserBuilder(String path, DocumentBuilder b) throws FileNotFoundException, SAXException, IOException {
        this.places = new HashMap<>();
        this.transitions = new HashMap();
        this.arcs = new HashMap();
        this.file = new FileInputStream(new File(path));
        this.builder = b;
    }

    public void parse() throws SAXException, IOException, XPathExpressionException {
        this.xmlDocument = this.builder.parse(this.file);
        this.parsePlaces();
        this.parseTransitions();
        this.parseArcs();
        this.builder.reset();
    }

    public HashMap<String, Place> getPlaces() {
        return places;
    }

    public HashMap<String, Transition> getTransitions() {
        return transitions;
    }

    public HashMap<String, Arc> getArcs() {
        return arcs;
    }

    public void parsePlaces() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//place/type/text");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Place p;

        for (int i = 0; i < nodes.getLength(); i++) {
            p = new Place();
            Node node = nodes.item(i).getParentNode().getParentNode();
            p.setId(node.getAttributes().getNamedItem("id").getTextContent());
            NodeList childs = node.getChildNodes();
            for (int j = 0; j < childs.getLength(); j++) {
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

            this.places.put(p.getId(), p);

        }

        this.builder.reset();
    }

    public void parseTransitions() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//trans");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Transition t;

        for (int i = 0; i < nodes.getLength(); i++) {
            t = new Transition();
            t.setId(nodes.item(i).getAttributes().getNamedItem("id").getTextContent());
            NodeList childs = nodes.item(i).getChildNodes();
            for (int j = 0; j < childs.getLength(); j++) {
                switch (childs.item(j).getNodeName()) {
                    case "posattr":
                        double x = Double.parseDouble(childs.item(j).getAttributes().getNamedItem("x").getTextContent());
                        double y = Double.parseDouble(childs.item(j).getAttributes().getNamedItem("y").getTextContent());
                        t.setPosX(x);
                        t.setPosY(y);
                        break;
                    case "text":
                        String text = childs.item(j).getTextContent();
                        t.setText(text.replaceFirst("\n", " "));
                        break;
                }
            }
            this.transitions.put(t.getId(), t);
        }
    }

    public void parseArcs() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//arc");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Arc a;

        for (int i = 0; i < nodes.getLength(); i++) {
            a = new Arc();

            NamedNodeMap attr = nodes.item(i).getAttributes();

            a.setId(attr.getNamedItem("id").getTextContent());
            a.setOrientation(attr.getNamedItem("orientation").getTextContent());

            NodeList childs = nodes.item(i).getChildNodes();
            for (int j = 0; j < childs.getLength(); j++) {
                switch (childs.item(j).getNodeName()) {
                    case "transend":
                        String t_id = childs.item(j).getAttributes().getNamedItem("idref").getTextContent();
                        Transition t = this.transitions.get(t_id);
                        a.setTransEnd(t);
                        break;
                    case "placeend":
                        String p_id = childs.item(j).getAttributes().getNamedItem("idref").getTextContent();
                        Place p = this.places.get(p_id);
                        a.setPlaceEnd(p);
                        break;
                    case "annot":
                        NodeList annot_child_nodes = childs.item(j).getChildNodes();
                        for (int k = 0 ; k<annot_child_nodes.getLength();k++) {
                            if (annot_child_nodes.item(k).getNodeName().equalsIgnoreCase("TEXT")) {
                                String text = annot_child_nodes.item(k).getTextContent();
                                a.setText(text.replaceAll("\n", " "));
                            }
                        }
                        break;
                }
            }

            this.arcs.put(a.getId(), a);
        }
    }
}
