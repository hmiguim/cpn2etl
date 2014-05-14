package xml.builder;

import xml.XMLHelper;
import cpn.Cpn;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;

/**
 *
 * @author hmg
 */
public class XMLBuilder {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final DocumentBuilder documentBuilder;
    private final TransformationBuilder transformationBuilder;
    private final JobBuilder jobBuilder;
    private final File file;

    /**
     *
     * @param file
     * @param pages
     * @throws ParserConfigurationException
     */
    public XMLBuilder(File file, Cpn pages) throws ParserConfigurationException {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        this.transformationBuilder = new TransformationBuilder(this.documentBuilder, file.getParent());
        this.jobBuilder = new JobBuilder(this.documentBuilder, this.transformationBuilder, file.getParent(), file.getName(), pages);
        this.file = file;
    }

    /**
     * Public method to create the Kettle document going to be saved
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    public void construct() throws TransformerException, FileNotFoundException, UnsupportedEncodingException {

        Document job = this.jobBuilder.createJob();

        XMLHelper.finalize(job, this.file);
    }

}
