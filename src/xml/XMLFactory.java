/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author hmg
 */
public class XMLFactory {

    /**
     *
     * @return A new instance of the XMLFactory
     */
    public static XMLFactory newInstance() {
        return new XMLFactory();
    }

    /**
     *
     * @param file {@link File} to be parsed by the {@code XMLParser}
     * @return A new XMLParser object for the specific {@code file}
     * @throws FileNotFoundException Signals that an attempt to open the file
     * denoted by a specified pathname has failed.
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
    public XMLParser newXMLParser(File file) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
        return new XMLParser(file);
    }

    /**
     *
     * @return A new XMLBuilder object
     * @throws ParserConfigurationException Indicates a serious configuration
     * error.
     */
    public XMLBuilder newXMLBuilder() throws ParserConfigurationException {
        return new XMLBuilder();
    }

    /**
     *
     */
    protected XMLFactory() {
    }
}
