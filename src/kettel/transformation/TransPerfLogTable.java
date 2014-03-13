/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kettel.transformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import kettel.Field;
import kettel.InterfaceLogs;
import xml.XMLBuilder;

/**
 *
 * @author hmg
 */
public final class TransPerfLogTable implements InterfaceLogs {

    private ArrayList<Field> fields;

    /**
     *
     * Constructor for the TransPerfLogTable class. Note that implements the
     * {@link InterfaceLogs} interface
     *
     */
    public TransPerfLogTable() {
        this.fields = new ArrayList<>();
        readConfig();
    }

    /**
     * Read the configuration files to be used in the {@link XMLBuilder} class
     */
    @Override
    public void readConfig() {
        String conf = "configs/transformation/perflogtable";

        BufferedReader bufferedReader = null;
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(conf));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                Field f = new Field(split[0], split[1], split[2]);

                this.fields.add(f);
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
     * Obtain an instance of an {@link java.util.ArrayList} object of {
     *
     * @url Field}
     *
     * @return An new instance of an {@code java.util.ArrayList} of {
     * @url Field}
     */
    @Override
    public ArrayList<Field> getT() {
        return this.fields;
    }

    /**
     * @deprecated Not supported yet
     */
    @Override
    public void overrideConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
