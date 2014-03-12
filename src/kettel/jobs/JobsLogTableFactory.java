/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kettel.jobs;

/**
 *
 * @author hmg
 */
public class JobsLogTableFactory {

    public static JobsLogTableFactory newInstance() {
        return new JobsLogTableFactory();
    }

    public JobLogTable logTable() {
        return new JobLogTable();
    }
    
    public JobEntryLogTable entryLogTable() {
        return new JobEntryLogTable();
    }

    public JobChannelLogTable channelLogTable() {
        return new JobChannelLogTable();
    }
}
