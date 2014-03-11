/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import kettel.Field;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import kettel.ConfigChannelLogTable;
import kettel.ConfigInfo;
import kettel.ConfigMetricsLogTable;
import kettel.ConfigPerfLogTable;
import kettel.ConfigStepLogTable;
import kettel.ConfigTransLogTable;
import kettel.KettelConfigsFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author hmg
 */
public class XMLBuilder {

    private DocumentBuilder documentBuilder;
    private KettelConfigsFactory configFactory;
    private String filename;

    public XMLBuilder() { }

    public XMLBuilder(DocumentBuilder documentBuilder) throws ParserConfigurationException {
        this.documentBuilder = documentBuilder;
        this.configFactory = KettelConfigsFactory.newInstance();
    }

    // Public Method to invoke the builder
    public void construct(File f) throws TransformerException {
        Document doc = documentBuilder.newDocument();    

        this.filename = f.getName();
        
        doc = initialize(doc);
        
        finalize(doc,f);
    }
    
    private Document initialize(Document doc) {
        
        // Root Element
        Element transformation = doc.createElement("transformation");
        doc.appendChild(transformation);
        
        transformation.appendChild(createInfo(doc));
        
        Element notepads = doc.createElement("notepads");
        transformation.appendChild(notepads);
        
        Element step_error_handling = doc.createElement("step_error_handling");
        transformation.appendChild(step_error_handling);
        
        Element slave_step_copy_partition_distribution = doc.createElement("slave-step-copy-partition-distribution");
        transformation.appendChild(slave_step_copy_partition_distribution);
        
        Element slave_transformation = doc.createElement("slave_transformation");
        slave_transformation.setTextContent("N");
        transformation.appendChild(slave_transformation);
        
        return doc;
    }
    
    private Element createInfo(Document doc) {
        // Info Element
        Element info = doc.createElement("info");
        
        // Name Element
        Element name = doc.createElement("name");
        int j = filename.lastIndexOf("/");
        name.setTextContent(filename.substring(j+1).replaceAll(".xml", ""));
        info.appendChild(name);
        
        // Description Element
        Element description = doc.createElement("description");
        info.appendChild(description);

        // Extended Description Element
        Element extended_description = doc.createElement("extended_description");
        info.appendChild(extended_description);

        // Trans Version Element
        Element trans_version = doc.createElement("trans_version");
        info.appendChild(trans_version);

        // Trans Type Element
        Element trans_type = doc.createElement("trans_type");
        trans_type.setTextContent("Normal");
        info.appendChild(trans_type);

        // Directory Element
        Element directory = doc.createElement("directory");
        directory.setTextContent("&#x2f;");
        info.appendChild(directory);

        // Parameters Element
        Element parameters = doc.createElement("parameters");
        info.appendChild(parameters);

        info.appendChild(createLog(doc));
        
        info.appendChild(createMaxDate(doc));
        
        ConfigInfo ci = configFactory.info();
        
        for(kettel.Element e : ci.getElements()) {
            Element a = doc.createElement(e.getTag());
            a.setTextContent(e.getContextText());
            
            info.appendChild(a);
        }
        
        Element shared_objects_file = doc.createElement("shared_objects_file");
        info.appendChild(shared_objects_file);
        
        Element dependencies = doc.createElement("dependencies");
        info.appendChild(dependencies); 
        
        Element partitionschemas = doc.createElement("partitionschemas");
        info.appendChild(partitionschemas);
        
        Element slaveservers = doc.createElement("slaveservers");
        info.appendChild(slaveservers);
        
        Element clusterschemas = doc.createElement("clusterschemas");
        info.appendChild(clusterschemas);
        
        Element created_user = doc.createElement("created_user");
        created_user.setTextContent("cpn2etl");
        info.appendChild(created_user);
        
        Element created_date = doc.createElement("created_date");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        String today = sdf.format(date);
        created_date.setTextContent(today);
        info.appendChild(created_date);
        
        Element modified_user = doc.createElement("modified_user");
        modified_user.setTextContent("cpn2etl");
        info.appendChild(modified_user);
        
        Element modified_date = doc.createElement("modified_date");
        modified_date.setTextContent(today);
        info.appendChild(modified_date);
        
        return info;
    }
    
    private Element createMaxDate(Document doc) {
        
        // Root Element
        Element maxdate = doc.createElement("maxdate");
        
        // Connection element
        Element connection = doc.createElement("connection");
        maxdate.appendChild(connection);
        
        // Table element
        Element table = doc.createElement("table");
        maxdate.appendChild(table);
        
        // Field element
        Element field = doc.createElement("field");
        maxdate.appendChild(field);
        
        // Offset element
        Element offset = doc.createElement("offset");
        offset.setTextContent("0.0");
        maxdate.appendChild(offset);
        
        // Maxdiff element
        Element maxdiff = doc.createElement("maxdiff");
        maxdiff.setTextContent("0.0");
        maxdate.appendChild(maxdiff);
        
        return maxdate;
    }
    
