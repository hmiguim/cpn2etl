package xml;

import cpn.Cpn;
import cpn.Transition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import kettel.conversion.ConversionDirector;
import kettel.conversion.ConversionFactory;
import kettel.jobs.JobChannelLogConfiguration;
import kettel.jobs.JobEntryLogConfiguration;
import kettel.jobs.JobLog;
import kettel.jobs.JobLogConfiguration;
import kettel.jobs.JobLogDirector;
import kettel.jobs.JobsLogFactory;
import kettel.mapping.Mapping;
import kettel.mapping.MappingComponent;
import kettel.mapping.MappingOrder;
import kettel.step.Step;
import kettel.step.StepDirector;
import kettel.step.StepFactory;
import kettel.transformation.TransChannelLogConfiguration;
import kettel.transformation.TransInfo;
import kettel.transformation.TransInfoDirector;
import kettel.transformation.TransLog;
import kettel.transformation.TransLogConfiguration;
import kettel.transformation.TransLogDirector;
import kettel.transformation.TransMetricsLogConfiguration;
import kettel.transformation.TransPerfLogConfiguration;
import kettel.transformation.TransStepLogConfiguration;
import kettel.transformation.TransformationFactory;
import kettel.xml.Field;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Utilities;

/**
 *
 * @author hmg
 */
public class XMLBuilder {

    private final DocumentBuilderFactory documentBuilderFactory;
    private final DocumentBuilder documentBuilder;
    private final TransformationFactory transLogFactory;
    private final JobsLogFactory jobLogFactory;
    private final ConversionFactory conversionFactory;
    private final ConversionDirector conversionDirector;
    private final StepFactory stepFactory;
    private final JobLogDirector jobLogDirector;
    private final TransLogDirector transLogDirector;
    private final TransInfoDirector transInfoDirector;
    private String filename;
    private String path;
    private Cpn cpnPages;

    /**
     * Constructor for the XMLBuilder class
     *
     * @throws ParserConfigurationException Indicates a serious configuration
     * error.
     */
    public XMLBuilder() throws ParserConfigurationException {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = this.documentBuilderFactory.newDocumentBuilder();
        this.transLogFactory = TransformationFactory.newInstance();
        this.jobLogFactory = JobsLogFactory.newInstance();
        this.conversionFactory = ConversionFactory.newInstance();
        this.conversionDirector = conversionFactory.newConversionDirector();
        this.stepFactory = StepFactory.newInstance();
        this.jobLogDirector = jobLogFactory.newJobLogDirector();
        this.transLogDirector = transLogFactory.newTransLogDirector();
        this.transInfoDirector = this.transLogFactory.newTransInfoDirector();
    }

