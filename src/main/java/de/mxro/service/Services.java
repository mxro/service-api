package de.mxro.service;

import delight.concurrency.Concurrency;

import de.mxro.service.internal.OperationCounterImpl;
import de.mxro.service.internal.ServiceRegistryImpl;
import de.mxro.service.internal.ShutdownHelperImpl;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class Services {

    /**
     * <p>
     * A simple counter for how many operations a service is processing at any
     * point in time.
     * 
     * @return A new operation counter instance.
     */
    public static final OperationCounter createOperationCounter(final Concurrency con) {
        return new OperationCounterImpl(con);
    }

    /**
     * <p>
     * A helper to make shutdown operations safer.
     * 
     * @param operationCounter
     * @return
     */
    public static final ShutdownHelper createShutdownHelper(final OperationCounter operationCounter,
            final Concurrency con) {
        return new ShutdownHelperImpl(operationCounter, con);
    }

    public static ServiceRegistry create() {
        return new ServiceRegistryImpl();
    }

}
