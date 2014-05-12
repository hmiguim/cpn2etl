package pdi.components.step;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class TableInputConfiguration extends StepBuilder {

    /**
     * Build the Step Configuration with the XML tags for the TableInput Kettle
     * element
     */
    @Override
    public void buildStep() {
        try {
            this.step.setElements(this.readConfig("configs/step/tableInput"));
        } catch (IOException ex) {
            Logger.getLogger(TableInputConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}