package kettel.jobs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import kettel.xml.Field;
import xml.XMLBuilder;

/**
 *
 * @author hmg
 */
public class JobLogConfiguration extends JobLogBuilder {
    
    /**
     * Build the JobLog with the fields read from the configuration files
     */
    @Override
    public void buildJobLog() {
        this.joblog.setLog(this.readConfig());
    }

    /**
     * Private method that reads the configuration files to be used in the {@link XMLBuilder} class
     */
    private ArrayList<Field> readConfig() {
        String conf = "configs/job/joblogtable";

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
