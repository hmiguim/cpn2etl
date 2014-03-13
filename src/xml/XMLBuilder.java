package xml;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import kettel.Field;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import kettel.jobs.JobChannelLogTable;
import kettel.jobs.JobEntryLogTable;
import kettel.jobs.JobLogTable;
import kettel.jobs.JobsLogTableFactory;
import kettel.transformation.TransChannelLogTable;
import kettel.transformation.TransInfo;
import kettel.transformation.TransMetricsLogTable;
import kettel.transformation.TransPerfLogTable;
import kettel.transformation.TransStepLogTable;
import kettel.transformation.TransLogTable;
import kettel.transformation.TransLogTableFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author hmg
 */
public class XMLBuilder {

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private TransLogTableFactory transLogFactory;
    private JobsLogTableFactory jobLogFactory;
    private String filename;

    /**
     * Constructor for the XMLBuilder class
     *
     * @throws ParserConfigurationException Indicates a serious configuration
     * error.
     */
    public XMLBuilder() throws ParserConfigurationException {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        this.transLogFactory = TransLogTableFactory.newInstance();
        this.jobLogFactory = JobsLogTableFactory.newInstance();
    }

    /**
     * Public method to create the Kettle XML file
     *
     * @param file The file where the XML document going to be saved
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     */
    public void construct(File file) throws TransformerException {
        this.filename = file.getName();

        Document job = this.createJob();

        this.finalize(job, file);
    }

    /**
     * Method that returns a {@link Document} for the Job element in Kettle
     * specification file
     *
     * @return A {@link Document} with the Job {@link Element} as root with all
     * the child nodes
     */
    private Document createJob() {

        Document document = documentBuilder.newDocument();

        // Root Element
        Element job = document.createElement("job");
        document.appendChild(job);

        // name element
        Element name = document.createElement("name");
        name.setTextContent(this.filename);
        job.appendChild(name);

        // description element
        Element description = document.createElement("description");
        job.appendChild(description);

        // extended_description element
        Element extended_description = document.createElement("extended_description");
        job.appendChild(extended_description);

        // job_version element
        Element job_version = document.createElement("job_version");
        job_version.setTextContent("1.0");
        job.appendChild(job_version);

        // directory element
        Element directory = document.createElement("directory");
        directory.setTextContent("/");
        job.appendChild(directory);

        // created_user element
        Element created_user = document.createElement("created_user");
        created_user.setTextContent("cpn2etl");
        job.appendChild(created_user);

        // created_date elememt
        Element created_date = document.createElement("created_date");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        String today = sdf.format(date);
        created_date.setTextContent(today);
        job.appendChild(created_date);

        // modified_user element
        Element modified_user = document.createElement("modified_user");
        modified_user.setTextContent("cpn2etl");
        job.appendChild(modified_user);

        // modified_date element
        Element modified_date = document.createElement("modified_date");
        modified_date.setTextContent(today);
        job.appendChild(modified_date);

        // parameters element
        Element parameters = document.createElement("parameters");
        job.appendChild(parameters);

        // slaveservers element
        Element slaveservers = document.createElement("slaveservers");
        job.appendChild(slaveservers);

        // job-log-table element
        job.appendChild(this.createJobLogTable());

        // jobentry-log-table element
        job.appendChild(this.createJobEntryLogTable());

        // channel-log-table
        job.appendChild(this.createJobChannelLogTabble());

        // pass_batchid element
        Element pass_batchid = document.createElement("pass_batchid");
        pass_batchid.setTextContent("N");
        job.appendChild(pass_batchid);

        // shared_objects_file element
        Element shared_objects_file = document.createElement("shared_objects_file");
        job.appendChild(shared_objects_file);

        // entries elements
        job.appendChild(this.createJobEntries());

        // hops elements
        job.appendChild(this.createJobHops());

        // notepads elements
        Element notepads = document.createElement("notepads");
        job.appendChild(notepads);

        return document;
    }

    /**
     * Method in charge of translate each transformation in one job entry and
     * append it in the element <code>entries</code>
     *
     * @return A {@link Element} <code>entries</code> with all the child nodes
     */
    private Element createJobEntries() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element entries = doc.createElement("entries");

