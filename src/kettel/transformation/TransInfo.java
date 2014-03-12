/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hmg
 */
public class ConfigInfo {
    
    private ArrayList<Element> elements;
    
    public ConfigInfo() {
        elements = new ArrayList<>();
        readConfig();
    }
    
    public ArrayList<Element> getElements() {
        return this.elements;
    }
    
    private void readConfig() {
        
        String conf = "configs/info";

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
    
}
