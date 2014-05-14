package xml.builder;

import xml.XMLHelper;
import cpn.Transition;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pdi.components.info.TransInfo;
import pdi.components.info.TransInfoDirector;
import pdi.components.log.TransChannelLogConfiguration;
import pdi.components.log.TransLog;
import pdi.components.log.TransLogConfiguration;
import pdi.components.log.TransLogDirector;
import pdi.components.log.TransMetricsLogConfiguration;
import pdi.components.log.TransPerfLogConfiguration;
import pdi.components.log.TransStepLogConfiguration;
import pdi.components.log.TransformationFactory;
import pdi.components.notepad.Notepad;
import pdi.components.step.Step;
import pdi.components.step.StepDirector;
import pdi.components.step.StepFactory;
import pdi.components.xml.Field;
import transformation.mapping.Mapping;
import transformation.mapping.MappingComponent;
import transformation.mapping.MappingOrder;
import utils.Helper;

/**
 *
 * @author hmg
 */
public class TransformationBuilder {
    
    private final DocumentBuilder documentBuilder;
    private final String path;
    
    
    private final StepFactory stepFactory;
    private final TransformationFactory transLogFactory;
    private final TransLogDirector transLogDirector;
    private final TransInfoDirector transInfoDirector;
    
    public TransformationBuilder(DocumentBuilder documentBuilder, String path) throws ParserConfigurationException {
        this.stepFactory = StepFactory.newInstance();
        this.transLogFactory = TransformationFactory.newInstance();
        this.transLogDirector = this.transLogFactory.newTransLogDirector();
        this.transInfoDirector = this.transLogFactory.newTransInfoDirector();
        this.documentBuilder = documentBuilder;
        this.path = path;
    }
    
