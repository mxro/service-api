package de.mxro.service.jre.internal;

import java.util.concurrent.atomic.AtomicInteger;

import de.mxro.service.utils.ActivityMonitor;

public class ServiceActivityMonitorImpl implements ActivityMonitor {

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
