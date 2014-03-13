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
public class XMLParser {

    private FileInputStream file;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document xmlDocument;
    private Cpn cpn;

    protected final ArrayList<String> ports = new ArrayList<>();

    /**
     * Constructor for the XMLBuilder class
     *
     * @param file {@link File} to be parsed by the {@code XMLParser}
     * @throws FileNotFoundException
     * @throws SAXException This class can contain basic error or warning
     * information from either the XML parser or the application: a parser
     * writer or application writer can subclass it to provide additional
     * functionality. SAX handlers may throw this exception or any exception
     * subclassed from it.
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations.
     * @throws ParserConfigurationException Indicates a serious configuration
     * error.
     */
    public XMLParser(File file) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
        this.cpn = new Cpn();
        this.file = new FileInputStream(file);
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
    }

    /**
     * Creates a new CPN according the information obtain on the parsed file
     *
     * @return A clone of a {@code Cpn} parsed
     * @throws SAXException This class can contain basic error or warning
     * information from either the XML parser or the application: a parser
     * writer or application writer can subclass it to provide additional
     * functionality. SAX handlers may throw this exception or any exception
     * subclassed from it.
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations.
     * @throws XPathExpressionException Represents an error in an XPath
     * expression.
     */
    public Cpn parse() throws SAXException, IOException, XPathExpressionException {
        this.xmlDocument = this.documentBuilder.parse(this.file);
        this.parseCPN();
        this.documentBuilder.reset();
        return cpn.clone();
    }

    /**
     * Private method in charge of parse the distinct elements presents on the
     * XML file
     *
     * @throws XPathExpressionException Represents an error in an XPath
     * expression.
     * @throws SAXException This class can contain basic error or warning
     * information from either the XML parser or the application: a parser
     * writer or application writer can subclass it to provide additional
     * functionality. SAX handlers may throw this exception or any exception
     * subclassed from it.
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations.
     */
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
                        Place p = this.parsePlace(childs.item(j));
                        places.put(p.getId(), p);
                        page.setPlaces(places);
                        break;
                    case "trans":
                        Transition t = this.parseTransition(childs.item(j));
                        transitions.put(t.getId(), t);
                        page.setTransitions(transitions);
                        break;
                    case "arc":
                        Arc a = this.parseArc(childs.item(j), page);
                        arcs.put(a.getId(), a);
                        page.setArcs(arcs);
                }

            }
            pages.put(page.getId(), page);
        }

        this.updatePorts(pages);
        this.updateSubPageInfo(pages);

        cpn.setPages(pages);
    }

    /**
     * Creates a place with the {@code node} information
     *
     * @param node To be parsed
     * @return A new {@code Place} object with the information obtain in the
     * {@code Node}
     */
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

    /**
     * Creates a place with the {@code node} information
     *
     * @param node To be parsed
     * @return A new {@code Transition} object with the information obtain in
     * the {@code Node}
     */
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
                    this.parsePortSock(childs.item(i));
                    break;
            }
        }

        return t;
    }

    /**
     * Creates a place with the {@code node} information
     *
     * @param node To be parsed
     * @param page
     * @return A new {@code Arc} object with the information obtain in the
     * {@code Node}
     * @see Page
     */
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

    /**
     * Creates a subpage with the {@code node} information
     *
     * @param node To be parsed
     * @return A new {@code SubPage} object with the information obtain in the
     * {@code Node}
     */
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

    /**
     * Creates a port with the {@code node} information
     *
     * @param node To be parsed
     * @return A new {@code Port} object with the information obtain in the
     * {@code Node}
     */
    private Port parsePort(Node node) {

        Port p = new Port();

        p.setId(node.getAttributes().getNamedItem("id").getTextContent());
        p.setType(node.getAttributes().getNamedItem("type").getTextContent());

        return p;
    }

    /**
     * Update the {@code SubPage} information according the {@code pages}
     * parameter. This method can only be called after the file be successfully
     * parsed
     *
     * @param pages {@link LinkedHashMap} with the pages parsed
     */
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

    /**
     * Add a portSock to the ports array with the {@code node} information
     *
     * @param node To be parsed
     */
    private void parsePortSock(Node node) {
        ports.add(node.getAttributes().getNamedItem("portsock").getTextContent());
    }

    /**
     * Update the {@code Place} information according the {@code pages}
     * parameter. This method can only be called after the file be successfully
     * parsed
     *
     * @param pages {@link LinkedHashMap} with the pages parsed
     */
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

    /**
     * Auxiliary method to get the place from a set of pages by an identifier
     *
     * @param pages {@link LinkedHashMap} with the pages parsed
     * @param id String identifier
     * @return The Place with the corresponding identifier
     */
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
