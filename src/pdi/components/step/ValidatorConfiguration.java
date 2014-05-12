package pdi.components.step;

import pdi.components.step.TransExecutorConfiguration;
import pdi.components.step.StepBuilder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class ValidatorConfiguration extends StepBuilder {

    @Override
    public void buildStep() {
        try {
            this.step.setElements(this.readConfig("configs/step/validator"));
        } catch (IOException ex) {
            Logger.getLogger(TransExecutorConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
