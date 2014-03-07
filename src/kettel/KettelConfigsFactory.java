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

    public ConfigTransLogTable transLogTable() {
        return new ConfigTransLogTable();
    }

    public ConfigPerfLogTable perfLogTable() {
        return new ConfigPerfLogTable();
    }

    public ConfigChannelLogTable channelLogTable() {
        return new ConfigChannelLogTable();
    }

    public ConfigStepLogTable stepLogTable() {
        return new ConfigStepLogTable();
    }

    public ConfigMetricsLogTable metricsLogTable() {
        return new ConfigMetricsLogTable();
    }

    public ConfigInfo info() {
        return new ConfigInfo();
    }
}
