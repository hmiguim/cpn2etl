/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kettel;

import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author hmg
 */
public class KettelConfigsFactory {
    
    public static KettelConfigsFactory newInstance() throws ParserConfigurationException {
        return new KettelConfigsFactory();
    }
    
    public ConfigTransLogTable newTransLogTable() {
        return new ConfigTransLogTable();
    }
    
    public ConfigPerfLogTable newPerfLogTable() {
        return new ConfigPerfLogTable();
    }
    
    public ConfigChannelLogTable newChannelLogTable() {
        return new ConfigChannelLogTable();
    }
    
    public ConfigStepLogTable newStepLogTable() {
        return new ConfigStepLogTable();
    }
    
    public ConfigMetricsLogTable newMetricsLogTable() {
        return new ConfigMetricsLogTable();
    }
    
    public ConfigInfo newInfo() {
        return new ConfigInfo();
    }
    private KettelConfigsFactory() { }
    
}
