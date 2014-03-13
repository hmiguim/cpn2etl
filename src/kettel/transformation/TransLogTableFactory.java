package kettel.transformation;

/**
 *
 * @author hmg
 */
public class TransLogTableFactory {

    /**
     * Protected constructor
     */
    protected TransLogTableFactory() {

    }

    /**
     * Obtain a new instance of a {@code TransLogTableFactory}. This static
     * method creates a new factory instance.
     *
     * @return A new instance of a {@code TransLogTableFactory}
     */
    public static TransLogTableFactory newInstance() {
        return new TransLogTableFactory();
    }

    /**
     * Creates a new instance of a {@code transLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a transLogTable
     */
    public TransLogTable transLogTable() {
        return new TransLogTable();
    }

    /**
     * Creates a new instance of a {@code perfLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a perfLogTable
     */
    public TransPerfLogTable perfLogTable() {
        return new TransPerfLogTable();
    }

    /**
     * Creates a new instance of a {@code channelTransLogTable} using the
     * currently configured parameters.
     *
     * @return A new instance of a channelTransLogTable
     */
    public TransChannelLogTable channelTransLogTable() {
        return new TransChannelLogTable();
    }

    /**
     * Creates a new instance of a {@code stepLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a stepLogTable
     */
    public TransStepLogTable stepLogTable() {
        return new TransStepLogTable();
    }

    /**
     * Creates a new instance of a {@code metricsLogTable} using the currently
     * configured parameters.
     *
     * @return A new instance of a metricsLogTable
     */
    public TransMetricsLogTable metricsLogTable() {
        return new TransMetricsLogTable();
    }

    /**
     * Creates a new instance of a {@code info} using the currently configured
     * parameters.
     *
     * @return A new instance of a info
     */
    public TransInfo info() {
        return new TransInfo();
    }
}
