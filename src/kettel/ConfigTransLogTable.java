/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kettel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public final class ConfigTransLogTable implements InterfaceConfigFields {

    private ArrayList<Field> fields;

    public ConfigTransLogTable() {
        this.fields = new ArrayList<>();
        readConfig();
    }

    @Override
    public void readConfig() {
        String conf = "configs/translogtable";

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

    @Override
    public ArrayList<Field> getFields() {
        return this.fields;
    }

    @Override
    public void overrideConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
