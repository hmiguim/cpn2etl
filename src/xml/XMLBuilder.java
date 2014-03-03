/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import cpn.Arc;
import cpn.Cpn;
import cpn.Page;
import cpn.Place;
import cpn.Port;
import cpn.SubPage;
import cpn.Transition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
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
public class XMLBuilder {

    private FileInputStream file;
    private DocumentBuilder builder;
    private Document xmlDocument;
    private Cpn cpn;

    private final ArrayList<String> ports = new ArrayList<>();

    public XMLBuilder() { }

    public XMLBuilder(String path, DocumentBuilder b) throws FileNotFoundException, SAXException, IOException {
        this.cpn = new Cpn();
        this.file = new FileInputStream(new File(path));
        this.builder = b;
    }

    // Public Method to invoke the parse
    public Cpn parse() throws SAXException, IOException, XPathExpressionException {
        this.xmlDocument = this.builder.parse(this.file);
        this.parseCPN();
        this.builder.reset();
        return cpn.clone();
    }

    // Parse the CPN File
    private void parseCPN() throws XPathExpressionException, SAXException, IOException {

        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xPath.compile("//page");
        NodeList nodes = (NodeList) expr.evaluate(this.xmlDocument, XPathConstants.NODESET);

        Page page;

        // Linked Hash with the pages from the CPN file
        LinkedHashMap<String, Page> pages = new LinkedHashMap<>();

        // Linked Hash with the places from a page
        LinkedHashMap<String, Place> places;

        // Linked Hash with the transitions from a page
        LinkedHashMap<String, Transition> transitions;

        // Linked Hash with the arcs from a page
        LinkedHashMap<String, Arc> arcs;

        for (int i = 0; i < nodes.getLength(); i++) {

            page = new Page();
            places = new LinkedHashMap<>();
            transitions = new LinkedHashMap<>();
            arcs = new LinkedHashMap<>();

            page.setId(nodes.item(i).getAttributes().getNamedItem("id").getTextContent());

            NodeList childs = nodes.item(i).getChildNodes();

            for (int j = 0; j < childs.getLength(); j++) {
                switch (childs.item(j).getNodeName()) {
                    case "pageattr":
                        page.setName(childs.item(j).getAttributes().getNamedItem("name").getTextContent());
                        break;
                    case "place":
                        Place p = parsePlace(childs.item(j));
                        places.put(p.getId(), p);
                        page.setPlaces(places);
                        break;
                    case "trans":
                        Transition t = parseTransition(childs.item(j));
                        transitions.put(t.getId(), t);
                        page.setTransitions(transitions);
                        break;
                    case "arc":
                        Arc a = parseArc(childs.item(j), page);
                        arcs.put(a.getId(), a);
                        page.setArcs(arcs);
                }

            }
            pages.put(page.getId(), page);
        }

        updatePorts(pages);
        updateSubPageInfo(pages);

        cpn.setPages(pages);
    }

