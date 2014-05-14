package pdi.components.step;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class SwitchCaseConfiguration extends StepBuilder {

    /**
     * Build the Step Configuration with the XML tags for the DBLookup Kettle
     * element
     */
    @Override
    public void buildStep() {
        try {
            this.step.setElements(this.readConfig("configs/step/switchcase"));
        } catch (IOException ex) {
            Logger.getLogger(DBLookupConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
}