    private Element createLog(Document doc) {

        // Root element
        Element log = doc.createElement("log");
        
        log.appendChild(createTransLogTable(doc));
        
        log.appendChild(createPerfLogTable(doc));
        
        log.appendChild(createChannelLogTable(doc));
        
        log.appendChild(createStepLogTable(doc));
        
        log.appendChild(createMetricsLogTable(doc));
        
        return log;
    }
    
    private Element createMetricsLogTable(Document doc) {
        
        // Root element
        Element metrics_log_table = doc.createElement("metrics-log-table");
     
        Element connection = doc.createElement("connection");
        metrics_log_table.appendChild(connection);
        
        // schema element
        Element schema = doc.createElement("schema");
        metrics_log_table.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        metrics_log_table.appendChild(table);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        metrics_log_table.appendChild(timeout_days);
        
        ConfigMetricsLogTable config = this.configFactory.metricsLogTable();
        
        for (Field f : config.getFields()) {
            metrics_log_table.appendChild(createField(f, doc));
        }
        
        return metrics_log_table;
    }
    
    private Element createStepLogTable(Document doc) {
        
        // Root element
        Element step_log_table = doc.createElement("step-log-table");
     
        Element connection = doc.createElement("connection");
        step_log_table.appendChild(connection);
        
        // schema element
        Element schema = doc.createElement("schema");
        step_log_table.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        step_log_table.appendChild(table);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        step_log_table.appendChild(timeout_days);
        
        ConfigStepLogTable config = configFactory.stepLogTable();
        
        for (Field f : config.getFields()) {
            step_log_table.appendChild(createField(f, doc));
        }
        
        return step_log_table;
    }
    
    private Element createChannelLogTable(Document doc) {
        // Root element
        Element channel_log_table = doc.createElement("channel-log-table");
     
        Element connection = doc.createElement("connection");
        channel_log_table.appendChild(connection);
        
        // schema element
        Element schema = doc.createElement("schema");
        channel_log_table.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        channel_log_table.appendChild(table);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        channel_log_table.appendChild(timeout_days);
        
        ConfigChannelLogTable config = configFactory.channelLogTable();
        
        for (Field f : config.getFields()) {
            channel_log_table.appendChild(createField(f, doc));
        }
        
        return channel_log_table;
    }
    
    private Element createPerfLogTable(Document doc) {
        
        // Root element
        Element perf_log_table = doc.createElement("perf-log-table");
        
        Element connection = doc.createElement("connection");
        perf_log_table.appendChild(connection);
        
        // schema element
        Element schema = doc.createElement("schema");
        perf_log_table.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        perf_log_table.appendChild(table);

        // interval element
        Element interval = doc.createElement("interval");
        perf_log_table.appendChild(interval);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        perf_log_table.appendChild(timeout_days);
        
        ConfigPerfLogTable confs = configFactory.perfLogTable();
        
        for (Field f : confs.getFields()) {
            perf_log_table.appendChild(createField(f, doc));
        }
        
        return perf_log_table;

    }
    
    private Element createTransLogTable(Document doc) {
        
        // Root element
        Element trans_log_table = doc.createElement("trans-log-table");
        
        // connection element
        Element connection = doc.createElement("connection");
        trans_log_table.appendChild(connection);

        // schema element
        Element schema = doc.createElement("schema");
        trans_log_table.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        trans_log_table.appendChild(table);

        // size_limit_lines element
        Element size_limit_lines = doc.createElement("size_limit_lines");
        trans_log_table.appendChild(size_limit_lines);

        // interval element
        Element interval = doc.createElement("interval");
        trans_log_table.appendChild(interval);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        trans_log_table.appendChild(timeout_days);
        
        ConfigTransLogTable confs = configFactory.transLogTable();
        
        for (Field f : confs.getFields()) {
            trans_log_table.appendChild(createField(f, doc));
        }
        
        return trans_log_table;
    }
    
    private Element createField(Field f,Document doc) {
        
        Element field = doc.createElement("field");
        Element id = doc.createElement("id");
        id.setTextContent(f.getId());
        field.appendChild(id);
        Element enable = doc.createElement("enabled");
        enable.setTextContent(f.getEnable());
        field.appendChild(enable);
        Element name = doc.createElement("name");
        name.setTextContent(f.getName());
        field.appendChild(name);
        
        return field;
    }
    
    private void finalize(Document doc,File f) throws TransformerException {
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(f);
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, result);
        
    } 
}