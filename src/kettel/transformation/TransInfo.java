package kettel.transformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import kettel.Element;
import kettel.InterfaceConfiguration;
import xml.XMLBuilder;

/**
 *
 * @author hmg
 */
public final class TransInfo implements InterfaceConfiguration<Element> {

    private final ArrayList<Element> elements;

    /**
     *
     * Constructor for the TransInfo class
     *
     */
    public TransInfo() {
        elements = new ArrayList<>();
        readConfig();
    }

    /**
     * Obtain an instance of an {@link java.util.ArrayList} object of {
     *
     * @url Element}
     *
     * @return An new instance of an {@code java.util.ArrayList} of {
     * @url Element}
     */
    @Override
    public ArrayList<Element> getT() {
        return this.elements;
    }

    /**
     * Read the configuration files to be used in the {@link XMLBuilder} class
     */
    @Override
    public void readConfig() {

        String conf = "configs/transformation/info";

        BufferedReader bufferedReader = null;
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(conf));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                Element e = new Element(split[0], split[1]);

                this.elements.add(e);
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {

                }
            }
        }
    }

    /**
     * @deprecated Not supported yet.
     */
    @Override
    public void overrideConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
