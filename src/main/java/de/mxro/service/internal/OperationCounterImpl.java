package de.mxro.service.internal;

import java.util.concurrent.atomic.AtomicInteger;

import de.mxro.service.utils.OperationCounter;

public class OperationCounterImpl implements OperationCounter {

	private final AtomicInteger activeRequests;
	
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

	public OperationCounterImpl() {
		super();
		this.activeRequests = new AtomicInteger(0);
	}

	
	
}
