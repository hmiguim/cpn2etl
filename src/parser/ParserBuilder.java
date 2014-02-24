/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import cpn.Arc;
import cpn.Cpn;
import cpn.Place;
import cpn.Port;
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
    private Cpn cpn;

    public ParserBuilder() {

    }

    public ParserBuilder(String path, DocumentBuilder b) throws FileNotFoundException, SAXException, IOException {
        this.cpn = new Cpn();
        this.file = new FileInputStream(new File(path));
        this.builder = b;
    }

    public Cpn parse() throws SAXException, IOException, XPathExpressionException {
        this.xmlDocument = this.builder.parse(this.file);
        this.parsePlaces();
        this.builder.reset();
        this.parseTransitions();
        this.builder.reset();
        this.parseArcs();
        this.builder.reset();

        return cpn.clone();
    }

    public void parsePlaces() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//place/type/text");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Place p;
        Port port;
        HashMap<String, Place> places = new HashMap<>();
        HashMap<String, Place> places_port = new HashMap<>();
        boolean is_port = false;

        for (int i = 0; i < nodes.getLength(); i++) {
            p = new Place();
            port = new Port();
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
                        p.setText(p.getText().replaceAll("\n", " "));
                        break;
                    case "port":
                        is_port = true;
                        port.setType(childs.item(j).getAttributes().getNamedItem("type").getTextContent());
                        port.setId(childs.item(j).getAttributes().getNamedItem("id").getTextContent());
                        p.setPort(port);
                        break;
                }

                p.setType(nodes.item(i).getTextContent());
            }
            if (is_port) {
                places_port.put(p.getId(), p);
                is_port = false;
            } else {
                places.put(p.getId(), p);
            }
        }
        this.cpn.setPlaces(places);
        this.cpn.setPlacesPort(places_port);
    }

    public void parseTransitions() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//trans");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Transition t;
        HashMap<String, Transition> transitions = new HashMap<>();

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
                        t.setText(text.replaceAll("\n", " "));
                        break;
                }
            }
            transitions.put(t.getId(), t);
        }
        this.cpn.setTransitions(transitions);
    }

    public void parseArcs() throws SAXException, IOException, XPathExpressionException {

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression expr = xPath.compile("//arc");

        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Arc a;
        HashMap<String, Arc> arcs = new HashMap<>();

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
                        Transition t = this.cpn.getTransitions().get(t_id);
                        a.setTransEnd(t);
                        break;
                    case "placeend":
                        String p_id = childs.item(j).getAttributes().getNamedItem("idref").getTextContent();
                        Place p = this.cpn.getPlaces().get(p_id);
                        a.setPlaceEnd(p);
                        break;
                    case "annot":
                        NodeList annot_child_nodes = childs.item(j).getChildNodes();
                        for (int k = 0; k < annot_child_nodes.getLength(); k++) {
                            if (annot_child_nodes.item(k).getNodeName().equalsIgnoreCase("TEXT")) {
                                String text = annot_child_nodes.item(k).getTextContent();
                                a.setText(text.replaceAll("\n", " "));
                            }
                        }
                        break;
                }
            }
            arcs.put(a.getId(), a);
        }
        this.cpn.setArcs(arcs);
    }
}
