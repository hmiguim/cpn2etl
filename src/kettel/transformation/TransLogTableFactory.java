/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kettel.transformation;

import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author hmg
 */
public class TransLogTableFactory {

    public static TransLogTableFactory newInstance() throws ParserConfigurationException {
        return new TransLogTableFactory();
    }

    public TransLogTable transLogTable() {
        return new TransLogTable();
    }

    public TransPerfLogTable perfLogTable() {
        return new TransPerfLogTable();
    }

    public TransChannelLogTable channelTransLogTable() {
        return new TransChannelLogTable();
    }

    public TransStepLogTable stepLogTable() {
        return new TransStepLogTable();
    }

    public TransMetricsLogTable metricsLogTable() {
        return new TransMetricsLogTable();
    }

    public TransInfo info() {
        return new TransInfo();
    }
}