    /**
     * Method in charge of create a {@code transformation} element and return it
     * to be appended
     *
     * @param t
     * @param mapping
     * @param notepads
     * @return A {@link Element} {@code transformation} with all the child nodes
     */
    public Document createTransformation(Transition t, Mapping mapping, ArrayList<Notepad> notepads) {

        Document doc = this.documentBuilder.newDocument();

        // transformation Element
        Element transformation = doc.createElement("transformation");
        doc.appendChild(transformation);

        transformation.appendChild(this.createTransformationInfo(doc, t));

        // notepads element
        transformation.appendChild(this.createTransformationNotepads(doc, notepads));

        transformation.appendChild(this.createTransformationOrder(doc, mapping.getOrders()));

        for (MappingComponent map : mapping.getComponents()) {
            transformation.appendChild(this.createTransformationStep(doc, map));
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
     * Method in charge of create a {@code transformation} element and return it
     * to be appended
     *
     * @param t
     * @param mapping
     * @return A {@link Element} {@code transformation} with all the child nodes
     */
    public Document createTransformation(Transition t, Mapping mapping) {

        Document doc = this.documentBuilder.newDocument();

        // transformation Element
        Element transformation = doc.createElement("transformation");
        doc.appendChild(transformation);

        transformation.appendChild(this.createTransformationInfo(doc, t));

        // notepads element
        transformation.appendChild(this.createTransformationNotepads(doc, null));

        transformation.appendChild(this.createTransformationOrder(doc, mapping.getOrders()));

        for (MappingComponent map : mapping.getComponents()) {
            transformation.appendChild(this.createTransformationStep(doc, map));
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
     * Method in charge of create a {@code info} element and return it to be
     * appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code info} with all the child nodes
     */
    private Element createTransformationInfo(Document doc, Transition t) {

        // Info Element
        Element info = doc.createElement("info");

        // Name Element
        Element name = doc.createElement("name");
        name.setTextContent(Helper.normalize(t.getSubPageInfo().getPage().getName()));
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

        for (pdi.components.xml.Element e : transInfo.getElement()) {
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
        created_user.setTextContent(Helper.getUser());
        info.appendChild(created_user);

        // created_date element
        Element created_date = doc.createElement("created_date");
        created_date.setTextContent(Helper.today());
        info.appendChild(created_date);

        // modified_user element
        Element modified_user = doc.createElement("modified_user");
        modified_user.setTextContent(Helper.getUser());
        info.appendChild(modified_user);

        // modified_date element
        Element modified_date = doc.createElement("modified_date");
        modified_date.setTextContent(Helper.today());
        info.appendChild(modified_date);

        return info;
    }
    
    /**
     * Method in charge of create a {@code maxdate} element and return it to be
     * appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@cde maxdate} with all the child nodes
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
    
    private Element createTransformationNotepads(Document doc, ArrayList<Notepad> npdas) {
        
        // Notepads Element
        Element notepads = doc.createElement("notepads");
        
        if (npdas == null) {
            return notepads;
        }
        
        for (Notepad n : npdas) {
            
            Element notepad = doc.createElement("notepad");
            
            Element note = doc.createElement("note");
            note.setTextContent(n.getNote());
            notepad.appendChild(note);
            
            Element xloc = doc.createElement("xloc");
            xloc.setTextContent(n.getXloc());
            notepad.appendChild(xloc);
            
            Element yloc = doc.createElement("yloc");
            yloc.setTextContent(n.getYloc());
            notepad.appendChild(yloc);
            
            for (pdi.components.xml.Element e : n.getElements()) {
                Element a = doc.createElement(e.getTag());
                a.setTextContent(e.getContextText());

                notepad.appendChild(a);
            }
            
            notepads.appendChild(notepad);
            
        }
        
        return notepads;
    }
    
    /**
     * Method in charge of create a {@code order} element and return it to be
     * appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @param orders An ArrayList of {@code MappingOrder} with the connections
     * between every Kettle ETL component
     * @return A {@link Element} {@code order} with all the child nodes
     */
    private Element createTransformationOrder(Document doc, ArrayList<MappingOrder> orders) {

        Element order = doc.createElement("order");

        if (orders != null) {
            for (MappingOrder o : orders) {
                order.appendChild(this.createTransformationHop(doc, o));
            }
        }
        return order;
    }
    
    /**
     * Method in charge of create a {@code hop} element and return it to be
     * appended
     *
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
     * @param map MappingComponent with the Kettle ETL components mapped from
     * the CPN Pattern
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

        StepDirector stepDirector = this.stepFactory.newStepDirector();

        stepDirector.setStepBuilder(this.stepFactory.newStepConfiguration(map.getKettleElement()));
        stepDirector.constructStep();
        Step stepConfiguration = stepDirector.getStep();

        for (pdi.components.xml.Element e : stepConfiguration.getElements()) {
            Element a = doc.createElement(e.getTag());
            a.setTextContent(e.getContextText());

            step.appendChild(a);
        }

        step.appendChild(this.createTransformationGUI(doc, map));
        
        if (map.getKettleElement().equals("TransExecutor")) {
            Element file = doc.createElement("filename");
            file.setTextContent(this.path + "/" + Helper.normalize(map.getFilename()) + ".ktr");
            step.appendChild(file);
        }

        return step;
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
     * Method in charge of create all the different child log elements
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} with all the child nodes:
     * <p>
     * - {@code trans-log-table}</p>
     * <p>
     * - {@code perf-log-table}</p>
     * <p>
     * - {@code perf-log-table}</p>
     * <p>
     * - {@code channel-log-table}</p>
     * <p>
     * - {@code step-log-table}</p>
     * <p>
     * - {@code metrics-log-table}</p>
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
     * Method in charge of create a {@code step-log-table} element and return it
     * to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code step-log-table} with all the child nodes
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
            step_log_table.appendChild(XMLHelper.createField(f, doc));
        }

        return step_log_table;
    }
    
    /**
     * Method in charge of create a {@code channel-log-table} element and return
     * it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code channel-log-table} with all the child
     * nodes
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
            channel_log_table.appendChild(XMLHelper.createField(f, doc));
        }

        return channel_log_table;
    }
    
    /**
     * Method in charge of create a {@code perf-log-table} element and return it
     * to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code perf-log-table} with all the child nodes
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
            perf_log_table.appendChild(XMLHelper.createField(f, doc));
        }

        return perf_log_table;

    }
    
    /**
     * Method in charge of create a {
     *
     * @codemetrics-log-table} element and return it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code metrics-log-table} with all the child
     * nodes
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
            metrics_log_table.appendChild(XMLHelper.createField(f, doc));
        }

        return metrics_log_table;
    }
    
    /**
     * Method in charge of create a {@code trans-log-table} element and return
     * it to be appended
     *
     * @param doc So that can be created elements to be added to the parent node
     * @return A {@link Element} {@code trans-log-table} with all the child
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
            trans_log_table.appendChild(XMLHelper.createField(f, doc));
        }

        return trans_log_table;
    }
}
