package pdi.components.job;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import pdi.components.xml.Field;

/**
 *
 * @author hmg
 */
public class JobChannelLogConfiguration extends JobLogBuilder {

    /**
     * Build the JobLog with the fields read from the configuration files
     */
    @Override
    public void buildJobLog() {
        this.joblog.setLog(this.readConfig());
    }

    /**
     * Private method that reads the configuration
     */
    private ArrayList<Field> readConfig() {
        String conf = "configs/job/channellogtable";

        BufferedReader bufferedReader = null;
        String line;

        ArrayList<Field> fields = new ArrayList<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(conf));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                Field f = new Field(split[0], split[1], split[2]);

                fields.add(f);
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

        return fields;
    }
}
