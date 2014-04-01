package kettel.transformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import kettel.xml.Element;

/**
 *
 * @author hmg
 */
public class TransInfoConfiguration extends TransInfoBuilder {

    /**
     * Build the TransLog with the fields read from the configuration files
     */
    @Override
    public void buildTransInfo() {
        this.transInfo.setElement(this.readConfig());
    }

    /**
     * Private method that reads the configuration files
     */
    private ArrayList<Element> readConfig() {
        String conf = "configs/transformation/info";

        BufferedReader bufferedReader = null;
        String line;

        ArrayList<Element> elements = new ArrayList<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(conf));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                Element e = new Element(split[0], split[1]);

                elements.add(e);
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
        return elements;
    }
}
