/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel;

import java.util.ArrayList;


/**
 *
 * @author hmg
 */
public interface ConfigFields {
    
    public void readConfig();
    
    public ArrayList<Field> getFields();
    
    public void overrideConfig();
}
