package de.mxro.service.jre;

import one.utils.jre.concurrent.JreConcurrency;
import de.mxro.service.internal.OperationCounterImpl;
import de.mxro.service.internal.ShutdownHelperImpl;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class ServicesJre {

    /**
     * <p>
     * A simple counter for how many operations a service is processing at any
     * point in time.
     * 
     * @return A new operation counter instance.
     */
    public static final OperationCounter createOperationCounter() {
        return new OperationCounterImpl(new JreConcurrency());
    }

    /**
     * <p>
     * A helper to make shutdown operations safer.
     * 
     * @param operationCounter
     * @return
     */
    public static final ShutdownHelper createShutdownHelper(final OperationCounter operationCounter) {
        return new ShutdownHelperImpl(operationCounter, new JreConcurrency());
    }

}
