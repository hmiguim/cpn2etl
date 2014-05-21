package pdi.components.step;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmg
 */
public class ExecSQLConfiguration extends StepBuilder {

    @Override
    public void buildStep() {
         try {
            this.step.setElements(this.readConfig("configs/step/execsql"));
        } catch (IOException ex) {
            Logger.getLogger(ExecSQLConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
