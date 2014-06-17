package xml.builder;

import xml.XMLHelper;
import cpn.Cpn;
import cpn.Transition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pdi.components.job.JobChannelLogConfiguration;
import pdi.components.job.JobEntryLogConfiguration;
import pdi.components.job.JobLog;
import pdi.components.job.JobLogConfiguration;
import pdi.components.job.JobLogDirector;
import pdi.components.job.JobLogFactory;
import pdi.components.xml.Field;
import transformation.pattern.PatternBuilder;
import transformation.pattern.PatternDirector;
import transformation.pattern.PatternFactory;
import transformation.pattern.activity.PatternActivityBuilder;
import transformation.pattern.activity.PatternActivityDirector;
import transformation.pattern.activity.PatternActivityFactory;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class JobBuilder {

    private final DocumentBuilder documentBuilder;
  
    private final JobLogFactory jobLogFactory;
    private final JobLogDirector jobLogDirector;
    private final PatternFactory patternFactory;
    private final PatternDirector patternDirector;
    private final PatternActivityFactory patternActivityFactory;
    private final PatternActivityDirector patternActivityDirector;
    private final TransformationBuilder transformationBuilder;
    private final Cpn pages;
    private final String path;
    private final String filename;
    
    /**
     *
     * @param documentBuilder
     * @param transformationBuilder
     * @param path
     * @param filename
     * @param pages
     * @throws ParserConfigurationException
     */
    public JobBuilder(DocumentBuilder documentBuilder, TransformationBuilder transformationBuilder, String path, String filename, Cpn pages) throws ParserConfigurationException {
        this.jobLogFactory = JobLogFactory.newInstance();
        this.jobLogDirector = this.jobLogFactory.newJobLogDirector();
        this.patternFactory = PatternFactory.newInstance();
        this.patternDirector = this.patternFactory.newPatternDirector();
        this.patternActivityFactory = PatternActivityFactory.newInstance();
        this.patternActivityDirector = this.patternActivityFactory.newPatternActivityDirector();
        this.documentBuilder = documentBuilder;
        this.transformationBuilder = transformationBuilder;
        this.pages = pages;
        this.path = path;
        this.filename = filename;
    }
    
    /**
     * Method that returns a {@link Document} for the Job element in Kettle
     * specification file
     * 
     * @return A {@link Document} with the Job {@link Element} as root with all
     * the child nodes
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    public Document createJob() throws TransformerException, FileNotFoundException, UnsupportedEncodingException {

        Document document = this.documentBuilder.newDocument();

        // Root Element
        Element job = document.createElement("job");
        document.appendChild(job);

        // name element
        Element name = document.createElement("name");
        name.setTextContent(filename);
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
        created_user.setTextContent(Helper.getUser());
        job.appendChild(created_user);

        // created_date elememt
        Element created_date = document.createElement("created_date");
        created_date.setTextContent(Helper.today());
        job.appendChild(created_date);

        // modified_user element
        Element modified_user = document.createElement("modified_user");
        modified_user.setTextContent(Helper.getUser());
        job.appendChild(modified_user);

        // modified_date element
        Element modified_date = document.createElement("modified_date");
        modified_date.setTextContent(Helper.today());
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
        job.appendChild(this.createJobEntries(document,this.pages));

        // hops elements
        job.appendChild(this.createJobHops(document));

        // notepads elements
        Element notepads = document.createElement("notepads");
        job.appendChild(notepads);

        return document;
    }
    
    /**
     * Method in charge of translate each transformation in one job entry and
     * append it in the element {@code entry}
     *
     * @param doc So that can be created elements to be added to the parent node
     * @param trans
     * @return A {@link Element} {@code entry} with all the child nodes
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
        filename_element.setTextContent(this.path + "/" + Helper.normalize(trans.getSubPageInfo().getPage().getName()) + ".ktr");
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
     * Method in charge of create a {@code jobentry-log-table} element and
     * return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element}  {@code jobentry-log-table} with all the child
     * nodes
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
            jobentryLogTable.appendChild(XMLHelper.createField(f, doc));
        }

        return jobentryLogTable;
    }
    
    /**
     * Method in charge of create a {@code channel-log-table} element and return
     * it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code channel-log-table} with all the child
     * nodes
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
            channelLogTable.appendChild(XMLHelper.createField(f, doc));
        }

        return channelLogTable;
    }
    
    /**
     * Method in charge of connect each transformation and append it in the
     * element {@code hops}
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code hops} with all the child nodes
     */
    private Element createJobHops(Document doc) {

        // Root Element
        Element hops = doc.createElement("hops");

        // TODO: create hops between two job entries
        return hops;
    }
    
    /**
     * Method in charge of create a {@code job-log-table} element and return it
     * to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code job-log-table} with all the child nodes
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
            jobLogTable.appendChild(XMLHelper.createField(f, doc));
        }

        return jobLogTable;

    }
    
    /**
     * Method in charge of translate each transformation in one job entry and
     * append it in the element {@code entries}
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code entries} with all the child nodes
     */
    private Element createJobEntries(Document doc,Cpn cpnPages) throws TransformerException, FileNotFoundException, UnsupportedEncodingException {

        // Root element
        Element entries = doc.createElement("entries");

        Collection<Transition> patterns = cpnPages.getPatternsMainPage();

        File f;
        Document transformation;
        Document activityTransformation;

        for (Transition pattern : patterns) {

            PatternBuilder patternBuilder = this.patternFactory.newPatternBuilder(pattern.getSubPageInfo().getPage().getName());

            if (patternBuilder != null) {
                this.patternDirector.setPatternBuilder(patternBuilder);

                boolean valid = this.patternDirector.constructPattern(pattern);

                if (valid) {
                    entries.appendChild(this.createJobEntry(doc, pattern));
                    transformation = this.transformationBuilder.createTransformation(pattern, this.patternDirector.getMapping());

                    f = new File(this.path + "/" + Helper.normalize(pattern.getSubPageInfo().getPage().getName()) + ".ktr");
                    XMLHelper.finalize(transformation, f);

                    if (pattern.getSubPageInfo().getPage().getName().equals("SCD/H 3") || pattern.getSubPageInfo().getPage().getName().equals("CDC 1")) {
                        ArrayList<Transition> modulesPerPage = pattern.getSubPageInfo().getPage().getModulesPerPage();
                        for (Transition t : modulesPerPage) {
                           PatternActivityBuilder patternActivityBuilder = this.patternActivityFactory.newPatternActivityBuilder(t.getText());
                           
                           if (patternActivityBuilder != null) {
                               this.patternActivityDirector.setPatternActivityBuilder(patternActivityBuilder);
                               
                               boolean activity = this.patternActivityDirector.constructPatternActivity(t);
                               
                               if (activity) {
                                   activityTransformation = this.transformationBuilder.createTransformation(t, this.patternActivityDirector.getMapping(),this.patternActivityDirector.getNotepads());
                                   File file = new File(this.path + "/" + Helper.normalize(t.getSubPageInfo().getName()) + ".ktr");
                                   XMLHelper.finalize(activityTransformation,file);
                               }
                           }
                        }
                    }
                }
            }
        }

        return entries;
    }
}