        // TODO: create each one of the job entries
        return entries;
    }

    /**
     * Method in charge of connect each transformation and append it in the
     * element <code>hops</code>
     *
     * @return A {@link Element} <code>hops</code> with all the child nodes
     */
    private Element createJobHops() {

        Document doc = this.documentBuilder.newDocument();

        // Root Element
        Element hops = doc.createElement("hops");

        // TODO: create hops between two job entries
        return hops;
    }

    /**
     * Method in charge of create a <code>job-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>job-log-table</code> with all the child
     * nodes
     */
    private Element createJobLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element jobLogTable = doc.createElement("job-log-table");

        // connection element
        Element connection = doc.createElement("connection");
        jobLogTable.appendChild(connection);

        // schema element
        Element schema = doc.createElement("schema");
        jobLogTable.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        jobLogTable.appendChild(table);

        // size_limit_lines element
        Element sizeLimitLines = doc.createElement("size_limit_lines");
        jobLogTable.appendChild(sizeLimitLines);

        // interval element
        Element interval = doc.createElement("interval");
        jobLogTable.appendChild(interval);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        jobLogTable.appendChild(timeout_days);

        JobLogTable logTable = this.jobLogFactory.logTable();

        for (Field f : logTable.getT()) {
            jobLogTable.appendChild(this.createField(f));
        }

        return jobLogTable;

    }

    /**
     * Method in charge of create a <code>jobentry-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>jobentry-log-table</code> with all the
     * child nodes
     */
    private Element createJobEntryLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element jobentryLogTable = doc.createElement("jobentry-log-table");

        // connection element
        Element connection = doc.createElement("connection");
        jobentryLogTable.appendChild(connection);

        // schema element
        Element schema = doc.createElement("schema");
        jobentryLogTable.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        jobentryLogTable.appendChild(table);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        jobentryLogTable.appendChild(timeout_days);

        JobEntryLogTable entryLogTable = this.jobLogFactory.entryLogTable();

        for (Field f : entryLogTable.getT()) {
            jobentryLogTable.appendChild(this.createField(f));
        }

        return jobentryLogTable;
    }

    /**
     * Method in charge of create a <code>channel-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>channel-log-table</code> with all the
     * child nodes
     */
    private Element createJobChannelLogTabble() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element channelLogTable = doc.createElement("channel-log-table");

        // connection element
        Element connection = doc.createElement("connection");
        channelLogTable.appendChild(connection);

        // schema element
        Element schema = doc.createElement("schema");
        channelLogTable.appendChild(schema);

        // table element
        Element table = doc.createElement("table");
        channelLogTable.appendChild(table);

        // timeout_days element
        Element timeout_days = doc.createElement("timeout_days");
        channelLogTable.appendChild(timeout_days);

        JobChannelLogTable channelLogTable1 = this.jobLogFactory.channelLogTable();

        for (Field f : channelLogTable1.getT()) {
            channelLogTable.appendChild(this.createField(f));
        }

        return channelLogTable;
    }

    /**
     * Method in charge of create a <code>transformation</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>transformation</code> with all the child
     * nodes
     */
    private Document createTransformation() {

        Document doc = this.documentBuilder.newDocument();

        // Root Element
        Element transformation = doc.createElement("transformation");
        doc.appendChild(transformation);

        transformation.appendChild(this.createInfo());

        // notepads element
        Element notepads = doc.createElement("notepads");
        transformation.appendChild(notepads);

        // step_error_handling element
        Element step_error_handling = doc.createElement("step_error_handling");
        transformation.appendChild(step_error_handling);

        // slave-step-copy-partition-distribution element
        Element slave_step_copy_partition_distribution = doc.createElement("slave-step-copy-partition-distribution");
        transformation.appendChild(slave_step_copy_partition_distribution);

        // slave_transformation element
        Element slave_transformation = doc.createElement("slave_transformation");
        slave_transformation.setTextContent("N");
        transformation.appendChild(slave_transformation);

        return doc;
    }

    /**
     * Method in charge of create a <code>info</code> element and return it to
     * be appended
     *
     * @return A {@link Element} <code>info</code> with all the child nodes
     */
    private Element createInfo() {

        Document doc = this.documentBuilder.newDocument();

        // Info Element
        Element info = doc.createElement("info");

        // Name Element
        Element name = doc.createElement("name");
        int j = filename.lastIndexOf("/");
        name.setTextContent(filename.substring(j + 1).replaceAll(".xml", ""));
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

        info.appendChild(this.createTransformationLog());

        info.appendChild(this.createMaxDate());

        TransInfo ci = this.transLogFactory.info();

        for (kettel.Element e : ci.getT()) {
            Element a = doc.createElement(e.getTag());
            a.setTextContent(e.getContextText());

            info.appendChild(a);
        }

        // shared_objects_file element
        Element shared_objects_file = doc.createElement("shared_objects_file");
        info.appendChild(shared_objects_file);

        // dependencies element
        Element dependencies = doc.createElement("dependencies");
        info.appendChild(dependencies);

        // partitionschemas element
        Element partitionschemas = doc.createElement("partitionschemas");
        info.appendChild(partitionschemas);

        // slaveservers element
        Element slaveservers = doc.createElement("slaveservers");
        info.appendChild(slaveservers);

        // clusterschemas element
        Element clusterschemas = doc.createElement("clusterschemas");
        info.appendChild(clusterschemas);

        // created_user element
        Element created_user = doc.createElement("created_user");
        created_user.setTextContent("cpn2etl");
        info.appendChild(created_user);

        // created_date element
        Element created_date = doc.createElement("created_date");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
        String today = sdf.format(date);
        created_date.setTextContent(today);
        info.appendChild(created_date);

        // modified_user element
        Element modified_user = doc.createElement("modified_user");
        modified_user.setTextContent("cpn2etl");
        info.appendChild(modified_user);

        // modified_date element
        Element modified_date = doc.createElement("modified_date");
        modified_date.setTextContent(today);
        info.appendChild(modified_date);

        return info;
    }

    /**
     * Method in charge of create a <code>maxdate</code> element and return it
     * to be appended
     *
     * @return A {@link Element} <code>maxdate</code> with all the child nodes
     */
    private Element createMaxDate() {

        Document doc = this.documentBuilder.newDocument();

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

    /**
     * Method in charge of create all the different child log elements
     *
     * @return A {@link Element} with all the child nodes:
     * <p>
     * - <code>trans-log-table</code></p>
     * <p>
     * - <code>perf-log-table</code></p>
     * <p>
     * - <code>perf-log-table</code></p>
     * <p>
     * - <code>channel-log-table</code></p>
     * <p>
     * - <code>step-log-table</code></p>
     * <p>
     * - <code>metrics-log-table</code></p>
     */
    private Element createTransformationLog() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element log = doc.createElement("log");

        // trans-log-table element
        log.appendChild(this.createTransformationTransLogTable());

        // perf-log-table element
        log.appendChild(this.createTransformationPerfLogTable());

        // channel-log-table element
        log.appendChild(this.createTransformationChannelLogTable());

        // step-log-table element
        log.appendChild(this.createTransformationStepLogTable());

        // metrics-log-table element
        log.appendChild(this.createTransformationMetricsLogTable());

        return log;
    }

    /**
     * Method in charge of create a <code>metrics-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>metrics-log-table</code> with all the
     * child nodes
     */
    private Element createTransformationMetricsLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element metrics_log_table = doc.createElement("metrics-log-table");

        // connection element
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

        TransMetricsLogTable config = this.transLogFactory.metricsLogTable();

        for (Field f : config.getT()) {
            metrics_log_table.appendChild(this.createField(f));
        }

        return metrics_log_table;
    }

    /**
     * Method in charge of create a <code>step-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>step-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationStepLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element step_log_table = doc.createElement("step-log-table");

        // connection element
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

        TransStepLogTable config = this.transLogFactory.stepLogTable();

        for (Field f : config.getT()) {
            step_log_table.appendChild(this.createField(f));
        }

        return step_log_table;
    }

    /**
     * Method in charge of create a <code>channel-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>channel-log-table</code> with all the
     * child nodes
     */
    private Element createTransformationChannelLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element channel_log_table = doc.createElement("channel-log-table");

        // connection element
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

        TransChannelLogTable config = this.transLogFactory.channelTransLogTable();

        for (Field f : config.getT()) {
            channel_log_table.appendChild(this.createField(f));
        }

        return channel_log_table;
    }

    /**
     * Method in charge of create a <code>perf-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>perf-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationPerfLogTable() {

        Document doc = this.documentBuilder.newDocument();

        // Root element
        Element perf_log_table = doc.createElement("perf-log-table");

        // connection element
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

        TransPerfLogTable confs = this.transLogFactory.perfLogTable();

        for (Field f : confs.getT()) {
            perf_log_table.appendChild(this.createField(f));
        }

        return perf_log_table;

    }

    /**
     * Method in charge of create a <code>trans-log-table</code> element and
     * return it to be appended
     *
     * @return A {@link Element} <code>trans-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationTransLogTable() {

        Document doc = this.documentBuilder.newDocument();

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

        TransLogTable confs = this.transLogFactory.transLogTable();

        for (Field f : confs.getT()) {
            trans_log_table.appendChild(this.createField(f));
        }

        return trans_log_table;
    }

    /**
     * Method that returns a field log element according the field object
     *
     * @param nameField Object field with the information obtain on the
     * configuration files
     * @return A {@link Element} {@code field} with all the child nodes
     */
    private Element createField(Field nameField) {

        Document doc = this.documentBuilder.newDocument();

        Element field = doc.createElement("field");
        Element id = doc.createElement("id");
        id.setTextContent(nameField.getId());
        field.appendChild(id);
        Element enable = doc.createElement("enabled");
        enable.setTextContent(nameField.getEnable());
        field.appendChild(enable);
        Element name = doc.createElement("name");
        name.setTextContent(nameField.getName());
        field.appendChild(name);

        return field;
    }

    /**
     * Method that write the {@code document} parameter in the {@code file} to
     * be exported as a XML file
     *
     * @param document The XML {@link Document} to be exported
     * @param file The destination file where the {@code document} going to be
     * exported
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     */
    private void finalize(Document document, File file) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, result);

    }
}
