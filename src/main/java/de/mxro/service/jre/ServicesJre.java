package de.mxro.service.jre;

import delight.concurrency.jre.JreConcurrency;

import de.mxro.service.Services;
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
        return Services.createOperationCounter(new JreConcurrency());
    }

    /**
     * <p>
     * A helper to make shutdown operations safer.
     * 
     * @param operationCounter
     * @return
     */
    public static final ShutdownHelper createShutdownHelper(final OperationCounter operationCounter) {
        return Services.createShutdownHelper(operationCounter, new JreConcurrency());
    }

}