    // Return the information for each place
    private Place parsePlace(Node node) {

        NodeList childs = node.getChildNodes();

        Place p = new Place();

        p.setId(node.getAttributes().getNamedItem("id").getTextContent());

        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()) {
                case "posattr":
                    double x = Double.parseDouble(childs.item(i).getAttributes().getNamedItem("x").getTextContent());
                    double y = Double.parseDouble(childs.item(i).getAttributes().getNamedItem("y").getTextContent());
                    p.setPosX(x);
                    p.setPosY(y);
                    break;
                case "text":
                    p.setText(childs.item(i).getTextContent());
                    p.setText(p.getText().replaceAll("\n", " "));
                    break;
                case "type":
                    NodeList type_node = childs.item(i).getChildNodes();
                    for (int j = 0; j < type_node.getLength(); j++) {
                        switch (type_node.item(j).getNodeName()) {
                            case "text":
                                p.setType(type_node.item(j).getTextContent());
                                p.setText(p.getText().replaceAll("\n", " "));
                                break;
                        }
                    }
                    break;
                case "port":
                    p.setPortInfo(parsePort(childs.item(i)));
                    p.setPort(true);
                    break;
            }
        }
        return p;
    }

    // Return the information for each transition
    private Transition parseTransition(Node node) {

        Transition t = new Transition();

        t.setId(node.getAttributes().getNamedItem("id").getTextContent());

        NodeList childs = node.getChildNodes();

        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()) {
                case "posattr":
                    double x = Double.parseDouble(childs.item(i).getAttributes().getNamedItem("x").getTextContent());
                    double y = Double.parseDouble(childs.item(i).getAttributes().getNamedItem("y").getTextContent());
                    t.setPosX(x);
                    t.setPosY(y);
                    break;
                case "text":
                    String text = childs.item(i).getTextContent();
                    t.setText(text.replaceAll("\n", " "));
                    break;
                case "subst":
                    t.setSubpage(true);
                    t.setSubPageInfo(parseSubPages(childs.item(i)));
                    parsePortSock(childs.item(i));
                    break;
            }
        }

        return t;
    }

    // Return the information for each arc
    private Arc parseArc(Node node, Page page) {

        Arc a = new Arc();

        a.setId(node.getAttributes().getNamedItem("id").getTextContent());

        NodeList childs = node.getChildNodes();

        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()) {
                case "transend":
                    String t_id = childs.item(i).getAttributes().getNamedItem("idref").getTextContent();
                    Transition t = page.getTransitions().get(t_id);
                    a.setTransEnd(t);
                    break;
                case "placeend":
                    String p_id = childs.item(i).getAttributes().getNamedItem("idref").getTextContent();
                    Place p = page.getPlaces().get(p_id);
                    a.setPlaceEnd(p);
                    break;
                case "annot":
                    NodeList annot_nodes = childs.item(i).getChildNodes();
                    for (int j = 0; j < annot_nodes.getLength(); j++) {
                        switch (annot_nodes.item(j).getNodeName()) {
                            case "text":
                                String text = annot_nodes.item(j).getTextContent();
                                text = text.replaceAll("\n", " ");
                                a.setText(text.replaceAll("\\s+", " "));
                                break;
                        }
                    }
                    break;
            }
        }

        return a;
    }

    // Return the information for each subpage
    private SubPage parseSubPages(Node node) {

        SubPage subPage = new SubPage();

        subPage.setPageRef(node.getAttributes().getNamedItem("subpage").getTextContent());

        NodeList childs = node.getChildNodes();

        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()) {
                case "subpageinfo":
                    String id = childs.item(i).getAttributes().getNamedItem("id").getTextContent();
                    subPage.setId(id);
                    break;
            }
        }

        return subPage;
    }

    private Port parsePort(Node node) {
        Port p = new Port();

        p.setId(node.getAttributes().getNamedItem("id").getTextContent());
        p.setType(node.getAttributes().getNamedItem("type").getTextContent());

        return p;
    }

    private void updateSubPageInfo(LinkedHashMap<String, Page> pages) {

        for (Entry<String, Page> entry : pages.entrySet()) {
            LinkedHashMap<String, Transition> transitions = entry.getValue().getTransitions();

            for (Entry<String, Transition> trans_entry : transitions.entrySet()) {
                Transition t = trans_entry.getValue();
                if (t.haveSubPage()) {
                    t.getSubPageInfo().setPage(pages.get(t.getSubPageInfo().getPageRef()));
                }
            }
        }
    }

    private void parsePortSock(Node node) {
        ports.add(node.getAttributes().getNamedItem("portsock").getTextContent());
    }

    private void updatePorts(LinkedHashMap<String, Page> pages) {

        for (String str : ports) {
            str = str.replaceAll("[(]", "");
            str = str.replaceAll("[)]", ";");

            String[] split = str.split(";");

            for (String ps : split) {
                String[] portSocks = ps.split(",");
                Place place = getPlaceByID(pages, portSocks[0]);
                Port port = place.getPort();
                port.setPlace(getPlaceByID(pages, portSocks[1]));
                place.setPortInfo(port);
                place.setPort(true);
            }
        }

    }

    private Place getPlaceByID(LinkedHashMap<String, Page> pages, String id) {
        Place p = new Place();

        for (Entry<String, Page> entry : pages.entrySet()) {
            LinkedHashMap<String, Place> places = entry.getValue().getPlaces();

            if (places.containsKey(id)) {
                p = places.get(id);
            }

        }
        return p;
    }
}
