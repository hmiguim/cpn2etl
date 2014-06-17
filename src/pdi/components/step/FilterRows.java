package pdi.components.step;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class FilterRows extends StepBuilder  {

    @Override
    public void buildStep() {
        try {
            this.step.setElements(this.readConfig("configs/step/filterrows"));
        } catch (IOException ex) {
            Logger.getLogger(FilterRows.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
