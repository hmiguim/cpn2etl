package kettel.transformation;

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
    
    public TransLogDirector newTransLogDirector() {
        return new TransLogDirector();
    }
    
    public TransInfoDirector newTransInfoDirector() {
        return new TransInfoDirector();
    }

    /**
     * Creates a new instance of a {@code transLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a transLogTable
     */
    public TransLogConfiguration transLogConfiguration() {
        return new TransLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code perfLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a perfLogTable
     */
    public TransPerfLogConfiguration perfLogConfiguration() {
        return new TransPerfLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code channelTransLogTable} using the
     * currently configured parameters.
     *
     * @return A new instance of a channelTransLogTable
     */
    public TransChannelLogConfiguration channelTransLogConfiguration() {
        return new TransChannelLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code stepLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a stepLogTable
     */
    public TransStepLogConfiguration stepLogConfiguration() {
        return new TransStepLogConfiguration();
    }

    /**
     * Creates a new instance of a {@code metricsLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a metricsLogTable
     */
    public TransMetricsLogConfiguration metricsLogConfiguration() {
        return new TransMetricsLogConfiguration();
    }
    
    public TransInfoConfiguration transInfoConfiguration() {
        return new TransInfoConfiguration();
    }
}
