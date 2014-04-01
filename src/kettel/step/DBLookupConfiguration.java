package kettel.step;

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
public class DBLookupConfiguration extends StepBuilder {

    /**
     * Build the Step Configuration with the XML tags for the DBLookup Kettle
     * element
     */
    @Override
    public void buildStep() {
        this.step.setElements(this.readConfig());
    }

    /**
     * Private method that reads the configuration files
     */
    private ArrayList<Element> readConfig() {
        String conf = "configs/step/dblookup";

        BufferedReader bufferedReader = null;
        String line;

        ArrayList<Element> elements = new ArrayList<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(conf));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                if (split[1].equals("empty")) {
                    split[1] = "";
                }

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
