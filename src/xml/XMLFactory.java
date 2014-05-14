package xml;

import xml.parser.XMLParser;
import cpn.Cpn;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import xml.builder.XMLBuilder;

/**
 *
 * @author hmg
 */
public class XMLFactory {

    /**
     * Protected constructor
     */
    protected XMLFactory() {
    }

    /**
     * Obtain a new instance of a <code>XMLFactory</code>. This static method
     * creates a new factory instance.
     *
     * @return A new instance of a {@code XMLFactory}
     */
    public static XMLFactory newInstance() {
        return new XMLFactory();
    }

    /**
     * Creates a new instance of a {@code XMLParser} using the currently
     * configured parameters.
     *
     * @param file {@link File} to be parsed by the {@code XMLParser}
     * @return A new instance of a XMLParser for the specific {@code file}
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
     * Creates a new instance of a {@code XMLBuilder} using the currently
     * configured parameters.
     *
     * @param file
     * @param cpnPages
     * @return A new instance of a XMLBuilder
     * @throws ParserConfigurationException Indicates a serious configuration
     * error.
     */
    public XMLBuilder newXMLBuilder(File file, Cpn cpnPages) throws ParserConfigurationException {
        return new XMLBuilder(file,cpnPages);
    }
}
