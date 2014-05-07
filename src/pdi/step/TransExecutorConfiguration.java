/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdi.step;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class TransExecutorConfiguration extends StepBuilder {

    /**
     * Build the Step Configuration with the XML tags for the DBLookup Kettle
     * element
     */
    @Override
    public void buildStep() {
        try {
            this.step.setElements(this.readConfig("configs/step/transexecutor"));
        } catch (IOException ex) {
            Logger.getLogger(TransExecutorConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