    /**
     * Public method to create the Kettle XML file
     *
     * @param file The file where the XML document going to be saved
     * @param pages The CPN information parsed
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     * @throws java.io.IOException Signals that an I/O exception of some sort
     * has occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations.
     */
    public void construct(File file, Cpn pages) throws TransformerException, IOException {

        this.filename = file.getName();
        this.path = file.getParent();

        this.cpnPages = pages.clone();

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
    private Document createJob() throws TransformerException {

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
        created_user.setTextContent(Utilities.getUser());
        job.appendChild(created_user);

        // created_date elememt
        Element created_date = document.createElement("created_date");
        created_date.setTextContent(Utilities.today());
        job.appendChild(created_date);

        // modified_user element
        Element modified_user = document.createElement("modified_user");
        modified_user.setTextContent(Utilities.getUser());
        job.appendChild(modified_user);

        // modified_date element
        Element modified_date = document.createElement("modified_date");
        modified_date.setTextContent(Utilities.today());
        job.appendChild(modified_date);

        // parameters element
        Element parameters = document.createElement("parameters");
        job.appendChild(parameters);

        // slaveservers element
        Element slaveservers = document.createElement("slaveservers");
        job.appendChild(slaveservers);

        // job-log-table element
        job.appendChild(this.createJobLogTable(document));

        // jobentry-log-table element
        job.appendChild(this.createJobEntryLogTable(document));

        // channel-log-table
        job.appendChild(this.createJobChannelLogTabble(document));

        // pass_batchid element
        Element pass_batchid = document.createElement("pass_batchid");
        pass_batchid.setTextContent("N");
        job.appendChild(pass_batchid);

        // shared_objects_file element
        Element shared_objects_file = document.createElement("shared_objects_file");
        job.appendChild(shared_objects_file);

        // entries elements
        job.appendChild(this.createJobEntries(document));

        // hops elements
        job.appendChild(this.createJobHops(document));

        // notepads elements
        Element notepads = document.createElement("notepads");
        job.appendChild(notepads);

        return document;
    }

    /**
     * Method in charge of translate each transformation in one job entry and
     * append it in the element <code>entries</code>
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>entries</code> with all the child nodes
     */
    private Element createJobEntries(Document doc) throws TransformerException {

        // Root element
        Element entries = doc.createElement("entries");

        Collection<Transition> modules = this.cpnPages.getPatternsMainPage();

        File f;
        Document transformation;

        for (Transition t : modules) {

            // by each one of the transition create a transformation for that
            switch (t.getSubPageInfo().getPage().getName()) {
                case "SKP":
                    entries.appendChild(this.createJobEntry(doc, t));
                    transformation = this.createTransformation(t);
                    f = new File(this.path + "/" + t.getSubPageInfo().getPage().getName() + ".ktr");
                    this.finalize(transformation, f);
                    break;
                case "SCD/H 1":
                    entries.appendChild(this.createJobEntry(doc, t));
                //    f = new File(this.path + "/" + t.getSubPageInfo().getPage().getName() + ".ktr");

                    //this.finalize(transformation,f);
                    break;
            }
        }

        return entries;
    }

    /**
     * Method in charge of translate each transformation in one job entry and
     * append it in the element <code>entry</code>
     *
     * @param doc So that can be created elements to be added to the parent node
     * @param trans
     * @return A {@link Element} <code>entry</code> with all the child nodes
     */
    private Element createJobEntry(Document doc, Transition trans) {

        // entry element
        Element entry = doc.createElement("entry");

        // name element 
        Element name = doc.createElement("name");
        name.setTextContent(trans.getSubPageInfo().getPage().getName());
        entry.appendChild(name);

        // description element
        Element description = doc.createElement("description");
        entry.appendChild(description);

        // type element
        Element type = doc.createElement("type");
        type.setTextContent("TRANS");
        entry.appendChild(type);

        // specification_method element
        Element specification_method = doc.createElement("specification_method");
        specification_method.setTextContent("filename");
        entry.appendChild(specification_method);

        // trans_object_id element
        Element trans_object_id = doc.createElement("trans_object_id");
        entry.appendChild(trans_object_id);

        // filename element
        Element filename_element = doc.createElement("filename");
        filename_element.setTextContent(this.path + "/" + trans.getSubPageInfo().getPage().getName() + ".ktr");
        entry.appendChild(filename_element);

        // transname element
        Element transname = doc.createElement("transname");
        entry.appendChild(transname);

        // arg_from_previous element
        Element arg_from_previous = doc.createElement("arg_from_previous");
        arg_from_previous.setTextContent("N");
        entry.appendChild(arg_from_previous);

        // params_from_previous element
        Element params_from_previous = doc.createElement("params_from_previous");
        params_from_previous.setTextContent("N");
        entry.appendChild(params_from_previous);

        // exec_per_row element
        Element exec_per_row = doc.createElement("exec_per_row");
        exec_per_row.setTextContent("N");
        entry.appendChild(exec_per_row);

        // clear_rows element
        Element clear_rows = doc.createElement("clear_rows");
        clear_rows.setTextContent("N");
        entry.appendChild(clear_rows);

        // clear_files element
        Element clear_files = doc.createElement("clear_files");
        clear_files.setTextContent("N");
        entry.appendChild(clear_files);

        // set_logfile element
        Element set_logfile = doc.createElement("set_logfile");
        set_logfile.setTextContent("N");
        entry.appendChild(set_logfile);

        // logfile element
        Element logfile = doc.createElement("logfile");
        entry.appendChild(logfile);

        // logext element
        Element logext = doc.createElement("logext");
        entry.appendChild(logext);

        // add_date element
        Element add_date = doc.createElement("add_date");
        add_date.setTextContent("N");
        entry.appendChild(add_date);

        Element add_time = doc.createElement("add_time");
        add_time.setTextContent("N");
        entry.appendChild(add_time);

        Element loglevel = doc.createElement("loglevel");
        loglevel.setTextContent("Basic");
        entry.appendChild(loglevel);

        Element cluster = doc.createElement("cluster");
        cluster.setTextContent("N");
        entry.appendChild(cluster);

        Element slave_server_name = doc.createElement("slave_server_name");
        entry.appendChild(slave_server_name);

        Element set_append_logfile = doc.createElement("set_append_logfile");
        set_append_logfile.setTextContent("N");
        entry.appendChild(set_append_logfile);

        Element wait_until_finished = doc.createElement("wait_until_finished");
        wait_until_finished.setTextContent("Y");
        entry.appendChild(wait_until_finished);

        Element follow_abort_remote = doc.createElement("follow_abort_remote");
        follow_abort_remote.setTextContent("N");
        entry.appendChild(follow_abort_remote);

        Element create_parent_folder = doc.createElement("create_parent_folder");
        create_parent_folder.setTextContent("N");
        entry.appendChild(create_parent_folder);

        Element logging_remote_work = doc.createElement("logging_remote_work");
        logging_remote_work.setTextContent("N");
        entry.appendChild(logging_remote_work);

        Element parameters = doc.createElement("parameters");
        Element pass_all_parameters = doc.createElement("pass_all_parameters");
        pass_all_parameters.setTextContent("Y");
        parameters.appendChild(pass_all_parameters);
        entry.appendChild(parameters);

        Element parallel = doc.createElement("parallel");
        parallel.setTextContent("N");
        entry.appendChild(parallel);

        Element draw = doc.createElement("draw");
        draw.setTextContent("Y");
        entry.appendChild(draw);

        Element nr = doc.createElement("nr");
        nr.setTextContent("0");
        entry.appendChild(nr);

        Element xloc = doc.createElement("xloc");
        String posx = String.valueOf(trans.getPosX());
        xloc.setTextContent(posx);
        entry.appendChild(xloc);

        Element yloc = doc.createElement("yloc");
        String posy = String.valueOf(trans.getPosY());
        yloc.setTextContent(posy);
        entry.appendChild(yloc);

        return entry;
    }

    /**
     * Method in charge of connect each transformation and append it in the
     * element <code>hops</code>
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>hops</code> with all the child nodes
     */
    private Element createJobHops(Document doc) {

        // Root Element
        Element hops = doc.createElement("hops");

        // TODO: create hops between two job entries
        return hops;
    }

    /**
     * Method in charge of create a <code>job-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>job-log-table</code> with all the child
     * nodes
     */
    private Element createJobLogTable(Document doc) {

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

        JobLogConfiguration jobLogConfiguration = this.jobLogFactory.logTable();
        this.jobLogDirector.setJobLogBuilder(jobLogConfiguration);

        this.jobLogDirector.constructJobLog();

        JobLog jobLog = this.jobLogDirector.getJobLog();

        for (Field f : jobLog.getLog()) {
            jobLogTable.appendChild(this.createField(f, doc));
        }

        return jobLogTable;

    }

    /**
     * Method in charge of create a <code>jobentry-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>jobentry-log-table</code> with all the
     * child nodes
     */
    private Element createJobEntryLogTable(Document doc) {

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

        JobEntryLogConfiguration entryLogConfiguration = this.jobLogFactory.entryLogTable();
        this.jobLogDirector.setJobLogBuilder(entryLogConfiguration);

        this.jobLogDirector.constructJobLog();

        JobLog jobLog = this.jobLogDirector.getJobLog();

        for (Field f : jobLog.getLog()) {
            jobentryLogTable.appendChild(this.createField(f, doc));
        }

        return jobentryLogTable;
    }

    /**
     * Method in charge of create a <code>channel-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>channel-log-table</code> with all the
     * child nodes
     */
    private Element createJobChannelLogTabble(Document doc) {

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

        JobChannelLogConfiguration channelLogConfiguration = this.jobLogFactory.channelLogTable();
        this.jobLogDirector.setJobLogBuilder(channelLogConfiguration);

        this.jobLogDirector.constructJobLog();

        JobLog jobLog = this.jobLogDirector.getJobLog();

        for (Field f : jobLog.getLog()) {
            channelLogTable.appendChild(this.createField(f, doc));
        }

        return channelLogTable;
    }

    /**
     * Method in charge of create a <code>transformation</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>transformation</code> with all the child
     * nodes
     */
    private Document createTransformation(Transition t) {

        Document doc = this.documentBuilder.newDocument();

        // transformation Element
        Element transformation = doc.createElement("transformation");
        doc.appendChild(transformation);

        transformation.appendChild(this.createTransformationInfo(doc, t));

        // notepads element
        Element notepads = doc.createElement("notepads");
        transformation.appendChild(notepads);

        this.conversionDirector.setConversionBuilder(this.conversionFactory.newConversionBuilder(t.getSubPageInfo().getPage().getName()));

        boolean valid = this.conversionDirector.constructConversion(t);

        //TODO : warning the output
        if (valid) {
            Mapping mapping = this.conversionDirector.getMapping();

            transformation.appendChild(this.createTransformationOrder(doc, mapping.getOrders()));

            for (MappingComponent map : mapping.getComponents()) {
                transformation.appendChild(this.createTransformationStep(doc, map));
            }
        }

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
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>info</code> with all the child nodes
     */
    private Element createTransformationInfo(Document doc, Transition t) {

        // Info Element
        Element info = doc.createElement("info");

        // Name Element
        Element name = doc.createElement("name");
        name.setTextContent(t.getSubPageInfo().getPage().getName());
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

        info.appendChild(this.createTransformationLog(doc));

        info.appendChild(this.createTransformationMaxDate(doc));

        this.transInfoDirector.setTransLogBuilder(this.transLogFactory.transInfoConfiguration());

        this.transInfoDirector.constructTransInfo();

        TransInfo transInfo = this.transInfoDirector.getTransInfo();

        for (kettel.xml.Element e : transInfo.getElement()) {
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
        created_user.setTextContent(Utilities.getUser());
        info.appendChild(created_user);

        // created_date element
        Element created_date = doc.createElement("created_date");
        created_date.setTextContent(Utilities.today());
        info.appendChild(created_date);

        // modified_user element
        Element modified_user = doc.createElement("modified_user");
        modified_user.setTextContent(Utilities.getUser());
        info.appendChild(modified_user);

        // modified_date element
        Element modified_date = doc.createElement("modified_date");
        modified_date.setTextContent(Utilities.today());
        info.appendChild(modified_date);

        return info;
    }

    /**
     * Method in charge of create a <code>maxdate</code> element and return it
     * to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>maxdate</code> with all the child nodes
     */
    private Element createTransformationMaxDate(Document doc) {

        // maxdate Element
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
     * @param doc So that can be created elements to be added to the parent node
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
    private Element createTransformationLog(Document doc) {

        // log element
        Element log = doc.createElement("log");

        // trans-log-table element
        log.appendChild(this.createTransformationTransLogTable(doc));

        // perf-log-table element
        log.appendChild(this.createTransformationPerfLogTable(doc));

        // channel-log-table element
        log.appendChild(this.createTransformationChannelLogTable(doc));

        // step-log-table element
        log.appendChild(this.createTransformationStepLogTable(doc));

        // metrics-log-table element
        log.appendChild(this.createTransformationMetricsLogTable(doc));

        return log;
    }

    /**
     * Method in charge of create a <code>metrics-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>metrics-log-table</code> with all the
     * child nodes
     */
    private Element createTransformationMetricsLogTable(Document doc) {

        // metrics_log_table element
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

        TransMetricsLogConfiguration metricsLogConfiguration = this.transLogFactory.metricsLogConfiguration();

        this.transLogDirector.setTransLogBuilder(metricsLogConfiguration);
        this.transLogDirector.constructTransLog();

        TransLog transLog = this.transLogDirector.getTransLog();

        for (Field f : transLog.getFields()) {
            metrics_log_table.appendChild(this.createField(f, doc));
        }

        return metrics_log_table;
    }

    /**
     * Method in charge of create a <code>step-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>step-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationStepLogTable(Document doc) {

        // step_log_table element
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

        TransStepLogConfiguration stepLogConfiguration = this.transLogFactory.stepLogConfiguration();

        this.transLogDirector.setTransLogBuilder(stepLogConfiguration);
        this.transLogDirector.constructTransLog();

        TransLog transLog = this.transLogDirector.getTransLog();

        for (Field f : transLog.getFields()) {
            step_log_table.appendChild(this.createField(f, doc));
        }

        return step_log_table;
    }

    /**
     * Method in charge of create a <code>channel-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>channel-log-table</code> with all the
     * child nodes
     */
    private Element createTransformationChannelLogTable(Document doc) {

        // channel_log_table element
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

        TransChannelLogConfiguration channelTransLogConfiguration = this.transLogFactory.channelTransLogConfiguration();

        this.transLogDirector.setTransLogBuilder(channelTransLogConfiguration);
        this.transLogDirector.constructTransLog();

        TransLog transLog = this.transLogDirector.getTransLog();

        for (Field f : transLog.getFields()) {
            channel_log_table.appendChild(this.createField(f, doc));
        }

        return channel_log_table;
    }

    /**
     * Method in charge of create a <code>perf-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>perf-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationPerfLogTable(Document doc) {

        // perf-log-table element
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

        TransPerfLogConfiguration perfLogConfiguration = this.transLogFactory.perfLogConfiguration();

        this.transLogDirector.setTransLogBuilder(perfLogConfiguration);
        this.transLogDirector.constructTransLog();

        TransLog transLog = this.transLogDirector.getTransLog();

        for (Field f : transLog.getFields()) {
            perf_log_table.appendChild(this.createField(f, doc));
        }

        return perf_log_table;

    }

    /**
     * Method in charge of create a <code>trans-log-table</code> element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} <code>trans-log-table</code> with all the child
     * nodes
     */
    private Element createTransformationTransLogTable(Document doc) {

        // trans-log-table element
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

        TransLogConfiguration transLogConfiguration = this.transLogFactory.transLogConfiguration();

        this.transLogDirector.setTransLogBuilder(transLogConfiguration);
        this.transLogDirector.constructTransLog();

        TransLog transLog = this.transLogDirector.getTransLog();

        for (Field f : transLog.getFields()) {
            trans_log_table.appendChild(this.createField(f, doc));
        }

        return trans_log_table;
    }

    /**
     * Method in charge of create a {@code order} element and return it to be
     * appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @param orders An ArrayList of {@code MappingOrder} with the connections between every Kettle ETL component
     * @return A {@link Element} {@code order} with all the child nodes
     */
    private Element createTransformationOrder(Document doc, ArrayList<MappingOrder> orders) {

        Element order = doc.createElement("order");

        for (MappingOrder o : orders) {
            order.appendChild(this.createTransformationHop(doc, o));
        }

        return order;
    }

    /**
     * Method in charge of create a {@code hop} element and return it to be
     * appended
     * @param doc So that can be created elements to be added to the parent node
     * @param order One specific connection between two kettle elements
     * @return A {@link Element} {@code hop} with all the child nodes
     */
    private Element createTransformationHop(Document doc, MappingOrder order) {

        Element hop = doc.createElement("hop");

        Element from = doc.createElement("from");
        from.setTextContent(order.getFrom().getCpnElement());
        hop.appendChild(from);

        Element to = doc.createElement("to");
        to.setTextContent(order.getTo().getCpnElement());
        hop.appendChild(to);

        Element enabled = doc.createElement("enabled");
        enabled.setTextContent("Y");
        hop.appendChild(enabled);

        return hop;
    }

    /**
     * Method in charge of create a {@code step} element and return it to be
     * appended
     *
     * @param doc Original document capable of create element to be appended
     * @param map MappingComponent with the Kettle ETL components mapped from the CPN Pattern
     * @return A {@link Element} {@code step} with all the child nodes
     */
    private Element createTransformationStep(Document doc, MappingComponent map) {

        Element step = doc.createElement("step");

        Element name = doc.createElement("name");
        name.setTextContent(map.getCpnElement());
        step.appendChild(name);

        Element type = doc.createElement("type");
        type.setTextContent(map.getKettleElement());
        step.appendChild(type);

        Element description = doc.createElement("description");
        step.appendChild(description);

        Element distribute = doc.createElement("distribute");
        distribute.setTextContent("Y");
        step.appendChild(distribute);

        Element custom_distribution = doc.createElement("custom_distribution");
        step.appendChild(custom_distribution);

        Element copies = doc.createElement("copies");
        copies.setTextContent("1");
        step.appendChild(copies);

        step.appendChild(this.createTrasnformationPartitioning(doc));

        StepDirector stepDirector = this.stepFactory.newStepDirector();

        stepDirector.setStepBuilder(this.stepFactory.newStepConfiguration(map.getKettleElement()));
        stepDirector.constructStep();
        Step stepConfiguration = stepDirector.getStep();

        for (kettel.xml.Element e : stepConfiguration.getElements()) {
            Element a = doc.createElement(e.getTag());
            a.setTextContent(e.getContextText());

            step.appendChild(a);
        }

        Element connection = doc.createElement("connection");
        step.appendChild(connection);

        Element execute_each_row = doc.createElement("execute_each_row");
        execute_each_row.setTextContent("N");
        step.appendChild(execute_each_row);

        Element variables_active = doc.createElement("variables_active");
        variables_active.setTextContent("N");
        step.appendChild(variables_active);

        Element lazy_conversion_active = doc.createElement("lazy_conversion_active");
        lazy_conversion_active.setTextContent("N");
        step.appendChild(lazy_conversion_active);

        Element cluster_schema = doc.createElement("cluster_schema");
        step.appendChild(cluster_schema);

        step.appendChild(this.createTransformationRemoteStep(doc));

        step.appendChild(this.createTransformationGUI(doc, map));

        return step;
    }

    /**
     * Method in charge of create a {@code partitioning} element and return
     * it to be appended
     *
     * @param doc Original document capable of create element to be appended
     * @return A {@link Element} {@code partitioning} with all the child
     * nodes
     */
    private Element createTrasnformationPartitioning(Document doc) {

        Element partitioning = doc.createElement("partitioning");
        Element method = doc.createElement("method");
        method.setTextContent("none");
        partitioning.appendChild(method);
        Element schema_name = doc.createElement("schema_name");
        partitioning.appendChild(schema_name);

        return partitioning;
    }

    /**
     * Method in charge of create a {@code remotesteps} element and return
     * it to be appended
     *
     * @param doc Original document capable of create element to be appended
     * @return A {@link Element} {@code remotesteps} with all the child
     * nodes
     */
    private Element createTransformationRemoteStep(Document doc) {
        Element remotesteps = doc.createElement("remotesteps");

        Element input = doc.createElement("input");
        remotesteps.appendChild(input);

        Element output = doc.createElement("output");
        remotesteps.appendChild(output);

        return remotesteps;
    }

    /**
     * Method in charge of create a {@code GUI} element and return it to be
     * appended
     *
     * @param doc Original document capable of create element to be appended
     * @param map MappingComponent with the axis positions
     * @return A {@link Element} {@code GUI} with all the child nodes
     */
    private Element createTransformationGUI(Document doc, MappingComponent map) {

        Element gui = doc.createElement("GUI");

        Element xloc = doc.createElement("xloc");
        xloc.setTextContent(map.getXloc());
        gui.appendChild(xloc);

        Element yloc = doc.createElement("yloc");
        yloc.setTextContent(map.getYloc());
        gui.appendChild(yloc);

        Element draw = doc.createElement("draw");
        draw.setTextContent("Y");
        gui.appendChild(draw);

        return gui;

    }

    /**
     * Method that returns a field log element according the field object
     *
     * @param nameField Object field with the information obtain on the
     * configuration files
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code field} with all the child nodes
     */
    private Element createField(Field nameField, Document doc) {

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
