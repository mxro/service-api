package de.mxro.service.jre.internal;

import java.util.concurrent.atomic.AtomicInteger;

import de.mxro.service.ServiceActivityMonitor;

public class ServiceActivityMonitorImpl implements ServiceActivityMonitor {

	AtomicInteger activeRequests;
	
	@Override
	public void notifyOperationStarted() {
		activeRequests.incrementAndGet();
	}

	@Override
	public void notifyOperationCompleted() {
		activeRequests.decrementAndGet();
	}

	@Override
	public int pendingOperations() {
		return activeRequests.get();
	}

}
