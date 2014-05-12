package pdi.components.log;

import pdi.components.log.TransChannelLogConfiguration;
import pdi.components.info.TransInfoConfiguration;
import pdi.components.info.TransInfoDirector;

/**
 *
 * @author hmg
 */
public class TransformationFactory {

    /**
     * Protected constructor
     */
    protected TransformationFactory() {

    }

    /**
     * Obtain a new instance of a {@code TransLogTableFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code TransLogTableFactory}
     */
    public static TransformationFactory newInstance() {
        return new TransformationFactory();
    }
    
    /**
     * Obtain a new instance of a {@code TransLogDirector}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code TransLogDirector}
     */
    public TransLogDirector newTransLogDirector() {
        return new TransLogDirector();
    }
    
    /**
     * Obtain a new instance of a {@code TransInfoDirector}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code TransInfoDirector}
     */
    public TransInfoDirector newTransInfoDirector() {
        return new TransInfoDirector();
    }

    /**
     * Creates a new instance of a {@code TransLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a TransLogConfiguration
     */
    public TransLogConfiguration transLogConfiguration() {
        return new TransLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code TransPerfLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a TransPerfLogConfiguration
     */
    public TransPerfLogConfiguration perfLogConfiguration() {
        return new TransPerfLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code TransChannelLogConfiguration} using the
     * currently configured parameters.
     *
     * @return A new instance of a TransChannelLogConfiguration
     */
    public TransChannelLogConfiguration channelTransLogConfiguration() {
        return new TransChannelLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code TransStepLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a TransStepLogConfiguration
     */
    public TransStepLogConfiguration stepLogConfiguration() {
        return new TransStepLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code TransMetricsLogConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a metriTransMetricsLogConfigurationcsLogTable
     */
    public TransMetricsLogConfiguration metricsLogConfiguration() {
        return new TransMetricsLogConfiguration();
    }
    
    /**
     * Creates a new instance of a {@code TransInfoConfiguration} using the currently
     * configured parameters.
     *
     * @return A new instance of a TransInfoConfiguration
     */
    public TransInfoConfiguration transInfoConfiguration() {
        return new TransInfoConfiguration();
    }
}
