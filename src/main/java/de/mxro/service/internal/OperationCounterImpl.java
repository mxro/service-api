package de.mxro.service.internal;

import delight.concurrency.Concurrency;
import delight.concurrency.wrappers.SimpleAtomicInteger;

import de.mxro.service.utils.OperationCounter;

public class OperationCounterImpl implements OperationCounter {

    private final SimpleAtomicInteger activeRequests;

    @Override
    public void increase() {
        activeRequests.incrementAndGet();
    }

    @Override
    public void decrease() {
        activeRequests.decrementAndGet();
    }

    @Override
    public int count() {
        return activeRequests.get();
    }

    public OperationCounterImpl(final Concurrency con) {
        super();
        this.activeRequests = con.newAtomicInteger(0);
    }

}
